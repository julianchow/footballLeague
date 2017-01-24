import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	JTable jt;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TableModel(Object[][] tableData, String[] tableColumnNames) {
			jt = new JTable(tableData, tableColumnNames);
	}
	
	
	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
