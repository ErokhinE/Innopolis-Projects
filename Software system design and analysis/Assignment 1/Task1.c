#include <iostream>
#include <fstream>
#include <string>
#include <cmath>
using namespace std;
int sign(string a){
    if (a=="+") return 1;
    if (a=="-") return 2;
    if (a=="/") return 3;
    if (a=="*") return 4;
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
    long long firstN;
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
    }
long long result;
    switch (sign(inLineSign) ) {
        case 1:
            result=firstN+secondN;
            output<<result;
            break;
        case 2:
            result=firstN-secondN;
            output<<result;
            break;
        case 3:
            if (secondN==0){
            output<<"NAN";
                break;
            }else { result=firstN/secondN;
                output<<result;
                break;
            }
        case 4:
            result=firstN*secondN;
            output<<result;
            break;
    }



    cout<<firstN<<secondN;
    return 0;
}
