package com.wolvesres.swing.table;

import javax.swing.Icon;

public class ModelActionBlackList {

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public EventActionBlackList<Object> getEvent() {
        return event;
    }

    public void setEvent(EventActionBlackList<Object> event) {
        this.event = event;
    }

    public String getToolTipTextBtn() {
        return toolTipTextBtn;
    }

    public void setToolTipTextBtn(String toolTipTextBtn) {
        this.toolTipTextBtn = toolTipTextBtn;
    }

    public ModelActionBlackList(Object entity, EventActionBlackList<Object> event) {
        this.entity = entity;
        this.event = event;
    }

    public ModelActionBlackList(Icon icon, Object entity, EventActionBlackList<Object> event) {
        this.icon = icon;
        this.entity = entity;
        this.event = event;
    }

    public ModelActionBlackList(Icon icon, String toolTipTextBtn, Object entity, EventActionBlackList<Object> event) {
        this.icon = icon;
        this.toolTipTextBtn = toolTipTextBtn;
        this.entity = entity;
        this.event = event;
    }
   

    public ModelActionBlackList() {
    }
    private Icon icon;

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    private String toolTipTextBtn;
    private Object entity;
    private EventActionBlackList<Object> event;
}
