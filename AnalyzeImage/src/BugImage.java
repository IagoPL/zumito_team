import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BugImage {
	List<Zone> zones;
	List<List<Pixel>> pixels;

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	public List<List<Pixel>> getPixels() {
		return pixels;
	}

	public void setPixels(List<List<Pixel>> pixels) {
		this.pixels = pixels;
	}

	public void createPixelsFromImage(BufferedImage image) {
		int numPixelsHoritzontal = image.getWidth();
		int numPixelsVertical = image.getHeight();
		this.setPixels(new ArrayList<>());
		for (int coordenadaX = 0; coordenadaX < numPixelsHoritzontal; coordenadaX++) {
			this.getPixels().add(new ArrayList<>());
			for (int coordenadaY = 0; coordenadaY < numPixelsVertical; coordenadaY++) {
				Pixel pixel = new Pixel();
				int c = image.getRGB(coordenadaX, coordenadaY);
				pixel.setRed((c & 0x00ff0000) >> 16);
				pixel.setGreen((c & 0x0000ff00) >> 8);
				pixel.setBlue(c & 0x000000ff);
				pixel.setCoordenadaX(coordenadaX);
				pixel.setCoordenadaY(coordenadaY);
				this.getPixels().get(coordenadaX).add(pixel);

			}
		}

	}
	
	private int[] limits (int coordenadaX, int coordenadaY, int pixelesHorizontal, int pixelesVertical ) {
		
		int [] limites = new int [4];
//		int limitInferiorX;
//		int limitSuperiorX;
//		int limitInferiorY;
//		int limitSuperiorY;
		if (coordenadaX==0) {
			limites[0] = coordenadaX;
			limites[1] = coordenadaX+1;
		} else if (coordenadaX==pixelesHorizontal) {
			limites[0] = coordenadaX-1;
			limites[1] = coordenadaX;
		} else {
			limites[0] = coordenadaX-1;
			limites[1] = coordenadaX +1;
		}
		if (coordenadaY==0) {
			limites[2] = coordenadaY;
			limites[3] = coordenadaY+1;
		} else if (coordenadaY==pixelesVertical) {
			limites[2] = coordenadaY-1;
			limites[3] = coordenadaY;
		} else {
			limites[2] = coordenadaY-1;
			limites[3] = coordenadaY +1;
		}
		
		
		return limites;
		
	}
	
	public void createZones () {
		
		ArrayList<Zone> zonas = new ArrayList<Zone>();
		Zone zona = new Zone();
		for (List<Pixel> filaDePixeles : this.pixels) {
			for (Pixel pixel : filaDePixeles) {
				if (pixel.getZona()==1 && !pixel.isVisited()) {
					zona = createZone(pixel);
					zonas.add(zona);
				}
			}
		}
		
	this.setZones(zonas);
		
	}
	
	private Zone createZone (Pixel pixel) {
		int [] limites =new int [4];
//		int limitInferiorX;
//		int limitSuperiorX;
//		int limitInferiorY;
//		int limitSuperiorY;
		Zone zone = new Zone();
		zone.setPixels(new ArrayList<>());
		zone.getPixels().add(pixel);
		limites= limits(pixel.getCoordenadaX(), pixel.getCoordenadaY(), this.pixels.size(), this.pixels.get(0).size());
		
		pixel.setVisited(true);
		for (int i=limites[0]; i<=limites[1]; i++) {
			for (int j=limites[2]; j<=limites[3]; j++) {
				if (!this.pixels.get(i).get(j).isVisited() && this.pixels.get(i).get(j).getZona() == 1) {
					zone.getPixels().addAll(createZone(this.pixels.get(i).get(j)).getPixels());
				}
	
			}
		}
		return zone;
	}

}
