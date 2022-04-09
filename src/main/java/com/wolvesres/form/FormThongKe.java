package com.wolvesres.form;

import com.wolvesres.form.thongke.FormThongKeBan;
import com.wolvesres.form.thongke.FormThongKeDoanhThu;
import com.wolvesres.form.thongke.FormThongKeKho;
import com.wolvesres.form.thongke.FormThongKeSanPham;
import com.wolvesres.form.thongke.FormThongKe_TongHop;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class FormThongKe extends javax.swing.JPanel {

    public FormThongKe(JFrame frame) {
        initComponents();
        this.frame = frame;
        init();
    }

    private void init() {
        final FormThongKe_TongHop formTH = new FormThongKe_TongHop(frame);
        formTH.setEventDoanhThu(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                FormThongKeDoanhThu tkDT = new FormThongKeDoanhThu();
                tkDT.setBack(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showForm(formTH);
                    }
                });
                showForm(tkDT);
            }
        });
        formTH.setEventBan(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                FormThongKeBan tkban = new FormThongKeBan();
                tkban.setBack(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showForm(formTH);
                    }
                });
                showForm(tkban);
            }
        });
        formTH.setEventKho(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                FormThongKeKho tkkho = new FormThongKeKho();
                tkkho.setBack(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showForm(formTH);
                    }
                });
                showForm(tkkho);
            }
        });
        formTH.setEventSanPham(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                FormThongKeSanPham tkSP = new FormThongKeSanPham();
                tkSP.setBack(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showForm(formTH);
                    }
                });
                showForm(tkSP);
            }

        });

        showForm(formTH);
    }
    JFrame frame;

    private void showForm(Component com) {
        this.removeAll();
        this.add(com);
        this.revalidate();
        this.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(209, 220, 208));
        setPreferredSize(new java.awt.Dimension(1117, 730));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
