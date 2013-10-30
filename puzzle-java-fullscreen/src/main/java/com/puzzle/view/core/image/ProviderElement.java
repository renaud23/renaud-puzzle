package com.puzzle.view.core.image;

import java.util.Observer;


public interface ProviderElement<KEY,ELEMENT> extends Observer{
	public ELEMENT getElement(KEY k);
	public ELEMENT getElementDeferred(KEY k,Observer obs);
}
