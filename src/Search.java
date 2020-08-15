import javax.microedition.lcdui.*;
class Search extends Form implements CommandListener,ItemCommandListener,Runnable
{
	Alert alert_searching;
	Command okCmd ;
	Command backCmd ;
	Image searchByProfileImage,searchByBuddyImage;
	ImageItem searchByProfileImageItem,searchByBuddyImageItem;
	Display display;
	FriendFinder friendFinder;
	SearchByProfile searchByProfile;
//	MatchedDevices matchedDevices;
	//NoDeviceFound noDeviceFound;
	boolean found=false;
	public Search(Display display,FriendFinder friendFinder){
		super("Search");
		 System.out.println("before search constructor");
		this.friendFinder=friendFinder;
		this.display=display;
		try{
			searchByProfileImage=Image.createImage("/searchbyprofile.jpg");
			searchByBuddyImage=Image.createImage("/searchbybuddy.jpg");

        }catch (java.io.IOException e){
             				  Alert alert=new Alert("Information");
							   alert.setString(e+"  ");
							   display.setCurrent(alert);

        }

		searchByProfileImageItem=new ImageItem("Search By Profile",searchByProfileImage,Item.LAYOUT_LEFT,null,Item.BUTTON);
	    searchByBuddyImageItem=new ImageItem("Search By Buddy",searchByBuddyImage,Item.LAYOUT_RIGHT,null,Item.BUTTON);

		searchByProfile=new SearchByProfile(display,this);
		alert_searching=new Alert("Alert","Searching For Buddy Devices",null,AlertType.INFO);
		alert_searching.setTimeout(2000);

		//matchedDevices= new MatchedDevices(display,this);
		//noDeviceFound=new NoDeviceFound();

		okCmd = new Command("Ok", Command.OK, 1);
		backCmd = new Command("Back", Command.BACK, 1);


		searchByProfileImageItem.setDefaultCommand(okCmd);
	    searchByBuddyImageItem.setDefaultCommand(okCmd);

		this.append(searchByProfileImageItem);
	    this.append(searchByBuddyImageItem);

		searchByProfileImageItem.setItemCommandListener(this);
	    searchByBuddyImageItem.setItemCommandListener(this);


        this.addCommand(backCmd);
		setCommandListener(this);
		 System.out.println("after search constructor");
	}
    public void commandAction(Command c,Displayable disp){
		if(c==backCmd){
			display.setCurrent(friendFinder);
		}

	}
	public void commandAction(Command c,Item i){
		if(c==List.SELECT_COMMAND || c==okCmd){
			if (i==searchByProfileImageItem){
				display.setCurrent(searchByProfile);
			}else if (i==searchByBuddyImageItem){

				Thread searchThread=new Thread(this);
				searchThread.start();
				display.setCurrent(alert_searching);
						
			}
		}
	}


public void run()
	{
			DeviceSearchByBuddy ds=new DeviceSearchByBuddy(display,this);
			ds.startInquiry();
	}

}
