import javax.microedition.io.*;
import javax.bluetooth.*;
import java.io.*;
import javax.microedition.lcdui.*;
import java.util.*;
import javax.microedition.rms.*;

public class DeviceSearchByBuddy implements DiscoveryListener
{
	MatchedDevices matchedDevices;
	NoDeviceFound noDeviceFound;
	LocalDevice local;
	DiscoveryAgent agent;
	UUID[] uuids=new UUID[]{new UUID(0x1101),new UUID(0x12345)};
	ServiceRecord record;
	Vector devices;
	Vector services;
	String[][] userNP;
//	byte[][] namePhone;
	int[] attributes=new int[]{0x4101,0x4102};

	Display display;
	Search search;
	boolean found=false;


	public DeviceSearchByBuddy(Display display,Search search)
	{
		this.display=display;
		this.search=search;
		System.out.println("In DeviceSearch Constructor");
		devices=new Vector();
		services=new Vector();
		matchedDevices=new MatchedDevices(display,search);
		noDeviceFound= new NoDeviceFound();
		int i,j;
		//String[] namePhoneStr=new String[11];
		userNP=new String[2][11];
		/*for(int k=1;k<=10;k++)
		{
			try
				{
				System.out.println("Record ID is "+k);
				namePhone[k]=Buddies.bdyRS.getRecord(k);	
				}
			catch (Exception e)
			{
				System.out.println(e);
			}*/
			String[] namePhoneStr=new String[11];
			String s;
			int k=0;
		try
		{
			RecordEnumeration re=Buddies.bdyRS.enumerateRecords(null,null,false);
			while(re.hasNextElement())
			{
				s=null;
				byte[] b=re.nextRecord();
				s=new String(b);
				namePhoneStr[k]=s;
				i=0;
				j=0;
				while((i=namePhoneStr[k].indexOf(","))>0)
				{
					userNP[j][k]=namePhoneStr[k].substring(0,i);
					namePhoneStr[k]=namePhoneStr[k].substring(i+1);
					j++;
				}
				System.out.println("Name:"+userNP[0][k]+"  Phone Number:"+userNP[1][k] );
				k++;
			}
		}catch(Exception e){
			System.out.println(e);
		}
			System.out.println("after exception");
			
		//System.out.println("DeviceSearch Constructor");
	//	}
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
			 String name=(String)(records[m].getAttributeValue(attributes[0])).getValue();
			 String phone=(String)(records[m].getAttributeValue(attributes[1])).getValue();

			 System.out.println("Name:"+name+" Phone Number:"+phone );


			for(int l=1;l<=10;l++)
			{
				if(phone.equals(userNP[1][l]))
					{
						matchedDevices.addDevice(name);
						found=true;
						break;
					}



			/*	if(gender.equals(userprofile[1]) && school.equals(userprofile[2]) && college.equals(userprofile[3]))
					{
						 Alert friend=new Alert("Alert","One Friend Founded his Name is:"+name,null,AlertType.INFO);
						friend.setTimeout(Alert.FOREVER);
						 FriendFinderDemo.display.setCurrent(friend);

				String url=record(m).getConnectionURL();
				 StreamConnection con=(StreamConnection)Connector.open(url);
				 DataOutputStream out=con.openDataOutputStream();
				 String response="Hai Friend";
				 out.writeUTF(response);
				 out.flush();
				 out.close();
				 con.close();


					 }*/
			  }
		}
	}

	public void serviceSearchCompleted(int transId, int complete)
	{ 
       System.out.println("in servicesearchcompleted");
	   if(found){
		   display.setCurrent(matchedDevices);
	   }else if(!found){
		   display.setCurrent(noDeviceFound, search);
	   }
	}
 }
