/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.flores;

import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class FlorBase extends Flor {



    @Override
    public void setNumero(int numero) {
        super.setNumero(numero); //To change body of generated methods, choose Tools | Templates.
    }


    public void selecionarImagem() {
        super.setImagem(new ImageIcon("imagens/florAmarela.png"));
    }
    
}
