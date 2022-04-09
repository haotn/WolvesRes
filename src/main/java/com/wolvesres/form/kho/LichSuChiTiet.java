package com.wolvesres.form.kho;

import com.swing.custom.raven.RScrollbar.RScrollBarCustom;
import com.wolvesres.dao.ChiTietLichSuDAO;
import com.wolvesres.dao.LichSuDAO;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.model.ModelChiTietLichSu;
import com.wolvesres.model.ModelLichSu;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FPT
 */
public class LichSuChiTiet extends javax.swing.JDialog {

    JFrame frame;
    private List<ModelLichSu> list = new ArrayList<>();
    private List<ModelChiTietLichSu> listCTLS = new ArrayList<>();
    private List<ModelChiTietLichSu> listSeen = new ArrayList<>();
    LichSuDAO dao = new LichSuDAO();
    ChiTietLichSuDAO daoCT = new ChiTietLichSuDAO();
    int selectHD = 0;
    int selectedRow = -1;
    int slSeen = -1;

    public LichSuChiTiet(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        init();
        //txtFindHDChiTiet.setLabelText("Tìm Kiếm");
    }

    public void init() {
        jScrollPane1.setVerticalScrollBar(new RScrollBarCustom());
        initTableSeen();
        initTable();
        loadtoList();
        fillTable();
      //  loadListSeen();
        showDetail(0);
       // showDetailseen(0);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    //Tbl hóa đơn

    public void initTableSeen() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"Sản Phẩm", "Giá/SP", "Số Lượng", "Giá"});
        tblSeen.setModel(model);
        tblSeen.setOpaque(true);
        tblSeen.setFillsViewportHeight(true);
        tblSeen.fixTable(jScrollPane1);
        tblSeen.setForeground(new Color(0, 0, 0));
        tblSeen.setColumnAction(10);
    }
//
//    //

    public void loadListSeen() {
        listSeen.clear();
        for (ModelChiTietLichSu lsct : listCTLS) {
            if (selectHD == lsct.getIdLS()) {
                listSeen.add(lsct);
            }
        }
    }
//

    public void fillTableSeen() {
        loadListSeen();
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"Sản Phẩm", "Giá/SP", "Số Lượng", "Giá"});
        tblSeen.setModel(model);
        for (ModelChiTietLichSu hd : listSeen) {
            tblSeen.addRow(hd.toRowTableCTLS());
        }

    }

    public void showDetail(int select) {
        if (select >= 0) {
            ModelLichSu emp = list.get(select);
            String tile = emp.isNhapKho() ? "Nhập Kho" : "Xuất kho";
            ////////////////////////////////////////
            lblSolo.setText(emp.idkho(emp.getId()) + "");
            lblTitle.setText("Chi Tiết Lịch Sử " + tile + "");
            lblTenNV.setText(emp.NguoiNhap(emp.getNguoiNhap()));
            lblMaNV.setText(emp.getNguoiNhap());
            lblHinhThuc.setText(emp.isNhapKho() ? "Nhập Kho" : "Xuất kho");
            lblNgaytao.setText(emp.toYMD(emp.getThoiGian()));
            lblTongtien.setText(XFormatMoney.formatMoney(emp.getTongTien()));
        }
    }

    //
    public void showDetailseen(int select) {
        ModelChiTietLichSu entity = listSeen.get(select);
        lblDanhMuc.setText(entity.getTenDanhMuc());
        lblDVT.setText(entity.getTenDonViTinh());
        lblMatHang.setText(entity.getTenLoaiSanPham());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///Hóa đơn
    //tbl hóa đon chi tiết
    private void initTable() {
        tblLS.setOpaque(true);
        tblLS.setBackground(new Color(255,255,255));
        tblLS.setFillsViewportHeight(true);
        tblLS.fixTable(jScrollPane1);
        tblLS.setFont(new Font("SansSerif", 1, 12));
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"ID kho", "Hình Thức", "Thời Gian", "Người Nhập", "Tổng Tiền"});
        tblLS.setModel(model);
        tblLS.setColumnAction(10);
    }

    //
    //
    public void loadtoList() {
        list.addAll(dao.selectAll());
        listCTLS.addAll(daoCT.selectAll());
    }

//    public void loadwhitetoList() {
//        whiteList.clear();
//        for (ModelLichSu hd : list) {
//            whiteList.add(hd);
//        }
//    }
    public void fillTable() {
        //loadwhitetoList();
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"ID LS", "Hình Thức", "Thời Gian", "Người Nhập", "Tổng Tiền"});
        //modela.setRowCount(0);
        tblLS.setModel(model);
        for (ModelLichSu hd : list) {
            tblLS.addRow(hd.toRowTableHDCT());
        }

    }

    
     private void timKiem() {
        String tuKhoa = txtFindHDChiTiet.getText().trim();
        list.clear();
        if (tuKhoa.length() != 0) {
            list.addAll(dao.TIMKIEM(tuKhoa));
            DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"ID kho", "Hình Thức", "Thời Gian", "Người Nhập", "Tổng Tiền"});
            tblLS.setModel(model);
            model.setRowCount(0);
            for (ModelLichSu dvt : list) {
                tblLS.addRow(dvt.toRowTableHDCT());
            }
        }else {
            list.addAll(dao.selectAll());
            fillTable();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        txtFindHDChiTiet = new com.swing.custom.raven.RTextField.RTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLS = new com.wolvesres.swing.table.Table();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        btnBack = new com.swing.custom.raven.RButton.RButton();
        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        jLabel2 = new javax.swing.JLabel();
        rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
        lblTitle = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblHinhThuc = new javax.swing.JLabel();
        lblNgaytao = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblTongtien = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSeen = new com.wolvesres.swing.table.Table();
        jLabel26 = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblSolo = new javax.swing.JLabel();
        lblDanhMuc = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblDVT = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblMatHang = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(new java.awt.Point(220, 60));

        rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));
        rRoundPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        rRoundPanel1.setMinimumSize(new java.awt.Dimension(1170, 730));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtFindHDChiTiet.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFindHDChiTiet.setLabelText("Tìm kiếm lịch sử chi tiết");
        txtFindHDChiTiet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindHDChiTietKeyReleased(evt);
            }
        });
        rRoundPanel1.add(txtFindHDChiTiet, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 650, -1));

        tblLS.setAutoCreateRowSorter(true);
        tblLS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblLS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblLSMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblLS);

        rRoundPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 169, 650, 580));

        rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
        btnBack.setText("Hủy");
        btnBack.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        rRoundPanel2.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 120, -1));

        rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 750, 650, 50));

        rRoundPanel3.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LỊCH SỬ");
        rRoundPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, -1, 50));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel3.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 100, 80));

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("WolvesRes");
        rRoundPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

        rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 120));

        rRoundPanel4.setBackground(new java.awt.Color(171, 240, 191));
        rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTitle.setText("Lịch Sử Chi Tiết");
        rRoundPanel4.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        jLabel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        rRoundPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 440, 10));

        lblHinhThuc.setText("Nhập Kho");
        rRoundPanel4.add(lblHinhThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, -1, -1));

        lblNgaytao.setText("01-01-2021");
        rRoundPanel4.add(lblNgaytao, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, -1, -1));

        jLabel8.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 13)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/image-removebg-preview.png"))); // NOI18N
        jLabel8.setText("WOLVESRES");
        rRoundPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel11.setText("Ngày Tạo:");
        rRoundPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, -1, -1));

        lblTongtien.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTongtien.setText("200000");
        rRoundPanel4.add(lblTongtien, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 720, 250, -1));

        jLabel20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        rRoundPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 710, 440, 20));

        jLabel22.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        rRoundPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 440, 10));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel24.setText("Tổng Tiền:");
        rRoundPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 720, -1, -1));
        rRoundPanel4.add(lblMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 90, 20));

        tblSeen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSeen.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblSeen.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblSeen.setTableBackgoundSelectRow(new java.awt.Color(0, 0, 0));
        tblSeen.setTableForegroundSelectRow(new java.awt.Color(255, 255, 255));
        tblSeen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSeenMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblSeen);
        if (tblSeen.getColumnModel().getColumnCount() > 0) {
            tblSeen.getColumnModel().getColumn(0).setResizable(false);
            tblSeen.getColumnModel().getColumn(1).setResizable(false);
            tblSeen.getColumnModel().getColumn(2).setResizable(false);
            tblSeen.getColumnModel().getColumn(3).setResizable(false);
        }

        rRoundPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, 410));

        jLabel26.setText("Hình Thức");
        rRoundPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));
        rRoundPanel4.add(lblTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 160, 20));

        jLabel28.setText("Người Tạo:");
        rRoundPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, 20));

        lblSolo.setText("1");
        rRoundPanel4.add(lblSolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 20, -1));

        lblDanhMuc.setText("ABC");
        rRoundPanel4.add(lblDanhMuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 130, -1));

        jLabel34.setText("Số Lô(ID Lịch sử):");
        rRoundPanel4.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jLabel35.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel35.setText("Thông Tin Nhập/Xuất:");
        rRoundPanel4.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        jLabel36.setText("DVT:");
        rRoundPanel4.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 40, -1));

        lblDVT.setText("ABC");
        rRoundPanel4.add(lblDVT, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 250, 40, -1));

        jLabel38.setText("Loại Hàng:");
        rRoundPanel4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, -1, -1));

        lblMatHang.setText("Mặt Hàng");
        rRoundPanel4.add(lblMatHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 70, -1));

        jLabel37.setText("Danh Mục:");
        rRoundPanel4.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        jLabel39.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel39.setText("Thông Tin Sản Phẩm:");
        rRoundPanel4.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 30));

        rRoundPanel1.add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 500, 770));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1170, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void tblLSMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLSMousePressed
        selectedRow = tblLS.getSelectedRow();
        if (selectedRow >= 0) {
            selectHD = (int) tblLS.getValueAt(selectedRow, 0);
            showDetail(selectedRow);
            fillTableSeen();
        }
    }//GEN-LAST:event_tblLSMousePressed

    private void txtFindHDChiTietKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindHDChiTietKeyReleased
        timKiem();
    }//GEN-LAST:event_txtFindHDChiTietKeyReleased

    private void tblSeenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSeenMousePressed
        slSeen = tblSeen.getSelectedRow();
        if (slSeen >= 0) {
            showDetailseen(slSeen);
        }
    }//GEN-LAST:event_tblSeenMousePressed

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
            java.util.logging.Logger.getLogger(LichSuChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LichSuChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LichSuChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LichSuChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LichSuChiTiet dialog = new LichSuChiTiet(new javax.swing.JFrame(), true);
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
    private com.swing.custom.raven.RButton.RButton btnBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDVT;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblHinhThuc;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMatHang;
    private javax.swing.JLabel lblNgaytao;
    private javax.swing.JLabel lblSolo;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTongtien;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
    private com.wolvesres.swing.table.Table tblLS;
    private com.wolvesres.swing.table.Table tblSeen;
    private com.swing.custom.raven.RTextField.RTextField txtFindHDChiTiet;
    // End of variables declaration//GEN-END:variables
}
