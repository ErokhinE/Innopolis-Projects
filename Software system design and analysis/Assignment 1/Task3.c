#include <iostream>
#include <fstream>
#include <string>
#include <cmath>
using namespace std;
int main() {
    ifstream input ("input.txt");
    ofstream output ("output.txt");
    string inLine;

    getline(input,inLine);
    int max=-1000;
    int* pToMax=&max;
    int maxI=-1000;
    for (int i=0;i<inLine.length();i++){
        if (inLine[i]>='0'&& inLine[i]<='9'){
            if(*pToMax<inLine[i]- '0'){
                *pToMax= inLine[i] - '0';
                maxI=i;
            }
        }else if(inLine[i]=='-'&&(inLine[i+1]>='0'&& inLine[i+1]<='9')){
            string add;
            //cout<<inLine[i];
            char fc;
            char sf = inLine[i+1];
            fc = '-';
            add = fc;
            add += sf;
           // cout<<add;
            if(*pToMax< stoi(add)){
                *pToMax= stoi (add);
                maxI=i+1;
            }
            i++;
        }
    }
if (max==-1000){
    output<<-1;
}else{
    output<<*pToMax<<","<<maxI;
}
    return 0;
}
