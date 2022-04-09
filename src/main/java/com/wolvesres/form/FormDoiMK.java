package com.wolvesres.form;

import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.form.doimatkhau.PanelCoverDoiMK;
import com.wolvesres.form.doimatkhau.PanelDoiMK;
import com.wolvesres.helper.AnimationShowWindow;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelTaiKhoan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class FormDoiMK extends javax.swing.JFrame {

    JFrame frame;
    private TaiKhoanDAO tkDAO = new TaiKhoanDAO();
    private NhanVienDAO nvDAO = new NhanVienDAO();

    public FormDoiMK() {
        initComponents();
        setLocationRelativeTo(null);
        this.frame = this;
        AnimationShowWindow.showFrame(frame);
        init();
        setIconImage(XImage.getAppIcon());
    }
    private MigLayout layout;
    private PanelCoverDoiMK cover;
    private PanelDoiMK forgerPass;
    private boolean isLogin;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private final DecimalFormat df = new DecimalFormat("##0.###");

    private void init() {
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCoverDoiMK();
        forgerPass = new PanelDoiMK(frame);
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
                        cover.identifyRight(fractionCover * 100);
                    } else {
                        cover.changePassRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.identifyLeft(fraction * 100);
                    } else {
                        cover.changePassLeft((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    forgerPass.showChangePass(!isLogin);
                }
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(forgerPass, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
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
        bg.add(forgerPass, "width " + loginSize + "%, pos 1al 0 n 100%"); //  1al as 100%

//        cover.addEvent(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//
//            }
//        });
        cover.setEvenExit(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AnimationShowWindow.closeFrame(frame);
                dispose();
            }
        });
        //
        forgerPass.setEvenExit(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AnimationShowWindow.closeFrame(frame);
                dispose();
            }
        });
        forgerPass.addEventIndentify(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                identity(animator);
            }
        });
        forgerPass.addEventConfirm(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                forgerPass.changePass();
                if (PanelDoiMK.success) {
                    dispose();
                }
            }
        });
        forgerPass.setEventPWDPass(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    identity(animator);
                }
            }

        });
        forgerPass.setEventPWDConfirmPass(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    forgerPass.changePass();
                    if (PanelDoiMK.success) {
                        dispose();
                    }
                }
            }

        });
    }

    private void identity(Animator animator) {
        ModelTaiKhoan account = new ModelTaiKhoan();
        for (ModelTaiKhoan tk : tkDAO.selectAll()) {
            if (tk.getTaiKhoan().equals(Auth.user.getMaNV())) {
                account = tk;
                // System.out.println("Succ");
            }
        }
        if (forgerPass.identify(account)) {
            if (!animator.isRunning()) {
                animator.start();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(800, 385));
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

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
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
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
            java.util.logging.Logger.getLogger(FormDoiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDoiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDoiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDoiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDoiMK().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
