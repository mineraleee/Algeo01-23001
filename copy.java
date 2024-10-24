public class InverseSPL {

    public static double[][] OperasiInverse(double[][] mat, int m, int n) {
        double[][] temp = new double[m][m];  // Matriks m x m untuk menyimpan koefisien
        double[] b = new double[m];          // Vektor solusi b
        double[][] res = new double[m][1];   // Hasil solusi matriks

        try {
            // Pisahkan matriks A dan vektor b dari mat
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    temp[i][j] = mat[i][j];  // Ambil bagian koefisien
                }
                b[i] = mat[i][m];  // Ambil bagian vektor solusi b
            }

            // Invers matriks A (misalkan inverseGaussJordan terimplementasi)
            double[][] InvA = InverseOBE.inverseGaussJordan(temp, m, m);

            // Hitung hasil Ax = b
            for (int i = 0; i < m; i++) {
                res[i][0] = 0;
                for (int j = 0; j < m; j++) {
                    res[i][0] += InvA[i][j] * b[j];
                }
            }

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }

        return res;  // Kembalikan hasil sebagai matriks
    }

    public static void main(String[] args) {
        // Contoh matriks augmented (A|b)
        double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0},{5.0, -3, 1.0, 14.0}}; 
        int m = 3;  // Jumlah baris
        int n = 3;  // Jumlah variabel (kolom A)

        // Hitung solusi SPL menggunakan invers matriks
        double[][] result = OperasiInverse(mat, m, n);

        // Cetak hasil solusi
        System.out.println("Solusi dari SPL adalah:");
        for (int i = 0; i < m; i++) {
            System.out.printf("x%d = %.2f\n", i + 1, result[i][0]);
        }
    }
}
