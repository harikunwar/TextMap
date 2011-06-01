//Class will be used to draw levels of details over the map
//Class used to draw Array list content on a MapView which is then returned
package com.textmap;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class DrawObjects{
	Context dcontext;
	//TextMap gmap=null;//get xml data through geoLocationMapping class
	//TextMap tpanel;
	geoLocationMapping gmap=null;
	ArrayList<String> alist=new ArrayList<String>();//use this list to store parent data
	MapView map;
	//MapView map2=(MapView)findViewById(R.id.map1);
	
	//DrawPanel bpanel;//Draw objects on bottom panel
	LinearLayout lin=null;//object created just for creating an object of DrawPanel
	
	String label,property,glat,glon,id,parent;
	int geolat,geolon;
	GeoPoint point;
	LayoutParams LabelParams;
	
	//Get background image
	Drawable white_strip,door; 
	
	ImageView imgv;
	
	int t=7;//list items per level
	
	int level=0;//level of detail
	int lev=0;
	
	private MapController mc; //control map
	
	private LinearLayout bpanel;//bottom panel
	
	private TransparentPanel btpanel;//bottom transparent panel
	
	private LinearLayout gal_bottom;//bottom gallery
	
	private TransparentPanel bottom_scroll;// bottom scrolling menu
	
	//Count objects in every level[use in removing objects from map view based on count]
	private int lev2=0,lev3=0,lev4=0,lev5=0;
	
	
	//Constructor
	public DrawObjects(Context context,LinearLayout panel,TransparentPanel p,TransparentPanel h){
		dcontext=context;
		gmap= new geoLocationMapping(dcontext);
		white_strip =	dcontext.getResources().getDrawable(R.drawable.red); 
		door= dcontext.getResources().getDrawable(R.drawable.door);
		imgv= new ImageView(dcontext);
		bpanel=panel;//get bottom panel
		
		btpanel=p;//get bottom transparent panel
		
		bottom_scroll=h;//bottom scrolling menu
		
		//tpanel= new TextMap();//object of main activity class
	}
	
	//0 level of detail
	public MapView Draw(MapView m,  ArrayList<String> list,int level){
		//int j=0;
		alist=list;//avoiding final 
		
		map=m;//avoiding final
		
		Button img= new Button(dcontext);
		
		
		//loop through the list
	
		for(int i=0,j=0;i<alist.size()/t;i++,j=j+t){//t is number item per tags
		//for(int i=0;i<1;i++){
		/*Fetch geolocation from alist later*/
			id=(String)alist.get(j);
			parent=(String)alist.get(j+1);//parent node id 
			label=(String)alist.get(j+2);
			property=(String)alist.get(j+3);
			glat=(String)alist.get(j+4);
			glon=(String)alist.get(j+5);
			level=Integer.parseInt((String) alist.get(j+6));
			
			//for event listener
			lev=level;
			
			//get geolocation
			geolat=(int)(Double.parseDouble(glat)*1E6);
			geolon=(int)(Double.parseDouble(glon)*1E6);
			
			point = new GeoPoint(geolat,geolon);
			
				
			//Set parameters 
			LabelParams=new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT, 
			MapView.LayoutParams.WRAP_CONTENT, point, MapView.LayoutParams.CENTER);
			
			//Button imgb = new Button(dcontext);
			final ToggleButton tog= new ToggleButton(dcontext);//first click on, second click off 
			
			tog.setClickable(true);

			//Draw single buttons in level=2
			if(level==2){
				//tog.setTag(parent);
				tog.setTag(R.string.tag1,id);//store tag id in button(toggle) view
				tog.setTag(R.string.tag2,parent);//set tag with key value pair
				tog.setTag(R.string.tag3,level+"");//set level info with the button
				tog.setPadding(1,1,1,1);
				tog.setText(label);
				
				//tog.setHint(parent);//save parent info in hint text
				tog.setTextOff(label);
				tog.setTextOn(label);
				tog.setTextSize(15);
//				tog.setWidth(30);
				//tog.setBackgroundDrawable(white_strip);
				
//				if(property.equalsIgnoreCase("Door")){
//					imgb.setBackgroundDrawable(door);
//					map.addView(imgb,LabelParams);
//				}
				//add view 
				map.addView(tog,LabelParams);
				lev2++;// number of level 2 objects in map view
				
			}
			
			//if level 3 draw floor menu
			if(level==3){
				//create new button and add in mapview	
				//tog.setId(j);//set id to differentiate in event listener
				tog.setTag(R.string.tag1,id);//store tag id in button(toggle) view
				tog.setTag(R.string.tag2,parent);//set tag with key value pair
				tog.setTag(R.string.tag3,level+"");//set level info with the button

				tog.setPadding(2,2,2,2);
				tog.setText(" "+label+" ");
				tog.setTextOff(" "+label+" ");
				tog.setTextOn(" "+label+" ");
				tog.setTextSize(17);
//				tog.setWidth(30);
				//tog.setHint(parent);//save parent info in hint text
				//tog.setBackgroundDrawable(white_strip);
				
				//Open floor menu in bottom panel
				//tpanel.DrawPanel(dcontext, fmenu);
				/***Add tog to the bottom of screen***/
				//addItemPanel(tog);
				
				//bpanel.addView(tog);//only level 3[floors] objects are in panel
				/******/
				
				
				/***Bottom scrollable gallery***/

				

				bottom_scroll.addView(tog);
				
			}
			
			if(level==4){
				//create new button and add in mapview	
				tog.setTag(R.string.tag1,id);//store tag id in button(toggle) view
				tog.setTag(R.string.tag2,parent);//set tag with key value pair
				tog.setTag(R.string.tag3,level+"");//set level info with the button

				tog.setPadding(2,2,2,2);
				
				tog.setText(label);
				tog.setTextOff(label);
				tog.setTextOn(label);
				tog.setTextSize(15);
				tog.setTag(parent);
				
				//				tog.setWidth(30);
//				tog.setHeight(20);
				//tog.setBackgroundDrawable(white_strip);
				//add view 
				map.addView(tog,LabelParams);
				lev4++;//number of level 4 objects in map
			
			}
			
			//Draw search locations
			if(level==5){
				tog.setTag(R.string.tag1,id);//store tag id in button(toggle) view
				tog.setTag(R.string.tag2,parent);//set tag with key value pair
				tog.setTag(R.string.tag3,level+"");//set level info with the button

				tog.setPadding(2,2,2,2);
				
				tog.setText(label);
				tog.setTextOff(label);
				tog.setTextOn(label);
				tog.setTextSize(15);
				tog.setTag(parent);
				
				//				tog.setWidth(30);
//				tog.setHeight(20);
				//tog.setBackgroundDrawable(white_strip);
				//add view 
				map.addView(tog,LabelParams);
				lev4++;//number of level 4 objects in map

			}
		

			tog.setClickable(true);
			//Add event listener[show children data when clicked]
			tog.setOnClickListener(new OnClickListener() {
				public void onClick(View v) { 
					//mc = map.getController();
					//mc.animateTo(point); 
					//mc.zoomIn();
					
				
					/***Adding toggle functionality***/
					if(tog.isChecked()){
						btpanel.setVisibility(View.VISIBLE);//make bottom visible

						bottom_scroll.setVisibility(View.VISIBLE);//bottom scroll panel 
						
						/***Get new parent list[first test with old alist]***/
						//int k=tog.getId();
						//int lev=0;//2-buildings,3-floor menu,4-inside,5-more info 
						
						String pid=(String)tog.getTag(R.string.tag1);//get id form button
						ArrayList<String> blist=gmap.getTextLocation(pid);
						
						//Get level info
						String levtog=(String)tog.getTag(R.string.tag3);
						int l= Integer.parseInt(levtog);
						//test
						if(l!=3){
							removePanelItems();//only level 3[floors] objects are in panel
						}
						
						/***Try to implement recursive method***/
						//Check if panel is already full
						Draw(map,blist,lev);//call draw method with new parent list
						
						
						
						
					}
					else{
						btpanel.setVisibility(View.INVISIBLE);
						bottom_scroll.setVisibility(View.INVISIBLE);//bottom scroll panel 
						//main label should never be removed
						map.removeAllViews();//remove all views
						//remove level 2 and 4 objects based on count
						//map.removeViews(0,4);
						//remove items from bottom panel
						removePanelItems();//only level 3[floors] objects are in panel
						
						/***Get main labels first ***/
						//int k=tog.getId();
						//int lev=0;//2-buildings,3-floor menu,4-inside,5-more info 
						String mid="princeton";//get parent id form button
						ArrayList <String> mlist=gmap.getTextLocation(mid);
						/***Try to implement recursive method***/
						Draw(map,mlist,2);//call draw method with new parent list
				
						/***Get parent ***/
						//int k=tog.getId();
						//int lev=0;//2-buildings,3-floor menu,4-inside,5-more info 
						String pid=(String)tog.getTag(R.string.tag2);//get parent id form button
						ArrayList <String> blist=gmap.getTextLocation(pid);
						/***Try to implement recursive method***/
						Draw(map,blist,lev);//call draw method with new parent list
						
					}
					
					
					
					
					/***Code for drawing building boundaries
					 * [failed because 
					 * 1-Could handle path properly
					 * 2-PathShape didn't work perfectly
					 * ] ***/
						//Set zoom level and focus area
				} 
			});
			
			//j=j+4;//next values
			
		}	
		return map;
	
	}
	
	public void addItemPanel(ToggleButton tog){
		Button btn= new Button(dcontext);
		btn.setText("ddddd");
		bpanel.addView(tog);
	}
	
	//bpanel must be declared final to use inside event listener 
	public void removePanelItems(){
		bottom_scroll.removeAllViews();
		bpanel.removeAllViews();
	}
	
	//function will plot search result labels
	public void plotSearchItems(ArrayList<String> slist){
		
	}

	//ToogleButton tog on function
	
	//ToggelButton tog off function
	
}