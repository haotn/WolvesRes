package com.wolvesres.form.sanpham;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.form.danhmuc.EditDanhMuc;
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
 * Liên quan: ModelDanhMuc
 * @author huynh
 *
 */
public class JDialogDanhMuc extends javax.swing.JDialog {

    private JFrame frame;
    private DefaultTableModel model;
    private List<ModelDanhMuc> listDM = new ArrayList<>();
    private DanhMucDAO dao = new DanhMucDAO();
    // sự kiện nút tbl
    /**
     * Nút sửa xoá trên form
     */
    private EventAction<ModelDanhMuc> eventAction = new EventAction<ModelDanhMuc>() {
        @Override
        public void delete(ModelDanhMuc entity) {
            if (dao.checkForeignKey(entity.getMaDanhMuc()) == null) {
                if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn xóa Đơn vị tính này không?", ROptionDialog.WARNING, Color.yellow, Color.black)) {
                    deleteDM(entity);
                }
            } else {
                ROptionDialog.showAlert(frame, "Thông báo", "Không thể xóa đơn vị tính đã được sử dụng trong danh mục!", ROptionDialog.PRIORITY_HIGHT, Color.red, Color.black);
            }
        }

        @Override
        public void update(ModelDanhMuc entity) {
            EditDanhMuc editForm = new EditDanhMuc(frame, true);
            editForm.setInsert(false);
            editForm.setDm(entity);
            editForm.setForm();
            editForm.setVisible(true);
            if (!editForm.isDispose()) {
            	updateDM(editForm.getDm());
            	fillToTable();
            }
        }
    };

    public JDialogDanhMuc(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.frame = (JFrame) parent;
        init();
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
     * Hàm load dứ liệu
     */
    private void loadToList() {
        listDM.addAll(dao.selectAll());
    }

    //
    public List<ModelDanhMuc> getListDM() {
        return listDM;
    }
    
    /**
     * Thêm danh mục
     * @param entity
     */
    public void insertDM(ModelDanhMuc entity) {
    	insertdata(entity);
    	fillinsert(entity);
    }
    
    public void insertdata(ModelDanhMuc entity) {
        dao.insert(entity);
    }
    
    public void fillinsert(ModelDanhMuc entity) {
        listDM.add(entity);
        fillToTable();
    }
    
    public void updateDM(ModelDanhMuc entity) {
    	updatedata(entity);
    	fillupdate(entity);
    	fillToTable();
    }
    
    public void updatedata(ModelDanhMuc entity) {
        dao.update(entity, entity.getMaDanhMuc());
    }
    
    public void fillupdate(ModelDanhMuc entity) {
        for (int i = 0; i < listDM.size(); i++) {
            if (listDM.get(i).getMaDanhMuc().equals(entity.getMaDanhMuc())) {
                listDM.set(i, entity);
                break;
            }
        }
    }

    /**
     * Hàm xóa danh mục
     * @param entity
     */
    private void deleteDM(ModelDanhMuc entity) {
    	deletedata(entity);
    	filldelete(entity);
    }
    
    public void deletedata(ModelDanhMuc entity) {
        dao.delete(entity.getMaDanhMuc());
    }
    
    public void filldelete (ModelDanhMuc entity) {
        listDM.remove(entity);
        fillToTable();
    }

    /**
     * Hàm fill dữ liệu lên bảng
     */
    private void fillToTable() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new Object[]{"Mã Danh Mục", "Tên Danh Mục", "Loại hàng", "Thao Tác"});
        tblDanhMuc.setModel(model);
        for (ModelDanhMuc dv : listDM) {
            tblDanhMuc.addRow(dv.toRowTable(eventAction));
        }
    }

    /**
     * Hàm desigs bảng
     */
    private void initTable() {
        tblDanhMuc.setOpaque(true);
        tblDanhMuc.setBackground(new Color(255, 255, 255));
        tblDanhMuc.setFillsViewportHeight(true);
        tblDanhMuc.fixTable(jScrollPane1);
        tblDanhMuc.setFont(new Font("SansSerif", 1, 12));
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Mã Danh Mục", "Tên Danh Mục", "Loại hàng", "Thao Tác"});
        tblDanhMuc.setModel(model);
        tblDanhMuc.setColumnAction(3);
    }
    
    /**
     * Hàm tìm kiếm theo tên danh mục
     * @param keyword
     * @return
     */
    public List<ModelDanhMuc> timkiem(String keyword){
        List<ModelDanhMuc> listFind = new ArrayList<>();
        	if(keyword.trim().length() > 0) {
        		listFind = dao.timkiem(keyword);
        	}else {
        		listFind = dao.selectAll();
        	}
        return listFind;
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
        btnClose = new com.swing.custom.raven.RButton.RButton();
        btnThemDM = new com.swing.custom.raven.RButton.RButton();
        btnDVT = new com.swing.custom.raven.RButton.RButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhMuc = new com.wolvesres.swing.table.Table();
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
        jLabel1.setText("DANH MỤC SẢN PHẨM");
        rRoundPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel2.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 100, 80));

        jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("WolvesRes");
        rRoundPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, -1, -1));

        rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 120));

        rRoundPanel3.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
        btnClose.setText("Đóng");
        btnClose.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        rRoundPanel3.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 90, 30));

        btnThemDM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/plus.png"))); // NOI18N
        btnThemDM.setText("Thêm danh mục");
        btnThemDM.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnThemDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDMActionPerformed(evt);
            }
        });
        rRoundPanel3.add(btnThemDM, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, 30));

        btnDVT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/categories.png"))); // NOI18N
        btnDVT.setText("Đơn vị tính");
        btnDVT.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnDVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDVTActionPerformed(evt);
            }
        });
        rRoundPanel3.add(btnDVT, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 140, 30));

        rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 720, 50));

        tblDanhMuc.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDanhMuc);

        rRoundPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 720, 490));

        txtFind.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFind.setLabelText("Tìm kiếm danh mục sản phẩm");
        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindKeyReleased(evt);
            }
        });
        rRoundPanel1.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 720, -1));

        getContentPane().add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnThemDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDMActionPerformed
        //
        EditDanhMuc editForm = new EditDanhMuc(frame, true);
        editForm.setVisible(true);
        if (!editForm.isDispose()) {
        	insertDM(editForm.getDm());
        	fillToTable();
        }
    }//GEN-LAST:event_btnThemDMActionPerformed

    private void txtFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKeyReleased
    	String keyword = txtFind.getText().trim();
    	listDM = timkiem(keyword);
		fillToTable();
    }//GEN-LAST:event_txtFindKeyReleased

    private void btnDVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDVTActionPerformed
        JDialogDonViTinh dvt = new JDialogDonViTinh(frame, true);
        dvt.setVisible(true);
    }//GEN-LAST:event_btnDVTActionPerformed

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
                JDialogDanhMuc dialog = new JDialogDanhMuc(new javax.swing.JFrame(), true);
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
    private com.swing.custom.raven.RButton.RButton btnClose;
    private com.swing.custom.raven.RButton.RButton btnDVT;
    private com.swing.custom.raven.RButton.RButton btnThemDM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.wolvesres.swing.table.Table tblDanhMuc;
    private com.swing.custom.raven.RTextField.RTextField txtFind;
    // End of variables declaration//GEN-END:variables
}
