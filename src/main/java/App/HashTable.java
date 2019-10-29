package App;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
public class HashTable {
  private class HashNode {
    String key;
    int value;
    HashNode next;

    public HashNode(String k, int v, HashNode n) {
      this.key = k;
      this.value = v;
      this.next = n;
    }

    public String getKey() {
      return this.key;
    }
  }

  public HashNode[] table;
  int pow = 5;
  int count = 0;

  public HashTable() {
    table = new HashNode[32];
  }

  public int get(String k) {
      int h = k.hashCode();
      int i = h & (table.length - 1);
      for(HashNode p = table[i]; p != null; p = p.next) {
        if(k.equals(p.key))
          return p.value;
      }
      return 0;
  }

  public void addOne(String k) {
    int h = k.hashCode();
    int i = h & (table.length - 1);
    //boolean didIt = false;
    //System.out.println("Begin");
    if(table[i] != null) {
      for (HashNode p = table[i]; p != null; p = p.next) {
        //System.out.println(k + " " + p.key + " " + k.equals(p.key));
        if(k.equals(p.key)) {
          //System.out.println("Duplicate");
          ++p.value;
          break;
        }
        else if(p.next == null){
          //System.out.println("Append");
          p.next =  new HashNode(k, 1, null);
          break;
        }
      }
    } else {
      //System.out.println("New");
      table[i] = new HashNode(k, 1, null);
    }
    //System.out.println("End");
    count++;
    if (count > .75 * table.length)
      resize();
  }

  public void resize() {
    //System.out.println("Here");
    HashNode[] newTable = new HashNode[(int)(Math.pow(2.0, (double)pow))];
    pow = pow + 1;
    int i = 0;
    while(i < table.length) {
      if(table[i] != null) {
        for(HashNode p = table[i]; p != null; p = p.next) {
          //System.out.println("oof");
          int h = p.key.hashCode();
          int t = h & (newTable.length - 1);
          newTable[t] = p;
        }
      }
      i++;
    }
    table = newTable;
  }

  public List<String> getAllStrings() {
    List<String> li = new ArrayList<String>();
    for(int i = 0; i < table.length; i++) {
      for(HashNode p = table[i]; p != null; p = p.next) {
        li.add(p.key);
      }
    }
    return li;
  }

  public List<Integer> getAllValues() {
    List<Integer> li = new ArrayList<Integer>();
    for(int i = 0; i < table.length; i++) {
      for(HashNode p = table[i]; p != null; p = p.next) {
        li.add(p.value);
      }
    }
    return li;
  }

  public int getCount() {
    return this.count;
  }

  @Override
  public String toString() {
    int j = 0;
    String myDude = "";
    String next = "";
    while(j < table.length) {
      if(table[j] != null) {
        for(HashNode p = table[j]; p != null; p = p.next) {
          //System.out.println(table[j].getKey());
          if(p.next == null) {next = "null";} else {next = p.next.getKey();}
          myDude = myDude + "Word: " + p.key + " Frequency: " + p.value + " Next: " + next + "\n";
        }
      }
      j++;
    }
    return myDude;
  }
}