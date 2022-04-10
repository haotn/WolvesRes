package com.wolvesres.form.ban;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.BanOrderDAO;
import com.wolvesres.dao.OrderDAO;
import com.wolvesres.form.FormBan;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelBanOrder;
import com.wolvesres.model.ModelOrder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;

/**
 * Gộp 2 bàn laij cùng 1 khu bàn
 *Phuong thuc: gopbandata, gopbannodata
 *Liên quan: ModelBanOder
 * @author 
 */
public class jDialogGopBan extends javax.swing.JDialog {

    JFrame frame;
    FormBan form = new FormBan(frame);
    List<ModelBan> list = new ArrayList<>();
    List<ModelBan> listtemp = new ArrayList<>();
    List<ModelBan> listKB = new ArrayList<>();
    List<ModelBanOrder> listBanOder = new ArrayList<>();
    BanOrderDAO banoderdao = new BanOrderDAO();
    OrderDAO oderDao = new OrderDAO();

    public jDialogGopBan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        init();
    }

    public void init() {
        loadToList();
        fillConboboxBanHienTai();
    }
//Kiểm tra bàn đã được oder hay chưa có ban>< null
    public ModelBanOrder kiemTraOder(String maBan) {
        ModelBanOrder ban = new ModelBanOrder();
        boolean exists = false;
        for (ModelBanOrder modelBanOrder : listBanOder) {
            if (modelBanOrder.getMaBan().equals(maBan)) {
                ban = modelBanOrder;
                exists = true;
                break;
            }
        }
        if (exists) {
            return ban;
        } else {
            return null;
        }
    }
// load dữ liệu từ data vào list 
    public void loadToList() {
        listBanOder.clear();
        listBanOder.addAll(banoderdao.selectAll());
        if (listBanOder.size() >= 0) {
//            for (int i = 0; i < listBanOder.size(); i++) {
//
//            }
            List<ModelBan> listdel = new ArrayList<>();
            for (ModelBan modelBan : form.getListBan()) {
                ModelBanOrder banOrder = kiemTraOder(modelBan.getMaBan());
                if (banOrder != null) {
                    if (banOrder.getMaBanGop().trim().length() == 0) {
                        list.add(modelBan);
                    } else {
                        boolean exists = false;
                        for (ModelBan modelBan1 : form.getListBan()) {
                            if (banOrder.getMaBanGop().equals(modelBan1.getMaBan())) {
                                listdel.add(modelBan1);
                                exists = true;
                            }
                        }
                    }
                } else {
                    list.add(modelBan);
                }
            }
            
            if (listdel.size() > 0) {
                for (int i = 0; i < listdel.size(); i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if (listdel.get(i).getMaBan().equals(list.get(j).getMaBan())) {
                            list.remove(list.get(j));
                        }
                    }
                }
            }
        } else {
            for (ModelBan modelBan : form.getListBan()) {
                list.add(modelBan);
            }
        }

    }

    //load dữ liệu vào list khu bàn muốn gộp
    public void loadListKB(String maban, String kb) {
        listKB.clear();
        listtemp.clear();
        listBanOder.clear();
        listBanOder.addAll(banoderdao.selectAll());
        if (listBanOder.size() >= 0) {
            for (int i = 0; i < listBanOder.size(); i++) {

            }
            List<ModelBan> listdel = new ArrayList<>();
            for (ModelBan modelBan : form.getListBan()) {
                ModelBanOrder banOrder = kiemTraOder(modelBan.getMaBan());
                if (banOrder != null) {
                    if (banOrder.getMaBanGop().trim().length() == 0) {
                        listtemp.add(modelBan);
                    } else {
                        boolean exists = false;
                        for (ModelBan modelBan1 : form.getListBan()) {
                            if (banOrder.getMaBanGop().equals(modelBan1.getMaBan())) {
                                listdel.add(modelBan1);
                                exists = true;
                            }
                        }
                    }
                } else {
                    listtemp.add(modelBan);
                }
            }
            if (listdel.size() > 0) {
                for (int i = 0; i < listdel.size(); i++) {
                    for (int j = 0; j < listtemp.size(); j++) {
                        if (listdel.get(i).getMaBan().equals(listtemp.get(j).getMaBan())) {
                            listtemp.remove(listtemp.get(j));
                        }
                    }
                }
            }
        } else {
            for (ModelBan modelBan : form.getListBan()) {
                listtemp.add(modelBan);
            }
        }
        for (ModelBan modelBan : listtemp) {
            if (kb.equals(modelBan.getMaKhuBan()) && !maban.equals(modelBan.getMaBan())) {
                listKB.add(modelBan);
            }
        }
    }

//    fill lên combobox bàn hiện tại
    private void fillConboboxBanHienTai() {
        DefaultComboBoxModel<ModelBan> modelCboChuyenBan = (DefaultComboBoxModel<ModelBan>) cboBanHienTai.getModel();
        cboBanHienTai.setModel(modelCboChuyenBan);
        modelCboChuyenBan.removeAllElements();
        for (ModelBan ban : list) {
            modelCboChuyenBan.addElement(ban);
        }
    }
//fill lên conbobox bàn muốn gộp
    private void fillConboboxBanMuonGop() {
        DefaultComboBoxModel<ModelBan> modelCboBanGop = (DefaultComboBoxModel<ModelBan>) cboBanMuonGop.getModel();
        cboBanMuonGop.setModel(modelCboBanGop);
        modelCboBanGop.removeAllElements();
        for (ModelBan ban : listKB) {
            modelCboBanGop.addElement(ban);
        }
    }

    public List<ModelBanOrder> getListBanOder() {
        return listBanOder;
    }

    public void setListBanOder(List<ModelBanOrder> listBanOder) {
        this.listBanOder = listBanOder;
    }

//    kiểm tra mã bàn đã được oder true><false
    public boolean isExistsListOder(String maBan) {
        ModelOrder od = oderDao.selectById(maBan);
        if (od == null) {
            return false;
        } else {
            return true;
        }
    }

//    gộp dữ liệu của 2 bàn 
    public void MergeData(ModelBan banht, ModelBan banGop) {
        String maBan = banht.getMaBan();
        String maBG = banGop.getMaBan();
        List<ModelOrder> list1 = new ArrayList<>();
        List<ModelOrder> list2 = new ArrayList<>();
        list1.addAll(oderDao.selectByBanAll(maBan));
        list2.addAll(oderDao.selectByBanAll(maBG));

        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list2.get(j).getSoLuong() > 0) {
                    if (list1.get(i).getMaSP().equals(list2.get(j).getMaSP())) {
                        //System.out.println(list1.get(i).getSoLuong());
                        list1.get(i).setSoLuong(list1.get(i).getSoLuong() + list2.get(j).getSoLuong());
                        // System.out.println(list1.get(i).getSoLuong());
                        list2.get(j).setSoLuong(0);
                        //System.out.println(list2.get(j).getSoLuong());
                        oderDao.updateByIdOder(list1.get(i));
                    }
                }
            }
        }
        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i).getSoLuong() == 0) {
                oderDao.deleteByIdOder(list2.get(i).getId());
            }
        }

        oderDao.updateMaBan(maBan, maBG);
    }
/**
 * gobandata, gopbannodata
 * @param ModelBanOder
 * */
//    gộp bàn
    public void gopBan() {
        ModelBanOrder banOder = new ModelBanOrder();
        ModelBan banht = (ModelBan) cboBanHienTai.getSelectedItem();
        ModelBan bangop = (ModelBan) cboBanMuonGop.getSelectedItem();
        String maBan = banht.getMaBan();
        String gopBan = bangop.getMaBan();
        if (listBanOder.size() == 0) {
            System.out.println("in1");
            banOder.setMaBan(maBan);
            banOder.setGhiChu(null);
            banOder.setMaVoucher("NOVOUCHER");
            banOder.setMaBanGop(gopBan);
            banoderdao.insert(banOder);
        } else {
            boolean xet = false;
            for (ModelBanOrder modelBanOrder : listBanOder) {
                if (maBan.equals(modelBanOrder.getMaBan())) {
                    xet = true;
                    break;
                } else if (!maBan.equals(modelBanOrder.getMaBan())) {
                    xet = false;
                }
            }
            //
            if ((!isExistsListOder(maBan) && !isExistsListOder(gopBan)) || !isExistsListOder(maBan)) {
            } else {
                MergeData(banht, bangop);
            }
            //check dl
            if (xet) {
                ModelBanOrder updeBanOrder = banoderdao.selectById(maBan);
                updeBanOrder.gopbandata(gopBan);
            } else {
                banOder.gopnodata(maBan, gopBan);
                 
            }

        }
        listBanOder.clear();
        listBanOder.addAll(banoderdao.selectAll());
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
        cboBanMuonGop = new com.swing.custom.raven.RComboBox.RComboBoxSuggestion();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rRoundPanel3.setBackground(new java.awt.Color(209, 220, 208));
        rRoundPanel3.setPreferredSize(new java.awt.Dimension(570, 380));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel5.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Gộp Bàn");
        rRoundPanel5.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel5.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 100, 80));

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("WolvesRes");
        rRoundPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, -1, -1));

        rRoundPanel3.add(rRoundPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 120));

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
                .addGap(63, 63, 63)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        rRoundPanel1Layout.setVerticalGroup(
            rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rRoundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rRoundPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(rRoundPanel1Layout.createSequentialGroup()
                        .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );

        rRoundPanel3.add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 580, 60));

        cboBanHienTai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBanHienTaiActionPerformed(evt);
            }
        });
        rRoundPanel3.add(cboBanHienTai, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 180, 30));

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Bàn hiện tại:");
        rRoundPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));
        rRoundPanel3.add(cboBanMuonGop, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 180, 30));

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Bàn muốn gọp:");
        rRoundPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rRoundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        if (ROptionDialog.showConfirm(frame,"Xác nhận", "Bạn có chác muốn gộp bàn không?", ROptionDialog.WARNING, Color.yellow, Color.black)) {
            gopBan();
            dispose();
        }


    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void cboBanHienTaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBanHienTaiActionPerformed
        int chon = cboBanHienTai.getSelectedIndex();
        ModelBan b = list.get(chon);
        loadListKB(b.getMaBan(), b.getMaKhuBan());
        fillConboboxBanMuonGop();
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
            java.util.logging.Logger.getLogger(jDialogGopBan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDialogGopBan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDialogGopBan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDialogGopBan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDialogGopBan dialog = new jDialogGopBan(new javax.swing.JFrame(), true);
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
    private com.swing.custom.raven.RComboBox.RComboBoxSuggestion cboBanMuonGop;
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
