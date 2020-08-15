import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
class Profiles extends Form implements ItemCommandListener,CommandListener {

	Command backCmd,okCmd;

	FriendFinder friendFinder;
	PersonalProfile pp;
	EducationalProfile ep;
	OrganizationalProfile op;

	Display display;
	public static RecordStore ravi;

	public static boolean ppserviceCreated=false;
	public static boolean epserviceCreated=false;
	public static boolean opserviceCreated=false;




	Image ppImage,epImage,opImage;
	ImageItem ppImageItem,epImageItem,opImageItem;

	public Profiles(Display display,FriendFinder friendFinder)  throws Exception{
        super("Profile");
		 System.out.println("before profile constructor");
		this.friendFinder=friendFinder;
		this.display=display;
		ravi=RecordStore.openRecordStore("Profiles",true);
		 try{
			ppImage=Image.createImage("/pp.jpg");
    		epImage=Image.createImage("/pp.jpg");
			opImage=Image.createImage("/op.jpg");

		}catch (java.io.IOException e){
			 Alert alert=new Alert("Information");
		   alert.setString(e+"  ");
		   display.setCurrent(alert);

		    //System.err.println(" Unable to locate or read resource files");
        }

	    ppImageItem=new ImageItem("Personal Profile",ppImage,Item.LAYOUT_LEFT,null,Item.BUTTON);
	    epImageItem=new ImageItem("Educational Profile",epImage,Item.LAYOUT_CENTER,null,Item.BUTTON);
	    opImageItem=new ImageItem("Organizatoinal Profile",opImage,Item.LAYOUT_RIGHT,null,Item.BUTTON);
        
		
	    okCmd=new Command("Ok",Command.OK,1);
   	    backCmd=new Command("Back",Command.BACK,2);

	    ppImageItem.setDefaultCommand(okCmd);
	    epImageItem.setDefaultCommand(okCmd);
	    opImageItem.setDefaultCommand(okCmd);

	    this.append(ppImageItem);
	    this.append(epImageItem);
	    this.append(opImageItem);

	    ppImageItem.setItemCommandListener(this);
	    epImageItem.setItemCommandListener(this);
	    opImageItem.setItemCommandListener(this);

		
	    this.addCommand(backCmd);
	    setCommandListener(this);
		 System.out.println("after profiles constructor");
	}
	public void commandAction(Command c,Displayable disp){
		 if(c==backCmd){
			 display.setCurrent(friendFinder);
		 }
	}
	public void commandAction(Command c,Item i){
		 if (c == List.SELECT_COMMAND || c==okCmd) {
			  if (i==ppImageItem){
				  try
					  {
						if( !ppserviceCreated )
						{
							  pp= new PersonalProfile(display,this,ravi,ppserviceCreated);
							  System.out.println("new pp creating");
						}
						else
						  {
							  pp= new PersonalProfile(display,this,ravi);
							  System.out.println("old pp");
						  }
					  }
				  catch (Exception e)
				  {
					  System.out.println(e);
				  }
			    display.setCurrent(pp);
              }else if (i==epImageItem){
				  try
					  {
						if( !epserviceCreated )
						{
							  ep= new  EducationalProfile(display,this,ravi,epserviceCreated);
							  System.out.println("new ep creating");
						}
						else
						  {
							  ep= new  EducationalProfile(display,this,ravi);
							  System.out.println("old ep");
						  }
					  }
				  catch (Exception e)
				  {
					  System.out.println(e);
				  }


				  display.setCurrent(ep);
              }else if (i==opImageItem){
				  try{
					  if(!opserviceCreated )
					  {
							op=new OrganizationalProfile(display,this,ravi,opserviceCreated);				
							System.out.println("new op creating");
					  }
					  else
					  {
  							  op= new OrganizationalProfile(display,this,ravi);
							  System.out.println("old op");

					  }
				  }catch(Exception e)
				  {
					  System.out.println(e);
				  }
				 display.setCurrent(op);	
              }
		 }
	}
}