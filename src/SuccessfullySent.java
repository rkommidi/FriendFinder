import javax.microedition.lcdui.*;
class  SuccessfullySent extends Alert { 
	private Image ssImage;
	public SuccessfullySent(){
		super(" Successfully Sent ");
		try
		{
	     ssImage = Image.createImage("/successfullysent.jpg");
		} catch (java.io.IOException e) {

        }
		this.setImage(ssImage);
        this.setType(AlertType.INFO);
        this.setTimeout(2000);
        this.setString(" Successfully Sent ");
	}
}
