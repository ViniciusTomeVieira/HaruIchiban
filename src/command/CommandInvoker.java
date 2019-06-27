package command;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
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
