package puzzle.generator.observateur;

import java.lang.reflect.Method;

public interface IObservateur<U> {
	public void notify(Method method,U o);
}
