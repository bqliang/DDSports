package ui;

import client.Commit;
import model.Activity;
import model.Agreement;
import model.Transfer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.sound.midi.Track;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * @author bqliang
 */
public class AdminViewActivityDetails extends JFrame implements Agreement {

	private JPanel contentPane;
	private int activityId;
	private JFrame jframe;
	private JFrame mySelf;
	private JTextArea postscriptShow;
	private JTextArea jusersShow;
	private JTextArea cusersShow;
	private JLabel idShow;
	private JLabel nameShow;
	private JLabel timeShow;
	private JLabel placeShow;
	private JLabel sponsorShow;
	private JLabel numShow;

	public AdminViewActivityDetails(int activityId, JFrame jframe) {
		this.jframe = jframe;
		jframe.setVisible(false);
		this.activityId = activityId;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		setBounds(100, 100, 650, 505);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(null);
		headPanel.setBackground(new Color(242, 142, 30));
		headPanel.setBounds(0, 0, 636, 56);
		contentPane.add(headPanel);
		
		JLabel headLabel = new JLabel("-> 滴滴一下，马上运动");
		headLabel.setHorizontalAlignment(SwingConstants.LEFT);
		headLabel.setForeground(Color.WHITE);
		headLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
		headLabel.setBounds(20, 4, 301, 47);
		headPanel.add(headLabel);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setBounds(10, 90, 616, 281);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		JLabel idLabel = new JLabel("ID：");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setFont(new Font("等线", Font.PLAIN, 16));
		idLabel.setBounds(75, 21, 64, 17);
		infoPanel.add(idLabel);
		
		JLabel nameLabel = new JLabel("活动名：");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("等线", Font.PLAIN, 16));
		nameLabel.setBounds(75, 59, 64, 17);
		infoPanel.add(nameLabel);
		
		JLabel sponsorLabel = new JLabel("发起人：");
		sponsorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sponsorLabel.setFont(new Font("等线", Font.PLAIN, 16));
		sponsorLabel.setBounds(75, 173, 64, 17);
		infoPanel.add(sponsorLabel);
		
		JLabel timeLabel = new JLabel("时间：");
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setFont(new Font("等线", Font.PLAIN, 16));
		timeLabel.setBounds(75, 97, 64, 17);
		infoPanel.add(timeLabel);
		
		JLabel placeLabel = new JLabel("地点：");
		placeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		placeLabel.setFont(new Font("等线", Font.PLAIN, 16));
		placeLabel.setBounds(75, 135, 64, 17);
		infoPanel.add(placeLabel);
		
		JLabel postscriptLabel = new JLabel("备注信息：");
		postscriptLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		postscriptLabel.setFont(new Font("等线", Font.PLAIN, 16));
		postscriptLabel.setBounds(0, 211, 139, 17);
		infoPanel.add(postscriptLabel);
		
		JLabel jusersLabel = new JLabel("报名用户：");
		jusersLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		jusersLabel.setFont(new Font("等线", Font.PLAIN, 16));
		jusersLabel.setBounds(340, 21, 80, 17);
		infoPanel.add(jusersLabel);
		
		JLabel cusersLabel = new JLabel("打卡用户：");
		cusersLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cusersLabel.setFont(new Font("等线", Font.PLAIN, 16));
		cusersLabel.setBounds(340, 120, 80, 17);
		infoPanel.add(cusersLabel);
		
		postscriptShow = new JTextArea();
		postscriptShow.setEditable(false);
		postscriptShow.setFont(new Font("等线", Font.PLAIN, 16));
		postscriptShow.setLineWrap(true);
		postscriptShow.setBounds(142, 211, 464, 60);
		infoPanel.add(postscriptShow);
		
		jusersShow = new JTextArea();
		jusersShow.setEditable(false);
		jusersShow.setLineWrap(true);
		jusersShow.setFont(new Font("等线", Font.PLAIN, 16));
		jusersShow.setBounds(340, 48, 266, 53);
		infoPanel.add(jusersShow);
		
		cusersShow = new JTextArea();
		cusersShow.setEditable(false);
		cusersShow.setLineWrap(true);
		cusersShow.setFont(new Font("等线", Font.PLAIN, 16));
		cusersShow.setBounds(340, 147, 266, 53);
		infoPanel.add(cusersShow);
		
		idShow = new JLabel("");
		idShow.setHorizontalAlignment(SwingConstants.LEFT);
		idShow.setFont(new Font("等线", Font.PLAIN, 16));
		idShow.setBounds(142, 21, 139, 17);
		infoPanel.add(idShow);
		
		nameShow = new JLabel("");
		nameShow.setHorizontalAlignment(SwingConstants.LEFT);
		nameShow.setFont(new Font("等线", Font.PLAIN, 16));
		nameShow.setBounds(142, 59, 164, 17);
		infoPanel.add(nameShow);
		
		timeShow = new JLabel("");
		timeShow.setHorizontalAlignment(SwingConstants.LEFT);
		timeShow.setFont(new Font("等线", Font.PLAIN, 16));
		timeShow.setBounds(142, 99, 172, 17);
		infoPanel.add(timeShow);
		
		placeShow = new JLabel("");
		placeShow.setHorizontalAlignment(SwingConstants.LEFT);
		placeShow.setFont(new Font("等线", Font.PLAIN, 16));
		placeShow.setBounds(142, 135, 172, 17);
		infoPanel.add(placeShow);
		
		sponsorShow = new JLabel("");
		sponsorShow.setHorizontalAlignment(SwingConstants.LEFT);
		sponsorShow.setFont(new Font("等线", Font.PLAIN, 16));
		sponsorShow.setBounds(142, 168, 139, 26);
		infoPanel.add(sponsorShow);
		
		JLabel numLabel = new JLabel("招募 / 报名 / 打卡 / 状态");
		numLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel.setFont(new Font("等线", Font.PLAIN, 16));
		numLabel.setBounds(32, 397, 197, 17);
		contentPane.add(numLabel);
		
		numShow = new JLabel("");
		numShow.setHorizontalAlignment(SwingConstants.CENTER);
		numShow.setFont(new Font("等线", Font.PLAIN, 16));
		numShow.setBounds(32, 424, 197, 17);
		contentPane.add(numShow);
		
		JButton deleteBtn = new JButton("删除活动");
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Transfer transfer = new Transfer();
				Transfer feedback = null;
				int result;
				transfer.setCommand(DELETE_ACTIVITY);
				transfer.setActivity(
						new Activity(activityId)
				);
				Commit.set(transfer);
				try {
					feedback = Commit.start();
				} catch (IOException | ClassNotFoundException ioException) {
					ioException.printStackTrace();
				}
				result = feedback.getResult();
				if (result == SUCCESS){
					JOptionPane.showMessageDialog(null, "成功删除活动！");
					closeWindow();
				}else if (result == DELETE_ACTIVITY_FAIL){
					JOptionPane.showMessageDialog(null, "很抱歉出现未知错误", "删除活动失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteBtn.setForeground(Color.WHITE);
		deleteBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
		deleteBtn.setFocusPainted(false);
		deleteBtn.setBorderPainted(false);
		deleteBtn.setBackground(new Color(198, 55, 51));
		deleteBtn.setBounds(444, 397, 153, 43);
		contentPane.add(deleteBtn);
		init();
		setVisible(true);
		mySelf = this;
	}

	private void init(){
		Transfer transfer = new Transfer();
		Transfer feedback = null;
		transfer.setCommand(VIEW_ACTIVITY);
		transfer.setActivity(
				new Activity(activityId)
		);
		Commit.set(transfer);
		try {
			feedback = Commit.start();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		setData(feedback.getActivity());
	}

	private void setData(Activity activity){
		idShow.setText(String.valueOf(activity.getId()));
		nameShow.setText(activity.getName());
		timeShow.setText(activity.getTime().toString().substring(0,16));
		placeShow.setText(activity.getPlace());
		sponsorShow.setText(activity.getSponsor());
		postscriptShow.setText(activity.getPostscript());
		jusersShow.setText(activity.getjUsers());
		cusersShow.setText(activity.getcUsers());
		numShow.setText(activity.getRecruit() + " / " +
				activity.getJoin() + " / " +
				activity.getCheckIn() + " / " + activity.getStatus());
	}

	private void closeWindow(){
		jframe.setVisible(true);
		this.dispose();
	}
}
