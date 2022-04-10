package com.wolvesres.form;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RIcon.GoogleMaterialDesignIcons;
import com.swing.custom.raven.RIcon.IconFontSwing;
import com.wolvesres.dao.HoaDonDAO;
import com.wolvesres.form.hoadon.HoaDonChiTiet;
import com.wolvesres.form.hoadon.HuyHoaDon;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.model.ModelHoaDon;
import com.wolvesres.swing.table.EventAction;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
/**
 * 
 * Chỉnh sửa: Hủy hóa đơn(chuyentrangthaihoadon).
 * liên quan: ModelHoaDon
 * */
public class FormHoaDon extends javax.swing.JPanel {

	JFrame frame;
	DefaultTableModel model;
	private List<ModelHoaDon> list = new ArrayList<ModelHoaDon>();
	private List<ModelHoaDon> whiteList = new ArrayList<ModelHoaDon>();
	HoaDonDAO dao = new HoaDonDAO();
	int selectedRow = -1;
	EventAction<ModelHoaDon> eventAction = new EventAction<ModelHoaDon>() {
		public void delete(ModelHoaDon entity) {

		}

		public void update(ModelHoaDon entity) {

		}
	};

	public FormHoaDon(JFrame frame) {
		initComponents();
		initTable();
		loadtoList();
		fillTable();
		if (!Auth.isBoss() && !Auth.isManager()) {
			btnHuy.setVisible(false);
		}
		selectedRow = 0;
		showDetail(selectedRow);
		btnHDCT.setVisible(false);
		Icon iconCancel = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.REMOVE_SHOPPING_CART, 32,
				new Color(0, 199, 135));
		btnHuy.setIcon(iconCancel);
	}

	//load vào list hóa đơn từ csdl 
	public void loadtoList() {
		list.addAll(dao.selectAll());
	}

	//lọc những hóa đơn có trạng thái là true(hoạt động)
	public void loadwhitetoList() {
		whiteList.clear();
		for (ModelHoaDon hd : list) {
			if (hd.isTrangThai() == true) {
				whiteList.add(hd);
			}
		}
	}

	//fill table hóa đơn
	public void fillTable() {
		loadwhitetoList();
		model.setRowCount(0);
		for (ModelHoaDon hd : whiteList) {
			tblHoaDon.addRow(hd.toRowTable(eventAction));
		}

	}

	//chuyển trạng thái hóa đơn
	/**
	 * chuyenTrangThaiHoaDon
	 * @param ModelHoaDon
	 * */
	public void HuyHoaDon(int select) {
		if (select >= 0) {
			ModelHoaDon hoadon = whiteList.get(select);
			if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có muôn hủy Hoá đơn " + hoadon.getMaHD() + " không?",
					ROptionDialog.WARNING, Color.yellow, Color.black)) {
				hoadon.chuyenTrangThaiHoaDon(list);
				fillTable();
			}
		} else {
			ROptionDialog.showAlert(frame, "Thông báo", "Bạn chưa chọn hóa đơn", ROptionDialog.WARNING, Color.red,
					Color.black);
		}
	}

// khởi tạo bảng ban đầu khi start chương trình
	private void initTable() {
		tblHoaDon.setOpaque(true);
		tblHoaDon.setBackground(new Color(255, 255, 255));
		tblHoaDon.setFillsViewportHeight(true);
		tblHoaDon.fixTable(jScrollPane1);
		tblHoaDon.setFont(new Font("SansSerif", 1, 12));
		model = new DefaultTableModel(new Object[][] {},
				new Object[] { "Mã HD", "NV", "Date", "Bàn", "Voucher", "Thuế", "Tiền hàng" });
		tblHoaDon.setModel(model);
	}

	//hiển thị giá trị bảng lên dữ liệu bên phải khi click vào
	public void showDetail(int select) {
		if (select >= 0) {
			ModelHoaDon hoaDon = whiteList.get(selectedRow);
			// lblHoTen.setText(emp.getHoTen());
			lblMaHD.setText(hoaDon.getMaHD());
			lblNgaytao.setText(hoaDon.toYMD(hoaDon.getNgayXuat()));
			lblNguoitao.setText(hoaDon.NguoiXuat(hoaDon.getNguoiXuat()));
			lblBan.setText(hoaDon.getMaBan());
			lblThue.setText(XFormatMoney.formatMoney(hoaDon.getThue()) + " (10%)");
			lblVoucher.setText(hoaDon.getMaVoucher());
			lblTienHang.setText(XFormatMoney.formatMoney(hoaDon.getTienHang()));
			lblTongtien.setText(XFormatMoney.formatMoney(hoaDon.getTienHang()));
			lblGiam.setText(hoaDon.GiamVC(hoaDon.getMaVoucher()) + "%");
			lblTrangthai.setText(hoaDon.TrangThai(hoaDon.isTrangThai()));
		}
	}

	//
	public List<ModelHoaDon> getList() {
		return list;
	}

	public void setList(List<ModelHoaDon> list) {
		this.list = list;
	}

//	tìm kiếm hóa đơn
	public List<ModelHoaDon> timKiem(String keyword, HoaDonDAO hdDAo) {
		List<ModelHoaDon> list = new ArrayList<ModelHoaDon>();
		if (keyword.length() != 0) {
			list.addAll(hdDAo.timKiem(keyword));
		} else {
			list.addAll(hdDAo.selectAll());
		}
		return list;
	}

//	lấy đối tự hóa đơn tại vị trí đc chọn từ hàng trên bảng
	private ModelHoaDon getHDformRowtable(int row) {
		ModelHoaDon hd = new ModelHoaDon();
		String maHD = String.valueOf(tblHoaDon.getValueAt(row, 0));
		for (ModelHoaDon modelHoaDon : list) {
			if (maHD.equals(modelHoaDon.getMaHD())) {
				hd = modelHoaDon;
			}
		}
		return hd;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel1 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		lblTitle = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblHoaDon = new com.wolvesres.swing.table.Table();
		txtFindHoaDon = new com.swing.custom.raven.RTextField.RTextField();
		btnHuy = new com.swing.custom.raven.RButton.RButton();
		btnHDCT = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		lblMaHD = new javax.swing.JLabel();
		lblNguoitao = new javax.swing.JLabel();
		lblNgaytao = new javax.swing.JLabel();
		lblGiam = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		lblBan = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		lblTienHang = new javax.swing.JLabel();
		lblVoucher = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		lblTongtien = new javax.swing.JLabel();
		jLabel17 = new javax.swing.JLabel();
		lblThue = new javax.swing.JLabel();
		jLabel19 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();
		lblTrangthai = new javax.swing.JLabel();
		jLabel22 = new javax.swing.JLabel();
		jLabel23 = new javax.swing.JLabel();
		jLabel24 = new javax.swing.JLabel();
		jLabel25 = new javax.swing.JLabel();
		jLabel26 = new javax.swing.JLabel();
		btnDS = new com.swing.custom.raven.RButton.RButton();

		setBackground(new java.awt.Color(6, 7, 13));
		setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		rRoundPanel4.setBackground(new java.awt.Color(0, 199, 135));
		rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("WolvesRes");
		rRoundPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 40, -1, -1));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel4.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 120, 110));

		lblTitle.setBackground(new java.awt.Color(255, 255, 255));
		lblTitle.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		lblTitle.setForeground(new java.awt.Color(255, 255, 255));
		lblTitle.setText("HÓA ĐƠN");
		rRoundPanel4.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 100));

		add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 110));

		jScrollPane1.setBorder(null);

		tblHoaDon.setBackground(new java.awt.Color(207, 224, 247));
		tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null } },
				new String[] { "Mã HD", "Người Xuất", "Ngày Xuất", "Mã Bàn", "Mã Voucher", "Thuế", "Tiền Hàng",
						"Trạng Thái" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, true, true, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		tblHoaDon.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				tblHoaDonFocusGained(evt);
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				tblHoaDonFocusLost(evt);
			}
		});
		tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblHoaDonMousePressed(evt);
			}
		});
		jScrollPane1.setViewportView(tblHoaDon);

		add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 690, 510));

		txtFindHoaDon.setForeground(new java.awt.Color(153, 153, 153));
		txtFindHoaDon.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtFindHoaDon.setLabelText("Tìm kiếm hóa đơn");
		txtFindHoaDon.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtFindHoaDonKeyReleased(evt);
			}
		});
		add(txtFindHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 690, -1));

		btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huyhoadon.png"))); // NOI18N
		btnHuy.setText("Hủy hóa đơn");
		btnHuy.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});
		add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 690, -1, 40));

		btnHDCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/detail.png"))); // NOI18N
		btnHDCT.setText("Hóa đơn chi tiết");
		btnHDCT.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnHDCT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHDCTActionPerformed(evt);
			}
		});
		add(btnHDCT, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 690, -1, 40));

		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jLabel2.setText("HÓA ĐƠN");
		rRoundPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, -1, -1));

		jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
		rRoundPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 413, 10));

		lblMaHD.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		lblMaHD.setText("HD01");
		rRoundPanel2.add(lblMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, -1, -1));

		lblNguoitao.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		lblNguoitao.setText("Nguyễn Văn An");
		rRoundPanel2.add(lblNguoitao, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 140, -1));

		lblNgaytao.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		lblNgaytao.setText("01-01-2021");
		rRoundPanel2.add(lblNgaytao, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, -1, -1));

		lblGiam.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		lblGiam.setText("20000");
		rRoundPanel2.add(lblGiam, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, -1, -1));

		jLabel8.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 13)); // NOI18N
		jLabel8.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/image-removebg-preview.png"))); // NOI18N
		jLabel8.setText("WOLVESRES");
		rRoundPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

		lblBan.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		lblBan.setText("Bàn 01");
		rRoundPanel2.add(lblBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, -1, -1));

		jLabel10.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		jLabel10.setText("Bàn");
		rRoundPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

		jLabel11.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		jLabel11.setText("Thời gian");
		rRoundPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

		lblTienHang.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		lblTienHang.setText("220000");
		rRoundPanel2.add(lblTienHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, -1, -1));

		lblVoucher.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		lblVoucher.setText("VC01");
		rRoundPanel2.add(lblVoucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, -1, -1));

		jLabel14.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		jLabel14.setText("Giảm:");
		rRoundPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, -1, -1));

		jLabel15.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		jLabel15.setText("Voucher:");
		rRoundPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, -1));

		lblTongtien.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		lblTongtien.setText("200000");
		rRoundPanel2.add(lblTongtien, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 470, -1, -1));

		jLabel17.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		jLabel17.setText("Thuế:");
		rRoundPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

		lblThue.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		lblThue.setText("10%");
		rRoundPanel2.add(lblThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, -1, -1));

		jLabel19.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		jLabel19.setText("Tiền hàng:");
		rRoundPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

		jLabel20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
		rRoundPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 413, 10));

		lblTrangthai.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		lblTrangthai.setText("lblTrangrhai");
		rRoundPanel2.add(lblTrangthai, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 410, -1, -1));

		jLabel22.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
		rRoundPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 413, 10));

		jLabel23.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		jLabel23.setText("Nhân Viên:");
		rRoundPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

		jLabel24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jLabel24.setText("Tổng Tiền:");
		rRoundPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 470, -1, -1));

		jLabel25.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		jLabel25.setText("Mã Hóa đơn");
		rRoundPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

		jLabel26.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
		jLabel26.setText("Trạng Thái:");
		rRoundPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

		add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 120, 470, 560));

		btnDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/banden.png"))); // NOI18N
		btnDS.setText("Xem DS Hóa Đơn Bị Hủy");
		btnDS.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnDS.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDSActionPerformed(evt);
			}
		});
		add(btnDS, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 690, -1, 40));
	}// </editor-fold>//GEN-END:initComponents

	private void btnHDCTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHDCTActionPerformed
		// TODO add your handling code here:
		HoaDonChiTiet hdct = new HoaDonChiTiet(frame, true);
		ModelHoaDon hd = getHDformRowtable(selectedRow);
		hdct.setMdhd(hd);
		hdct.init();
		hdct.setVisible(true);

	}// GEN-LAST:event_btnHDCTActionPerformed

	private void tblHoaDonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblHoaDonMousePressed

		selectedRow = tblHoaDon.getSelectedRow();
		showDetail(selectedRow);
		btnHDCT.setVisible(true);
	}// GEN-LAST:event_tblHoaDonMousePressed

	private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyActionPerformed

		HuyHoaDon(tblHoaDon.getSelectedRow());
	}// GEN-LAST:event_btnHuyActionPerformed

	private void btnDSActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDSActionPerformed
		new HuyHoaDon(frame, true).setVisible(true);
	}// GEN-LAST:event_btnDSActionPerformed

	private void txtFindHoaDonKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtFindHoaDonKeyReleased
		String tuKhoa = txtFindHoaDon.getText().trim();
		List<ModelHoaDon> found = timKiem(tuKhoa, dao);
		list.clear();
		list.addAll(found);
		fillTable();
	}// GEN-LAST:event_txtFindHoaDonKeyReleased

	private void tblHoaDonFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_tblHoaDonFocusGained
		btnHDCT.setVisible(true);
	}// GEN-LAST:event_tblHoaDonFocusGained

	private void tblHoaDonFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_tblHoaDonFocusLost
		btnHDCT.setVisible(false);
	}// GEN-LAST:event_tblHoaDonFocusLost

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.swing.custom.raven.RButton.RButton btnDS;
	private com.swing.custom.raven.RButton.RButton btnHDCT;
	private com.swing.custom.raven.RButton.RButton btnHuy;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel22;
	private javax.swing.JLabel jLabel23;
	private javax.swing.JLabel jLabel24;
	private javax.swing.JLabel jLabel25;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblBan;
	private javax.swing.JLabel lblGiam;
	private javax.swing.JLabel lblMaHD;
	private javax.swing.JLabel lblNgaytao;
	private javax.swing.JLabel lblNguoitao;
	private javax.swing.JLabel lblThue;
	private javax.swing.JLabel lblTienHang;
	private javax.swing.JLabel lblTitle;
	private javax.swing.JLabel lblTongtien;
	private javax.swing.JLabel lblTrangthai;
	private javax.swing.JLabel lblVoucher;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
	private com.wolvesres.swing.table.Table tblHoaDon;
	private com.swing.custom.raven.RTextField.RTextField txtFindHoaDon;
	// End of variables declaration//GEN-END:variables
}
