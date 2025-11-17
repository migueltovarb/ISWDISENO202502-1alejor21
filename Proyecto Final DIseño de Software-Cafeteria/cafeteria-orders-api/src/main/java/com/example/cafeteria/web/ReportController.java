package com.example.cafeteria.web;

import com.example.cafeteria.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/daily")
    public Map<String, Object> daily(@RequestParam String date) {
        LocalDate d = LocalDate.parse(date);
        return reportService.dailyReport(d);
    }
}
