package com.pos.reporting.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.util.*;

/**
 * JasperReports basics: compile once, fill with data, export to PDF.
 * Used for receipts, invoices, inventory reports, sales summaries.
 */
public class JasperReportsBasics {

    // Compile report at startup (cache the compiled report)
    private final JasperReport receiptReport;

    public JasperReportsBasics() throws JRException {
        receiptReport = JasperCompileManager.compileReport(
            getClass().getResourceAsStream("/reports/receipt.jrxml")
        );
    }

    // Generate receipt PDF
    public byte[] generateReceipt(Sale sale) throws JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("RECEIPT_NUMBER", sale.receiptNumber());
        params.put("CASHIER", sale.cashierName());
        params.put("DATE", sale.date());
        params.put("TOTAL", sale.total());
        params.put("REPORT_DATA_SOURCE", new JRBeanCollectionDataSource(sale.lineItems()));

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            JasperPrint print = JasperFillManager.fillReport(receiptReport, params, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(print, out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Receipt generation failed", e);
        }
    }

    // Generate inventory report
    public byte[] generateInventoryReport(List<Product> products) throws JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("REPORT_DATE", new Date());
        params.put("REPORT_DATA_SOURCE", new JRBeanCollectionDataSource(products));

        JasperPrint print = JasperFillManager.fillReport(receiptReport, params, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(print);
    }

    record Sale(String receiptNumber, String cashierName, Date date, double total, List<LineItem> lineItems) {}
    record LineItem(String name, int qty, double price) {}
    record Product(String name, int qty, double price) {}
}
