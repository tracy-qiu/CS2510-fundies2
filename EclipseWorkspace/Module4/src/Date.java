import tester.Tester;

interface IDate {
}

class Date implements IDate {
  int year;
  int month;
  int day;

  int checkRange(int value, int low, int high, String message) {
    if (value >= low && value <= high) {
      return value;
    }
    else {
      throw new IllegalArgumentException(message);
    }
  }

  Date(int year, int month, int day) {
    this.year = new Utils().checkRange(year, 1500, 2100, "Invalid year: " + Integer.toString(year));
    this.month = new Utils().checkRange(month, 1, 12, "Invalid month: " + Integer.toString(month));
    this.day = new Utils().checkRange(day, 1, 31, "Invalid day: " + Integer.toString(day));
    //    if (year >= 1500 && year <= 2100) {
    //      this.year = year;
    //    }
    //    else {
    //      throw new IllegalArgumentException("Invalid year: " + Integer.toString(year));
    //    }
    //    if (month >= 1 && month <= 12) {
    //      this.month = month;
    //    }
    //    else {
    //      throw new IllegalArgumentException("Invalid month: " + Integer.toString(year));
    //    }
    //    if (day >= 1 && day <= 31) {
    //      this.day = day;
    //    }
    //    else {
    //      throw new IllegalArgumentException("Invalid day: " + Integer.toString(year));
    //    }
  }
}

class Utils {
  int checkRange(int val, int min, int max, String msg) {
    if (val >= min && val <= max) {
      return val;
    }
    else {
      throw new IllegalArgumentException(msg);
    }
  }
}

class ExamplesDates {
  ExamplesDates() {
  }

  //Good Dates 
  IDate d20100228 = new Date(2010, 2, 28); //February 28, 2010
  IDate d20091012 = new Date(2009, 10, 12);

  //Bad Date 
  // IDate dn303323 = new Date(-30, 33, 23);

  //Tester
  boolean testCheckConstructorException(Tester t) {
    return t.checkConstructorException(new IllegalArgumentException("Invalid year: -30"), "Date",
        -30, 33, 23)
        && t.checkConstructorException(new IllegalArgumentException("Invalid month: -1"), "Date",
            2000, -1, 23)
        && t.checkConstructorException(new IllegalArgumentException("Invalid day: -1"), "Date",
            2000, 1, -1);
  }
}