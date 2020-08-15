import javax.microedition.lcdui.*;
class  MemoryFull extends Alert{
	private Image mfImage;
	public MemoryFull(){
		super("Alert");
	 try
		{
	    mfImage = Image.createImage("/alreadyexists.jpg");
		 } catch (java.io.IOException e)
      {

       }
//        this.append(new ImageItem(null, image, ImageItem.LAYOUT_CENTER, null));
         this.setImage(mfImage);
         this.setType(AlertType.WARNING);
          this.setTimeout( 2000);
          this.setString("Memory Full");
	}
}
