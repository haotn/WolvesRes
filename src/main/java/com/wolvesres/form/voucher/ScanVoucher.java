package com.wolvesres.form.voucher;

import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.wolvesres.form.FormBanHang;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelVouCher;
import com.wolvesres.readqrcode.ReadQRCode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;

import java.awt.BorderLayout;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class ScanVoucher extends javax.swing.JDialog {

	Webcam webcam = Webcam.getDefault();

	public ScanVoucher(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		this.frame = (JFrame) parent;
		initComponents();
		setLocationRelativeTo(null);
		run();
	}

	private boolean isScan = true;
	JFrame frame;
	private FormBanHang formParent = new FormBanHang(frame);
	private Thread t = null;
	int count = 0;
	private String code = "";
	private boolean dispose = false;

	private void run() {
		isScan = true;
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open();
		runMythread();
		setBtnEnabel(false);
	}

	private void setBtnEnabel(boolean en) {
		btnApdung.setEnabled(en);
		btnQuetLai.setEnabled(en);
		Color color = null;
		if (en) {
			color = new Color(173, 173, 173);
		} else {
			color = Color.red;
		}
		btnQuetLai.setEffectColor(color);
		btnApdung.setEffectColor(color);
	}

	private void runMythread() {
		t = new Thread() {
			@Override
			public void run() {
				if (isScan) {
					while (true) {
						synchronized (this) {
							if (webcam.isOpen()) {

								WebcamPanel camView = new WebcamPanel(webcam);
								camView.setFPSDisplayed(true);
								camView.setDisplayDebugInfo(true);
								camView.setImageSizeDisplayed(true);
								camView.setMirrored(true);
								camView.repaint(100);
								pnlFrame.setLayout(new BorderLayout());
								pnlFrame.add(camView, BorderLayout.CENTER);
								try {

									String name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss")
											.format(new Date(HEIGHT, WIDTH, getX()));

									File fileImg = new File("tempReadQR/" + name + ".png");
									ImageIO.write(webcam.getImage(), "PNG", fileImg);
									String charset = "UTF-8";
									Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
									hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
									String findCode = ReadQRCode.readQRcode(fileImg.getAbsolutePath(), charset,
											hintMap);
									System.out.println("Data stored in the QR Code is: \n" + findCode);
									ModelVouCher voucher = formParent.getVoucherByMaVouCher(findCode);
									if (voucher.getMaVoucher() != null) {
										if (!voucher.getMaVoucher().equals("NOVOUCHER")) {
											if (voucher.isTrangThai()) {
												if (voucher.getSoLuong() > 0) {
													Date today = XDate.toDate(XDate.toString(new Date(), "dd-MM-yyyy"),
															"dd-MM-yyyy");
													Date ngayBatDau = XDate.toDate(voucher.getNgayBatDau(),
															"dd-MM-yyyy");
													Date ngayKetThuc = XDate.toDate(voucher.getNgayKetThuc(),
															"dd-MM-yyyy");
													if (ngayBatDau.before(today) || ngayBatDau.equals(today)) {
														if (ngayKetThuc.equals(today) || ngayKetThuc.after(today)) {
															setCode(voucher.getMaVoucher());
															lblMaVoucher.setText(voucher.getMaVoucher());
															lblPhanTramGiamGia.setText(
																	String.valueOf(voucher.getGiamGia()) + "%");
															setBtnEnabel(true);
															isScan = false;
															webcam.close();

															t.join();
															break;
														} else {
															lblMaVoucher.setText(voucher.getMaVoucher());
															lblPhanTramGiamGia.setText("Lỗi hạn");
														}
													} else {
														lblMaVoucher.setText(voucher.getMaVoucher());
														lblPhanTramGiamGia.setText("Lỗi hạn");
													}
												} else {
													lblMaVoucher.setText(voucher.getMaVoucher());
													lblPhanTramGiamGia.setText("Hết lượt");
												}
											} else {
												lblMaVoucher.setText(voucher.getMaVoucher());
												lblPhanTramGiamGia.setText("Không khả dụng");
											}
										}
									}
								} catch (IOException | InterruptedException e) {
									e.printStackTrace();
								} catch (NotFoundException ex) {
									ex.printStackTrace();
								} catch (IllegalMonitorStateException e) {
									// TODO: handle exception
								} catch (IllegalStateException e) {
									// TODO: handle exception
								}
							}
						}
					}
				}
			}
		};
		t.start();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDispose() {
		return dispose;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnDong = new com.swing.custom.raven.RButton.RButton();
		btnQuetLai = new com.swing.custom.raven.RButton.RButton();
		btnApdung = new com.swing.custom.raven.RButton.RButton();
		pnlFrame = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		lblMaVoucher = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		lblPhanTramGiamGia = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setMaximumSize(new java.awt.Dimension(500, 500));
		setMinimumSize(new java.awt.Dimension(500, 500));
		getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		rRoundPanel1.setBackground(new java.awt.Color(255, 255, 255));
		rRoundPanel1.setMaximumSize(new java.awt.Dimension(500, 500));
		rRoundPanel1.setMinimumSize(new java.awt.Dimension(500, 500));
		rRoundPanel1.setPreferredSize(new java.awt.Dimension(500, 500));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		btnDong.setBackground(new java.awt.Color(102, 102, 102));
		btnDong.setForeground(new java.awt.Color(255, 255, 255));
		btnDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		btnDong.setText("Đóng");
		btnDong.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnDong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDongActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnDong, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 100, 40));

		btnQuetLai.setBackground(new java.awt.Color(102, 102, 102));
		btnQuetLai.setForeground(new java.awt.Color(255, 255, 255));
		btnQuetLai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/scan.png"))); // NOI18N
		btnQuetLai.setText("Quét lại");
		btnQuetLai.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnQuetLai.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnQuetLaiActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnQuetLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 130, 40));

		btnApdung.setBackground(new java.awt.Color(102, 102, 102));
		btnApdung.setForeground(new java.awt.Color(255, 255, 255));
		btnApdung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/confirm.png"))); // NOI18N
		btnApdung.setText("Áp dụng");
		btnApdung.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnApdung.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnApdungActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnApdung, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 130, 40));

		javax.swing.GroupLayout pnlFrameLayout = new javax.swing.GroupLayout(pnlFrame);
		pnlFrame.setLayout(pnlFrameLayout);
		pnlFrameLayout.setHorizontalGroup(pnlFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 500, Short.MAX_VALUE));
		pnlFrameLayout.setVerticalGroup(pnlFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 350, Short.MAX_VALUE));

		rRoundPanel1.add(pnlFrame, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 350));

		jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		jLabel1.setText("Mã Voucher:");
		rRoundPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, -1, 20));

		lblMaVoucher.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		lblMaVoucher.setForeground(new java.awt.Color(0, 255, 0));
		rRoundPanel1.add(lblMaVoucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 170, 20));

		jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		jLabel3.setText("Phần trăm giảm giá:");
		rRoundPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, -1, -1));

		lblPhanTramGiamGia.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		lblPhanTramGiamGia.setForeground(new java.awt.Color(0, 255, 0));
		rRoundPanel1.add(lblPhanTramGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 100, 20));

		getContentPane().add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 500));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btnApdungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnApdungActionPerformed
		dispose();
		webcam.close();
	}// GEN-LAST:event_btnApdungActionPerformed

	private void btnQuetLaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnQuetLaiActionPerformed
		run();
	}// GEN-LAST:event_btnQuetLaiActionPerformed

	private void btnDongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDongActionPerformed
		isScan = false;
		dispose = true;
		webcam.close();
//		webSource.release();
		dispose();
	}// GEN-LAST:event_btnDongActionPerformed

	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ScanVoucher dialog = new ScanVoucher(new javax.swing.JFrame(), true);
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
	private com.swing.custom.raven.RButton.RButton btnApdung;
	private com.swing.custom.raven.RButton.RButton btnDong;
	private com.swing.custom.raven.RButton.RButton btnQuetLai;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel lblMaVoucher;
	private javax.swing.JLabel lblPhanTramGiamGia;
	private javax.swing.JPanel pnlFrame;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	// End of variables declaration//GEN-END:variables
}
