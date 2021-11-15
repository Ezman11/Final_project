package com.web.project.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;


import java.nio.file.Paths;

@Service
public class QRcodeGenerator {
    public String QR(String id) {
        try {

            String directory = Paths.get("").toAbsolutePath().toString();
            String text = id;
            String path = directory+"/.Qrcode/Qrcode" + id + ".jpg";

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 500, 500);

            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

            return path;

        } catch (Exception e) {
            System.out.println("Error while creating barcode");
            return "Error while creating barcode";
        }
    }
}

