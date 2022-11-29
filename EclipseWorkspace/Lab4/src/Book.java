// 5/23/20 
//CS 2510, Lab 4 

import tester.Tester;

//    +-------+
//               | IBook |
//               +-------+
//                  / \
//                  ---
//                   |
//       ---------------------------------------
//       |                  |                  |
//+---------------+  +---------------+  +---------------+
//| Book          |  | RefBook       |  | AudioBook     |
//+---------------+  +---------------+  +---------------+
//| String title  |  | String title  |  | String title  |
//| String author |  | int dayTaken  |  | String author |
//| int dayTaken  |  +---------------+  | int dayTaken  |
//+---------------+                     +---------------+

interface IBook {
  // to compute the number of days the a book is overdue - negative if not due yet 
  public int daysOverdue(int today);

  // to check if a book is overdue
  public boolean isOverdue(int givenDay);

  // to compute the fine of an overdue book 
  public double computeFine(int givenDay);
}

// to represent a book 
abstract class ABook implements IBook {
  String title;
  int dayTaken;

  ABook(String title, int dayTaken) {
    this.title = title;
    this.dayTaken = dayTaken;
  }

  // to compute the number of days this book is overdue
  public int daysOverdue(int today) {
    return today - dayTaken - 14;
  }

  // to check if this book is overdue 
  public boolean isOverdue(int givenDay) {
    return (givenDay - dayTaken > 14);
  }

  // to compute fine if this book is overdue 
  public abstract double computeFine(int givenDay);
}

// to represent a book 
class Book extends ABook {
  String author;

  Book(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }

  /*  TEMPLATE 
  Fields:
  ... this.title ...           -- String
  ... this.author ...          -- String
  ... this.dayTaken ...        -- int
  Methods:
  ... this.daysOverdue(int) ...        -- int 
  ... this.isOverdue(int) ...          -- boolean
  ... this.computeFine(int) ...        -- double
  Methods for fields:
  */

  // to compute fine if this book is overdue 
  public double computeFine(int givenDay) {
    if (givenDay - dayTaken > 14) {
      return (givenDay - dayTaken - 14) * 0.1;
    }
    else {
      return 0.0;
    }
  }
}

// to represent a reference book 
class RefBook extends ABook {

  RefBook(String title, int dayTaken) {
    super(title, dayTaken);
  }

  /*  TEMPLATE 
  Fields:
  ... this.title ...           -- String
  ... this.dayTaken ...        -- int
  Methods:
  ... this.daysOverdue(int) ...        -- int 
  ... this.isOverdue(int) ...          -- boolean
  ... this.computeFine(int) ...        -- double
  Methods for fields:
  */

  @Override
  // to compute the number of days this book is overdue
  public int daysOverdue(int today) {
    return today - dayTaken - 2;
  }

  @Override
  // to check if this book is overdue 
  public boolean isOverdue(int givenDay) {
    return (givenDay - dayTaken > 2);
  }

  // to compute fine if this book is overdue 
  public double computeFine(int givenDay) {
    if (givenDay - dayTaken > 2) {
      return (givenDay - dayTaken - 2) * 0.1;
    }
    else {
      return 0.0;
    }
  }
}

// to represent an audiobook 
class AudioBook extends ABook {
  String author;

  AudioBook(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }

  /*  TEMPLATE 
  Fields:
  ... this.title ...           -- String
  ... this.author ...          -- String
  ... this.dayTaken ...        -- int
  Methods:
  ... this.daysOverdue(int) ...        -- int 
  ... this.isOverdue(int) ...          -- boolean
  ... this.computeFine(int) ...        -- double
  Methods for fields:
  */

  // to compute fine if this book is overdue 
  public double computeFine(int givenDay) {
    if (givenDay - dayTaken > 14) {
      return (givenDay - dayTaken - 14) * 0.2;
    }
    else {
      return 0.0;
    }
  }
}

// book examples and tests 
class ExamplesBooks {
  ExamplesBooks() {
  }

  IBook b = new Book("Harry Potter", "JK Rowling", 7820);
  IBook rb = new RefBook("Animals", 7835);
  IBook ab = new AudioBook("Finanical Literacy", "James Scott", 7830);

  boolean testDaysOverdue(Tester t) {
    return t.checkExpect(this.b.daysOverdue(7840), 6) && t.checkExpect(this.rb.daysOverdue(7840), 3)
        && t.checkExpect(this.ab.daysOverdue(7840), -4);
  }

  boolean testIsOverdue(Tester t) {
    return t.checkExpect(this.b.isOverdue(7840), true)
        && t.checkExpect(this.rb.isOverdue(7834), false)
        && t.checkExpect(this.ab.isOverdue(7840), false);
  }

  boolean testComputeFine(Tester t) {
    return t.checkInexact(this.b.computeFine(7840), 0.6, 0.01)
        && t.checkInexact(this.b.computeFine(7833), 0.0, 0.01)
        && t.checkInexact(this.rb.computeFine(7845), 0.8, 0.01)
        && t.checkInexact(this.rb.computeFine(7834), 0.0, 0.01)
        && t.checkInexact(this.ab.computeFine(7850), 1.2, 0.01)
        && t.checkInexact(this.ab.computeFine(7840), 0.0, 0.01);
  }
}
