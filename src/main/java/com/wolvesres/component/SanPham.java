package com.wolvesres.component;

import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelSanPham;
import java.awt.event.ActionListener;

public class SanPham extends javax.swing.JPanel {

    public SanPham() {
        setOpaque(false);
        initComponents();
    }

    public SanPham(ModelSanPham product, ActionListener actionOrder) {
        initComponents();
        setOpaque(false);
        this.product = product;
        this.actionOrder = actionOrder;
        init();
    }

    private ModelSanPham product;
    private ActionListener actionOrder;

    public void setActionOrder(ActionListener actionOrder) {
        this.actionOrder = actionOrder;
    }

    public ModelSanPham getProduct() {
        return product;
    }

    public void setProduct(ModelSanPham product) {
        this.product = product;
    }

    public void init() {
        btnOrder.setToolTipText("Gọi món");
        setOpaque(false);
//        if (product.getPathAnh() != null) {
        imgHinhAnh.setIcon(XImage.readImageThucDon(product.getPathAnh()));
//        }
        lblTenSanPham.setText(product.getTenSP());
        lblGiaBan.setText(XFormatMoney.formatMoney(product.getGiaBan()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        imgHinhAnh = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        lblGiaBan = new javax.swing.JLabel();
        lblTenSanPham = new javax.swing.JLabel();
        btnOrder = new com.swing.custom.raven.RButton.RButtonOutLine();

        setOpaque(false);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel3.setBackground(new java.awt.Color(234, 143, 59));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imgHinhAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/bocube.jpg"))); // NOI18N
        rRoundPanel3.add(imgHinhAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 120, 120));

        rRoundPanel2.setBackground(new java.awt.Color(0, 0, 0));
        rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblGiaBan.setFont(new java.awt.Font("SansSerif", 2, 18)); // NOI18N
        lblGiaBan.setForeground(new java.awt.Color(204, 204, 204));
        lblGiaBan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGiaBan.setText("100.000 VND");
        lblGiaBan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        rRoundPanel2.add(lblGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 200, 30));

        lblTenSanPham.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblTenSanPham.setForeground(new java.awt.Color(255, 255, 255));
        lblTenSanPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenSanPham.setText("Bò cobe");
        rRoundPanel2.add(lblTenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 200, 30));

        btnOrder.setBorder(null);
        btnOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnOrder.setText("Order");
        btnOrder.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });
        rRoundPanel2.add(btnOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 80, 30));

        rRoundPanel3.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 200, 190));

        add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 250));
    }// </editor-fold>//GEN-END:initComponents

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        actionOrder.actionPerformed(evt);
    }//GEN-LAST:event_btnOrderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.raven.RButton.RButtonOutLine btnOrder;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar imgHinhAnh;
    private javax.swing.JLabel lblGiaBan;
    private javax.swing.JLabel lblTenSanPham;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    // End of variables declaration//GEN-END:variables
}
