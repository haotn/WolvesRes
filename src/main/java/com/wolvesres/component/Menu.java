package com.wolvesres.component;

import com.swing.custom.raven.RButton.RButtonMenu;
import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.REvent.REventButtonMenu;
import com.swing.custom.raven.RIcon.GoogleMaterialDesignIcons;
import com.swing.custom.raven.RIcon.IconFontSwing;
import com.swing.custom.raven.RScrollbar.RScrollBarCustom;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.form.FormDoiMK;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelNhanVien;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class Menu extends javax.swing.JPanel {

	public REventButtonMenu event;
	public RButtonMenu menuTongQuan = new RButtonMenu();
	public RButtonMenu menuNhanVien = new RButtonMenu();
	public RButtonMenu menuBanHang = new RButtonMenu();
	public RButtonMenu menuTaiKhoan = new RButtonMenu();
	public RButtonMenu menuBan = new RButtonMenu();
	public RButtonMenu menuThucDon = new RButtonMenu();
	public RButtonMenu menuVoucher = new RButtonMenu();
	public RButtonMenu menuKho = new RButtonMenu();
	public RButtonMenu menuHoaDon = new RButtonMenu();
	public RButtonMenu menuThongKe = new RButtonMenu();
	public RButtonMenu menuDangXuat = new RButtonMenu();
	JFrame frame;

	/**
	 * Profile Color [71,95,77]
	 */
	public Menu() {
		initComponents();
		setOpaque(false);
		RScrollBarCustom sb = new RScrollBarCustom();
		sb.setForeground(new Color(130, 130, 130, 100));
		jScrollPane2.setVerticalScrollBar(sb);
		pnlMenu.setLayout(new MigLayout("wrap, fillx, inset 3", "[fill]", "[]0[]"));
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void initMenu(REventButtonMenu event) {
		this.event = event;
		Icon iconDoiMatKhau = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LOCK_OUTLINE, 32,
				new Color(0, 199, 135));
		btnDoiMatKhau.setIcon(iconDoiMatKhau);
		Icon iconTongQuan = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.VIEW_QUILT, 32, new Color(0, 199, 135));
		Icon iconNhanVien = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PEOPLE, 32, new Color(0, 199, 135));
		Icon iconBanhang = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.SHOPPING_BASKET, 32,
				new Color(0, 199, 135));
		Icon iconTaiKhoan = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PERSON, 32, new Color(0, 199, 135));
		Icon iconThucDon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.RESTAURANT_MENU, 32,
				new Color(0, 199, 135));
		Icon iconBan = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ROOM_SERVICE, 32, new Color(0, 199, 135));
		Icon iconVoucher = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.IMPORTANT_DEVICES, 32,
				new Color(0, 199, 135));
		Icon iconKho = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.HOME, 32, new Color(0, 199, 135));
		Icon iconHoaDon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.RECEIPT, 32, new Color(0, 199, 135));
		Icon iconThongKe = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.TRENDING_UP, 32, new Color(0, 199, 135));
		Icon iconDangXuat = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.EXIT_TO_APP, 32, new Color(0, 199, 135));
		if (Auth.isManager() || Auth.isBoss()) {
//            new ImageIcon(getClass().getResource("/com/wolvesres/icon/overview.png"))
			addMenu(menuTongQuan, iconTongQuan, "Tổng quan", 0);
			addMenu(menuNhanVien, iconNhanVien, "Nhân viên", 1);
			addMenu(menuBanHang, iconBanhang, "Bán hàng", 2);
			addMenu(menuTaiKhoan, iconTaiKhoan, "Tài khoản", 3);
			addMenu(menuThucDon, iconThucDon, "Thực đơn", 4);
			addMenu(menuBan, iconBan, "Bàn", 5);
			addMenu(menuVoucher, iconVoucher, "Voucher", 6);
			addMenu(menuKho, iconKho, "Kho", 7);
			addMenu(menuHoaDon, iconHoaDon, "Hóa đơn", 8);
			if (Auth.isBoss()) {
				addMenu(menuThongKe, iconThongKe, "Thống kê", 9);
			}
		}
		if (!Auth.isBoss() && !Auth.isManager()) {
			addMenu(menuTongQuan, iconTongQuan, "Tổng quan", 0);
			// addMenu(new
			// ImageIcon(getClass().getResource("/com/wolvesres/icon/staff.png")), "NhÃ¢n
			// viÃªn", 1);
			addMenu(menuBanHang, iconBanhang, "Bán hàng", 2);
			// addMenu(new
			// ImageIcon(getClass().getResource("/com/wolvesres/icon/user.png")), "TÃ i
			// khoáº£n", 3);
			addMenu(menuThucDon, iconThucDon, "Thực đơn", 4);
			addMenu(menuBan, iconBan, "Bàn", 5);
			addMenu(menuVoucher, iconVoucher, "Voucher", 6);
			addMenu(menuKho, iconKho, "Kho", 7);
			addMenu(menuHoaDon, iconHoaDon, "Hóa đơn", 8);
		}

		addEmpty();
		addMenu(menuDangXuat, iconDangXuat, "Đăng xuất", 10);
	}

	private void addEmpty() {
		pnlMenu.add(new JLabel(), "push");
	}

	private void addMenu(String text, final int index) {
		final RButtonMenu menu = new RButtonMenu();
		menu.setText("  " + text);
		menu.setFont(new Font("SansSerif", 1, 14));
		pnlMenu.add(menu);
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				event.selected(index);
				setSelected(menu);
			}
		});
	}

	private void addMenu(final RButtonMenu menu, Icon icon, String text, final int index) {
		menu.setIcon(icon);
		menu.setText("  " + text);
		menu.setFont(new Font("SansSerif", 1, 14));
		pnlMenu.add(menu);
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				event.selected(index);
				setSelected(menu);
			}
		});
	}

	public void setSelected(RButtonMenu menu) {
		for (Component com : pnlMenu.getComponents()) {
			if (com instanceof RButtonMenu) {
				RButtonMenu b = (RButtonMenu) com;
				b.setSelected(false);
			}
		}
		menu.setSelected(true);
	}

	public void setText() {
		ModelNhanVien nv = Auth.user;
		lblChucVu.setText(nv.getTenChucVu(nv.getChucVu()));
		lblTenNV.setText(nv.getHoTen());
		if (nv.getPathHinhAnh() != null) {
			avatar.setIcon(XImage.readImageNhanVien(nv.getPathHinhAnh()));
		}
	}

	private void changeAvatar() {
		if (fileChooser.showOpenDialog(this) == fileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			XImage.saveImageNhanVien(file);
			ImageIcon icon = XImage.readImageNhanVien(file.getName());
			avatar.setIcon(icon);
			avatar.setToolTipText(file.getName());
			NhanVienDAO nvDAO = new NhanVienDAO();
			ModelNhanVien emp = nvDAO.selectById(Auth.user.getMaNV());
			emp.setPathHinhAnh(avatar.getToolTipText());
			nvDAO.update(emp, emp.getMaNV());
		}
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		fileChooser = new javax.swing.JFileChooser();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		avatar = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		lblTenNV = new javax.swing.JLabel();
		lblChucVu = new javax.swing.JLabel();
		btnDoiMatKhau = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		pnlMenu = new javax.swing.JPanel();

		setMaximumSize(new java.awt.Dimension(200, 730));
		setMinimumSize(new java.awt.Dimension(200, 730));
		setPreferredSize(new java.awt.Dimension(200, 730));

		rRoundPanel2.setBackground(new java.awt.Color(0, 0, 0));
		rRoundPanel2.setMaximumSize(new java.awt.Dimension(200, 100));
		rRoundPanel2.setMinimumSize(new java.awt.Dimension(200, 100));
		rRoundPanel2.setPreferredSize(new java.awt.Dimension(200, 100));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/a.jpg"))); // NOI18N
		avatar.setPreferredSize(new java.awt.Dimension(70, 70));
		avatar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				avatarMousePressed(evt);
			}
		});
		rRoundPanel2.add(avatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 58, 78));

		lblTenNV.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblTenNV.setForeground(new java.awt.Color(255, 255, 255));
		lblTenNV.setText("áº¾ch Xanh Cute");
		rRoundPanel2.add(lblTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 16, -1, -1));

		lblChucVu.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
		lblChucVu.setForeground(new java.awt.Color(255, 255, 255));
		lblChucVu.setText("Admin");
		rRoundPanel2.add(lblChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 41, -1, -1));

		btnDoiMatKhau.setBackground(new java.awt.Color(102, 102, 102));
		btnDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/resetPass.png"))); // NOI18N
		btnDoiMatKhau.setPreferredSize(new java.awt.Dimension(35, 35));
		btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDoiMatKhauActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnDoiMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 35, 35));

		rRoundPanel3.setBackground(new java.awt.Color(0, 0, 0));
		rRoundPanel3.setMaximumSize(new java.awt.Dimension(200, 100));
		rRoundPanel3.setMinimumSize(new java.awt.Dimension(200, 100));
		rRoundPanel3.setPreferredSize(new java.awt.Dimension(200, 100));

		jScrollPane2.setBorder(null);

		pnlMenu.setBackground(new java.awt.Color(0, 0, 0));

		javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
		pnlMenu.setLayout(pnlMenuLayout);
		pnlMenuLayout.setHorizontalGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 176, Short.MAX_VALUE));
		pnlMenuLayout.setVerticalGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 597, Short.MAX_VALUE));

		jScrollPane2.setViewportView(pnlMenu);

		javax.swing.GroupLayout rRoundPanel3Layout = new javax.swing.GroupLayout(rRoundPanel3);
		rRoundPanel3.setLayout(rRoundPanel3Layout);
		rRoundPanel3Layout
				.setHorizontalGroup(rRoundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(rRoundPanel3Layout.createSequentialGroup().addContainerGap()
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addContainerGap()));
		rRoundPanel3Layout
				.setVerticalGroup(rRoundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(rRoundPanel3Layout.createSequentialGroup().addContainerGap()
								.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
								.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(rRoundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(rRoundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(rRoundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(10, 10, 10)
						.addComponent(rRoundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDoiMatKhauActionPerformed
		FormDoiMK doiML = new FormDoiMK();
		doiML.setVisible(true);
	}// GEN-LAST:event_btnDoiMatKhauActionPerformed

	private void avatarMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_avatarMousePressed
		if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn đổi ảnh avatar không?",
				ROptionDialog.WARNING, Color.yellow, Color.black)) {
			changeAvatar();
		}
	}// GEN-LAST:event_avatarMousePressed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.swing.custom.raven.RImageAvatar.RImageAvatar avatar;
	private com.swing.custom.raven.RButton.RButton btnDoiMatKhau;
	private javax.swing.JFileChooser fileChooser;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JLabel lblChucVu;
	private javax.swing.JLabel lblTenNV;
	private javax.swing.JPanel pnlMenu;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	// End of variables declaration//GEN-END:variables
}
