package com.wolvesres.form.doimatkhau;

import com.swing.custom.raven.RButton.RButton;
import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RPasswordField.RPasswordField;
import com.swing.custom.raven.RTextField.RTextField;
import com.wolvesres.dao.GhiNhoDAO;
import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XIpAddress;
import com.wolvesres.model.ModelTaiKhoan;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
/**
 * update(password)
 * Lien quan: ModelTaiKhoan
 * */
public class PanelDoiMK extends javax.swing.JPanel {

	private ActionListener eventIdentify;
	private ActionListener eventConfirm;
	private KeyAdapter eventPWDPass;
	private KeyAdapter eventPWDConfirmPass;

	public void setEventPWDPass(KeyAdapter eventPWDPass) {
		this.eventPWDPass = eventPWDPass;
	}

	public void setEventPWDConfirmPass(KeyAdapter eventPWDConfirmPass) {
		this.eventPWDConfirmPass = eventPWDConfirmPass;
	}

	/**
	 * Creates new form PanelLoginAndRegister
	 */
	JFrame frame;
	ModelTaiKhoan account = new ModelTaiKhoan();

	// FormDoiMK dmk = new FormDoiMK();
	public PanelDoiMK(JFrame frame) {
		initComponents();
		initIdentify();
		this.frame = frame;
		initChangePass();
		pnlIdentify.setVisible(true);
		pnlChangePass.setVisible(false);
	}

	/**
	 * Tao component cho panel doi mat khau
	 */
	JLabel lblTitleChangePass = new JLabel("ĐỔI MẬT KHẨU");
	RTextField txtUser = new RTextField();
	RPasswordField pwdPass = new RPasswordField();
	RButton btnIdentify = new RButton();
	RButton btnExit = new RButton();
	JLabel lblhienmatkhau = new JLabel();
	JLabel lblhienmatkhauchange = new JLabel();
	boolean show = false;
	boolean showchange = false;
	private ActionListener evenExit;

	/**
	 * Ham tao panel doi mat khau
	 */
	private void initIdentify() {
		pnlIdentify.setLayout(new MigLayout("", "[]", "5[]25[]10[]10[]40[]push"));
		// exit
		btnExit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/closeBlack.png")));
		btnExit.setFont(new Font("sansserif", 1, 15));
		// btnExit.setBackground(new Color(51, 51, 51));
		// btnExit.setForeground(new Color(255, 255, 255));
		pnlIdentify.add(btnExit, "w 25!, h 25!, gapleft 440, wrap");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evenExit.actionPerformed(e);
			}
		});
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/closeRed.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/closeBlack.png")));
			}
		});
		//
		lblTitleChangePass.setFont(new Font("sansserif", 1, 30));
		lblTitleChangePass.setForeground(new Color(0, 0, 0));
		pnlIdentify.add(lblTitleChangePass, " gapleft 125,wrap");
		txtUser.setLabelText("Tên tài khoản");
		pnlIdentify.add(txtUser, "w 70%, gapleft 55, wrap");
		// pass
		pwdPass.setLabelText("Mật khẩu");
		pwdPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				eventPWDPass.keyPressed(e);
			}

		});

		pnlIdentify.add(pwdPass, "w 63%, gapleft 55, split 2");
		// hien mk
		lblhienmatkhau.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/showPass.png")));
		pnlIdentify.add(lblhienmatkhau, "w 32 , h 32, gaptop 15,wrap");

		lblhienmatkhau.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// System.out.println("vo");
				if (!show) {
					// System.out.println("show");
					lblhienmatkhau.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/hindPass.png")));
					pwdPass.setEchoChar((char) 0);
					show = true;
				} else {
					// System.out.println("hind");
					lblhienmatkhau.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/showPass.png")));
					show = false;
					pwdPass.setEchoChar('*');
				}
			}
		});

		btnIdentify.setBackground(new Color(0, 0, 0));
		btnIdentify.setForeground(new Color(250, 250, 250));
		btnIdentify.setFont(new Font("SansSerif", 1, 15));
		btnIdentify.setText("Xác nhận");//
		btnIdentify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventIdentify.actionPerformed(e);
			}
		});
		pnlIdentify.add(btnIdentify, "w 40%, h 40,, gapleft 130, wrap");
	}

//	kiemer tra form doi mat khau
	public boolean identify(ModelTaiKhoan accountLogin) {
		String user = txtUser.getText().trim();
		String pass = String.valueOf(pwdPass.getPassword()).trim();
		if (user.length() == 0 || pass.length() == 0) {
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ thông tin!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (!user.equals(accountLogin.getTaiKhoan()) || !pass.equals(accountLogin.getMatKhau())) {
			ROptionDialog.showAlert(frame, "Lỗi", "Sai tài khoản hoặc mật khẩu!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		this.account = accountLogin;
		return true;
	}

	/**
	 * Tao component cho panel tao mat khau moi
	 */
	JLabel lblTitleCreateNewPass = new JLabel("TẠO MẬT KHẨU MỚI");
	RPasswordField pwdNewPass = new RPasswordField();
	RPasswordField pwdConfirmNewPass = new RPasswordField();
	RButton btnConfirm = new RButton();

	/**
	 * Ham tao panel doi mat khau
	 */
	public void initChangePass() {
		pnlChangePass.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]25[]25[]40[]push"));

		lblTitleCreateNewPass.setFont(new Font("sansserif", 1, 30));
		lblTitleCreateNewPass.setForeground(new Color(0, 0, 0));
		pnlChangePass.add(lblTitleCreateNewPass);

		pwdNewPass.setLabelText("Mật khẩu mới");
		pnlChangePass.add(pwdNewPass, "w 60%");

		pwdConfirmNewPass.setLabelText("Xác nhận mật khẩu mới");
		pnlChangePass.add(pwdConfirmNewPass, "w 54%, split 2");
		// hien mk
		lblhienmatkhauchange.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/showPass.png")));
		pnlChangePass.add(lblhienmatkhauchange, "w 32 , h 32, gaptop 15,wrap");

		lblhienmatkhauchange.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// System.out.println("vo");
				if (!showchange) {
					// System.out.println("show");
					lblhienmatkhauchange
							.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/hindPass.png")));
					pwdNewPass.setEchoChar((char) 0);
					pwdConfirmNewPass.setEchoChar((char) 0);
					showchange = true;
				} else {
					// System.out.println("hind");
					lblhienmatkhauchange
							.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/showPass.png")));
					showchange = false;
					pwdNewPass.setEchoChar('*');
					pwdConfirmNewPass.setEchoChar('*');
				}
			}
		});
		pwdConfirmNewPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				eventPWDConfirmPass.keyPressed(e);
			}

		});

		btnConfirm.setBackground(new Color(0, 0, 0));
		btnConfirm.setForeground(new Color(250, 250, 250));
		btnConfirm.setText("Xác nhận");//
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventConfirm.actionPerformed(e);
			}
		});
		pnlChangePass.add(btnConfirm, "w 40%, h 40");
	}

	public boolean newPassIsValid() {
		String newPass = String.valueOf(pwdNewPass.getPassword()).trim();
		String confirmNewPas = String.valueOf(pwdConfirmNewPass.getPassword()).trim();
		if (!FormValidator.isTextIsNotEmpty(newPass) || !FormValidator.isTextIsNotEmpty(confirmNewPas)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ thông tin!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (FormValidator.isLessThan(newPass.length(), 6)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Độ dài mật khẩu quá ngắn!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (!FormValidator.isTextEqual(newPass, confirmNewPas)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Xác nhận mật khẩu không khớp!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		return true;
	}

	public void changePass() {
		if (newPassIsValid()) {
			String pass = String.valueOf(pwdNewPass.getPassword()).trim();
			TaiKhoanDAO dao = new TaiKhoanDAO();
			this.account.update(pass);
			success = true;
			ROptionDialog.showAlert(frame, "Thông báo", "Đổi mật khẩu thành công!", ROptionDialog.NOTIFICATIONS_ACTIVE,
					new Color(0, 199, 135), Color.black);
		}
	}

	public static boolean success = false;

	/**
	 * Dat su kien de hien thi panel tao mat khau moi
	 *
	 * @param event
	 */
	public void addEventIndentify(ActionListener event) {
		this.eventIdentify = event;
	}

	/**
	 * Dat su kien de kiem tra va tao mat khau moi
	 *
	 * @param event
	 */
	public void addEventConfirm(ActionListener event) {
		this.eventConfirm = event;
	}

	/**
	 * Set show panel tao mat khau
	 *
	 * @param show
	 */
	public void showChangePass(boolean show) {
		if (show) {
			pnlChangePass.setVisible(true);
			pnlIdentify.setVisible(false);
		} else {
			pnlIdentify.setVisible(true);
			pnlChangePass.setVisible(false);
		}
	}

	public void setEvenExit(ActionListener evenExit) {
		this.evenExit = evenExit;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLayeredPane1 = new javax.swing.JLayeredPane();
		pnlIdentify = new javax.swing.JPanel();
		pnlChangePass = new javax.swing.JPanel();

		setBackground(new java.awt.Color(255, 255, 255));

		jLayeredPane1.setLayout(new java.awt.CardLayout());

		pnlIdentify.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout pnlIdentifyLayout = new javax.swing.GroupLayout(pnlIdentify);
		pnlIdentify.setLayout(pnlIdentifyLayout);
		pnlIdentifyLayout.setHorizontalGroup(pnlIdentifyLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		pnlIdentifyLayout.setVerticalGroup(pnlIdentifyLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

		jLayeredPane1.add(pnlIdentify, "card3");

		pnlChangePass.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout pnlChangePassLayout = new javax.swing.GroupLayout(pnlChangePass);
		pnlChangePass.setLayout(pnlChangePassLayout);
		pnlChangePassLayout.setHorizontalGroup(pnlChangePassLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		pnlChangePassLayout.setVerticalGroup(pnlChangePassLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

		jLayeredPane1.add(pnlChangePass, "card4");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLayeredPane1));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLayeredPane1));
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLayeredPane jLayeredPane1;
	private javax.swing.JPanel pnlChangePass;
	private javax.swing.JPanel pnlIdentify;
	// End of variables declaration//GEN-END:variables
}
