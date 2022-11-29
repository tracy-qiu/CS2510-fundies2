import java.util.ArrayList;
import java.util.Arrays;

import tester.Tester;

/*
 * which checks if source contains a sub-sequence of the integers in sequence. 
 * The sub-sequence should contain the same numbers as sequence in the same order. 
 * You may assume that neither list is null and that this method is defined in a Utils class.
 * Do not use recursion; use appropriate loop forms instead.
 */

class Utils {
  Utils() {
  }

  //  boolean containsSequence(ArrayList<Integer> source, ArrayList<Integer> sequence) {
  //    int counter = 0;
  //    while (counter < sequence.size()) {
  //      for (int i = 0; i < source.size(); i++) {
  //        if (source.get(i) == sequence.get(icounter) {
  //          counter++;
  //          for (int j = i + 1; j < source.size(); j++) {
  //            if (source.get(i) == sequence.get(counter)) {
  //              counter++;
  //            }
  //            else {
  //              return false;
  //            }
  //          }
  //        }
  //        return true;
  //      }
  //    }
  //  }

  boolean containsSequence(ArrayList<Integer> source, ArrayList<Integer> sequence) {
    //    int counter = 0;
    while (source.size() > sequence.size()) {
      if (source.get(0) == sequence.get(0)) {
        for (int i = 1; i < sequence.size(); i++) {
          if (!(source.get(i) == sequence.get(i))) {
            return false;
          }
        }
        return true;
      }
      else {
        source.remove(source.get(0));
      }
    }
    return false;
  }
}

class ExamplesSequences {
  Utils u = new Utils();

  ArrayList<Integer> ints = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));
  ArrayList<Integer> seq1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
  ArrayList<Integer> seq2 = new ArrayList<Integer>(Arrays.asList(3, 4, 5));
  ArrayList<Integer> seq3 = new ArrayList<Integer>(Arrays.asList(2, 3, 6));

  void testSequence(Tester t) {
    t.checkExpect(u.containsSequence(ints, seq1), true);
    t.checkExpect(u.containsSequence(ints, seq2), true);
    t.checkExpect(u.containsSequence(ints, seq3), false);

  }
}