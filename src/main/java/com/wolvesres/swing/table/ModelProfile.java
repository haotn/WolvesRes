package com.wolvesres.swing.table;

import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelSanPham;
import javax.swing.Icon;

public class ModelProfile {

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelProfile(Icon icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public ModelProfile() {
    }

    public ModelProfile(Icon icon, String name, ModelSanPham productModelSanPham) {
        this.product = productModelSanPham;
        this.icon = icon;
        this.name = name;
    }

    public ModelSanPham getProduct() {
        return product;
    }

    public void setProduct(ModelSanPham product) {
        this.product = product;
    }

    public ModelNhanVien getEmp() {
        return emp;
    }

    public void setEmp(ModelNhanVien emp) {
        this.emp = emp;
    }

    public ModelProfile(Icon icon, String name, ModelNhanVien emp) {
        this.icon = icon;
        this.name = name;
        this.emp = emp;
    }
    
    private ModelSanPham product;
    private Icon icon;
    private String name;
    private ModelNhanVien emp;
}
