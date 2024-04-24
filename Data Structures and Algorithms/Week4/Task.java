//Erokhin Evgenii
//e.erokhin@innopolis.university
//This code was tested by CodeForces and passed all tests

import java.util.*;

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
        int amountPrices = in.nextInt(); //Number of price
        int[] lowPrice = new int[amountPrices];
        int[] highPrice = new int[amountPrices];
        int[] counterHigh = new int[100010];
        int[] acmHigh = new int[100010];
        int[] counter = new int[200];
        int[] acm = new int[200];
        int[] lowPriceSorted = new int[amountPrices];
        int[] highPriceSortedIndexF = new int[amountPrices];
        int[] highPriceSortedIndex = new int[amountPrices];
        for (int i = 0; i < amountPrices; i++) {
            lowPrice[i] = in.nextInt();
            counter[lowPrice[i]]++;
            highPrice[i] = in.nextInt();
            counterHigh[highPrice[i]]++;
        }
        for (int j=0;j<100010;j++){
            if (j==0)acmHigh[j]=counterHigh[j];
            else acmHigh[j]=acmHigh[j-1]+counterHigh[j];
        }
        for (int i=amountPrices-1;i>=0;i--){
            lowPriceSorted[acmHigh[highPrice[i]]-1]=lowPrice[i];
            highPriceSortedIndex[acmHigh[highPrice[i]]-1]=i+1;
            acmHigh[highPrice[i]]--;
        }


        for (int i=0;i<200;i++){
            if (i==0)acm[i]=counter[i];
            else acm[i]=acm[i-1]+counter[i];
        }
        for (int i=0;i<amountPrices;i++){
            highPriceSortedIndexF[acm[lowPriceSorted[i]]-1]=highPriceSortedIndex[i];
            acm[lowPriceSorted[i]]--;
        }


        for (int i=amountPrices-1;i>=0;i--){
            System.out.print(highPriceSortedIndexF[i]+" ");
        }


    }
}
