package jdbc.petru.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import jdbc.petru.dao.EmployeeDao;
import jdbc.petru.model.Employee;
import jdbc.petru.model.User;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class EmployeeView extends JFrame {

	
	private JPanel contentPane;
	private JTextField textFieldLastName;
	private EmployeeDao employeeDao;
	private JTable table;
	private int userId;
	private User user;

	public EmployeeView(EmployeeDao employeeDao, User user, int userId) {
		setTitle("User: "+user.getFirstName());
		this.employeeDao = employeeDao;
		this.user = user;
		this.userId = userId;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "2, 2, 13, 1, fill, fill");
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener((ActionEvent e)->{
			
			
			try{
				String lastName = textFieldLastName.getText();
				
				
				List<Employee> empList = null;
				
				
				if(lastName!=null && lastName.trim().length()>0) {
					empList = employeeDao.searchEmployees(lastName);
					
				}else {
					empList = employeeDao.getEmployees();
					
				}
				
				EmployeeTableModel empModel = new EmployeeTableModel(empList);
				table.setModel(empModel);
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		panel_1.add(btnSearch);
		
		textFieldLastName = new JTextField();
		panel_1.add(textFieldLastName);
		textFieldLastName.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "2, 4, 11, 13, fill, fill");
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnAddEmployee = new JButton("Add Employee");
		btnAddEmployee.addActionListener((ActionEvent e)->{
				EmployeeAddDialog empAdd = new EmployeeAddDialog(this, employeeDao, userId, "Add Employee");
				empAdd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				empAdd.setVisible(true);
		});
		contentPane.add(btnAddEmployee, "2, 18");
		
		JButton btnUpdateEmployee = new JButton("Update Employee");
		btnUpdateEmployee.addActionListener((ActionEvent e)->{
			int row = table.getSelectedRow();
			System.out.println(row);
			if(row<0) {
				JOptionPane.showMessageDialog(this, "You must select an employee!!!","Select employee!",JOptionPane.ERROR_MESSAGE);
			}else {
				Employee emp = (Employee) table.getValueAt(row, EmployeeTableModel.EMPLOYEE);
				System.out.println(emp.getFirstName()+" "+emp.getSalary());
				System.out.println(emp.getId());
				EmployeeDialog empDialog = new EmployeeDialog(this,employeeDao, emp, user.getId(),"Update Employee");
				empDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				empDialog.setVisible(true);
				
			}
		});
		contentPane.add(btnUpdateEmployee, "4, 18");
		
		JButton btnFireEmployee = new JButton("Fire Employee");
		btnFireEmployee.addActionListener((ActionEvent e)->{
			int row = table.getSelectedRow();
			System.out.println(row);
			if(row<0) {
				JOptionPane.showMessageDialog(this, "You must select an employee!!!","Select employee!",JOptionPane.ERROR_MESSAGE);
			}else {
				Employee emp = (Employee)table.getValueAt(row, EmployeeTableModel.EMPLOYEE);
				System.out.println(emp.getFirstName()+" "+emp.getLastName());
				boolean update = employeeDao.setUnemployed(emp, userId);
				if(update) JOptionPane.showMessageDialog(this, "Employee Fired!","Fired!!!",JOptionPane.INFORMATION_MESSAGE);
				else JOptionPane.showMessageDialog(this, "OOOps employee still working!","Error!", JOptionPane.ERROR_MESSAGE);
			}
		});
		contentPane.add(btnFireEmployee, "6, 18");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "2, 20, 13, 1, fill, fill");
	}
	
	public void refreshEmployeeView() {

		try {
			List<Employee> employees = employeeDao.getEmployees();

			// create the model and update the "table"
			EmployeeTableModel model = new EmployeeTableModel(employees);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
