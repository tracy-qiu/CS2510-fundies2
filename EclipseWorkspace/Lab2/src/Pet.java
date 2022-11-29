import tester.Tester;

// to represent a pet owner
class Person {
  String name;
  IPet pet;
  int age;

  // constructor
  Person(String name, IPet pet, int age) {
    this.name = name;
    this.pet = pet;
    this.age = age;
  }

  // is this Person older than the given Person?
  boolean isOlder(Person other) {
    return this.age > other.age;
  }

  // has this Person's pet perished?
  Person perish() {
    return new Person(this.name, new NoPet(), this.age);
  }
}

// to represent a pet
interface IPet {
  // does the name of this person's pet match the given name?
  boolean sameName(String name);
}

// to represent a pet cat
class Cat implements IPet {
  String name;
  String kind;
  boolean longhaired;

  // constructor
  Cat(String name, String kind, boolean longhaired) {
    this.name = name;
    this.kind = kind;
    this.longhaired = longhaired;
  }

  public boolean sameName(String name) {
    return this.name == name;
  }
}

// to represent a pet dog
class Dog implements IPet {
  String name;
  String kind;
  boolean male;

  // constructor
  Dog(String name, String kind, boolean male) {
    this.name = name;
    this.kind = kind;
    this.male = male;
  }

  public boolean sameName(String name) {
    return this.name == name;
  }
}

//to represent a pet dog
class NoPet implements IPet {
  NoPet() {
  }

  // constructor
  public boolean sameName(String name) {
    return false;
  }
}

//to represent examples and tests for people and their pets 
class ExamplesPets {
  IPet c1 = new Cat("Bobby", "Siamese", true);
  IPet c2 = new Cat("Fur ball", "Sphynx", false);
  IPet d1 = new Dog("Buddy", "Golden Retreiver", true);
  IPet d2 = new Dog("Luna", "Border Collie", false);
  IPet n1 = new NoPet();

  Person p1 = new Person("Ann", c1, 29);
  Person p2 = new Person("Dan", c2, 81);
  Person p3 = new Person("Jack", d1, 23);
  Person p4 = new Person("Alice", d2, 48);
  Person p5 = new Person("Frank", n1, 35);
  Person p6 = new Person("Jane", n1, 21);

  // test isOlder method
  boolean testIsOlder(Tester t) {
    return t.checkExpect(this.p1.isOlder(p2), false) && t.checkExpect(this.p3.isOlder(p4), false)
        && t.checkExpect(this.p5.isOlder(p6), true);
  }

  // test sameName method
  boolean testSameName(Tester t) {
    return t.checkExpect(this.c1.sameName("Bobby"), true)
        && t.checkExpect(this.c2.sameName("Harry"), false)
        && t.checkExpect(this.d1.sameName("Buddy"), true)
        && t.checkExpect(this.d2.sameName("Zoe"), false)
        && t.checkExpect(this.n1.sameName("Alex"), false);
  }

  // test perish method
  boolean testPerish(Tester t) {
    return t.checkExpect(this.p1.perish(), new Person(this.p1.name, n1, this.p1.age))
        && t.checkExpect(this.p2.perish(), new Person(this.p2.name, n1, this.p2.age));
  }
}
