/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HotelSearch {
    private Connection con;
    private String City, Hotels, RoomsType ,CheckInDays, CheckOutDays;
    private int adults ,children, kings, queens, suite, rooms, days;
    private double price1, price2, price3; 
    //private boolean directFlight;

    public HotelSearch(Connection con, String City, String Hotels, String RoomsType, String CheckInDays, String CheckOutDays, int adults, int rooms) {
        this.con = con;
        this.City = City;
        this.Hotels = Hotels;
        this.RoomsType = RoomsType;
        this.CheckInDays = CheckInDays;
        this.CheckOutDays = CheckOutDays;
        this.adults = adults;
        this.children = children;
        this.rooms = rooms;
    }

  
    
    
    public boolean findListHotels()
    {
        boolean select = false;
        
        try
        {
   
           if ( RoomsType == "King") { int Kings = 1; int Queen = 0; int Suite =0;};
           if ( RoomsType == "Queens"){int Kings = 0; int Queen = 1; int Suite =0;};
           if ( RoomsType == "Suite"){int Kings = 0; int Queen = 0; int Suite =1;};
          
          String sql = "SELECT * FROM hotel" 
                  + " WHERE city= '" + City + "' AND"
                  + " Hotels='" + Hotels + "' AND"
                  + " AVA1='" + CheckInDays + "' AND"
                  + " AVA2='" + CheckInDays + "' AND"
                  + " AVA3='" + CheckInDays + "' AND"
                  ;
            
          Statement st = con.createStatement();
          ResultSet rs;
          
          rs = st.executeQuery(sql);
          
          while( rs.next() )
          {
              select = true;
          }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return select;
    }
    
}
