//Erokhin Evgenii
//e.erokhin@innopolis.university
import java.util.Scanner;

import static java.lang.Math.abs;


public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int wordAmount= in.nextInt();
        in.nextLine();
        String inLine = in.nextLine();
        String[] inLineSplit = inLine.split(" ");
        //System.out.println(inLine);
      //  for (int i=0;i<wordAmount;i++){
      //      System.out.print(getHash(inLineSplit[i])+" ");
    //    }
        MapWithHash Map1 = new MapWithHash();
        Map1.set0();
        for (int i=0;i<wordAmount;i++){
            Map1.set(getHash(inLineSplit[i]),inLineSplit[i]);
        }
        for (int a=0;a<10000;a++) {
            for (int b = 0; b < 10; b++) {
                if (Map1.hashTable[a][b]!=0){
                    int max = 1;
                    int maxI = -1;
                    int maxJ = -1;
                    for (int i = 0; i < 10000; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (Map1.hashTable[i][j] >= max) {
                                if (max==1 &&maxI==-1&&maxJ==-1){
                                    max=Map1.hashTable[i][j];
                                    maxI=i;
                                    maxJ=j;
                                }else if(Map1.hashTable[i][j] > max) {
                                    max = Map1.hashTable[i][j];
                                    maxI = i;
                                    maxJ = j;
                                    break;
                                } else if (Map1.hashTable[i][j] == max)
                                 {
                                        char[] firstString = Map1.wordsTable[maxI][maxJ].toCharArray();
                                        char[] secondString = Map1.wordsTable[i][j].toCharArray();
                                        int maxIndex;
                                        if (firstString.length > secondString.length) {
                                            maxIndex = secondString.length;
                                        } else maxIndex = firstString.length;
                                        for (int x = 0; x < maxIndex; x++) {
                                            if (secondString[x] < firstString[x]) {
                                                max = Map1.hashTable[i][j];
                                                maxI = i;
                                                maxJ = j;
                                                break;
                                            }else if (secondString[x]!=firstString[x]){
                                                break;
                                            }

                                    }
                                }

                            }
                        }
                    }
                    System.out.println(Map1.wordsTable[maxI][maxJ]+" "+max);
                    Map1.wordsTable[maxI][maxJ]="w";
                    Map1.hashTable[maxI][maxJ]=0;
                    a=0;
                }
            }
        }

    }
    public static int getHash(String a){
        int hash=-1;
        for (int i=0;i<a.length();i++){
            hash=hash+a.charAt(i);
        }
        return hash;
    }
}
/*class Entry<K, V>{
    public K key;
    public V value;

    Entry(K k, V v){
        key = k;
        value = v;
    }
}*/


interface Map<K, V>{
    void set(int k, String v );
    String get(int k);
    void remove(int k);
  //  int size();
   // boolean isEmpty();
    void set0();
}
class MapWithHash<K,V> implements Map<K,V>{
    int[][] hashTable = new int[10000][10];

    String[][] wordsTable = new String[10000][10];

    public void set0(){
        for (int i=0;i<10000;i++){
            for (int j=0;j<10;j++){
                hashTable[i][j]=0;
                wordsTable[i][j]="null";
            }
        }
    }



    @Override
    public void set(int k, String v) {
       // Entry<K,V> entry;
        for (int i=0;i<10;i++) {
            if (wordsTable[k][i].equals(v)) {
                hashTable[k][i]++;
                break;
            }
            else if (wordsTable[k][i].equals("null")) {
                wordsTable[k][i] = v;
                hashTable[k][i]++;
                break;
            }
        }
    }



    @Override
    public String get(int k) {
        return null;
    }

    public void remove(int k){

    }

}


