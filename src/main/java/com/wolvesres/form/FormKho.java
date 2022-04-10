package com.wolvesres.form;

import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.form.kho.JDialogNhapKho;
import com.wolvesres.form.kho.JDialogXuatKho;
import com.wolvesres.form.kho.LichSuChiTiet;
import com.wolvesres.helper.Auth;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelSanPham;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class FormKho extends javax.swing.JPanel {

    public FormKho(JFrame frame) {
        initComponents();
        this.frame = frame;
        setOpaque(false);
        txtFind.setLabelText("Tìm kiếm món ăn hoặc mặt hàng");
        init();
    }

    private JFrame frame;
    private List<ModelKho> listKho = new ArrayList<ModelKho>();
    private List<ModelKho> whiteList = new ArrayList<ModelKho>();
    private List<ModelSanPham> listSP = new ArrayList<ModelSanPham>();
    private KhoDAO khoDAO = new KhoDAO();
    private SanPhamDAO spDAO = new SanPhamDAO();
    private DefaultTableModel model;

    private void init() {
        loadListKho();
        loadListSP();
        initTable();
        fillToTable();
        if (!Auth.isBoss() && !Auth.isManager()) {
            btnNhapKho.setVisible(false);
            btnXuat.setVisible(false);
            btnXemLichSu.setVisible(false);
        }
    }

    private void initTable() {
        tblKho.setOpaque(true);
        tblKho.setBackground(new Color(255, 255, 255));
        tblKho.setFillsViewportHeight(true);
        tblKho.fixTable(jScrollPane1);
        tblKho.setFont(new Font("SansSerif", 1, 12));
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Mã lô hàng", "Sản phẩm", "Số lượng", "Hạn sử dụng"});
        tblKho.setModel(model);
        tblKho.setColumnAction(10);
    }
//fill dữ liệu lên table kho whitelist(trạng thái true)
    private void fillToTable() {
        //DefaultTableModel model = (DefaultTableModel) tblKho.getModel();
        //tblKho.setModel(model);
        model.setRowCount(0);
        loadToWhiteList();
        for (ModelKho kho : whiteList) {
            tblKho.addRow(kho.toRowTable(listSP));
        }
    }
//load dữ liệu vào list có trạng thái là true
    private void loadToWhiteList() {
        whiteList.clear();
        for (ModelKho kho : listKho) {
            if (kho.isTrangThai()) {
                whiteList.add(kho);
            }
        }
    }

    public void setListKho(List<ModelKho> listKho) {
        this.listKho = listKho;
    }

    public List<ModelSanPham> getListSP() {
        return this.listSP;
    }

    private void loadListKho() {
        listKho.addAll(khoDAO.selectAll());
    }

    private void loadListSP() {
        listSP.addAll(spDAO.selectAll());
    }
    int index = -1;

//    tìm kiếm sản phẩm trong kho
    private void find(String keyword) {
        List<ModelKho> listFind = new ArrayList<ModelKho>();
        listFind.clear();
        for (int i = 0; i < listKho.size(); i++) {
            if (keyword.trim().length() != 0) {
                if (listKho.get(i).getTenSanPham(listKho.get(i).getMaSP()).contains(keyword)) {
                    listFind.add(listKho.get(i));
                    model.setRowCount(0);
                    for (ModelKho kho : listFind) {
                        tblKho.addRow(kho.toRowTable(listSP));
                    }
                }
            } else {
                fillToTable();
            }
        }
    }

    //private JDialogNhapKho xnk = new JDialogNhapKho(frame, true);
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKho = new com.wolvesres.swing.table.Table();
        rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        lblTitle = new javax.swing.JLabel();
        btnNhapKho = new com.swing.custom.raven.RButton.RButton();
        btnXemLichSu = new com.swing.custom.raven.RButton.RButton();
        txtFind = new com.swing.custom.raven.RTextField.RTextField();
        btnXuat = new com.swing.custom.raven.RButton.RButton();

        setMaximumSize(new java.awt.Dimension(1170, 730));
        setMinimumSize(new java.awt.Dimension(1170, 730));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel1.setBackground(new java.awt.Color(6, 7, 13));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(null);

        tblKho.setBackground(new java.awt.Color(207, 224, 247));
        tblKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Số Lượng", "Hạn Sử Dụng", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKhoMousePressed(evt);
            }
        });
        tblKho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblKhoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblKho);

        rRoundPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 1170, 470));

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
        lblTitle.setText("KHO");
        rRoundPanel4.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 110));

        rRoundPanel1.add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 110));

        btnNhapKho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/import.png"))); // NOI18N
        btnNhapKho.setText("Nhập Kho");
        btnNhapKho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapKhoActionPerformed(evt);
            }
        });
        rRoundPanel1.add(btnNhapKho, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 680, 130, 40));

        btnXemLichSu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/nhapkho.png"))); // NOI18N
        btnXemLichSu.setText("Xem Lịch Sử Kho");
        btnXemLichSu.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnXemLichSu.setPreferredSize(new java.awt.Dimension(175, 30));
        btnXemLichSu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemLichSuActionPerformed(evt);
            }
        });
        rRoundPanel1.add(btnXemLichSu, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 680, -1, 40));

        txtFind.setForeground(new java.awt.Color(153, 153, 153));
        txtFind.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFind.setLabelText("Tìm kiếm trong kho");
        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindKeyReleased(evt);
            }
        });
        rRoundPanel1.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 1170, 50));

        btnXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/export.png"))); // NOI18N
        btnXuat.setText("Xuất Kho");
        btnXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatActionPerformed(evt);
            }
        });
        rRoundPanel1.add(btnXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 680, 120, 40));

        add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 730));
    }// </editor-fold>//GEN-END:initComponents

    private void btnNhapKhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapKhoActionPerformed
        new JDialogNhapKho(frame, true).setVisible(true);
        listKho = new JDialogNhapKho(frame, true).getListKho();
        fillToTable();
    }//GEN-LAST:event_btnNhapKhoActionPerformed

    private void btnXemLichSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemLichSuActionPerformed
        new LichSuChiTiet(frame, true).setVisible(true);
    }//GEN-LAST:event_btnXemLichSuActionPerformed

    private void tblKhoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblKhoKeyPressed
//       index = tblKho.getSelectedRow();
//        if (evt.getKeyChar() == KeyEvent.VK_0) {
//            if (evt.isControlDown()) {
//                // ROptionDialog.showAlert(frame, "asdfasdf");
//                int id = Integer.parseInt(String.valueOf(tblKho.getValueAt(index, 0)));
//                for (int i = 0; i < listKho.size(); i++) {
//                    if (id == listKho.get(i).getId()) {
//                        String sl = String.valueOf(listKho.get(i).getSoLuong());
//                        sl += String.valueOf(0);
//                        listKho.get(i).setSoLuong(Integer.parseInt(sl));
//                    }
//                }
//                fillToTable();
//            }
//        }
    }//GEN-LAST:event_tblKhoKeyPressed

    private void tblKhoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblKhoMousePressed

    private void btnXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatActionPerformed
        new JDialogXuatKho(frame, true).setVisible(true);
        listKho = new JDialogXuatKho(frame, true).getListKho();
        fillToTable();
    }//GEN-LAST:event_btnXuatActionPerformed

    private void txtFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKeyReleased
        find(txtFind.getText().trim());
    }//GEN-LAST:event_txtFindKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.raven.RButton.RButton btnNhapKho;
    private com.swing.custom.raven.RButton.RButton btnXemLichSu;
    private com.swing.custom.raven.RButton.RButton btnXuat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
    private com.wolvesres.swing.table.Table tblKho;
    private com.swing.custom.raven.RTextField.RTextField txtFind;
    // End of variables declaration//GEN-END:variables
}
