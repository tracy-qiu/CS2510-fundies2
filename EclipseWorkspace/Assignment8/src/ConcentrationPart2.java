//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
//import Archive.CartPtOffscreen;
//import Archive.PlayerBullet;
//import Archive.SpaceInvaders;
//import javalib.impworld.World;
//import javalib.impworld.WorldScene;
//import javalib.worldimages.OutlineMode;
//import javalib.worldimages.OverlayImage;
//import javalib.worldimages.Posn;
//import javalib.worldimages.RectangleImage;
//import javalib.worldimages.TextImage;
//import javalib.worldimages.WorldImage;
//import tester.Tester;
//
//// represents the cards in the game
//class Card {
//  int rank;
//  String suit;
//  boolean flipped;
//
//  Card(int rank, String suit) {
//
//    this.rank = new Utils().checkRange(rank, 1, 13, "Invalid rank: " + Integer.toString(rank));
//
//    this.suit = new Utils().checkString(suit,
//        new ArrayList<String>(Arrays.asList("♣", "♦", "♥", "♠")), "Invalid suit: " + suit);
//
//    // default card is not flipped - start of game
//    this.flipped = true;
//  }
//
//  // converts cards with rank 1, 11, 12, 13 to proper card equivalent
//  String convertRank() {
//    if (this.rank == 1) {
//      return "A";
//    }
//    else if (this.rank == 11) {
//      return "J";
//    }
//    else if (this.rank == 12) {
//      return "Q";
//    }
//    else if (this.rank == 13) {
//      return "K";
//    }
//    else {
//      return Integer.toString(this.rank);
//    }
//  }
//
//  // draw the card - rank and suit text image overlaying rectangle image
//  WorldImage drawCard() {
//    if (flipped) {
//      if (this.suit.equals("♣") || this.suit.equals("♠")) {
//        return new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage(this.convertRank() + this.suit, Color.black));
//      }
//      return new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//          new TextImage(this.convertRank() + this.suit, Color.red));
//    }
//    else {
//      return new RectangleImage(30, 40, OutlineMode.SOLID, Color.black);
//    }
//
//  }
//
//  // change the status of the card (flipped or not)
//  void changeFlipped() {
//    this.flipped = !this.flipped;
//  }
//
//  void changeFlipped(boolean showCard) {
//    this.flipped = showCard;
//  }
//
//  // determines if two cards have the same suit
//  boolean sameSuit(Card card2) {
//    return this.suit.equals(card2.suit);
//  }
//
//}
//
//// represents methods used on arrayLists
//class Utils {
//
//  // determines if value is in the given range
//  int checkRange(int val, int min, int max, String msg) {
//    if (val >= min && val <= max) {
//      return val;
//    }
//    else {
//      throw new IllegalArgumentException(msg);
//    }
//  }
//
//  // determines if String is valid
//  String checkString(String val, ArrayList<String> validStrings, String msg) {
//    for (String s : validStrings) {
//      if (val.equals(s)) {
//        return val;
//      }
//    }
//    throw new IllegalArgumentException(msg);
//  }
//
//  // determines the number of cards in arrayList that are flipped face-up
//  int countFlipped(ArrayList<Card> cardList) {
//
//    int count = 0;
//
//    for (Card elem : cardList)
//      if (elem.flipped) {
//        count = count + 1;
//      }
//    return count;
//  }
//
//  // get flipped cards in deck
//  ArrayList<Card> getFlippedCards(ArrayList<Card> cardList) {
//    ArrayList<Card> flippedCards = new ArrayList<Card>();
//
//    for (Card elem : cardList) {
//      if (elem.flipped) {
//        flippedCards.add(elem);
//      }
//    }
//    return flippedCards;
//  }
//
//  //  // determines if two cards in a List have the same suit
//  //  boolean sameSuit(ArrayList<Card> cardList) {
//  //    return cardList.get(0).suit.equals(cardList.get(1).suit);
//  //  }
//
//}
//
////represents the World
//class CardWorld extends World {
//  ArrayList<Card> cards;
//  Random rand;
//  int row;
//  int col;
//
//  // test constructor with row and col
//  CardWorld(ArrayList<Card> cards, int row, int col) {
//    this.cards = cards;
//    this.row = row;
//    this.col = col;
//  }
//
//  // test constructor with preset rows and cols
//  CardWorld(ArrayList<Card> cards) {
//    this.cards = cards;
//    this.row = 4;
//    this.col = 13;
//  }
//
//  // real constructor for game
//  CardWorld() {
//    Random random = new Random();
//    this.rand = random;
//    this.cards = initializeDeck();
//    this.row = 4;
//    this.col = 13;
//  }
//
//  // test constructor with Random
//  CardWorld(Random random) {
//    this.rand = random;
//    this.cards = initializeDeck();
//  }
//
//  // initializes full deck (52 cards only) then randomizes it
//  ArrayList<Card> initializeDeck() {
//    ArrayList<Card> deck = new ArrayList<Card>();
//    ArrayList<Card> randomizedDeck = new ArrayList<Card>();
//    ArrayList<String> suits = new ArrayList<String>(Arrays.asList("♣", "♦", "♥", "♠"));
//    int randomIndex;
//
//    for (String s : suits) {
//      for (int r = 1; r <= 13; r++) {
//        deck.add(new Card(r, s));
//      }
//    }
//    while (deck.size() > 0) {
//      randomIndex = rand.nextInt(deck.size());
//      randomizedDeck.add(0, deck.get(randomIndex));
//      deck.remove(deck.get(randomIndex));
//    }
//    return randomizedDeck;
//  }
//
//  // makes WorldScene
//  // create field in CardsWorld: testRow and testCol
//  public WorldScene makeScene() {
//    WorldScene cardWorld = new WorldScene(600, 500);
//    int cardIndex = 0;
//
//    if (this.cards.size() > 0) {
//      for (int row = 0; row < this.row; row++) {
//        for (int col = 0; col < this.col; col++) {
//          cardWorld.placeImageXY(this.cards.get(cardIndex).drawCard(), col * 40 + 50,
//              row * 100 + 100);
//          cardIndex++;
//        }
//      }
//    }
//    return cardWorld;
//  }
//
//  // use a for loop, find a way to determine if the position matches card space
//  public void onMouseClicked(Posn pos) {
//
//    Utils util = new Utils();
//    int cardIndex = 0;
//
//    if (util.countFlipped(this.cards) < 2) {
//      for (int row = 0; row < this.row; row++) {
//        for (int col = 0; col < this.col; col++) {
//          if ((col * 40 + 50) + 15 >= pos.x && (col * 40 + 50) - 15 <= pos.x
//              && (row * 100 + 100) + 20 >= pos.y && (row * 100 + 100) - 20 <= pos.y) {
//            this.cards.get(cardIndex).changeFlipped();
//          }
//          cardIndex++;
//        }
//      }
//    }
//
//    if (util.getFlippedCards(this.cards).size() == 2) {
//      for (int i = 0; i < util.getFlippedCards(this.cards).size() - 1; i++) {
//        if (util.getFlippedCards(this.cards).get(i)
//            .sameSuit(util.getFlippedCards(this.cards).get(i + 1))) {
//          this.cards.remove(i);
//          this.cards.remove(i + 1);
//        }
//        else {
//          TimeUnit.SECONDS.sleep(2);
//          util.getFlippedCards(this.cards).get(i).changeFlipped(false);
//          util.getFlippedCards(this.cards).get(i + 1).changeFlipped(false);
//        }
//      }
//    }
//
//  }
//
//  // // creates the final scene of game
//  // WorldScene makeAFinalScene() {
//  // WorldScene blank = new WorldScene(500, 500);
//  // return blank;
//  // }
//
//  //  public void onTick() {
//  //    Utils util = new Utils();
//  //
//  //    for (int i = 0; i < util.getFlippedCards(this.cards).size() - 1; i++) {
//  //      if (!(util.getFlippedCards(this.cards).get(i)
//  //          .sameSuit(util.getFlippedCards(this.cards).get(i + 1)))) {
//  //        util.getFlippedCards(this.cards).get(i).changeFlipped();
//  //        util.getFlippedCards(this.cards).get(i + 1).changeFlipped();
//  //      }
//  //    }
//  //  }
//
//  //add a key event to move the player spaceship when the "left" or "right" key
//  // is pressed
//  public void onKeyEvent(String key) {
//    if (key.equals("r")) {
//      for (Card c : cards) {
//        c.changeFlipped(false);
//      }
//    }
//  }
//
//class ExamplesCards {
//
//  // BigBang
//  void testBigBang(Tester t) {
//    CardWorld world = new CardWorld();
//    int worldWidth = 600;
//    int worldHeight = 500;
//    world.bigBang(worldWidth, worldHeight);
//  }
//
//  Card card1;
//  Card card2;
//  ArrayList<String> suitList;
//
//  Card c1;
//  Card c2;
//  Card c3;
//  Card c4;
//  Card c5;
//  Card c6;
//  Card c7;
//  Card c8;
//  Card c9;
//  Card c10;
//  Card c11;
//  Card c12;
//  Card c13;
//  Card c14;
//  Card c15;
//  Card c16;
//  Card c17;
//  Card c18;
//  Card c19;
//  Card c20;
//  Card c21;
//  Card c22;
//  Card c23;
//  Card c24;
//  Card c25;
//  Card c26;
//  Card c27;
//  Card c28;
//  Card c29;
//  Card c30;
//  Card c31;
//  Card c32;
//  Card c33;
//  Card c34;
//  Card c35;
//  Card c36;
//  Card c37;
//  Card c38;
//  Card c39;
//  Card c40;
//  Card c41;
//  Card c42;
//  Card c43;
//  Card c44;
//  Card c45;
//  Card c46;
//  Card c47;
//  Card c48;
//  Card c49;
//  Card c50;
//  Card c51;
//  Card c52;
//
//  ArrayList<Card> mainDeck;
//  ArrayList<Card> testDeck1;
//  ArrayList<Card> mt;
//
//  CardWorld test1;
//  CardWorld test2;
//
//  CardWorld blank;
//  WorldScene cardWorld;
//
//  // initialize cards
//  void initCards() {
//
//    this.card1 = new Card(5, "♣");
//    this.card2 = new Card(6, "♣");
//    this.suitList = new ArrayList<String>(Arrays.asList("♣", "♦", "♥", "♠"));
//
//    this.c1 = new Card(1, "♣");
//    this.c2 = new Card(2, "♣");
//    this.c3 = new Card(3, "♣");
//    this.c4 = new Card(4, "♣");
//    this.c5 = new Card(5, "♣");
//    this.c6 = new Card(6, "♣");
//    this.c7 = new Card(7, "♣");
//    this.c8 = new Card(8, "♣");
//    this.c9 = new Card(9, "♣");
//    this.c10 = new Card(10, "♣");
//    this.c11 = new Card(11, "♣");
//    this.c12 = new Card(12, "♣");
//    this.c13 = new Card(13, "♣");
//    this.c14 = new Card(1, "♦");
//    this.c15 = new Card(2, "♦");
//    this.c16 = new Card(3, "♦");
//    this.c17 = new Card(4, "♦");
//    this.c18 = new Card(5, "♦");
//    this.c19 = new Card(6, "♦");
//    this.c20 = new Card(7, "♦");
//    this.c21 = new Card(8, "♦");
//    this.c22 = new Card(9, "♦");
//    this.c23 = new Card(10, "♦");
//    this.c24 = new Card(11, "♦");
//    this.c25 = new Card(12, "♦");
//    this.c26 = new Card(13, "♦");
//    this.c27 = new Card(1, "♥");
//    this.c28 = new Card(2, "♥");
//    this.c29 = new Card(3, "♥");
//    this.c30 = new Card(4, "♥");
//    this.c31 = new Card(5, "♥");
//    this.c32 = new Card(6, "♥");
//    this.c33 = new Card(7, "♥");
//    this.c34 = new Card(8, "♥");
//    this.c35 = new Card(9, "♥");
//    this.c36 = new Card(10, "♥");
//    this.c37 = new Card(11, "♥");
//    this.c38 = new Card(12, "♥");
//    this.c39 = new Card(13, "♥");
//    this.c40 = new Card(1, "♠");
//    this.c41 = new Card(2, "♠");
//    this.c42 = new Card(3, "♠");
//    this.c43 = new Card(4, "♠");
//    this.c44 = new Card(5, "♠");
//    this.c45 = new Card(6, "♠");
//    this.c46 = new Card(7, "♠");
//    this.c47 = new Card(8, "♠");
//    this.c48 = new Card(9, "♠");
//    this.c49 = new Card(10, "♠");
//    this.c50 = new Card(11, "♠");
//    this.c51 = new Card(12, "♠");
//    this.c52 = new Card(13, "♠");
//
//    this.mainDeck = new ArrayList<Card>(Arrays.asList(this.c1, this.c2, this.c3, this.c4, this.c5,
//        this.c6, this.c7, this.c8, this.c9, this.c10, this.c11, this.c12, this.c13, this.c14,
//        this.c15, this.c16, this.c17, this.c18, this.c19, this.c20, this.c21, this.c22, this.c23,
//        this.c24, this.c25, this.c26, this.c27, this.c28, this.c29, this.c30, this.c31, this.c32,
//        this.c33, this.c34, this.c35, this.c36, this.c37, this.c38, this.c39, this.c40, this.c41,
//        this.c42, this.c43, this.c44, this.c45, this.c46, this.c47, this.c48, this.c49, this.c50,
//        this.c51, this.c52));
//
//    this.testDeck1 = new ArrayList<Card>(Arrays.asList(this.c1, this.c2, this.c3));
//    this.mt = new ArrayList<Card>(Arrays.asList());
//
//    this.test1 = new CardWorld(this.testDeck1);
//    this.test2 = new CardWorld(this.testDeck1, 1, 3);
//    this.blank = new CardWorld(this.mt);
//
//    this.cardWorld = new WorldScene(600, 500);
//
//  }
//
//  // test changeFlipped method
//  void testchangeFlipped(Tester t) {
//
//    initCards();
//
//    t.checkExpect(this.card1.drawCard(),
//        new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    card1.changeFlipped();
//    t.checkExpect(this.card1.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage(Integer.toString(5) + "♣", Color.black)));
//    card1.changeFlipped();
//    t.checkExpect(this.card1.drawCard(),
//        new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//
//    t.checkExpect(this.card2.drawCard(),
//        new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    card2.changeFlipped();
//    t.checkExpect(this.card2.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage(Integer.toString(6) + "♣", Color.black)));
//    card2.changeFlipped();
//    t.checkExpect(this.card2.drawCard(),
//        new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//  }
//
//  // test drawCard
//  void testDrawCard(Tester t) {
//
//    initCards();
//
//    t.checkExpect(this.c1.drawCard(), new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    c1.changeFlipped();
//    t.checkExpect(this.c1.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage("A" + "♣", Color.black)));
//
//    t.checkExpect(this.c2.drawCard(), new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    c2.changeFlipped();
//    t.checkExpect(this.c2.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage(Integer.toString(2) + "♣", Color.black)));
//
//    t.checkExpect(this.c24.drawCard(), new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    this.c24.changeFlipped();
//    t.checkExpect(this.c24.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage("J" + "♦", Color.red)));
//
//    t.checkExpect(this.c25.drawCard(), new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    this.c25.changeFlipped();
//    t.checkExpect(this.c25.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage("Q" + "♦", Color.red)));
//
//    t.checkExpect(this.c26.drawCard(), new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    this.c26.changeFlipped();
//    t.checkExpect(this.c26.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage("K" + "♦", Color.red)));
//
//    t.checkExpect(this.c39.drawCard(), new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    this.c39.changeFlipped();
//    t.checkExpect(this.c39.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage("K" + "♥", Color.red)));
//
//    t.checkExpect(this.c43.drawCard(), new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    this.c43.changeFlipped();
//    t.checkExpect(this.c43.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage("4" + "♠", Color.black)));
//
//    t.checkExpect(this.c49.drawCard(), new RectangleImage(30, 40, OutlineMode.SOLID, Color.black));
//    this.c49.changeFlipped();
//    t.checkExpect(this.c49.drawCard(),
//        new OverlayImage(new RectangleImage(30, 40, OutlineMode.OUTLINE, Color.black),
//            new TextImage("10" + "♠", Color.black)));
//
//  }
//
//  // test checkRange + IllegalArgumentException
//  void testCheckRange(Tester t) {
//
//    initCards();
//    Utils util = new Utils();
//
//    t.checkExpect(util.checkRange(this.card1.rank, 1, 13,
//        "Invalid rank: " + Integer.toString(this.card1.rank)), 5);
//    t.checkExpect(util.checkRange(this.card2.rank, 1, 13,
//        "Invalid rank: " + Integer.toString(this.card2.rank)), 6);
//    t.checkExpect(
//        util.checkRange(this.c1.rank, 1, 13, "Invalid rank: " + Integer.toString(this.c1.rank)), 1);
//    t.checkExpect(
//        util.checkRange(this.c10.rank, 1, 13, "Invalid rank: " + Integer.toString(this.c10.rank)),
//        10);
//    t.checkExpect(
//        util.checkRange(this.c20.rank, 1, 13, "Invalid rank: " + Integer.toString(this.c20.rank)),
//        7);
//    t.checkExpect(
//        util.checkRange(this.c43.rank, 1, 13, "Invalid rank: " + Integer.toString(this.c43.rank)),
//        4);
//    t.checkExpect(
//        util.checkRange(this.c24.rank, 1, 13, "Invalid rank: " + Integer.toString(this.c24.rank)),
//        11);
//    t.checkExpect(
//        util.checkRange(this.c25.rank, 1, 13, "Invalid rank: " + Integer.toString(this.c25.rank)),
//        12);
//    t.checkExpect(
//        util.checkRange(this.c26.rank, 1, 13, "Invalid rank: " + Integer.toString(this.c26.rank)),
//        13);
//
//    t.checkConstructorException(new IllegalArgumentException("Invalid rank: 14"), "Card", 14, "♣");
//    t.checkConstructorException(new IllegalArgumentException("Invalid rank: 0"), "Card", 0, "♣");
//    t.checkConstructorException(new IllegalArgumentException("Invalid rank: 15"), "Card", 15, "♣");
//
//  }
//
//  // test checkString
//  void testCheckString(Tester t) {
//
//    initCards();
//    Utils util = new Utils();
//
//    t.checkExpect(
//        util.checkString(this.card1.suit, this.suitList, "Invalid suit: " + this.card1.suit), "♣");
//    t.checkExpect(
//        util.checkString(this.card2.suit, this.suitList, "Invalid suit: " + this.card1.suit), "♣");
//    t.checkExpect(util.checkString(this.c44.suit, this.suitList, "Invalid suit: " + this.c44.suit),
//        "♠");
//    t.checkExpect(util.checkString(this.c45.suit, this.suitList, "Invalid suit: " + this.c45.suit),
//        "♠");
//    t.checkExpect(util.checkString(this.c25.suit, this.suitList, "Invalid suit: " + this.c25.suit),
//        "♦");
//    t.checkExpect(util.checkString(this.c16.suit, this.suitList, "Invalid suit: " + this.c16.suit),
//        "♦");
//    t.checkExpect(util.checkString(this.c27.suit, this.suitList, "Invalid suit: " + this.c27.suit),
//        "♥");
//    t.checkExpect(util.checkString(this.c28.suit, this.suitList, "Invalid suit: " + this.c28.suit),
//        "♥");
//
//    t.checkConstructorException(new IllegalArgumentException("Invalid suit: +"), "Card", 11, "+");
//    t.checkConstructorException(new IllegalArgumentException("Invalid suit: -"), "Card", 10, "-");
//    t.checkConstructorException(new IllegalArgumentException("Invalid suit: %"), "Card", 5, "%");
//    t.checkConstructorException(new IllegalArgumentException("Invalid suit: $"), "Card", 11, "$");
//
//  }
//
//  // test initializeDeck
//  void testInitializeDeck(Tester t) {
//
//    // random deck of cards (randDeck correct order)
//    this.c1 = new Card(9, "♠");
//    this.c2 = new Card(10, "♥");
//    this.c3 = new Card(1, "♠");
//    this.c4 = new Card(1, "♥");
//    this.c5 = new Card(8, "♦");
//    this.c6 = new Card(5, "♦");
//    this.c7 = new Card(7, "♦");
//    this.c8 = new Card(6, "♠");
//    this.c9 = new Card(2, "♥");
//    this.c10 = new Card(4, "♦");
//    this.c11 = new Card(6, "♦");
//    this.c12 = new Card(1, "♣");
//    this.c13 = new Card(8, "♥");
//    this.c14 = new Card(11, "♣");
//    this.c15 = new Card(9, "♦");
//    this.c16 = new Card(10, "♣");
//    this.c17 = new Card(5, "♠");
//    this.c18 = new Card(9, "♥");
//    this.c19 = new Card(12, "♣");
//    this.c20 = new Card(2, "♠");
//    this.c21 = new Card(1, "♦");
//    this.c22 = new Card(12, "♥");
//    this.c23 = new Card(9, "♣");
//    this.c24 = new Card(6, "♣");
//    this.c25 = new Card(12, "♠");
//    this.c26 = new Card(3, "♣");
//    this.c27 = new Card(2, "♣");
//    this.c28 = new Card(3, "♦");
//    this.c29 = new Card(11, "♦");
//    this.c30 = new Card(3, "♠");
//    this.c31 = new Card(2, "♦");
//    this.c32 = new Card(12, "♦");
//    this.c33 = new Card(8, "♣");
//    this.c34 = new Card(4, "♠");
//    this.c35 = new Card(11, "♠");
//    this.c36 = new Card(13, "♦");
//    this.c37 = new Card(6, "♥");
//    this.c38 = new Card(11, "♥");
//    this.c39 = new Card(7, "♥");
//    this.c40 = new Card(10, "♦");
//    this.c41 = new Card(13, "♠");
//    this.c42 = new Card(5, "♣");
//    this.c43 = new Card(3, "♥");
//    this.c44 = new Card(8, "♠");
//    this.c45 = new Card(10, "♠");
//    this.c46 = new Card(7, "♣");
//    this.c47 = new Card(4, "♥");
//    this.c48 = new Card(13, "♥");
//    this.c49 = new Card(7, "♠");
//    this.c50 = new Card(4, "♣");
//    this.c51 = new Card(5, "♥");
//    this.c52 = new Card(13, "♣");
//
//    ArrayList<Card> randDeck = new ArrayList<Card>(Arrays.asList(this.c1, this.c2, this.c3, this.c4,
//        this.c5, this.c6, this.c7, this.c8, this.c9, this.c10, this.c11, this.c12, this.c13,
//        this.c14, this.c15, this.c16, this.c17, this.c18, this.c19, this.c20, this.c21, this.c22,
//        this.c23, this.c24, this.c25, this.c26, this.c27, this.c28, this.c29, this.c30, this.c31,
//        this.c32, this.c33, this.c34, this.c35, this.c36, this.c37, this.c38, this.c39, this.c40,
//        this.c41, this.c42, this.c43, this.c44, this.c45, this.c46, this.c47, this.c48, this.c49,
//        this.c50, this.c51, this.c52));
//
//    ArrayList<Card> randDeck2 = new ArrayList<Card>(Arrays.asList(this.c29, this.c33, this.c37,
//        this.c4, this.c31, this.c11, this.c5, this.c23, this.c6, this.c18, this.c39, this.c7,
//        this.c28, this.c51, this.c27, this.c30, this.c2, this.c41, this.c12, this.c20, this.c32,
//        this.c19, this.c13, this.c14, this.c22, this.c17, this.c47, this.c26, this.c48, this.c49,
//        this.c25, this.c8, this.c45, this.c42, this.c43, this.c35, this.c9, this.c46, this.c15,
//        this.c44, this.c3, this.c21, this.c34, this.c40, this.c36, this.c16, this.c24, this.c50,
//        this.c52, this.c10, this.c38, this.c1));
//
//    ArrayList<Card> randDeck3 = new ArrayList<Card>(
//        Arrays.asList(this.c8, this.c41, this.c14, this.c1, this.c50, this.c42, this.c47, this.c7,
//            this.c38, this.c20, this.c28, this.c22, this.c27, this.c32, this.c37, this.c6, this.c35,
//            this.c52, this.c21, this.c45, this.c49, this.c15, this.c51, this.c3, this.c25, this.c10,
//            this.c26, this.c24, this.c16, this.c29, this.c48, this.c30, this.c31, this.c18,
//            this.c19, this.c33, this.c36, this.c2, this.c12, this.c34, this.c17, this.c46, this.c43,
//            this.c39, this.c13, this.c4, this.c40, this.c9, this.c5, this.c44, this.c23, this.c11));
//
//    t.checkExpect(new CardWorld(new Random(5)).initializeDeck(), randDeck);
//    t.checkExpect(new CardWorld(new Random(6)).initializeDeck(), randDeck2);
//    t.checkExpect(new CardWorld(new Random(7)).initializeDeck(), randDeck3);
//
//  }
//
//  // test convertRank
//  void testConvertRank(Tester t) {
//    initCards();
//
//    t.checkExpect(c1.convertRank(), "A");
//    t.checkExpect(c8.convertRank(), "8");
//    t.checkExpect(c9.convertRank(), "9");
//    t.checkExpect(c10.convertRank(), "10");
//    t.checkExpect(c11.convertRank(), "J");
//    t.checkExpect(c12.convertRank(), "Q");
//    t.checkExpect(c13.convertRank(), "K");
//    t.checkExpect(c27.convertRank(), "A");
//    t.checkExpect(c37.convertRank(), "J");
//    t.checkExpect(c38.convertRank(), "Q");
//    t.checkExpect(c39.convertRank(), "K");
//  }
//
//  // test makeScene
//  void testMakeScene(Tester t) {
//
//    initCards();
//
//    WorldScene cardWorld = new WorldScene(600, 500);
//    WorldScene cardWorldFull = new WorldScene(600, 500);
//
//    t.checkExpect(this.blank.makeScene(), cardWorld);
//
//    cardWorld.placeImageXY(this.c1.drawCard(), 50, 100);
//    cardWorld.placeImageXY(this.c2.drawCard(), 90, 100);
//    cardWorld.placeImageXY(this.c3.drawCard(), 130, 100);
//
//    t.checkExpect(this.test2.makeScene(), cardWorld);
//
//    cardWorldFull.placeImageXY(this.c1.drawCard(), 50, 100);
//    cardWorldFull.placeImageXY(this.c2.drawCard(), 90, 100);
//    cardWorldFull.placeImageXY(this.c3.drawCard(), 130, 100);
//    cardWorldFull.placeImageXY(this.c4.drawCard(), 170, 100);
//    cardWorldFull.placeImageXY(this.c5.drawCard(), 210, 100);
//    cardWorldFull.placeImageXY(this.c6.drawCard(), 250, 100);
//    cardWorldFull.placeImageXY(this.c7.drawCard(), 290, 100);
//    cardWorldFull.placeImageXY(this.c8.drawCard(), 330, 100);
//    cardWorldFull.placeImageXY(this.c9.drawCard(), 370, 100);
//    cardWorldFull.placeImageXY(this.c10.drawCard(), 410, 100);
//    cardWorldFull.placeImageXY(this.c11.drawCard(), 450, 100);
//    cardWorldFull.placeImageXY(this.c12.drawCard(), 490, 100);
//    cardWorldFull.placeImageXY(this.c13.drawCard(), 530, 100);
//    cardWorldFull.placeImageXY(this.c14.drawCard(), 50, 200);
//    cardWorldFull.placeImageXY(this.c15.drawCard(), 90, 200);
//    cardWorldFull.placeImageXY(this.c16.drawCard(), 130, 200);
//    cardWorldFull.placeImageXY(this.c17.drawCard(), 170, 200);
//    cardWorldFull.placeImageXY(this.c18.drawCard(), 210, 200);
//    cardWorldFull.placeImageXY(this.c19.drawCard(), 250, 200);
//    cardWorldFull.placeImageXY(this.c20.drawCard(), 290, 200);
//    cardWorldFull.placeImageXY(this.c21.drawCard(), 330, 200);
//    cardWorldFull.placeImageXY(this.c22.drawCard(), 370, 200);
//    cardWorldFull.placeImageXY(this.c23.drawCard(), 410, 200);
//    cardWorldFull.placeImageXY(this.c24.drawCard(), 450, 200);
//    cardWorldFull.placeImageXY(this.c25.drawCard(), 490, 200);
//    cardWorldFull.placeImageXY(this.c26.drawCard(), 530, 200);
//    cardWorldFull.placeImageXY(this.c27.drawCard(), 50, 300);
//    cardWorldFull.placeImageXY(this.c28.drawCard(), 90, 300);
//    cardWorldFull.placeImageXY(this.c29.drawCard(), 130, 300);
//    cardWorldFull.placeImageXY(this.c30.drawCard(), 170, 300);
//    cardWorldFull.placeImageXY(this.c31.drawCard(), 210, 300);
//    cardWorldFull.placeImageXY(this.c32.drawCard(), 250, 300);
//    cardWorldFull.placeImageXY(this.c33.drawCard(), 290, 300);
//    cardWorldFull.placeImageXY(this.c34.drawCard(), 330, 300);
//    cardWorldFull.placeImageXY(this.c35.drawCard(), 370, 300);
//    cardWorldFull.placeImageXY(this.c36.drawCard(), 410, 300);
//    cardWorldFull.placeImageXY(this.c37.drawCard(), 450, 300);
//    cardWorldFull.placeImageXY(this.c38.drawCard(), 490, 300);
//    cardWorldFull.placeImageXY(this.c39.drawCard(), 530, 300);
//    cardWorldFull.placeImageXY(this.c40.drawCard(), 50, 400);
//    cardWorldFull.placeImageXY(this.c41.drawCard(), 90, 400);
//    cardWorldFull.placeImageXY(this.c42.drawCard(), 130, 400);
//    cardWorldFull.placeImageXY(this.c43.drawCard(), 170, 400);
//    cardWorldFull.placeImageXY(this.c44.drawCard(), 210, 400);
//    cardWorldFull.placeImageXY(this.c45.drawCard(), 250, 400);
//    cardWorldFull.placeImageXY(this.c46.drawCard(), 290, 400);
//    cardWorldFull.placeImageXY(this.c47.drawCard(), 330, 400);
//    cardWorldFull.placeImageXY(this.c48.drawCard(), 370, 400);
//    cardWorldFull.placeImageXY(this.c49.drawCard(), 410, 400);
//    cardWorldFull.placeImageXY(this.c50.drawCard(), 450, 400);
//    cardWorldFull.placeImageXY(this.c51.drawCard(), 490, 400);
//    cardWorldFull.placeImageXY(this.c52.drawCard(), 530, 400);
//
//    t.checkExpect(new CardWorld().makeScene(), cardWorldFull);
//
//  }
//
//}