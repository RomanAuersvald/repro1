package hra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.Random;

public class Zed {

	public static final int SIRKA = 45;
	public static final int RYCHLOST = -6; //rychlost pohybu zdi
	public static final int MEZERA = 200;//mezera mezi zdmi nahoøe a dole
	public static final int BODY_ZA_ZED = 1;
	private Random random;
	
	//TODO

	
	private static BufferedImage img = null;
	private static int vzdalenostPosledniZdi;
	private int x; //xova sour zdi
	//yova sour zdi(horni sour spodni zdi)
	
	private int y;
	private int vyska;
	
	
	public Zed(int vzdalenost_zdi_od_zacatku_hraci_plochy) {
		this.x = vzdalenost_zdi_od_zacatku_hraci_plochy;
		
		random = new Random();
		vygenerujNahodneHodnotyProZed();
		
		//TODO
	}


	private void vygenerujNahodneHodnotyProZed() {
		//yová souø horní casti spodni zdi
		y = random.nextInt(HraciPlocha.VYSKA - 400)+ MEZERA;
		
		vyska = HraciPlocha.VYSKA -y;
	}
	
	public void paint(Graphics g){
		//spodni cast
		g.drawImage(img, x, y, null);
		
		//horni cast
		g.drawImage(img, x,(- HraciPlocha.VYSKA)+(y - MEZERA),null);
		
		if (HraciPlocha.DEBUG) {
			g.setColor(Color.BLACK);
			g.drawString("x="+ x + "y=" + y + "vyska je " + vyska, x, y-5);
		}
	}
	
	public void posun(){
		x =x + Zed.RYCHLOST;
		
		//kdyz se zed posune za hranici obrazovky 
		//tak nastav zdi nove parametry a vykresli znovu
		if (x<=0 - Zed.SIRKA) {
			
			x= Zed.vzdalenostPosledniZdi;
			
			//TODO
		}
	}
	
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public static void setVzdalenostPosledniZdi(int vzdalenostPosledniZdi) {
		Zed.vzdalenostPosledniZdi = vzdalenostPosledniZdi;
	}
	
	public static int getVzdalenostPosledniZdi() {
		return vzdalenostPosledniZdi;
	}
	
	public static void setObrazek(BufferedImage img){
		Zed.img = img;
	}
	
	
	
	public Rectangle getMezSpodniCastiZdi(){
		return new Rectangle(x,y,SIRKA, vyska);
	}
	
	public Rectangle getMezHorniCastiZdi(){
		return new Rectangle(x, 0, SIRKA, HraciPlocha.VYSKA - (vyska + MEZERA));
	}
	
	
	public void reset(){
		vygenerujNahodneHodnotyProZed();
	}
	
	
}
