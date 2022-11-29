import tester.Tester;

// represents a Person with a user name and a list of buddies
class Person {

  String username;
  ILoBuddy buddies;
  double dictionScore;
  double hearingScore;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
    this.dictionScore = 0.0;
    this.hearingScore = 0.0;
  }

  Person(String username, double dictionScore, double hearingScore) {
    this.username = username;
    this.buddies = new MTLoBuddy();
    this.dictionScore = dictionScore;
    this.hearingScore = hearingScore;

  }

  // Change this person's buddy list so that it includes the given person
  void addBuddy(Person buddy) {
    if (this.sameUsername(buddy)) {
      throw new RuntimeException("Same Person!");
    }
    else {
      this.buddies = new ConsLoBuddy(buddy, this.buddies);
    }
  }

  // are two usernames the same?
  boolean sameUsername(Person buddy) {
    return this.username.equals(buddy.username);
  }

  // returns true if this Person has that as a direct buddy
  boolean hasDirectBuddy(Person that) {
    return this.buddies.hasDirectBuddyHelper(that);
  }

  // returns the number of people who will show up at the party
  // given by this person
  int partyCount() {
    return this.partyCountFirstHelper().length();
  }

  // returns list of people going to party
  ILoBuddy partyCountFirstHelper() {
    return this.buddies.partyCountHelper(new ConsLoBuddy(this, new MTLoBuddy()));
  }

  // returns the number of people that are direct buddies
  // of both this and that person
  int countCommonBuddies(Person that) {
    return this.buddies.countCommonBuddiesHelper(that.buddies);
  }

  // will the given person be invited to a party
  // organized by this person?
  boolean hasExtendedBuddy(Person that) {
    return this.hasDirectBuddy(that)
        || this.buddies.hasExtendedBuddyHelper(that, new ConsLoBuddy(this, new MTLoBuddy()));
  }

  // get buddies of a given person
  ILoBuddy getBuddies() {
    return this.buddies;
  }

  // calculates the maxLikelihood score between two people
  double maxLikelihood(Person that) {
    return this.maxLikelihoodHelper(that, new MTLoBuddy());

  }

  // calculates the maxLikelihood score between two people
  double maxLikelihoodHelper(Person that, ILoBuddy checkedlist) {
    if (this.sameUsername(that)) {
      return 1.0;
    }
    else if (checkedlist.contains(this)) {
      return 0.0;
    }
    else {
      return this.buddies.maxLikelihoodHelper2(this.dictionScore, that, 0.0,
          new ConsLoBuddy(this, checkedlist));
    }
  }

}

//represents a list of Person's buddies
interface ILoBuddy {

  // returns true if this Person has that as a direct buddy
  boolean hasDirectBuddyHelper(Person that);

  // calculates the number of people that are direct buddies between these two
  // people
  int countCommonBuddiesHelper(ILoBuddy other);

  // determines if someone is an extended/indirect buddy of someone else
  boolean hasExtendedBuddyHelper(Person that, ILoBuddy checkedList);

  // removes a person from a given buddy list
  ILoBuddy remove(ILoBuddy given);

  // determines if a buddy list contains a given person
  boolean contains(Person that);

  // appends two buddy lists together
  ILoBuddy append(ILoBuddy given);

  // calculates the number of people invited/will attend a party
  ILoBuddy partyCountHelper(ILoBuddy given);

  // determines the length of a list of buddies
  int length();

  // determines the maxLikelihood score between two people
  double maxLikelihoodHelper2(double givenDiction, Person that, double max, ILoBuddy checkedList);

}

//represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
  MTLoBuddy() {
  }

  // returns true if this Person has that as a direct buddy
  public boolean hasDirectBuddyHelper(Person that) {
    return false;
  }

  // calculates the number of people that are direct buddies between two people
  public int countCommonBuddiesHelper(ILoBuddy other) {
    return 0;
  }

  // determines if given person is an extended buddy
  public boolean hasExtendedBuddyHelper(Person that, ILoBuddy checkedList) {
    return false;
  }

  // removes a person from a buddy list
  public ILoBuddy remove(ILoBuddy given) {
    return this;
  }

  // determines if a buddy list contains a given person
  public boolean contains(Person that) {
    return false;
  }

  // appends two buddy lists together
  public ILoBuddy append(ILoBuddy given) {
    return given;
  }

  // determines the number of people who will be directly/indirectly invited to a
  // party
  public ILoBuddy partyCountHelper(ILoBuddy given) {
    return given;
  }

  // determines the length of a buddy list
  public int length() {
    return 0;
  }

  // determines the maxLikelihood score between two people
  public double maxLikelihoodHelper2(double givenDiction, Person that, double max,
      ILoBuddy checkedList) {
    return max;
  }

}

//represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }

  // returns true if this Person has that as a direct buddy
  public boolean hasDirectBuddyHelper(Person that) {
    return this.first.sameUsername(that) || this.rest.hasDirectBuddyHelper(that);

  }

  // determines the number of direct buddies shared between two people
  public int countCommonBuddiesHelper(ILoBuddy other) {
    if (other.hasDirectBuddyHelper(this.first)) {
      return 1 + this.rest.countCommonBuddiesHelper(other);
    }
    else {
      return this.rest.countCommonBuddiesHelper(other);
    }
  }

  // determines if given person is an extended buddy
  public boolean hasExtendedBuddyHelper(Person that, ILoBuddy checkedList) {
    if (this.first.hasDirectBuddy(that)) {
      return true;
    }
    else {
      return this.rest.append(this.first.getBuddies().remove(checkedList))
          .hasExtendedBuddyHelper(that, new ConsLoBuddy(this.first, checkedList));
    }
  }

  // determines if the given buddy list contains a given person
  public boolean contains(Person that) {
    return this.first.sameUsername(that) || this.rest.contains(that);
  }

  // removes a person from a buddy list
  public ILoBuddy remove(ILoBuddy given) {
    if (given.contains(this.first)) {
      return this.rest.remove(given);
    }
    else {
      return new ConsLoBuddy(this.first, this.rest.remove(given));
    }
  }

  // appends two buddy lists together
  public ILoBuddy append(ILoBuddy given) {
    return new ConsLoBuddy(this.first, this.rest.append(given));
  }

  // determines the number of people who will be invited to a party
  public ILoBuddy partyCountHelper(ILoBuddy given) {
    if (given.contains(this.first)) {
      return this.rest.partyCountHelper(given);
    }
    else {
      return this.rest.partyCountHelper(
          this.first.buddies.partyCountHelper(new ConsLoBuddy(this.first, given)));
    }

    // OR this.rest.append(this.first.getBuddies().remove(checkedList))
    // .partyCountHelper(that, new ConsLoBuddy(this.first, checkedList))

  }

  // calculates the length of a buddy list
  public int length() {
    return 1 + this.rest.length();
  }

  // calculates the maxLikelihood score between two people
  public double maxLikelihoodHelper2(double givenDiction, Person that, double max,
      ILoBuddy checkedList) {
    double first = this.first.hearingScore * givenDiction
        * this.first.maxLikelihoodHelper(that, checkedList);
    return this.rest.maxLikelihoodHelper2(givenDiction, that, Math.max(first, max), checkedList);

    // if (that.buddies.contains(this.first)) {
    // return that.hearingScore * that.dictionScore * this.first.hearingScore;
    // } else {
    // return that.hearingScore * that.dictionScore
    // * this.rest.maxLikelihoodHelper(that); // this.first.buddies???
    // }
  }

}

//represent examples and tests for the buddies problem, 
class ExamplesBuddies {
  ExamplesBuddies() {
  }

  Person ann;
  Person bob;
  Person cole;
  Person dan;
  Person ed;
  Person fay;
  Person gabi;
  Person hank;
  Person jan;
  Person kim;
  Person len;

  ILoBuddy annFriends;
  ILoBuddy bobFriends;
  ILoBuddy coleFriends;
  ILoBuddy danFriends;
  ILoBuddy edFriends;
  ILoBuddy fayFriends;
  ILoBuddy gabiFriends;
  ILoBuddy hankFriends;
  ILoBuddy janFriends;
  ILoBuddy kimFriends;
  ILoBuddy lenFriends;
  ILoBuddy mt;

  // created separate examples to test maxLikelihood (and helper methods),
  // using different constructor
  Person annMax;
  Person bobMax;
  Person coleMax;
  Person danMax;
  Person edMax;
  Person fayMax;
  Person gabiMax;
  Person hankMax;
  Person janMax;
  Person kimMax;
  Person lenMax;

  void initBuddies() {

    this.ann = new Person("annUsername");
    this.bob = new Person("bobUsername");
    this.cole = new Person("coleUsername");
    this.dan = new Person("danUsername");
    this.ed = new Person("edUsername");
    this.fay = new Person("fayUsername");
    this.gabi = new Person("gabiUsername");
    this.hank = new Person("hankUsername");
    this.jan = new Person("janUsername");
    this.kim = new Person("kimUsername");
    this.len = new Person("lenUsername");

    this.annFriends = new ConsLoBuddy(this.bob, new ConsLoBuddy(this.cole, new MTLoBuddy()));
    this.bobFriends = new ConsLoBuddy(this.ann,
        new ConsLoBuddy(this.ed, new ConsLoBuddy(this.hank, new MTLoBuddy())));
    this.coleFriends = new ConsLoBuddy(this.dan, new MTLoBuddy());
    this.danFriends = new ConsLoBuddy(this.cole, new MTLoBuddy());
    this.edFriends = new ConsLoBuddy(this.fay, new MTLoBuddy());
    this.fayFriends = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.gabi, new MTLoBuddy()));
    this.gabiFriends = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.fay, new MTLoBuddy()));
    this.hankFriends = new MTLoBuddy();
    this.janFriends = new ConsLoBuddy(this.kim, new ConsLoBuddy(this.len, new MTLoBuddy()));
    this.kimFriends = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.len, new MTLoBuddy()));
    this.lenFriends = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.kim, new MTLoBuddy()));
    this.mt = new MTLoBuddy();

    this.ann.addBuddy(cole);
    this.ann.addBuddy(bob);
    this.bob.addBuddy(hank);
    this.bob.addBuddy(ed);
    this.bob.addBuddy(ann);
    this.cole.addBuddy(dan);
    this.dan.addBuddy(cole);
    this.ed.addBuddy(fay);
    this.fay.addBuddy(gabi);
    this.fay.addBuddy(ed);
    this.gabi.addBuddy(fay);
    this.gabi.addBuddy(ed);
    this.jan.addBuddy(len);
    this.jan.addBuddy(kim);
    this.kim.addBuddy(len);
    this.kim.addBuddy(jan);
    this.len.addBuddy(kim);
    this.len.addBuddy(jan);

    // created separate examples to test maxLikelihood (and helper methods),
    // using different constructor
    this.annMax = new Person("annUsername", 0.2, 0.3);
    this.bobMax = new Person("bobUsername", 0.7, 0.6);
    this.coleMax = new Person("coleUsername", 0.1, 0.4);
    this.danMax = new Person("danUsername", 0.5, 0.3);
    this.edMax = new Person("edUsername", 0.7, 0.6);
    this.fayMax = new Person("fayUsername", 0.5, 0.2);
    this.gabiMax = new Person("gabiUsername", 0.4, 0.3);
    this.hankMax = new Person("hankUsername", 0.0, 0.0);
    this.janMax = new Person("janUsername", 0.6, 0.1);
    this.kimMax = new Person("kimUsername", 0.1, 0.2);
    this.lenMax = new Person("lenUsername", 0.3, 0.3);

    this.annMax.addBuddy(coleMax);
    this.annMax.addBuddy(bobMax);
    this.bobMax.addBuddy(hankMax);
    this.bobMax.addBuddy(edMax);
    this.bobMax.addBuddy(annMax);
    this.coleMax.addBuddy(danMax);
    this.danMax.addBuddy(coleMax);
    this.edMax.addBuddy(fayMax);
    this.fayMax.addBuddy(gabiMax);
    this.fayMax.addBuddy(edMax);
    this.gabiMax.addBuddy(fayMax);
    this.gabiMax.addBuddy(edMax);
    this.janMax.addBuddy(lenMax);
    this.janMax.addBuddy(kimMax);
    this.kimMax.addBuddy(lenMax);
    this.kimMax.addBuddy(janMax);
    this.lenMax.addBuddy(kimMax);
    this.lenMax.addBuddy(janMax);

  }

  // test the addBuddy method
  void testAddBuddy(Tester t) {
    initBuddies();

    t.checkExpect(this.ann.buddies, this.annFriends);
    t.checkExpect(this.bob.buddies, this.bobFriends);
    t.checkExpect(this.cole.buddies, this.coleFriends);
    t.checkExpect(this.dan.buddies, this.danFriends);
    t.checkExpect(this.ed.buddies, this.edFriends);
    t.checkExpect(this.fay.buddies, this.fayFriends);
    t.checkExpect(this.gabi.buddies, this.gabiFriends);
    t.checkExpect(this.jan.buddies, this.janFriends);
    t.checkExpect(this.kim.buddies, this.kimFriends);
    t.checkExpect(this.len.buddies, this.lenFriends);

    t.checkException(new RuntimeException("Same Person!"), this.ann, "addBuddy", this.ann);
    t.checkException(new RuntimeException("Same Person!"), this.bob, "addBuddy", this.bob);
    t.checkException(new RuntimeException("Same Person!"), this.cole, "addBuddy", this.cole);

  }

  // test sameUsername method
  void testSameUsername(Tester t) {
    initBuddies();

    t.checkExpect(this.ann.sameUsername(ann), true);
    t.checkExpect(this.ann.sameUsername(bob), false);
    t.checkExpect(this.jan.sameUsername(jan), true);
    t.checkExpect(this.jan.sameUsername(len), false);
  }

  // test the hasDirectBuddy method
  void testDirectBuddy(Tester t) {
    initBuddies();

    t.checkExpect(this.ann.hasDirectBuddy(bob), true);
    t.checkExpect(this.ann.hasDirectBuddy(cole), true);
    t.checkExpect(this.ann.hasDirectBuddy(len), false);
    t.checkExpect(this.bob.hasDirectBuddy(ann), true);
    t.checkExpect(this.bob.hasDirectBuddy(ed), true);
    t.checkExpect(this.bob.hasDirectBuddy(jan), false);
    t.checkExpect(this.cole.hasDirectBuddy(jan), false);
    t.checkExpect(this.cole.hasDirectBuddy(dan), true);
    t.checkExpect(this.ed.hasDirectBuddy(fay), true);
    t.checkExpect(this.ed.hasDirectBuddy(gabi), false);

  }

  // test hasDirectBuddyHelper method
  void testDirectBuddyHelper(Tester t) {
    initBuddies();

    t.checkExpect(this.mt.hasDirectBuddyHelper(ann), false);
    t.checkExpect(this.ann.buddies.hasDirectBuddyHelper(bob), true);
    t.checkExpect(this.ann.buddies.hasDirectBuddyHelper(cole), true);
    t.checkExpect(this.ann.buddies.hasDirectBuddyHelper(len), false);
    t.checkExpect(this.bob.buddies.hasDirectBuddyHelper(ann), true);
    t.checkExpect(this.bob.buddies.hasDirectBuddyHelper(ed), true);
    t.checkExpect(this.bob.buddies.hasDirectBuddyHelper(jan), false);
    t.checkExpect(this.cole.buddies.hasDirectBuddyHelper(jan), false);
    t.checkExpect(this.cole.buddies.hasDirectBuddyHelper(dan), true);
    t.checkExpect(this.ed.buddies.hasDirectBuddyHelper(fay), true);
    t.checkExpect(this.ed.buddies.hasDirectBuddyHelper(gabi), false);

  }

  // test countCommonBuddies method
  void testCountCommonBuddies(Tester t) {
    initBuddies();

    t.checkExpect(this.ann.countCommonBuddies(bob), 0);
    t.checkExpect(this.fay.countCommonBuddies(gabi), 1);
    t.checkExpect(this.gabi.countCommonBuddies(fay), 1);
    t.checkExpect(this.jan.countCommonBuddies(kim), 1);
    t.checkExpect(this.kim.countCommonBuddies(jan), 1);
    t.checkExpect(this.len.countCommonBuddies(kim), 1);
    t.checkExpect(this.len.countCommonBuddies(jan), 1);
  }

  // test countCommonBuddiesHelper method

  void testCountCommonBuddiesHelper(Tester t) {
    initBuddies();

    t.checkExpect(this.mt.countCommonBuddiesHelper(annFriends), 0);
    t.checkExpect(this.ann.buddies.countCommonBuddiesHelper(this.mt), 0);
    t.checkExpect(this.ann.buddies.countCommonBuddiesHelper(bobFriends), 0);
    t.checkExpect(this.fay.buddies.countCommonBuddiesHelper(gabiFriends), 1);
    t.checkExpect(this.gabi.buddies.countCommonBuddiesHelper(fayFriends), 1);
    t.checkExpect(this.jan.buddies.countCommonBuddiesHelper(kimFriends), 1);
    t.checkExpect(this.kim.buddies.countCommonBuddiesHelper(janFriends), 1);
    t.checkExpect(this.len.buddies.countCommonBuddiesHelper(kimFriends), 1);
    t.checkExpect(this.len.buddies.countCommonBuddiesHelper(janFriends), 1);
  }

  // test the hasExtendedBuddy method
  void testHasExtendedBuddy(Tester t) {
    initBuddies();

    t.checkExpect(this.ed.hasExtendedBuddy(gabi), true);
    t.checkExpect(this.ed.hasExtendedBuddy(ann), false);
    t.checkExpect(this.ann.hasExtendedBuddy(dan), true);
    t.checkExpect(this.ann.hasExtendedBuddy(bob), true);
    t.checkExpect(this.ann.hasExtendedBuddy(jan), false);
  }

  // test the hasExtendedBuddyHelper method
  void testHasExtendedBuddyHelper(Tester t) {
    initBuddies();

    t.checkExpect(this.mt.hasExtendedBuddyHelper(bob, new ConsLoBuddy(this.ann, new MTLoBuddy())),
        false);
    t.checkExpect(
        this.ann.buddies.hasExtendedBuddyHelper(bob, new ConsLoBuddy(this.ann, new MTLoBuddy())),
        false);
    t.checkExpect(
        this.ann.buddies.hasExtendedBuddyHelper(dan, new ConsLoBuddy(this.ann, new MTLoBuddy())),
        true);
    t.checkExpect(
        this.ed.buddies.hasExtendedBuddyHelper(gabi, new ConsLoBuddy(this.ed, new MTLoBuddy())),
        true);
    t.checkExpect(
        this.hank.buddies.hasExtendedBuddyHelper(fay, new ConsLoBuddy(this.ed, new MTLoBuddy())),
        false);

  }

  // test remove method
  void testRemove(Tester t) {
    initBuddies();

    t.checkExpect(this.mt.remove(annFriends), this.mt);
    t.checkExpect(this.annFriends.remove(annFriends), new MTLoBuddy());
    t.checkExpect(this.annFriends.remove(bobFriends), this.annFriends);
    t.checkExpect(this.janFriends.remove(kimFriends), new ConsLoBuddy(this.kim, new MTLoBuddy()));
    t.checkExpect(this.kimFriends.remove(janFriends), new ConsLoBuddy(this.jan, new MTLoBuddy()));

  }

  // test contains method
  void testContains(Tester t) {
    initBuddies();

    t.checkExpect(this.mt.contains(ann), false);
    t.checkExpect(this.annFriends.contains(bob), true);
    t.checkExpect(this.annFriends.contains(cole), true);
    t.checkExpect(this.annFriends.contains(jan), false);
    t.checkExpect(this.annFriends.contains(dan), false);

  }

  // test append method
  void testAppend(Tester t) {
    initBuddies();

    t.checkExpect(this.annFriends.append(mt), this.annFriends);
    t.checkExpect(this.mt.append(annFriends), this.annFriends);
    t.checkExpect(this.danFriends.append(coleFriends),
        new ConsLoBuddy(this.cole, new ConsLoBuddy(this.dan, this.mt)));
  }

  // test partyCount method
  void testPartyCount(Tester t) {
    initBuddies();

    t.checkExpect(this.ann.partyCount(), 8);
    t.checkExpect(this.hank.partyCount(), 1);
    t.checkExpect(this.jan.partyCount(), 3);
    t.checkExpect(this.dan.partyCount(), 2);

  }

  // test partyCountFirstHelper method
  void testPartyCountFirstHelper(Tester t) {
    initBuddies();

    t.checkExpect(this.hank.partyCountFirstHelper(), new ConsLoBuddy(this.hank, this.mt));
    t.checkExpect(this.dan.partyCountFirstHelper(),
        new ConsLoBuddy(this.cole, new ConsLoBuddy(this.dan, this.mt)));
    t.checkExpect(this.cole.partyCountFirstHelper(),
        new ConsLoBuddy(this.dan, new ConsLoBuddy(this.cole, this.mt)));
    t.checkExpect(this.jan.partyCountFirstHelper(),
        new ConsLoBuddy(this.len, new ConsLoBuddy(this.kim, new ConsLoBuddy(this.jan, this.mt))));

  }

  // test partyCountHelper method
  void testPartyCountHelper(Tester t) {
    initBuddies();

    t.checkExpect(this.hankFriends.partyCountHelper(new ConsLoBuddy(this.hank, new MTLoBuddy())),
        new ConsLoBuddy(this.hank, new MTLoBuddy()));
    t.checkExpect(this.danFriends.partyCountHelper(new ConsLoBuddy(this.dan, new MTLoBuddy())),
        new ConsLoBuddy(this.cole, new ConsLoBuddy(this.dan, new MTLoBuddy())));
    t.checkExpect(this.coleFriends.partyCountHelper(new ConsLoBuddy(this.cole, new MTLoBuddy())),
        new ConsLoBuddy(this.dan, new ConsLoBuddy(this.cole, new MTLoBuddy())));
    t.checkExpect(this.janFriends.partyCountHelper(new ConsLoBuddy(this.jan, new MTLoBuddy())),
        new ConsLoBuddy(this.len,
            new ConsLoBuddy(this.kim, new ConsLoBuddy(this.jan, new MTLoBuddy()))));

  }

  // test length method
  void testLength(Tester t) {
    initBuddies();

    t.checkExpect(this.mt.length(), 0);
    t.checkExpect(this.annFriends.length(), 2);
    t.checkExpect(this.danFriends.length(), 1);
    t.checkExpect(this.coleFriends.length(), 1);

  }

  // test maxLikelihood method
  void testMaxLikelihood(Tester t) {
    initBuddies();

    t.checkInexact(this.annMax.maxLikelihood(annMax), 1.0, 0.0001);
    t.checkInexact(this.annMax.maxLikelihood(bobMax), 0.2 * 0.6, 0.0001);
    t.checkInexact(this.hankMax.maxLikelihood(annMax), 0.0, 0.0001);
    t.checkInexact(this.annMax.maxLikelihood(danMax), 0.2 * 0.4 * 0.1 * 0.3, 0.0001);
    t.checkInexact(this.edMax.maxLikelihood(gabiMax), 0.7 * 0.2 * 0.5 * 0.3, 0.0001);

  }

  // test maxLikelihoodHelper method
  void testMaxLikelihoodHelper(Tester t) {
    initBuddies();

    t.checkInexact(this.annMax.maxLikelihoodHelper(annMax, this.mt), 1.0, 0.0001);
    t.checkInexact(this.annMax.maxLikelihoodHelper(bobMax, this.mt), 0.2 * 0.6, 0.0001);
    t.checkInexact(this.hankMax.maxLikelihoodHelper(annMax, this.mt), 0.0, 0.0001);
    t.checkInexact(this.annMax.maxLikelihoodHelper(danMax, this.mt), 0.2 * 0.4 * 0.1 * 0.3, 0.0001);
    t.checkInexact(this.edMax.maxLikelihoodHelper(gabiMax, this.mt), 0.7 * 0.2 * 0.5 * 0.3, 0.0001);

  }

  // test maxLikelihoodHelper2 method
  void testMaxLikelihoodHelper2(Tester t) {
    initBuddies();

    t.checkInexact(
        this.annMax.buddies.maxLikelihoodHelper2(this.annMax.dictionScore, bobMax, 0.0, this.mt),
        0.2 * 0.6, 0.0001);
    t.checkInexact(
        this.hankMax.buddies.maxLikelihoodHelper2(this.hankMax.dictionScore, annMax, 0.0, this.mt),
        0.0, 0.0001);
    t.checkInexact(
        this.annMax.buddies.maxLikelihoodHelper2(this.annMax.dictionScore, danMax, 0.0, this.mt),
        0.2 * 0.4 * 0.1 * 0.3, 0.0001);
    t.checkInexact(
        this.edMax.buddies.maxLikelihoodHelper2(this.edMax.dictionScore, gabiMax, 0.0, this.mt),
        0.7 * 0.2 * 0.5 * 0.3, 0.0001);

  }

}
