package app;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import hra.HraciPlocha;
import obrazek.ManagerObrazku;
import obrazek.ZdrojObrazkuSoubor;

public class FlappyGameMain extends JFrame{
	private ManagerObrazku mo;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlappyGameMain() {
		
		mo = new ManagerObrazku(new ZdrojObrazkuSoubor());
	}
	
	public void initGUI(){
		setSize(HraciPlocha.SIRKA, HraciPlocha.VYSKA);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("FlappyDang");
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void spust(){
		
		class Vlakno extends SwingWorker<Object, Object> {
			
			private JFrame vlastnik;
			private JLabel lb;
			private HraciPlocha hraciPlocha;
			
			public void setVlastnik(JFrame vlastnik) {
				this.vlastnik = vlastnik;
			}
			
			public void doBeforeExecute(){
				lb = new JLabel("Waiting huh?");
				lb.setFont(new Font("Arial", Font.BOLD, 42));
				lb.setForeground(Color.RED);
				lb.setHorizontalAlignment(SwingConstants.CENTER);
				
				vlastnik.add(lb);
				lb.setVisible(true);
				vlastnik.revalidate();
				vlastnik.repaint();
			}
			
			@Override
			protected Object doInBackground() throws Exception {
				mo.pripravObrazky();
				hraciPlocha = new HraciPlocha(mo);
				hraciPlocha.pripravHraciPlochu();
				return null;
			}
			
			@Override
			protected void done() {
				super.done();
				vlastnik.remove(lb);
				vlastnik.revalidate();
				vlastnik.add(hraciPlocha);
				hraciPlocha.setVisible(true);
				vlastnik.revalidate();
				vlastnik.repaint();
			}
		}
		
		Vlakno v = new Vlakno();
		v.setVlastnik(this);
		v.doBeforeExecute();
		v.execute();
		
		
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				FlappyGameMain app = new FlappyGameMain();
				app.initGUI();
				app.spust();			}
		});

	}

}
