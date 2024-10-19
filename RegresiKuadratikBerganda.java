//Multiple Quadratic Regression
public class RegresiKuadratikBerganda {
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
    //fungsi membangun satu matriks sampel(u,v)
    public static double[][] buildSampelMatriks(double u, double v){
        int i,j;
        double[][] sampelMatriks= new double[6][6]; // 6 kali 6 karena 2 variabel
        //inisialisasi elemen pinggir
        double[] pinggir={1,u,v,u*u,u*v,v*v};

        //isi matriks
        for (i=0;i<6;i++){
            for (j=0;j<6;j++){
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
        double[][] hasil_X= new double[6][6];

        for (i=0;i<N;i++){
            double u= data_X[i][0]; //u yang kiri
            double v= data_X[i][1]; //v yang kanan
            double[][] sampelMatriks=buildSampelMatriks(u, v);
            hasil_X=tambahMatriks(hasil_X, sampelMatriks);
        }
        return hasil_X;
    }
    //hitung matriks hasil_Y
    public static double[] calculateHasilY(double[][] data_X,double[] data_Y){
        int i;
        int N = data_X.length; //untuk mengetahui jumlah sampel
        double[] hasil_Y= new double[6];

        for (i=0;i<N;i++){
            double u= data_X[i][0]; //u yang kiri
            double v= data_X[i][1]; //v yang kanan
            hasil_Y[0]+=data_Y[i];
            hasil_Y[1]+=data_Y[i]*u;
            hasil_Y[2]+=data_Y[i]*v;
            hasil_Y[3]+=data_Y[i]*u*u;
            hasil_Y[4]+=data_Y[i]*u*v;
            hasil_Y[5]+=data_Y[i]*v*v;
        }
        return hasil_Y;
    }
    public static double[] multipleQuadraticRegression(double[][] data_X, double[] data_Y){
        int i,j;
        double[][] hasil_X = calculateHasilX(data_X);
        double[] hasil_Y = calculateHasilY(data_X, data_Y);
        //menghitung koefisien regresi dengan penyelesaian SPL
        //menjadikan matriks hasil_X dan hasil_Y ke augmented matriks
        double[][] augmented = new double[6][7]; 
        //mengisi matriks augmented dengan menyalin matriks hasil_X pada sisi kiri
        for (i=0;i<6;i++){
            for (j=0;j<6;j++){
                augmented[i][j]= hasil_X[i][j];
            }
        }
        //mengisi matriks augmented dengan menyalin matriks hasi_Y pada sisi kanan
        for (i=0;i<6;i++){
            for (j=0;j<1;j++){
                augmented[i][j+(6)]= hasil_Y[i];
            }
        }

        //matriks baru untuk simpan solusi
        double[] x = new double[6];
        EliminasiGauss(augmented, 6, 7, x);

        return x;
    }
    public static void main(String[] args){
        //data dari studi kasus
        double[][] data_X={
            {72.4,76.3},
            {41.6,70.3},
            {34.3,77.1},
            {35.1,68.0},
            {10.7,79.0},
            {12.9,67.4},
            {8.3,66.8},
            {20.1,76.9},
            {72.2,77.7},
            {24.0,67.7},
            {23.2,76.8},
            {47.4,86.6},
            {31.5,76.9},
            {10.6,86.3},
            {11.2,86.0},
            {73.3,76.3},
            {75.4,77.9},
            {96.6,78.7},
            {107.4,86.8},
            {54.9,70.9}
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
        double[] koefisien= multipleQuadraticRegression(data_X, data_Y);
        System.out.println("x1=u");
        System.out.println("x2=v");
        System.out.println("x3=u²");
        System.out.println("x1=uv");
        System.out.println("x1=v²");
        System.out.printf("f(x) = %.5f", koefisien[0]); // konstanta/intersep
        for (int i = 1; i < koefisien.length; i++) {
            if (koefisien[i] >= 0) {
                System.out.printf(" + %.5fx%d", koefisien[i], i);
            } else {
                System.out.printf(" - %.5fx%d", Math.abs(koefisien[i]), i);
            }
        }
        System.out.println();
    }
}
