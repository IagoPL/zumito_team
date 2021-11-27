import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageMosquitoes {
	public static void main(String[] args) {
		File file = new File("C:\\Users\\Asus\\eclipse-workspace\\AnalyzeImage\\src\\imagen.png");
		try {
			Color color;
			BufferedImage image;
			int c;
			int red;
			int green;
			int blue;
			image = ImageIO.read(file);
			int max;
			int min;
			int hue;
			int luminosidad;
			int saturacion;
			int numPixelsHoritzontal = image.getWidth();
			int numPixelsVertical = image.getHeight();
			String colorEnHex = "";
			ArrayList<Integer>[/* x */][/* y */] colorFotPixel = new ArrayList[numPixelsHoritzontal][numPixelsVertical];
			for (int coordenadaX = 0; coordenadaX < numPixelsHoritzontal; coordenadaX++) {
				for (int coordenadaY = 0; coordenadaY < numPixelsVertical; coordenadaY++) {
					c = image.getRGB(coordenadaX, coordenadaY);
					red = (c & 0x00ff0000) >> 16;
					green = (c & 0x0000ff00) >> 8;
					blue = c & 0x000000ff;
//					color = new Color(red, green, blue);
					max = calcularMax(red, green, blue);
					min = calcularMin(red, green, blue);
					hue = obtenerHue(max, min, red, green, blue);
					luminosidad = obtenerLuminosidad(max, min);
					saturacion = obtenerSaturacion(max, min, luminosidad);

					/**
					 * guardar en arayList
					 */
					colorFotPixel[coordenadaX][coordenadaY].add(red);
					colorFotPixel[coordenadaX][coordenadaY].add(green);
					colorFotPixel[coordenadaX][coordenadaY].add(blue);
					colorFotPixel[coordenadaX][coordenadaY].add(max);
					colorFotPixel[coordenadaX][coordenadaY].add(min);
					colorFotPixel[coordenadaX][coordenadaY].add(hue);
					colorFotPixel[coordenadaX][coordenadaY].add(luminosidad);
					colorFotPixel[coordenadaX][coordenadaY].add(saturacion);

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int calcularMax(int red, int green, int blue) {
		int max;
		if (red >= green) {
			if (red >= blue) {
				max = red;
			} else {
				max = blue;
			}
		} else {
			if (green >= blue) {
				max = green;
			} else {
				max = blue;
			}
		}
		return max;
	}

	public static int calcularMin(int red, int green, int blue) {
		int min;
		if (red <= green) {
			if (red <= blue) {
				min = red;
			} else {
				min = blue;
			}
		} else {
			if (green <= blue) {
				min = green;
			} else {
				min = blue;
			}
		}
		return min;
	}

	public static int obtenerHue(int max, int min, int red, int green, int blue) {
		int hue = -1;
		if (max == min) { // esto es gris
			hue = 0;
		} else if (max == red) {
			hue = (60 * (green - blue) / (max - min) + 360) % 360;
		} else if (max == green) {
			hue = 60 * (blue - red) / (max - min) + 120;
		} else if (max == blue) {
			hue = 60 * (red - green) / (max - min) + 240;
		} else {
			System.out.println("Error al obtener el hue, no es ninguno de los cuatro casos");
		}
		return hue;
	}

	public static int obtenerLuminosidad(int max, int min) {
		int luminosidad = (max + min) / 2;
		return luminosidad;
	}

	public static int obtenerSaturacion(int max, int min, int luminosidad) {
		int saturacion = -1;
		if (max == min) {
			saturacion = 0;
		} else if (luminosidad <= 1 / 2) {
			saturacion = (max - min) / 2 * luminosidad;
		} else if (luminosidad > 1 / 2) {
			saturacion = (max - min) / (2 - 2 * luminosidad);
		} else {
			System.out.println("Error al obtener la luminosidad, no es ninguno de los tres casos");
		}
		return saturacion;
	}

	/**
	 * TODO: interfaz simple
	 * 
	 * TODO: añadir la imagen
	 * 
	 * TODO: analizar imagen con puntos de mosquitos 
	 * 
	 * TODO: analizar lineas devolver
	 * 
	 * TODO: porcentaje
	 * 
	 * TODO: separar "mosquitos"
	 */
}
