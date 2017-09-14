package MyProjectPkg;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PNRStatus extends JPanel implements ActionListener
{
  JLabel jl;
  JTextField jtf; 
  JButton jb;
  Font f,f1;
  
  PNRStatus()
  {
    this.setLayout(null);
    Toolkit tk=Toolkit.getDefaultToolkit();
    Dimension d=tk.getScreenSize();
    this.setBounds(d.width/6,0,d.width,d.height);
    this.setBackground(Color.LIGHT_GRAY);
    
    f=new Font("Serif",Font.PLAIN,22);
    f1=new Font("Serif",Font.BOLD,22);
    
    jl=new JLabel("Enter the PNR Number :");
    jtf=new JTextField(15);
    
    jl.setBounds(20,20,250,30);
    jl.setFont(f);
    
    jtf.setBounds(430,20,200,30);
    jtf.setFont(f1);
    
    jb=new JButton("Get PNR Details");
    jb.addActionListener(this);
    jb.setBounds(430,70,200,30);
    
    this.add(jl);
    this.add(jtf);
    this.add(jb);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if((e.getActionCommand()).equals("Get PNR Details"))
    {
     // System.out.println(" vansh "+jtf.getText());
      new getPNRStatus(jtf.getText());
    }
  } 
}


class getPNRStatus implements Runnable
{
  Thread th;
  String pnr1;
  String dispstr="";
  
  public getPNRStatus(String pnrno)
  {
    th=new Thread(this);
    pnr1=pnrno;
    th.start();    
  }
  
  public void run()
  {
    try
    {
      URL url=new URL("http://api.railwayapi.com/v2/pnr-status/pnr/"+pnr1+"/apikey/2aemlgjc26/");
      
      URLConnection c=url.openConnection();
      
      BufferedReader br=new BufferedReader(new InputStreamReader(c.getInputStream()));
      
      String data=br.readLine();
      
      System.out.println(data);
      
      JSONParser jp=new JSONParser();
      
      JSONObject json=(JSONObject)jp.parse(data);
      
      JSONObject json_upto=(JSONObject)json.get("reservation_upto");
      
      JSONObject json_from=(JSONObject)json.get("from_station");

      dispstr+=("Reservation Upto :- "+(String)(json_upto.get("name"))+" "+(String)(json_upto.get("code"))+"\n\n");
      
      dispstr+=("From Station :- "+(String)(json_from.get("name"))+" "+(String)(json_from.get("code"))+"\n\n");

      dispstr+=("Train Number :- "+  (String)(((JSONObject)json.get("train")).get("number"))+"\n\n");

      dispstr+=("Train Name :- "+  (String)(((JSONObject)json.get("train")).get("name"))+"\n\n");
  
      dispstr+=("Journey Class :- "+  (String)(((JSONObject)json.get("journey_class")).get("name"))+"("+(String)(((JSONObject)json.get("journey_class")).get("code"))+")"+"\n\n");  
      
      dispstr+=("Journey Date :- "+ (String)(json.get("doj"))+"\n\n");
      
      dispstr+=("Boarding Point :- "+  (String)(((JSONObject)json.get("boarding_point")).get("name"))+"("+(String)(((JSONObject)json.get("boarding_point")).get("code"))+")"+"\n\n");
      
      String statusofchart= (String)(json.get("chart_prepared")).toString();
      
      if(statusofchart.equals("false"))
        dispstr+=("Charting Status :- Chart Not Prepared \n\n");
      else
        dispstr+=("Charting Status :- Chart Prepared \n\n");
      
      
      dispstr+="--------------------Passengers Details--------------------\n";
      
      JSONArray Passengers=(JSONArray)json.get("passengers");
      
      for(int i=0;i<Passengers.size();i++)
      {
        dispstr+=("Passenger "+(String)(((JSONObject)(Passengers.get(i))).get("no")).toString()+":- \n");  
        dispstr+= ("Current Status :- "+(String)(((JSONObject)Passengers.get(i)).get("current_status"))+"\n");
        dispstr+=("Booking Status :- "+(String)(((JSONObject)Passengers.get(i)).get("booking_status"))+"\n\n");
      }
      
      
      JOptionPane.showMessageDialog(null,dispstr);
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,"Bad Internet Connectivity \n Or Wrong Input");  
       
      
      e.printStackTrace();
    }
  }
  
}


