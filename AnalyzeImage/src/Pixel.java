
public class Pixel {
	private int red;
	private int green;
	private int blue;
	private int coordenadaX;
	private int coordenadaY;
	private boolean visited;
	

	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public int getCoordenadaX() {
		return coordenadaX;
	}
	public void setCoordenadaX(int coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	public int getCoordenadaY() {
		return coordenadaY;
	}
	public void setCoordenadaY(int coordenadaY) {
		this.coordenadaY = coordenadaY;
	}
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}

	public int getMax() {
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
	
	public int getMin() {
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
	
	public int getHue() {
		int hue = -1;
		if (this.getMax() == this.getMin()) { // esto es gris
			return 0;
		} else if (this.getMax() == red) {
			return (60 * (green - blue) / (this.getMax()- this.getMin()) + 360) % 360;
		} else if (this.getMax() == green) {
			return 60 * (blue - red) / (this.getMax() - this.getMin()) + 120;
		} else if (this.getMax() == blue) {
			return 60 * (red - green) / (this.getMax() - this.getMin()) + 240;
		} else {
			System.out.println("Error al obtener el hue, no es ninguno de los cuatro casos");
			return hue;
		}
	}
	
	public int getSaturation() {
		int saturacion = -1;
		if (this.getMax() == this.getMin()) {
			saturacion = 0;
		} else if (this.getLuminosidad() <= 1 / 2) {
			saturacion = (this.getMax() - this.getMin()) / 2 * this.getLuminosidad();
		} else if (this.getLuminosidad() > 1 / 2) {
			saturacion = (this.getMax() - this.getMin()) / (2 - 2 * this.getLuminosidad());
		} else {
			System.out.println("Error al obtener la luminosidad, no es ninguno de los tres casos");
		}
		return saturacion;
	}
	
	public int getLuminosidad() {
		int luminosidad = (this.getMax() + this.getMin()) / 2;
		return luminosidad;
	}
	
	public int getZona() {
		//-1 significa fondo
				//0 significa papel atrapamoscas
				//1 significa mosquito
				int zona = 2;
				if (this.getLuminosidad() < 85) {
					if (this.getLuminosidad() < 40) {
						zona = 1;
					} else {
						if (this.getHue() > 47 && this.getHue() < 64) {
							if (this.getSaturation() > 55) {
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
}
