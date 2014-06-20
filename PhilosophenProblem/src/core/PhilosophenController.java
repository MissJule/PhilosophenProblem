package core;

import javax.swing.SwingUtilities;

import gui.PhilosophenWindow;

public class PhilosophenController {

   private Philosoph[] philosophen = new Philosoph[5];
   private Staebchen[] staebchen = new Staebchen[5];
   private boolean alreadyStarted = false;
   private PhilosophenWindow currentWindow;

   public PhilosophenController(PhilosophenWindow currentWindow) {
      this.currentWindow = currentWindow;
   }

   public void pauseThreads() {
      // performed when pressing stop button
      for (int i = 0; i < philosophen.length; i++) {
         philosophen[i].makePause();
      }
   }

   public void startThreads() {
      // performed when pressing start button
      if (alreadyStarted) {
         continueThreads();
      } else {
         initThreads();
      }
   }

   public void continueThreads() {
      // let threads continue
      for (int i = 0; i < philosophen.length; i++) {
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
      for (int i = 0; i < philosophen.length; i++) {
         new Thread(philosophen[i]).start();
      }
      System.out.println("Philosophen gestartet");
      alreadyStarted = true;
   }
}
