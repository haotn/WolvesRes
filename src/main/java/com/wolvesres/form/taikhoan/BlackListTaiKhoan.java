package com.wolvesres.form.taikhoan;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.form.FormTaiKhoan;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelTaiKhoan;
import com.wolvesres.swing.table.EventActionBlackList;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
/**
 * Chỉnh sửa tìm kiếm, comment ở các hàm
 * Liên quan ModelTaiKhoan
 * @author huynh
 *
 */
public class BlackListTaiKhoan extends javax.swing.JDialog {

    public BlackListTaiKhoan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        txtFindBLTaiKhoan.setLabelText("TÃ¬m kiáº¿m trong danh sÃ¡ch Ä‘en");
        setLocationRelativeTo(null);
    }

    JFrame frame;
    DefaultTableModel model;
    private boolean isChangeData = false;
    List<ModelTaiKhoan> listTK = new ArrayList<ModelTaiKhoan>();
    List<ModelNhanVien> listNhanVien = new ArrayList<>();
    private List<ModelTaiKhoan> listReturn = new ArrayList<>();
    FormTaiKhoan formParent = new FormTaiKhoan(frame);
    TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    NhanVienDAO nhanVienDAO = new NhanVienDAO();
    
    /**
     * nút update trên bảng
     */
    EventActionBlackList<ModelTaiKhoan> eventAction = new EventActionBlackList<ModelTaiKhoan>() {
        @Override
        public void update(ModelTaiKhoan taikhoan) {
            if (tblBLTaiKhoan.getSelectedRow() >= 0) {
                ModelTaiKhoan tk = listTK.get(tblBLTaiKhoan.getSelectedRow());
                if (checkNhanVien(tk.getTaiKhoan())) {
                    if (ROptionDialog.showConfirm(frame, "XÃ¡c nháº­n", "Báº¡n cÃ³ cháº¯c muá»‘n kÃ­ch hoáº¡t láº¡i tÃ i khoáº£n khÃ´ng?", ROptionDialog.WARNING, Color.yellow, Color.black)) {
                        isChangeData = true;
                        tk.setTrangThai(true);
                        updateTaiKhoan(tk);
                        addToListReturn(tk);
                        fillToTable();
                    }
                }
            } else {
                ROptionDialog.showAlert(frame, "ThÃ´ng bÃ¡o", "VÃ¹i lÃ²ng chá»�n tÃ i khoáº£n!", ROptionDialog.PRIORITY_HIGHT, Color.red, Color.black);
            }
        }
    };

    /**
     * Hàm tổng hợp các hàm bên dưới
     */
    public void init() {
        listNhanVien.addAll(nhanVienDAO.selectAll());
        initTable();
        loadToBlackList();
        fillToTable();
    }

    /**
     * Hàm desigs bảng trên form 
     */
    public void initTable() {
        tblBLTaiKhoan.setOpaque(true);
        tblBLTaiKhoan.setBackground(new Color(255, 255, 255));
        tblBLTaiKhoan.setFillsViewportHeight(true);
        tblBLTaiKhoan.fixTable(jScrollPane1);
        tblBLTaiKhoan.setFont(new Font("SansSerif", 1, 12));
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"TÃªn tÃ i khoáº£n", "NhÃ¢n viÃªn sá»Ÿ há»¯u", "Chá»©c vá»¥", "Thao tÃ¡c"});
        tblBLTaiKhoan.setModel(model);
        tblBLTaiKhoan.setColumnAction(3);
        tblBLTaiKhoan.setActionWhiteList(false);
    }

    /**
     * Hàm load dữ liệu lên bảng đen
     */
    public void loadToBlackList() {
        listTK.clear();
        for (ModelTaiKhoan tk : formParent.getList()) {
            if (tk.isTrangThai() == false) {
                listTK.add(tk);
            }
        }
    }

    public boolean getIsChangeData() {
        return this.isChangeData;
    }

    /**
     * Hàm return bảng
     * @param acc
     */
    private void addToListReturn(ModelTaiKhoan acc) {
        listReturn.add(acc);
    }

    public List<ModelTaiKhoan> getListReturn() {
        return this.listReturn;
    }

    /**
     * Hàm fill dữ liệu lên bảng
     */
    public void fillToTable() {
        loadToBlackList();
        model.setRowCount(0);
        for (int i = 0; i < listTK.size(); i++) {
            tblBLTaiKhoan.addRow(listTK.get(i).toRowTable(eventAction));
        }
    }

    /**
     * Hàm lấy mã nhân viên
     * @param manv
     * @return
     */
    private ModelNhanVien getNhanVienByMaNV(String manv) {
        ModelNhanVien emp = new ModelNhanVien();
        for (int i = 0; i < listNhanVien.size(); i++) {
            if (manv.equals(listNhanVien.get(i).getMaNV())) {
                emp = listNhanVien.get(i);
                break;
            }
        }
        return emp;
    }

    /**
     * Hàm kiểm tra mã nhân viên
     */
    private boolean checkNhanVien(String manv) {
        ModelNhanVien emp = getNhanVienByMaNV(manv);
        if (!emp.isTrangThai()) {
            ROptionDialog.showAlert(frame, "ThÃ´ng bÃ¡o", "KhÃ´ng thá»ƒ kÃ­ch hoáº¡t tÃ i khoáº£n nÃ y, nhÃ¢n viÃªn nÃ y Ä‘ang náº±m trong danh sÃ¡ch Ä‘en!", ROptionDialog.WARNING, Color.red, Color.black);
            return false;
        }
        return true;

    }
    
    public void updateTaiKhoan(ModelTaiKhoan entity) {
    	updatedata(entity);
    	fillupdate(entity);
    }
    
    public void updatedata(ModelTaiKhoan entity) {
    	taiKhoanDAO.update(entity, entity.getTaiKhoan());
    }
    
    public void fillupdate(ModelTaiKhoan entity) {
        for (int i = 0; i < formParent.getList().size(); i++) {
            if (formParent.getList().get(i).getTaiKhoan().endsWith(entity.getTaiKhoan())) {
                formParent.getList().set(i, entity);
                break;
            }
        }
        fillToTable();
    }
    
    /**
     * Tìm kiếm theo tên tài khoản
     * @param keyword
     * @return
     */
    public List<ModelTaiKhoan> timkiem(String keyword){
        List<ModelTaiKhoan> listFind = new ArrayList<>();
        	if(keyword.trim().length() > 0) {
        		listFind = taiKhoanDAO.timkiem(keyword);
        	}else {
        		listFind = taiKhoanDAO.selectAll();
        	}
        return listFind;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDuaRaKBL = new com.swing.custom.raven.RButton.RButton();
        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        jLabel5 = new javax.swing.JLabel();
        txtFindBLTaiKhoan = new com.swing.custom.raven.RTextField.RTextField();
        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        btnDong = new com.swing.custom.raven.RButton.RButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBLTaiKhoan = new com.wolvesres.swing.table.Table();

        btnDuaRaKBL.setBackground(new java.awt.Color(102, 102, 102));
        btnDuaRaKBL.setForeground(new java.awt.Color(255, 255, 255));
        btnDuaRaKBL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/back.png"))); // NOI18N
        btnDuaRaKBL.setText("KÃ­ch hoáº¡t tÃ i khoáº£n");
        btnDuaRaKBL.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnDuaRaKBL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDuaRaKBLActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(720, 710));

        rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));
        rRoundPanel1.setMaximumSize(new java.awt.Dimension(720, 710));
        rRoundPanel1.setMinimumSize(new java.awt.Dimension(720, 710));

        rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DANH SÃ�CH VÃ” HIá»†U HÃ“A");
        rRoundPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, 50));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel2.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 100, 80));

        jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("WolvesRes");
        rRoundPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, -1, -1));

        txtFindBLTaiKhoan.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFindBLTaiKhoan.setLabelText("Tim kiáº¿m tÃ i khoáº£n trong danh sÃ¡ch Ä‘en");
        txtFindBLTaiKhoan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindBLTaiKhoanKeyReleased(evt);
            }
        });

        rRoundPanel3.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
        btnDong.setText("Ä�Ã³ng");
        btnDong.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongActionPerformed(evt);
            }
        });
        rRoundPanel3.add(btnDong, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 100, 30));

        tblBLTaiKhoan.setAutoCreateRowSorter(true);
        tblBLTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblBLTaiKhoan);

        javax.swing.GroupLayout rRoundPanel1Layout = new javax.swing.GroupLayout(rRoundPanel1);
        rRoundPanel1.setLayout(rRoundPanel1Layout);
        rRoundPanel1Layout.setHorizontalGroup(
            rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(rRoundPanel1Layout.createSequentialGroup()
                .addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rRoundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFindBLTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rRoundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        rRoundPanel1Layout.setVerticalGroup(
            rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rRoundPanel1Layout.createSequentialGroup()
                .addComponent(rRoundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFindBLTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(rRoundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDuaRaKBLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDuaRaKBLActionPerformed

    }//GEN-LAST:event_btnDuaRaKBLActionPerformed

    private void btnDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongActionPerformed
        dispose();
    }//GEN-LAST:event_btnDongActionPerformed

    private void txtFindBLTaiKhoanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindBLTaiKhoanKeyReleased
    	String keyword = txtFindBLTaiKhoan.getText().trim();
    	if(keyword.isEmpty()) {
    		listTK.clear();
    	}else {
    		listTK = timkiem(keyword);
    	}
    	fillToTable();
    }//GEN-LAST:event_txtFindBLTaiKhoanKeyReleased

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
            java.util.logging.Logger.getLogger(BlackListTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BlackListTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BlackListTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BlackListTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BlackListTaiKhoan dialog = new BlackListTaiKhoan(new javax.swing.JFrame(), true);
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
    private com.swing.custom.raven.RButton.RButton btnDong;
    private com.swing.custom.raven.RButton.RButton btnDuaRaKBL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.wolvesres.swing.table.Table tblBLTaiKhoan;
    private com.swing.custom.raven.RTextField.RTextField txtFindBLTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
