/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.sapos;


import decorator.sapos.Sapo;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class SapoRosa extends SapoDecorator{

    public SapoRosa(Sapo sapo) {
        super(sapo);
    }
    
    
    
    public void selecionarImageSapo(){
        super.selecionarImagemSapo(new ImageIcon("imagens/SapoRosa.jpg"));
        selecionarNome();
    }
    
    private void selecionarNome() {
        super.setNome("SapoRosa");
    }
    
}
