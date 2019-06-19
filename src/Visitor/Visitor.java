/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visitor;

import AbstractFactory.Jogador;
import composite.Tabuleiro;

/**
 *
 * @author Jogos
 */
public interface Visitor {
    void visit(Jogador jogador) throws Exception;
    void visit(Tabuleiro tabuleiro) throws Exception;
}
