/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import AbstractFactory.Jogador;
import composite.Peca;
import decorator.nenufares.NenufarClaroComFlorAmarela;
import decorator.nenufares.NenufarClaroComFlorRosa;
import decorator.nenufares.NenufarEscuroComFlorAmarela;
import decorator.nenufares.NenufarEscuroComFlorRosa;

/**
 *
 * @author Adroan
 */
public class CalcularPontuacao {

    private VerificarFlores verificar;

    public int verificar(Peca[][] matriz, String cor) {
        int pontuacao = 0;
        System.out.println("Jogador: "+cor);
        if (cor.equals("Rosa")) {
            for (int linha = 0; linha < 5; linha++) {
                for (int coluna = 0; coluna < 5; coluna++) {
                    if (matriz[coluna][linha].getNome().equals("NenufarClaroComFlorRosa")
                            || matriz[coluna][linha].getNome().equals("NenufarEscuroComFlorRosa")) {
                        verificar = new VerificarQuadrado();
                        System.out.println("Verificando quadrado");
                        pontuacao += verificar.verificar(matriz, linha, coluna);
                        if (pontuacao >= 5) {
                            return 5;
                        }
                        verificar = new VerificarLinhaHorizontal();
                        System.out.println("Verificando horizontal");
                        pontuacao += verificar.verificar(matriz, linha, coluna);
                        if (pontuacao >= 5) {
                            return 5;
                        }
                        verificar = new VerificarLinhaVertical();
                        System.out.println("Verificando vertical");
                        pontuacao += verificar.verificar(matriz, linha, coluna);
                        if (pontuacao >= 5) {
                            return 5;
                        }
                        verificar = new VerificarLinhaDiagonal();
                        System.out.println("Verificando diagonal");
                        pontuacao += verificar.verificar(matriz, linha, coluna);
                        if (pontuacao >= 5) {
                            return 5;
                        }
                    }
                }
            }
            return pontuacao;
        } else if (cor.equals("Amarelo")) {
            for (int linha = 0; linha < 5; linha++) {
                for (int coluna = 0; coluna < 5; coluna++) {
                    if (matriz[coluna][linha].getNome().equals("NenufarClaroComFlorAmarela")
                            || matriz[coluna][linha].getNome().equals("NenufarEscuroComFlorAmarela")) {
                        verificar = new VerificarQuadrado();
                        System.out.println("Verificando quadrado");
                        pontuacao += verificar.verificar(matriz, linha, coluna);
                        if (pontuacao >= 5) {
                            return 5;
                        }
                        verificar = new VerificarLinhaHorizontal();
                        System.out.println("Verificando horizontal");
                        pontuacao += verificar.verificar(matriz, linha, coluna);
                        if (pontuacao >= 5) {
                           return 5;
                        }
                        verificar = new VerificarLinhaVertical();
                        System.out.println("Verificando vertical");
                        pontuacao += verificar.verificar(matriz, linha, coluna);
                        if (pontuacao >= 5) {
                            return 5;
                        }
                        verificar = new VerificarLinhaDiagonal();
                        System.out.println("Verificando diagonal");
                        pontuacao += verificar.verificar(matriz, linha, coluna);
                        if (pontuacao >=5) {
                           return 5;
                        }
                    }
                }
            }
            return pontuacao;
        }

        return 0;
    }
}
