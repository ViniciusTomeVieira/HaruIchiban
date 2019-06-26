/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.sapos;

import javax.swing.ImageIcon;

/**
 *
 * @author vinny
 */
public class SapoBase extends Sapo{
    
    public void selecionarImagem(){
        super.setImagem(new ImageIcon("imagens/sapoRosa.jpg"));
        selecionarNome();
    }
    private void selecionarNome() {
        super.setNome("SapoRosa");
    }
}
