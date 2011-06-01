//Using DOM for getting xml data
//Use xml for storing geolocations and corresponding text label 
package com.textmap;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import java.util.HashMap;
import java.util.ArrayList;
public class xmlParser {

	//private XmlResourceParser xrp;
	private String id,parent,label,geoLon,geoLat,property;
	
	String rootId="princeton";
	Context conn;
	//Data structures
	//private HashMap<?, ?> hash= new HashMap();
	private ArrayList<String> list= new ArrayList<String>();
	public xmlParser(Context con){	
		//xrp = con.getResources().getXml(R.xml.test);
		conn=con;
	}
	
	//function will search for a root node and all children data in an arraylist
	//Function will get all child nodes attributes from given parent with id=pid
	public ArrayList<String> getTextGeolocations(String pid){
		//clear list
		list.removeAll(list);
		//Having problem with parser position 
		XmlResourceParser xrp = conn.getResources().getXml(R.xml.test);
		try {
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				
				if (xrp.getEventType() == XmlResourceParser.START_TAG) {
					
					//String s = xrp.getName();
					
					//Search for root node and get all of its children in Hashmap
					//Get buildings info only
					
					String s = xrp.getIdAttribute();
					
					//Opening any node should show direct children nodes only and not grand children nodes
					if(s.equals(pid)){//root="Princeton_Ave" store child node of root
						String label,geoLon,geoLat;
						int pdepth=xrp.getDepth();
						xrp.next();
						
					//	s=xrp.getName();
						while(xrp.getDepth()!=pdepth){//traverse till the end of root
						//Problem is getAttributName check closing tag also Hence error
							if(xrp.getEventType()==XmlResourceParser.START_TAG){
								if(xrp.getDepth()==pdepth+1){//only child nodes
									id=xrp.getIdAttribute();
									parent=xrp.getAttributeValue(1);
									label =xrp.getAttributeValue(2);
									property=xrp.getAttributeValue(3);
									geoLon=xrp.getAttributeValue(4);
									geoLat=xrp.getAttributeValue(5);
									
									list.add(id);
									list.add(parent);//parent id 
									list.add(label);
									list.add(property);
									list.add(geoLon);
									list.add(geoLat);
									//depth level(String)
									list.add(pdepth+"");//parent depth
								}
								
							}
							
							if(xrp.getEventType()==XmlResourceParser.END_TAG){
								//xrp.next();
							}
							
							xrp.next();
						//	s=xrp.getName();
							
						}

						/**fill hashmap**/
						//hash.put("princeton", list);
						
						/***Stop traversing XML document***/
						break;

					}//end of if( root tag)
					
					/*if(s.equals(root)){//found matching tag to root[currently building]
						
						
						if(root!="building"){
							xrp.next();
							s=xrp.getName();
							
							//Loop till closing root tag reached
							while(!s.equals(root)){
							//Problem is getAttributName check closing tag also Hence error
								
								if(xrp.getEventType()==XmlResourceParser.START_TAG){
									label =xrp.getAttributeValue(0);
									property=xrp.getAttributeValue(1);
									geoLon=xrp.getAttributeValue(2);
									geoLat=xrp.getAttributeValue(3);
									list.add(label);
									list.add(property);
									list.add(geoLon);
									list.add(geoLat);
								}
								
								if(xrp.getEventType()==XmlResourceParser.END_TAG){
								}
								xrp.next();
								s=xrp.getName();
								
							}//end of while

						}//end of if
						else{//get all buildings information
								label =xrp.getAttributeValue(0);
								property=xrp.getAttributeValue(1);
								geoLon=xrp.getAttributeValue(2);
								geoLat=xrp.getAttributeValue(3);
								list.add(label);
								list.add(property);
								list.add(geoLon);
								list.add(geoLat);
						}//end of else
						
					}//end of main if
					*/
				} 
				
				else if (xrp.getEventType() == XmlResourceParser.END_TAG) {
					;
				}
				
				else if (xrp.getEventType() == XmlResourceParser.TEXT) { 
					// Get our value from the plaintag element. 
					// Since this is a value and not an
					// attribute, we retrieve it with the 
					// generic .getText().
					String s1 = xrp.getText();
				}
				
				xrp.next();
			
			}//end of while
			
			xrp.close();
			
		}//end of try
		catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
					
		//return arraylist[Handle HashMap in seperate class]
		return list;
	
	}//end of parse function

	//Get geolocation and other information about a given text label
	public ArrayList<String> getSearchGeolocations(String pid){
		//clear list
		list.removeAll(list);
		//Having problem with parser position 
		XmlResourceParser xp = conn.getResources().getXml(R.xml.test);
		try {
			while (xp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				
				if (xp.getEventType() == XmlResourceParser.START_TAG) {
					
					//String s = xrp.getName();
					
					//Search for root node and get all of its children in Hashmap
					//Get buildings info only
					String s=xp.getAttributeValue(2);//get label value of tag
					
					//label value of tag should be equal to search text label
					if(s.equals(pid)){//root="Princeton_Ave" store child node of root
						String label,geoLon,geoLat;
						int pdepth=xp.getDepth();
						//xp.next();
						id=xp.getIdAttribute();
						parent=xp.getAttributeValue(1);
						label =xp.getAttributeValue(2);
						property=xp.getAttributeValue(3);
						geoLon=xp.getAttributeValue(4);
						geoLat=xp.getAttributeValue(5);
						
						list.add(id);
						list.add(parent);//parent id 
						list.add(label);
						list.add(property);
						list.add(geoLon);
						list.add(geoLat);
						//depth level(String)
						list.add(pdepth+"");//parent depth
					}
								
						//	s=xrp.getName();
				}//end of if( root tag)
					
			
				
				else if (xp.getEventType() == XmlResourceParser.END_TAG) {
					;
				}
				
				else if (xp.getEventType() == XmlResourceParser.TEXT) { 
					// Get our value from the plaintag element. 
					// Since this is a value and not an
					// attribute, we retrieve it with the 
					// generic .getText().
					//String s1 = xrp.getText();
				}
				
				xp.next();
			
			}//end of while
			
			xp.close();
			
		}//end of try
		catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
					
		//return arraylist[Handle HashMap in seperate class]
		return list;
	
	}//end of parse function
	
}