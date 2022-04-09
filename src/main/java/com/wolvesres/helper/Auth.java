package com.wolvesres.helper;

import com.wolvesres.model.ModelNhanVien;

public class Auth {

    public static ModelNhanVien user = null;

    public static void clear() {
        Auth.user = null;
    }

    public static boolean isLogin() {
        return Auth.user != null;
    }

    public static boolean isManager() {
        return (Auth.isLogin() && user.getChucVu() == 2);
    }

    public static boolean isBoss() {
        return (Auth.isLogin() && user.getChucVu() == 1);
    }
}
