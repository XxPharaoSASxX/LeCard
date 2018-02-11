import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class PanelLernen extends JPanel {
	private JPanel jp2;
	private JButton b2;
	private JPanel pLernen;
	private JPanel pSpracheEins;
	private JPanel pSpracheZwei;
	private JPanel pPruefen;
	private JPanel pAuswertung;
	private JButton bWechsel;
	private JButton bPruefen;
	private JLabel lSpracheEins;
	private JLabel lSpracheEinsFrage;
	private JLabel lSpracheZwei;
	private JTextField tSpracheZweiAntwort;
	private JLabel lLoesung;

	public PanelLernen() {
		initComponents();
		bindListener();
		paint();

	}

	public void repaint() {
		initComponents();
		bindListener();
		// paint();
	}

	private void initComponents() {
		pLernen = new JPanel();
		pSpracheEins = new JPanel();
		pSpracheZwei = new JPanel();
		pPruefen = new JPanel();
		pAuswertung = new JPanel();
		bPruefen = new JButton(ResourceBundle.getBundle("Bundle", Hauptfenster.locale).getString("pruefen"));
		bWechsel = new JButton("<->");
		lSpracheEins = new JLabel(main.daten1.getAktuelleSprache());
		lSpracheEinsFrage = new JLabel(main.daten1.getAktuelleKarte().getWortA());
		lSpracheZwei = new JLabel(main.daten1.getAktuelleSprache());
		tSpracheZweiAntwort = new JTextField();
		lLoesung = new JLabel("Richtig/Flasch");
		// initiiere Layout von den Panles
		pLernen.setLayout(new GridLayout(4, 1));
		pSpracheZwei.setLayout(new GridLayout(1, 2));
		pPruefen.setLayout(new BorderLayout());
		pAuswertung.setLayout(new BorderLayout());
	}

	private void bindListener() {
		bWechsel.addActionListener(new ButtonListenerSpracheWechseln());
		bPruefen.addActionListener(new ButtonListenerPruefen());
		tSpracheZweiAntwort.addActionListener(new JTextFieldListener());

	}

	public void paint() {
		// Adding Components
		pSpracheEins.add(lSpracheEins);
		pSpracheEins.add(lSpracheEinsFrage);
		pSpracheZwei.add(lSpracheZwei);
		pSpracheZwei.add(tSpracheZweiAntwort);
		pPruefen.add(bPruefen, BorderLayout.CENTER);
		pAuswertung.add(lLoesung, BorderLayout.EAST);

		pLernen.add(pSpracheEins);
		pLernen.add(pSpracheZwei);
		pLernen.add(pPruefen);
		pLernen.add(pAuswertung);
		add(pLernen);
	}

	class ButtonListenerSpracheWechseln implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Sprache wird gewechselt");

		}
	}

	class ButtonListenerPruefen implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println(main.daten1.getAktuelleKarte().getWortB());
			System.out.println(tSpracheZweiAntwort.getText());

			if (main.daten1.getAktuelleKarte().getWortB().equalsIgnoreCase(tSpracheZweiAntwort.getText())) {

				System.out.println("Korrekt");
				main.daten1.gibNaechsteKarte();

			}

			else {
				System.out.println("Falsch");

			}
		}
	}

	class JTextFieldListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			System.out.println(tSpracheZweiAntwort.getText());
		}

	}

}
