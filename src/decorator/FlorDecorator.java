/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator;

import javax.swing.Icon;

/**
 *
 * @author vinny
 */
public abstract class FlorDecorator extends Flor{
    
    private Flor flor;
    


    public FlorDecorator(Flor flor){
        this.flor = flor;
    }

    @Override
    public void selecionarImagem() {
        this.flor.selecionarImagem();
    }
    
    
    
    
    
}
