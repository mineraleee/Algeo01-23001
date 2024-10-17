//Multiple Linier Regression
public class RegresiLinierBerganda {
    public static void main(String[] args){
        //data dari studi kasus
        double[][] data_x={
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

        double[] data_y = {
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

        //untuk estimasi nilai Nutrous Oxide
        double humidity = 50.0;
        double temperatur = 76.0;
        double tekanan_udara = 29.30;
    

        //tambahkan intersep (bias)
        //inisialisasi array baru dengan ukuran kolom+1
        double[][] intersep = new double[data_x.length][data_x[0].length+1];
        //pengisian angka 1 pada kolom 0
        for (int i=0; i<intersep.length;i++){
            intersep[i][0]=1;
            for(int j=1;j<intersep[0].length;j++){
                intersep[i][j]=data_x[i][j-1];
            }
        }

        //membentuk matriks X^T*X (perkalian X transpose dengan X)
        double[][] hasil_XTX=new double[4][4]; // krena ada 4 koefisien(b0,b1,b2,b3)
        for (int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                hasil_XTX[i][j]=0;
                for(int k=0;k<intersep.length;k++){
                    hasil_XTX[i][j]+=intersep[k][i]*intersep[k][j];
                }
            }
        }

        //membentuk vektor X^T*Y (perkalian antara X transpose dengan Y)
        double[][] hasil_XTY=new double[4][1]; // krena ada 4 koefisien(b0,b1,b2,b3)
        for (int i=0;i<4;i++){
            hasil_XTY[i][0]=0;
            for(int k=0;k<intersep.length;k++){
                hasil_XTY[i][0]+=intersep[k][i]*data_y[k];
            }
        }

        //menampilkan hasil ke layar berupa sistem persamaan linier
        System.out.println("Diperoleh sistem persamaan linear sebagai berikut:");
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                System.out.printf("%.4fb%d\t", hasil_XTX[i][j],j);
                if (j<3) System.out.print(" + ");
            }
            System.out.printf("=  %.4f\n",hasil_XTY[i][0]);
        }

        //menghitung koefisien regresi dengan penyelesaian SPL
    
        /*  Estimasi nilai Nitrous Oxide
        double estimasi = b[0] + b[1] * humidity + b[2] * temperature + b[3] * pressure;
        System.out.printf("\nEstimasi nilai Nitrous Oxide: %.4f\n", estimasi);*/
    }
}
