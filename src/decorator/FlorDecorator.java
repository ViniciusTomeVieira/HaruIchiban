/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator;

import javax.swing.Icon;
import javax.swing.ImageIcon;

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
    public void setNumero(int numero) {
        this.flor.setNumero(numero); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void selecionarImagem(ImageIcon imageIcon) {
        this.flor.selecionarImagem(imageIcon);
    }
    
    
    
    
    
}
