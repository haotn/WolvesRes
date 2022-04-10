package com.wolvesres.form.ban;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.BanDAO;
import com.wolvesres.form.FormBan;
import com.wolvesres.main.Main;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelBanOrder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;

/**
 * comment các hàm
 * @author FPT
 */
public class jDialogChuyenBan extends javax.swing.JDialog {

    JFrame frame;
    DefaultComboBoxModel<ModelBan> modelCboChuyenBan;
    DefaultComboBoxModel<ModelBan> modelCboChuyenBanMuon;
    List<ModelBan> listBanForm = new ArrayList<>();
    List<ModelBan> listBanTo = new ArrayList<>();
    BanDAO dao = new BanDAO();
    ModelBan banFrom = new ModelBan();
    ModelBan banTo = new ModelBan();
    private boolean dispose = false;
    FormBan form = new FormBan(frame);

    public jDialogChuyenBan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        loadToList();
        fillConboboxBanHienTai();
        fillConboboxBanMuonChuyen();
    }

    public boolean isDispose() {
        return dispose;
    }
    
    /**
     * Load dữ liệu lên bảng
     */
    public void loadToList(){
        for(ModelBan ban : form.getListBan()){
            boolean exists = false;
           for(ModelBanOrder banto : Main.listBanOrder){
               if(ban.getMaBan().equals(banto.getMaBan())){
                   exists = true;
               }
           }
            if (exists) {
                listBanForm.add(ban);
            }else{
                listBanTo.add(ban);
            }
        }
    }

    /**
     * fill dữ liệu các bàn đã oder lên combobox
     */
    private void fillConboboxBanHienTai() {
        modelCboChuyenBan = (DefaultComboBoxModel<ModelBan>) cboBanHienTai.getModel();
        cboBanHienTai.setModel(modelCboChuyenBan);
        modelCboChuyenBan.removeAllElements();
        for (ModelBan ban : listBanForm){
            modelCboChuyenBan.addElement(ban);
        }
    }

    /**
     * fill dữ liệu các bàn trống lên combobox
     */
    private void fillConboboxBanMuonChuyen() {
        modelCboChuyenBanMuon = (DefaultComboBoxModel<ModelBan>) cboBanMuonChuyen.getModel();
        cboBanMuonChuyen.setModel(modelCboChuyenBanMuon);
        modelCboChuyenBanMuon.removeAllElements();
        for (ModelBan ban : listBanTo){
            modelCboChuyenBanMuon.addElement(ban);
        }
    }

    /**
     * Lấy mã bàn
     * @param maban
     * @return
     */
    private ModelBan getbanByMaBan(String maban) {
        ModelBan ban = new ModelBan();
        for (ModelBan model : form.getListBan()) {
            if (maban.equals(model.getMaBan())) {
                ban = model;
                break;
            }
        }
        return ban;
    }

    public ModelBan getBanTo() {
        return banTo;
    }

    public ModelBan getBanFrom() {
        return banFrom;
    }

    /**
     * Hàm get form
     */
    public void getForm() {
        ModelBan Ban1 = (ModelBan) cboBanMuonChuyen.getSelectedItem();
        banTo = Ban1;

        ModelBan Ban2 = (ModelBan) cboBanHienTai.getSelectedItem();
        banFrom = Ban2;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        rRoundPanel5 = new com.swing.custom.raven.RPanel.RRoundPanel();
        lblTitle = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        jLabel2 = new javax.swing.JLabel();
        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        btnXacNhan = new com.swing.custom.raven.RButton.RButton();
        btnHuy = new com.swing.custom.raven.RButton.RButton();
        cboBanHienTai = new com.swing.custom.raven.RComboBox.RComboBoxSuggestion();
        jLabel3 = new javax.swing.JLabel();
        cboBanMuonChuyen = new com.swing.custom.raven.RComboBox.RComboBoxSuggestion();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rRoundPanel3.setBackground(new java.awt.Color(209, 220, 208));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel5.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Chuyển Bàn");
        rRoundPanel5.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel5.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 100, 80));

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("WolvesRes");
        rRoundPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, -1, -1));

        rRoundPanel3.add(rRoundPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 120));

        rRoundPanel1.setBackground(new java.awt.Color(71, 92, 77));

        btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/confirmation.png"))); // NOI18N
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rRoundPanel1Layout = new javax.swing.GroupLayout(rRoundPanel1);
        rRoundPanel1.setLayout(rRoundPanel1Layout);
        rRoundPanel1Layout.setHorizontalGroup(
            rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rRoundPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 273, Short.MAX_VALUE)
                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        rRoundPanel1Layout.setVerticalGroup(
            rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rRoundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rRoundPanel3.add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 570, 60));

        cboBanHienTai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBanHienTaiActionPerformed(evt);
            }
        });
        rRoundPanel3.add(cboBanHienTai, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 180, 30));

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Bàn hiện tại:");
        rRoundPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));
        rRoundPanel3.add(cboBanMuonChuyen, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 180, 30));

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Bàn muốn chuyển:");
        rRoundPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rRoundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        if (ROptionDialog.showConfirm(frame,"Xác nhận", "Bạn có chắc muốn chuyển bàn không?",ROptionDialog.WARNING, Color.yellow, Color.black)) {
            getForm();
            dispose();
        }
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        dispose = true;
        dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void cboBanHienTaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBanHienTaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboBanHienTaiActionPerformed

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
            java.util.logging.Logger.getLogger(jDialogChuyenBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDialogChuyenBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDialogChuyenBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDialogChuyenBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDialogChuyenBan dialog = new jDialogChuyenBan(new javax.swing.JFrame(), true);
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
    private com.swing.custom.raven.RButton.RButton btnHuy;
    private com.swing.custom.raven.RButton.RButton btnXacNhan;
    private com.swing.custom.raven.RComboBox.RComboBoxSuggestion cboBanHienTai;
    private com.swing.custom.raven.RComboBox.RComboBoxSuggestion cboBanMuonChuyen;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblTitle;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel5;
    // End of variables declaration//GEN-END:variables
}
