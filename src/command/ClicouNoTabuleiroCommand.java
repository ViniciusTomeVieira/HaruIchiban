/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import Observer.Observador;
import control.GerenciadorJogo;
import control.GerenciadorJogoImpl;

/**
 *
 * @author Jogos
 */
public class ClicouNoTabuleiroCommand extends Command{
    GerenciadorJogo gerenciador;
    public ClicouNoTabuleiroCommand(Observador observer) {
        super(observer);
        gerenciador = GerenciadorJogoImpl.getInstance();
    }

    @Override
    public void execute(int linha, int coluna) {
        gerenciador.clicouNoTabuleiro(linha, coluna);
    }

    

    
}
