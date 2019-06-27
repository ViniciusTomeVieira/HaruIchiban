/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import composite.Peca;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class Agua extends Peca{
    
    public Agua() {
        super.setImagem(new ImageIcon("imagens/agua.jpg"));
        selecionarNome();
    }
    
    public void selecionarNome(){
        super.setNome("Agua");
    }
    
}
