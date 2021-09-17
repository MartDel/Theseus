package fr.martdel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe {

    private final String name;
    private final int count;
    private final String label;
    private final List<List<Item>> requires;


    public Recipe(String name, int count, String label, List<List<Item>> requires) {
        this.name = name;
        this.count = count;
        this.label = label;
        this.requires = requires;
    }

    public static Map<String, Recipe> getAllRecipe(String filename){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Map<String, Recipe> recipes = new HashMap<>();

        try {
            // Add a security
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // Parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList recipes_node = root.getChildNodes();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            for (int i = 0; i < recipes_node.getLength(); i++) {
                Node current_recipe_node = recipes_node.item(i);
                if (current_recipe_node.getNodeType() == Node.ELEMENT_NODE){
                    Element current_recipe = (Element) current_recipe_node;

                    // Get recipe basics data
                    String name = current_recipe.getNodeName();
                    int count = Integer.parseInt(current_recipe.getElementsByTagName("count").item(0).getTextContent());
                    NodeList label_node = current_recipe.getElementsByTagName("label");
                    String label = label_node.getLength() > 0 ? label_node.item(0).getTextContent() : null;

                    // Get recipe requires
                    List<List<Item>> requires = new ArrayList<>();
                    NodeList requires_node = current_recipe.getElementsByTagName("requires").item(0).getChildNodes();
                    for (int j = 0; j < requires_node.getLength(); j++) {
                        Node current_require_node = requires_node.item(j);
                        if (current_require_node.getNodeType() == Node.ELEMENT_NODE){
                            Element current_require = (Element) current_require_node;
                            NodeList options_node = current_require.getElementsByTagName("options");
                            List<Item> options = new ArrayList<>();
                            if (options_node.getLength() > 0){
                                // The current require can be more than only one item
                                int require_count = Integer.parseInt(current_require.getElementsByTagName("amount").item(0).getTextContent());
                                options_node = options_node.item(0).getChildNodes();
                                for (int k = 0; k < options_node.getLength(); k++) {
                                    Node current_option_node = options_node.item(k);
                                    if (current_option_node.getNodeType() == Node.ELEMENT_NODE) {
                                        String option_name = current_option_node.getTextContent();
                                        options.add(new Item(option_name, require_count));
                                    }
                                }
                            } else {
                                // The current require can only be one item
                                NodeList tag_node = current_require.getElementsByTagName("tag");
                                boolean has_tag = tag_node.getLength() > 0;
                                String require_name = has_tag ?
                                        tag_node.item(0).getTextContent() :
                                        current_require.getElementsByTagName("name").item(0).getTextContent();
                                int require_count = Integer.parseInt(current_require.getElementsByTagName("amount").item(0).getTextContent());
                                Item current_item = new Item(require_name, require_count);
                                current_item.setIsTag(has_tag);
                                options.add(current_item);
                            }
                            requires.add(options);
                        }
                    }

//                    // Debug
//                    System.out.print(name + " (" + count + " - " + label + ") [ ");
//                    for(List<Item> options : requires){
//                        if (options.size() > 1) {
//                            System.out.print("[ ");
//                            for(Item current_option : options)
//                                System.out.print((current_option.getName() + "," + current_option.getCount()) + " ");
//                            System.out.print("] ");
//                        } else System.out.print((options.get(0).getName() + "," + options.get(0).getCount()) + " ");
//                    }
//                    System.out.println("]");

                    recipes.put(name, new Recipe(name, count, label, requires));
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getLabel() {
        return label;
    }

    public List<List<Item>> getRequires() {
        return requires;
    }
}
