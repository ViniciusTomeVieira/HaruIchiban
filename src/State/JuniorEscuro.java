/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Observer.Observador;
import control.GerenciadorJogoImpl;
import decorator.nenufares.Nenufar;
import decorator.nenufares.NenufarBase;
import decorator.nenufares.NenufarEscuroComFlorAmarela;
import decorator.nenufares.NenufarEscuroComFlorRosa;

/**
 *
 * @author vinny
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
    public void juniorEscuro() {
        Nenufar nenufarBase = new NenufarBase();
        Nenufar nenufarBase2 = new NenufarBase();
        NenufarEscuroComFlorRosa necfr = new NenufarEscuroComFlorRosa(nenufarBase);
        necfr.selecionarImageNenufar();
        NenufarEscuroComFlorAmarela necfa = new NenufarEscuroComFlorAmarela(nenufarBase);
        necfa.selecionarImageNenufar();
        
        
        if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getNome().equals("NenufarEscuro")) {
            if (jogadorDaVez.getCorDaFlor().equals("Rosa")) {
                tabuleiroGerenciador[columnAtPoint][rowAtPoint] = nenufarBase;
            } else {
                tabuleiroGerenciador[columnAtPoint][rowAtPoint] = nenufarBase2;
            }
            jogadorDaVez.getMao().remove(jogadorDaVez.getFlorEscolhida());
            estadoJogo = "SeniorEscolhe";
            indiceMensagens = 4;
            trocarJogadorDaVez();
            for (Observador obs : observadores) {
                obs.notificarTabuleiroAlterado();
            }
        }
    }
    
}
