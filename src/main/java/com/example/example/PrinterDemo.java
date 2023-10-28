package com.example.example;

import java.awt.print.PrinterJob;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.Font;
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

        int staticContentHeight = 6 * 12;
        int dynamicContentHeight = items.size() * 12;
        int totalHeight = staticContentHeight + dynamicContentHeight;

        Paper paper = new Paper();
        paper.setSize(226.77, totalHeight);
        paper.setImageableArea(0, 0, 226.77, totalHeight);

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
                g.setFont(headerFont);

                g.drawString(companyName, x, y);
                y += 2 * lineHeight;

                g.drawLine(0, y - 6, 226, y - 6);
                y += 2 * lineHeight;

                g.setFont(smallFont);
                g.drawString(cif, x, y);
                y += lineHeight;
                g.drawString(address, x, y);
                y += lineHeight;
                g.drawString(phone, x, y);
                y += lineHeight;

                g.drawString(dateFormat.format(date), x, y);
                y += lineHeight;

                for (String item : items) {
                    g.drawString(item, x, y);
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
