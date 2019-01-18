/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pat
 */
public class Connection {
    City dest;
    double dist;
    
    public Connection(City dest, double dist){
        this.dest = dest;
        this.dist = dist;
    }
    
    public String ToString(){
        return "" + dest + " " + dist;
    }
    
    public static int compareTo(Connection first, Connection second){
        return first.dest.cityName.compareTo(second.dest.cityName);
    }
}
