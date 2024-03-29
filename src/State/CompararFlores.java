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
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class CompararFlores extends EstadoJogo{

    public CompararFlores(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
    }

    @Override
    public void proxEstado() {
         gerenciadorJogo.setEstadojogo(new JuniorEscuro(gerenciadorJogo));
         gerenciadorJogo.trocarJogadorDaVez();
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
            gerenciadorJogo.setIndiceMensagens(3);
            System.out.println(gerenciadorJogo.getEstadojogo().toString());
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarJuniorSenior();
            }   
            proxEstado();
        } else if (gerenciadorJogo.getJogador1().getFlorEscolhida().getNumero() > gerenciadorJogo.getJogador2().getFlorEscolhida().getNumero()) {
            gerenciadorJogo.fabricaJogador = new FabricaSenior();
            gerenciadorJogo.jogador1 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador1());
            gerenciadorJogo.fabricaJogador = new FabricaJunior();
            gerenciadorJogo.jogador2 = gerenciadorJogo.getFabricaJogador().criarJogador(gerenciadorJogo.getJogador2());
            gerenciadorJogo.getJogador1().setJuniorSenior("Senior");
            gerenciadorJogo.getJogador2().setJuniorSenior("Junior");
            gerenciadorJogo.getJogador1().getMao().remove(gerenciadorJogo.getJogador1().getFlorEscolhida());
            gerenciadorJogo.getJogador2().getMao().remove(gerenciadorJogo.getJogador2().getFlorEscolhida());            
            gerenciadorJogo.setIndiceMensagens(3);
            System.out.println(gerenciadorJogo.getEstadojogo().toString());
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarJuniorSenior();
            }
           proxEstado();
        } else { //Empate
            gerenciadorJogo.setIndiceMensagens(9);
            gerenciadorJogo.empate = true;
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarEmpateComparacao();
            }
        }
        }
    
    
    @Override
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
            gerenciadorJogo.setIndiceMensagens(9);
            gerenciadorJogo.setJogadorDaVez(gerenciadorJogo.getJogador1());
            gerenciadorJogo.setMaoDaVez(gerenciadorJogo.getJogador1().getMao());
            gerenciadorJogo.setFlorDaVez(gerenciadorJogo.getJogador1().getFlores());
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
            gerenciadorJogo.setIndiceMensagens(9);
            gerenciadorJogo.setJogadorDaVez(gerenciadorJogo.getJogador2());
            gerenciadorJogo.setMaoDaVez(gerenciadorJogo.getJogador2().getMao());
            gerenciadorJogo.setFlorDaVez(gerenciadorJogo.getJogador2().getFlores());
            for (Observador obs : gerenciadorJogo.getObservadores()) {
                obs.notificarJuniorSenior();
            }
        }
    }
    
}
