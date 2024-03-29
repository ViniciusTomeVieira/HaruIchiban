/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visitor;

import AbstractFactory.Jogador;
import composite.Tabuleiro;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class MontarImgPontuacao extends AbstractVisitor{

    private int pontuacao;
    private String cor;

    @Override
    public void visit(Jogador jogador) throws Exception {
        pontuacao = jogador.getPontuacao();
        cor=jogador.getCorDaFlor();
    }

    public Icon getPontuacao() {
        if (pontuacao != 0) {
            if (cor.equals("Rosa")) {
                return new ImageIcon("Imagens/" + pontuacao + "ver.jpg");
            } else if (cor.equals("Amarela")) {
                return new ImageIcon("Imagens/" + pontuacao + "amar.jpg");
            }
        }
        return null;
    }
}
