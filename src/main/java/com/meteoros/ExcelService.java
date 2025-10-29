package com.meteoros;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {

    public void writeDeviceAccessExcel(List<DeviceBasic> devices) {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("DeviceAccess");

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Device Name");
            header.createCell(1).setCellValue("Access Code");

            // Fill data rows
            int rowIndex = 1;
            for (DeviceBasic device : devices) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(device.getDeviceName());
                row.createCell(1).setCellValue(device.getAccessToken());
            }

            // Auto-size
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            // File location → root of project
            String filePath = Paths.get("").toAbsolutePath() + "/device_access.xlsx";

            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();

            System.out.println("\nExcel Generated Successfully!");
            System.out.println("Saved at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
