package core;

import javax.swing.SwingUtilities;

import gui.PhilosophenWindow;

public class PhilosophenController {

	private Philosoph[] philosophen = new Philosoph[5];
	private Staebchen[] staebchen = new Staebchen[5];
	private Thread[] threads = null;
	private PhilosophenWindow currentWindow;

	public PhilosophenController(PhilosophenWindow currentWindow) {
		this.currentWindow = currentWindow;
	}

	public void continueThreads() {
		// let thread continue
		for (int i = 0; i < threads.length; i++) {
			philosophen[i].continueToRun();
		}
	}

	public void initThreads() {
		// 1st start of threads
		// erzeuge Staebchen
		for (int i = 0; i < staebchen.length; i++) {
			staebchen[i] = new Staebchen();
		}
		System.out.println("Staebchen erzeugt");
		// ordne den Philosophen ihr linkes und rechtes Staebchen zu
		for (int i = 0; i < philosophen.length; i++) {
			philosophen[i] = new Philosoph(i, staebchen[(i + 4) % 5],
					staebchen[i % 5], currentWindow);
		}
		System.out.println("Philosophen erzeugt");
		// starte Philosophen-Threads
		threads = new Thread[philosophen.length];
		for (int i = 0; i < philosophen.length; i++) {
			threads[i] = new Thread(philosophen[i]);
			threads[i].start();
		}
		System.out.println("Philosophen gestartet");
		
		currentWindow.btnStart.setEnabled(false);
		currentWindow.btnStop.setEnabled(true);

		
	}

	public void pauseThreads() {
		for (int i = 0; i < threads.length; i++) {
			philosophen[i].makePause();
		}
		currentWindow.btnStop.setEnabled(false);
		currentWindow.btnStart.setEnabled(true);
	}
}
