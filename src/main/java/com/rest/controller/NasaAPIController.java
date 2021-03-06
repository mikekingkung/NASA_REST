package com.rest.controller;

import com.rest.filemaker.FileMaker;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.rest.converter.Converter;

import java.io.File;
import java.util.ArrayList;



@CrossOrigin(origins = "*")
@RestController
public class NasaAPIController {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @RequestMapping(path = "/getimages/{searchterm}/{processinglimit}", method = RequestMethod.GET)
            public String hello(@PathVariable String searchterm, @PathVariable int processinglimit) {
            System.out.println("searchTerm" + searchterm);
            System.out.println("processinglimit" + processinglimit);
            RestTemplate restTemplate= new RestTemplate();
            String data = restTemplate.getForObject(
                "https://images-api.nasa.gov/search?q="+searchterm, String.class);
//			String quote = restTemplate.getForObject(
//				"https://api.nasa.gov/neo/rest/v1/neo/3542519?api_key=DEMO_KEY", String.class);
        FileMaker fileMaker = new FileMaker(data);
        File jsonInputFile = new File("src/main/resources/nasafile.json");
        Converter converter = new Converter();
        ArrayList<String> images = converter.convertData(jsonInputFile, processinglimit);
        //log.info(data.toString());
        //Convert convert = new Convert();
        //convert.convertData(quote.toString());

        return images.toString();
    }


}




