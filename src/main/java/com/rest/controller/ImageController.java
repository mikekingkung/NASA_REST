package com.rest.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
public class ImageController {

    @RequestMapping(value = "/getstaticimage/{image}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public ResponseEntity<byte[]> getImage(@PathVariable String image) throws IOException {
        String fileName = "src/main/resources/" + image;
        File imgFile = new File(fileName);
        //File initialFile = new File("src/main/resources/sample.txt");
        InputStream targetStream = new FileInputStream(imgFile);
        //ClassPathResource imgFile = new ClassPathResource("D:/Documents/Java/NASA_REST/src/image/marscopter.jpg");
        byte[] bytes = StreamUtils.copyToByteArray(targetStream);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}




