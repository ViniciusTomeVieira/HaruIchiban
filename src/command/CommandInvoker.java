package command;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {

    private List<Command> todos = new ArrayList<>();
    private List<Command> imediatos = new ArrayList<>();

    public void execute(int coluna,int linha) {

        for (Command command : imediatos) {
            command.execute(coluna, linha);
            todos.add(command);
        }
        imediatos.clear();

    }

    public void add(Command comm) {
        imediatos.add(comm);
    }

}
