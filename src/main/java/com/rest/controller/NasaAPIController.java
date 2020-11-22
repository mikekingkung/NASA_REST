package com.rest.controller;

import com.rest.filemaker.FileMaker;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import com.rest.converter.Converter;
import com.rest.filemaker.FileMaker;
import java.io.File;
import java.util.ArrayList;


@CrossOrigin(origins = "*")
@RestController
@EnableAutoConfiguration
public class NasaAPIController {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @RequestMapping("/getimages")
    @ResponseBody
    String hello() {
        RestTemplate restTemplate = new RestTemplate();
        //String data = restTemplate.getForObject(
        //        "https://images-api.nasa.gov/search?q=shuttle", String.class);
//			String quote = restTemplate.getForObject(
//					"https://api.nasa.gov/neo/rest/v1/neo/3542519?api_key=DEMO_KEY", String.class);
        //FileMaker fileMaker = new FileMaker(data);
        File jsonInputFile = new File("D:/Documents/Java/Consumer/src/main/resources/nasafile.json");
        Converter converter = new Converter();
        ArrayList<String> images = converter.convertData(jsonInputFile);
        //log.info(data.toString());
        //Convert convert = new Convert();
        //convert.convertData(quote.toString());

        return images.toString();
    }
}




