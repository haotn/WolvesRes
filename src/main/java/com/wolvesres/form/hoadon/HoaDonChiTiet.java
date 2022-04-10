package com.wolvesres.form.hoadon;
import com.swing.custom.raven.RScrollbar.RScrollBarCustom;
import com.wolvesres.dao.HoaDonChiTietDAO;
import com.wolvesres.dao.HoaDonDAO;
import com.wolvesres.form.FormHoaDon;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.model.ModelHoaDon;
import com.wolvesres.model.ModelHoaDonChiTiet;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import com.wolvesres.helper.FormValidator;
/**
 * Hiện hóa đơn chi tiết, tìm kiếm hóa đơn chi tiết theo hóa đơn.
 * Lien quan:  
 * @author  
 */
public class HoaDonChiTiet extends javax.swing.JDialog {

    JFrame frame;
    private List<ModelHoaDon> list = new ArrayList<>();
     private List<ModelHoaDon> listHD = new ArrayList<>();
    private List<ModelHoaDon> whiteList = new ArrayList<>();
    private List<ModelHoaDonChiTiet> listHDCT = new ArrayList<>();
    private List<ModelHoaDonChiTiet> listSeen = new ArrayList<>();
    private FormValidator validator = new FormValidator();
    HoaDonDAO dao = new HoaDonDAO();
    HoaDonChiTietDAO daoCT = new HoaDonChiTietDAO();
    String selectHD = "";
    int selectedRow = -1;
    private ModelHoaDon mdhd = new ModelHoaDon();

    public ModelHoaDon getMdhd() {
        return mdhd;
    }

    public void setMdhd(ModelHoaDon mdhd) {
        this.mdhd = mdhd;
    }
    
    public HoaDonChiTiet(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
       // init();
        txtFindHDChiTiet.setLabelText("Tìm Kiếm");
    }

    public void init() {
        jScrollPane1.setVerticalScrollBar(new RScrollBarCustom());
        initTableSeen();
        initTable();
        loadListHoaDon();
        filltableHoaDon();
        selectedRow = 0;
        showDetail(selectedRow);
    }

    //Tbl hóa đơn
    public void initTableSeen() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"Sản Phẩm", "Giá/SP", "Số Lượng", "Giá",});
        tblSeen.setModel(model);
        tblSeen.setOpaque(true);
        tblSeen.setFillsViewportHeight(true);
        tblSeen.fixTable(jScrollPane1);
        tblSeen.setForeground(new Color(0, 0, 0));
        tblSeen.setColumnAction(10);
    }

    //Load list hiển thị lên hóa đơn chi tiết
    public void loadListHoaDonChiTiet() {
        listSeen.clear();
        for (ModelHoaDonChiTiet hdct : listHDCT) {
            if (selectHD.equals(hdct.getMaHD())) {
                listSeen.add(hdct);
            }
        }
    }
//    Điền lên bảng hóa đơn chi tiết
    public void filltableHoaDonChiTiet() {
        loadListHoaDonChiTiet();
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"Sản Phẩm", "Giá/SP", "Số Lượng", "Giá",});
        tblSeen.setModel(model);
        for (ModelHoaDonChiTiet hd : listSeen) {
            tblSeen.addRow(hd.toRowTableHDCT());
        }

    }

    ///Hóa đơn
    //tbl hóa đon chi tiết
    private void initTable() {
        tblHDChiTiet.setOpaque(true);
        tblHDChiTiet.setBackground(new Color(255,255,255));
        tblHDChiTiet.setFillsViewportHeight(true);
        tblHDChiTiet.fixTable(jScrollPane1);
        tblHDChiTiet.setFont(new Font("SansSerif", 1, 12));
        DefaultTableModel modela = new DefaultTableModel(new Object[][]{}, new Object[]{"Mã HD", "NV", "Date", "Tiền hàng"});
        tblHDChiTiet.setModel(modela);
    }

    //
    //load list hóa đơn
    public void loadListHoaDon() {
        listHD.addAll(dao.selectAll());
        System.out.println(mdhd.getMaHD());
        for (ModelHoaDon modelHoaDon : listHD) {
            if (modelHoaDon.getMaHD().equals(mdhd.getMaHD())) {
                list.add(modelHoaDon);
            }
        }
        listHDCT.addAll(daoCT.selectAll());
    }

    public void loadwhitetoList() {
        whiteList.clear();
        for (ModelHoaDon hoadon : list) {
            whiteList.add(hoadon);
        }
    }
//    fill table hóa đơn
    public void filltableHoaDon() {
        loadwhitetoList();
        DefaultTableModel modeltablehoadon = new DefaultTableModel(new Object[][]{}, new Object[]{"Mã HD", "NV", "Date", "Tiền hàng"});
        modeltablehoadon.setRowCount(0);
        tblHDChiTiet.setModel(modeltablehoadon);
        for (ModelHoaDon hoadon : whiteList) {
            tblHDChiTiet.addRow(hoadon.toRowTableHDCT());
        }

    }

    //hiển thị dữ liệu lên hóa đơn bên phải
    public void showDetail(int select) {
        if (select >= 0) {
            ModelHoaDon hoaDon = whiteList.get(selectedRow);
            // lblHoTen.setText(emp.getHoTen());
            lblMaHD.setText(selectHD);
            lblNgaytao.setText(hoaDon.toYMD(hoaDon.getNgayXuat()));
            lblTongtien.setText(XFormatMoney.formatMoney((hoaDon.getTienHang())));
        }
    }

    //tìm kiếm hóa đơn theo mã, ngày
    private void timkiemhoadon(String keyword) {
        List<ModelHoaDon> listFind = new ArrayList<>();
        listFind.clear();
        for (int i = 0; i < list.size(); i++) {
            if (!validator.isTextIsNotEmpty(keyword)) {
                if (list.get(i).getMaHD().contains(keyword) || list.get(i).toYMD(list.get(i).getNgayXuat()).contains(keyword)) {
                    listFind.add(list.get(i));
                    DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"Sản Phẩm", "Giá/SP", "Số Lượng", "Giá",});
                    tblSeen.setModel(model);
                    model.setRowCount(0);
                    for (ModelHoaDon dvt : listFind) {
                        tblHDChiTiet.addRow(dvt.toRowTableHDCT());
                    }
                }
            } else {
                filltableHoaDon();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        txtFindHDChiTiet = new com.swing.custom.raven.RTextField.RTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHDChiTiet = new com.wolvesres.swing.table.Table();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        btnBack = new com.swing.custom.raven.RButton.RButton();
        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel2 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        jLabel1 = new javax.swing.JLabel();
        rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        lblNgaytao = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblTongtien = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSeen = new com.wolvesres.swing.table.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(new java.awt.Point(220, 60));

        rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));
        rRoundPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        rRoundPanel1.setMinimumSize(new java.awt.Dimension(1170, 730));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtFindHDChiTiet.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFindHDChiTiet.setLabelText("Tìm kiếm hóa đơn chi tiết");
        txtFindHDChiTiet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindHDChiTietKeyReleased(evt);
            }
        });
        rRoundPanel1.add(txtFindHDChiTiet, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 650, -1));

        tblHDChiTiet.setAutoCreateRowSorter(true);
        tblHDChiTiet.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHDChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHDChiTietMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblHDChiTiet);

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

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("WolvesRes");
        rRoundPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel3.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 100, 80));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("HÓA ĐƠN CHI TIẾT");
        rRoundPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, -1, 40));

        rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 120));

        rRoundPanel4.setBackground(new java.awt.Color(171, 240, 191));
        rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("HÓA ĐƠN CHI TIẾT");
        rRoundPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        jLabel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        rRoundPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 440, 10));

        lblMaHD.setText("HD01");
        rRoundPanel4.add(lblMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, -1, -1));

        lblNgaytao.setText("01-01-2021");
        rRoundPanel4.add(lblNgaytao, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, -1, -1));

        jLabel8.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 13)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/image-removebg-preview.png"))); // NOI18N
        jLabel8.setText("WOLVESRES");
        rRoundPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel11.setText("Ngày Tạo:");
        rRoundPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

        lblTongtien.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTongtien.setText("200000");
        rRoundPanel4.add(lblTongtien, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 720, -1, -1));

        jLabel20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        rRoundPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 710, 440, 20));

        jLabel22.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        rRoundPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 440, 10));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel24.setText("Tổng Tiền:");
        rRoundPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 720, -1, -1));

        jLabel25.setText("Mã Hóa đơn");
        rRoundPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        tblSeen.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSeen.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblSeen.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblSeen.setTableBackgoundSelectRow(new java.awt.Color(0, 0, 0));
        tblSeen.setTableForegroundSelectRow(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(tblSeen);

        rRoundPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 530));

        rRoundPanel1.add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 500, 770));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void tblHDChiTietMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDChiTietMousePressed
        selectedRow = tblHDChiTiet.getSelectedRow();
        selectHD = (String) tblHDChiTiet.getValueAt(selectedRow, 0);
        showDetail(selectedRow);
        filltableHoaDonChiTiet();
    }//GEN-LAST:event_tblHDChiTietMousePressed

    private void txtFindHDChiTietKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindHDChiTietKeyReleased
        timkiemhoadon(txtFindHDChiTiet.getText().trim());
    }//GEN-LAST:event_txtFindHDChiTietKeyReleased

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
            java.util.logging.Logger.getLogger(HoaDonChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HoaDonChiTiet dialog = new HoaDonChiTiet(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblNgaytao;
    private javax.swing.JLabel lblTongtien;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
    private com.wolvesres.swing.table.Table tblHDChiTiet;
    private com.wolvesres.swing.table.Table tblSeen;
    private com.swing.custom.raven.RTextField.RTextField txtFindHDChiTiet;
    // End of variables declaration//GEN-END:variables
}
