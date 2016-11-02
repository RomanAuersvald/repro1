package hra;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Hrac {
	public static final int SIRKA = 40;
	public static final int VYSKA = 33;
	//velikost skoku hráèe
	private static final int KOEF_ZRYCHLENI = 1;
	//rychlost pádu hráèe
	private static final int KOEF_RYCHLOST = 2;
	private BufferedImage img = null;
	//pocatecni x-ova souø nemeni se (hrac neskace dopredu ani dozadu)
	private int x;
	//pocatexni y pozice hrace a ta se bude menit
	private int y;
	private int rychlost;
	
	
	public Hrac(BufferedImage img) {
		this.img = img;
		x = (HraciPlocha.SIRKA / 2) - (img.getWidth() / 2);
		y = HraciPlocha.VYSKA / 2 ;
		rychlost = KOEF_RYCHLOST;
	}
	
	/**
	 * volá se po nárazu do okna
	 */
	
	public void reset(){
		y = HraciPlocha.VYSKA / 2;
		rychlost = KOEF_RYCHLOST;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void skok(){
		rychlost = -17;
		
	}
	
	/**
	 * zajistuje pohyb hrace
	 */
	
	public void posun(){
		rychlost = rychlost + KOEF_ZRYCHLENI;
		y = y + rychlost;
	}
	
	public void paint(Graphics g){
		g.drawImage(img, x, y, null);
	}
	
	
	public int getVyskaHrace(){
		return img.getHeight();
	}
	
	/**
	 * vrací pomyslnný ètverec/obdelník, který opisuje hráèe
	 * @return
	 */
	public Rectangle getMez(){
		return new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
}
