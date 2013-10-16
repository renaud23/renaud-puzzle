package com.puzzle.view.basicTapis;


import javax.swing.SwingUtilities;

import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.RepaintTask;
import com.puzzle.view.controller.AbstractTapisController;
import com.puzzle.view.drawer.DrawSelection;









public class TapisBasicController extends AbstractTapisController{

	public TapisBasicController(Fenetre fenetre, Tapis tapis) {
		super(fenetre, tapis);
		
		this.converter = new TapisBasicConverter(fenetre.getOffscreen(), tapis);
		this.selectionDrawer = new DrawSelection(this.fenetre.getBuffer(1), this.converter);
		this.tapisDrawer = new TapisBasicDrawer(tapis,fenetre.getBuffer(0),this.converter);
		this.selectionDrawer.setParam(this.selectionParam);
		this.converter.update();
		
		this.tapisDrawer.draw();
		this.selectionDrawer.draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}
	

	
}
