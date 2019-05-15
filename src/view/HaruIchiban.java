package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import control.GerenciadorJogo;
import control.GerenciadorJogoImpl;
import Observer.Observador;
import command.ClicouNoTabuleiroCommand;
import command.CommandInvoker;
import command.EscolherFloresDeckCommand;
import command.EscolherFloresParaJogarCommand;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class HaruIchiban extends JFrame implements Observador, ActionListener {

    private static final long serialVersionUID = 1L;

    //Botoes mao do jogador        
    private JButton jbCoachar;

    //Menu superior
    private JMenuBar menuBarSuperior;
    private JMenu menuMenu;
    private JMenuItem miNovo, miSair;

    //Mensagens no canto superior esquerdo
    private JTextArea jtaMensagem;

    //Textos mao do jogador
    private JTextArea jtaCarta1;
    private JTextArea jtaCarta2;
    private JTextArea jtaCarta3;
    


    //Layouts
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    //Mecanica do jogo
    private GerenciadorJogo gerenciador;

    //Tabelas
    private JTable Tbtabuleiro;
    private JTable TbFlores;
    private JTable TbMao;
    
    //Placar
    private JLabel jlPlacar;
    
    //Command
    private final CommandInvoker invk = new CommandInvoker();

    
//Notificadores    
    @Override
    public void notificarFlorEscolhida() {
        repaint();
    }

    @Override
    public void notificarJogadorDaVezAlterado() {
            repaint();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(HaruIchiban.class.getName()).log(Level.SEVERE, null, ex);
//        }
    
        jtaMensagem.setText(gerenciador.getJogadorDaVez().getNome() + gerenciador.getMensagemAtual());
    }

    @Override
    public void notificarJuniorSenior() {
        JOptionPane.showMessageDialog(rootPane, "Jogador 1: "+ gerenciador.getJogador1().getJuniorSenior()+"\n Jogador 2: "+ gerenciador.getJogador2().getJuniorSenior());
        jtaMensagem.setText(gerenciador.getJogadorDaVez().getNome() + gerenciador.getMensagemAtual());

    }

    @Override
    public void notificarTabuleiroAlterado() {
        repaint();
        jtaMensagem.setText(gerenciador.getJogadorDaVez().getNome() + gerenciador.getMensagemAtual());

    }

    @Override
    public void notificarFlorEscolhidaParaMover() {
        gerenciador.setFlorEscolhidaParaMover(JOptionPane.showOptionDialog(rootPane, gerenciador.getJogadorDaVez().getNome() + " escolha uma direcao para mover o nenufar", "Escolha de direcao", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, gerenciador.getOpcoesDeMover(), null));
        gerenciador.juniorMovePecas();
    }

    @Override
    public void notificarErro() {
        JOptionPane.showMessageDialog(rootPane, gerenciador.getMensagemErro());
    }

    @Override
    public void notificarRodadaEncerado() {
        JOptionPane.showMessageDialog(rootPane,"Rodada encerrada");
        jlPlacar.setText(gerenciador.getJogador1().getPontuacao() + " x " + gerenciador.getJogador2().getPontuacao());
        gerenciador.novaRodada();
        repaint();
        
    }

    @Override
    public void notificarJogoEncerado(String vencedor) {
        JOptionPane.showMessageDialog(rootPane, vencedor);
    }

    @Override
    public void notificarEmpateComparacao() {
        JOptionPane.showMessageDialog(rootPane, "Empate!!!");
    }


    // Modelo de tabela visual do tabuleiro
    class TabuleiroTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public int getRowCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int row, int col) {
            try {
                return gerenciador.getPeca(col, row);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }

    }
    // Renderizador de celulas do tabuleiro

    class TabuleiroRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);

            return this;
        }

    }

    // Modelo de tabela visual das cartas do jogador
    class FloresTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int row, int col) {
            try {
                return gerenciador.getFlor(col, row);
            } catch (Exception e) {
                return null;
            }
        }

    }

    // Renderizador de celulas do tabuleiro  
    class FloresRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);

            return this;
        }

    }
    // Modelo de tabela visual das cartas do jogador
    class MaoTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount() {
            return 2;
        }
        
        

        @Override
        public int getRowCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int row, int col) {
            try {

                if (gerenciador.getFlorMao(col, row) == null) {
                    System.out.println("Ta nulo essa caralhas");
                }
                return gerenciador.getFlorMao(col, row);
            } catch (Exception e) {
                return null;
            }
        }

    }

    // Renderizador de celulas do tabuleiro  
    class MaoRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);

            return this;
        }

    }

    //Construtor da view
    public HaruIchiban() throws Exception {

        //Inicia gerenciador e faz algumas operações
        this.gerenciador = GerenciadorJogoImpl.getInstance();
        this.gerenciador.inicializarTabuleiro(JOptionPane.showOptionDialog(rootPane, "Escolha a forma do tabuleiro", "FORMA DO TABULEIRO", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, gerenciador.getOpcoesDeTabuleiro(), null)); // Vai mudar e receber novas formas de inicio(Builder)       
        gerenciador.inicializarJogadores();
        gerenciador.setCorDasFlores(JOptionPane.showOptionDialog(rootPane, "Jogador 1: escolha sua cor", "Escolha de cor", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, gerenciador.getOpcoesDeFlor(), null));
        gerenciador.fluxoJogo();
        gerenciador.setJogadorDaVez(gerenciador.getJogador1());
        this.gerenciador.getJogador1().setNome("Jogador 1");
        this.gerenciador.getJogador2().setNome("Jogador 2");
        gerenciador.getJogador1().setFlores(this.gerenciador.inicializarFlores(gerenciador.getJogador1().getFlores(), gerenciador.getJogador1()));
        gerenciador.getJogador2().setFlores(this.gerenciador.inicializarFlores(gerenciador.getJogador2().getFlores(), gerenciador.getJogador2()));
        this.gerenciador.addObservador(this);
        this.gerenciador.setFlorDaVez(gerenciador.getJogador1().getFlores());
        this.gerenciador.setMaoDaVez(gerenciador.getJogador1().getMao());



        //Configura a view
        setTitle("HaruIchiban");
        setBounds(200, 200, 900, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());

       
        initComponents();
        pack();

    }

    //Inicia todos os componentes da interface
    private void initComponents() {

        //Inicia e adiciona os menus
        menuBarSuperior = new JMenuBar();
        menuMenu = new JMenu("Menu");
        miNovo = new JMenuItem("Novo jogo");
        miSair = new JMenuItem("Sair");
        miSair.addActionListener(this);
        miNovo.addActionListener(this);
        menuMenu.add(miNovo);
        menuMenu.add(miSair);
        menuBarSuperior.add(menuMenu);
        setJMenuBar(menuBarSuperior);

        //Inicia os componentes de layout
        constraints = new GridBagConstraints();
        layout = new GridBagLayout();

        // criar o tabuleiro e seus componentes
        Tbtabuleiro = new JTable();
        Tbtabuleiro.setModel(new TabuleiroTableModel());
        for (int x = 0; x < Tbtabuleiro.getColumnModel().getColumnCount(); x++) {
            Tbtabuleiro.getColumnModel().getColumn(x).setWidth(100);
            Tbtabuleiro.getColumnModel().getColumn(x).setMinWidth(100);
            Tbtabuleiro.getColumnModel().getColumn(x).setMaxWidth(100);
        }
        Tbtabuleiro.setRowHeight(100);
        Tbtabuleiro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Tbtabuleiro.setShowGrid(false);
        Tbtabuleiro.setGridColor(Color.red);
        Tbtabuleiro.setIntercellSpacing(new Dimension(0, 0));
        Tbtabuleiro.setDefaultRenderer(Object.class, new TabuleiroRenderer());

        Tbtabuleiro.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               invk.add(new ClicouNoTabuleiroCommand(gerenciador ,Tbtabuleiro.rowAtPoint(e.getPoint()), Tbtabuleiro.columnAtPoint(e.getPoint())));
               invk.execute(Tbtabuleiro.rowAtPoint(e.getPoint()), Tbtabuleiro.columnAtPoint(e.getPoint()));
            }   

        });

        //Adiciona o tabuleiro no centro da tela
        add("Center", Tbtabuleiro);

        ActionListener cartaClicadaAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    //controle.setTipoHeroi(event.getActionCommand());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.toString());
                }
            }
        };
        

        // criar mão do jogador       
        // criar os botoes de radio / ToDo: criar a parte das cartas do usuario
        JPanel jpMensagem = new JPanel();
        jpMensagem.setLayout(layout);
        jtaMensagem = new JTextArea(2, 2);
        jtaMensagem.setText(gerenciador.getJogadorDaVez().getNome() + "  pegue suas cartas");
        jtaMensagem.setEditable(false);
        jtaMensagem.setLineWrap(true);
        jtaMensagem.setFont(new Font("Calibri", 1, 20));

        //Adiciona a mensagem no panel da mensagem
        constraints.gridx = 1;
        constraints.gridy = 0;
        jpMensagem.add(jtaMensagem, constraints);

        //Inicia o panel da esquerda
        JPanel panelEsquerda = new JPanel();
        panelEsquerda.setLayout(layout);




        TbMao = new JTable();
        TbMao.setModel(new MaoTableModel());
        for (int x = 0; x < TbMao.getColumnModel().getColumnCount(); x++) {
            TbMao.getColumnModel().getColumn(x).setWidth(100);
            TbMao.getColumnModel().getColumn(x).setMinWidth(100);
            TbMao.getColumnModel().getColumn(x).setMaxWidth(100);
        }
        TbMao.setRowHeight(100);
        TbMao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TbMao.setShowGrid(false);
        TbMao.setIntercellSpacing(new Dimension(0, 0));
        TbMao.setDefaultRenderer(Object.class, new MaoRenderer());
        TbMao.setOpaque(false);
        ((MaoRenderer)TbMao.getDefaultRenderer(Object.class)).setOpaque(false);
        TbMao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                invk.add(new EscolherFloresParaJogarCommand(gerenciador ,TbMao.rowAtPoint(e.getPoint()), TbMao.columnAtPoint(e.getPoint())));
               invk.execute(TbMao.rowAtPoint(e.getPoint()), TbMao.columnAtPoint(e.getPoint()));

            }

        });

        //Cria a panel da mao do jogador
         constraints.gridx = 0;
        constraints.gridy = 1;
        panelEsquerda.add(TbMao,constraints);

        // Faz a adição das cartas na mão do jogador


        //Criação da panel da direita
        JPanel panelDireita = new JPanel();
        panelDireita.setLayout(layout);


        //Criação do placar
        JPanel jpPlacar = new JPanel();
        jlPlacar = new JLabel();
        jlPlacar.setText(gerenciador.getJogador1().getPontuacao() + " x " + gerenciador.getJogador2().getPontuacao());
        jlPlacar.setFont(new Font("Calibri", 1, 20));

        //Adiciona o placar no painel do placar
        jpPlacar.add(jlPlacar);

       
        //Table de flores do lado direito
        TbFlores = new JTable();
        TbFlores.setModel(new FloresTableModel());
        for (int x = 0; x < TbFlores.getColumnModel().getColumnCount(); x++) {
            TbFlores.getColumnModel().getColumn(x).setWidth(100);
            TbFlores.getColumnModel().getColumn(x).setMinWidth(100);
            TbFlores.getColumnModel().getColumn(x).setMaxWidth(100);
        }
        TbFlores.setRowHeight(100);
        TbFlores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TbFlores.setShowGrid(false);
        TbFlores.setIntercellSpacing(new Dimension(0, 0));
        
        TbFlores.setDefaultRenderer(Object.class, new FloresRenderer());
        TbFlores.setOpaque(false);
        ((FloresRenderer)TbFlores.getDefaultRenderer(Object.class)).setOpaque(false);
        TbFlores.setGridColor(Color.red);
        TbFlores.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               invk.add(new EscolherFloresDeckCommand(gerenciador ,TbFlores.rowAtPoint(e.getPoint()), TbFlores.columnAtPoint(e.getPoint())));
               invk.execute(TbFlores.rowAtPoint(e.getPoint()), TbFlores.columnAtPoint(e.getPoint()));
            }

        });

        jbCoachar = new JButton("Coachar");

        //Adiciona o painel placar, tabela de flores e o botao coachar no painel da direita
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 10;
        panelDireita.add(jpPlacar, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0;
        panelDireita.add(TbFlores, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 10;
        panelDireita.add(jbCoachar, constraints); // Botao coachar

        //Adiciona o painel de mensagem e o painel da mao no painel da esquerda
        constraints.gridx = 0;
        constraints.gridy = 0;
        panelEsquerda.add(jpMensagem, constraints);


        //Adiciona o painel da esquerda na esquerda da tela
        add("West", panelEsquerda);
        //Adiciona o painel da direita na direita da tela
        add("East", panelDireita);

    }

    //Metodo MAIN
    public static void main(String[] args) {
        try {
            HaruIchiban d = new HaruIchiban();
            d.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    

    //Atualizar o tabuleiro principal
    @Override
    public void mudouTabuleiro() {
        Tbtabuleiro.repaint();
    }

    //Faz as ações do menu superior
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == miNovo) {
            //Command
        }
        if (e.getSource() == miSair) {
            System.exit(0);
        }
    }

    //Metodo executado ao final do jogo
    @Override
    public void fimDeJogo(String msgErro) {
        JOptionPane.showMessageDialog(null, msgErro);
        System.exit(0);
    }

    
    
    

}
