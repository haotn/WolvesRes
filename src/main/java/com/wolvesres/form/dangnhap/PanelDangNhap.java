package com.wolvesres.form.dangnhap;

import com.swing.custom.raven.RButton.RButton;
import com.swing.custom.raven.RCheckBox.RCheckBox;
import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RImageAvatar.RImageAvatar;
import com.swing.custom.raven.RPasswordField.RPasswordField;
import com.swing.custom.raven.RTextField.RTextField;
import com.wolvesres.component.Menu;
import com.wolvesres.dao.GhiNhoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.helper.AnimationShowWindow;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XImage;
import com.wolvesres.helper.XIpAddress;
import com.wolvesres.model.ModelGhiNho;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelTaiKhoan;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

/**
 *Hiển thị lên form bằng miglayout, xử lý đăng nhập.
 *Lien quan: ModelGhiNho, ModelNhanVien
 *Dao: TaiKhoanDAO, NhanVienDAO
 * @author 
 */
public class PanelDangNhap extends javax.swing.JPanel {

	/**
	 * Creates new form PanelLoginAndRegister
	 */
	JFrame frame;

	public PanelDangNhap(JFrame frame) {
		initComponents();
		this.frame = frame;
		initLogin();
		initQuenMK();
		login.setVisible(true);
		register.setVisible(false);
		loadList();
		checkRememberme();
	}
 
//Chuyen miglayout sang quên mật khẩu
	private void initQuenMK() {
		register.setLayout(new MigLayout("", "push[center]push", "push[]25[]10[]10[]25[]push"));
		JLabel label = new JLabel("Đăng nhập thành công!");
		label.setFont(new Font("sansserif", 1, 30));
		label.setForeground(new Color(0, 0, 0));
		register.add(label);
	}
//	tạo form bằng code java
	RTextField txtUser = new RTextField();
	JLabel label = new JLabel("ĐĂNG NHẬP");
	RPasswordField pwdPass = new RPasswordField();
	JButton cmdForget = new JButton(" ");
	RButton cmd = new RButton();
	ActionListener event;
	JLabel lblQMK = new JLabel();
	MouseAdapter eventlblQMK;
	KeyAdapter keyEvent;
	RButton btnExit = new RButton();
	RCheckBox chkrememberme = new RCheckBox();
	JLabel lblhienmatkhau = new JLabel();
	boolean show = false;
	JLabel lblTKKhac = new JLabel();
	RImageAvatar avartar = new RImageAvatar();
	JLabel a = new JLabel();

	public void setEventCLK(MouseAdapter mouse) {
		this.eventlblQMK = mouse;
	}

	private void initLogin() {
		login.setLayout(new MigLayout("", "[]", "5[]10[][5::200]25[]10[]25[]25[]10[]100"));
		btnExit.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/closeBlack.png")));
		btnExit.setFont(new Font("sansserif", 1, 15));
		login.add(btnExit, "w 25!, h 25!, gapleft 500, wrap");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnimationShowWindow.closeFrame(frame);
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
		// Dang nhap
		label.setFont(new Font("sansserif", 1, 30));
		label.setForeground(new Color(0, 0, 0));
		login.add(label, "gapleft 220, wrap");
		//
		avartar.setVisible(false);
		avartar.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/userRemember.png")));
		login.add(avartar, "w 150!, h 150! ,cell 0 2,gapleft 235, wrap");
		// user
		txtUser.setLabelText("Tài Khoản");
		this.txtUser = txtUser;
		login.add(txtUser, "w 60% , gapleft 135 , wrap");
		// password
		pwdPass.setLabelText("Mật khẩu");
		pwdPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keyEvent.keyPressed(e);
			}

		});
		// this.pwdpass = pwdPass;
		login.add(pwdPass, "w 53%  , gapleft 135, split 2");
		// hien mk
		lblhienmatkhau.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/showPass.png")));
		login.add(lblhienmatkhau, "w 32 , h 32, gaptop 15,wrap");

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

		// forget password
		lblQMK.setText("Quên mật khẩu?");
		lblQMK.setFont(new Font("sansserif", 1, 13));
		lblQMK.setForeground(new Color(0, 0, 0));
		login.add(lblQMK, "gapleft 180, split 2");
		// checkbox rememberme?
		chkrememberme.setText("Ghi nhớ mật khẩu?");
		login.add(chkrememberme, "gapleft 40, wrap");
		// button signup
		cmd.setBackground(new Color(0, 0, 0));
		cmd.setForeground(new Color(250, 250, 250));
		cmd.setFont(new Font("SansSerif", 1, 15));
		cmd.setText("Đăng nhập");//
		login.add(cmd, "w 50%, h 40, gapleft 180, wrap");
		cmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				event.actionPerformed(e);
			}
		});
		lblQMK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblQMK.setText("<html><i>Quên mật khẩu?</i></html>");
				lblQMK.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblQMK.setText("Quên mật khẩu?");
				lblQMK.setForeground(new Color(0, 0, 0));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				eventlblQMK.mousePressed(e);
			}

		});
		//
		lblTKKhac.setVisible(false);
		lblTKKhac.setText("Đăng nhập với tài khoản Khác?");
		lblTKKhac.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblTKKhac.setText("<html><i>Đăng nhập với tài khoản khác?</i></html>");
				lblTKKhac.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblTKKhac.setText("Đăng nhập với tài khoản khác?");
				lblTKKhac.setForeground(new Color(0, 0, 0));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				gndao.delete(XIpAddress.getIPAddres());
				avartar.setVisible(false);
				cmd.setText("Đăng nhập");
				cmd.setIcon(null);
				lblTKKhac.setVisible(false);
				txtUser.setVisible(true);
				pwdPass.setVisible(true);
				lblhienmatkhau.setVisible(true);
				lblQMK.setVisible(true);
				chkrememberme.setVisible(true);
			}

		});
		login.add(lblTKKhac, "gapleft 230");
	}

	//
	List<ModelTaiKhoan> listTK = new ArrayList<ModelTaiKhoan>();
	TaiKhoanDAO tkdao = new TaiKhoanDAO();

	// load
	public void loadList() {
		listTK.addAll(tkdao.selectAll());
		listNV.addAll(nvdao.selectAll());
	}

	//kiểm tra login
	public boolean checkLogin() {
		loadList();
		String username = "";
		String pass = "";
		if (checkRememberme()) {
			ModelGhiNho ghinhocheck = null;
			ghinhocheck = gndao.selectById(XIpAddress.getIPAddres());
			username = ghinhocheck.getTaiKhoan();
			pass = ghinhocheck.getPassWord();
		} else {
			username = txtUser.getText().trim();
			pass = pwdPass.getText().trim();
		}

		if (!FormValidator.isTextIsNotEmpty(username) || !FormValidator.isTextIsNotEmpty(pass)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Tài Khoản hoặc Mật Khẩu bị trống!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		ModelTaiKhoan user = tkdao.checkLogin(username, pass);
		if (user == null) {
			ROptionDialog.showAlert(frame, "Lỗi", "Tài Khoản hoặc mật khẩu không hợp lệ!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		}
		if (!user.isTrangThai()) {
			ROptionDialog.showAlert(frame, "Lỗi", "Tài khoản đã bị vô hiệu hóa!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		for (int i = 0; i < listNV.size(); i++) {
			if (FormValidator.isTextEqual(user.getNhanVien().getMaNV(), listNV.get(i).getMaNV())) {
				Auth.user = listNV.get(i);
				break;
			}
		}
		// System.out.println(Auth.user.getHoTen());
		return true;
	}

	//
	private Menu nMenu;
	List<ModelNhanVien> listNV = new ArrayList<ModelNhanVien>();
	NhanVienDAO nvdao = new NhanVienDAO();
	public static String ten = "";
	public String user = txtUser.getText().trim();

	public void addEvent(ActionListener event) {
		this.event = event;
	}

	public void setKeyEvent(KeyAdapter keyEvent) {
		this.keyEvent = keyEvent;
	}

	public void showRegister(boolean show) {
		if (show) {
			register.setVisible(false);
			login.setVisible(true);

		} else {
			register.setVisible(true);
			login.setVisible(false);
		}
	}

	public String getUser() {
		return this.txtUser.getText();
	}

	List<ModelGhiNho> listGhiNho = new ArrayList<ModelGhiNho>();
	GhiNhoDAO gndao = new GhiNhoDAO();
//kiểm tra ghi nhớ đăng nhặp
	public void rememberme() {
		if (!checkRememberme()) {
			if (chkrememberme.isSelected()) {
				ModelGhiNho ghinho = null;
				ghinho = gndao.selectById(XIpAddress.getIPAddres());
				ModelGhiNho ghinhopresent = new ModelGhiNho();
				ghinhopresent.setIp(XIpAddress.getIPAddres());
				ghinhopresent.setTaiKhoan(txtUser.getText().trim());
				ghinhopresent.setPassWord(String.valueOf(pwdPass.getPassword()).trim());
				if (ghinho == null) {
					ghinhopresent.insert();
				} else {
					if (!FormValidator.isTextEqual(ghinho.getTaiKhoan(), txtUser.getText().trim())) {
						gndao.delete(XIpAddress.getIPAddres());
						ghinhopresent.insert();
					}
				}
			} else {
				ModelGhiNho ghinho = null;
				ghinho = gndao.selectById(XIpAddress.getIPAddres());
				if (ghinho != null) {
					gndao.delete(XIpAddress.getIPAddres());
				}
			}
		}

	}

	private String getNameUser(String hoten) {
		String ten = "";
		ten = Auth.user.getHoTen().substring(Auth.user.getHoTen().lastIndexOf(" "), Auth.user.getHoTen().length());
		return ten;
	}

//	lấy user rememberme
	private String getTenUserRemember(String user) {
		String tenOfUser = "";
		ModelNhanVien nv = nvdao.selectById(user);
		tenOfUser = nv.getHoTen().substring(nv.getHoTen().lastIndexOf(" "), nv.getHoTen().length());
		return tenOfUser;
	}

	public boolean checkRememberme() {
		ModelGhiNho ghinhocheck = null;
		ghinhocheck = gndao.selectById(XIpAddress.getIPAddres());
		if (ghinhocheck != null) {
//            txtUser.setText(ghinhocheck.getTaiKhoan());
//            pwdPass.setText(ghinhocheck.getPassWord());
//            chkrememberme.setSelected(true);
			txtUser.setVisible(false);
			pwdPass.setVisible(false);
			lblQMK.setVisible(false);
			lblhienmatkhau.setVisible(false);
			chkrememberme.setVisible(false);
			lblTKKhac.setVisible(true);
			// avartar.setVisible(false);
			avartar.setVisible(true);
			ModelNhanVien nv = nvdao.selectById(ghinhocheck.getTaiKhoan());
			if (!FormValidator.isTextIsNotEmpty(nv.getPathHinhAnh())) {
				avartar.setIcon(XImage.readImageNhanVien(nv.getPathHinhAnh()));
			} else {
				avartar.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/userRemember.png")));
			}
			cmd.setText("Tiếp tục với tư cách " + getTenUserRemember(ghinhocheck.getTaiKhoan()));
			cmd.setHorizontalTextPosition(JButton.RIGHT);
			cmd.setIcon(new ImageIcon(getClass().getResource("/com/wolvesres/icon/into.png")));
			cmd.setHorizontalTextPosition(SwingConstants.LEFT);
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLayeredPane1 = new javax.swing.JLayeredPane();
		register = new javax.swing.JPanel();
		login = new javax.swing.JPanel();

		setBackground(new java.awt.Color(255, 255, 255));

		jLayeredPane1.setLayout(new java.awt.CardLayout());

		register.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
		register.setLayout(registerLayout);
		registerLayout.setHorizontalGroup(registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE));
		registerLayout.setVerticalGroup(registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE));

		jLayeredPane1.add(register, "card3");

		login.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
		login.setLayout(loginLayout);
		loginLayout.setHorizontalGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE));
		loginLayout.setVerticalGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE));

		jLayeredPane1.add(login, "card2");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLayeredPane1));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLayeredPane1));
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLayeredPane jLayeredPane1;
	private javax.swing.JPanel login;
	private javax.swing.JPanel register;
	// End of variables declaration//GEN-END:variables
}
