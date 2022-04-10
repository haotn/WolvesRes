package com.wolvesres.form.thongke;

import com.swing.custom.raven.RCard.RModelCard;
import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.ThongKeDAO;
import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XFormatMoney;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class FormThongKeBan extends javax.swing.JPanel {

    private JFrame frame;
    List<Object[]> listTKBAN = new ArrayList<>();
    ThongKeDAO dao = new ThongKeDAO();
    DefaultTableModel model;
    int type = 1;
    private ActionListener back;

    /**
     * Sự kiện nút để vè trang thống kê tổng
     * @param back
     */
    public void setBack(ActionListener back) {
        this.back = back;
    }

    /**
     * Hàm gọi các hàm bên dưới
     */
    public FormThongKeBan() {
        initComponents();
        setOpaque(false);
        initTable();
        date();
    }
    
    /**
     * Hàm chọn ngày này bắt đầu sẽ đc mặc định cách ngày kết thúc 1 năm
     */
    public void date() {
        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        String from = formater.format(today);
        int year = dateChooser1.getSelectedDate().getYear() - 1;
        from = from.substring(0, 6);
        from += String.valueOf(year);
        dateChooser1.setSelectedDate(XDate.toDate(from, "dd-MM-yyyy"));
        listTKBAN.addAll(dao.getBan_TG(from, txtDenNgay.getText(), 1));
        fillToTable();
    }

    /**
     * hàm desigs bảng
     */
    private void initTable() {
        tblTKBan.setOpaque(true);
        tblTKBan.setBackground(new Color(255, 255, 255));
        tblTKBan.setFillsViewportHeight(true);
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Ngày", "Tên Bàn", "Số Lượng Hóa Đơn", "Doanh Thu Thực"});
        tblTKBan.setModel(model);
        tblTKBan.setColumnAction(10);
    }

    /**
     * fill dữ liệu
     */
    public void fillToTable() {
        model.setRowCount(0);
        for (Object[] row : listTKBAN) {
            model.addRow(new Object[]{row[0], row[1], row[2], XFormatMoney.formatMoney(Float.parseFloat(String.valueOf(row[3])))});

        }
    }

    /**
     * Hàm xuất thống kê ra file excel
     */
    private void xuatTK() {
        try {
            // Excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("THỐNG KÊ 1");

            // Thêm chủ đề thống kê từ dòng 0
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 2).setCellValue("THỐNG KÊ BAN");

            // Thêm tiêu đề cột từ dòng 2
            rowhead = sheet.createRow((short) 2);
            rowhead.createCell((short) 0).setCellValue("NGÀY");
            rowhead.createCell((short) 1).setCellValue("TÊN BÀN");
            rowhead.createCell((short) 2).setCellValue("SỐ LƯỢNG HÓA ĐƠN");
            rowhead.createCell((short) 3).setCellValue("DOANH THU THỰC");

            // Thêm dữ liệu từ dòng 3
            for (int i = 0; i < listTKBAN.size(); i++) {
                HSSFRow row = sheet.createRow((short) i + 3);
                row.createCell((short) 0).setCellValue((String) listTKBAN.get(i)[0]);
                row.createCell((short) 1).setCellValue((String) listTKBAN.get(i)[1]);
                row.createCell((short) 2).setCellValue((int) listTKBAN.get(i)[2]);
                row.createCell((short) 3).setCellValue((double) listTKBAN.get(i)[3]);
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
        ROptionDialog.showAlert(frame, "Thông báo", "Xuất dữ liệu thành công!", ROptionDialog.NOTIFICATIONS_ACTIVE, new Color(0, 199, 135), Color.BLACK);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser = new com.raven.datechooser.DateChooser();
        dateChooser1 = new com.raven.datechooser.DateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTKBan = new com.wolvesres.swing.table.Table();
        btnThang = new com.swing.custom.raven.RButton.RButton();
        btnNgay = new com.swing.custom.raven.RButton.RButton();
        btnNam = new com.swing.custom.raven.RButton.RButton();
        btnXuatDuLieu = new com.swing.custom.raven.RButton.RButton();
        jLabel3 = new javax.swing.JLabel();
        txtTuNgay = new com.swing.custom.raven.RTextField.RTextField();
        txtDenNgay = new com.swing.custom.raven.RTextField.RTextField();
        rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        lblTitle = new javax.swing.JLabel();
        rButton7 = new com.swing.custom.raven.RButton.RButton();

        dateChooser.setForeground(new java.awt.Color(0, 0, 0));
        dateChooser.setTextRefernce(txtDenNgay);
        dateChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dateChooserMousePressed(evt);
            }
        });

        dateChooser1.setForeground(new java.awt.Color(0, 0, 0));
        dateChooser1.setTextRefernce(txtTuNgay);
        dateChooser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dateChooser1MousePressed(evt);
            }
        });

        setBackground(new java.awt.Color(209, 220, 208));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBorder(null);

        tblTKBan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTKBan.setOpaque(false);
        jScrollPane2.setViewportView(tblTKBan);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 191, 1150, 480));

        btnThang.setText("Tháng");
        btnThang.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThangActionPerformed(evt);
            }
        });
        add(btnThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 150, 70, -1));

        btnNgay.setText("Ngày");
        btnNgay.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNgayActionPerformed(evt);
            }
        });
        add(btnNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 70, -1));

        btnNam.setText("Năm");
        btnNam.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNamActionPerformed(evt);
            }
        });
        add(btnNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, 70, -1));

        btnXuatDuLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/exportFile.png"))); // NOI18N
        btnXuatDuLieu.setText("Xuất file");
        btnXuatDuLieu.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnXuatDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDuLieuActionPerformed(evt);
            }
        });
        add(btnXuatDuLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 682, 169, -1));

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Chọn khoảng thời gian");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        txtTuNgay.setLabelText("Từ Ngày");
        txtTuNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtTuNgayMousePressed(evt);
            }
        });
        add(txtTuNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 190, 40));

        txtDenNgay.setLabelText("Đến Ngày");
        txtDenNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtDenNgayMousePressed(evt);
            }
        });
        add(txtDenNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 190, 40));

        rRoundPanel4.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("WolvesRes");
        rRoundPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 40, 160, -1));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel4.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 120, 110));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("THỐNG KÊ BÀN");
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
        add(rButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 680, 120, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        // TODO add your handling code here:
        xuatTK();
    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void btnNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgayActionPerformed
        type = 1;
        listTKBAN.clear();
        listTKBAN.addAll(dao.getBan_TG(txtTuNgay.getText(), txtDenNgay.getText(), type));
        fillToTable();
    }//GEN-LAST:event_btnNgayActionPerformed

    private void btnThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThangActionPerformed
        type = 2;
        listTKBAN.clear();
        listTKBAN.addAll(dao.getBan_TG(txtTuNgay.getText(), txtDenNgay.getText(), type));
        fillToTable();
    }//GEN-LAST:event_btnThangActionPerformed

    private void btnNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNamActionPerformed
        type = 3;
        listTKBAN.clear();
        listTKBAN.addAll(dao.getBan_TG(txtTuNgay.getText(), txtDenNgay.getText(), type));
        fillToTable();
    }//GEN-LAST:event_btnNamActionPerformed

    private void txtTuNgayMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTuNgayMousePressed

    }//GEN-LAST:event_txtTuNgayMousePressed

    private void txtDenNgayMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDenNgayMousePressed

    }//GEN-LAST:event_txtDenNgayMousePressed

    private void dateChooserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateChooserMousePressed

    }//GEN-LAST:event_dateChooserMousePressed

    private void dateChooser1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateChooser1MousePressed

    }//GEN-LAST:event_dateChooser1MousePressed

    private void rButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton7ActionPerformed
        back.actionPerformed(evt);
    }//GEN-LAST:event_rButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.raven.RButton.RButton btnNam;
    private com.swing.custom.raven.RButton.RButton btnNgay;
    private com.swing.custom.raven.RButton.RButton btnThang;
    private com.swing.custom.raven.RButton.RButton btnXuatDuLieu;
    private com.raven.datechooser.DateChooser dateChooser;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitle;
    private com.swing.custom.raven.RButton.RButton rButton7;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
    private com.wolvesres.swing.table.Table tblTKBan;
    private com.swing.custom.raven.RTextField.RTextField txtDenNgay;
    private com.swing.custom.raven.RTextField.RTextField txtTuNgay;
    // End of variables declaration//GEN-END:variables
}
