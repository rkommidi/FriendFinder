import javax.microedition.lcdui.*;
class  AlreadyExists extends Alert{
	private Image arImage;
	public AlreadyExists(){
		super("Alert");
	 try
		{
	    arImage = Image.createImage("/alreadyexists.jpg");
		 } catch (java.io.IOException e) {
     	   }
//        this.append(new ImageItem(null, image, ImageItem.LAYOUT_CENTER, null));
         this.setImage(arImage);
         this.setType(AlertType.WARNING);
          this.setTimeout( 2000);
          this.setString("Already Exists ");
	}
}
