package puzzle.generator.observateur;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.cglib.MethodInterceptor;
import net.sf.cglib.MethodProxy;

public class ProxyObserver<U> implements MethodInterceptor {
	
	private Collection<IObservateur<U>> observateurs = new ArrayList<IObservateur<U>>();
	private boolean echo;
	
	public ProxyObserver(){
		this.echo = true;
	}

	
	

	@SuppressWarnings("unchecked")
	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {

		
			Object o = methodProxy.invokeSuper(object, args);
			if(this.echo){
				this.echo = false;
				for(IObservateur<U> obs: this.observateurs){
					obs.notify(method, (U) object);
				}
				this.echo = true;
			}
		
		
			return o;
	
	}



	@SuppressWarnings("unchecked")
	public void add(IObservateur<?> obs){
		this.observateurs.add((IObservateur<U>) obs);
	}

	
	
	
	
}
