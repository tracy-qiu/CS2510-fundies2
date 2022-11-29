import tester.Tester;

// a piece of media
interface IMedia {

  // is this media really old?
  boolean isReallyOld();

  // are captions available in this language?
  boolean isCaptionAvailable(String language);

  // a string showing the proper display of the media
  String format();
}

// to represent a piece of media 
abstract class AMedia implements IMedia {
  String title;
  ILoString captionOptions; //available captions 

  AMedia(String title, ILoString captionOptions) {
    this.title = title;
    this.captionOptions = captionOptions;
  }

  /*  TEMPLATE 
  Fields:
  ... this.title ...                   -- String
  ... this.captionOptions ...          -- ILoString
  Methods:
  ... this.isReallyOld() ...                 -- int 
  ... this.isCaptionAvailable(String) ...    -- boolean
  ... this.format() ...                      -- String
  Methods for fields:
  ... this.captionOptions.isCaptionAvailable(String) ...    -- boolean
  */

  // is this media really old?
  public boolean isReallyOld() {
    return false;
  }

  // are captions available in this language?
  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.captionAvailable(language);
  }

  // a string showing the proper display of the media
  public abstract String format();
}

// represents a movie
class Movie extends AMedia {
  int year;

  Movie(String title, int year, ILoString captionOptions) {
    super(title, captionOptions);
    this.year = year;
  }

  /*  TEMPLATE 
  Fields:
  ... this.title ...                   -- String
  ... this.year ...                    -- int 
  ... this.captionOptions ...          -- ILoString
  Methods:
  ... this.isReallyOld() ...                 -- int 
  ... this.isCaptionAvailable(String) ...    -- boolean
  ... this.format() ...                      -- String
  Methods for fields:
  ... this.captionOptions.isCaptionAvailable(String) ...    -- boolean
  */

  // is this movie really old?
  @Override
  public boolean isReallyOld() {
    return (year < 1930);
  }

  // a string showing the proper display of a movie 
  public String format() {
    return this.title + " (" + this.year + ")";
  }
}

// represents a TV episode
class TVEpisode extends AMedia {
  String showName;
  int seasonNumber;
  int episodeOfSeason;

  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    super(title, captionOptions);
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
  }

  /*  TEMPLATE 
  Fields:
  ... this.title ...                   -- String
  ... this.showName ...                -- String
  ... this.seasonNumber ...            -- int
  ... this.episodeOfSeason ...         -- int
  ... this.captionOptions ...          -- ILoString
  Methods:
  ... this.isReallyOld() ...                 -- int 
  ... this.isCaptionAvailable(String) ...    -- boolean
  ... this.format() ...                      -- String
  Methods for fields:
  ... this.captionOptions.isCaptionAvailable(String) ...    -- boolean
  */

  // a string showing the proper display of a tv show episode
  public String format() {
    return this.showName + " " + this.seasonNumber + "." + this.episodeOfSeason + " - "
        + this.title;
  }
}

// represents a YouTube video
class YTVideo extends AMedia {
  String channelName;

  public YTVideo(String title, String channelName, ILoString captionOptions) {
    super(title, captionOptions);
    this.channelName = channelName;
  }

  /*  TEMPLATE 
  Fields:
  ... this.title ...                   -- String
  ... this.channelName ...             -- String
  ... this.captionOptions ...          -- ILoString
  Methods:
  ... this.isReallyOld() ...                 -- int 
  ... this.isCaptionAvailable(String) ...    -- boolean
  ... this.format() ...                      -- String
  Methods for fields:
  ... this.captionOptions.isCaptionAvailable(String) ...    -- boolean
  */

  // a string showing the proper display of a youtube video 
  public String format() {
    return this.title + " by " + this.channelName;
  }

}

// lists of strings
interface ILoString {
  boolean captionAvailable(String language);
}

// an empty list of strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  /*  TEMPLATE 
  Fields:
  
  Methods:
  ... this.captionAvailable(String) ...    -- boolean
  
  Methods for fields:
  
  */

  // determines if language is in the list of Strings 
  public boolean captionAvailable(String language) {
    return false;
  }
}

// a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /*  TEMPLATE 
  Fields:
  ... this.first ...         -- String
  ... this.rest ...          -- ILoString
  
  Methods:
  ... this.captionAvailable(String) ...    -- boolean
  
  Methods for fields:
  */

  // determines if language is in the list of Strings 
  public boolean captionAvailable(String language) {
    if (this.first == language) {
      return true;
    }
    else {
      return this.rest.captionAvailable(language);
    }
  }
}

// examples and tests for types of media 
class ExamplesMedia {
  ExamplesMedia() {
  }

  // define lists of captions 
  ILoString captions1 = new ConsLoString("English",
      new ConsLoString("Spanish", new ConsLoString("French", new MtLoString())));
  ILoString captions2 = new ConsLoString("Hindi",
      new ConsLoString("Mandarin", new ConsLoString("Russian", new MtLoString())));
  ILoString captions3 = new ConsLoString("Korean", new ConsLoString("Italian", new MtLoString()));
  ILoString captions4 = new ConsLoString("German",
      new ConsLoString("Spanish", new ConsLoString("French", new MtLoString())));
  ILoString captions5 = new ConsLoString("Japanese",
      new ConsLoString("Spanish", new ConsLoString("French", new MtLoString())));
  ILoString captions6 = new ConsLoString("English",
      new ConsLoString("Spanish", new ConsLoString("Arabic", new MtLoString())));

  //define objects of media 
  IMedia m1 = new Movie("Shrek", 2005, captions1);
  IMedia m2 = new Movie("Another Day", 1928, captions2);
  IMedia tv1 = new TVEpisode("Rainy Day", "Grey's Anatomy", 5, 3, captions3);
  IMedia tv2 = new TVEpisode("Cold Case", "Law and Order", 2, 11, captions4);
  IMedia yt1 = new YTVideo("Cookie Recipe", "CookingWithJoe", captions5);
  IMedia yt2 = new YTVideo("Music Video", "Vevo", captions6);

  // test isReallyOld method
  boolean testIsReallyOld(Tester t) {
    return t.checkExpect(this.m1.isReallyOld(), false) && t.checkExpect(this.m2.isReallyOld(), true)
        && t.checkExpect(this.tv1.isReallyOld(), false)
        && t.checkExpect(this.yt2.isReallyOld(), false);
  }

  // test isCaptionAvailable method 
  boolean testIsCaptionAvailable(Tester t) {
    return t.checkExpect(this.m2.isCaptionAvailable("Mandarin"), true)
        && t.checkExpect(this.tv1.isCaptionAvailable("Hindi"), false)
        && t.checkExpect(this.tv2.isCaptionAvailable("German"), true)
        && t.checkExpect(this.yt1.isCaptionAvailable("English"), false);
  }

  // test format method 
  boolean testFormat(Tester t) {
    return t.checkExpect(this.m1.format(), "Shrek (2005)")
        && t.checkExpect(this.tv2.format(), "Law and Order 2.11 - Cold Case")
        && t.checkExpect(this.yt1.format(), "Cookie Recipe by CookingWithJoe")
        && t.checkExpect(this.yt2.format(), "Music Video by Vevo");
  }
}
