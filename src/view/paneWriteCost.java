package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import bo.dailyCostBo;
import javax.swing.ImageIcon;

public class paneWriteCost extends JPanel {
	private static final long serialVersionUID = 1L;
	private JComboBox<String> cmbPayer;
	private JTextField textDay;
	private JTextField textCost;
	private JButton btnAddCost;
	private dailyCostBo dcBo = new dailyCostBo();
	private JLabel lblDay, lblMonth, lblYear, lblBoderDate,lblmoney;
	private JLabel lblPayToday;
	private JTextField textMonth;
	private JTextField textYear;
	
	private void setbtnAddCost(Color color) {
		btnAddCost.setForeground(color);
		btnAddCost.setBorder(new LineBorder((Color) color, 2, true));
	}
	
	public void writeCost() {
		String year = textYear.getText().trim();
		String month = textMonth.getText().trim();
		String day = textDay.getText().trim();
		
		switch (dcBo.checkDate(day, month, year)) {
		case -3:
			textYear.requestFocus();
			JOptionPane.showMessageDialog(null, "Invalid year!", "ERROR!"
					, JOptionPane.OK_OPTION
					, new ImageIcon(paneWriteCost.class.getResource("/imgs/cancel-48.png")));
			return;
			
		case -2:
			textMonth.requestFocus();
			JOptionPane.showMessageDialog(null, "Invalid month!", "ERROR!"
					, JOptionPane.OK_OPTION
					, new ImageIcon(paneWriteCost.class.getResource("/imgs/cancel-48.png")));
			return;

		case -1:
			textDay.requestFocus();
			JOptionPane.showMessageDialog(null, "Invalid day!", "ERROR!"
					, JOptionPane.OK_OPTION
					, new ImageIcon(paneWriteCost.class.getResource("/imgs/cancel-48.png")));
			return;
		default:
			break;
		}
		
		String money = textCost.getText().trim();
		if(money.isEmpty()) {
			textCost.requestFocus();
			JOptionPane.showMessageDialog(null, "Cost is not empty!", "ERROR!"
					, JOptionPane.OK_OPTION
					, new ImageIcon(paneWriteCost.class.getResource("/imgs/cancel-48.png")));
			return;
		}
		int cost;
		try {
			cost = Integer.parseInt(money);
			if(cost < 1) {
				JOptionPane.showMessageDialog(null, "Cost > 0!", "ERROR!"
						, JOptionPane.OK_OPTION
						, new ImageIcon(paneWriteCost.class.getResource("/imgs/cancel-48.png")));
				return;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Cost is not number!", "ERROR!"
					, JOptionPane.OK_OPTION
					, new ImageIcon(paneWriteCost.class.getResource("/imgs/cancel-48.png")));
			return;
		}
		if(dcBo.InsertDailyCost(day+"/"+month+"/"+year, cost*1000, cmbPayer.getSelectedIndex())) {
			JOptionPane.showMessageDialog(null, "Add successful!", "CORRECT!"
					, JOptionPane.OK_OPTION
					, new ImageIcon(paneWriteCost.class.getResource("/imgs/checkbox-48.png")));
			textCost.setText("");
			textCost.requestFocus();
			lblmoney.setVisible(true);
			DailyCostFrame.isRefesh = 2;
			lblPayToday.setVisible(!dcBo.checkPayCurDate());
		}else {
			JOptionPane.showMessageDialog(null, "<html>Add unsaccessful !<br>Date or Cost isn't correct?</html>"
					, "ERROR!"
					, JOptionPane.OK_OPTION
					, new ImageIcon(paneWriteCost.class.getResource("/imgs/cancel-48.png")));
			textDay.requestFocus();
		}
	}
	
	private FocusListener focusDate = new FocusAdapter() {
		public void focusGained(FocusEvent e) {
			lblBoderDate.setForeground(Color.white);
			lblBoderDate.setBorder(new MatteBorder(0, 0, 2, 0, Color.green));
		}
		public void focusLost(FocusEvent e) {
			lblBoderDate.setForeground(Color.yellow);
			lblBoderDate.setBorder(new MatteBorder(0, 0, 2, 0, Color.WHITE));
		}
	};
	
	public paneWriteCost() {
		setOpaque(false);
		setBorder(new LineBorder(Color.WHITE, 1, true));
		setBounds(171, 113, 580, 384);
		
		textCost = new JTextField();
		textCost.setBounds(243, 167, 157, 31);
		textCost.setHorizontalAlignment(SwingConstants.CENTER);
		textCost.setCaretColor(new Color(0, 0, 0));
		textCost.setOpaque(false);
		textCost.setForeground(Color.YELLOW);
		textCost.setCaretColor(Color.WHITE);
		textCost.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textCost.setColumns(10);
		textCost.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.WHITE));
		textCost.setBackground(Color.WHITE);
		textCost.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				textCost.setBorder(new MatteBorder(0, 0, 2, 0, Color.GREEN));
			}
			public void focusLost(FocusEvent e) {
				textCost.setBorder(new MatteBorder(0, 0, 2, 0, Color.WHITE));
			}
		});
		textCost.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() < '0' || e.getKeyChar() > '9')
					e.consume();
			}
			public void keyReleased(KeyEvent e) {
				lblmoney.setVisible(textCost.getText().isEmpty());
			}
		});
		setLayout(null);
		add(textCost);
		textCost.requestFocus();
		
		JLabel lblNewLabel_3 = new JLabel("Ghi số tiền đã chi tiêu");
		lblNewLabel_3.setBounds(179, 32, 219, 25);
		lblNewLabel_3.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(240, 255, 255)));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(new Color(248, 248, 255));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblNewLabel_3);
		
		textDay = new JTextField(""+dcBo.getCurDay());
		textDay.setBounds(243, 104, 73, 30);
		textDay.addFocusListener(focusDate);
		textDay.setHorizontalAlignment(SwingConstants.CENTER);
		textDay.setOpaque(false);
		textDay.setForeground(Color.YELLOW);
		textDay.setCaretColor(Color.WHITE);
		textDay.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textDay.setColumns(10);
		textDay.setBorder(null);
		textDay.setBackground(Color.WHITE);
		textDay.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					writeCost();
				}
			}
			public void keyReleased(KeyEvent e) {
				if(textDay.getText().isEmpty())
					lblDay.setVisible(true);
				else lblDay.setVisible(false);
			}
		});
		add(textDay);
		
		JLabel lblNgyChi = new JLabel("Ngày chi tiêu:");
		lblNgyChi.setBounds(114, 104, 119, 30);
		lblNgyChi.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNgyChi.setForeground(Color.WHITE);
		lblNgyChi.setFont(new Font("Segoe UI", Font.BOLD, 15));
		add(lblNgyChi);
		
		JLabel lblSTin = new JLabel("Số tiền:");
		lblSTin.setBounds(114, 167, 119, 30);
		lblSTin.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSTin.setForeground(Color.WHITE);
		lblSTin.setFont(new Font("Segoe UI", Font.BOLD, 15));
		add(lblSTin);
		
		JLabel lblNgiThanhTa = new JLabel("Người thanh toán:");
		lblNgiThanhTa.setBounds(112, 232, 133, 28);
		lblNgiThanhTa.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNgiThanhTa.setForeground(Color.WHITE);
		lblNgiThanhTa.setFont(new Font("Segoe UI", Font.BOLD, 15));
		add(lblNgiThanhTa);
		
		cmbPayer = new JComboBox<String>();
		cmbPayer.setBounds(243, 232, 221, 30);
		cmbPayer.setOpaque(false);
		cmbPayer.setForeground(new Color(178, 34, 34));
		cmbPayer.setFont(new Font("Tahoma", Font.BOLD, 14));
		cmbPayer.setModel(dcBo.get_modelListNameMember());
		add(cmbPayer);
		
		btnAddCost = new JButton("Hoàn tất");
		btnAddCost.setBounds(216, 309, 145, 36);
		btnAddCost.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setbtnAddCost(Color.green);
			}
			public void mouseExited(MouseEvent e) {
				setbtnAddCost(Color.white);
			}
		});
		btnAddCost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeCost();
			}
		});
		btnAddCost.setOpaque(false);
		btnAddCost.setMultiClickThreshhold(1L);
		btnAddCost.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAddCost.setForeground(Color.WHITE);
		btnAddCost.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnAddCost.setFocusCycleRoot(true);
		btnAddCost.setDoubleBuffered(true);
		btnAddCost.setContentAreaFilled(false);
		btnAddCost.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.WHITE));
		add(btnAddCost);
		
		JLabel lblTng_2 = new JLabel(".000 VNĐ");
		lblTng_2.setBounds(400, 168, 64, 30);
		lblTng_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTng_2.setForeground(new Color(255, 255, 0));
		lblTng_2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		add(lblTng_2);
		
		lblPayToday = new JLabel("Ghi chi tiêu hôm nay !");
		lblPayToday.setBounds(217, 72, 145, 21);
		lblPayToday.setIcon(new ImageIcon(paneWriteCost.class.getResource("/imgs/warning-24.png")));
		lblPayToday.setHorizontalAlignment(SwingConstants.LEFT);
		lblPayToday.setForeground(Color.ORANGE);
		lblPayToday.setFont(new Font("Segoe UI", Font.BOLD, 11));
		add(lblPayToday);
		lblPayToday.setVisible(!dcBo.checkPayCurDate());
		
		textMonth = new JTextField(""+dcBo.getCurMonth());
		textMonth.setBounds(317, 104, 73, 30);
		textMonth.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				writeCost();
			}
		});
		textMonth.setOpaque(false);
		textMonth.addFocusListener(focusDate);
		textMonth.setHorizontalAlignment(SwingConstants.CENTER);
		textMonth.setForeground(Color.YELLOW);
		textMonth.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textMonth.setColumns(10);
		textMonth.setCaretColor(Color.WHITE);
		textMonth.setBorder(null);
		textMonth.setBackground(Color.WHITE);
		add(textMonth);
		
		textYear = new JTextField(""+dcBo.getCurYear());
		textYear.setBounds(391, 104, 73, 30);
		textYear.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				writeCost();
			}
		});
		textYear.setOpaque(false);
		textYear.addFocusListener(focusDate);
		textYear.setHorizontalAlignment(SwingConstants.CENTER);
		textYear.setForeground(Color.YELLOW);
		textYear.setFont(new Font("Segoe UI", Font.BOLD, 15));
		textYear.setColumns(10);
		textYear.setCaretColor(Color.WHITE);
		textYear.setBorder(null);
		textYear.setBackground(Color.WHITE);
		add(textYear);
		
		lblDay = new JLabel("dd");
		lblDay.setBounds(243, 106, 64, 28);
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay.setForeground(Color.LIGHT_GRAY);
		lblDay.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblDay.setVisible(false);
		add(lblDay);
		
		lblMonth = new JLabel("mm");
		lblMonth.setBounds(317, 106, 68, 28);
		lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonth.setForeground(Color.LIGHT_GRAY);
		lblMonth.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblMonth.setVisible(false);
		add(lblMonth);
		
		lblYear = new JLabel("yyyy");
		lblYear.setBounds(391, 104, 73, 30);
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setForeground(Color.LIGHT_GRAY);
		lblYear.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblYear.setVisible(false);
		add(lblYear);
		
		lblBoderDate = new JLabel("/          /");
		lblBoderDate.setBounds(243, 104, 221, 30);
		lblBoderDate.setForeground(new Color(255, 255, 0));
		lblBoderDate.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		lblBoderDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblBoderDate.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblBoderDate);
		
		lblmoney = new JLabel("$$");
		lblmoney.setForeground(Color.LIGHT_GRAY);
		lblmoney.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblmoney.setHorizontalAlignment(SwingConstants.CENTER);
		lblmoney.setBounds(298, 173, 46, 25);
		add(lblmoney);
	}
}
