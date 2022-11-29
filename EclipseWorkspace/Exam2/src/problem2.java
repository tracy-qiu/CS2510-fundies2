import java.util.ArrayList;
import java.util.Arrays;

import tester.Tester;

//represents a customer at the bank
class Customer {
  String name;

  ArrayList<Integer> pin; //a secret code for a customer to login

  Customer(ArrayList<Integer> pin) {
    this.name = "";
    this.pin = pin;
  }

  // only set the name if the pin is correct 
  void setName(int n0, int n1, int n2, int n3, String name) {
    if (n0 == pin.get(0) && n1 == pin.get(1) && n2 == pin.get(2) && n3 == pin.get(3)) {
      this.name = name;
    }
  }

  //reset the pin number of this customer
  void resetPin(int n0, int n1, int n2, int n3) {
    this.pin.set(0, n0);
    this.pin.set(1, n1);
    this.pin.set(2, n2);
    this.pin.set(3, n3);
  }
}

class ExampleCustomers {
  Customer c1 = new Customer(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)));
  
  c1.setName(1, 2, 3, 4, "Jack");

  void testGet(Tester t) {
    t.checkExpect(c1.pin.get(0), 1);
  }

}