package hra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import obrazek.Obrazek;
import obrazek.ZdrojObrazkuSoubor;

public class HraciPlocha extends JPanel {
	public static final boolean DEBUG = true;
	private static final long serialVersionUID = 1L;
	public static final int VYSKA = 800;
	public static final int SIRKA = 600;

	// r\chlost behu pozadi
	public static final int RYCHLOST = -2;
	
	
	//musí být alespoò tøi zdi, jinak se první zeï nestihne posunout a vygenerovat se znovu vpravo
	public static final int POCET_ZDI = 4;
	
	private SeznamZdi seznamZdi;
	private Zed aktualniZed;
	private Zed predchoziZed;
	
	private int skore = 0; //
	private JLabel lblSkore;
	private JLabel lblZprava;
	private Font font;
	private Hrac hrac;
	private Font fontZpravy;
	private BufferedImage imgPozadi;
	private Timer casovacAnimace;
	private boolean pauza = false;
	private boolean hraBezi = false;
	private int posunPozadiX = 0;

	public HraciPlocha() {
		// TODO
		ZdrojObrazkuSoubor z = new ZdrojObrazkuSoubor();
		z.naplnMapu();
		z.setZdroj(Obrazek.POZADI.getKlic());
		
		try {
			imgPozadi = z.getObrazek();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		z.setZdroj(Obrazek.HRAC.getKlic());
		BufferedImage imgHrac;
		// hrac = new Hrac(null);

		try {
			imgHrac = z.getObrazek();
			hrac = new Hrac(imgHrac);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		z.setZdroj(Obrazek.ZED.getKlic());
		BufferedImage imgZed;

		try {
			imgZed = z.getObrazek();
			Zed.setObrazek(imgZed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		seznamZdi = new SeznamZdi();
		vyrobFontyALabely();
	}
	
	private void vyrobFontyALabely(){
		font = new Font("Arial", Font.BOLD, 40);
		fontZpravy = new Font("Arial", Font.BOLD, 20);
		
		this.setLayout(new BorderLayout());
		
		lblZprava = new JLabel("");
		lblZprava.setFont(fontZpravy);
		lblZprava.setForeground(Color.YELLOW);
		lblZprava.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblSkore = new JLabel("0");
		lblSkore.setFont(font);
		lblSkore.setForeground(Color.GREEN);
		lblSkore.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.add(lblSkore, BorderLayout.NORTH);
		this.add(lblZprava, BorderLayout.CENTER);
		
		
	}
	
	
	private void vyrobZdi(int pocet){
		Zed zed;
		int vzdalenost = HraciPlocha.SIRKA;
		for(int i=0; i<pocet;i++){
			zed = new Zed(vzdalenost);
			seznamZdi.add(zed);
			vzdalenost = vzdalenost + (HraciPlocha.SIRKA/2);
			
		}
		vzdalenost = vzdalenost -HraciPlocha.SIRKA - Zed.SIRKA;
		Zed.setVzdalenostPosledniZdi(vzdalenost);
	}

	public void paint(Graphics g) {
		super.paint(g);

		// dve pozadi za sebe pro plynulé pøechody
		// prvni
		g.drawImage(imgPozadi, posunPozadiX, 0, null);
		// druhe je posunuto o sirku obrazku
		g.drawImage(imgPozadi, posunPozadiX + imgPozadi.getWidth(), 0, null);

		if (HraciPlocha.DEBUG) {
			g.setColor(Color.BLACK);
			g.drawString("posun pozadi x= " + posunPozadiX, 0, 10);
		}
		
		for (Zed zed : seznamZdi) {
			zed.paint(g);
		}

		hrac.paint(g);
		lblSkore.paint(g);
		lblZprava.paint(g);
		
	}

	private void posun() {
		if (!pauza && hraBezi) {
			//nastavime aktualni zed
			aktualniZed = seznamZdi.getAktualniZed();
			//predchozi zed
			predchoziZed = seznamZdi.getPredchoziZed();
			
			//detekcekolizi
			if (isKolizeSeZdi(predchoziZed, hrac) || isKolizeSeZdi(aktualniZed, hrac) ||
					isKolizeSHraniciHraciPlochy(hrac)) {
				ukonciAVyresetujHruPoNarazu();
				
			} else {
				
				
				for (Zed zed : seznamZdi) {
					zed.posun();
				}
				hrac.posun();
				
				//hrac prosel zdi bez narazu
				//zjistit, kde se nachazi
				//bud pred aktualni zdi - nedelej nic
				//nebo za aktualni zdi - posun dalsi zed v poradi a prepocitej skore
				
				if (hrac.getX()>= aktualniZed.getX()) {
					
					seznamZdi.nastavIndexDalsiZdiNaAktualni();
					zvedniSkoreZed();
					lblSkore.setText(skore + "");
					
				}
			}


			// posun pozce pozadi hraci plochy
			posunPozadiX = posunPozadiX + HraciPlocha.RYCHLOST;
			// kdyz se pozadi cele doposouva zacni od zacatku
			if (posunPozadiX == -imgPozadi.getWidth()) {
				posunPozadiX = 0;
			}
		}
	}
	
	
	private void ukonciAVyresetujHruPoNarazu() {
		hraBezi = false;
		casovacAnimace.stop();
		casovacAnimace = null;
		vyresetujHru();
		 nastavZpravuNarazDoZdi();
		
	}

	private boolean isKolizeSeZdi(Zed zed,Hrac hrac){
		return false;
		//return (zed.getMezSpodniCastiZdi().intersects(hrac.getMez())) || 
		//		(zed.getMezHorniCastiZdi().intersects(hrac.getMez()));
	}
	
	private boolean isKolizeSHraniciHraciPlochy(Hrac hrac){
		return (hrac.getY() <=0) || (hrac.getY() >= HraciPlocha.VYSKA - hrac.getVyskaHrace() - 40);
	}
	
	

	private void spustHru() {
		casovacAnimace = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				posun();
			}
		});
		nastavZpravuPrazdna();
		hraBezi = true;
		casovacAnimace.start();
	}

	public void pripravHraciPlochu() {
		nastavZpravuOvladani();
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					// skok
					hrac.skok();

				}

				if (e.getButton() == MouseEvent.BUTTON3) {
					// pauza
					if (hraBezi) {
						if (pauza) {
							nastavZpravuPrazdna();
							pauza = false;
						} else {
							nastavZpravuPauza();
							pauza = true;
						}
					} else {
						pripravNovouHru();
						spustHru();
					}
				}

			}

		});
		setSize(SIRKA, VYSKA);
	}

	protected void pripravNovouHru() {
		vyresetujHru();
	}
	
	
	private void vyresetujHru(){
		resetujVsechnyZdi();
		hrac.reset();
		//nejprve zobraz stare skore
		lblSkore.setText(skore + "");
		
		vynulujSkore();


	}


	private void resetujVsechnyZdi() {
		seznamZdi.clear();
		vyrobZdi(POCET_ZDI);
		
	}

	private void vynulujSkore() {
		skore = 0;
		
	}
	
	private void zvedniSkoreZed(){
		skore = skore + Zed.BODY_ZA_ZED;
	}
	
	private void nastavZpravuNarazDoZdi(){
		lblZprava.setFont(font);
		lblZprava.setText("U suck!");
	}
	
	
	private void nastavZpravuPauza(){
		lblZprava.setFont(font);
		lblZprava.setText("Pauza");
	}
	
	private void nastavZpravuOvladani(){
		lblZprava.setFont(fontZpravy);
		lblZprava.setText("pravý klik = start/pauza, levý klik = skok");
	}
	
	private void nastavZpravuPrazdna(){
		lblZprava.setFont(font);
		lblZprava.setText("");
	}
	
	
	

}
