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
public class NenufarClaroComFlorAmarela extends NenufarDecorator {

    public NenufarClaroComFlorAmarela(Nenufar nenufar) {
        super(nenufar);
    }
    
    public void selecionarImageNenufar(){
        super.selecionarImagemNenufar(new ImageIcon("imagens/nenufarClaroComFlorAmarela.jpg"));
        selecionarNome();
    }
    public void selecionarNome(){
        super.setNome("NenufarClaroComFlorAmarela");
    }

    

   
    
    
    
}
