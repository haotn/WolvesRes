package com.wolvesres.bill;

import com.wolvesres.helper.PrintHelper;
import com.wolvesres.model.ModelHoaDon;
import com.wolvesres.model.ModelOrder;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.model.ModelVouCher;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JRootPane;

public class KitchenPrint implements Printable {

    public KitchenPrint(String time, List<ModelOrder> listOrders, List<ModelSanPham> listSanPhams, JRootPane rootPane) {
        this.listOrders = listOrders;
        this.listSanPhams = listSanPhams;
        this.rootPane = rootPane;
        this.time = time;
        getListMonAn();
    }
    JRootPane rootPane;
    float bHeight = 0;
    double bodyHeight = bHeight;
    String time;
    double headerHeight = 5.0;
    double footerHeight = 10;
    double width = PrintHelper.cm_to_pp(35);
    double height = PrintHelper.cm_to_pp(headerHeight + bodyHeight + footerHeight);
    List<ModelOrder> listOrders = new ArrayList<ModelOrder>();
    List<ModelOrder> listMonAn = new ArrayList<ModelOrder>();
    List<ModelSanPham> listSanPhams = new ArrayList<ModelSanPham>();

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

    private ModelSanPham getSanPhamByMaSP(String maSP) {
        ModelSanPham sanPham = new ModelSanPham();
        for (int i = 0; i < listSanPhams.size(); i++) {
            if (maSP.equals(listSanPhams.get(i).getMaSP())) {
                sanPham = listSanPhams.get(i);
                break;
            }
        }
        return sanPham;
    }

    private void getListMonAn() {
        for (int i = 0; i < listOrders.size(); i++) {
            ModelSanPham sanPham = getSanPhamByMaSP(listOrders.get(i).getMaSP());
            if (sanPham != null) {
                if (!sanPham.isMatHang()) {
                    listMonAn.add(listOrders.get(i));
                }
            }

        }
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

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {

        int result = NO_SUCH_PAGE;
        Paper paper = new Paper();
        paper.setSize(200, height);
        paper.setImageableArea(10, 10, paper.getWidth(), paper.getHeight());
        pageFormat.setPaper(paper);
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
                g2d.drawString("                         WolvesRes", 80, y);
                y += yShift;
                g2d.drawString("            No 00000 Address Line One", 80, y);
                y += yShift;
                g2d.drawString("                         Cần Thơ", 80, y);
                y += yShift;
                g2d.drawString("                 www.facebook.com/", 80, y);
                y += yShift;
                g2d.drawString("                  000000000000000", 80, y);
                y += yShift;
                g2d.drawString("------------------------------------------------------------------------------------", 40, y);
                y += yShift;
                g2d.drawString("Mã bàn: " + listOrders.get(0).getMaBan(), 60, y);
                y += yShift;
                g2d.drawString("Thời gian: " + time, 60, y);
                y += yShift;
                g2d.drawString("------------------------------------------------------------------------------------", 40, y);
                y += headerRectHeight;

                g2d.drawString("      Tên món ăn                                      Số lượng    ", 40, y);
                y += yShift;
                g2d.drawString("------------------------------------------------------------------------------------", 40, y);
                y += headerRectHeight;

                for (int i = 0; i < listMonAn.size(); i++) {
                    String tenSP = getSanPhamByMaSP(listMonAn.get(i).getMaSP()).getTenSP();
                    String soLuong = String.valueOf(listMonAn.get(i).getSoLuong());
                    g2d.drawString(line(tenSP, soLuong, 55), 60, y);
                    y += headerRectHeight;
                }
                g2d.drawString("**************************************************************************", 35, y);
                y += yShift;
                g2d.drawString("             NHÀ HÀNG WOLVESRES", 80, y);
                y += yShift;
//                g2d.drawString("                             AMEN", 80, y);
//                y += yShift;

            } catch (Exception e) {
                e.printStackTrace();
            }
            result = PAGE_EXISTS;
        }
        return result;
    }

    public void print() {
        bHeight = Float.parseFloat(String.valueOf(listMonAn.size()));
        PrinterJob pj = PrinterJob.getPrinterJob();

        pj.setPrintable(this, PrintHelper.getPageFormat(pj, bHeight, width, headerHeight));
        try {
            pj.print();
        } catch (PrinterException ex) {
            // ex.printStackTrace();
        }
    }

}
