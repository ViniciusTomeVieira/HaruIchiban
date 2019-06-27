package Observer;
/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public interface Observador {

	void mudouTabuleiro();


	void fimDeJogo(String msgErro);
        void notificarFlorEscolhida();

    public void notificarJogadorDaVezAlterado();

    public void notificarJuniorSenior();

    public void notificarTabuleiroAlterado();

    public void notificarFlorEscolhidaParaMover();

    public void notificarErro();

    public void notificarRodadaEncerado();

    public void notificarJogoEncerado(String vencedor);

    public void notificarEmpateComparacao();
}
