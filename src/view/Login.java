package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import bean.ImagePanel;
import dao.LoginDao;
import java.awt.SystemColor;
import java.awt.Cursor;

public class Login extends JFrame{
	private static final long serialVersionUID = 1L;
	private ImagePanel contenPanel;
	private JButton btnLogin;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JLabel lblError, lblPassword, lblUsername;
	
	private LoginDao lDao = new LoginDao();
	
	public void login() {
		lblError.setText("");
		String username = txtUsername.getText();
		String password = String.valueOf(passwordField.getPassword());
		if(Check(username, password)) {
			if(lDao.signIn(username, password)) {
				new DailyCostFrame().setVisible(true);
				dispose();
			}else {
				lblError.setText("Login unsuccessful. Re-Login!");
				passwordField.setText("");
				txtUsername.setText("");
				lblUsername.setVisible(true);
				lblPassword.setVisible(true);
				txtUsername.requestFocus();
			}
		}
	}
	
	private boolean Check(String username, String password) {
		if(username.isEmpty()) {
			txtUsername.requestFocus();
			lblError.setText("The username is empty!");
			return false;
		}
		if(password.isEmpty()) {
			passwordField.requestFocus();
			lblError.setText("The password is empty!");
			return false;
		}
		return true;
	}
	
	private void setbtnLogin(Color color) {
		btnLogin.setForeground(color);
		btnLogin.setBorder(new LineBorder(color, 2, true));
	}
	
	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(new ImageIcon(Login.class.getResource("/imgs/user.png")).getImage());
		setAutoRequestFocus(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 435, 490);
		
		contenPanel = new ImagePanel("/imgs/login.jpg");
		contenPanel.setBounds(0, 0, 435, 490);
		setContentPane(contenPanel);
		contenPanel.setLayout(null);
		
		JLabel lblAcc = new JLabel("");
		lblAcc.setIcon(new ImageIcon(Login.class.getResource("/imgs/user.png")));
		lblAcc.setBounds(163, 44, 108, 88);
		lblAcc.setHorizontalAlignment(SwingConstants.CENTER);
		contenPanel.add(lblAcc);
		
		btnLogin = new JButton("Next");
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnLogin.setBounds(137, 363, 145, 38);
		btnLogin.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				setbtnLogin(Color.white);
			}
		});
		btnLogin.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				setbtnLogin(Color.green);
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.setOpaque(false);
		btnLogin.setMultiClickThreshhold(1L);
		btnLogin.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnLogin.setFocusCycleRoot(true);
		btnLogin.setDoubleBuffered(true);
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorder(new LineBorder(Color.WHITE, 2, true));
		contenPanel.add(btnLogin);
		
		JLabel lblUser = new JLabel("");
		lblUser.setVerticalAlignment(SwingConstants.BOTTOM);
		lblUser.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblUser.setHorizontalAlignment(SwingConstants.LEFT);
		lblUser.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblUser.setIcon(new ImageIcon(Login.class.getResource("/imgs/username-48.png")));
		lblUser.setBounds(49, 205, 48, 48);
		lblUser.setForeground(Color.LIGHT_GRAY);
		lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
		contenPanel.add(lblUser);
		
		txtUsername = new JTextField();
		txtUsername.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		txtUsername.setBounds(49, 223, 340, 30);
		txtUsername.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				txtUsername.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.yellow));
			}
			public void focusLost(FocusEvent e) {
				lblUsername.setVisible(txtUsername.getText().isEmpty());
				txtUsername.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
			}
		});
		txtUsername.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
			public void keyReleased(KeyEvent e) {
				lblUsername.setVisible(txtUsername.getText().isEmpty());
			}
		});
		txtUsername.setFont(new Font("Segoe UI", Font.BOLD, 15));
		txtUsername.setOpaque(false);
		txtUsername.setForeground(Color.YELLOW);
		txtUsername.setCaretColor(Color.YELLOW);
		contenPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPass = new JLabel("");
		lblPass.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblPass.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPass.setHorizontalAlignment(SwingConstants.LEFT);
		lblPass.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblPass.setIcon(new ImageIcon(Login.class.getResource("/imgs/password-48.png")));
		lblPass.setBounds(49, 264, 48, 48);
		lblPass.setForeground(Color.LIGHT_GRAY);
		lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
		contenPanel.add(lblPass);
		
		passwordField = new JPasswordField();
		passwordField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(49, 282, 340, 30);
		passwordField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				passwordField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.yellow));
			}
			public void focusLost(FocusEvent e) {
				lblPassword.setVisible(String.valueOf(passwordField.getPassword()).isEmpty());
				passwordField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
			}
		});
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
			public void keyReleased(KeyEvent e) {
				lblPassword.setVisible(String.valueOf(passwordField.getPassword()).isEmpty());
			}
		});
		passwordField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		passwordField.setForeground(Color.YELLOW);
		passwordField.setCaretColor(Color.YELLOW);
		passwordField.setFont(new Font("Segoe UI", Font.BOLD, 15));
		passwordField.setOpaque(false);
		contenPanel.add(passwordField);
		
		JLabel lblTTC = new JLabel("");
		lblTTC.setBounds(24, 24, 93, 75);
		lblTTC.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.PINK));
		lblTTC.setIcon(new ImageIcon(Login.class.getResource("/imgs/ttc.png")));
		lblTTC.setHorizontalAlignment(SwingConstants.CENTER);
		lblTTC.setForeground(new Color(255, 255, 255));
		lblTTC.setFont(new Font("SimSun", Font.BOLD | Font.ITALIC, 30));
		contenPanel.add(lblTTC);
		
		lblError = new JLabel("");
		lblError.setBounds(15, 412, 384, 14);
		lblError.setForeground(Color.RED);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		contenPanel.add(lblError);
		
		JLabel lblLoginAccount = new JLabel("Login Account");
		lblLoginAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginAccount.setForeground(Color.green);
		lblLoginAccount.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblLoginAccount.setBounds(103, 133, 229, 30);
		contenPanel.add(lblLoginAccount);
		
		JLabel lblbyTrnTrung = new JLabel("@By Trần Trung Chính - ĐT: 0362.942.329");
		lblbyTrnTrung.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblbyTrnTrung.setHorizontalAlignment(SwingConstants.CENTER);
		lblbyTrnTrung.setForeground(Color.WHITE);
		lblbyTrnTrung.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblbyTrnTrung.setBackground(SystemColor.menu);
		lblbyTrnTrung.setBounds(90, 461, 255, 18);
		contenPanel.add(lblbyTrnTrung);
		
		lblPassword = new JLabel("Enter password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(Color.LIGHT_GRAY);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setBounds(159, 283, 117, 30);
		contenPanel.add(lblPassword);
		
		lblUsername = new JLabel("Enter username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setForeground(Color.LIGHT_GRAY);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsername.setBounds(159, 223, 117, 30);
		contenPanel.add(lblUsername);
		
		JLabel lblExit = new JLabel("X");
		lblExit.setBorder(new LineBorder(new Color(169, 169, 169)));
		lblExit.setBounds(396, 0, 38, 33);
		lblExit.setForeground(Color.WHITE);
		lblExit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
			public void mouseEntered(MouseEvent e) {
				lblExit.setForeground(Color.RED);
			}
			public void mouseExited(MouseEvent e) {
				lblExit.setForeground(Color.white);
			}
		});
		lblExit.setHorizontalAlignment(SwingConstants.CENTER);
		lblExit.setFont(new Font("Segoe UI", Font.BOLD, 18));
		contenPanel.add(lblExit);
		
		JLabel lblExit_1 = new JLabel("-");
		lblExit_1.setBorder(new LineBorder(new Color(169, 169, 169)));
		lblExit_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblExit_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblExit_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblExit_1.setLabelFor(this);
		lblExit_1.setBounds(357, 0, 38, 33);
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
		lblExit_1.setForeground(Color.WHITE);
		lblExit_1.setFont(new Font("Segoe UI", Font.BOLD, 27));
		contenPanel.add(lblExit_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBorder(new LineBorder(new Color(255, 69, 0), 2));
		lblNewLabel_2.setBounds(0, 0, 434, 490);
		contenPanel.add(lblNewLabel_2);
		
		setLocationRelativeTo(null);
		setUndecorated(true);
	}
}
