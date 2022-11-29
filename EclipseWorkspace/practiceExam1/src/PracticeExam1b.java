import tester.Tester;

//represents a list of strings
interface ILoString {
  // a) That returns a new list with the elements whose position in the list is a multiple of the given argument "multiple". The first element in the list is at position 0. Make sure you test thoroughly. Class and method templates are not required. 
  ILoString multipleOf(int multiple);

  ILoString multipleOfHelper(int multiple, int counter);

  // c) undo interleave 
  // which takes a single ILoString and produces a pair of lists. For example,
  // list3.unzip() → new PairOfLists(list1, list2)
  // (Hint: you will likely need to design two helper methods on ILoString.)
  PairOfLists unzipFirst();

  PairOfLists unzipSecond();

  PairOfLists unzip();

}

//represents an empty list of strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  public ILoString multipleOfHelper(int multiple, int counter) {
    return new MtLoString();
  }

  public ILoString multipleOf(int multiple) {
    return new MtLoString();
  }

  public PairOfLists unzipFirst() {
    return new PairOfLists(new MtLoString(), new MtLoString());
  }

  public PairOfLists unzipSecond() {
    return new PairOfLists(new MtLoString(), new MtLoString());
  }

  public PairOfLists unzip() {
    return new PairOfLists(new MtLoString(), new MtLoString());
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

  //  public ILoString multipleOfHelper(ILoString list, int multiple, int counter) {
  //    if (counter == 0) {
  //      return new ConsLoString(this.first, list);
  //    } else {
  //      return multipleOfHelper(list, multiple, counter - 1);
  //    }
  //  }

  public ILoString multipleOfHelper(int multiple, int counter) {
    if (counter % multiple == 0) {
      return new ConsLoString(this.first, this.rest.multipleOfHelper(multiple, counter + 1));
    }
    else {
      return rest.multipleOfHelper(multiple, counter + 1);
    }
  }

  public ILoString multipleOf(int multiple) {
    return multipleOfHelper(multiple, 0);
  }

  public PairOfLists unzipFirst() {
    return this.rest.unzipSecond().addToFirst(this.first);
  }

  public PairOfLists unzipSecond() {
    return this.rest.unzipFirst().addToSecond(this.first);
  }

  // a b c d e f 

  public PairOfLists unzip() {
    return this.unzipFirst();
  }

}

//represents a pair of lists of strings
class PairOfLists {
  ILoString first;
  ILoString second;

  PairOfLists(ILoString first, ILoString second) {
    this.first = first;

    this.second = second;

  }

  // b) Produces a new pair of lists, with the given String added to 
  // the front of the first list of this pair
  PairOfLists addToFirst(String first) {
    return new PairOfLists(new ConsLoString(first, this.first), this.second);
  }

  // b) Produces a new pair of lists, with the given String added to
  // the front of the second list of this pair
  PairOfLists addToSecond(String second) {
    return new PairOfLists(this.first, new ConsLoString(second, this.second));
  }
}

class ExamplesLists {
  ILoString mtStrings = new MtLoString();
  ILoString list1 = new ConsLoString("A", new ConsLoString("B", this.mtStrings));
  ILoString list2 = new ConsLoString("D", new ConsLoString("E", this.mtStrings));
  ILoString list3 = new ConsLoString("A",
      new ConsLoString("D", new ConsLoString("B", new ConsLoString("E", this.mtStrings))));

  boolean testMultipleOf(Tester t) {
    return t.checkExpect(this.list3.multipleOf(3),
        new ConsLoString("A", new ConsLoString("E", new MtLoString())))
        && t.checkExpect(this.list3.multipleOf(2),
            new ConsLoString("A", new ConsLoString("B", new MtLoString())));
  }

  boolean testUnzip(Tester t) {
    return t.checkExpect(this.list3.unzip(), new PairOfLists(list1, list2))
        && t.checkExpect(this.mtStrings.unzip(), new PairOfLists(mtStrings, mtStrings));
  }

}