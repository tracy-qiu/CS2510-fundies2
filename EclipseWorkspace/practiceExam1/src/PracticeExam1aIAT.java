import tester.Tester;

//represents an ancestor tree
interface IAT {
  boolean containsName(String givenName);

  boolean duplicateNames();

  IAT youngerIAThelper(IAT other, int yob);

  IAT youngerIAT(IAT other);

  IAT youngestAncInGen(int gen);

}

//represents a leaf on an ancestor tree
class Unknown implements IAT {
  Unknown() {
  }

  public boolean containsName(String givenName) {
    return false;
  }

  public boolean duplicateNames() {
    return false;
  }

  public IAT youngerIAThelper(IAT other, int yob) {
    return other;
  }

  // c) returns the youngest ancestor at the given generation for this person
  public IAT youngerIAT(IAT other) {
    return other;
  }

  public IAT youngestAncInGen(int gen) {
    return new Unknown();
  }
}

//represents a person in an ancestor tree
class Person implements IAT {
  String name;
  int yob;
  IAT dad;
  IAT mom;

  Person(String name, int yob, IAT dad, IAT mom) {
    this.name = name;
    this.yob = yob;
    this.dad = dad;
    this.mom = mom;
  }

  // a) returns true if this IAT contains some Person with the given name

  public boolean containsName(String givenName) {
    return ((this.name == givenName) || this.dad.containsName(givenName)
        || this.mom.containsName(givenName));

  }

  // b) returns true if anyone in this ancestor tree has the same name as one of their ancestors
  public boolean duplicateNames() {
    if (this.mom.containsName(this.name) || this.dad.containsName(this.name)) {
      return true;
    }
    else {
      return (this.mom.duplicateNames() || this.dad.duplicateNames());
    }
  }

  public IAT youngerIAThelper(IAT other, int yob) {
    if (this.yob > yob) {
      return this;
    }
    else {
      return other;
    }
  }

  // c) returns the youngest ancestor at the given generation for this person
  public IAT youngerIAT(IAT other) {
    return other.youngerIAThelper(this, this.yob);
  }

  public IAT youngestAncInGen(int gen) {
    if (gen == 0) {
      return this;
    }
    else {
      return this.mom.youngestAncInGen(gen - 1).youngerIAT(this.dad.youngestAncInGen(gen - 1));
    }
  }

}

class ExamplesIAT {
  IAT unknown = new Unknown();
  IAT enid = new Person("Enid", 1904, unknown, unknown);
  IAT edward = new Person("Edward", 1902, unknown, unknown);
  IAT emma = new Person("Emma", 1906, unknown, unknown);
  IAT eustace = new Person("Andrew", 1907, unknown, unknown);

  IAT david = new Person("David", 1925, unknown, this.edward);
  IAT daisy = new Person("Daisy", 1927, unknown, unknown);
  IAT dana = new Person("Dana", 1933, unknown, unknown);
  IAT darcy = new Person("Darcy", 1930, this.emma, this.eustace);
  IAT darren = new Person("Darren", 1935, this.enid, unknown);
  IAT dixon = new Person("Dixon", 1936, unknown, unknown);

  IAT clyde = new Person("Clyde", 1955, this.daisy, this.david);
  IAT candace = new Person("Candace", 1960, this.dana, this.darren);
  IAT cameron = new Person("David", 1959, unknown, this.dixon);
  IAT claire = new Person("Enid", 1956, this.darcy, unknown);

  IAT bill = new Person("Bill", 1980, this.candace, this.clyde);
  IAT bree = new Person("Bree", 1981, this.claire, this.cameron);

  IAT andrew = new Person("Andrew", 2001, this.bree, this.bill);

  // test containsName method 
  boolean testContainsName(Tester t) {
    return t.checkExpect(this.andrew.containsName("Clyde"), true)
        && t.checkExpect(this.claire.containsName("Andrew"), true)
        && t.checkExpect(this.emma.containsName("Dixon"), false);
  }

  // test duplicateNames method 
  boolean testDuplicateNames(Tester t) {
    return t.checkExpect(this.andrew.duplicateNames(), true)
        && t.checkExpect(this.cameron.duplicateNames(), false)
        && t.checkExpect(this.claire.duplicateNames(), false)
        && t.checkExpect(this.dixon.duplicateNames(), false);
  }

  // test youngestAncInGen method 
  boolean testYoungestAncInGe(Tester t) {
    return t.checkExpect(this.andrew.youngestAncInGen(3), this.dixon)
        && t.checkExpect(this.cameron.youngestAncInGen(2), this.unknown)
        && t.checkExpect(this.claire.youngestAncInGen(1), this.darcy)
        && t.checkExpect(this.dixon.youngestAncInGen(0), this.dixon)
        && t.checkExpect(this.unknown.youngestAncInGen(2), this.unknown);
  }
}