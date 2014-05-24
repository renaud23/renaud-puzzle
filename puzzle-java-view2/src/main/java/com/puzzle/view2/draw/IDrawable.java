package com.puzzle.view2.draw;

import com.puzzle.view2.layer.Vue;


/**
 * une entit� qui peut dessiner gr�ce � idraweroperation.
 * @author Renaud
 *
 */
public interface IDrawable {
	public boolean isChange();
	public void setChange();
	public void draw(Vue vue);
}
