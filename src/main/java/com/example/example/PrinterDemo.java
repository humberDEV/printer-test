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
import java.util.List;

public class PrinterDemo {
    private final List<String> items;
    private final Date date;

    public PrinterDemo(List<String> items) {
        this.items = items;
        this.date = new Date();
    }

    public void print() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();

        String companyName = "Pasapalos Bar";
        String cif = "CIF: Y4990591L";
        String address = "Cl Juan Bautista Vives, 1";
        String phone = "747857939";

        int contentWidth = 226;
        int staticContentHeight = 6 * 12;
        int dynamicContentHeight = items.size() * 12;
        int totalHeight = staticContentHeight + dynamicContentHeight;

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

                int x = 0;
                int y = 10;
                int lineHeight = 12;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Font smallFont = new Font("Arial", Font.PLAIN, 12);
                Font headerFont = new Font("Arial", Font.BOLD, 14);
                FontMetrics fontMetrics = g.getFontMetrics(headerFont);

                int headerCompanyName = (contentWidth - fontMetrics.stringWidth(companyName)) / 2;
                int headerCif = (contentWidth - fontMetrics.stringWidth(cif)) / 2;
                int headerAddress = (contentWidth - fontMetrics.stringWidth(address)) / 2;
                int headerPhone = (contentWidth - fontMetrics.stringWidth(phone)) / 2;
                int headerDate = (contentWidth - fontMetrics.stringWidth(dateFormat.format(date))) / 2;
                g.setFont(headerFont);

                g.drawString(companyName, headerCompanyName, y);
                y += 2 * lineHeight;

                fontMetrics = g.getFontMetrics(smallFont);
                g.setFont(smallFont);

                g.drawString(cif, headerCif, y);
                y += lineHeight;

                int dynamicContentY = y;

                g.drawString(address, headerAddress, dynamicContentY);
                dynamicContentY += lineHeight;

                g.drawString(phone, headerPhone, dynamicContentY);
                dynamicContentY += lineHeight;

                g.drawString(dateFormat.format(date), headerDate, dynamicContentY);
                dynamicContentY += lineHeight;

                for (String item : items) {
                    g.drawString(item, x, dynamicContentY);
                    dynamicContentY += lineHeight;
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

    public static void main(String[] args) {
        List<String> pasapalosItems = List.of(
                "Alitas de pollo",
                "Tequeños",
                "Mini hamburguesas",
                "Papas fritas",
                "Deditos de queso");

        PrinterDemo printer = new PrinterDemo(pasapalosItems);
        printer.print();
    }
}
