package App;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.event.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Modality;
import javafx.geometry.Pos;

public class Main extends Application {
  //we need a button for the methods to return
  //we need a text box for the website input for the comparison
  static List<HashTable> hashTableList;
  static List<String> websites;
  public static void main(String[] args) throws Exception {
      //System.out.println(System.getProperty("user.dir"));
      File savedWebsites = new File("src/main/resources/websites.txt");
      websites = getWebsites(savedWebsites);
      //System.out.println(websites);

      hashTableList = new ArrayList<HashTable>();
      for(String website: websites) {
          Parser p = new Parser(website);
          HashTable ht = p.getStuff();
          hashTableList.add(ht);
      }
      launch(args);
  }

    /*
     * Method: getWebsites(File f)
     */
    public static List<String> getWebsites(File f) throws IOException {
        List<String> sites = new ArrayList<String>();
        BufferedReader grabber = new BufferedReader(new FileReader(f));
        String website;
        while((website = grabber.readLine()) != null) {
            //System.out.println(website);
            sites.add(website);
        }
        return sites;
    }

    @Override
    public void start(Stage s) throws Exception {
        s.setTitle("Wikipedia Comparator");
        GridPane gp = createPane();
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gp.add(nameField, 1,1);

        Button b = new Button("Send It!");
        gp.add(b, 2,2);

        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TDIDF tdidf = new TDIDF();
                Parser newParse = new Parser(nameField.getText());
                List<List<Double>> listOfDoc = new ArrayList<>();
                List<List<String>> allTerms = new ArrayList<>();

                try {
                    HashTable h = newParse.getStuff();
                    if(nameField.getText().equals("print")) {
                        for(HashTable ht: hashTableList)
                            System.out.println(ht);
                    } else {
                        for(HashTable hash: hashTableList) {
                            List<String> thisDoc = hash.getAllStrings();
                            allTerms.add(thisDoc);
                        }
                        allTerms.add(h.getAllStrings());
                        for(HashTable has: hashTableList) {
                            List<Double> tfidfValues = new ArrayList<>();
                            for(List<String> k: allTerms) {
                                for(String s: k) {
                                    double tf = new TDIDF().getTF(has.count, has.get(s));
                                    double idf = new TDIDF().getIDF(allTerms, s);
                                    tfidfValues.add(tf * idf);
                                    //System.out.println(tf * idf);
                                }
                            }
                            listOfDoc.add(tfidfValues);
                        }

                        List<Double> tfidfVals = new ArrayList<>();
                        for(List<String> s: allTerms) {
                            for(String k: s) {
                                double tf = new TDIDF().getTF(h.count, h.get(k));
                                double idf = new TDIDF().getIDF(allTerms, k);
                                tfidfVals.add(tf * idf);
                                //System.out.println(tf * idf);
                            }
                        }
                        //System.out.println();
                        List<Double> cosSim = new ArrayList<>();
                        for(List<Double> li: listOfDoc) {
                            cosSim.add(new TDIDF().cosineSimilarity(li, tfidfVals));
                        }
                        System.out.println(cosSim);
                        //System.out.println(cosSim);
                        //for(List<Double> l: listOfDoc)
                            //System.out.println(l.size());
                        String s = "";
                        for(Integer o: getGreat(cosSim)) {
                            s += websites.get(o);
                        }
                        Stage dialogStage = new Stage();
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        //Text t = new Text();
                        VBox vbox = new VBox(new Text(s), new Button("Ok."));
                        vbox.setAlignment(Pos.CENTER);
                        vbox.setPadding(new Insets(15));

                        dialogStage.setScene(new Scene(vbox));
                        dialogStage.show();

                    }
                } catch (IOException | IllegalArgumentException f) {
                    System.out.println("Problem");
                }
            }
        });
        Scene scene = new Scene(gp, 800, 500);
        s.setScene(scene);
        s.show();
    }

    private GridPane createPane() {
        GridPane g = new GridPane();
        g.setAlignment(Pos.CENTER);
        g.setPadding(new Insets(40,40,40,40));
        g.setHgap(10);
        g.setVgap(10);
        return g;
    }

    private List<Integer> getGreat(List<Double> li) {
        int i = 1;
        Double greatest = li.get(0);
        while(i < li.size()) {
            if(li.get(i) > greatest)
                greatest = li.get(i);
            i++;
        }
        List<Integer> ret = new ArrayList<>();
        int j = 0;
        while(j < li.size()) {
            if(li.get(j) == greatest)
                ret.add(j);
            j++;
        }
        return ret;
    }
}