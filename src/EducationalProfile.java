import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.bluetooth.*;
import java.io.*;
import javax.microedition.io.*;

class  EducationalProfile extends Form implements CommandListener,Runnable
	{

       TextField schoolName, schoolYear,collegeName,collegeYear;
	   ChoiceGroup qualification,university;
	   private Ticker comment;

       Command backCmd, saveCmd;
	   Display display;
	   Profiles profiles;
	   Alert service_alert,serviceC;
	   SuccessfullySaved successfullySaved;
	   RecordStore ravi;
	   String epStr;
	   byte[] epStrByteArray;
	   boolean done;

		 public static int recordID2;
		 static boolean serviceCreated;

    	 private final static int SERVICE_TELEPHONY = 0x400000;
		 static StreamConnectionNotifier conn;
	     LocalDevice ld;
		 ServiceRecord sRecord;
		 UUID uuid;
		 int[] attr=new int[]{0x4201,0x4202,0x4203,0x4204,0x4205,0x4206};

	public EducationalProfile(Display display,Profiles profiles,RecordStore ravi,boolean serviceCreated)
		{
	 		    super("EducationalProfile");
				this.display=display;
				this.profiles=profiles;
				this.ravi=ravi;
				this.serviceCreated=serviceCreated;

				successfullySaved=new SuccessfullySaved();
				 service_alert=new Alert("Alert","Creating Service",null,AlertType.INFO);
				 service_alert.setTimeout(Alert.FOREVER);
		         serviceC=new Alert("Alert","Service Created",null,AlertType.INFO);

				comment=new Ticker("Enter Full Name With Out Caps ");

				schoolName= new TextField("School Name","", 25, TextField.ANY);
     	     	schoolYear = new TextField("School Year","", 4, TextField.NUMERIC);	        		

				collegeName= new TextField("College Name","", 25, TextField.ANY);
     	     	collegeYear = new TextField("College Year","", 4, TextField.NUMERIC);	        		

	            qualification = new ChoiceGroup("Qualification", Choice.POPUP);
				qualification.append("B.ARTS", null);
		        qualification.append("B.COMMERCE", null);    	  	  
	            qualification.append("B.SCIENCE", null);    
                qualification.append("B.ENGINEERING", null); 
			    qualification.append("B.MEDICINE",null);
			    qualification.append("OTHERS",null);

				university= new ChoiceGroup("University", Choice.POPUP);
				university.append("JNTU", null);
		        university.append("OU", null);    	  	  
	            university.append("AU", null);    
                university.append("OTHERS", null); 

    			saveCmd=new Command("Save",Command.OK,1);
    		    backCmd=new Command("Back",Command.BACK,2);

				this.setTicker(comment);
	            this.append(schoolName);
	            this.append(schoolYear);
	            this.append(collegeName);
	            this.append(collegeYear);
	            this.append(qualification);
				this.append(university);
				
				this.addCommand(saveCmd);
                this.addCommand(backCmd);
				
			    setCommandListener(this);
				
		}



		public EducationalProfile(Display display,Profiles profiles,RecordStore ravi) throws Exception
			{
	 		    super("EducationalProfile");
				this.display=display;
				this.profiles=profiles;
				this.ravi=ravi;

				successfullySaved=new SuccessfullySaved();
				 service_alert=new Alert("Alert","Creating Service",null,AlertType.INFO);
				 service_alert.setTimeout(Alert.FOREVER);
				 serviceC=new Alert("Alert","Service Created",null,AlertType.INFO);

				 epStrByteArray=ravi.getRecord(recordID2);
				 epStr=new String(epStrByteArray);
				
				  int i;
				  String[] subStr=new String[8];
				  int count=0;
		
				while((epStr.indexOf(","))>0)
				{
					i=epStr.indexOf(",");
					subStr[count++]=epStr.substring(0,i);
					epStr=epStr.substring(i+1);
				}

				comment=new Ticker("Enter Full Name With Out Caps ");

				schoolName= new TextField("School Name",subStr[0], 25, TextField.ANY);
     	     	schoolYear = new TextField("School Year",subStr[1], 4, TextField.NUMERIC);	        		

				collegeName= new TextField("College Name",subStr[2], 25, TextField.ANY);
     	     	collegeYear = new TextField("College Year",subStr[3], 4, TextField.NUMERIC);	        		

	            qualification = new ChoiceGroup("Qualification", Choice.POPUP);
				qualification.append("B.ARTS", null);
		        qualification.append("B.COMMERCE", null);    	  	  
	            qualification.append("B.SCIENCE", null);    
                qualification.append("B.ENGINEERING", null); 
			    qualification.append("B.MEDICINE",null);
			    qualification.append("OTHERS",null);


			 if(!subStr[4].equals("0")) 
			  {
			    int index=Integer.parseInt(subStr[4]);
				String first=qualification.getString(0);
				String second=qualification.getString(index);
				qualification.set(0,second,null);
				qualification.set(index,first,null);
    		  }


				university= new ChoiceGroup("University", Choice.POPUP);
				university.append("JNTU", null);
		        university.append("OU", null);    	  	  
	            university.append("AU", null);    
                university.append("OTHERS", null); 

			 if(!subStr[6].equals("0")) 
			  {
			    int index=Integer.parseInt(subStr[6]);
				String first=university.getString(0);
				String second=university.getString(index);
				university.set(0,second,null);
				university.set(index,first,null);
    		  }


    			saveCmd=new Command("Save",Command.OK,1);
   	            backCmd=new Command("Back",Command.BACK,2);

				this.setTicker(comment);
	            this.append(schoolName);
	            this.append(schoolYear);
	            this.append(collegeName);
	            this.append(collegeYear);
	            this.append(qualification);
				this.append(university);
				
				this.addCommand(saveCmd);
                this.addCommand(backCmd);
				
			    setCommandListener(this);

    			
				
		}
	public void commandAction(Command c,Displayable disp){
		if(c==saveCmd)
		{
			//save to RMS
			epStr=schoolName.getString().concat(",").concat(schoolYear.getString()).concat(",").concat(collegeName.getString()).concat(",").concat(collegeYear.getString()).concat(",").concat(""+qualification.getSelectedIndex()).concat(",").concat(qualification.getString(qualification.getSelectedIndex())).concat(",").concat(""+university.getSelectedIndex()).concat(",").concat(university.getString(university.getSelectedIndex())).concat(",");
			System.out.println(epStr);
			epStrByteArray=epStr.getBytes();

			try{
				if(!serviceCreated )
				{
					recordID2=ravi.addRecord(epStrByteArray,0,epStrByteArray.length);
					System.out.println("ep record added at "+recordID2);
					ravi.setRecord(recordID2,epStrByteArray,0,epStrByteArray.length);
					System.out.println("after adding and seting");
					//serviceCreated=true;
                    Profiles.epserviceCreated=true;

				}else 
				{
					ravi.setRecord(recordID2,epStrByteArray,0,epStrByteArray.length);
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
		display.setCurrent(serviceC);
	}
  public void startService()
	{
		uuid=new UUID(0x12345);
		DataElement data_schoolname=new DataElement(DataElement.STRING,schoolName.getString());
		DataElement data_schoolyear=new DataElement(DataElement.STRING,schoolYear.getString());
		DataElement data_collegename=new DataElement(DataElement.STRING,collegeName.getString());
		DataElement data_collegeyear=new DataElement(DataElement.STRING,collegeYear.getString());
        DataElement data_qualification =new DataElement(DataElement.STRING,qualification.getString(qualification.getSelectedIndex()));
        DataElement data_university =new DataElement(DataElement.STRING,university.getString(university.getSelectedIndex()));
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
	
			sRecord.setAttributeValue(attr[0],data_schoolname);
			sRecord.setAttributeValue(attr[1],data_schoolyear);
			sRecord.setAttributeValue(attr[2],data_collegename);
			sRecord.setAttributeValue(attr[3],data_collegeyear);
			sRecord.setAttributeValue(attr[4],data_qualification);
			sRecord.setAttributeValue(attr[5],data_university);


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
			System.out.println("Received data in ep:"+profiles);
			in.close();
			con.close();
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
