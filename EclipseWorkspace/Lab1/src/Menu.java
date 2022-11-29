// to represent the deli menu
interface IMenu {
}
 
// to represent a soup
class Soup implements IMenu {
  String name;
  int price;
  boolean vegetarian;
 
  Soup(String name, int price, boolean vegetarian) {
    this.name = name;
    this.price = price;
    this.vegetarian = vegetarian;
  }
}

//to represent a salad
class Salad implements IMenu {
  String name;
  int price;
  boolean vegetarian;
  String dressing;
  
  Salad(String name, int price, boolean vegetarian, String dressing) {
    this.name = name;
    this.price = price;
    this.vegetarian = vegetarian;
    this.dressing = dressing;
  }
}

//to represent a sandwich
class Sandwich implements IMenu {
  String name;
  int price;
  String bread; 
  String filling1; 
  String filling2; 
  
  
  Sandwich(String name, int price, String bread, String filling1, String filling2) {
    this.name = name;
    this.price = price;
    this.bread = bread; 
    this.filling1 = filling1;
    this.filling2 = filling2;
  }
}

//to represent examples and tests for shapes
class ExamplesIMenu{
  ExamplesIMenu() {}
  
  IMenu soup1 = new Soup("Chicken Noodle Soup", 350, false);
  IMenu soup2 = new Soup("Vegetable Soup", 300, true);
  
  IMenu salad1 = new Salad("Chicken Caesar Salad", 600, false, "Caesar");
  IMenu salad2 = new Salad("Garden Salad", 550, true, "Balsamic");

  IMenu sandwich1 = new Sandwich("Ham Sandwich", 700, "Italian", "Ham", "Cheese");
  IMenu sandwich2 = new Sandwich("Turkey Sandwich", 750, "Sourdough", "Turkey", "Mayo");
}
