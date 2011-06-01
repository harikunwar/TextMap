//Main activity class
package com.textmap;
import java.util.List;

import android.content.Context; 
import android.graphics.Color;
import android.graphics.drawable.Drawable; 
import android.location.Address; 
import android.location.Geocoder; 
import android.os.Bundle; 
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.maps.GeoPoint; 
import com.google.android.maps.MapActivity; 
import com.google.android.maps.MapController; 
import com.google.android.maps.MapView; 
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


//Data Structure
import java.util.HashMap;
import java.util.ArrayList;

public class TextMap extends MapActivity { 
	
	
	TextView tv;
	List<Overlay> mapOverlays; 
	
	//Class MyMarkerLayer
	MyMarkerLayer markerlayer;
	
	//Class geoLocationMapping
	geoLocationMapping geomap;
	
	//xmlParser class
	xmlParser par;
	
	//Class DrawObjects
	//DrawObjects drawobj;
	DrawObjects dob;
	
	//Search object
	Search sobj;
	ArrayList<String> slist;//search text list
	
	//MapView 
	MapView mapView;
	
	//Scroll menu bottom
	TransparentPanel bottom_scroll; 
	
	//Level of details 1- Collection of buildings 2- Building 3- Apartment
	int level=0;
	
	//Main map labels 
	private ArrayList<String> alist;
	
	private MapController mc; 
	public static Context mContext;
	
	//Text+geoLocation data 
//	HashMap hash= new HashMap();
//	ArrayList list= new ArrayList();
	
	/******/
	AutoCompleteTextView textView; 
	
	/***Test***/
	static final String[] COUNTRIES = new String[] {
		 "1","2","3","4","B","M","111"
		};

	TransparentPanel span;//top search panel
	TransparentPanel btpan;//bottom transparent panel
	//span.setVisibility(2);//make transparent panel invisible
	LinearLayout search;
	//search.setVisibility(2);
	public ToggleButton sbtn;
	Drawable simg; 
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 

		//Get current activity context
		mContext = this; 
		
		//Get view items from layout
		setContentView(R.layout.main);
		mapView = (MapView) findViewById(R.id.map1);
		//Visual overlay over the map image
		//mapOverlays = mapView.getOverlays();
		
		/*******/
		textView = new AutoCompleteTextView(mContext);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, COUNTRIES);
	    textView.setAdapter(adapter);
	    textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0, 0));
	    textView.setThreshold(1);
		
		
		/***Search Panel***/
		
		sobj=new Search(mContext);//Search object
		
		 span= (TransparentPanel)findViewById(R.id.transparent_panel_top);
		//span.setVisibility(2);//make transparent panel invisible
		 search= (LinearLayout)findViewById(R.id.search);
		//search.setVisibility(2);
		 sbtn= (ToggleButton)findViewById(R.id.searchImg);
		 simg =	mContext.getResources().getDrawable(R.drawable.sicon); 
		sbtn.setBackgroundDrawable(simg);
		sbtn.setTextOn("");
		sbtn.setText("");
		sbtn.setTextOff("");
		
	  
	    sbtn.setOnClickListener(new OnClickListener(){
	    		public void onClick(View v){
	    			//if(sbtn.isChecked()){
	    				searchPanel();//function to toggle search panel
	    			//}
	    			
	    		}
	    		
	    	}	
	    );
	    
		/***Bottom Panel***/
		LinearLayout fmenu= (LinearLayout)findViewById(R.id.fmenu);
		btpan=(TransparentPanel)findViewById(R.id.bt_panel);
		

		/****Bottom Scrolling menu****/
		bottom_scroll=(TransparentPanel)findViewById(R.id.bpanel);
		
		
	
		/***Get geoLocations***/
		
		//1 Option :- Use a data structure like list or hashmap to store all objects and their geolocations
		//2 Option :- Use XML file to storing objects
		//geoLocation does'nt care about source of data xml or Mysql
		geomap= new geoLocationMapping(mContext);
		
		par= new xmlParser(mContext);
		
		/*Fetch geolocation from database later*/
		int geolat = (int)(42.96154*1E6); 
		int geolon = (int)(-78.81778*1E6);
		//Create geopoint
		GeoPoint point = new GeoPoint(geolat,geolon); 
		
		/***Get main map geolocations from xml***/
		//Show text and geolocations
		//Display Hashmap content
		alist=getParentData("princeton");//call function to get children's node data
		/***End of main map geoLocation***/

	
		/***Draw text labels and symbols***/
		dob=new DrawObjects(mContext,fmenu,btpan,bottom_scroll);//pass bottom panel also
		
		/***Get Main labeled map view****/
		level=0;//level of detail
		mapView=dob.Draw(mapView,alist,level);//Draw main map
		
		
		
		/***Set Map view properties***/
		
		mapView.setBuiltInZoomControls(false);
		mapView.setVisibility(1);//make invisible
		mapView.setSatellite(true);

		mc = mapView.getController();
		
		mc.animateTo(point); 
		mc.setZoom(20);
	
		/***End of Mapview settings***/

		/***Add to map view***/
		 //mapView.addView(enter, LabelParams);
		
		
		
	}
	@Override 
	protected boolean isRouteDisplayed() { return false; }
	
	//All classes get data through main class[loosely coupled]
	public ArrayList<String> getParentData(String pid){
		/***Get geolocations from xml***/
		//Show text and geolocations
		//Display Hashmap content
		//hash=geomap.getTextLocation(pid);
		ArrayList<String> alist= geomap.getTextLocation(pid);//send parent tag id
		//mapView.addView(tv);
		/***End of geoLocation***/

		return alist;
	}
	
	//function to toggle search panel
	public void searchPanel(){
			
		if(sbtn.isChecked()){
			span.setVisibility(View.VISIBLE);
			search.setVisibility(View.VISIBLE);//set visibility
			search.addView(textView);
		}
		else{
		
	
			//search entered text
			String s=(String)textView.getText().toString(); 
			//String s =(String)newTxt.toString();

			
			
			slist=sobj.searchT(s);//search text in text box
			
			//Draw searched locations
			mapView.removeAllViews();
			mapView=dob.Draw(mapView, alist,2);//Draw main map again
			mapView=dob.Draw(mapView,slist,5);//Draw[add] search locations
		
			//sobj.searchT(textView.getText().toString());
			search.setVisibility(View.INVISIBLE);
			span.setVisibility(View.INVISIBLE);
			search.removeAllViews();
		}
	}
	

	//Draw bottom Panel and populate with objects
//	public void DrawPanel(Context c,String fm){
//		Button btn= new Button(c);
//		btn.setText("aaaaa");
//		Button btn1= new Button(c);
//		btn1.setText("aaaa");
//		
//		fmenu.addView(btn);
//		fmenu.addView(btn1);		
//	}
//	
}