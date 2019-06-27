/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.nenufares;


import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class NenufarClaro extends NenufarDecorator{
    
    public NenufarClaro(Nenufar nenufar) {
        super(nenufar);
    }
    
    public void selecionarImageNenufar(){
        super.selecionarImagemNenufar(new ImageIcon("imagens/nenufarClaro.jpg"));
        selecionarNome();
    }
    
    public void selecionarNome(){
        super.setNome("NenufarClaro");
    }
    
    
    
}
