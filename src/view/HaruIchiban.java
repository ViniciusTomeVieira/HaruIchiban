package view;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import control.GerenciadorJogo;
import control.GerenciadorJogoImpl;
import control.Observador;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class HaruIchiban extends JFrame implements Observador {

    private static final long serialVersionUID = 1L;

    //Botoes mao do jogador        
    private JButton jbCarta1;
    private JButton jbCarta2;
    private JButton jbCarta3;

    //Textos mao do jogador
    private JLabel jlCarta1;
    private JLabel jlCarta2;
    private JLabel jlCarta3;

    private GridBagLayout layout;
    private GridBagConstraints constraints;

    class HeroiTableModel extends AbstractTableModel {

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

    class FloresTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public int getRowCount() {
            return 2;
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

    class HeroiRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);

            return this;
        }

    }

    private GerenciadorJogo gerenciador;
    private JTable Tbtabuleiro;
    private JTable TbFlores;

    public HaruIchiban() throws Exception {
        this.gerenciador = new GerenciadorJogoImpl();
        this.gerenciador.inicializarTabuleiro();
        this.gerenciador.addObservador(this);

        setTitle("HaruIchiban");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        pack();

    }

    private void initComponents() {

        // criar o tabuleiro e seus componentes
        Tbtabuleiro = new JTable();
        Tbtabuleiro.setModel(new HeroiTableModel());
        for (int x = 0; x < Tbtabuleiro.getColumnModel().getColumnCount(); x++) {
            Tbtabuleiro.getColumnModel().getColumn(x).setWidth(200);
            Tbtabuleiro.getColumnModel().getColumn(x).setMinWidth(200);
            Tbtabuleiro.getColumnModel().getColumn(x).setMaxWidth(200);
        }
        Tbtabuleiro.setRowHeight(200);
        Tbtabuleiro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Tbtabuleiro.setShowGrid(false);
        Tbtabuleiro.setIntercellSpacing(new Dimension(0, 0));
        Tbtabuleiro.setDefaultRenderer(Object.class, new HeroiRenderer());

        Tbtabuleiro.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    //gerenciador.pressTecla( e.getKeyCode() );
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.toString());
                }
            }

        });

        add(Tbtabuleiro, CENTER);

        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(2, 1, 0, 0));

        // criar os botoes de radio / ToDo: criar a parte das cartas do usuario
        JPanel jpMensagem = new JPanel();
        jtaMensagem = new JTextArea(2, 2);
        jtaMensagem.setText("Xesquedeleeeeeeeeeeeeeeeeeeeee");
        jtaMensagem.setEditable(false);
        jtaMensagem.setLineWrap(true);
        jtaMensagem.setFont(new Font("Calibri", 1, 20));
        jpMensagem.add(jtaMensagem, CENTER);

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
        //cartaClicadaAction.actionPerformed(new ActionEvent(jrMontanha, ActionEvent.ACTION_PERFORMED, jrMontanha.getActionCommand()));

        jp.add(jpMensagem);

        // criar mão do jogador
        JPanel jpMao = new JPanel();
        //jpMao.setLayout(new GridLayout(3,2));

        layout = new GridBagLayout();
        constraints = new GridBagConstraints();

        // layout.setConstraints(jpMao, constraints);
        jpMao.setLayout(layout);

        //Criação dos botões
        jbCarta1 = new JButton();
        jbCarta1.setIcon(new ImageIcon("imagens/florRosa.png"));
        jbCarta2 = new JButton();
        jbCarta2.setIcon(new ImageIcon("imagens/florAmarela.png"));
        jbCarta3 = new JButton();
        jbCarta3.setIcon(new ImageIcon("imagens/florRosa.png"));

        //Criação das labels
        jlCarta1 = new JLabel();
        jlCarta1.setText("1");
        jlCarta2 = new JLabel();
        jlCarta2.setText("2");
        jlCarta3 = new JLabel();
        jlCarta3.setText("3");

        jbCriar = new JButton("Criar");
        jbCriar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    //controle.run();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.toString());
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        jpMao.add(jbCarta1, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 100;
        jpMao.add(jlCarta1, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        jpMao.add(jbCarta2, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 100;
        jpMao.add(jlCarta2, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        jpMao.add(jbCarta3, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 100;
        jpMao.add(jlCarta3, constraints);

        jpMao.setBackground(Color.white);
        jpMao.setSize(300, getContentPane().getHeight());
        jp.setBackground(Color.white);
        jp.add(jpMao);
        add(jp, WEST);

        //Criação da panel da direita
        JPanel jpDireita = new JPanel();
        jpDireita.setLayout(new GridLayout(3, 1, 0, 0));

        //Criação do placar
        JPanel jpPlacar = new JPanel();
        JLabel jlPlacar = new JLabel();
        jlPlacar.setText("2x2");

        jpPlacar.add(jlPlacar);
        jpDireita.add(jpPlacar);
        TbFlores = new JTable();
        TbFlores.setModel(new FloresTableModel());
        jpDireita.add(TbFlores);

        add(jpDireita, BorderLayout.EAST);

    }

    private JRadioButton jrMontanha;
    private JRadioButton jrAgua;
    private JButton jbCriar;
    private JTextArea jtaMensagem;

    public static void main(String[] args) {
        try {
            HaruIchiban d = new HaruIchiban();
            d.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

    }

    @Override
    public void iniciouJogo() {
        jbCriar.setEnabled(false);
        jrMontanha.setEnabled(false);
        jrAgua.setEnabled(false);
    }

    @Override
    public void mudouTabuleiro() {
        Tbtabuleiro.repaint();
    }

    @Override
    public void fimDeJogo(String msgErro) {
        JOptionPane.showMessageDialog(null, msgErro);
        System.exit(0);
    }

}
