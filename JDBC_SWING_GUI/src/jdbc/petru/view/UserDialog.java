package jdbc.petru.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import jdbc.petru.dao.EmployeeDao;
import jdbc.petru.model.User;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private JComboBox comboBox;
	private EmployeeDao employeeDao;
	private List<User> users;
	
	public static void main(String[] args) {
		try {
			EmployeeDao empDao = new EmployeeDao();
			List<User> users = empDao.getUsers();
			UserDialog dialog = new UserDialog();
			dialog.populateUsers(users);
			dialog.setEmployeeDao(empDao);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void setEmployeeDao(EmployeeDao dao) {
		this.employeeDao = dao;
	}
	
	
	public void populateUsers(List<User> users) {
		this.users = users;
		comboBox.setModel(new DefaultComboBoxModel(users.toArray(new User[0])));
	}
	
	public UserDialog() {
		
		setBounds(100, 100, 450, 300);
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblUser = new JLabel("User:");
			contentPanel.add(lblUser, "2, 4");
		}
		{
			comboBox  = new JComboBox();
			contentPanel.add(comboBox, "8, 4, fill, default");
			try{
				//populateUsers(users);
				
			}catch(Exception e) {
				e.printStackTrace();
				}
		}
		{
			JLabel lblPassword = new JLabel("Password:");
			contentPanel.add(lblPassword, "2, 10");
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, "8, 10, fill, default");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener((ActionEvent e)->{
					User user = (User)comboBox.getSelectedItem();
					
					System.out.println(user.getPassword()+" "+user.getFirstName());
					System.out.println(passwordField.getText());
					if (user.getPassword().equals(passwordField.getText())) {
						JOptionPane.showMessageDialog(this, "Login Successfull!!!","Logged in!",JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						dispose();
						EmployeeView empView = new EmployeeView(employeeDao, user, user.getId());
						empView.setVisible(true);
						
					}else JOptionPane.showMessageDialog(this, "Incorrect password!","Error!",JOptionPane.ERROR_MESSAGE);
					
					
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
