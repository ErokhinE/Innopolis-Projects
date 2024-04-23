//Evgenii Erokhin
//e.erokhin@innopolis.university

import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int amountwords1 = in.nextInt();
        HashMap<String, Integer> hashMap1 = new HashMap<>(10000);
        String ss;
        for (int i = 0; i < amountwords1; i++){
            ss = in.next();
            if (hashMap1.get(ss) == -1){
                hashMap1.set(ss, 0);
            }
        }
        int amountwords2 = in.nextInt();
        String answer = "";
        HashMap<String, Integer> hashMap2 = new HashMap<>(10000);
        int counter = 0;
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < amountwords2; i++){
            ss = in.next();
            if (hashMap2.get(ss) == -1){
                hashMap2.set(ss, 0);
                if (hashMap1.get(ss) == - 1){
                    counter++;
                    a.add(ss);
                }
            }
        }
        System.out.println(counter);
        for(int i = 0; i < a.size(); i++){
            System.out.println(a.get(i));
        }
    }
}
class Entry<String, Integer>{
    public String key;
    public int value;

    Entry(String s, Integer n){
        key = s;
        value = (int) n;
    }
}

interface Map<String, Integer>{
    void set(String s, Integer n);
    int get(String s);
    int size();
}
class HashMap<String, Integer> implements Map<String , Integer>{
    List<Entry<String,Integer>> [] buckets;
    int capacity;
    int size;
    public HashMap(int capacity){
        this.capacity = capacity;
        this.size = 0;
        buckets = new List[capacity];
        for (int i = 0; i < capacity; i++){
            buckets[i] = new LinkedList<Entry<String, Integer>>();
        }
    }

    public void set(String s, Integer n){
        int bucket_index = mod(s.hashCode(), capacity);
        for (Entry<String, Integer> e: buckets[bucket_index]){
            if (s.equals(e.key)){
                e.value = (int) n;
                return;
            }
        }
        Entry<String, Integer> new_entry = new Entry<String, Integer>(s, n);
        buckets[bucket_index].add(new_entry);
        size++;
    }
    public int get(String s) {
        int bucket_index = mod(s.hashCode(), capacity);
        for (Entry<String, Integer> e : buckets[bucket_index]) {
            if (s.equals(e.key)) {
                return e.value;
            }
        }
        return -1;
    }
    int mod(int a, int b)
    {
        if(b < 0)
            return -mod(-a, -b);
        int ret = a % b;
        if(ret < 0)
            ret+=b;
        return ret;
    }
    @Override
    public int size() {
        return this.size;
    }
}
