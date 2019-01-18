/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author Pat
 */
public class Map {
    Hashtable<String, City> cities;
    
    public Map(String cityFilePath, String edgeFilePath) throws Exception{
        File cityFile = new File(cityFilePath);
        File edgeFile = new File(edgeFilePath);
        cities = new Hashtable<String, City>();
        
        try{
            BufferedReader in_city = 
                    new BufferedReader(new FileReader(cityFile));
            while(in_city.ready()){
                try{
                    City temp_city = new City(in_city.readLine());
                    cities.put(temp_city.cityName, temp_city);
                }
                catch(Exception e){
                    System.err.println("Read Error");
                    throw e;
                }
            }
            in_city.close();
        }
        catch(Exception e){
            System.err.println("File not found: city.dat");
            System.exit(0);
        }
        
        try{
            BufferedReader in_edge = 
                new BufferedReader(new FileReader(edgeFile));
            while(in_edge.ready()){
                String next = in_edge.readLine();
                String[] splitLine = next.split("\\s+");
                if(splitLine.length == 1 && (splitLine[0].compareTo("") == 0)){
                    
                }
                else if(splitLine.length == 2){
                    cities.get(splitLine[0]).AddConnection(cities.get(splitLine[1]));
                }
                else{
                    throw new Exception("ERROR: Unexpected File Format");
                }
            }
        }
        catch(Exception e){
            System.err.println("File not found: edge.dat");
            System.exit(0);
        }
        
        cities.forEach((k,v) -> v.SortConnections());
    }
    
    public void Clear(){
        cities.forEach((k,v) -> v.Clear());
    }
    
    public void SetHeuristic(City end){
        cities.forEach( (k,v) -> v.heuristic = v.ComputeDistance(end) );
    }
            
}
