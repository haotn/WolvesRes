package com.wolvesres.model;

import com.wolvesres.dao.GhiNhoDAO;

/**
 * 
 * Them: insert
 * */
public class ModelGhiNho {
    private String ip;
    private String taiKhoan;
    private String passWord;
    private GhiNhoDAO gndao = new GhiNhoDAO();
    public ModelGhiNho() {
    }

    public ModelGhiNho(String ip, String taiKhoan, String passWord) {
        this.ip = ip;
        this.taiKhoan = taiKhoan;
        this.passWord = passWord;
    }

    
    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the taiKhoan
     */
    public String getTaiKhoan() {
        return taiKhoan;
    }

    /**
     * @param taiKhoan the taiKhoan to set
     */
    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    /**
     * @return the passWord
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * @param passWord the passWord to set
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
//  insert
    public void insert() {
    	gndao.insert(this);
	}
    
}
