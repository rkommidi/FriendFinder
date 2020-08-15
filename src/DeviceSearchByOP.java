import javax.microedition.io.*;
import javax.bluetooth.*;
import java.io.*;
import javax.microedition.lcdui.*;
import java.util.*;
import javax.microedition.rms.*;

public class DeviceSearchByOP implements DiscoveryListener
{
	MatchedDevices matchedDevices;
	NoDeviceFound noDeviceFound;
	LocalDevice local;
	DiscoveryAgent agent;
	UUID[] uuids=new UUID[]{new UUID(0x1101),new UUID(0x12345)};
	ServiceRecord record;
	Vector devices;
	Vector services;

	int[] attributes=new int[]{0x4501,0x4502,0x4503};

	Display display;
	SearchByProfile searchByProfile;
	boolean found=false;

	String opName,opPlace,opOccupation;


	public DeviceSearchByOP(Display display,SearchByProfile searchByProfile)
	{
		this.display=display;
		this.searchByProfile=searchByProfile;

		System.out.println("In DeviceSearch Constructor");
		devices=new Vector();
		services=new Vector();

		matchedDevices=new MatchedDevices(display,searchByProfile);
		noDeviceFound= new NoDeviceFound();
		try
		{
			System.out.println("Record ID is "+OrganizationalProfile.recordID3);
			byte[] opStrByteArray=Profiles.ravi.getRecord(OrganizationalProfile.recordID3);	
			String opStr=new String(opStrByteArray);
				
			int i;
			String[] subStr=new String[4];
			int count=0;
	        while((opStr.indexOf(","))>0){
				i=opStr.indexOf(",");
				subStr[count++]=opStr.substring(0,i);
				opStr=opStr.substring(i+1);
			}
			opName=subStr[0];
			opPlace=subStr[1];
			opOccupation=subStr[3];
			System.out.println(opName+opPlace+opOccupation);
			

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
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
					System.out.println("after rd");
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
		for(int m=0;m<records.length;m++)
		{
				System.out.println("1");
        	 String name=(String)(records[m].getAttributeValue(attributes[0])).getValue();
			 			System.out.println("after 2");
			 String place=(String)(records[m].getAttributeValue(attributes[1])).getValue();
			 			System.out.println("after 3");
			 String occupation=(String)(records[m].getAttributeValue(attributes[2])).getValue();
			 			System.out.println("after 4");

			 System.out.println("Name:"+name+" Place : "+place +" Occupation "+occupation);

			 if((name.equals(opName)) && (place.equals(opPlace)) && (occupation.equals(opOccupation)) )
			  {
				 matchedDevices.addDevice("Organization "+name);
                 found=true;

			  }
		
	      }
	}

	public void serviceSearchCompleted(int transId, int complete)
	{ 
       System.out.println("in servicesearchcompleted");
	   if(found){
		   display.setCurrent(matchedDevices);
	   }else if(!found){
		   display.setCurrent(noDeviceFound, searchByProfile);
	   }
	}
 }
