package jdbc.petru.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jdbc.petru.dao.EmployeeDao;
import jdbc.petru.model.Employee;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

public class EmployeeAddDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private String title;
	private EmployeeDao empDao;
	private boolean open;
	private int userId;
	private EmployeeView empView;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldEmail;
	private JTextField textFieldDepartment;
	private JTextField textFieldSalary;
	
	public EmployeeAddDialog(EmployeeView empView, EmployeeDao empDao, int userId, String title) {
		this();
		this.empView = empView;
		this.empDao = empDao;
		this.userId = userId;
		this.title = title;
		setTitle(title);
		
	}
	
	public EmployeeAddDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("434px"),},
			new RowSpec[] {
				RowSpec.decode("228px"),
				RowSpec.decode("33px"),}));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, "1, 1, fill, fill");
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
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
			JLabel lblFirstName = new JLabel("First Name");
			contentPanel.add(lblFirstName, "2, 2, default, fill");
		}
		{
			textFieldFirstName = new JTextField();
			contentPanel.add(textFieldFirstName, "8, 2, fill, default");
			textFieldFirstName.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Last Name");
			contentPanel.add(lblNewLabel, "2, 6");
		}
		{
			textFieldLastName = new JTextField();
			contentPanel.add(textFieldLastName, "8, 6, fill, default");
			textFieldLastName.setColumns(10);
		}
		{
			JLabel lblEmail = new JLabel("Email");
			contentPanel.add(lblEmail, "2, 10");
		}
		{
			textFieldEmail = new JTextField();
			contentPanel.add(textFieldEmail, "8, 10, fill, default");
			textFieldEmail.setColumns(10);
		}
		{
			JLabel lblDepartment = new JLabel("Department");
			contentPanel.add(lblDepartment, "2, 14");
		}
		{
			textFieldDepartment = new JTextField();
			contentPanel.add(textFieldDepartment, "8, 14, fill, default");
			textFieldDepartment.setColumns(10);
		}
		{
			JLabel lblSalary = new JLabel("Salary");
			contentPanel.add(lblSalary, "2, 16");
		}
		{
			textFieldSalary = new JTextField();
			contentPanel.add(textFieldSalary, "8, 16, fill, default");
			textFieldSalary.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, "1, 2, fill, top");
			{
				JButton addButton = new JButton("Add");
				addButton.addActionListener((ActionEvent e)->{
					String firstName = textFieldFirstName.getText();
					String lastName = textFieldLastName.getText();
					String email = textFieldEmail.getText();
					String department = textFieldDepartment.getText();
					BigDecimal salary = BigDecimal.valueOf(Double.valueOf(textFieldSalary.getText()));
					if(textFieldFirstName.getText()==null || textFieldLastName.getText()==null) {
						JOptionPane.showMessageDialog(this, "Please complete all fields!","Complete fields!",JOptionPane.ERROR_MESSAGE);
					}else {
						Employee emp = new Employee(lastName,firstName, email, department, salary);
						boolean add = empDao.addEmployee(emp, userId);
						if(add) {
							JOptionPane.showMessageDialog(this, "Employee added!", "Employee added!",JOptionPane.INFORMATION_MESSAGE);
							empView.refreshEmployeeView();
							setVisible(false);
							dispose();
						}else {
							JOptionPane.showMessageDialog(this, "Error, didn't add emplyee!!!","Error!",JOptionPane.ERROR_MESSAGE);
						}
					}
					
				});
				addButton.setActionCommand("OK");
				buttonPane.add(addButton);
				getRootPane().setDefaultButton(addButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
