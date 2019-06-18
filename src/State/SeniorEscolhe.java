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
import decorator.nenufares.NenufarClaro;
import decorator.nenufares.NenufarClaroComFlorAmarela;
import decorator.nenufares.NenufarClaroComFlorRosa;
import decorator.sapos.SapoAmarelo;
import decorator.sapos.SapoRosa;

/**
 *
 * @author vinny
 */
public class SeniorEscolhe extends EstadoJogo{

    public SeniorEscolhe(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
    }

    @Override
    public void proxEstado() {
        gerenciadorJogo.setEstadojogo(new JuniorMovePecas(gerenciadorJogo));
    }
    
    @Override
    public void seniorEscolhe(int columnAtPoint, int rowAtPoint){
        Nenufar nenufarBase = new NenufarBase();
        Peca peca = gerenciadorJogo.getTabuleiro().getPecaTabuleiro(columnAtPoint, rowAtPoint);
            if ( peca.getNome().equals("NenufarClaro")) {
                if (gerenciadorJogo.getJogadorDaVez().getCorDaFlor().equals("Rosa")) {
                    if (gerenciadorJogo.sapo != null) {
                        gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint,gerenciadorJogo.sapo);
                        gerenciadorJogo.sapo = null;
                    } else {
                        NenufarClaroComFlorRosa nccfr = new NenufarClaroComFlorRosa(nenufarBase);
                        nccfr.selecionarImageNenufar();
                        gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint,nenufarBase);
                    }
                } else {
                    if (gerenciadorJogo.sapo != null) {
                        gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, gerenciadorJogo.sapo);
                        gerenciadorJogo.sapo = null;
                    } else {
                        NenufarClaroComFlorAmarela nccfa = new NenufarClaroComFlorAmarela(nenufarBase);
                        nccfa.selecionarImageNenufar();
                        gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, nenufarBase);
                    }
                }

                //Troca
                gerenciadorJogo.setIndiceMensagens(5);
                proxEstado();
                gerenciadorJogo.getJogadorDaVez().getMao().remove(gerenciadorJogo.getJogadorDaVez().getFlorEscolhida());
                gerenciadorJogo.trocarJogadorDaVez();
                for (Observador obs : gerenciadorJogo.getObservadores()) {
                    obs.notificarTabuleiroAlterado();
                }

            } else if (peca.getNome().equals("SapoRosa") || peca.getNome().equals("SapoAmarelo")) {
                if (gerenciadorJogo.getJogadorDaVez().getCorDaFlor().equals("Rosa")) {
                    gerenciadorJogo.sapo =  gerenciadorJogo.getTabuleiro().getPecaTabuleiro(columnAtPoint, rowAtPoint);
                    NenufarClaroComFlorRosa nccfr = new NenufarClaroComFlorRosa(nenufarBase);
                    nccfr.selecionarImageNenufar();
                    gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint,nenufarBase);
                } else {
                    gerenciadorJogo.sapo =  gerenciadorJogo.getTabuleiro().getPecaTabuleiro(columnAtPoint, rowAtPoint);
                     NenufarClaroComFlorAmarela nccfa = new NenufarClaroComFlorAmarela(nenufarBase);
                     nccfa.selecionarImageNenufar();
                    gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint,nenufarBase);
                }
                gerenciadorJogo.setIndiceMensagens(6);
                for (Observador obs : gerenciadorJogo.getObservadores()) {
                    obs.notificarTabuleiroAlterado();
                }
            }
        
    }
    
}
