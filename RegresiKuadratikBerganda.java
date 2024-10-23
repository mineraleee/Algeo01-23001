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
    /*public static void main(String[] args){
        //data dari studi kasus
        double[][] data_X={
            {72.4,76.3,29.18},
            {41.6,70.3,29.35},
            {34.3,77.1,29.24},
            {35.1,68.0,29.27},
            {10.7,79.0,29.78},
            {12.9,67.4,29.39},
            {8.3,66.8,29.69},
            {20.1,76.9,29.48},
            {72.2,77.7,29.09},
            {24.0,67.7,29.60},
            {23.2,76.8,29.38},
            {47.4,86.6,29.35},
            {31.5,76.9,29.63},
            {10.6,86.3,29.56},
            {11.2,86.0,29.48},
            {73.3,76.3,29.40},
            {75.4,77.9,29.28},
            {96.6,78.7,29.29},
            {107.4,86.8,29.03},
            {54.9,70.9,29.37}
        };

        double[] data_Y = {
            0.90,
            0.91,
            0.96,
            0.89,
            1.00,
            1.10,
            1.15,
            1.03,
            0.77,
            1.07,
            1.07,
            0.94,
            1.10,
            1.10,
            1.10,
            0.91,
            0.87,
            0.78,
            0.82,
            0.95
        };
        int n= data_X[0].length;//jumlah variabel linier 
        int dimensi = 1+n+((n*(n+1))/2); //ukuran matriks dengan 1 konstan N, n variabel linier, n variabel kuadrat, dan nC2 variabel interaksi
        double[] koefisien= multipleQuadraticRegression(data_X, data_Y);
        String[] peubah = {"p","q","r","s","t","u", "v", "w",}; // Daftar variabel, bisa ditambah sesuai kebutuhan
        String[] variabel= new String[dimensi-1];

        // Cetak representasi kombinasi variabel
        int i,j,idx;
        for (i=0;i<n;i++){
            variabel[i]=peubah[i]; //n variabel linier
        }
        idx=n;
        for (i=0;i<n;i++){
            for(j=i;j<n;j++){
                if (peubah[i]==peubah[j]){
                    variabel[idx]=peubah[i]+"^2";
                }
                else{
                    variabel[idx]=peubah[i]+peubah[j];
                }
                idx++;
            }
        }

        for (i = 0; i < dimensi-1; i++) {
            System.out.println("x"+(i+1)+ " = " + variabel[i]);
        }

        // Cetak polinomial dengan kombinasi tersebut
        System.out.printf("f(x) = %.4f", koefisien[0]); // Konstanta/intersep

        for (i = 1; i < koefisien.length; i++) {
            if (koefisien[i] >= 0) {
                System.out.printf(" + %.4fx%d", koefisien[i], i);
            } else {
                System.out.printf(" - %.4fx%d", Math.abs(koefisien[i]), i);
            }
        }
        System.out.println();
    }
}*/
