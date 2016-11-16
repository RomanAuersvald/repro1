package hra;

import java.awt.image.BufferedImage;

public class Zed {

	public static final int SIRKA = 45;
	public static final int RYCHLOST = -6; //rychlost pohybu zdi
	public static final int MEZERA = 200;//mezera mezi zdmi nahoøe a dole
	
	
	//TODO

	
	private static BufferedImage img = null;
	private int x; //xova sour zdi
	//yova sour zdi(horni sour spodni zdi)
	
	private int y;
	private int vyska;
	
	
	public Zed(int vzdalenost_zdi_od_zacatku_hraci_plochy) {
		this.x = vzdalenost_zdi_od_zacatku_hraci_plochy;
		
		//TODO
	}
}
