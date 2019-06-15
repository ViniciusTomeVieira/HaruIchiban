/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author vinny
 */
public class FlorBase extends Flor {
     protected int numero;
    
   

    public int getNumero() {
        return numero;
    }

    @Override
    public void selecionarImagem() {
        super.setImagem(new ImageIcon("imagens/florAmarela.png"));
    }
    
}
