package fr.martdel;

import java.util.List;
import java.util.Map;

public class Main {

    private static final String FILENAME = "/home/martin/Documents/dev/Java/TheseusProto/src/fr/martdel/res/recipes.xml";

    public static void main(String[] args) {
        Map<String, Recipe> recipes = Recipe.getAllRecipe(FILENAME, true);
        if(!recipes.isEmpty()){
//            for (String name : recipes.keySet()) {
//                Recipe current_recipe = recipes.get(name);
//                int count = current_recipe.getCount();
//                String label = current_recipe.getLabel();
//                List<List<Item>> requires = current_recipe.getRequires();
//                System.out.print(name + " (" + count + " - " + label + ") [ ");
//                for(List<Item> options : requires){
//                    if (options.size() > 1) {
//                        System.out.print("[ ");
//                        for(Item current_option : options)
//                            System.out.print((current_option.getName() + "," + current_option.getCount()) + " ");
//                        System.out.print("] ");
//                    } else System.out.print((options.get(0).getName() + "," + options.get(0).getCount()) + " ");
//                }
//                System.out.println("]");
//            }
        } else {
            System.out.println("No recipe has been parsed...");
        }
    }
}
