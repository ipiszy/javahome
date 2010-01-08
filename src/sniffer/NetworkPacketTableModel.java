/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sniffer;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ipiszy 
 */
public class NetworkPacketTableModel extends AbstractTableModel {

    private String[] tableHeader = new String[]{
        "Time", "Src IP", "Dest IP", "Protocol"
    };
    private Vector<Vector<Object>> tableContent = new Vector<Vector<Object>>();
    private Vector<Vector<Object>> oldTableContent;

    public NetworkPacketTableModel() {
        /*
        for (int i = 0; i < 4; ++i) {
        Vector<Object> v = new Vector();
        for (int j = 0; j < 4; ++j) {
        v.add(null);
        }
        tableContent.add(v);

        }
         */
    }

    public boolean addRow(Vector<Object> row) {
        if (row.size() != getColumnCount()) {
            return false;
        }
        tableContent.add(row);
        return true;
    }

    public void clear() {
        oldTableContent = tableContent;
        tableContent.clear();
    }

    public int getRowCount() {
        return tableContent.size();
    }

    public int getColumnCount() {
        return tableHeader.length;
    }

    @Override
    public String getColumnName(int index) {
        return this.tableHeader[index];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > tableContent.size()) {
            return "";
        }
        Object o;
        if ((o = tableContent.get(rowIndex).get(columnIndex)) == null) {
            return "";
        }
        return o;
    }
}
