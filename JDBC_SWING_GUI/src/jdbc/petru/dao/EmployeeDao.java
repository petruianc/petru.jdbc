package jdbc.petru.dao;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jdbc.petru.model.AuditHistory;
import jdbc.petru.model.Employee;
import jdbc.petru.model.User;

public class EmployeeDao {
	
	private Connection conn;
	
	public EmployeeDao() throws Exception{
		Properties props = new Properties();
		props.load(new FileInputStream("sql/databaseConnection.properties"));
		
		String url = props.getProperty("dburl");
		String pass = props.getProperty("password");
		String user = props.getProperty("user");
		
		conn = DriverManager.getConnection(url, user, pass);
		System.out.println("Successfully connected to database: "+conn.getMetaData().getDatabaseProductName());
		
	}
	
	public User convertUserToRow(ResultSet rs) throws Exception{
		int id = rs.getInt("id");
		String lastName = rs.getString("last_name");
		String firstName = rs.getString("first_name");
		String email = rs.getString("email");
		String password = rs.getString("password");
		
		User user = new User(id, lastName, firstName, email, password);
		return user;
	}
	
	public List<User> getUsers(){
		String sql = "select * from users order by last_name";
		List<User> users = null;
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql))
		{
			users = new ArrayList<>();
			while(rs.next()) {
				User user = convertUserToRow(rs);
				users.add(user);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			return users;
	}
	
	public Employee convertEmployeeToRow(ResultSet rs) throws Exception{
		int id = rs.getInt("id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		String email = rs.getString("email");
		String department = rs.getString("department");
		BigDecimal salary = rs.getBigDecimal("salary");
		
		Employee emp = new Employee(id, lastName, firstName, email, department, salary);
		return emp;
		
	}
	
	public List<Employee> getEmployees(){
		String sql = "select id, first_name, last_name, email, department, salary from employees";
		List<Employee> employees = null;
		
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql))
		{
			employees = new ArrayList<>();
			while(rs.next()) {
				Employee emp = convertEmployeeToRow(rs);
				employees.add(emp);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			return employees;
		
	}
	
	public List<Employee> searchEmployees(String lastName){
		List<Employee> emp = new ArrayList<>();
		
		lastName += "%";
		
		try(PreparedStatement stmt = conn.prepareStatement("select id, first_name, last_name, email, department, salary from Employees where last_name like ?"))
		{
			stmt.setString(1, lastName);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Employee e = convertEmployeeToRow(rs);
				emp.add(e);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return emp;
	}
	
	public boolean addEmployee(Employee emp, int userId) {
		String sql = "insert into employees(first_name, last_name, email, department, salary) values(?,?,?,?,?)";
		boolean add = false;
		try(PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS))
		{
			stmt.setString(1, emp.getFirstName());
			stmt.setString(2, emp.getLastName());
			stmt.setString(3, emp.getEmail());
			stmt.setString(4, emp.getDepartment());
			stmt.setBigDecimal(5, emp.getSalary());
			
			int row = stmt.executeUpdate();
			if(row>0) {
				add = true;
			}
			// get the generated key
			try(ResultSet rs = stmt.getGeneratedKeys()){
				while(rs.next()) {
					emp.setId(rs.getInt(1));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			// insert into audit history
			String insert = "insert into audit_history(user_id, employee_id, action, action_date_time) values(?,?,?,?)";
			try(PreparedStatement pStmt = conn.prepareStatement(insert))
			{
				pStmt.setInt(1, userId);
				pStmt.setInt(2, emp.getId());
				pStmt.setString(3, "Employee added!");
				pStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				
				pStmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			return add;
	}
	
	public boolean updateEmployee(Employee emp, int userId) {
		
		String sql = "update employees"
				+ " set first_name=?, last_name=?, email=?, department = ?, salary=?"
				+ " where id=?";
		boolean update = false;
		try(PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setString(1, emp.getFirstName());
			stmt.setString(2, emp.getLastName());
			stmt.setString(3, emp.getEmail());
			stmt.setString(4, emp.getDepartment());
			stmt.setBigDecimal(5, emp.getSalary());
			stmt.setInt(6, emp.getId());
			
			int row = stmt.executeUpdate();
			
			// insert into audit history
			String newSql = "insert into audit_history(user_id, employee_id, action, action_date_time) values(?,?,?,?)";
			if(row>0)try(PreparedStatement pStmt = conn.prepareStatement(newSql))
			{
				update = true;
				pStmt.setInt(1, userId);
				pStmt.setInt(2, emp.getId());
				pStmt.setString(3, "Employee updated!");
				pStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				
				pStmt.executeUpdate();
			}
				
		}catch(Exception e) {
			e.printStackTrace();
		}
			return update;
	}
	
	public boolean setUnemployed(Employee emp, int userId) {
		String sql = "update employees set department = ?, salary = ? where id = ?";
		boolean update = false;
		try(PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setString(1, null);
			stmt.setBigDecimal(2, BigDecimal.valueOf(0.0));
			stmt.setInt(3, emp.getId());
			
			int row = stmt.executeUpdate();
			
			// add to audit history
			String insertSql = "insert into audit_history(user_id, employee_id, action, action_date_time) values(?,?,?,?)";
			if(row>0)try(PreparedStatement pStmt = conn.prepareStatement(insertSql))
			{
				update = true;
				pStmt.setInt(1, userId);
				pStmt.setInt(2, emp.getId());
				pStmt.setString(3, "Employee fired!");
				pStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				
				pStmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			return update;
	}
	
	public List<AuditHistory> getAuditHistory(int employeeId){
		String sql = "SELECT history.user_id, history.employee_id, history.action, history.action_date_time, users.first_name, users.last_name  "
				+ "FROM audit_history history, users users "
				+ "WHERE history.user_id=users.id AND history.employee_id=" + employeeId;
		List<AuditHistory> histories = new ArrayList<>();
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql))
		{
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				int empId = rs.getInt("employee_id");
				String action = rs.getString("action");
				Timestamp tmp = rs.getTimestamp("action_date_time");
				String firstName = rs.getString("first_name");
				String lastName =rs.getString("last_name");
				AuditHistory audit = new AuditHistory(userId, empId, action, tmp, firstName, lastName);
				histories.add(audit);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			return histories;
	}
	
//	public void deleteEmployee(Employee emp, int userId) {
//		String sql = "delete from employees where id = ?";
//		int id = 0;
//		String newSql = "select id from employees where email = ?";
//		try(PreparedStatement nStmt = conn.prepareStatement(newSql);
//			ResultSet rs = nStmt.executeQuery())
//		{
//			nStmt.setString(1, emp.getEmail());
//			while(rs.next()) {
//				id = rs.getInt("id");
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		try(PreparedStatement stmt = conn.prepareStatement(sql))
//		{
//			
//			stmt.setInt(1, emp.getId());
//			int row = stmt.executeUpdate();
//			
//			if(row>0) {
//				// insert into audit history
//				String insertSql = "insert into audit_history(user_id, employee_id, action, action_date_time) values(?,?,?,?)";
//				try(PreparedStatement iStmt = conn.prepareStatement(insertSql))
//				{
//					iStmt.setInt(1, userId);
//					iStmt.setInt(2, id);
//					iStmt.setString(3, "Employee deleted!");
//					iStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		try{
			EmployeeDao dao = new EmployeeDao();
			System.out.println(dao.getUsers());
			System.out.println(dao.getEmployees());
			
//			Employee emp = new Employee(17, "Stoenescu", "Dragos", "stoe@yahoo.com", "Security", BigDecimal.valueOf(70000.00));
//			dao.setUnemployed(emp, 3);
			System.out.println(dao.getAuditHistory(17));
		}catch(Exception e) {
				e.printStackTrace();
		}
	}
}
