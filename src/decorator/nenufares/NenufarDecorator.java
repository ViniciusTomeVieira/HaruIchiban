/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.nenufares;

import javax.swing.ImageIcon;

/**
 *
 * @author vinny
 */
public abstract class NenufarDecorator extends Nenufar {
    
    private Nenufar nenufar;

    public NenufarDecorator(Nenufar nenufar) {
        this.nenufar = nenufar;
    }

    @Override
    public void selecionarImagemNenufar(ImageIcon imagem) {
        this.nenufar.selecionarImagemNenufar(imagem); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
