import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
class UpDate extends Form implements CommandListener{
       TextField name, phoneNumber;
	    Command backCmd,saveCmd;
		Display display;
        ViewBuddyList viewBuddyList;
		SuccessfullySaved successfullySaved;
		BlankInfo blankInfo;
		AlreadyExists alreadyExists;
		AddBuddy addBuddy;
		RecordStore bdyRS;
		int listno;
		byte[] upDateStrByteArray,buddyStrByteArray;
		String upDateStr,buddyStr;
		Buddies buddies;
		String[] subStr;
		int i,count;

   public UpDate(Display display,ViewBuddyList viewBuddyList, RecordStore bdyRS,Buddies buddies,int listno ) throws Exception  {
	 	    super("Update");
			this.display=display;
			this.buddies=buddies;
			this.viewBuddyList=viewBuddyList;
			this.bdyRS= bdyRS;
			this.listno=listno;
			successfullySaved=new SuccessfullySaved();
			blankInfo=new BlankInfo();
			alreadyExists=new AlreadyExists();

			//RMS code

			//To find no of delete buddies
			int num=0,loop;
			addBuddy=new AddBuddy(display,buddies, bdyRS);
		    for(loop=1;loop<=listno;loop++){
				try{
					buddyStrByteArray=bdyRS.getRecord(addBuddy.recordID[loop]);
					buddyStr=new String(buddyStrByteArray);
					count=0;
					String[] subStr= {"",""};
				  	while((buddyStr.indexOf(","))>0){
						i=buddyStr.indexOf(",");
    					subStr[count++]=buddyStr.substring(0,i);
						buddyStr=buddyStr.substring(i+1);
					}
					if(subStr[0].equals("")){
						listno++;
					}
				 }catch(Exception e){
					  Alert alert=new Alert("Information");
				   alert.setString(e+"  ");
				   display.setCurrent(alert);

				}
			
			}

			this.listno=listno;

			//to get the updated record
			upDateStrByteArray=bdyRS.getRecord(addBuddy.recordID[listno]);
			upDateStr=new String(upDateStrByteArray);
			count=0;
			subStr=new String[2];
		  	while((upDateStr.indexOf(","))>0){
				i=upDateStr.indexOf(",");
       			subStr[count++]=upDateStr.substring(0,i);
				upDateStr=upDateStr.substring(i+1);
			}
			//palcing the records in the text field
			name = new TextField("Name",subStr[0], 25, TextField.ANY);
			phoneNumber = new TextField("Phone number",subStr[1], 25, TextField.DECIMAL);
		    saveCmd=new Command("Save",Command.OK,1);
   	        backCmd=new Command("Back",Command.BACK,2);
		   this.append(name);
		   this.append(phoneNumber);
		   this.addCommand(saveCmd);
           this.addCommand(backCmd);
		   setCommandListener(this);
	 }
	 public void commandAction(Command c,Displayable disp){
		 if(c==saveCmd){
			 //save to RMS
			 upDateStr= name.getString().concat(",").concat(phoneNumber.getString()).concat(",");
			 upDateStrByteArray= upDateStr.getBytes();
			 //to check whether it alreday exists and balnk data
		
				 int i,count,loop;
				 for(loop=1;loop<=10;loop++){
					if(loop==listno && loop!=10)
						continue;
					try{
						buddyStrByteArray=bdyRS.getRecord(addBuddy.recordID[loop]);
						buddyStr=new String(buddyStrByteArray);
						count=0;
						String[] subStr= {"",""};
					  	while((buddyStr.indexOf(","))>0){
							i=buddyStr.indexOf(",");
       						subStr[count++]=buddyStr.substring(0,i);
							buddyStr=buddyStr.substring(i+1);
						}
						if(name.getString().equals("") || phoneNumber.getString().equals("")){
							display.setCurrent(blankInfo);
							break;
						}else if(  ( subStr[0].equals(name.getString()) || subStr[1].equals(phoneNumber.getString()) )  && loop!=10){
							display.setCurrent(alreadyExists);
							break;
						}else if(loop==10){
							//placing at the appropriate position
							bdyRS.setRecord(addBuddy.recordID[listno],upDateStrByteArray,0,upDateStrByteArray.length);
							display.setCurrent(successfullySaved, buddies);
							break;
						}
					}catch(Exception e){
						  Alert alert=new Alert("Information");
							alert.setString(e+"  ");
						 display.setCurrent(alert);

					}
				}
	
		 }else if(c==backCmd){
			 display.setCurrent(viewBuddyList);
		}
	}
}