
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lee
 */
public class ShowFlightSearch extends javax.swing.JPanel {

    /**
     * Creates new form ShowFlightSearch
     */
    
    List<List<String>> directList = null, IndirectList1= null,
                      IndirectList2= null, IndirectList3= null;
    boolean directFlight;
    
    int ComboBox1numOfItem = 0;
    int ComboBox2numOfItem = 0;
    
    String[] defaultComboboxList;
    
    List<String> comboBox1List = new ArrayList<String>();
    List<String> comboBox2List = new ArrayList<String>();
    
    ComboBoxModel[] model1;
    ComboBoxModel[] model2;
    
    Connection con;
   
    int adultFirst, childFirst, adultBusiness, childBusiness;
    
    public ShowFlightSearch(List<List<String>> directList, 
                            List<List<String>> IndirectList1,
                            List<List<String>> IndirectList2,
                            List<List<String>> IndirectList3,
                            boolean directFlight, Connection con,
                            int adultFirst, int childFirst, int adultBusiness,
                            int childBusiness)
    {
        
        //
        this.adultFirst = adultFirst;
        this.childFirst = childFirst;
        this.adultBusiness = adultBusiness;
        this.childBusiness = childBusiness;
        this.con = con;
        this.IndirectList1 = IndirectList1;
        this.IndirectList2 = IndirectList2;
        this.IndirectList3 = IndirectList3;
        this.directList = directList;
        this.directFlight = directFlight;
        
        initComponents();
        
        this.setJlabel();
        this.model1 = new ComboBoxModel[ComboBox1numOfItem];
        this.setComboBox2();
        this.model2 = new ComboBoxModel[ComboBox2numOfItem];
        this.setComboBox3();
        
        
    }
    
    
    public void setJlabel()
    {
        if(directFlight)
        {
           jLabel1.setText("Direct Flight");
           jLabel2.setText(directList.get(0).get(1) + "->" + directList.get(0).get(2) );
           this.setComboBox1();
           
           this.jComboBox2.setVisible(false);
           this.jComboBox3.setVisible(false);
           
        }        
        else if (IndirectList1.size()-1 > 0 && IndirectList2.size()-1 > 0)
        {
            jLabel1.setText("Indirect Flight");
            
            if( IndirectList1.size()-1 > 0 && IndirectList2.size()-1 > 0 && IndirectList3.size()-1 < 0)
            {
                
                jLabel2.setText(IndirectList1.get(0).get(1) + "->" 
                               + IndirectList1.get(0).get(2) + "->"
                               + IndirectList2.get(0).get(2));
                this.setComboBox1();
                this.jComboBox2.setVisible(true);
                this.jComboBox3.setVisible(false);
                
            }
            else if( IndirectList1.size()-1 >0 && IndirectList2.size()-1 >0 && IndirectList3.size()-1 >0 )
            {
                
                jLabel2.setText(IndirectList1.get(0).get(1) + "->" 
                               + IndirectList1.get(0).get(2) + "->"
                               + IndirectList2.get(0).get(2) + "->"
                               + IndirectList3.get(0).get(2));
                this.setComboBox1();
                this.jComboBox2.setVisible(true);
                this.jComboBox3.setVisible(true);
            }            
        }
        else if(IndirectList2.size()-1 <= 0  )
        {
           jLabel1.setText("NO FLIGHT FOUND");
           this.jButton1.setVisible(false);
           this.jComboBox1.setVisible(false);
           this.jComboBox2.setVisible(false);
           this.jComboBox3.setVisible(false);
        }
        
    }
    
    public void setComboBox1()
    {
        if(directFlight)
        {
           //jComboBox1.removeAllItems();
           //List<String> array = new ArrayList<String>();
            
            if(directList.size()-1 >0){
                 String[][] list = new String[directList.size()][];
       
                 int i = 0;
       
                 for (List<String> l : directList) 
                   list[i++] = l.toArray(new String[l.size()]);
       
                 int row = list.length;
       
                 for( int j = 0; j<row-1; j++)
                 {
                    String element;
                    element = list[j][0] + "/ " + list[j][1] + " " + list[j][3] + " "
                      +list[j][5] + "-->" + list[j][2] + " " + list[j][4] + " "
                      +list[j][6];
              
                    comboBox1List.add(element);
                    jComboBox1.addItem(element);
                    ComboBox1numOfItem++;
                 }
            }else if(directList.size()-1 == 0)
            {
                jComboBox1.addItem("No conncted Flight available");
            }
       
          
        }
        else   //  INDIRECT FLIGHT
        {
           //jComboBox1.removeAllItems();
           //List<String> array = new ArrayList<String>();
            
           if(IndirectList1.size()-1 >0){
               String[][] list = new String[IndirectList1.size()][];
       
               int i = 0;
       
               for (List<String> l : IndirectList1) 
                  list[i++] = l.toArray(new String[l.size()]);
       
               int row = list.length;
       
               for( int j = 0; j<row-1; j++)
               {
                  String element;
                  element = list[j][0] + "/ " + list[j][1] + "   " + list[j][3] + "   "
                          +list[j][5] + "  -->  " + list[j][2] + "   " + list[j][4] + "   "
                          +list[j][6] +" ";
               
                  comboBox1List.add(element);
                  jComboBox1.addItem(element);
                  ComboBox1numOfItem++;
               }
           }else if(IndirectList1.size()-1 ==0)
           {
             jComboBox1.addItem("No conncted Flight available");  
           }
       
           
        }       
       
    }
    
    public void setComboBox2()
    { if(!directFlight && IndirectList1.size()-1 >0 && IndirectList2.size()-1 >0)
      {
       String[][] list = new String[IndirectList2.size()][];
       
       int i = 0;
       
       for (List<String> l : IndirectList2) 
          list[i++] = l.toArray(new String[l.size()]);
           
       for( int j = 0; j < ComboBox1numOfItem; j++)
       {
         String str = comboBox1List.get(j);
         
         String flight1DesDate = str.substring(43,53);
         int flight1DesTime = Integer.parseInt(str.substring(56,60));
         
         ArrayList<String> array = new ArrayList<String>();
         
         int row = list.length;
         for( int k = 0; k<row-1; k++)
         {
          String element = null;
          
            
          // list[k][3] flight2DepartureDate
          // list[k][5] flight2DepartureTime
          
          // NOW : compare flight1DesDate flight2DepartureDate
          if( flight1DesDate.equals(list[k][3]))
          {
              // now compare time
              int flight2DepartureTime = Integer.parseInt(list[k][5]);
              
              if( flight2DepartureTime > flight1DesTime )
              {
               element = list[k][0] + "/ " + list[k][1] + "   " + list[k][3] + "   "
                  +list[k][5] + "  -->  " + list[k][2] + "   " + list[k][4] + "   "
                  +list[k][6] +" "; 
               
               array.add(element);
               comboBox2List.add(element);
               ComboBox2numOfItem++;
              
              }
              
          }
          
          if(!flight1DesDate.equals(list[k][3]))
          {
              element = list[k][0] + "/ " + list[k][1] + "   " + list[k][3] + "   "
                  +list[k][5] + "  -->  " + list[k][2] + "   " + list[k][4] + "   "
                  +list[k][6] +" "; 
               
               array.add(element);
               comboBox2List.add(element);
               ComboBox2numOfItem++;
          }
          
         }
         
         //if Array is empty  ,   set modelList = "not"
         String[] model1List;
         if (!array.isEmpty())
         {
            model1List= new String[array.size()];
            model1List = array.toArray(model1List);    
         }
         else
         {
             model1List = new String[1];
             model1List[0] = "No conncted Flight available";
         }
         
         model1[j] = new DefaultComboBoxModel(model1List);
       }
       
        jComboBox2.setModel(model1[0]); 
     }  
    }
    
    
    public void setComboBox3()
    { if( !directFlight && IndirectList1.size()-1 >0 && IndirectList2.size()-1 >0 && IndirectList3.size()-1 >0 )
     { 
       
       String[][] list = new String[IndirectList3.size()][];
       
       int i = 0;
       
       for (List<String> l : IndirectList3) 
          list[i++] = l.toArray(new String[l.size()]);
       
       
           
       for( int j = 0; j < ComboBox2numOfItem; j++)
       {
         String str = comboBox2List.get(j);
         
         String flight2DesDate = str.substring(43,53);
         int flight2DesTime = Integer.parseInt(str.substring(56,60));
         
         ArrayList<String> array = new ArrayList<String>();
         
         int row = list.length;
         for( int k = 0; k<row-1; k++)
         {
          String element = null;
          
            
          // list[k][3] flight3DepartureDate
          // list[k][5] flight3DepartureTime
          
          // NOW : compare flight1DesDate flight2DepartureDate
          if( flight2DesDate.equals(list[k][3]))
          {
              // now compare time
              int flight3DepartureTime = Integer.parseInt(list[k][5]);
              
              if( flight3DepartureTime > flight2DesTime )
              {
               element = list[k][0] + "/ " + list[k][1] + "   " + list[k][3] + "   "
                  +list[k][5] + "  -->  " + list[k][2] + "   " + list[k][4] + "   "
                  +list[k][6] +" "; 
               
               array.add(element);
               //comboBox3List.add(element);
               //ComboBoxnumOfItem++;
              
              }
              
          }
          
          if(!flight2DesDate.equals(list[k][3]))
          {
              element = list[k][0] + "/ " + list[k][1] + "   " + list[k][3] + "   "
                  +list[k][5] + "  -->  " + list[k][2] + "   " + list[k][4] + "   "
                  +list[k][6] +" "; 
               
               array.add(element);
               //comboBox2List.add(element);
               //ComboBox2numOfItem++;
          }
          
         }
         
         //if Array is empty  ,   set modelList = "not"
         String[] model2List;
         if (!array.isEmpty())
         {
            model2List= new String[array.size()];
            model2List = array.toArray(model2List);    
         }
         else
         {
             model2List = new String[1];
             model2List[0] = "No conncted Flight available";
         }
         
         model2[j] = new DefaultComboBoxModel(model2List);
       }
       
         jComboBox3.setModel(model2[0]); 
     }  
    }
    
    
    
    public double calcuatePrice()
    {
        double price = 0.0;
        
        //Dircet Flight
        if(directFlight)
        {
            String str = jComboBox1.getSelectedItem().toString();
            String flightCode = str.substring(0, 5);
            
            double firstAdult = 0.0;
            double businessAdult = 0.0;
            double firstDiscountRate = 0.0;
            double businessDiscountRate = 0.0;
            
            try{
                String sql = "SELECT * FROM flightschedule "
                        + "WHERE airlineID = '" + flightCode +"'";
            
                Statement st = con.createStatement();
                ResultSet rs;
          
                rs = st.executeQuery(sql);
            
                while( rs.next())
                {
                   firstAdult = Double.parseDouble(rs.getString("FirstClassPrice"));
                   businessAdult = Double.parseDouble(rs.getString("BusinessClassPrice"));
                   firstDiscountRate = Double.parseDouble(rs.getString("firstDiscount"));
                   businessDiscountRate = Double.parseDouble(rs.getString("businessDiscount"));
                }
                
                price = firstAdult*adultFirst + businessAdult*adultBusiness
                       + (1.0-firstDiscountRate)*childFirst*firstAdult
                       + (1.0-businessDiscountRate)*childBusiness*businessAdult;
                
                
            }
            catch(SQLException e)
            {
               e.printStackTrace();
            }
        }
        
        
        
        //Indirect Flight with one stop in the middle
        if( (IndirectList1.size()-1 >0)&&(IndirectList2.size()-2 >0)&&(IndirectList3.size()-1 <=0))
        {
            String str = jComboBox1.getSelectedItem().toString();
            String flightCode1 = str.substring(0, 5);
            str = jComboBox2.getSelectedItem().toString();
            String flightCode2 = str.substring(0, 5);
            
            double firstAdult1 = 0.0;
            double businessAdult1 = 0.0;
            double firstDiscountRate1 = 0.0;
            double businessDiscountRate1 = 0.0;
            double price1 = 0.0;
            
            double firstAdult2 = 0.0;
            double businessAdult2 = 0.0;
            double firstDiscountRate2 = 0.0;
            double businessDiscountRate2 = 0.0;
            double price2 = 0.0;
            
            try{
                String sql = "SELECT * FROM flightschedule "
                        + "WHERE airlineID = '" + flightCode1 +"'";
            
                Statement st = con.createStatement();
                ResultSet rs;
          
                rs = st.executeQuery(sql);
            
                while( rs.next())
                {
                   firstAdult1 = Double.parseDouble(rs.getString("FirstClassPrice"));
                   businessAdult1 = Double.parseDouble(rs.getString("BusinessClassPrice"));
                   firstDiscountRate1 = Double.parseDouble(rs.getString("firstDiscount"));
                   businessDiscountRate1 = Double.parseDouble(rs.getString("businessDiscount"));
                }
                
                price1 = firstAdult1*adultFirst + businessAdult1*adultBusiness
                       + (1.0-firstDiscountRate1)*childFirst*firstAdult1
                       + (1.0-businessDiscountRate1)*childBusiness*businessAdult1;
                
                
                sql = "SELECT * FROM flightschedule "
                        + "WHERE airlineID = '" + flightCode2 +"'";
            
                rs = st.executeQuery(sql);
            
                while( rs.next())
                {
                   firstAdult2 = Double.parseDouble(rs.getString("FirstClassPrice"));
                   businessAdult2 = Double.parseDouble(rs.getString("BusinessClassPrice"));
                   firstDiscountRate2 = Double.parseDouble(rs.getString("firstDiscount"));
                   businessDiscountRate2 = Double.parseDouble(rs.getString("businessDiscount"));
                }
                
                price2 = firstAdult2*adultFirst + businessAdult2*adultBusiness
                       + (1.0-firstDiscountRate2)*childFirst*firstAdult2
                       + (1.0-businessDiscountRate2)*childBusiness*businessAdult2;
                
                
                
                price = price1 + price2;
                
            }
            catch(SQLException e)
            {
               e.printStackTrace();
            }
        }
        
        
        
        
        
        //Indirect Flight with two stops
        if( (IndirectList1.size()-1 >0)&&(IndirectList2.size()-1 >0)&&(IndirectList3.size()-1 >0))
        {
            String str = jComboBox1.getSelectedItem().toString();
            String flightCode1 = str.substring(0, 5);
            str = jComboBox2.getSelectedItem().toString();
            String flightCode2 = str.substring(0, 5);
            str = jComboBox3.getSelectedItem().toString();
            String flightCode3 = str.substring(0, 5);
            
            double firstAdult1 = 0.0;
            double businessAdult1 = 0.0;
            double firstDiscountRate1 = 0.0;
            double businessDiscountRate1 = 0.0;
            double price1 = 0.0;
            
            double firstAdult2 = 0.0;
            double businessAdult2 = 0.0;
            double firstDiscountRate2 = 0.0;
            double businessDiscountRate2 = 0.0;
            double price2 = 0.0;
            
            double firstAdult3 = 0.0;
            double businessAdult3 = 0.0;
            double firstDiscountRate3 = 0.0;
            double businessDiscountRate3 = 0.0;
            double price3 = 0.0;
            
            try{
                String sql = "SELECT * FROM flightschedule "
                        + "WHERE airlineID = '" + flightCode1 +"'";
            
                Statement st = con.createStatement();
                ResultSet rs;
          
                rs = st.executeQuery(sql);
            
                while( rs.next())
                {
                   firstAdult1 = Double.parseDouble(rs.getString("FirstClassPrice"));
                   businessAdult1 = Double.parseDouble(rs.getString("BusinessClassPrice"));
                   firstDiscountRate1 = Double.parseDouble(rs.getString("firstDiscount"));
                   businessDiscountRate1 = Double.parseDouble(rs.getString("businessDiscount"));
                }
                
                price1 = firstAdult1*adultFirst + businessAdult1*adultBusiness
                       + (1.0-firstDiscountRate1)*childFirst*firstAdult1
                       + (1.0-businessDiscountRate1)*childBusiness*businessAdult1;
                
                
                ////////////////////////////////////////////////////////
                
                sql = "SELECT * FROM flightschedule "
                        + "WHERE airlineID = '" + flightCode2 +"'";
            
                rs = st.executeQuery(sql);
            
                while( rs.next())
                {
                   firstAdult2 = Double.parseDouble(rs.getString("FirstClassPrice"));
                   businessAdult2 = Double.parseDouble(rs.getString("BusinessClassPrice"));
                   firstDiscountRate2 = Double.parseDouble(rs.getString("firstDiscount"));
                   businessDiscountRate2 = Double.parseDouble(rs.getString("businessDiscount"));
                }
                
                price2 = firstAdult2*adultFirst + businessAdult2*adultBusiness
                       + (1.0-firstDiscountRate2)*childFirst*firstAdult2
                       + (1.0-businessDiscountRate2)*childBusiness*businessAdult2;
                
                ///////////////////////////////////////////////////////////
                
                sql = "SELECT * FROM flightschedule "
                        + "WHERE airlineID = '" + flightCode3 +"'";
            
                rs = st.executeQuery(sql);
            
                while( rs.next())
                {
                   firstAdult3 = Double.parseDouble(rs.getString("FirstClassPrice"));
                   businessAdult3 = Double.parseDouble(rs.getString("BusinessClassPrice"));
                   firstDiscountRate3 = Double.parseDouble(rs.getString("firstDiscount"));
                   businessDiscountRate3 = Double.parseDouble(rs.getString("businessDiscount"));
                }
                
                price3 = firstAdult3*adultFirst + businessAdult3*adultBusiness
                       + (1.0-firstDiscountRate3)*childFirst*firstAdult3
                       + (1.0-businessDiscountRate3)*childBusiness*businessAdult3;
                
                ////////////////////////////////
                price = price1 + price2 + price3;
                //////////////////////////////
            }
            catch(SQLException e)
            {
               e.printStackTrace();
            }
        }
        
        
        return price;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton1.setText("Book flights");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Total Price : ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, 403, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jButton1)
                .addGap(92, 92, 92))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if( IndirectList2.size()-1 > 0 )
        {
            int i = jComboBox1.getSelectedIndex();
        
            if(model1 != null)
            {
               jComboBox2.setModel(model1[i]);  
            }
        }
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        if( IndirectList3.size()-1 > 0)
        {
           int i = jComboBox2.getSelectedIndex();
        
           if(model2 != null)
          {
             jComboBox3.setModel(model2[i]);  
          } 
        }
        
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
         
        boolean proceed = true;
        boolean booked = false;
        
        // one stop case
        if(!directFlight && IndirectList1.size()-1 >0 && IndirectList2.size()-1 >0)
        {
            if( jComboBox1.getSelectedItem()=="No conncted Flight available" 
               || jComboBox2.getSelectedItem()=="No conncted Flight available")
            {
                proceed = false;
                JOptionPane.showMessageDialog(null,
                "Please check Comboboxes again");
            }
        }        
        //tow stops case
        else if( !directFlight && IndirectList1.size()-1 >0 
                && IndirectList2.size()-1 >0 
                && IndirectList3.size()-1 >0 )
        {
            if( jComboBox1.getSelectedItem()=="No conncted Flight available" 
               || jComboBox2.getSelectedItem()=="No conncted Flight available"
               || jComboBox3.getSelectedItem()=="No conncted Flight available")
            {
                proceed = false;
                JOptionPane.showMessageDialog(null,
                "Please check Comboboxes again");
            }
        }
        
        if(proceed)
        {
           //Calculate and display total ticket price
           jLabel4.setText( String.valueOf(this.calcuatePrice()));
           
           
           //Updating DB after booking is done
           try
           {
              
              
              String sql;
            
              Statement st = con.createStatement();
              ResultSet rs;
            
              //rs = st.executeQuery(sql);
              if(directFlight)
              {
                 int firstSeat = 0, businessSeat = 0;
                 String str = jComboBox1.getSelectedItem().toString();
                 String flightCode = str.substring(0,5);
                 
                 sql = "SELECT * FROM flightschedule " +
                       "WHERE airlineID = '" + flightCode +"'";
                  
                 rs = st.executeQuery(sql);
                  
                 while( rs.next() )
                 {
                   firstSeat = Integer.parseInt(rs.getString("FirstClassSeat"));
                   businessSeat = Integer.parseInt(rs.getString("BusinessClassSeat"));
                 }
                  
                 firstSeat = firstSeat - adultFirst - childFirst;
                 businessSeat = businessSeat - adultBusiness - childBusiness;
                  
                 sql = "UPDATE flightschedule SET FirstClassSeat = " + firstSeat
                     + ", " + "BusinessClassSeat = " + businessSeat
                     + " WHERE airlineID = '" + flightCode +"'";
                 int n = st.executeUpdate(sql);
                 
                 if(n>0)
                     booked = true;
              }
              
              
              //One stop 
              if(!directFlight && IndirectList1.size()-1 >0 && IndirectList2.size()-1 >0)
              {
                  int firstSeat1 = 0, businessSeat1 = 0;
                  int firstSeat2 = 0, businessSeat2 = 0;
                  
                 String str = jComboBox1.getSelectedItem().toString();
                 String flightCode1 = str.substring(0,5);
                 str = jComboBox2.getSelectedItem().toString();
                 String flightCode2 = str.substring(0,5);
                 
                 
                 
                 sql = "SELECT * FROM flightschedule " +
                       "WHERE airlineID = '" + flightCode1 +"'";
                  
                 rs = st.executeQuery(sql);
                  
                 while( rs.next() )
                 {
                   firstSeat1 = Integer.parseInt(rs.getString("FirstClassSeat"));
                   businessSeat1 = Integer.parseInt(rs.getString("BusinessClassSeat"));
                 }
                  
                 firstSeat1 = firstSeat1 - adultFirst - childFirst;
                 businessSeat1 = businessSeat1 - adultBusiness - childBusiness;
                  
                 sql = "UPDATE flightschedule SET FirstClassSeat = " + firstSeat1
                     + ", " + "BusinessClassSeat = " + businessSeat1
                     + " WHERE airlineID = '" + flightCode1 +"'";
                 int n1 = st.executeUpdate(sql);
                 
                 
                 
                 
                 sql = "SELECT * FROM flightschedule " +
                       "WHERE airlineID = '" + flightCode2 +"'";
                  
                 rs = st.executeQuery(sql);
                  
                 while( rs.next() )
                 {
                   firstSeat2 = Integer.parseInt(rs.getString("FirstClassSeat"));
                   businessSeat2 = Integer.parseInt(rs.getString("BusinessClassSeat"));
                 }
                  
                 firstSeat2 = firstSeat2 - adultFirst - childFirst;
                 businessSeat2 = businessSeat2 - adultBusiness - childBusiness;
                  
                 sql = "UPDATE flightschedule SET FirstClassSeat = " + firstSeat2
                     + ", " + "BusinessClassSeat = " + businessSeat2
                     + " WHERE airlineID = '" + flightCode2 +"'";
                 int n2 = st.executeUpdate(sql);
                                  
                 if(n1>0 && n2>0 )
                     booked = true;
              }
              
              
              
              //tow stops case
              else if( !directFlight && IndirectList1.size()-1 >0 
                       && IndirectList2.size()-1 >0 
                       && IndirectList3.size()-1 >0 )
              {
                  int firstSeat1 = 0, businessSeat1 = 0;
                  int firstSeat2 = 0, businessSeat2 = 0;
                  int firstSeat3 = 0, businessSeat3 = 0;
                  
                 String str = jComboBox1.getSelectedItem().toString();
                 String flightCode1 = str.substring(0,5);
                 str = jComboBox2.getSelectedItem().toString();
                 String flightCode2 = str.substring(0,5);
                 str = jComboBox3.getSelectedItem().toString();
                 String flightCode3 = str.substring(0,5);
                 
                 
                 
                 
                 sql = "SELECT * FROM flightschedule " +
                       "WHERE airlineID = '" + flightCode1 +"'";
                  
                 rs = st.executeQuery(sql);
                  
                 while( rs.next() )
                 {
                   firstSeat1 = Integer.parseInt(rs.getString("FirstClassSeat"));
                   businessSeat1 = Integer.parseInt(rs.getString("BusinessClassSeat"));
                 }
                  
                 firstSeat1 = firstSeat1 - adultFirst - childFirst;
                 businessSeat1 = businessSeat1 - adultBusiness - childBusiness;
                  
                 sql = "UPDATE flightschedule SET FirstClassSeat = " + firstSeat1
                     + ", " + "BusinessClassSeat = " + businessSeat1
                     + " WHERE airlineID = '" + flightCode1 +"'";
                 int n1 = st.executeUpdate(sql);
                 
                 
                 
                 
                 sql = "SELECT * FROM flightschedule " +
                       "WHERE airlineID = '" + flightCode2 +"'";
                  
                 rs = st.executeQuery(sql);
                  
                 while( rs.next() )
                 {
                   firstSeat2 = Integer.parseInt(rs.getString("FirstClassSeat"));
                   businessSeat2 = Integer.parseInt(rs.getString("BusinessClassSeat"));
                 }
                  
                 firstSeat2 = firstSeat2 - adultFirst - childFirst;
                 businessSeat2 = businessSeat2 - adultBusiness - childBusiness;
                  
                 sql = "UPDATE flightschedule SET FirstClassSeat = " + firstSeat2
                     + ", " + "BusinessClassSeat = " + businessSeat2
                     + " WHERE airlineID = '" + flightCode2 +"'";
                 int n2 = st.executeUpdate(sql);
                 
                 
                 
                 
                 sql = "SELECT * FROM flightschedule " +
                       "WHERE airlineID = '" + flightCode3 +"'";
                  
                 rs = st.executeQuery(sql);
                  
                 while( rs.next() )
                 {
                   firstSeat3 = Integer.parseInt(rs.getString("FirstClassSeat"));
                   businessSeat3 = Integer.parseInt(rs.getString("BusinessClassSeat"));
                 }
                  
                 firstSeat3 = firstSeat3 - adultFirst - childFirst;
                 businessSeat3 = businessSeat3 - adultBusiness - childBusiness;
                  
                 sql = "UPDATE flightschedule SET FirstClassSeat = " + firstSeat3
                     + ", " + "BusinessClassSeat = " + businessSeat3
                     + " WHERE airlineID = '" + flightCode3 +"'";
                 int n3 = st.executeUpdate(sql);
                 
                                  
                 if(n1>0 && n2>0 && n3>0 )
                     booked = true;
            
              }
           }catch(SQLException e)
           {
              e.printStackTrace();
           }
           
        }
        
        if(booked)
        {
            JOptionPane.showMessageDialog(null,
                "Flight booked success");
            this.jButton1.setVisible(false);
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
