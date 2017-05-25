package com.ilibed.report;

import com.ilibed.cloud.CloudStorage;
import com.ilibed.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ReportService {

    private final UserRepository userRepository;

    @Autowired
    public ReportService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateActivityReport(int userId) throws IOException {
        ReportGenerator generator = new UserActivityReportGenerator(userRepository);
        byte[] reportBytes = generator.generateReport(userId);
        return uploadReport("activity", "xls", reportBytes);
    }

    private String uploadReport(String filename, String extension, byte[] data) throws IOException {
        File savedFile = new File(filename + "." + extension);
        Path path = savedFile.toPath();
        Files.write(path, data);
        String cloudPath = CloudStorage.uploadFile(savedFile);
        savedFile.delete();

        return cloudPath;
    }
}
