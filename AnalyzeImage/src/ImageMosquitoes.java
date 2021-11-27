import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
			int[][] clasificacionPorZonas = new int[numPixelsHoritzontal][numPixelsVertical];
			String colorEnHex = "";
			ArrayList<Integer>[/* x */][/* y */] colorForPixel = new ArrayList[numPixelsHoritzontal][numPixelsVertical];
			ArrayList<HashMap<Integer, Integer>> zonasDeBichos = new ArrayList<HashMap<Integer, Integer>>();
			boolean[][] pixelEnZona = new boolean [numPixelsHoritzontal][numPixelsVertical];
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
					colorForPixel[coordenadaX][coordenadaY] = new ArrayList<Integer>();
					colorForPixel[coordenadaX][coordenadaY].add(red);
					colorForPixel[coordenadaX][coordenadaY].add(green);
					colorForPixel[coordenadaX][coordenadaY].add(blue);
					colorForPixel[coordenadaX][coordenadaY].add(max);
					colorForPixel[coordenadaX][coordenadaY].add(min);
					colorForPixel[coordenadaX][coordenadaY].add(hue);
					colorForPixel[coordenadaX][coordenadaY].add(luminosidad);
					colorForPixel[coordenadaX][coordenadaY].add(saturacion);
					clasificacionPorZonas[coordenadaX][coordenadaY] = obtenerZona(hue, luminosidad, saturacion);
					pixelEnZona[coordenadaX][coordenadaY] = false;
				}
			}
			for (int coordenadaX = 0; coordenadaX < numPixelsHoritzontal; coordenadaX++) {
				for (int coordenadaY = 0; coordenadaY < numPixelsVertical; coordenadaY++) {
					delimitarZonaDeBichos(pixelEnZona, coordenadaX, coordenadaY, clasificacionPorZonas);
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
	
	public static int obtenerZona (int hue, int luminosidad, int saturacion) {
		//-1 significa fondo
		//0 significa papel atrapamoscas
		//1 significa mosquito
		int zona = 2;
		if (luminosidad < 85) {
			if (luminosidad < 40) {
				zona = 1;
			} else {
				if (hue > 47 && hue < 64) {
					if (saturacion > 55) {
						zona = 0;
					} else {
						zona = 1;
					}
				} else {
					zona = 1;
				}
			}
		} else {
			zona = -1;
		}
		return zona;
	}
	

	public static void delimitarZonaDeBichos (boolean[][] pixelEnZona, Integer coordenadaX, Integer coordenadaY, int[][] clasificacionPorZonas) {
		HashMap<Integer, Integer> zonaBichos = new HashMap<Integer, Integer>();
		int limitInferiorX;
		int limitSuperiorX;
		int limitInferiorY;
		int limitSuperiorY;

		if (coordenadaX==0 && coordenadaY==0) {
			limitInferiorX=coordenadaX;
			limitInferiorY=coordenadaY;
			limitSuperiorX = coordenadaX +1;
			limitSuperiorY = coordenadaY +1;
		} else if (coordenadaX==0 && coordenadaY!=pixelEnZona[0].length && coordenadaY!=0) {
			limitInferiorX=coordenadaX;
			limitInferiorY=coordenadaY-1;
			limitSuperiorX = coordenadaX +1;
			limitSuperiorY = coordenadaY +1;
		} else if (coordenadaX==0 && coordenadaY==pixelEnZona[0].length) {
			limitInferiorX=coordenadaX;
			limitInferiorY=coordenadaY-1;
			limitSuperiorX = coordenadaX +1;
			limitSuperiorY = coordenadaY;
		} else if (coordenadaX==pixelEnZona.length && coordenadaY==0) {
			limitInferiorX=coordenadaX-1;
			limitInferiorY=coordenadaY;
			limitSuperiorX = coordenadaX;
			limitSuperiorY = coordenadaY +1;
		} else if (coordenadaX==pixelEnZona.length && coordenadaY!=pixelEnZona[0].length && coordenadaY!=0) {
			limitInferiorX=coordenadaX-1;
			limitInferiorY=coordenadaY-1;
			limitSuperiorX = coordenadaX;
			limitSuperiorY = coordenadaY +1;
		} else if (coordenadaX==pixelEnZona.length && coordenadaY==pixelEnZona[0].length) {
			limitInferiorX=coordenadaX-1;
			limitInferiorY=coordenadaY-1;
			limitSuperiorX = coordenadaX;
			limitSuperiorY = coordenadaY;
		} else if (coordenadaX!=pixelEnZona.length && coordenadaX!=0 && coordenadaY==0) {
			limitInferiorX=coordenadaX-1;
			limitInferiorY=coordenadaY;
			limitSuperiorX = coordenadaX+1;
			limitSuperiorY = coordenadaY+1;
		} else if (coordenadaX!=pixelEnZona.length && coordenadaX!=0 && coordenadaY!=pixelEnZona[0].length && coordenadaY!=0) {
			limitInferiorX=coordenadaX-1;
			limitInferiorY=coordenadaY-1;
			limitSuperiorX = coordenadaX+1;
			limitSuperiorY = coordenadaY +1;
		} else if (coordenadaX==pixelEnZona.length && coordenadaY==pixelEnZona[0].length) {
			limitInferiorX=coordenadaX-1;
			limitInferiorY=coordenadaY-1;
			limitSuperiorX = coordenadaX;
			limitSuperiorY = coordenadaY;
		}
		for (int i=coordenadaX-1; i<=coordenadaX+1; i++) {
			for (int j=coordenadaY-1; j<=coordenadaY+1; j++) {
				if (!(i==coordenadaX && j==coordenadaY)) {
					if (clasificacionPorZonas[i][j]==1 && pixelEnZona[i][j]==false) {//Si ese pixel forma parte de un bicho y no lo hemos añadido a la zona
						pixelEnZona[i][j] = true;
						zonaBichos.put(i, j);
						delimitarZonaDeBichos(pixelEnZona, i, j, clasificacionPorZonas);
					}
				}
			}
		}
	}

	/**
	 * TODO: interfaz simple
	 * 
	 * TODO: añadir las imagen
	 * 
	 * TODO: analizar lineas devolver
	 * 
	 * TODO: porcentaje
	 * 
	 * TODO: separar "mosquitos"
	 */
}
