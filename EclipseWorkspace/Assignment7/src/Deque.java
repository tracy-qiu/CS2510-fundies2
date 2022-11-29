import java.util.function.Predicate;

import tester.Tester;

// represents a deque
class Deque<T> {
  Sentinel<T> header;

  // constructors
  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // determines size of deque
  int size() {
    return this.header.next.sizeHelper();
  }

  // insert at start of deque
  void addAtHead(T value) {
    // // Without abstraction
    // Node<T> newNode = new Node<T>(value);
    // this.header.insertHead(newNode, this.header.next);
    addAtCustom(value, true);
  }

  // insert at the end of deque
  void addAtTail(T value) {
    // // Without abstraction
    // Node<T> newNode = new Node<T>(value);
    // this.header.insertTail(newNode, this.header.next, this.header.prev);
    addAtCustom(value, false);
  }

  // insert at the start of end of deque
  void addAtCustom(T value, boolean atHead) {
    Node<T> newNode = new Node<T>(value);
    if (atHead) {
      this.header.insertHead(newNode, this.header.next);
    }
    else {
      this.header.insertTail(newNode, this.header.next, this.header.prev);
    }
  }

  // remove the first thing from the deque
  // field of field of field access
  T removeFromHead() {
    return this.header.next.removeFromHelper();
  }

  // remove the last thing from the deque
  T removeFromTail() {
    return this.header.prev.removeFromHelper();
  }

  // modify the deque by removing the given node
  void removeNode(ANode<T> given) {
    given.removeFromHelper();
  }

  // finds first node that passes the predicate
  ANode<T> find(Predicate<T> predicate) {
    return this.header.next.findHelper(predicate);
  }

}

//represents ANode 
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // updates the next
  void updateNext(ANode<T> next) {
    this.next = next;
  }

  // updates the previous
  void updatePrev(ANode<T> prev) {
    this.prev = prev;
  }

  // determines the size of a deque
  abstract int sizeHelper();

  // inserts a node at the beginning of a deque
  void insertHead(Node<T> newNode, ANode<T> oldFirst) {
    this.next = newNode;
    // dont have to update previous
    newNode.next = oldFirst;
    newNode.prev = this;
    oldFirst.prev = newNode;
  }

  // inserts node at the end of a deque
  void insertTail(Node<T> newNode, ANode<T> firstNode, ANode<T> oldLast) {
    if (this.prev == firstNode) {
      this.prev = newNode;
      firstNode.next = newNode;
      newNode.next = this;
      newNode.prev = firstNode;
    }
    else {
      this.prev = newNode;
      oldLast.next = newNode;
      newNode.next = this;
      newNode.prev = oldLast;
    }

  }

  // removes node from deque
  abstract T removeFromHelper();

  // find node that passes the given predicate
  abstract ANode<T> findHelper(Predicate<T> predicate);

}

// represents a sentinel 
class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // determines the size of a deque
  int sizeHelper() {
    return 0;
  }

  // removes the node from the deque
  T removeFromHelper() {
    throw new RuntimeException("Empty Deque!");
  }

  // find node that passes the given predicate
  ANode<T> findHelper(Predicate<T> predicate) {
    return this;
  }
}

// represents a Node
class Node<T> extends ANode<T> {
  T data;

  // node constructor
  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;
    if (next == null) {
      throw new IllegalArgumentException("Node is Null!");
    }
    this.next = next;
    // update the previous
    next.updatePrev(this);

    if (prev == null) {
      throw new IllegalArgumentException("Node is Null!");
    }
    this.prev = prev;
    // update the next
    prev.updateNext(this);

  }

  // determines size of the deque
  int sizeHelper() {
    return 1 + this.next.sizeHelper();
  }

  // returns data of node that was removed and patches up the data
  T removeFromHelper() {
    this.next.prev = this.prev;
    this.prev.next = this.next;

    return this.data;
  }

  // returns the first node that passes the predicate
  ANode<T> findHelper(Predicate<T> predicate) {
    if (predicate.test(data)) {
      return this;
    }
    else {
      return this.next.findHelper(predicate);
    }
  }

}

// Predicate that determines if string length equals 3 
class StringLength3 implements Predicate<String> {
  public boolean test(String str) {
    return str.length() == 3;
  }
}

// tests and examples of deques 
class ExamplesDeque {
  ExamplesDeque() {
  }

  Sentinel<String> mtsent;
  Deque<String> deque1;

  Sentinel<String> sent2;
  Node<String> first;
  Node<String> second;
  Node<String> third;
  Node<String> fourth;
  Deque<String> deque2;

  Sentinel<String> sent4;
  Node<String> a;
  Deque<String> deque4;

  Sentinel<String> sent5;
  Node<String> a5;
  Node<String> first5;
  Node<String> second5;
  Node<String> third5;
  Node<String> fourth5;
  Deque<String> deque5;

  Sentinel<String> sent6;
  Node<String> a6;
  Node<String> b6;
  Deque<String> deque6;

  Sentinel<String> sent7;
  Node<String> b7;
  Node<String> first7;
  Node<String> second7;
  Node<String> third7;
  Node<String> fourth7;
  Deque<String> deque7;

  Sentinel<String> sent8;
  Node<String> first8;
  Node<String> second8;
  Node<String> third8;
  Node<String> fourth8;
  Node<String> fifth8;
  Deque<String> deque8;

  Sentinel<String> sent9;
  Node<String> first9;
  Node<String> second9;
  Node<String> third9;
  Node<String> fourth9;
  Node<String> fifth9;
  Node<String> sixth9;
  Deque<String> deque9;

  void initDeque() {

    mtsent = new Sentinel<String>();
    deque1 = new Deque<String>(this.mtsent);

    sent2 = new Sentinel<String>();
    first = new Node<String>("abc", this.sent2, this.sent2);
    second = new Node<String>("bcd", this.sent2, this.first);
    third = new Node<String>("cde", this.sent2, this.second);
    fourth = new Node<String>("def", this.sent2, this.third);
    deque2 = new Deque<String>(this.sent2);

    sent4 = new Sentinel<String>();
    a = new Node<String>("a", this.sent4, this.sent4);
    deque4 = new Deque<String>(this.sent4);

    sent5 = new Sentinel<String>();
    a5 = new Node<String>("a", this.sent5, this.sent5);
    first5 = new Node<String>("abc", this.sent5, this.a5);
    second5 = new Node<String>("bcd", this.sent5, this.first5);
    third5 = new Node<String>("cde", this.sent5, this.second5);
    fourth5 = new Node<String>("def", this.sent5, this.third5);
    deque5 = new Deque<String>(this.sent5);

    sent6 = new Sentinel<String>();
    a6 = new Node<String>("a", this.sent6, this.sent6);
    b6 = new Node<String>("b", this.sent6, this.a6);
    deque6 = new Deque<String>(this.sent6);

    sent7 = new Sentinel<String>();
    first7 = new Node<String>("abc", this.sent7, this.sent7);
    second7 = new Node<String>("bcd", this.sent7, this.first7);
    third7 = new Node<String>("cde", this.sent7, this.second7);
    fourth7 = new Node<String>("def", this.sent7, this.third7);
    b7 = new Node<String>("b", this.sent7, this.fourth7);
    deque7 = new Deque<String>(this.sent7);

    sent8 = new Sentinel<String>();
    first8 = new Node<String>("cheese", this.sent8, this.sent8);
    second8 = new Node<String>("pizza", this.sent8, this.first8);
    third8 = new Node<String>("tea", this.sent8, this.second8);
    fourth8 = new Node<String>("cookie", this.sent8, this.third8);
    fifth8 = new Node<String>("berry", this.sent8, this.fourth8);
    deque8 = new Deque<String>(this.sent8);

    sent9 = new Sentinel<String>();
    first9 = new Node<String>("cheese", this.sent9, this.sent9);
    second9 = new Node<String>("pizza", this.sent9, this.first9);
    third9 = new Node<String>("tea", this.sent9, this.second9);
    fourth9 = new Node<String>("cookie", this.sent9, this.third9);
    fifth9 = new Node<String>("berry", this.sent9, this.fourth9);
    sixth9 = new Node<String>("added", this.sent9, this.fifth9);
    deque9 = new Deque<String>(this.sent9);

  }

  // test sizeMethod
  void testSize(Tester t) {
    initDeque();

    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque4.size(), 1);
    t.checkExpect(this.deque6.size(), 2);
    t.checkExpect(this.deque8.size(), 5);
  }

  // test addToHead
  void testAddAtHead(Tester t) {
    initDeque();

    this.deque1.addAtHead("a");
    this.deque2.addAtHead("a");

    t.checkExpect(this.deque1, this.deque4);
    t.checkExpect(this.deque2, this.deque5);
  }

  // test addToTail
  void testAddAtTail(Tester t) {
    initDeque();

    this.deque4.addAtTail("b");
    this.deque2.addAtTail("b");
    this.deque8.addAtTail("added");

    t.checkExpect(this.deque4, this.deque6);
    t.checkExpect(this.deque2, this.deque7);
    t.checkExpect(this.deque8, this.deque9);
  }

  // test addAtCustom
  void testAddAtCustom(Tester t) {
    initDeque();

    this.deque2.addAtCustom("a", true);
    this.deque4.addAtCustom("b", false);
    this.deque8.addAtCustom("added", false);

    t.checkExpect(this.deque2, this.deque5);
    t.checkExpect(this.deque4, this.deque6);
    t.checkExpect(this.deque8, this.deque9);
  }

  // test RemoveFromHead
  void testRemoveFromHead(Tester t) {
    initDeque();

    t.checkException("testRemoveFromHead", new RuntimeException("Empty Deque!"), deque1,
        "removeFromHead");
    t.checkExpect(this.deque2.removeFromHead(), "abc");
    t.checkExpect(this.deque4.removeFromHead(), "a");
    t.checkExpect(this.deque8.removeFromHead(), "cheese");

  }

  // test RemoveFromTail
  void testRemoveFromTail(Tester t) {
    initDeque();

    t.checkException("testRemoveFromTail", new RuntimeException("Empty Deque!"), deque1,
        "removeFromTail");
    t.checkExpect(this.deque2.removeFromTail(), "def");
    t.checkExpect(this.deque4.removeFromTail(), "a");
    t.checkExpect(this.deque8.removeFromTail(), "berry");
  }

  // test removeNode
  void testRemoveNode(Tester t) {
    initDeque();

    t.checkException("testRemoveNode", new RuntimeException("Empty Deque!"), deque1, "removeNode",
        mtsent);
    t.checkException("testRemoveNode", new RuntimeException("Empty Deque!"), deque5, "removeNode",
        mtsent);

    this.deque5.removeNode(a5);
    this.deque4.removeNode(a);
    this.deque9.removeNode(sixth9);

    t.checkExpect(this.deque4, this.deque1);
    t.checkExpect(this.deque5, this.deque2);
    t.checkExpect(this.deque9, this.deque8);
  }

  // test find
  void testFind(Tester t) {
    initDeque();

    t.checkExpect(this.deque1.find(new StringLength3()), this.deque1.header);
    t.checkExpect(this.deque2.find(new StringLength3()), this.deque2.header.next);
    t.checkExpect(this.deque4.find(new StringLength3()), this.deque4.header);
    t.checkExpect(this.deque8.find(new StringLength3()), this.deque8.header.next.next.next);
  }

  // test Node constructor exceptions
  boolean testCheckConstructorException(Tester t) {
    return t.checkConstructorException(new IllegalArgumentException("Node is Null!"), "Node", "abc",
        null, first)
        && t.checkConstructorException(new IllegalArgumentException("Node is Null!"), "Node", "xyz",
            first, null)
        && t.checkConstructorException(new IllegalArgumentException("Node is Null!"), "Node",
            "hello", null, null);
  }

  // test updateNext
  void testUpdateNext(Tester t) {
    initDeque();

    ANode<String> first = new Node<String>("abc", mtsent, mtsent);
    ANode<String> next = new Node<String>("x", mtsent, first);
    ANode<String> firstNext = new Node<String>("abc", next, mtsent);

    first.updateNext(next);
    t.checkExpect(first, firstNext);

    ANode<String> second = new Node<String>("cba", mtsent, mtsent);
    ANode<String> next2 = new Node<String>("x", mtsent, second);
    ANode<String> secondNext = new Node<String>("cba", next2, mtsent);

    second.updateNext(next2);
    t.checkExpect(second, secondNext);

  }

  // test updatePrev
  void testUpdatePrev(Tester t) {
    initDeque();

    ANode<String> first = new Node<String>("abc", mtsent, mtsent);
    ANode<String> prev = new Node<String>("y", mtsent, mtsent);
    ANode<String> firstPrev = new Node<String>("abc", mtsent, prev);

    first.updatePrev(prev);
    t.checkExpect(first, firstPrev);

    ANode<String> second = new Node<String>("cba", mtsent, mtsent);
    ANode<String> prev2 = new Node<String>("y", mtsent, mtsent);
    ANode<String> secondPrev = new Node<String>("cba", mtsent, prev2);

    second.updatePrev(prev2);
    t.checkExpect(second, secondPrev);

  }

  // test insert head by verifying size change
  void testInsertHead(Tester t) {
    initDeque();

    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque4.size(), 1);

    this.deque1.header.insertHead(a, mtsent);
    t.checkExpect(this.deque1.size(), 1);

    this.deque2.header.insertHead(a, first);
    t.checkExpect(this.deque2.size(), 5);

    initDeque();

    Node<String> b = new Node<String>("b", this.sent4, this.sent4);
    this.deque4.header.insertHead(b, a);
    t.checkExpect(this.deque4.size(), 2);

  }

  // test insert tail by verifying size change
  void testInsertTail(Tester t) {
    initDeque();

    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque4.size(), 1);

    this.deque1.header.insertTail(a, this.mtsent, this.mtsent);
    t.checkExpect(this.deque1.size(), 1);

    initDeque();

    this.deque2.header.insertTail(a, first, fourth);
    t.checkExpect(this.deque2.size(), 5);

    initDeque();

    Node<String> b = new Node<String>("b", this.sent4, this.a);
    this.deque4.header.insertTail(b, a, a);
    t.checkExpect(this.deque4.size(), 2);

  }

  // test sizeHelper
  void testSizeHelper(Tester t) {
    initDeque();

    t.checkExpect(this.mtsent.sizeHelper(), 0);
    t.checkExpect(this.b6.sizeHelper(), 1);
    t.checkExpect(this.third7.sizeHelper(), 3);
    t.checkExpect(this.fourth8.sizeHelper(), 2);
    t.checkExpect(this.fifth9.sizeHelper(), 2);
  }

  // test removeFromHelper
  void testRemoveFromHelper(Tester t) {
    initDeque();

    t.checkException("testRemoveFromHelper", new RuntimeException("Empty Deque!"), mtsent,
        "removeFromHelper");
    t.checkExpect(this.b6.removeFromHelper(), "b");
    t.checkExpect(this.third7.removeFromHelper(), "cde");
    t.checkExpect(this.fourth8.removeFromHelper(), "cookie");
    t.checkExpect(this.fifth9.removeFromHelper(), "berry");
  }

  // test findHelper
  void testFindHelper(Tester t) {
    t.checkExpect(this.mtsent.findHelper(new StringLength3()), this.deque1.header);
    t.checkExpect(this.sent2.findHelper(new StringLength3()), this.deque2.header);
    t.checkExpect(this.sent4.findHelper(new StringLength3()), this.deque4.header);
    t.checkExpect(this.first9.findHelper(new StringLength3()), this.first9.next.next);
  }

}
