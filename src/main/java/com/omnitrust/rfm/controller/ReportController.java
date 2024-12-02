package com.omnitrust.rfm.controller;

import com.omnitrust.rfm.domain.CatchInfo;
import com.omnitrust.rfm.service.CatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    private CatchService catchService;


    @PostMapping("/user")
    public ResponseEntity<?> getReleaseReport(@RequestBody Map<String, Object> payload) {
        try {
            LocalDate reportDate = LocalDate.parse(payload.get("date").toString(), DateTimeFormatter.ISO_DATE_TIME);
            List<CatchInfo> catches = catchService.getAllCatchForDayWithReleaseInfoNotNullByUser(reportDate);
            return ResponseEntity.status(HttpStatus.OK).body(catches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }

    @PostMapping("/rfm")
    public ResponseEntity<?> getReport(@RequestBody Map<String, Object> payload) {
        try {
            LocalDate reportDate = LocalDate.parse(payload.get("date").toString(), DateTimeFormatter.ISO_DATE_TIME);
            List<CatchInfo> list = catchService.getAllCatchForDayWithReleaseInfoNotNull(reportDate);
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
