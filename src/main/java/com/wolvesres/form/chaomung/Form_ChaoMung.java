package com.wolvesres.form.chaomung;

import com.wolvesres.form.FormDangNhap;
import com.wolvesres.helper.AnimationShowWindow;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.Timer;

public class Form_ChaoMung extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	JDialog dialog;

	public Form_ChaoMung(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		this.dialog = this;
		AnimationShowWindow.showDialog(dialog);
		setLocationRelativeTo(null);
		lblLogo.setVisible(false);
		chaoMung();
	}

	// Timer
	Timer taiChaoMung;
	int phanTram = 0;

	/**
	 * Welcome method
	 */
	private void chaoMung() {
		taiChaoMung = new Timer(16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phanTram++;
				if (phanTram == 80) {
					rBLoading2.setVisible(false);
					lbl_Background.setBackground(new Color(0, 0, 0));
					lblLogo.setVisible(true);
					taiChaoMung.setDelay(50);
				} else if (phanTram == 100) {
					taiChaoMung.stop();
					AnimationShowWindow.closeDialog(dialog);
					dispose();
					new FormDangNhap().setVisible(true);
				}
			}
		});
		taiChaoMung.start();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		pnl_ChaoMung = new javax.swing.JPanel();
		rBLoading2 = new com.wolvesres.component.RBLoading();
		lblLogo = new javax.swing.JLabel();
		lbl_Background = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setMaximumSize(new java.awt.Dimension(970, 520));
		setMinimumSize(new java.awt.Dimension(970, 520));
		setUndecorated(true);
		setPreferredSize(new java.awt.Dimension(970, 520));

		pnl_ChaoMung.setBackground(new java.awt.Color(21, 21, 21));
		pnl_ChaoMung.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
		pnl_ChaoMung.add(rBLoading2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, -1, -1));

		lblLogo.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		pnl_ChaoMung.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 350, 520));

		lbl_Background.setBackground(new java.awt.Color(255, 255, 255));
		lbl_Background.setOpaque(true);
		pnl_ChaoMung.add(lbl_Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 520));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnl_ChaoMung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addComponent(pnl_ChaoMung,
						javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Form_ChaoMung.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Form_ChaoMung.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Form_ChaoMung.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Form_ChaoMung.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Form_ChaoMung dialog = new Form_ChaoMung(new javax.swing.JFrame(), true);
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
	private javax.swing.JLabel lblLogo;
	private javax.swing.JLabel lbl_Background;
	private javax.swing.JPanel pnl_ChaoMung;
	private com.wolvesres.component.RBLoading rBLoading2;
	// End of variables declaration//GEN-END:variables
}
