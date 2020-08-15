import javax.microedition.io.*;
import javax.bluetooth.*;
import java.io.*;
import javax.microedition.lcdui.*;
import java.util.*;
import javax.microedition.rms.*;

public class DeviceSearchByEP implements DiscoveryListener
{
	MatchedDevices matchedDevices;
	NoDeviceFound noDeviceFound;
	LocalDevice local;
	DiscoveryAgent agent;
	UUID[] uuids=new UUID[]{new UUID(0x1101),new UUID(0x12345)};
	ServiceRecord record;
	RemoteDevice rd;
	Vector devices;
	Vector services;
	int num=1;

	int[] attributes=new int[]{0x4201,0x4202,0x4203,0x4204,0x4205,0x4206};

	Display display;
	SearchByProfile searchByProfile;
	boolean found=false;

	String schoolName,schoolYear,collegeName,collegeYear,qualification,university;


	public DeviceSearchByEP(Display display,SearchByProfile searchByProfile)
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
			System.out.println("Record ID is "+EducationalProfile.recordID2);
			byte[] epStrByteArray=Profiles.ravi.getRecord(EducationalProfile.recordID2);	
			String epStr=new String(epStrByteArray);
				
			int i;
			String[] subStr=new String[8];
			int count=0;
	        while((epStr.indexOf(","))>0){
				i=epStr.indexOf(",");
				subStr[count++]=epStr.substring(0,i);
				epStr=epStr.substring(i+1);
			}
			schoolName=subStr[0];
			schoolYear=subStr[1];
			collegeName=subStr[2];
			collegeYear=subStr[3];
			qualification=subStr[5];
			university=subStr[7];
			System.out.println(schoolName+schoolYear+collegeName+collegeYear+qualification+university);
			

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
				
					rd = (RemoteDevice) devices.elementAt(i);
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
        	 String schoolname=(String)(records[m].getAttributeValue(attributes[0])).getValue();
			 String schoolyear=(String)(records[m].getAttributeValue(attributes[1])).getValue();
			 String collegename=(String)(records[m].getAttributeValue(attributes[2])).getValue();
			 String collegeyear=(String)(records[m].getAttributeValue(attributes[3])).getValue();
			 String qualificationRD=(String)(records[m].getAttributeValue(attributes[4])).getValue();
			 String universityRD=(String)(records[m].getAttributeValue(attributes[5])).getValue();

			 System.out.println("schoolName:"+schoolname+" school Year: "+schoolyear+"college Name:"+collegename+" college Year: "+collegeyear+" qualification"+qualificationRD+"university :"+universityRD);

			 if (   (   (schoolName.equals(schoolname))  && (schoolYear.equals(schoolyear))   )  ||   (  (collegeName.equals(collegename))  && (collegeYear.equals(collegeyear)) && (qualification.equals(qualificationRD))  )  ||  ( university.equals(universityRD) )  )
			  {
				 try
					 {
						matchedDevices.addDevice(num+" "+rd.getBluetoothAddress() +rd.getFriendlyName(true));
						String url=records[m].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT,false);
					StreamConnection conn=(StreamConnection)Connector.open(url);
					DataOutputStream out=conn.openDataOutputStream();
					out.writeUTF("Hai Friend");
					out.flush();
					out.close();
					conn.close();
						
					}catch (Exception e)
					{
						 System.out.println(e);
					 }
					num++;
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
