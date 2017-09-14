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
public class TrainFareEnquiry extends JPanel implements ItemListener, ActionListener
{
  JLabel jl1,jl2,jl3,jl4,jl5,jl6;
  JTextField jtf1,jtf2,jtf3,jtf4;
  JComboBox jcb1,jcb2,jcb3,jcb4;
  JButton jb;
  String dd="1",mm="1",yyyy="2017",acco="SL",quota="PQ",srcc="",destc="",trainNumber="",age="";
 
  Map<String,String>QuotaClass;
  
  Font f,f1;  
  
  TrainFareEnquiry()
  {
    this.setLayout(null);
    Toolkit tk=Toolkit.getDefaultToolkit();
    Dimension d=tk.getScreenSize();
    this.setBounds(d.width/6,0,d.width,d.height);
    this.setBackground(Color.LIGHT_GRAY);
    
    f=new Font("Serif",Font.PLAIN,22);
    f1=new Font("Serif",Font.BOLD,22);
    
    jl1=new JLabel("Enter the train Number :");
    jl2=new JLabel("Enter the journey date (DD-MM-YYYY) :");
    jl3=new JLabel("Enter Source Station Code :");
    jl4=new JLabel("Enter Destination Station Code :");
    jl5=new JLabel("Enter the Quota :");
    jl6=new JLabel("Enter the Age of Passenger :");
    
    jtf1=new JTextField(10);
    jtf2=new JTextField(5);
    jtf3=new JTextField(5);
    jtf4=new JTextField(6);
    
    
    jb=new JButton("Calculate Journey Fare");
    
    String arr[]=new String[31];
    String brr[]=new String[12];
    String crr[]=new String[2];
    //String drr[]=new String[7];
    String err[]=new String[19];
            
  
    QuotaClass=new HashMap();  
    
    QuotaClass.put("General Quota","GN");
    QuotaClass.put("Ladies Quota","LD");
    QuotaClass.put("Head quarters/high official Quota","HO");
    QuotaClass.put("Defence Quota","DF");
    QuotaClass.put("Parliament house Quota","PH");
    QuotaClass.put("Foreign Tourist Quota","FT");
    QuotaClass.put("Duty Pass Quota","DP");
    QuotaClass.put("Tatkal Quota","TQ");
    QuotaClass.put("Premium Tatkal Quota","PT");
    QuotaClass.put("Female(above 45 Year)/Senior Citizen/Travelling alone","SS");
    QuotaClass.put("Physically Handicapped Quota","HP");
    QuotaClass.put("Railway Employee Staff on Duty for the train","RE");
    QuotaClass.put("General Quota Road Side","GNRS");
    QuotaClass.put("Out Station","OS");
    QuotaClass.put("Pooled Quota","PQ");
    QuotaClass.put("Reservation Against Cancellation","RAC");
    QuotaClass.put("Road Side","RS");
    QuotaClass.put("Yuva","YU");
    QuotaClass.put("Lower Berth","LB");

 
    Set Quotaset=QuotaClass.entrySet();
    Iterator itr;
    int local1=0;

    itr=Quotaset.iterator();
    local1=0;
    while(itr.hasNext())
    {
      Map.Entry me=(Map.Entry)itr.next();
      err[local1]=(String)me.getKey();
      local1++;
    }
    
    
    
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
    jcb4=new JComboBox(err);
    
    jtf1.setBackground(Color.LIGHT_GRAY);
    jtf2.setBackground(Color.LIGHT_GRAY);
    jtf3.setBackground(Color.LIGHT_GRAY);
    jtf4.setBackground(Color.LIGHT_GRAY);
    
    jtf1.setFont(f1);
    jtf2.setFont(f1);
    jtf3.setFont(f1);
    jtf4.setFont(f1);
    
    jl1.setFont(f);
    jl2.setFont(f);
    jl3.setFont(f);
    jl4.setFont(f);
    jl5.setFont(f);
    jl6.setFont(f);
    
    jcb1.setFont(f);
    jcb2.setFont(f);
    jcb3.setFont(f);
    jcb4.setFont(f);
    
    jl1.setBounds(20,20,250,30);   //hardcoded
    jl2.setBounds(20,70,400,30);
    jl3.setBounds(20,120,250,30);
    jl4.setBounds(20,170,300,30);
    jl5.setBounds(20,220,300,30);
    jl6.setBounds(20,270,300,30);
    
    jcb1.setBounds(430,70,50,30);
    jcb2.setBounds(490,70,50,30);
    jcb3.setBounds(550,70,100,30);
    jcb4.setBounds(430,220,530,30);
    
    
    jb.setBounds(430,350,200,30);
    
    jcb1.addItemListener(this);
    jcb2.addItemListener(this);
    jcb3.addItemListener(this);
    jcb4.addItemListener(this);
    
    jb.addActionListener(this);
    
    jtf1.setBounds(430,20,200,30);
    jtf2.setBounds(430,120,100,30);
    jtf3.setBounds(430,170,100,30);
    jtf4.setBounds(430,270,50,30);
    
    this.add(jtf1);
    this.add(jtf2);
    this.add(jtf3);
    this.add(jtf4);
    
    this.add(jl1);
    this.add(jl2);
    this.add(jl3);
    this.add(jl4);
    this.add(jl5);
    this.add(jl6);
    
    this.add(jcb1);
    this.add(jcb2);
    this.add(jcb3);
    this.add(jcb4);    
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
    else if(e.getSource()==jcb4)
    {
      if(e.getStateChange()==ItemEvent.SELECTED)
      {
       quota=QuotaClass.get((String)jcb4.getSelectedItem());
      }
    }      
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==jb)
    {
      trainNumber=jtf1.getText();  
      srcc=jtf2.getText();
      destc=jtf3.getText();
      age=jtf4.getText();
      getTrainFare tf=new getTrainFare(trainNumber,srcc,destc,dd+"-"+mm+"-"+yyyy,age,quota); 
    }
  }

}

class getTrainFare implements Runnable
{
  Thread th;
  String trainNumber;
  String source;
  String dest;
  String date;
  String age;
  String quota;
  
  public getTrainFare(String trainNumber,String source, String dest, String date, String age, String quota)
  {
    this.trainNumber=trainNumber;
    this.age=age;
    this.date=date;
    this.dest=dest;
    this.source=source;
     
    th=new Thread(this);
    
    th.start();
  }
  
  @Override
  public void run()
  { 
    try
    {
      URL url=new URL("http://api.railwayapi.com/v2/fare/train/"+trainNumber+"/source/"+source+"/dest/"+dest+"/age/"+age+"/quota/"+quota+"/date/"+date+"/apikey/2aemlgjc26/");
      
      URLConnection uc= url.openConnection();
      
      BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream()));
      
      String data=br.readLine();
      
      System.out.println(data); //extra
      
      JSONParser jp=new JSONParser();
      
      JSONObject json=(JSONObject)jp.parse(data);
      
      JSONArray jr= (JSONArray)json.get("fare");
      
      String Title="Train Fare For Different Classes";
      
      String arr[][]=new String[jr.size()][3];
          
      
      for(int i=0;i<jr.size();i++)
      {
        JSONObject fares=(JSONObject)jr.get(i);
        arr[i][0]=(String)fares.get("name");
        arr[i][1]=(String)fares.get("code");
        arr[i][2]=(String)fares.get("fare").toString();
      }
      
      new TableExample2(arr,Title);
      
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,"Bad Internet Connectivity \n Or Wrong Input");  
    }
  }
}


class TableExample2 
{    
    JFrame f;    
    TableExample2(String data[][],String title){    
    f=new JFrame(title);   

    String column[]={"Class","Class Code","Fare"};         
    JTable jt=new JTable(data,column);    
    jt.setBounds(100,50,400,400);          
    JScrollPane sp=new JScrollPane(jt);    
    f.add(sp);          
    f.setSize(400,400);    
    f.setVisible(true);    
  }
}


