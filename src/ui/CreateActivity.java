package ui;

import client.Commit;
import client.Logined;
import client.Tools;
import model.Activity;
import model.Agreement;
import model.Transfer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.IOException;
import java.sql.Timestamp;
import javax.swing.border.LineBorder;

/**
 * @author bqliang
 */
public class CreateActivity extends JFrame implements Agreement {

	private JPanel contentPane;
	private JTextField activityNameInput;
	private JTextField recruitInput;
	private JTextField placeInput;
	private JTextField durationInput;
	private JTextField minuteInput;
	JComboBox yearComboBox;
	JComboBox monthComboBox;
	JComboBox dayComboBox;
	JComboBox hourComboBox;
	private JFrame jframe;

	public CreateActivity(JFrame jframe) {
		this.jframe = jframe;
		jframe.setVisible(false);
		setTitle("滴滴一下 马上运动");
		setIconImage(Tools.getImage("icons/flag.png"));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		setBounds(100, 100, 651, 532);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(null);
		headPanel.setBackground(new Color(242, 142, 30));
		headPanel.setBounds(0, 0, 637, 56);
		contentPane.add(headPanel);
		
		JLabel headLabel = new JLabel("-> 发起运动");
		headLabel.setBounds(20, 4, 261, 47);
		headPanel.add(headLabel);
		headLabel.setHorizontalAlignment(SwingConstants.LEFT);
		headLabel.setForeground(Color.WHITE);
		headLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
		
		JLabel activityNameLabel = new JLabel("活动名：");
		activityNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		activityNameLabel.setFont(new Font("等线", Font.PLAIN, 16));
		activityNameLabel.setBounds(54, 103, 64, 17);
		contentPane.add(activityNameLabel);
		
		activityNameInput = new JTextField();
		activityNameInput.setFont(new Font("等线", Font.PLAIN, 16));
		activityNameInput.setEnabled(true);
		activityNameInput.setColumns(10);
		activityNameInput.setBounds(137, 96, 230, 32);
		contentPane.add(activityNameInput);
		
		JLabel timeLabel = new JLabel("时间：");
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setFont(new Font("等线", Font.PLAIN, 16));
		timeLabel.setBounds(54, 161, 64, 17);
		contentPane.add(timeLabel);
		
		JLabel durationLabel = new JLabel("持续时间：");
		durationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		durationLabel.setFont(new Font("等线", Font.PLAIN, 16));
		durationLabel.setBounds(31, 209, 87, 17);
		contentPane.add(durationLabel);
		
		JLabel placeLabel = new JLabel("地点：");
		placeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		placeLabel.setFont(new Font("等线", Font.PLAIN, 16));
		placeLabel.setBounds(54, 262, 64, 17);
		contentPane.add(placeLabel);
		
		JLabel postscriptLabel = new JLabel("备注信息：");
		postscriptLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		postscriptLabel.setFont(new Font("等线", Font.PLAIN, 16));
		postscriptLabel.setBounds(31, 322, 87, 17);
		contentPane.add(postscriptLabel);
		
		JLabel recruitLabel = new JLabel("招募人数：");
		recruitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		recruitLabel.setFont(new Font("等线", Font.PLAIN, 16));
		recruitLabel.setBounds(31, 396, 87, 17);
		contentPane.add(recruitLabel);
		
		recruitInput = new JTextField();
		recruitInput.setFont(new Font("等线", Font.PLAIN, 16));
		recruitInput.setEnabled(true);
		recruitInput.setColumns(10);
		recruitInput.setBounds(137, 389, 70, 32);
		contentPane.add(recruitInput);
		
		placeInput = new JTextField();
		placeInput.setFont(new Font("等线", Font.PLAIN, 16));
		placeInput.setEnabled(true);
		placeInput.setColumns(10);
		placeInput.setBounds(137, 255, 257, 32);
		contentPane.add(placeInput);
		
		durationInput = new JTextField();
		durationInput.setFont(new Font("等线", Font.PLAIN, 16));
		durationInput.setEnabled(true);
		durationInput.setColumns(10);
		durationInput.setBounds(137, 202, 70, 32);
		contentPane.add(durationInput);
		
		JTextPane postscriptInput = new JTextPane();
		postscriptInput.setFont(new Font("等线", Font.PLAIN, 16));
		postscriptInput.setBorder(new LineBorder(new Color(0, 0, 0)));
		postscriptInput.setBounds(137, 311, 257, 56);
		contentPane.add(postscriptInput);
		
		JButton createButton = new JButton("发起运动");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Transfer transfer = new Transfer();
				Transfer feedback = null;
				String timeStr = yearComboBox.getSelectedItem().toString() + "-"
						+ monthComboBox.getSelectedItem().toString() + "-"
						+ dayComboBox.getSelectedItem().toString() + " "
						+ hourComboBox.getSelectedItem().toString() + ":"
						+ minuteInput.getText() + ":"
						+ "00";
				Timestamp timestamp = Timestamp.valueOf(timeStr);
				transfer.setActivity(
						new Activity(
								activityNameInput.getText(),
								Logined.getUser().getName(),
								timestamp,
								Float.parseFloat(durationInput.getText()),
								placeInput.getText(),
								postscriptInput.getText(),
								Integer.parseInt(recruitInput.getText())
						)
				);
				transfer.setCommand(CREATE_ACTIVITY);
				Commit.set(transfer);
				try {
					feedback = Commit.start();
				} catch (IOException | ClassNotFoundException ioException) {
					ioException.printStackTrace();
				}
				int result = feedback.getResult();
				if (result == SUCCESS){
					JOptionPane.showMessageDialog(null, "成功发起运动！");
					closeWindow();
				}else if (result == CREATE_ACTIVITY_FAIL){
					JOptionPane.showMessageDialog(null, "很抱歉出现未知错误", "发起运动失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		createButton.setForeground(Color.WHITE);
		createButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		createButton.setFocusPainted(false);
		createButton.setBorderPainted(false);
		createButton.setBackground(new Color(242, 142, 30));
		createButton.setBounds(428, 430, 153, 43);
		contentPane.add(createButton);
		
		JLabel help = new JLabel("帮助");
		help.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 点击帮助
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				help.setForeground(new Color(30, 144, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				help.setForeground(new Color(242, 142, 30));
			}
		});
		help.setHorizontalAlignment(SwingConstants.LEFT);
		help.setForeground(new Color(242, 142, 30));
		help.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		help.setBounds(31, 445, 38, 16);
		contentPane.add(help);
		
		JLabel pact = new JLabel("滴滴运动公约");
		pact.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 点击公约
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				pact.setForeground(new Color(30, 144, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				pact.setForeground(new Color(242, 142, 30));
			}
		});
		pact.setHorizontalAlignment(SwingConstants.LEFT);
		pact.setForeground(new Color(242, 142, 30));
		pact.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		pact.setBounds(79, 445, 94, 16);
		contentPane.add(pact);
		
		JLabel yearLabel = new JLabel("年");
		yearLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yearLabel.setFont(new Font("等线", Font.PLAIN, 16));
		yearLabel.setBounds(209, 161, 28, 17);
		contentPane.add(yearLabel);
		
		JLabel monthLabel = new JLabel("月");
		monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		monthLabel.setFont(new Font("等线", Font.PLAIN, 16));
		monthLabel.setBounds(298, 161, 16, 17);
		contentPane.add(monthLabel);
		
		JLabel dayLabel = new JLabel("日");
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setFont(new Font("等线", Font.PLAIN, 16));
		dayLabel.setBounds(366, 161, 38, 17);
		contentPane.add(dayLabel);
		
		JLabel hourLabel = new JLabel("时");
		hourLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hourLabel.setFont(new Font("等线", Font.PLAIN, 16));
		hourLabel.setBounds(457, 161, 16, 17);
		contentPane.add(hourLabel);
		
		JLabel minuteLabel = new JLabel("分");
		minuteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		minuteLabel.setFont(new Font("等线", Font.PLAIN, 16));
		minuteLabel.setBounds(527, 161, 28, 17);
		contentPane.add(minuteLabel);
		
		yearComboBox = new JComboBox();
		yearComboBox.setFont(new Font("等线", Font.PLAIN, 16));
		yearComboBox.setModel(new DefaultComboBoxModel(new String[] {"2020", "2021", "2022"}));
		yearComboBox.setBounds(137, 159, 70, 23);
		contentPane.add(yearComboBox);
		
		monthComboBox = new JComboBox();
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		monthComboBox.setFont(new Font("等线", Font.PLAIN, 16));
		monthComboBox.setBounds(247, 158, 43, 23);
		contentPane.add(monthComboBox);
		
		dayComboBox = new JComboBox();
		dayComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayComboBox.setFont(new Font("等线", Font.PLAIN, 16));
		dayComboBox.setBounds(324, 158, 43, 23);
		contentPane.add(dayComboBox);
		
		hourComboBox = new JComboBox();
		hourComboBox.setModel(new DefaultComboBoxModel(new String[] {"05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		hourComboBox.setFont(new Font("等线", Font.PLAIN, 16));
		hourComboBox.setBounds(404, 158, 43, 23);
		contentPane.add(hourComboBox);
		
		minuteInput = new JTextField();
		minuteInput.setFont(new Font("等线", Font.PLAIN, 16));
		minuteInput.setEnabled(true);
		minuteInput.setColumns(10);
		minuteInput.setBounds(483, 154, 38, 32);
		contentPane.add(minuteInput);
		
		JLabel durationTrips = new JLabel("精确到小数点后一位（如0.5）");
		durationTrips.setHorizontalAlignment(SwingConstants.CENTER);
		durationTrips.setForeground(Color.GRAY);
		durationTrips.setFont(new Font("等线", Font.PLAIN, 14));
		durationTrips.setBounds(264, 210, 197, 23);
		contentPane.add(durationTrips);
		
		JLabel durationUnitLabel = new JLabel("小时");
		durationUnitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		durationUnitLabel.setFont(new Font("等线", Font.PLAIN, 16));
		durationUnitLabel.setBounds(209, 211, 45, 17);
		contentPane.add(durationUnitLabel);
		setVisible(true);
	}

	private void initUI(){

	}

	private void closeWindow(){
		jframe.setVisible(true);
		this.dispose();
	}
}
