// Erokhin Evgenii DSAI-03 Task 2
#include <iostream>
#include <cmath>
#include <fstream>
using namespace std;
ifstream in("input.txt");
ofstream out("output.txt");

long long numOfPrisoners(unsigned long long& numberOfBottles) {
    return ceil(log2(numberOfBottles));
    /**
     * So the main idea for this task:
     * Our bottle has 2 states poisoned or not. In the worst case for this task last bottle will be poisoned
     * Therefore we can represent last bottle(number of bottles) in binary representation
     * So formula will be following 2^numOfPrisoners=NumberOfBottles
     * To find number of prisoners log2(Number of bottles)
     */
}

int main(int argc, const char * argv[]) {
    unsigned long long numberB; //number of bottles
    in >> numberB; //read from file number
    if (numberB != 0)
        out << numOfPrisoners(numberB);
    else out << 0;//if number of bottles 0 then 0 prisoners
    return 0;
}

