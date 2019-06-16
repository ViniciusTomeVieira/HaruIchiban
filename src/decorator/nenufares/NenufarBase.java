/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.nenufares;

import javax.swing.ImageIcon;

/**
 *
 * @author vinny
 */
public class NenufarBase extends Nenufar {

    @Override
    public void selecionarImagemNenufar(ImageIcon imagem) {
        super.setImagem(new ImageIcon("imagens/nenufarClaro.png"));//To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
