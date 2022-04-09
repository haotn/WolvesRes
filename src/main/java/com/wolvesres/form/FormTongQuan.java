package com.wolvesres.form;

import com.swing.custom.raven.RCard.RModelCard;
import com.swing.custom.raven.RIcon.GoogleMaterialDesignIcons;
import com.swing.custom.raven.RIcon.IconFontSwing;
import com.swing.custom.raven.RScrollbar.RScrollBarCustom;
import com.wolvesres.dao.BanDAO;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.dao.VoucherDAO;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.model.ModelVouCher;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Icon;

public class FormTongQuan extends javax.swing.JPanel {

    public FormTongQuan() {
        initComponents();
        inint();
        initCardData();
        setOpaque(false);
    }
    KhoDAO khoDAO = new KhoDAO();
    NhanVienDAO nhanVienDAO = new NhanVienDAO();
    SanPhamDAO sanPhamDAO = new SanPhamDAO();
    BanDAO banDAO = new BanDAO();
    VoucherDAO voucherDAO = new VoucherDAO();
    DanhMucDAO danhMucDAO = new DanhMucDAO();
    List<ModelNhanVien> listNhanVien = new ArrayList<ModelNhanVien>();
    List<ModelNhanVien> listNhanVienHienTai = new ArrayList<ModelNhanVien>();
    List<ModelNhanVien> listNhanVienQuanLy = new ArrayList<ModelNhanVien>();
    List<ModelNhanVien> listNhanVienThuNgan = new ArrayList<ModelNhanVien>();
    List<ModelNhanVien> listNhanVienKhac = new ArrayList<ModelNhanVien>();
    List<ModelSanPham> listSanPham = new ArrayList<ModelSanPham>();
    List<ModelSanPham> listSanPhamHienTai = new ArrayList<ModelSanPham>();
    List<ModelSanPham> listSanPhamMatHang = new ArrayList<>();
    List<ModelSanPham> listSanPhamMonAn = new ArrayList<>();
    List<ModelBan> listBan = new ArrayList<>();
    List<ModelBan> listBanHienTai = new ArrayList<>();
    List<ModelVouCher> listVoucher = new ArrayList<>();
    List<ModelVouCher> listVoucherHienTai = new ArrayList<>();
    List<ModelKho> listKho = new ArrayList<>();
    private int tongNV, tongSP, tongBan, tongVC, slQuanLy, slThuNgan, slNhanVienKhac, slMatHang,
            slMonAn, slVoucher, slBan, ptQuanLy, ptThuNgan, ptNhanVienKhac, ptMatHang, ptMonAn, ptVoucher, ptBan = 0;

    private void inint() {
        listNhanVien.addAll(nhanVienDAO.selectAll());
        listSanPham.addAll(sanPhamDAO.selectAll());
        listBan.addAll(banDAO.selectAll());
        listVoucher.addAll(voucherDAO.selectAll());
        listKho.addAll(khoDAO.selectAll());
        alert();
        //nv
        for (ModelNhanVien modelNhanVien : listNhanVien) {
            if (modelNhanVien.isTrangThai() == true) {
                listNhanVienHienTai.add(modelNhanVien);
            }
        }
        for (ModelNhanVien modelNhanVien : listNhanVienHienTai) {
            if (modelNhanVien.getChucVu() == 2) {
                listNhanVienQuanLy.add(modelNhanVien);
            }
            if (modelNhanVien.getChucVu() == 3) {
                listNhanVienThuNgan.add(modelNhanVien);
            }
            if (modelNhanVien.getChucVu() > 3) {
                listNhanVienKhac.add(modelNhanVien);
            }
        }
        //sp
        for (ModelSanPham modelSanPham : listSanPham) {
            if (modelSanPham.isTrangThai() == true) {
                listSanPhamHienTai.add(modelSanPham);
            }
        }

        for (ModelSanPham modelSanPham : listSanPhamHienTai) {
            for (ModelDanhMuc danhMuc : danhMucDAO.selectAll()) {
                if (modelSanPham.getMaDanhMuc().equals(danhMuc.getMaDanhMuc())) {
                    if (danhMuc.isMatHang()) {
                        listSanPhamMatHang.add(modelSanPham);
                    }
                    if (!danhMuc.isMatHang()) {
                        listSanPhamMonAn.add(modelSanPham);
                    }
                }
            }
        }
        //ban
        for (ModelBan modelBan : listBan) {
            if (modelBan.isHoatDong() == true) {
                listBanHienTai.add(modelBan);
            }
        }
        //vc
        for (ModelVouCher modelVouCher : listVoucher) {
            if (modelVouCher.isTrangThai() == true) {
                listVoucherHienTai.add(modelVouCher);
            }
        }
        tongNV = listNhanVienHienTai.size();
        tongSP = listSanPhamHienTai.size();
        tongBan = listBanHienTai.size();
        tongVC = listVoucher.size();
        slQuanLy = listNhanVienQuanLy.size();
        slThuNgan = listNhanVienThuNgan.size();
        slNhanVienKhac = listNhanVienKhac.size();
        slMatHang = listSanPhamMatHang.size();
        slMonAn = listSanPhamMonAn.size();
        slVoucher = listVoucherHienTai.size();
        slBan = listBanHienTai.size();
        ptQuanLy = (int) ((slQuanLy * 100) / (tongNV - 1));
        ptThuNgan = (int) ((slThuNgan * 100) / (tongNV - 1));
        ptNhanVienKhac = (int) ((slNhanVienKhac * 100) / (tongNV - 1));
        ptMatHang = (int) ((slMatHang * 100) / (tongSP));
        ptMonAn = (int) ((slMonAn * 100) / (tongSP));
        ptVoucher = (int) ((slVoucher * 100) / (tongSP));
    }

    static {
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
    }

    private void initCardData() {
        Icon icon1 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PEOPLE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardTongNV.setData(new RModelCard("Tổng nhân viên", tongNV, 100, icon1));
        Icon icon2 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.BUSINESS_CENTER, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardQuanLy.setData(new RModelCard("Quản lý", slQuanLy, ptQuanLy, icon2));
        Icon icon3 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.MONETIZATION_ON, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardThuNgan.setData(new RModelCard("Thu ngân", slThuNgan, ptThuNgan, icon3));
        Icon icon4 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PORTRAIT, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardNhanVienKhac.setData(new RModelCard("Nhân viên khác", slNhanVienKhac, ptNhanVienKhac, icon4));
        //
        Icon icon5 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LOCAL_GROCERY_STORE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardMathang.setData(new RModelCard("Mặt hàng", slMatHang, ptMatHang, icon5));
        Icon icon6 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LOCAL_DINING, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardMonan.setData(new RModelCard("Món ăn", slMonAn, ptMonAn, icon6));
        Icon icon7 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.EVENT_SEAT, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardBan.setData(new RModelCard("Bàn", slBan, 100, icon7));
        Icon icon8 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.BUSINESS_CENTER, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardVoucher.setData(new RModelCard("Voucher", slVoucher, ptVoucher, icon8));
    }

    private ModelSanPham getSanPhamByMaSP(String masp) {
        ModelSanPham entity = new ModelSanPham();
        for (int i = 0; i < listSanPham.size(); i++) {
            if (masp.equals(listSanPham.get(i).getMaSP())) {
                entity = listSanPham.get(i);
            }
        }
        return entity;
    }

    private void alert() {
        jScrollPane1.setVerticalScrollBar(new RScrollBarCustom());
        txtAlert.setEditable(false);
        boolean exists = false;
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        String day = formater.format(new Date());
        for (int i = 0; i < listKho.size(); i++) {
            Date today = XDate.toDate(day, "dd-MM-yyyy");
            Date hanSD = XDate.toDate(listKho.get(i).getHanSuDung(), "dd-MM-yyyy");
            Date target = XDate.addDays(today, 30);
            if (listKho.get(i).getSoLuong() < 50) {
                txtAlert.append("\nSản phẩm " + getSanPhamByMaSP(listKho.get(i).getMaSP()).getTenSP() + " Sắp hết hàng, số lượng còn lại trong kho là " + listKho.get(i).getSoLuong() + ".");
                exists = true;
            }
            if (target.equals(hanSD) || target.after(hanSD)) {
                txtAlert.append("\nSản phẩm " + getSanPhamByMaSP(listKho.get(i).getMaSP()).getTenSP() + " sắp hết hạn sử dụng. Ngày hết hạn: " + XDate.toString(hanSD, "dd-MM-yyyy") + ".");
            }
        }
        if (!exists) {
            txtAlert.setText("Không có thông báo mới.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        cardTongNV = new com.swing.custom.raven.RCard.RCard();
        cardQuanLy = new com.swing.custom.raven.RCard.RCard();
        cardThuNgan = new com.swing.custom.raven.RCard.RCard();
        cardNhanVienKhac = new com.swing.custom.raven.RCard.RCard();
        jLabel1 = new javax.swing.JLabel();
        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel2 = new javax.swing.JLabel();
        cardMathang = new com.swing.custom.raven.RCard.RCard();
        cardVoucher = new com.swing.custom.raven.RCard.RCard();
        cardBan = new com.swing.custom.raven.RCard.RCard();
        cardMonan = new com.swing.custom.raven.RCard.RCard();
        rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlert = new javax.swing.JTextArea();

        setMaximumSize(new java.awt.Dimension(1170, 730));
        setMinimumSize(new java.awt.Dimension(1170, 730));

        rRoundPanel1.setBackground(new java.awt.Color(21, 25, 29));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel2.setBackground(new java.awt.Color(6, 7, 13));
        rRoundPanel2.setPreferredSize(new java.awt.Dimension(1170, 150));
        rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cardTongNV.setBackground(new java.awt.Color(0, 153, 153));
        rRoundPanel2.add(cardTongNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 270, 150));

        cardQuanLy.setBackground(new java.awt.Color(0, 0, 153));
        rRoundPanel2.add(cardQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 270, 150));

        cardThuNgan.setBackground(new java.awt.Color(204, 0, 102));
        rRoundPanel2.add(cardThuNgan, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 270, 150));

        cardNhanVienKhac.setBackground(new java.awt.Color(51, 153, 0));
        rRoundPanel2.add(cardNhanVienKhac, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 40, 270, 150));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nhân viên");
        rRoundPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 200));

        rRoundPanel3.setBackground(new java.awt.Color(6, 7, 13));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Hoạt động bán hàng");
        rRoundPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        cardMathang.setBackground(new java.awt.Color(0, 153, 153));
        rRoundPanel3.add(cardMathang, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 270, 150));

        cardVoucher.setBackground(new java.awt.Color(51, 153, 0));
        rRoundPanel3.add(cardVoucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 40, 260, 150));

        cardBan.setBackground(new java.awt.Color(204, 0, 102));
        rRoundPanel3.add(cardBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, 270, 150));

        cardMonan.setBackground(new java.awt.Color(0, 0, 153));
        rRoundPanel3.add(cardMonan, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 270, 150));

        rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 1170, 220));

        rRoundPanel4.setBackground(new java.awt.Color(6, 7, 13));
        rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Thông báo mới");
        rRoundPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jScrollPane1.setBorder(null);

        txtAlert.setBackground(new java.awt.Color(6, 7, 13));
        txtAlert.setColumns(20);
        txtAlert.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        txtAlert.setForeground(new java.awt.Color(255, 153, 0));
        txtAlert.setLineWrap(true);
        txtAlert.setRows(5);
        txtAlert.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAlert);

        rRoundPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 1130, 210));

        rRoundPanel1.add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 1170, 280));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.raven.RCard.RCard cardBan;
    private com.swing.custom.raven.RCard.RCard cardMathang;
    private com.swing.custom.raven.RCard.RCard cardMonan;
    private com.swing.custom.raven.RCard.RCard cardNhanVienKhac;
    private com.swing.custom.raven.RCard.RCard cardQuanLy;
    private com.swing.custom.raven.RCard.RCard cardThuNgan;
    private com.swing.custom.raven.RCard.RCard cardTongNV;
    private com.swing.custom.raven.RCard.RCard cardVoucher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
    private javax.swing.JTextArea txtAlert;
    // End of variables declaration//GEN-END:variables
}
