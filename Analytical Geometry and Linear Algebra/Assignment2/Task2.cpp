// Erokhin Evgenii DSAI-03
#include <iostream>
#include <fstream>
#include <vector>
#include <iomanip>
#include <cmath>

using namespace std;
//ifstream in("input.txt");
//ofstream out("output.txt");

class Exception {
public:

};
class Matrix{
public:


    vector<vector<double>> matrixNew;
    int row;
    int column;
    Matrix(){
        row = 0;
        column = 0;
    }
    Matrix(int r, int c) {
        row = r;
        column = c;
        matrixNew.resize(row,vector<double>(column));

    }




    void add(int i, int j, double value)  { //add element
        matrixNew[i][j] = value;
    }

    double get(int i, int j)  { //get element
        return matrixNew[i][j];
    }

    Matrix operator=( Matrix& M) {
        matrixNew=M.matrixNew;
        row = M.row;
        column = M.column;
        return *this;
    }

    Matrix operator+(Matrix& M) {
        Matrix newM;
        try {
            if (row != M.row || column != M.column) {
                exception e;
                throw exception(e);
            } else{
                newM = *this;
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < column; j++)
                        newM.add(i, j, matrixNew[i][j] + M.get(i, j));  //overload +

            }
        } catch (exception e){cout << "Error: the dimensional problem occurred\n";}
        return newM;
    }

    Matrix operator-(Matrix& M)  {Matrix newM;
        try {
            if (row != M.row || column != M.column) {
                exception e;
                throw exception(e);
            } else{ newM = *this;
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < column; j++)
                        newM.add(i, j, matrixNew[i][j] - M.get(i, j)); //overload -

            }
        } catch (exception e){cout << "Error: the dimensional problem occurred\n";}
        return newM;
    }

    // overload of operator "*"
    Matrix operator*(Matrix& M)  {
        Matrix forSwitch;
        try {
            if (column != M.row) {
                exception e;
                throw exception(e);
            } else{ Matrix temp = Matrix(row, M.column);
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
        } catch (exception e){cout << "Error: the dimensional problem occurred\n";}
        return forSwitch;
    }

    Matrix transpose()  {
        Matrix forSwitch(column, row);
        for(int i = 0; i < row; i++)
            for(int j = 0; j < column; j++)
                forSwitch.add(j, i, matrixNew[i][j]); //transpose matrix
        return forSwitch;
    }
};
class SqareMatrix: public Matrix {
public:


    SqareMatrix() {
        row = 0;
        column = 0;
    }

    explicit SqareMatrix(int r) {
        row = column = r;
        matrixNew.resize(row, vector<double>(column));

    }

    SqareMatrix operator=(SqareMatrix &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator=(*m1);
        SqareMatrix *res1 = (SqareMatrix *) &res;
        return *res1;

    }

    SqareMatrix operator*(SqareMatrix &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator*(*m1);
        SqareMatrix *res1 = (SqareMatrix *) &res;
        return *res1;
    }

    SqareMatrix operator-(SqareMatrix &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator-(*m1);
        SqareMatrix *res1 = (SqareMatrix *) &res;
        return *res1;
    }

    SqareMatrix operator+(SqareMatrix &M) {
        Matrix *m1 = &M;
        Matrix *sq = this;
        Matrix res = sq->operator+(*m1);
        SqareMatrix *res1 = (SqareMatrix *) &res;
        return *res1;
    }

    SqareMatrix transpose() {
        Matrix *sq = this;
        Matrix res = sq->transpose();
        SqareMatrix *res1 = (SqareMatrix *) &res;
        return *res1;

    }




};
class IdentityMatrix: public SqareMatrix {

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

    IdentityMatrix operator=(IdentityMatrix &M) {
        SqareMatrix *m1 = &M;
        SqareMatrix *sq = this;
        SqareMatrix res = sq->operator=(*m1);
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;

    }

    IdentityMatrix operator*(IdentityMatrix &M) {
        SqareMatrix *m1 = &M;
        SqareMatrix *sq = this;
        SqareMatrix res = sq->operator*(*m1);
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;
    }

    IdentityMatrix operator-(IdentityMatrix &M) {
        SqareMatrix *m1 = &M;
        SqareMatrix *sq = this;
        SqareMatrix res = sq->operator-(*m1);
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;
    }

    IdentityMatrix operator+(IdentityMatrix &M) {
        SqareMatrix *m1 = &M;
        SqareMatrix *sq = this;
        SqareMatrix res = sq->operator+(*m1);
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;
    }

    IdentityMatrix transpose() {
        SqareMatrix *sq = this;
        SqareMatrix res = sq->transpose();
        IdentityMatrix *res1 = (IdentityMatrix *) &res;
        return *res1;

    }
};
class EliminationMatrix:public IdentityMatrix {
public:
    EliminationMatrix(){
        row = 0;
        column = 0;
    }


    explicit EliminationMatrix(SqareMatrix& A, int i,int j) {
        IdentityMatrix temp(A.row);
        matrixNew.resize(A.row, vector<double>(A.column));

        SqareMatrix Anew = A;
        int ro = 0;
        double element;
        element = Anew.matrixNew[i][j]/Anew.matrixNew[j][j];
        temp.matrixNew[i][j] = -1*element;
        EliminationMatrix *res1 = (EliminationMatrix *) &temp;
        *this = *res1;

    }


};
class PermutationMatrix:public IdentityMatrix {
public:
    PermutationMatrix(){
        column = 0;
        row = 0;
    }


    explicit PermutationMatrix(SqareMatrix& A, int i,int j) {
        IdentityMatrix temp(A.row);
        matrixNew.resize(A.row, vector<double>(A.column));

        SqareMatrix Anew = A;
        vector<double> temp1 = temp.matrixNew[i];
        temp.matrixNew[i] = temp.matrixNew[j];
        temp.matrixNew[j] = temp1;
        PermutationMatrix *res1 = (PermutationMatrix *) &temp;
        *this = *res1;

    }


};
class ColumnVector: public Matrix {
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
    ColumnVector operator=(SqareMatrix &M) {
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

    ColumnVector transpose() {
        Matrix *sq = this;
        Matrix res = sq->transpose();
        ColumnVector *res1 = (ColumnVector *) &res;
        return *res1;

    }
};


static SqareMatrix Inverse(SqareMatrix&A){
    IdentityMatrix temp(A.row);
    Matrix ansToPrint(A.row,A.column+A.column);
    int kToprint = 0;
    kToprint++;
    for (int i = 0; i < A.row; i++) {
        for (int j = 0; j < A.column; j++) {
            ansToPrint.matrixNew[i][j] = A.matrixNew[i][j];
        }
    }
    for (int i = 0; i < A.row; i++) {
        for (int j = A.column; j < ansToPrint.column; j++) {
            ansToPrint.matrixNew[i][j] = temp.matrixNew[i][j-A.column];
        }
    }

    for (int j = 0; j < A.column; j++) {

        int fabsMax = -1111111;
        int rowMax = -1;
        for (int i = j; i < A.row; i++) {
            if (fabs(A.matrixNew[i][j]) > fabsMax) {
                fabsMax = fabs(A.matrixNew[i][j]);
                rowMax = i;
            }
        }
        if (j != rowMax) {
            PermutationMatrix x(A,j,rowMax);
            IdentityMatrix V = x;
            SqareMatrix Z = x;
            SqareMatrix res1 = Z * A;
            IdentityMatrix res2 = V * temp;
            temp = res2;
            A = res1;
            kToprint++;
            for (int i = 0; i < A.row; i++) {
                for (int j = 0; j < A.column; j++) {
                    ansToPrint.matrixNew[i][j] = A.matrixNew[i][j];
                }
            }
            for (int i = 0; i < A.row; i++) {
                for (int j = A.column; j < ansToPrint.column; j++) {
                    ansToPrint.matrixNew[i][j] = temp.matrixNew[i][j-A.column];
                }
            }

        }
        for (int k = j+1;k<A.row;k++){
            if (A.matrixNew[k][j]!=0) {
                EliminationMatrix H(A, k, j);
                IdentityMatrix V = H;
                SqareMatrix Y = H;
                SqareMatrix res = Y * A;
                IdentityMatrix res1 = V*temp;
                A = res;
                temp = res1;
                kToprint++;
                for (int i = 0; i < A.row; i++) {
                    for (int j = 0; j < A.column; j++) {
                        ansToPrint.matrixNew[i][j] = A.matrixNew[i][j];
                    }
                }
                for (int i = 0; i < A.row; i++) {
                    for (int j = A.column; j < ansToPrint.column; j++) {
                        ansToPrint.matrixNew[i][j] = temp.matrixNew[i][j-A.column];
                    }
                }
            }
        }
    }
    for (int j = A.column-1; j >= 0; j--) {
        for (int k = j - 1; k >=0; k--) {
            if (A.matrixNew[k][j] != 0) {
                EliminationMatrix H(A, k, j);
                IdentityMatrix V = H;
                SqareMatrix Y = H;
                SqareMatrix res = Y * A;
                IdentityMatrix res1 = V*temp;
                A = res;
                temp = res1;
                kToprint++;
                for (int i = 0; i < A.row; i++) {
                    for (int j = 0; j < A.column; j++) {
                        ansToPrint.matrixNew[i][j] = A.matrixNew[i][j];
                    }
                }
                for (int i = 0; i < A.row; i++) {
                    for (int j = A.column; j < ansToPrint.column; j++) {
                        ansToPrint.matrixNew[i][j] = temp.matrixNew[i][j-A.column];
                    }
                }
            }
        }
    }
    int pos1=0,pos2=0;
    for (int i = 0; i < A.row; i++) {
        for (int j = 0; j < A.column; j++) {
            temp.matrixNew[i][j]=temp.matrixNew[i][j]/A.matrixNew[pos1][pos2];
        }
        A.matrixNew[pos1][pos2]=1;
        pos1++;
        pos2++;
    }
    SqareMatrix answer = temp;
    for (int i = 0; i < A.row; i++) {
        for (int j = 0; j < A.column; j++) {
            ansToPrint.matrixNew[i][j] = A.matrixNew[i][j];
        }
    }
    for (int i = 0; i < A.row; i++) {
        for (int j = A.column; j < ansToPrint.column; j++) {
            ansToPrint.matrixNew[i][j] = temp.matrixNew[i][j-A.column];
        }
    }
    return answer;

};
istream& operator>>(istream& in, Matrix& matrix) {
    double val;
    for (int i = 0; i < matrix.row; i++) {
        for (int j = 0; j < matrix.column; j++) {  //overload >>
            in >> val;
            matrix.add(i, j, val);
        }
    }
    return in;
}
ostream& operator<<(ostream& out, Matrix& matrix) {
    for (int i = 0; i < matrix.row; i++) {
        for (int j = 0; j < matrix.column; j++) {  //overload <<
            out <<fixed << setprecision(4)<<matrix.get(i, j) << " ";
        }
        out << endl;
    }
    return out;
}
static Matrix makeDiagonal(Matrix A){
    Matrix diagonal(A.column,A.row);
    for (int i=0;i<A.column;i++){
        for (int j=0;j<A.row;j++){
            if (i==j){
                diagonal.matrixNew[i][j] = A.matrixNew[i][j];
            } else diagonal.matrixNew[i][j] = 0;
        }
    }
    return diagonal;
}
static double takeAccuracy(ColumnVector a, ColumnVector b){
    double sum=0;
    for (int i=0;i<a.row;i++){
        sum = sum + pow((a.matrixNew[i][0] - b.matrixNew[i][0]),2);
    }
    return ::sqrt(sum);
}

int main(int argc,  char * argv[]) {
    int dimension;
    cin>>dimension;

    SqareMatrix A(dimension);
    cin>>A;
    Matrix MatrixA = A;
    for (int y=0;y<MatrixA.row;y++){
        int sum = 0;
        for (int k=0;k<MatrixA.column;k++){
            sum = sum + abs(MatrixA.matrixNew[y][k]);
        }
        sum = sum - abs(MatrixA.matrixNew[y][y]);
        if (abs(MatrixA.matrixNew[y][y])<sum){
            cout<<"The method is not applicable!";
            ::exit(0);
        }
    }

    int dimensionOfVector;
    cin>>dimensionOfVector;
    ColumnVector b(dimensionOfVector);
    cin>>b;
    double e;
    cin>>e;

    Matrix diagonal = makeDiagonal(A);
    Matrix res = diagonal.operator=(diagonal);
    SqareMatrix *res1 = (SqareMatrix *) &res;
    SqareMatrix diagonalInverse = Inverse(*res1);
    Matrix diagonalInverseM = diagonalInverse;
    SqareMatrix diagonalInverseMulA = diagonalInverse*A;
    IdentityMatrix identity(diagonalInverseMulA.row);
    SqareMatrix identityNew = identity;
    SqareMatrix alpha = identityNew - diagonalInverseMulA;

    Matrix bVector = b;
    Matrix diagonalInverseForMul = diagonalInverse;
    Matrix beta = diagonalInverseForMul*bVector;
    ColumnVector beta1;
    ColumnVector* res2 = (ColumnVector*) &beta;
    beta1 = *res2;
    double accuracy = 2000000;

    cout<<"alpha:\n";
    cout<<alpha;
    cout<<"beta:\n";
    cout<<beta1;
    int i=0;
    ColumnVector X(3);
    X = beta1;
    cout<<"x("<<i<<"):\n";
    i++;
    cout<<X;
    ColumnVector X1(3);
    while (abs(accuracy)>e){    
        Matrix Xi = X;
        Matrix resOfmul = MatrixA*Xi;
        Matrix sub = bVector - resOfmul;
        Matrix resOfMul2 = diagonalInverseM*sub;
        Matrix XiAdd = Xi+resOfMul2;
        ColumnVector* res3 = (ColumnVector*) &XiAdd;
        X1 = *res3;
        accuracy = takeAccuracy(X1,X);
        cout<<"e: "<<accuracy<<endl;
        cout<<"x("<<i<<"):\n";
        i++;
        cout<<X1;
        X = X1;

    }
    return 0;
}


