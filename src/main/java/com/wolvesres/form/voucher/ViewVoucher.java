package com.wolvesres.form.voucher;

import com.wolvesres.form.FormVoucher;
import com.wolvesres.helper.XImage;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ViewVoucher extends javax.swing.JDialog {

    public ViewVoucher(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.frame = (JFrame) parent;
        setLocationRelativeTo(null);

    }
    private JFrame frame;
    private FormVoucher formParent = new FormVoucher(frame);
    private JFileChooser fileChooser ;

    private void init() {
        ImageIcon pic = new ImageIcon(XImage.readImageQRCode(getPath()).getImage().getScaledInstance(lblQR.getWidth(), lblQR.getHeight(), Image.SCALE_SMOOTH));
        lblQR.setIcon(pic);
        //System.out.println(path);
    }
    private String path = null;

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        lblQR = new javax.swing.JLabel();
        rButton1 = new com.swing.custom.raven.RButton.RButton();
        rButton2 = new com.swing.custom.raven.RButton.RButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        rRoundPanel1.setBackground(new java.awt.Color(255, 255, 255));

        rButton1.setBackground(new java.awt.Color(51, 51, 51));
        rButton1.setForeground(new java.awt.Color(255, 255, 255));
        rButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
        rButton1.setText("Đóng");
        rButton1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        rButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton1ActionPerformed(evt);
            }
        });

        rButton2.setBackground(new java.awt.Color(51, 51, 51));
        rButton2.setForeground(new java.awt.Color(255, 255, 255));
        rButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/exportFile.png"))); // NOI18N
        rButton2.setText("Mở vị trí tệp");
        rButton2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        rButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rRoundPanel1Layout = new javax.swing.GroupLayout(rRoundPanel1);
        rRoundPanel1.setLayout(rRoundPanel1Layout);
        rRoundPanel1Layout.setHorizontalGroup(
            rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rRoundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rRoundPanel1Layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addComponent(rButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165))
        );
        rRoundPanel1Layout.setVerticalGroup(
            rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rRoundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQR, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_rButton1ActionPerformed

    private void rButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton2ActionPerformed
        fileChooser = new JFileChooser("QRCode/"+getPath());
        //    fileChooser.setCurrentDirectory(new File(path));
        fileChooser.showDialog(formParent, "Close");
    }//GEN-LAST:event_rButton2ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        init();
    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(ViewVoucher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewVoucher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewVoucher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewVoucher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewVoucher dialog = new ViewVoucher(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblQR;
    private com.swing.custom.raven.RButton.RButton rButton1;
    private com.swing.custom.raven.RButton.RButton rButton2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    // End of variables declaration//GEN-END:variables
}
