/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package composite;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public  class Peca extends Objeto {
    
    private String nome;
    

    public Peca() {
    }

    public Icon getImagem() {
        return super.imagem;
    }

    @Override
    public  void setImagem(ImageIcon imagem){
        super.imagem = imagem;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
    
    

    
    
    
    
    
    
}
