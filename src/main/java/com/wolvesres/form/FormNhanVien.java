package com.wolvesres.form;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RIcon.GoogleMaterialDesignIcons;
import com.swing.custom.raven.RIcon.IconFontSwing;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.form.nhanvien.BlackListNhanVien;
import com.wolvesres.form.nhanvien.EditNhanVien;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelTaiKhoan;
import com.wolvesres.swing.table.EventAction;
import com.wolvesres.swing.table.ModelProfile;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * Cac class lien quan: FormNhanVien, NhanVienDAO, EditNhanVien,
 * BlackListNhanVien
 * 
 * @author Brian
 *
 */
public class FormNhanVien extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	public FormNhanVien(JFrame frame) {
		initComponents();
		setOpaque(false);
		init();
		this.frame = frame;
	}

	/**
	 * Generate variable
	 */
	private JFrame frame;
	private List<ModelNhanVien> listNhanVien = new ArrayList<ModelNhanVien>();
	private List<ModelNhanVien> whiteList = new ArrayList<ModelNhanVien>();
	private NhanVienDAO dao = new NhanVienDAO();
	private List<ModelTaiKhoan> listTaiKhoan = new ArrayList<ModelTaiKhoan>();
	private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

	private DefaultTableModel model;
	private int index = -1;
	/**
	 * Event action on table
	 */
	private EventAction<ModelNhanVien> eventAction = new EventAction<ModelNhanVien>() {
		public void delete(ModelNhanVien entity) {
			if (roleIsValid(entity)) {
				if (isNotConflicForeignKey(entity, dao)) {
					if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn xóa nhân viên này không?",
							ROptionDialog.WARNING, Color.red, Color.black)) {
						deleteEmployee(entity);
						listNhanVien.remove(entity);
						fillToTable();
						index = 0;
						showDetail(getEmployeeFromRowTable(index));
					}
				} else {
					if (ROptionDialog.showConfirm(frame, "Thông báo xác nhận",
							"Không thể xóa, xác nhận đưa vào danh sách đen?", ROptionDialog.PRIORITY_HIGHT, Color.red,
							Color.black)) {
						addToBlackList(entity);
						fillToTable();
						index = 0;
						showDetail(getEmployeeFromRowTable(index));
					}
				}
			} else {
				ROptionDialog.showAlert(frame, "Thông báo", "Bạn không có quyền xóa quản lý!", ROptionDialog.WARNING,
						Color.red, Color.black);
			}

		}

		public void update(ModelNhanVien nhanVien) {
			EditNhanVien editForm = new EditNhanVien(frame, true);
			editForm.setInsert(false);
			editForm.setNhanVien(nhanVien);
			editForm.setForm();
			editForm.setVisible(true);
			if (editForm.getIsDispose() == false) {
				for (int i = 0; i < listNhanVien.size(); i++) {
					if (listNhanVien.get(i).getMaNV().equals(editForm.getNhanVien().getMaNV())) {
						listNhanVien.set(i, editForm.getNhanVien());
						break;
					}
				}
				fillToTable();
				showDetail(editForm.getNhanVien());
			}
		}
	};

	/**
	 * Init method
	 */
	private void init() {
		initTable();
		loadToList();
		fillToTable();
		showDetail(whiteList.get(0));
		Icon iconThemNV = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PERSON_ADD, 32, new Color(0, 199, 135));
		Icon iconDSDen = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FEATURED_PLAY_LIST, 32,
				new Color(0, 199, 135));
		Icon iconAddDSDen = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.REMOVE_CIRCLE, 32,
				new Color(255, 51, 51));
		btnThem.setIcon(iconThemNV);
		btnDuaVaoDSDen.setIcon(iconAddDSDen);
		btnXemDanhSachDen.setIcon(iconDSDen);
	}

	/**
	 * Load database
	 */
	private void loadToList() {
		for (ModelNhanVien modelNhanVien : dao.selectAll()) {
			if (!modelNhanVien.getMaNV().equals(Auth.user.getMaNV())) {
				if (modelNhanVien.getChucVu() != 1) {
					listNhanVien.add(modelNhanVien);
				}
			}
		}
		listTaiKhoan.addAll(taiKhoanDAO.selectAll());
	}

	/**
	 * Check if Auth.user is Manager and entity is not Manager
	 * 
	 * @param entity
	 * @return role is valid
	 */
	public Boolean roleIsValid(ModelNhanVien entity) {
		if (Auth.isBoss()) {
			return true;
		} else if (Auth.isManager() && entity.getChucVu() != 2) {
			return true;
		}
		return false;
	}

	/**
	 * Check if entity is not conflic with any foreign key
	 * 
	 * @param entity
	 * @param nvDao
	 * @return is not conflic
	 */
	public Boolean isNotConflicForeignKey(ModelNhanVien entity, NhanVienDAO nvDao) {
		return nvDao.checkForeignKey(entity.getMaNV()) == null;
	}

	/**
	 * Add to whitelist
	 */
	private void loadToWhiteList() {
		whiteList.clear();
		for (ModelNhanVien emp : listNhanVien) {
			if (emp.isTrangThai()) {
				whiteList.add(emp);
			}
		}
	}

	/**
	 * Return employee from a row on table
	 *
	 * @param row
	 * @return
	 */
	private ModelNhanVien getEmployeeFromRowTable(int row) {
		ModelNhanVien emp = null;
		if (row >= 0) {
			Object o = (Object) tblNhanVien.getValueAt(row, 0);
			if (o instanceof ModelProfile) {
				ModelProfile mpf = (ModelProfile) o;
				emp = mpf.getEmp();
			}
//            String soDT = String.valueOf(tblNhanVien.getValueAt(index, 3));
//            for (int i = 0; i < whiteList.size(); i++) {
//                if (soDT.equals(whiteList.get(i).getSoDT())) {
//                    emp = whiteList.get(i);
//                }
//            }
		}
		return emp;
	}

	/**
	 * Get listNhanVien employee
	 *
	 * @return
	 */
	public List<ModelNhanVien> getList() {
		return this.listNhanVien;
	}

	/**
	 * Fill white listNhanVien to table
	 */
	private void fillToTable() {
		model.setRowCount(0);
		loadToWhiteList();
		for (ModelNhanVien emp : whiteList) {
			if (emp.getPathHinhAnh() != null) {
				emp.setIcon(XImage.readImageNhanVien(emp.getPathHinhAnh()));
			} else {
				emp.setIcon(null);
			}
			tblNhanVien.addRow(emp.toRowTable(eventAction));
		}
	}

	/**
	 * Show detail employee
	 *
	 * @param emp
	 */
	private void showDetail(ModelNhanVien emp) {
		// if (selectedRow >= 0) {
		if (emp.getPathHinhAnh() != null) {
			lblAvatar.setIcon(XImage.readImageNhanVien(emp.getPathHinhAnh()));
		}
		lblHoTen.setText(emp.getHoTen());
		lblCCCD.setText(emp.getCMND());
		lblChucVu.setText(emp.getTenChucVu(emp.getChucVu()));
		lblEmail.setText(emp.getEmail());
		lblGioiTinh.setText(emp.isGioiTinh() ? "Nam" : "Nữ");
		lblMaNV.setText(emp.getMaNV());
		lblNgaySinh.setText(emp.getNgaySinh());
		lblSDT.setText(emp.getSoDT());
		lblTrangThai.setText(emp.isTrangThai() ? "Còn làm việc" : "Đã nghỉ việc");
		// }
	}

	/**
	 * Delete employee from listNhanVien
	 *
	 * @param entity
	 */
	private void deleteEmployee(ModelNhanVien entity) {
		entity.remove();
	}

	/**
	 * Init table
	 */
	private void initTable() {
		tblNhanVien.setOpaque(true);
		tblNhanVien.setBackground(new Color(51, 51, 51));
		tblNhanVien.setFillsViewportHeight(true);
		tblNhanVien.fixTable(jScrollPane2);
		tblNhanVien.setFont(new Font("SansSerif", 1, 12));
		model = new DefaultTableModel(new Object[][] {},
				new Object[] { "Họ tên", "Giới tính", "Chức vụ", "Số ĐT", "Thao tác" });
		tblNhanVien.setModel(model);
		tblNhanVien.setColumnAction(4);
	}

	private List<ModelNhanVien> timKiemNhanVien(String keyword, NhanVienDAO dao, List<ModelNhanVien> whiteList) {
		List<ModelNhanVien> listFound = new ArrayList<ModelNhanVien>();
		for (int i = 0; i < whiteList.size(); i++) {
			if (keyword.trim().length() != 0) {
				listFound = dao.findNhanVien(keyword);
			} else {
				listFound.addAll(whiteList);
			}
		}
		return listFound;
	}

	/**
	 * Get ModelTaiKhoan by ma nhan vien
	 * 
	 * @param manv
	 * @return ModelTaiKhoan
	 */
	private ModelTaiKhoan getTaiKhoanByMaNV(String manv) {
		ModelTaiKhoan account = new ModelTaiKhoan();
		for (int i = 0; i < listTaiKhoan.size(); i++) {
			if (manv.equals(listTaiKhoan.get(i).getTaiKhoan())) {
				account = listTaiKhoan.get(i);
				break;
			}
		}
		return account;
	}

	/**
	 * Add entity to black list
	 * 
	 * @param emp
	 */
	private void addToBlackList(ModelNhanVien emp) {
		emp.addToBlackList();
		ModelTaiKhoan account = null;
		account = getTaiKhoanByMaNV(emp.getMaNV());
		if (account != null) {
			if (account.isTrangThai()) {
				account.setTrangThai(false);
				taiKhoanDAO.update(account, account.getTaiKhoan());
			}
		}
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		rRoundPanel5 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		tblNhanVien = new com.wolvesres.swing.table.Table();
		btnXemDanhSachDen = new com.swing.custom.raven.RButton.RButton();
		jLabel10 = new javax.swing.JLabel();
		btnThem = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel2 = new javax.swing.JLabel();
		lblTrangThai = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		lblHoTen = new javax.swing.JLabel();
		lblMaNV = new javax.swing.JLabel();
		lblChucVu = new javax.swing.JLabel();
		lblCCCD = new javax.swing.JLabel();
		lblNgaySinh = new javax.swing.JLabel();
		lblGioiTinh = new javax.swing.JLabel();
		lblSDT = new javax.swing.JLabel();
		jLabel19 = new javax.swing.JLabel();
		lblEmail = new javax.swing.JLabel();
		btnDuaVaoDSDen = new com.swing.custom.raven.RButton.RButton();
		lblAvatar = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		txtFind = new com.swing.custom.raven.RTextField.RTextField();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		rImageAvatar2 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();

		setMaximumSize(new java.awt.Dimension(1170, 730));
		setMinimumSize(new java.awt.Dimension(1170, 730));
		setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		rRoundPanel1.setBackground(new java.awt.Color(25, 25, 25));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		rRoundPanel5.setBackground(new java.awt.Color(6, 7, 13));
		rRoundPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jScrollPane2.setBorder(null);

		tblNhanVien.setAutoCreateRowSorter(true);
		tblNhanVien
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		tblNhanVien.setOpaque(false);
		tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblNhanVienMousePressed(evt);
			}
		});
		jScrollPane2.setViewportView(tblNhanVien);

		rRoundPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 720, 510));

		btnXemDanhSachDen.setText("Xem danh sách đem");
		btnXemDanhSachDen.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnXemDanhSachDen.setPreferredSize(new java.awt.Dimension(175, 30));
		btnXemDanhSachDen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXemDanhSachDenActionPerformed(evt);
			}
		});
		rRoundPanel5.add(btnXemDanhSachDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 680, 210, 40));

		jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel10.setForeground(new java.awt.Color(255, 255, 255));
		jLabel10.setText("Danh sách nhân viên");
		rRoundPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 190, 30));

		btnThem.setText("Thêm nhân viên");
		btnThem.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnThem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThemActionPerformed(evt);
			}
		});
		rRoundPanel5.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 170, 40));

		rRoundPanel2.setBackground(new java.awt.Color(6, 7, 13));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(204, 204, 204));
		jLabel2.setText("Chức vụ:");
		rRoundPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, -1, 30));

		lblTrangThai.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblTrangThai.setForeground(new java.awt.Color(255, 255, 255));
		lblTrangThai.setText("Đã nghỉ việc");
		rRoundPanel2.add(lblTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 520, -1, 30));

		jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel4.setForeground(new java.awt.Color(204, 204, 204));
		jLabel4.setText("Ngày Sinh:");
		rRoundPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, -1, 30));

		jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(204, 204, 204));
		jLabel5.setText("Giới tính:");
		rRoundPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, -1, 40));

		jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel6.setForeground(new java.awt.Color(204, 204, 204));
		jLabel6.setText("Số điện thoại:");
		rRoundPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 460, -1, 30));

		jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel7.setForeground(new java.awt.Color(204, 204, 204));
		jLabel7.setText("Trạng thái");
		rRoundPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 520, -1, 30));

		jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel8.setForeground(new java.awt.Color(204, 204, 204));
		jLabel8.setText("CMND/CCCD:");
		rRoundPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, -1, 30));

		jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel9.setForeground(new java.awt.Color(204, 204, 204));
		jLabel9.setText("Mã nhân viên:");
		rRoundPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, 30));

		jLabel11.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel11.setForeground(new java.awt.Color(204, 204, 204));
		jLabel11.setText("Họ Tên:");
		rRoundPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, -1, 30));

		lblHoTen.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblHoTen.setForeground(new java.awt.Color(255, 255, 255));
		lblHoTen.setText("a");
		rRoundPanel2.add(lblHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, -1, 30));

		lblMaNV.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblMaNV.setForeground(new java.awt.Color(255, 255, 255));
		lblMaNV.setText("a");
		rRoundPanel2.add(lblMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 310, -1, 30));

		lblChucVu.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblChucVu.setForeground(new java.awt.Color(255, 255, 255));
		lblChucVu.setText("a");
		rRoundPanel2.add(lblChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, -1, 30));

		lblCCCD.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblCCCD.setForeground(new java.awt.Color(255, 255, 255));
		lblCCCD.setText("a");
		rRoundPanel2.add(lblCCCD, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 370, -1, 30));

		lblNgaySinh.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblNgaySinh.setForeground(new java.awt.Color(255, 255, 255));
		lblNgaySinh.setText("a");
		rRoundPanel2.add(lblNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, -1, 30));

		lblGioiTinh.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblGioiTinh.setForeground(new java.awt.Color(255, 255, 255));
		lblGioiTinh.setText("a");
		rRoundPanel2.add(lblGioiTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, -1, 40));

		lblSDT.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblSDT.setForeground(new java.awt.Color(255, 255, 255));
		lblSDT.setText("a");
		rRoundPanel2.add(lblSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 460, -1, 30));

		jLabel19.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel19.setForeground(new java.awt.Color(204, 204, 204));
		jLabel19.setText("Email:");
		rRoundPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 490, -1, 30));

		lblEmail.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblEmail.setForeground(new java.awt.Color(255, 255, 255));
		lblEmail.setText("a");
		rRoundPanel2.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 490, -1, 30));

		btnDuaVaoDSDen.setText("Đưa vào danh sách đen");
		btnDuaVaoDSDen.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnDuaVaoDSDen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDuaVaoDSDenActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnDuaVaoDSDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 570, 230, 40));
		rRoundPanel2.add(lblAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 220, 210));

		txtFind.setBackground(new java.awt.Color(6, 7, 13));
		txtFind.setForeground(new java.awt.Color(255, 255, 255));
		txtFind.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtFind.setLabelText("Tìm kiếm nhân viên");
		txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtFindKeyReleased(evt);
			}
		});
		rRoundPanel2.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 50));

		rRoundPanel5.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, 450, 620));

		rRoundPanel3.setBackground(new java.awt.Color(0, 199, 135));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel12.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel12.setForeground(new java.awt.Color(255, 255, 255));
		jLabel12.setText("NHÂN VIÊN");
		rRoundPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 0, 250, 110));

		jLabel13.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
		jLabel13.setForeground(new java.awt.Color(255, 255, 255));
		jLabel13.setText("WolvesRes");
		rRoundPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 40, 160, 30));

		rImageAvatar2.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel3.add(rImageAvatar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 120, 110));

		rRoundPanel5.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 110));

		rRoundPanel1.add(rRoundPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 730));

		add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 730));
	}// </editor-fold>//GEN-END:initComponents

	private void btnXemDanhSachDenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXemDanhSachDenActionPerformed
		/**
		 * Nhan listReturn tu blackList va cap nhat vao list
		 */
		BlackListNhanVien bls = new BlackListNhanVien(frame, true);
		bls.setVisible(true);
		if (bls.getIsChangeData()) {
			for (int i = 0; i < listNhanVien.size(); i++) {
				for (int j = 0; j < bls.getListReturn().size(); j++) {
					if (listNhanVien.get(i).getMaNV().equals(bls.getListReturn().get(j).getMaNV())) {
						listNhanVien.set(i, bls.getListReturn().get(j));
						break;
					}
				}
			}
			fillToTable();
		}
	}// GEN-LAST:event_btnXemDanhSachDenActionPerformed

	private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
		/**
		 * Nhan gia tri tra ve tu formEdit, them moi nhan vien tu gia tri tra ve
		 */
		EditNhanVien editForm = new EditNhanVien(frame, true);
		editForm.setInsert(true);
		editForm.setVisible(true);
		if (editForm.getNhanVien() != null) {
			listNhanVien.add(editForm.getNhanVien());
			fillToTable();
			showDetail(editForm.getNhanVien());
		}

	}// GEN-LAST:event_btnThemActionPerformed

	private void tblNhanVienMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblNhanVienMousePressed
		index = tblNhanVien.getSelectedRow();
		if (index >= 0) {
			showDetail(getEmployeeFromRowTable(index));
		}
	}// GEN-LAST:event_tblNhanVienMousePressed

	private void btnDuaVaoDSDenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDuaVaoDSDenActionPerformed
		if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn đưa nhân viên vào danh sách đen không?",
				ROptionDialog.WARNING, Color.yellow, Color.black)) {
			if (index >= 0) {
				ModelNhanVien emp = getEmployeeFromRowTable(index);
				addToBlackList(emp);
				fillToTable();
				index = 0;
				showDetail(getEmployeeFromRowTable(index));
			}
		}
	}// GEN-LAST:event_btnDuaVaoDSDenActionPerformed

	private void txtFindKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtFindKeyReleased
		String keyword = txtFind.getText().trim();
		listNhanVien = timKiemNhanVien(keyword, dao, whiteList);
		fillToTable();
	}// GEN-LAST:event_txtFindKeyReleased

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.swing.custom.raven.RButton.RButton btnDuaVaoDSDen;
	private com.swing.custom.raven.RButton.RButton btnThem;
	private com.swing.custom.raven.RButton.RButton btnXemDanhSachDen;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JScrollPane jScrollPane2;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar lblAvatar;
	private javax.swing.JLabel lblCCCD;
	private javax.swing.JLabel lblChucVu;
	private javax.swing.JLabel lblEmail;
	private javax.swing.JLabel lblGioiTinh;
	private javax.swing.JLabel lblHoTen;
	private javax.swing.JLabel lblMaNV;
	private javax.swing.JLabel lblNgaySinh;
	private javax.swing.JLabel lblSDT;
	private javax.swing.JLabel lblTrangThai;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel5;
	private com.wolvesres.swing.table.Table tblNhanVien;
	private com.swing.custom.raven.RTextField.RTextField txtFind;
	// End of variables declaration//GEN-END:variables
}
