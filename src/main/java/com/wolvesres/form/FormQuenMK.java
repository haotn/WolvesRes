package com.wolvesres.form;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.form.quenmatkhau.PanelCoverQuenMK;
import com.wolvesres.form.quenmatkhau.PanelQuenMK;
import com.wolvesres.helper.AnimationShowWindow;
import com.wolvesres.helper.MailSender;
import com.wolvesres.helper.XImage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class FormQuenMK extends javax.swing.JFrame {

    JFrame frame;

    public FormQuenMK() {
        initComponents();
        setLocationRelativeTo(null);
        this.frame = this;
        AnimationShowWindow.showFrame(frame);
        init();
        setIconImage(XImage.getAppIcon());
    }
    private MigLayout layout;
    private PanelCoverQuenMK cover;
    private PanelQuenMK forgerPass;
    private boolean isLogin;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private final DecimalFormat df = new DecimalFormat("##0.###");
    TaiKhoanDAO tkdao = new TaiKhoanDAO();

    private void init() {
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCoverQuenMK();
        forgerPass = new PanelQuenMK(frame);
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
                        cover.restorePassRight(fractionCover * 100);
                    } else {
                        cover.forgetPassRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.restorePassLeft(fraction * 100);
                    } else {
                        cover.forgetPassLeft((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    forgerPass.showLayMatKhau(!isLogin);
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
        //bg.add(verify, "pos 0 0 100% 100%");
        bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        bg.add(forgerPass, "width " + loginSize + "%, pos 1al 0 n 100%"); //  1al as 100%

        //chuyen sang dang nhap
        cover.addEvent(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
//                if (!animator.isRunning()) {
//                    animator.start();
//                }
                dispose();
                new FormDangNhap().setVisible(true);;
            }
        });

        //kiem tra comfirm
        forgerPass.addEventGetCode(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (forgerPass.checkQuenMatKhau()) {
                    GuiMess();
                    code = ROptionDialog.showInput(frame, "Kiểm tra email và và nhập code vào bên dưới");
                    if (code.trim() != null) {
                        try {
                            if (checkCode()) {
                                if (!animator.isRunning()) {
                                    animator.start();
                                }
                            }
                        } catch (Exception ex) {
                            ROptionDialog.showAlert(frame, "Lỗi", "Mã xác nhận chưa chính xác vui lòng kiểm tra lại !!!", ROptionDialog.WARNING, Color.red, Color.black);
                        }
                    }

                }
            }
        });
        forgerPass.setKeyEventGetCode(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (forgerPass.checkQuenMatKhau()) {
                        GuiMess();
                        code = ROptionDialog.showInput(frame, "Kiểm tra email và và nhập code vào bên dưới");
                        if (code.trim() != null) {
                            try {
                                if (checkCode()) {
                                    if (!animator.isRunning()) {
                                        animator.start();
                                    }
                                }
                            } catch (Exception ex) {
                                ROptionDialog.showAlert(frame, "Lỗi", "Mã xác nhận chưa chính xác vui lòng kiểm tra lại !!!", ROptionDialog.WARNING, Color.red, Color.black);
                            }
                        }
                    }
                }
            }
        });
        //xac nhan taoj mk
        forgerPass.addEventConfirm(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (forgerPass.checkNewPass()) {
                	 
                    forgerPass.ChangPassword();
                    ROptionDialog.showAlert(frame, "Thông báo", "Tạo mật khẩu mới thành công!", ROptionDialog.NOTIFICATIONS_ACTIVE, new Color(0, 199, 135), Color.black);
                }
            }
        });
        forgerPass.setKeyEventConfirm(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (forgerPass.checkNewPass()) {
                        forgerPass.ChangPassword();
                        ROptionDialog.showAlert(frame, "Thông báo", "Tạo mật khẩu mới thành công!", ROptionDialog.NOTIFICATIONS_ACTIVE, new Color(0, 199, 135), Color.black);
                    }
                }
            }
        });

    }

    int randomInt;
    int second = 90;
    String code = null;
    Thread t;

    public void GuiMess() {
        for (int i = 1; i < 2; i++) {
            double randomDouble = Math.random();
            randomDouble = randomDouble * 1000000 + 1;
            randomInt = (int) randomDouble;
        }
        try {
            final String taiKhoan = "duanwolvesrespro1041@gmail.com";
			final String matKhau = "wolvesres1041";

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(taiKhoan, matKhau);
                }
            });

            String from = "duanwolvesrespro1041@gmail.com";
            String to = forgerPass.email;
            String subject = "Mã xác nhận ";
            String body = "Mã xác nhận của bạn là : " + randomInt;

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(body);

            //Transport.send(message);
            MailSender.queue(message);

            //JOptionPane.showMessageDialog(this, "Đã gởi mail thành công");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            e.printStackTrace();
            System.out.println(e);
        }
    }

    boolean checkCode() {
        if (Integer.parseInt(code.trim()) != randomInt) {
            JOptionPane.showMessageDialog(this, "Mã xác nhận chưa chính xác vui lòng kiểm tra lại !!!");
            return false;
        }
        return true;
    }

//    public void setTime() {
//        t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true) {
//                        second--;
//                        t.sleep(1000);  // interval duoc cung cap bang gia tri mili giay 
//                        lblSetTime.setText("Mã xác nhận còn hiệu lực trong " + String.valueOf(second) + "!");
//                        if (second == 0) {
//                            randomInt = 0;
//                            lblSetTime.setText("Mã xác nhận đã hết hiệu lực!");
//                            break;
//                        }
//
//                    }
//                } catch (Exception e) {
//                }
//            }
//        }
//        );
//        t.start();
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(800, 385));
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
            java.util.logging.Logger.getLogger(FormQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormQuenMK().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
