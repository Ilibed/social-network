package com.ilibed.report;

import com.ilibed.user.UserActivity;
import com.ilibed.user.UserRepository;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class UserActivityReportGenerator implements ReportGenerator {

    private final UserRepository userRepository;

    @Autowired
    public UserActivityReportGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public byte[] generateReport(int userId) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        List<UserActivity> userActivities = userRepository.findUserActivities(userId);
        createRow(sheet, "Name", "Value", 0);
        int rowNumber = 1;
        for (UserActivity activity : userActivities) {
            createRow(sheet, activity.getActivityName(), activity.getActivityValue(), rowNumber++);
        }

        ByteOutputStream stream = new ByteOutputStream();
        workbook.write(stream);

        return stream.getBytes();
    }

    private void createRow(Sheet sheet, String name, String value, int rowNumber) {
        Row row = sheet.createRow(rowNumber);
        Cell nameCell = row.createCell(0);
        nameCell.setCellValue(name);
        Cell valueCell = row.createCell(1);
        valueCell.setCellValue(value);
    }
}
