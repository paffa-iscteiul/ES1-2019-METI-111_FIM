package Projeto_METI_111_FIM.Projeto_METI_111_FIM;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame ;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


/**
 * Construtor da class 
 *Nao recebe nenhum atributo 
 *faz criaçao da frame inicial
 *define o tamanho da mesma 
 *invoca metodo addFrameContent para adicionar os restantes elementos da interface 
 *
 */
public class Interface1 {
	private JFrame frame;
	Openfile of = new Openfile(this);
	private boolean fileSelected = false;
	private String string = "";
	private JPanel centerPanel= new JPanel();
	JButton finish;
	JButton browser;
	
	public Interface1 () {
		frame = new JFrame ("Aplicação de avaliaçao de código");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setSize(400,100);
		addFrameContent();
	}
	/** addFrameContent() é um metodo que vai criar o paineis novos as Labels é neste 
	 * metodo que é chamada a class openfile para carregar para se procurar o ficheiro
	 * e a interface 3 
	 *
	 */

	private void addFrameContent() {
		JLabel alert = new JLabel ("Clique no botão browser para escolher o ficheiro Excel");
		frame.add(alert,BorderLayout.NORTH);
		
		//painel sul para o botao nao ocupar mt espaco
		JPanel southPanel= new JPanel ();
		southPanel.setLayout(new FlowLayout());
		frame.add(southPanel,BorderLayout.SOUTH);
		
		// painel centro
		centerPanel.setLayout(new FlowLayout());
		frame.add(centerPanel,BorderLayout.CENTER);
		
		
		//botao de executa a janela
		browser = new JButton ("Browser");
		southPanel.add(browser, BorderLayout.WEST);
		
		//acao do botao invoca o objecto da outra class para se ir buscar o ficheiro 
		browser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					of.Browser();
					JLabel fille = new JLabel("ficheiro carregado:"+ of.Excelfile.getName());
					centerPanel.add(fille);
					frame.setSize(400,111);
				}catch(Exception a) {a.printStackTrace();}
			}
	});
		
		finish = new JButton ("Finish");
		southPanel.add(finish, BorderLayout.CENTER);
		finish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileSelected) {
					JOptionPane.showMessageDialog(null, "Avançar para nova janela?");
					Interface3 interface3 = new Interface3(string, of.getRecords());
					frame.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Tem de selecionar algum ficheiro");
				}
				
			}
	});
		
	}
	/** fileConfirmed() é um metodo que vai vereficar se o ficheiro esta escolhido
	 *  @param s nome do ficheiro escolhido
	 * @param string é o nome completo do ficheiro  
	 *
	 */
	public void fileConfirmed (String s, String string) {
		JOptionPane.showMessageDialog(null, "O ficheiro " + s + " foi carregado corretamente. Carregue Finish para continuar");
		fileSelected=true;
		this.string=string;
	}
	
	
	public static void main (String[] args) {
		Interface1 i1 = new Interface1 ();
	}
}
