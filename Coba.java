import java.util.Scanner;

public class Coba {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Input jumlah data
        System.out.print("Masukkan jumlah data: ");
        int jumlahData = scanner.nextInt();

        // Input data X
        double[][] data_x = new double[jumlahData][3];
        for (int i = 0; i < jumlahData; i++) {
            System.out.print("Masukkan data X untuk data ke-" + (i + 1) + " (3 nilai, dipisahkan spasi): ");
            for (int j = 0; j < 3; j++) {
                data_x[i][j] = scanner.nextDouble();
            }
        }

        // Input data Y
        double[] data_y = new double[jumlahData];
        for (int i = 0; i < jumlahData; i++) {
            System.out.print("Masukkan data Y untuk data ke-" + (i + 1) + ": ");
            data_y[i] = scanner.nextDouble();
        }

        // Input nilai untuk estimasi
        System.out.print("Masukkan nilai untuk estimasi:\nHumidity: ");
        double humidity = scanner.nextDouble();
        System.out.print("Temperature: ");
        double temperature = scanner.nextDouble();
        System.out.print("Tekanan Udara: ");
        double tekanan_udara = scanner.nextDouble();

        // Tambahkan intersep (bias)
        double[][] intersep = new double[jumlahData][4]; // 3 fitur + 1 untuk intersep
        for (int i = 0; i < jumlahData; i++) {
            intersep[i][0] = 1; // Kolom intersep
            for (int j = 1; j < 4; j++) {
                intersep[i][j] = data_x[i][j - 1];
            }
        }

        // Membentuk matriks X^T * X
        double[][] hasil_XTX = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                hasil_XTX[i][j] = 0;
                for (int k = 0; k < jumlahData; k++) {
                    hasil_XTX[i][j] += intersep[k][i] * intersep[k][j];
                }
            }
        }

        // Membentuk vektor X^T * Y
        double[][] hasil_XTY = new double[4][1];
        for (int i = 0; i < 4; i++) {
            hasil_XTY[i][0] = 0;
            for (int k = 0; k < jumlahData; k++) {
                hasil_XTY[i][0] += intersep[k][i] * data_y[k];
            }
        }

        // Menampilkan sistem persamaan linear
        System.out.println("Diperoleh sistem persamaan linear sebagai berikut:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%.4f b%d\t", hasil_XTX[i][j], j);
                if (j < 3) System.out.print(" + ");
            }
            System.out.printf("=  %.4f\n", hasil_XTY[i][0]);
        }

        // Menghitung koefisien regresi menggunakan metode invers matriks (Gauss-Jordan)
        double[][] koefisien = inversMatriks(hasil_XTX);
        double[][] b = new double[4][1];
        for (int i = 0; i < 4; i++) {
            b[i][0] = 0;
            for (int j = 0; j < 4; j++) {
                b[i][0] += koefisien[i][j] * hasil_XTY[j][0];
            }
        }

        // Menampilkan koefisien regresi
        System.out.println("\nKoefisien regresi:");
        for (int i = 0; i < 4; i++) {
            System.out.printf("b%d = %.4f\n", i, b[i][0]);
        }

        // Estimasi nilai Nitrous Oxide
        double estimasi = b[0][0] + b[1][0] * humidity + b[2][0] * temperature + b[3][0] * tekanan_udara;
        System.out.printf("\nEstimasi nilai Nitrous Oxide: %.4f\n", estimasi);

        scanner.close();
    }

    // Metode untuk menghitung invers matriks menggunakan Gauss-Jordan
    public static double[][] inversMatriks(double[][] matriks) {
        int n = matriks.length;
        double[][] augmented = new double[n][2 * n];

        // Membuat matriks augmented [A | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matriks[i][j];
            }
            augmented[i][i + n] = 1; // Identitas
        }

        // Menggunakan metode Gauss-Jordan untuk menghitung invers
        for (int i = 0; i < n; i++) {
            double pivot = augmented[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= pivot;
            }
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double factor = augmented[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmented[j][k] -= factor * augmented[i][k];
                    }
                }
            }
        }

        // Mengambil invers dari matriks
        double[][] invers = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                invers[i][j] = augmented[i][j + n];
            }
        }

        return invers;
    }
}
