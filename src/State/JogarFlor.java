/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import control.GerenciadorJogoImpl;

/**
 *
 * @author vinny
 */
public class JogarFlor extends EstadoJogo {

    public JogarFlor(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
        System.out.println("Jogar flor");
    }

    @Override
    public void proxEstado() {
        gerenciadorJogo.setEstadojogo(new CompararFlores(gerenciadorJogo));
    }
    
    @Override
    public void escolherFlorParaJogar(int index) {
        gerenciadorJogo.getJogadorDaVez().setFlorEscolhida(gerenciadorJogo.getMaoDaVez().get(index));
        gerenciadorJogo.trocarJogadorDaVez();
    }
    
}
