import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
	class AddBuddy extends Form implements CommandListener {
       TextField name, phoneNumber;
	    Command backCmd,okCmd;
		Display display;
		Buddies buddies;
		AlreadyExists alreadyExists;
		RecordStore bdyRS;
		SuccessfullySaved successfullySaved;
		MemoryFull memoryFull;
		BlankInfo blankInfo;
		String buddyStr;
		byte[] buddyStrByteArray;
		public static int recordID[]=new int[11];
		static boolean buddiesCreated=false;

       public AddBuddy(Display display,Buddies buddies, RecordStore bdyRS)   {
	 	    super("Buddies");
			this.display=display;
			this.buddies=buddies;
			this.bdyRS=bdyRS;
			name = new TextField("Name","", 25, TextField.ANY);
			phoneNumber = new TextField("Phone number","", 25, TextField.DECIMAL);
		    okCmd=new Command("Ok",Command.OK,1);
   	        backCmd=new Command("Back",Command.BACK,2);
			blankInfo=new BlankInfo();
			memoryFull=new MemoryFull();
			alreadyExists= new AlreadyExists();
			successfullySaved = new SuccessfullySaved();
			this.append(name);
		    this.append(phoneNumber);
		    this.addCommand(okCmd);
            this.addCommand(backCmd);
		    setCommandListener(this);
	  }
	public void commandAction(Command c,Displayable disp){
		if(c==okCmd){
			//save buddy name and phone number to buddy list
			String buddyStr= name.getString().concat(",").concat(phoneNumber.getString()).concat(",");
			byte[] buddyStrArray= buddyStr.getBytes();

			if(!buddiesCreated)
			{
				try
				{
					for(int k=1;k<=10;k++){
						recordID[k]=bdyRS.addRecord( buddyStrArray,0,buddyStrArray.length);
						bdyRS.setRecord(recordID[k], buddyStrArray,0,buddyStrArray.length);
						System.out.println("to memory at "+recordID[k]+buddyStr);
						buddiesCreated=true;
					}
				}catch (Exception e)
				{
					System.out.println(e);
				}
			}
			else
			{
				int i,count,loop;
				for(loop=1;loop<=11;loop++){
					if(loop>10){
						display.setCurrent(memoryFull,buddies);
						break;
					}
					try{
						buddyStrByteArray=bdyRS.getRecord(recordID[loop]);
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
						}else if(subStr[0].equals(name.getString()) || subStr[1].equals(phoneNumber.getString())){
							display.setCurrent(alreadyExists);
							break;
						}else if(subStr[0]=="" && subStr[1]==""){
							bdyRS.setRecord(recordID[loop], buddyStrArray,0,buddyStrArray.length);
							display.setCurrent(successfullySaved, buddies);
							break;
						}
					}catch(Exception e){
						  Alert alert=new Alert("Information");
					   alert.setString(e+"  ");
						 display.setCurrent(alert);

					}
				}
			}
			
		}else if(c==backCmd){
			display.setCurrent(buddies);
		}
    }
}