//Multiple Linier Regression
public class RegresiLinierBerganda {
    //fungsi untuk mengoperasikan eliminasi gauss
    public static void EliminasiGauss(double[][] mat, int m, int n, double[] x){
        for(int j=0; j<n-1; j++){
            int pivot = j;
            for(int i=j+1; i<m; i++){
                if(Math.abs(mat[i][j]) > Math.abs(mat[pivot][j])) pivot = i;
            }
            if(mat[pivot][j] == 0) continue;
            else{
                if(pivot != j){
                    for(int k=j; k<n; k++){
                        double tmp = mat[j][k];
                        mat[j][k] = mat[pivot][k];
                        mat[pivot][k] = tmp;
                    }
                }    
            }

            for(int i=j+1; i<m; i++){
                double factor = mat[i][j]/mat[j][j];
                for(int k=j; k<n; k++){
                    mat[i][k] -= factor*mat[j][k];
                }
            }
        }

        for(int i=n-2; i>=0; i--){
            double jml = 0;
            for(int j=i+1; j<n-1; j++){
                jml += mat[i][j]*x[j];
            }
            x[i] = (mat[i][n-1]-jml)/mat[i][i];
        }
    }
    //fungsi untuk perhitungan regresi linier berganda
    public static double[] multipleLinierRegression(double[][] data_X, double[] data_Y){
        int n = data_X.length; //untuk mengetahui jumlah sampel
        int p = data_X[0].length; //untuk mengetahui jumlah variabel peubahnya
        int i,j,k;
        //tambahkan intersep (bias)
        //inisialisasi array baru dengan ukuran kolom+1
        double[][] intersep = new double[data_X.length][data_X[0].length+1];
        //pengisian angka 1 pada kolom 0
        for (i=0; i<intersep.length;i++){
            intersep[i][0]=1;
            for(j=1;j<intersep[0].length;j++){
                intersep[i][j]=data_X[i][j-1];
            }
        }

        //membentuk matriks X^T*X (perkalian X transpose dengan X)
        double[][] hasil_X=new double[p+1][p+1]; // krena jumlah koefisien adalah jumlah variabel+1
        for (i=0;i<p+1;i++){
            for(j=0;j<p+1;j++){
                hasil_X[i][j]=0;
                for(k=0;k<intersep.length;k++){
                    hasil_X[i][j]+=intersep[k][i]*intersep[k][j];
                }
            }
        }

        //membentuk vektor X^T*Y (perkalian antara X transpose dengan Y)
        double[] hasil_Y=new double[p+1]; // krena krena jumlah koefisien adalah jumlah variabel+1
        for (i=0;i<p+1;i++){
            hasil_Y[i]=0;
            for(k=0;k<intersep.length;k++){
                hasil_Y[i]+=intersep[k][i]*data_Y[k];
            }
        }

        //menampilkan hasil ke layar berupa sistem persamaan linier
        System.out.println("Diperoleh sistem persamaan linear sebagai berikut:");
        for (i=0;i<p+1;i++){
            for (j=0;j<p+1;j++){
                System.out.printf("%.4fb%d\t", hasil_X[i][j],j);
                if (j<3) System.out.print(" + ");
            }
            System.out.printf("=  %.4f\n",hasil_Y[i]);
        }

        //menghitung koefisien regresi dengan penyelesaian SPL
        //menjadikan matriks hasil_X dan hasil_Y ke augmented matriks
        double[][] augmented = new double[p+1][p+2]; 
        //mengisi matriks augmented dengan menyalin matriks hasil_X pada sisi kiri
        for (i=0;i<p+1;i++){
            for (j=0;j<p+1;j++){
                augmented[i][j]= hasil_X[i][j];
            }
        }
        //mengisi matriks augmented dengan menyalin matriks hasi_Y pada sisi kanan
        for (i=0;i<p+1;i++){
            for (j=0;j<1;j++){
                augmented[i][j+(p+1)]= hasil_Y[i];
            }
        }

        //matriks baru untuk simpan solusi
        double[] x = new double[p+1];
        EliminasiGauss(augmented, p+1, p+2, x);

        return x;
    }
}