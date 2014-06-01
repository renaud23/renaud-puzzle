package com.puzzle.view2.image;

import com.puzzle.view2.layer.Vue;




/**
 * une entité qui peut dessiner grâce à idraweroperation.
 * @author Renaud
 *
 */
public interface IDrawable {
	public boolean isChange();
	public void setChange();
	public void draw(Vue vue);
}
