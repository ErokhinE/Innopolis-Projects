// Erokhin Evgenii
// e.erokhin@innopolis.university
#include <iostream>
#include <vector>
#include <iomanip>
#include <cmath>

using namespace std;

class Matrix { //class Matrix
public:

    vector<vector<double>> matrixNew;
    int row;
    int column;

    Matrix() {
        row = 0;
        column = 0;
    }

    Matrix(int r, int c) {
        row = r;
        column = c;
        matrixNew.resize(row, vector<double>(column));

    }


    void add(int i, int j, double value) { //add element
        matrixNew[i][j] = value;
    }

    double get(int i, int j) { //get element
        return matrixNew[i][j];
    }

    Matrix operator=(Matrix &M) {//overload =
        matrixNew = M.matrixNew;
        row = M.row;
        column = M.column;
        return *this;
    }

    Matrix operator+(Matrix &M) {
        Matrix newM;
        try {
            if (row != M.row || column != M.column) {
                exception e;
                throw exception(e);
            } else {
                newM = *this;
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < column; j++)
                        newM.add(i, j, matrixNew[i][j] + M.get(i, j));  //overload +

            }
        } catch (exception e) { cout << "Error: the dimensional problem occurred\n"; }
        return newM;
    }

    Matrix operator-(Matrix &M) {
        Matrix newM;
        try {
            if (row != M.row || column != M.column) {
                exception e;
                throw exception(e);
            } else {
                newM = *this;
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < column; j++)
                        newM.add(i, j, matrixNew[i][j] - M.get(i, j)); //overload -

            }
        } catch (exception e) { cout << "Error: the dimensional problem occurred\n"; }
        return newM;
    }

    // overload of operator "*"
    Matrix operator*(Matrix &M) {
        Matrix forSwitch;
        try {
            if (column != M.row) {
                exception e;
                throw exception(e);
            } else {
                Matrix temp = Matrix(row, M.column);
                forSwitch = temp;
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < M.column; j++) {  //overload *
                        forSwitch.add(i, j, 0);
                        for (int k = 0; k < M.row; k++)
                            forSwitch.add(i, j, forSwitch.get(i, j)
                                                + matrixNew[i][k] * M.get(k, j));
                    }
                }
            }
        } catch (exception e) { cout << "Error: the dimensional problem occurred\n"; }
        return forSwitch;
    }

    Matrix transpose() {
        Matrix forSwitch(column, row);
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                forSwitch.add(j, i, matrixNew[i][j]); //transpose matrix
        return forSwitch;
    }
};

class SquareMatrix : public Matrix { //class square based on Matrix row = column
public:


    SquareMatrix() {
        row = 0;
        column = 0;
    }

    explicit SquareMatrix(int r) {
        row = column = r;
        matrixNew.resize(row, vector<double>(column));

    }

    //overload operation to this class;

    SquareMatrix operator=(SquareMatrix &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator=(*m1);
        SquareMatrix *res1 = (SquareMatrix *) &res;
        return *res1;

    }

    SquareMatrix operator*(SquareMatrix &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator*(*m1);
        SquareMatrix *res1 = (SquareMatrix *) &res;
        return *res1;
    }

    SquareMatrix operator-(SquareMatrix &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator-(*m1);
        SquareMatrix *res1 = (SquareMatrix *) &res;
        return *res1;
    }

    SquareMatrix operator+(SquareMatrix &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator+(*m1);
        SquareMatrix *res1 = (SquareMatrix *) &res;
        return *res1;
    }

    SquareMatrix transpose() {
        Matrix *sq = this;
        Matrix res = sq->transpose();
        SquareMatrix *res1 = (SquareMatrix *) &res;
        return *res1;

    }


};

class IdentityMatrix : public SquareMatrix { //class of identity matrix construct identity matrix based on SquareMatrix

public:

    IdentityMatrix() {
        row = 0;
        column = 0;
    }

    explicit IdentityMatrix(int r) {
        row = column = r;
        matrixNew.resize(row, vector<double>(column));
        for (int ro = 0; ro < row; ro++) {
            for (int col = 0; col < column; col++) {
                if (ro == col) {
                    matrixNew[ro][col] = 1;
                } else {
                    matrixNew[ro][col] = 0;
                }
            }
        }

    }

    //overload operations for this class


    IdentityMatrix operator=(IdentityMatrix &M) {
        SquareMatrix *m1 = &M;
        SquareMatrix *sq = this;
        SquareMatrix res = sq->operator=(*m1);
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;

    }

    IdentityMatrix operator*(IdentityMatrix &M) {
        SquareMatrix *m1 = &M;
        SquareMatrix *sq = this;
        SquareMatrix res = sq->operator*(*m1);
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;
    }

    IdentityMatrix operator-(IdentityMatrix &M) {
        SquareMatrix *m1 = &M;
        SquareMatrix *sq = this;
        SquareMatrix res = sq->operator-(*m1);
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;
    }

    IdentityMatrix operator+(IdentityMatrix &M) {
        SquareMatrix *m1 = &M;
        SquareMatrix *sq = this;
        SquareMatrix res = sq->operator+(*m1);
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;
    }

    IdentityMatrix transpose() {
        SquareMatrix *sq = this;
        SquareMatrix res = sq->transpose();
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;

    }
};

class EliminationMatrix
        : public IdentityMatrix { //class of elimination matrix which constructs of Given SquaredMatrix and Identity
public:
    EliminationMatrix() {
        row = 0;
        column = 0;
    }


    explicit EliminationMatrix(SquareMatrix &A, int i, int j) {
        IdentityMatrix temp(A.row);
        matrixNew.resize(A.row, vector<double>(A.column));

        SquareMatrix Anew = A;
        int ro = 0;
        double element;
        element = Anew.matrixNew[i][j] / Anew.matrixNew[j][j];
        temp.matrixNew[i][j] = -1 * element;
        EliminationMatrix *res1 = (EliminationMatrix *) &temp;
        *this = *res1;

    }


};

class PermutationMatrix
        : public IdentityMatrix {//class of permutation matrix which constructs of Given SquaredMatrix and Identity
public:
    PermutationMatrix() {
        column = 0;
        row = 0;
    }


    explicit PermutationMatrix(SquareMatrix &A, int i, int j) {
        IdentityMatrix temp(A.row);
        matrixNew.resize(A.row, vector<double>(A.column));

        SquareMatrix Anew = A;
        vector<double> temp1 = temp.matrixNew[i];
        temp.matrixNew[i] = temp.matrixNew[j];
        temp.matrixNew[j] = temp1;
        PermutationMatrix *res1 = (PermutationMatrix *) &temp;
        *this = *res1;

    }


};

class ColumnVector : public Matrix { //class for column vector:Matrix with 1 column
public:


    ColumnVector() {
        row = 0;
        column = 0;
    }

    explicit ColumnVector(int r) {
        row = r;
        column = 1;
        matrixNew.resize(row, vector<double>(column));

    }

    //overload operations


    ColumnVector operator=(SquareMatrix &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator=(*m1);
        ColumnVector *res1 = (ColumnVector *) &res;
        return *res1;

    }


    ColumnVector operator*(ColumnVector &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator*(*m1);
        ColumnVector *res1 = (ColumnVector *) &res;
        return *res1;
    }

    ColumnVector operator-(ColumnVector &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator-(*m1);
        ColumnVector *res1 = (ColumnVector *) &res;
        return *res1;
    }

    ColumnVector operator+(ColumnVector &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator+(*m1);
        ColumnVector *res1 = (ColumnVector *) &res;
        return *res1;
    }

};


static ColumnVector solving(SquareMatrix &A, ColumnVector &koef) { //Method for solving linear equation
    bool flag = false;//flag to understand that our system has infinite number of solution

    for (int j = 0; j < A.column; j++) {

        double fabsMax = -1111111;
        int rowMax = -1;
        for (int i = j; i < A.row; i++) {
            if (fabs(A.matrixNew[i][j]) > fabsMax) { //Find max of |x| to make pivots
                fabsMax = abs(A.matrixNew[i][j]);
                rowMax = i;
            }
        }
        if (j != rowMax) {
            PermutationMatrix x(A, j, rowMax); //Make permutation if max is not in right place
            Matrix V = x;
            Matrix C = koef;
            Matrix resOfMul = V * C;
            ColumnVector *res2 = (ColumnVector *) &resOfMul;
            koef = *res2;

            SquareMatrix Z = x;
            SquareMatrix res1 = Z * A;
            A = res1;
        }
        for (int k = j + 1; k < A.row; k++) {  // make 0 under pivot
            if (A.matrixNew[k][j] != 0) {
                EliminationMatrix H(A, k, j);
                Matrix V = H;
                Matrix C = koef;
                Matrix resOfMul = V * C;
                ColumnVector *res2 = (ColumnVector *) &resOfMul;
                koef = *res2;


                SquareMatrix Y = H;
                SquareMatrix res = Y * A;
                A = res;

                for (int i = 0; i < A.row; i++) {
                    for (int j = 0; j < A.column; j++) {
                        if (fabs(A.matrixNew[i][j]) < 0.01) {
                            A.matrixNew[i][j] = 0.00;
                        }
                    }
                }
                for (int i = 0; i < A.row; i++) {
                    if (fabs(koef.matrixNew[i][0]) < 0.01) {
                        koef.matrixNew[i][0] = 0.00;
                    }
                }
            }
        }
        for (int i = 0; i < A.row; i++) {
            int counter = 0;
            for (int j = 0; j < A.column; j++) {
                if (A.matrixNew[i][j] == 0) counter++;
            }
            if (counter == A.column && koef.matrixNew[i][0] == 0)
                flag = true; //if all elements in row and coefficient 0
            else if (counter == A.column && koef.matrixNew[i][0] != 0) {
                cout << "NO";        //if all elements in row 0 but coefficient is not 0 it means no solution
                exit(0);
            }
        }
    }

    for (int j = A.column - 1; j >= 0; j--) { //make 0 up to pivots
        for (int k = j - 1; k >= 0; k--) {
            if (A.matrixNew[k][j] != 0) {
                EliminationMatrix H(A, k, j);
                Matrix V = H;
                Matrix C = koef;
                Matrix resOfMul = V * C;
                ColumnVector *res2 = (ColumnVector *) &resOfMul;
                koef = *res2;


                SquareMatrix Y = H;
                SquareMatrix res = Y * A;
                A = res;

                for (int i = 0; i < A.row; i++) {
                    for (int j = 0; j < A.column; j++) {
                        if (fabs(A.matrixNew[i][j]) < 0.01) {
                            A.matrixNew[i][j] = 0.00;
                        }
                    }
                }
                for (int i = 0; i < A.row; i++) {
                    if (fabs(koef.matrixNew[i][0]) < 0.01) {
                        koef.matrixNew[i][0] = 0.00;
                    }
                }
            }
        }
        for (int i = 0; i < A.row; i++) { //check the solution again
            int counter = 0;
            for (int j = 0; j < A.column; j++) {
                if (A.matrixNew[i][j] == 0) counter++;
            }
            if (counter == A.column && koef.matrixNew[i][0] == 0) flag = true;
            else if (counter == A.column && koef.matrixNew[i][0] != 0) {
                cout << "NO";
                exit(0);
            }
        }
    }
    if (flag) {
        cout << "INF"; // if flag is true then our system have infinite solution
        exit(0);
    }

    int pos1 = 0, pos2 = 0;  //diagonal normalization
    for (int i = 0; i < A.row; i++) {
        koef.matrixNew[i][0] = koef.matrixNew[i][0] / A.matrixNew[pos1][pos2];
        if (fabs(koef.matrixNew[i][0]) < 0.01) {
            koef.matrixNew[i][0] = 0.00;
        }
        A.matrixNew[pos1][pos2] = 1;
        pos1++;
        pos2++;


    }

    ColumnVector answer = koef;//answer
    for (int i = 0; i < A.row; i++) {
        if (fabs(answer.matrixNew[i][0]) < 0.01) {
            answer.matrixNew[i][0] = 0.00;
        }
    }
    for (int i = 0; i < A.row; i++) { //print solution
        cout << fixed << setprecision(2) << answer.matrixNew[i][0] << endl;
    }

    return answer;

    //In all places I am checking -0.00 because we use only two sign after dot,
    // so it can lead to -0.00 in double, and it is incorrect to represent


};

istream &operator>>(istream &in, Matrix &matrix) {
    double val;
    for (int i = 0; i < matrix.row; i++) {
        for (int j = 0; j < matrix.column; j++) {  //overload >>
            in >> val;
            matrix.add(i, j, val);
        }
    }
    return in;
}

ostream &operator<<(ostream &out, Matrix &matrix) {
    for (int i = 0; i < matrix.row; i++) {
        for (int j = 0; j < matrix.column; j++) {  //overload <<
            out << matrix.get(i, j) << " ";
        }
        out << endl;
    }
    return out;
}

int main(int argc, char *argv[]) {
    int param;
    cin >> param;   // read row and column value of A
    SquareMatrix A(param);
    cin >> A;               //fill A

    ColumnVector F(A.column); //read column vector
    cin >> F;

    ColumnVector B = solving(A, F);//vector B is the result
}
 
