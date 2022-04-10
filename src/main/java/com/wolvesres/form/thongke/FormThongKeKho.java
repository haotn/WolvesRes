package com.wolvesres.form.thongke;

import com.swing.custom.raven.RCard.RModelCard;
import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.ThongKeDAO;
import com.wolvesres.helper.XDate;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Comment các hàm
 * @author huynh
 *
 */
public class FormThongKeKho extends javax.swing.JPanel {

    private JFrame frame;
    List<Object[]> listTKKHO = new ArrayList<>();
    ThongKeDAO dao = new ThongKeDAO();
    DefaultTableModel model;
    private ActionListener back;

    /**
    * Sự kiện nút để vè trang thống kê tổng
    * @param back
    */
    public void setBack(ActionListener back) {
        this.back = back;
    }
    
    public FormThongKeKho() {
        initComponents();
        setOpaque(false);
        init();
    }

    /**
     * Hàm gọi các hàm bên dưới
     */
    public void init() {
        initTable();
        loadToList();
        fillToTable();
    }

    /**
     * hàm desigs bảng
     */
    private void initTable() {
        tblTKKho.setOpaque(true);
        tblTKKho.setBackground(new Color(255, 255, 255));
        tblTKKho.setFillsViewportHeight(true);
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Tên sản phẩm", "Số lượng", "Số lô", "Han sử dụng"});
        tblTKKho.setModel(model);
        tblTKKho.setColumnAction(10);
    }

    /**
     * load dữ liệu
     */
    public void loadToList() {
        listTKKHO.clear();
        listTKKHO.addAll(dao.getKho());
    }

    /**
     * fill dữ liệu
     */
    public void fillToTable() {
        model.setRowCount(0);
        for (Object[] row : listTKKHO) {
            tblTKKho.addRow(new Object[]{row[0], row[1], row[2], XDate.toString((Date) row[3], "dd-MM-yyyy")});
        }
    }

    /**
     * Hàm xuất thống kê ra file excel
     */
    private void xuatTK() {
        try {
            // Excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("THỐNG KÊ");

            // Thêm chủ đề thống kê từ dòng 0
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("THỐNG KÊ KHO");

            // Thêm tiêu đề cột từ dòng 2
            rowhead = sheet.createRow((short) 2);
            rowhead.createCell((short) 0).setCellValue("TÊN SẢN PHẨM");
            rowhead.createCell((short) 1).setCellValue("SỐ LƯỢNG");
            rowhead.createCell((short) 2).setCellValue("SỐ LÔ");
            rowhead.createCell((short) 3).setCellValue("HẠN SỬ DỤNG");

            // Thêm dữ liệu từ dòng 3
            for (int j = 0; j < listTKKHO.size(); j++) {
                Object[] o = listTKKHO.get(j);
                o[3] = XDate.toString(XDate.toDate(String.valueOf(o[3]), "yyyy-MM-dd"), "dd-MM-yyyy");
            }
            for (int i = 0; i < listTKKHO.size(); i++) {
                HSSFRow row = sheet.createRow((short) i + 3);
                row.createCell((short) 0).setCellValue((String) listTKKHO.get(i)[0]);
                row.createCell((short) 1).setCellValue((int) listTKKHO.get(i)[1]);
                row.createCell((short) 2).setCellValue((int) listTKKHO.get(i)[2]);
                row.createCell((short) 3).setCellValue((String) listTKKHO.get(i)[3]);
            }

            String duongDan = null;
            JFileChooser luuDanDuong;
            if (duongDan == null) {
                luuDanDuong = new JFileChooser();
                luuDanDuong.setSelectedFile(new File(""));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", ".xls", ".xlsx"); // kiểu file
                luuDanDuong.addChoosableFileFilter(filter);

                int returnValue = luuDanDuong.showSaveDialog(this); // kiểu Open file
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    duongDan = luuDanDuong.getSelectedFile().getPath();
                    if (!duongDan.endsWith(".xls")) {
                        duongDan = duongDan + ".xls";
                    }
                    try {
                        workbook.write(new File(duongDan));
                    } catch (Exception e) {
                        ROptionDialog.showAlert(frame, "Lỗi", "Xuất dữ liệu thất bại!", ROptionDialog.WARNING, Color.red, Color.black);
                    }
                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
                    return;
                }
            } else {
                workbook.write(new File(duongDan));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        ROptionDialog.showAlert(frame, "Thông báo", "Xuất dữ liệu thành công!", ROptionDialog.NOTIFICATIONS_ACTIVE, new Color(0, 199, 135), Color.black);
    }

//private String dateToString(Object o) {
//        String date = " ";
//            date = String.valueOf(o);
////            date =  XDate.toString(XDate.toDate((String) o, "dd-MM-yyyy"), "dd-MM-yyyy");
//            System.out.println("dsdfmsbdfsbfsjdbfsdf");
//        return date;
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser = new com.raven.datechooser.DateChooser();
        dateChooser1 = new com.raven.datechooser.DateChooser();
        btnXuatDuLieu = new com.swing.custom.raven.RButton.RButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTKKho = new com.wolvesres.swing.table.Table();
        rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        lblTitle = new javax.swing.JLabel();
        rButton7 = new com.swing.custom.raven.RButton.RButton();

        dateChooser.setForeground(new java.awt.Color(0, 0, 0));

        dateChooser1.setForeground(new java.awt.Color(0, 0, 0));

        setBackground(new java.awt.Color(209, 220, 208));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnXuatDuLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/exportFile.png"))); // NOI18N
        btnXuatDuLieu.setText("Xuất Dữ Liệu");
        btnXuatDuLieu.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnXuatDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDuLieuActionPerformed(evt);
            }
        });
        add(btnXuatDuLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 687, 169, 30));

        jScrollPane2.setBorder(null);

        tblTKKho.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTKKho.setOpaque(false);
        jScrollPane2.setViewportView(tblTKKho);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 198, 1150, 476));

        rRoundPanel4.setBackground(new java.awt.Color(71, 92, 77));
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
        lblTitle.setText("THỐNG KÊ KHO");
        rRoundPanel4.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 110));

        rButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/back.png"))); // NOI18N
        rButton7.setText("Quan lại");
        rButton7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        rButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton7ActionPerformed(evt);
            }
        });
        add(rButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 690, 120, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        xuatTK();
    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void rButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton7ActionPerformed
        back.actionPerformed(evt);
    }//GEN-LAST:event_rButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.raven.RButton.RButton btnXuatDuLieu;
    private com.raven.datechooser.DateChooser dateChooser;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitle;
    private com.swing.custom.raven.RButton.RButton rButton7;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
    private com.wolvesres.swing.table.Table tblTKKho;
    // End of variables declaration//GEN-END:variables
}
