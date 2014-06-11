package com.puzzle.view2.layer.state;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.image.IDrawable;

public interface IAnimation extends IDrawable,DrawOperationAware{
	public void play();
	public boolean isFinish();
}
