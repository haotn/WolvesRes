package com.wolvesres.form.chaomung;

import com.wolvesres.helper.AnimationShowWindow;
import com.wolvesres.main.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.Timer;

public class FormLoadData extends javax.swing.JDialog {

    JDialog dialog;

    public FormLoadData(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.dialog = this;
        AnimationShowWindow.showDialog(dialog);
        setLocationRelativeTo(null);
        chaoMung();
    }
//    public static ModelBanOrder banOrderGlobal = new ModelBanOrder();
//    /**
//     * Dao
//     */
//    public static TaiKhoanDAO taiKhoanDAo = new TaiKhoanDAO();
//    public static NhanVienDAO nhanVienDAO = new NhanVienDAO();
//    public static SanPhamDAO sanPhamDAO = new SanPhamDAO();
//    public static BanDAO banDAO = new BanDAO();
//    public static KhuBanDAO khuBanDAO = new KhuBanDAO();
//    public static DanhMucDAO danhMucDAO = new DanhMucDAO();
//    public static DonViTinhDAO donViTinhDAO = new DonViTinhDAO();
//    public static AutoDAO autoDAO = new AutoDAO();
//    public static BanOrderDAO banOrderDAO = new BanOrderDAO();
//    public static ChiTietLichSuDAO chiTietLichSuDAO = new ChiTietLichSuDAO();
//    public static HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO();
//    public static HoaDonDAO hoaDonDAO = new HoaDonDAO();
//    public static KhoDAO khoDAO = new KhoDAO();
//    public static LichSuDAO lichSuDAO = new LichSuDAO();
//    public static LichSuGiaDAO lichSuGiaDAO = new LichSuGiaDAO();
//    public static OrderDAO orderDAO = new OrderDAO();
//    public static ThongKeDAO thongKeDAO = new ThongKeDAO();
//    public static VoucherDAO voucherDAO = new VoucherDAO();
//    /**
//     * List
//     */
//
//    public static List<ModelNhanVien> listNhanViens = new ArrayList<>();
//    public static List<ModelBan> listBans = new ArrayList<>();
//    public static List<ModelBanOrder> listBanOrders = new ArrayList<>();
//    public static List<ModelChiTietLichSu> listcChiTietLichSus = new ArrayList<>();
//    public static List<ModelDanhMuc> listDanhMucs = new ArrayList<>();
//    public static List<ModelDonViTinh> listDonViTinhs = new ArrayList<>();
//    public static List<ModelHoaDon> listHoaDons = new ArrayList<>();
//    public static List<ModelHoaDonChiTiet> listHoaDonChiTiets = new ArrayList<>();
//    public static List<ModelKho> listKhos = new ArrayList<>();
//    public static List<ModelKhuBan> listKhuBans = new ArrayList<>();
//    public static List<ModelLichSu> listLichSus = new ArrayList<>();
//    public static List<ModelOrder> listOrders = new ArrayList<>();
//    public static List<ModelSanPham> listSanPhams = new ArrayList<>();
//    public static List<ModelTaiKhoan> listTaiKhoans = new ArrayList<>();
//    public static List<ModelVouCher> listVouChers = new ArrayList<>();
//    public static List<ModelLichSuGia> listLichSuGias = new ArrayList<>();
//    public static List<ModelOrder> listOrderByMaBan = new ArrayList<>();
//
//    private void init() {
//        loadListNhanVien();
//        loadListTaiKhoan();
//        loadListBan();
//        loadListBanOrder();
//        loadListOrder();
//        loadListChiTietLichSu();
//        loadListDanhMuc();
//        loadListDonViTinh();
//        loadListHoaDon();
//        loadListKho();
//        loadListKhuBan();
//        loadListLichSu();
//        loadListSanPham();
//        loadListVoucher();
//        loadListLichSuGia();
//    }
//
//    public static void loadListLichSuGia() {
//        listLichSuGias.addAll(lichSuGiaDAO.selectAll());
//    }
//
//    public static void loadListVoucher() {
//        listVouChers.addAll(voucherDAO.selectAll());
//    }
//
//    public static void loadListSanPham() {
//        listSanPhams.addAll(sanPhamDAO.selectAll());
//    }
//
//    public static void loadListLichSu() {
//        listLichSus.addAll(lichSuDAO.selectAll());
//    }
//
//    public static void loadListKho() {
//        listKhos.addAll(khoDAO.selectAll());
//    }
//
//    public static void loadListHoaDonChiTiet() {
//        listHoaDonChiTiets.addAll(hoaDonChiTietDAO.selectAll());
//    }
//
//    public static void loadListHoaDon() {
//        listHoaDons.addAll(hoaDonDAO.selectAll());
//    }
//
//    public static void loadListDonViTinh() {
//        listDonViTinhs.addAll(donViTinhDAO.selectAll());
//    }
//
//    public static void loadListDanhMuc() {
//        listDanhMucs.addAll(danhMucDAO.selectAll());
//    }
//
//    public static void loadListChiTietLichSu() {
//        listcChiTietLichSus.addAll(chiTietLichSuDAO.selectAll());
//    }
//
//    public static void loadListOrder() {
//        listOrders.addAll(orderDAO.selectAll());
//    }
//
//    public static void loadListBanOrder() {
//        listBanOrders.addAll(banOrderDAO.selectAll());
//    }
//
//    public static void loadListKhuBan() {
//        listKhuBans.addAll(khuBanDAO.selectAll());
//    }
//
//    public static void loadListBan() {
//        listBans.addAll(banDAO.selectAll());
//    }
//
//    public static void loadListTaiKhoan() {
//        for (ModelTaiKhoan taiKhoan : taiKhoanDAo.selectAll()) {
////            if (!taiKhoan.getTaiKhoan().equals(Auth.user.getMaNV())) {
//            listTaiKhoans.add(taiKhoan);
////            }
//        }
//    }
//
//    public static void loadListNhanVien() {
//        for (ModelNhanVien nv : nhanVienDAO.selectAll()) {
//            if (nv.getChucVu() != 1) {
//                if (!nv.getMaNV().equals(Auth.user.getMaNV())) {
//                    listNhanViens.add(nv);
//                }
//            }
//        }
//    }

    // sử dụng luồng 
    Timer taiChaoMung;
    int phanTram = 0;

    // Phương thức tải chào mừng
    private void chaoMung() {
        taiChaoMung = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  prg.setValue(phanTram);

                // init();
                AnimationShowWindow.closeDialog(dialog);
                taiChaoMung.stop();
                dispose();
                // new FormDangNhap().setVisible(true);
                new Main().setVisible(true);
                taiChaoMung.stop();

            }
        });
        taiChaoMung.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_ChaoMung = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnl_ChaoMung.setBackground(new java.awt.Color(21, 21, 21));
        pnl_ChaoMung.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/loadData.gif"))); // NOI18N
        pnl_ChaoMung.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 480, 480));

        lbl_Background.setBackground(new java.awt.Color(27, 27, 27));
        lbl_Background.setForeground(new java.awt.Color(229, 239, 241));
        lbl_Background.setOpaque(true);
        pnl_ChaoMung.add(lbl_Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_ChaoMung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_ChaoMung, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FormLoadData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLoadData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLoadData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLoadData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormLoadData dialog = new FormLoadData(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_Background;
    private javax.swing.JPanel pnl_ChaoMung;
    // End of variables declaration//GEN-END:variables
}
