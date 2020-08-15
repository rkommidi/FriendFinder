//Midlet 
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.*;
import javax.microedition.io.*;
import java.lang.*;

public class FFTest extends MIDlet {
    	Display display;  
	    FriendFinder friendFinder;
		WelcomeScreen welcomeScreen;
	public FFTest() throws Exception{
		System.out.println("before fftest constructor");
		display=Display.getDisplay(this);
		friendFinder =new FriendFinder(display);
	    welcomeScreen=new WelcomeScreen();
		System.out.println("after fftest constructor");

	}
	public void startApp()	{
		display.setCurrent(welcomeScreen,friendFinder);
	}

	public void pauseApp()	{
	}
	public void destroyApp(boolean b){
	}
}