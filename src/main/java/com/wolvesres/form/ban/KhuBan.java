package com.wolvesres.form.ban;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.KhuBanDAO;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelKhuBan;
import com.wolvesres.model.ModelTaiKhoan;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
/**
 * Chỉnh sửa tìm kiếm, bắt lỗi, Commnet các hàm
 * Liên quan: ModelKhuban
 * @author huynh
 *
 */
public class KhuBan extends javax.swing.JDialog {

    public KhuBan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        init();
    }

    private JFrame frame;
    private DefaultTableModel model;
    private List<ModelKhuBan> listKB = new ArrayList<>();
    private KhuBanDAO daokb = new KhuBanDAO();
    private AutoDAO autoDao = new AutoDAO();
    private ModelKhuBan entity = new ModelKhuBan();
    private ModelKhuBan kb = null;

    /**
     * Hàm gọi các hàm bên dưới
     */
    private void init() {
        initAuto();
        initTable();
        loadToList();
        fillToTable();
    }
    
    /**
     * Hàm tự xin mã
     */
    private void initAuto() {
        lblMaKhuBan.setText(autoDao.AuToKhuBan());
    }

    /**
     * Hàm desigs bảng
     */
    private void initTable() {
        tblKhuBan.setOpaque(true);
        tblKhuBan.setBackground(new Color(209, 220, 208));
        tblKhuBan.setFillsViewportHeight(true);
        tblKhuBan.fixTable(jScrollPane2);
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Mã Khu Bàn", "Tên Khu Bàn", "Ghi Chú"});
        tblKhuBan.setModel(model);
        tblKhuBan.setColumnAction(3);
        txtTenKhuBan.setLabelText("Tên Khu Bàn");
        txtFindKhuBan.setLabelText("Tìm Kiếm");
    }

    /**
     * Hàm load dữ liệu
     */
    public void loadToList() {
        listKB.addAll(daokb.selectAll());
    }

    /**
     * Hàm fill dữ liệu
     */
    public void fillToTable() {
        model.setRowCount(0);
        for (ModelKhuBan kb : listKB) {
            model.addRow(kb.toRowModel());
        }
    }

    /**
     * Hàm select bảng
     * @param selectedRow
     */
    public void showDetail(int selectedRow) {
        if (selectedRow >= 0) {
            ModelKhuBan kb = listKB.get(selectedRow);
            lblMaKhuBan.setText(kb.getMaKhuBan());
            txtTenKhuBan.setText(kb.getTenKhuBan());
            txtGhiChu.setText(kb.getGhiChu());
        }
    }

    /**
     * Hàm get form
     * @return
     */
    public ModelKhuBan getForm() {
        ModelKhuBan kb = new ModelKhuBan();
        kb.setMaKhuBan(lblMaKhuBan.getText());
        kb.setTenKhuBan(txtTenKhuBan.getText());
        kb.setGhiChu(txtGhiChu.getText());
        return kb;
    }

    /**
     * Hàm set form
     */
    public void setForm() {
        lblMaKhuBan.setText(entity.getMaKhuBan());
        txtTenKhuBan.setText(entity.getTenKhuBan());
        txtGhiChu.setText(entity.getGhiChu());
    }

    /**
     * Hàm clean form
     */
    public void clearForm() {
        lblMaKhuBan.setText(autoDao.AuToKhuBan());
        txtTenKhuBan.setText("");
        txtGhiChu.setText("");
        btnAdd.setEnabled(true);
    }

    public List<ModelKhuBan> getListReturn() {
        return this.listKB;
    }

    /**
     * Hàm bắt lỗi
     * @return
     */
    public boolean valideForm() {
        String makb = lblMaKhuBan.getText().trim();
        String tenkb = txtTenKhuBan.getText().trim();
        String ghichu = txtGhiChu.getText().trim();
        if (!FormValidator.isTextIsNotEmpty(makb) || !FormValidator.isTextIsNotEmpty(tenkb) || !FormValidator.isTextIsNotEmpty(ghichu)) {
            ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ thông tin!", ROptionDialog.WARNING, Color.red, Color.black);
            return false;
        } else if (!tenkb.contains(" ")) {
            ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ tên khu bàn!", ROptionDialog.WARNING, Color.red, Color.black);
            return false;
        }
        this.kb = getForm();
        return true;
    }

    public ModelKhuBan getKhuBan() {
        return this.kb;
    }
    
    /**
     * thêm khu bàn
     * @param entity
     */
    public void insertKB(ModelKhuBan entity) {
    	insertKBData(entity);
    	fillinsertKB(entity);
    }
    
    public void insertKBData(ModelKhuBan entity) {
    	daokb.insert(entity);
    }
    
    public void fillinsertKB(ModelKhuBan entity) {
    	listKB.add(entity);
    	fillToTable();
    }
    
    /**
     * xóa khu bàn
     * @param entity
     */
    public void deleteKB(ModelKhuBan entity) {
    	deleteKBData(entity);
    	filldeleteKB(entity);
    }
    
    public void deleteKBData(ModelKhuBan entity) {
    	daokb.delete(entity.getMaKhuBan());
      }
    
    public void filldeleteKB(ModelKhuBan entity) {
        listKB.remove(entity);
        fillToTable();
      }
    
    /**
     * sửa khu bàn
     * @param entity
     */
    public void updateKB(ModelKhuBan entity) {
    	updateKBData(entity);
    	fillupdate(entity);
    }
    
    public void updateKBData(ModelKhuBan entity) {
    	 daokb.update(entity, entity.getMaKhuBan());
    }
    
    public void fillupdate(ModelKhuBan entity) {
        for (int i = 0; i < listKB.size(); i++) {
            if (entity.getMaKhuBan() == listKB.get(i).getMaKhuBan()) {
                listKB.set(i, entity);
                break;
            }
        }
        fillToTable();
      }
    
    
    

    /**
     * Hàm lấy 1 dòng dữ liệu trên bảng
     * @param index
     * @return
     */
    private ModelKhuBan getFormRowTable(int index) {
        ModelKhuBan entity = new ModelKhuBan();
        String ma = String.valueOf(tblKhuBan.getValueAt(index, 0));
        for (int i = 0; i < listKB.size(); i++) {
            if (listKB.get(i).getMaKhuBan().equals(ma)) {
                entity = listKB.get(i);
                break;
            }
        }
        return entity;
    }
    
    /**
     * Tìm kiếm theo tên khu bàn
     * @param keyword
     * @return
     */
    public List<ModelKhuBan> timkiem(String keyword){
        List<ModelKhuBan> listFind = new ArrayList<>();
        	if(keyword.trim().length() > 0) {
        		listFind = daokb.timkiem(keyword);
        	}else {
        		listFind = daokb.selectAll();
        	}
        return listFind;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        txtTenKhuBan = new com.swing.custom.raven.RTextField.RTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhuBan = new com.wolvesres.swing.table.Table();
        txtFindKhuBan = new com.swing.custom.raven.RTextField.RTextField();
        rRoundPanel5 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        lblTitle = new javax.swing.JLabel();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        btnAdd = new com.swing.custom.raven.RButton.RButton();
        btnMoi = new com.swing.custom.raven.RButton.RButton();
        btnHuy = new com.swing.custom.raven.RButton.RButton();
        btnXoa = new com.swing.custom.raven.RButton.RButton();
        btnCapNhat = new com.swing.custom.raven.RButton.RButton();
        lblMaKhuBan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Ghi Chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        tblKhuBan.setAutoCreateRowSorter(true);
        tblKhuBan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblKhuBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKhuBanMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhuBan);

        txtFindKhuBan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindKhuBanKeyReleased(evt);
            }
        });

        rRoundPanel5.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("WolvesRes");
        rRoundPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, -1, -1));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel5.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 0, 150, 100));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Khu bàn");
        rRoundPanel5.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/addition.png"))); // NOI18N
        btnAdd.setText("Thêm khu bàn");
        btnAdd.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/clean.png"))); // NOI18N
        btnMoi.setText("Làm mới");
        btnMoi.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/isNotActive.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/control-system.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rRoundPanel2Layout = new javax.swing.GroupLayout(rRoundPanel2);
        rRoundPanel2.setLayout(rRoundPanel2Layout);
        rRoundPanel2Layout.setHorizontalGroup(
            rRoundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rRoundPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        rRoundPanel2Layout.setVerticalGroup(
            rRoundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rRoundPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rRoundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblMaKhuBan.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblMaKhuBan.setForeground(new java.awt.Color(102, 102, 102));
        lblMaKhuBan.setText("Mã Khu Bàn");

        javax.swing.GroupLayout rRoundPanel1Layout = new javax.swing.GroupLayout(rRoundPanel1);
        rRoundPanel1.setLayout(rRoundPanel1Layout);
        rRoundPanel1Layout.setHorizontalGroup(
            rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rRoundPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblMaKhuBan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTenKhuBan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rRoundPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtFindKhuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rRoundPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(rRoundPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rRoundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        rRoundPanel1Layout.setVerticalGroup(
            rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rRoundPanel1Layout.createSequentialGroup()
                .addComponent(rRoundPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(rRoundPanel1Layout.createSequentialGroup()
                        .addComponent(lblMaKhuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(txtTenKhuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rRoundPanel1Layout.createSequentialGroup()
                        .addComponent(txtFindKhuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rRoundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rRoundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 928, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rRoundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void tblKhuBanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhuBanMousePressed
        if (tblKhuBan.getSelectedRow() >= 0) {
            btnAdd.setEnabled(false);
            showDetail(tblKhuBan.getSelectedRow());
        }
    }//GEN-LAST:event_tblKhuBanMousePressed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (valideForm()) {
        	insertKB(getKhuBan());
        	fillToTable();
            clearForm();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtFindKhuBanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKhuBanKeyReleased
    	String keyword = txtFindKhuBan.getText().trim();
    	listKB = timkiem(keyword);
    	fillToTable();
    }//GEN-LAST:event_txtFindKhuBanKeyReleased

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (getFormRowTable(tblKhuBan.getSelectedRow()) != null) {
            if (valideForm()) {
                if (ROptionDialog.showConfirm(frame, "Xác nhận", "Xác nhận cập nhật thông tin khu bàn?", ROptionDialog.WARNING, Color.yellow, Color.black)) {
                	updateKB(getForm());
                	fillToTable();
                    ROptionDialog.showAlert(frame, "Thông báo", "Cập nhật thành công!", ROptionDialog.NOTIFICATIONS_ACTIVE, new Color(0, 199, 135), Color.black);
                }
            }
        } else {
            ROptionDialog.showAlert(frame, "Thông báo", "Vui lòng chọn dữ liệu từ bảng!", ROptionDialog.WARNING, Color.red, Color.black);
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (tblKhuBan.getSelectedRow() >= 0) {
            ModelKhuBan khuban = getFormRowTable(tblKhuBan.getSelectedRow());
            if (daokb.checkForeignKey(khuban.getMaKhuBan()) == null) {
                if (ROptionDialog.showConfirm(frame, "Xác nhận", "Xác nhận xóa khu bàn?", ROptionDialog.WARNING, Color.yellow, Color.black)) {
                	deleteKB(getFormRowTable(tblKhuBan.getSelectedRow()));
                	fillToTable();
                    ROptionDialog.showAlert(frame, "Thông báo", "Xóa thành công!", ROptionDialog.NOTIFICATIONS_ACTIVE, new Color(0, 199, 135), Color.black);
                    clearForm();
                }
            } else {
                ROptionDialog.showAlert(frame,"Thông báo", "Khu bàn này không thể xóa", ROptionDialog.PRIORITY_HIGHT, Color.red, Color.black);
            }
        } else {
            ROptionDialog.showAlert(frame,"Thông báo", "Vui lòng chọn dữ liệu từ bảng!", ROptionDialog.PRIORITY_HIGHT, Color.red, Color.black);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

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
            java.util.logging.Logger.getLogger(KhuBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhuBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhuBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhuBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KhuBan dialog = new KhuBan(new javax.swing.JFrame(), true);
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
    private com.swing.custom.raven.RButton.RButton btnAdd;
    private com.swing.custom.raven.RButton.RButton btnCapNhat;
    private com.swing.custom.raven.RButton.RButton btnHuy;
    private com.swing.custom.raven.RButton.RButton btnMoi;
    private com.swing.custom.raven.RButton.RButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMaKhuBan;
    private javax.swing.JLabel lblTitle;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel5;
    private com.wolvesres.swing.table.Table tblKhuBan;
    private com.swing.custom.raven.RTextField.RTextField txtFindKhuBan;
    private javax.swing.JTextArea txtGhiChu;
    private com.swing.custom.raven.RTextField.RTextField txtTenKhuBan;
    // End of variables declaration//GEN-END:variables
}
