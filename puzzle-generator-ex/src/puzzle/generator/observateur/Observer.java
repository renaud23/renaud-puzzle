package puzzle.generator.observateur;

import java.util.HashMap;
import java.util.Map;




public class Observer {
	
	Map<Object,ProxyObserver<?>> map;
	
	private static Observer instance;
	
	public static Observer getInstance(){
		if(Observer.instance == null)
			Observer.instance = new Observer();
		return Observer.instance;
	}
	
	private Observer(){
		map = new HashMap<Object, ProxyObserver<?>>();
	}
	
	public void observe(IObservateur<?> obs,Object o){
		if(map.containsKey(o)){
			map.get(o).add(obs);
		}
	}
	
	public void add(Object o,ProxyObserver<?> proxy){
		this.map.put(o, proxy);
	}
	
	
	public void clean(Object o){
		map.remove(o);
	}
}
