import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
class Buddies extends Form implements CommandListener,ItemCommandListener  {
	Command backCmd,okCmd;
	Image addImage,viewImage;
	ImageItem addImageItem,viewImageItem;
	AddBuddy addBuddy;
	ViewBuddyList viewBuddyList;
	FriendFinder friendFinder;
	Display display;

	static RecordStore bdyRS;
	String[] buddyName,buddyNumber;
	String buddyStr;
	byte[] buddyStrByteArray;

	public Buddies(Display display,FriendFinder friendFinder) throws Exception {
		super("Buddies");
		 System.out.println("before buddies constructor");
		this.display=display;
		this.friendFinder=friendFinder;
		bdyRS= RecordStore.openRecordStore("buddies",true);

		try{
			addImage=Image.createImage("/addbuddy.png");
			viewImage=Image.createImage("/viewbuddy.jpg");
		}catch (java.io.IOException e){
			 Alert alert=new Alert("Information");
		   alert.setString(e+"  ");
		   display.setCurrent(alert);


		}

		addImageItem=new ImageItem("Add Buddy",addImage,Item.LAYOUT_LEFT,null,Item.BUTTON);
	    viewImageItem=new ImageItem("View Buddy List",viewImage,Item.LAYOUT_RIGHT,null,Item.BUTTON);

		okCmd=new Command("Ok",Command.OK,1);
   		backCmd=new Command("Back",Command.BACK,2);

		addImageItem.setDefaultCommand(okCmd);
		viewImageItem.setDefaultCommand(okCmd);

		this.append(addImageItem);
		this.append(viewImageItem);

		addImageItem.setItemCommandListener(this);
	    viewImageItem.setItemCommandListener(this);

		this.addCommand(backCmd);
		setCommandListener(this);
		System.out.println("after buddies constructor");
	}
	public void commandAction(Command c,Displayable disp){
		if(c==backCmd){
			display.setCurrent(friendFinder);
		}
	}
	public void commandAction(Command c,Item item){
		 if (c == List.SELECT_COMMAND || c==okCmd) {
				if (item==addImageItem){
						addBuddy=new AddBuddy(display,this, bdyRS);
					  display.setCurrent(addBuddy);
				}else if (item==viewImageItem){
					int i;
					buddyName=new String[10];
					buddyNumber=new String[10];
					int count,num=0,loop;
					addBuddy=new AddBuddy(display,this, bdyRS);
					for(loop=1;loop<=10;loop++){
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
							buddyName[num]=subStr[0];
							buddyNumber[num]=subStr[1];
							num++;
						}catch(Exception e){
							 Alert alert=new Alert("Information");
							alert.setString(e+"  ");
							display.setCurrent(alert);

							 
						}
					}
					viewBuddyList=new ViewBuddyList(display,this, bdyRS,buddyName,buddyNumber);
					display.setCurrent(viewBuddyList);
				}
		 }
	}
}