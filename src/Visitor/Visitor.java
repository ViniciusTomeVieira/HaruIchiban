/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visitor;

import AbstractFactory.Jogador;

/**
 *
 * @author Jogos
 */
public interface Visitor {
    void visit(Jogador jogador) throws Exception;
}
