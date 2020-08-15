import java.io.*;
import java.lang.*;
import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.io.*;
import javax.wireless.messaging.*;
public class myproject1 extends MIDlet implements CommandListener
{
Display display;
Form MainForm=new Form("Main Screen");
Form LoggedInForm=new Form("Login Screen");
Ticker MainTicker=new Ticker("my project program");
TextField TxtLoginMain=new TextField ("Login Name","",20,TextField.ANY);
TextField TxtPasswordMain=new TextField ("Password","",20,TextField.ANY);
Command CmdMainLoginButton=new Command("Login",Command.OK,1);
Command CmdMainBackButton=new Command("Back",Command.BACK,0);
Alert InValidAlert=new Alert("Invalid Message");
public void Idea()
{

}
public void startApp()
{
try
{
display=Display.getDisplay(this);
MainForm.append(TxtLoginMain);
MainForm.append(TxtPasswordMain);
MainForm.addCommand(CmdMainLoginButton);
MainForm.addCommand(CmdMainBackButton);
MainForm.setCommandListener(this);
LoggedInForm.setCommandListener(this);
MainForm.setTicker(MainTicker);
LoggedInForm.setTicker(MainTicker);
display.setCurrent(MainForm);
//display.setCurrent(LoggedInForm); 

}
catch(Exception E)
{
System.out.println(E.getMessage());
}
}
public void pauseApp()
{
}
public void destroyApp(boolean b)
{
display.setCurrent((Displayable)null);
notifyDestroyed();
}
public void commandAction(Command C,Displayable D)
{
if (C==CmdMainLoginButton) 
{
if (TxtLoginMain.getString().equals(""))
{
InValidAlert.setString("Please enter your UserName Correctly");
InValidAlert.setTimeout(1000); 
InValidAlert.setType(AlertType.WARNING); 
display.setCurrent(InValidAlert); 
}
else if (TxtPasswordMain.getString().equals(""))
{
InValidAlert.setString("Please enter your Password Correctly");
InValidAlert.setTimeout(1000);
InValidAlert.setType(AlertType.INFO); 
display.setCurrent(InValidAlert); 
}
else if(TxtPasswordMain.getString().equals("labs"))
{
MainTicker.setString("Your are Logged in");
LoggedInForm.append("Your are Valid User");
display.setCurrent(LoggedInForm); 
/*else if(TxtLoginMain.getString().equals("idea"))
{
MainTicker.setString("Your are Logged in");
LoggedInForm.append("Your are Valid User");
display.setCurrent(LoggedInForm); 
}*/
}

}
}
}