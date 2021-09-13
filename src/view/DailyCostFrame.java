package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import bean.ImagePanel;
import bo.dailyCostBo;

import java.awt.Color;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.SystemColor;

public class DailyCostFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ImagePanel background;
	private paneWriteCost panewricost;
	private paneHistory panehistory;
	private paneStatistical panestatistical;
	private JPanel panelChosse;
	private JLabel lblHistory;
	private JLabel lblStatistical;
	private JLayeredPane layeredPane;
	private JLabel lblThoat;
	
	private JLabel lblNewDailyCost;
	private dailyCostBo dcBo = new dailyCostBo();
	
	private int paneVisit = 0;
	public static int isRefesh = 0;
	
	public void switchPanel(int panel) {
		switch (panel) {
		case 1:
			if(paneVisit != 1) {
				lblNewDailyCost.setBorder(new LineBorder(Color.YELLOW, 2, true));
				lblNewDailyCost.setForeground(Color.yellow);
				panehistory.setVisible(false);
				panestatistical.setVisible(false);
				panewricost.setVisible(true);
				paneVisit = 1;
			}
			break;
		case 2:
			if(paneVisit != 2) {
				lblHistory.setBorder(new LineBorder(Color.YELLOW, 2, true));
				lblHistory.setForeground(Color.yellow);
				panewricost.setVisible(false);
				panestatistical.setVisible(false);
				panehistory.setVisible(true);
				if(isRefesh > 0) {
					panehistory.setTableHistory();
					isRefesh--;
				}
				paneVisit = 2;
			}
			break;
		case 3:
			if(paneVisit != 3) {
				lblStatistical.setBorder(new LineBorder(Color.YELLOW, 2, true));
				lblStatistical.setForeground(Color.yellow);
				panewricost.setVisible(false);
				panehistory.setVisible(false);
				panestatistical.setVisible(true);
				if(isRefesh > 0) {
					panestatistical.setTablePayer();
					isRefesh--;
				}
				paneVisit = 3;
			}
			break;
		default:
			break;
		}
	}
	
	public void revokeVisit(int index) {
		switch (index) {
		case 1:
			if(paneVisit != 1) {
				lblNewDailyCost.setBorder(null);
				lblNewDailyCost.setForeground(Color.WHITE);
			}
			break;
		case 2:
			if(paneVisit != 2) {
				lblHistory.setBorder(null);
				lblHistory.setForeground(Color.WHITE);
			}
			break;
		case 3:
			if(paneVisit != 3) {
				lblStatistical.setBorder(null);
				lblStatistical.setForeground(Color.WHITE);
			}
			break;
		default:
			break;
		}
	}
	/**
	 * Create the frame.
	 */
	public DailyCostFrame() {
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				if(dcBo.checkPayCurDate()) {
					switchPanel(2);
					lblHistory.setBorder(new LineBorder(Color.yellow, 2, true));
				}
				else {
					switchPanel(1);
					lblNewDailyCost.setBorder(new LineBorder(Color.yellow, 2, true));
				}
			}
		});
		setIconImage(new ImageIcon(DailyCostFrame.class.getResource("/imgs/commerce-64.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 761, 537);
		
		set_panelChosse();
		set_layeredPane();
		set_background();
		
		setContentPane(background);
		
		JLabel lblCurDate = new JLabel(dcBo.getCurDateE());
		lblCurDate.setName("Today");
		lblCurDate.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		lblCurDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurDate.setForeground(new Color(255, 255, 0));
		lblCurDate.setFont(new Font("Sylfaen", Font.BOLD, 15));
		lblCurDate.setBackground(SystemColor.menu);
		lblCurDate.setBounds(593, 90, 158, 18);
		background.add(lblCurDate);
		
		JLabel lblExit = new JLabel("X");
		lblExit.setBorder(new LineBorder(Color.LIGHT_GRAY));
		lblExit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			public void mouseEntered(MouseEvent e) {
				lblExit.setForeground(Color.red);
			}
			public void mouseExited(MouseEvent e) {
				lblExit.setForeground(Color.white);
			}
		});
		lblExit.setHorizontalAlignment(SwingConstants.CENTER);
		lblExit.setForeground(Color.WHITE);
		lblExit.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblExit.setBounds(728, 2, 31, 30);
		background.add(lblExit);
		
		JLabel lblExit_1 = new JLabel("-");
		lblExit_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		lblExit_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(ICONIFIED);
			}
			public void mouseEntered(MouseEvent e) {
				lblExit_1.setForeground(Color.green);
			}
			public void mouseExited(MouseEvent e) {
				lblExit_1.setForeground(Color.white);
			}
		});
		lblExit_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblExit_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblExit_1.setForeground(Color.WHITE);
		lblExit_1.setFont(new Font("Segoe UI", Font.BOLD, 27));
		lblExit_1.setBounds(698, 2, 31, 30);
		background.add(lblExit_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBorder(new LineBorder(new Color(255, 69, 0), 2));
		lblNewLabel_2.setIconTextGap(8);
		lblNewLabel_2.setBounds(0, 0, 761, 537);
		background.add(lblNewLabel_2);
		
		setLocationRelativeTo(null);
		setUndecorated(true);
	}
	
	public void set_panelChosse() {
		panelChosse = new JPanel();
		panelChosse.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.WHITE));
		panelChosse.setOpaque(false);
		panelChosse.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelChosse.setForeground(Color.ORANGE);
		panelChosse.setBounds(10, 113, 158, 384);
		panelChosse.setLayout(null);
		
		
		lblHistory = new JLabel("Lịch sử");
		lblHistory.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int tam = paneVisit;
				switchPanel(2);
				revokeVisit(tam);
			}
			public void mouseEntered(MouseEvent e) {
				if(paneVisit != 2)
					lblHistory.setBorder(new LineBorder(Color.green, 2, true));
			}
			public void mouseExited(MouseEvent e) {
				revokeVisit(2);
			}
		});
		
		lblHistory.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistory.setIcon(new ImageIcon(DailyCostFrame.class.getResource("/imgs/pay-date-48.png")));
		lblHistory.setForeground(new Color(255, 255, 255));
		lblHistory.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHistory.setBounds(8, 121, 142, 75);
		panelChosse.add(lblHistory);
		
		lblNewDailyCost = new JLabel("<html>Ghi chép<br>chi tiêu</html>");
		lblNewDailyCost.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int tam = paneVisit;
				switchPanel(1);
				revokeVisit(tam);
			}
			public void mouseEntered(MouseEvent e) {
				if(paneVisit != 1)
					lblNewDailyCost.setBorder(new LineBorder(Color.green, 2, true));
			}
			public void mouseExited(MouseEvent e) {
				revokeVisit(1);
			}
		});
		lblNewDailyCost.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblNewDailyCost.setForeground(new Color(248, 248, 255));
		lblNewDailyCost.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewDailyCost.setIcon(new ImageIcon(DailyCostFrame.class.getResource("/imgs/property-48.png")));
		lblNewDailyCost.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewDailyCost.setBounds(8, 23, 142, 75);
		panelChosse.add(lblNewDailyCost);
		
		JLabel lblError_1 = new JLabel("");
		lblError_1.setBounds(0, 77, 152, 14);
		panelChosse.add(lblError_1);
		lblError_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblError_1.setForeground(Color.RED);
		
		lblStatistical = new JLabel("Thống kê");
		lblStatistical.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int tam = paneVisit;
				switchPanel(3);
				revokeVisit(tam);
			}
			public void mouseEntered(MouseEvent e) {
				if(paneVisit != 3)
					lblStatistical.setBorder(new LineBorder(Color.green, 2, true));
			}
			public void mouseExited(MouseEvent e) {
				revokeVisit(3);
			}
		});
		lblStatistical.setIcon(new ImageIcon(DailyCostFrame.class.getResource("/imgs/money-bag-48.png")));
		lblStatistical.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatistical.setForeground(Color.WHITE);
		lblStatistical.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStatistical.setBounds(8, 219, 142, 75);
		panelChosse.add(lblStatistical);
		
		lblThoat = new JLabel("Thoát");
		lblThoat.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			public void mouseEntered(MouseEvent e) {
				lblThoat.setBorder(new LineBorder(Color.red, 2, true));
			}
			public void mouseExited(MouseEvent e) {
				lblThoat.setBorder(null);
			}
		});
		lblThoat.setIcon(new ImageIcon(DailyCostFrame.class.getResource("/imgs/exit-32.png")));
		lblThoat.setHorizontalAlignment(SwingConstants.CENTER);
		lblThoat.setForeground(Color.WHITE);
		lblThoat.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblThoat.setBounds(8, 317, 142, 44);
		panelChosse.add(lblThoat);
	}
	
	public void set_layeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.WHITE));
		layeredPane.setBounds(171, 113, 580, 384);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		panehistory = new paneHistory();
		panehistory.setTableHistory();
		panestatistical = new paneStatistical();
		panestatistical.setTablePayer();
		panewricost = new paneWriteCost();
		
		layeredPane.add(panewricost, 0);
		layeredPane.add(panehistory, 1);
		layeredPane.add(panestatistical, 2);
	}
	
	public void set_background() {
		background = new ImagePanel("/imgs/preview.jpg");
		background.setBounds(0, 0, 761, 537);
		background.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("QUẢN LÝ CHI TIÊU SINH VIÊN");
		lblNewLabel.setBounds(129, 40, 526, 39);
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		background.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(51, 30, 78, 72);
		lblNewLabel_1.setLabelFor(this);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(DailyCostFrame.class.getResource("/imgs/commerce-64.png")));
		background.add(lblNewLabel_1);
		
		JLabel lblbyTrnTrung = new JLabel("@By Trần Trung Chính - ĐT: 0362.942.329");
		lblbyTrnTrung.setBounds(167, 508, 427, 18);
		lblbyTrnTrung.setBackground(new Color(240, 240, 240));
		lblbyTrnTrung.setHorizontalAlignment(SwingConstants.CENTER);
		lblbyTrnTrung.setForeground(Color.WHITE);
		lblbyTrnTrung.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		background.add(lblbyTrnTrung);
		
		background.add(panelChosse);
		background.add(layeredPane);
	}
}
