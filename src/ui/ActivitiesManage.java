package ui;

import client.Commit;
import client.Tools;
import model.Activity;
import model.Agreement;
import model.Transfer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * @author bqliang
 */
public class ActivitiesManage extends JFrame implements Agreement {
	
	private JPanel contentPane;
	private JTable table;
	private JTextField searchInput;
	private JCheckBox notStartCheckBox;
	private JCheckBox hadStartCheckBox;
	private JCheckBox hadEndCheckBox;
	private JFrame mySelf;
	private JFrame jframe;


	public ActivitiesManage(JFrame jframe) {
		this.jframe = jframe;
		jframe.setVisible(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		setTitle("活动管理");
		setIconImage(Tools.getImage("icons/basketball.png"));
		setBounds(100, 100, 640, 410);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 40, 626, 333);
		contentPane.add(scrollPane);
		
		table =  new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					int selecRow = table.getSelectedRow();
					int id = Integer.parseInt((String) table.getValueAt(selecRow, 0));
					new AdminViewActivityDetails(id, mySelf);
				}
			}
		});
		scrollPane.setViewportView(table);

		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 626, 35);
		contentPane.add(panel);
		panel.setLayout(null);
		
		searchInput = new JTextField();
		searchInput.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					search();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}


		});
		searchInput.setBounds(10, 4, 112, 26);
		panel.add(searchInput);
		searchInput.setColumns(10);
		
		JButton searchBtn = new JButton("搜索");
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		searchBtn.setMargin(new Insets(2, 10, 2, 10));
		searchBtn.setFont(new Font("等线", Font.PLAIN, 15));
		searchBtn.setForeground(Color.WHITE);
		searchBtn.setBackground(new Color(242, 142, 30));
		searchBtn.setFocusPainted(false);
		searchBtn.setBorderPainted(false);
		searchBtn.setBounds(132, 6, 64, 23);
		panel.add(searchBtn);
		
		JButton filtrateBtn = new JButton("筛选");
		filtrateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (notStartCheckBox.isSelected()){
					filterActivities("SELECT * FROM activity WHERE status = '未开始'");
				}else if (hadStartCheckBox.isSelected()){
					filterActivities("SELECT * FROM activity WHERE status = '已开始'");
				}else if (hadEndCheckBox.isSelected()){
					filterActivities("SELECT * FROM activity WHERE status = '已结束'");
				}else {
					init();
				}
			}
		});
		filtrateBtn.setMargin(new Insets(2, 10, 2, 10));
		filtrateBtn.setForeground(Color.WHITE);
		filtrateBtn.setFont(new Font("等线", Font.PLAIN, 15));
		filtrateBtn.setFocusPainted(false);
		filtrateBtn.setBorderPainted(false);
		filtrateBtn.setBackground(new Color(242, 142, 30));
		filtrateBtn.setBounds(552, 6, 64, 23);
		panel.add(filtrateBtn);
		
		notStartCheckBox = new JCheckBox("未开始");
		notStartCheckBox.setMargin(new Insets(2, 0, 2, 0));
		notStartCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (notStartCheckBox.isSelected()){
					hadStartCheckBox.setSelected(false);
					hadEndCheckBox.setSelected(false);
				}
			}
		});
		notStartCheckBox.setFocusPainted(false);
		notStartCheckBox.setBackground(Color.WHITE);
		notStartCheckBox.setBounds(318, 6, 61, 23);
		panel.add(notStartCheckBox);
		
		hadStartCheckBox = new JCheckBox("已开始");
		hadStartCheckBox.setMargin(new Insets(2, 0, 2, 0));
		hadStartCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (hadStartCheckBox.isSelected()){
					notStartCheckBox.setSelected(false);
					hadEndCheckBox.setSelected(false);
				}
			}
		});
		hadStartCheckBox.setFocusPainted(false);
		hadStartCheckBox.setBackground(Color.WHITE);
		hadStartCheckBox.setBounds(397, 6, 61, 23);
		panel.add(hadStartCheckBox);
		
		hadEndCheckBox = new JCheckBox("已结束");
		hadEndCheckBox.setMargin(new Insets(2, 0, 2, 0));
		hadEndCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (hadEndCheckBox.isSelected()){
					notStartCheckBox.setSelected(false);
					hadStartCheckBox.setSelected(false);
				}
			}
		});
		hadEndCheckBox.setFocusPainted(false);
		hadEndCheckBox.setBackground(Color.WHITE);
		hadEndCheckBox.setBounds(476, 6, 61, 23);
		panel.add(hadEndCheckBox);
		init();
		setVisible(true);
		mySelf = this;
	}

	private void init(){
		filterActivities("SELECT * FROM activity");
	}

	private void filterActivities(String sql) {
		Transfer transfer = new Transfer();
		transfer.setCommand(FILTER_ACTIVITIES);
		transfer.setSql(sql);
		Commit.set(transfer);
		Transfer feedback = null;
		try {
			feedback = Commit.start();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		setData(feedback.getActivityList());
	}


	private void setData(List<Activity> activities) {
		String[][] data = new String[activities.size()][6];
		for(int i = 0; i < activities.size(); i++){
			data[i][0] = String.valueOf(activities.get(i).getId());
			data[i][1] = activities.get(i).getName();
			data[i][2] = activities.get(i).getPlace();
			data[i][3] = activities.get(i).getTime().toString().substring(0,16);
			data[i][4] = activities.get(i).getSponsor();
			data[i][5] = activities.get(i).getStatus();
		}

		table.setModel(new DefaultTableModel(data,new String []{"ID", "运动", "地点", "时间", "发起人", "状态"}) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
	}

	private void search(){
		notStartCheckBox.setSelected(false);
		hadStartCheckBox.setSelected(false);
		hadEndCheckBox.setSelected(false);
		String sql = "", input;
		input = searchInput.getText();
		if (input.matches("^[1-9]\\d*$")){
			sql = String.format("SELECT * FROM activity WHERE id = %d OR sponsor LIKE '%s'",
					Integer.parseInt(input), "%" + input + "%");
		}else {
			sql = String.format("SELECT * FROM activity WHERE name LIKE '%s' OR place LIKE '%s' OR sponsor LIKE '%s'",
					"%" + input + "%", "%" + input + "%", "%" + input + "%");
		}
		filterActivities(sql);
	}


	private void closeWindow(){
		jframe.setVisible(true);
		mySelf.dispose();
	}
}
