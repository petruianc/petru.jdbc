package jdbc.petru.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import jdbc.petru.model.AuditHistory;
import jdbc.petru.model.Employee;

public class AuditTableModel extends AbstractTableModel{
	private static final int USERID_COLUMN = 0;
	private static final int FIRSTNAME_COLUMN = 1;
	private static final int LASTNAME_COLUMN = 2;
	private static final int EMPLOYEEID_COLUMN = 3;
	private static final int ACTION_COLUMN = 4;
	private static final int DATE_COLUMN = 5;
	protected static final int AUDIT = -1;
	
	private final String[] columnNames = {"User ID", "First Name", "Last Name", "Employee ID", "Action", "Action Date"};
	private List<AuditHistory> histories;
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		return histories.size();
	}
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	@Override
	public Object getValueAt(int row, int column) {
		AuditHistory tempAudit = histories.get(row);

		switch (column) {
		case USERID_COLUMN:
			return tempAudit.getUserId();
		case FIRSTNAME_COLUMN:
			return tempAudit.getUserFirstName();
		case LASTNAME_COLUMN:
			return tempAudit.getUserLastName();
		case EMPLOYEEID_COLUMN:
			return tempAudit.getEmployeeID();
		case ACTION_COLUMN:
			return tempAudit.getAction();
		case DATE_COLUMN:
			return tempAudit.getActionDate();
		case AUDIT:
			return tempAudit;
		default:
			return tempAudit.getUserLastName();
		}
	
	}
}
