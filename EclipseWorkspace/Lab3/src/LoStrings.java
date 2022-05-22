//import tester.Tester;
//interface IGreeting {
//}
//class Surprise implements IGreeting {
//  String surprise() {
//    return "surprise!";
//  }
//}
//class ExamplesGreeting {
//  IGreeting myGreeting = new Surprise();
//
//  boolean testSurprise(Tester t) {
//    return t.checkExpect(myGreeting.surprise(), "surprise!");
//  }
//} 

import tester.Tester;

interface ILoString {
  // return the 0-based index of the first occurrence of str in this list
  // or -1 if it is not present
  int indexOf(String str);

}

class MtLoString implements ILoString {
  public int indexOf(String str) {
    return -1;
  }
}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  public int indexOf(String str) {
    if (this.first == str) {
      return 0;
    }
    else {
      if (rest.indexOf(str) == -1) {
        return -1;
      }
      else {
        return 1 + rest.indexOf(str);
      }
    }
  }
}

class ExamplesStrings {
  ILoString mt = new MtLoString();
  ILoString appleBeeApple = new ConsLoString("apple",
      new ConsLoString("bee", new ConsLoString("apple", mt)));

  boolean testIndexOf(Tester t) {
    return t.checkExpect(this.mt.indexOf("apple"), -1)
        && t.checkExpect(this.appleBeeApple.indexOf("orange"), -1)
        && t.checkExpect(this.appleBeeApple.indexOf("apple"), 0)
        && t.checkExpect(this.appleBeeApple.indexOf("bee"), 1);
  }
}