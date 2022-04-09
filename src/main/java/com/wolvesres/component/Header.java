package com.wolvesres.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;

public class Header extends javax.swing.JPanel {

    public JFrame frame;

    public Header() {
        initComponents();
        setOpaque(false);
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    private ActionListener event;

    public void setEvent(ActionListener event) {
        this.event = event;
    }
    private MouseMotionAdapter mouseDrag;
    private MouseAdapter mousePress;

    public void setMouseDrag(MouseMotionAdapter mouseDrag) {
        this.mouseDrag = mouseDrag;
    }

    public void setMousePress(MouseAdapter mousePress) {
        this.mousePress = mousePress;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        brnDong = new com.swing.custom.raven.RButton.RButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1400, 50));

        rRoundPanel1.setBackground(new java.awt.Color(0, 0, 0));
        rRoundPanel1.setPreferredSize(new java.awt.Dimension(1400, 50));
        rRoundPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                rRoundPanel1MouseDragged(evt);
            }
        });
        rRoundPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                rRoundPanel1MousePressed(evt);
            }
        });
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        brnDong.setBackground(new java.awt.Color(0, 199, 135));
        brnDong.setForeground(new java.awt.Color(255, 255, 255));
        brnDong.setText("X");
        brnDong.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        brnDong.setPreferredSize(new java.awt.Dimension(30, 30));
        brnDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnDongActionPerformed(evt);
            }
        });
        rRoundPanel1.add(brnDong, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 10, 40, 30));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("WolvesRes");
        rRoundPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(608, 13, 234, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Version 0.1");
        rRoundPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rRoundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void brnDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnDongActionPerformed
        event.actionPerformed(evt);
    }//GEN-LAST:event_brnDongActionPerformed

    private void rRoundPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rRoundPanel1MouseDragged
        mouseDrag.mouseDragged(evt);
    }//GEN-LAST:event_rRoundPanel1MouseDragged

    private void rRoundPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rRoundPanel1MousePressed
        mousePress.mousePressed(evt);
    }//GEN-LAST:event_rRoundPanel1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static com.swing.custom.raven.RButton.RButton brnDong;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        Area area = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        g2.fill(area);
        g2.dispose();
        super.paint(grphcs);
    }
}
