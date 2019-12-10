package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
* Esta classe permite a visualização do ficheiro excel numa interface gráfica
* desenhada, bem como a possibilidade de escrita de regras
*
* @author  Pedro Fonseca, Fábio Cardoso, Filipa Gomes, Inês Gomes, Sofia Pérsio, Marco Silva
*/
public class Interface3 {
	private JFrame frame;
	private String limlocLer="";
	private String limatfdLer="";
	private String limcycloLer="";
	private String limlaaLer="";
	private JButton and, smaller, telse, or, equal, tif, ttrue, tfalse, bigger, tso, islongmethod, isfeatureenvy, loc, cyclo, atfd, laa, open, close;
	private JTextField screen, limloc, limlaa, limcyclo, limatfd;
	ArrayList <Regra> regras = new ArrayList<Regra>() ;
	ArrayList <Record> records = new ArrayList<Record>() ;	
	String main ;
	String THENfinal;
	String ELSEfinal;
	String string;

	/**
	 * startInstance Interface3
	 * criar a interface com título e dimensão
	 * @param string 
	 * @param records 
	 */
	public Interface3 (String string, ArrayList<Record> records) {
		frame = new JFrame ("Aplicação de avaliaçao de código");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(500,300);
		addFrameContent();
		this.string=string;
		this.records=records;
	}	
	
	/**
	 * Criação de todos os paineis necessários para a janela
	 * de introdução dos parâmetros pelo utilizador
	 */
	
	public void addFrameContent() {
		//painel onde se lê o excell
		JPanel centerPanel = new JPanel ();
		centerPanel.setLayout(new FlowLayout());
		frame.add(ExcelDisplay.getInstance().getSp(),BorderLayout.NORTH); //onde entra  excell (está criado o espaço) com scroll
		
		JLabel aviso = new JLabel ("Valores da Tabela selecionada");
		centerPanel.add(aviso,BorderLayout.NORTH);
		
		JPanel factors = new JPanel();
		factors.setLayout(new BorderLayout());
		frame.add(factors);

		JPanel thresholdsPanel = new JPanel();
		thresholdsPanel.setLayout(new GridLayout(4,3));
		factors.add(thresholdsPanel, BorderLayout.CENTER);
		
		JPanel calculator = new JPanel();  //onde entra a calculadora (está criado o espaço)
		calculator.setLayout(new BorderLayout());
		frame.add(calculator, BorderLayout.EAST);
		
		// Organização do Painel da "Calculadora" - mais tarde poderá ter a sua propria classe
		calculator.setLayout(new BorderLayout());
		
		JPanel cpainel = new JPanel();
		
		
		/**
		 * Criação do mostrador para introduzir a calculadora 
		 * botões para fácil utilização e adição dos parâmetros e condições
		 */
		
		//Mostrador
		screen = new JTextField();   
		calculator.add(screen, BorderLayout.NORTH);
		
		// Painel com os termos das regras
		JPanel terms = new JPanel();
		terms.setLayout(new GridLayout(3,4));
		
		and = new JButton(" AND");
		and.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + and.getText();
				screen.setText(enterTerm);
			}
		});
		
		smaller = new JButton(" <");
		smaller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String metric = null;
				if(screen.getText().length() > 0) {
					String regra = screen.getText();
					ArrayList<String> arr = new ArrayList<String>(Arrays.asList(regra.split(" ")));
					metric = arr.get(arr.size() -1);
					
					if(metric.equals("CYCLO")) {
						String enterTerm = screen.getText() + smaller.getText() + " " + limcycloLer;
						
						screen.setText(enterTerm);
					}
					if(metric.equals("LAA")) {
						String enterTerm = screen.getText() + smaller.getText() + " " + limlaaLer;
						screen.setText(enterTerm);
					}
						
					if(metric.equals("ATFD")) {
						String enterTerm = screen.getText() + smaller.getText() + " " + limatfdLer;
						screen.setText(enterTerm);
					}
						
					if(metric.equals("LOC")) {
						String enterTerm = screen.getText() + smaller.getText() + " " + limlocLer;
						screen.setText(enterTerm);
					}
				}
				
				
			}
		});
		
		telse = new JButton(" ELSE");
		telse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + telse.getText();
				screen.setText(enterTerm);
			}
		});
		
		
		or = new JButton(" OR");
		or.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + or.getText();
				screen.setText(enterTerm);
			}
		});
		
		equal = new JButton(" =");
		equal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + equal.getText();
				screen.setText(enterTerm);
			}
		});
		
		ttrue = new JButton(" TRUE");     //double t propositado
		ttrue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTermo = screen.getText() + ttrue.getText();
				screen.setText(enterTermo);
			}
		});
		
		tif = new JButton(" IF");    //botao if
		tif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + tif.getText();
				screen.setText(enterTerm);
			}
		});
		
		tfalse = new JButton(" FALSE");
		tfalse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + tfalse.getText();
				screen.setText(enterTerm);
			}
		});
		
		bigger = new JButton(" >");
		bigger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String metric = null;
				if(screen.getText().length() > 0) {
					String regra = screen.getText();
					ArrayList<String> arr = new ArrayList<String>(Arrays.asList(regra.split(" ")));
					metric = arr.get(arr.size() -1);
					
					if(metric.equals("CYCLO")) {
						String enterTerm = screen.getText() + bigger.getText() + " " + limcycloLer;
						
						screen.setText(enterTerm);
					}
					if(metric.equals("LAA")) {
						String enterTerm = screen.getText() + bigger.getText() + " " + limlaaLer;
						screen.setText(enterTerm);
					}
						
					if(metric.equals("ATFD")) {
						String enterTerm = screen.getText() + bigger.getText() + " " + limatfdLer;
						screen.setText(enterTerm);
					}
						
					if(metric.equals("LOC")) {
						String enterTerm = screen.getText() + bigger.getText() + " " + limlocLer;
						screen.setText(enterTerm);
					}
				}
								
								
				
					
					
			}
		});
		
		tso = new JButton(" THEN");					//'entao'
		tso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + tso.getText();
				screen.setText(enterTerm);
			}
		});
		
		isfeatureenvy = new JButton(" is_feature_envy");
		isfeatureenvy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm =screen.getText() + isfeatureenvy.getText();
				screen.setText(enterTerm);
			}
		});
		
		islongmethod = new JButton(" is_long_method");
		islongmethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + islongmethod.getText();
				screen.setText(enterTerm);
			}
		});
		
		loc = new JButton(" LOC");
		loc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + loc.getText();
				screen.setText(enterTerm);
			}
		});
		
		cyclo = new JButton(" CYCLO");
		cyclo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + cyclo.getText();
				screen.setText(enterTerm);
			}
		});
		
		atfd = new JButton(" ATFD");
		atfd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + atfd.getText();
				screen.setText(enterTerm);
			}
		});
		
		laa = new JButton(" LAA");
		laa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + laa.getText();
				screen.setText(enterTerm);
			}
		});
		
		open = new JButton(" (");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + open.getText();
				screen.setText(enterTerm);
			}
		});
		
		close = new JButton(" )");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterTerm = screen.getText() + close.getText();
				screen.setText(enterTerm);
			}
		});
		
		/**
		 * adicionar os botões ao painel de vizualização
		 */
		
		terms.add(and);
		terms.add(smaller);
		terms.add(telse);
		terms.add(or);
		terms.add(equal);
		terms.add(ttrue);
		terms.add(tif);
		terms.add(tfalse);
		terms.add(bigger);
		terms.add(tso);
		terms.add(isfeatureenvy);
		terms.add(islongmethod);
		terms.add(loc);
		terms.add(cyclo);
		terms.add(atfd);
		terms.add(laa);
		terms.add(open);
		terms.add(close);
		
		cpainel.add(terms);
		
		
		/**
		 * criação de painel com as ações do utilizador
		 * opçoes de submeter ou apagar
		 */
		//Painel com as ações
		JPanel actions = new JPanel();
		actions.setLayout(new GridLayout(2,1));
		JButton submit = new JButton("Submeter");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String regra = screen.getText();
				String posicao[] = regra.split(" IF ");
				String posicao1[] = posicao[1].split(" THEN ");
				String temporary1= posicao1[0].replace("( ","");
				main = temporary1.replace(" )","");
				String posicao2 [] = posicao1[1].split(" ELSE ");
				String temporary2= posicao2[0].replace("( ", "");
				THENfinal= temporary2.replace(" )", ""); 
				String temporary3= posicao2[1].replace("( ", "");
				ELSEfinal= temporary3.replace(" )", "");
				construcaoRegra(main, THENfinal, ELSEfinal);
			}

			private void construcaoRegra(String main, String THENfinal, String ELSEfinal) {
				if(main.contains("AND") && main.contains("OR")) {
					char c1 = 'O';
					char c2 = 'R';
					int indice = 0;
					for(int i=0; i!=main.length();i++) {
						if(main.charAt(i)==c1 && main.charAt(i+1)==c2) {
							indice=i;
							break;
						}
					}
					String main1 = main.substring(0, indice);
					String main2 = main.substring(indice+3, main.length());
					Regra r1 = new Regra(main1,THENfinal,ELSEfinal);
					Regra r2 = new Regra(main2,THENfinal,ELSEfinal);
					regras.add(r1);
					regras.add(r2);
					
				} else {
					Regra r3 = new Regra(main,THENfinal,ELSEfinal);
					regras.add(r3);
				}
			}
		});
		
		
		JButton clean = new JButton("Apagar");
		clean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(screen.getText().length() > 0) {
							
					String regra = screen.getText();
					
					ArrayList<String> arr = new ArrayList<String>(Arrays.asList(regra.split(" ")));

					if(arr!=null) {
						if(arr.size() > 0) {
							arr.remove(arr.size() -1);
						}
					}
					
					String apagado = String.join(" ", arr);

					screen.setText(apagado);
				}
				
			}
		});
		
		JButton finalizar = new JButton("Finalizar");
		finalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					ResultDisplay rd = new ResultDisplay(string, regras, records);
				} catch(Exception e1) {
					
				}
			}
		});
		
		actions.add(submit);
		actions.add(clean);
		actions.add(finalizar);
		cpainel.add(actions);
		calculator.add(cpainel, BorderLayout.CENTER);
			
		
		JLabel inserir = new JLabel ("Insira os valores de limiares pretendidos");
		factors.add(inserir,BorderLayout.NORTH);
		
		JLabel labelloc = new JLabel("LOC");
		JLabel labelcyclo = new JLabel("CYCLO");
		JLabel labelatfd = new JLabel("ATFD");
		JLabel labellaa = new JLabel("LAA");
		
		limloc = new JTextField();
		limloc.setText("0");
		limlocLer="0";
		limcyclo = new JTextField();
		limcyclo.setText("0");
		limcycloLer="0";
		limatfd = new JTextField();
		limatfd.setText("0");
		limatfdLer="0";
		limlaa = new JTextField();
		limlaa.setText("0");
		limlaaLer="0";
		
		JButton guardarLOC =new JButton("Guardar");
		guardarLOC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limlocLer=limloc.getText();
			}
		});
		
		JButton guardarCYCLO=new JButton("Guardar");
		guardarCYCLO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limcycloLer=limcyclo.getText();
			}
		});
		
		JButton guardarATFD=new JButton("Guardar");
		guardarATFD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limatfdLer=limatfd.getText();
			}
		});
		
		
		JButton guardarLAA=new JButton("Guardar");
		guardarLAA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limlaaLer=limlaa.getText();
			}
		});
		
		
		/**
		 * introdução de todos os botões nas respetivas posições
		 */
		thresholdsPanel.add(labelloc,0);
		thresholdsPanel.add(limloc,1);
		thresholdsPanel.add(guardarLOC,2);
		thresholdsPanel.add(labelcyclo,3);
		thresholdsPanel.add(limcyclo,4);
		thresholdsPanel.add(guardarCYCLO,5);
		thresholdsPanel.add(labelatfd,6);
		thresholdsPanel.add(limatfd,7);
		thresholdsPanel.add(guardarATFD,8);
		thresholdsPanel.add(labellaa,9);
		thresholdsPanel.add(limlaa,10);
		thresholdsPanel.add(guardarLAA,11);
				
		limloc.setSize(20,20);
		limcyclo.setSize(20,20);
		limatfd.setSize(20,20);
		limlaa.setSize(20,20);
		
		frame.setVisible(true);
	}
}
		
