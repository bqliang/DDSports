package ui;

import client.Commit;
import client.Logined;
import client.Tools;
import model.Activity;
import model.Agreement;
import model.Transfer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bqliang
 */
public class UserViewActivities extends JFrame implements Agreement {

    private JTable table;
    private JTextField searchInput;
    private JFrame mySelf;

    public UserViewActivities() throws IOException, ClassNotFoundException {
        setTitle("滴滴运动");
        setIconImage(Tools.getImage("ddsports-icon.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 640, 410);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, 626, 40);
        toolBar.setFloatable(false);
        toolBar.setBorderPainted(false);
        toolBar.setBackground(new Color(242, 142, 30));
        contentPane.add(toolBar);

        JButton createActivityBtn = new JButton("发起运动", new ImageIcon(Tools.getImage("icons/flag.png")));
        createActivityBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateActivity(mySelf);
            }
        });
        createActivityBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        createActivityBtn.setFocusPainted(false);
        toolBar.add(createActivityBtn);

        JButton editProfileBtn = new JButton("修改资料", new ImageIcon(Tools.getImage("icons/edit.png")));
        editProfileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditProfile(mySelf);
            }
        });
        editProfileBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        editProfileBtn.setFocusPainted(false);
        toolBar.add(editProfileBtn);

        JButton changePasswordBtn = new JButton("更改密码", new ImageIcon(Tools.getImage("icons/security.png")));
        changePasswordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ifRememberPassword = JOptionPane.showConfirmDialog(null, "您是否仍记得原密码？", "更改密码", 0);
                if(ifRememberPassword == JOptionPane.YES_OPTION){
                    // 通过原密码更改新密码
                    new ResetPwByPw(mySelf);
                }else if (ifRememberPassword == JOptionPane.NO_OPTION){
                    new RetrievePassword(mySelf);
                }
            }
        });
        changePasswordBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        changePasswordBtn.setFocusPainted(false);
        toolBar.add(changePasswordBtn);

        JButton certificationBtn = new JButton("实名认证", new ImageIcon(Tools.getImage("icons/certification.png")));
        certificationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Certification(mySelf);
            }
        });
        certificationBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        certificationBtn.setFocusPainted(false);
        toolBar.add(certificationBtn);

        JButton exitBtn = new JButton("退出登录", new ImageIcon(Tools.getImage("icons/exit.png")));
        exitBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "是否退出登录？", "确认操作", 0);
                if (choice == JOptionPane.YES_OPTION){
                    new Login();
                    mySelf.dispose();
                }
            }
        });
        exitBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        exitBtn.setFocusPainted(false);
        toolBar.add(exitBtn);

        JButton helpBtn = new JButton("帮助", new ImageIcon(Tools.getImage("icons/help.png")));
        helpBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        helpBtn.setFocusPainted(false);
        toolBar.add(helpBtn);

        JButton aboutBtn = new JButton("关于", new ImageIcon(Tools.getImage("icons/about.png")));
        aboutBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        aboutBtn.setFocusPainted(false);
        toolBar.add(aboutBtn);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 75, 626, 298);
        contentPane.add(scrollPane);

        table =  new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int selectRow = table.getSelectedRow();
                    int id = Logined.getIdList().get(selectRow);
                    try {
                        new UserViewActivityDetails(id, mySelf);
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        scrollPane.setViewportView(table);
        initTable();
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 40, 626, 35);
        contentPane.add(panel);
        panel.setLayout(null);

        searchInput = new JTextField();
        searchInput.setBounds(10, 4, 112, 26);
        panel.add(searchInput);
        searchInput.setColumns(10);

        JCheckBox notStartCheckBox = new JCheckBox("未开始");
        notStartCheckBox.setBackground(Color.WHITE);
        notStartCheckBox.setFocusPainted(false);
        notStartCheckBox.setBounds(321, 6, 64, 23);
        panel.add(notStartCheckBox);

        JCheckBox hasJoinCheckBox = new JCheckBox("由我发起 / 我已参加");
        hasJoinCheckBox.setBackground(Color.WHITE);
        hasJoinCheckBox.setFocusPainted(false);
        hasJoinCheckBox.setBounds(400, 6, 146, 23);
        panel.add(hasJoinCheckBox);

        JButton searchBtn = new JButton("搜索");
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notStartCheckBox.setSelected(false);
                hasJoinCheckBox.setSelected(false);
                String sql = "SELECT * FROM activity WHERE name LIKE '%s' OR place LIKE '%s'";
                try {
                    filterActivities(String.format(sql, "%" + searchInput.getText() + "%", "%" + searchInput.getText() + "%"));
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
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
                try {
                    // 没有筛选条件，直接显示所有活动
                    if (!notStartCheckBox.isSelected() && !hasJoinCheckBox.isSelected()){
                        initTable();
                        // 显示未开始的活动
                    }else if (notStartCheckBox.isSelected()){
                        filterActivities("SELECT * FROM activity WHERE status = '未开始'");
                        // 显示已参加的活动
                    }else if (hasJoinCheckBox.isSelected()){
                        String sql = "SELECT * FROM activity WHERE sponsor = '%s' OR jusers LIKE '%s'";
                        String name = Logined.getUser().getName();
                        filterActivities(String.format(sql, name, "%" + name + "%"));
                    }else if (notStartCheckBox.isSelected() && hasJoinCheckBox.isSelected()){
                        String sql = "SELECT * FROM activity WHERE status = '未开始' AND (sponsor = '%s' OR jusers LIKE '%s');";
                        String name = Logined.getUser().getName();
                        filterActivities(String.format(sql, name, "%" + name + "%"));
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
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
        setVisible(true);
        mySelf = this;
    }

    private void initTable() throws IOException, ClassNotFoundException {
        Transfer transfer = new Transfer();
        transfer.setCommand(VIEW_ACTIVITIES);
        Commit.set(transfer);
        Transfer feedback = Commit.start();
        setData(feedback);
    }

    private void filterActivities(String sql) throws IOException, ClassNotFoundException {
        Transfer transfer = new Transfer();
        transfer.setCommand(FILTER_ACTIVITIES);
        transfer.setSql(sql);
        Commit.set(transfer);
        Transfer feedback = Commit.start();
        setData(feedback);
    }

    private void setData(Transfer feedback) {
        List<Activity> activityList = feedback.getActivityList();
        int numOfActivities = activityList.size();
        String[][] data = new String[numOfActivities][6];
        List<Integer> idList = new ArrayList<>();
        for(int i = 0; i < numOfActivities; i++){
            idList.add(activityList.get(i).getId());
            data[i][0] = activityList.get(i).getName();
            data[i][1] = activityList.get(i).getPlace();
            data[i][2] = activityList.get(i).getTime().toString().substring(0,16);
            data[i][3] = String.valueOf(activityList.get(i).getRecruit());
            data[i][4] = String.valueOf(activityList.get(i).getJoin());
            data[i][5] = activityList.get(i).getStatus();
        }
        Logined.setIdList(idList);

        table.setModel(new DefaultTableModel(data,new String []{"运动", "地点", "时间", "招募人数", "报名人数", "状态"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

//        DefaultTableModel dtm = new DefaultTableModel(data,new String []{"运动", "地点", "时间", "招募人数", "报名人数"});
//        table.setModel(dtm);
    }

}
