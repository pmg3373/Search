/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Pat
 */
public class Search {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map map = null;
        City start, end;
        String startName, endName;
        Scanner input = null;
        boolean inputFileMode = false;
        
        
        if(args.length != 2){
            System.out.println("Usage: java Search inputFile outputFile");
            System.exit(0);
        }
        
        
        try{
            map = new Map("city.dat", "edge.dat");
        }
        catch(Exception e){
            System.err.println("Error reading setup files");
            System.exit(0);
        }

        
        if(args[0].compareTo("-") == 0){
            input = new Scanner(System.in);
        }
        else{
            inputFileMode = true;
            try{
                input = new Scanner(new File(args[0]));
            }
            catch(Exception e){
                System.err.println("File not found: " + args[0]);
                System.exit(0);
            }
        }
        
        
        if(!inputFileMode)
            System.out.println("Start City?");
        startName = input.nextLine();

        if(!map.cities.containsKey(startName)){
            System.err.println("No such city: " + startName);
            System.exit(0);
        }
        start = map.cities.get(startName);

        
        if(!inputFileMode)
            System.out.println("End City?");
        endName = input.nextLine();

        if(!map.cities.containsKey(endName)){
            System.err.println("No such city: " + endName);
            System.exit(0);
        }
        end = map.cities.get(endName);
        input.close();
        
        
        LinkedList<City> bfsList, dfsList, astarList;
        String bfsResults, dfsResults, astarResults;
        
        map.SetHeuristic(end);
        bfsList = SearchAlgorithms.BreadthFirstSearch(map, start, end);
        bfsResults = SearchAlgorithms.ResultsPrint(map, start, end);
        map.Clear();
        
        map.SetHeuristic(end);
        dfsList = SearchAlgorithms.DepthFirstSearch(map, start, end);
        dfsResults = SearchAlgorithms.ResultsPrint(map, start, end);
        map.Clear();
        
        map.SetHeuristic(end);
        SearchAlgorithms.AStarSearch(map, start, end);
        astarResults = SearchAlgorithms.ResultsPrint(map, start, end);
        map.Clear();
        
        if(args[1].compareTo("-") == 0){
            System.out.println("Breadth-First Search Results:");
            System.out.println(bfsResults);
            
            System.out.println("Depth-First Search Results:");
            System.out.println(dfsResults);
            
            System.out.println("A* Search Results:");
            System.out.println(astarResults);
        }
        else{
            try{
                PrintWriter out = new PrintWriter(args[1], "UTF-8");
                out.println("Breadth-First Search Results:");
                out.println(bfsResults);
                
                out.println("Depth-First Search Results:");
                out.println(dfsResults);
                
                out.println("A* Search Results:");
                out.println(astarResults);
                out.close();
            }
            catch(Exception e){
                System.err.println("Cannot create output file: " + args[1]);
                System.exit(0);
            }
        }
        
    }
    
}
