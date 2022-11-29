//// to represent an ancestor tree
//interface IAT{ }
// 
//// to represent an unknown member of an ancestor tree
//class Unknown implements IAT{
//  Unknown() {}
//}
// 
//// to represent a person with the person's ancestor tree
//class Person implements IAT{
//  String name;
//  IAT mom;
//  IAT dad;
// 
//  Person(String name, IAT mom, IAT dad) {
//    this.name = name;
//    this.mom = mom;
//    this.dad = dad;
//  }
//}
//
////examples and tests for the class hierarchy that represents
////ancestor trees
//class ExamplesAncestors{
//  ExamplesAncestors() {}
//
//  IAT unknown = new Unknown();
//  IAT annie = new Person("Annie", this.unknown, this.unknown);
//  IAT alex = new Person("Alex", this.unknown, this.unknown);
//  IAT alice = new Person("Alice", this.unknown, this.unknown);
//  IAT anthony = new Person("Anthony", this.alice, this.unknown);
//  
//  IAT susan = new Person("Susan", this.annie, this.alex);
//  
//  IAT jack = new Person("Jack", this.susan, this.anthony);
//}
