import tester.Tester;

//represents a list of strings
interface ILoString {
  // returns the nth item in the list, or "" if no such index exists in the list.
  String getNth(int n);

  // returns the nth item in the list, keeping track of the index so far
  String getNthHelper(int index, int sofar);
}

//represents an empty list of strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  // returns the nth item in the list, or "" if no such index exists in the list.
  public String getNth(int n) {
    return ("");
  }

  // returns the nth item in the list, keeping track of the index so far
  public String getNthHelper(int index, int sofar) {
    return ("");
  }

}

//represents a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  // returns the nth item in the list, keeping track of the index so far
  public String getNthHelper(int index, int sofar) {
    if (sofar == index) {
      return this.first;
    }
    else {
      return this.rest.getNthHelper(index, sofar + 1);
    }
  }

  // returns the nth item in the list, or "" if no such index exists in the list.
  public String getNth(int n) {
    return this.getNthHelper(n, 0);
  }

}

//represents a meal order for takeout
class TakeoutOrder {
  OrderCode code;
  ILoString entrees;
  ILoString sides;
  ILoString beverages;

  TakeoutOrder(OrderCode code, ILoString entrees, ILoString sides, ILoString beverages) {
    this.code = code;
    this.entrees = entrees;
    this.sides = sides;
    this.beverages = beverages;
  }

  // translates the order code to list of meal items 
  public ILoString translate() {
    return new ConsLoString(entrees.getNth(code.entreeNum),
        new ConsLoString(sides.getNth(code.sideNum),
            new ConsLoString(beverages.getNth(code.beverageNum), new MtLoString())));
  }
}

//represents a code that corresponds to meal items in an order 
class OrderCode {
  int entreeNum;
  int sideNum;
  int beverageNum;

  OrderCode(int entreeNum, int sideNum, int beverageNum) {
    this.entreeNum = entreeNum;
    this.sideNum = sideNum;
    this.beverageNum = beverageNum;
  }
}

class ExamplesOrders {
  ILoString mt = new MtLoString();

  ILoString entrees = new ConsLoString("chicken",
      new ConsLoString("pasta", new ConsLoString("salmon", this.mt)));

  ILoString sides = new ConsLoString("mashed potatoes", new ConsLoString("fries",
      new ConsLoString("broccoli", new ConsLoString("rice pilaf", this.mt))));

  ILoString beverages = new ConsLoString("lemonade", new ConsLoString("iced tea", this.mt));

  TakeoutOrder o = new TakeoutOrder(new OrderCode(2, 3, 1), this.entrees, this.sides,
      this.beverages);

  TakeoutOrder o2 = new TakeoutOrder(new OrderCode(1, 0, 1), this.entrees, this.sides,
      this.beverages);

  // test getNth method 
  boolean testGetNth(Tester t) {
    return t.checkExpect(this.entrees.getNth(2), "salmon")
        && t.checkExpect(this.sides.getNth(0), "mashed potatoes")
        && t.checkExpect(this.beverages.getNth(2), "");
  }

  // test getNthHelper method 
  boolean testGetNthHelper(Tester t) {
    return t.checkExpect(this.entrees.getNthHelper(2, 0), "salmon")
        && t.checkExpect(this.sides.getNthHelper(0, 0), "mashed potatoes")
        && t.checkExpect(this.beverages.getNthHelper(2, 0), "");
  }

  //test translate method
  boolean testTranslate(Tester t) {
    return t.checkExpect(this.o.translate(),
        new ConsLoString("salmon",
            new ConsLoString("rice pilaf", new ConsLoString("iced tea", new MtLoString()))))
        && t.checkExpect(this.o2.translate(), new ConsLoString("pasta",
            new ConsLoString("mashed potatoes", new ConsLoString("iced tea", new MtLoString()))));
  }
}
