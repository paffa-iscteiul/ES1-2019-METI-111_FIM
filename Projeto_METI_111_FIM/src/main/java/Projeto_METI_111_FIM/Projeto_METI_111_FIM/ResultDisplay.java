package Projeto_METI_111_FIM.Projeto_METI_111_FIM;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
* Esta classe permite a visualização dos resultados da deteção de defeitos
*
* @author  Pedro Fonseca, Fábio Cardoso, Filipa Gomes, Inês Gomes, Sofia Pérsio, Marco Silva
*/
public class ResultDisplay {
	
	private JTable table;
	private JScrollPane sp;
	private JPanel panel;
//	private JLabel labelTable;    //acrescentei filipa
//	private JPanel results;
	private JLabel labelResults;  //acrescentei filipa
//	private JPanel panelResults;
	private JFrame frame;
	private JFrame f;
	private String [] columnTitles;
	private String [] [] data;
	private String string;
	private ArrayList<Regra> regras = new ArrayList<Regra>();
	private ArrayList<Regra> regras2 = new ArrayList<Regra>();
	private ArrayList<Record> records = new ArrayList<Record>();
	private JTable tt;
	private JList b;
	private JButton button;
	
	
	/**
	 * Construtor da classe
	 * Recebe o nome do ficheiro Excel, lista de regras definidas pelo utilizador, vetor de registos
	 * @param string nome ficheiro
	 * @param regras lista de regras
	 * @param records lista de registos
	 * @param selectedValue caminho selecionado para teste unitário
	 * @throws IOException
	 */
	public ResultDisplay(String string, ArrayList<Regra> regras, ArrayList<Record> records,String selectedValue) {
		try {
			inic(string,regras,records,selectedValue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Outro construtor da classe
	 * Recebe o nome do ficheiro Excel, lista de regras definidas pelo utilizador e lista de registos
	 * @param string nome ficheiro
	 * @param regras lista de regras
	 * @param records lista de registos
	 * @throws IOException
	 */
	public ResultDisplay(final String string, final ArrayList<Regra> regras, final ArrayList<Record> records) throws IOException {
		regras2.clear();
		f = new JFrame("frame"); 
        JPanel p =new JPanel(new BorderLayout()); 
        JLabel l= new JLabel("Select rule"); 
        String week[]=new String [regras.size()];
        for(int o = 0; o!=regras.size();o++){
        	String iff=regras.get(o).getMetricas();
        	String then=regras.get(o).getEntao();
        	String elsee=regras.get(o).getSenao(); 
        	week[o] = "IF( " + iff + " ) THEN ( " + then + " ) ELSE ( " + elsee + " )";
        }
        b= new JList(week); 
        p.add(b, BorderLayout.NORTH); 
        JButton bt = new JButton("Avançar");
        p.add(bt, BorderLayout.CENTER);
        f.add(p); 
        f.setSize(600,200);  
        f.setVisible(true);
        this.string=string;
		this.regras=regras;
		this.records=records;
		button=bt;
        bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String s = b.getSelectedValue().toString();
					System.out.println(s);
					setTitles();
					setData(string);
					addFrameContent();
					setFrameVisible();		
					for(int i=5;i!=9;i++) {
						for(int j=0;j!=records.size();j++) {
							if(i==5) {
								table.setValueAt("", j, i);
							}
							if(i==6) {
								table.setValueAt("", j, i);
							}
						}	
					}
					for(int b=0;b!=regras.size();b++) {
						String aux = "IF( " + regras.get(b).getMetricas() + " ) THEN ( " + regras.get(b).getEntao() + " ) ELSE ( " + regras.get(b).getSenao() + " )";
						if(aux.equals(s)){
							regras2.add(regras.get(b));
							System.out.println(regras.get(b).getMetricas() + " " + regras.get(b).getEntao() + " " + regras.get(b).getSenao());
						}
					}

					f.dispose();
					analizarMetricas();
					rulesResults();
					comparatorsResults();
				} catch(IOException e1) {
					
				}						

			}
		});    
		
	}
	
	/**
	 * analizarMetricas() É um procedimento que permite realizar o mecanismo de  processamento das regras
	 */
	private void analizarMetricas() {
		for(int i=0;i!=regras2.size();i++) {
			for(int j=0;j!=records.size();j++) {
				String [] vetor_metricas = regras2.get(i).getVetor_metricas();
				if(occurrences(regras2.get(i).getMetricas())==2) {
					verificar(records.get(j), vetor_metricas[0], regras2.get(i).getEntao(),regras2.get(i).getSenao());
				}else {
					if(regras2.get(i).getOp().equals("AND")) {
						for(int k=0;k!=vetor_metricas.length-1;k++) {
							verificarAND(records.get(j), vetor_metricas[k],vetor_metricas[k+1],regras2.get(i).getEntao(),regras2.get(i).getSenao());
						}
					}
					if(regras2.get(i).getOp().equals("OR")) {
						for(int k=0;k!=vetor_metricas.length-1;k++) {
							verificarOR(records.get(j), vetor_metricas[k],vetor_metricas[k+1],regras2.get(i).getEntao(),regras2.get(i).getSenao());
						}
					}
				}
			}
		}
	}

	/**
	 * verificarOR() É um procedimento que permite o preenchimento da tabela conforme as regras recebidas
	 * @param record Regra
	 * @param string2 condição a verificar 1
	 * @param string3 condição a verificar 2
	 * @param entao o que fazer caso regra seja verificada
	 * @param senao o que fazer caso regra não seja verificada
	 */
	private void verificarOR(Record record, String string2, String string3, String entao, String senao) {
		String [] tokens1 = string2.split("<|\\>");
		String [] tokens2 = string3.split("<|\\>");
		double d1 = Double.parseDouble(tokens1[1]);
		double d2 = Double.parseDouble(tokens2[1]);
		if((tokens1[0].equals("LAA")) && (tokens2[0].equals("LAA"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))<d1)||((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))<d1)||((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))>d1)||((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))>d1)||((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LAA")) && (tokens2[0].equals("LOC"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))<d1)||((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))<d1)||((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))>d1)||((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))>d1)||((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LAA")) && (tokens2[0].equals("CYCLO"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))<d1)||((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))<d1)||((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))>d1)||((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))>d1)||((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LAA")) && (tokens2[0].equals("ATFD"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))<d1)||((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))<d1)||((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))>d1)||((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))>d1)||((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LOC")) && (tokens2[0].equals("LAA"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))<d1)||((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))<d1)||((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))>d1)||((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))>d1)||((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LOC")) && (tokens2[0].equals("LOC"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))<d1)||((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))<d1)||((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))>d1)||((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))>d1)||((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LOC")) && (tokens2[0].equals("CYCLO"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))<d1)||((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))<d1)||((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))>d1)||((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))>d1)||((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LOC")) && (tokens2[0].equals("ATFD"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))<d1)||((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))<d1)||((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))>d1)||((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))>d1)||((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("CYCLO")) && (tokens2[0].equals("LAA"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)||((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)||((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)||((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)||((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("CYCLO")) && (tokens2[0].equals("LOC"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)||((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)||((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)||((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)||((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("CYCLO")) && (tokens2[0].equals("CYCLO"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)||((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)||((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)||((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)||((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("CYCLO")) && (tokens2[0].equals("ATFD"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)||((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)||((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)||((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)||((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("ATFD")) && (tokens2[0].equals("LAA"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)||((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)||((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)||((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)||((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("ATFD")) && (tokens2[0].equals("LOC"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)||((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)||((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)||((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)||((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("ATFD")) && (tokens2[0].equals("CYCLO"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)||((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)||((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)||((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)||((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("ATFD")) && (tokens2[0].equals("ATFD"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)||((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)||((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)||((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)||((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		
	}
	
	/**
	 * verificarAND() É um procedimento que permite o preenchimento da tabela conforme as regras recebidas
	 * @param record Regra
	 * @param string2 condição a verificar 1
	 * @param string3 condição a verificar 2
	 * @param entao o que fazer caso regra seja verificada
	 * @param senao o que fazer caso regra não seja verificada
	 */
	private void verificarAND(Record record, String string2, String string3, String entao, String senao) {
		String [] tokens1 = string2.split("<|\\>");
		String [] tokens2 = string3.split("<|\\>");
		double d1 = Double.parseDouble(tokens1[1]);
		double d2 = Double.parseDouble(tokens2[1]);
		if((tokens1[0].equals("LAA")) && (tokens2[0].equals("LAA"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))<d1)&&((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))<d1)&&((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))>d1)&&((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))>d1)&&((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LAA")) && (tokens2[0].equals("LOC"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))<d1)&&((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))<d1)&&((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))>d1)&&((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))>d1)&&((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LAA")) && (tokens2[0].equals("CYCLO"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))<d1)&&((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))<d1)&&((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))>d1)&&((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))>d1)&&((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LAA")) && (tokens2[0].equals("ATFD"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))<d1)&&((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))<d1)&&((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))>d1)&&((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))>d1)&&((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LOC")) && (tokens2[0].equals("LAA"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))<d1)&&((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))<d1)&&((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))>d1)&&((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))>d1)&&((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LOC")) && (tokens2[0].equals("LOC"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))<d1)&&((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))<d1)&&((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))>d1)&&((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))>d1)&&((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LOC")) && (tokens2[0].equals("CYCLO"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))<d1)&&((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))<d1)&&((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))>d1)&&((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))>d1)&&((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("LOC")) && (tokens2[0].equals("ATFD"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))<d1)&&((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))<d1)&&((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))>d1)&&((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))>d1)&&((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("CYCLO")) && (tokens2[0].equals("LAA"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)&&((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)&&((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)&&((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)&&((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("CYCLO")) && (tokens2[0].equals("LOC"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)&&((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)&&((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)&&((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)&&((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("CYCLO")) && (tokens2[0].equals("CYCLO"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)&&((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)&&((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)&&((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)&&((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("CYCLO")) && (tokens2[0].equals("ATFD"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)&&((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)&&((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)&&((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)&&((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("ATFD")) && (tokens2[0].equals("LAA"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)&&((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)&&((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)&&((Double.parseDouble(record.getLaa()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)&&((Double.parseDouble(record.getLaa()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("ATFD")) && (tokens2[0].equals("LOC"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)&&((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)&&((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)&&((Double.parseDouble(record.getLoc()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)&&((Double.parseDouble(record.getLoc()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("ATFD")) && (tokens2[0].equals("CYCLO"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)&&((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)&&((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)&&((Double.parseDouble(record.getCyclo()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)&&((Double.parseDouble(record.getCyclo()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		if((tokens1[0].equals("ATFD")) && (tokens2[0].equals("ATFD"))) {
			if(string2.contains("<")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)&&((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains("<")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)&&((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)&&((Double.parseDouble(record.getAtfd()))<d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
			if(string2.contains(">")&&string3.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)&&((Double.parseDouble(record.getAtfd()))>d2)) {
					setVal(((int) Double.parseDouble(record.methodID)-1), entao);
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1), senao);
				}
			}
		}
		
	}
	
	/**
	 * verificar() É um procedimento que permite o preenchimento da tabela conforme as regras recebidas sem ORs ou ANDs
	 * @param record Regra
	 * @param string2 condição a verificar
	 * @param entao o que fazer caso regra seja verificada
	 * @param senao o que fazer caso regra não seja verificada
	 */
	private void verificar(Record record, String string2, String entao, String senao) {
		String [] tokens1 = string2.split("<|\\>");
		double d1 = Double.parseDouble(tokens1[1]);
		if((tokens1[0].equals("LAA"))) {
			if(string2.contains("<")) {
				if(((Double.parseDouble(record.getLaa()))<d1)) {
					setVal(((int) Double.parseDouble(record.methodID)-1),entao);					
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1),senao);
				}
			}
			if(string2.contains(">")) {
				if(((Double.parseDouble(record.getLaa()))>d1)) {
					setVal(((int) Double.parseDouble(record.methodID)-1),entao);					
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1),senao);
				}
			}
		}
		if((tokens1[0].equals("LOC"))) {
			if(string2.contains("<")) {
				if(((Double.parseDouble(record.getLoc()))<d1)) {
					setVal(((int) Double.parseDouble(record.methodID)-1),entao);	
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1),senao);
				}
			}
			if(string2.contains(">")) {
				if(((Double.parseDouble(record.getLoc()))>d1)) {
					setVal(((int) Double.parseDouble(record.methodID)-1),entao);					
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1),senao);
				}
			}
		}
		if((tokens1[0].equals("CYCLO"))) {
			if(string2.contains("<")) {
				if(((Double.parseDouble(record.getCyclo()))<d1)) {
					setVal(((int) Double.parseDouble(record.methodID)-1),entao);					
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1),senao);
				}
			}
			if(string2.contains(">")) {
				if(((Double.parseDouble(record.getCyclo()))>d1)) {
					setVal(((int) Double.parseDouble(record.methodID)-1),entao);					
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1),senao);
				}
			}
		}
		if((tokens1[0].equals("ATFD"))) {
			if(string2.contains("<")) {
				if(((Double.parseDouble(record.getAtfd()))<d1)) {
					setVal(((int) Double.parseDouble(record.methodID)-1),entao);					
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1),senao);
				}
			}
			if(string2.contains(">")) {
				if(((Double.parseDouble(record.getAtfd()))>d1)) {
					setVal(((int) Double.parseDouble(record.methodID)-1),entao);					
				}else {
					setVal(((int) Double.parseDouble(record.methodID)-1),senao);
				}
			}
		}
	}
	
	/**
	 * setVal() É um procedimento que escreve resultados na tabela
	 * @param i linha a ser alterada
	 * @param aux verificação de colunas
	 */
	private void setVal(int i, String aux) {
		if(aux.contains("long_method")&&aux.contains("TRUE")) {
			table.setValueAt("TRUE", i, 5);
		}
		if(aux.contains("long_method")&&aux.contains("FALSE")) {
			table.setValueAt("FALSE", i, 5);
		}
		if(aux.contains("feature_envy")&&aux.contains("TRUE")) {
			table.setValueAt("TRUE", i, 6);
		}
		if(aux.contains("feature_envy")&&aux.contains("FALSE")) {
			table.setValueAt("FALSE", i, 6);
		}	
	}

	/**
	 * occurrences() Cálculo de número de ocorrencias de ' ' na string metricas
	 * @param metricas métrica
	 * @return count contagem do número de ocorrências de caracter ' ' em metricas
	 */
	private int occurrences(String metricas) {
		int count = 0;
		for(int i=0;i!=metricas.length();i++) {
			if(metricas.charAt(i)==' ') {
				count++;
			}
		}
		return count;
	}

	/**
	 * setFrameVisible() É um procedimento que permite tornar visivel a frame de resultados
	 */
	private void setFrameVisible() {
		frame = new JFrame ("Resultados");
		frame.setLayout(new BorderLayout());
		frame.add(sp, BorderLayout.NORTH);
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * setFrameVisible2() É um procedimento que permite tornar visivel a frame de resultados na realização de testes unitários
	 */
	private void setFrameVisible2() {
		frame = new JFrame ("Resultados");
		frame.setLayout(new BorderLayout());
		frame.add(sp, BorderLayout.NORTH);
		frame.add(panel, BorderLayout.CENTER);
	}

	
	/**
	 * Set do panel display
	 */
	public void addFrameContent() {	
				
		//Initializing the JTable
		table = new JTable(data, columnTitles);
		table.setBounds(30, 40, 300, 600);
						
		//Adding it to JScrollPane
		sp = new JScrollPane(table);
			
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		labelResults = new JLabel("                      Resultados das Deteções de Erros em relação à Ferramenta 'Is_long_method'");
		
		//acrescentada mais uma linha para o ES_is_feauture_envy
		tt = new JTable(5,5);
		tt.setValueAt("DCI",0,1);
		tt.setValueAt("DII",0,2);
		tt.setValueAt("ADCI",0,3);
		tt.setValueAt("ADII",0,4);
		tt.setValueAt("PMD",1,0);
		tt.setValueAt("iPlasma",2,0);
		tt.setValueAt("ES_is_long_method",3,0);
		tt.setValueAt("ES_is_feature_envy",4,0);
		
		
							
		panel.add(labelResults, BorderLayout.NORTH);
		panel.add(tt, BorderLayout.CENTER);
	}
	
	/**
	 * setTitles() Formata a parte dos títulos/nome das colunas
	 */
	public void setTitles() {
		columnTitles = new String[9];
		columnTitles[0] = "MethodID";
		columnTitles[1] = "is_long_Method";
		columnTitles[2] = "iPlasma";
		columnTitles[3] = "PMD";
		columnTitles[4] = "is_feature_envy";
		columnTitles[5] = "ES_long";
		columnTitles[6] = "ES_envy";
		columnTitles[7] = "Defeito is_long_Method";
		columnTitles[8] = "Defeito is_feature_envy";
	}
		
	/**
	 * setData1() Vai buscar informação necessária ao já formado display
	 * @param string2 
	 * @throws IOException 
	 */
	public void setData1(String string2) throws IOException {
		String s = System.getProperty("user.dir");
		String a = s + "\\files\\Long-Method.xlsx";
		ExcelDisplay teste = new ExcelDisplay(new ReadExcel (new File(a)));
		String [] [] aux = teste.getData();
		data = new String [aux.length] [columnTitles.length];
		for(int linha = 0; linha < aux.length; linha++) {				//this equals to the row in our matrix
			for(int coluna = 0; coluna < aux[0].length; coluna++) {				//this equals to the column in each row
				switch (coluna) {
				case 0: data[linha][coluna] = aux [linha][coluna];				//MethodID 			 / Em uso
					break;
				case 1: 														//Package 			 / Sem uso
					break;
				case 2: 														//Class 			 / Sem uso
					break;
				case 3: 														//method			 / Sem uso
					break;
				case 4: 														//LOC 				 / Sem uso
					break;
				case 5: 														//CYCLO 			 / Sem uso
					break;
				case 6: 														//ATFD				 / Sem uso
					break;
				case 7: 														//LAA 				 / Sem uso
					break;
				case 8: data[linha][1] = aux [linha][coluna];					//is_long_method 	 / Em uso
					break;
				case 9: data[linha][2] = aux [linha][coluna];					//iPlasma			 / Em uso
					break;
				case 10: data[linha][3] = aux [linha][coluna];					//PMD				 / Em uso
					break;
				case 11: data[linha][4] = aux [linha][coluna];					//is_feature_envy	 / Em uso
					break;
				default:
					break;
				}
				
			
			}
		}
		
	}


	/**
	 * setData() Vai buscar informação necessária ao já formado display
	 * @param string2 
	 * @throws IOException 
	 */
	public void setData(String string2) throws IOException {
		ExcelDisplay teste = new ExcelDisplay(new ReadExcel (new File(string2)));
		String [] [] aux = teste.getData();
		data = new String [aux.length] [columnTitles.length];
		for(int linha = 0; linha < aux.length; linha++) {				//this equals to the row in our matrix
			for(int coluna = 0; coluna < aux[0].length; coluna++) {				//this equals to the column in each row
				switch (coluna) {
				case 0: data[linha][coluna] = aux [linha][coluna];				//MethodID 			 / Em uso
					break;
				case 1: 														//Package 			 / Sem uso
					break;
				case 2: 														//Class 			 / Sem uso
					break;
				case 3: 														//method			 / Sem uso
					break;
				case 4: 														//LOC 				 / Sem uso
					break;
				case 5: 														//CYCLO 			 / Sem uso
					break;
				case 6: 														//ATFD				 / Sem uso
					break;
				case 7: 														//LAA 				 / Sem uso
					break;
				case 8: data[linha][1] = aux [linha][coluna];					//is_long_method 	 / Em uso
					break;
				case 9: data[linha][2] = aux [linha][coluna];					//iPlasma			 / Em uso
					break;
				case 10: data[linha][3] = aux [linha][coluna];					//PMD				 / Em uso
					break;
				case 11: data[linha][4] = aux [linha][coluna];					//is_feature_envy	 / Em uso
					break;
				default:
					break;
				}
				
			
			}
		}
			}


	/**
	 * rulesResults() Mecanismo de preenchimento das colunas de deteção de defeitos is_long_method e is_feature_envy
	 */
	public void rulesResults() {

		String [] [] aux = data;
		
		for(int linha = 0; linha < aux.length; linha++) {	
			String valor1 = (String) table.getValueAt(linha,1);
			String valor2 = (String) table.getValueAt(linha,5);
			
			if( valor1.equals(valor2)) {
				table.setValueAt("FALSE", linha, 7);
			} else {
				if(valor2.equals("")) {
					table.setValueAt("FALSE", linha, 7);
				}else {
					table.setValueAt("TRUE", linha, 7);
				}

			}
		
			
			String valor3 = (String) table.getValueAt(linha,4);
			String valor4 = (String) table.getValueAt(linha,6);
			
			if( valor3.equals(valor4)) {
				table.setValueAt("FALSE", linha, 8);
			} else {
				if(valor4.equals("")) {
					table.setValueAt("FALSE", linha, 8);
				}else {
					table.setValueAt("TRUE", linha, 8);
				}

			}		}
	}
	

	/**
	 * comparatorsResults() Mecanismo de cálculo e apresentação dos resultados de deteção de defeitos
	 */
	public void comparatorsResults() { //contadores para os defeitos 
		String [] [] aux1 = data;
		
		//CONTADORES PARA O IS_LONG_METHOD
		int countDCI=0; //iPlasma
		int countDII=0; 
		int countADII=0;
		int countADCI=0;
		
		int countDCI1=0; //PMD
		int countDII1=0;
		int countADII1=0;
		int countADCI1=0;
		
		int countDCI2=0;  //ES_is_long_method
		int countDII2=0;
		int countADII2=0;
		int countADCI2=0;
		
		//CONTADORES PARA O IS_FEATURE_ENVY, descomentado/acrescentado após testes
		
		int countDCI3=0; //ES_is_feature_envy com is_feature_envy
		int countDII3=0;
		int countADII3=0;
		int countADCI3=0;
		
		for(int linha=0; linha<aux1.length;linha++) {
			
			//is_long_method para comparar com os restantes 4
			String valorILM = (String) table.getValueAt(linha, 1); 
			//VALORES PARA PARA COMPARAR COM IS_LONG_METHOD
			String valoriPlasma = (String) table.getValueAt(linha, 2); //iPlasma
			String valorPMD = (String) table.getValueAt(linha, 3); //PMD
			String valorESILM = (String) table.getValueAt(linha, 5); //ES_is_longMethod
			
			String valorESIFE = (String) table.getValueAt(linha, 6); //ES_is_feature_envy
			String valorIFE=(String) table.getValueAt(linha, 4);  //is_feature_envy
					
			if(valorILM.equals("TRUE")) {							
				if(valoriPlasma.equals("TRUE")) { //Def.corretos	//iPlasma
					countDCI++;
//					System.out.println("DCIplasma" + "\n" + countDCI + "\n" + "estamos na linha" + "\n" + linha);	
					//tt.setValueAt(countDCI,2,1);
				}else {
					countADII++; //ausencias de def.corretos
					System.out.println("ADIIplasma" + "\n" + countADII);
				}
				
				if(valorPMD.equals("TRUE")) { //Def.corretos 		//PMD
					countDCI1++;
					System.out.println("DCI1PMD" + "\n" + countDCI1 + "estamos na linha" + "\n" + linha);
					
				}else {
					countADII1++; //ausencias de def.corretos
					System.out.println("ADII1PMD" + "\n" + countADII1);
				}
				if(valorESILM.equals("TRUE")) { //Def.corretos 		//ES_is_long_method
					countDCI2++;
					System.out.println("ADCI12ESILM" + "\n" +countDCI2);
				}else {
					if(valorESILM.equals("")==false) {
						countADII2++; //ausencias de def.corretos
						System.out.println("ADII2ESILM" + "\n" +countADII2);
					}
				}
			}

			if(valorIFE.equals("TRUE"))	{					//is_feature_envy com ES_is_feature_envy
//				if(!valorESIFE.equals(" ")) {
				if(valorESIFE.equals("TRUE")) {
					countDCI3++;
				}else {
					if(valorESIFE.equals("")==false) {
						countADII3++;
					}
				}
				
			}
			
			
			if(valorILM.equals("FALSE")) { 
				if(valoriPlasma.equals("TRUE")){ 				//iPlasma
					countDII++; //defeitos incorretos
					System.out.println("DIIILM" + "\n" +countDII);
				}else {
					countADCI++; //aus.def.incorr
					System.out.println("ADCIILM" + "\n" +countADCI);
				}
				
				if(valorPMD.equals("TRUE")){ 						//PMD
					countDII1++; //defeitos incorretos
					System.out.println("DII1PMD" + "\n" +countDII1);
				}else {
					countADCI1++; //aus.def.incorr
					System.out.println("ADCI1PMD" + "\n" +countADCI1 + "estamos na linha" + linha);
				}
				
				if(valorESILM.equals("TRUE")){ 						//ES_is_long_method
					countDII2++; //defeitos incorretos
					System.out.println("DII2ILM" + "\n" +countDII2);
				}else {
					if(valorESILM.equals("")==false) {
						countADCI2++; //aus.def.incorr
						System.out.println("ADCI2ILM" + "\n" +countADCI2);
					}
				}
			
			}
			
				
		if(valorIFE.equals("FALSE"))	{ 						//is_feature_envy com ES_is_feature_envy
			if(valorESIFE.equals("TRUE")) {
				countDII3++;
				System.out.println("DII3IFE" + "\n" +countDII3);
			}else {
				if(valorESIFE.equals("")==false) {
					countADCI3++;
//					System.out.println("ADCI3IFE" + "\n" +countADCI3);
				}
			}
		
			
		}
			
		//ORGANIZAR MELHOR	

			tt.setValueAt(countDCI,2,1);
			tt.setValueAt(countDII,2,2);
			tt.setValueAt(countADCI,2,3);
			tt.setValueAt(countADII,2,4);
			
			tt.setValueAt(countDCI1,1,1);
			tt.setValueAt(countDII1,1,2);
			tt.setValueAt(countADII1,1,4);
			tt.setValueAt(countADCI1,1,3);
			
			tt.setValueAt(countDCI2,3,1);
			tt.setValueAt(countDII2, 3, 2);
			tt.setValueAt(countADII2, 3, 4);
			tt.setValueAt(countADCI2,3,3);
			
			tt.setValueAt(countDCI3,4,1);
			tt.setValueAt(countDII3,4,2);
			tt.setValueAt(countADCI3,4,3);
			tt.setValueAt(countADII3,4,4);
		}
	}
	

	/**
	 * getValue() Devolve valor na entrada da tabela na linha de indice 0 e coluna de indice 5
	 */
	public String getValue() {
		return (String) table.getValueAt(0, 5);
	}
	

	/**
	 * inic() Inicialização de atributos para a realização de testes unitários
	 * @param string nome ficheiro
	 * @param regras regras definidas
	 * @param records registos do ficheiro Excel
	 * @param selectedValue caminho do ficheiro 
	 * @throws IOException 
	 */
	public void inic(String string, ArrayList<Regra> regras, ArrayList<Record> records,String selectedValue) throws IOException {
		regras2.clear();
		f = new JFrame("frame"); 
        JPanel p =new JPanel(new BorderLayout()); 
        JLabel l= new JLabel("Select rule"); 
        String week[]=new String [regras.size()];
        for(int o = 0; o!=regras.size();o++){
        	String iff=regras.get(o).getMetricas();
        	String then=regras.get(o).getEntao();
        	String elsee=regras.get(o).getSenao(); 
        	week[o] = "IF( " + iff + " ) THEN ( " + then + " ) ELSE ( " + elsee + " )";
        }
        JList b= new JList(week); 
        p.add(b, BorderLayout.NORTH); 
        JButton bt = new JButton("Avançar");
        p.add(bt, BorderLayout.CENTER);
        f.add(p); 
        f.setSize(600,200);  
        f.setVisible(true);
        this.string=string;
		this.regras=regras;
		this.records=records;
		button=bt;
		
		String s = selectedValue;
		System.out.println(s);
		setTitles();
		setData1(string);
		addFrameContent();
		setFrameVisible2();		
		for(int i=5;i!=9;i++) {
			for(int j=0;j!=records.size();j++) {
				if(i==5) {
					table.setValueAt("", j, i);
				}
				if(i==6) {
					table.setValueAt("", j, i);
				}
			}	
		}
		for(int b1=0;b1!=regras.size();b1++) {
			String aux = "IF( " + regras.get(b1).getMetricas() + " ) THEN ( " + regras.get(b1).getEntao() + " ) ELSE ( " + regras.get(b1).getSenao() + " )";
			if(aux.equals(s)){
				regras2.add(regras.get(b1));
				System.out.println(regras.get(b1).getMetricas() + " " + regras.get(b1).getEntao() + " " + regras.get(b1).getSenao());
			}
		}

		f.dispose();
		analizarMetricas();
	}

	
}