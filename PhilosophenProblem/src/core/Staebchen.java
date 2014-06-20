package core;

public class Staebchen {

   private boolean besetzt;

   public Staebchen() {
      besetzt = false;
   }

   synchronized void staebchenNehmen() {
      // wartet, bis das staebchen frei wird, dann besetze es
      while (besetzt) {
         try {
            wait();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      besetzt = true;
   }

   synchronized void staebchenZuruecklegen() {
      // gibt ein Staebchen wieder frei und benachrichtige wartende Philosophen
      besetzt = false;
      notify();
   }

}
