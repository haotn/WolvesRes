package com.wolvesres.helper;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

public class PrintHelper {

    public static PageFormat getPageFormat(PrinterJob pj, double bHeight, double width, double height) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        paper.setSize(width, height);
        paper.setImageableArea(50, 10, width, height - cm_to_pp(1));
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);
        return pf;
    }

    public static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    public static double toPPI(double inch) {
        return inch * 72d;
    }

}
