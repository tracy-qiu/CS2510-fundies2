import java.util.ArrayList;
import java.util.function.Predicate;

import tester.Tester;

class ArrayUtils {
  // filter out elements that do not pass the given predicate 
  <T> ArrayList<T> filter(ArrayList<T> arr, Predicate<T> pred) {
    // method without abstraction
    //    ArrayList<T> newArr = new ArrayList<T>();
    //    for (T o: arr) {
    //      if (pred.test(o)) {
    //        newArr.add(o);
    //      }
    //    } 
    //  return newArr;
    return customFilter(arr, pred, true);

  }

  // filter out elements that pass the given predicate 
  <T> ArrayList<T> filterNot(ArrayList<T> arr, Predicate<T> pred) {
    // method without abstraction
    //    ArrayList<T> newArr = new ArrayList<T>();
    //    for (T o: arr) {
    //      if (!pred.test(o)) {
    //        newArr.add(o);
    //      }
    //    } 
    //    return newArr;
    return customFilter(arr, pred, false);
  }

  // filter out elements based on the given predicate and keepPassing
  <T> ArrayList<T> customFilter(ArrayList<T> arr, Predicate<T> pred, boolean keepPassing) {
    ArrayList<T> newArr = new ArrayList<T>();
    for (T o : arr) {
      if (pred.test(o) && keepPassing) {
        newArr.add(o);
      }
      else if (!pred.test(o) && !keepPassing) {
        newArr.add(o);
      }
    }
    return newArr;
  }

  // modify the array to remove elements that fail the given predicate
  <T> void removeFailing(ArrayList<T> arr, Predicate<T> pred) {
    // method without abstraction
    //    ArrayList<T> thingsToRemove = new ArrayList<T>();
    //    for (T o : arr) {
    //      if (!pred.test(o)) {
    //        thingsToRemove.add(o);
    //      }
    //    }
    //    for (T o : thingsToRemove) {
    //      arr.remove(o);
    //    }
    removeCustom(arr, pred, true);
  }

  // modify the array to remove elements that pass the given predicate
  <T> void removePassing(ArrayList<T> arr, Predicate<T> pred) {
    // method without abstraction
    //    ArrayList<T> thingsToRemove = new ArrayList<T>();
    //    for (T o : arr) {
    //      if (pred.test(o)) {
    //        thingsToRemove.add(o);
    //      }
    //    }
    //    for (T o : thingsToRemove) {
    //      arr.remove(o);
    //    }
    removeCustom(arr, pred, false);
  }

  // modify the array to remove elements based on the given predicate and removeFailing 
  <T> void removeCustom(ArrayList<T> arr, Predicate<T> pred, boolean removeFailing) {
    ArrayList<T> thingsToRemove = new ArrayList<T>();
    for (T o : arr) {
      if (pred.test(o) && !removeFailing) {
        thingsToRemove.add(o);
      }
      else if (!pred.test(o) && removeFailing) {
        thingsToRemove.add(o);
      }
    }
    for (T o : thingsToRemove) {
      arr.remove(o);
    }
  }

  // interweave lists in order ex. {A, B, C, D} { x, y, z} --> {A, x, B, y, C, z, D}
  <T> ArrayList<T> interweave(ArrayList<T> arr1, ArrayList<T> arr2) {
    // method without abstraction 
    //    ArrayList<T> newArr = new ArrayList<T>();
    //    ArrayList<T> smallArr = new ArrayList<T>();
    //    ArrayList<T> bigArr = new ArrayList<T>();
    //    if (arr1.size() < arr2.size()) {
    //      smallArr = arr1;
    //      bigArr = arr2;
    //    }
    //    else {
    //      smallArr = arr2;
    //      bigArr = arr1;
    //    }
    //    for (int i = 0; i < smallArr.size(); i++) {
    //      newArr.add(arr1.get(i));
    //      newArr.add(arr2.get(i));
    //    }
    //    for (int i = smallArr.size(); i < bigArr.size(); i++) {
    //      newArr.add(bigArr.get(i));
    //    }
    //    return newArr;
    return interweave(arr1, arr2, 1, 1);
  }

  // interweave lists in order taking given number of values from each list 
  // ex. interweave(arr1, arr2, 2, 1) 
  // arr1 = {A, B, C, D, E, F} arr2 = {x, y} --> {A, B, x, C, D y, E, F, z}
  <T> ArrayList<T> interweave(ArrayList<T> arr1, ArrayList<T> arr2, int getFrom1, int getFrom2) {
    ArrayList<T> newArr = new ArrayList<T>();
    ArrayList<T> bigArr = new ArrayList<T>();
    if (arr1.size() > arr2.size()) {
      bigArr = arr1;
    }
    else {
      bigArr = arr2;
    }
    // counter for array 1 index
    int index1 = 0;
    // counter for array 2 index
    int index2 = 0;
    while (index1 < arr1.size() && index2 < arr2.size()) {
      for (int x = 0; x < getFrom1; x++) {
        newArr.add(arr1.get(index1));
        index1++;
      }
      for (int x = 0; x < getFrom2; x++) {
        newArr.add(arr2.get(index2));
        index2++;
      }
    }
    // counter for list with bigger index 
    int newIndex;
    if (index1 > index2) {
      newIndex = index1;
    }
    else {
      newIndex = index2;
    }
    for (int x = newIndex; x < bigArr.size(); x++) {
      newArr.add(bigArr.get(newIndex));
      newIndex++;
    }
    return newArr;
  }
}

// Predicate that determines if the first letter of the given string is "T"
class FirstLetterT implements Predicate<String> {
  //determines if the first letter of the months is "T"
  public boolean test(String s) {
    return s.substring(0, 1).equals("T");
  }
}

//Predicate that determines if the first letter of the given string is "T"
class LongerThanFive implements Predicate<String> {
  //determines if the first letter of the months is "T"
  public boolean test(String s) {
    return s.length() > 5;
  }
}

// class for array list examples and tests 
class ExampleArrayLists {
  ArrayUtils util = new ArrayUtils();

  // test filter methods 
  void testFilter(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    someStrings.add("Trains");
    someStrings.add("Box");
    someStrings.add("Tree");
    someStrings.add("Cookies");

    ArrayList<String> someStrings2 = new ArrayList<String>();
    someStrings2.add("Amelia");
    someStrings2.add("John");
    someStrings2.add("Tanner");

    ArrayList<String> stringResult1 = new ArrayList<String>();
    stringResult1.add("Trains");
    stringResult1.add("Tree");
    ArrayList<String> stringResult2 = new ArrayList<String>();
    stringResult2.add("Box");
    stringResult2.add("Cookies");
    ArrayList<String> stringResult3 = new ArrayList<String>();
    stringResult3.add("Trains");
    stringResult3.add("Cookies");
    ArrayList<String> stringResult4 = new ArrayList<String>();
    stringResult4.add("Box");
    stringResult4.add("Tree");
    ArrayList<String> stringResult5 = new ArrayList<String>();
    stringResult5.add("Tanner");
    ArrayList<String> stringResult6 = new ArrayList<String>();
    stringResult6.add("Amelia");
    stringResult6.add("John");
    ArrayList<String> stringResult7 = new ArrayList<String>();
    stringResult7.add("Amelia");
    stringResult7.add("Tanner");
    ArrayList<String> stringResult8 = new ArrayList<String>();
    stringResult8.add("John");
    t.checkExpect(util.customFilter(someStrings, new FirstLetterT(), true), stringResult1);
    t.checkExpect(util.customFilter(someStrings, new FirstLetterT(), false), stringResult2);
    t.checkExpect(util.customFilter(someStrings, new LongerThanFive(), true), stringResult3);
    t.checkExpect(util.customFilter(someStrings, new LongerThanFive(), false), stringResult4);
    t.checkExpect(util.customFilter(someStrings2, new FirstLetterT(), true), stringResult5);
    t.checkExpect(util.customFilter(someStrings2, new FirstLetterT(), false), stringResult6);
    t.checkExpect(util.customFilter(someStrings2, new LongerThanFive(), true), stringResult7);
    t.checkExpect(util.customFilter(someStrings2, new LongerThanFive(), false), stringResult8);

  }

  // test remove methods 
  void testRemove(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    someStrings.add("Trains");
    someStrings.add("Box");
    someStrings.add("Tree");
    someStrings.add("Cookies");

    util.removeFailing(someStrings, new FirstLetterT());
    t.checkExpect(someStrings.get(0), "Trains");
    t.checkExpect(someStrings.get(1), "Tree");

    ArrayList<String> someStrings2 = new ArrayList<String>();
    someStrings2.add("Trains");
    someStrings2.add("Box");
    someStrings2.add("Tree");
    someStrings2.add("Cookies");
    util.removePassing(someStrings2, new LongerThanFive());
    t.checkExpect(someStrings2.get(0), "Box");
    t.checkExpect(someStrings2.get(1), "Tree");

    ArrayList<String> someStrings3 = new ArrayList<String>();
    someStrings3.add("Animals");
    someStrings3.add("Bottle");
    someStrings3.add("Track");
    someStrings3.add("Tacos");
    someStrings3.add("Taller");
    util.removeCustom(someStrings3, new FirstLetterT(), true);
    t.checkExpect(someStrings3.get(0), "Track");
    t.checkExpect(someStrings3.get(1), "Tacos");
    t.checkExpect(someStrings3.get(2), "Taller");

    ArrayList<String> someStrings4 = new ArrayList<String>();
    someStrings4.add("Animals");
    someStrings4.add("Bottle");
    someStrings4.add("Track");
    someStrings4.add("Tacos");
    someStrings4.add("Taller");
    util.removeCustom(someStrings4, new LongerThanFive(), false);
    t.checkExpect(someStrings4.get(0), "Track");
    t.checkExpect(someStrings4.get(1), "Tacos");
  }

  // test interweave methods 
  void testInterweave(Tester t) {
    ArrayList<String> arr1 = new ArrayList<String>();
    ArrayList<String> arr2 = new ArrayList<String>();
    arr1.add("A");
    arr1.add("B");
    arr1.add("C");
    arr1.add("D");
    arr1.add("E");
    arr1.add("F");
    arr2.add("w");
    arr2.add("x");
    arr2.add("y");
    arr2.add("z");
    ArrayList<String> result1 = new ArrayList<String>();
    result1.add("A");
    result1.add("w");
    result1.add("B");
    result1.add("x");
    result1.add("C");
    result1.add("y");
    result1.add("D");
    result1.add("z");
    result1.add("E");
    result1.add("F");

    ArrayList<String> result2 = new ArrayList<String>();
    result2.add("A");
    result2.add("B");
    result2.add("C");
    result2.add("w");
    result2.add("x");
    result2.add("D");
    result2.add("E");
    result2.add("F");
    result2.add("y");
    result2.add("z");

    ArrayList<String> result3 = new ArrayList<String>();
    result3.add("w");
    result3.add("A");
    result3.add("x");
    result3.add("B");
    result3.add("y");
    result3.add("C");
    result3.add("z");
    result3.add("D");
    result3.add("E");
    result3.add("F");
    t.checkExpect(util.interweave(arr1, arr2), result1);
    t.checkExpect(util.interweave(arr1, arr2, 1, 1), result1);
    t.checkExpect(util.interweave(arr1, arr2, 3, 2), result2);
    t.checkExpect(util.interweave(arr2, arr1), result3);
  }
}
