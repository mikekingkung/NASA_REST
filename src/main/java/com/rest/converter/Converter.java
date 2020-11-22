package com.rest.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class Converter {

    public ArrayList<String> convertData(File jsonInputFile) {
        ArrayList<String> mediaCollection = new ArrayList<String>();
        ArrayList<String> collections = new ArrayList<String>();
        ArrayList<String> mp4LinksList = new ArrayList<String>();
        ArrayList<String> mediaList = new ArrayList<String>();
        int LINK_PROCESSING_LIMIT = 50;
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

            //JsonNode itemsHrefNode = itemsNodeArray.path("href");

            Iterator<JsonNode> itemsIterator = itemsNodeArray.elements();
            System.out.println("\nProcessing items node array:");
            int count = 1;
            int linkProcessedCount = 0;


            while (itemsIterator.hasNext()) {
                JsonNode itemNode = itemsIterator.next();
                //System.out.println("\nExtracting href from item Node:");
                //JsonNode itemHrefNode = itemNode.path("href");
                //System.out.println(itemHrefNode.textValue());
                //images.add(itemHrefNode.textValue());

                JsonNode itemNodeLink = itemNode.path("href");
                // Get the title for the item
                // Get the description for the item
                if (itemNodeLink.textValue().contains("title")) {
                    mediaList.add("{\"title\"" + ": {\"id\":" + "\"" + linkProcessedCount + "\"," + "\"title\": " + "\"" + itemNodeLink.textValue() + "\"}}");
                } else if (itemNodeLink.textValue().contains("description")) {
                    mediaList.add("{\"description\"" + ": {\"id\":" + "\"" + linkProcessedCount + "\"," + "\"description\": " + "\"" + itemNodeLink.textValue() + "\"}}");
                }
                //get the collection url for the item href
                if (itemNodeLink.textValue().contains("collection")) {
                    System.out.println("Adding a collection");
                    System.out.println("collection: " + itemNodeLink.textValue());
                    collections.add(itemNodeLink.textValue());
                }
                linkProcessedCount++;
                if (linkProcessedCount > LINK_PROCESSING_LIMIT){
                    System.out.println("Reached processing limit");
                    break;
                }



/*
                JsonNode linksNodeArray = itemNode.path("links");
                Iterator<JsonNode> linksIterator = linksNodeArray.elements();
                System.out.println("\nProcessing item links node array:");

                while (linksIterator.hasNext()) {
                    JsonNode linkNode = linksIterator.next();
                    JsonNode linkHrefNode = linkNode.path("href");
                    System.out.println("\nExtracting href from link Node:");
                    System.out.println(linkHrefNode.textValue());
                    images.add("{\"image\"" + ": {\"id\":" + "\"" + count + "\"," + "\"url\": " + "\"" + linkHrefNode.textValue() + "\"}}");
                    count++;
                    System.out.println("length of images" + images.size());


                }

                JsonNode linkItemsNode = rootNode.path("items");
                Iterator<JsonNode> linkItemsItr = linkItemsNode.elements();
                //System.out.println("\nitems:");
                while (linkItemsItr.hasNext()) {
                    JsonNode hrefLinkItem = linkItemsItr.next();
                    JsonNode hrefNode = hrefLinkItem.path("href");
                    System.out.println(hrefLinkItem.textValue());
                    images.add("{\"image\"" + ": {\"id\":" + "\"" + count + "\"," + "\"url\": " + "\"" + hrefLinkItem.textValue() + "\"}}");
                    count++;
                }
*/
            }
            System.out.println("Collections" + collections);
            mediaList =  getCollection(collections);
            System.out.println("media list" + mediaList);
            // Adding any video or image found from collections
            mediaCollection.addAll(mediaList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mediaCollection;
    }

    private ArrayList<String> getCollection(ArrayList<String> urlList) {
        // Get the collection data for the item and extract mp4 and image urls
        ArrayList<String> linkList = new ArrayList<String>();
        RestTemplate restTemplate = new RestTemplate();
        int linkCount = 1;
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
            //if (found) break;
            // for each url add any that have large and mp4 in the url
            for (String linkUrl: data){
                if (((!linkList.contains(data))) && (linkUrl.contains(".mp4") && (linkUrl.contains("large")))) {
                    System.out.println("mp4 added:" + data);
                    linkList.add("{\"video\"" + ": {\"id\":" + "\"" + linkCount + "\"," + "\"url\": " + "\"" + linkUrl + "\"}}");
                    linkCount++;
                    // As soon as we find a large mp4 then exit processing
                    found = true;
                    //break;
                }
                else if (((!linkList.contains(data))) && (linkUrl.contains(".jpg") && (linkUrl.contains("large")))) {
                    System.out.println("jpg added:" + data);
                    linkList.add("{\"image\"" + ": {\"id\":" + "\"" + linkCount + "\"," + "\"url\": " + "\"" + linkUrl + "\"}}");
                    linkCount++;
                    // As soon as we find a large mp4 then exit processing
                    found = true;
                    //break;
                }
                System.out.println("linkCount" + linkCount);
            }
        }
        return linkList;
    }
}



