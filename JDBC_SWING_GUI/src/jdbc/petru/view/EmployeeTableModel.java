package jdbc.petru.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import jdbc.petru.model.Employee;;
public class EmployeeTableModel extends AbstractTableModel {

	private static final int LAST_NAME_COLUMN = 0;
	private static final int FIRST_NAME_COLUMN = 1;
	private static final int EMAIL_COLUMN = 2;
	private static final int DEPARTMENT_COLUMN = 3;
	private static final int SALARY_COLUMN = 4;
	protected static final int EMPLOYEE = -1;
	
	private final String[] columns = {"Last Name", "First Name","Email","Department","Salary"};
	private List<Employee> employees;
	
	public EmployeeTableModel(List<Employee> employees) {
		this.employees = employees;
	}
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return employees.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columns[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {

		Employee tempEmployee = employees.get(row);

		switch (col) {
		case LAST_NAME_COLUMN:
			return tempEmployee.getLastName();
		case FIRST_NAME_COLUMN:
			return tempEmployee.getFirstName();
		case EMAIL_COLUMN:
			return tempEmployee.getEmail();
		case DEPARTMENT_COLUMN:
			return tempEmployee.getDepartment();
		case SALARY_COLUMN:
			return tempEmployee.getSalary();
		case EMPLOYEE:
			return tempEmployee;
		default:
			return tempEmployee.getLastName();
		}
	}

	
	

}
