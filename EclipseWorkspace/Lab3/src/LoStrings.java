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
/*
 * different approach without accumulators 
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
*/

interface ILoString {
  // return the 0-based index of the first occurrence of str in this list
  // or -1 if it is not present
  int indexOf(String str);

  // solve indexOf, keep track of how many strings have been seen so far
  int indexOf(String str, int soFar);
}

class MtLoString implements ILoString {

  // no string to be found, return -1
  public int indexOf(String str) {
    return -1;
  }

  // no string to be found, return -1
  public int indexOf(String str, int soFar) {
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

  // find the index of str in this list
  public int indexOf(String str) {
    return this.indexOf(str, 0);
  }

  // find the index of str in this list, with soFar elements seen so far
  public int indexOf(String str, int soFar) {
    if (this.first.equals(str)) {
      return soFar;
    }
    else {
      return this.indexOf(str, soFar + 1);
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