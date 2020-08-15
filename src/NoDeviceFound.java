import javax.microedition.lcdui.*;
class  NoDeviceFound extends Alert{
	private Image ndfImage;
	public NoDeviceFound(){
		super("No Device Found Alert");
		try
		{
	      ndfImage = Image.createImage("/nodevicefound.jpg");
		} catch (java.io.IOException e)
      {

       }
		this.setImage(ndfImage);	        
        this.setType(AlertType.INFO);
        this.setTimeout(2000 );
        this.setString(" No Device Found ");
	}
}
