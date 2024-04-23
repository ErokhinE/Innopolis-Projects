//Erokhin Evgenii
//e.erokhin@innopolis.university
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Cellinfo { //class Cell with info
public:
    char value;
    int copies;

    Cellinfo(char value, int copies) {
        this->copies = copies;
        this->value = value;
    }

    //overload operation to compare cells
    bool operator==(const Cellinfo &cell) const {
        return (this->value == cell.value && this->copies == cell.copies);
    }

};

class Bag { //class bag with cells
public:
    vector<Cellinfo> elements;

    void insert(char val, int n) { //put cell in bag
        bool exist = false;
        for (int i = 0; i < elements.size(); i++) { //if we have such element just add copies
            if (val == elements[i].value) {
                elements[i].copies = elements[i].copies + n;
                exist = true;
            }
        }
        if (!exist) {
            Cellinfo newCell(val, n);//if we do not have such element add cell
            elements.push_back(newCell);
        }
    }

    void remove(char val, int n) {//remove cell from bag
        int numberOfElements;
        for (int i = 0; i < elements.size(); i++) {
            if (elements[i].value == val) {

                //if we want to take more than we have take all and delete element
                if (n >= elements[i].copies) {
                    elements.erase(elements.begin() + i);
                } else {
                    elements[i].copies = elements[i].copies - n;
                }
            }
        }
    }

    char min() {//min in bag
        char min = '}';
        if (elements.size() == 0) return min;
        for (int i = 0; i < elements.size(); i++) {
            if (min > elements[i].value) {
                min = elements[i].value;
            }
        }
        return min;
    }

    char max() {//max in beg
        char max = 'A';
        if (elements.size() == 0) return max;
        for (int i = 0; i < elements.size(); i++) {
            if (max < elements[i].value) {
                max = elements[i].value;
            }
        }
        return max;
    }

    bool isEqual(Bag b) {//check bag1 equals to bag2 if yes return true else false
        int counter = 0;
        if (elements.empty() && b.elements.empty()) return true;//if both empty equals

        if (elements.size() == b.elements.size()) {//Check size

            for (int i = 0; i < elements.size(); i++) {
                for (int j = 0; j < b.elements.size(); j++) {
                    if (elements[i] == b.elements[j]) {//Check elements
                        counter++;
                        continue;
                    }
                }
            }
            if (counter == elements.size()) return true;
            else return false;
        } else return false;
    }

};

int main() {
    int numOfcom;
    cin >> numOfcom;
    Bag bag1;
    Bag bag2;
    for (int i = 0; i < numOfcom; i++) {
        char comand;
        cin >> comand;
        int numOfbag;
        cin >> numOfbag;
        char value;
        cin >> value;
        int copies;
        cin >> copies;
        if (comand == 'r') {//Chose command
            if (numOfbag == 1) bag1.remove(value, copies);
            else bag2.remove(value, copies);
        }
        if (comand == 'i') {
            if (numOfbag == 1) bag1.insert(value, copies);
            else bag2.insert(value, copies);
        }

    }
    if (bag1.max() == 'A') cout << -1 << " ";//if empty print -1 else print max
    else cout << bag1.max() << " ";
    if (bag2.min() == '}') cout << -1 << " ";//if empty print -1 else print min
    else cout << bag2.min() << " ";
    cout << bag1.isEqual(bag2);
    return 0;
}


