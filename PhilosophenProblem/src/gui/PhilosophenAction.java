package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import core.Philosoph;
import core.Staebchen;

public class PhilosophenAction implements ActionListener {

	private Philosoph[] philosophen = new Philosoph[5];
	private Staebchen[] staebchen = new Staebchen[5];
	private Thread[] threads;
	private PhilosophenWindow currentWindow;
	private boolean alreadyStarted = false;

	public PhilosophenAction(PhilosophenWindow currentWindow) {
		this.currentWindow = currentWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (currentWindow.btnStart.isEnabled()
				&& !currentWindow.btnStop.isEnabled()) {
			if (alreadyStarted) {
				// let thread continue
				for (int i = 0; i < threads.length; i++) {
					philosophen[i].continueToRun();
				}
			} else {
				// erzeuge Staebchen
				for (int i = 0; i < staebchen.length; i++) {
					staebchen[i] = new Staebchen();
				}
				// ordne den Philosophen ihr linkes und rechtes Staebchen zu
				for (int i = 0; i < philosophen.length; i++) {
					philosophen[i] = new Philosoph(i, staebchen[(i + 4) % 5],
							staebchen[i % 5], currentWindow);
				}
				// starte Philosophen-Threads
				threads = new Thread[philosophen.length];
				for (int i = 0; i < philosophen.length; i++) {
					threads[i] = new Thread(philosophen[i]);
					threads[i].start();
				}
				alreadyStarted = true;
			}
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					currentWindow.btnStart.setEnabled(false);
					currentWindow.btnStop.setEnabled(true);
				}
			});
			
		} else {
			// let thread continue
			System.out.println(philosophen);
			 for (int i = 0; i < threads.length; i++) {
			 philosophen[i].makePause();
			 }
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					currentWindow.btnStop.setEnabled(false);
					currentWindow.btnStart.setEnabled(true);
				}
				
			});
		
		}
	}

}
