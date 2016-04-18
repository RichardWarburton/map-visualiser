package com.monotonic.map_visualiser;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

/**
 * A simple program that shows at the terminal, 
 * the structure of sample HashMap and TreeMap instances.
 * 
 * @author Manoel Campos da Silva Filho
*/
public class Sample {
    private final Console console;
    
    public Sample(){
        this.console = new Console();    
        printTreeMap();
        printHashMap();
    }
    
    private void printTreeMap(){
        TreeMap<String, Object> treeMap1 = new TreeMap<>();
        insertSampleMapData(treeMap1);
        System.out.println(treeMap1.getClass().getSimpleName());
        new TreeMapVisualiser(console).visualise(treeMap1);     
        System.out.println();           
    }
    
    private void printHashMap(){
        HashMap<String, Object> hashMap1 = new HashMap<>();
        insertSampleMapData(hashMap1);
        System.out.println(hashMap1.getClass().getSimpleName());
        new HashMapVisualiser(console).visualise(hashMap1);
        System.out.println();         
    }
    
    private void insertSampleMapData(Map map){
        map.put("name", "Manoel Campos");
        map.put("country", "Brazil");        
        map.put("age", 35);
        map.put("height", 1.8);
        map.put("gender", 'M');
        map.put("site", "http://github.com/manoelcampos");        
    }
    
    public static void main(String args[]){
        new Sample();
    }
}