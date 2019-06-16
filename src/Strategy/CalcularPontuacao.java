/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;


import model.Peca;

/**
 *
 * @author Adroan
 */
public class CalcularPontuacao {

    private VerificarFlores verificar;

    public int verificar(Peca[][] matriz) {
        int pontuacao=0;
        for (int linha = 0; linha < 5; linha++) {
            for (int coluna = 0; coluna < 5; coluna++) {
                if (matriz[coluna][linha].getClass() == NenufarClaroComFlorAmarela.class
                        || matriz[coluna][linha].getClass() == NenufarClaroComFlorRosa.class
                        || matriz[coluna][linha].getClass() == NenufarEscuroComFlorAmarela.class
                        || matriz[coluna][linha].getClass() == NenufarEscuroComFlorRosa.class) {
                    verificar = new VerificarQuadrado();
                    System.out.println("Verificando quadrado");
                    pontuacao+=verificar.verificar(matriz, linha, coluna);
                    if(pontuacao>5){
                        break;
                    }
                    verificar= new VerificarLinhaHorizontal();
                    System.out.println("Verificando horizontal");
                    pontuacao+=verificar.verificar(matriz, linha, coluna);
                    if(pontuacao>5){
                        break;
                    }
                    verificar= new VerificarLinhaVertical();
                    System.out.println("Verificando vertical");
                    pontuacao+=verificar.verificar(matriz, linha, coluna);
                    if(pontuacao>5){
                        break;
                    }
                    verificar= new VerificarLinhaDiagonal();
                    System.out.println("Verificando diagonal");
                    pontuacao+=verificar.verificar(matriz, linha, coluna);
                    if(pontuacao>5){
                        break;
                    }
                }
            }
        }
        return pontuacao;
    }
//     private void verificarFloracao() {
//        for (int linha = 0; linha < 5; linha++) {
//            for (int coluna = 0; coluna < 5; coluna++) {
//                if (tabuleiroGerenciador[coluna][linha].getClass() == NenufarClaroComFlorAmarela.class
//                        || tabuleiroGerenciador[coluna][linha].getClass() == NenufarClaroComFlorRosa.class
//                        || tabuleiroGerenciador[coluna][linha].getClass() == NenufarEscuroComFlorAmarela.class
//                        || tabuleiroGerenciador[coluna][linha].getClass() == NenufarEscuroComFlorRosa.class) {
//                    verificarQuadrado(coluna, linha);
//                    verificarLinhaHori(coluna, linha);
//                    verificarLinhaVert(coluna, linha);
//                    verificarLinhaDiag(coluna, linha);
//
//                }
//            }
//
//        }
//    }
//
//    private void verificarQuadrado(int coluna, int linha) {
//
//        try {
//            if (tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna + 1][linha].getClass()
//                    && tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna][linha + 1].getClass()
//                    && tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna + 1][linha + 1].getClass()) {
//                temQuadrado = true;
//            }
//        } catch (Exception ex) {
//            System.out.println("não tem quadrado");
//        }
//
//    }
//
//    private void verificarLinhaHori(int coluna, int linha) {
//        try {
//            while (tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna + 1][linha].getClass()) {
//                formacaoDeFlores.add(tabuleiroGerenciador[coluna][linha]);
//                coluna++;
//            }
//        } catch (ArrayIndexOutOfBoundsException ex) {
//        }
//        if (formacaoDeFlores.size() == 4) {
//            temLinhaHorVer4 = true;
//        } else if (formacaoDeFlores.size() == 5) {
//            temLinha5 = true;
//        }
//        formacaoDeFlores.clear();
//    }
//
//    private void verificarLinhaVert(int coluna, int linha) {
//        try {
//            while (tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna][linha + 1].getClass()) {
//                formacaoDeFlores.add(tabuleiroGerenciador[coluna][linha]);
//                linha++;
//            }
//        } catch (ArrayIndexOutOfBoundsException ex) {
//
//        }
//
//        System.out.println("quantidade na formação:" + formacaoDeFlores.size());
//        if (formacaoDeFlores.size() == 4) {
//            temLinhaHorVer4 = true;
//        } else if (formacaoDeFlores.size() == 5) {
//            temLinha5 = true;
//        }
//        formacaoDeFlores.clear();
//    }
//
//    private void verificarLinhaDiag(int coluna, int linha) {
//
//        try {
//            while (tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna + 1][linha + 1].getClass()) {
//                formacaoDeFlores.add(tabuleiroGerenciador[coluna][linha]);
//                coluna++;
//                linha++;
//            }
//        } catch (ArrayIndexOutOfBoundsException ex) {
//        }
//
//        if (formacaoDeFlores.size() == 4) {
//            temLinhaHorVer4 = true;
//        } else if (formacaoDeFlores.size() == 5) {
//            temLinha5 = true;
//        }
//        formacaoDeFlores.clear();
//
//    }
}
