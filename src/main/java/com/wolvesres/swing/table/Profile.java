package com.wolvesres.swing.table;

import javax.swing.JLabel;

public class Profile extends javax.swing.JPanel {

    public Profile(ModelProfile data) {
        initComponents();
        pic.setIcon(data.getIcon());
        lbName.setText(data.getName());
    }

    public JLabel getLabelText() {
        return this.lbName;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbName = new javax.swing.JLabel();
        pic = new com.swing.custom.raven.RImageAvatar.RImageAvatar();

        lbName.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lbName.setForeground(new java.awt.Color(255, 255, 255));
        lbName.setText("Name");

        pic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/a.jpg"))); // NOI18N
        pic.setPreferredSize(new java.awt.Dimension(32, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(pic, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pic, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbName;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar pic;
    // End of variables declaration//GEN-END:variables
}
