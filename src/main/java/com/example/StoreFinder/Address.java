package com.example.StoreFinder;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class Address {
	
	private String street1;
	
	private String street2;
	
	private String city;
	
	private String postcode;
	
	private String longitude;
	
	private String latitude;
	
	//private Location geoLocation;

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
		System.out.println("Inside setPOst Code");
	}

	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) throws Exception {
		if (longitude == null) {
		System.out.println("Inside longitude is null and getLonglat");
		String latLongs[] = gettLatLongPositions(getPostcode());
		this.longitude  = latLongs[0];	
		} else {
			System.out.println("Inside else of setLongitude");
			this.longitude  = longitude;
		}
		
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) throws Exception {
		if (latitude == null) {
			String latLongs[] = gettLatLongPositions(getPostcode());
			this.latitude  = latLongs[1];	
			} else {
				this.latitude  = latitude;
			}
	}

	
	
	public String[] gettLatLongPositions(String postcode) throws Exception {
			
		int responseCode = 0;
		
		System.out.println("Inside get Long lat function");
		
		String api= "https://maps.googleapis.com/maps/api/geocode/xml?address=" 
		+ URLEncoder.encode(postcode, "UTF-8") + "&sensor=true&key=AIzaSyDJbLGTbsdtvqhozUisQUQ6WudzBOYfI30";
		URL url = new URL(api);
		
		HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
		httpConnection.connect();
	    responseCode = httpConnection.getResponseCode();
	    
	    System.out.println("Inside get Long lat function responsecode" + responseCode);
	    
	    if(responseCode == 200)
	    {
	      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	      Document document = builder.parse(httpConnection.getInputStream());
	      XPathFactory xPathfactory = XPathFactory.newInstance();
	      XPath xpath = xPathfactory.newXPath();
	      XPathExpression expr = xpath.compile("/GeocodeResponse/status");
	      
	      String status = (String)expr.evaluate(document, XPathConstants.STRING);
	      
	      System.out.println("Inside get Long lat function Status" + status);
	      
	      if(status.equals("OK"))
	      {
	         expr = xpath.compile("//geometry/location/lat");
	         System.out.println("Inside response code OK" + expr);
	         String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         expr = xpath.compile("//geometry/location/lng");
	         String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         return new String[] {latitude, longitude};
	      }
	      else
	      {
	         throw new Exception("Error from the API - response status: "+status);
	      }
	    }
		return null;			
					
	}
	

}
