package com.wolvesres.form;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RIcon.GoogleMaterialDesignIcons;
import com.swing.custom.raven.RIcon.IconFontSwing;
import com.swing.custom.raven.RNoticeBoard.RModelNoticeBoard;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.form.taikhoan.BlackListTaiKhoan;
import com.wolvesres.form.taikhoan.EditTaiKhoan;
import com.wolvesres.helper.Auth;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.model.ModelTaiKhoan;
import com.wolvesres.swing.table.EventAction;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Chỉnh sửa tìm kiếm, comment ở các hàm
 * Liên quan: ModelTaiKhoan
 * @author huynh
 *
 */
public class FormTaiKhoan extends javax.swing.JPanel {
	
    public FormTaiKhoan(JFrame frame) {
        initComponents();
        this.frame = frame;
        setOpaque(false);
        init();
    }
    /**
     * Hàm tổng hợp các hàm bên dưới
     */
    public void init() {
        loadToList();
        initTable();
        initNoticeBoard();
        fillToTable();
        Icon iconThemTK = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PERSON_ADD, 32, new Color(0, 199, 135));
        Icon iconDSDen = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FEATURED_PLAY_LIST, 32, new Color(0, 199, 135));
        btnThemTK.setIcon(iconThemTK);
        btnDanhSachDenTK.setIcon(iconDSDen);
        txtFindBLTaiKhoan.setForeground(Color.white);
    }
    private JFrame frame;
    private DefaultTableModel model;
    private List<ModelTaiKhoan> listTaiKhoan = new ArrayList<ModelTaiKhoan>();
    private List<ModelTaiKhoan> whiteList = new ArrayList<ModelTaiKhoan>();
    private List<ModelNhanVien> listNhanVien = new ArrayList<ModelNhanVien>();
    private TaiKhoanDAO tkdao = new TaiKhoanDAO();
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private int index = -1;
    
    /**
     * nút update và nút delete trên bảng
     */
    private EventAction<ModelTaiKhoan> eventAction = new EventAction<ModelTaiKhoan>() {
        public void update(ModelTaiKhoan entity) {
            if (index >= 0) {
                if (ROptionDialog.showConfirm(frame,"XÃ¡c nháº­n", "Báº¡n cÃ³ cháº¯c muá»‘n vÃ´ hiá»‡u hÃ³a tÃ i khoáº£n khÃ´ng?", ROptionDialog.WARNING, Color.yellow, Color.black)) {
                    addToBlackList(entity);
                }
            } else {
                ROptionDialog.showAlert(frame,"ThÃ´ng bÃ¡o", "Vui lÃ²ng chá»�n tÃ i khoáº£n!", ROptionDialog.WARNING, Color.red, Color.black);
            }
        }

        public void delete(ModelTaiKhoan entity) {
            boolean isValid = true;
            ModelNhanVien emp = getNhanVienByMaNV(entity.getTaiKhoan());
            if (Auth.user.getChucVu() == 2) {
                if (emp.getChucVu() == 2) {
                    isValid = false;
                }
            }
            if (isValid) {
                if (tkdao.checkForeignKeyHoaDon(entity).getTaiKhoan() == null && tkdao.checkForeignKeyLichSu(entity).getTaiKhoan() == null) {
                    if (ROptionDialog.showConfirm(frame,"XÃ¡c nháº­n", "XÃ¡c nháº­n xÃ³a tÃ i khoáº£n?", ROptionDialog.WARNING, Color.yellow, Color.black)) {
                    	deleteAccount(entity);
                    }
                } else {
                    ROptionDialog.showAlert(frame,"ThÃ´ng bÃ¡o", "KhÃ´ng thá»ƒ xÃ³a tÃ i khoáº£n!", ROptionDialog.WARNING, Color.red, Color.black);
//                    if (ROptionDialog.showConfirm(frame, "KhÃ´ng thá»ƒ xÃ³a, xÃ¡c nháº­n vÃ´ hiá»‡u hÃ³a?")) {
//                        addToBlackList(entity);
//                    }
                }
            } else {
                ROptionDialog.showAlert(frame,"ThÃ´ng bÃ¡o", "Báº¡n khÃ´ng cÃ³ quyá»�n xÃ³a tÃ i khoáº£n nÃ y!", ROptionDialog.WARNING, Color.red, Color.black);
            }

        }

    };
    
    /**
     * Hàm desigs bảng trên form 
     */
    private void initTable() {
        tblTaiKhoan.setOpaque(true);
        tblTaiKhoan.setBackground(new Color(255, 255, 255));
        tblTaiKhoan.setFillsViewportHeight(true);
        tblTaiKhoan.fixTable(jScrollPane1);
        tblTaiKhoan.setFont(new Font("SansSerif", 1, 12));
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"TÃªn tÃ i khoáº£n", "NhÃ¢n viÃªn sá»Ÿ há»¯u", "Chá»©c vá»¥", "Thao TÃ¡c"});
        tblTaiKhoan.setModel(model);
        tblTaiKhoan.setColumnAction(3);
    }

    /**
     * Hàm chữ vô dụng trên form :)
     */
    private void initNoticeBoard() {
        rNoticeBoard1.addDate("18/12/2021");
        rNoticeBoard1.addNoticeBoard(new RModelNoticeBoard(new Color(255, 255, 0), "", "Chá»§ nhÃ  hÃ ng", "Chá»§ nhÃ  hÃ ng cÃ³ thá»ƒ sá»­ dá»¥ng táº¥t cáº£ cÃ¡c chá»©c nÄƒng \n trong pháº§n má»�m bao gá»“m chá»©c nÄƒng thá»‘ng kÃª, tÃ i khoáº£n cá»§a chá»§ nhÃ  hÃ ng sáº½ khÃ´ng hiá»ƒn thá»‹ trÃªn báº£ng."));
        rNoticeBoard1.addNoticeBoard(new RModelNoticeBoard(new Color(0, 0, 0), "", "Quáº£n lÃ½", "Quáº£n lÃ½ nhÃ  hÃ ng cÃ³ thá»ƒ sá»­ dá»¥ng táº¥t cáº£ cÃ¡c chá»©c nÄƒng trong pháº§n má»�m ngoáº¡i trá»« chá»©c nÄƒng thá»‘ng kÃª."));
        rNoticeBoard1.addDate("18/12/2021");
        rNoticeBoard1.addNoticeBoard(new RModelNoticeBoard(new Color(0, 0, 0), "", "Thu ngÃ¢n", "Thu ngÃ¢n cÃ³ quyá»�n sá»­ dá»¥ng cÃ¡c chá»©c nÄƒng tra cá»©u (trá»« tra cá»©u nhÃ¢n viÃªn, vÃ  tÃ i khoáº£n), bÃªn cáº¡nh Ä‘Ã³ lÃ  cÃ¡c chá»©c nÄƒng chuyÃªn mÃ´n, nhÆ° thanh toÃ¡n, xuáº¥t hÃ³a Ä‘Æ¡n, in hÃ³a Ä‘Æ¡n, in báº¿p."));
        rNoticeBoard1.addNoticeBoard(new RModelNoticeBoard(new Color(0, 0, 0), "", "NhÃ¢n viÃªn", "NhÃ¢n viÃªn bÃ¬nh thÆ°á»�ng khÃ´ng cÃ³ quyá»�n sá»­ dá»¥ng chá»©c nÄƒng trong pháº§n má»�m."));
        //  rNoticeBoard1.addNoticeBoard(new RModelNoticeBoard(new Color(27, 188, 204), "Cáº­p nháº­t thÃ´ng tin (thay Ä‘á»•i chá»©c vá»¥)", "Náº¿u thay Ä‘á»•i chá»©c vá»¥ cá»§a tÃ i khoáº£n cÃ³ quyá»�n truy cáº­p pháº§n má»�m thÃ nh chá»©c vá»¥ khÃ´ng cÃ³ quyá»�n truy cáº­p pháº§n má»�m vÃ  nhÃ¢n viÃªn Ä‘Ã£ cÃ³ thao tÃ¡c tá»›i dá»¯ liá»‡u há»‡ thá»‘ng thÃ¬ pháº§n má»�m sáº½ thÃ´ng bÃ¡o xÃ¡c nhÃ¢n vÃ´ hiá»‡u hÃ³a quyá»�n truy cáº­p há»‡ thá»‘ng cá»§a nhÃ¢n viÃªn."));
        rNoticeBoard1.addNoticeBoard(new RModelNoticeBoard(new Color(0, 0, 0), "", "XÃ³a tÃ i khoáº£n", "Náº¿u tÃ i khoáº£n Ä‘Ã£ cÃ³ thao tÃ¡c tá»›i dá»¯ liá»‡u pháº§n má»�m thÃ¬ sáº½ cÃ³ thÃ´ng bÃ¡o yÃªu cáº§u xÃ¡c nhÃ¢n vÃ´ hiá»‡u hÃ³a tÃ i khoáº£n, Ä‘á»ƒ Ä‘áº£m báº£o tÃ­nh toÃ n váº¹n dá»¯ liá»‡u nÃªn tÃ i khoáº£n Ä‘Ã£ cÃ³ thao tÃ¡c tá»›i dá»¯ liá»‡u sáº½ khÃ´ng thá»ƒ xÃ³a."));
        rNoticeBoard1.addNoticeBoard(new RModelNoticeBoard(new Color(0, 0, 0), "", "VÃ´ hiá»‡u hÃ³a tÃ i khoáº£n", "TÃ i khoáº£n sáº½ khÃ´ng thá»ƒ truy cáº­p pháº§n má»�m sau khi bá»‹ vÃ´ hiá»‡u hÃ³a."));
        rNoticeBoard1.addNoticeBoard(new RModelNoticeBoard(new Color(0, 0, 0), "", "KÃ­ch hoáº¡t khoáº£n", "TÃ i khoáº£n sáº½ Ä‘Æ°á»£c cáº¥p quyá»�n truy cáº­p bÃ¬nh thÆ°á»Ÿng sau khi kÃ­ch hoáº¡t láº¡i."));
        rNoticeBoard1.scrollToTop();
    }
    
    
    public List<ModelTaiKhoan> getList() {
        return this.listTaiKhoan;
    }
    
    /**
     * Hàm load dữ liệu lên bảng
     */
    private void loadToList() {
        listNhanVien.addAll(nhanVienDAO.selectAll());
        for (ModelTaiKhoan modelTaiKhoan : tkdao.selectAll()) {
            ModelNhanVien emp = getNhanVienByMaNV(modelTaiKhoan.getTaiKhoan());
            if (!modelTaiKhoan.getTaiKhoan().equals(Auth.user.getMaNV())) {
                if (emp.getChucVu() != 1) {
                    listTaiKhoan.add(modelTaiKhoan);
                }
            }
        }
    }

    /**
	 * Add to whitelist liên quan đến bảng đen
	 */
    private void loadToWhiteList() {
        whiteList.clear();
        for (ModelTaiKhoan acc : listTaiKhoan) {
            if (acc.isTrangThai()) {
                whiteList.add(acc);
            }
        }
    }

    /**
     * Hàm lấy dữ liệu mã nhân viên từ data
     * @param manv
     * @return
     */
    private ModelNhanVien getNhanVienByMaNV(String manv) {
        ModelNhanVien emp = new ModelNhanVien();
        for (int i = 0; i < listNhanVien.size(); i++) {
            if (manv.equals(listNhanVien.get(i).getMaNV())) {
                emp = listNhanVien.get(i);
            }
        }
        return emp;
    }

    /**
     * Hàm fill bảng chắc ai cũng hiểu :)
     */
    private void fillToTable() {
        loadToWhiteList();
        model.setRowCount(0);
        for (ModelTaiKhoan acc : whiteList) {
            tblTaiKhoan.addRow(acc.toRowTableTK(eventAction));
        }
    }
    /**
     * select bảng
     * @param selectedRow
     */
    public void showDetail(int selectedRow) {
        if (selectedRow >= 0) {
            ModelTaiKhoan tk = whiteList.get(selectedRow);
            lblTenTK.setText(tk.getTaiKhoan());
            lblMatKhau.setText(tk.getMatKhau());
            lblTrangThai.setText(tk.isTrangThai() ? "Ä�ang hoáº¡t Ä‘á»™ng" : "KhÃ´ng hoáº¡t Ä‘á»™ng");
        }
    }

    /**
     * Hàm lấy doàng dữ liệu trên bảng
     * @param row
     * @return
     */
    private ModelTaiKhoan getTaiKhoanFromRowTable(int row) {
        ModelTaiKhoan account = new ModelTaiKhoan();
        String tenTaiKhoan = String.valueOf(tblTaiKhoan.getValueAt(row, 0));
        for (int i = 0; i < listTaiKhoan.size(); i++) {
            if (tenTaiKhoan.equals(listTaiKhoan.get(i).getTaiKhoan())) {
                account = listTaiKhoan.get(i);
                break;
            }
        }
        return account;
    }

    /**
     * Hàm đưa vào danh sách đen
     * @param account
     */
    private void addToBlackList(ModelTaiKhoan account) {
        account.setTrangThai(false);
        tkdao.update(account, account.getTaiKhoan());
        updateAccount(account);
        fillToTable();
    }
    
    /**
     * insert tài khoản
     * @param entity
     */
    public void insertAccount(ModelTaiKhoan entity) {
    	insertdata(entity);
    	fillinsert(entity);
    }
    
    public void insertdata(ModelTaiKhoan entity) {
    	tkdao.insert(entity);
    }
    
    public void fillinsert(ModelTaiKhoan entity) {
    	listTaiKhoan.add(entity);
    	fillToTable();
    }
    
    /**
     * update tài khoản
     * @param entity
     */
    public void updateAccount(ModelTaiKhoan entity) {
    	updatedata(entity);
    	fillupdate(entity);
    }
    
    public void updatedata(ModelTaiKhoan entity) {
    	tkdao.update(entity, entity.getTaiKhoan());
    }
    
    public void fillupdate(ModelTaiKhoan entity) {
    	for (int i = 0; i < listTaiKhoan.size(); i++) {
			if (listTaiKhoan.get(i).getTaiKhoan().equals(entity.getTaiKhoan())) {
				listTaiKhoan.set(i, entity);
			}
		}
		fillToTable();
    }
    
    /**
     * delete tài khoản
     * @param entity
     */
    public void deleteAccount(ModelTaiKhoan entity) {
    	deletedata(entity);
    	filldelete(entity);
    }
    
    public void deletedata(ModelTaiKhoan entity) {
    	tkdao.delete(entity.getTaiKhoan());
    }
    
    public void filldelete(ModelTaiKhoan entity) {
    	listTaiKhoan.remove(entity);
    	fillToTable();
    }
    
    /**
     * hàm tiềm kiếm
     * @param keyword
     * @return
     */
    public List<ModelTaiKhoan> timkiem(String keyword){
        List<ModelTaiKhoan> listFind = new ArrayList<>();
        	if(keyword.trim().length() > 0) {
        		listFind = tkdao.timkiem(keyword);
        	}else {
        		listFind = tkdao.selectAll();
        	}
        return listFind;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        btnDuaVaoDanhSachDen = new com.swing.custom.raven.RButton.RButton();
        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        btnThemTK = new com.swing.custom.raven.RButton.RButton();
        btnDanhSachDenTK = new com.swing.custom.raven.RButton.RButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTaiKhoan = new com.wolvesres.swing.table.Table();
        rNoticeBoard1 = new com.swing.custom.raven.RNoticeBoard.RNoticeBoard();
        rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTenTK = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        txtFindBLTaiKhoan = new com.swing.custom.raven.RTextField.RTextField();
        rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
        jLabel4 = new javax.swing.JLabel();
        rImageAvatar2 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        btnDuaVaoDanhSachDen.setBackground(new java.awt.Color(102, 102, 102));
        btnDuaVaoDanhSachDen.setForeground(new java.awt.Color(255, 255, 255));
        btnDuaVaoDanhSachDen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/disabled.png"))); // NOI18N
        btnDuaVaoDanhSachDen.setText("VÃ´ hiá»‡u hÃ³a tÃ i khoáº£n");
        btnDuaVaoDanhSachDen.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnDuaVaoDanhSachDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDuaVaoDanhSachDenActionPerformed(evt);
            }
        });

        setMaximumSize(new java.awt.Dimension(1170, 730));
        setMinimumSize(new java.awt.Dimension(1170, 730));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rRoundPanel1.setBackground(new java.awt.Color(6, 7, 13));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnThemTK.setText("ThÃªm tÃ i khoáº£n");
        btnThemTK.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnThemTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTKActionPerformed(evt);
            }
        });
        rRoundPanel1.add(btnThemTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 170, 40));

        btnDanhSachDenTK.setForeground(new java.awt.Color(0, 0, 0));
        btnDanhSachDenTK.setText("Xem danh sÃ¡ch vÃ´ hiá»‡u hÃ³a");
        btnDanhSachDenTK.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnDanhSachDenTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhSachDenTKActionPerformed(evt);
            }
        });
        rRoundPanel1.add(btnDanhSachDenTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 690, 280, 40));

        jScrollPane1.setBorder(null);

        tblTaiKhoan.setAutoCreateRowSorter(true);
        tblTaiKhoan.setBackground(new java.awt.Color(207, 224, 247));
        tblTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        
        tblTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblTaiKhoanMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblTaiKhoan);

        rRoundPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 710, 520));

        rNoticeBoard1.setBackground(new java.awt.Color(51, 51, 51));
        rNoticeBoard1.setOpaque(true);
        rRoundPanel1.add(rNoticeBoard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 160, 440, 570));

        rRoundPanel2.setBackground(new java.awt.Color(102, 102, 102));
        rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("TÃªn tÃ i khoáº£n:");
        rRoundPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 130, -1, -1));

        jLabel2.setText("Máº­t kháº©u:");
        rRoundPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 193, -1, -1));

        jLabel3.setText("Tráº¡ng thÃ¡ng:");
        rRoundPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 261, -1, -1));

        lblTenTK.setText("A");
        rRoundPanel2.add(lblTenTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, -1, -1));

        lblMatKhau.setText("A");
        rRoundPanel2.add(lblMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 193, -1, -1));

        lblTrangThai.setText("A");
        rRoundPanel2.add(lblTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 261, -1, -1));

        rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 230, 440, 450));

        txtFindBLTaiKhoan.setBackground(new java.awt.Color(6, 7, 13));
        txtFindBLTaiKhoan.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFindBLTaiKhoan.setLabelText("TÃ¬m kiáº¿m tÃ i khoáº£n");
        
        txtFindBLTaiKhoan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindBLTaiKhoanKeyReleased(evt);
            }
        });
        rRoundPanel1.add(txtFindBLTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 110, 440, 50));

        rRoundPanel3.setBackground(new java.awt.Color(0, 199, 135));
        rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("WolvesRes");
        rRoundPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 40, 150, 30));

        rImageAvatar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
        rRoundPanel3.add(rImageAvatar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 120, 110));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TÃ€I KHOáº¢N");
        rRoundPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 0, 250, 110));

        rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 110));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Danh sÃ¡ch tÃ i khoáº£n");
        rRoundPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 190, 30));

        add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 730));
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTKActionPerformed
        EditTaiKhoan editForm = new EditTaiKhoan(frame, true);
        editForm.setInsert(true);
        editForm.setVisible(true);
        if (!editForm.getIsDispose()) {
        	insertAccount(editForm.getTaiKhoan());
            ROptionDialog.showAlert(frame,"ThÃ´ng bÃ¡o", "ThÃªm dá»¯ liá»‡u thÃ nh cÃ´ng!", ROptionDialog.NOTIFICATIONS_ACTIVE, new Color(0,199,135), Color.black);
        }
    }//GEN-LAST:event_btnThemTKActionPerformed

    private void btnDanhSachDenTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachDenTKActionPerformed
        BlackListTaiKhoan bls = new BlackListTaiKhoan(frame, true);
        bls.setVisible(true);
        if (bls.getIsChangeData()) {
            for (int i = 0; i < listTaiKhoan.size(); i++) {
                for (int j = 0; j < bls.getListReturn().size(); j++) {
                    if (listTaiKhoan.get(i).getTaiKhoan().equals(bls.getListReturn().get(j).getTaiKhoan())) {
                        listTaiKhoan.set(i, bls.getListReturn().get(j));
                    }
                }
            }
            fillToTable();
        }
    }//GEN-LAST:event_btnDanhSachDenTKActionPerformed

    private void tblTaiKhoanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTaiKhoanMousePressed
        index = tblTaiKhoan.getSelectedRow();
        if (evt.getButton() == MouseEvent.BUTTON3) {
            tblTaiKhoan.clearSelection();
        } else {
            showDetail(tblTaiKhoan.getSelectedRow());
        }

    }//GEN-LAST:event_tblTaiKhoanMousePressed

    private void btnDuaVaoDanhSachDenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDuaVaoDanhSachDenActionPerformed

    }//GEN-LAST:event_btnDuaVaoDanhSachDenActionPerformed

    private void txtFindBLTaiKhoanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindBLTaiKhoanKeyReleased
    	String keyword = txtFindBLTaiKhoan.getText().trim();
    	if(keyword.isEmpty()) {
    		listTaiKhoan.clear();
    		loadToList();
    	}else {
    		listTaiKhoan = timkiem(keyword);
    	}
    	fillToTable();
    }//GEN-LAST:event_txtFindBLTaiKhoanKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.raven.RButton.RButton btnDanhSachDenTK;
    private com.swing.custom.raven.RButton.RButton btnDuaVaoDanhSachDen;
    private com.swing.custom.raven.RButton.RButton btnThemTK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblTenTK;
    private javax.swing.JLabel lblTrangThai;
    private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar2;
    private com.swing.custom.raven.RNoticeBoard.RNoticeBoard rNoticeBoard1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
    private com.wolvesres.swing.table.Table tblTaiKhoan;
    private com.swing.custom.raven.RTextField.RTextField txtFindBLTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
