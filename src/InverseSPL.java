public class InverseSPL {

    // Metode untuk menghitung determinan
    public static double determinant(double[][] matrix) {
        int n = matrix.length;
        double det = 1;
        double[][] temp = new double[n][n];

        // Salin matrix ke temp
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, temp[i], 0, n);
        }

        for (int i = 0; i < n; i++) {
            // Mencari pivot
            double pivot = temp[i][i];
            if (Math.abs(pivot) < 1e-10) {
                return 0; // Determinan nol jika pivot nol
            }
            for (int j = i + 1; j < n; j++) {
                double factor = temp[j][i] / pivot;
                for (int k = i; k < n; k++) {
                    temp[j][k] -= factor * temp[i][k];
                }
            }
            det *= pivot; // Menghitung determinan
        }
        return det;
    }

    // Metode untuk menghitung solusi SPL
    public static double[] OperasiInverse(double[][] mat, int m, int n) {
        double[][] temp = new double[m][m];  // Matriks m x m untuk menyimpan koefisien
        double[] b = new double[m];          // Vektor solusi b
        double[] res = new double[m];   // Hasil solusi matriks

        // Pisahkan matriks A dan vektor b dari mat
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = mat[i][j];  // Ambil bagian koefisien
            }
            b[i] = mat[i][m];  // Ambil bagian vektor solusi b
        }

        // Hitung determinan dari matriks A
        double det = determinant(temp);

        if (det == 0) {
            // Cek solusi tak hingga atau tidak ada solusi
            if (isConsistent(temp, b, m)) {
                System.out.println("Sistem memiliki solusi tak hingga.");
            } else {
                System.out.println("Sistem tidak memiliki solusi.");
            }
            return null;  // Kembalikan null jika tidak ada solusi
        }

        // Invers matriks A
        double[][] InvA = InverseOBE.inverseGaussJordan(temp, m, m);

        // Hitung hasil Ax = b
        for (int i = 0; i < m; i++) {
            res[i] = 0;
            for (int j = 0; j < m; j++) {
                res[i] += InvA[i][j] * b[j];
            }
        }

        return res;  // Kembalikan hasil sebagai matriks
    }

    // Metode untuk memeriksa konsistensi sistem
    public static boolean isConsistent(double[][] A, double[] b, int m) {
        // Gabungkan A dan b untuk membentuk augmented matrix
        double[][] augmented = new double[m][A[0].length + 1];
        for (int i = 0; i < m; i++) {
            System.arraycopy(A[i], 0, augmented[i], 0, A[0].length);
            augmented[i][A[0].length] = b[i];
        }

        // Lakukan eliminasi Gaussian
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                double factor = augmented[j][i] / augmented[i][i];
                for (int k = i; k < augmented[0].length; k++) {
                    augmented[j][k] -= factor * augmented[i][k];
                }
            }
        }

        // Periksa konsistensi
        for (int i = 0; i < m; i++) {
            if (augmented[i][i] == 0 && augmented[i][augmented[0].length - 1] != 0) {
                return false;  // Tidak ada solusi
            }
        }
        return true;  // Solusi konsisten
    }

    public static void main(String[] args) {
        // Contoh matriks augmented (A|b)
        /*double[][] mat = {
            {1.0, 1.0, 2.0, 4.0},
            {2.0, -1.0, 1.0, 2.0},
            {1.0, 2.0, 3.0, 7.0}
        };*/
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 6.0}};
        double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0},{5.0, -3, 1.0, 14.0}};
        // double[][] mat = {{3.0, 2.0, -4.0, 3.0}, {2.0, 3.0, 3.0, 15.0}, {5.0, -3, 1.0, 14.0}};
        int m = 3;  // Jumlah baris
        int n = 3;  // Jumlah variabel (kolom A)

        // Hitung solusi SPL menggunakan invers matriks
        double[] result = OperasiInverse(mat, m, n);

        // Jika hasil tidak null, cetak hasil solusi
        if (result != null) {
            System.out.println("Solusi dari SPL adalah:");
            for (int i = 0; i < m; i++) {
                System.out.printf("x%d = %.2f\n", i + 1, result[i]);
            }
        }
    }
}
