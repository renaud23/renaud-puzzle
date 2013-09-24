package com.jPuzzle.view.image;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

public class OtherComposite  implements Composite{

	@Override
	public CompositeContext createContext(ColorModel arg0, ColorModel arg1,
			RenderingHints arg2) {
		
		return new OtherCompositeContext();
	}

}
