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
import java.util.HashMap;
import java.util.Map;

public class PrinterDemo {
    private final Map<String, Double> itemsWithPrices;
    private final Date date;

    public PrinterDemo(Map<String, Double> itemsWithPrices) {
        this.itemsWithPrices = itemsWithPrices;
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
        int staticContentHeight = 6 * 14;
        int dynamicContentHeight = itemsWithPrices.size() * 12;
        int totalHeight = staticContentHeight + dynamicContentHeight + 2 * 12; // Add extra space for the total

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
                int lineHeight = 14;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Font smallFont = new Font("Arial", Font.PLAIN, 11);
                Font headerFont = new Font("Arial", Font.BOLD, 13);
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

                for (Map.Entry<String, Double> entry : itemsWithPrices.entrySet()) {
                    g.drawString(entry.getKey(), x, dynamicContentY);
                    int priceX = 150;
                    g.drawString(String.format("%.2f", entry.getValue()), priceX, dynamicContentY);
                    dynamicContentY += lineHeight;
                }

                double total = itemsWithPrices.values().stream().mapToDouble(Double::doubleValue).sum();
                g.setFont(headerFont);
                int totalX = 100;
                g.drawString("Total:", totalX, dynamicContentY);
                g.setFont(smallFont);
                int totalAmountX = 150;
                g.drawString(String.format("%.2f", total), totalAmountX, dynamicContentY);

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
        Map<String, Double> itemsWithPrices = new HashMap<>();
        itemsWithPrices.put("Cachapa con queso", 9.0);
        itemsWithPrices.put("Tequeños", 7.0);
        itemsWithPrices.put("Hamburguesa Pasapalos", 12.0);
        itemsWithPrices.put("Coca-cola", 1.8);

        PrinterDemo printer = new PrinterDemo(itemsWithPrices);
        printer.print();
    }
}
