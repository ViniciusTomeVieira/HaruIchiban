/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.nenufares;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class NenufarClaroComFlorRosa extends NenufarDecorator {

    public NenufarClaroComFlorRosa(Nenufar nenufar) {
        super(nenufar);
    }
    
    public void selecionarImageNenufar(){
        super.selecionarImagemNenufar(new ImageIcon("imagens/nenufarClaroComFlorRosa.png"));
    }

    

   
    
    
    
}
