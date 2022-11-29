import java.util.ArrayList;
import java.util.Arrays;

import tester.Tester;

//represents a bank account
class BankAccount {
  int balance; // number of dollars in account

  BankAccount() {
    this.balance = 0;
  }

  BankAccount(int dollars) {
    this.balance = dollars;
  }

  //EFFECT: adds the given amount to this account's balance. amount may be negative
  void process(int amount) {
    this.balance = this.balance + amount;
  }

}

class Utils {

  // applies all of the transactions in the given backup to the given BankAccount which should be mutated
  void processTransactions(BankAccount ba, ArrayList<ArrayList<Integer>> backup) {
    for (ArrayList<Integer> al : backup) {
      for (Integer i : al) {
        ba.process(i);
      }
    }
  }

  // checks if any of the accounts in the list appear more than once
  boolean anyRepeats(ArrayList<BankAccount> accounts) {
    for (int i = 0; i < accounts.size(); i++) {
      for (int j = 0; j < accounts.size(); j++) {
        if (!(i == j)) {
          if (accounts.get(i) == accounts.get(j)) {
            return true;
          }
        }
      }
    }
    return false;
  }
}

// examples and tests for bank accounts 
class ExamplesBankAccounts {
  BankAccount ba1 = new BankAccount(10);
  BankAccount ba2 = new BankAccount(10);
  BankAccount ba3 = new BankAccount(10);
  BankAccount ba4 = new BankAccount(10);
  BankAccount ba5 = new BankAccount(10);
  BankAccount ba6 = new BankAccount(6);
  BankAccount ba7 = new BankAccount(7);
  BankAccount ba8 = new BankAccount(8);

  ArrayList<Integer> i1 = new ArrayList<Integer>(Arrays.asList(1, 2));
  ArrayList<Integer> i2 = new ArrayList<Integer>(Arrays.asList(2, 4, 6));
  ArrayList<Integer> i3 = new ArrayList<Integer>(Arrays.asList(3, 5));

  ArrayList<Integer> i4 = new ArrayList<Integer>(Arrays.asList(1, 1, 1));
  ArrayList<Integer> i5 = new ArrayList<Integer>(Arrays.asList(2, 2));
  ArrayList<Integer> i6 = new ArrayList<Integer>(Arrays.asList(5, 5, 10));

  ArrayList<ArrayList<Integer>> aal = new ArrayList<ArrayList<Integer>>(Arrays.asList(i1, i2, i3));
  ArrayList<ArrayList<Integer>> aal2 = new ArrayList<ArrayList<Integer>>(
      Arrays.asList(i1, i2, i3, i4));
  ArrayList<ArrayList<Integer>> aal3 = new ArrayList<ArrayList<Integer>>(
      Arrays.asList(i1, i2, i3, i5));
  ArrayList<ArrayList<Integer>> aal4 = new ArrayList<ArrayList<Integer>>(
      Arrays.asList(i1, i2, i3, i6));

  ArrayList<BankAccount> baList1 = new ArrayList<BankAccount>(Arrays.asList(ba2, ba3, ba4));
  ArrayList<BankAccount> baList2 = new ArrayList<BankAccount>(Arrays.asList(ba2, ba3, ba2));
  ArrayList<BankAccount> baList3 = new ArrayList<BankAccount>(Arrays.asList(ba3, ba3, ba4));
  ArrayList<BankAccount> baList4 = new ArrayList<BankAccount>(Arrays.asList(ba2, ba5, ba4));

  Utils u = new Utils();

  // test processTransactions method
  void testProcessTransactions(Tester t) {
    u.processTransactions(ba1, aal);
    u.processTransactions(ba2, aal2);
    u.processTransactions(ba3, aal3);
    u.processTransactions(ba4, aal4);

    t.checkExpect(ba1.balance, 33);
    t.checkExpect(ba2.balance, 36);
    t.checkExpect(ba3.balance, 37);
    t.checkExpect(ba4.balance, 53);
  }

  // test anyRepeats method 
  void testAnyRepeats(Tester t) {

    t.checkExpect(u.anyRepeats(baList1), false);
    t.checkExpect(u.anyRepeats(baList2), true);
    t.checkExpect(u.anyRepeats(baList3), true);
    t.checkExpect(u.anyRepeats(baList4), false);
  }
}
