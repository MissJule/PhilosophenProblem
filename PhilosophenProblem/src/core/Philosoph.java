package core;

import gui.PhilosophenWindow;

public class Philosoph implements Runnable {

   Staebchen staebchenRechts, staebchenLinks;
   Zustand status;
   int nummer;
   private PhilosophenWindow currentWindow;
   boolean pause = false;

   public Philosoph(int nummer, Staebchen staebchenLinks,
         Staebchen staebchenRechts, PhilosophenWindow currentWindow) {
      this.nummer = nummer;
      this.staebchenLinks = staebchenLinks;
      this.staebchenRechts = staebchenRechts;
      this.status = Zustand.PHILOSOPHIEREND;
      this.currentWindow = currentWindow;
   }

   public synchronized void makePause() {
      pause = true;
   }

   public synchronized void continueToRun() {
      pause = false;
      notify();
   }

   @Override
   public synchronized void run() {
      while (true) {
         while (pause) {
            try {
               this.wait();
            } catch (InterruptedException e) {
               System.out.println("wait funkt nicht!");
            }
         }
         switch (status) {
         case PHILOSOPHIEREND:
            // geht nach kurzer Zeit direkt in Zustand hungrig ueber
            currentWindow.changePhilsState(nummer, status);
            System.out.println("Philosoph " + nummer + " philosophiert...");
            try {
               Thread.sleep((long) (Math.random() * 5000));
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            status = Zustand.HUNGRIG;
            break;
         case HUNGRIG:
            // nimmt erst das linke und wenn verfügbar das rechte staebchen
            // und geht ueber in Zustand essend
            currentWindow.changePhilsState(nummer, status);
            System.out.println("Philosoph " + nummer + " ist hungrig...");
            staebchenLinks.staebchenNehmen();
            try {
               Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            staebchenRechts.staebchenNehmen();
            status = Zustand.ESSEND;
            break;
         case ESSEND:
            // legt nach kurzer Zeit das linke und rechte Staebchen zurueck
            // und
            // geht ueber in Zustand philosophierend
            currentWindow.changePhilsState(nummer, status);
            System.out.println("Philosoph " + nummer + " isst...");
            try {
               Thread.sleep((int) (Math.random() * 5000));
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            staebchenLinks.staebchenZuruecklegen();
            staebchenRechts.staebchenZuruecklegen();
            status = Zustand.PHILOSOPHIEREND;
            break;
         }
      }

   }

}
