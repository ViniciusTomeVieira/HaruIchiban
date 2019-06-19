/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visitor;

import Strategy.CalcularPontuacao;
import composite.Tabuleiro;

/**
 *
 * @author Adroan
 */
public class VerificaPadrao extends AbstractVisitor{
    private final CalcularPontuacao calcular = new CalcularPontuacao();
    private int pontuacao;
    @Override
    public void visit(Tabuleiro tabuleiro) throws Exception {
        pontuacao = calcular.verificar(tabuleiro.getTabuleiro());
    }

    public int getPontuacao() {
        return pontuacao;
    }    
}
