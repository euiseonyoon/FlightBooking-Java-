/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lee
 */
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

public class FlightSearchGUI extends javax.swing.JPanel {
    
    private String dateString;
    private int adultFirst = 0, adultBusiness = 0, childFirst = 0, childBusiness = 0;
    private String departureAiport, destinationAirport; 
    Connection con = null;
    ComboBoxModel[] model1 = new ComboBoxModel[3];
    ComboBoxModel[] model2 = new ComboBoxModel[3];
    FindFlight findFlight;
    boolean nextStep;

    /**
     * Creates new form FlightSearch
     */
    public FlightSearchGUI(Connection con) {
        initComponents();
        this.con = con;
        this.setCityList();
        this.jComboBox3ActionPerformed(null);
        this.jComboBox7ActionPerformed(null);
        this.jComboBox6ActionPerformed(null);
        
    }
    
    public int getAdultFirst()
    {
        return adultFirst;
    }
    
    public int getAdultBusiness()
    {
        return adultBusiness;
    }
    
    public String getDeparture()
    {
        return departureAiport;
    }
    
    public String getDestination()
    {
        return destinationAirport;
    }
    
    public String getDateString()
    {
        return dateString;
    }
    
    //Read list of cities from DB and 
    //set it to list of the combobox for 'From' and 'To'
    public void setCityList()
    {  
        
       List<String> TXList = new ArrayList<String>();
       List<String> CAList = new ArrayList<String>();
       List<String> AZList = new ArrayList<String>();
       
       try
       {
          String sql = "SELECT * from city";
            
          Statement st = con.createStatement();
          ResultSet rs;
            
          rs = st.executeQuery(sql);
          
          //Reading list of cities from Database and save the data into cityList
          while(rs.next())
          {
              String element = rs.getString(1) + "--" + rs.getString(2);
              
              if ( "TX".equals(rs.getString("state")))
              {
                  TXList.add(element);
              }
              
              if ( "CA".equals(rs.getString("state")))
              {
                  CAList.add(element);
              }
              
              if ( "AZ".equals(rs.getString("state")))
              {
                  AZList.add(element);
              }             
          }
       }
       catch(SQLException e)
       {
          e.printStackTrace();
       }
       
        
       
       String[] txList = new String[TXList.size()];
       txList = TXList.toArray(txList);       
       
       String[] caList = new String[CAList.size()];
       caList = CAList.toArray(caList);      
       
       String[] azList = new String[AZList.size()];
       azList = AZList.toArray(azList);       
       
       model1[0] = new DefaultComboBoxModel(txList);
       model1[1] = new DefaultComboBoxModel(caList);
       model1[2] = new DefaultComboBoxModel(azList);
       
       model2[0] = new DefaultComboBoxModel(txList);
       model2[1] = new DefaultComboBoxModel(caList);
       model2[2] = new DefaultComboBoxModel(azList);
       
       jComboBox1.setModel(model1[0]);
       jComboBox2.setModel(model2[0]);   
       
       
       
    }
    
    public boolean evaluateDate()
    {
        int year, month, day;
        // if there is an error -> acceptable = false
        boolean acceptable = false; 
        
        try
        {
            year = Integer.parseInt(jTextPane1.getText());
            month = Integer.parseInt(jTextPane3.getText());
            day = Integer.parseInt(jTextPane2.getText());

            //Validating Year Value
            if (year < 0 || year < 1000 )
            {
                acceptable = false;
                JOptionPane.showMessageDialog(null,
                    "Year must be 4 digit input. Try again");
            }
            else
            acceptable = true;

            //Validating Month Value
            if( month <0 || month > 12)
            {
                acceptable = false;
                JOptionPane.showMessageDialog(null,
                    "Please Check your Month input again");
            }
            else
            acceptable = true;

            //Validating Day Value
            if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8  || month == 10 || month == 12)
            {
                if( day > 31 || day < 1)
                {
                    acceptable = false;
                    JOptionPane.showMessageDialog(null,
                        "Please Check your Day input again");
                }
                else
                acceptable = true;
            }

            if (month == 2 || month == 4 || month == 6 || month == 9
                || month == 11)
            {
                // Leap year
                if(year%4 == 0)
                {
                    if( day < 1 || day > 29)
                    {
                        acceptable = false;
                        JOptionPane.showMessageDialog(null,
                            "Please Check your Day input again");
                    }
                    else
                    acceptable = true;
                }
                else if( year%4 != 0)
                {
                    if( day < 1 || day > 28)
                    {
                        acceptable = false;
                        JOptionPane.showMessageDialog(null,
                            "Please Check your Day input again");
                    }
                    else
                    acceptable = true;
                }
            }
            
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            Date today = calendar.getTime();
            
             int todayDay = calendar.get(Calendar.DATE);
             // +1 the month for current month
             int todayMonth = calendar.get(Calendar.MONTH) + 1;
             int todayYear = calendar.get(Calendar.YEAR);
             
             if ( year < todayYear )
                 acceptable = false;
             else if( year == todayYear)
             {
                 if( month < todayMonth)
                     acceptable =false;
                 else if( month == todayMonth)
                 {
                     if( day < todayDay)
                         acceptable = false;
                     else
                         acceptable = true;
                 }
             }
             
            //If there was no error, set dateString with using year,month, day
            if(acceptable)
            {
                if ( day < 10)
                {
                    String dayString = "0" + day;
                    dateString = year+"-"+month+"-"+dayString;
                }
                else if( day > 10)
                    dateString = year+"-"+month+"-"+day;
            }
            else if (!acceptable)
            {
                JOptionPane.showMessageDialog(null,
                            "Please Check your date input again");
            } 
            
        }
        
        //When input is not only an integer, It will throw exception and
        //Show error message
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Year, Month, and Day should be INTERGER ONLY. Try again (no space)");
        }
        
        return acceptable;
    }
    
    public boolean evaluateNumberOfSeat()
    {
        boolean acceptable;
        
        if ( Integer.parseInt(jComboBox5.getSelectedItem().toString()) <= 0 &&
                Integer.parseInt(jComboBox6.getSelectedItem().toString()) <= 0)
        {
            acceptable = false;
            JOptionPane.showMessageDialog(null,
                "Please check how many seats you want to reserve again");
        }
        else
        acceptable = true;
        
        return acceptable;
    }
    
    public boolean evaluateFromAndTo()
    {
        boolean acceptable;
        
        //Check if 'From' and 'TO' are same or not
        if( departureAiport.equals(destinationAirport) )
        {
            acceptable = false;
            JOptionPane.showMessageDialog(null,
                "Please check 'From' and 'To' again"); 
        }
        else
        acceptable = true;
        
        return acceptable;
    }
    
    public int sortChildren()
    {
        int childTotal = 
                Integer.parseInt(jComboBox6.getSelectedItem().toString());
        
        boolean check = true;
        
        int overThree = 0, lessThree = 0;
        String child1, child2, child3, child4, child5;
        
        if (childTotal == 0)
        {
           overThree = 0;
           lessThree = 0; 
        }
        
        if(childTotal == 1)
        {
           if ( "less than 3".equals(child1Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child1Combobox.getSelectedItem().toString()))
               overThree++;
           
           if(lessThree + overThree != 1)
               check = false;
           
           
        }
        
        if(childTotal == 2)
        {
           if ( "less than 3".equals(child1Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child1Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child2Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child2Combobox.getSelectedItem().toString()))
               overThree++;
           
           if(lessThree + overThree != 2)
               check = false;
           
          
        }
        
        if(childTotal == 3)
        {
           if ( "less than 3".equals(child1Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child1Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child2Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child2Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child3Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child3Combobox.getSelectedItem().toString()))
               overThree++;
           
           if(lessThree + overThree != 3)
               check = false;
           
           
        }
        
        if(childTotal == 4)
        {
           if ( "less than 3".equals(child1Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child1Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child2Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child2Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child3Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child3Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child4Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child4Combobox.getSelectedItem().toString()))
               overThree++;
           
           if(lessThree + overThree != 4)
               check = false;
           
           
        }
        
        if(childTotal == 5)
        {
           if ( "less than 3".equals(child1Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child1Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child2Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child2Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child3Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child3Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child4Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child4Combobox.getSelectedItem().toString()))
               overThree++;
           
           if ( "less than 3".equals(child5Combobox.getSelectedItem().toString()) )
               lessThree++;
           else if ("3+".equals(child5Combobox.getSelectedItem().toString()))
               overThree++;
           
           if(lessThree + overThree != 5)
               check = false;
           
           
        }
        
        if (check)
            return lessThree;
        else
        {
           System.out.println("sortChildren error");
           return 0;
        }
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        child1Combobox = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        child2Combobox = new javax.swing.JComboBox();
        child3Combobox = new javax.swing.JComboBox();
        child4Combobox = new javax.swing.JComboBox();
        child5Combobox = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 784, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 527, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Round", jPanel1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("From");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("To");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Month");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Day");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Adults");

        jLabel7.setText("Class");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Search flight ( double Click)");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Year");

        jScrollPane1.setViewportView(jTextPane1);

        jScrollPane2.setViewportView(jTextPane2);

        jScrollPane3.setViewportView(jTextPane3);

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "First Class", "Business" }));
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });

        jLabel9.setText("Children");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5" }));
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        jComboBox6.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox6PropertyChange(evt);
            }
        });

        jLabel5.setText("Age of ");

        jLabel10.setText("Child1");

        child1Combobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "less than 3", "3+" }));

        jLabel11.setText("Child2");

        jLabel12.setText("Child3");

        jLabel13.setText("Child4");

        jLabel14.setText("Child5");

        child2Combobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "less than 3", "3+" }));
        child2Combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                child2ComboboxActionPerformed(evt);
            }
        });

        child3Combobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "less than 3", "3+" }));

        child4Combobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {  "less than 3", "3+" }));

        child5Combobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "less than 3", "3+" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TX", "CA", "AZ" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TX", "CA", "AZ" }));
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(child1Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(child2Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(child3Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(100, 100, 100)
                                .addComponent(jLabel12)))
                        .addGap(100, 100, 100)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(child4Combobox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(100, 100, 100)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(child5Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)))
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(149, 149, 149)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(220, 220, 220)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel8});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(child1Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(child2Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(child3Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(child4Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(child5Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(69, 69, 69)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel8});

        jTabbedPane1.addTab("oneWay", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
        int i = jComboBox7.getSelectedIndex();
        jComboBox2.setModel(model2[i]);
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        int i = jComboBox3.getSelectedIndex();
        jComboBox1.setModel(model1[i]);
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void child2ComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_child2ComboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_child2ComboboxActionPerformed

    private void jComboBox6PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox6PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6PropertyChange

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
        int i = jComboBox6.getSelectedIndex();

        if( i == 0)
        {
            jLabel5.setVisible(false);

            jLabel10.setVisible(false);
            child1Combobox.setVisible(false);

            jLabel11.setVisible(false);
            child2Combobox.setVisible(false);

            jLabel12.setVisible(false);
            child3Combobox.setVisible(false);

            jLabel13.setVisible(false);
            child4Combobox.setVisible(false);

            jLabel14.setVisible(false);
            child5Combobox.setVisible(false);
        }

        if( i == 1)
        {
            jLabel5.setVisible(true);

            jLabel10.setVisible(true);
            child1Combobox.setVisible(true);

            jLabel11.setVisible(false);
            child2Combobox.setVisible(false);

            jLabel12.setVisible(false);
            child3Combobox.setVisible(false);

            jLabel13.setVisible(false);
            child4Combobox.setVisible(false);

            jLabel14.setVisible(false);
            child5Combobox.setVisible(false);
        }

        if( i == 2)
        {
            jLabel5.setVisible(true);

            jLabel10.setVisible(true);
            child1Combobox.setVisible(true);

            jLabel11.setVisible(true);
            child2Combobox.setVisible(true);

            jLabel12.setVisible(false);
            child3Combobox.setVisible(false);

            jLabel13.setVisible(false);
            child4Combobox.setVisible(false);

            jLabel14.setVisible(false);
            child5Combobox.setVisible(false);
        }

        if( i == 3)
        {
            jLabel5.setVisible(true);

            jLabel10.setVisible(true);
            child1Combobox.setVisible(true);

            jLabel11.setVisible(true);
            child2Combobox.setVisible(true);

            jLabel12.setVisible(true);
            child3Combobox.setVisible(true);

            jLabel13.setVisible(false);
            child4Combobox.setVisible(false);

            jLabel14.setVisible(false);
            child5Combobox.setVisible(false);
        }

        if( i == 4)
        {
            jLabel5.setVisible(true);

            jLabel10.setVisible(true);
            child1Combobox.setVisible(true);

            jLabel11.setVisible(true);
            child2Combobox.setVisible(true);

            jLabel12.setVisible(true);
            child3Combobox.setVisible(true);

            jLabel13.setVisible(true);
            child4Combobox.setVisible(true);

            jLabel14.setVisible(false);
            child5Combobox.setVisible(false);
        }

        if( i == 5)
        {
            jLabel5.setVisible(true);

            jLabel10.setVisible(true);
            child1Combobox.setVisible(true);

            jLabel11.setVisible(true);
            child2Combobox.setVisible(true);

            jLabel12.setVisible(true);
            child3Combobox.setVisible(true);

            jLabel13.setVisible(true);
            child4Combobox.setVisible(true);

            jLabel14.setVisible(true);
            child5Combobox.setVisible(true);
        }
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ActionPerformed
/*
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    */ 
    public void jButton1ActionPerformed(java.awt.event.ActionEvent evt) { 
    // TODO add your handling code here:
    
        boolean dateError = this.evaluateDate();
        boolean seatError = this.evaluateNumberOfSeat();

        //Setting departureAiport to airport code of departure
        String temp = jComboBox1.getSelectedItem().toString();
        departureAiport = temp.substring(temp.length() - 3);

        //Setting destinationAirpott to airport code of destination
        temp = jComboBox2.getSelectedItem().toString();
        destinationAirport = temp.substring(temp.length() - 3);

        boolean fromtoError = this.evaluateFromAndTo();

        nextStep = dateError && seatError && fromtoError;

        if("First Class".equals(jComboBox8.getSelectedItem().toString()))
        {
            int childTotal =
            Integer.parseInt(jComboBox6.getSelectedItem().toString());

            childFirst = this.sortChildren();

            adultFirst =
            Integer.parseInt(jComboBox5.getSelectedItem().toString()) +
            childTotal - childFirst;

        }

        if("Business".equals(jComboBox8.getSelectedItem().toString()))
        {
            int childTotal =
            Integer.parseInt(jComboBox6.getSelectedItem().toString());

            childBusiness = this.sortChildren();

            adultBusiness =
            Integer.parseInt(jComboBox5.getSelectedItem().toString()) +
            childTotal - childBusiness;
        }

        int child = this.sortChildren();
        
        findFlight = new FindFlight( con,
            departureAiport,
            destinationAirport,
            dateString,
            adultFirst,
            adultBusiness,
            childFirst, childBusiness,
            jComboBox3.getSelectedItem().toString(),
            jComboBox7.getSelectedItem().toString());
        
        findFlight.findDirectFlight();
        boolean directFlightAvailable = findFlight.getDirectFlight();
        
        if( !directFlightAvailable)
        {
            try {
            findFlight.findIndirectFlight();
            
            //this.setVisible(false);
            
            // if there weren't any errors made or
            // errors are fixed   ->  then we can call another method or another
            // class to proceed the application.
            // if(nextStep) {  ~~~  }
               } catch (ParseException ex) {
            Logger.getLogger(FlightSearchGUI.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

/*
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox child1Combobox;
    private javax.swing.JComboBox child2Combobox;
    private javax.swing.JComboBox child3Combobox;
    private javax.swing.JComboBox child4Combobox;
    private javax.swing.JComboBox child5Combobox;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    // End of variables declaration//GEN-END:variables
*/
    private javax.swing.JComboBox child1Combobox;
    private javax.swing.JComboBox child2Combobox;
    private javax.swing.JComboBox child3Combobox;
    private javax.swing.JComboBox child4Combobox;
    private javax.swing.JComboBox child5Combobox;
    public javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;

}
