package ontologies;import jade.content.*;public class Account implements Concept {// --------------------------------------   private String id;   private String name;   private float balance = 0;   public String getId() {      return id;   }   public String getName() {      return name;   }   public float getBalance() {      return balance;   }   public void setId(String id) {      this.id = id;   }   public void setName(String name) {      this.name = name;   }   public void setBalance(float balance) {      this.balance = balance;   }   public String toString() {	  return name + "  # " + id + "  --> balance = " + balance;   }}