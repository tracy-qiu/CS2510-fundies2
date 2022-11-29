import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import tester.Tester;

class AllPredicates<T> implements Predicate<T> {
  ArrayList<Predicate<T>> predArr;

  // tests if for all predicates in the array returns true
  AllPredicates(ArrayList<Predicate<T>> predArr) {
    this.predArr = predArr;
  }

  @Override
  public boolean test(T t) {
    for (Predicate<T> pred : predArr) {
      if (!pred.test(t)) {
        return false;
      }
    }
    return true;
  }
}

//Predicate that determines if the first letter of the given string is "T"
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

//Predicate that determines if the first letter of the given string is "T"
class SevenLong implements Predicate<String> {
  //determines if the first letter of the months is "T"
  public boolean test(String s) {
    return s.length() == 7;
  }
}

// tests and examples for predicates
class ExamplesPredicates {

  ArrayList<String> arr1 = new ArrayList<String>(Arrays.asList("Traceys", "Tractor", "Trainss"));
  ArrayList<String> arr2 = new ArrayList<String>(Arrays.asList("Graceys", "Fractor", "Trainss"));

  ArrayList<Predicate<String>> predList1 = new ArrayList<Predicate<String>>(
      Arrays.asList(new FirstLetterT(), new LongerThanFive(), new SevenLong()));

  void testAllPredicates(Tester t)

  arr1 AllPredicates(predList1) --> true

  arr2 AllPredicates(predList1) --> false
//  void testAllPredicates(Tester t) {
//    t.checkExpect(test(arr1), null)
//  }

}