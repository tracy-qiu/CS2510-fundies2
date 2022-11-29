class Bait {
  int value;
  DoubleBack db;

  Bait(int value, DoubleBack db) {
    this.value = value;
    this.db = db;
  }

  //returns either a TurnAbout or a DoubleBack based on the value
  ISwitch baitAndSwitch(ISwitch s) {
    if (this.value == 10) {
      return new TurnAbout(((TurnAbout) s).howMuch + 100.0);
    }
    else if (s.doubleBackHuh()) {
      return new DoubleBack(this.db.howMany * 10);
    }
    else {
      return this.db;
    }
  }
}

interface ISwitch {
  //is this ISwitch a DoubleBack?
  boolean doubleBackHuh();
}

class TurnAbout implements ISwitch {
  double howMuch;

  TurnAbout(double howMuch) {
    this.howMuch = howMuch;
  }

  //is this Turnabout a DoubleBack?
  public boolean doubleBackHuh() {
    return false;
  }
}

class DoubleBack implements ISwitch {
  int howMany;

  DoubleBack(int howMany) {
    this.howMany = howMany;
  }

  //is this DoubleBack a DoubleBack?
  public boolean doubleBackHuh() {
    return true;
  }
}