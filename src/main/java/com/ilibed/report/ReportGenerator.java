package com.ilibed.report;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface ReportGenerator {
    byte[] generateReport(int userId) throws IOException;
}
