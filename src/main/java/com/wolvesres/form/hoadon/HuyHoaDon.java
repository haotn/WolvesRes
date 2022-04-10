package com.wolvesres.form.hoadon;

import com.wolvesres.form.FormHoaDon;
import com.wolvesres.model.ModelHoaDon;
import com.wolvesres.swing.table.EventAction;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *Hiện danh sách hóa đơn có trạng thái là true
 *Liên Quan: 
 * @author FPT
 */
public class HuyHoaDon extends javax.swing.JDialog {

    JFrame frame;
    DefaultTableModel model;
    private List<ModelHoaDon> blacklist = new ArrayList<>();
    FormHoaDon fhd = new FormHoaDon(frame);
        EventAction<ModelHoaDon> eventAction = new EventAction<ModelHoaDon>() {
        @Override
        public void delete(ModelHoaDon entity) {

        }

        @Override
        public void update(ModelHoaDon entity) {

        }
    };
//    hàm tạo start
    public HuyHoaDon(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initTable();
        setLocationRelativeTo(null);
        fhd.getList();
        fillTable();
    }

    //tải dữ liệu vào list danh sách đen 
    public void loadblacktoList() {
        blacklist.clear();
        for (ModelHoaDon hd : fhd.getList()) {
            if (hd.isTrangThai() == false) {
                blacklist.add(hd);
            }
        }
    }

    //fill lên bảng
    public void fillTable() {
        loadblacktoList();
        model.setRowCount(0);
        for (ModelHoaDon hd : blacklist) {
            tblHoaDon.addRow(hd.toRowTable(eventAction));
        }

    }

//khởi tạo bảng
    private void initTable() {
        tblHoaDon.setOpaque(true);
        tblHoaDon.setBackground(new Color(255,255,255));
        tblHoaDon.setFillsViewportHeight(true);
        tblHoaDon.fixTable(jScrollPane1);
        tblHoaDon.setFont(new Font("SansSerif", 1, 12));
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Mã HD", "NV", "Date", "Bàn", "Voucher", "Thuế", "Tiền hàng"});
        tblHoaDon.setModel(model);
        tblHoaDon.setColumnAction(10);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new com.wolvesres.swing.table.Table();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        btnBack = new com.swing.custom.raven.RButton.RButton();
        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));
        rRoundPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(209, 220, 208), 2));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHoaDon.setAutoCreateRowSorter(true);
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblHoaDon);

        rRoundPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 119, 720, 630));

        rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
        btnBack.setText("Hủy");
        btnBack.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        rRoundPanel2.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 120, -1));

        rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 750, 720, 50));

        rRoundPanel3.setBackground(new java.awt.Color(71, 92, 77));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DS HỦY HÓA ĐƠN");
        rRoundPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, -1, 40));

        rImageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel3.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 100, 80));

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("WolvesRes");
        rRoundPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, -1, -1));

        rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 120));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnBackActionPerformed

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
            java.util.logging.Logger.getLogger(HuyHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HuyHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HuyHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HuyHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HuyHoaDon dialog = new HuyHoaDon(new javax.swing.JFrame(), true);
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
    private com.swing.custom.raven.RButton.RButton btnBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.wolvesres.swing.table.Table tblHoaDon;
    // End of variables declaration//GEN-END:variables
}
