package ui;

import client.Commit;
import client.CountDownThread;
import client.Logined;
import client.Tools;
import model.Agreement;
import model.Transfer;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import java.awt.event.*;
import java.awt.Insets;
import java.io.IOException;

/**
 * @author bqliang
 */
public class EmailLogin extends JFrame implements Agreement {

	private JPanel contentPane;
	private JTextField emailInput;
	private JTextField codeInput;
	private String code;
	private JFrame jframe;

	public EmailLogin(JFrame jframe) {
		this.jframe = jframe;
		jframe.setVisible(false);
		this.setVisible(true);
		setTitle("验证码登录");
		setIconImage(Tools.getImage("icons/email.png"));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(80, 35, 253, 122);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton getCode = new JButton("获取验证码");
		getCode.setMargin(new Insets(2, 10, 2, 10));
		getCode.setFont(new Font("微软雅黑", Font.BOLD, 14));
		getCode.setFocusPainted(false);
		getCode.setForeground(new Color(242, 142, 30));
		getCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(emailInput.getText() == null){
					JOptionPane.showMessageDialog(null, "请检查后重试", "您似乎还未输入邮箱", JOptionPane.ERROR_MESSAGE);
					return;
				}else if(!Tools.isEmail(emailInput.getText())){
					JOptionPane.showMessageDialog(null, "请检查后重试", "您输入邮箱不合法", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Transfer transfer = new Transfer();
				Transfer feedback = new Transfer();
				transfer.setCommand(SEND_CODE);
				User user = new User();
				user.setEmail(emailInput.getText());
				transfer.setUser(user);
				Commit.set(transfer);
				try {
					feedback = Commit.start();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				if (feedback.getResult() == SUCCESS){
					emailInput.setEnabled(false);
					code = feedback.getCode();
					CountDownThread countDownThread = new CountDownThread(getCode);
					new Thread(countDownThread).start();
				}else if(feedback.getResult() == EMAIL_NOT_EXISTS){
					JOptionPane.showMessageDialog(null, "请检查后重试", "账号不存在", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		getCode.setBackground(SystemColor.menu);
		getCode.setBorderPainted(false);
		getCode.setBounds(151, 58, 102, 25);
		panel.add(getCode);
		
		emailInput = new JTextField();
		emailInput.setEnabled(true);
		emailInput.setEditable(true);
		emailInput.setFont(new Font("等线", Font.PLAIN, 16));
		emailInput.setColumns(10);
		emailInput.setBounds(67, 2, 185, 32);
		panel.add(emailInput);
		
		JLabel emailLabel = new JLabel("邮箱：");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("等线", Font.PLAIN, 16));
		emailLabel.setBounds(0, 1, 69, 32);
		panel.add(emailLabel);
		
		JLabel codeLabel = new JLabel("验证码：");
		codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		codeLabel.setFont(new Font("等线", Font.PLAIN, 16));
		codeLabel.setBounds(0, 54, 70, 32);
		panel.add(codeLabel);
		
		JLabel notReceiveEmail = new JLabel("没有收到验证码？");
		notReceiveEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		notReceiveEmail.setToolTipText("请检查邮件是否被归档到垃圾邮箱");
		notReceiveEmail.setHorizontalTextPosition(SwingConstants.CENTER);
		notReceiveEmail.setForeground(Color.GRAY);
		notReceiveEmail.setFont(new Font("微软雅黑", Font.ITALIC, 14));
		notReceiveEmail.setBounds(135, 95, 118, 16);
		panel.add(notReceiveEmail);
		
		codeInput = new JTextField();
		codeInput.setFont(new Font("等线", Font.PLAIN, 16));
		codeInput.setColumns(10);
		codeInput.setBounds(67, 55, 78, 32);
		panel.add(codeInput);
		
		JLabel loginByPw = new JLabel("密码登录");
		loginByPw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closeWindow();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				loginByPw.setForeground(new Color(30, 144, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginByPw.setForeground(new Color(242, 142, 30));
			}
		});
		loginByPw.setForeground(new Color(242, 142, 30));
		loginByPw.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		loginByPw.setBounds(67, 95, 65, 16);
		panel.add(loginByPw);
		loginByPw.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton loginButton = new JButton("登录");
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(242, 142, 30));
		loginButton.setFocusPainted(false);
		loginButton.setBorderPainted(false);
		loginButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 登录
				if(codeInput.getText() == null){
					JOptionPane.showMessageDialog(null, "请检查后重试", "您似乎还未输入验证码", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(codeInput.getText().equals(code)){
					Transfer transfer = new Transfer();
					Transfer feedback = new Transfer();
					transfer.setCommand(LOGIN_BY_EMAIL);
					User user = new User();
					user.setEmail(emailInput.getText());
					transfer.setUser(user);
					Commit.set(transfer);
					try {
						feedback = Commit.start();
					} catch (IOException | ClassNotFoundException ioException) {
						ioException.printStackTrace();
					}
					Logined.setUser(feedback.getUser());
					try {
						new UserViewActivities();
						close();
					} catch (IOException | ClassNotFoundException ioException) {
						ioException.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "请检查后重试", "验证码错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		loginButton.setBounds(141, 179, 153, 43);
		contentPane.add(loginButton);
	}

	private void closeWindow(){
		jframe.setVisible(true);
		this.dispose();
	}

	private void close(){
		this.dispose();
	}
}
