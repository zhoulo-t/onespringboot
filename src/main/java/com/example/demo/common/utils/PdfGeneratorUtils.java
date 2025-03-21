package com.example.demo.common.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PdfGeneratorUtils {

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public PdfGeneratorUtils(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static final Font CONTENT_FONT = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

    /**
     * 从HTML模板生成PDF并返回字节数组
     * @param templateName 模板名称
     * @param variables 模板变量
     * @return PDF字节数组
     */
    public byte[] generatePdfFromHtml(String templateName, Map<String, Object> variables) {
        try {
            // 处理模板
            Context context = new Context();
            if (variables != null) {
                context.setVariables(variables);
            }
            String htmlContent = templateEngine.process(templateName, context);

            // 创建PDF渲染器
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();

            // 输出到字节数组
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("生成PDF失败", e);
        }
    }

    /**
     * 从HTML字符串生成PDF并返回字节数组
     * @param htmlContent HTML内容
     * @return PDF字节数组
     */
    public byte[] generatePdfFromHtmlContent(String htmlContent) {
        try {
            // 创建PDF渲染器
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();

            // 输出到字节数组
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            log.error("生成PDF失败", e);
            throw new RuntimeException("生成PDF失败", e);
        }
    }

    /**
     * 生成工作数据PDF报告
     * @param dataList 数据列表
     * @param outputPath 输出路径
     * @return 是否生成成功
     */
    public boolean generateJobDataPdf(List<Map<String, String>> dataList, String outputPath) {
        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // 添加标题
            addTitle(document);

            // 创建表格
            PdfPTable table = createTable();

            // 添加表头
            addTableHeader(table);

            // 添加数据行
            for (Map<String, String> data : dataList) {
                addTableRow(table, data);
            }

            document.add(table);
            return true;

        } catch (Exception e) {
            log.error("生成PDF文件失败", e);
            return false;
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Job Data Report", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
    }

    private PdfPTable createTable() throws DocumentException {
        PdfPTable table = new PdfPTable(7); // 7列
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1.5f, 1.5f, 2f, 1f, 1f, 2.5f, 2.5f});
        return table;
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = {"Job ID", "Date", "Reference 1", "Weight", "Size", "Location", "Notes"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }

    private void addTableRow(PdfPTable table, Map<String, String> data) {
        addCell(table, data.get("jobId"));
        addCell(table, data.get("date"));
        addCell(table, data.get("reference"));
        addCell(table, data.get("weight"));
        addCell(table, data.get("size"));
        addCell(table, data.get("location"));
        addCell(table, data.get("notes"));
    }

    private void addCell(PdfPTable table, String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content != null ? content : "", CONTENT_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        table.addCell(cell);
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        map.put("jobId", "2878262");
//        map.put("date", "18/03/2025");
//        map.put("reference", "6411865290");
//        map.put("weight", ".29900");
//        map.put("size", "40HQ");
//        map.put("location", "ENVIRA WEST BROMWICH B70 7JR");
//        map.put("notes", "NOTES: LOAD REF ENV13341 General");
//
//        List<Map<String, String>> testData = new ArrayList<>();
//        testData.add(map);
//
//        // 生成PDF
//        PdfGeneratorUtils generator = new PdfGeneratorUtils();
//        boolean success = generator.generateJobDataPdf(testData, "D:\\job_data_report.pdf");
//        System.out.println("PDF生成" + (success ? "成功" : "失败"));

        // 生成PDF
//        PdfGeneratorUtils generator = new PdfGeneratorUtils(null);
//        byte[] pdfBytes = generator.generatePdfFromHtml("job_data", null);
//        System.out.println("PDF生成成功，字节数组长度：" + pdfBytes.length);

    }
} 