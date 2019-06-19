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
 * @author Adroan
 */
public class AbstractVisitor implements Visitor{

    @Override
    public void visit(Jogador jogador) throws Exception {}

    @Override
    public void visit(Tabuleiro tabuleiro) throws Exception {}
    
}
