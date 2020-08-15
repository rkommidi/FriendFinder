import javax.microedition.lcdui.*;
class Messages extends TextBox implements CommandListener{
	 private final static Command backCmd = new Command("Back", Command.BACK, 
                                                        2);
    private final static Command sendCmd = new Command("Send", Command.OK, 
                                                        1);
	Display display;
	MatchedDevices matchedDevices;
	SuccessfullySent successfullySent;
	public Messages(Display display,MatchedDevices matchedDevices){
		super("Message","hai",100,TextField.ANY);
		this.display=display;
		this.matchedDevices=matchedDevices;
		successfullySent=new SuccessfullySent();
		this.addCommand(backCmd);
		this.addCommand(sendCmd);
		setCommandListener(this);
	}
	public void commandAction(Command c,Displayable disp) {
		if (c==sendCmd) {
			//send a message to selected device
			display.setCurrent(successfullySent);
		}else if (c==backCmd){
			display.setCurrent(matchedDevices);
		}
	}
};
