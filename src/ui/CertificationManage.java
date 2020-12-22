package ui;

import client.Commit;
import model.Agreement;
import model.Transfer;
import model.User;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JCheckBox;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * @author bqliang
 */
public class CertificationManage extends JFrame implements Agreement {

	private JPanel contentPane;
	private JTable table;
	private JPanel headpanel;
	private JTextField searchInput;
	private JButton searchBtn;
	private JCheckBox checkBox;
	private JButton filtrateBtn;
	private JFrame mySelf;
	private JFrame jframe;

	public CertificationManage(JFrame jframe) {
		this.jframe = jframe;
		jframe.setVisible(false);
		setTitle("实名认证审核");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		setBounds(100, 100, 640, 410);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 42, 626, 331);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					int selectRow = table.getSelectedRow();
					int selectColumn = table.getSelectedColumn();
					int selectId = Integer.parseInt((String) table.getValueAt(selectRow, selectColumn));

				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		scrollPane.setViewportView(table);
		
		headpanel = new JPanel();
		headpanel.setBackground(Color.WHITE);
		headpanel.setBounds(0, 0, 638, 43);
		contentPane.add(headpanel);
		headpanel.setLayout(null);
		
		searchInput = new JTextField();
		searchInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					search();
				}
			}
		});
		searchInput.setColumns(10);
		searchInput.setBounds(10, 8, 112, 26);
		headpanel.add(searchInput);
		
		searchBtn = new JButton("搜索");
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		searchBtn.setMargin(new Insets(2, 10, 2, 10));
		searchBtn.setForeground(Color.WHITE);
		searchBtn.setFont(new Font("等线", Font.PLAIN, 15));
		searchBtn.setFocusPainted(false);
		searchBtn.setBorderPainted(false);
		searchBtn.setBackground(new Color(242, 142, 30));
		searchBtn.setBounds(132, 10, 64, 23);
		headpanel.add(searchBtn);
		
		checkBox = new JCheckBox("待审核");
		checkBox.setSelected(true);
		checkBox.setFocusPainted(false);
		checkBox.setBackground(Color.WHITE);
		checkBox.setBounds(475, 10, 71, 23);
		headpanel.add(checkBox);
		
		filtrateBtn = new JButton("筛选");
		filtrateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkBox.isSelected()){
					filterUsers("SELECT * FROM user WHERE certificate = '审核中'");
				}else {
					filterUsers("SELECT * FROM user");
				}
			}
		});
		filtrateBtn.setMargin(new Insets(2, 10, 2, 10));
		filtrateBtn.setForeground(Color.WHITE);
		filtrateBtn.setFont(new Font("等线", Font.PLAIN, 15));
		filtrateBtn.setFocusPainted(false);
		filtrateBtn.setBorderPainted(false);
		filtrateBtn.setBackground(new Color(242, 142, 30));
		filtrateBtn.setBounds(552, 10, 64, 23);
		headpanel.add(filtrateBtn);
		init();
		setVisible(true);
		mySelf = this;
	}

	private void init(){
		filterUsers("SELECT * FROM user WHERE certificate = '审核中'");
	}

	private void setTableData(List<User> users) {
		String [] head = new String[] {"id", "登录名", "真实姓名", "身份证号码", "认证状态"};
		String [][] data = new String [users.size()][5];
		for (int i = 0; i < users.size(); i++){
			data[i][0] = String.valueOf(users.get(i).getId());
			data[i][1] = users.get(i).getName();
			data[i][2] = users.get(i).getRealName();
			data[i][3] = users.get(i).getIdCard();
			data[i][4] = users.get(i).getCertificate();
		}
		table.setModel(new DefaultTableModel(data, head) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
	}

	private void filterUsers(String sql){
		Transfer transfer = new Transfer();
		Transfer feedback = null;
		transfer.setSql(sql);
		transfer.setCommand(FILTER_USERS);
		Commit.set(transfer);
		try {
			feedback = Commit.start();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		setTableData(feedback.getUserList());
	}

	private void search(){
		checkBox.setSelected(false);
		String sql = "", input;
		input = searchInput.getText();
		if (input.matches("^[1-9]\\d*$")){
			sql = String.format("SELECT * FROM user WHERE id = %d OR name LIKE '%s'", Integer.parseInt(input), "%" + input + "%");
		}else {
			sql= String.format("SELECT * FROM user WHERE name LIKE '%s'", "%" + input + "%");
		}
		filterUsers(sql);
	}

	private void closeWindow(){
		jframe.setVisible(true);
		mySelf.dispose();
	}
}
