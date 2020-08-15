import javax.microedition.lcdui.*;
class  WelcomeScreen extends Alert { 
	private Image friendFinderImage;
	public WelcomeScreen(){
		super(" Welcome Screen ");
		try
		{

		 friendFinderImage = Image.createImage("/welcome.png");

		} catch (java.io.IOException e) {
          // System.err.println("Unable to locate or read image file");
        }
		this.setImage(null);
        this.setType(AlertType.INFO);
        this.setTimeout(500);
       // this.setString(" welcome to Friend Finder");
	}
}
