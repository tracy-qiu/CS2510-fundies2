// to represent a post 
class Post {
  String user;
  String text;
  int likes;
  int timeStamp;

  // the constructor
  Post(String user, String text, int likes, int timeStamp) {
    this.user = user;
    this.text = text;
    this.likes = likes;
    this.timeStamp = timeStamp;
  }
}

// examples and tests for the class that represents a post
class ExamplesPost {
  ExamplesPost() {
  }

  Post personalNews = new Post("iheartfundies",
      "Some personal news: I will be taking fundies 2 this fall", 200, 1625699955);
  Post cupcakeAd = new Post("thequeenscups", "life is too short to not eat cupcakes", 48,
      1631661555);
  Post tweet = new Post("natureaccount", "today is a beautiful day", 18, 1621669035);
}