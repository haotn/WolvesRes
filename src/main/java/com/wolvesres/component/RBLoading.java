package com.wolvesres.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class RBLoading extends javax.swing.JComponent {

    public RBLoading() {
        initComponents();
        icon = new ImageIcon("/com/wolvesres/icon/jumpLoading.gif");
    }
    private Icon icon;

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImage = new javax.swing.JLabel();

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/jumpLoading.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImage)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImage)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(255, 255, 255, 200));
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblImage;
    // End of variables declaration//GEN-END:variables
}
