package com.wolvesres.swing.table;

import com.swing.custom.raven.RScrollbar.RScrollBarCustom;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class Table extends JTable {

    private static final long serialVersionUID = 1L;
    private int colAction = 0;
    private boolean actionWhiteList = true;
    private Color TableBackground = new Color(255, 255, 255);
    private Color Tableforeground = new Color(0, 0, 0);
    private Color TableBackgoundSelectRow = new Color(239, 244, 255);
    private Color TableForegroundSelectRow = new Color(0, 0, 0);

    public Table() {
        setShowHorizontalLines(true);
        setGridColor(new Color(51, 51, 51));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                TableHeader header = new TableHeader(o + "");
                if (i1 == 4) {
                    header.setHorizontalAlignment(JLabel.CENTER);
                }
                return header;
            }
        });
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean focus, int i, int i1) {
                if (o instanceof ModelProfile) {
                    ModelProfile data = (ModelProfile) o;
                    Profile cell = new Profile(data);
                    cell.setForeground(getTableforeground());
                    if (selected) {
                        cell.getLabelText().setForeground(getTableForegroundSelectRow());
                        cell.setBackground(getTableBackgoundSelectRow());
                    } else {
                        cell.getLabelText().setForeground(getTableforeground());
                        cell.setBackground(getTableBackground());
                    }
                    return cell;

                } else if (o instanceof ModelAction) {
                    ModelAction data = (ModelAction) o;
                    Action cell = new Action(data);
                    if (selected) {
                        cell.setBackground(getTableBackgoundSelectRow());
                    } else {
                        cell.setBackground(getTableBackground());
                    }
                    return cell;
                } else if (o instanceof ModelActionBlackList) {
                    ModelActionBlackList data = (ModelActionBlackList) o;
                    ActionBalckList cell = new ActionBalckList(data);
                    if (selected) {
                        cell.setBackground(getTableBackgoundSelectRow());
                    } else {
                        cell.setBackground(getTableBackground());
                    }
                    return cell;
                } else {
                    //Tạo component là dòng trên bảng 
                    Component com = super.getTableCellRendererComponent(jtable, o, selected, focus, i, i1);
                    setBorder(noFocusBorder);
                    com.setForeground(getTableforeground());
                    //Nếu được select thì đổi màu nền 
                    if (selected) {
                        com.setForeground(getTableForegroundSelectRow());
                        com.setBackground(getTableBackgoundSelectRow());
                    } else {
                        com.setForeground(getTableforeground());
                        com.setBackground(getTableBackground());
                    }
                    return com;
                }
            }
        });
    }

    @Override
    public TableCellEditor getCellEditor(int row, int col) {
        if (col == colAction && actionWhiteList) {
            return new TableCellAction();
        } else if (col == colAction && !actionWhiteList) {
            return new TableCellActionBlackList();
        } else {
            return super.getCellEditor(row, col);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == colAction) {
            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public Class<?> getColumnClass(int column) {
//        return super.getColumnClass(column); 
//    }
//    @Override
//    public Class<?> getColumnClass(int column) {
//        return super.getColumnClass(column); //To change body of generated methods, choose Tools | Templates.
//    }
    public void addRow(Object[] row) {
        DefaultTableModel mod = (DefaultTableModel) getModel();
        mod.addRow(row);
    }

    public void setColumnAction(int col) {
        this.colAction = col;
    }

    public void setActionWhiteList(boolean isWhite) {
        this.actionWhiteList = isWhite;
    }

    public void fixTable(JScrollPane scroll) {
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setVerticalScrollBar(new RScrollBarCustom());
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        scroll.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    /**
     * @return the TableBackground
     */
    public Color getTableBackground() {
        return TableBackground;
    }

    /**
     * @return the Tableforeground
     */
    public Color getTableforeground() {
        return Tableforeground;
    }

    /**
     * @return the TableBackgoundSelectRow
     */
    public Color getTableBackgoundSelectRow() {
        return TableBackgoundSelectRow;
    }

    /**
     * @return the TableForegroundSelectRow
     */
    public Color getTableForegroundSelectRow() {
        return TableForegroundSelectRow;
    }

    /**
     * @param TableBackground the TableBackground to set
     */
    public void setTableBackground(Color TableBackground) {
        this.TableBackground = TableBackground;
    }

    /**
     * @param Tableforeground the Tableforeground to set
     */
    public void setTableforeground(Color Tableforeground) {
        this.Tableforeground = Tableforeground;
    }

    /**
     * @param TableBackgoundSelectRow the TableBackgoundSelectRow to set
     */
    public void setTableBackgoundSelectRow(Color TableBackgoundSelectRow) {
        this.TableBackgoundSelectRow = TableBackgoundSelectRow;
    }

    /**
     * @param TableForegroundSelectRow the TableForegroundSelectRow to set
     */
    public void setTableForegroundSelectRow(Color TableForegroundSelectRow) {
        this.TableForegroundSelectRow = TableForegroundSelectRow;
    }

//    @Override
//    public void paintComponent(Graphics g) {
//        Color TableBackground = new Color(168, 210, 241);
//        Color controlColor = new Color(230, 240, 230);
//        int width = getWidth();
//        int height = getHeight();
//        Graphics2D g2 = (Graphics2D) g;
//        Paint oldPaint = g2.getPaint();
//        g2.setPaint(new GradientPaint(0, 0, TableBackground, width, 0, controlColor));
//        g2.fillRect(0, 0, width, height);
//        g2.setPaint(oldPaint);
//        for (int row : getSelectedRows()) {
//            Rectangle start = getCellRect(row, 0, true);
//            Rectangle end = getCellRect(row, getColumnCount() - 1, true);
//            g2.setPaint(new GradientPaint(start.x, 0, controlColor, (int) ((end.x + end.width - start.x) * 1.25), 0, Color.orange));
//            g2.fillRect(start.x, start.y, end.x + end.width - start.x, start.height);
//        }
//        super.paintComponent(g);
//    }
//  ((JComponent) getDefaultRenderer(Object.class)).setOpaque(true);
    /**
     * @return the TableBackground
     */
}
