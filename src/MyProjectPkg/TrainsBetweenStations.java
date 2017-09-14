package MyProjectPkg;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class TrainsBetweenStations extends JPanel implements ActionListener,ItemListener
{
 JLabel jl1,jl2,jl3;
  JTextField jtf1,jtf2;
  JComboBox jcb1,jcb2,jcb3;
  JButton jb;
  String dd="1",mm="1",yyyy="2017",srcc="",destc="";
  
  Font f,f1;  
  
  TrainsBetweenStations()
  {
    this.setLayout(null);
    Toolkit tk=Toolkit.getDefaultToolkit();
    Dimension d=tk.getScreenSize();
    this.setBounds(d.width/6,0,d.width,d.height);
    this.setBackground(Color.LIGHT_GRAY);
    
    f=new Font("Serif",Font.PLAIN,22);
    f1=new Font("Serif",Font.BOLD,22);
    
    jl1=new JLabel("Enter the journey date (DD-MM-YYYY) :");
    jl2=new JLabel("Enter Source Station Code :");
    jl3=new JLabel("Enter Destination Station Code :");
    
    jtf1=new JTextField(5);
    jtf2=new JTextField(5);
    
    
    jb=new JButton("Get Trains");
    
    String arr[]=new String[31];
    String brr[]=new String[12];
    String crr[]=new String[2];
    
    for(int i=1;i<=31;i++)
    {
     arr[i-1]=i+""; 
    }
    
    for(int i=1;i<=12;i++)
    {
     brr[i-1]=i+""; 
    }
    
    crr[0]=2017+"";
    crr[1]=2018+"";
    
    jcb1=new JComboBox(arr);
    jcb2=new JComboBox(brr);
    jcb3=new JComboBox(crr);

    jtf1.setBackground(Color.LIGHT_GRAY);
    jtf2.setBackground(Color.LIGHT_GRAY);

    jtf1.setFont(f1);
    jtf2.setFont(f1);

    jl1.setFont(f);
    jl2.setFont(f);
    jl3.setFont(f);
    
    jcb1.setFont(f);
    jcb2.setFont(f);
    jcb3.setFont(f);

    jl1.setBounds(20,20,400,30);
    jl2.setBounds(20,70,400,30);
    jl3.setBounds(20,120,300,30);
    
    jcb1.setBounds(430,20,50,30);
    jcb2.setBounds(490,20,50,30);
    jcb3.setBounds(550,20,100,30);
    
    
    jb.setBounds(430,170,200,30);
    
    jcb1.addItemListener(this);
    jcb2.addItemListener(this);
    jcb3.addItemListener(this);
    
    jb.addActionListener(this);
    
    jtf1.setBounds(430,70,100,30);
    jtf2.setBounds(430,120,100,30);

    this.add(jtf1);
    this.add(jtf2);

    this.add(jl1);
    this.add(jl2);
    this.add(jl3);
    
    this.add(jcb1);
    this.add(jcb2);
    this.add(jcb3);

    this.add(jb);
  }
  
  public void itemStateChanged(ItemEvent e)
  {
    if(e.getSource()==jcb1)
    {
      if(e.getStateChange()==ItemEvent.SELECTED)
      {
        dd=(String)(jcb1.getSelectedItem());
      }
    }
    else if(e.getSource()==jcb2)
    {
      if(e.getStateChange()==ItemEvent.SELECTED)
      {
        mm=(String)(jcb2.getSelectedItem());
      }
    }
    else if(e.getSource()==jcb3)
    {
      if(e.getStateChange()==ItemEvent.SELECTED)
      {
        yyyy=(String)(jcb3.getSelectedItem());
      }
    }
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==jb)
    {
      new getTrains(jtf1.getText(),jtf2.getText(),dd+"-"+mm+"-"+yyyy);       
    }
  }

}

class getTrains implements Runnable
{
  Thread th; 
  String date;
  String source;
  String dest;
  
  public getTrains(String source,String dest,String date)
  {
    th=new Thread(this);
    this.date=date;
    this.source=source;
    this.dest=dest;
    th.start();
  } 
  public void run()
  {
      
    try
    {
      URL url=new URL("http://api.railwayapi.com/v2/between/source/"+source+"/dest/"+dest+"/date/"+date+"/apikey/2aemlgjc26/");
      
      URLConnection uc=url.openConnection();
      
      BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream()));
      
      String data=br.readLine();
      
      String disp="----------Trains Between the Stations----------\n\n";
      
      JSONParser jp=new JSONParser();
      
      JSONObject json=(JSONObject)jp.parse(data);
      
      JSONArray jarr= (JSONArray)json.get("trains");
      
      if(jarr.size()==0)
      {
        disp+= "No Trains Available\n";
      }
      else
      {
        for(int i=0;i<jarr.size();i++)
        {
          disp+="Train Number :-";  
          disp+=  (String)(((JSONObject)jarr.get(i)).get("number"))+"\n";
          disp+="Train Name :-";
          disp+= (String)(((JSONObject)jarr.get(i)).get("name"))+"\n";
          disp+="Travel Time :-";
          disp+= (String)(((JSONObject)jarr.get(i)).get("travel_time"))+"\n";
          disp+="Departure Time :-";
          disp+= (String)(((JSONObject)jarr.get(i)).get("src_departure_time"))+"\n";
          disp+="Destination Arrival Time :-";
          disp+= (String)(((JSONObject)jarr.get(i)).get("dest_arrival_time"))+"\n";
          disp+="Classes Available :-";
          
          JSONArray classes= (JSONArray)(((JSONObject)(jarr.get(i))).get("classes"));
          
          String cc=""; 
          for(int j=0;j<classes.size();j++)
          {
            if(((String)(((JSONObject)classes.get(j)).get("available"))).equals("Y"))
            {
              cc+= (String)(((JSONObject)classes.get(j)).get("name"));
              if(j!=(classes.size()-1))
              {
                cc+=",";
              }
            }
          }
          disp+= (cc+"\n");
          disp+="Days :- MON TUE WED THU FRI SAT SUN\n";
          disp+="                 ";
          JSONArray days= (JSONArray)(((JSONObject)(jarr.get(i))).get("days"));
          
          String cc1=""; 
          for(int j=0;j<days.size();j++)
          {
            cc1+= (String)(((JSONObject)days.get(j)).get("runs"));
            if(j!=(classes.size()-1))
            {
             cc1+="       ";
            }
          }
          disp+=(cc1+"\n");
          disp+="---------------------------------------------------------------\n";       
        }
        JOptionPane.showMessageDialog(null,disp);
      }
      
    }
    catch(Exception r)
    {
       JOptionPane.showMessageDialog(null,"Bad Internet Connectivity \n Or Wrong Input");
    }    
  }

}