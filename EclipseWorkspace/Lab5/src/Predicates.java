interface IPred<T> {
  boolean apply(T t);
}

class firstLetterT implements IPred<Month> {
  //determines if the first letter of the months is "T"
  public boolean apply(Month m) {
    return m.month.substring(0, 1).equals("T");
  }
}

// A--> R
interface IFunction<A, R> {
  R apply(A t);
}

class MonthToAbr implements IFunction<Month, String> {
  // returns substring of first three letters of month 
  public String apply(Month m) {
    return m.month.substring(0, 3);
  }
}

// [A1, A2] --> R
interface IFunction2<A1, A2, R> {
  R apply(A1 arg1, A2 arg2);
}

class totalMonthsEndInEr implements IFunction2<Month, Integer, Integer> {
  // return count of how many months in list end in "er"
  public Integer apply(Month m, Integer count) {
    if (m.month.substring(m.month.length() - 2).equals("er")) {
      return 1 + count;
    }
    else {
      return count;
    }
  }

}