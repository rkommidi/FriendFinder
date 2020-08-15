import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
class  Delete extends Alert implements CommandListener{
	private Image deleteImage;
	private final static Command noCmd = new Command("No", Command.BACK, 
                                                        2);
    private final static Command yesCmd = new Command("Yes", Command.OK, 
                                                        1);
	Display display;
	ViewBuddyList viewBuddyList;
	Buddies buddies;
	AddBuddy addBuddy;

	RecordStore bdyRS;
	int listno,i,count;
	byte[] buddyStrByteArray;
	String buddyStr;

	public Delete(Display display,ViewBuddyList viewBuddyList, RecordStore bdyRS,Buddies buddies,int listno){
		super(" Deletion Conformation ");
		this.display=display;
		this.bdyRS=bdyRS;
		this.viewBuddyList=viewBuddyList;
		this.bdyRS= bdyRS;
		this.buddies=buddies;
		this.listno=listno;
		addBuddy=new AddBuddy(display,buddies, bdyRS);


		//To find no of delete buddies
		int num=0,loop;
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

		try
		{
	    deleteImage = Image.createImage("/addbuddy.png");
		 } catch (java.io.IOException e){
				  Alert alert=new Alert("Information");
				   alert.setString(e+"  ");
				   display.setCurrent(alert);
           
         }
          this.setType(AlertType.INFO);
          this.setTimeout(Alert.FOREVER);
		  this.setImage(deleteImage);
          this.setString("Are You Sure You to Delete");
		  this.addCommand(noCmd);
		  this.addCommand(yesCmd);
		  setCommandListener(this);
	}
	public void commandAction(Command c,Displayable disp){
		if(c==yesCmd){
			//delete the buddy from the buddy list
			String delStr=",,";
			byte[] deleteStrArray=delStr.getBytes();
			try{
				bdyRS.setRecord(addBuddy.recordID[listno],deleteStrArray,0,deleteStrArray.length);
			}catch(Exception e){
				  Alert alert=new Alert("Information");
				   alert.setString(e+"  ");
				   display.setCurrent(alert);
				
			}

			// RMS CODE
			display.setCurrent(buddies);
		}else if(c==noCmd){
			//do not delete buddy from the buddy list

			display.setCurrent(viewBuddyList);
		}
	}

}
