/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visitor;

import AbstractFactory.Jogador;
import Strategy.CalcularPontuacao;
import composite.Tabuleiro;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class VerificaPadrao extends AbstractVisitor {

    private final CalcularPontuacao calcular = new CalcularPontuacao();
    private int pontuacaoRosa;
    private int pontuacaoAmarelo;

    public int getPontuacaoRosa() {
        return pontuacaoRosa;
    }

    public int getPontuacaoAmarelo() {
        return pontuacaoAmarelo;
    }

    @Override
    public void visit(Tabuleiro tabuleiro) throws Exception {
        pontuacaoRosa = calcular.verificar(tabuleiro.getTabuleiro(), "Rosa");
        System.out.println("Está no visit");
        pontuacaoAmarelo = calcular.verificar(tabuleiro.getTabuleiro(), "Amarelo");
    }
}
