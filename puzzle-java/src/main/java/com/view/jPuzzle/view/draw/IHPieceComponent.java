package com.view.jPuzzle.view.draw;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import com.puzzle.model.ComponentPiece;

/**
 * proxy dynamique permettant d'ajouter la fonction de dessin
 * aux pieces. Il fait office de décorateur dynamique.
 * @author Renaud
 *
 */
public class IHPieceComponent<U extends ComponentPiece> implements InvocationHandler {
	
	private U component;
	private IDrawer drawer;

	public IHPieceComponent(U cmp,IDrawer drawer) {
		this.drawer = drawer;
		this.component = cmp;
	}



	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object rtn = null;
		if("draw".equals(method.getName())){
			this.drawer.draw();
		}else{
			rtn =  method.invoke(this.component,args);
		}
		
		return rtn;
	}
	
	
	
	public static <U extends ComponentPiece> ComponentPiece createComponent(U piece,IDrawer drawer){
		ComponentPiece p = (ComponentPiece) Proxy.newProxyInstance(
				ComponentPiece.class.getClassLoader(),
				new Class[] {ComponentPiece.class, IDrawer.class },
				new IHPieceComponent<U>(piece,drawer));
		
		return p;
	}

}
