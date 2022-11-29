import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import tester.Tester;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  Random random;
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode() {
    this.random = rand;
    this.code = this.initEncoder();
  }

  //Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode(Random rand) {
    this.random = rand;
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code
  PermutationCode(ArrayList<Character> code) {
    this.random = rand;
    this.code = code;
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {

    ArrayList<Character> alphabetCopy = new ArrayList<Character>(this.alphabet);
    ArrayList<Character> randomEncoder = new ArrayList<Character>();

    int randomIndex;
    while (alphabetCopy.size() > 0) {
      randomIndex = random.nextInt(alphabetCopy.size());
      randomEncoder.add(0, alphabetCopy.get(randomIndex));
      alphabetCopy.remove(alphabetCopy.get(randomIndex));
    }
    return randomEncoder;
  }

  // produce a decoded String from the given String
  String decode(String code) {
    return this.customHelper(code, true);
  }

  //  // before abstraction 
  //  // produce a decoded String from the given String
  //  String decodeHelper(String code) {
  //    String mt = "";
  //
  //    for (int i = 0; i < code.length(); i = i + 1) {
  //
  //      char letter = this.alphabet.get(this.code.indexOf(code.charAt(i)));
  //      mt = mt + letter;
  //    }
  //
  //    return mt;
  //  }

  // produce an encoded String from the given String
  String encode(String source) {
    return this.customHelper(source, false);
  }

  //  // before abstraction 
  //  // produce an encoded String from the given String
  //  String encodeHelper(String source) {
  //    String mt = "";
  //
  //    for (int n = 0; n < source.length(); n = n + 1) {
  //      char randLetter = this.code.get(this.alphabet.indexOf(source.charAt(n)));
  //      mt = mt + randLetter;
  //    }
  //
  //    return mt;
  //  }

  // decode/encode helper abstraction
  String customHelper(String given, boolean decode) {
    String mt = "";

    if (decode) {
      for (int i = 0; i < given.length(); i = i + 1) {

        char letter = this.alphabet.get(this.code.indexOf(given.charAt(i)));
        mt = mt + letter;
      }
    }
    else {

      for (int n = 0; n < given.length(); n = n + 1) {
        char randLetter = this.code.get(this.alphabet.indexOf(given.charAt(n)));
        mt = mt + randLetter;
      }
    }
    return mt;
  }
}

// tests and examples of permutations 
class ExamplesPermutation {
  ExamplesPermutation() {
  }

  // test fields 
  ArrayList<Character> code1;
  ArrayList<Character> code2;
  ArrayList<Character> code3;
  PermutationCode pc1;
  PermutationCode pc2;
  PermutationCode pc3;
  ArrayList<Character> code4;
  ArrayList<Character> code8;
  ArrayList<Character> code25;
  PermutationCode pc4;
  PermutationCode pc8;
  PermutationCode pc25;

  // assign values to fields 
  void initCode() {
    code1 = new ArrayList<Character>(Arrays.asList('b', 'e', 'a', 'c', 'd'));
    code2 = new ArrayList<Character>(Arrays.asList('c', 'b', 'e', 'f', 'd', 'a'));
    code3 = new ArrayList<Character>(Arrays.asList('a', 'c', 'b', 'd'));
    pc1 = new PermutationCode(code1);
    pc2 = new PermutationCode(code2);
    pc3 = new PermutationCode(code3);

    code4 = new ArrayList<Character>(Arrays.asList('z', 'p', 'g', 'u', 'k', 'm', 'n', 'x', 'v', 'q',
        'w', 'b', 'd', 'a', 'l', 'i', 'j', 'f', 'o', 't', 'e', 'c', 'r', 's', 'y', 'h'));
    code8 = new ArrayList<Character>(Arrays.asList('z', 'i', 'v', 'a', 'k', 'w', 'f', 'x', 'u', 'y',
        'l', 't', 'e', 'o', 'b', 'c', 'm', 's', 'h', 'p', 'n', 'g', 'j', 'r', 'q', 'd'));
    code25 = new ArrayList<Character>(Arrays.asList('c', 'w', 's', 'd', 'f', 'n', 'k', 'x', 'v',
        'o', 'm', 'h', 'e', 'g', 'y', 'r', 'z', 'p', 'q', 'l', 'b', 'j', 'i', 'u', 't', 'a'));

    pc4 = new PermutationCode(new Random(4));
    pc8 = new PermutationCode(new Random(8));
    pc25 = new PermutationCode(new Random(25));
  }

  // test decode and customHelper
  void testDecode(Tester t) {
    initCode();

    t.checkExpect(this.pc1.decode("abeedc"), "cabbed");
    t.checkExpect(this.pc1.customHelper("abecdc", true), "cabded");
    t.checkExpect(this.pc2.decode("bdaceffd"), "befacdde");
    t.checkExpect(this.pc2.customHelper("bdacefdd", true), "befacdee");
    t.checkExpect(this.pc3.decode("dacbb"), "dabcc");
    t.checkExpect(this.pc3.customHelper("daccb", true), "dabbc");
  }

  // test encode and customHelper
  void testEncode(Tester t) {
    initCode();

    t.checkExpect(this.pc1.encode("badace"), "ebcbad");
    t.checkExpect(this.pc1.customHelper("badace", false), "ebcbad");
    t.checkExpect(this.pc2.encode("feddacb"), "adffceb");
    t.checkExpect(this.pc2.customHelper("fedaacb", false), "adfcceb");
    t.checkExpect(this.pc3.encode("badcd"), "cadbd");
    t.checkExpect(this.pc3.customHelper("bacbd", false), "cabcd");
  }

  // test initEncoder
  void testInitEncoder(Tester t) {
    initCode();

    t.checkExpect(pc4.initEncoder(), code4);
    t.checkExpect(pc8.initEncoder(), code8);
    t.checkExpect(pc25.initEncoder(), code25);
  }

}
