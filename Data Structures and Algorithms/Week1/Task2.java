//Erokhin Evgenii
//e.erokhin@innopolis.university
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int inNumber = in.nextInt();
        int outNumber = in.nextInt();
        String[] array = new String[inNumber];
        int[] arrInt = new int[inNumber];

        for(int i=0;i<inNumber;i++)
        {
            array[i] = in.next();
            arrInt[i]= in.nextInt();
        }
        int[] newArrayInt= new int[arrInt.length];
        String[] newArray = new String[array.length];
        int num;
        if (inNumber>outNumber){
            num=outNumber;
        }else{
            num=inNumber;
        }
        for (int j=0;j<num;j++) {

            int max = -100000001;
            int inMax = -1;
            for (int i = 0; i < arrInt.length; i++) {
                if (arrInt[i] > max) {
                    max = arrInt[i];
                    inMax=i;
                }
            }
            newArrayInt[j]=max;
            newArray[j]=array[inMax];
            arrInt[inMax]= max = -100000001;
        }

            for (int i=0;i<num;i++){
                System.out.print(newArray[i]+" ");
                System.out.println(newArrayInt[i]);
            }

        }

    }
