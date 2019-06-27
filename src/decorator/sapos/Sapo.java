/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.sapos;

import javax.swing.ImageIcon;
import composite.Peca;


/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public abstract class Sapo extends Peca {
    
    public Sapo() {
        
    }
    
    public void selecionarImagemSapo(ImageIcon imagem){
        this.setImagem(imagem);
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
