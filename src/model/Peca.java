/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.Icon;

/**
 *
 * @author Adroan
 */
public  abstract class Peca {
    private Icon imagem;

    public Peca(Icon imagem) {
        this.imagem = imagem;
    }

    public Icon getImagem() {
        return imagem;
    }
    
    
    
    
}
