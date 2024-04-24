//Erokhin Evgenii
//e.erokhin@innopolis.university
//This code was tested by CodeForces and passed all tests

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int amountwords1 = in.nextInt(); //Number of strings
        in.nextLine();

        HashMap<String, Double> hashMap1 = new HashMap<>(50000);
        //Hash map1 with key Date value price

        HashMap<String, ArrayList<String>> hashMap2 = new HashMap<>(50000);
        //Hash map2 with key Date value of array list of different id's

        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < amountwords1; i++) {
            String n = in.nextLine();
            String[] nSplit = n.split(" ");

            if (!data.contains(nSplit[0])) {
                data.add(nSplit[0]); // Add different Data's to list
            }

            String result = nSplit[3].substring(0, 0) + nSplit[3].substring(1);
            double price = Double.parseDouble(result);
            // Parse String with $ to double

            if (hashMap1.containsKey(nSplit[0]) && hashMap2.get(nSplit[0]).contains(nSplit[2])) {
                price(hashMap1, nSplit, price);
                // If we have this data and this id then +price

            } else if (hashMap1.containsKey(nSplit[0]) && !hashMap2.get(nSplit[0]).contains(nSplit[2])) {
                price(hashMap1, nSplit, price);
                hashMap2.get(nSplit[0]).add(nSplit[2]);
                // If we have this data but do not have  this id then +price + add +new id to map

            } else if (!hashMap2.containsKey(nSplit[0])) {// Do not have this data  + id + data
                hashMap1.set(nSplit[0], price);
                ArrayList<String> id = new ArrayList();
                id.add(nSplit[2]);
                hashMap2.set(nSplit[0], id);
            }
        }
        for (int i = 1; i < data.size(); i++) {
            for (int j = 0; j < data.size() - i; j++) {
                if (data.get(j).compareTo(data.get(j + 1)) > 0) {
                    String s;
                    s = data.get(j + 1);
                    data.set(j + 1, data.get(j)); //Sort of data strings
                    data.set(j, s);
                }
            }
        }
        for (String datum : data) {
            System.out.print(datum + " $" + hashMap1.get(datum) + " " + hashMap2.get(datum).size());
            System.out.println(); //Output
        }


    }

    /**
     * Price.
     *
     * @param hashMap1 the hash map 1
     * @param nSplit   the n splited line
     * @param price    the price
     */
    public static void price(HashMap<String, Double> hashMap1, String[] nSplit, double price) {
        //adding price function
        double x = hashMap1.get(nSplit[0]);
        x = x + price;
        hashMap1.set(nSplit[0], x);
    }
}

/**
 * The interface Map.
 *
 * @param <T> the type parameter
 * @param <S> the type parameter
 */
interface Map<T, S> {
    /**
     * Set.
     *
     * @param key the key
     * @param value the value
     */
    void set(T key, S value);

    /**
     * Get s.
     *
     * @param key the key
     * @return the S (value)
     */
    S get(T key);

    /**
     * Size int.
     *
     * @return the int
     */
    int size();

    /**
     * Contains key boolean.
     *
     * @param key the key
     * @return the boolean
     */
    boolean containsKey(T key);


}


/**
 * The type Entry.
 *
 * @param <T> the type parameter
 * @param <S> the type parameter
 */
class Entry<T, S> {
    /**
     * The Key.
     */
    public T key;
    /**
     * The Value.
     */
    public S value;

    /**
     * Instantiates a new Entry.
     *
     * @param s the s
     * @param n the n
     */
    Entry(T s, S n) {
        key = s;
        value = n;
    }
}

/**
 * The type Hash map.
 *
 * @param <T> the type parameter
 * @param <S> the type parameter
 */
class HashMap<T, S> implements Map<T, S> {
    /**
     * The Buckets.
     */
    List<Entry<T, S>>[] buckets;
    /**
     * The Capacity.
     */
    int capacity;
    /**
     * The Size.
     */
    int size;


    /**
     * Instantiates a new Hash map.
     *
     * @param capacity the capacity
     */
    public HashMap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        buckets = new List[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }


    public void set(T s, S a) {
        int bucket_index = mod(s.hashCode(), capacity);
        for (Entry<T, S> e : buckets[bucket_index]) {  //set element to a map
            if (s.equals(e.key)) {
                e.value = a;
                return;
            }
        }
        Entry<T, S> new_entry = new Entry<T, S>(s, a);
        buckets[bucket_index].add(new_entry);
        size++;
    }

    public S get(T s) {
        S a = null;
        int bucket_index = mod(s.hashCode(), capacity);
        for (Entry<T, S> e : buckets[bucket_index]) { //Get value using key
            if (s.equals(e.key)) {
                return e.value;
            }
        }
        return a;
    }

    /**
     * Mod int.
     *
     * @param a the a
     * @param b the b
     * @return the int
     */
    int mod(int a, int b) {
        if (b < 0)
            return -mod(-a, -b);
        int ret = a % b;
        if (ret < 0)
            ret += b;
        return ret;
    }

    @Override
    public int size() {
        return this.size;//Size of map
    }

    public boolean containsKey(T a) {
        S k = null;
        int bucket_index = mod(a.hashCode(), capacity);
        for (Entry<T, S> e : buckets[bucket_index]) { //Contains key true if yes
            if (a.equals(e.key)) {
                return true;
            }

        }
        return false;

    }


}
