package com.sof_eng.Controller.FileHandler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class CodeController {
    @PostMapping("/saveToRemote")
    public String saveToRemote(@RequestBody String code) throws UnsupportedEncodingException {
        String decodedCode = URLDecoder.decode(code, "UTF-8");
        int length=decodedCode.length()-1;
        decodedCode=decodedCode.substring(0,length);
        try (FileWriter writer = new FileWriter("TEMP.py")) {
            writer.write(decodedCode);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error saving code: " + e.getMessage();
        }
        return "Code saved successfully";
    }
}
