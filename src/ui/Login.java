package ui;

import client.Commit;
import client.Logined;
import model.Admin;
import model.Agreement;
import model.Transfer;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @author bqliang
 */

public class Login extends JFrame implements Agreement {

	private JPanel contentPane;
	private JTextField accountInput;
	private JPasswordField passwordInput;
	static private JFrame mySelf;

	public Login() {
		setTitle("\u6EF4\u6EF4\u8FD0\u52A8");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 430);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(null);
		headPanel.setBackground(new Color(242, 142, 30));
		headPanel.setBounds(0, 0, 586, 87);
		contentPane.add(headPanel);
		
		JLabel titleLabel = new JLabel("\u6EF4\u6EF4\u8FD0\u52A8");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 0, 586, 87);
		headPanel.add(titleLabel);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(158, 122, 261, 120);
		contentPane.add(mainPanel);
		
		accountInput = new JTextField();
		accountInput.setFont(new Font("等线", Font.PLAIN, 16));
		accountInput.setColumns(10);
		accountInput.setBounds(81, 0, 176, 32);
		mainPanel.add(accountInput);
		
		passwordInput = new JPasswordField();
		passwordInput.setFont(new Font("等线", Font.PLAIN, 16));
		passwordInput.setBounds(81, 53, 176, 32);
		mainPanel.add(passwordInput);
		
		JLabel loginLabel = new JLabel("\u767B\u5F55\u540D\uFF1A");
		loginLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		loginLabel.setFont(new Font("等线", Font.PLAIN, 16));
		loginLabel.setBounds(0, 1, 82, 32);
		mainPanel.add(loginLabel);
		
		JLabel passwLabel = new JLabel("\u5BC6\u7801\uFF1A");
		passwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwLabel.setFont(new Font("等线", Font.PLAIN, 16));
		passwLabel.setBounds(0, 54, 82, 32);
		mainPanel.add(passwLabel);
		
		JRadioButton isAdmin = new JRadioButton("\u7BA1\u7406\u5458");
		isAdmin.setToolTipText("非管理员请勿勾选");
		isAdmin.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		isAdmin.setHorizontalAlignment(SwingConstants.RIGHT);
		isAdmin.setFocusPainted(false);
		isAdmin.setContentAreaFilled(false);
		isAdmin.setBounds(183, 94, 74, 25);
		mainPanel.add(isAdmin);
		
		JLabel loginByCode = new JLabel("-> \u9A8C\u8BC1\u7801\u767B\u5F55");
		loginByCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new EmailLogin(mySelf);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				loginByCode.setForeground(new Color(30, 144, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginByCode.setForeground(new Color(242, 142, 30));
			}
		});
		loginByCode.setToolTipText("使用邮箱验证码登录账号");
		loginByCode.setHorizontalTextPosition(SwingConstants.CENTER);
		loginByCode.setForeground(new Color(242, 142, 30));
		loginByCode.setFont(new Font("微软雅黑", Font.ITALIC, 14));
		loginByCode.setBounds(67, 98, 99, 16);
		mainPanel.add(loginByCode);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBounds(108, 263, 369, 56);
		contentPane.add(buttonPanel);
		
		JButton registerButton = new JButton("\u6CE8\u518C");
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Register(mySelf);
			}
		});
		registerButton.setForeground(new Color(242, 142, 30));
		registerButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		registerButton.setFocusPainted(false);
		registerButton.setBorderPainted(false);
		registerButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		registerButton.setBackground(SystemColor.menu);
		registerButton.setBounds(50, 5, 109, 45);
		buttonPanel.add(registerButton);
		
		JButton loginButton = new JButton("\u767B\u5F55");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Transfer transfer = new Transfer();
				Transfer feedback = null;
				int result = 0;

				if (isAdmin.isSelected()){
					transfer.setAdmin(
							new Admin(accountInput.getText(), new String(passwordInput.getPassword()))
					);
					transfer.setCommand(ADMIN_LOGIN);
				}else {
					transfer.setUser(
							new User(accountInput.getText(), new String(passwordInput.getPassword()))
					);
					transfer.setCommand(USER_LOGIN);
				}

				Commit.set(transfer);
				try {
					feedback = Commit.start();
					result = feedback.getResult();
				} catch (IOException | ClassNotFoundException exception) {
					exception.printStackTrace();
				}

				if (result == SUCCESS){
					try {
						// 打开活动列表
						new UserViewActivities();
						mySelf.dispose();
					} catch (IOException | ClassNotFoundException ioException) {
						ioException.printStackTrace();
					}
					// 将登录成功的用户信息保存
					Logined.setUser(feedback.getUser());
					if(isAdmin.isSelected()){

					}else {

					}
				}else if(result == ACCOUNT_NOT_EXIST){
					JOptionPane.showMessageDialog(null, "请检查后重试", "账号不存在", JOptionPane.ERROR_MESSAGE);
				}else if (result == PASSWORD_ERROR){
					JOptionPane.showMessageDialog(null, "请检查后重试，如忘记密码，可通过邮箱找回", "密码错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		loginButton.setForeground(Color.WHITE);
		loginButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		loginButton.setFocusPainted(false);
		loginButton.setBorderPainted(false);
		loginButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		loginButton.setBackground(new Color(242, 142, 30));
		loginButton.setBounds(209, 5, 109, 45);
		buttonPanel.add(loginButton);
		
		JLabel buttonInfoLabel = new JLabel("Copyright \u00A9 2020    996开发组");
		buttonInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		buttonInfoLabel.setForeground(Color.DARK_GRAY);
		buttonInfoLabel.setFont(new Font("等线 Light", Font.PLAIN, 15));
		buttonInfoLabel.setBounds(179, 368, 218, 25);
		contentPane.add(buttonInfoLabel);
		
		JLabel forgetPassword = new JLabel("忘记密码？立即找回");
		forgetPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 点击找回密码
				new RetrievePassword(mySelf);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				forgetPassword.setForeground(new Color(30, 144, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				forgetPassword.setForeground(new Color(242, 142, 30));
			}
		});
		forgetPassword.setHorizontalAlignment(SwingConstants.CENTER);
		forgetPassword.setHorizontalTextPosition(SwingConstants.CENTER);
		forgetPassword.setForeground(new Color(242, 142, 30));
		forgetPassword.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		forgetPassword.setBounds(108, 319, 369, 43);
		contentPane.add(forgetPassword);
		setVisible(true);
		mySelf = this;
	}
}
