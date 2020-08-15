import javax.microedition.lcdui.*;
class  BlankInfo extends Alert{
	private Image biImage;
	public BlankInfo(){
		super("Alert");
	 try
		{
	    biImage = Image.createImage("/alreadyexists.jpg");
		 } catch (java.io.IOException e)
      {

       }
//        this.append(new ImageItem(null, image, ImageItem.LAYOUT_CENTER, null));
         this.setImage(biImage);
         this.setType(AlertType.WARNING);
          this.setTimeout( 2000);
          this.setString("Enter Name & PhoneNumber");
	}
}
