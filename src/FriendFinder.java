import javax.microedition.lcdui.*;
public class FriendFinder extends Form implements ItemCommandListener
{
   Command selectCmd;
   Image profileImage,buddyImage,searchImage;
   ImageItem profileImageItem,buddyImageItem,searchImageItem;
   Profiles profiles;
   Buddies buddies;
   Search search;
   Display display;   
   public FriendFinder(Display display) throws Exception{	  
	   super("Friend Finder");
	    System.out.println("before friend finder constructor");
	   this.display=display;
	  	   
	   try{
		  			profileImage=Image.createImage("/profiles.jpg");
					buddyImage=Image.createImage("/buddies.jpg");
					searchImage=Image.createImage("/search.png");
       }catch (java.io.IOException e){
		   Alert alert=new Alert("Information");
		   alert.setString(e+"  ");
		   display.setCurrent(alert);

			 /*e.printStackTrace();
			 System.out.println(e);
             System.err.println(" Unable to locate or read resource files");*/
       }

	   profileImageItem=new ImageItem("Profiles",profileImage,Item.LAYOUT_LEFT,null,Item.BUTTON);
	   buddyImageItem=new ImageItem("Buddies",buddyImage,Item.LAYOUT_CENTER,null,Item.BUTTON);
	   searchImageItem=new ImageItem("Search",searchImage,Item.LAYOUT_RIGHT,null,Item.BUTTON);

	   profiles=new Profiles(display,this);
	   buddies=new Buddies(display,this);
	   search=new Search(display,this);

	   selectCmd=new Command("Select",Command.OK,1);
   	   
	   profileImageItem.setDefaultCommand(selectCmd);
	   buddyImageItem.setDefaultCommand(selectCmd);
	   searchImageItem.setDefaultCommand(selectCmd);

	   this.append(profileImageItem);
	   this.append(buddyImageItem);
	   this.append(searchImageItem);

	   profileImageItem.setItemCommandListener(this);
	   buddyImageItem.setItemCommandListener(this);
	   searchImageItem.setItemCommandListener(this);
	   System.out.println("after fftest constructor");

  }
  public void commandAction(Command c,Item i){
	  	  if ( c == List.SELECT_COMMAND || c==selectCmd )  {
				if (i==profileImageItem){
					display.setCurrent(profiles);
                }else if (i==buddyImageItem){
					display.setCurrent(buddies);
                }else if (i==searchImageItem){
					display.setCurrent(search);
                }
		  }
  }

}