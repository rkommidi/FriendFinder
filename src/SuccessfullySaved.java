import javax.microedition.lcdui.*;
class  SuccessfullySaved extends Alert { 
	private Image ssImage;
	public SuccessfullySaved(){
		super(" Alert ");
		try
		{
	     ssImage = Image.createImage("/successfullysent.jpg");
		} catch (java.io.IOException e) {
           
        }
		this.setImage(ssImage);
        this.setType(AlertType.INFO);
        this.setTimeout(2000);
        this.setString(" Successfully Saved ");
	}
}
