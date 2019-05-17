/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import control.GerenciadorJogo;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class EscolherFloresDeckCommand extends Command{

    public EscolherFloresDeckCommand(GerenciadorJogo gerenciador, int linha, int coluna) {
        super(gerenciador, linha, coluna);
    }

    @Override
    public void execute(int linha, int coluna) {
        gerenciador.escolherFloresDeck(linha, coluna);
    }
    
}
