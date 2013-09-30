package puzzle.generator.vue;

import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

public class MonComposite implements Composite {
	
	Color color;
	
	public MonComposite(Color color){
		this.color = color;
	}

	public CompositeContext createContext(ColorModel srcColorModel,
			ColorModel dstColorModel, RenderingHints hints) {
		return new MonCompositeContext(this.color);
	}

}
