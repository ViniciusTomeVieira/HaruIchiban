/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visitor;

import AbstractFactory.Jogador;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Jogos
 */
public class MontarImgPontuacao implements Visitor {

    private int pontuacao;
    private String cor;

    @Override
    public void visit(Jogador jogador) throws Exception {
        pontuacao = jogador.getPontuacao();

    }

    public Icon getPontuacao() {
        if (pontuacao != 0) {
            if (cor.equals("Rosa")) {
                return new ImageIcon("Imagens/" + pontuacao + "ver.jpg");
            } else if (cor.equals("Amarelo")) {
                return new ImageIcon("Imagens/" + pontuacao + "amar.jpg");
            }
        }
        return null;
    }

}
