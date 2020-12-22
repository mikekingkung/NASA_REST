package com.rest.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class Converter {
    int LINK_PROCESSING_LIMIT = 2;

    public ArrayList<String> convertData(File jsonInputFile) {
        ArrayList<String> mediaCollection = new ArrayList<String>();
        ArrayList<String> collections = new ArrayList<String>();
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
            System.out.println("\nProcessing collection Node:");
            JsonNode collectionNode = rootNode.path("collection");
            // read links collectionNode.items
            System.out.println("\nProcessing items Node:");
            JsonNode itemsNodeArray = collectionNode.path("items");

            Iterator<JsonNode> itemsIterator = itemsNodeArray.elements();

            System.out.println("\nProcessing items node array:");
            int linkProcessedCount = 0;
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
                    System.out.println("Adding a collection");
                    System.out.println("collection: " + itemNodeLink.textValue());
                    collections.add(itemNodeLink.textValue());
                }

                System.out.println("Collections" + collections);
                mediaCollection =  getCollection(collections, linkProcessedCount, title, description);
                mediaList.addAll(mediaCollection);
                if (linkProcessedCount > LINK_PROCESSING_LIMIT){
                    System.out.println("Reached processing limit");
                    break;
                }
                linkProcessedCount++;
            }

            System.out.println("media list" + mediaList);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mediaList;
    }

    private ArrayList<String> getCollection(ArrayList<String> urlList, int linkProcessedCount, String title, String description) {
        // Get the collection data for the item and extract mp4 and image urls
        ArrayList<String> linkList = new ArrayList<String>();
        RestTemplate restTemplate = new RestTemplate();

        boolean found = false;
        System.out.println("urlList" + urlList);
        // go through each collection.json and get the urls contained
        ArrayList<String> data = new ArrayList<String>();

        for (String url : urlList) {
            System.out.println("url: " + url);
            try {
                data = restTemplate.getForObject(
                        url, ArrayList.class);
            }
            catch(Exception ex){
                System.out.println("Exception on collection url" + url);
            }
            for (String linkUrl: data){
                if (((!linkList.contains(data))) && (linkUrl.contains(".mp4") && (linkUrl.contains("large")))) {
                    System.out.println("mp4 added:" + data);
                    linkList.add("{\"video\"" + ": {\"id\":" + "\"" + linkProcessedCount + "\"," + "\"url\": " + "\"" + linkUrl  + "\"," + "\"title\": " + "\"" + title + "\"," + "\"description\": " + "\"" + description  + "\"}}");
                    // As soon as we find a large mp4 then exit processing
                    found = true;
                }
                else if (((!linkList.contains(data))) && (linkUrl.contains(".jpg") && (linkUrl.contains("large")))) {
                    System.out.println("jpg added:" + data);
                    linkList.add("{\"image\"" + ": {\"id\":" + "\"" + linkProcessedCount  + "\"," + "\"url\": " + "\"" + linkUrl + "\"," + "\"title\": " + "\"" + title + "\"," + "\"description\": " + "\"" + description  + "\"}}");
                    // As soon as we find a large mp4 then exit processing
                    found = true;
                }
                System.out.println("linkProcessedCount" + linkProcessedCount);
                linkProcessedCount++;
                if (linkProcessedCount > LINK_PROCESSING_LIMIT){
                    System.out.println("Reached processing limit");
                    break;
                }
            }
        }
        return linkList;
    }
}



