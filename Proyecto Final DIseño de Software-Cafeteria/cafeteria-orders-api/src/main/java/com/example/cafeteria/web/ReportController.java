package com.example.cafeteria.web;

import com.example.cafeteria.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    // ⬇⬇⬇ Constructor explícito
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // HU008: reporte diario de ventas
    @GetMapping("/daily")
    public Map<String, Object> dailyReport(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return reportService.dailyReport(date);
    }
}
