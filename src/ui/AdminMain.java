package ui;

import client.Tools;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMain extends JFrame {

	private JPanel contentPane;
	private AdminMain mySelf;

	public AdminMain() {
		setTitle("滴滴运动 - 管理端");
		setIconImage(Tools.getImage("ddsports-icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 194);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(5, 43, 641, 107);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 3, 5, 5));
		
		JButton userManageBtn = new JButton("用户管理");
		userManageBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				userManageBtn.setBackground(new Color(52, 111, 164));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				userManageBtn.setBackground(new Color(242, 142, 30));
			}
		});
		userManageBtn.setForeground(Color.WHITE);
		userManageBtn.setBackground(new Color(242, 142, 30));
		userManageBtn.setBorderPainted(false);
		userManageBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
		userManageBtn.setFocusPainted(false);
		userManageBtn.setIcon(new ImageIcon(Tools.getImage("icons/admin_groups.png")));
		panel.add(userManageBtn);
		
		JButton activityManageBtn = new JButton("活动管理");
		activityManageBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				activityManageBtn.setBackground(new Color(52, 111, 164));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				activityManageBtn.setBackground(new Color(242, 142, 30));
			}
		});
		activityManageBtn.setForeground(Color.WHITE);
		activityManageBtn.setBackground(new Color(242, 142, 30));
		activityManageBtn.setBorderPainted(false);
		activityManageBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
		activityManageBtn.setFocusPainted(false);
		activityManageBtn.setIcon(new ImageIcon(Tools.getImage("icons/admin_basketball.png")));
		panel.add(activityManageBtn);
		
		JButton certificationBtn = new JButton("实名审核");
		certificationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				certificationBtn.setBackground(new Color(52, 111, 164));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				certificationBtn.setBackground(new Color(242, 142, 30));
			}
		});
		certificationBtn.setForeground(Color.WHITE);
		certificationBtn.setBackground(new Color(242, 142, 30));
		certificationBtn.setBorderPainted(false);
		certificationBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
		certificationBtn.setFocusPainted(false);
		certificationBtn.setIcon(new ImageIcon(Tools.getImage("icons/admin_verified.png")));
		panel.add(certificationBtn);
		
		JLabel welcomeText = new JLabel("Hello, 斌强，别来无恙啊！");
		welcomeText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				welcomeText.setForeground(new Color(30, 144, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				welcomeText.setForeground(new Color(242, 142, 30));
			}
		});
		welcomeText.setHorizontalAlignment(SwingConstants.LEFT);
		welcomeText.setForeground(new Color(242, 142, 30));
		welcomeText.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		welcomeText.setBounds(10, 10, 214, 23);
		contentPane.add(welcomeText);
		setVisible(true);
		mySelf = this;
	}

}
