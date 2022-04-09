package com.wolvesres.dao;

import java.util.List;

public interface WolvesResDAO <E, K>{
     // Tên chuẩn cho phương thức select * ...
    public List<E> selectAll();
    
    // Tên chuẩn cho phương thức select ... where ... = ID
    public E selectById(K ID);
    
    // Tên chuẩn cho phương thức select ... where ... = key1, ..., ... = keyn
    List<E> selectBySQL(String sql, Object...Entity);
    
    // Tên chuẩn cho phương thức insert ...
    public void insert(E Entity);
    
    // Tên chuẩn cho phương thức update ...
    public void update(E Entity, K ID);
    
    // Tên chuẩn cho phương thức delete ...
    public void delete(K ID);
    
}
