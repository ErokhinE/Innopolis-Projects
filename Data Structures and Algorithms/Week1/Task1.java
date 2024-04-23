//Erokhin Evgenii
//e.erokhin@innopolis.university
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        String[] array = new String[2*N];
        for(int i=0;i<N;i++)
        {
            array[i] = in.next();
        }
        int[] arrInt = new int[N];
        for (int i =0;i<N;i++){
            arrInt[i]=Integer.parseInt(array[i]);
        }
        int begin = 0;
        int end  = N;
        while (begin<end){
            if(begin==0||arrInt[begin-1]<=arrInt[begin]){
                begin++;
            }
            else {
                int xNew=0;
                xNew=arrInt[begin-1];
                arrInt[begin-1]=arrInt[begin];
                arrInt[begin]=xNew;
                begin--;
            }
        }
        for (int i=0;i<N;i++){
            System.out.print(arrInt[i]+" ");
        }

    }
}
