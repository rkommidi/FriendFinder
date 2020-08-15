import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.bluetooth.*;
import java.io.*;
import javax.microedition.io.*;

class  PersonalProfile extends Form implements CommandListener,Runnable  
	{
      TextField name,phone,place,age, email;
	  ChoiceGroup gender,hobbies;
	  Ticker comment;
      Command backCmd,saveCmd;

	  Display display;
	  Profiles profiles;

	  Alert service_alert,serviceC;
	  SuccessfullySaved successfullySaved;

	  static RecordStore ravi;
	  String ppStr;
	  byte[] ppStrByteArray;
	  boolean done;
	  
	  public static int recordID1;
	  static boolean serviceCreated;

	  private final static int SERVICE_TELEPHONY = 0x400000;
	  static StreamConnectionNotifier conn;
	  LocalDevice ld;
	  ServiceRecord sRecord;
	  UUID uuid;
	  int[] attr=new int[]{0x4101,0x4102,0x4103,0x4104,0x4105};

	public PersonalProfile(Display display,Profiles profiles,RecordStore ravi,boolean serviceCreated){
	 	    super("PersonalProfile");
			this.display=display;
			this.profiles=profiles;
			this.ravi=ravi;
			this.serviceCreated=serviceCreated;
			
			comment=new Ticker("Enter full name with out caps");
    	    this.setTicker(comment);

			successfullySaved=new SuccessfullySaved();
			service_alert=new Alert("Alert","Creating Service",null,AlertType.INFO);
			service_alert.setTimeout(Alert.FOREVER);
			serviceC=new Alert("Alert","Service Created",null,AlertType.INFO);


			name = new TextField("Name","", 15, TextField.ANY);
			phone = new TextField("Phone Number","", 12, TextField.PHONENUMBER);
		    place = new TextField("Place","", 10, TextField.ANY);
			age = new TextField("Age","",2,TextField.NUMERIC);
			email = new TextField("Email","",30,TextField.EMAILADDR);
			  
   			//gender choice group
		    gender = new ChoiceGroup("Gender", Choice.POPUP);
			gender.append("Male", null);
		    gender.append("Female", null);  
			
			//hobbies choice group
			 hobbies = new ChoiceGroup("Hobbies", Choice.POPUP);
             hobbies.append("Technology", null);
		     hobbies.append("Entertainment", null);    	  	  
	         hobbies.append("Sports", null);    
             hobbies.append("Philately", null); 
			 hobbies.append("Reading books",null);
	         hobbies.append("Other", null);  
			 
			 saveCmd=new Command("Save",Command.OK,1);
   	         backCmd=new Command("Back",Command.BACK,2);
	         this.setTicker(comment);
		     this.append(name);
		     this.append(phone);
			 this.append(place);
			 this.append(age);
		  	 this.append(gender);
			 this.append(email);
			 this.append(hobbies);
			 this.addCommand(saveCmd);
			 this.addCommand(backCmd);
			setCommandListener(this);
		 }


	 public PersonalProfile(Display display,Profiles profiles,RecordStore ravi) throws Exception{
	 	    super("PersonalProfile");
			this.display=display;
			this.profiles=profiles;
			this.ravi=ravi;
			
			System.out.println("pp record at "+recordID1);
			ppStrByteArray=ravi.getRecord(recordID1);
			
			
			ppStr=new String(ppStrByteArray);
			
			int i;
			String[] subStr=new String[7];
			int count=0;
			while((ppStr.indexOf(","))>0){
						i=ppStr.indexOf(",");
       					subStr[count++]=ppStr.substring(0,i);
						ppStr=ppStr.substring(i+1);
			}
			
			comment=new Ticker("Enter full name with out caps");
    	    this.setTicker(comment);

			successfullySaved=new SuccessfullySaved();
			service_alert=new Alert("Alert","Creating Service",null,AlertType.INFO);
			service_alert.setTimeout(Alert.FOREVER);
			serviceC=new Alert("Alert","Service Created",null,AlertType.INFO);


			name = new TextField("Name",subStr[0], 15, TextField.ANY);
			phone = new TextField("Phone Number",subStr[1], 12, TextField.PHONENUMBER);
		    place = new TextField("Place",subStr[2], 10, TextField.ANY);
			age = new TextField("Age",subStr[3],2,TextField.NUMERIC);
			email = new TextField("Email",subStr[4],30,TextField.EMAILADDR);
			  
   			//gender choice group
		    gender = new ChoiceGroup("Gender", Choice.POPUP);
			
	     
			gender.append("Male", null);
		    gender.append("Female", null);  
			
			if(!subStr[5].equals("Male")){
				gender.deleteAll();
				gender.append("Female",null);
				gender.append("Male",null);
			}
			
		
   
			//hobbies choice group
			 hobbies = new ChoiceGroup("Hobbies", Choice.POPUP);
             hobbies.append("Technology", null);
		     hobbies.append("Entertainment", null);    	  	  
	         hobbies.append("Sports", null);    
             hobbies.append("Philately", null); 
			 hobbies.append("Reading books",null);
	         hobbies.append("Other", null);  
	  	
	  if(!subStr[6].equals("0"))
		 {
				int index=Integer.parseInt(subStr[6]);
				String first=hobbies.getString(0);
				String second=hobbies.getString(index);
				hobbies.set(0,second,null);
				hobbies.set(index,first,null);

		 }
			
			 
			 saveCmd=new Command("Save",Command.OK,1);
   	         backCmd=new Command("Back",Command.BACK,2);
	         this.setTicker(comment);
			 this.append(name);
		     this.append(phone);
		     this.append(place);
			 this.append(age);
		  	 this.append(gender);
			 this.append(email);
			 this.append(hobbies);
			 this.addCommand(saveCmd);
		     this.addCommand(backCmd);
			 setCommandListener(this);
		 }


	public void commandAction(Command c,Displayable disp){
		if(c==saveCmd){
			//save to RMS
			ppStr=name.getString().concat(",").concat(phone.getString()).concat(",").concat(place.getString()).concat(",").concat(age.getString()).concat(",").concat(email.getString()).concat(",").concat(gender.getString(gender.getSelectedIndex())).concat(",").concat(""+hobbies.getSelectedIndex()).concat(",");
			ppStrByteArray=ppStr.getBytes();
			try{
				if(!serviceCreated )
				{
					recordID1=ravi.addRecord(ppStrByteArray,0,ppStrByteArray.length);
					System.out.println("pp record added at "+recordID1);
					ravi.setRecord(recordID1,ppStrByteArray,0,ppStrByteArray.length);
					System.out.println("after adding and seting");
					Profiles.ppserviceCreated=true;

				}else 
				{
					ravi.setRecord(recordID1,ppStrByteArray,0,ppStrByteArray.length);
				}
				Thread service=new Thread(this);
				service.start();
				display.setCurrent(service_alert);				
			}catch (Exception e){
				 Alert alert=new Alert("Information");
			   alert.setString(e+"  ");
			   display.setCurrent(alert);
			}
		}else if(c==backCmd){
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
		DataElement data_phone=new DataElement(DataElement.STRING,phone.getString());
		DataElement data_age=new DataElement(DataElement.STRING,age.getString());
        DataElement data_gender=new DataElement(DataElement.STRING,gender.getString(gender.getSelectedIndex()));
        DataElement data_hobbies=new DataElement(DataElement.STRING,hobbies.getString(hobbies.getSelectedIndex()));
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
			sRecord.setAttributeValue(attr[1],data_phone);
			sRecord.setAttributeValue(attr[2],data_age);
			sRecord.setAttributeValue(attr[3],data_gender);
			sRecord.setAttributeValue(attr[4],data_hobbies);

			sRecord.setDeviceServiceClasses( SERVICE_TELEPHONY  );
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

}
