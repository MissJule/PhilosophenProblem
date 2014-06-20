package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import core.PhilosophenController;
import core.Zustand;

@SuppressWarnings("serial")
public class PhilosophenWindow extends JFrame {

	static BufferedImage imgPhil, imgPhilosophierend, imgHunger, imgNomnom;

	static {
		try {
			imgPhil = ImageIO.read(PhilosophenWindow.class.getClassLoader()
					.getResourceAsStream("img/Philosoph.png"));
			imgPhilosophierend = ImageIO.read(PhilosophenWindow.class
					.getClassLoader().getResourceAsStream(
							"img/Philosophierend.png"));
			imgHunger = ImageIO.read(PhilosophenWindow.class.getClassLoader()
					.getResourceAsStream("img/Hunger.png"));
			imgNomnom = ImageIO.read(PhilosophenWindow.class.getClassLoader()
					.getResourceAsStream("img/Nomnom.png"));
		} catch (IOException e) {
			System.out.println("Picture not found");
			// TODO: JDialog mit ErrorMsg
		}
	}

	private JLabel lblHeader;

	private JPanel pnlCenter, pnlBottom;

	private JLabel[] lblPhils, lblStates;
	public JButton btnStart;
	public JButton btnStop;

	private ImageIcon iconPhils = new ImageIcon(imgPhil);
	private ImageIcon iconPhilosophierend = new ImageIcon(imgPhilosophierend);
	private ImageIcon iconHunger = new ImageIcon(imgHunger);
	private ImageIcon iconNomnom = new ImageIcon(imgNomnom);
	private PhilosophenController ctl;	

	public PhilosophenWindow() {
		ctl = new PhilosophenController(this);

		setTitle("Philosophen");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		createWidgets();
		addWidgets();
		setListeners();

		pack();
		setLocationRelativeTo(null);

		setVisible(true);
	}

	private void setListeners() {
		//btnStart.addActionListener(new PhilosophenAction(this));
		//btnStop.addActionListener(new PhilosophenAction(this));
	   
		btnStart.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent arg0) {
            ctl.startThreads();
            SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {
                  btnStart.setEnabled(false);
                  btnStop.setEnabled(true);
               }
            });
         }
		});
			
		btnStop.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ctl.pauseThreads();
            SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {
                  btnStop.setEnabled(false);
                  btnStart.setEnabled(true);
               }
            });
         }
		});
	}

	private void addWidgets() {
		getContentPane().setLayout(new BorderLayout(5, 5));
		getContentPane().add(BorderLayout.PAGE_START, lblHeader);
		getContentPane().add(BorderLayout.CENTER, pnlCenter);
		getContentPane().add(BorderLayout.PAGE_END, pnlBottom);
		addLbls(lblStates, pnlCenter);
		addLbls(lblPhils, pnlCenter);
		pnlBottom.add(btnStart);
		pnlBottom.add(btnStop);

	}

	private void createWidgets() {
		lblHeader = new JLabel("Philosophen-Problem");
		lblHeader.setFont(lblHeader.getFont().deriveFont(
				Font.BOLD + Font.ITALIC, 30));
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setOpaque(true);
		lblHeader.setBackground(Color.DARK_GRAY);
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);

		pnlCenter = new JPanel();
		pnlCenter.setLayout(new GridLayout(2, 5));

		lblStates = createLbls(iconPhilosophierend);
		lblPhils = createLbls(iconPhils);

		pnlBottom = new JPanel();
		pnlBottom.setLayout(new FlowLayout());

		btnStart = new JButton("Start");
		btnStart.setHorizontalAlignment(SwingConstants.CENTER);
		btnStop = new JButton("Stop");
		btnStop.setHorizontalAlignment(SwingConstants.CENTER);
		btnStop.setEnabled(false);

	}

	public void changePhilsState(int nummer, Zustand status) {
		final int nummerNow = nummer;
		final Zustand statusNow = status;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				switch (statusNow) {
				case PHILOSOPHIEREND:
					lblStates[nummerNow].setIcon(iconPhilosophierend);
					break;
				case HUNGRIG:
					lblStates[nummerNow].setIcon(iconHunger);
					break;
				case ESSEND:
					lblStates[nummerNow].setIcon(iconNomnom);
					break;
				}
				lblStates[nummerNow].revalidate();
			}

		});

	}

	private JLabel[] createLbls(ImageIcon img) {
		JLabel[] lbl = new JLabel[5];
		for (int i = 0; i < 5; i++) {
			lbl[i] = new JLabel();
			lbl[i].setIcon(img);
			lbl[i].setPreferredSize(new Dimension(150, 150));
		}
		return lbl;
	}

	private void addLbls(JLabel[] lbl, JPanel p) {
		for (int i = 0; i < 5; i++) {
			p.add(lbl[i]);
		}
	}


	public static void main(String[] args) {
		PhilosophenWindow w = new PhilosophenWindow();
	}

}
