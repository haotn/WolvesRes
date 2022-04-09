package com.wolvesres.component;

import com.wolvesres.helper.Auth;
import com.wolvesres.model.ModelBan;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class Ban extends javax.swing.JPanel {

    public Ban(ModelBan ban) {
        initComponents();
        this.entity = ban;
        init();
    }

    public Ban() {
        initComponents();
    }

    private ModelBan entity;

    public void init() {
        btnDelete.setToolTipText("Xóa bàn");
        btnUpdate.setToolTipText("Cập nhật thông tin bàn");
        lblTenBan.setText(entity.getTenBan());
        if (!Auth.isBoss() && !Auth.isManager()) {
            btnDelete.setVisible(false);
            btnUpdate.setVisible(false);
        }
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eventUpdate.actionPerformed(e);
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eventDelete.actionPerformed(e);
            }
        });
        btnOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eventOrder.actionPerformed(e);
            }
        });
    }

    private ActionListener eventOrder;
    private ActionListener eventUpdate;
    private ActionListener eventDelete;

    public ModelBan getEntity() {
        return entity;
    }

    public void setEntity(ModelBan entity) {
        this.entity = entity;
    }

    public void setEventUpdate(ActionListener addEventUpdate) {
        this.eventUpdate = addEventUpdate;
    }

    public void setEventDelete(ActionListener addEventDelete) {
        this.eventDelete = addEventDelete;
    }

    public void setEventOrder(ActionListener actionOrder) {
        this.eventOrder = actionOrder;
    }

    public void setTrangThai(boolean status) {
        if (status) {
            btnOrder.setToolTipText("Xem danh sách gọi món");
            lblImgTrangThai.setToolTipText("Trạng thái: đang hoạt động");
            btnOrder.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/checkTable.png")));
            lblImgTrangThai.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/isActive.png")));
        } else {
            btnOrder.setToolTipText("Gọi món cho bàn này");
            lblImgTrangThai.setToolTipText("Trạng thái: không hoạt động");
            btnOrder.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/orderNow.png")));
            lblImgTrangThai.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/isNotActive.png")));
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.setColor(new Color(230, 230, 230));
        grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }

    public void setTenBan(String text) {
        lblTenBan.setText(text);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        btnOrder = new com.swing.custom.raven.RButton.RButton();
        lblTenBan = new javax.swing.JLabel();
        lblImgTrangThai = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        lblImage = new javax.swing.JLabel();
        btnDelete = new com.swing.custom.raven.RButton.RButton();
        btnUpdate = new com.swing.custom.raven.RButton.RButton();

        setMaximumSize(new java.awt.Dimension(280, 190));
        setMinimumSize(new java.awt.Dimension(280, 190));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(280, 190));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel1.setBackground(new java.awt.Color(204, 204, 204));
        rRoundPanel1.setMaximumSize(new java.awt.Dimension(270, 190));
        rRoundPanel1.setMinimumSize(new java.awt.Dimension(270, 190));
        rRoundPanel1.setPreferredSize(new java.awt.Dimension(270, 190));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/orderNow.png"))); // NOI18N
        rRoundPanel1.add(btnOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 50, 50));

        lblTenBan.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblTenBan.setForeground(new java.awt.Color(0, 204, 102));
        lblTenBan.setText("Bàn");
        rRoundPanel1.add(lblTenBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 220, -1));

        lblImgTrangThai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/isNotActive.png"))); // NOI18N
        rRoundPanel1.add(lblImgTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 30, 30));

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/restaurant.png"))); // NOI18N
        rRoundPanel1.add(lblImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 150));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/bin.png"))); // NOI18N
        rRoundPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 25, 25));

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/edit.png"))); // NOI18N
        rRoundPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 32, 32));

        add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 190));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.raven.RButton.RButton btnDelete;
    private com.swing.custom.raven.RButton.RButton btnOrder;
    private com.swing.custom.raven.RButton.RButton btnUpdate;
    private javax.swing.JLabel lblImage;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar lblImgTrangThai;
    private javax.swing.JLabel lblTenBan;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    // End of variables declaration//GEN-END:variables
}
