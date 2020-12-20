package ui;

import client.Commit;
import client.Logined;
import client.Tools;
import model.Agreement;
import model.Transfer;
import model.User;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * @author bqliang
 */
public class ResetPwByPw extends JFrame implements Agreement {

	private JPanel contentPane;
	private JPasswordField newPwInput;
	private JPasswordField oldPwInput;
	private JPasswordField pwAgainInput;
	private JButton resetPwBtn;
	private JLabel passwordTrips;
	private JFrame jframe;

	public ResetPwByPw(JFrame jframe) {
		this.jframe = jframe;
		jframe.setVisible(false);
		setTitle("更改密码");
		setIconImage(Tools.getImage("icons/security.png"));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		setBounds(100, 100, 510, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBackground(Color.WHITE);
		panel2.setBounds(67, 20, 408, 166);
		contentPane.add(panel2);
		
		JLabel oldPwLabel = new JLabel("原密码:");
		oldPwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		oldPwLabel.setFont(new Font("等线", Font.PLAIN, 16));
		oldPwLabel.setBounds(20, 1, 60, 32);
		panel2.add(oldPwLabel);
		
		JLabel newPwLabel = new JLabel("新密码:");
		newPwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		newPwLabel.setFont(new Font("等线", Font.PLAIN, 16));
		newPwLabel.setBounds(0, 54, 80, 32);
		panel2.add(newPwLabel);
		
		newPwInput = new JPasswordField();
		newPwInput.setFont(new Font("等线", Font.PLAIN, 16));
		newPwInput.setBounds(87, 54, 189, 33);
		panel2.add(newPwInput);
		
		oldPwInput = new JPasswordField();
		oldPwInput.setFont(new Font("等线", Font.PLAIN, 16));
		oldPwInput.setBounds(87, 1, 189, 33);
		panel2.add(oldPwInput);
		
		pwAgainInput = new JPasswordField();
		pwAgainInput.setFont(new Font("等线", Font.PLAIN, 16));
		pwAgainInput.setBounds(87, 123, 189, 33);
		panel2.add(pwAgainInput);
		
		JLabel pwAgainLabel = new JLabel("确认密码:");
		pwAgainLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwAgainLabel.setFont(new Font("等线", Font.PLAIN, 16));
		pwAgainLabel.setBounds(0, 123, 80, 32);
		panel2.add(pwAgainLabel);
		
		passwordTrips = new JLabel("8-10个字符，必须包含大小写字母与数字，可以使用特殊字符");
		passwordTrips.setFont(new Font("等线", Font.PLAIN, 14));
		passwordTrips.setForeground(Color.GRAY);
		passwordTrips.setBounds(17, 98, 389, 15);
		panel2.add(passwordTrips);
		
		resetPwBtn = new JButton("重设密码");
		resetPwBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				user.setId(Logined.getUser().getId());
				user.setPw(new String(oldPwInput.getPassword()));
				Transfer transfer = new Transfer();
				Transfer feedback = null;
				transfer.setCommand(RESET_PASSWORD);
				transfer.setUser(user);
				transfer.setNewPassword(new String(newPwInput.getPassword()));
				Commit.set(transfer);
				try {
					feedback = Commit.start();
				} catch (IOException | ClassNotFoundException ioException) {
					ioException.printStackTrace();
				}
				int result = feedback.getResult();
				if (result == SUCCESS){
					JOptionPane.showMessageDialog(null, "更改密码成功！");
					closeWindow();
				}else if (result == PASSWORD_ERROR){
					JOptionPane.showMessageDialog(null, "请检查后重试，若忘记原密码可通过邮箱重设密码。", "原密码错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		resetPwBtn.setForeground(Color.WHITE);
		resetPwBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
		resetPwBtn.setFocusPainted(false);
		resetPwBtn.setBorderPainted(false);
		resetPwBtn.setBackground(new Color(242, 142, 30));
		resetPwBtn.setBounds(171, 196, 153, 43);
		contentPane.add(resetPwBtn);
		setVisible(true);
	}

	private void closeWindow(){
		dispose();
		jframe.setVisible(true);
	}
}
