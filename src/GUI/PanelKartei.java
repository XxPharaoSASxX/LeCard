package GUI;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Logik.Kartei;

/* @autor Lars Weder,Martin Heinzle,Roman Vorburger, Marvin K�ndig
 * @version 0.8
 * Datum:24.02.2018
 */
public class PanelKartei extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainFrame;
	private JPanel kartei;
	private JPanel stat;
	private JPanel start;
	private PanelStatistik grafischeStat;
	private JPanel datenStatistik;
	private JLabel richtigeAntwort;
	private JLabel falscheAntwort;

	public PanelKartei() {
		initComponents();
		setBalkendiagramm();
		bindListener();
		paint();
	}

	public void repaint() {
		initComponents();
		bindListener();
		setBalkendiagramm();

	}

	public void initComponents() {
		mainFrame = new JPanel();

		grafischeStat = new PanelStatistik();
		grafischeStat.setPreferredSize(new Dimension(400, 400));

		datenStatistik = new JPanel();
		kartei = new JPanel();
		stat = new JPanel();
		start = new JPanel();
		richtigeAntwort = new JLabel(
				ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("richtigeAntworten") + " "
						+ Kartei.getInstance().getRichtigeAntwort());
		falscheAntwort = new JLabel(
				ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("falscheAntworten") + " "
						+ Kartei.getInstance().getFalscheAntwort());

	}

	private void bindListener() {

	}

	public void paint() {
		datenStatistik.setLayout(new GridLayout(3, 2));
		datenStatistik.add(richtigeAntwort);
		datenStatistik.add(falscheAntwort);

		// mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.PAGE_AXIS));

		// kartei.setLayout(new GridLayout(1, 3));
		kartei.setLayout(new FlowLayout());
		// stat.setLayout(new GridLayout(1, 2));
		stat.setLayout(new FlowLayout());

		stat.add(grafischeStat);
		stat.add(datenStatistik);

		mainFrame.add(stat);
		mainFrame.add(start);

		add(mainFrame);

	}

	public void setBalkendiagramm() {
		// Setzen von Barwidth
		grafischeStat.setKart1_WIDTH(Kartei.getInstance().getFachGroesse(0));
		grafischeStat.setKart2_WIDTH(Kartei.getInstance().getFachGroesse(1));
		grafischeStat.setKart3_WIDTH(Kartei.getInstance().getFachGroesse(2));
		grafischeStat.setKart4_WIDTH(Kartei.getInstance().getFachGroesse(3));
		grafischeStat.setKart5_WIDTH(Kartei.getInstance().getFachGroesse(4));
		richtigeAntwort.setText(ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("richtigeAntworten")
				+ " " + Kartei.getInstance().getRichtigeAntwort());
		falscheAntwort.setText(ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("falscheAntworten")
				+ " " + Kartei.getInstance().getFalscheAntwort());

	}

	public void spracheWechseln() {
		richtigeAntwort.setText(ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("richtigeAntworten") + " " + Kartei.getInstance().getRichtigeAntwort());
		falscheAntwort.setText(ResourceBundle.getBundle("Bundles\\Bundle", Kartei.getInstance().getLocale()).getString("falscheAntworten") + " "	+ Kartei.getInstance().getFalscheAntwort());
		
	}

}