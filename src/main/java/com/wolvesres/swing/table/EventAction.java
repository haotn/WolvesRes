package com.wolvesres.swing.table;

public interface EventAction<E> {

    public void delete(E entity);

    public void update(E entity);
}
