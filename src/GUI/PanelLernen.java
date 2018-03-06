package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Logik.Kartei;

/* @autor Lars Weder,Martin Heinzle,Roman Vorburger, Marvin K�ndig
 * @version 0.4
 * Datum:24.02.2018
 */
public class PanelLernen extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton bWechsel, bPruefen;
	private JPanel pLernen, pSpracheA, pSpracheB, pPruefen, pAuswertung;
	private JLabel lSpracheA, lSpracheB, lLoesung;
	private JTextField tSpracheA, tSpracheB;
	private boolean learnReverse;

	public PanelLernen() {
		initComponents();
		bindListener();
		init();
		paint();
		learnReverse = false;
	}

	public void init() {
		initComponents();
		loadCard();
		bindListener();
	}

	private void initComponents() {
		pLernen = new JPanel();
		pSpracheA = new JPanel();
		pSpracheB = new JPanel();
		pPruefen = new JPanel();
		pAuswertung = new JPanel();
		bPruefen = new JButton(ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("pruefen"));
		bWechsel = new JButton("<->");
		bWechsel.setPreferredSize(new Dimension(220, 22));
		lSpracheA = new JLabel(Kartei.getInstance().getAktuelleSprache().getSpracheA());
		lSpracheA.setPreferredSize(new Dimension(220, 22));
		tSpracheA = new JTextField();
		tSpracheA.setPreferredSize(new Dimension(220, 22));
		tSpracheA.setEditable(false);
		tSpracheA.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lSpracheB = new JLabel(Kartei.getInstance().getAktuelleSprache().getSpracheB());
		lSpracheB.setPreferredSize(new Dimension(220, 22));
		tSpracheB = new JTextField();
		tSpracheB.setPreferredSize(new Dimension(220, 22));
		tSpracheB.addKeyListener(new textfeldListener());
		tSpracheB.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lLoesung = new JLabel("");
		// initiiere Layout von den Panles
		pLernen.setLayout(new GridLayout(4, 1));
		pSpracheA.setLayout(new GridLayout(1, 2));
		pSpracheB.setLayout(new GridLayout(1, 2));
		// pAuswertung.setLayout(new GridLayout(1, 2));
		pPruefen.setLayout(new GridLayout(1, 2));

	}

	// Pr�ft die Antwort und gibt die entsprechende Karte aus
	public void loadCard() {
		if (Kartei.getInstance().gibNaechsteKarte() == true) {
			if (learnReverse == false) {
				tSpracheA.setText(Kartei.getInstance().getAktuelleKarte().getWortA());
				pPruefen.setVisible(true);
			}
			else {
				tSpracheB.setText(Kartei.getInstance().getAktuelleKarte().getWortB());
				pPruefen.setVisible(true);
				
			}
			
		}

		// Dialog keine Karte vorhanden und "Pr�fen Button" ausblenden
		else {
			
			if (learnReverse == false) {
			if (Kartei.getInstance().getAktuellesFach() != 0) {
				JOptionPane.showMessageDialog(pLernen,
						ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("keinKarteVorhanden"));
				tSpracheB.setText("");
				tSpracheA.setText("-");
				pPruefen.setVisible(false);
			}
			}
			else {
				if (Kartei.getInstance().getAktuellesFach() != 0) {
					JOptionPane.showMessageDialog(pLernen,
							ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("keinKarteVorhanden"));
					tSpracheA.setText("");
					tSpracheB.setText("-");
					pPruefen.setVisible(false);
				}
				
			}
		}

	}

	private void bindListener() {
		bWechsel.addActionListener(new ButtonListenerSpracheWechseln());
		bPruefen.addActionListener(e -> verifyAnswer());
		bPruefen.addActionListener(new ButtonListenerPruefen());
		tSpracheB.addActionListener(new JTextFieldListener());

	}

	public void paint() {
		// Adding Components

		pSpracheA.add(tSpracheA, BorderLayout.CENTER);

		pAuswertung.add(lLoesung);
		pSpracheA.add(lSpracheA, BorderLayout.CENTER);
		pSpracheA.add(tSpracheA, BorderLayout.CENTER);
		pSpracheB.add(lSpracheB, BorderLayout.EAST);
		pSpracheB.add(tSpracheB, BorderLayout.EAST);
		pPruefen.add(bWechsel, BorderLayout.WEST);
		pPruefen.add(bPruefen, BorderLayout.EAST);

		pLernen.add(pSpracheA);
		pLernen.add(pSpracheB);
		pLernen.add(pPruefen);
		pLernen.add(pAuswertung);
		add(pLernen);
	}

	class ButtonListenerSpracheWechseln implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if (learnReverse == false) {
				
				tSpracheA.setEditable(true);
				tSpracheB.setEditable(false);
				tSpracheA.setText("");
				learnReverse = true;
			}
			else {
				tSpracheA.setEditable(false);
				tSpracheB.setEditable(true);
				tSpracheB.setText("");
				learnReverse = false;
			}
			
			loadCard();


		}
	}

	// Prueft ob der"Pr�fen Button" angezeigt wird oder nicht

	public void verifyAnswer() {

	}
	
	

	class ButtonListenerPruefen implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Kartei.getInstance().getAktuelleKarte().getWortB().equalsIgnoreCase(tSpracheB.getText())) {

				System.out.println("Korrekt");
				Kartei.getInstance().karteVerschieben(Kartei.getInstance().getAktuelleKarte(), Kartei.getInstance().getAktuellesFach() + 1);
				if (learnReverse == false) {
					tSpracheB.setText("");
				}
				else {
					tSpracheA.setText("");
				}
				
				
				lLoesung.setText("Richtig");
				lLoesung.setFont(lLoesung.getFont().deriveFont(22f));
				lLoesung.setForeground(Color.GREEN);
				Kartei.getInstance().setRichtigeAntwort();

			}
			

			else {
				System.out.println("Falsch");
				Kartei.getInstance().karteVerschieben(Kartei.getInstance().getAktuelleKarte(), 1); 
				lLoesung.setText("Falsch"+ "  "+ Kartei.getInstance().getAktuelleKarte().getWortB());
				lLoesung.setForeground(Color.RED);
				lLoesung.setFont(lLoesung.getFont().deriveFont(22f));
				tSpracheB.setText("");
				Kartei.getInstance().setFalscheAntwort();

			}
			Kartei.getInstance().lernkarteiSpeichern();

			loadCard();

		}

	}

	class JTextFieldListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			System.out.println(tSpracheB.getText());
		}

	}

	public void spracheWechseln() {
		bPruefen.setText(ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("pruefen"));
		
	}
	
	class textfeldListener implements KeyListener {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				bPruefen.doClick();
			}

		}
		
	}
	


}