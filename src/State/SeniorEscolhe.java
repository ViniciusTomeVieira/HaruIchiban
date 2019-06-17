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
public class SeniorEscolhe extends EstadoJogo{

    public SeniorEscolhe(GerenciadorJogoImpl gerenciadorJogo) {
        super(gerenciadorJogo);
    }

    @Override
    public void proxEstado() {
        gerenciadorJogo.setEstadojogo(new JuniorMovePecas(gerenciadorJogo));
    }
    
}
