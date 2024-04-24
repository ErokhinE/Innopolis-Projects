// Erokhin Evgenii DSAI-03
#include <iostream>
#include <vector>
#include <iomanip>
#include <cmath>
#include "cstdio";
#define GNUPLOT_NAME "C:\\gnuplot\\bin\\gnuplot -persist"



using namespace std;


int main(int argc,  char * argv[]) {

 //   FILE* pipe = _popen(GNUPLOT_NAME, "w");

    int victims;
    cin>>victims;
    int killers;
    cin>>killers;
    double alpha1,beta1,alpha2,beta2;
    cin>>alpha1>>beta1>>alpha2>>beta2;
    int timeLimit;
    cin>>timeLimit;
    int numFor;
    cin>>numFor;
    double v[timeLimit*numFor/timeLimit+1];
    double t[timeLimit*numFor/timeLimit+1];
    double k[timeLimit*numFor/timeLimit+1];
    double timeLimit1 = (double) timeLimit;
    double numFor1 = (double) numFor;
    double v0 = victims - alpha2/beta2;
    double k0 = killers - alpha1/beta1;

    double step = timeLimit1/numFor1;
    int counter = 0;
    double i=0;
    while (i<=timeLimit1){
        t[counter] = i;
        v[counter] = v0*cos(sqrt(alpha1*alpha2)*i)-(k0*sqrt(alpha2)*beta1*sin(sqrt(alpha1*alpha2)*i))/
                (beta2*sqrt(alpha1));
        k[counter] = (v0*sqrt(alpha1)*beta2*sin(sqrt(alpha1*alpha2)*i))/
                (beta1*sqrt(alpha2))+k0*cos(sqrt(alpha1*alpha2)*i);
        //cout<<k[counter];

        counter++;
        i=i+step;
    }
    cout<<"t:\n";
    for (int i=0;i<counter;i++){
        cout <<fixed << setprecision(2)<<t[i] << " ";
    }
    cout<<endl;
    cout<<"v:\n";
    for (int i=0;i<counter;i++){
        cout <<fixed << setprecision(2)<<v[i]+alpha2/beta2<< " ";
    } cout<<endl;
    cout<<"k:\n";
    for (int i=0;i<counter;i++){
        cout <<fixed << setprecision(2)<<k[i]+alpha1/beta1 << " ";
    }




}


