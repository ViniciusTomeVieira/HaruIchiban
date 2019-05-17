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
public abstract class Flor extends Peca {
    
    protected int numero;
    
    public Flor(Icon imagem, int numero) {
        super(imagem);
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }
    
}
