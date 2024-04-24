// Erokhin Evgenii DSAI-03
#include <iostream>
#include <vector>
#include <iomanip>
#include <cmath>

using namespace std;


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
        //if (std::round(temp.matrixNew[i][j])==-0.00) temp.matrixNew[i][j]=0.00;
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




static ColumnVector solving(SqareMatrix&A,ColumnVector&koef){ //function to solve liner equation
    int kToprint = 0;
    cout<<"step #"<<kToprint<<":"<<endl;
    kToprint++;
    for (int i = 0; i < A.row; i++) {
        for (int j = 0; j < A.column; j++) {
            if(fabs(A.matrixNew[i][j])>=0.005) {
                cout << fixed << setprecision(2) << A.get(i, j) << " ";
            }
            else {
                cout<<fixed << setprecision(2)<<0.00<<" ";
            }
        }
        cout<<endl;
    }
    for (int i = 0; i < A.row; i++) {
        if(fabs(koef.matrixNew[i][0])>=0.005) {
            cout << fixed << setprecision(2) << koef.get(i, 0) << endl;
        }
        else {
            cout<<fixed << setprecision(2)<<0.00<<endl;
        }
    }

    for (int j = 0; j < A.column; j++) {  //find maximum and put it in right place by permutation

        double fabsMax = -1111111;
        int rowMax = -1;
        for (int i = j; i < A.row; i++) {
            if (fabs(A.matrixNew[i][j]) > fabsMax) {
                fabsMax = abs(A.matrixNew[i][j]);
                rowMax = i;
            }
        }
        if (j != rowMax) {
            PermutationMatrix x(A,j,rowMax);
            Matrix V = x;
            Matrix C = koef;
            Matrix resOfMul = V*C;
            ColumnVector* res2 = (ColumnVector*) &resOfMul;
            koef = *res2;

            SqareMatrix Z = x;
            SqareMatrix res1 = Z * A;
            A = res1;

            cout<<"step #"<<kToprint<<": permutation"<<endl;
            kToprint++;
            for (int i = 0; i < A.row; i++) {
                for (int j = 0; j < A.column; j++) {
                    if(fabs(A.matrixNew[i][j])>=0.005) {
                        cout << fixed << setprecision(2) << A.get(i, j) <<" ";
                    }
                    else{
                        cout<<fixed << setprecision(2)<<0.00<<" ";
                    }
                }
                cout<<endl;
            }
            for (int i = 0; i < A.row; i++) {
                if(fabs(koef.matrixNew[i][0])>=0.005) {
                    cout << fixed << setprecision(2) << koef.get(i, 0) <<endl;
                }
                else{
                    cout<<fixed << setprecision(2)<<0.00<<endl;
                }
            }


        }
        for (int k = j+1;k<A.row;k++){ //make all 0 under main diagonal
            if (A.matrixNew[k][j]!=0) {
                EliminationMatrix H(A, k, j);
                Matrix V = H;
                Matrix C = koef;
                Matrix resOfMul = V*C;
                ColumnVector* res2 = (ColumnVector*) &resOfMul;
                koef = *res2;


                SqareMatrix Y = H;
                SqareMatrix res = Y * A;
                A = res;

                cout << "step #" << kToprint << ": elimination" << endl;
                kToprint++;
                for (int i = 0; i < A.row; i++) {
                    for (int j = 0; j < A.column; j++) {
                        if(fabs(A.matrixNew[i][j])>=0.005) {
                            cout << fixed << setprecision(2) << A.get(i, j) << " ";
                        }
                        else{
                            cout<<fixed << setprecision(2)<<0.00<<" ";
                        }
                    }
                    cout<<endl;
                }
                for (int i = 0; i < A.row; i++) {
                    if(fabs(koef.matrixNew[i][0])>=0.005) {
                        cout << fixed << setprecision(2) << koef.get(i, 0) <<endl;
                    }
                    else{
                        cout<<fixed << setprecision(2)<<0.00<<endl;
                    }

                }
            }
        }
    }

    for (int j = A.column-1; j >= 0; j--) { //make 0 up to main diagonal
        for (int k = j - 1; k >=0; k--) {
            if (A.matrixNew[k][j] != 0) {
                EliminationMatrix H(A, k, j);
                Matrix V = H;
                Matrix C = koef;
                Matrix resOfMul = V*C;
                ColumnVector* res2 = (ColumnVector*) &resOfMul;
                koef = *res2;


                SqareMatrix Y = H;
                SqareMatrix res = Y * A;
                A = res;
                cout << "step #" << kToprint << ": elimination" << endl;
                kToprint++;
                for (int i = 0; i < A.row; i++) {
                    for (int j = 0; j < A.column; j++) {
                        if(fabs(A.matrixNew[i][j])>=0.005) {
                            cout << fixed << setprecision(2) << A.get(i, j) << " ";
                        }
                        else{
                            cout<<fixed << setprecision(2)<<0.00<<" ";
                        }
                    }
                    cout<<endl;
                }
                for (int i = 0; i < A.row; i++) {
                    if(fabs(koef.matrixNew[i][0])>=0.005) {
                        cout << fixed << setprecision(2) << koef.get(i, 0) <<endl;
                    }
                    else{
                        cout<<fixed << setprecision(2)<<0.00<<endl;
                    }
                }
            }
        }
    }

    //make diagonal normalization
    cout<<"Diagonal normalization:"<<endl;
    int pos1=0,pos2=0;
    for (int i = 0; i < A.row; i++) {
        koef.matrixNew[i][0]=koef.matrixNew[i][0]/A.matrixNew[pos1][pos2];
        A.matrixNew[pos1][pos2]=1;
        pos1++;
        pos2++;
    }
    ColumnVector answer = koef;
    for (int i = 0; i < A.row; i++) {
        for (int j = 0; j < A.column; j++) {
            if(fabs(A.matrixNew[i][j])>=0.005) {
                cout << fixed << setprecision(2) << A.get(i, j) << " ";
            }
            else{
                cout<<fixed << setprecision(2)<<0.00<<" ";
            }
        }
        cout<<endl;
    }
    for (int i = 0; i < A.row; i++) {
        if(fabs(koef.matrixNew[i][0])>=0.005) {
            cout << fixed << setprecision(2) << koef.get(i, 0) <<endl;
        }
        else{
            cout<<fixed << setprecision(2)<<0.00<<endl;
        }
    }
    for (int i = 0; i < A.row; i++) {
        if(fabs(answer.matrixNew[i][0])<0.005) {
            answer.matrixNew[i][0]=0.00;
        }
    }
    return answer;


};

//while we do algorithm for solving we change our matrix to identity by permutation and elimination we change the column
//vector and in final obtain the result. Also, we check -0.00 because we work with doubles


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
            out << matrix.get(i, j) << " ";
        }
        out << endl;
    }
    return out;
}
int main(int argc,  char * argv[]) {
    int param;
    cin >> param;   // read row and column value of A
    SqareMatrix A(param);
    cin >> A;               //fill A
    int colVectorCol;
    cin>>colVectorCol;
    ColumnVector F(colVectorCol);
    cin>>F;
    ColumnVector B = solving(A,F);
    cout<<"result:"<<endl;
    cout<<B;
}


