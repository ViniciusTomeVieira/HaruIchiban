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
import decorator.nenufares.NenufarEscuro;
import decorator.sapos.SapoAmarelo;
import decorator.sapos.SapoRosa;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinny
 */
public class SeniorEscolheEscuro extends EstadoJogo {

    public SeniorEscolheEscuro(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
    }

    @Override
    public void proxEstado() {
        gerenciadorJogo.setEstadojogo(new EscolherFlores(gerenciadorJogo));
    }
    @Override
    public void seniorEscolheEscuro(int columnAtPoint, int rowAtPoint) {
        Nenufar nenufarBase = new NenufarBase();
        Peca peca = gerenciadorJogo.getTabuleiro().getPecaTabuleiro(columnAtPoint, rowAtPoint);
        if (gerenciadorJogo.sapo != null) {

            if (peca.getNome().equals("NenufarClaro")) {
                gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, gerenciadorJogo.sapo);
                gerenciadorJogo.setSapoInserido(true);
            }
        }
        if ((peca.getNome().equals("NenufarClaro")|| peca.getNome().equals("SapoAmarelo")|| peca.getNome().equals("SapoRosa"))) {
            if ((peca.getNome().equals("SapoAmarelo")|| peca.getNome().equals("SapoRosa")) && gerenciadorJogo.sapo == null) {
                gerenciadorJogo.sapo = peca;
                NenufarEscuro nenufarEscuro = new NenufarEscuro(nenufarBase);
                nenufarEscuro.selecionarImageNenufar();
                gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, nenufarBase);
                gerenciadorJogo.setIndiceMensagens(6);
                for (Observador obs : gerenciadorJogo.getObservadores()) {
                    obs.notificarTabuleiroAlterado();
                }
            }
            if (peca.getNome().equals("NenufarClaro") && !gerenciadorJogo.isSapoInserido()) {
                NenufarEscuro nenufarEscuro = new NenufarEscuro(nenufarBase);
                nenufarEscuro.selecionarImageNenufar();
                gerenciadorJogo.getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, nenufarBase);
                gerenciadorJogo.setSapoInserido(true);
            }
            if (gerenciadorJogo.isSapoInserido()) {
                gerenciadorJogo.sapo = null;
                gerenciadorJogo.setSapoInserido(false);
                gerenciadorJogo.setIndiceMensagens(0);
                proxEstado();
                gerenciadorJogo.recomecarComJogador1();
                if (gerenciadorJogo.verificarTamanhoDeck() == 0) {
                    gerenciadorJogo.trocarJogadorDaVez();
                }
                if (gerenciadorJogo.verificarTamanhoDeck() == 0) {
                    gerenciadorJogo.trocarJogadorDaVez();
                }
                for (Observador obs : gerenciadorJogo.getObservadores()) {
                    obs.notificarTabuleiroAlterado();
                }
                try {
                    gerenciadorJogo.atualizarPontuacao();
                } catch (Exception ex) {
                    Logger.getLogger(SeniorEscolheEscuro.class.getName()).log(Level.SEVERE, null, ex);
                }
                gerenciadorJogo.verificarVencedor();

            }

        }
    }

    
    
}
