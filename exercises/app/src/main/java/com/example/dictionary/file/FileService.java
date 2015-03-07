package com.example.dictionary.file;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.UUID;

@Component
public class FileService {

    private static Logger log = Logger.getLogger(FileService.class);

    public String createFile(String data) {
        UUID id = UUID.randomUUID();
        String filename = "/tmp/wordfile-" + id.toString();

        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("Saved file: "  + filename);
        return filename;
    }

}
