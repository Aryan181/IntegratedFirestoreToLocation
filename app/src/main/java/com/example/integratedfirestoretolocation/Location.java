package com.example.integratedfirestoretolocation;


import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Location {
  private String lat ;
  private String lon;
  private String UserID;
  public Location(String x, String y)
  {
      this.lat=lat;
      this.lon=lon;
      this.UserID=UserID;
  }

    public static void setLon() {

    }
    public static void setLat() {
    }

    public String getLat()
  {
      return lat;
  }
  public String getLon()
  {
      return lon;
  }
  public String getUserID()
  {
      return UserID;
  }



}
