/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Observer.Observador;
import composite.Peca;
import control.GerenciadorJogoImpl;
import decorator.nenufares.Nenufar;
import decorator.nenufares.NenufarBase;
import decorator.nenufares.NenufarEscuroComFlorAmarela;
import decorator.nenufares.NenufarEscuroComFlorRosa;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class JuniorEscuro extends EstadoJogo {

    public JuniorEscuro(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
    }

    @Override
    public void proxEstado() {
        gerenciadorJogo.setEstadojogo(new SeniorEscolhe(gerenciadorJogo));
    }
    
    @Override
    public void juniorEscuro(int columnAtPoint, int rowAtPoint ) {
        Nenufar nenufarBase = new NenufarBase();
        
        Peca peca = gerenciadorJogo.getTabuleiro().getPecaTabuleiro(columnAtPoint,rowAtPoint);
        if (peca.getNome().equals("NenufarEscuro")) {
            if (gerenciadorJogo.getJogadorDaVez().getCorDaFlor().equals("Rosa")) {
                NenufarEscuroComFlorRosa necfr = new NenufarEscuroComFlorRosa(nenufarBase);
                necfr.selecionarImageNenufar();
                gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint,rowAtPoint,nenufarBase);
            } else {
                NenufarEscuroComFlorAmarela necfa = new NenufarEscuroComFlorAmarela(nenufarBase);
                necfa.selecionarImageNenufar();
                gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint,rowAtPoint,nenufarBase);
            }
            gerenciadorJogo.getJogadorDaVez().getMao().remove(gerenciadorJogo.getJogadorDaVez().getFlorEscolhida());
            proxEstado();
            gerenciadorJogo.setIndiceMensagens(4);
            gerenciadorJogo.trocarJogadorDaVez();
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarTabuleiroAlterado();
            }
        }
    }
    
}
