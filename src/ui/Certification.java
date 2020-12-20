package ui;

import client.Commit;
import client.Logined;
import client.Tools;
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

public class Certification extends JFrame implements Agreement {

	private JPanel headPanel;
	private JLabel headLabel;
	private JPanel contentPane;
	private JTextField realNameInput;
	private JTextField idCardInput;
	private JFrame jframe;

	public Certification(JFrame jframe) {
		this.jframe = jframe;
		jframe.setVisible(false);
		setTitle("申请实名认证");
		setIconImage(Tools.getImage("icons/certification.png"));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 370);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(110, 95, 266, 110);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);

		JLabel realNameLabel = new JLabel("真实姓名：");
		realNameLabel.setBounds(0, 24, 80, 17);
		realNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		realNameLabel.setFont(new Font("等线", Font.PLAIN, 16));
		mainPanel.add(realNameLabel);

		realNameInput = new JTextField();
		realNameInput.setFont(new Font("等线", Font.PLAIN, 16));
		realNameInput.setEnabled(true);
		realNameInput.setColumns(10);
		realNameInput.setBounds(81, 17, 178, 32);
		mainPanel.add(realNameInput);

		JLabel idCardLabel = new JLabel("身份证号：");
		idCardLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idCardLabel.setFont(new Font("等线", Font.PLAIN, 16));
		idCardLabel.setBounds(0, 74, 80, 17);
		mainPanel.add(idCardLabel);

		idCardInput = new JTextField();
		idCardInput.setFont(new Font("等线", Font.PLAIN, 16));
		idCardInput.setEnabled(true);
		idCardInput.setColumns(10);
		idCardInput.setBounds(81, 67, 178, 32);
		mainPanel.add(idCardInput);

		headPanel = new JPanel();
		headPanel.setBackground(new Color(242, 142, 30));
		headPanel.setBounds(0, 0, 486, 56);
		contentPane.add(headPanel);
		headPanel.setLayout(null);

		headLabel = new JLabel("审核状态：审核中");
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headLabel.setForeground(Color.WHITE);
		headLabel.setBounds(102, 4, 281, 47);
		headLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
		headPanel.add(headLabel);

		JButton applyButton = new JButton("发起申请");
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Transfer transfer = new Transfer();
				Transfer feedback = new Transfer();
				User user = new User();
				user.setId(Logined.getUser().getId());
				user.setRealName(realNameInput.getText());
				user.setIdCard(idCardInput.getText());
				transfer.setUser(user);
				transfer.setCommand(AUTHENTICATE);
				Commit.set(transfer);
				try {
					feedback = Commit.start();
				} catch (IOException | ClassNotFoundException ioException) {
					ioException.printStackTrace();
				}
				int result = feedback.getResult();
				if (result == SUCCESS){
					JOptionPane.showMessageDialog(null, "您已成功申请实名认证！");
					User userX= Logined.getUser();
					userX.setRealName(realNameInput.getText());
					userX.setIdCard(idCardInput.getText());
					userX.setCertificate("审核中");
					Logined.setUser(userX);
					initUI();
				}else if (result == APPLY_AUTHENTICATE_FAIL){
					JOptionPane.showMessageDialog(null, "很抱歉出现未知错误", "申请实名认证失败", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		applyButton.setForeground(Color.WHITE);
		applyButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		applyButton.setFocusPainted(false);
		applyButton.setBorderPainted(false);
		applyButton.setBackground(new Color(242, 142, 30));
		applyButton.setBounds(299, 272, 153, 43);
		contentPane.add(applyButton);

		JLabel trips = new JLabel("请仔细核对所提交的信息，多次恶意提交错误信息账号将会被封禁");
		trips.setHorizontalAlignment(SwingConstants.LEFT);
		trips.setForeground(Color.GRAY);
		trips.setFont(new Font("等线", Font.PLAIN, 14));
		trips.setBounds(34, 226, 418, 17);
		contentPane.add(trips);

		JLabel terms = new JLabel("条款");
		terms.setHorizontalAlignment(SwingConstants.LEFT);
		terms.setForeground(new Color(242, 142, 30));
		terms.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		terms.setBounds(130, 292, 36, 16);
		contentPane.add(terms);

		JLabel privacy = new JLabel("隐私权");
		privacy.setHorizontalAlignment(SwingConstants.LEFT);
		privacy.setForeground(new Color(242, 142, 30));
		privacy.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		privacy.setBounds(69, 292, 51, 16);
		contentPane.add(privacy);

		JLabel help = new JLabel("帮助");
		help.setHorizontalAlignment(SwingConstants.LEFT);
		help.setForeground(new Color(242, 142, 30));
		help.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		help.setBounds(21, 292, 38, 16);
		contentPane.add(help);
		initUI();
		setVisible(true);
	}

	private void initUI(){
		User user = Logined.getUser();
		String certificate = user.getCertificate();
		if (certificate.equals("未审核")){
			headLabel.setText("审核状态：未审核");
			headPanel.setBackground(new Color(198, 55, 51));
		}else if (certificate.equals("审核通过")){
			headLabel.setText("审核状态：审核通过");
			headPanel.setBackground(new Color(98, 173, 81));
			realNameInput.setText(user.getRealName());
			idCardInput.setText(user.getIdCard());
		}else if (certificate.equals("审核中")){
			headLabel.setText("审核状态：审核中");
			headPanel.setBackground(new Color(242, 142, 30));
			realNameInput.setText(user.getRealName());
			idCardInput.setText(user.getIdCard());
		}else if (certificate.equals("审核失败")){
			headLabel.setText("审核状态：审核失败");
			headPanel.setBackground(new Color(198, 55, 51));
			realNameInput.setText(user.getRealName());
			idCardInput.setText(user.getIdCard());
		}
	}

	private void closeWindow(){
		dispose();
		jframe.setVisible(true);
	}
}

