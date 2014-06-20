package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.PhilosophenController;

public class PhilosophenStopAction implements ActionListener {
	
	private PhilosophenController ctl;
	
	PhilosophenStopAction(PhilosophenController ctl) {
		this.ctl = ctl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ctl.pauseThreads();
		
	}

}
