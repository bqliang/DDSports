package ui;

import client.Commit;
import model.Agreement;
import model.Transfer;
import model.User;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;

/**
 * @author bqliang
 */
public class CertificationHandle extends JFrame implements Agreement {

	private JPanel contentPane;
	private JLabel statusShow;
	private JLabel idCardShow;
	private JLabel realNameShow;
	private JLabel idShow;
	private JLabel nameShow;
	private JLabel genderShow;
	private int userId;
	private JFrame jframe;
	private JFrame mySelf;
	private JButton rejectBtn;
	private JButton passBtn;
	private JButton dropCertificationBtn;

	public CertificationHandle(int userId, JFrame jframe) {
		this.userId = userId;
		this.jframe = jframe;
		jframe.setVisible(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		setBounds(100, 100, 584, 400);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(null);
		headPanel.setBackground(new Color(242, 142, 30));
		headPanel.setBounds(0, 0, 571, 56);
		contentPane.add(headPanel);
		
		JLabel headLabel = new JLabel("用户实名认证管理");
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headLabel.setForeground(Color.WHITE);
		headLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
		headLabel.setBounds(188, 4, 231, 47);
		headPanel.add(headLabel);
		
		JButton iconBtn = new JButton("");
		iconBtn.setBounds(140, 7, 39, 42);
		headPanel.add(iconBtn);
		iconBtn.setIcon(new ImageIcon("C:\\Users\\bqliang\\Desktop\\baseline_verified_user_white_24dp.png"));
		iconBtn.setBackground(new Color(242, 142, 30));
		iconBtn.setFocusPainted(false);
		iconBtn.setBorderPainted(false);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBounds(0, 282, 571, 49);
		contentPane.add(btnPanel);
		btnPanel.setLayout(null);
		
		rejectBtn = new JButton("拒绝");
		rejectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setStatus("审核失败");
			}
		});
		rejectBtn.setForeground(Color.WHITE);
		rejectBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
		rejectBtn.setFocusPainted(false);
		rejectBtn.setBorderPainted(false);
		rejectBtn.setBackground(new Color(198, 55, 51));
		rejectBtn.setBounds(88, 3, 153, 43);
		btnPanel.add(rejectBtn);
		
		passBtn = new JButton("通过");
		passBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setStatus("审核通过");
			}
		});
		passBtn.setForeground(Color.WHITE);
		passBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
		passBtn.setFocusPainted(false);
		passBtn.setBorderPainted(false);
		passBtn.setBackground(new Color(98, 173, 81));
		passBtn.setBounds(329, 3, 153, 43);
		btnPanel.add(passBtn);
		
		dropCertificationBtn = new JButton("撤销认证");
		dropCertificationBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setStatus("审核失败");
			}
		});
		dropCertificationBtn.setVisible(false);
		dropCertificationBtn.setForeground(Color.WHITE);
		dropCertificationBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
		dropCertificationBtn.setFocusPainted(false);
		dropCertificationBtn.setBorderPainted(false);
		dropCertificationBtn.setBackground(new Color(198, 55, 51));
		dropCertificationBtn.setBounds(209, 3, 153, 43);
		btnPanel.add(dropCertificationBtn);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setBounds(25, 85, 521, 177);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		JLabel idLabel = new JLabel("ID：");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setFont(new Font("等线", Font.PLAIN, 16));
		idLabel.setBounds(31, 19, 64, 17);
		infoPanel.add(idLabel);
		
		JLabel realNameLabel = new JLabel("真实姓名：");
		realNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		realNameLabel.setFont(new Font("等线", Font.PLAIN, 16));
		realNameLabel.setBounds(10, 55, 85, 17);
		infoPanel.add(realNameLabel);
		
		JLabel idCardLabel = new JLabel("身份证号：");
		idCardLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idCardLabel.setFont(new Font("等线", Font.PLAIN, 16));
		idCardLabel.setBounds(10, 91, 85, 17);
		infoPanel.add(idCardLabel);
		
		JLabel statusLabel = new JLabel("认证状态：");
		statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusLabel.setFont(new Font("等线", Font.PLAIN, 16));
		statusLabel.setBounds(10, 127, 85, 17);
		infoPanel.add(statusLabel);
		
		JLabel nameLabel = new JLabel("用户名：");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("等线", Font.PLAIN, 16));
		nameLabel.setBounds(178, 19, 64, 17);
		infoPanel.add(nameLabel);
		
		JLabel genderLabel = new JLabel("性别：");
		genderLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		genderLabel.setFont(new Font("等线", Font.PLAIN, 16));
		genderLabel.setBounds(353, 19, 64, 17);
		infoPanel.add(genderLabel);
		
		statusShow = new JLabel("");
		statusShow.setHorizontalAlignment(SwingConstants.LEFT);
		statusShow.setFont(new Font("等线", Font.PLAIN, 16));
		statusShow.setBounds(94, 127, 105, 17);
		infoPanel.add(statusShow);
		
		idCardShow = new JLabel("");
		idCardShow.setHorizontalAlignment(SwingConstants.LEFT);
		idCardShow.setFont(new Font("等线", Font.PLAIN, 16));
		idCardShow.setBounds(94, 91, 192, 17);
		infoPanel.add(idCardShow);
		
		realNameShow = new JLabel("");
		realNameShow.setHorizontalAlignment(SwingConstants.LEFT);
		realNameShow.setFont(new Font("等线", Font.PLAIN, 16));
		realNameShow.setBounds(94, 57, 138, 17);
		infoPanel.add(realNameShow);
		
		idShow = new JLabel("");
		idShow.setHorizontalAlignment(SwingConstants.LEFT);
		idShow.setFont(new Font("等线", Font.PLAIN, 16));
		idShow.setBounds(94, 19, 74, 17);
		infoPanel.add(idShow);
		
		nameShow = new JLabel("");
		nameShow.setHorizontalAlignment(SwingConstants.LEFT);
		nameShow.setFont(new Font("等线", Font.PLAIN, 16));
		nameShow.setBounds(241, 19, 115, 17);
		infoPanel.add(nameShow);
		
		genderShow = new JLabel("");
		genderShow.setHorizontalAlignment(SwingConstants.LEFT);
		genderShow.setFont(new Font("等线", Font.PLAIN, 16));
		genderShow.setBounds(415, 19, 85, 17);
		infoPanel.add(genderShow);
		
		JLabel seeDetails = new JLabel("查看用户详细资料");
		seeDetails.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				seeDetails.setForeground(new Color(30, 144, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				seeDetails.setForeground(new Color(242, 142, 30));
			}
		});
		seeDetails.setHorizontalAlignment(SwingConstants.CENTER);
		seeDetails.setBounds(383, 153, 128, 16);
		infoPanel.add(seeDetails);
		seeDetails.setForeground(new Color(242, 142, 30));
		seeDetails.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		init();
		setVisible(true);
	}

	private void init(){
		Transfer transfer = new Transfer();
		Transfer feedback = new Transfer();
		transfer.setCommand(VIEW_USER);
		transfer.setUser(
				new User(userId)
		);
		Commit.set(transfer);
		try {
			feedback = Commit.start();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		setData(feedback.getUser());
	}

	private void setData(User user){
		String status = user.getCertificate();
		idShow.setText(String.valueOf(user.getId()));
		nameShow.setText(user.getName());
		genderShow.setText(user.getGender());
		realNameShow.setText(user.getRealName());
		idCardShow.setText(user.getIdCard());
		statusShow.setText(status);
		if (status.equals("审核通过")){
			rejectBtn.setVisible(false);
			passBtn.setVisible(false);
			dropCertificationBtn.setVisible(true);
		}else if (status.equals("未审核")){
			rejectBtn.setVisible(false);
			passBtn.setVisible(false);
			dropCertificationBtn.setVisible(false);
		}else if (status.equals("审核失败")){
			rejectBtn.setVisible(true);
			passBtn.setVisible(true);
			dropCertificationBtn.setVisible(false);
		}
	}

	private void closeWindow(){
		jframe.setVisible(true);
		this.dispose();
	}

	private void refresh(){
		init();
	}

	private void setStatus(String status) {
		Transfer transfer = new Transfer();
		Transfer feedback = null;
		int result;
		transfer.setCommand(SET_CERTIFICATE_STATUS);
		transfer.setUser(
				new User(userId, status)
		);
		Commit.set(transfer);
		try {
			feedback = Commit.start();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		result = feedback.getResult();
		if (result == SUCCESS){
			JOptionPane.showMessageDialog(null, "修改认证状态成功！");
			init();
		}else if (result == SET_CERTIFICATE_STATUS_FAIL){
			JOptionPane.showMessageDialog(null, "很抱歉出现未知错误", "修改认证状态失败", JOptionPane.ERROR_MESSAGE);
		}

	}
}
