public class InverseOBE {
    public static int EliminasiGaussJordan(double[][] mat, int m, int n, int flag){
        int c;
        for(int i=0; i<m; i++){
            if(mat[i][i] == 0){ //jika nol, maka cari baris lain yang punya bilangan bukan nol untuk ditukar
                c = 1;
                while(i+c < m && mat[i+c][i] == 0) c++; //cari baris dengan nilai bukan nol paling kiri
                if(i+c == m){
                    flag = 1; //pada satu kolom, nilainya nol semua sehingga tidak ada pivot
                    break;
                }
                for(int k=0; k<n; k++){
                    double tmp = mat[i][k];
                    mat[i][k] = mat[i+c][k];
                    mat[i+c][k] = tmp;
                }
            }
            
            // Normalize the pivot row
            double pivot = mat[i][i];
            for (int k = 0; k < n; k++) {
                mat[i][k] /= pivot; // Normalize the pivot row
            }

            for(int j=0; j<m; j++){
                if(i != j){
                    double factor = mat[j][i];
                    for(int k=0; k<n; k++){
                        mat[j][k] -= factor*mat[i][k];
                    } 
                }
            }
        }
        return flag;
    }

    public static double[][] inverseGaussJordan(double[][] mat, int m, int n){
        int i,j;
        //membuat matriks baru dengan ukuran kolom 2 kalinya
        double[][] augmented = new double[m][2*n];

        //mengisi matriks augmented dengan menyalin matriks M pada sisi kiri
        for (i=0;i<m;i++){
            for (j=0;j<n;j++){
                augmented[i][j]= mat[i][j];
            }
        }

        //mengisi matriks augmented dengan matriks identitas pada sisi kanan
        double[][] identitas = new double[m][n];
        for (i=0;i<n;i++){
            identitas[i][i] = 1; }
        for (i=0;i<m;i++){
            for (j=0;j<n;j++){
                augmented[i][j+n]=identitas[i][j];
            }
        }
        // Perform Gauss-Jordan elimination
        int flag = EliminasiGaussJordan(augmented, m, 2 * n, 0);

        if (flag == 1) {
            throw new IllegalArgumentException("Matrix is singular and cannot be inverted.");
        }

        //pembuatan matriks balikan dari M
        double[][] inverse = new double[m][n];
        for (i=0;i<m;i++){
            for (j=0;j<n;j++){
                inverse[i][j]=augmented[i][j+n];//ambil matriks yang awalnya identtas atau yang sisi kanan
            }
        }
        return inverse;
    } 
}