package core;

import gui.PhilosophenWindow;

public class Philosoph implements Runnable {

   Staebchen staebchenRechts, staebchenLinks;
   Zustand status;
   int nummer;
   private PhilosophenWindow currentWindow;
   boolean pause = false;
   static Object lock = "";

   public Philosoph(int nummer, Staebchen staebchenLinks,
         Staebchen staebchenRechts, PhilosophenWindow currentWindow) {
      this.nummer = nummer;
      this.staebchenLinks = staebchenLinks;
      this.staebchenRechts = staebchenRechts;
      this.status = Zustand.PHILOSOPHIEREND;
      this.currentWindow = currentWindow;
   }

   public void makePause() {
      synchronized (lock) {
         pause = true;
      }
   }

   public void continueToRun() {
      synchronized (lock) {
         pause = false;
         lock.notify();
      }
   }

   @Override
   public void run() {
      while (true) {
         while (pause) {
            synchronized (lock) {
               try {
                  lock.wait();
               } catch (InterruptedException e) {
                  System.out.println("Interrupted while waiting");
               }
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
