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
public class LiveTrainStatus extends JPanel implements ItemListener,ActionListener 
{
  JLabel jl1,jl2,jl3,jl4;
  JTextField jtf1;
  JComboBox jcb1,jcb2,jcb3;
  JButton jb;
  Font f,f1;
  String dd="1",mm="1",yyyy="2017";
  String trainNumber="";
  LiveTrainStatus()
  {
    this.setLayout(null);
    Toolkit tk=Toolkit.getDefaultToolkit();
    Dimension d=tk.getScreenSize();
    
    f=new Font("Serif",Font.PLAIN,22);
    f1=new Font("Serif",Font.BOLD,22);
    jl1=new JLabel("Enter the train Number :");
    jl2=new JLabel("Enter the train start date (DD-MM-YYYY) :");
    jl3=new JLabel("");
    jl4=new JLabel("");
    
    jtf1=new JTextField(10);
    jb=new JButton("Get Train Status");
    
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
    jtf1.setFont(f1);
    
    jl1.setFont(f);
    jl2.setFont(f);
    jl3.setFont(f);
    jl4.setFont(f);
    
    jcb1.setFont(f);
    jcb2.setFont(f);
    jcb3.setFont(f);
    
    jl1.setBounds(20,20,250,30);   //hardcoded
    jl2.setBounds(20,70,400,30);  //hardcoded
    
    
    
    jtf1.setBounds(430,20,200,30);  //hardcoded
    jcb1.setBounds(430,70,50,30);
    jcb2.setBounds(490,70,50,30);
    jcb3.setBounds(550,70,100,30);
    jb.setBounds(430,120,200,30);
    
    jl3.setBounds(20,160,1000,30);
    jl4.setBounds(20,200,1000,30);
    
    jcb1.addItemListener(this);
    jcb2.addItemListener(this);
    jcb3.addItemListener(this);
    jb.addActionListener(this);
    
    this.add(jtf1);
    this.add(jl1);
    this.add(jl2);
    this.add(jcb1);
    this.add(jcb2);
    this.add(jcb3);
    this.add(jb);
    this.add(jl3);
    this.add(jl4);
    
    this.setBounds(d.width/6,0,d.width,d.height);
    this.setBackground(Color.LIGHT_GRAY);
   
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
      trainNumber=jtf1.getText();  
      HTTPRequestSender htps=new HTTPRequestSender(dd,mm,yyyy,trainNumber,jl3,jl4);
    }
  }
}

class HTTPRequestSender implements Runnable
{
    
  String tableData[][],dd="",mm="",yyyy="",trainNumber="";   
  JLabel jl3,jl4;
  Thread th;
  HTTPRequestSender(String dd,String mm,String yyyy,String trainNumber,JLabel jl3,JLabel jl4)
  {
    this.dd=dd;
    this.mm=mm;
    this.yyyy=yyyy;
    this.trainNumber=trainNumber;
    this.jl3=jl3;
    this.jl4=jl4;
    
    th=new Thread(this);
    th.start();
    //System.out.println("asdfasdasdfasdfasdfasdfasdfasdfadsfsdf");
  }
  public void run()
  {
     try
       {     
         URL railway=new URL("http://api.railwayapi.com/v2/live/train/"+trainNumber+"/date/"+dd+"-"+mm+"-"+yyyy+"/apikey/2aemlgjc26/");
    
         URLConnection uc= railway.openConnection();
    
         BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream()));
    
         String str;
    
         str=br.readLine();
         
       //  System.out.println(str);
         JSONParser jp=new JSONParser();
         if(str!=null)
         {
           Object obj= jp.parse(str);
           JSONObject jsonob=(JSONObject)obj;
    
           JSONObject jsonob1=(JSONObject)jsonob.get("train");
           //System.out.println(arr);
           
           JSONArray myarr=(JSONArray)jsonob.get("route");
             
           String strp=(String)jsonob.get("position");
           String strtn=(String)jsonob1.get("name");
           
           jl3.setVisible(false);
           jl3.setText("Current Position :"+strp);
           jl3.setVisible(true);
           
           jl4.setVisible(false);
           jl4.setText("Train Name :"+strtn);
           jl4.setVisible(true);
           
           tableData=new String[myarr.size()][12];
           
           for(int i=0;i<myarr.size();i++)
           {
             tableData[i][0]=(String)(((((JSONObject)(myarr.get(i))).get("no")))+"");
             tableData[i][1]=(String)((((JSONObject)(myarr.get(i))).get("day"))+"");
             tableData[i][2]=(String)(((JSONObject)(((JSONObject)(myarr.get(i))).get("station"))).get("name"));
             tableData[i][3]=(String)(((JSONObject)(((JSONObject)(myarr.get(i))).get("station"))).get("code"));
             tableData[i][4]=(String)((((JSONObject)(myarr.get(i))).get("has_arrived"))+"");
             tableData[i][5]=(String)((((JSONObject)(myarr.get(i))).get("has_departed"))+"");
             tableData[i][6]=(String)((((JSONObject)(myarr.get(i))).get("distance"))+"");
             tableData[i][7]=(String)((((JSONObject)(myarr.get(i))).get("scharr"))+"");
             tableData[i][8]=(String)((((JSONObject)(myarr.get(i))).get("schdep"))+"");
             tableData[i][9]=(String)((((JSONObject)(myarr.get(i))).get("actarr"))+"");
             tableData[i][10]=(String)((((JSONObject)(myarr.get(i))).get("actdep"))+"");
             tableData[i][11]=(String)((((JSONObject)(myarr.get(i))).get("status"))+"");
             
             //((JSONObject)(myarr.get(i))).get("station");
           }
           
            new TableExample(tableData);
          }    
         br.close();
        }
        catch(Exception e1)
        {
          JOptionPane.showMessageDialog(null,"Bad Internet Connectivity Or Wrong Data");  
          //e1.printStackTrace();
        }
  }
}

class TableExample {    
    JFrame f;    
    TableExample(String data[][]){    
    f=new JFrame();        
    String column[]={"Station No.","Day","Station","Station Code","Has Arrived","Has Departed","Distance","Sch Arr","Sch Dept","Act Arr","Act Dept","Delayed By"};         
    JTable jt=new JTable(data,column);    
    jt.setBounds(100,50,1200,500);          
    JScrollPane sp=new JScrollPane(jt);    
    f.add(sp);          
    f.setSize(1200,500);    
    f.setVisible(true);    
  }
}    