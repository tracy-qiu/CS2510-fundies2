// CS 2510, Assignment 3
// 5/20/22

import tester.Tester;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // produces a list where all occurrences of the first string are replaced by
  // the second string
  ILoString findAndReplace(String lookingFor, String replaceWith);

  // determines whether the list contains the string we are looking for
  int anyDupesHelper(String lookingFor);

  // determines whether this list contains any string more than once
  boolean anyDupes();

  // inserts a string in list based on alphabetical order
  ILoString insert(String other);

  // sort a list in alphabetical order
  ILoString sort();

  // determines if the given string is in the right alphabetical order
  boolean isSortedHelper(String other);

  // determines if the list of strings is in alphabetical order
  boolean isSorted();

  // takes this list and given list of strings and produces a list where the 1st,
  // 3rd, 5th... elements are from this list and the 2nd, 4th, 6th... elements are
  // from the given list
  ILoString interleave(ILoString given);

  // helper function that passes around the original list to get the elements from
  // both the this and given list
  ILoString interleaveHelper(ILoString original);

  // takes sorted lists of strings and produces a sorted list of strings that
  // contains all items in both lists including duplicates
  ILoString merge(ILoString given);

  // recursively calls the rest of the original list to merge with the given list
  ILoString mergeHelper(ILoString original);

  // produces a string where all the strings are concatenate in reverse order
  String reverseConcat();

  // determines if the list contains pairs of identical strings for example list:
  // cat cat dog dog rat rat
  boolean isDoubledList();

  // produces a new list of every other element in the list
  ILoString everyOtherElement();

  // produces a new list of every other element in the list
  ILoString elementsHelper();

  // produces a new list of the strings in reverse order, initializes the empty
  // string
  ILoString reversed();

  // builds the reversed list
  ILoString reversedHelper(ILoString newList);

  // determines if the list contains the same works reading the list in either order
  boolean isPalindromeList();

}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  public ILoString findAndReplace(String lookingFor, String replaceWith) {
    return new MtLoString();
  }

  public int anyDupesHelper(String lookingFor) {
    return 0;
  }

  public boolean anyDupes() {
    return false;
  }

  public ILoString insert(String other) {
    return new ConsLoString(other, new MtLoString());
  }

  public ILoString sort() {
    return new MtLoString();
  }

  public boolean isSortedHelper(String other) {
    return true;
  }

  public boolean isSorted() {
    return true;
  }

  public ILoString interleave(ILoString given) {
    return given;
  }

  public ILoString interleaveHelper(ILoString original) {
    return original;
  }

  public ILoString mergeHelper(ILoString original) {
    return new MtLoString();
  }

  public ILoString merge(ILoString given) {
    return given;
  }

  public String reverseConcat() {
    return "";
  }

  public boolean isDoubledList() {
    return true;
  }

  public ILoString elementsHelper() {
    return new MtLoString();
  }

  public ILoString everyOtherElement() {
    return new MtLoString();
  }

  public ILoString reversedHelper(ILoString newList) {
    return newList;
  }

  public ILoString reversed() {
    return new MtLoString();
  }

  public boolean isPalindromeList() {
    return true;
  }

}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE FIELDS: 
   * ... this.first ... -- String
   * ... this.rest ... -- ILoString
   * 
   * METHODS 
   * ... this.combine() ... -- String
   * 
   * METHODS FOR FIELDS 
   * ... this.first.concat(String) ... -- String 
   * ... this.first.compareTo(String) ... -- int 
   * ... this.rest.combine() ... -- String
   * 
   */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  public ILoString findAndReplace(String lookingFor, String replaceWith) {
    if (this.first == lookingFor) {
      return new ConsLoString(replaceWith, this.rest.findAndReplace(lookingFor, replaceWith));
    }
    else {
      return new ConsLoString(first, this.rest.findAndReplace(lookingFor, replaceWith));
    }
  }

  public int anyDupesHelper(String lookingFor) {
    if (this.first == lookingFor) {
      return 1 + this.rest.anyDupesHelper(lookingFor);
    }
    else {
      return this.rest.anyDupesHelper(lookingFor);
    }
  }

  public boolean anyDupes() {
    if (rest.anyDupesHelper(first) > 0) {
      return true;
    }
    else {
      return this.rest.anyDupes();
    }
  }

  public ILoString insert(String other) {
    if (this.first.toLowerCase().compareTo(other.toLowerCase()) < 0) {
      return new ConsLoString(this.first, this.rest.insert(other));
    }
    else {
      return new ConsLoString(other, this);
    }
  }

  public ILoString sort() {
    return (this.rest.sort()).insert(this.first);
  }

  public boolean isSortedHelper(String other) {
    if (this.first.toLowerCase().compareTo(other.toLowerCase()) < 0) {
      return false;
    }
    else {
      return this.rest.isSortedHelper(other);
    }
  }

  public boolean isSorted() {
    if (rest.isSortedHelper(first)) {
      return this.rest.isSorted();
    }
    else {
      return false;
    }
  }

  public ILoString interleaveHelper(ILoString original) {
    return new ConsLoString(this.first, original.interleave(this.rest));
  }

  public ILoString interleave(ILoString given) {
    return new ConsLoString(this.first, given.interleaveHelper(this.rest));
  }

  public ILoString mergeHelper(ILoString original) {
    return this.rest.merge(original);
  }

  public ILoString merge(ILoString given) {
    return mergeHelper(given.insert(first));
  }

  public String reverseConcat() {
    return (this.rest.reverseConcat().concat(this.first));
  }

  public ILoString elementsHelper() {
    return this.rest.everyOtherElement();
  }

  public ILoString everyOtherElement() {
    return new ConsLoString(this.first, this.rest.elementsHelper());
  }

  public boolean isDoubledList() {
    return (this.everyOtherElement().combine().equals((this.rest.everyOtherElement().combine())));
  }

  public ILoString reversedHelper(ILoString newList) {
    return rest.reversedHelper(new ConsLoString(this.first, newList));
  }

  public ILoString reversed() {
    return this.rest.reversedHelper(new ConsLoString(this.first, new MtLoString()));
  }

  public boolean isPalindromeList() {
    return this.interleave(this.reversed()).isDoubledList();
  }

}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));
  ILoString hello = new ConsLoString("hello ", new ConsLoString("Mary ",
      new ConsLoString("hello ", new ConsLoString("Joe ", new MtLoString()))));
  ILoString abc = new ConsLoString("apples ",
      new ConsLoString("Bananas ", new ConsLoString("carrots ", new MtLoString())));
  ILoString xyz = new ConsLoString("x ",
      new ConsLoString("y ", new ConsLoString("z ", new MtLoString())));
  ILoString bye = new ConsLoString("bye ", new ConsLoString("bye ",
      new ConsLoString("Annie ", new ConsLoString("Annie ", new MtLoString()))));
  ILoString celebrate = new ConsLoString("Cheers ", new ConsLoString("everyone ",
      new ConsLoString("horray ", new ConsLoString("horray ", new MtLoString()))));
  ILoString abcba = new ConsLoString("a ", new ConsLoString("b ",
      new ConsLoString("c ", new ConsLoString("b ", new ConsLoString("a ", new MtLoString())))));

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.")
        && t.checkExpect(this.mary.findAndReplace("had", "has"),
            new ConsLoString("Mary ",
                new ConsLoString("had ",
                    new ConsLoString("a ",
                        new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString()))))))
        && t.checkExpect(this.mary.findAndReplace("lamb.", "cat."),
            new ConsLoString("Mary ",
                new ConsLoString("had ",
                    new ConsLoString("a ",
                        new ConsLoString("little ", new ConsLoString("cat.", new MtLoString()))))))
        && t.checkExpect(this.mary.anyDupesHelper("Mary "), 1)
        && t.checkExpect(this.hello.anyDupesHelper("hello "), 2)
        && t.checkExpect(this.mary.anyDupes(), false) && t.checkExpect(this.hello.anyDupes(), true)
        && t.checkExpect(this.abc.insert("air "), new ConsLoString("air ", abc))
        && t.checkExpect(this.abc.sort(), abc)
        && t.checkExpect(this.hello.sort(),
            new ConsLoString("hello ",
                new ConsLoString("hello ",
                    new ConsLoString("Joe ", new ConsLoString("Mary ", new MtLoString())))))
        && t.checkExpect(this.abc.isSortedHelper("air "), true)
        && t.checkExpect(this.abc.isSortedHelper("cake"), false)
        && t.checkExpect(this.abc.isSorted(), true) && t.checkExpect(this.hello.isSorted(), false)
        && t.checkExpect(this.abc.interleave(xyz),
            new ConsLoString("apples ",
                new ConsLoString("x ", new ConsLoString("Bananas ",
                    new ConsLoString("y ",
                        new ConsLoString("carrots ", new ConsLoString("z ", new MtLoString())))))))
        && t.checkExpect(this.mary.interleave(xyz), new ConsLoString("Mary ", new ConsLoString("x ",
            new ConsLoString("had ", new ConsLoString("y ", new ConsLoString("a ",
                new ConsLoString("z ",
                    new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))))))))
        && t.checkExpect(this.abc.merge(xyz),
            new ConsLoString("apples ",
                new ConsLoString("Bananas ",
                    new ConsLoString("carrots ",
                        new ConsLoString("x ",
                            new ConsLoString("y ", new ConsLoString("z ", new MtLoString())))))))
        && t.checkExpect(this.xyz.merge(xyz),
            new ConsLoString("x ",
                new ConsLoString("x ",
                    new ConsLoString("y ",
                        new ConsLoString("y ",
                            new ConsLoString("z ", new ConsLoString("z ", new MtLoString())))))))
        && t.checkExpect(this.abc.reverseConcat(), "carrots Bananas apples ")
        && t.checkExpect(this.xyz.reverseConcat(), "z y x ")
        && t.checkExpect(this.mary.everyOtherElement(),
            new ConsLoString("Mary ",
                new ConsLoString("a ", new ConsLoString("lamb.", new MtLoString()))))
        && t.checkExpect(this.xyz.everyOtherElement(),
            new ConsLoString("x ", new ConsLoString("z ", new MtLoString())))
        && t.checkExpect(this.bye.everyOtherElement(),
            new ConsLoString("bye ", new ConsLoString("Annie ", new MtLoString())))
        && t.checkExpect(this.bye.everyOtherElement().combine(), "bye Annie ")
        && t.checkExpect(this.bye.isDoubledList(), true)
        && t.checkExpect(this.celebrate.isDoubledList(), false)
        && t.checkExpect(this.hello.isDoubledList(), false)
        && t.checkExpect(this.xyz.reversed(),
            new ConsLoString("z ",
                new ConsLoString("y ", new ConsLoString("x ", new MtLoString()))))
        && t.checkExpect(this.abcba.reversed(), abcba)
        && t.checkExpect(this.abcba.isPalindromeList(), true)
        && t.checkExpect(this.abc.isPalindromeList(), false);
  }
}
