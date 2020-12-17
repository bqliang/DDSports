package ui;

import client.Commit;
import client.Logined;
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

public class EditProfile extends JFrame implements Agreement {

    private JComboBox contactComboBox;
    private JComboBox genderComboBox;
    private JPanel contentPane;
    private JTextField nameInput;
    private JTextField contactInput;
    private JTextField emailInput;
    private JFrame jframe;

    public EditProfile(JFrame jframe) {
        this.jframe = jframe;
        jframe.setVisible(false);
        setTitle("修改资料");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 594, 416);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 65, 580, 314);
        contentPane.add(mainPanel);
        mainPanel.setLayout(null);

        JLabel nameLabel = new JLabel("用户名：");
        nameLabel.setBounds(82, 18, 64, 17);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setFont(new Font("等线", Font.PLAIN, 16));
        mainPanel.add(nameLabel);

        nameInput = new JTextField();
        nameInput.setFont(new Font("等线", Font.PLAIN, 16));
        nameInput.setEnabled(true);
        nameInput.setEditable(false);
        nameInput.setColumns(10);
        nameInput.setBounds(165, 10, 178, 32);
        mainPanel.add(nameInput);

        JLabel nameTrips = new JLabel("作为登录名，暂不允许修改");
        nameTrips.setForeground(Color.GRAY);
        nameTrips.setHorizontalAlignment(SwingConstants.CENTER);
        nameTrips.setFont(new Font("等线", Font.PLAIN, 14));
        nameTrips.setBounds(165, 49, 178, 17);
        mainPanel.add(nameTrips);

        JLabel genderLabel = new JLabel("性别：");
        genderLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        genderLabel.setFont(new Font("等线", Font.PLAIN, 16));
        genderLabel.setBounds(98, 81, 48, 17);
        mainPanel.add(genderLabel);

        genderComboBox = new JComboBox();
        genderComboBox.setFont(new Font("等线", Font.PLAIN, 16));
        genderComboBox.setModel(new DefaultComboBoxModel(new String[] {"男", "女", "不便透露"}));
        genderComboBox.setSelectedIndex(0);
        genderComboBox.setBounds(165, 78, 101, 23);
        mainPanel.add(genderComboBox);

        JLabel contactLabel = new JLabel("联系方式：");
        contactLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contactLabel.setFont(new Font("等线", Font.PLAIN, 16));
        contactLabel.setBounds(66, 124, 80, 17);
        mainPanel.add(contactLabel);

        contactComboBox = new JComboBox();
        contactComboBox.setModel(new DefaultComboBoxModel(new String[] {"微信", "QQ", "手机"}));
        contactComboBox.setSelectedIndex(0);
        contactComboBox.setFont(new Font("等线", Font.PLAIN, 16));
        contactComboBox.setBounds(165, 121, 101, 23);
        mainPanel.add(contactComboBox);

        contactInput = new JTextField();
        contactInput.setFont(new Font("等线", Font.PLAIN, 16));
        contactInput.setEnabled(true);
        contactInput.setEditable(true);
        contactInput.setColumns(10);
        contactInput.setBounds(294, 117, 178, 32);
        mainPanel.add(contactInput);

        JLabel emailLabel = new JLabel("电子邮箱：");
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        emailLabel.setFont(new Font("等线", Font.PLAIN, 16));
        emailLabel.setBounds(66, 174, 80, 17);
        mainPanel.add(emailLabel);

        emailInput = new JTextField();
        emailInput.setFont(new Font("等线", Font.PLAIN, 16));
        emailInput.setEnabled(true);
        emailInput.setEditable(true);
        emailInput.setColumns(10);
        emailInput.setBounds(165, 167, 178, 32);
        mainPanel.add(emailInput);

        JLabel emailTrips = new JLabel("仅用于找回密码与验证码登录，我们承诺将严格保密您的信息");
        emailTrips.setHorizontalAlignment(SwingConstants.LEFT);
        emailTrips.setForeground(Color.GRAY);
        emailTrips.setFont(new Font("等线", Font.PLAIN, 14));
        emailTrips.setBounds(66, 209, 406, 17);
        mainPanel.add(emailTrips);

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 点击保存
                User user = new User();
                user.setId(Logined.getUser().getId());
                user.setGender(genderComboBox.getSelectedItem().toString());
                user.setContact(contactComboBox.getSelectedItem().toString() + "：" + contactInput.getText());
                user.setEmail(emailInput.getText());
                Transfer transfer = new Transfer();
                Transfer feedback = null;
                transfer.setCommand(EDIT_PROFILE);
                transfer.setUser(user);
                Commit.set(transfer);
                try {
                    feedback = Commit.start();
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
                int result = feedback.getResult();
                if (result == SUCCESS){
                    // 修改资料成功
                    JOptionPane.showMessageDialog(null, "修改资料成功！");
                    closeWindow();
                }else if (result == EDIT_PROFILE_FAIL){
                    // 修改资料失败
                    JOptionPane.showMessageDialog(null, "很抱歉出现未知错误", "修改资料失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setBackground(new Color(242, 142, 30));
        saveButton.setBounds(406, 254, 153, 43);
        mainPanel.add(saveButton);

        JLabel help = new JLabel("帮助");
        help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                help.setForeground(new Color(30, 144, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                help.setForeground(new Color(242, 142, 30));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                // 点击帮助
            }
        });
        help.setHorizontalAlignment(SwingConstants.LEFT);
        help.setForeground(new Color(242, 142, 30));
        help.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        help.setBounds(31, 269, 38, 16);
        mainPanel.add(help);

        JLabel privacy = new JLabel("隐私权");
        privacy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 点击隐私权
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                privacy.setForeground(new Color(30, 144, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                privacy.setForeground(new Color(242, 142, 30));
            }
        });
        privacy.setHorizontalAlignment(SwingConstants.LEFT);
        privacy.setForeground(new Color(242, 142, 30));
        privacy.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        privacy.setBounds(79, 269, 51, 16);
        mainPanel.add(privacy);

        JLabel terms = new JLabel("条款");
        terms.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 点击条款
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                terms.setForeground(new Color(30, 144, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                terms.setForeground(new Color(242, 142, 30));
            }
        });
        terms.setHorizontalAlignment(SwingConstants.LEFT);
        terms.setForeground(new Color(242, 142, 30));
        terms.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        terms.setBounds(140, 269, 36, 16);
        mainPanel.add(terms);

        JPanel headPanel = new JPanel();
        headPanel.setBackground(new Color(242, 142, 30));
        headPanel.setBounds(0, 0, 580, 56);
        contentPane.add(headPanel);
        headPanel.setLayout(null);

        JLabel headLabel = new JLabel("-> 修改您的账号信息");
        headLabel.setHorizontalAlignment(SwingConstants.LEFT);
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(26, 4, 281, 47);
        headLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
        headPanel.add(headLabel);
        initUI();
        setVisible(true);
    }

    private void initUI(){
        User user = Logined.getUser();
        nameInput.setText(user.getName());
        String gender = user.getGender();
        String contact = user.getContact();

        if (gender.equals("男")){
            genderComboBox.setSelectedIndex(0);
        }else if(gender.equals("女")){
            genderComboBox.setSelectedIndex(1);
        }else {
            genderComboBox.setSelectedIndex(2);
        }

        if (contact.contains("微信")){
            contactComboBox.setSelectedIndex(0);
        }else if (contact.contains("手机")){
            contactComboBox.setSelectedIndex(2);
        }else {
            contactComboBox.setSelectedIndex(1);
        }
        contactInput.setText(contact.split("：")[1]);
        emailInput.setText(user.getEmail());
    }

    private void closeWindow(){
        dispose();
        jframe.setVisible(true);
    }
}
