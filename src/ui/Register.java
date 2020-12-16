package ui;

import client.Commit;
import model.Agreement;
import model.Transfer;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * @author bqliang
 */

public class Register extends JFrame implements Agreement {

	private JPanel contentPane;
	private JTextField nameInput;
	private JPasswordField passwordInput;
	private JPasswordField passwordInputAgain;
	private JTextField contactInput;
	private JTextField emailInput;
	private Login loginJframe;

	public Register(Login jFrame) {
		this.loginJframe = jFrame;
		loginJframe.setVisible(false);
		setTitle("注册账号");
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 627, 550);
		setLocationRelativeTo(null);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 65, 613, 448);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);
		
		JLabel nameLabel = new JLabel("用户名：");
		nameLabel.setBounds(82, 18, 64, 17);
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("等线", Font.PLAIN, 16));
		mainPanel.add(nameLabel);
		
		nameInput = new JTextField();
		nameInput.setFont(new Font("等线", Font.PLAIN, 16));
		nameInput.setEnabled(true);
		nameInput.setEditable(true);
		nameInput.setColumns(10);
		nameInput.setBounds(165, 10, 178, 32);
		mainPanel.add(nameInput);
		
		JLabel nameTrips = new JLabel("字母开头，5-16个字符，允许字母、数字与下划线");
		nameTrips.setForeground(Color.GRAY);
		nameTrips.setHorizontalAlignment(SwingConstants.LEFT);
		nameTrips.setFont(new Font("等线", Font.PLAIN, 14));
		nameTrips.setBounds(82, 56, 317, 17);
		mainPanel.add(nameTrips);
		
		JLabel passwordLabel = new JLabel("密码：");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setFont(new Font("等线", Font.PLAIN, 16));
		passwordLabel.setBounds(98, 107, 48, 17);
		mainPanel.add(passwordLabel);
		
		JLabel passwordTrips = new JLabel("8-10个字符，必须包含大小写字母与数字，可以使用特殊字符");
		passwordTrips.setHorizontalAlignment(SwingConstants.LEFT);
		passwordTrips.setForeground(Color.GRAY);
		passwordTrips.setFont(new Font("等线", Font.PLAIN, 14));
		passwordTrips.setBounds(98, 145, 389, 17);
		mainPanel.add(passwordTrips);
		
		JLabel passwordAgainLabel = new JLabel("确认密码：");
		passwordAgainLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordAgainLabel.setFont(new Font("等线", Font.PLAIN, 16));
		passwordAgainLabel.setBounds(66, 186, 80, 17);
		mainPanel.add(passwordAgainLabel);
		
		passwordInput = new JPasswordField();
		passwordInput.setFont(new Font("等线", Font.PLAIN, 16));
		passwordInput.setBounds(165, 99, 178, 32);
		mainPanel.add(passwordInput);
		
		passwordInputAgain = new JPasswordField();
		passwordInputAgain.setFont(new Font("等线", Font.PLAIN, 16));
		passwordInputAgain.setBounds(165, 178, 178, 32);
		mainPanel.add(passwordInputAgain);
		
		JLabel genderLabel = new JLabel("性别：");
		genderLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		genderLabel.setFont(new Font("等线", Font.PLAIN, 16));
		genderLabel.setBounds(98, 228, 48, 17);
		mainPanel.add(genderLabel);
		
		JComboBox genderComboBox = new JComboBox();
		genderComboBox.setFont(new Font("等线", Font.PLAIN, 16));
		genderComboBox.setModel(new DefaultComboBoxModel(new String[] {"男", "女", "不便透露"}));
		genderComboBox.setSelectedIndex(0);
		genderComboBox.setBounds(165, 225, 101, 23);
		mainPanel.add(genderComboBox);
		
		JLabel contactLabel = new JLabel("联系方式：");
		contactLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		contactLabel.setFont(new Font("等线", Font.PLAIN, 16));
		contactLabel.setBounds(66, 276, 80, 17);
		mainPanel.add(contactLabel);
		
		JComboBox contactComboBox = new JComboBox();
		contactComboBox.setModel(new DefaultComboBoxModel(new String[] {"微信", "QQ", "手机"}));
		contactComboBox.setSelectedIndex(0);
		contactComboBox.setFont(new Font("等线", Font.PLAIN, 16));
		contactComboBox.setBounds(165, 273, 101, 23);
		mainPanel.add(contactComboBox);
		
		contactInput = new JTextField();
		contactInput.setFont(new Font("等线", Font.PLAIN, 16));
		contactInput.setEnabled(true);
		contactInput.setEditable(true);
		contactInput.setColumns(10);
		contactInput.setBounds(286, 268, 178, 32);
		mainPanel.add(contactInput);
		
		JLabel emailLabel = new JLabel("电子邮箱：");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("等线", Font.PLAIN, 16));
		emailLabel.setBounds(66, 324, 80, 17);
		mainPanel.add(emailLabel);
		
		emailInput = new JTextField();
		emailInput.setFont(new Font("等线", Font.PLAIN, 16));
		emailInput.setEnabled(true);
		emailInput.setEditable(true);
		emailInput.setColumns(10);
		emailInput.setBounds(165, 316, 178, 32);
		mainPanel.add(emailInput);
		
		JLabel emailTrips = new JLabel("仅用于找回密码与验证码登录，我们承诺将严格保密您的信息");
		emailTrips.setHorizontalAlignment(SwingConstants.LEFT);
		emailTrips.setForeground(Color.GRAY);
		emailTrips.setFont(new Font("等线", Font.PLAIN, 14));
		emailTrips.setBounds(66, 356, 406, 17);
		mainPanel.add(emailTrips);

		JButton registerButton = new JButton("注册");
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User user = new User(nameInput.getText(), new String(passwordInput.getPassword()), (String) genderComboBox.getSelectedItem(),contactComboBox.getSelectedItem() + ":" + contactInput.getText(), emailInput.getText());
				Transfer transfer = new Transfer();
				Transfer feedback = null;
				transfer.setCommand(REGISTER);
				transfer.setUser(user);
				Commit.set(transfer);
				try {
					feedback = Commit.start();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				int result = feedback.getResult();
				if (result == NAME_ALREADY_EXISTS){
					JOptionPane.showMessageDialog(null, "请尝试更换登录名", "账号已存在", JOptionPane.ERROR_MESSAGE);
				}else if (result == EMAIL_ALREADY_EXISTS){
					JOptionPane.showMessageDialog(null, "请尝试更换邮箱", "邮箱已被注册", JOptionPane.ERROR_MESSAGE);
				}else if (result == SUCCESS){
					JOptionPane.showMessageDialog(null, "注册成功");
				}
			}
		});
		registerButton.setForeground(Color.WHITE);
		registerButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		registerButton.setFocusPainted(false);
		registerButton.setBorderPainted(false);
		registerButton.setBackground(new Color(242, 142, 30));
		registerButton.setBounds(438, 392, 153, 43);
		mainPanel.add(registerButton);
		
		JLabel login = new JLabel("登录现有账号");
		login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closeWindow();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				login.setForeground(new Color(30, 144, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				login.setForeground(new Color(242, 142, 30));
			}
		});
		login.setHorizontalAlignment(SwingConstants.LEFT);
		login.setForeground(new Color(242, 142, 30));
		login.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		login.setBounds(333, 417, 90, 16);
		mainPanel.add(login);
		
		JLabel help = new JLabel("帮助");
		help.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				help.setForeground(new Color(30, 144, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				help.setForeground(new Color(242, 142, 30));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// 点击帮助
			}
		});
		help.setHorizontalAlignment(SwingConstants.LEFT);
		help.setForeground(new Color(242, 142, 30));
		help.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		help.setBounds(34, 417, 38, 16);
		mainPanel.add(help);
		
		JLabel privacy = new JLabel("隐私权");
		privacy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 点击隐私权
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				privacy.setForeground(new Color(30, 144, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				privacy.setForeground(new Color(242, 142, 30));
			}
		});
		privacy.setHorizontalAlignment(SwingConstants.LEFT);
		privacy.setForeground(new Color(242, 142, 30));
		privacy.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		privacy.setBounds(82, 417, 51, 16);
		mainPanel.add(privacy);
		
		JLabel terms = new JLabel("条款");
		terms.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 点击条款
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				terms.setForeground(new Color(30, 144, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				terms.setForeground(new Color(242, 142, 30));
			}
		});
		terms.setHorizontalAlignment(SwingConstants.LEFT);
		terms.setForeground(new Color(242, 142, 30));
		terms.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		terms.setBounds(143, 417, 36, 16);
		mainPanel.add(terms);
		
		JPanel headPanel = new JPanel();
		headPanel.setBackground(new Color(242, 142, 30));
		headPanel.setBounds(0, 0, 613, 56);
		contentPane.add(headPanel);
		headPanel.setLayout(null);
		
		JLabel headLabel = new JLabel("-> 创建您的滴滴运动账号");
		headLabel.setHorizontalAlignment(SwingConstants.LEFT);
		headLabel.setForeground(Color.WHITE);
		headLabel.setBounds(26, 4, 327, 47);
		headLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
		headPanel.add(headLabel);
		setVisible(true);
	}

	private void closeWindow(){
		dispose();
		loginJframe.setVisible(true);
	}
}
