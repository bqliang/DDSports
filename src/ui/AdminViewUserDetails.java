package ui;

import client.Commit;
import model.Agreement;
import model.Transfer;
import model.User;

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
public class AdminViewUserDetails extends JFrame implements Agreement {

	private JPanel contentPane;
	private JLabel idShow;
	private JComboBox contactComboBox;
	private JComboBox genderComboBox;
	private JLabel certificateStatusShow;
	private JTextField nameInput;
	private JTextField passwordInput;
	private JTextField contactInput;
	private JTextField emailInput;
	private JTextField idCardInput;
	private JTextField realNameInput;
	private JFrame jframe;
	private JFrame mySelf;
	private int userId;


	public AdminViewUserDetails(int userId, JFrame jframe) {
		this.userId = userId;
		jframe.setVisible(false);
		this.jframe = jframe;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		setBounds(100, 100, 618, 440);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBounds(244, 334, 344, 45);
		contentPane.add(btnPanel);
		btnPanel.setLayout(null);
		
		JButton deleteBtn = new JButton("删除用户");
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "确定删除该用户？", "请谨慎操作", 0);
				if (choice != JOptionPane.YES_OPTION){
					return;
				}
				Transfer transfer = new Transfer();
				Transfer feedback = null;
				int result;
				transfer.setCommand(DELETE_USER);
				transfer.setUser(
						new User(userId)
				);
				Commit.set(transfer);
				try {
					feedback = Commit.start();
				} catch (IOException | ClassNotFoundException ioException) {
					ioException.printStackTrace();
				}
				result = feedback.getResult();
				if (result == SUCCESS){
					JOptionPane.showMessageDialog(null, "删除用户成功！");
					closeWindow();
				}else if (result == DELETE_USER_FAIL){
					JOptionPane.showMessageDialog(null, "很抱歉出现未知错误", "删除用户失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteBtn.setForeground(Color.WHITE);
		deleteBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
		deleteBtn.setFocusPainted(false);
		deleteBtn.setBorderPainted(false);
		deleteBtn.setBackground(new Color(198, 55, 51));
		deleteBtn.setBounds(12, 1, 153, 43);
		btnPanel.add(deleteBtn);
		
		JButton updateBtn = new JButton("更新信息");
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Transfer transfer = new Transfer();
				Transfer feedback = null;
				int result;
				transfer.setCommand(ADMIN_UPDATE_USER_PROFILE);
				transfer.setUser(
						new User(
								userId,
								nameInput.getText(),
								passwordInput.getText(),
								genderComboBox.getSelectedItem().toString(),
								contactComboBox.getSelectedItem().toString() + "：" + contactInput.getText(),
								emailInput.getText(),
								realNameInput.getText(),
								idCardInput.getText(),
								certificateStatusShow.getText()
						)
				);
				Commit.set(transfer);
				try {
					feedback = Commit.start();
				} catch (IOException | ClassNotFoundException ioException) {
					ioException.printStackTrace();
				}
				result = feedback.getResult();
				if (result == SUCCESS){
					init();
					JOptionPane.showMessageDialog(null, "更新信息成功！");
				}else if (result == ADMIN_UPDATE_USER_PROFILE_FAIL){
					JOptionPane.showMessageDialog(null, "很抱歉出现未知错误", "更新信息失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateBtn.setForeground(Color.WHITE);
		updateBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
		updateBtn.setFocusPainted(false);
		updateBtn.setBorderPainted(false);
		updateBtn.setBackground(new Color(98, 173, 81));
		updateBtn.setBounds(177, 1, 153, 43);
		btnPanel.add(updateBtn);
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(null);
		headPanel.setBackground(new Color(242, 142, 30));
		headPanel.setBounds(0, 0, 604, 56);
		contentPane.add(headPanel);
		
		JLabel headLabel = new JLabel("-> 滴滴一下，马上运动");
		headLabel.setHorizontalAlignment(SwingConstants.LEFT);
		headLabel.setForeground(Color.WHITE);
		headLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
		headLabel.setBounds(20, 4, 301, 47);
		headPanel.add(headLabel);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setBounds(0, 68, 588, 256);
		contentPane.add(infoPanel);
		
		JLabel idLabel = new JLabel("ID：");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setFont(new Font("等线", Font.PLAIN, 16));
		idLabel.setBounds(75, 21, 64, 17);
		infoPanel.add(idLabel);
		
		JLabel nameLabel = new JLabel("用户名：");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("等线", Font.PLAIN, 16));
		nameLabel.setBounds(75, 59, 64, 17);
		infoPanel.add(nameLabel);
		
		JLabel contactLabel = new JLabel("联系方式：");
		contactLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		contactLabel.setFont(new Font("等线", Font.PLAIN, 16));
		contactLabel.setBounds(52, 173, 87, 17);
		infoPanel.add(contactLabel);
		
		JLabel passwordLabel = new JLabel("密码：");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setFont(new Font("等线", Font.PLAIN, 16));
		passwordLabel.setBounds(75, 97, 64, 17);
		infoPanel.add(passwordLabel);
		
		JLabel genderLabel = new JLabel("性别：");
		genderLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		genderLabel.setFont(new Font("等线", Font.PLAIN, 16));
		genderLabel.setBounds(75, 135, 64, 17);
		infoPanel.add(genderLabel);
		
		idShow = new JLabel("");
		idShow.setHorizontalAlignment(SwingConstants.LEFT);
		idShow.setFont(new Font("等线", Font.PLAIN, 16));
		idShow.setBounds(142, 21, 139, 17);
		infoPanel.add(idShow);
		
		nameInput = new JTextField();
		nameInput.setFont(new Font("等线", Font.PLAIN, 16));
		nameInput.setBounds(142, 52, 125, 30);
		infoPanel.add(nameInput);
		nameInput.setColumns(10);
		
		passwordInput = new JTextField();
		passwordInput.setFont(new Font("等线", Font.PLAIN, 16));
		passwordInput.setColumns(10);
		passwordInput.setBounds(142, 90, 125, 30);
		infoPanel.add(passwordInput);
		
		genderComboBox = new JComboBox();
		genderComboBox.setModel(new DefaultComboBoxModel(new String[] {"男", "女", "不便透露"}));
		genderComboBox.setFont(new Font("等线", Font.PLAIN, 16));
		genderComboBox.setBounds(142, 133, 64, 23);
		infoPanel.add(genderComboBox);
		
		contactComboBox = new JComboBox();
		contactComboBox.setModel(new DefaultComboBoxModel(new String[] {"微信", "QQ", "手机"}));
		contactComboBox.setFont(new Font("等线", Font.PLAIN, 16));
		contactComboBox.setBounds(142, 170, 64, 23);
		infoPanel.add(contactComboBox);
		
		contactInput = new JTextField();
		contactInput.setFont(new Font("等线", Font.PLAIN, 16));
		contactInput.setColumns(10);
		contactInput.setBounds(221, 166, 125, 30);
		infoPanel.add(contactInput);
		
		JLabel emailLabel = new JLabel("电子邮箱：");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("等线", Font.PLAIN, 16));
		emailLabel.setBounds(52, 211, 87, 17);
		infoPanel.add(emailLabel);
		
		emailInput = new JTextField();
		emailInput.setFont(new Font("等线", Font.PLAIN, 16));
		emailInput.setColumns(10);
		emailInput.setBounds(142, 204, 169, 30);
		infoPanel.add(emailInput);
		
		JLabel realNameLabel = new JLabel("真实姓名：");
		realNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		realNameLabel.setFont(new Font("等线", Font.PLAIN, 16));
		realNameLabel.setBounds(284, 59, 87, 17);
		infoPanel.add(realNameLabel);
		
		JLabel idCardLabel = new JLabel("身份证号：");
		idCardLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idCardLabel.setFont(new Font("等线", Font.PLAIN, 16));
		idCardLabel.setBounds(284, 97, 87, 17);
		infoPanel.add(idCardLabel);
		
		idCardInput = new JTextField();
		idCardInput.setHorizontalAlignment(SwingConstants.LEFT);
		idCardInput.setFont(new Font("等线", Font.PLAIN, 16));
		idCardInput.setColumns(10);
		idCardInput.setBounds(374, 90, 175, 30);
		infoPanel.add(idCardInput);
		
		realNameInput = new JTextField();
		realNameInput.setFont(new Font("等线", Font.PLAIN, 16));
		realNameInput.setColumns(10);
		realNameInput.setBounds(374, 52, 125, 30);
		infoPanel.add(realNameInput);

		JLabel certificateStatusLabel = new JLabel("认证状态：");
		certificateStatusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		certificateStatusLabel.setFont(new Font("等线", Font.PLAIN, 16));
		certificateStatusLabel.setBounds(284, 135, 87, 17);
		infoPanel.add(certificateStatusLabel);
		
		certificateStatusShow = new JLabel("");
		certificateStatusShow.setHorizontalAlignment(SwingConstants.LEFT);
		certificateStatusShow.setFont(new Font("等线", Font.PLAIN, 16));
		certificateStatusShow.setBounds(374, 135, 139, 17);
		infoPanel.add(certificateStatusShow);
		init();
		setVisible(true);
		mySelf = this;
	}

	private void init(){
		Transfer transfer = new Transfer();
		Transfer feedback = null;
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
		String gender = user.getGender();
		String contact = user.getContact();
		idShow.setText(String.valueOf(user.getId()));
		nameInput.setText(user.getName());
		passwordInput.setText(user.getPw());

		if (gender.contains("男")){
			genderComboBox.setSelectedIndex(0);
		}else if (gender.contains("女")){
			genderComboBox.setSelectedIndex(1);
		}else {
			genderComboBox.setSelectedIndex(2);
		}

		if (contact.contains("微信")){
			contactComboBox.setSelectedIndex(0);
		}else if (contact.contains("QQ")){
			contactComboBox.setSelectedIndex(1);
		}else {
			contactComboBox.setSelectedIndex(2);
		}
		contactInput.setText(contact.split("：")[1]);

		emailInput.setText(user.getEmail());
		realNameInput.setText(user.getRealName());
		idCardInput.setText(user.getIdCard());
		certificateStatusShow.setText(user.getCertificate());
	}

	private void closeWindow(){
		jframe.setVisible(true);
		this.dispose();
	}

	private void close(){
		this.dispose();
	}
}
