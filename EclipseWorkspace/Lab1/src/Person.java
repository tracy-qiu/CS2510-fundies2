// to represent a person 
class Person {
  String name;
  int age;
  String gender;
  Address address;

  // the constructor
  Person(String name, int age, String gender, Address address) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
  }
}

// to represent an address 
class Address {
  String city;
  String state;

  // the constructor
  Address(String city, String state) {
    this.city = city;
    this.state = state;
  }
}

// to represent examples and tests for person 
class ExamplesPerson {
  ExamplesPerson() {
  }

  Address boston = new Address("Boston", "MA");
  Address warwick = new Address("Warwick", "RI");
  Address nashua = new Address("Nashua", "NH");
  Address natick = new Address("Natick", "MA");
  Address newton = new Address("Newton", "MA");

  Person tim = new Person("Tim", 23, "Male", boston);
  Person kate = new Person("Kate", 22, "Female", warwick);
  Person rebecca = new Person("Rebecca", 31, "Non-binary", nashua);
  Person john = new Person("John", 29, "Non-binary", natick);
  Person ann = new Person("Ann", 21, "female", newton);
}