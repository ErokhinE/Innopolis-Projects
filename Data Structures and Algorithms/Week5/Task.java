//Erokhin Evgenii
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        Tree newTree = new Tree();
        newTree.size = N;
        newTree.setRoot(in.nextInt());
        for (int i=0;i<N-1;i++){
            newTree.setNode(in.nextInt());
        }
        newTree.print();
//        newTree.goThrough(newTree.root, N);

    }
}
class Node {
    public int value;
    public Node leftChild;
    public Node rightChild;
    public Node parent1;



}
class Tree{
    public int size;
    public Node root= new Node();

    public void setRoot(int value){
        root.value=value;
    }

    public void setNode(int value){
        Node newNode = new Node();
        newNode.value=value;
         Node currentNode=root;
         Node parent;
         while (true){
             parent = currentNode;
             if(currentNode.value>newNode.value){
                 currentNode=currentNode.leftChild;
                 if(currentNode==null){
                     parent.leftChild=newNode;
                     parent.leftChild.parent1=parent;
                     return;
                 }
             }
             else {
                 currentNode = currentNode.rightChild;
                 if(currentNode==null){
                     parent.rightChild=newNode;
                     parent.rightChild.parent1=parent;
                     return;
                 }
             }
         }


    }
    //    public void goThrough(Node root,int N){
//        String[] out = new String[N];
//        System.out.println(N);
//        Node currentNode=root;
////        Node parent=null;
//        int i=0;
//        int x=-1;
//        ArrayList vis = new ArrayList<>();
//        while (vis.size()<N){
//            if(currentNode.leftChild!=null){
//                if((vis.contains(currentNode.leftChild.value))
//                        &&(vis.contains(currentNode.rightChild.value))) {
//                    out[i]=currentNode.value + " " + currentNode.leftChild.value + " " + currentNode.rightChild.value;
//                    i++;
//                    vis.add(currentNode.value);
//                    if (currentNode.parent1!=null)currentNode=currentNode.parent1;
//
//                }
//                else if((vis.contains(currentNode.leftChild.value))
//                        &&(currentNode.rightChild==null)) {
//                    out[i]=currentNode.value + " " + currentNode.leftChild.value + " " + x;
//                    i++;
//                    vis.add(currentNode.value);
//                    currentNode=currentNode.parent1;
//                }else if(!vis.contains(currentNode.leftChild.value)){
//                   // parent = currentNode;
//                    currentNode = currentNode.leftChild;
//                }
//            }
//             if (currentNode.rightChild!=null){
//                if((vis.contains(currentNode.rightChild.value)&&(currentNode.leftChild==null))){
//                    out[i]=currentNode.value+" "+x+" "+currentNode.rightChild.value;
//                    i++;
//                    vis.add(currentNode.value);
//                    currentNode=currentNode.parent1;
//                }else if(!vis.contains(currentNode.rightChild.value)){
//                   // parent = currentNode;
//                    currentNode = currentNode.rightChild;
//                }
//            }
//            else {
//                 out[i]=currentNode.value+" "+x+" "+x;
//                 i++;
//                vis.add(currentNode.value);
//                currentNode=currentNode.parent1;
//            }
//        }
//        for (int j=out.length-1;j>=0;j--){
//            System.out.println(out[j]);
//        }
//        System.out.print("1");
//    }
//}


    String[] out = new String[size];
    int counter=1;
    private int recur(Node cur){int counter1=counter;
        int indexRi=-1;
        int indexL=-1;
        
        counter++;
        if (cur.leftChild!=null){
            indexL=recur(cur.leftChild);
        }
        if (cur.rightChild!=null){
            indexRi=recur(cur.rightChild);
        }
        out[counter1-1] = cur.value+" "+indexL+" "+indexRi;
        return counter1;
    }
    public void print(){
        System.out.println(size);
        out = new String[size];
        recur(root);
        for (int j=0;j<size;j++){
            System.out.println(out[j]);
        }
        System.out.print(1);
    }
}
