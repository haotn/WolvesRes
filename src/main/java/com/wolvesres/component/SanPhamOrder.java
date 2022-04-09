package com.wolvesres.component;

import com.swing.custom.raven.RButton.RButton;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelOrder;
import com.wolvesres.model.ModelSanPham;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class SanPhamOrder extends javax.swing.JPanel {

    public SanPhamOrder() {
        initComponents();
    }

    public SanPhamOrder(ModelOrder product, ActionListener actionEdit, ActionListener actionDelete) {
        initComponents();
        this.product = product;
        this.actionEdit = actionEdit;
        this.actionDelete = actionDelete;
        init();
    }

    public void init() {
        ((DefaultEditor) spnSoLuong.getEditor()).getTextField().setEditable(false);
        txtGhiChu.setEditable(false);
        imgHinhAnh.setIcon(XImage.readImageThucDon(product.getSanPham().getPathAnh()));
        lblTenSanPham.setText(product.getSanPham().getTenSP());
        lblGiaBan.setText(XFormatMoney.formatMoney(product.getGia()));
        spnSoLuong.setValue(product.getSoLuong());
        lblTongTien.setText(XFormatMoney.formatMoney(product.getGia() * product.getSoLuong()));
        hideSpinnerArrow(spnSoLuong, false);
        btnDelete.setToolTipText("Xóa khỏi danh sách gọi món");
        btnEdit.setToolTipText("Cập nhật số lượng");
    }
    private boolean isEdit = false;
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();
    private ActionListener actionEdit;
    private ActionListener actionDelete;
    private ModelOrder product = new ModelOrder();

    private ModelSanPham getSanPhamByMaSP(String maSP) {
        ModelSanPham sanPham = new ModelSanPham();
        for (ModelSanPham modelSanPham : sanPhamDAO.selectAll()) {
            if (maSP.equals(modelSanPham.getMaSP())) {
                sanPham = modelSanPham;
            }
        }
        return sanPham;
    }

    public ModelOrder getProduct() {
        return product;
    }

    public void setProduct(ModelOrder product) {
        this.product = product;
    }

//    public boolean isEdit() {
//        return edit;
//    }
//
//    public void setEdit(boolean edit) {
//        this.edit = edit;
//    }
    public void setActionEdit(ActionListener actionEdit) {
        this.actionEdit = actionEdit;
    }

    public void setActionDelete(ActionListener actionDelete) {
        this.actionDelete = actionDelete;
    }

    public boolean checkAmount() {
        try {
            Integer.parseInt(String.valueOf(spnSoLuong.getValue()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateProduct() {
        if (checkAmount()) {
            product.setSoLuong(Integer.parseInt(String.valueOf(spnSoLuong.getValue())));
            return true;
        }
        return false;
    }

    public boolean isEdit() {
        if (isEdit) {
            hideSpinnerArrow(spnSoLuong, false);
            txtGhiChu.setEditable(false);
            ((DefaultEditor) spnSoLuong.getEditor()).getTextField().setEditable(false);
            btnEdit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/editGra.png")));
            isEdit = false;
        } else {
            hideSpinnerArrow(spnSoLuong, true);
            txtGhiChu.setEditable(true);
            ((DefaultEditor) spnSoLuong.getEditor()).getTextField().setEditable(true);
            btnEdit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/saveEdit.png")));
            isEdit = true;
        }
        return isEdit;
    }

    public void hideSpinnerArrow(JSpinner spinner, final boolean enable) {
        Dimension dim = spinner.getPreferredSize();
        dim.width = 45;
        spinner.setUI(new BasicSpinnerUI() {
            @Override
            protected Component createNextButton() {
                if (enable) {
                    return super.createNextButton();
                } else {
                    return null;
                }
            }

            @Override
            protected Component createPreviousButton() {

                if (enable) {
                    return super.createPreviousButton();
                } else {
                    return null;
                }
            }
        });
        spinner.setPreferredSize(dim);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        lblTenSanPham = new javax.swing.JLabel();
        imgHinhAnh = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        lblGiaBan = new javax.swing.JLabel();
        btnEdit = new com.swing.custom.raven.RButton.RButton();
        btnDelete = new com.swing.custom.raven.RButton.RButton();
        spnSoLuong = new javax.swing.JSpinner();
        lblTongTien = new javax.swing.JLabel();

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(51, 51, 51)));

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setRows(5);
        txtGhiChu.setToolTipText("Ghi chú");
        txtGhiChu.setWrapStyleWord(true);
        txtGhiChu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(255, 255, 255)));
        txtGhiChu.setSelectionColor(new java.awt.Color(234, 125, 105));
        jScrollPane1.setViewportView(txtGhiChu);

        setOpaque(false);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel1.setBackground(new java.awt.Color(0, 0, 0));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTenSanPham.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        lblTenSanPham.setForeground(new java.awt.Color(255, 255, 255));
        lblTenSanPham.setText("Bò Kobe");
        rRoundPanel1.add(lblTenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 270, -1));

        imgHinhAnh.setBackground(new java.awt.Color(255, 255, 255));
        imgHinhAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/bocube.jpg"))); // NOI18N
        rRoundPanel1.add(imgHinhAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 80));

        lblGiaBan.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        lblGiaBan.setForeground(new java.awt.Color(153, 153, 153));
        lblGiaBan.setText("500,000 VND");
        rRoundPanel1.add(lblGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 110, -1));

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/editGra.png"))); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        rRoundPanel1.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 32, 32));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/bin.png"))); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        rRoundPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 25, 25));

        spnSoLuong.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        spnSoLuong.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        spnSoLuong.setBorder(null);
        rRoundPanel1.add(spnSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 60, 40));

        lblTongTien.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(255, 255, 255));
        lblTongTien.setText("jj");
        rRoundPanel1.add(lblTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 220, 30));

        add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 80));
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        actionEdit.actionPerformed(evt);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        actionDelete.actionPerformed(evt);
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.raven.RButton.RButton btnDelete;
    private com.swing.custom.raven.RButton.RButton btnEdit;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar imgHinhAnh;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGiaBan;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JLabel lblTongTien;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private javax.swing.JSpinner spnSoLuong;
    private javax.swing.JTextArea txtGhiChu;
    // End of variables declaration//GEN-END:variables
}
