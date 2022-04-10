package com.wolvesres.form.quenmatkhau;

import com.swing.custom.raven.RButton.RButton;
import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RPasswordField.RPasswordField;
import com.swing.custom.raven.RTextField.RTextField;
import com.wolvesres.dao.GhiNhoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.TaiKhoanDAO;

import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XIpAddress;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelTaiKhoan;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
/**
 * 
 * Dao: NhanVienDAO, TaiKhoanDAO
 * Lien quan: ModelNhanVien, ModeltaiKhoan
 * */
public class PanelQuenMK extends javax.swing.JPanel {

	JFrame frame;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	private FormValidator validator = new FormValidator();
	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	//
	public static final Pattern VALID_PASS_ADDRESS_REGEX = Pattern.compile("[a-z0-9_-]{6,16}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validatePass(String passStr) {
		Matcher matcher = VALID_PASS_ADDRESS_REGEX.matcher(passStr);
		return matcher.find();
	}

	TaiKhoanDAO tkdao = new TaiKhoanDAO();
	NhanVienDAO nvdao = new NhanVienDAO();
	List<ModelTaiKhoan> listTK = new ArrayList<>();
	List<ModelNhanVien> listNV = new ArrayList<>();
	private ActionListener eventGetCode;
	private ActionListener eventConfirm;
	public String user;
	public String email;
	RTextField txtUser;
	RTextField txtEmail;
	RPasswordField pwdMatKhauMoi;
	RPasswordField pwdXacNhanMatKhauMoi;
	private KeyListener keyEventGetCode;
	private KeyListener keyEventConfirm;
	RButton btnExit = new RButton();
	JLabel lblhienmatkhau = new JLabel();
	JLabel lblhienmatkhauchange = new JLabel();
	boolean show = false;
	boolean showchange = false;
	private ActionListener evenExit;

	public void setKeyEventGetCode(KeyListener keyEventGetCode) {
		this.keyEventGetCode = keyEventGetCode;
	}

	public void setKeyEventConfirm(KeyListener keyEventConfirm) {
		this.keyEventConfirm = keyEventConfirm;
	}

	/**
	 * Creates new form PanelLoginAndRegister
	 */
	public PanelQuenMK(JFrame frame) {
		initComponents();
		this.frame = frame;
		initForgetPass();
		initRestorePass();
		loadList();
		pnlQuenMatKhau.setVisible(true);
		pnlLayMatKhau.setVisible(false);
	}

	// tai list
	public void loadList() {
		listTK.addAll(tkdao.selectAll());
		listNV.addAll(nvdao.selectAll());
	}

	private void initForgetPass() {

		pnlQuenMatKhau.setLayout(new MigLayout("wrap", "push[center]push", "5[]25[]10[]10[]25[]push"));
		// exit
		btnExit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/closeBlack.png")));
		btnExit.setFont(new Font("sansserif", 1, 15));
		pnlQuenMatKhau.add(btnExit, "w 25!, h 25!, gapleft 440, wrap");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
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
		JLabel lblTitleQMK = new JLabel("QUÊN MẬT KHẨU");
		lblTitleQMK.setFont(new Font("sansserif", 1, 30));
		lblTitleQMK.setForeground(new Color(0, 0, 0));
		pnlQuenMatKhau.add(lblTitleQMK);
		txtUser = new RTextField();
		txtUser.setLabelText("User");
		pnlQuenMatKhau.add(txtUser, "w 60%");
		txtEmail = new RTextField();
		txtEmail.setLabelText("Email");
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keyEventGetCode.keyPressed(e);
			}
		});
		pnlQuenMatKhau.add(txtEmail, "w 60%");
		RButton cmd = new RButton();
		cmd.setBackground(new Color(0, 0, 0));
		cmd.setForeground(new Color(250, 250, 250));
		cmd.setText("Quên mật khẩu");//
		cmd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventGetCode.actionPerformed(e);
			}
		});
		pnlQuenMatKhau.add(cmd, "w 40%, h 40");
//        //
//        if (cmd.isSelected()) {
//            System.out.println("a");
//        }
	}

	public void initRestorePass() {
		pnlLayMatKhau.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]25[]25[]40[]push"));
		JLabel label = new JLabel("TẠO MẬT KHẨU MỚI");
		label.setFont(new Font("sansserif", 1, 30));
		label.setForeground(new Color(0, 0, 0));
		pnlLayMatKhau.add(label);
		pwdMatKhauMoi = new RPasswordField();
		pwdMatKhauMoi.setLabelText("Mật khẩu mới");
		pnlLayMatKhau.add(pwdMatKhauMoi, "w 60%");
		pwdXacNhanMatKhauMoi = new RPasswordField();
		pwdXacNhanMatKhauMoi.setLabelText("Xác nhận mật khẩu mới");
		pwdXacNhanMatKhauMoi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keyEventConfirm.keyPressed(e);
			}
		});
		pnlLayMatKhau.add(pwdXacNhanMatKhauMoi, "w 54%, split 2");
		// hien mk
		lblhienmatkhauchange.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/showPass.png")));
		pnlLayMatKhau.add(lblhienmatkhauchange, "w 32 , h 32, gaptop 15,wrap");

		lblhienmatkhauchange.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// System.out.println("vo");
				if (!showchange) {
					// System.out.println("show");
					lblhienmatkhauchange
							.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/hindPass.png")));
					pwdMatKhauMoi.setEchoChar((char) 0);
					pwdXacNhanMatKhauMoi.setEchoChar((char) 0);
					showchange = true;
				} else {
					// System.out.println("hind");
					lblhienmatkhauchange
							.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/showPass.png")));
					showchange = false;
					pwdMatKhauMoi.setEchoChar('*');
					pwdXacNhanMatKhauMoi.setEchoChar('*');
				}
			}
		});
		JButton cmdForget = new JButton(" ");
		RButton cmd = new RButton();
		cmd.setBackground(new Color(0, 0, 0));
		cmd.setForeground(new Color(250, 250, 250));
		cmd.setText("Xác nhận");//
		cmd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventConfirm.actionPerformed(e);
			}
		});
		pnlLayMatKhau.add(cmd, "w 40%, h 40");
	}
//kiem tra email cua tk (tk - email)

	public boolean checkEmailOfTK(String email, String taiKhoan) {// hợp lệ True >< false
		String TK = "";
		// lay tk cuar mail
		for (ModelNhanVien nv : listNV) {
			if (validator.isTextEqual(email, nv.getEmail() )) {
				TK = nv.getMaNV();
				break;
			}
		}
		// kiem tra hop le
		if (!validator.isTextEqual(TK, taiKhoan)) {
			return false;
		}
		return true;
	}

//	Kiem tra quen mat khau
	public boolean checkQuenMatKhau() {
		String username = txtUser.getText();
		String email = txtEmail.getText();
		// check trong
		if (!FormValidator.isTextIsNotEmpty(username) || !FormValidator.isTextIsNotEmpty(email)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Email hoặc User bị trống!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
			// check dinh dang mail
		} else if (!FormValidator.isValidEmail(email)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Email không hợp lệ!", ROptionDialog.WARNING, Color.red, Color.black);
			return false;
		}
		boolean existsuser = false, existsEmail = false;
		// kiem tra user ton tai
		for (ModelTaiKhoan tk : listTK) {
			if (validator.isTextEqual(txtUser.getText().trim(), tk.getTaiKhoan())) {
				existsuser = true;
				break;
			}
		}
		if (existsuser == false) {
			ROptionDialog.showAlert(frame, "Lỗi", "Tài Khoản hoặc Email không hợp lệ!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		}
		// kiem tra email ton tai
		for (ModelNhanVien nv : listNV) {
			if (validator.isTextEqual(txtEmail.getText().trim(), nv.getEmail() )) {
				existsEmail = true;
				break;
			}
		}
		// kiểm tra mail và tk là của 1 tk
		if (existsEmail == true) {
			boolean kt = checkEmailOfTK(txtEmail.getText().trim(), txtUser.getText().trim());
			if (kt == false) {
				ROptionDialog.showAlert(frame, "Lỗi", "Tài Khoản hoặc Email không hợp lệ!", ROptionDialog.WARNING,
						Color.red, Color.black);
				return false;
			}
		} else if (existsEmail == false) {
			ROptionDialog.showAlert(frame, "Lỗi", "Tài Khoản hoặc Email không hợp lệ!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		}
		email = txtEmail.getText().trim();
		user = txtUser.getText().trim();
		return true;
	}

	///
	// kiem tra mat khau moi
	public boolean checkNewPass() {
		if (!validatePass(String.valueOf(pwdMatKhauMoi.getPassword()).trim())) {
			ROptionDialog.showAlert(frame, "Lỗi", "Mật Khẩu phải từ 6 - 16 ký tự", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		if (!validator.isTextEqual(pwdMatKhauMoi.getText().trim(), String.valueOf(pwdXacNhanMatKhauMoi.getPassword()).trim())) {
			ROptionDialog.showAlert(frame, "Lỗi", "Mật khẩu mới không trùng khớp", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}

		return true;
	}

	//xử lý đổi password
	
	public void ChangPassword() {
		String username = txtUser.getText().trim();
		String newpassword = pwdMatKhauMoi.getText().trim();
		tkdao.UpdateChangePass(newpassword, username);
		GhiNhoDAO gndao = new GhiNhoDAO();
		gndao.delete(XIpAddress.getIPAddres());
	}

	// sự kiện nút lấy mã
	public void addEventGetCode(ActionListener event) {
		this.eventGetCode = event;
	}

	// sự kiện textconfirm
	public void addEventConfirm(ActionListener event) {
		this.eventConfirm = event;
	}

	// chuyen panel
	public void showLayMatKhau(boolean show) {
		if (show) {
			pnlLayMatKhau.setVisible(true);
			pnlQuenMatKhau.setVisible(false);
		} else {
			pnlQuenMatKhau.setVisible(true);
			pnlLayMatKhau.setVisible(false);
		}
	}

//	hàm hổ trợ miglayout
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLayeredPane1 = new javax.swing.JLayeredPane();
		pnlQuenMatKhau = new javax.swing.JPanel();
		pnlLayMatKhau = new javax.swing.JPanel();

		setBackground(new java.awt.Color(255, 255, 255));

		jLayeredPane1.setLayout(new java.awt.CardLayout());

		pnlQuenMatKhau.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout pnlQuenMatKhauLayout = new javax.swing.GroupLayout(pnlQuenMatKhau);
		pnlQuenMatKhau.setLayout(pnlQuenMatKhauLayout);
		pnlQuenMatKhauLayout.setHorizontalGroup(pnlQuenMatKhauLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		pnlQuenMatKhauLayout.setVerticalGroup(pnlQuenMatKhauLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

		jLayeredPane1.add(pnlQuenMatKhau, "card3");

		pnlLayMatKhau.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout pnlLayMatKhauLayout = new javax.swing.GroupLayout(pnlLayMatKhau);
		pnlLayMatKhau.setLayout(pnlLayMatKhauLayout);
		pnlLayMatKhauLayout.setHorizontalGroup(pnlLayMatKhauLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		pnlLayMatKhauLayout.setVerticalGroup(pnlLayMatKhauLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

		jLayeredPane1.add(pnlLayMatKhau, "card4");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLayeredPane1));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLayeredPane1));
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLayeredPane jLayeredPane1;
	private javax.swing.JPanel pnlLayMatKhau;
	private javax.swing.JPanel pnlQuenMatKhau;
	// End of variables declaration//GEN-END:variables
}
