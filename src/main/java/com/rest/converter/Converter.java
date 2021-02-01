package com.rest.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class Converter {
    int LINK_PROCESSING_LIMIT = 10;
    int linkProcessedCount = 0;

    public ArrayList<String> convertData(File jsonInputFile) {
        ArrayList<String> mediaCollection = new ArrayList<String>();
        String collection = null;
        ArrayList<String> mediaList = new ArrayList<String>();

        try {
            // reading json input from the file and mapping to object
            // collection.href
            // collection.items (array)
            // traverse through collection.items and for each item get links
            // for each links get href
            // traverse through links and for each link get href
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonInputFile);
            // read collection
            //System.out.println("\nProcessing collection Node:");
            JsonNode collectionNode = rootNode.path("collection");
            // read links collectionNode.items
            //System.out.println("\nProcessing items Node:");
            JsonNode itemsNodeArray = collectionNode.path("items");

            Iterator<JsonNode> itemsIterator = itemsNodeArray.elements();

            //System.out.println("\nProcessing items node array:");
            while (itemsIterator.hasNext()) {
                String title = "";
                String description = "";
                JsonNode dataZeroNode = null;
                JsonNode itemNode = itemsIterator.next();


                JsonNode itemNodeLink = itemNode.path("href");
                JsonNode dataNodeArray = itemNode.path("data");
                Iterator<JsonNode> dataNodeIterator = dataNodeArray.elements();
                while (dataNodeIterator.hasNext()) {
                    dataZeroNode = dataNodeIterator.next();
                    // Get the title for the item
                    // Get the description for the item
                    JsonNode titleNodeLink = dataZeroNode.path("title");
                    JsonNode descriptionNodeLink = dataZeroNode.path("description");
                    if (titleNodeLink.textValue() != null) {
                        title = titleNodeLink.textValue();}

                    if (descriptionNodeLink.textValue() != null) {
                        description = descriptionNodeLink.textValue();}
                }

                //get the collection url for the item href
                if (itemNodeLink.textValue().contains("collection")) {
                    collection = itemNodeLink.textValue();
                }

                //System.out.println("Collections" + collections);
                mediaCollection =  getCollection(collection, title, description);
                mediaList.addAll(mediaCollection);
                if (linkProcessedCount > LINK_PROCESSING_LIMIT){
                    System.out.println("Reached processing limit");
                    break;
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mediaList;
    }

    private ArrayList<String> getCollection(String collectionUrl, String title, String description) {
        // Get the collection data for the item and extract mp4 and image urls
        ArrayList<String> linkList = new ArrayList<String>();
        RestTemplate restTemplate = new RestTemplate();

        boolean found = false;
        System.out.println("getting data for collection:" + collectionUrl);
        // go through each collection.json and get the urls contained
        ArrayList<String> data = new ArrayList<String>();
        title = title.replace("\"", "\\\"");
        description = description.replace("\"", "\\\"");

        System.out.println("collectionUrl: " + collectionUrl);
        try {
            data = restTemplate.getForObject(
                    collectionUrl, ArrayList.class);
        }
        catch(Exception ex){
            System.out.println("Exception on collection url" + collectionUrl);
        }



        for (String linkUrl: data){
            if (((!linkList.contains(data))) && (linkUrl.contains(".mp4"))) {
                System.out.println("mp4 added:" + linkUrl);
                linkList.add("{\"video\"" + ": {\"id\":" + "\"" + linkProcessedCount + "\"," + "\"url\": " + "\"" + linkUrl  + "\"," + "\"title\": " + "\"" + title + "\"," + "\"description\": " + "\"" + description  + "\"}}");
                // As soon as we find a large mp4 then exit processing
                found = true;
                System.out.println("linkProcessedCount" + linkProcessedCount);
                linkProcessedCount++;
                break;
            }
            else if (((!linkList.contains(data))) && (linkUrl.contains(".jpg") && (linkUrl.contains("large")))) {
                System.out.println("jpg added:" + linkUrl);
                linkList.add("{\"image\"" + ": {\"id\":" + "\"" + linkProcessedCount  + "\"," + "\"url\": " + "\"" + linkUrl + "\"," + "\"title\": " + "\"" + title + "\"," + "\"description\": " + "\"" + description  + "\"}}");
                // As soon as we find a large mp4 then exit processing
                System.out.println("linkProcessedCount" + linkProcessedCount);
                linkProcessedCount++;
                break;
            }

        }
        return linkList;
    }
}



