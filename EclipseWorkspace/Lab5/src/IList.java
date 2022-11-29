//import java.util.function.BiFunction;
//import java.util.function.Function;
//import java.util.function.Predicate;

import tester.Tester;

interface IList<T> {

  //filter this IList using the given predicate
  IList<T> filter(IPred<T> pred);

  //map the given function onto every member of this IList
  <U> IList<U> map(IFunction<T, U> converter);

  //combine the items in this IList using the given function
  <U> U fold(IFunction2<T, U, U> converter, U initial);

}

class MtList<T> implements IList<T> {

  MtList() {
  }

  //filter this MtList using the given predicate
  public IList<T> filter(IPred<T> pred) {
    return new MtList<T>();
  }

  //map the given function onto every member of this MtList
  public <U> IList<U> map(IFunction<T, U> converter) {
    return new MtList<U>();
  }

  //combine the items in this MtList using the given function
  public <U> U fold(IFunction2<T, U, U> converter, U initial) {
    return initial;
  }

}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  //filter this ConsList using the given predicate
  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  //map the given function onto every member of this ConsList
  public <U> IList<U> map(IFunction<T, U> converter) {
    return new ConsList<U>(converter.apply(this.first), this.rest.map(converter));
  }

  //combine the items in this ConsList using the given function
  public <U> U fold(IFunction2<T, U, U> converter, U initial) {
    return converter.apply(this.first, this.rest.fold(converter, initial));
  }
}

// represent a month
class Month {
  String month;

  Month(String month) {
    this.month = month;
  }
}

// list examples and tests 
class ExamplesLists {
  ExamplesLists() {
  }

  Month january = new Month("January");
  Month february = new Month("February");
  Month march = new Month("March");
  Month april = new Month("April");
  Month may = new Month("May");
  Month june = new Month("June");
  Month july = new Month("July");
  Month august = new Month("August");
  Month september = new Month("September");
  Month october = new Month("October");
  Month november = new Month("November");
  Month december = new Month("December");

  Month fake1 = new Month("Cake");
  Month fake2 = new Month("Tree");
  Month fake3 = new Month("Jacket");
  Month fake4 = new Month("Taller");
  Month fake5 = new Month("Tangent");

  IList<Month> year = new ConsList<Month>(january,
      new ConsList<Month>(february,
          new ConsList<Month>(march,
              new ConsList<Month>(april,
                  new ConsList<Month>(may,
                      new ConsList<Month>(june,
                          new ConsList<Month>(july,
                              new ConsList<Month>(august, new ConsList<Month>(september,
                                  new ConsList<Month>(october, new ConsList<Month>(november,
                                      new ConsList<Month>(december, new MtList<Month>()))))))))))));

  IList<Month> fakeMonths = new ConsList<Month>(fake1,
      new ConsList<Month>(fake2, new ConsList<Month>(fake3,
          new ConsList<Month>(fake4, new ConsList<Month>(fake5, new MtList<Month>())))));

  IList<String> yearAbrs = new ConsList<String>("Jan",
      new ConsList<String>("Feb",
          new ConsList<String>("Mar",
              new ConsList<String>("Apr",
                  new ConsList<String>("May",
                      new ConsList<String>("Jun",
                          new ConsList<String>("Jul",
                              new ConsList<String>("Aug", new ConsList<String>("Sep",
                                  new ConsList<String>("Oct", new ConsList<String>("Nov",
                                      new ConsList<String>("Dec", new MtList<String>()))))))))))));

  IList<String> fakeAbr = new ConsList<String>("Cak",
      new ConsList<String>("Tre", new ConsList<String>("Jac",
          new ConsList<String>("Tal", new ConsList<String>("Tan", new MtList<String>())))));
  IList<Month> fakeTMonths = new ConsList<Month>(fake2,
      new ConsList<Month>(fake4, new ConsList<Month>(fake5, new MtList<Month>())));

  // test IList filter - create new list with only months that start with "T"
  boolean testIListFilter(Tester t) {
    return t.checkExpect(this.year.filter(new firstLetterT()), new MtList<Month>())
        && t.checkExpect(this.fakeMonths.filter(new firstLetterT()), fakeTMonths);
  }

  // test IList map - abbreviate all months to the first three letters 
  boolean testIListMap(Tester t) {
    return t.checkExpect(this.year.map(new MonthToAbr()), yearAbrs)
        && t.checkExpect(this.fakeMonths.map(new MonthToAbr()), fakeAbr);
  }

  // test IList fold - count the number of months that end in "er"
  boolean testIListFold(Tester t) {
    return t.checkExpect(this.year.fold(new totalMonthsEndInEr(), 0), 4)
        && t.checkExpect(this.fakeMonths.fold(new totalMonthsEndInEr(), 0), 1);

  }
}