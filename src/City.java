/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.Math;
import java.util.ArrayList;

/**
 *
 * @author Pat
 */
public class City {
    String separator = "\\s+";
    String cityName, state;
    double latitude, longitude;
    ArrayList<Connection> connections;
    double heuristic, distTraveled;
    boolean visited;
    City previous;
    
    public static Double tryParseDouble(String d){
        double dp;
        try{
            dp = Double.parseDouble(d);
        }
        catch(Exception e){
            return null;
        }
        return dp;
    }
    
    public City(String city, String state, double latitude, double longitude){
        this.cityName = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        connections = new ArrayList();
    }
    
    public City(String cityLine) throws Exception{
        
        String[] line = cityLine.split(separator);
        if(line.length != 4){
            System.err.println("ERROR: Line of unexpected size");
            throw new Exception("Line of unexpected size");
        }
        
        String temp_city, temp_state;
        double temp_lat, temp_long;
        Double temp_parse;
        
        temp_city = line[0];
        temp_state = line[1];
        
        temp_parse = tryParseDouble(line[2]);
        if(temp_parse != null){
            temp_lat = temp_parse;
        }
        else{
            System.err.println("ERROR: Latitude was NaN");
            throw new Exception("Latitude was NaN");
        }
        
        temp_parse = tryParseDouble(line[3]);
        if(temp_parse != null){
            temp_long = temp_parse;
        }
        else{
            System.err.println("ERROR: Longitude was NaN");
            throw new Exception("Longitude was NaN");
        }
        
        this.cityName = temp_city;
        this.state = temp_state;
        this.latitude = temp_lat;
        this.longitude = temp_long;
        connections = new ArrayList();
    }
    
    public void SortConnections(){
        connections.sort((a,b) -> Connection.compareTo(a, b));
    }
    
    public void AddConnection(City dest, double dist, boolean re){
        connections.add(new Connection(dest, dist));
        if(re){
            dest.AddConnection(this, dist, false);
        }
    }
    
    public void AddConnection(City dest){
        double dist = ComputeDistance(dest);
        this.connections.add(new Connection(dest, dist));
        
        dest.AddConnection(this, dist, false);
    }
    
    public double ComputeDistance(City c2){
        return Math.sqrt( (Math.pow( (this.latitude - c2.latitude), 2.0) + 
                Math.pow( (this.longitude - c2.longitude), 2.0))) * 100.0;
    }
    
    public void Clear(){
        this.previous = null;
        this.visited = false;
        this.heuristic = 0;
    }
    
    public String ToString(){
        String out = "City: " + this.cityName + " State: " + this.state +
                " Latitude: " + this.latitude + " Longitude: " + this.longitude
                + "\n Connections: ";
        for(int i = 0; i < this.connections.size(); i++){
            out.concat("\n" + this.connections.get(i).ToString());
        }
        
        return out;
    }
}

