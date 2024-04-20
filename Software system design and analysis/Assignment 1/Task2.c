#include <iostream>
#include <fstream>
#include <string>
#include <cmath>
using namespace std;
int sign(string a){
    if (a=="+") return 1;
    if (a=="-") return 2;
    if (a=="*") return 3;
}
int main() {
    ifstream input ("input.txt");
    ofstream output ("output.txt");
    string inLineNum1;
    string inLineNum2;
    string inLineSign;
    getline(input,inLineNum1,',');
    getline(input,inLineNum2,',');
    getline(input,inLineSign,',');
  //  cout<<inLineNum1[0]<<inLineSign<<inLineNum2[0];
   /* long long firstN;
    long long secondN;
    char e='e';
    if (inLineNum1.length()==1||inLineNum1.length()==2){
        firstN= stoi(inLineNum1);
    } else if(inLineNum1[1]==e) {
            firstN= (inLineNum1[0] - '0')*pow(10, inLineNum1[2]- '0');
    } else if(inLineNum1[2]==e) {
        firstN= -1*(inLineNum1[1] - '0')*pow(10, inLineNum1[3]- '0');
    }else {
        firstN= stoi(inLineNum1);
    }
    if (inLineNum2.length()==1||inLineNum2.length()==2){
        secondN= stoi(inLineNum2);
    } else if(inLineNum2[1]==e) {
        secondN= (inLineNum2[0] - '0')*pow(10, inLineNum2[2]- '0');
    } else if(inLineNum2[2]==e) {
        secondN= -1*(inLineNum2[1] - '0')*pow(10, inLineNum2[3]- '0');
    }else {
        secondN= stoi(inLineNum2);
    }*/
string result;
    if (sign(inLineSign)==1 ) {

            result=inLineNum1+inLineNum2;
            output<<result;
    }
    else if (sign(inLineSign)==2 ) {
        int counter = 0;
        int j=inLineNum1.length()-1;
        cout<<j;
        for (int i = inLineNum2.length()-1; i >= 0; i--) {
            if (inLineNum1[j]==inLineNum2[i]) counter++;j--;
        }
        cout<<counter;
        if (counter==inLineNum2.length()){
            for (int i=0;i<inLineNum1.length()-inLineNum2.length();i++){
                output<<inLineNum1[i];
            }
        } else {
            output<<"error message";
        }


    } else if (sign(inLineSign)==3 ) {
        int counter= stoi(inLineNum2);
        string mulString=inLineNum1;
        for (int i=0;i<counter-1;i++){
            mulString=mulString+inLineNum1;
        }
        output<<mulString;



    }




    //cout<<firstN<<secondN;
    return 0;
}
