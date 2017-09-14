/*Application Designed By Abhijeet Rawat */
package MyProjectPkg;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
  

public class DashBoard 
{     
  public static void main(String args[])
  {
    DashBoardFrame djf=new DashBoardFrame("Welcome To Indian Railways");          
  }
}


class DashBoardFrame extends JFrame
{
  LeftPanel lft;
  ArrayList<String>lst;
  ArrayList<Object>rhtpanels;
  
  DashBoardFrame(String str)
  {
    super(str);
    Toolkit tk=Toolkit.getDefaultToolkit();
    Dimension d= tk.getScreenSize();
    lst=new ArrayList();
    lst.add("Live Train Status");
    lst.add("PNR Status");
    lst.add("Seat Availability");
    lst.add("Trains Between Stations");
    lst.add("Train Fare Enquiry");
    
    rhtpanels=new ArrayList();
    rhtpanels.add(new LiveTrainStatus());
    rhtpanels.add(new PNRStatus());
    rhtpanels.add(new SeatAvailability());
    rhtpanels.add(new TrainsBetweenStations());
    rhtpanels.add(new TrainFareEnquiry());
    
    ((LiveTrainStatus)(rhtpanels.get(0))).setVisible(true);
    ((PNRStatus)(rhtpanels.get(1))).setVisible(false);
    ((SeatAvailability)(rhtpanels.get(2))).setVisible(false);
    ((TrainsBetweenStations)(rhtpanels.get(3))).setVisible(false);
    ((TrainFareEnquiry)(rhtpanels.get(4))).setVisible(false);
    
    lft=new LeftPanel(lst,rhtpanels);
    
    
    this.add(lft);
    this.add((LiveTrainStatus)(rhtpanels.get(0)));
    this.add((PNRStatus)(rhtpanels.get(1)));        
    this.add((SeatAvailability)(rhtpanels.get(2)));        
    this.add((TrainsBetweenStations)(rhtpanels.get(3)));
    this.add((TrainFareEnquiry)(rhtpanels.get(4)));
    
    this.setLayout(null);
    this.setVisible(true);
    this.setBounds(0,0,d.width,d.height);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}


class LeftPanel extends JPanel implements ActionListener
{
  JButton jb[];
  JLabel jl;
  Font f;
  ArrayList<Object> rhtpanels;
  
  LeftPanel(ArrayList<String> lst, ArrayList<Object> rhtpanels )
  {
    this.rhtpanels=rhtpanels; 
    Toolkit tk=Toolkit.getDefaultToolkit();
    Dimension d=tk.getScreenSize();
    Font f=new Font("Serif",Font.PLAIN,25);
    this.setLayout(null);
    this.setBackground(Color.GRAY);
    
    jb=new JButton[lst.size()];
    jl=new JLabel("MENU",SwingConstants.CENTER);
    jl.setFont(f);
    jl.setForeground(Color.BLACK);
    jl.setBounds(5,5,d.width/6-10,d.height/25); 
    add(jl);
    
    for(int i=0;i<lst.size();i++)
    {
      jb[i]=new JButton(lst.get(i));
      jb[i].setBounds(5,d.height/25+5*(i+1)+i*d.height/25, d.width/6-10,d.height/25);  
      jb[i].addActionListener(this);
      add(jb[i]);
    }
    
    this.setBounds(0,0,d.width/6,d.height); 
    
  }
  
  
  public void actionPerformed(ActionEvent e)
  {
    ((LiveTrainStatus)(rhtpanels.get(0))).setVisible(false);
    ((PNRStatus)(rhtpanels.get(1))).setVisible(false);
    ((SeatAvailability)(rhtpanels.get(2))).setVisible(false);
    ((TrainsBetweenStations)(rhtpanels.get(3))).setVisible(false);
    ((TrainFareEnquiry)(rhtpanels.get(4))).setVisible(false);
      
    for(int i=0;i<jb.length;i++)
    {
      if(e.getSource()==jb[i])
      {
       if(i==0)
        ((LiveTrainStatus)(rhtpanels.get(0))).setVisible(true);   
       else if(i==1)
         ((PNRStatus)(rhtpanels.get(1))).setVisible(true);  
       else if(i==2)
        ((SeatAvailability)(rhtpanels.get(2))).setVisible(true);
       else if(i==3)
        ((TrainsBetweenStations)(rhtpanels.get(3))).setVisible(true);   
       else if(i==4)    
         ((TrainFareEnquiry)(rhtpanels.get(4))).setVisible(true);  
      }
    }
  }
}

