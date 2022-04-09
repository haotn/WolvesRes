package com.wolvesres.form.doimatkhau;

import com.swing.custom.raven.RButton.RButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class PanelCoverDoiMK extends javax.swing.JPanel {

    private final DecimalFormat df = new DecimalFormat("##0.###");
    private ActionListener event;
    private MigLayout layout;
    private JLabel title;
    private JLabel description;
    private JLabel description1;
    private RButton button;
    private boolean isIdentity;
    RButton btnExit = new RButton();
    private ActionListener evenExit;

    public PanelCoverDoiMK() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "[]70[]25[]10[]25[]push");
        setLayout(layout);
        init();
    }

    private void init() {
        btnExit.setVisible(false);
        btnExit.setOpaque(true);
        btnExit.setBackground(new Color(4, 28, 52));
        add(btnExit, "w 25, h 25, gapleft 280");
        btnExit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/closeWhite.png")));
        btnExit.setFont(new Font("sansserif", 1, 15));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evenExit.actionPerformed(e);
            }
        });
        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnExit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/closeRed.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/closeWhite.png")));
            }
        });
        //
        title = new JLabel("Xác nhận tài khoản!");
        title.setFont(new Font("sansserif", 1, 30));
        title.setForeground(new Color(245, 245, 245));
        add(title);
        description = new JLabel("Nhập tài khoản và mật khẩu");
        description.setForeground(new Color(245, 245, 245));
        add(description);
        description1 = new JLabel("Ghi nhớ thay đổi của bạn!");
        description1.setForeground(new Color(245, 245, 245));
        add(description1);
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
@Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        GradientPaint gra = new GradientPaint(0, 0, new Color(0, 213, 145), 0, getHeight(), new Color(0, 105, 71));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    public void addEvent(ActionListener event) {
        this.event = event;
    }

    public void identifyLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
    }

    public void identifyRight(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
    }

    public void changePassLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
    }

    public void changePassRight(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
    }

    private void login(boolean identity) {
        if (this.isIdentity != identity) {
            if (identity) {
                title.setText("Tạo mật khẩu mới");
                description.setText("Tạo mật khẩu mới an toàn hơn nhé");
                description1.setText("Bạn nên đổi mật khẩu thường xuyên");
                // button.setVisible(false);
                btnExit.setVisible(true);
                //button.setText("");
            } else {
                title.setText("Xác minh danh tính của bạn!");
                description.setText("Nhập tài khoản và mật khẩu để tiếp tục");
                description1.setText("Ghi nhớ thay đổi của bạn!");
                //button.setVisible(false);
                //button.setText("Đăng nhập");
                btnExit.setVisible(false);
            }
            this.isIdentity = identity;
        }
    }

    public void setEvenExit(ActionListener evenExit) {
        this.evenExit = evenExit;
    }

}
