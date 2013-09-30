package puzzle.generator.vue;

import java.awt.TextArea;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;

public class LoggerArea extends JPanel{
	
	private static LoggerArea instance;
	
	public static LoggerArea getInstance(){
		if(LoggerArea.instance == null)
			LoggerArea.instance = new LoggerArea();
		return LoggerArea.instance;
	}

	private TextArea textArea;
	
	private LoggerArea(){
		this.textArea = new TextArea();
		this.add(this.textArea);
	}
	
	public void info(String texte){
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		Date date = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		this.textArea.append("INFO ");
		this.textArea.append(format.format(date));
		this.textArea.append(" ");
		this.textArea.append(texte);
		this.textArea.append("\n");
	}
	
	
	public void append(String texte){
		this.textArea.append(texte);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7006938969342965445L;

	@Override
	public void repaint() {
		super.repaint();
		if(this.textArea != null){
			this.textArea.setPreferredSize(this.getSize());
		}
	}
	

}
