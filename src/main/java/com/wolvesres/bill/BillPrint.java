package com.wolvesres.bill;

import com.wolvesres.helper.PrintHelper;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.model.ModelHoaDon;
import com.wolvesres.model.ModelHoaDonChiTiet;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.model.ModelVouCher;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JRootPane;

public class BillPrint implements Printable {

	public BillPrint(ModelHoaDon hoaDon, String time, float giamGia, float tienKhachDua, float tienTraLai,
			List<ModelHoaDonChiTiet> listHDCT, List<ModelSanPham> listSanPham, List<ModelVouCher> listVouCher,
			JRootPane rootPane) {
		this.hoaDon = hoaDon;
		this.listHDCT = listHDCT;
		this.listSanPham = listSanPham;
		this.listVouCher = listVouCher;
		this.tienKhachDua = tienKhachDua;
		this.rootPane = rootPane;
		this.time = time;
		this.giamGia = giamGia;
		this.tienTraLai = tienTraLai;
	}

	JRootPane rootPane;
	float tongTienHang = 0;
	float bHeight = 0;
	float tienKhachDua = 0;
	float giamGia = 0;
	float tienTraLai = 0;
	double bodyHeight = bHeight;
	double headerHeight = 8.0;
	double footerHeight = 20.0;
	double width = PrintHelper.cm_to_pp(15);
	double height = PrintHelper.cm_to_pp(headerHeight + bodyHeight + footerHeight);
	ModelHoaDon hoaDon = new ModelHoaDon();
	String time;
	List<ModelHoaDonChiTiet> listHDCT = new ArrayList<ModelHoaDonChiTiet>();
	List<ModelSanPham> listSanPham = new ArrayList<ModelSanPham>();
	List<ModelVouCher> listVouCher = new ArrayList<ModelVouCher>();

	private ModelSanPham getSanPhamByMaSanPham(String maSP) {
		ModelSanPham sanPham = new ModelSanPham();
		for (int i = 0; i < listSanPham.size(); i++) {
			if (maSP.equals(listSanPham.get(i).getMaSP())) {
				sanPham = listSanPham.get(i);
				break;
			}
		}
		return sanPham;
	}

	private float tinhTienHang(float donGia, int soLuong) {
		return donGia * soLuong;
	}

	private float getPhanTramGiamGia(ModelHoaDon hoaDon, List<ModelVouCher> list) {
		float phanTram = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMaVoucher().equals(hoaDon.getMaVoucher())) {
				phanTram = list.get(i).getGiamGia();
				break;
			}
		}
		return phanTram;
	}

	private String line(String chuoi1, String chuoi2, int lenght) {
		String line = "";
		String space = "";
		int lenghtChuoi1 = chuoi1.trim().length();
		int lenghtChuoi2 = chuoi2.trim().length();
		int spaceLenght = lenght - (lenghtChuoi1 + lenghtChuoi2);
		line += chuoi1;
		for (int i = 0; i < spaceLenght; i++) {
			space += " ";
		}
		line += space;
		line += chuoi2;
		return line;
	}

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		int result = NO_SUCH_PAGE;
		if (pageIndex == 0) {
			Graphics2D g2d = (Graphics2D) graphics;
			double width = pageFormat.getImageableWidth();
			g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
			try {
				int y = 20;
				int yShift = 15;
				int headerRectHeight = 18;

				g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));

				y += yShift + 10;
				g2d.drawString("---------------------------------------------------------------------", 70, y);
				y += yShift;
				g2d.setFont(new Font("ShowCard Gothic", Font.BOLD, 20));
				g2d.drawString("                         WolvesRes", 20, y);
				g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
				y += yShift;
				g2d.drawString("            No 00000 Address Line One", 90, y);
				y += yShift;
				g2d.drawString("                         Can Tho", 90, y);
				y += yShift;
				g2d.drawString("                 www.facebook.com/", 90, y);
				y += yShift;
				g2d.drawString("                  000000000000000", 90, y);
				y += yShift;
				g2d.drawString("------------------------------------------------------------------------------------",
						40, y);
				y += yShift;
				g2d.drawString("Bàn: " + hoaDon.getMaBan() + "                   Thời gian: " + time, 80, y);
				y += yShift;
				g2d.drawString("Thu ngân: " + hoaDon.getNguoiXuat(), 80, y);
				y += yShift;
				g2d.drawString("------------------------------------------------------------------------------------",
						40, y);
				y += headerRectHeight;

				g2d.drawString("      Tên hàng                                            Thành tiền     ", 40, y);
				y += yShift;
				g2d.drawString("------------------------------------------------------------------------------------",
						40, y);
				y += headerRectHeight;

				for (int i = 0; i < listHDCT.size(); i++) {
					String tenSP = getSanPhamByMaSanPham(listHDCT.get(i).getMaSP()).getTenSP();
					String soLuongVaDonGia = String.valueOf(listHDCT.get(i).getSoLuong() + " * "
							+ XFormatMoney.formatMoney(listHDCT.get(i).getDonGia()));
					g2d.drawString(line(tenSP, "", 40), 60, y);
					y += yShift;
					g2d.drawString(line(soLuongVaDonGia, "", 40), 75, y);
					float tongTienSP = tinhTienHang(listHDCT.get(i).getDonGia(), listHDCT.get(i).getSoLuong());
					g2d.drawString(line("", XFormatMoney.formatMoney(tongTienSP), 40), 160, y);
					y += yShift;
				}

				g2d.drawString("------------------------------------------------------------------------------------",
						40, y);
				y += yShift;
				g2d.drawString("       Tiền hàng:           " + XFormatMoney.formatMoney(hoaDon.getTienHang()) + "   ",
						40, y);
				y += yShift;
				g2d.drawString("       Giảm giá:            " + XFormatMoney.formatMoney(giamGia) + " ("
						+ String.valueOf(getPhanTramGiamGia(hoaDon, listVouCher)) + "%) " + "   ", 40, y);
				y += yShift;
				g2d.drawString("       Thuế:                  " + XFormatMoney.formatMoney(hoaDon.getThue()) + "   ",
						40, y);
				y += yShift;
				g2d.drawString("", 130, y);
				y += yShift;
				g2d.setFont(new Font("SansSerif", Font.BOLD, 19));
				g2d.drawString(
						"       Tổng tiền:     " + XFormatMoney.formatMoney(hoaDon.getTienHang() - giamGia) + "   ", 40,
						y);
				g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
				y += yShift;
				g2d.drawString("------------------------------------------------------------------------------------",
						40, y);
				y += yShift;
				g2d.drawString("       Khách đưa:            " + XFormatMoney.formatMoney(tienKhachDua) + "   ", 40, y);
				y += yShift;
				g2d.drawString("------------------------------------------------------------------------------------",
						40, y);
				y += yShift;
				g2d.drawString("       Trả lại:                   " + XFormatMoney.formatMoney(tienTraLai) + "   ", 40,
						y);
				y += yShift;

				g2d.drawString("**************************************************************************", 35, y);
				y += yShift;
				g2d.drawString("                 CẢM ƠN QUÝ KHÁCH", 90, y);
				y += yShift;
				g2d.drawString("**************************************************************************", 35, y);
				y += yShift;
				g2d.setFont(new Font("ShowCard Gothic", Font.BOLD, 15));
				g2d.drawString("             NHÀ HÀNG WOLVESRES", 70, y);
				g2d.setFont(new Font("ShowCard Gothic", Font.BOLD, 15));
				y += yShift;
				y += yShift;
				g2d.drawString("                             AMEN", 80, y);
				y += yShift;

			} catch (Exception e) {
				// e.printStackTrace();
			}
			result = PAGE_EXISTS;
		}
		return result;
	}

	public void print() {
		bHeight = Float.parseFloat(String.valueOf(listHDCT.size()));
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(this, PrintHelper.getPageFormat(pj, bHeight, width, height));
		try {
			pj.print();
		} catch (PrinterException ex) {
		}
	}

}
