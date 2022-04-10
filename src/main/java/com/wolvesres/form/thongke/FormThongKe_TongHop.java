package com.wolvesres.form.thongke;

import java.awt.event.MouseAdapter;
import javax.swing.JFrame;
/**
 * Comment các hàm
 * @author huynh
 *
 */
public class FormThongKe_TongHop extends javax.swing.JPanel {

    public FormThongKe_TongHop(JFrame frame) {
        initComponents();
        setOpaque(false);
        this.frame = frame;
    }
    JFrame frame;
    private MouseAdapter eventDoanhThu;
    private MouseAdapter eventKho;
    private MouseAdapter eventBan;
    private MouseAdapter eventSanPham;
    
    /**
     * Sự kiện nút để chuyển đến các bảng
     * @param eventKho
     */
    public void setEventKho(MouseAdapter eventKho) {
        this.eventKho = eventKho;
    }

    public void setEventBan(MouseAdapter eventBan) {
        this.eventBan = eventBan;
    }

    public void setEventSanPham(MouseAdapter eventSanPham) {
        this.eventSanPham = eventSanPham;
    }

    public void setEventDoanhThu(MouseAdapter eventDoanhThu) {
        this.eventDoanhThu = eventDoanhThu;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        lblTitle = new javax.swing.JLabel();
        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        DoanhThu = new com.wolvesres.component.KGradientPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Kho = new com.wolvesres.component.KGradientPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        SanPham = new com.wolvesres.component.KGradientPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Ban = new com.wolvesres.component.KGradientPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(21, 21, 21));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel1.setBackground(new java.awt.Color(6, 7, 13));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel4.setBackground(new java.awt.Color(0, 199, 135));
        rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("WolvesRes");
        rRoundPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 40, -1, -1));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel4.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 120, 110));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("THỐNG KÊ TỔNG HỢP");
        rRoundPanel4.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, -1));

        rRoundPanel1.add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 110));

        rRoundPanel3.setBackground(new java.awt.Color(0, 199, 135));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 640, 1170, 90));

        DoanhThu.setkEndColor(new java.awt.Color(102, 255, 102));
        DoanhThu.setkStartColor(new java.awt.Color(51, 255, 255));
        DoanhThu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DoanhThuMousePressed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/BAOCAO1.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel6.setText("THỐNG KÊ ");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel7.setText("DOANH THU");

        javax.swing.GroupLayout DoanhThuLayout = new javax.swing.GroupLayout(DoanhThu);
        DoanhThu.setLayout(DoanhThuLayout);
        DoanhThuLayout.setHorizontalGroup(
            DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DoanhThuLayout.setVerticalGroup(
            DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(DoanhThuLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rRoundPanel1.add(DoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 300, 150));

        Kho.setkEndColor(new java.awt.Color(255, 0, 51));
        Kho.setkStartColor(new java.awt.Color(255, 255, 0));
        Kho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                KhoMousePressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel9.setText("THỐNG KÊ ");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/BAOCAO2.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel8.setText("KHO");

        javax.swing.GroupLayout KhoLayout = new javax.swing.GroupLayout(Kho);
        Kho.setLayout(KhoLayout);
        KhoLayout.setHorizontalGroup(
            KhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(KhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        KhoLayout.setVerticalGroup(
            KhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KhoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(KhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(KhoLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rRoundPanel1.add(Kho, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, 300, 150));

        SanPham.setkEndColor(new java.awt.Color(255, 0, 255));
        SanPham.setkStartColor(new java.awt.Color(51, 204, 255));
        SanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SanPhamMousePressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel13.setText("THỐNG KÊ ");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/BAOCAO4.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel12.setText("SẢN PHẨM");

        javax.swing.GroupLayout SanPhamLayout = new javax.swing.GroupLayout(SanPham);
        SanPham.setLayout(SanPhamLayout);
        SanPhamLayout.setHorizontalGroup(
            SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        SanPhamLayout.setVerticalGroup(
            SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(SanPhamLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rRoundPanel1.add(SanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 420, 300, 150));

        Ban.setkEndColor(new java.awt.Color(255, 255, 102));
        Ban.setkStartColor(new java.awt.Color(51, 255, 51));
        Ban.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BanMousePressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel11.setText("THỐNG KÊ ");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/BAOCAO3.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel10.setText("BÀN");

        javax.swing.GroupLayout BanLayout = new javax.swing.GroupLayout(Ban);
        Ban.setLayout(BanLayout);
        BanLayout.setHorizontalGroup(
            BanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        BanLayout.setVerticalGroup(
            BanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BanLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(29, 29, 29)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rRoundPanel1.add(Ban, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, 300, 150));

        add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 730));
    }// </editor-fold>//GEN-END:initComponents

    private void DoanhThuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DoanhThuMousePressed
        eventDoanhThu.mousePressed(evt);
    }//GEN-LAST:event_DoanhThuMousePressed

    private void KhoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KhoMousePressed
        eventKho.mousePressed(evt);
    }//GEN-LAST:event_KhoMousePressed

    private void BanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BanMousePressed
        eventBan.mousePressed(evt);
    }//GEN-LAST:event_BanMousePressed

    private void SanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SanPhamMousePressed
        eventSanPham.mousePressed(evt);
    }//GEN-LAST:event_SanPhamMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.wolvesres.component.KGradientPanel Ban;
    private com.wolvesres.component.KGradientPanel DoanhThu;
    private com.wolvesres.component.KGradientPanel Kho;
    private com.wolvesres.component.KGradientPanel SanPham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblTitle;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
    // End of variables declaration//GEN-END:variables
}
