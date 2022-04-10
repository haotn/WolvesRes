package com.wolvesres.form.sanpham;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.form.donvitinh.EditDonviTinh;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelDonViTinh;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.swing.table.EventAction;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
/**
 * Chỉnh sửa tìm kiếm, comment các hàm
 * Liên quan: ModelDonViTinh
 * @author huynh
 *
 */
public class JDialogDonViTinh extends javax.swing.JDialog {

    private JFrame frame;
    private List<ModelDonViTinh> listDVT = new ArrayList<>();
    private DonViTinhDAO dao = new DonViTinhDAO();
    private DefaultTableModel model;
    /**
     * Nút sửa xóa trên form
     */
    private EventAction<ModelDonViTinh> eventAction = new EventAction<ModelDonViTinh>() {
        @Override
        public void delete(ModelDonViTinh entity) {
            if (dao.checkForeignKey(entity.getMaDVT()) == null) {
                if (ROptionDialog.showConfirm(frame, "Xác nhận", "Xác nhận xóa?", ROptionDialog.WARNING, Color.yellow, Color.black)) {
                	deleteDVT(entity);
                	fillToTable();
                }
            } else {
                ROptionDialog.showAlert(frame, "Thông báo", "Đơn vị tính đang được sử dụng!", ROptionDialog.WARNING, Color.red, Color.black);
            }
        }

        @Override
        public void update(ModelDonViTinh entity) {
            EditDonviTinh editForm = new EditDonviTinh(frame, true);
            editForm.setInsert(false);
            editForm.setDonViTinh(entity);
            editForm.setForm();
            editForm.setVisible(true);
            if (!editForm.isDispose()) {
            	updateDVT(editForm.getDonViTinh());
                fillToTable();
            }
        }
    };

    public JDialogDonViTinh(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        setLocationRelativeTo(null);
        this.frame = (JFrame) parent;
    }

    /**
     * Hàm gọi các hàm bên dưới
     */
    private void init() {
        initTable();
        loadToList();
        fillToTable();
    }

    /**
     * Hàm desigs bảng
     */
    private void initTable() {
        tblDonViTinh.setOpaque(true);
        tblDonViTinh.setBackground(new Color(255, 255, 255));
        tblDonViTinh.setFillsViewportHeight(true);
        tblDonViTinh.fixTable(jScrollPane1);
        tblDonViTinh.setFont(new Font("SansSerif", 1, 12));
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Mã DVT", "Đơn Vị Tính", "Thao Tác"});
        tblDonViTinh.setModel(model);
        tblDonViTinh.setColumnAction(2);
    }

    /**
     * HÀm load dữ liệu
     */
    private void loadToList() {
        listDVT.addAll(dao.selectAll());
    }

    public List<ModelDonViTinh> getList() {
        return this.listDVT;
    }

    /**
     * Hàm fill dữ liệu
     */
    private void fillToTable() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"Mã DVT", "Đơn Vị Tính", "Thao Tác"});
        tblDonViTinh.setModel(model);
        for (ModelDonViTinh dvt : listDVT) {
            tblDonViTinh.addRow(dvt.toRowTable(eventAction));
        }
    }
    
    /**
     * Tìm theo tên đơn vị tính
     * @param keyword
     * @return
     */
    public List<ModelDonViTinh> timkiem(String keyword){
        List<ModelDonViTinh> listFind = new ArrayList<>();
        	if(keyword.trim().length() > 0) {
        		listFind = dao.timkiem(keyword);
        	}else {
        		listFind = dao.selectAll();
        	}
        return listFind;
    }
    
    /**
     * Thêm đơn vị tính
     * @param dvt
     */
    public void insertDVT(ModelDonViTinh dvt) {
    	insertdata(dvt);
    	fillinsertDVT(dvt);
    }
    
    public void insertdata(ModelDonViTinh dvt) {
    	dao.insert(dvt);
    }
    
    public void fillinsertDVT(ModelDonViTinh dvt) {
    	listDVT.add(dvt);
    	fillToTable();
    }
    
    /**
     * Update đơn vị tính
     */
    public void updateDVT(ModelDonViTinh dvt) {
    	updatedate(dvt);
    	fillupdateDVT(dvt);
    }
    
    public void updatedate(ModelDonViTinh dvt) {
        dao.update(dvt, dvt.getMaDVT());
    }
    
    public void fillupdateDVT(ModelDonViTinh dvt) {
        for (int i = 0; i < listDVT.size(); i++) {
            if (listDVT.get(i).getMaDVT() == dvt.getMaDVT()) {
                listDVT.set(i, dvt);
                break;
            }
        }
        fillToTable();
    }
    
    /**
     * delete đơn vị tính
     */
    public void deleteDVT(ModelDonViTinh dvt) {
    	deletedata(dvt);
    	filldelete(dvt);
    }
    
    public void deletedata(ModelDonViTinh dvt) {
        dao.delete(dvt.getMaDVT());
    }
    
    public void filldelete(ModelDonViTinh dvt) {
        listDVT.remove(dvt);
        fillToTable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        jLabel5 = new javax.swing.JLabel();
        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        rButton1 = new com.swing.custom.raven.RButton.RButton();
        btnThem = new com.swing.custom.raven.RButton.RButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDonViTinh = new com.wolvesres.swing.table.Table();
        txtFind = new com.swing.custom.raven.RTextField.RTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(720, 710));
        setMinimumSize(new java.awt.Dimension(720, 710));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ĐƠN VỊ TÍNH");
        rRoundPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, -1, 40));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel2.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 100, 80));

        jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("WolvesRes");
        rRoundPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, -1, -1));

        rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 120));

        rRoundPanel3.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
        rButton1.setText("Đóng");
        rButton1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        rButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton1ActionPerformed(evt);
            }
        });
        rRoundPanel3.add(rButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 90, -1));

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/addition.png"))); // NOI18N
        btnThem.setText("Thêm đơn vị tính");
        btnThem.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        rRoundPanel3.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 180, 30));

        rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 720, 50));

        tblDonViTinh.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDonViTinh);

        rRoundPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 720, 490));

        txtFind.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFind.setLabelText("Tìm kiếm đơn vị tính");
        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindKeyReleased(evt);
            }
        });
        rRoundPanel1.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 720, -1));

        getContentPane().add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_rButton1ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        EditDonviTinh editForm = new EditDonviTinh(frame, true);
        editForm.setVisible(true);
        if (!editForm.isDispose()) {
        	insertDVT(editForm.getDonViTinh());
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKeyReleased
    	String keyword = txtFind.getText().trim();
    	listDVT = timkiem(keyword);
		fillToTable();
    }//GEN-LAST:event_txtFindKeyReleased
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
            java.util.logging.Logger.getLogger(JDialogDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogDonViTinh dialog = new JDialogDonViTinh(new javax.swing.JFrame(), true);
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
    private com.swing.custom.raven.RButton.RButton btnThem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private com.swing.custom.raven.RButton.RButton rButton1;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.wolvesres.swing.table.Table tblDonViTinh;
    private com.swing.custom.raven.RTextField.RTextField txtFind;
    // End of variables declaration//GEN-END:variables
}
