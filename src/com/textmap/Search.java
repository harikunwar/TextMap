package com.textmap;

import java.util.ArrayList;

import android.content.Context;

public class Search{
	private String textLabel;
	xmlParser sxml;
	ArrayList<String> slist;
	
	public  Search(Context c){
		sxml= new xmlParser(c);
	}
	public ArrayList<String> searchT(String s){
		slist=sxml.getSearchGeolocations(s);
		
		return slist;
	}
	
}