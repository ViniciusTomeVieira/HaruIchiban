/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Observer.Observador;
import composite.Peca;
import control.GerenciadorJogoImpl;
import model.Agua;

/**
 *
 * @author vinny
 */
public class JuniorMovePecas extends EstadoJogo{

    public JuniorMovePecas(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
    }

    @Override
    public void proxEstado() {
        gerenciadorJogo.setEstadojogo(new SeniorEscolheEscuro(gerenciadorJogo));
    }
    
    @Override
    public void juniorMovePecas(int columnAtPoint, int rowAtPoint) {
        Peca peca = gerenciadorJogo.getTabuleiro().getPecaTabuleiro(columnAtPoint, rowAtPoint);
         //!= Agua.class
            if (!peca.getNome().equals("Agua")) {
                gerenciadorJogo.setPosicaoNenufarX(columnAtPoint);
                gerenciadorJogo.setPosicaoNenufarY(rowAtPoint);
                gerenciadorJogo.setEscolheuFlorMover(true);
                gerenciadorJogo.setIndiceMensagens(7);
                for (Observador obs : gerenciadorJogo.getObservadores()) {
                    obs.notificarFlorEscolhidaParaMover();
                }
            }
        }      
}
