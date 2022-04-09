package com.wolvesres.main;

import com.swing.custom.raven.RButton.RButtonMenu;
import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.REvent.REventButtonMenu;
import com.swing.custom.raven.RIcon.GoogleMaterialDesignIcons;
import com.swing.custom.raven.RIcon.IconFontSwing;
import com.wolvesres.component.Header;
import com.wolvesres.component.Menu;
import com.wolvesres.dao.BanOrderDAO;
import com.wolvesres.dao.GhiNhoDAO;
import com.wolvesres.dao.OrderDAO;
import com.wolvesres.form.FormBan;
import com.wolvesres.form.FormBanHang;
import com.wolvesres.form.FormDangNhap;
import com.wolvesres.form.FormHoaDon;
import com.wolvesres.form.FormKho;
import com.wolvesres.form.FormNhanVien;
import com.wolvesres.form.FormTaiKhoan;
import com.wolvesres.form.FormThongKe;
import com.wolvesres.form.FormSanPham;
import com.wolvesres.form.FormTongQuan;
import com.wolvesres.form.FormVoucher;
import com.wolvesres.helper.AnimationShowWindow;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.XImage;
import com.wolvesres.helper.XIpAddress;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelBanOrder;
import com.wolvesres.model.ModelOrder;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class Main extends javax.swing.JFrame {

    int indexButton = 0;
    JFrame frame;
    int pX, pY;
    public static List<ModelBanOrder> listBanOrder = new ArrayList<>();
    public static List<ModelOrder> listOrDerByMaBan = new ArrayList();
    public static BanOrderDAO banOrderDAO = new BanOrderDAO();
    public static OrderDAO odDAO = new OrderDAO();
    public static ModelBanOrder banOrderGlobal = new ModelBanOrder();

    static {
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
    }

    public Main() {
        initComponents();
        this.frame = this;
        setLocationRelativeTo(null);
        AnimationShowWindow.showFrame(frame);
        pnlMenu.setFrame(frame);
        init();
        setIconImage(XImage.getAppIcon());
    }

    public Menu getPnlMenu() {
        return pnlMenu;
    }

    public void init() {
        listBanOrder.addAll(banOrderDAO.selectAll());
        if (listBanOrder.size() > 0) {
            banOrderGlobal = listBanOrder.get(0);
            listOrDerByMaBan.addAll(odDAO.selectAllByIdBan(banOrderGlobal));
        }
        REventButtonMenu event;

        event = new REventButtonMenu() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    indexButton = index;
                    showForm(new FormTongQuan());
                } else if (index == 1) {
                    indexButton = index;
                    showForm(new FormNhanVien(frame));
                } else if (index == 2) {
                    if (banOrderGlobal.getMaBan() == null) {
                        ROptionDialog.showAlert(frame, "ThÃ´ng bÃ¡o", "ChÆ°a cÃ³ bÃ n Ä‘ang hoáº¡t Ä‘á»™ng, vui lÃ²ng chá»�n bÃ n!", ROptionDialog.PRIORITY_HIGHT, new Color(0, 199, 135), Color.black);
                        pnlMenu.setSelected(getButtonMenu());
                    } else {
                        indexButton = index;
                        FormBanHang banHang = new FormBanHang(frame);
                        banHang.setActionHuyHoaDon(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showForm(new FormBan(frame));
                            }
                        });
                        showForm(banHang);
                    }
                } else if (index == 3) {
                    indexButton = index;
                    showForm(new FormTaiKhoan(frame));
                } else if (index == 4) {
                    indexButton = index;
                    showForm(new FormSanPham(frame));
                } else if (index == 5) {
                    indexButton = index;
                    final FormBan formBan = new FormBan(frame);
                    formBan.setActionOrder(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ModelBan ban = formBan.getBan();
                            boolean exists = false;
                            for (int i = 0; i < listBanOrder.size(); i++) {
                                if (listBanOrder.get(i).getMaBan().equals(ban.getMaBan()) || listBanOrder.get(i).getMaBanGop().equals(ban.getMaBan())) {
                                    banOrderGlobal = listBanOrder.get(i);
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                banOrderGlobal.setMaBan(ban.getMaBan());
                                banOrderGlobal.setMaVoucher("NOVOUCHER");
                                banOrderGlobal.setGhiChu("");
                                banOrderGlobal.setMaBanGop("");
                                banOrderDAO.insert(banOrderGlobal);
                            }
                            FormBanHang formBanHang = new FormBanHang(frame);
                            indexButton = 5;
                            pnlMenu.setSelected(pnlMenu.menuBanHang);
                            showForm(formBanHang);
                        }
                    });
                    showForm(formBan);
                } else if (index == 6) {
                    indexButton = index;
                    showForm(new FormVoucher(frame));
                } else if (index == 7) {
                    indexButton = index;
                    showForm(new FormKho(frame));
                } else if (index == 8) {
                    indexButton = index;
                    showForm(new FormHoaDon(frame));
                } else if (index == 9) {
                    indexButton = index;
                    showForm(new FormThongKe(frame));
                } else {
                    if (ROptionDialog.showConfirm(frame, "XÃ¡c nháº­n", "Báº¡n cÃ³ cháº¯c muá»‘n Ä‘Äƒng xuáº¥t khÃ´ng?", ROptionDialog.WARNING, Color.yellow, Color.black)) {
                        dispose();
                        Auth.clear();
                        GhiNhoDAO gndao = new GhiNhoDAO();
                        gndao.delete(XIpAddress.getIPAddres());
                        new FormDangNhap().setVisible(true);
                    }
                }
            }
        };
        pnlMenu.setText();
        pnlMenu.initMenu(event);
        showForm(new FormTongQuan());
        pnlMenu.setSelected(pnlMenu.menuTongQuan);
        pnlHeader.setFrame(this);
        pnlHeader.setEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnimationShowWindow.closeFrame(frame);
                System.exit(0);
            }
        });
        pnlHeader.setMouseDrag(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(frame.getLocation().x + e.getX() - pX, frame.getLocation().y + e.getY() - pY);
            }
        });
        pnlHeader.setMousePress(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pX = e.getX();
                pY = e.getY();
            }

        });

    }

    public void setListBanOrder(List<ModelBanOrder> list) {
        this.listBanOrder = list;
    }

    public List<ModelBanOrder> getListBanOrder() {
        return this.listBanOrder;
    }

    public RButtonMenu getButtonMenu() {
        RButtonMenu menu = new RButtonMenu();
        if (indexButton == 0) {
            menu = pnlMenu.menuTongQuan;
        } else if (indexButton == 1) {
            menu = pnlMenu.menuNhanVien;
        } else if (indexButton == 2) {
            menu = pnlMenu.menuBanHang;
        } else if (indexButton == 3) {
            menu = pnlMenu.menuTaiKhoan;
        } else if (indexButton == 4) {
            menu = pnlMenu.menuThucDon;
        } else if (indexButton == 5) {
            menu = pnlMenu.menuBan;
        } else if (indexButton == 6) {
            menu = pnlMenu.menuVoucher;
        } else if (indexButton == 7) {
            menu = pnlMenu.menuKho;
        } else if (indexButton == 8) {
            menu = pnlMenu.menuHoaDon;
        } else if (indexButton == 9) {
            menu = pnlMenu.menuThongKe;
        }
        return menu;

    }

    public void showForm(Component com) {
        pnlContent.removeAll();
        pnlContent.add(com);
        pnlContent.revalidate();
        pnlContent.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
        pnlHeader = new com.wolvesres.component.Header();
        pnlMenu = new com.wolvesres.component.Menu();
        pnlContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(25, 25, 25));
        setMinimumSize(new java.awt.Dimension(1400, 800));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        rRoundPanel1.setBackground(new java.awt.Color(21, 25, 29));
        rRoundPanel1.setMaximumSize(new java.awt.Dimension(1400, 800));
        rRoundPanel1.setMinimumSize(new java.awt.Dimension(1400, 800));
        rRoundPanel1.setPreferredSize(new java.awt.Dimension(1400, 800));
        rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        rRoundPanel1.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        rRoundPanel1.add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        pnlContent.setBackground(new java.awt.Color(21, 25, 29));
        pnlContent.setPreferredSize(new java.awt.Dimension(1170, 730));
        pnlContent.setLayout(new java.awt.BorderLayout());
        rRoundPanel1.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 1170, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rRoundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlContent;
    private com.wolvesres.component.Header pnlHeader;
    private com.wolvesres.component.Menu pnlMenu;
    private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
    // End of variables declaration//GEN-END:variables

}
