package com.wolvesres.form.dangnhap;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
/**
 *  Hổ trựo xử lý giao diện
 * */
public class PanelCoverDangNhap extends javax.swing.JPanel {

    private final DecimalFormat df = new DecimalFormat("###,###");
    private ActionListener event;
    private MigLayout layout;
    private JLabel title;
    private JLabel description;
    private JLabel description1;
    //   private RButton button;
    private boolean isLogin;

    public PanelCoverDangNhap() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[][]push");
        setLayout(layout);
        init();
    }

    private void init() {
        title = new JLabel("Chào mừng bạn trở lại!");
        title.setFont(new Font("sansserif", 1, 30));
        title.setForeground(new Color(245, 245, 245));
        add(title);
        description = new JLabel("Hãy giữ liên lạc với chúng tôi nhé!");
        description.setForeground(new Color(245, 245, 245));
        add(description);
        description1 = new JLabel("Đăng nhập với tài khoản của bạn");
        description1.setForeground(new Color(245, 245, 245));
        add(description1);
//        button = new RButton();
//        button.setBackground(new Color(255, 255, 255));
//        button.setForeground(new Color(7, 164, 121));
//        button.setText("Đăng nhập");
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                event.actionPerformed(ae);
//            }
//        });
//       // button.doClick();
//        add(button, "w 60%, h 40");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    //animation giao diện latout
@Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        GradientPaint gra = new GradientPaint(0, 0, new Color(0, 213, 145), 0, getHeight(), new Color(0, 105, 71));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }
//thêm sự kiện
    public void addEvent(ActionListener event) {
        this.event = event;
    }
//xử lý phần layout bên trái
    public void registerLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
    }
  //xử lý phần layout bên trái
    public void registerRight(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
    }
//xử lý phần dữ liệu hiện form bên trái
    public void loginLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
    }
  //xử lý phần dữliệu hiện form bên phải
    public void loginRight(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
    }
//xử lý chuyển đổi khi đăng nhập thành công 
    private void login(boolean login) {
        if (this.isLogin != login) {
            if (!login) {
                title.setText("Xin chào, Bạn!");
                description.setText("Nhập thông tin");
                description1.setText("để sử dựng ứng dụng");
            } else {
                title.setText("WolvesRes");
                description.setText("Phát triển bởi ThreeWolves Company");
                description1.setText("Liên hệ để nhận sự trợ giúp!");
            }
            this.isLogin = login;
        }
    }
}
