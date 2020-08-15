import javax.microedition.io.*;
import javax.bluetooth.*;
import java.io.*;
import javax.microedition.lcdui.*;
import java.util.*;
import javax.microedition.rms.*;

public class DeviceSearchByOthers implements DiscoveryListener
{
	MatchedDevices matchedDevices;
	NoDeviceFound noDeviceFound;
	LocalDevice local;
	DiscoveryAgent agent;
	UUID[] uuids=new UUID[]{new UUID(0x1101),new UUID(0x12345)};
	ServiceRecord record;
	Vector devices;
	Vector services;
	String[]  userOthers;
	byte[] userOthersArray;
	int[] attributes=new int[]{0x4101,0x4103,0x4104,0x4105};

	Display display;
	Others others;
	int a;
	String  g, h, o;
	boolean found=false;
	boolean ageFound,genderFound,hobbiesFound;


	public DeviceSearchByOthers(Display display,Others others, int a,String g, String h)
	{
		this.a=a;
		this.g=g;
//		this.o=o;
		this.h=h;
		this.display=display;
		this.others=others;
		System.out.println("In DeviceSearch Constructor");
		devices=new Vector();
		services=new Vector();
		matchedDevices=new MatchedDevices(display,others);
		noDeviceFound= new NoDeviceFound();
	
	}

	public void startInquiry()
	{
		try
		{
			local=LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
			agent=local.getDiscoveryAgent();
			agent.startInquiry(DiscoveryAgent.GIAC,this);
			System.out.println("Got DiscoveryAgent");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

  public void deviceDiscovered(RemoteDevice remoteDevice,DeviceClass deviceClass)
  {
	  System.out.println("A remote Bluetooth device is discovered:");
	  devices.addElement( remoteDevice );
  }

  public void inquiryCompleted(int complete)
  {
      int[] searchIDs=new int[devices.size()];
	  System.out.println("in inquirycompleted");
	  if(devices.size()==0)
	  {
		  display.setCurrent(noDeviceFound);
	  }else
	  {
			for(int i=0;i<devices.size();i++)
				{     
					RemoteDevice rd = (RemoteDevice) devices.elementAt(i);
					try
						{
			              searchIDs[i] = agent.searchServices(attributes, uuids,rd, this);
						  System.out.println("Searching for services");
						}catch (Exception e)
						{ 
						  System.err.println("Can't search services for: "+ rd.getBluetoothAddress() + " due to " + e);
						    continue;
						}
				 }
	  }
   }                                         

   public void servicesDiscovered(int transId, ServiceRecord[] records)
   {
		System.out.println("in servicesDiscovered");
		System.out.println(records.length);

		for(int m=0;m<records.length;m++)
		{
			 String name=(String)(records[m].getAttributeValue(attributes[0])).getValue();
			 String age=(String)(records[m].getAttributeValue(attributes[1])).getValue();
			 String gender=(String)(records[m].getAttributeValue(attributes[2])).getValue();
			// String occupation=(String)(records[m].getAttributeValue(attributes[3])).getValue();
			 String hobbies=(String)(records[m].getAttributeValue(attributes[3])).getValue();

		    int j= Integer.parseInt( age);
			System.out.println(" values from others are                   "+j+g+h);
			System.out.println("after getting values from services   "+name+j+gender+hobbies);

			 switch (a){
				 case 0:

					 ageFound = true;
 					 System.out.println("ageFound is true in any");
					 break;
				case 1:
					 if(j<15){
						 ageFound=true;
					}else
						ageFound=false;
					break;

				case 2:
					 if (j>=15&&j<=20)
					{
						 ageFound=true;
 					}else
						 ageFound=false;
	    	        break;
					
				case 3:
					 if (j>=21&&j<=25)
					{
						ageFound=true;
					}else
						ageFound=false;
					break;
						
				case 4:
					 if (j>=26&&j<=30)
					{
						ageFound=true;
					}else
						 ageFound=false;
					break;
		
				case 5:
					 if (j>=31&&j<=35)
					{
						ageFound=true;
					}else
						 ageFound=false;
					break;
					
				 case 6:
					if (j>36)
					{
						ageFound=true;
					}else
						 ageFound=false;
					break;
				
			}
			if(ageFound==true)
			{
				System.out.println("ageFound is true");
			}


			if (g.equals("Any")||g.equals(gender))
			{
 					 System.out.println("gender Found is true");
				genderFound=true;
			}else  
					genderFound=false;
	    	


/*			if (o.equals("Any")||o.equals(occupation))
			{
				occupationFound=true;
			}else 
				   occupationFound=false;*/

            
			if (h.equals("Any")||h.equals(hobbies))
			{
				 System.out.println(" hobby Found is true");
				 hobbiesFound=true;
			}else
				 hobbiesFound=false;
     
	        if ((ageFound)&&(genderFound)&&(hobbiesFound))
	        {
					matchedDevices.addDevice(name);
					found=true;

	        }
    }//end of for loop
}//end of method
	

	public void serviceSearchCompleted(int transId, int complete)
	{ 
       System.out.println("in servicesearchcompleted");
	   if(found){
		   display.setCurrent(matchedDevices);
	   }else if(!found){
		   display.setCurrent(noDeviceFound,others);
	   }
	}

 }
