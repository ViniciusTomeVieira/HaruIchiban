/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.nenufares;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import composite.Peca;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public abstract class Nenufar extends Peca{

    public Nenufar() {
    }
    
    public void selecionarImagemNenufar(ImageIcon imagem){
        this.setImagem(imagem);
    }
    
    
    
}
