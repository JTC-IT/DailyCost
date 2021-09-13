package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import bo.dailyCostBo;

public class paneStatistical extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField textDay1;
	private JTextField textDay2;
	private JLabel txtTongTien,txtSoNguoi,txtTienNguoi;
	private JTable tablePayer;
	private dailyCostBo dcBo = new dailyCostBo();
	private JTextField textMonth1;
	private JTextField textYear1;
	private JTextField textMonth2;
	private JTextField textYear2;
	private JLabel lblD1, lblD2, lblM1, lblM2, lblY1, lblY2;
	private JLabel lblBoderDate1, lblBoderDate2, lblFrom, lblTo;
	private JLabel lblNewLabel;
	private JLabel lblSNgi;
	private JLabel lblSTinMi;
	
	private int type = 1;
	
	public void setTablePayer() {
		String day = textDay1.getText().trim();
		String month = textMonth1.getText().trim();
		String year = textYear1.getText().trim();
		int result = dcBo.checkDate(day, month, year);
		if(result != 1) {
			if(result == -3)
				textYear1.requestFocus();
			else if(result == -2)
				textMonth1.requestFocus();
			else textDay1.requestFocus();
			JOptionPane.showMessageDialog(null, "Invalid date!", "ERROR!"
					, JOptionPane.OK_OPTION
					, new ImageIcon(paneStatistical.class.getResource("/imgs/cancel-48.png")));
			return;
		}
		String date1 = day+"/"+month+"/"+year;
		
		day = textDay2.getText().trim();
		month = textMonth2.getText().trim();
		year = textYear2.getText().trim();
		result = dcBo.checkDate(day, month, year);
		if(result != 1) {
			if(result == -3)
				textYear2.requestFocus();
			else if(result == -2)
				textMonth2.requestFocus();
			else textDay2.requestFocus();
			JOptionPane.showMessageDialog(null, "Invalid date!", "ERROR!"
					, JOptionPane.OK_OPTION
					, new ImageIcon(paneStatistical.class.getResource("/imgs/cancel-48.png")));
			return;
		}
		String date2 = day+"/"+month+"/"+year;
		int Sum = dcBo.getSumCostByDate(date1, date2);
		txtTongTien.setText(dcBo.formatmoney(Sum));
		txtSoNguoi.setText(dcBo.getSizeMember()+"");
		txtTienNguoi.setText(dcBo.formatmoney((double)Sum/dcBo.getSizeMember()));
		tablePayer.setModel(dcBo.geTableModelPayer(date1, date2));
	}
	
	public void setBorderDate(Color colorb, Color colorf) {
		if(type == 1) {
			lblFrom.setBorder(new MatteBorder(2, 2, 2, 0, colorb));
			lblBoderDate1.setBorder(new MatteBorder(2, 0, 2, 2, colorb));
			lblBoderDate1.setForeground(colorf);
		}else {
			lblTo.setBorder(new MatteBorder(2, 2, 2, 0, colorb));
			lblBoderDate2.setBorder(new MatteBorder(2, 0, 2, 2, colorb));
			lblBoderDate2.setForeground(colorf);
		}
	}
	
	private FocusListener focusAdapter1 = new FocusAdapter() {
		public void focusGained(FocusEvent e) {
			type = 1;
			setBorderDate(Color.green, Color.white);
	    }
	    public void focusLost(FocusEvent e) {
	    	type = 1;
	    	setBorderDate(Color.white, Color.yellow);
	    }
	};
		
	
	private FocusListener focusAdapter2 = new FocusAdapter(){
		public void focusGained(FocusEvent e) {
			type = 2;
			setBorderDate(Color.green, Color.white);
	    }
	    public void focusLost(FocusEvent e) {
	    	type = 2;
	    	setBorderDate(Color.white, Color.yellow);
	    }
	};
	
	public paneStatistical() {
		setOpaque(false);
		setBorder(new LineBorder(Color.WHITE, 1, true));
		setBounds(171, 113, 580, 384);
		setLayout(null);
		
		textDay1 = new JTextField("1");
		textDay1.addFocusListener(focusAdapter1);
		textDay1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					setTablePayer();
			}
			public void keyReleased(KeyEvent e) {
				if(textDay1.getText().isEmpty())
					lblD1.setVisible(true);
				else lblD1.setVisible(false);
			}
		});
		textDay1.setHorizontalAlignment(SwingConstants.CENTER);
		textDay1.setOpaque(false);
		textDay1.setBorder(null);
		textDay1.setForeground(Color.YELLOW);
		textDay1.setCaretColor(Color.WHITE);
		textDay1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textDay1.setColumns(10);
		textDay1.setBackground(Color.WHITE);
		textDay1.setBounds(77, 54, 50, 30);
		add(textDay1);
		
		textDay2 = new JTextField(""+dcBo.getCurDay());
		textDay2.addFocusListener(focusAdapter2);
		textDay2.setHorizontalAlignment(SwingConstants.CENTER);
		textDay2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					setTablePayer();
			}
			public void keyReleased(KeyEvent e) {
				if(textDay2.getText().isEmpty())
					lblD2.setVisible(true);
				else lblD2.setVisible(false);
			}
		});
		textDay2.setOpaque(false);
		textDay2.setForeground(Color.YELLOW);
		textDay2.setCaretColor(Color.WHITE);
		textDay2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textDay2.setColumns(10);
		textDay2.setBorder(null);
		textDay2.setBackground(Color.WHITE);
		textDay2.setBounds(360, 54, 60, 30);
		add(textDay2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		scrollPane.setBounds(10, 184, 560, 189);
		add(scrollPane);
		
		tablePayer = new JTable();
		tablePayer.setRowHeight(30);
		scrollPane.setViewportView(tablePayer);
		tablePayer.setForeground(new Color(178, 34, 34));
		tablePayer.setFont(new Font("Tahoma", Font.BOLD, 14));
		tablePayer.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textMonth1 = new JTextField(""+dcBo.getCurMonth());
		textMonth1.addFocusListener(focusAdapter1);
		textMonth1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					setTablePayer();
			}
			public void keyReleased(KeyEvent e) {
				if(textMonth1.getText().isEmpty())
					lblM1.setVisible(true);
				else lblM1.setVisible(false);
			}
		});
		textMonth1.setOpaque(false);
		textMonth1.setHorizontalAlignment(SwingConstants.CENTER);
		textMonth1.setForeground(Color.YELLOW);
		textMonth1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textMonth1.setColumns(10);
		textMonth1.setCaretColor(Color.WHITE);
		textMonth1.setBorder(null);
		textMonth1.setBackground(Color.WHITE);
		textMonth1.setBounds(137, 54, 54, 30);
		add(textMonth1);
		
		textYear1 = new JTextField(""+dcBo.getCurYear());
		textYear1.addFocusListener(focusAdapter1);
		textYear1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					setTablePayer();
			}
			public void keyReleased(KeyEvent e) {
				if(textYear1.getText().isEmpty())
					lblY1.setVisible(true);
				else lblY1.setVisible(false);
			}
		});
		textYear1.setOpaque(false);
		textYear1.setHorizontalAlignment(SwingConstants.CENTER);
		textYear1.setForeground(Color.YELLOW);
		textYear1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textYear1.setColumns(10);
		textYear1.setCaretColor(Color.WHITE);
		textYear1.setBorder(null);
		textYear1.setBackground(Color.WHITE);
		textYear1.setBounds(202, 54, 60, 30);
		add(textYear1);
		
		textMonth2 = new JTextField(""+dcBo.getCurMonth());
		textMonth2.addFocusListener(focusAdapter2);
		textMonth2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					setTablePayer();
			}
			public void keyReleased(KeyEvent e) {
				if(textMonth2.getText().isEmpty())
					lblM2.setVisible(true);
				else lblM2.setVisible(false);
			}
		});
		textMonth2.setOpaque(false);
		textMonth2.setHorizontalAlignment(SwingConstants.CENTER);
		textMonth2.setForeground(Color.YELLOW);
		textMonth2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textMonth2.setColumns(10);
		textMonth2.setCaretColor(Color.WHITE);
		textMonth2.setBorder(null);
		textMonth2.setBackground(Color.WHITE);
		textMonth2.setBounds(430, 54, 54, 30);
		add(textMonth2);
		
		textYear2 = new JTextField(""+dcBo.getCurYear());
		textYear2.addFocusListener(focusAdapter2);
		textYear2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					setTablePayer();
			}
			public void keyReleased(KeyEvent e) {
				if(textYear2.getText().isEmpty())
					lblY2.setVisible(true);
				else lblY2.setVisible(false);
			}
		});
		textYear2.setOpaque(false);
		textYear2.setHorizontalAlignment(SwingConstants.CENTER);
		textYear2.setForeground(Color.YELLOW);
		textYear2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textYear2.setColumns(10);
		textYear2.setCaretColor(Color.WHITE);
		textYear2.setBorder(null);
		textYear2.setBackground(Color.WHITE);
		textYear2.setBounds(494, 54, 61, 30);
		add(textYear2);
		
		lblD2 = new JLabel("dd");
		lblD2.setHorizontalAlignment(SwingConstants.CENTER);
		lblD2.setForeground(Color.LIGHT_GRAY);
		lblD2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblD2.setBounds(360, 59, 60, 25);
		lblD2.setVisible(false);
		add(lblD2);
		
		lblY1 = new JLabel("yyyy");
		lblY1.setHorizontalAlignment(SwingConstants.CENTER);
		lblY1.setForeground(Color.LIGHT_GRAY);
		lblY1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblY1.setBounds(193, 59, 69, 25);
		lblY1.setVisible(false);
		add(lblY1);
		
		lblBoderDate2 = new JLabel("/        /");
		lblBoderDate2.setHorizontalAlignment(SwingConstants.CENTER);
		lblBoderDate2.setForeground(Color.YELLOW);
		lblBoderDate2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBoderDate2.setBorder(new MatteBorder(2, 0, 2, 2, (Color) new Color(255, 255, 255)));
		lblBoderDate2.setBounds(360, 54, 195, 30);
		add(lblBoderDate2);
		
		lblM1 = new JLabel("mm");
		lblM1.setHorizontalAlignment(SwingConstants.CENTER);
		lblM1.setForeground(Color.LIGHT_GRAY);
		lblM1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblM1.setBounds(137, 60, 54, 25);
		lblM1.setVisible(false);
		add(lblM1);
		
		lblM2 = new JLabel("mm");
		lblM2.setHorizontalAlignment(SwingConstants.CENTER);
		lblM2.setForeground(Color.LIGHT_GRAY);
		lblM2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblM2.setBounds(424, 59, 60, 25);
		lblM2.setVisible(false);
		add(lblM2);
		
		lblY2 = new JLabel("yyyy");
		lblY2.setHorizontalAlignment(SwingConstants.CENTER);
		lblY2.setForeground(Color.LIGHT_GRAY);
		lblY2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblY2.setBounds(486, 59, 69, 25);
		lblY2.setVisible(false);
		add(lblY2);
		
		lblBoderDate1 = new JLabel("/        /");
		lblBoderDate1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBoderDate1.setForeground(Color.YELLOW);
		lblBoderDate1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBoderDate1.setBorder(new MatteBorder(2, 0, 2, 2, (Color) new Color(255, 255, 255)));
		lblBoderDate1.setBounds(67, 54, 195, 30);
		add(lblBoderDate1);
		
		lblD1 = new JLabel("dd");
		lblD1.setForeground(new Color(192, 192, 192));
		lblD1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblD1.setHorizontalAlignment(SwingConstants.CENTER);
		lblD1.setBounds(72, 59, 60, 25);
		lblD1.setVisible(false);
		add(lblD1);
		
		lblTo = new JLabel("To:");
		lblTo.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 255, 255)));
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setForeground(Color.WHITE);
		lblTo.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTo.setBounds(317, 54, 47, 30);
		add(lblTo);
		
		JLabel lblNewLabel_4 = new JLabel("Thống kê chi tiêu");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setForeground(new Color(248, 248, 255));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_4.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(240, 255, 255)));
		lblNewLabel_4.setBounds(203, 11, 174, 25);
		add(lblNewLabel_4);
		
		lblFrom = new JLabel("From:");
		lblFrom.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrom.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 255, 255)));
		lblFrom.setForeground(Color.WHITE);
		lblFrom.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFrom.setBounds(21, 54, 54, 30);
		add(lblFrom);
		
		txtTongTien = new JLabel("0 VNĐ");
		txtTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTongTien.setOpaque(false);
		txtTongTien.setForeground(Color.YELLOW);
		txtTongTien.setFont(new Font("Segoe UI", Font.BOLD, 15));
		txtTongTien.setBorder(null);
		txtTongTien.setBounds(234, 100, 195, 25);
		add(txtTongTien);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBorder(new LineBorder(Color.RED, 2));
		lblNewLabel.setBounds(145, 95, 289, 82);
		add(lblNewLabel);
		
		lblSNgi = new JLabel("Số người:");
		lblSNgi.setHorizontalAlignment(SwingConstants.LEFT);
		lblSNgi.setForeground(Color.WHITE);
		lblSNgi.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblSNgi.setBorder(null);
		lblSNgi.setBounds(149, 124, 142, 25);
		add(lblSNgi);
		
		lblSTinMi = new JLabel("Số tiền mỗi người:");
		lblSTinMi.setHorizontalAlignment(SwingConstants.LEFT);
		lblSTinMi.setForeground(Color.WHITE);
		lblSTinMi.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblSTinMi.setBorder(null);
		lblSTinMi.setBounds(149, 148, 142, 25);
		add(lblSTinMi);
		
		JLabel lblTong = new JLabel("Tổng tiền:");
		lblTong.setBorder(null);
		lblTong.setHorizontalAlignment(SwingConstants.LEFT);
		lblTong.setForeground(Color.WHITE);
		lblTong.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTong.setBounds(149, 100, 142, 25);
		add(lblTong);
		
		txtSoNguoi = new JLabel("0");
		txtSoNguoi.setOpaque(false);
		txtSoNguoi.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSoNguoi.setForeground(Color.YELLOW);
		txtSoNguoi.setFont(new Font("Segoe UI", Font.BOLD, 15));
		txtSoNguoi.setBorder(null);
		txtSoNguoi.setBounds(325, 124, 104, 25);
		add(txtSoNguoi);
		
		txtTienNguoi = new JLabel("0 VNĐ");
		txtTienNguoi.setOpaque(false);
		txtTienNguoi.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTienNguoi.setForeground(Color.YELLOW);
		txtTienNguoi.setFont(new Font("Segoe UI", Font.BOLD, 15));
		txtTienNguoi.setBorder(null);
		txtTienNguoi.setBounds(287, 148, 142, 25);
		add(txtTienNguoi);
	}
}
