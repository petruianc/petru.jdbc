package jdbc.petru.model;

import java.util.Date;

public class AuditHistory {
	private int userId;
	private int employeeID;
	private String action;
	private Date actionDate;
	
	private String userLastName;
	private String userFirstName;
	
	public AuditHistory(int userId, int employeeID, String action, Date actionDate, String userLastName, String userFirstName) {
		super();
		this.userId = userId;
		this.employeeID = employeeID;
		this.action = action;
		this.actionDate = actionDate;
		this.userLastName = userLastName;
		this.userFirstName = userFirstName;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	@Override
	public String toString() {
		return "AuditHistory [userId=" + userId + ", employeeID=" + employeeID + ", action=" + action + ", actionDate="
				+ actionDate + ", userLastName=" + userLastName + ", userFirstName=" + userFirstName + "]";
	}
	
	
	
}
