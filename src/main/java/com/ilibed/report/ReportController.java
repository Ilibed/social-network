package com.ilibed.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/api/print/{printName}/{userId}")
    @ResponseBody
    public ResponseEntity<String> updateUser(@PathVariable("printName") String name,
                                             @PathVariable("userId") Integer userId) {

        try {
            String reportPath = reportService.generateActivityReport(userId);
            return new ResponseEntity<>(reportPath, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
