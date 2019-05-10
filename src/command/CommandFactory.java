package command;

import Observer.Observador;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;


public class CommandFactory {

	private Map<String, Class<? extends Command>> comandos = new HashMap<>();
	private Observador observer;

	public CommandFactory(Observador observer) {

		//comandos.put("new", NewCommand.class);
		//comandos.put("delete", DeleteCommand.class);
		//comandos.put("get", GetCommand.class);

		//comandos.put("all", AllCommand.class);
		this.observer = observer;
	}

	public Command getCommand(String comando) throws Exception {
	
		Class<? extends Command> commClass = comandos.get(comando);
		Constructor<? extends Command> constr = commClass.getConstructor(Observador.class);
		
		Command comm = constr.newInstance(observer);

		return comm;
	}
}
