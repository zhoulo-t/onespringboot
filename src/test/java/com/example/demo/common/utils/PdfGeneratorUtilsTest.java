//package com.example.demo.common.utils;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.context.Context;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class PdfGeneratorUtilsTest {
//
//    @Mock
//    private SpringTemplateEngine templateEngine;
//
//    private PdfGeneratorUtils pdfGeneratorUtils;
//
//    private static final String HTML_TEMPLATE =
//            "<!DOCTYPE html>" +
//            "<html xmlns:th=\"http://www.thymeleaf.org\">" +
//            "<head>" +
//                "<title>Test Document</title>" +
//                "<style>" +
//                    "body { font-family: Arial, sans-serif; }" +
//                    ".content { margin: 20px; }" +
//                "</style>" +
//            "</head>" +
//            "<body>" +
//                "<div class=\"content\">" +
//                    "<h1>%s</h1>" +
//                    "<p>This is a test document.</p>" +
//                "</div>" +
//            "</body>" +
//            "</html>";
//
//    @BeforeEach
//    void setUp() {
//        pdfGeneratorUtils = new PdfGeneratorUtils(templateEngine);
//    }
//
//    @Test
//    void generatePdfFromHtml_WithValidTemplate_ShouldReturnPdfBytes() {
//        // Given
//        String templateName = "job_data";
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("title", "Test Title");
//
//        String mockHtmlContent = String.format(HTML_TEMPLATE, "Test Title");
//        when(templateEngine.process(eq(templateName), any(Context.class))).thenReturn(mockHtmlContent);
//
//        // When
//        byte[] result = pdfGeneratorUtils.generatePdfFromHtml(templateName, variables);
//
//        // Then
//        assertNotNull(result);
//        assertTrue(result.length > 0);
//    }
//
//    @Test
//    void generatePdfFromHtml_WithNullVariables_ShouldReturnPdfBytes() {
//        // Given
//        String templateName = "job_data";
//        String mockHtmlContent = String.format(HTML_TEMPLATE, "Simple Content");
//        when(templateEngine.process(eq(templateName), any(Context.class))).thenReturn(mockHtmlContent);
//
//        // When
//        byte[] result = pdfGeneratorUtils.generatePdfFromHtml(templateName, null);
//
//        // Then
//        assertNotNull(result);
//        assertTrue(result.length > 0);
//    }
//
//    @Test
//    void generatePdfFromHtml_WithEmptyVariables_ShouldReturnPdfBytes() {
//        // Given
//        String templateName = "job_data";
//        Map<String, Object> variables = new HashMap<>();
//        String mockHtmlContent = String.format(HTML_TEMPLATE, "Empty Variables Test");
//        when(templateEngine.process(eq(templateName), any(Context.class))).thenReturn(mockHtmlContent);
//
//        // When
//        byte[] result = pdfGeneratorUtils.generatePdfFromHtml(templateName, variables);
//
//        // Then
//        assertNotNull(result);
//        assertTrue(result.length > 0);
//    }
//
//    @Test
//    void generatePdfFromHtml_WhenTemplateEngineThrowsException_ShouldThrowRuntimeException() {
//        // Given
//        String templateName = "job_data";
//        when(templateEngine.process(eq(templateName), any(Context.class)))
//                .thenThrow(new RuntimeException("Template processing failed"));
//
//        // When & Then
//        assertThrows(RuntimeException.class, () ->
//            pdfGeneratorUtils.generatePdfFromHtml(templateName, null)
//        );
//    }
//
//    @Test
//    void generatePdfFromHtml_WithComplexVariables_ShouldReturnPdfBytes() {
//        // Given
//        String templateName = "job_data";
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("title", "Complex Test");
//        variables.put("number", 123);
//        variables.put("isEnabled", true);
//
//        String mockHtmlContent =
//            "<!DOCTYPE html>" +
//            "<html xmlns:th=\"http://www.thymeleaf.org\">" +
//            "<head>" +
//                "<title>Complex Test Document</title>" +
//                "<style>" +
//                    "body { font-family: Arial, sans-serif; }" +
//                    ".content { margin: 20px; }" +
//                "</style>" +
//            "</head>" +
//            "<body>" +
//                "<div class=\"content\">" +
//                    "<h1>Complex Test</h1>" +
//                    "<p>Number: 123</p>" +
//                    "<p>Enabled: true</p>" +
//                    "<table border=\"1\">" +
//                        "<tr>" +
//                            "<th>Field</th>" +
//                            "<th>Value</th>" +
//                        "</tr>" +
//                        "<tr>" +
//                            "<td>Test Field</td>" +
//                            "<td>Test Value</td>" +
//                        "</tr>" +
//                    "</table>" +
//                "</div>" +
//            "</body>" +
//            "</html>";
//        when(templateEngine.process(eq(templateName), any(Context.class))).thenReturn(mockHtmlContent);
//
//        // When
//        byte[] result = pdfGeneratorUtils.generatePdfFromHtml(templateName, variables);
//
//        // Then
//        assertNotNull(result);
//        assertTrue(result.length > 0);
//    }
//}