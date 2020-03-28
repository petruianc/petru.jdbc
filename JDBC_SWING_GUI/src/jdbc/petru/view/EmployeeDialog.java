package jdbc.petru.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import jdbc.petru.dao.EmployeeDao;
import jdbc.petru.model.Employee;
import jdbc.petru.model.User;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

public class EmployeeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldLastName;
	private JTextField textFieldFirstName;
	private JTextField textFieldEmail;
	private JTextField textFieldDepartment;
	private JTextField textFieldSalary;
	private String title;
	private EmployeeDao empDao;
	private boolean open;
	private int userId;
	private Employee emp;
	private EmployeeView empView;
	
	public void populateGUI(Employee emp){
		textFieldLastName.setText(emp.getLastName());
		textFieldFirstName.setText(emp.getFirstName());
		textFieldEmail.setText(emp.getEmail());
		textFieldDepartment.setText(emp.getDepartment());
		textFieldSalary.setText(emp.getSalary().toString());
	}
	
	public EmployeeDialog(EmployeeView empView, EmployeeDao empDao, Employee emp, int userId, String title) {
		this();
		this.empView = empView;
		this.empDao = empDao;
		this.emp = emp;
		this.userId = userId;
		this.title = title;
		populateGUI(emp);
		setTitle(title);
	}
	
	public EmployeeDialog() {
		
		setBounds(100, 100, 473, 315);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
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
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblLastName = new JLabel("Last Name");
			contentPanel.add(lblLastName, "4, 2");
		}
		{
			textFieldLastName = new JTextField();
			contentPanel.add(textFieldLastName, "10, 2, fill, default");
			textFieldLastName.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("First Name");
			contentPanel.add(lblNewLabel_1, "4, 6");
		}
		{
			textFieldFirstName = new JTextField();
			contentPanel.add(textFieldFirstName, "10, 6, fill, default");
			textFieldFirstName.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Email");
			contentPanel.add(lblNewLabel_2, "4, 10");
		}
		{
			textFieldEmail = new JTextField();
			contentPanel.add(textFieldEmail, "10, 10, fill, default");
			textFieldEmail.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Department");
			contentPanel.add(lblNewLabel_3, "4, 14");
		}
		{
			textFieldDepartment = new JTextField();
			contentPanel.add(textFieldDepartment, "10, 14, fill, default");
			textFieldDepartment.setColumns(10);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Salary");
			contentPanel.add(lblNewLabel_4, "4, 18");
		}
		{
			textFieldSalary = new JTextField();
			contentPanel.add(textFieldSalary, "10, 18, fill, default");
			textFieldSalary.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener((ActionEvent e)->{
					String lastName = textFieldLastName.getText();
					String firstName = textFieldFirstName.getText();
					String email = textFieldEmail.getText();
					String department = textFieldDepartment.getText();
					BigDecimal salary = BigDecimal.valueOf(Double.parseDouble(textFieldSalary.getText()));
					System.out.println(emp.getId());
					Employee employeeUpdate = new Employee(emp.getId(),lastName, firstName, email, department, salary);
					System.out.println(employeeUpdate);
					System.out.println(userId);
					boolean update = empDao.updateEmployee(employeeUpdate, userId);
					if(update) {
						empView.refreshEmployeeView();
						JOptionPane.showMessageDialog(this, "Updated successfully!","Updated!",JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						dispose();
					}else {
						JOptionPane.showMessageDialog(this, "Employee not updated!","Error!",JOptionPane.ERROR_MESSAGE);
					}
					
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener((ActionEvent e)->{
					setVisible(false);
					dispose();
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
