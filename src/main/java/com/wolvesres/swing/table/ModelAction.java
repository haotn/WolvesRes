package com.wolvesres.swing.table;

import javax.swing.Icon;

public class ModelAction {

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public EventAction getEvent() {
        return event;
    }

    public void setEvent(EventAction event) {
        this.event = event;
    }

    public Icon getIconUpdate() {
        return iconUpdate;
    }

    public void setIconUpdate(Icon iconUpdate) {
        this.iconUpdate = iconUpdate;
    }

    public Icon getIconDelete() {
        return iconDelete;
    }

    public void setIconDelete(Icon iconDelete) {
        this.iconDelete = iconDelete;
    }

    public String getToolTipTextBtnEdit() {
        return toolTipTextBtnEdit;
    }

    public void setToolTipTextBtnEdit(String toolTipTextBtnEdit) {
        this.toolTipTextBtnEdit = toolTipTextBtnEdit;
    }

    public String getToolTipTextBtnDelete() {
        return toolTipTextBtnDelete;
    }

    public void setToolTipTextBtnDelete(String tollTipTextBtnDelete) {
        this.toolTipTextBtnDelete = tollTipTextBtnDelete;
    }

    public ModelAction(String toolTipTextBtnEdit, String tollTipTextBtnDelete, Icon iconUpdate, Icon iconDelete, Object entity, EventAction event) {
        this.toolTipTextBtnEdit = toolTipTextBtnEdit;
        this.toolTipTextBtnDelete = tollTipTextBtnDelete;
        this.iconUpdate = iconUpdate;
        this.iconDelete = iconDelete;
        this.entity = entity;
        this.event = event;
    }

    public ModelAction(Icon iconUpdate, Icon iconDelete, Object entity, EventAction event) {
        this.iconUpdate = iconUpdate;
        this.iconDelete = iconDelete;
        this.entity = entity;
        this.event = event;
    }

    public ModelAction(Object entity, EventAction event) {
        this.entity = entity;
        this.event = event;
    }

    public ModelAction() {
    }
    private String toolTipTextBtnEdit;
    private String toolTipTextBtnDelete;
    private Icon iconUpdate;
    private Icon iconDelete;
    private Object entity;
    private EventAction event;
}
