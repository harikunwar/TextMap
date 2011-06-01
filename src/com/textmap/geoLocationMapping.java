//Class for giving mapping between visual item on map and its geolocation
//Class can use SQL database or XML for getting mapping information
//[Outside class are not effected by implementation method used] 

package com.textmap;
import java.util.HashMap;
import java.util.ArrayList;

import android.content.Context;
public class geoLocationMapping{
	//constructor
	HashMap<String, ArrayList<String>> hash=new HashMap<String,ArrayList<String>>();;
	ArrayList<String> list;
	
	//Get xml parser object
	xmlParser par;
	String level;
	public geoLocationMapping(Context con){
		 par= new xmlParser(con);
	}
	
	//return HashMap containing Arraylist holding all data
	public ArrayList<String> getTextLocation(String pid){
		//level 1
//		if(loc=="Princeton_Ave"){
//			//get all buildings
//			level="building";
//		}
		
		list=par.getTextGeolocations(pid);
		//hash.put(pid,list);
		return list;
	}
	
	public void getLocations(int level){
		
	}

}