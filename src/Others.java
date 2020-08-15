import javax.microedition.lcdui.*;
class Others extends Form implements CommandListener,Runnable{
	ChoiceGroup age,gender,hobbies;
	Alert alert_searching;
	Command okCmd;
    Command backCmd;
	Display display;
	int a;
	String  g, h;
	SearchByProfile searchByProfile;

	public Others(Display display,SearchByProfile searchByProfile){
		super("Others");
		this.display=display;
		this.searchByProfile=searchByProfile;

		alert_searching=new Alert("Alert","Please wait......\nSearching For Devices Based On Selected Parameters ",null,AlertType.INFO);
		alert_searching.setTimeout(2000);
		
		String[] ageArray = {
			"Any","Less than 15","16 to 20","21 to 25","26 to 35","Above 35"
		};
		String[] genderArray = {
			"Any","Male","Female"
		};
		String[] occupationArray = {
			"Any","Engineer","Doctor","Lawyer","Manager","CA","Other"
		};
		String[] hobbiesArray = {
			"Any","Technology","Sports","Philately","Entertainment","Reading books","Others"
		};
		Image[] imageArray = null;
		age=new ChoiceGroup("Age",ChoiceGroup.POPUP, ageArray, 
                                imageArray);
		gender=new ChoiceGroup("Gender",ChoiceGroup.POPUP, genderArray, 
                                imageArray);
	/*	occupation=new ChoiceGroup("Occupation",ChoiceGroup.POPUP, occupationArray, 
                                imageArray);*/
 		hobbies=new ChoiceGroup("Hobbies",ChoiceGroup.POPUP, hobbiesArray, 
                                imageArray);
		this.append(age);
		this.append(gender);
//		this.append(occupation);
		this.append(hobbies);

		okCmd= new Command("Search", Command.OK, 1);
		backCmd=new Command("Back", Command.BACK, 1);

		this.addCommand(backCmd);
		this.addCommand(okCmd);
		setCommandListener(this);
	}
	public void commandAction(Command c,Displayable disp){
		if(c==okCmd){
			a=age.getSelectedIndex();
			g=gender.getString(gender.getSelectedIndex());
//			o=occupation.getString(occupation.getSelectedIndex());
			h=hobbies.getString(hobbies.getSelectedIndex());

			Thread searchThread=new Thread(this);
			searchThread.start();
			display.setCurrent(alert_searching);

			/*if(found){
				display.setCurrent(matchedDevices);
			}else {
				display.setCurrent(noDeviceFound);
			}*/
		}else if(c==backCmd){
			display.setCurrent(searchByProfile);
		}
	}

	public void run()
	{
			DeviceSearchByOthers ds=new DeviceSearchByOthers(display,this, a, g, h);
			ds.startInquiry();
	}

}


