public class cobalagi {
    public static void main(String[] args) {
        // Data input
        double[][] dataX = {
            {72.4, 76.3, 29.18},
            {41.6, 70.3, 29.35},
            {34.3, 77.1, 29.24},
            {35.1, 68.0, 29.27},
            {10.7, 79.0, 29.78},
            {12.9, 67.4, 29.39},
            {8.3, 66.8, 29.69},
            {20.1, 76.9, 29.48},
            {72.2, 77.7, 29.09},
            {24.0, 67.7, 29.60},
            {23.2, 76.8, 29.38},
            {47.4, 86.6, 29.35},
            {31.5, 76.9, 29.63},
            {10.6, 86.3, 29.56},
            {11.2, 86.0, 29.48},
            {73.3, 76.3, 29.40},
            {75.4, 77.9, 29.28},
            {96.6, 78.7, 29.29},
            {107.4, 86.8, 29.03},
            {54.9, 70.9, 29.37}
        };

        double[] dataY = {
            0.90, 0.91, 0.96, 0.89, 1.00,
            1.10, 1.15, 1.03, 0.77, 1.07,
            1.07, 0.94, 1.10, 1.10, 1.10,
            0.91, 0.87, 0.78, 0.82, 0.95
        };

        // Hitung jumlah data
        int n = dataX.length;

        // Membuat matriks desain untuk regresi linier
        double[][] matriksDesainLinier = buatMatriksLinier(dataX, n);

        // Hitung sistem persamaan regresi linier
        double[][] sistemPersamaanLinier = transposeKaliMatriks(matriksDesainLinier, dataY, n);

        // Menampilkan sistem persamaan linier
        tampilkanSistemPersamaan(sistemPersamaanLinier);
    }

    // Membuat matriks desain linier
    public static double[][] buatMatriksLinier(double[][] dataX, int n) {
        int p = 4; // Variabel konstanta, x1, x2, x3

        double[][] matriksDesain = new double[n][p];

        for (int i = 0; i < n; i++) {
            double x1 = dataX[i][0];
            double x2 = dataX[i][1];
            double x3 = dataX[i][2];

            matriksDesain[i][0] = 1;  // Konstanta
            matriksDesain[i][1] = x1; // Variabel linier x1
            matriksDesain[i][2] = x2; // Variabel linier x2
            matriksDesain[i][3] = x3; // Variabel linier x3
        }

        return matriksDesain;
    }

    // Fungsi menghitung transpose matriks kali matriks (A^T * A dan A^T * Y)
    public static double[][] transposeKaliMatriks(double[][] matriksDesain, double[] dataY, int n) {
        int p = matriksDesain[0].length;

        double[][] sistemPersamaan = new double[p][p + 1];

        // Menghitung A^T * A
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    sistemPersamaan[i][j] += matriksDesain[k][i] * matriksDesain[k][j];
                }
            }
        }

        // Menghitung A^T * Y
        for (int i = 0; i < p; i++) {
            for (int k = 0; k < n; k++) {
                sistemPersamaan[i][p] += matriksDesain[k][i] * dataY[k];
            }
        }

        return sistemPersamaan;
    }

    // Menampilkan sistem persamaan linier
    public static void tampilkanSistemPersamaan(double[][] sistemPersamaan) {
        int p = sistemPersamaan.length;

        System.out.println("Sistem Persamaan Linier:");
        for (int i = 0; i < p; i++) {
            for (int j = 0; j <= p; j++) {
                System.out.printf("%.4f ", sistemPersamaan[i][j]);
            }
            System.out.println();
        }
    }
}
