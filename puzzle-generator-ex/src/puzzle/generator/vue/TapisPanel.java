package puzzle.generator.vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import puzzle.generator.modele.Tapis;

public class TapisPanel extends JPanel implements ChangeListener{

	private static TapisPanel instance;
	
	public static TapisPanel getInstance(){
		if(TapisPanel.instance == null)
			TapisPanel.instance = new TapisPanel();
		return TapisPanel.instance;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1056011462438610210L;

	private JSlider sliderHauteur;
	private JSlider sliderLargeur;
	private JColorChooser chooser;
	
	
	private TapisPanel(){
		int min = 1000;
		int max = 10000;
	
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.black));
		
		
		this.sliderHauteur = new JSlider(min,max,max / 2);
		this.sliderLargeur = new JSlider(min,max,max / 2);
		this.sliderHauteur.addChangeListener(this);
		this.sliderHauteur.setValue(Tapis.DEFAULT_TAPIS.getHauteur());
		this.sliderLargeur.setValue(Tapis.DEFAULT_TAPIS.getLargeur());
		this.sliderLargeur.addChangeListener(this);
		this.chooser = new JColorChooser(
			new Color(Tapis.DEFAULT_TAPIS.getR()
					, Tapis.DEFAULT_TAPIS.getG()
					, Tapis.DEFAULT_TAPIS.getB()));
		
		
		
		this.addComponent("largeur du tapis",this.sliderHauteur);
		this.addComponent("hauteur du tapis",this.sliderLargeur);
		this.addComponent("Couleur du tapis",this.chooser);
	}
	
	
	
	private void addComponent(String title,Component c){
		Label label = new Label(title);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(label);
		panel.add(c);
	
		this.add(panel);
		
	}

	public int getLargeurTapis(){
		return this.sliderLargeur.getValue();
	}
	
	public int getHauteurTapis(){
		return this.sliderHauteur.getValue();
	}
	
	public Color getColorTapis(){
		return this.chooser.getColor();
	}

	public void stateChanged(ChangeEvent e) {
		
		
	}
	
}
