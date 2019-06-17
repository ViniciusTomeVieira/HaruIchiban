/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Observer.Observador;
import control.GerenciadorJogoImpl;
import decorator.flores.Flor;

/**
 *
 * @author vinny
 */
public class EscolherFlores extends EstadoJogo {

    public EscolherFlores(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
    }

    @Override
    public void proxEstado() {
        gerenciadorJogo.setIndiceMensagens(1);
        gerenciadorJogo.setEstadojogo(new JogarFlor(gerenciadorJogo));
    }
    
    @Override
    public void escolherFloresDeck(int row, int col) {


            Flor cartaEscolhida = gerenciadorJogo.florDaVez[col][row];
            if (gerenciadorJogo.getMaoDaVez().size() < 3 && gerenciadorJogo.verificarTamanhoDeck() > 0) {
                if (gerenciadorJogo.florDaVez[col][row] != null) {
                    gerenciadorJogo.enviarCartaParaMao(cartaEscolhida);
                    gerenciadorJogo.florDaVez[col][row] = null;
                    gerenciadorJogo.getJogadorDaVez().setMao(gerenciadorJogo.getMaoDaVez());
                    gerenciadorJogo.getJogadorDaVez().setFlores(gerenciadorJogo.getFlorDaVez());
                    if (gerenciadorJogo.getMaoDaVez().size() == 3) {
                        gerenciadorJogo.trocarJogadorDaVez();
                    }
                    for (Observador obs : gerenciadorJogo.getObservadores()) {
                        obs.notificarFlorEscolhida();
                    }
                }
            } else {
                gerenciadorJogo.trocarJogadorDaVez();
                for (Observador obs : gerenciadorJogo.getObservadores()) {
                    obs.notificarFlorEscolhida();
                }
            }

    }
    
}
