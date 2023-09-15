package com.pendataan.workshop.utils;

import org.springframework.http.MediaType;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;

public class FileUtils {

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static MediaType getMediaTypeForDocName(ServletContext servletContext, String docName) {
        String mineType = servletContext.getMimeType(docName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
