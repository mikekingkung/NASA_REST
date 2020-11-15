package com.rest.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class Converter {

    public ArrayList<String> convertData(File jsonInputFile) {
        ArrayList<String> images = new ArrayList<String>();
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
            while (itemsIterator.hasNext()) {
                JsonNode itemNode = itemsIterator.next();
                //System.out.println("\nExtracting href from item Node:");
                //JsonNode itemHrefNode = itemNode.path("href");
                //System.out.println(itemHrefNode.textValue());
                //images.add(itemHrefNode.textValue());

                JsonNode linksNodeArray = itemNode.path("links");
                Iterator<JsonNode> linksIterator = linksNodeArray.elements();
                System.out.println("\nProcessing item links node array:");

                while (linksIterator.hasNext()) {
                    JsonNode linkNode = linksIterator.next();
                    JsonNode linkHrefNode = linkNode.path("href");
                    System.out.println("\nExtracting href from link Node:");
                    System.out.println(linkHrefNode.textValue());
                    images.add("{\"image\""+": {\"id\":"+"\""+count+"\","+"\"url\": " +"\""+linkHrefNode.textValue()+"\"}}");
                    count++;
                    System.out.println("length of images" + images.size());


                }
            }

            JsonNode linkItemsNode = rootNode.path("items");
            Iterator<JsonNode> linkItemsItr = linkItemsNode.elements();
            //System.out.println("\nitems:");
            while (linkItemsItr.hasNext()) {
                JsonNode hrefLinkItem = linkItemsItr.next();
                JsonNode hrefNode = hrefLinkItem.path("href");
                System.out.println(hrefLinkItem.textValue());
                images.add("{\"image\""+": {\"id\":"+"\""+count+"\","+"\"url\": " +"\""+hrefLinkItem.textValue()+"\"}}");
                count++;
            }




        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return images;
    }
}



