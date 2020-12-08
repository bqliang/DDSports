package client;

import javax.swing.*;

/**
 * @author bqliang
 */

public class CountDownThread implements Runnable {
    private JButton getCode;

    public CountDownThread(JButton getCode){
        this.getCode = getCode;
    }

    @Override
    public void run() {
        getCode.setEnabled(false);
        for (int i = 30; i > 0; i--){
            getCode.setText(i + "秒");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getCode.setText("获取验证码");
        getCode.setEnabled(true);
    }
}
