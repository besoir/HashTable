package App;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.*;

public class Parser {
  String website;
  public Parser(String web) {
    this.website = web;
  }
  public HashTable getStuff() throws IOException {
    HashTable ht = new HashTable();
    Document d = Jsoup.connect(website).get();
    Elements para = d.select("p");
    //System.out.println("This is from the wiki page  " + website);
    for(Element p: para) {
      List<String> strings = Arrays.asList(p.text().split(" "));
      for(String in: strings) {
        in = in.replaceAll("[0-9]+", "");
        in = in.replaceAll("\\s", "");
        in = in.replaceAll("\\W", "");
        in = in.trim();
        if(!in.equals(""))
          ht.addOne(in.toLowerCase());
      }
        //System.out.println(strings);
    }
    return ht;
  }
}
