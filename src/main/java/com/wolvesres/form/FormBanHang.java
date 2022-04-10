package com.wolvesres.form;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RScrollbar.RScrollBarCustom;
import com.wolvesres.bill.BillPrint;
import com.wolvesres.bill.KitchenPrint;
import com.wolvesres.component.SanPham;
import com.wolvesres.component.SanPhamOrder;
import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.BanDAO;
import com.wolvesres.dao.BanOrderDAO;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.dao.HoaDonChiTietDAO;
import com.wolvesres.dao.HoaDonDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.OrderDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.dao.VoucherDAO;
import com.wolvesres.form.voucher.ScanVoucher;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.helper.XImage;
import com.wolvesres.main.Main;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelBanOrder;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelHoaDon;
import com.wolvesres.model.ModelHoaDonChiTiet;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelOrder;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.model.ModelVouCher;
import com.wolvesres.swing.table.EventActionBlackList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 * Cac class lien quan: ModelHoaDon, ModelHoaDonChiTiet, ModelVoucher,
 * ModelOrder, ModelBan, ModelBanOrder, ModelKho
 * 
 * @author Brian Cho cap nhat method tim kiem san pham tu sanPhamDAO 
 */
public class FormBanHang extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param frame
	 */
	public FormBanHang(JFrame frame) {
		initComponents();
		this.frame = frame;
		setOpaque(false);
		init();
	}

	JFrame frame;
	/**
	 * Generate global variable
	 */
	private List<ModelSanPham> listSanPhams = new ArrayList<ModelSanPham>();
	private List<ModelSanPham> whiteListSanPham = new ArrayList<ModelSanPham>();
	private List<ModelOrder> listOrder = new ArrayList<ModelOrder>();
	private List<ModelBan> listBan = new ArrayList<ModelBan>();
	private List<ModelBanOrder> listBanOrder = new ArrayList<ModelBanOrder>();
	private List<ModelKho> listKho = new ArrayList<ModelKho>();
	private List<ModelVouCher> listVouCher = new ArrayList<ModelVouCher>();
	private List<ModelDanhMuc> listDanhMuc = new ArrayList<ModelDanhMuc>();
	private List<SanPham> listSanPhamComp = new ArrayList<SanPham>();
	private List<SanPhamOrder> listOrderComp = new ArrayList<SanPhamOrder>();
	/**
	 * DAO
	 */
	private AutoDAO autoDAO = new AutoDAO();
	private SanPhamDAO sanPhamDAO = new SanPhamDAO();
	private DanhMucDAO danhMucDAO = new DanhMucDAO();
	private HoaDonDAO hoaDonDAO = new HoaDonDAO();
	private HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAO();
	private BanDAO banDAO = new BanDAO();
	private DonViTinhDAO donViTinhDAO = new DonViTinhDAO();
	private OrderDAO orderDAO = new OrderDAO();
	private BanOrderDAO banOrderDAO = new BanOrderDAO();
	private KhoDAO khoDAO = new KhoDAO();
	private VoucherDAO voucherDAO = new VoucherDAO();
	/**
	 * Model
	 */
	private ModelBanOrder banOrderGlobal = new ModelBanOrder();
	private DefaultTableModel model;
	private Thread t;
	private float tongTien = 0;
	private float thue = 0;
	private float giamGia = 0;
	private float tienTraLai = 0;
	private float tienHang = 0;
	private boolean confirm = true;
	private boolean isVoucherValid = false;
	private boolean acceptPtint = false;
	private boolean checkForApply = false;

	private ActionListener actionHuyHoaDon;

	private int indexTableORder = -1;
	private int indexTableSP = -1;
	/**
	 * Event action
	 */
	private EventActionBlackList<ModelSanPham> eventAddToOrder = new EventActionBlackList<ModelSanPham>() {
		public void update(ModelSanPham entity) {
			orderSanPham(entity);
			String maVoucher = lblMaVoucher.getText().trim();
			tinhTongTien(getTienKhachDua(), maVoucher);
		}
	};
//    private EventActionBlackList<ModelOrder> eventAction = new EventActionBlackList<ModelOrder>() {
//        @Override
//        public void update(ModelOrder entity) {
//            if (ROptionDialog.showConfirm(frame, "Xác nhận","Xác nhận xóa sản phẩm khỏi danh sách gọi món?", ROptionDialog.WARNING, Color.red, Color.black)) {
//                ModelKho khoEn = null;
//                for (int i = 0; i < listKho.size(); i++) {
//                    if (listKho.get(i).getMaSP().equals(entity.getMaSP())) {
//                        khoEn = new ModelKho();
//                        khoEn = listKho.get(i);
//                        break;
//                    }
//                }
//                if (khoEn != null) {
//                    ModelSanPham sanPham = getSanPhamByMaSP(khoEn.getMaSP());
//                    if (sanPham.isMatHang()) {
//                        khoEn.setSoLuong(khoEn.getSoLuong() + entity.getSoLuong());
//                        updateKho(khoEn);
//                    }
//                }
//                deleteOrder(entity);
//                tinhTongTien();
//            }
//        }
//    };

	/**
	 * Set event huy hoa don
	 * 
	 * @param actionHuyHoaDon
	 */
	public void setActionHuyHoaDon(ActionListener actionHuyHoaDon) {
		this.actionHuyHoaDon = actionHuyHoaDon;
	}

	/**
	 * Init method
	 */
	private void init() {
		initPnlSanPham();
		initPnlOrder();
		reInit();
	}

	/**
	 * Reinit method
	 */
	private void reInit() {
		this.banOrderGlobal = Main.banOrderGlobal;
		loadToList();
		fillToPnlSanPham(listSanPhamComp);
		fillToPnlOrder();
		setForm();
	}

	/**
	 * Load data to list
	 */
	public void loadToList() {
		listBan.clear();
		listBanOrder.clear();
		listDanhMuc.clear();
		listKho.clear();
		listOrder.clear();
		listSanPhams.clear();
		listVouCher.clear();
		listBan.addAll(banDAO.selectAll());
		listBanOrder.addAll(banOrderDAO.selectAll());
		listOrder.addAll(orderDAO.selectAllByIdBan(banOrderGlobal));
		listSanPhams.addAll(sanPhamDAO.selectAll());
		listKho.addAll(khoDAO.selectAll());
		listBan.addAll(banDAO.selectAll());
		listVouCher.addAll(voucherDAO.selectAll());
		listDanhMuc.addAll(danhMucDAO.selectAll());
	}

	/**
	 * init Table San Pham
	 */
	private void initPnlSanPham() {
		pnlContainSanPham.setLayout(null);
		jScrollPane3.setVerticalScrollBar(new RScrollBarCustom());
	}

	/**
	 * Sort white list sanpham
	 */
	@SuppressWarnings("unused")
	private void sortWhiteListSanPham() {
		Comparator<ModelSanPham> com = new Comparator<ModelSanPham>() {
			public int compare(ModelSanPham o1, ModelSanPham o2) {
				String ten1 = o1.getTenSP();
				String ten2 = o2.getTenSP();
				return ten1.compareTo(ten2);
			}
		};
		whiteListSanPham.sort(com);
	}

	/**
	 * Load to list san pham component
	 */
	private void loadToListSanPhamComp() {
		loadToWhiteListSanPham();
		// sortWhiteListSanPham();
		listSanPhamComp.clear();
		for (int i = 0; i < whiteListSanPham.size(); i++) {
			final SanPham spComp = new SanPham();
			spComp.setProduct(whiteListSanPham.get(i));
			spComp.setActionOrder(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					orderSanPham(spComp.getProduct());
				}
			});
			spComp.init();
			listSanPhamComp.add(spComp);
		}
	}

	/**
	 * Fill component san pham to panel
	 * 
	 * @param list
	 */
	public void fillToPnlSanPham(List<SanPham> list) {
		loadToListSanPhamComp();
		pnlContainSanPham.removeAll();
		pnlContainSanPham.revalidate();
		pnlContainSanPham.repaint();
		int x = 0;
		int y = 0;
		int xShift = 220;
		int yShift = 260;
		int width = 210;
		int height = 250;

		int rowCount = list.size() / 2;
		int du = list.size() % 2;
		if (du > 0) {
			rowCount += 1;
		}
		int pnlHeight = yShift * rowCount;
		pnlContainSanPham.setPreferredSize(new Dimension(1130, pnlHeight));
		int entityCount = 0;
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < 2; j++) {
				final SanPham entity = list.get(entityCount);
				entity.setActionOrder(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						orderSanPham(entity.getProduct());

						String maVoucher = lblMaVoucher.getText().trim();
						tinhTongTien(getTienKhachDua(), maVoucher);
					}
				});
				if (entity.getProduct().getPathAnh() != null) {
					entity.getProduct().setIcon(XImage.readImageThucDon(entity.getProduct().getPathAnh()));
				} else {
					entity.getProduct().setIcon(null);
				}
				entity.setBounds(x, y, width, height);
				pnlContainSanPham.add(entity);
				x += xShift;
				if (entityCount == (list.size() - 1)) {
					break;
				} else {
					entityCount++;
				}
			}
			y += yShift;
			x = 0;
		}
	}

	/**
	 * init Table Order
	 */
	private void initPnlOrder() {
		pnlContainOrder.setLayout(null);
		jScrollPane2.setVerticalScrollBar(new RScrollBarCustom());
	}

	public float getTienKhachDua() {
		float tienKhachDua = 0;
		String tienKhachDuaString = txtTienKhachDua.getText();
		if (FormValidator.isTextIsNotEmpty(tienKhachDuaString)) {
			try {
				tienKhachDua = Float.parseFloat(tienKhachDuaString.trim());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return tienKhachDua;
	}

	/**
	 * Load to list order component
	 */
	private void loadToListOrderComp() {
		listOrderComp.clear();
		String tienKhachDuaString = txtTienKhachDua.getText();
		float tienKhachDua = 0;
		if (FormValidator.isTextIsNotEmpty(tienKhachDuaString)) {
			Float.parseFloat(tienKhachDuaString);
		}
		String maVoucher = lblMaVoucher.getText().trim();
		if (listOrder.size() > 0) {
			for (int i = 0; i < listOrder.size(); i++) {
				final ModelOrder entity = listOrder.get(i);
				final SanPhamOrder orderComp = new SanPhamOrder();
				orderComp.setProduct(listOrder.get(i));
				orderComp.setActionEdit(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!orderComp.isEdit()) {
							if (orderComp.updateProduct()) {
								updateOrderToDatabse(orderComp.getProduct());
								updateOrderToList(orderComp.getProduct());
								fillToPnlOrder();
								tinhTongTien(tienKhachDua, maVoucher);
							} else {
								ROptionDialog.showAlert(frame, "Thông báo", "Cập nhật không thành công!",
										ROptionDialog.NOTIFICATIONS, Color.red, Color.black);
							}
						}
					}
				});
				orderComp.setActionDelete(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (ROptionDialog.showConfirm(frame, "Xác nhận",
								"Xác nhận xóa sản phẩm khói danh sách gọi món?", ROptionDialog.WARNING, Color.yellow,
								Color.black)) {
							deleteOrderFromDatabase(entity);
							deleteOrderFromList(entity);
							fillToPnlOrder();
							tinhTongTien(tienKhachDua, maVoucher);
						}
					}
				});
				orderComp.init();
				listOrderComp.add(orderComp);
			}
		}
	}

	/**
	 * Fill component order to panel
	 */
	public void fillToPnlOrder() {
		loadToListOrderComp();
		pnlContainOrder.removeAll();
		pnlContainOrder.revalidate();
		pnlContainOrder.repaint();
		int x = 10;
		int y = 10;
//        int xShift = 280;
		int yShift = 90;
		int width = 710;
		int height = 80;

		int pnlHeight = yShift * listOrderComp.size();
		pnlContainOrder.setPreferredSize(new Dimension(730, pnlHeight));
		for (int i = 0; i < listOrderComp.size(); i++) {
			SanPhamOrder entity = listOrderComp.get(i);
			if (entity.getProduct().getSanPham().getPathAnh() != null) {
				entity.getProduct().getSanPham()
						.setIcon(XImage.readImageThucDon(entity.getProduct().getSanPham().getPathAnh()));
			} else {
				entity.getProduct().getSanPham().setIcon(null);
			}
			entity.setBounds(x, y, width, height);
			pnlContainOrder.add(entity);
			y += yShift;
		}
	}

	/**
	 * Load to white list san pham
	 */
	public void loadToWhiteListSanPham() {
		whiteListSanPham.clear();
		for (ModelSanPham sp : listSanPhams) {
			if (sp.isTrangThai()) {
				boolean exists = false;
				if (sp.isMatHang()) {
					for (ModelKho kho : listKho) {
						if (sp.getMaSP().equals(kho.getMaSP())) {
							if (kho.getSoLuong() > 0) {
								exists = true;
							}
						}
					}
					if (exists) {
						whiteListSanPham.add(sp);
					}
				} else {
					whiteListSanPham.add(sp);
				}
			}
		}
	}

	/**
	 * Set time
	 */
	private void setTime() {
		t = new Thread(new Runnable() {
			public void run() {
				int second = 0;
				try {
					while (true) {
						second++;
						t.sleep(600);
						lblThoiGian.setText(XDate.toString(new Date(), "HH:mm dd-MM-yyyy"));
					}
				} catch (Exception e) {
				}
			}
		});
		t.start();
	}

	/**
	 * Get tenban by maban
	 * 
	 * @param maBan
	 * @return
	 */
	private String getTenBan(String maBan) {
		String tenBan = "";
		for (int i = 0; i < listBan.size(); i++) {
			if (maBan.equals(listBan.get(i).getMaBan())) {
				tenBan = listBan.get(i).getTenBan();
			}
		}
		return tenBan;
	}

	/**
	 * Set form
	 */
	public void setForm() {
		if (banOrderGlobal.getMaBanGop().equals("")) {
			lblBan.setText(getTenBan(banOrderGlobal.getMaBan()));
		} else {
			lblBan.setText(getTenBan(banOrderGlobal.getMaBan()) + " - " + getTenBan(banOrderGlobal.getMaBanGop()));
		}
		lblThuNgan.setText(Auth.user.getHoTen());
		txtGhiChu.setText(banOrderGlobal.getGhiChu());
		lblMaVoucher.setText(banOrderGlobal.getMaVoucher());
		if (!banOrderGlobal.getMaVoucher().equals("NOVOUCHER")) {
			checkMaVoucher(lblMaVoucher.getText().trim());
			// btnRefreshVoucher.setIcon(new
			// ImageIcon(getClass().getResource("/com/wolvesres/icon/refreshVoucher.png")));
			confirm = false;
			isVoucherValid = true;
		}
		String maVoucher = lblMaVoucher.getText().trim();
		tinhTongTien(getTienKhachDua(), maVoucher);
		setTime();
	}

	/**
	 * Update BanOrder to database
	 */
	private void updateBanOrderToDatabase(ModelBanOrder entity) {
		entity.update();
	}

	/**
	 * Update BanOrder to list
	 * 
	 * @param entity
	 */
	public void updateBanOrderToList(ModelBanOrder entity) {
		for (int i = 0; i < listBanOrder.size(); i++) {
			if (entity.getMaBan().equals(listBanOrder.get(i).getMaBan())) {
				listBanOrder.set(i, entity);
				break;
			}
		}
	}

	/**
	 * Delete banOrder
	 * 
	 * @param entity
	 */
	@SuppressWarnings("unused")
	private void deleteBanOrder(ModelBanOrder entity) {
		banOrderDAO.delete(entity.getMaBan());
		listBanOrder.remove(entity);
	}

	/**
	 * Valid ma Voucher
	 */
	private void checkMaVoucher(String keyword) {
		for (int i = 0; i < listVouCher.size(); i++) {
			if (FormValidator.isTextEqual(keyword, listVouCher.get(i).getMaVoucher())
					&& !FormValidator.isTextEqual(keyword, "NOVOUCHER")) {
				if (listVouCher.get(i).isTrangThai()) {
					if ((listVouCher.get(i).getSoLuong() > 0 && checkForApply)
							|| (listVouCher.get(i).getSoLuong() >= 0 && !checkForApply)) {
						Date today = XDate.toDate(XDate.toString(new Date(), "dd-MM-yyyy"), "dd-MM-yyyy");
						Date ngayBatDau = XDate.toDate(listVouCher.get(i).getNgayBatDau(), "dd-MM-yyyy");
						Date ngayKetThuc = XDate.toDate(listVouCher.get(i).getNgayKetThuc(), "dd-MM-yyyy");
						if (FormValidator.isDateBefore(ngayBatDau, today)) {
							if (FormValidator.isDateAfter(ngayBatDau, today)) {
								setTextCheckVoucher("Mã Voucher hợp lệ!", listVouCher.get(i).getGiamGia(), true);
								break;
							} else {
								setTextCheckVoucher("Mã Voucher đã hết hạn!", 0, false);
							}
						} else {
							setTextCheckVoucher("Mã Voucher chưa thể sử dụng!", 0, false);
						}
					} else {
						setTextCheckVoucher("Mã Voucher đã hết lượt dùng!", 0, false);
					}
				} else {
					setTextCheckVoucher("Mã Voucher đã bị vô hiệu hóa!", 0, false);
				}
			} else {
				setTextCheckVoucher("Mã Voucher không tồn tại!", 0, false);
			}
		}
	}

	/**
	 * Set text check voucher
	 * 
	 * @param textLblHopLe
	 * @param ptGiamGia
	 * @param isVoucherValid
	 */
	private void setTextCheckVoucher(String textLblHopLe, float ptGiamGia, boolean isVoucherValid) {
		lblHopLe.setText(textLblHopLe);
		lblPhanTramGiamGia.setText(String.valueOf(ptGiamGia) + "%");
		this.isVoucherValid = isVoucherValid;
	}

	/**
	 * apply voucher
	 * 
	 * @param entity
	 */
	public void appLyVoucher(ModelVouCher entity, float tienKhachDua, String maVoucher) {
		banOrderGlobal.setMaVoucher(entity.getMaVoucher());
		updateBanOrderToDatabase(banOrderGlobal);
		updateBanOrderToList(banOrderGlobal);
		int soLuong = entity.getSoLuong();
		if (soLuong > 0) {
			entity.setSoLuong(soLuong - 1);
		} else {
			entity.setTrangThai(false);
		}
		checkMaVoucher(entity.getMaVoucher());
		updateVoucher(entity);
		updateVoucherToList(entity);
		tinhTongTien(tienKhachDua, maVoucher);
	}

	/**
	 * handle apply voucher
	 */
	private void handleApplyVoucher(Boolean isValid, String maVoucher) {
		if (isValid) {
			// Get voucher by ma voucher
			ModelVouCher voucher = getVoucherByMaVouCher(maVoucher);
			// Check current voucher
			ModelVouCher voucherCurrent = getVoucherByMaVouCher(banOrderGlobal.getMaVoucher());
			if (voucherCurrent.getMaVoucher() != null && !voucherCurrent.getMaVoucher().equals("NOVOUCHER")) {
				// If current voucher is not null and current voucher is not NOVOUCHER
				// Revert amount of current voucher
				int soLuongVC = voucherCurrent.getSoLuong();
				voucherCurrent.setSoLuong(soLuongVC + 1);
				updateVoucher(voucherCurrent);
			}
			// apply vouche
			appLyVoucher(voucher, getTienKhachDua(), maVoucher);
		}
	}

	/**
	 * Get Voucher by maVoucher
	 * 
	 * @param maVoucher
	 * @return ModelVoucher
	 */
	public ModelVouCher getVoucherByMaVouCher(String maVoucher) {
		ModelVouCher voucher = new ModelVouCher();
		for (int i = 0; i < listVouCher.size(); i++) {
			if (maVoucher.equals(listVouCher.get(i).getMaVoucher())) {
				voucher = listVouCher.get(i);
				break;
			}
		}
		return voucher;
	}

	/**
	 * Update Voucher to database
	 * 
	 * @param entity
	 */
	private void updateVoucher(ModelVouCher entity) {
		entity.update(entity.getMaVoucher());

	}

	/**
	 * Update voucher to list
	 * 
	 * @param entity
	 */
	public void updateVoucherToList(ModelVouCher entity) {
		for (int i = 0; i < listVouCher.size(); i++) {
			if (entity.getMaVoucher().equals(listVouCher.get(i).getMaVoucher())) {
				listVouCher.set(i, entity);
				break;
			}
		}
	}

	/**
	 * Tinh giam gia
	 * 
	 * @return giam gia
	 */
	private float tinhGiamGia(String maVoucher) {
		float giamGia = 0;
		if (isVoucherValid) {
			ModelVouCher voucher = getVoucherByMaVouCher(maVoucher);
			giamGia = (tinhTienHang() / 100) * voucher.getGiamGia();
		}
		// this.giamGia = giamGia;
		lblGiamGia.setText(XFormatMoney.formatMoney(giamGia));
		this.giamGia = giamGia;
		return giamGia;
	}

	/**
	 * Tinh tien hang
	 * 
	 * @return tien hang
	 */
	private float tinhTienHang() {
		float tienHang = 0;
		if (listOrder.size() > 0) {
			for (int i = 0; i < listOrder.size(); i++) {
				tienHang += (listOrder.get(i).getGia() * listOrder.get(i).getSoLuong());
			}
		}
		lblTienHang.setText(XFormatMoney.formatMoney(tienHang));
		this.tienHang = tienHang;
		return tienHang;
	}

	/**
	 * Tinh tong tien
	 * 
	 * @return tong tien
	 */
	private float tinhTongTien(float tienKhachDua, String maVoucher) {
		float tongTien = 0;
		tongTien = tinhTienHang() - tinhGiamGia(maVoucher);
		float tienTraLai = 0;
		try {
			if (txtTienKhachDua.getText().trim().length() > 0) {
				tienTraLai = tienKhachDua - tongTien;
				if (tienTraLai > 0) {
					lblTraLai.setText(XFormatMoney.formatMoney(tienTraLai));
				} else {
					lblTraLai.setText("");
				}
			} else {
				lblTraLai.setText("");
			}
		} catch (Exception e) {
			ROptionDialog.showAlert(frame, "Lỗi định dạng", "Vui lòng nhập giá trị số!", ROptionDialog.REPORT,
					Color.RED, Color.black);
		}
		lblTongTien.setText(XFormatMoney.formatMoney(tongTien));
		this.tongTien = tongTien;
		this.tienTraLai = tienTraLai;
		return tongTien;
	}

	/**
	 * Tinh tien thue
	 * 
	 * @return tien thue
	 */
	private float tinhTienThue() {
		float tienThue = 0;
		if (listOrder.size() > 0) {
			for (int i = 0; i < listOrder.size(); i++) {
				tienThue += ((listOrder.get(i).getGia() / 100) * 10) * listOrder.get(i).getSoLuong();
			}
		}
		this.thue = tienThue;
		return tienThue;
	}

	/**
	 * Insert order to database
	 * 
	 * @param entity
	 */
	private void insertOrderToDatabase(ModelOrder entity) {
		entity.insert();
	}

	/**
	 * Add order to list
	 * 
	 * @param entity
	 */
	public void addOrderToList(ModelOrder entity) {
		listOrder.add(entity);
	}

	/**
	 * Update order
	 * 
	 * @param entity
	 */

	public void updateOrderToDatabse(ModelOrder entity) {
		entity.update();
		fillToPnlOrder();
	}

	/**
	 * Udpate Order to list
	 * 
	 * @param entity
	 */
	public void updateOrderToList(ModelOrder entity) {
		for (int i = 0; i < listOrder.size(); i++) {
			if ((entity.getMaBan().equals(listOrder.get(i).getMaBan()))
					&& (entity.getMaSP().equals(listOrder.get(i).getMaSP()))) {
				listOrder.set(i, entity);
				break;
			}
		}
	}

	/**
	 * Delete order from database
	 * 
	 * @param entity
	 */
	public void deleteOrderFromDatabase(ModelOrder entity) {
		entity.removeProduct();
	}

	/**
	 * Delete order from list
	 * 
	 * @param entity
	 */

	public void deleteOrderFromList(ModelOrder entity) {
		listOrder.remove(entity);
	}

	/**
	 * Get SanPham by tenSanPham
	 * 
	 * @param tenSP
	 * @return ModelSanPham
	 */
	private ModelSanPham getSanPhamByName(String tenSP) {
		ModelSanPham sanPham = new ModelSanPham();
		for (int i = 0; i < listSanPhams.size(); i++) {
			if (tenSP.equals(listSanPhams.get(i).getTenSP())) {
				sanPham = listSanPhams.get(i);
				break;
			}
		}
		return sanPham;
	}

	/**
	 * Get Order by maSanPhamAndMaBan
	 * 
	 * @param maSP
	 * @param maBan
	 * @return ModelOrder
	 */
	private ModelOrder getOrderByMaSanPhamAndMaBan(String maSP, String maBan) {
		ModelOrder order = new ModelOrder();
		for (int i = 0; i < listOrder.size(); i++) {
			if ((listOrder.get(i).getMaBan().equals(maBan)) && (listOrder.get(i).getMaSP().equals(maSP))) {
				order = listOrder.get(i);
				break;
			}
		}
		return order;
	}

	/**
	 * Order SanPham
	 * 
	 * @param sanPham
	 */
	public void orderSanPham(ModelSanPham sanPham) {
		ModelOrder order = null;
		for (int i = 0; i < listOrder.size(); i++) {
			if ((banOrderGlobal.getMaBan().equals(listOrder.get(i).getMaBan()))
					&& (sanPham.getMaSP().equals(listOrder.get(i).getMaSP()))) {
				order = listOrder.get(i);
				break;
			}
		}
		if (order == null) {
			order = new ModelOrder(sanPham.getMaSP(), banOrderGlobal.getMaBan(), sanPham.getGiaBan(), 1);
			insertOrderToDatabase(order);
			addOrderToList(order);
			fillToPnlOrder();
		} else {
			int soLuong = order.getSoLuong();
			order.setSoLuong(soLuong + 1);
			updateOrderToDatabse(order);
			updateOrderToList(order);
			fillToPnlOrder();
		}
		ModelKho kho = getKhoFromRowTable(sanPham);
		if (kho != null) {
			if (kho.getSoLuong() > 0) {
				int soLuong = kho.getSoLuong();
				kho.setSoLuong(soLuong - 1);
				if (kho.getSoLuong() == 0) {
					kho.setTrangThai(false);
				}
			}
			updateKhoToDatabase(kho);
			fillToPnlSanPham(listSanPhamComp);
		}
		fillToPnlOrder();
	}

	/**
	 * Get SanPham by maSanPham
	 * 
	 * @param maSP
	 * @return ModelSanPham
	 */
	@SuppressWarnings("unused")
	private ModelSanPham getSanPhamByMaSP(String maSP) {
		ModelSanPham sanPham = new ModelSanPham();
		for (int i = 0; i < listSanPhams.size(); i++) {
			if (maSP.equals(listSanPhams.get(i).getMaSP())) {
				sanPham = listSanPhams.get(i);
			}
		}
		return sanPham;
	}

	/**
	 * Get kho from row table
	 * 
	 * @param sanPham
	 * @return ModelKho
	 */
	private ModelKho getKhoFromRowTable(ModelSanPham sanPham) {
		ModelKho kho = null;
		List<ModelKho> listSPInKho = new ArrayList<ModelKho>();
		if (sanPham.isMatHang()) {
			for (int i = 0; i < listKho.size(); i++) {
				if (sanPham.getMaSP().equals(listKho.get(i).getMaSP()) && sanPham.isMatHang()) {
					listSPInKho.add(listKho.get(i));
					break;
				}
			}
			if (listSPInKho.size() > 1) {
				for (int i = 0; i < listSPInKho.size(); i++) {
					for (int j = 0; j < listSPInKho.size(); j++) {
						if (XDate.toDate(listSPInKho.get(i).getHanSuDung(), "dd-MM-yyyy")
								.before(XDate.toDate(listSPInKho.get(j).getHanSuDung(), "dd-MM-yyyy"))) {
							kho = new ModelKho();
							kho = listSPInKho.get(i);
							break;
						}
					}
				}
			} else {
				kho = listSPInKho.get(0);
			}
		}
		return kho;
	}

	/**
	 * Update Kho
	 * 
	 * @param entity
	 */
	public void updateKhoToDatabase(ModelKho entity) {
		entity.update();
	}

	/**
	 * Update to list
	 * 
	 * @param entity
	 */
	public void updateKhoToList(ModelKho entity) {
		for (int i = 0; i < listKho.size(); i++) {
			if (entity.getMaSP().equals(listKho.get(i).getMaSP())
					&& entity.getHanSuDung().equals(listKho.get(i).getHanSuDung())) {
				listKho.set(i, entity);
				break;
			}
		}
	}

	/**
	 * Huy hoa don
	 */
	public void huyHoaDon() {
		listOrder.clear();
		orderDAO.delete(banOrderGlobal.getMaBan());
		banOrderDAO.delete(banOrderGlobal.getMaBan());
		Main.listBanOrder = Main.banOrderDAO.selectAll();
		if (Main.listBanOrder.size() > 0) {
			Main.banOrderGlobal = Main.listBanOrder.get(0);
			reInit();
		} else {
			Main.banOrderGlobal = new ModelBanOrder();
			this.removeAll();
			revalidate();
			repaint();
			this.setLayout(new BorderLayout());
			JLabel lbl = new JLabel("Vui lòng chọn bàn để tiếp tục gọi món!");
			lbl.setHorizontalAlignment(JLabel.CENTER);
			lbl.setFont(new Font("SansSerif", 1, 50));
			lbl.setForeground(new Color(102, 102, 102));
			this.add(lbl, BorderLayout.CENTER);
		}
	}

	/**
	 * Generate HoaDon
	 * 
	 * @param banOrder
	 * @return ModelHoaDon
	 */
	private ModelHoaDon generateHoaDon(ModelBanOrder banOrder) {
		ModelHoaDon hoaDon = new ModelHoaDon();
		hoaDon.setMaHD(autoDAO.AuToHoaDon());
		hoaDon.setMaBan(banOrder.getMaBan());
		hoaDon.setMaVoucher(banOrder.getMaVoucher());
		hoaDon.setNguoiXuat(Auth.user.getMaNV());
		hoaDon.setThue(tinhTienThue());
		hoaDon.setTienHang(tinhTienHang());
		hoaDon.setTrangThai(true);
		return hoaDon;
	}

	/**
	 * Generate HoaDonChiTiet
	 * 
	 * @param hoaDon
	 * @param order
	 * @return ModelHoaDonChiTiet
	 */
	private ModelHoaDonChiTiet generateHoaDonChiTiet(ModelHoaDon hoaDon, ModelOrder order) {
		ModelHoaDonChiTiet hdct = new ModelHoaDonChiTiet();
		hdct.setMaHD(hoaDon.getMaHD());
		hdct.setMaSP(order.getMaSP());
		hdct.setDonGia(order.getGia());
		hdct.setSoLuong(order.getSoLuong());
		return hdct;
	}

	/**
	 * Thanh toan
	 */
	private void thanhToan() {
		ModelHoaDon hoaDon = generateHoaDon(banOrderGlobal);
		hoaDonDAO.insert(hoaDon);
		List<ModelHoaDonChiTiet> listHoaDonChiTiets = new ArrayList<ModelHoaDonChiTiet>();
		for (int i = 0; i < listOrder.size(); i++) {
			ModelHoaDonChiTiet hdct = generateHoaDonChiTiet(hoaDon, listOrder.get(i));
			listHoaDonChiTiets.add(hdct);
			hdctDAO.insert(hdct);
		}
		if (acceptPtint) {
			Date toDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("HH:mm dd-MM-yyyy");
			String time = format.format(toDate);
			float tienKhachDua = Float.parseFloat(txtTienKhachDua.getText().trim());
			BillPrint print = new BillPrint(hoaDon, time, this.giamGia, tienKhachDua, this.tienTraLai,
					listHoaDonChiTiets, listSanPhams, listVouCher, getRootPane());
			print.print();
		}
	}

	/**
	 * In hoa don
	 */
	private void inHoaDon() {
		ModelHoaDon hoaDon = generateHoaDon(banOrderGlobal);
		List<ModelHoaDonChiTiet> listHoaDonChiTiets = new ArrayList<ModelHoaDonChiTiet>();
		for (int i = 0; i < listOrder.size(); i++) {
			ModelHoaDonChiTiet hdct = generateHoaDonChiTiet(hoaDon, listOrder.get(i));
			listHoaDonChiTiets.add(hdct);
		}
		Date toDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm dd-MM-yyyy");
		String time = format.format(toDate);
		float tienKhachDua = 0;
		if (txtTienKhachDua.getText().trim().length() > 0) {
			tienKhachDua = Float.parseFloat(txtTienKhachDua.getText().trim());
		}
		BillPrint print = new BillPrint(hoaDon, time, this.giamGia, tienKhachDua, this.tienTraLai, listHoaDonChiTiets,
				listSanPhams, listVouCher, getRootPane());
		print.print();
	}

	/**
	 * In bep
	 */
	private void inBep() {
		Date toDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm dd-MM-yyyy");
		String time = format.format(toDate);
		KitchenPrint print = new KitchenPrint(time, listOrder, listSanPhams, getRootPane());
		print.print();
	}

	/**
	 * Check tien khach dua
	 * 
	 * @return is valid
	 */
	private boolean checkTienKhachDua() {
		if (txtTienKhachDua.getText().trim().length() == 0) {
			ROptionDialog.showAlert(frame, "Thông báo", "Vui lòng nhập tiên khách đưa!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		} else if (tienTraLai < 0) {
			ROptionDialog.showAlert(frame, "Thông báo", "Không cho nợ nhé!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		return true;
	}

	/**
	 * Tim kiem san pham
	 * 
	 * @param keyword
	 */
	public void timKiemSanPham(String keyword) {
		keyword = keyword.trim();
		keyword = keyword.toUpperCase();
		List<SanPham> list = new ArrayList();
		list.clear();
		if (keyword.length() != 0) {
			for (int i = 0; i < listSanPhamComp.size(); i++) {
				if (listSanPhamComp.get(i).getProduct().getTenSP().toUpperCase().contains(keyword)
						|| listSanPhamComp.get(i).getProduct().getTenSP().toUpperCase().contains(keyword)) {
					list.add(listSanPhamComp.get(i));
					fillToPnlSanPham(list);
				}
			}
		} else {
			fillToPnlSanPham(listSanPhamComp);
		}

	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPopupMenu1 = new javax.swing.JPopupMenu();
		lblHopLe = new javax.swing.JLabel();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnHuyHoaDon = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		pnlContainSanPham = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel18 = new javax.swing.JLabel();
		txtFind = new com.swing.custom.raven.RTextField.RTextField();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		lblThuNgan = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		txtGhiChu = new javax.swing.JTextField();
		jLabel17 = new javax.swing.JLabel();
		jLabel19 = new javax.swing.JLabel();
		lblThoiGian = new javax.swing.JLabel();
		lblBan = new javax.swing.JLabel();
		rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel1 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		btnInHoaDon = new com.swing.custom.raven.RButton.RButton();
		btnInBep = new com.swing.custom.raven.RButton.RButton();
		btnThanhToan = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel5 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel2 = new javax.swing.JLabel();
		lblTongTien = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		txtTienKhachDua = new javax.swing.JTextField();
		jLabel9 = new javax.swing.JLabel();
		lblTraLai = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		lblGiamGia = new javax.swing.JLabel();
		btnRefreshVoucher = new com.swing.custom.raven.RButton.RButton();
		lblPhanTramGiamGia = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		lblTienHang = new javax.swing.JLabel();
		lblMaVoucher = new javax.swing.JLabel();
		rRoundPanel7 = new com.swing.custom.raven.RPanel.RRoundPanel();
		lblTenSP = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		pnlContainOrder = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel16 = new javax.swing.JLabel();

		lblHopLe.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblHopLe.setForeground(new java.awt.Color(0, 204, 0));

		setMaximumSize(new java.awt.Dimension(1170, 730));
		setMinimumSize(new java.awt.Dimension(1170, 730));
		setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		rRoundPanel1.setBackground(new java.awt.Color(6, 7, 13));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		btnHuyHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huyhoadon.png"))); // NOI18N
		btnHuyHoaDon.setText("Hủy hóa đơn");
		btnHuyHoaDon.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnHuyHoaDon.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyHoaDonActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnHuyHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 690, 140, 40));

		rRoundPanel2.setBackground(new java.awt.Color(32, 29, 37));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jScrollPane3.setBackground(new java.awt.Color(51, 51, 51));
		jScrollPane3.setBorder(null);
		jScrollPane3.setHorizontalScrollBar(null);

		pnlContainSanPham.setBackground(new java.awt.Color(41, 47, 52));
		jScrollPane3.setViewportView(pnlContainSanPham);

		rRoundPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 430, 470));

		jLabel18.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel18.setForeground(new java.awt.Color(255, 255, 255));
		jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/menu.png"))); // NOI18N
		jLabel18.setText("Danh Sách Thực Đơn");
		rRoundPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 40));

		txtFind.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtFind.setLabelText("Nhập tên món ăn hoặc mặt hàng");
		txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtFindKeyReleased(evt);
			}
		});
		rRoundPanel2.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 430, -1));

		rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 160, 430, 570));

		rRoundPanel3.setBackground(new java.awt.Color(0, 199, 135));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		lblThuNgan.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		lblThuNgan.setForeground(new java.awt.Color(255, 255, 255));
		lblThuNgan.setToolTipText("");
		rRoundPanel3.add(lblThuNgan, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, 210, 30));

		jLabel11.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
		jLabel11.setForeground(new java.awt.Color(255, 255, 255));
		jLabel11.setText("BÁN HÀNG");
		jLabel11.setToolTipText("");
		rRoundPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 210, -1));

		jLabel12.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel12.setForeground(new java.awt.Color(255, 255, 255));
		jLabel12.setText("Ghi chú:");
		jLabel12.setToolTipText("");
		rRoundPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

		jLabel13.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel13.setForeground(new java.awt.Color(255, 255, 255));
		jLabel13.setText("Bàn: ");
		jLabel13.setToolTipText("");
		rRoundPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, -1, -1));

		txtGhiChu.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txtGhiChuKeyPressed(evt);
			}
		});
		rRoundPanel3.add(txtGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 270, 30));

		jLabel17.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel17.setForeground(new java.awt.Color(255, 255, 255));
		jLabel17.setText("Thời gian :");
		jLabel17.setToolTipText("");
		rRoundPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

		jLabel19.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel19.setForeground(new java.awt.Color(255, 255, 255));
		jLabel19.setText("Thu ngân: ");
		jLabel19.setToolTipText("");
		rRoundPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, -1));

		lblThoiGian.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblThoiGian.setForeground(new java.awt.Color(255, 255, 255));
		rRoundPanel3.add(lblThoiGian, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 270, 20));

		lblBan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblBan.setForeground(new java.awt.Color(255, 255, 255));
		rRoundPanel3.add(lblBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 260, 20));

		rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 150));

		rRoundPanel4.setBackground(new java.awt.Color(0, 199, 135));
		rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("WolvesRes");
		rRoundPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, -1, -1));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel4.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 150, 150));

		rRoundPanel1.add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 0, 430, 150));

		btnInHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/inhoadon.png"))); // NOI18N
		btnInHoaDon.setText("In hóa đơn");
		btnInHoaDon.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnInHoaDonActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnInHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 690, 160, 40));

		btnInBep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/inbep.png"))); // NOI18N
		btnInBep.setText("In bếp");
		btnInBep.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnInBep.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnInBepActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnInBep, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 690, 160, 40));

		btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/thanhtoan.png"))); // NOI18N
		btnThanhToan.setText("Thanh toán");
		btnThanhToan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThanhToanActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 690, 140, 40));

		rRoundPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
		jLabel2.setText("Khách Đưa:");
		rRoundPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 30));

		lblTongTien.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		lblTongTien.setForeground(new java.awt.Color(255, 0, 51));
		lblTongTien.setText("1000000");
		rRoundPanel5.add(lblTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, 330, 40));

		jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
		jLabel4.setText("Thuế VAT:");
		rRoundPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

		jLabel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
		rRoundPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 10));

		jLabel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
		rRoundPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 300, 10));

		jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
		jLabel7.setText("Mã Voucher:");
		rRoundPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, -1));

		jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
		jLabel8.setText("Giảm giá:");
		rRoundPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

		txtTienKhachDua.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		txtTienKhachDua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txtTienKhachDuaKeyPressed(evt);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtTienKhachDuaKeyReleased(evt);
			}
		});
		rRoundPanel5.add(txtTienKhachDua, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 180, 30));

		jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
		jLabel9.setText("Trả lại:");
		rRoundPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 30));

		lblTraLai.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
		rRoundPanel5.add(lblTraLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 180, 30));

		jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel3.setText("10%");
		rRoundPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 70, -1));

		jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
		jLabel10.setText("Tổng tiền thanh toán:");
		rRoundPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, -1, -1));

		lblGiamGia.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
		lblGiamGia.setForeground(new java.awt.Color(0, 204, 0));
		lblGiamGia.setText("0");
		rRoundPanel5.add(lblGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 320, 20));

		btnRefreshVoucher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/scanner.png"))); // NOI18N
		btnRefreshVoucher.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnRefreshVoucherActionPerformed(evt);
			}
		});
		rRoundPanel5.add(btnRefreshVoucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 40, 40));

		lblPhanTramGiamGia.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		lblPhanTramGiamGia.setForeground(new java.awt.Color(0, 204, 0));
		lblPhanTramGiamGia.setText("0%");
		rRoundPanel5.add(lblPhanTramGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 70, 40));

		jLabel14.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
		jLabel14.setText("Tiền hàng");
		rRoundPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, -1));

		lblTienHang.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		rRoundPanel5.add(lblTienHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 96, 320, 30));

		lblMaVoucher.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
		lblMaVoucher.setText("d");
		rRoundPanel5.add(lblMaVoucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 180, 40));

		rRoundPanel1.add(rRoundPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 730, 210));

		rRoundPanel7.setBackground(new java.awt.Color(32, 29, 37));
		rRoundPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		lblTenSP.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		lblTenSP.setForeground(new java.awt.Color(255, 255, 255));
		rRoundPanel7.add(lblTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 290, 50));

		jScrollPane2.setBorder(null);
		jScrollPane2.setHorizontalScrollBar(null);

		pnlContainOrder.setBackground(new java.awt.Color(41, 47, 52));
		jScrollPane2.setViewportView(pnlContainOrder);

		rRoundPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 730, 260));

		jLabel16.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel16.setForeground(new java.awt.Color(255, 255, 255));
		jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/menu.png"))); // NOI18N
		jLabel16.setText("Danh Sách Oder");
		rRoundPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 40));

		rRoundPanel1.add(rRoundPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 730, 310));

		add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 730));
	}// </editor-fold>//GEN-END:initComponents

	private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyHoaDonActionPerformed
		if (ROptionDialog.showConfirm(frame, "Xác nhận", "Xác nhận hủy hóa đon bán hàng?", ROptionDialog.WARNING,
				Color.YELLOW, Color.black)) {
			huyHoaDon();
		}
	}// GEN-LAST:event_btnHuyHoaDonActionPerformed

	private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInHoaDonActionPerformed
		inHoaDon();
	}// GEN-LAST:event_btnInHoaDonActionPerformed

	private void btnInBepActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInBepActionPerformed
		inBep();
	}// GEN-LAST:event_btnInBepActionPerformed

	private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThanhToanActionPerformed
		if (listOrder.size() > 0) {
			if (checkTienKhachDua()) {
				if (ROptionDialog.showConfirm(frame, "Tùy chọn", "Bạn có muốn in hóa đơn không?",
						ROptionDialog.NOTIFICATIONS_ACTIVE, Color.yellow, Color.black)) {
					acceptPtint = true;
				}
				thanhToan();
				huyHoaDon();
			}
		} else {
			ROptionDialog.showAlert(frame, "Thông báo", "Vui lòng gọi món trước khi thanh toán", ROptionDialog.WARNING,
					Color.red, Color.black);
		}
	}// GEN-LAST:event_btnThanhToanActionPerformed

	private void txtTienKhachDuaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTienKhachDuaKeyPressed
		if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
			String maVoucher = lblMaVoucher.getText().trim();
			tinhTongTien(getTienKhachDua(), maVoucher);
			if (this.tienTraLai < 0) {
				ROptionDialog.showAlert(frame, "Thông báo", "Tiền khách đưa phải lớn hơn hoặc bằng tổng tiên!",
						ROptionDialog.WARNING, Color.red, Color.black);
			}

		}
	}// GEN-LAST:event_txtTienKhachDuaKeyPressed

	private void btnRefreshVoucherActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRefreshVoucherActionPerformed
		ScanVoucher scan = new ScanVoucher(frame, true);
		scan.setVisible(true);
		if (!scan.isDispose()) {
			lblMaVoucher.setText(scan.getCode());
			isVoucherValid = true;
			checkForApply = true;
			handleApplyVoucher(isVoucherValid, scan.getCode());
		}
	}// GEN-LAST:event_btnRefreshVoucherActionPerformed

	private void txtGhiChuKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtGhiChuKeyPressed
		if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
			banOrderGlobal.setGhiChu(txtGhiChu.getText().trim());
			updateBanOrderToDatabase(banOrderGlobal);
			updateBanOrderToList(banOrderGlobal);
		}
	}// GEN-LAST:event_txtGhiChuKeyPressed

	private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTienKhachDuaKeyReleased
		String maVoucher = lblMaVoucher.getText().trim();
		tinhTongTien(getTienKhachDua(), maVoucher);
	}// GEN-LAST:event_txtTienKhachDuaKeyReleased

	private void txtFindKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtFindKeyReleased
		timKiemSanPham(txtFind.getText().trim());
	}// GEN-LAST:event_txtFindKeyReleased

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.swing.custom.raven.RButton.RButton btnHuyHoaDon;
	private com.swing.custom.raven.RButton.RButton btnInBep;
	private com.swing.custom.raven.RButton.RButton btnInHoaDon;
	private com.swing.custom.raven.RButton.RButton btnRefreshVoucher;
	private com.swing.custom.raven.RButton.RButton btnThanhToan;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPopupMenu jPopupMenu1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JLabel lblBan;
	private javax.swing.JLabel lblGiamGia;
	private javax.swing.JLabel lblHopLe;
	private javax.swing.JLabel lblMaVoucher;
	private javax.swing.JLabel lblPhanTramGiamGia;
	private javax.swing.JLabel lblTenSP;
	private javax.swing.JLabel lblThoiGian;
	private javax.swing.JLabel lblThuNgan;
	private javax.swing.JLabel lblTienHang;
	private javax.swing.JLabel lblTongTien;
	private javax.swing.JLabel lblTraLai;
	private com.swing.custom.raven.RPanel.RRoundPanel pnlContainOrder;
	private com.swing.custom.raven.RPanel.RRoundPanel pnlContainSanPham;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel5;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel7;
	private com.swing.custom.raven.RTextField.RTextField txtFind;
	private javax.swing.JTextField txtGhiChu;
	private javax.swing.JTextField txtTienKhachDua;
	// End of variables declaration//GEN-END:variables
}
