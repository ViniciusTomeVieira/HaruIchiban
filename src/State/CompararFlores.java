/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import AbstractFactory.FabricaJunior;
import AbstractFactory.FabricaSenior;
import Observer.Observador;
import control.GerenciadorJogoImpl;

/**
 *
 * @author Jogos
 */
public class CompararFlores extends EstadoJogo{

    public CompararFlores(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
        this.compararFlores();
    }

    @Override
    public void proxEstado() {
         gerenciadorJogo.setEstadojogo(new JuniorEscuro(gerenciadorJogo));
    }
    
    @Override
    public void compararFlores() {
        if (gerenciadorJogo.getJogador1().getFlorEscolhida().getNumero() < gerenciadorJogo.getJogador2().getFlorEscolhida().getNumero()) {
            gerenciadorJogo.fabricaJogador = new FabricaJunior();
            gerenciadorJogo.jogador1 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador1());
            gerenciadorJogo.fabricaJogador = new FabricaSenior();
            gerenciadorJogo.jogador2 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador2());
            gerenciadorJogo.getJogador1().setJuniorSenior("Junior");
            gerenciadorJogo.getJogador2().setJuniorSenior("Senior");
            gerenciadorJogo.getJogador1().getMao().remove(gerenciadorJogo.getJogador1().getFlorEscolhida());
            gerenciadorJogo.getJogador2().getMao().remove(gerenciadorJogo.getJogador2().getFlorEscolhida());
            proxEstado();
            gerenciadorJogo.trocarJogadorDaVez();
            gerenciadorJogo.setIndiceMensagens(3);
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarJuniorSenior();
            }
        } else if (gerenciadorJogo.getJogador1().getFlorEscolhida().getNumero() > gerenciadorJogo.getJogador2().getFlorEscolhida().getNumero()) {
            gerenciadorJogo.fabricaJogador = new FabricaSenior();
            gerenciadorJogo.jogador1 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador1());
            gerenciadorJogo.fabricaJogador = new FabricaJunior();
            gerenciadorJogo.jogador2 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador2());
            gerenciadorJogo.getJogador1().setJuniorSenior("Senior");
            gerenciadorJogo.getJogador2().setJuniorSenior("Junior");
            gerenciadorJogo.getJogador1().getMao().remove(gerenciadorJogo.getJogador1().getFlorEscolhida());
            gerenciadorJogo.getJogador2().getMao().remove(gerenciadorJogo.getJogador2().getFlorEscolhida());
            proxEstado();
            gerenciadorJogo.trocarJogadorDaVez();
            gerenciadorJogo.setIndiceMensagens(3);
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarJuniorSenior();
            }
        } else { //Empate
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarEmpateComparacao();
            }
        }

    }
    
    public void empate(int vencedor){
        if(vencedor == 1){
            gerenciadorJogo.fabricaJogador = new FabricaJunior();
            gerenciadorJogo.jogador1 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador1());
            gerenciadorJogo.fabricaJogador = new FabricaSenior();
            gerenciadorJogo.jogador2 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador2());
            gerenciadorJogo.getJogador1().setJuniorSenior("Junior");
            gerenciadorJogo.getJogador2().setJuniorSenior("Senior");
            gerenciadorJogo.getJogador1().getMao().remove(gerenciadorJogo.getJogador1().getFlorEscolhida());
            gerenciadorJogo.getJogador2().getMao().remove(gerenciadorJogo.getJogador2().getFlorEscolhida());
            proxEstado();
            gerenciadorJogo.trocarJogadorDaVez();
            gerenciadorJogo.setIndiceMensagens(3);
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarJuniorSenior();
            }
        }else{
            gerenciadorJogo.fabricaJogador = new FabricaSenior();
            gerenciadorJogo.jogador1 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador1());
            gerenciadorJogo.fabricaJogador = new FabricaJunior();
            gerenciadorJogo.jogador2 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador2());
            gerenciadorJogo.getJogador1().setJuniorSenior("Senior");
            gerenciadorJogo.getJogador2().setJuniorSenior("Junior");
            gerenciadorJogo.getJogador1().getMao().remove(gerenciadorJogo.getJogador1().getFlorEscolhida());
            gerenciadorJogo.getJogador2().getMao().remove(gerenciadorJogo.getJogador2().getFlorEscolhida());
            proxEstado();
            gerenciadorJogo.trocarJogadorDaVez();
            gerenciadorJogo.setIndiceMensagens(3);
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarJuniorSenior();
            }
        }
    }
    
}