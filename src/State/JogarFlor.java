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
    }

    @Override
    public void proxEstado() {
        gerenciadorJogo.setEstadojogo(new JuniorEscuro(gerenciadorJogo));
    }
    
}
