package ui;

import client.Commit;
import client.CountDownThread;
import client.Tools;
import model.Agreement;
import model.Transfer;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import java.awt.event.*;
import java.awt.Insets;
import java.io.IOException;


/**
 * @author bqliang
 */
public class RetrievePassword extends JFrame implements Agreement {

    private JPanel contentPane;
    private JTextField emailInput;
    private JTextField codeInput;
    private String code;
    private JPasswordField pwAgainInput;
    private JPasswordField newPwInput;
    private JFrame jframe;

    public RetrievePassword(JFrame jframe) {
        this.jframe = jframe;
        jframe.setVisible(false);
        setTitle("找回密码");
        setIconImage(Tools.getImage("icons/security.png"));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

        setBounds(100, 100, 450, 380);
        this.setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel1.setBounds(80, 35, 253, 122);
        contentPane.add(panel1);
        panel1.setLayout(null);

        JButton getCode = new JButton("获取验证码");
        getCode.setMargin(new Insets(2, 10, 2, 10));
        getCode.setFont(new Font("微软雅黑", Font.BOLD, 14));
        getCode.setFocusPainted(false);
        getCode.setForeground(new Color(242, 142, 30));
        getCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(emailInput.getText() == null){
                    JOptionPane.showMessageDialog(null, "请检查后重试", "您似乎还未输入邮箱", JOptionPane.ERROR_MESSAGE);
                    return;
                }else if(!Tools.isEmail(emailInput.getText())){
                    JOptionPane.showMessageDialog(null, "请检查后重试", "您输入邮箱不合法", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Transfer transfer = new Transfer();
                Transfer feedback = new Transfer();
                transfer.setCommand(SEND_CODE);
                User user = new User();
                user.setEmail(emailInput.getText());
                transfer.setUser(user);
                Commit.set(transfer);
                try {
                    feedback = Commit.start();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (feedback.getResult() == SUCCESS){
                    emailInput.setEnabled(false);
                    code = feedback.getCode();
                    CountDownThread countDownThread = new CountDownThread(getCode);
                    new Thread(countDownThread).start();
                }else if(feedback.getResult() == EMAIL_NOT_EXISTS){
                    JOptionPane.showMessageDialog(null, "请检查后重试", "账号不存在", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getCode.setBackground(SystemColor.menu);
        getCode.setBorderPainted(false);
        getCode.setBounds(151, 58, 102, 25);
        panel1.add(getCode);

        emailInput = new JTextField();
        emailInput.setEnabled(true);
        emailInput.setEditable(true);
        emailInput.setFont(new Font("等线", Font.PLAIN, 16));
        emailInput.setColumns(10);
        emailInput.setBounds(67, 2, 185, 32);
        panel1.add(emailInput);

        JLabel emailLabel = new JLabel("邮箱：");
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        emailLabel.setFont(new Font("等线", Font.PLAIN, 16));
        emailLabel.setBounds(0, 1, 69, 32);
        panel1.add(emailLabel);

        JLabel codeLabel = new JLabel("验证码：");
        codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        codeLabel.setFont(new Font("等线", Font.PLAIN, 16));
        codeLabel.setBounds(0, 54, 70, 32);
        panel1.add(codeLabel);

        JLabel notReceiveEmail = new JLabel("没有收到验证码？");
        notReceiveEmail.setHorizontalAlignment(SwingConstants.RIGHT);
        notReceiveEmail.setToolTipText("请检查邮件是否被归档到垃圾邮箱");
        notReceiveEmail.setHorizontalTextPosition(SwingConstants.CENTER);
        notReceiveEmail.setForeground(Color.GRAY);
        notReceiveEmail.setFont(new Font("微软雅黑", Font.ITALIC, 14));
        notReceiveEmail.setBounds(135, 95, 118, 16);
        panel1.add(notReceiveEmail);

        codeInput = new JTextField();
        codeInput.setFont(new Font("等线", Font.PLAIN, 16));
        codeInput.setColumns(10);
        codeInput.setBounds(67, 55, 78, 32);
        panel1.add(codeInput);

        JButton retrievePwBtn = new JButton("重设密码");
        retrievePwBtn.setForeground(Color.WHITE);
        retrievePwBtn.setBackground(new Color(242, 142, 30));
        retrievePwBtn.setFocusPainted(false);
        retrievePwBtn.setBorderPainted(false);
        retrievePwBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
        retrievePwBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(newPwInput.getPassword());
                if(codeInput.getText() == null){
                    JOptionPane.showMessageDialog(null, "请检查后重试", "您似乎还未输入验证码", JOptionPane.ERROR_MESSAGE);
                    return;
                }else if (password == null){
                    JOptionPane.showMessageDialog(null, "请检查后重试", "您似乎还未输入新密码", JOptionPane.ERROR_MESSAGE);
                }
                if(codeInput.getText().equals(code)){
                    Transfer transfer = new Transfer();
                    Transfer feedback = new Transfer();
                    transfer.setCommand(RETRIEVE_PASSWORD);
                    User user = new User();
                    user.setEmail(emailInput.getText());
                    user.setPw(password);
                    transfer.setUser(user);
                    Commit.set(transfer);
                    try {
                        feedback = Commit.start();
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                    int result = feedback.getResult();
                    if (result == RETRIEVE_PASSWORD_FAIL){
                        JOptionPane.showMessageDialog(null, "很抱歉出现未知错误", "重设密码失败", JOptionPane.ERROR_MESSAGE);
                    }else if (result == SUCCESS){
                        JOptionPane.showMessageDialog(null, "重设密码成功！");
                        closeWindow();
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "请检查后重试", "验证码错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        retrievePwBtn.setBounds(141, 276, 153, 43);
        contentPane.add(retrievePwBtn);

        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBackground(Color.WHITE);
        panel2.setBounds(57, 169, 276, 97);
        contentPane.add(panel2);

        JLabel newPwLabel = new JLabel("新密码:");
        newPwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        newPwLabel.setFont(new Font("等线", Font.PLAIN, 16));
        newPwLabel.setBounds(20, 1, 60, 32);
        panel2.add(newPwLabel);

        JLabel pwAgainLabel = new JLabel("确认密码:");
        pwAgainLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pwAgainLabel.setFont(new Font("等线", Font.PLAIN, 16));
        pwAgainLabel.setBounds(0, 54, 80, 32);
        panel2.add(pwAgainLabel);

        pwAgainInput = new JPasswordField();
        pwAgainInput.setFont(new Font("等线", Font.PLAIN, 16));
        pwAgainInput.setBounds(87, 54, 189, 33);
        panel2.add(pwAgainInput);

        newPwInput = new JPasswordField();
        newPwInput.setFont(new Font("等线", Font.PLAIN, 16));
        newPwInput.setBounds(87, 1, 189, 33);
        panel2.add(newPwInput);
        this.setVisible(true);
    }

    private void closeWindow(){
        jframe.setVisible(true);
        this.dispose();
    }

    private void close(){
        this.dispose();
    }
}
