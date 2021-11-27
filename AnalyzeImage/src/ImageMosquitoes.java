import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageMosquitoes {
	public static void main(String[] args) {
		System.out.println(41/16);
		File file = new File("C:\\Users\\Asus\\eclipse-workspace\\AnalyzeImage\\src\\imagen.png");
		try {
			Color color;
			BufferedImage image;
			int c;
			int red;
			int  green;
			int  blue;
			image = ImageIO.read(file);
			int numPixelsHoritzontal = image.getWidth();
			int numPixelsVertical = image.getHeight();
			String colorEnHex = "";
			for (int coordenadaX =0; coordenadaX<numPixelsHoritzontal; coordenadaX++) {
				for (int coordenadaY=0; coordenadaY<numPixelsVertical; coordenadaY++) {
					c = image.getRGB(coordenadaX, coordenadaY);
					red = (c & 0x00ff0000) >> 16;
					green = (c & 0x0000ff00) >> 8;
					blue = c & 0x000000ff;
					color = new Color(red, green, blue);
					//colorEnHex = colorEnHex + pasarDeDecAHex(red);
					//colorEnHex = colorEnHex + pasarDeDecAHex(green);
					//colorEnHex = colorEnHex + pasarDeDecAHex(blue);
					/*if (color.getRed()==0 && color.getBlue()==0 && color.getGreen()==0) {
						System.out.println("X: " + coordenadaX);
						System.out.println("Y: " + coordenadaY + "\n\n");
					}*/
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public static String pasarDeDecAHex (int numeroDecimal) {
		ArrayList<Integer> digitosHex = new ArrayList<>();
		int numADividir = numeroDecimal;
		int cociente = -1;
		int residuo;
		while (cociente != 0) {
			cociente = numADividir/16;
			residuo = numADividir - cociente * 16;
			digitosHex.add(residuo);
			numADividir = cociente;
		}
		digitosHex.add(numADividir);
		String valorHexadecimal = "";
		for (int i=digitosHex.size()-1; i>=0; i--) {
			if (digitosHex.get(i)>=0 && digitosHex.get(i)<=9) {
				valorHexadecimal = valorHexadecimal + digitosHex.get(i);
			} else if (digitosHex.get(i)>=10 && digitosHex.get(i)<=15) {
				valorHexadecimal = valorHexadecimal + digitoDecimalAHex(digitosHex.get(i));
			} else {
				System.out.println("El digito no esta entre 0 y 15");
			}
		}
		return valorHexadecimal;
	}*/
	
	/*public static String digitoDecimalAHex (int digito) {
		String digitoARetornar = "";
		switch (digito) {
		case 10:
			digitoARetornar = "A";
			break;
		case 11:
			digitoARetornar = "B";
			break;
		case 12:
			digitoARetornar = "C";
			break;
		case 13:
			digitoARetornar = "D";
			break;
		case 14:
			digitoARetornar = "E";
			break;
		case 15:
			digitoARetornar = "F";
			break;
		default:
			System.out.println("El digito no esta entre 10 y 15");
		}
		return digitoARetornar;
	}*/
	
	public static void obtenerColor (int red, int green, int blue) {
		//String color;
		/*if (red==0 && green==0 && blue==0) {
			color = "blanco";
		} else if (red==0 && green==0) {
			color = "azul";
		} else if (green==0 && blue==0) {
			color = "rojo";
		} else if (red==0 && blue==0) {
			color = "verde";
		} else if (red==0) {
			color = "cian";
		} else if (green==0) {
			color = "magenta";
		} else if (blue==0) {
			color = "amarillo";
		} else if (red==green && green==blue && blue==red) {
			color = "gris";
		}*/
		
	}
}
