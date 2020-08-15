
import javax.microedition.lcdui.*;

class SearchByProfile extends Form implements CommandListener,ItemCommandListener,Runnable
{
	Alert alert_searching;
	Command okCmd ;
    Command backCmd;
	Display display;
	Search search;
	ChoiceGroup searchCG;
	MatchedDevices matchedDevices;
	NoDeviceFound noDeviceFound;
	Others others;
	boolean found=true;
	int flag=0;

	public SearchByProfile(Display display,Search search)
	{
		super("Search By Profile");
		 System.out.println("before search by profile constructor");


		matchedDevices=new MatchedDevices(display,this);
		noDeviceFound=new NoDeviceFound();
		others=new Others(display,this);
		alert_searching=new Alert("Alert","Searching For Profile Based Devices",null,AlertType.INFO);
		alert_searching.setTimeout(2000);

		searchCG=new ChoiceGroup("Select any profile to Search",Choice.EXCLUSIVE);
		searchCG.append("Educational Profile",null);
		searchCG.append("Organizational Profile",null);
		searchCG.append("Others",null);
		this.display=display;
		this.search=search;

		okCmd = new Command("Search", Command.OK, 1);
		backCmd = new Command("Back", Command.BACK, 1);


		this.append(searchCG);
        this.addCommand(backCmd);
        searchCG.addCommand(okCmd);
		searchCG.setItemCommandListener(this);
		setCommandListener(this);
		 System.out.println("after search by profile constructor");
	}
	public void commandAction(Command c,Item i)
	{
		if (c == List.SELECT_COMMAND || c==okCmd) 
		{
                  switch (searchCG.getSelectedIndex()) 
					{
                        case 0:
							Thread searchThread0=new Thread(this);
							flag=0;
							searchThread0.start();
							display.setCurrent(alert_searching);
                            break;
                        case 1:
							Thread searchThread1=new Thread(this);
							flag=1;
							searchThread1.start();
							display.setCurrent(alert_searching);
                            break;

						case 2:
							display.setCurrent(others);
				  }
		}
	}

public void run()
	{
	if(flag==0)
		{
			DeviceSearchByEP ds=new DeviceSearchByEP(display,this);
			ds.startInquiry();
		}
	else if(flag==1)
		{

			DeviceSearchByOP ds=new DeviceSearchByOP(display,this);
			ds.startInquiry();
		}
	}

public void commandAction(Command c,Displayable disp)
	{
		if(c==backCmd)
		{
			display.setCurrent(search);
		}
	}
}
