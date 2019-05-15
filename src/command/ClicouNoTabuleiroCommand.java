/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;


import control.GerenciadorJogo;


/**
 *
 * @author Jogos
 */
public class ClicouNoTabuleiroCommand extends Command{

    public ClicouNoTabuleiroCommand(GerenciadorJogo gerenciador, int linha, int coluna) {
        super(gerenciador, linha, coluna);
    }
    


    @Override
    public void execute(int linha, int coluna) {
        gerenciador.clicouNoTabuleiro(linha, coluna);
    }

    

    
}
