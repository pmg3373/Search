/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Pat
 */
public class SearchAlgorithms {
    
    public static String ResultsPrint(Map cities, City start, City end){
        City current = null;
        LinkedList<City> path = new LinkedList<>();
        
        current = end;
        int visited = -1;
        double dist = 0;
        int miles;
        while(true){
            path.addFirst(current);
            visited++;
            if(current == start)
                break;
            dist += current.ComputeDistance(current.previous);
            current = current.previous;
        }
        
        String out = "";
        miles = (int)(dist + .5);
        while(!path.isEmpty()){
            //out.concat(path.pop().cityName);
            out += path.pop().cityName + "\n";
        }
        out += ("That took " + visited + " hops to find.\n");
        out += ("Total distance = " + miles + " miles.\n");
        return out;
    }
    
    public static LinkedList<City> BreadthFirstSearch(Map cities, City start, City end){
        LinkedList<City> path = new LinkedList<City>();
        
        if(!cities.cities.containsValue(start) || !cities.cities.containsValue(end)){
            System.err.println("Start or end city does not exist");
            return null;
        }
        LinkedList<City> choices = new LinkedList<City>();
        City current = start;
        current.previous = null;
        current.visited = true;
        while(!end.visited){
            for(int i = 0; i < current.connections.size(); i++){
                if(!current.connections.get(i).dest.visited){
                    choices.addLast(current.connections.get(i).dest);
                    current.connections.get(i).dest.previous = current;
                    current.connections.get(i).dest.visited = true;
                }
            }
            current = choices.pop();
        }
        current = end;
        int visited = 0;
        while(current != start){
            path.addLast(current);
            visited++;
            current = current.previous;
        }
        return path;
    }
    
    public static LinkedList<City> DepthFirstSearch(Map cities, City start, City end){
        LinkedList<City> path = new LinkedList<>();
        if(!cities.cities.containsValue(start) || !cities.cities.containsValue(end)){
            System.err.println("Start or end city does not exist");
            return null;
        }
        LinkedList<City> choices = new LinkedList<City>();
        City current = start;
        current.previous = null;
        current.visited = true;
        while(!end.visited){
            for(int i = current.connections.size()-1; i > -1; i--){
                if(!current.connections.get(i).dest.visited){
                    choices.addFirst(current.connections.get(i).dest);
                    current.connections.get(i).dest.previous = current;
                    current.connections.get(i).dest.visited = true;
                }
            }
            current = choices.pop();
        }
        
        
        current = end;
        int visited = 0;
        int dist = 0;
        while(current != start){
            path.addLast(current);
            visited++;
            dist += current.ComputeDistance(current.previous);
            current = current.previous;
        }
        return path;
    }
    
    public static void AStarSearch(Map cities, City start, City end){
        ArrayList<City> choices = new ArrayList<>();
        
        choices.add(start);
        
        while(true){
            double minimum = Double.MAX_VALUE;
            City chosen = null;
            for(int i = 0; i < choices.size(); i++){
                if((choices.get(i).heuristic + choices.get(i).distTraveled)
                        < minimum){
                    minimum = (choices.get(i).heuristic + choices.get(i).distTraveled);
                    chosen = choices.get(i);
                }
            }
            
            choices.remove(chosen);
            
            if(chosen == end){
                break;
            }
            
            for(int i = 0; i < chosen.connections.size(); i++){
                if(!chosen.connections.get(i).dest.visited){
                    choices.add(chosen.connections.get(i).dest);
                    chosen.connections.get(i).dest.distTraveled = 
                            chosen.connections.get(i).dist + 
                           chosen.distTraveled;
                    chosen.connections.get(i).dest.visited = true;
                    chosen.connections.get(i).dest.previous = chosen;
                }
            }
        }
    }
    
}
