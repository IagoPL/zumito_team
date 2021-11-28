import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BugCounter {
	
	public static void main(String[] args) throws IOException {
		File file = new File("C:\\Users\\Asus\\eclipse-workspace\\AnalyzeImage\\src\\imagen3.png");
		BufferedImage image = ImageIO.read(file);
		
		BugImage bugImage = new BugImage();
		bugImage.createPixelsFromImage(image);
		bugImage.createZones();
		System.out.println("Mosquitos: " + bugImage.getZones().size());
	}
	

}
