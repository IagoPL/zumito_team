import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageMosquitoes {
	public static void main(String[] args) {
		File file = new File("C:\\Users\\Asus\\eclipse-workspace\\AnalyzeImage\\src\\imagen.png");
		try {//horitzontal:89
			//vertical: 83
			Color color;
			BufferedImage image;
			int c;
			int red;
			int  green;
			int  blue;
			image = ImageIO.read(file);
			int numPixelsHoritzontal = image.getWidth();
			int numPixelsVertical = image.getHeight();
			for (int coordenadaX =0; coordenadaX<numPixelsHoritzontal; coordenadaX++) {
				for (int coordenadaY=0; coordenadaY<numPixelsVertical; coordenadaY++) {
					c = image.getRGB(coordenadaX, coordenadaY);
					red = (c & 0x00ff0000) >> 16;
					green = (c & 0x0000ff00) >> 8;
					blue = c & 0x000000ff;
					color = new Color(red, green, blue);
					if (color.getRed()==0 && color.getBlue()==0 && color.getGreen()==0) {
						System.out.println("X: " + coordenadaX);
						System.out.println("Y: " + coordenadaY + "\n\n");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
