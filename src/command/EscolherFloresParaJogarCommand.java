/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import control.GerenciadorJogo;

/**
 *
 * @author Adroan
 */
public class EscolherFloresParaJogarCommand extends Command{

    public EscolherFloresParaJogarCommand(GerenciadorJogo gerenciador, int linha, int coluna) {
        super(gerenciador, linha, coluna);
    }

    @Override
    public void execute(int linha, int coluna) {
        gerenciador.escolherFlorParaJogar(linha);
    }
    
}
