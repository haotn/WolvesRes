package com.wolvesres.form;

import com.wolvesres.form.chaomung.FormLoadData;
import com.wolvesres.form.dangnhap.PanelCoverDangNhap;
import com.wolvesres.form.dangnhap.PanelDangNhap;
import com.wolvesres.helper.AnimationShowWindow;
import com.wolvesres.helper.XImage;
import com.wolvesres.main.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class FormDangNhap extends javax.swing.JFrame {

    private MigLayout layout;
    private PanelCoverDangNhap cover;
    private PanelDangNhap loginAndRegister;
    private boolean isLogin;
    private Main main;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private final DecimalFormat df = new DecimalFormat("###,###");
    JFrame frame;

    public FormDangNhap() {
        initComponents();
        init();
        this.frame = this;
        AnimationShowWindow.showFrame(this);
        setIconImage(XImage.getAppIcon());
    }
    int second = 0;
    Thread t = new Thread();

    private void init() {
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCoverDangNhap();
        loginAndRegister = new PanelDangNhap(frame);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if (fraction <= 0.5f) {
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
                }
                if (isLogin) {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if (fraction >= 0.5f) {
                        cover.registerRight(fractionCover * 100);
                    } else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.registerLeft(fraction * 100);
                    } else {
                        cover.loginLeft((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
            }
        };
        final Animator animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);  //  for smooth animation
        bg.setLayout(layout);
        bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%"); //  1al as 100%
        loginAndRegister.setEventCLK(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new FormQuenMK().setVisible(true);
                dispose();
            }

        });
        loginAndRegister.addEvent(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                login(animator);
            }
        });
        loginAndRegister.setKeyEvent(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    login(animator);
                }
            }

        });
    }

    private void login(Animator animator) {
        if (!animator.isRunning()) {
            if (loginAndRegister.checkLogin()) {
                animator.start();
                loginAndRegister.rememberme();
                //loginAndRegister.xetTK();
                t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            while (true) {
                                second++;
                                t.sleep(45);
                                if (second == 45) {
                                    init();
                                    dispose();
                                    new FormLoadData(frame, true).setVisible(true);
                                    //      new Main().setVisible(true);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                );
                t.start();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);
        bg.setPreferredSize(new java.awt.Dimension(800, 450));

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line argumNV01 1ents
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
