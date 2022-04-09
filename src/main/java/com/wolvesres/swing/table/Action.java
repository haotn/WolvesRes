package com.wolvesres.swing.table;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Action extends javax.swing.JPanel {

    public Action(final ModelAction action) {
        initComponents();
        if (action.getIconDelete() != null) {
            btnDelete.setIcon(action.getIconDelete());
        }
        if (action.getIconUpdate() != null) {
            btnEdit.setIcon(action.getIconUpdate());
        }
        if (action.getToolTipTextBtnDelete() != null) {
            btnDelete.setToolTipText(action.getToolTipTextBtnDelete());
        }
        if (action.getToolTipTextBtnEdit() != null) {
            btnEdit.setToolTipText(action.getToolTipTextBtnEdit());
        }
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                action.getEvent().update(action.getEntity());
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                action.getEvent().delete(action.getEntity());
            }
        });
    }

    public void setToolTipTextBtnEdit(String text) {
        btnEdit.setToolTipText(text);
    }

    public void setToolTipTextBtnDelete(String text) {
        btnDelete.setToolTipText(text);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEdit = new com.swing.custom.raven.RButton.RButton();
        btnDelete = new com.swing.custom.raven.RButton.RButton();

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/edit.png"))); // NOI18N
        btnEdit.setPreferredSize(new java.awt.Dimension(32, 32));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/delete.png"))); // NOI18N
        btnDelete.setPreferredSize(new java.awt.Dimension(32, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdEditActionPerformed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.raven.RButton.RButton btnDelete;
    private com.swing.custom.raven.RButton.RButton btnEdit;
    // End of variables declaration//GEN-END:variables
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.setColor(new Color(230, 230, 230));
        grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
}
