/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import Observer.Observador;
import control.GerenciadorJogoImpl;

/**
 *
 * @author vinny
 */
public class EscolherCorCommand extends Command{

    public EscolherCorCommand(Observador observer) {
        super(observer);
    }

    @Override
    public void execute(int linha, int coluna) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
