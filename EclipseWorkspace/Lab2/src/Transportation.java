//// Represents a mode of transportation
//interface IMOT {
//  // returns true if this mode of transportation is at least
//  // as efficient as the given mpg, false otherwise
//  boolean isMoreFuelEfficientThan(int mpg);
//}
//
//// Represents a bicycle as a mode of transportation
//class Bicycle implements IMOT {
//  String brand;
//
//  Bicycle(String brand) {
//    this.brand = brand;
//  }
//
//  // a bicycle does not consume fuel, so it will always be more fuel efficient
//  public boolean isMoreFuelEfficientThan(int mpg) {
//    return true;
//  }
//}
//
//// Represents a car as a mode of transportation
//class Car implements IMOT {
//  String make;
//  int mpg; // represents the fuel efficiency in miles per gallon
//
//  Car(String make, int mpg) {
//    this.make = make;
//    this.mpg = mpg;
//  }
//
//  // compare this car's fuel efficiency to the given fuel efficiency
//  public boolean isMoreFuelEfficientThan(int mpg) {
//    return this.mpg >= mpg;
//  }
//}
//
//// Keeps track of how a person is transported
//class Person {
//  String name;
//  IMOT mot;
//
//  Person(String name, IMOT mot) {
//    this.name = name;
//    this.mot = mot;
//  }
//
//  // Does this person's mode of transportation meet the given fuel
//  // efficiency target (in miles per gallon)?
//  boolean motMeetsFuelEfficiency(int mpg) {
//    return this.mot.isMoreFuelEfficientThan(mpg);
//  }
//}
//
////to represent examples and tests for people traveling 
//class ExamplesPerson {
//  IMOT diamondback = new Bicycle("Diamondback");
//  IMOT toyota = new Car("Toyota", 30);
//  IMOT lamborghini = new Car("Lamborghini", 17);
//
//  Person bob = new Person("Bob", diamondback);
//  Person ben = new Person("Ben", toyota);
//  Person becca = new Person("Becca", lamborghini);
//}