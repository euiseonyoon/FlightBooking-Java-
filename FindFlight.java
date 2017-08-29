
import java.sql.Connection;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lee
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FindFlight {
    
    private Connection con;
    private String destination, departure, date, departureState, destinationState;
    private int adultFirst, adultBusiness, childFirst, childBusiness ;
    private boolean directFlight = false;
    
    public List<List<String>> IndirectList1 = new ArrayList<List<String>>();
    public List<List<String>> IndirectList2 = new ArrayList<List<String>>();
    public List<List<String>> IndirectList3 = new ArrayList<List<String>>();
    
    public List<List<String>> directFlightList = new ArrayList<List<String>>();
    
    public FindFlight(Connection con, String departure, String destination
                      ,String dateString, int adultFirst, int adultBusiness,
                      int childFirst, int childBusiness, String departureState,
                      String destinationState)
    {
       this.con = con;
       this.departure = departure;
       this.destination = destination;
       this.date = dateString;
       this.adultFirst = adultFirst;
       this.adultBusiness = adultBusiness;
       this.childFirst = childFirst;
       this.childBusiness = childBusiness;
       this.departureState = departureState;
       this.destinationState = destinationState;
      
    }
    
    public int getAdultFirst()
    {
        return adultFirst;
    }
    
    public int getChildFirst()
    {
        return childFirst;
    }
    
    public int getAdultBusiness()
    {
        return adultBusiness;
    }
    
    public int getChildBusiness()
    {
        return childBusiness;
    }
    
    public boolean getDirectFlight()
    {
        return directFlight;
    }
    
    public List<List<String>> getDirectList()
    {
        return directFlightList;
    }
    
    public List<List<String>> getIndirectList1()
    {
        return IndirectList1;
    }
    
    public List<List<String>> getIndirectList2()
    {
        return IndirectList2;
    }
    
    public List<List<String>> getIndirectList3()
    {
        return IndirectList3;
    }
    
    
    public void findDirectFlight()
    {  
        try
        {         
          String sql = "SELECT * FROM flightschedule" 
                  + " WHERE depAirport= '" + departure + "' AND"
                  + " desAirport='" + destination + "' AND"
                  + " DepartureDate='" + date + "' AND"
                  + " FirstClassSeat >= " + (adultFirst + childFirst)+ " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness + childBusiness);
            
          Statement st = con.createStatement();
          ResultSet rs;
          
          rs = st.executeQuery(sql);
          
          directFlightList.add(new ArrayList<String>());  
          int i = 0; 
          
          while( rs.next() )
          {
              //System.out.println("! found");
              
              directFlightList.get(i).add(rs.getString(1));//flight code
              directFlightList.get(i).add(rs.getString(2));//Departure
              directFlightList.get(i).add(rs.getString(3));//Destination
              directFlightList.get(i).add(rs.getString(4));//Departure date
              directFlightList.get(i).add(rs.getString(5));//ArriveDate
              directFlightList.get(i).add(rs.getString(6));//Departe time
              directFlightList.get(i).add(rs.getString(7));//Arrive time
              directFlightList.get(i).add(rs.getString(8));//firstclass seat
              directFlightList.get(i).add(rs.getString(9));//business seat
              directFlightList.get(i).add(rs.getString(10));//firstclass price
              directFlightList.get(i).add(rs.getString(11));//busiess price
              directFlightList.get(i).add(rs.getString(12));//first discount rate
              directFlightList.get(i).add(rs.getString(13));//business discount
              
              directFlightList.add(new ArrayList<String>());
              i++;
              
              directFlight = true;
          }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void findIndirectFlight() throws ParseException
    {
        String fromHubAirport = null, toHubAirport = null;
        
        //List<List<String>> finalList = new ArrayList<List<String>>();
        
        try{
            String sql = "SELECT * FROM hubairport" 
                  + " WHERE state= '" + departureState + "'";
            
            Statement st = con.createStatement();
            ResultSet rs;
          
            rs = st.executeQuery(sql);
            
            while( rs.next())
            {
                fromHubAirport = rs.getString("hubAirport");
            }
            
            //System.out.println(fromHubAirport);
            
            sql = "SELECT * FROM hubairport" 
                  + " WHERE state= '" + destinationState + "'";
            
            rs = st.executeQuery(sql);
            
            while( rs.next())
            {
                toHubAirport = rs.getString("hubAirport");
            }
            
            //System.out.println(toHubAirport);
            
            //Finding flight from hub airport to non hub airport
            if ( departure.equals(fromHubAirport) && !destination.equals(toHubAirport) )
            {   
                sql = "SELECT * FROM flightschedule" 
                  + " WHERE depAirport= '" + departure + "' AND"
                  + " desAirport='" + toHubAirport + "' AND"
                  + " DepartureDate='" + date + "' AND"
                  + " FirstClassSeat >= " + (adultFirst + childFirst)+ " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness + childBusiness);
            
                rs = st.executeQuery(sql);
                
                IndirectList1.add(new ArrayList<String>());  
                int i = 0;
                
                while (rs.next())
                {
                    IndirectList1.get(i).add(rs.getString(1));
                    IndirectList1.get(i).add(rs.getString(2));
                    IndirectList1.get(i).add(rs.getString(3));
                    IndirectList1.get(i).add(rs.getString(4));
                    IndirectList1.get(i).add(rs.getString(5));
                    IndirectList1.get(i).add(rs.getString(6));
                    IndirectList1.get(i).add(rs.getString(7));
                    IndirectList1.get(i).add(rs.getString(8));
                    IndirectList1.get(i).add(rs.getString(9));
                    IndirectList1.get(i).add(rs.getString(10));
                    IndirectList1.get(i).add(rs.getString(11));
                    IndirectList1.get(i).add(rs.getString(12));
                    IndirectList1.get(i).add(rs.getString(13));
              
                    IndirectList1.add(new ArrayList<String>());
                    i++;
                }
                
                String date2;  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance(); 
                c.setTime(sdf.parse(date));
                c.add(Calendar.DATE, 1);  
                date2 = sdf.format(c.getTime());  
                
                sql = "SELECT * FROM flightschedule" 
                  + " WHERE (depAirport= '" + toHubAirport + "' AND"
                  + " desAirport='" + destination + "' AND"
                  + " DepartureDate='" + date + "' AND"
                  + " FirstClassSeat >= " + (adultFirst + childFirst) + " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness + childBusiness) + ")" 
                  + " OR ( depAirport= '" + toHubAirport + "' AND"
                  + " desAirport='" + destination + "' AND"
                  + " DepartureDate='" + date2 + "' AND"
                  + " FirstClassSeat >= " + (adultFirst + childFirst)+ " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness + childBusiness) + ")";
                
                rs = st.executeQuery(sql);
                
                IndirectList2.add(new ArrayList<String>());  
                
                i = 0;
                while (rs.next())
                {
                    IndirectList2.get(i).add(rs.getString(1));
                    IndirectList2.get(i).add(rs.getString(2));
                    IndirectList2.get(i).add(rs.getString(3));
                    IndirectList2.get(i).add(rs.getString(4));
                    IndirectList2.get(i).add(rs.getString(5));
                    IndirectList2.get(i).add(rs.getString(6));
                    IndirectList2.get(i).add(rs.getString(7));
                    IndirectList2.get(i).add(rs.getString(8));
                    IndirectList2.get(i).add(rs.getString(9));
                    IndirectList2.get(i).add(rs.getString(10));
                    IndirectList2.get(i).add(rs.getString(11));
                    IndirectList2.get(i).add(rs.getString(12));
                    IndirectList2.get(i).add(rs.getString(13));
              
                    IndirectList2.add(new ArrayList<String>());
                    i++;
                }
                /*
                for(List<String> list : IndirectList1)
          {
              for(String val : list) 
                  System.out.println(val + "");
          }
        
        for(List<String> list : IndirectList2)
          {
              for(String val : list) 
                  System.out.println(val + "");
          }*/
            
            }
            
            //Finding flight from non-hub airport to hub airport
            if ( !departure.equals(fromHubAirport) && destination.equals(toHubAirport) )
            {   
                sql = "SELECT * FROM flightschedule" 
                  + " WHERE depAirport= '" + departure + "' AND"
                  + " desAirport='" + fromHubAirport + "' AND"
                  + " DepartureDate='" + date + "' AND"
                  + " FirstClassSeat >= " + (adultFirst+childFirst) + " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness+childBusiness);
            
                rs = st.executeQuery(sql);
                
                IndirectList1.add(new ArrayList<String>());  
                int i = 0;
                
                while (rs.next())
                {
                    IndirectList1.get(i).add(rs.getString(1));
                    IndirectList1.get(i).add(rs.getString(2));
                    IndirectList1.get(i).add(rs.getString(3));
                    IndirectList1.get(i).add(rs.getString(4));
                    IndirectList1.get(i).add(rs.getString(5));
                    IndirectList1.get(i).add(rs.getString(6));
                    IndirectList1.get(i).add(rs.getString(7));
                    IndirectList1.get(i).add(rs.getString(8));
                    IndirectList1.get(i).add(rs.getString(9));
                    IndirectList1.get(i).add(rs.getString(10));
                    IndirectList1.get(i).add(rs.getString(11));
                    IndirectList1.get(i).add(rs.getString(12));
                    IndirectList1.get(i).add(rs.getString(13));
              
                    IndirectList1.add(new ArrayList<String>());
                    i++;
                }
                
                String date2;  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance(); 
                c.setTime(sdf.parse(date));
                c.add(Calendar.DATE, 1);  
                date2 = sdf.format(c.getTime());  
                
                sql = "SELECT * FROM flightschedule" 
                  + " WHERE (depAirport= '" + fromHubAirport + "' AND"
                  + " desAirport='" + destination + "' AND"
                  + " DepartureDate='" + date + "' AND"
                  + " FirstClassSeat >= " + (adultFirst+childFirst) + " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness+childBusiness) + ")" 
                  + "OR ( depAirport= '" + fromHubAirport + "' AND"
                  + " desAirport='" + destination + "' AND"
                  + " DepartureDate='" + date2 + "' AND"
                  + " FirstClassSeat >= " + (adultFirst+childFirst) + " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness+childBusiness)+ ")";
            
                rs = st.executeQuery(sql);
                
                IndirectList2.add(new ArrayList<String>());  
                
                i = 0;
                while (rs.next())
                {
                    IndirectList2.get(i).add(rs.getString(1));
                    IndirectList2.get(i).add(rs.getString(2));
                    IndirectList2.get(i).add(rs.getString(3));
                    IndirectList2.get(i).add(rs.getString(4));
                    IndirectList2.get(i).add(rs.getString(5));
                    IndirectList2.get(i).add(rs.getString(6));
                    IndirectList2.get(i).add(rs.getString(7));
                    IndirectList2.get(i).add(rs.getString(8));
                    IndirectList2.get(i).add(rs.getString(9));
                    IndirectList2.get(i).add(rs.getString(10));
                    IndirectList2.get(i).add(rs.getString(11));
                    IndirectList2.get(i).add(rs.getString(12));
                    IndirectList2.get(i).add(rs.getString(13));
              
                    IndirectList2.add(new ArrayList<String>());
                    i++;
                }
                /*
                for(List<String> list : IndirectList1)
          {
              for(String val : list) 
                  System.out.println(val + "");
          }
        
        for(List<String> list : IndirectList2)
          {
              for(String val : list) 
                  System.out.println(val + "");
          }*/
            }
            
            //Finding flight from non-hub airport to non-hub airport
            if ( !departure.equals(fromHubAirport) && !destination.equals(toHubAirport) )
            {   
                sql = "SELECT * FROM flightschedule" 
                  + " WHERE depAirport= '" + departure + "' AND"
                  + " desAirport='" + fromHubAirport + "' AND"
                  + " DepartureDate='" + date + "' AND"
                  + " FirstClassSeat >= " + ( adultFirst + adultBusiness)+ " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness+childBusiness);
            
                rs = st.executeQuery(sql);
                
                IndirectList1.add(new ArrayList<String>());  
                int i = 0;
                
                while (rs.next())
                {
                    IndirectList1.get(i).add(rs.getString(1));
                    IndirectList1.get(i).add(rs.getString(2));
                    IndirectList1.get(i).add(rs.getString(3));
                    IndirectList1.get(i).add(rs.getString(4));
                    IndirectList1.get(i).add(rs.getString(5));
                    IndirectList1.get(i).add(rs.getString(6));
                    IndirectList1.get(i).add(rs.getString(7));
                    IndirectList1.get(i).add(rs.getString(8));
                    IndirectList1.get(i).add(rs.getString(9));
                    IndirectList1.get(i).add(rs.getString(10));
                    IndirectList1.get(i).add(rs.getString(11));
                    IndirectList1.get(i).add(rs.getString(12));
                    IndirectList1.get(i).add(rs.getString(13));
              
                    IndirectList1.add(new ArrayList<String>());
                    i++;
                }
                
                String date2;  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance(); 
                c.setTime(sdf.parse(date));
                c.add(Calendar.DATE, 1);  
                date2 = sdf.format(c.getTime());  
                
                sql = "SELECT * FROM flightschedule" 
                  + " WHERE (depAirport= '" + fromHubAirport + "' AND"
                  + " desAirport='" + toHubAirport + "' AND"
                  + " DepartureDate='" + date + "' AND"
                  + " FirstClassSeat >= " + (adultFirst + adultBusiness) + " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness+childBusiness) + ")" 
                  + "OR ( depAirport= '" + fromHubAirport + "' AND"
                  + " desAirport='" + toHubAirport + "' AND"
                  + " DepartureDate='" + date2 + "' AND"
                  + " FirstClassSeat >= " + ( adultFirst + adultBusiness) + " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness+childBusiness) + ")";
            
                rs = st.executeQuery(sql);
                
                IndirectList2.add(new ArrayList<String>());  
                
                i = 0;
                while (rs.next())
                {
                    IndirectList2.get(i).add(rs.getString(1));
                    IndirectList2.get(i).add(rs.getString(2));
                    IndirectList2.get(i).add(rs.getString(3));
                    IndirectList2.get(i).add(rs.getString(4));
                    IndirectList2.get(i).add(rs.getString(5));
                    IndirectList2.get(i).add(rs.getString(6));
                    IndirectList2.get(i).add(rs.getString(7));
                    IndirectList2.get(i).add(rs.getString(8));
                    IndirectList2.get(i).add(rs.getString(9));
                    IndirectList2.get(i).add(rs.getString(10));
                    IndirectList2.get(i).add(rs.getString(11));
                    IndirectList2.get(i).add(rs.getString(12));
                    IndirectList2.get(i).add(rs.getString(13));
              
                    IndirectList2.add(new ArrayList<String>());
                    i++;
                }
                
                sql = "SELECT * FROM flightschedule" 
                  + " WHERE (depAirport= '" + toHubAirport + "' AND"
                  + " desAirport='" + destination + "' AND"
                  + " DepartureDate='" + date + "' AND"
                  + " FirstClassSeat >= " + (adultFirst + adultBusiness) + " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness+childBusiness) + ")" 
                  + "OR ( depAirport= '" + toHubAirport + "' AND"
                  + " desAirport='" + destination + "' AND"
                  + " DepartureDate='" + date2 + "' AND"
                  + " FirstClassSeat >= " + (adultFirst + adultBusiness) + " AND"
                  + " BusinessClassSeat >= "+ (adultBusiness+childBusiness) + ")";
            
                rs = st.executeQuery(sql);
                
                IndirectList3.add(new ArrayList<String>());  
                
                i = 0;
                while (rs.next())
                {
                    IndirectList3.get(i).add(rs.getString(1));
                    IndirectList3.get(i).add(rs.getString(2));
                    IndirectList3.get(i).add(rs.getString(3));
                    IndirectList3.get(i).add(rs.getString(4));
                    IndirectList3.get(i).add(rs.getString(5));
                    IndirectList3.get(i).add(rs.getString(6));
                    IndirectList3.get(i).add(rs.getString(7));
                    IndirectList3.get(i).add(rs.getString(8));
                    IndirectList3.get(i).add(rs.getString(9));
                    IndirectList3.get(i).add(rs.getString(10));
                    IndirectList3.get(i).add(rs.getString(11));
                    IndirectList3.get(i).add(rs.getString(12));
                    IndirectList3.get(i).add(rs.getString(13));
              
                    IndirectList3.add(new ArrayList<String>());
                    i++;
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        /*
        for(List<String> list : IndirectList1)
          {
              for(String val : list) 
                  System.out.println(val + "");
          }
        
        for(List<String> list : IndirectList2)
          {
              for(String val : list) 
                  System.out.println(val + "");
          }
        
        for(List<String> list : IndirectList3)
          {
              for(String val : list) 
                  System.out.println(val + "");
          }*/
    }
    
    
    
}
