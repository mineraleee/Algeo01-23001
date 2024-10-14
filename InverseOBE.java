public class InverseOBE {
    public static int EliminasiGaussJordan(double[][] mat, int m, int n, int flag){
        int c;
        for(int i=0; i<m; i++){
            if(mat[i][i] == 0){ //jika nol, maka cari baris lain yang punya bilangan bukan nol untuk ditukar
                c = 1;
                while(i+c < n && mat[i+c][c] == 0) c++; //cari baris dengan nilai bukan nol paling kiri
                if(i+c == n){
                    flag = 1; //pada satu kolom, nilainya nol semua sehingga tidak ada pivot
                    break;
                }
                for(int k=0; k<=n; k++){
                    double tmp = mat[i][k];
                    mat[i][k] = mat[i+c][k];
                    mat[i+c][k] = tmp;
                }
            }
            for(int j=0; j<m; j++){
                if(i != j){
                    double factor = mat[j][i]/mat[i][i];
                    for(int k=0; k<=n; k++){
                        mat[j][k] -= factor*mat[i][k];
                    } 
                }
            }
        }
        return flag;
    }

    public static matriks inverseGaussJordan(matriks M){
        int i,j;
        //membuat matriks baru dengan ukuran kolom 2 kalinya
        matriks augmented= new matriks(M.baris, 2*M.kolom);

        //mengisi matriks augmented dengan menyalin matriks M pada sisi kiri
        for (i=0;i<M.baris;i++){
            for (j=0;j<M.kolom;j++){
                augmented.mat[i][j]=M.mat[i][j];
            }
        }

        //mengisi matriks augmented dengan matriks identitas pada sisi kanan
        matriks identitas = matriks.Identity(M.baris);
        for (i=0;i<M.baris;i++){
            for (j=0;j<M.baris;j++){
                augmented.mat[i][j+M.kolom]=identitas.mat[i][j];
            }
        }
        //Pengoperasian eliminasi Gauss-Jordan sehingga sisi kiri menjadi matriks identitas
        int flag = EliminasiGaussJordan(augmented.mat, M.baris, M.kolom, 0);

        // Check the flag to determine the status of the inversion process
        //if (flag != 0) {
        //    throw new ArithmeticException("Matriks tidak memiliki invers.");
        //}

        //pembuatan matriks balikan dari M
        matriks inverse= new matriks(M.baris, M.kolom);
        for (i=0;i<M.baris;i++){
            for (j=0;j<M.kolom;j++){
                inverse.mat[i][j]=augmented.mat[i][j+M.kolom];//ambil matriks yang awalnya identtas atau yang sisi kanan
            }
        }
        return inverse;
    } 
    /*
    // Metode untuk menghitung adjoin
    public matriks Adjoin() {
        matriks adjoint = new matriks(M.baris, kolom);
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                double cof = determinanKofaktor(Kofaktor(this, baris, i, j), baris - 1);
                adjoint.mat[j][i] = cof * Math.pow(-1, i + j); // Transpose dan kofaktor
            }
        }
        return adjoint;
    }

    public matriks inverseAdjoint() {
        double determinan = determinanKofaktor(this, baris); // Hitung determinan
        if (determinan == 0) {
            throw new ArithmeticException("Matriks tidak dapat dibalik (determinannya nol).");
        }
        matriks adjoint = Adjoin(); // Hitung adjoin
        matriks invers = new matriks(baris, kolom);
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                invers.mat[i][j] = adjoint.mat[i][j] / determinan; // Membagi adjoin dengan determinan
            }
        }
        return invers;
    }*/
}

