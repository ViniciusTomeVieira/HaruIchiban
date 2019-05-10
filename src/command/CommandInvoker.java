package command;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {

	private List<Command> todos = new ArrayList<>();
		
	public void execute(Command comm, String args[]) {
	
		comm.execute(args);
		todos.add(comm);
	}
	

}

