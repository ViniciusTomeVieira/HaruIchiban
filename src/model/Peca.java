/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.Icon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
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
