package ui;

import client.Logined;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		JButton usersManageBtn = new JButton("用户管理");
		usersManageBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				usersManageBtn.setBackground(new Color(52, 111, 164));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				usersManageBtn.setBackground(new Color(242, 142, 30));
			}
		});
		usersManageBtn.setForeground(Color.WHITE);
		usersManageBtn.setBackground(new Color(242, 142, 30));
		usersManageBtn.setBorderPainted(false);
		usersManageBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
		usersManageBtn.setFocusPainted(false);
		usersManageBtn.setIcon(new ImageIcon(Tools.getImage("icons/admin_groups.png")));
		panel.add(usersManageBtn);
		
		JButton activitiesManageBtn = new JButton("活动管理");
		activitiesManageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ActivitiesManage(mySelf);
			}
		});
		activitiesManageBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				activitiesManageBtn.setBackground(new Color(52, 111, 164));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				activitiesManageBtn.setBackground(new Color(242, 142, 30));
			}
		});
		activitiesManageBtn.setForeground(Color.WHITE);
		activitiesManageBtn.setBackground(new Color(242, 142, 30));
		activitiesManageBtn.setBorderPainted(false);
		activitiesManageBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
		activitiesManageBtn.setFocusPainted(false);
		activitiesManageBtn.setIcon(new ImageIcon(Tools.getImage("icons/admin_basketball.png")));
		panel.add(activitiesManageBtn);
		
		JButton certificationManageBtn = new JButton("实名审核");
		certificationManageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CertificationManage(mySelf);
			}
		});
		certificationManageBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				certificationManageBtn.setBackground(new Color(52, 111, 164));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				certificationManageBtn.setBackground(new Color(242, 142, 30));
			}
		});
		certificationManageBtn.setForeground(Color.WHITE);
		certificationManageBtn.setBackground(new Color(242, 142, 30));
		certificationManageBtn.setBorderPainted(false);
		certificationManageBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
		certificationManageBtn.setFocusPainted(false);
		certificationManageBtn.setIcon(new ImageIcon(Tools.getImage("icons/admin_verified.png")));
		panel.add(certificationManageBtn);
		
		JLabel welcomeText = new JLabel("");
		welcomeText.setText(String.format("Hello, %s，别来无恙。", Logined.getAdmin().getName()));
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
