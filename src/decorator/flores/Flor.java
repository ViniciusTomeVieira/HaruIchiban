/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.flores;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import composite.Peca;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public abstract class Flor extends Peca {
    
    public int numero;
    
    public Flor() {

    }
    
    public void setNumero(int numero){
        this.numero = numero;
        System.out.println("Numero setado: " + this.numero);
    }
    public int getNumero(){
        return this.numero;
    }
    
    

    public void selecionarImagemFloc(ImageIcon imageIcon) {
        this.setImagem(imageIcon);
    }
        
    

    
    
}
