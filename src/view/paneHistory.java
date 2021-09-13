package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboPopup;

import bo.dailyCostBo;

public class paneHistory extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JComboBox<String> cmbMonth, cmbYear;
	private dailyCostBo dcBo = new dailyCostBo();
	
	public void setTableHistory() {
		int month = Integer.parseInt(cmbMonth.getItemAt(cmbMonth.getSelectedIndex()).toString());
		int year = Integer.parseInt(cmbYear.getItemAt(cmbYear.getSelectedIndex()).toString());
		table.setModel(dcBo.get_TableModelHistory(month, year));
	}
	
	public paneHistory() {
		setOpaque(false);
		setBorder(new LineBorder(Color.WHITE, 1, true));
		setBounds(171, 113, 580, 384);
		setLayout(null);
		
		cmbMonth = new JComboBox<String>();
		cmbMonth.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1)
					setTableHistory();
			}
		});
		cmbMonth.setModel(dcBo.get_cmbMonthModel());
		cmbMonth.setOpaque(false);
		cmbMonth.setForeground(new Color(178, 34, 34));
		cmbMonth.setFont(new Font("Tahoma", Font.BOLD, 16));
		cmbMonth.setBounds(184, 22, 57, 32);
		add(cmbMonth);
		
		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setForeground(Color.YELLOW);
		lblMonth.setFont(new Font("MV Boli", Font.BOLD, 16));
		lblMonth.setBounds(121, 22, 65, 32);
		add(lblMonth);
		
		JLabel lblYear = new JLabel("Year:");
		lblYear.setForeground(Color.YELLOW);
		lblYear.setFont(new Font("MV Boli", Font.BOLD, 16));
		lblYear.setBounds(311, 22, 45, 32);
		add(lblYear);
		
		cmbYear = new JComboBox<String>();
		cmbYear.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1) {
					setTableHistory();
				}
			}
		});
		cmbYear.setModel(dcBo.get_cmbYearModel());
		cmbYear.setOpaque(false);
		cmbYear.setForeground(new Color(178, 34, 34));
		cmbYear.setFont(new Font("Tahoma", Font.BOLD, 16));
		cmbYear.setBounds(359, 22, 76, 32);
		add(cmbYear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setViewportBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		scrollPane.setBorder(new LineBorder(Color.WHITE, 1, true));
		scrollPane.setBounds(10, 74, 560, 299);
		add(scrollPane);
		
		table = new JTable();
		table.setRequestFocusEnabled(false);
		table.setRowHeight(30);
		table.setOpaque(false);
		table.setForeground(new Color(178, 34, 34));
		table.setFont(new Font("Tahoma", Font.BOLD, 13));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		cmbYear.setSelectedIndex(0);
		cmbMonth.setSelectedIndex(dcBo.getCurMonth()-1);
		setTableHistory();
	}
}
