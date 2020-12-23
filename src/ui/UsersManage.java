package ui;

import client.Commit;
import client.Tools;
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
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * @author bqliang
 */
public class UsersManage extends JFrame implements Agreement {

	private JPanel contentPane;
	private JTable table;
	private JPanel headpanel;
	private JTextField searchInput;
	private JButton searchBtn;
	private JFrame jframe;
	private JFrame mySelf;


	public UsersManage(JFrame jframe) {
		jframe.setVisible(false);
		this.jframe = jframe;
		setTitle("用户管理");
		setIconImage(Tools.getImage("icons/groups.png"));
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
		init();
		setVisible(true);
		mySelf = this;
	}

	private void init(){
		Transfer transfer = new Transfer();
		Transfer feedback = null;
		transfer.setCommand(FILTER_USERS);
		transfer.setSql("SELECT * FROM user");
		Commit.set(transfer);
		try {
			feedback = Commit.start();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		setTableData(feedback.getUserList());
	}

	private void setTableData(List<User> users) {
		String [] head = new String[] {"id", "登录名", "性别", "电子邮箱", "真实姓名"};
		String [][] data = new String [users.size()][5];
		for (int i = 0; i < users.size(); i++){
			data[i][0] = String.valueOf(users.get(i).getId());
			data[i][1] = users.get(i).getName();
			data[i][2] = users.get(i).getGender();
			data[i][3] = users.get(i).getEmail();
			data[i][4] = users.get(i).getRealName();
		}
		table.setModel(new DefaultTableModel(data, head) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
	}

	private void search(){
		String sql = "";
		String input = searchInput.getText();
		if (input.matches("^[1-9]\\d*$")){
			sql = String.format("SELECT * FROM user WHERE id = %d OR name LIKE '%s'",
					Integer.parseInt(input), "%" + input + "%");
		}else if (input.equals("")){
			init();
			return;
		}else {
			sql = String.format("SELECT * FROM user WHERE name LIKE '%s' OR email LIKE '%s' OR realname LIKE '%s'",
					"%" + input + "%", "%" + input + "%", "%" + input + "%");
		}
		Transfer transfer = new Transfer();
		Transfer feedback = null;
		transfer.setCommand(FILTER_USERS);
		transfer.setSql(sql);
		Commit.set(transfer);
		try {
			feedback = Commit.start();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		setTableData(feedback.getUserList());
	}

	private void closeWindow(){
		jframe.setVisible(true);
		this.dispose();
	}
}
