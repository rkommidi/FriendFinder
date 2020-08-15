import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.bluetooth.*;
import java.io.*;
import javax.microedition.io.*;

class  OrganizationalProfile extends Form  implements CommandListener,Runnable  {
           TextField name,place;
	       ChoiceGroup occupation;
	       Ticker comment;
           Command backCmd,saveCmd;

		   Display display;
		   Profiles profiles;

		   Alert service_alert,serviceC;
		   SuccessfullySaved successfullySaved;

		   RecordStore ravi;
		   String opStr;
		   byte[] opStrByteArray;
		   boolean done;

		   public static int recordID3;
		   static boolean serviceCreated;

    	   private final static int SERVICE_TELEPHONY = 0x400000;
		   static StreamConnectionNotifier conn;
	       LocalDevice ld;
		   ServiceRecord sRecord;
		   UUID uuid;
		   int[] attr=new int[]{0x4501,0x4502,0x4503};




    public OrganizationalProfile(Display display,Profiles profiles,RecordStore ravi,boolean serviceCreated) throws Exception      
		{
		 super("OrganizationalProfile");
		 this.display=display;
		 this.profiles=profiles;
		 this.ravi=ravi;
		 this.serviceCreated=serviceCreated;

		 successfullySaved=new SuccessfullySaved();
		 service_alert=new Alert("Alert","Creating Service",null,AlertType.INFO);
		 service_alert.setTimeout(Alert.FOREVER);
         serviceC=new Alert("Alert","Service Created",null,AlertType.INFO);

	     comment=new Ticker("Enter full name with out caps");
         this.setTicker(comment);
	     name = new TextField(" Organizational Name ","", 25, TextField.ANY);
		 place = new TextField("Place","", 15, TextField.ANY);  		    
		 //Occupation
		 occupation = new ChoiceGroup("Occupation", Choice.POPUP);
         occupation.append("Engineer", null);
		 occupation.append("Doctor", null);    	  	  
	     occupation.append("Lawyer", null);    
		 occupation.append("Manager",null);
         occupation.append("C.A", null); 		
	     occupation.append("Other", null);  			

		 saveCmd=new Command("Save",Command.OK,1);
   	     backCmd=new Command("Back",Command.BACK,2);

	     this.setTicker(comment);
	     this.append(name);
	     this.append(place);
		 this.append(occupation);
		 this.addCommand(saveCmd);
         this.addCommand(backCmd);
		 setCommandListener(this);
		}

	public OrganizationalProfile(Display display,Profiles profiles,RecordStore ravi) throws Exception  
		{
	      super("OrganizationalProfile");
		  this.display=display;
		  this.profiles=profiles;
		  this.ravi=ravi;

		  successfullySaved=new SuccessfullySaved();
		  service_alert=new Alert("Alert","Creating Service",null,AlertType.INFO);
		  service_alert.setTimeout(Alert.FOREVER);
    	  serviceC=new Alert("Alert","Service Created",null,AlertType.INFO);

		  opStrByteArray=ravi.getRecord(recordID3);
		  opStr=new String(opStrByteArray);
				
		  int i;
		  String[] subStr=new String[4];
		  int count=0;
		
		  while((opStr.indexOf(","))>0){
			i=opStr.indexOf(",");
			subStr[count++]=opStr.substring(0,i);
			opStr=opStr.substring(i+1);
		  }
			
		

		  comment=new Ticker("Enter full name with out caps");
    	  this.setTicker(comment);
		  name = new TextField(" Organizational Name ",subStr[0], 25, TextField.ANY);
		  place = new TextField("Place",subStr[1], 15, TextField.ANY);  	
		  
		  //Occupation
		  occupation = new ChoiceGroup("Occupation", Choice.POPUP);
          occupation.append("Engineer", null);
		  occupation.append("Doctor", null);    	  	  
	      occupation.append("Lawyer", null);    
		  occupation.append("Manager",null);
          occupation.append("C.A", null); 		
	      occupation.append("Other", null);  			

		  if(!subStr[2].equals("0")) 
			  {
			    int index=Integer.parseInt(subStr[2]);
				String first=occupation.getString(0);
				String second=occupation.getString(index);
				occupation.set(0,second,null);
				occupation.set(index,first,null);
    		  }


	  	   saveCmd=new Command("Save",Command.OK,1);
   	       backCmd=new Command("Back",Command.BACK,2);

	       this.setTicker(comment);
	       this.append(name);
	       this.append(place);
		   this.append(occupation);
		   this.addCommand(saveCmd);
           this.addCommand(backCmd);
		   setCommandListener(this);
		 }

  public void commandAction(Command c,Displayable disp)
	  {
		if(c==saveCmd)
		{
			//save to RMS
			opStr=name.getString().concat(",").concat(place.getString()).concat(",").concat(""+occupation.getSelectedIndex()).concat(",").concat(occupation.getString(occupation.getSelectedIndex())).concat(",");
			opStrByteArray=opStr.getBytes();

			try{
				if(!serviceCreated )
				{
					recordID3=ravi.addRecord(opStrByteArray,0,opStrByteArray.length);
					System.out.println("op record added at "+recordID3);
					ravi.setRecord(recordID3,opStrByteArray,0,opStrByteArray.length);
					System.out.println("after adding and seting");
					Profiles.opserviceCreated=true;

				}else 
				{
					ravi.setRecord(recordID3,opStrByteArray,0,opStrByteArray.length);
				}
				Thread service=new Thread(this);
				service.start();
				display.setCurrent(service_alert);	
			
			}
			catch (Exception e)
			{
				 Alert alert=new Alert("Information");
				 alert.setString(e+"  ");
				 display.setCurrent(alert);
			}

		}
		else if(c==backCmd)
		{
			display.setCurrent(profiles);
		}
	}



  public void run()
	{
		startService();
		display.setCurrent(serviceC, profiles);
	}
  public void startService()
	{
		uuid=new UUID(0x12345);
		DataElement data_name=new DataElement(DataElement.STRING,name.getString());
		DataElement data_place=new DataElement(DataElement.STRING,place.getString());
        DataElement data_occupation=new DataElement(DataElement.STRING,occupation.getString(occupation.getSelectedIndex()));
		try
		{
			ld=LocalDevice.getLocalDevice();
			if(!serviceCreated)
			{
    			String url="btspp://localhost:"+uuid.toString();
				conn=(StreamConnectionNotifier)Connector.open(url);
				serviceCreated=true;
			}
			
			sRecord=ld.getRecord(conn);
			sRecord.setAttributeValue(0x0008, new DataElement( DataElement.U_INT_1, 0xFF ));
			sRecord.setAttributeValue(attr[0],data_name);
			sRecord.setAttributeValue(attr[1],data_place);
			sRecord.setAttributeValue(attr[2],data_occupation);

			sRecord.setDeviceServiceClasses( SERVICE_TELEPHONY);
			ld.updateRecord(sRecord);
			done=false;
			System.out.println("Service Created");
			
		}
		catch (Exception e)
		{
			done=true;
			System.out.println(e);
		}


	if(!done)
		{
			try
			{
			System.out.println("Waiting for Clients");
			StreamConnection con=conn.acceptAndOpen();
			System.out.println("Got Clients");
			DataInputStream in=con.openDataInputStream();
			String profiles=in.readUTF();
			in.close();
/*			Alert profile=new Alert("Alert","Profile Received",null,AlertType.INFO);
			profile.setTimeout(2000);
			display.setCurrent(profile);*/
			}
			catch (Exception e)
			{
				System.out.println(e);
			}


		}
	}




}//end of class
