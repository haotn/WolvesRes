package com.wolvesres.helper;

import com.wolvesres.form.dangnhap.PanelDangNhap;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XIpAddress {

    public static String getIPAddres() {
        String ipAddress = "";
        try {
            InetAddress IP = InetAddress.getLocalHost();
            ipAddress = IP.getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(PanelDangNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ipAddress;
    }
}
