/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.Icon;

/**
 *
 * @author vinny
 */
public abstract class Flor extends Peca {
    
    protected int numero;
    
    public Flor(Icon imagem, int numero) {
        super(imagem);
        this.numero = numero;
    }
    
}
