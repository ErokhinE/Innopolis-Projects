// Erokhin Evgenii DSAI-03 Task 1
#include <iostream>
#include <fstream>
using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");
class Matrix{
public:
    int** matrixNew;
    int row;
    int column;
    Matrix(int r, int c) {
        row = r;
        column = c;
        matrixNew = (int**) new int*[row];
        for (int i = 0; i < row; i++)
            matrixNew[i] = (int*)new int[column];

    }

    void add(int i, int j, int value) const { //add element
        matrixNew[i][j] = value;
    }

    int get(int i, int j) const { //get element
        return matrixNew[i][j];
    }

    Matrix operator=(const Matrix& M) {
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                matrixNew[i][j] = M.matrixNew[i][j]; //overload =
        return *this;
    }

    int operator+(Matrix& M) const {
        if (row != M.row || column != M.column) {
            out << "Error: the dimensional problem occurred\n";
            return 0;
        }
        else{
            for (int i = 0; i < row; i++)
                for (int j = 0; j < column; j++)
                    M.add(i, j, matrixNew[i][j] + M.get(i, j));  //overload +
            return 1;
        }
    }
    int operator-(Matrix& M) const {
        if (row != M.row || column != M.column) {
            out << "Error: the dimensional problem occurred\n";
            return 0;
        } else {
            for (int i = 0; i < row; i++)
                for (int j = 0; j < column; j++)
                    M.add(i, j, matrixNew[i][j] - M.get(i, j)); //overload -
            return 1;
        }
    }

    // overload of operator "*"
    void operator*(Matrix& M) const {
        Matrix forSwitch (row, M.column);
        if (column != M.row) {
            out << "Error: the dimensional problem occurred\n";
        } else {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < M.column; j++) {  //overload *
                    forSwitch.add(i, j, 0);
                    for (int k = 0; k < M.row; k++)
                        forSwitch.add(i, j, forSwitch.get(i, j)
                                            + matrixNew[i][k] * M.get(k, j));
                }
            }

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < M.column; j++) {
                    out << forSwitch.get(i, j);
                    out << " ";
                }
                out << endl;
            }
        }
    }

    void transpose()  {
        Matrix forSwitch(column, row);
        for(int i = 0; i < row; i++)
            for(int j = 0; j < column; j++)
                forSwitch.add(j, i, matrixNew[i][j]); //transpose matrix

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                out << forSwitch.get(i, j) << " ";
            }
            out << endl;
        }
    }
};
ifstream& operator>>(ifstream& in, Matrix& matrix) {
    int val;
    for (int i = 0; i < matrix.row; i++) {
        for (int j = 0; j < matrix.column; j++) {  //overload >>
            in >> val;
            matrix.add(i, j, val);
        }
    }
    return in;
}
ofstream& operator<<(ofstream& out, Matrix& matrix) {
    for (int i = 0; i < matrix.row; i++) {
        for (int j = 0; j < matrix.column; j++) {  //overload <<
            out << matrix.get(i, j) << " ";
        }
        out << endl;
    }
    return out;
}
int main(int argc, const char * argv[]) {
    int row,column;
    in >> row >> column;   // read row and column value of A
    Matrix A(row, column);
    in >> A;               //fill A
    int columnA = column;
    int rowA = row;


    in >> row >> column;      // read row and column value of B
    int rowB=row;
    int columnB=column;
    Matrix B(row, column);
    in >> B;                 //fill B
    Matrix D(rowB,columnB);  //Matrix D which will be equal to sum of A and B
    D = B;
    int sum = A + D;
    if (sum)
        out << D;


    int sub;
    Matrix E(rowA,columnA); //Matrix E which will be equal to sub of B and A
    E = A;
    sub = B - E;
    if (sub)
        out << E;

    in >> row >> column;
    Matrix C(row, column);
    in >> C;

    C * A; //Matrix multiplication


    Matrix G(rowA,columnA); //Transpose matrix G
    G = A;
    G.transpose();

    return 0;
}


