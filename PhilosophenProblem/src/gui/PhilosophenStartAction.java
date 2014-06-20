package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.PhilosophenController;

public class PhilosophenStartAction implements ActionListener {

	private PhilosophenController ctl;
	private boolean alreadyStarted = false;

	PhilosophenStartAction(PhilosophenController ctl) {
		this.ctl = ctl;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (alreadyStarted) {
			ctl.continueThreads();
		} else {
			ctl.initThreads();
			alreadyStarted = true;
		}
		

	}

}
