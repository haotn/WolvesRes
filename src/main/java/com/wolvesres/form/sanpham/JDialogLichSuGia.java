/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wolvesres.form.sanpham;

import com.wolvesres.dao.LichSuGiaDAO;
import com.wolvesres.form.FormSanPham;
import com.wolvesres.model.ModelDonViTinh;
import com.wolvesres.model.ModelLichSuGia;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.swing.table.EventAction;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Chỉnh sửa tìm kiếm, comment các hàm
 * Liên quan: ModelLichSuGia
 * @author FPT
 */
public class JDialogLichSuGia extends javax.swing.JDialog {

    JFrame frame;
    DefaultTableModel model;
    List<ModelLichSuGia> listLSG = new ArrayList<>();
    List<ModelLichSuGia> listLSG1 = new ArrayList<>();
    LichSuGiaDAO dao = new LichSuGiaDAO();
    int selectedRow = -1;
    FormSanPham formSP = new FormSanPham(frame);
    private ModelSanPham sp = new ModelSanPham();
    public JDialogLichSuGia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        listLSG1.addAll(dao.selectAll());
        setLocationRelativeTo(null);
    }
    /**
     * Nút sửa xóa trên form
     */
    EventAction<ModelLichSuGia> eventAction = new EventAction<ModelLichSuGia>() {
        @Override
            public void delete(ModelLichSuGia lichsu) {
        }
        @Override
        public void update(ModelLichSuGia lichsu) {
        }
    };

    public ModelSanPham getSp() {
        return sp;
    }

    public void setSp(ModelSanPham sp) {
        this.sp = sp;
    }
    
    /**
     * Hàm gọi các hàm bên dưới
     */
    public void init(){
        setBackground(new Color(209,220,208));
        initTable();
        loadToList();
        fillToTable();
    }
    
    /**
     * Hàm desigs bảng
     */
    private void initTable() {
        tblLichSuGia.setOpaque(true);
        tblLichSuGia.setBackground(new Color(255, 255, 255));
        tblLichSuGia.setFillsViewportHeight(true);
        tblLichSuGia.fixTable(jScrollPane1);
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Tên sản phẩm", "Mã sản phẩm", "Ngày thay đổi", "Giá bán"});
        tblLichSuGia.setModel(model);
        tblLichSuGia.setColumnAction(10);
    }
    
    /**
     * Hàm load dữ liệu
     */
    public void loadToList(){
        for(ModelLichSuGia lsGia : listLSG1){
           if(lsGia.getMaSP().equals(sp.getMaSP())){
              listLSG.add(lsGia);
           }
        }
    }
    
    /**
     * Hàm fill dữ liệu
     */
    public void fillToTable(){
        for(ModelLichSuGia ls : listLSG){
            tblLichSuGia.addRow(ls.toRowTable(eventAction));
        }
    } 
    
    /**
     * tìm kiếm theo mã sản phẩm
     */
    public List<ModelLichSuGia> timkiem(String keyword){
        List<ModelLichSuGia> listFind = new ArrayList<>();
        	if(keyword.trim().length() > 0) {
        		listFind = dao.timkiem(keyword);
        		listFind = dao.selectAll();
        	}
        return listFind;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblLichSuGia = new com.wolvesres.swing.table.Table();
        txtFind = new com.swing.custom.raven.RTextField.RTextField();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        jLabel5 = new javax.swing.JLabel();
        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        btnDong1 = new com.swing.custom.raven.RButton.RButton();
        rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(209, 220, 208));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblLichSuGia.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLichSuGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblLichSuGiaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblLichSuGia);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 720, 490));

        txtFind.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFind.setLabelText("Tìm kiếm lịch sử giá");
        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindKeyReleased(evt);
            }
        });
        getContentPane().add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 720, -1));

        rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LỊCH SỬ GIÁ");
        rRoundPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, -1, 40));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel2.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 100, 80));

        jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("WolvesRes");
        rRoundPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, -1, -1));

        getContentPane().add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 120));

        rRoundPanel3.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDong1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
        btnDong1.setText("Đóng");
        btnDong1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnDong1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDong1ActionPerformed(evt);
            }
        });
        rRoundPanel3.add(btnDong1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 90, -1));

        getContentPane().add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 720, 50));

        rRoundPanel4.setBackground(new java.awt.Color(209, 220, 208));
        rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKeyReleased
    	String keyword = txtFind.getText().trim();
    	listLSG = timkiem(keyword);
		fillToTable();
    }//GEN-LAST:event_txtFindKeyReleased

    private void btnDong1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDong1ActionPerformed
        dispose();
    }//GEN-LAST:event_btnDong1ActionPerformed

    private void tblLichSuGiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuGiaMousePressed

    }//GEN-LAST:event_tblLichSuGiaMousePressed

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
            java.util.logging.Logger.getLogger(JDialogLichSuGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogLichSuGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogLichSuGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogLichSuGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogLichSuGia dialog = new JDialogLichSuGia(new javax.swing.JFrame(), true);
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
    private com.swing.custom.raven.RButton.RButton btnDong1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
    private com.wolvesres.swing.table.Table tblLichSuGia;
    private com.swing.custom.raven.RTextField.RTextField txtFind;
    // End of variables declaration//GEN-END:variables
}
