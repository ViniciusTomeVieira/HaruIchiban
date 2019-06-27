/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import control.GerenciadorJogoImpl;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class SelecionarCor extends EstadoJogo{

    public SelecionarCor(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
    }

    @Override
    public void proxEstado() {
        gerenciadorJogo.setEstadojogo(new EscolherFlores(gerenciadorJogo));
    }
    
    @Override
    public void selecionarCores() {
        switch (gerenciadorJogo.getCorDasFlores()) {
            case 0:
                gerenciadorJogo.getJogador1().setCorDaFlor("Rosa");
                gerenciadorJogo.getJogador2().setCorDaFlor("Amarela");
                break;
            case 1:
                gerenciadorJogo.getJogador1().setCorDaFlor("Amarela");
                gerenciadorJogo.getJogador2().setCorDaFlor("Rosa");
                break;
            default:
                gerenciadorJogo.getJogador1().setCorDaFlor("Rosa");
                gerenciadorJogo.getJogador2().setCorDaFlor("Amarela");
        }
    }
    
}
