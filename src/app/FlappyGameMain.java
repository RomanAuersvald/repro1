package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import hra.HraciPlocha;

public class FlappyGameMain extends JFrame{
	private HraciPlocha hp;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlappyGameMain() {
	}
	
	public void initGUI(){
		setSize(HraciPlocha.SIRKA, HraciPlocha.VYSKA);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("FlappyDang");
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void spust(){
		hp = new HraciPlocha();
		hp.pripravHraciPlochu();
		getContentPane().add(hp, "Center");
		
		hp.setVisible(true);
		this.revalidate();
		hp.repaint();
		
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				FlappyGameMain app = new FlappyGameMain();
				app.initGUI();
				this.revalidate();
				//this.
			}
		});

	}

}
