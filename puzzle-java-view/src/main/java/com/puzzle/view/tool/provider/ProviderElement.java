package com.puzzle.view.tool.provider;

import java.util.Observer;


public interface ProviderElement<KEY,ELEMENT> extends Observer{
	public ELEMENT getElement(KEY k);
	public ELEMENT getElementDeferred(KEY k,Observer obs);
}
