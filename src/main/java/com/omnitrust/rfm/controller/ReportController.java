package com.omnitrust.rfm.controller;

import com.omnitrust.rfm.domain.CatchInfo;
import com.omnitrust.rfm.domain.ReleaseInfo;
import com.omnitrust.rfm.service.CatchService;
import com.omnitrust.rfm.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    private CatchService catchService;

    @Autowired
    private ReleaseService releaseService;

    @PostMapping("/user")
    public ResponseEntity<?> getCatchReport(@RequestBody Map<String, Object> payload) {
        try {
            LocalDate reportDate = LocalDate.parse(payload.get("date").toString(), DateTimeFormatter.ISO_DATE_TIME);
            List<CatchInfo> catches = catchService.getCatchInfoForDayByUser(reportDate);
            List<ReleaseInfo> releases = releaseService.getReleaseInfoForDay(reportDate);
            Map<String, List<?>> map = new HashMap<>();
            map.put("catches", catches);
            map.put("releases", releases);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }


    @PostMapping("/rfm")
    public ResponseEntity<?> getReleaseReport(@RequestBody Map<String, Object> payload) {
        try {
            LocalDate reportDate = LocalDate.parse(payload.get("date").toString(), DateTimeFormatter.ISO_DATE_TIME);
            List<CatchInfo> catches = catchService.getAllCatchForDay(reportDate);
            return ResponseEntity.status(HttpStatus.OK).body(catches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
