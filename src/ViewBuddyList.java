import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
class ViewBuddyList extends List implements CommandListener{
	private final static Command upCmd =
	    new Command("Update", Command.OK, 1);
    private final static Command delCmd =
	    new Command("Delete", Command.OK, 1);
	private final static Command backCmd =
	    new Command("Back", Command.BACK, 1);
	Display display;
	Buddies buddies;
	UpDate upDate;
	Delete delete;
	RecordStore bdyRS;
	public ViewBuddyList(Display display,Buddies buddies, RecordStore bdyRS,String[] buddyName,String[] buddyNumber){
		super("Buddy List",Choice.IMPLICIT);
		this.display=display;
		this.buddies=buddies;
		this.bdyRS= bdyRS;

		for(int loop=0,num=1;loop<10;loop++){
			if(buddyName[loop]!="" || buddyNumber[loop]!=""){
				this.append(num+"."+buddyName[loop]+"     :    "+buddyNumber[loop],null);
				num++;
			}
		}

		
		this.addCommand(backCmd);
		this.addCommand(upCmd);
		this.addCommand(delCmd);
		setCommandListener(this);
	}
public void commandAction(Command c,Displayable disp){
	if(c==upCmd){
		try{
		upDate=new UpDate(display,this, bdyRS,buddies,((List)this).getSelectedIndex()+1);
		display.setCurrent(upDate);
		}catch(Exception e){
			 Alert alert=new Alert("Information");
		   alert.setString(e+"  ");
		   display.setCurrent(alert);

		}
	}else if(c==delCmd){
		delete=new Delete(display,this, bdyRS,buddies,((List)this).getSelectedIndex()+1);
		display.setCurrent(delete);
	}else if(c==backCmd){
		display.setCurrent(buddies);
	}
}
}