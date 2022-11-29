//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;
//
//import javalib.impworld.World;
//import javalib.impworld.WorldScene;
//import javalib.worldimages.OutlineMode;
//import javalib.worldimages.OverlayImage;
//import javalib.worldimages.RectangleImage;
//import javalib.worldimages.TextImage;
//import javalib.worldimages.WorldImage;
//import tester.Tester;
//
//class Card {
//  int rank;
//  String suit;
//
//  Card(int rank, String suit) {
//    this.rank = new Utils().checkRange(rank, 1, 13, "Invalid rank: " + Integer.toString(rank));
//    ;
//    this.suit = new Utils().checkString(suit,
//        new ArrayList<String>(Arrays.asList("♣", "♦", "♥", "♠")), "Invalid suit: " + suit);
//    ;
//  }
//
//  // draw the card - rank and suit text image overlaying rectangle image
//  WorldImage drawCard() {
//    return new OverlayImage(new RectangleImage(50, 80, OutlineMode.OUTLINE, Color.black),
//        new TextImage(Integer.toString(this.rank) + this.suit, Color.black));
//  }
//}
//
//class Utils {
//  int checkRange(int val, int min, int max, String msg) {
//    if (val >= min && val <= max) {
//      return val;
//    }
//    else {
//      throw new IllegalArgumentException(msg);
//    }
//  }
//
//  String checkString(String val, ArrayList<String> validStrings, String msg) {
//    for (String s : validStrings) {
//      if (val.equals(s)) {
//        return val;
//      }
//    }
//    throw new IllegalArgumentException(msg);
//  }
//}
//
////represents the World
//class CardWorld extends World {
//  ArrayList<Card> cards;
//  Random rand;
//
//  CardWorld() {
//    Random random = new Random();
//    this.cards = initializeDeck();
//    this.rand = random;
//  }
//
//  CardWorld(Random random) {
//    this.cards = initializeDeck();
//    this.rand = random;
//  }
//
//  ArrayList<Card> initializeDeck() {
//    ArrayList<Card> deck = new ArrayList<Card>();
//    ArrayList<String> suits = new ArrayList<String>(Arrays.asList("♣", "♦", "♥", "♠"));
//
//    for (String s : suits) {
//      for (int r = 1; r <= 13; r++) {
//        deck.add(new Card(r, s));
//      }
//    }
//    return deck;
//  }
//
//  // makes WorldScene
//  public WorldScene makeScene() {
//    WorldScene cardWorld = new WorldScene(500, 500);
//    int cardIndex = 0;
//    for (int row = 0; row < 4; row++) {
//      for (int col = 0; col < 13; col++) {
//        cardWorld.placeImageXY(this.cards.get(cardIndex).drawCard(), col * 30 + 50,
//            row * 100 + 100);
//        cardIndex++;
//      }
//    }
//    return cardWorld;
//  }
//
//  //  // creates the final scene of game
//  //  WorldScene makeAFinalScene() {
//  //    WorldScene blank = new WorldScene(500, 500);
//  //    return blank;
//  //  }
//
//  public void onTick() {
//  }
//
//  public void onKeyEvent(String key) {
//  }
//}
//
//class ExamplesCards {
//  //BigBang
//  void testBigBang(Tester t) {
//    CardWorld world = new CardWorld();
//    int worldWidth = 500;
//    int worldHeight = 500;
//    world.bigBang(worldWidth, worldHeight);
//  }
//}
