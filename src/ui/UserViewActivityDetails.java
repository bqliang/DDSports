package ui;

import client.Commit;
import client.Logined;
import client.Tools;
import model.Activity;
import model.Agreement;
import model.Transfer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JSpinner;

/**
 * @author bqliang
 */
public class UserViewActivityDetails extends JFrame implements Agreement {

	private JPanel contentPane;
	private JButton button;
	private JTextArea jusersShow;
	private JTextArea postscriptShow;
	private JTextArea cusersShow;
	private JLabel activityNameShow;
	private JLabel sponsorShow;
	private JLabel statrTimeShow;
	private JLabel durationShow;
	private JLabel placeShow;
	private JLabel recruitShow;
	private JLabel joinShow;
	private JLabel checkinShow;
	private JLabel statusShow;
	private int activityId;
	private JFrame mySelf;
	private JFrame jframe;

	public UserViewActivityDetails(int activityId, JFrame jframe) throws IOException, ClassNotFoundException {
		this.activityId = activityId;
		this.jframe = jframe;
		jframe.setVisible(false);
		setIconImage(Tools.getImage("icons/details.png"));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		setBounds(100, 100, 585, 460);
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
		
		JLabel headLabel = new JLabel("-> 滴滴一下，马上运动");
		headLabel.setHorizontalAlignment(SwingConstants.LEFT);
		headLabel.setForeground(Color.WHITE);
		headLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
		headLabel.setBounds(20, 4, 301, 47);
		headPanel.add(headLabel);
		
		JLabel activityNameLabel = new JLabel("活动名：");
		activityNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		activityNameLabel.setFont(new Font("等线", Font.PLAIN, 16));
		activityNameLabel.setBounds(48, 78, 64, 17);
		contentPane.add(activityNameLabel);
		
		JLabel sponsorLabel = new JLabel("发起人：");
		sponsorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sponsorLabel.setFont(new Font("等线", Font.PLAIN, 16));
		sponsorLabel.setBounds(48, 105, 64, 17);
		contentPane.add(sponsorLabel);
		
		JLabel statrTimeLabel = new JLabel("开始时间：");
		statrTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statrTimeLabel.setFont(new Font("等线", Font.PLAIN, 16));
		statrTimeLabel.setBounds(24, 132, 88, 17);
		contentPane.add(statrTimeLabel);
		
		JLabel durationLabel = new JLabel("持续时间：");
		durationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		durationLabel.setFont(new Font("等线", Font.PLAIN, 16));
		durationLabel.setBounds(24, 159, 88, 17);
		contentPane.add(durationLabel);
		
		JLabel placeLabel = new JLabel("地点：");
		placeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		placeLabel.setFont(new Font("等线", Font.PLAIN, 16));
		placeLabel.setBounds(24, 186, 88, 17);
		contentPane.add(placeLabel);
		
		JLabel postscriptLabel = new JLabel("备注信息：");
		postscriptLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		postscriptLabel.setFont(new Font("等线", Font.PLAIN, 16));
		postscriptLabel.setBounds(24, 331, 88, 17);
		contentPane.add(postscriptLabel);
		
		JLabel recruitLabel = new JLabel("招募人数：");
		recruitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		recruitLabel.setFont(new Font("等线", Font.PLAIN, 16));
		recruitLabel.setBounds(24, 213, 88, 17);
		contentPane.add(recruitLabel);
		
		JLabel joinLabel = new JLabel("报名人数：");
		joinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		joinLabel.setFont(new Font("等线", Font.PLAIN, 16));
		joinLabel.setBounds(24, 240, 88, 17);
		contentPane.add(joinLabel);
		
		JLabel checkinLabel = new JLabel("打卡人数：");
		checkinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		checkinLabel.setFont(new Font("等线", Font.PLAIN, 16));
		checkinLabel.setBounds(24, 267, 88, 17);
		contentPane.add(checkinLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(30, 319, 511, 2);
		contentPane.add(separator);
		
		JLabel jusersLabel = new JLabel("报名用户：");
		jusersLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		jusersLabel.setFont(new Font("等线", Font.PLAIN, 16));
		jusersLabel.setBounds(316, 80, 88, 17);
		contentPane.add(jusersLabel);
		
		jusersShow = new JTextArea();
		jusersShow.setEditable(false);
		jusersShow.setLineWrap(true);
		jusersShow.setBounds(326, 105, 187, 62);
		contentPane.add(jusersShow);
		
		postscriptShow = new JTextArea();
		postscriptShow.setEditable(false);
		postscriptShow.setBounds(34, 357, 254, 56);
		contentPane.add(postscriptShow);
		
		button = new JButton("");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String btnStr = button.getText();
				if (btnStr.equals("删除")){

				}else if (btnStr.equals("加入活动")){

				}else if(btnStr.equals("退出活动")){

				}else if (btnStr.equals("打卡")){

				}
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("微软雅黑", Font.BOLD, 20));
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setBackground(new Color(242, 142, 30));
		button.setBounds(388, 359, 153, 43);
		contentPane.add(button);
		
		JLabel cusersLabel = new JLabel("打卡用户：");
		cusersLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cusersLabel.setFont(new Font("等线", Font.PLAIN, 16));
		cusersLabel.setBounds(316, 186, 88, 17);
		contentPane.add(cusersLabel);
		
		cusersShow = new JTextArea();
		cusersShow.setEditable(false);
		cusersShow.setLineWrap(true);
		cusersShow.setBounds(326, 210, 187, 62);
		contentPane.add(cusersShow);
		
		activityNameShow = new JLabel("");
		activityNameShow.setHorizontalAlignment(SwingConstants.LEFT);
		activityNameShow.setFont(new Font("等线", Font.PLAIN, 16));
		activityNameShow.setBounds(117, 78, 153, 17);
		contentPane.add(activityNameShow);
		
		sponsorShow = new JLabel("");
		sponsorShow.setHorizontalAlignment(SwingConstants.LEFT);
		sponsorShow.setFont(new Font("等线", Font.PLAIN, 16));
		sponsorShow.setBounds(117, 107, 153, 17);
		contentPane.add(sponsorShow);
		
		statrTimeShow = new JLabel("");
		statrTimeShow.setHorizontalAlignment(SwingConstants.LEFT);
		statrTimeShow.setFont(new Font("等线", Font.PLAIN, 16));
		statrTimeShow.setBounds(117, 134, 153, 17);
		contentPane.add(statrTimeShow);
		
		durationShow = new JLabel("");
		durationShow.setHorizontalAlignment(SwingConstants.LEFT);
		durationShow.setFont(new Font("等线", Font.PLAIN, 16));
		durationShow.setBounds(117, 161, 153, 17);
		contentPane.add(durationShow);
		
		placeShow = new JLabel("");
		placeShow.setHorizontalAlignment(SwingConstants.LEFT);
		placeShow.setFont(new Font("等线", Font.PLAIN, 16));
		placeShow.setBounds(117, 188, 153, 17);
		contentPane.add(placeShow);
		
		recruitShow = new JLabel("");
		recruitShow.setHorizontalAlignment(SwingConstants.LEFT);
		recruitShow.setFont(new Font("等线", Font.PLAIN, 16));
		recruitShow.setBounds(117, 215, 153, 17);
		contentPane.add(recruitShow);
		
		joinShow = new JLabel("");
		joinShow.setHorizontalAlignment(SwingConstants.LEFT);
		joinShow.setFont(new Font("等线", Font.PLAIN, 16));
		joinShow.setBounds(117, 242, 153, 17);
		contentPane.add(joinShow);
		
		checkinShow = new JLabel("");
		checkinShow.setHorizontalAlignment(SwingConstants.LEFT);
		checkinShow.setFont(new Font("等线", Font.PLAIN, 16));
		checkinShow.setBounds(117, 269, 153, 17);
		contentPane.add(checkinShow);

		JLabel statusLabel = new JLabel("活动状态：");
		statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusLabel.setFont(new Font("等线", Font.PLAIN, 16));
		statusLabel.setBounds(24, 294, 88, 17);
		contentPane.add(statusLabel);

		statusShow = new JLabel("");
		statusShow.setHorizontalAlignment(SwingConstants.LEFT);
		statusShow.setFont(new Font("等线", Font.PLAIN, 16));
		statusShow.setBounds(117, 294, 153, 17);
		contentPane.add(statusShow);
		init();
		mySelf = this;
		setVisible(true);
	}

	private void init() throws IOException, ClassNotFoundException {
		Transfer transfer = new Transfer();
		Transfer feedback = null;
		transfer.setCommand(VIEW_ACTIVITY);
		transfer.setActivity(new Activity(activityId));
		Commit.set(transfer);
		feedback = Commit.start();
		int result = feedback.getResult();
		if (result == SUCCESS){
			setData(feedback.getActivity());
		}else if (result == VIEW_ACTIVITY_FAIL){
			// 浏览活动失败
		}
	}

	private void setData(Activity activity){
		String status = activity.getStatus();
		activityNameShow.setText(activity.getName());
		sponsorShow.setText(activity.getSponsor());
		statrTimeShow.setText(activity.getTime().toString().substring(0,16));
		durationShow.setText(String.valueOf(activity.getDuration()));
		placeShow.setText(activity.getPlace());
		recruitShow.setText(String.valueOf(activity.getRecruit()));
		joinShow.setText(String.valueOf(activity.getJoin()));
		checkinShow.setText(String.valueOf(activity.getCheckIn()));
		postscriptShow.setText(activity.getPostscript());
		jusersShow.setText(activity.getjUsers());
		cusersShow.setText(activity.getcUsers());
		statusShow.setText(status);

		if (status.equals("已结束")){
			button.setVisible(false);
		}else if (status.equals("已开始")){
			if (activity.getSponsor().equals(Logined.getUser().getName())){
				button.setVisible(false);
			}else if(activity.getcUsers().contains(Logined.getUser().getName())){
				button.setText("已打卡");
				button.setEnabled(false);
			}else if(!activity.getcUsers().contains(Logined.getUser().getName())){
				button.setText("打卡");
			}
		}else if (status.equals("未开始")){
			if (activity.getSponsor().equals(Logined.getUser().getName())){
				button.setText("删除");
			}else if(activity.getjUsers().contains(Logined.getUser().getName())){
				button.setText("退出活动");
			}else if(!activity.getjUsers().contains(Logined.getUser().getName())){
				button.setText("加入活动");
			}
		}
	}

	private void closeWindow(){
		jframe.setVisible(true);
		close();
	}

	private void close(){
		mySelf.dispose();
	}

	private void refreshWindow() throws IOException, ClassNotFoundException {
		mySelf.setVisible(false);
		new UserViewActivityDetails(activityId, jframe);
		mySelf.dispose();
	}
}
