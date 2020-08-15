import javax.microedition.io.*;
import javax.bluetooth.*;
import java.io.*;
import javax.microedition.lcdui.*;
import java.util.*;
import javax.microedition.rms.*;

class MatchedDevices extends Form implements CommandListener,ItemCommandListener{

	Command ppCmd;
    Command epCmd ;
	Command opCmd ;
	Command messageCmd ;
    Command backCmd;
	ChoiceGroup devices;
	Form parentForm;
	Display display;
	Messages message;
	SuccessfullySent successfullySent;

	byte[] ppStrByteArray;
	String ppStr;
	ServiceRecord[] records;

	public MatchedDevices(Display display,Form parentForm){
		super("Searched Results");
		System.out.println("before Matched Device Construtor");
		this.display=display;
		this.parentForm=parentForm;
		message=new Messages(display,this);
		successfullySent=new SuccessfullySent();
		System.out.println("after creating objects of message and ss");
		devices=new ChoiceGroup("Matched Devices",Choice.EXCLUSIVE);
		System.out.println("after CG in matched devices");

		ppCmd = new Command("Send Personal Profile", Command.OK, 1);
		epCmd = new Command("Send Educational Profile", Command.OK, 1);
		opCmd = new Command("Send Organizational Profile", Command.OK, 1);
		messageCmd = new Command("Send a Message", Command.OK, 1);
		backCmd = new Command("Back", Command.BACK, 1);

 
		this.addCommand(backCmd);
		this.append(devices);
		devices.addCommand(ppCmd);
		devices.addCommand(epCmd);
		devices.addCommand(opCmd);
		devices.addCommand(messageCmd);
		devices.setItemCommandListener(this);
		setCommandListener(this);
	}

	public void addDevice(String Str){
		devices.append("*"+Str,null);
	//	this.records=records;
	}
	public void commandAction(Command c,Item item){
		if (c==ppCmd){
				//send personal profile to selected device
				try
				{
					ppStrByteArray=Profiles.ravi.getRecord(PersonalProfile.recordID1);
					ppStr=new String(ppStrByteArray);
			
					int i;
					String[] subStr=new String[7];
					int count=0;
					while((ppStr.indexOf(","))>0)
						{
							i=ppStr.indexOf(",");
       						subStr[count++]=ppStr.substring(0,i);
							ppStr=ppStr.substring(i+1);
						}
					String ppSendStr="Name : "+subStr[0]+"\nPhone Number : "+subStr[1]+"\nPlace : "+subStr[2]+"\nAge : "+subStr[3]+"\nEmail : "+subStr[4]+"\nGender : "+subStr[5];
					System.out.println(ppSendStr);
						
/*					String url=records.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT,false);
					StreamConnection conn=(StreamConnection)Connector.open(url);
					DataOutputStream out=conn.openDataOutputStream();
					out.writeUTF("Hai Friend");
					out.flush();
					out.close();
					conn.close();*/


 				    display.setCurrent(successfullySent);
				}
				catch (Exception e)
				{
					System.out.println(e);
				}
			}else if (c==epCmd){
				//send educational profile to selected device
				display.setCurrent(successfullySent);
			}else if (c==opCmd){
				//send organizational profile to selected device
				display.setCurrent(successfullySent);
			}else if (c==messageCmd){
				display.setCurrent(message);
		}
	}
	public void commandAction(Command c,Displayable disp){
		 if (c==backCmd){
			 devices.deleteAll();
			display.setCurrent(parentForm);
		}
	}
};