package obrazek;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ZdrojObrazkuSoubor extends ZdrojObrazku{
	
	private static final String CESTA = "img/";
	
	@Override
	public void naplnMapu() {
		getMapa().put(Obrazek.POZADI.getKlic(), "pozadi3.png");
		getMapa().put(Obrazek.HRAC.getKlic(), "player.png");
		getMapa().put(Obrazek.ZED.getKlic(), "wall.png");
		//TODO hrac zed bonus
	}

	@Override
	public BufferedImage getObrazek() throws IOException {
		return ImageIO.read(new File(CESTA+ getZdroj()));
	}
	
	

}
