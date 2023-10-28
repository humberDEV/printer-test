package com.example.example;

import java.awt.print.PrinterJob;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class GenerateOrder {
    private final Map<String, Integer> items;
    private final Date date;
    private final String table;

    public GenerateOrder(Map<String, Integer> items, String table) {
        this.items = items;
        this.date = new Date();
        this.table = table;
    }

    public void print() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();

        int contentWidth = 226;
        int staticContentHeight = 4 * 14;
        int dynamicContentHeight = items.size() * 12;
        int totalHeight = staticContentHeight + dynamicContentHeight + 3 * 14;

        Paper paper = new Paper();
        paper.setSize(contentWidth, totalHeight);
        paper.setImageableArea(0, 0, contentWidth, totalHeight);

        pageFormat.setPaper(paper);

        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
                if (page > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                int y = 12;
                int lineHeight = 12;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Font smallFont = new Font("Arial", Font.PLAIN, 12);
                Font headerFont = new Font("Arial", Font.BOLD, 14);
                FontMetrics fontMetrics = g.getFontMetrics(headerFont);

                String title = "Comanda Cocina";
                int headerX = (contentWidth - fontMetrics.stringWidth(title)) / 2;
                g.setFont(headerFont);

                g.drawString(title, headerX, y);
                y += 2 * lineHeight;

                g.setFont(smallFont);
                int tableX = (contentWidth - fontMetrics.stringWidth(table)) / 2;
                g.drawString(table, tableX, y);
                y += 2 * lineHeight;

                g.setFont(smallFont);
                int dateX = (contentWidth - fontMetrics.stringWidth(dateFormat.format(date))) / 2;
                g.drawString(dateFormat.format(date), dateX, y);
                y += 2 * lineHeight;

                int productX = 20;
                g.setFont(headerFont);
                g.drawString("Producto", productX, y);
                int unitsX = 150;
                g.drawString("Ud", unitsX, y);
                y += lineHeight;

                for (Map.Entry<String, Integer> entry : items.entrySet()) {
                    g.setFont(smallFont);
                    g.drawString(entry.getKey(), productX, y);
                    int unitsValueX = 150;
                    g.drawString(entry.getValue().toString(), unitsValueX, y);
                    y += lineHeight;
                }

                return Printable.PAGE_EXISTS;
            }
        }, pageFormat);

        try {
            printerJob.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

}
