import java.util.ArrayList;

import tester.Tester;

class Util {
  Util() {
  }

  <T> void swap(ArrayList<T> arrayList, int index1, int index2) {

  }
}

class ArrayListExamples {
  ArrayListExamples() {
  }

  void testArrayListAddGet(Tester t) {
    ArrayList<String> stringList = new ArrayList<String>();
    t.checkExpect(stringList.size(), 0);

    stringList.add("1st Item");
    t.checkExpect(stringList.size(), 1);

    stringList.add("2nd Item");
    t.checkExpect(stringList.size(), 2);

    stringList.remove(1);
    t.checkExpect(stringList.size(), 1);

    t.checkExpect(stringList.get(0), "1st Item");
    stringList.add(0, "0th Item");
    t.checkExpect(stringList.size(), 2);
    t.checkExpect(stringList.get(0), "0th Item");

  }
}