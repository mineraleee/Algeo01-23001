//Multiple Quadratic Regression
public class RegresiKuadratikBerganda {
    //fungsi untuk kombinasi 

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
    //fungsi untuk perhitungan regresi kuadratik berganda
    //fungsi untuk menjadikan matriks seperti rumus X
    //buat matriks sebanyak N untuk tiap sampel data dengan isi elemen adalah perkalian baris paling atas dan kolom paling kiri
    //fungsi membangun satu matriks sampel dari data_X
    public static double[][] buildSampelMatriks(double[] variabel){
        int i,j,dimensi,n,idx;
        n=variabel.length;//jumlah variabel
        dimensi = 1+n+((n*(n+1))/2); //ukuran matriks dengan 1 konstan N, n variabel linier, n variabel kuadrat, dan nC2 variabel interaksi
        double[][] sampelMatriks= new double[dimensi][dimensi]; // 6 kali 6 karena 2 variabel
        //inisialisasi elemen pinggir
        double[] pinggir= new double[dimensi];

        //isi matriks
        //menghitung elemen matriks pinggir (1,x1,x2,...)
        pinggir[0]=1;//1 konstan N
        for (i=0;i<n;i++){
            pinggir[i+1]=variabel[i]; //n variabel linier
        }
        idx=n+1;
        for (i=0;i<n;i++){
            for(j=i;j<n;j++){
                pinggir[idx]=variabel[i]*variabel[j];
                idx++;
            }
        }
        for (i=0;i<dimensi;i++){
            for (j=0;j<dimensi;j++){
                sampelMatriks[i][j]=pinggir[i]*pinggir[j];
            }
        }
        return sampelMatriks;
    }
    //penjumlahan 2 matriks
    public static double[][] tambahMatriks(double[][] x, double[][] y){
        int i,j;
        double[][]hasil= new double[x.length][x[0].length];
        for (i=0;i<x.length;i++){
            for (j=0;j<x[0].length;j++){
                hasil[i][j]=x[i][j]+y[i][j];
            }
        }
        return hasil;
    }
    //hitung matiks hasil_X dengan jumlahkan semua matriks sampel
    public static double[][] calculateHasilX(double[][] data_X){
        int i;
        int N = data_X.length; //untuk mengetahui jumlah sampel
        int n= data_X[0].length;//jumlah variabel linier 
        int dimensi = 1+n+((n*(n+1))/2); //ukuran matriks dengan 1 konstan N, n variabel linier, n variabel kuadrat, dan nC2 variabel interaksi
        double[][] hasil_X= new double[dimensi][dimensi];

        for (i=0;i<N;i++){
            double[][] sampelMatriks=buildSampelMatriks(data_X[i]);
            hasil_X=tambahMatriks(hasil_X, sampelMatriks);
        }
        return hasil_X;
    }
    //hitung matriks hasil_Y
    public static double[] calculateHasilY(double[][] data_X,double[] data_Y){
        int i,idx;
        int N = data_X.length; //untuk mengetahui jumlah sampel
        int n= data_X[0].length;//jumlah variabel linier 
        int dimensi = 1+n+((n*(n+1))/2); //ukuran matriks dengan 1 konstan N, n variabel linier, n variabel kuadrat, dan nC2 variabel interaksi
        double[] hasil_Y= new double[dimensi];

        for (i = 0; i < N; i++) {
            double[] variabel = data_X[i];
            hasil_Y[0] += data_Y[i]; // konstanta
            idx = 1;
            for (int j = 0; j < n; j++) {
                hasil_Y[idx] += data_Y[i] * variabel[j]; // linear terms
                idx++;
            }
            for (int j = 0; j < n; j++) {
                for (int k = j; k < n; k++) {
                    hasil_Y[idx] += data_Y[i] * variabel[j] * variabel[k]; // bilinear and quadratic terms
                    idx++;
                }
            }
        }

        return hasil_Y;
    }
    public static double[] multipleQuadraticRegression(double[][] data_X, double[] data_Y){
        int i,j;
        int N = data_X.length; //untuk mengetahui jumlah sampel
        int n= data_X[0].length;//jumlah variabel linier 
        int dimensi = 1+n+((n*(n+1))/2); //ukuran matriks dengan 1 konstan N, n variabel linier, n variabel kuadrat, dan nC2 variabel interaksi
        double[][] hasil_X = calculateHasilX(data_X);
        double[] hasil_Y = calculateHasilY(data_X, data_Y);
        //menghitung koefisien regresi dengan penyelesaian SPL
        //menjadikan matriks hasil_X dan hasil_Y ke augmented matriks
        double[][] augmented = new double[dimensi][dimensi+1]; 
        //mengisi matriks augmented
        for (i=0;i<dimensi;i++){
            for (j=0;j<dimensi;j++){
                augmented[i][j]= hasil_X[i][j];
            }
        }
        //mengisi matriks augmented dengan menyalin matriks hasi_Y pada sisi kanan
        for (i=0;i<dimensi;i++){
            for (j=0;j<1;j++){
                augmented[i][j+dimensi]= hasil_Y[i];
            }
        }

        //matriks baru untuk simpan solusi
        double[] koefisien = new double[dimensi];
        EliminasiGauss(augmented, dimensi, dimensi, koefisien);

        return koefisien;
    }
}