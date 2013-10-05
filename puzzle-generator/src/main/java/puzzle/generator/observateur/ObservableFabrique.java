package puzzle.generator.observateur;

import net.sf.cglib.Enhancer;

public class ObservableFabrique<U> {

	private Class<? extends U> clazz;
	
	public ObservableFabrique(Class<? extends U> clazz){
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	public U create(){
		
		ProxyObserver proxy = new ProxyObserver<U>();
		
		U u = (U) Enhancer.enhance(clazz, null,proxy);
		
		Observer.getInstance().add(u, proxy);
		
	
		return u;
	}
	
}
