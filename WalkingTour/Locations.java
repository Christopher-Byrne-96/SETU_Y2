package WalkingTour;

// Class for location objects this will hold the name and longs + lats for the objects
public class Locations {

	//Constructor to create the object
private static int totalLocations = 0;
private String name = "";
private double lng = 0.0;
private double lat = 0.0;

//Empty constructor needed to initialize the array of objects in the main method
public Locations(String name, double lng, double lat) {
	this.name = name;
	this.lng = lng;
	this.lat = lat;
	totalLocations++;
}

// Bellow are some getter methods used by other methods when referencing the instance of the object
public Locations() {
	name = "";
	lng = 0;
	lat = 0;		
}


 public String getName() {
	 return name;
 }
 
 
 
 public double getLng() {
	 return lng;
 }
 
 
 
 public double getLat() {
	 return lat;
 }
 
 
 public int getNumLocations() {
	 return totalLocations;
 }
}
