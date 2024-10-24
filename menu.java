import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class menu {
    public static void main(String[] args) {
        System.out.println("MENU: ");
        System.out.println("1. Sistem Persamaan Linier" );
        System.out.println("2. Determinan" );
        System.out.println("3. Matriks Balikan" );
        System.out.println("4. Interpolasi Polinom" );
        System.out.println("5. Interpolasi Bicubic Spline" );
        System.out.println("6. Regresi Linier dan Kuadratik Berganda" );
        System.out.println("7. Keluar" );

        Scanner scanner = new Scanner(System.in);
        System.out.printf("Masukkan Pilihan Anda (1/2/etc):" );
        int opsi = scanner.nextInt();
        scanner.nextLine();

        switch (opsi) {
            case 1:
                System.out.println("1. Metode Eliminasi Gauss" );
                System.out.println("2. Metode Eliminasi Gauss-Jordan" );
                System.out.println("3. Metode Matriks Balikan" );
                System.out.println("4. Kaidah Cramer" );
                int pilihan1 = scanner.nextInt();
                while (pilihan1 != 1 && pilihan1 != 2 && pilihan1 != 3 && pilihan1 != 4){
                    System.out.printf("Masukkan Pilihan Anda (1/2/etc): " );
                    pilihan1 = scanner.nextInt(); 
                }   
                switch (pilihan1) {
                    case 1:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)" );

                        System.out.println("Masukkan Pilihan Anda (1/2):" );
                        scanner.nextLine();
                        int pilihan3 = scanner.nextInt();
                        if (pilihan3 == 1){
                            System.out.print("Masukkan jumlah baris: ");
                            int baris = scanner.nextInt();
                            double[] x = new double[baris];
                            System.out.print("Masukkan jumlah kolom: ");
                            int kolom = scanner.nextInt();
                            matriks Mat = new matriks(baris, kolom);
                            Mat.ReadMat();
                            double[][] mat = Mat.toDoubleArray();
                            int flag = Gauss.OperasiGauss(mat, Mat.baris, Mat.kolom-1, 0);
                            if(flag == 1){
                                flag = Gauss.Cek(mat, Mat.baris, Mat.kolom, flag);
                                if(flag == 2){
                                    Gauss.SubstitusiParametrik(mat, Mat.baris, Mat.kolom-1);
                                }
                                else if(flag == 3) System.out.println("Tidak ada solusi.");
                            }else{
                                for (int i=0; i<Mat.baris; i++) {
                                    x[i] =  mat[i][Mat.kolom-1]/mat[i][i];
                                }
                                for (int i=0; i<Mat.baris; i++) {
                                    System.out.printf("x%d = %.2f\n", i+1, x[i]); //konstanta dibagi dengan pivot
                                }
                            }
                            System.out.println();
                            System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                            scanner.nextLine();
                            String file = scanner.nextLine();
                            if (file.equals("y")) {
                                String str = matriks.matriksToString(x);
                                try{
                                    matriks.simpan(str);
                                } catch (IOException e){
                                    System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                }
                            }
                        } else if (pilihan3 == 2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            scanner.nextLine();
                            String file_nama = scanner.nextLine();
                            
                            try {
                                System.out.printf("Masukkan jumlah baris: ");
                                int m = scanner.nextInt();
                                System.out.printf("Masukkan jumlah kolom: ");
                                int n = scanner.nextInt();
                                matriks Mat = new matriks(file_nama);
                                double[][] mat = Mat.toDoubleArray();
                                double[] x = new double[m];
                                int flag = Gauss.OperasiGauss(mat, m, n-1, 0);
                                if(flag == 1){
                                    flag = Gauss.Cek(mat, m, n-1, flag);
                                    if(flag == 2){
                                        Gauss.SubstitusiParametrik(mat, m, n-1);
                                    }
                                    else if(flag == 3) System.out.println("Tidak ada solusi.");
                                }else{
                                    for (int i=0; i<m; i++) {
                                        x[i] =  mat[i][n-1]/mat[i][i];
                                    }
                                    for (int i=0; i<m; i++) {
                                        System.out.printf("x%d = %.2f\n", i+1, x[i]); //konstanta dibagi dengan pivot
                                    }
                                }
                                System.out.println();
                                System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                                scanner.nextLine();
                                String file = scanner.nextLine();
                                if (file.equals("y")) {
                                    String str = matriks.matriksToString(x);
                                    try{
                                        matriks.simpan(str);
                                    } catch (IOException e){
                                        System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                    }
                                }
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)" );

                        System.out.println("Masukkan Pilihan Anda (1/2): " );
                        scanner.nextLine();
                        pilihan3 = scanner.nextInt();
                        if (pilihan3 == 1){
                            System.out.print("Masukkan jumlah baris: ");
                            int baris = scanner.nextInt();
                            double[] x = new double[baris];
                            System.out.print("Masukkan jumlah kolom: ");
                            int kolom = scanner.nextInt();
                            matriks Mat = new matriks(baris, kolom);
                            Mat.ReadMat();
                            double[][] mat = Mat.toDoubleArray();
                            int flag = GaussJordan.OperasiGaussJordan(mat, Mat.baris, Mat.kolom-1, 0);
                            if(flag == 1){
                                flag = GaussJordan.Cek(mat, Mat.baris, Mat.kolom-1, flag);
                                if(flag == 2){
                                    GaussJordan.SubstitusiParametrik(mat, Mat.baris, Mat.kolom-1);
                                }
                                else if(flag == 3) System.out.println("Tidak ada solusi.");
                            }else{
                                for (int i=0; i<Mat.baris; i++) {
                                    x[i] =  mat[i][Mat.kolom-1]/mat[i][i];
                                }
                                for (int i=0; i<Mat.baris; i++) {
                                    System.out.printf("x%d = %.2f\n", i+1, x[i]); //konstanta dibagi dengan pivot
                                }
                            }
                            System.out.println();
                            System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                            scanner.nextLine();
                            String file = scanner.nextLine();
                            if (file.equals("y")) {
                                String str = matriks.matriksToString(x);
                                try{
                                    matriks.simpan(str);
                                } catch (IOException e){
                                    System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                }
                            }
                        }else if(pilihan3 == 2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            scanner.nextLine();
                            String file_nama = scanner.nextLine();
                            
                            try {
                                System.out.printf("Masukkan jumlah baris: ");
                                int m = scanner.nextInt();
                                System.out.printf("Masukkan jumlah kolom: ");
                                int n = scanner.nextInt();
                                matriks Mat = new matriks(file_nama);
                                double[][] mat = Mat.toDoubleArray();
                                double[] x = new double[Mat.baris];
                                int flag = GaussJordan.OperasiGaussJordan(mat, m, n-1, 0);
                                if(flag == 1){
                                    flag = GaussJordan.Cek(mat, m, n-1, flag);
                                    if(flag == 2){
                                        GaussJordan.SubstitusiParametrik(mat, m, n-1);
                                    }
                                    else if(flag == 3) System.out.println("Tidak ada solusi.");
                                }else{
                                    for (int i=0; i<m; i++) {
                                        x[i] =  mat[i][n-1]/mat[i][i];
                                    }
                                    for (int i=0; i<m; i++) {
                                        System.out.printf("x%d = %.2f\n", i+1, x[i]); //konstanta dibagi dengan pivot
                                    }
                                }
                                System.out.println();
                                System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                                scanner.nextLine();
                                String file = scanner.nextLine();
                                if (file.equals("y")) {
                                    String str = matriks.matriksToString(x);
                                    try{
                                        matriks.simpan(str);
                                    } catch (IOException e){
                                        System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                    }
                                }
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)" );

                        System.out.println("Masukkan Pilihan Anda (1/2): " );
                        scanner.nextLine();
                        pilihan3 = scanner.nextInt();
                        if (pilihan3 == 1){
                            System.out.print("Masukkan jumlah baris: ");
                            int baris = scanner.nextInt();
                            //double[] x = new double[baris];
                            System.out.print("Masukkan jumlah kolom: ");
                            int kolom = scanner.nextInt();
                            matriks Mat = new matriks(baris, kolom);
                            Mat.ReadMat();
                            double[][] mat = Mat.toDoubleArray();
                            double[] x = InverseSPL.OperasiInverse(mat, baris, kolom);
                            if (x != null) {
                                System.out.println("Solusi dari SPL adalah:");
                                for (int i = 0; i < baris; i++) {
                                    System.out.printf("x%d = %.2f\n", i + 1, x[i]);
                                }
                            }
                            System.out.println();
                            System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                            scanner.nextLine();
                            String file = scanner.nextLine();
                            if (file.equals("y")) {
                                String str = matriks.matriksToString(x);
                                try{
                                    matriks.simpan(str);
                                } catch (IOException e){
                                    System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                }
                            }
                        }else if(pilihan3 == 2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            scanner.nextLine();
                            String file_nama = scanner.nextLine();
                            
                            try {
                                System.out.printf("Masukkan jumlah baris: ");
                                int m = scanner.nextInt();
                                System.out.printf("Masukkan jumlah kolom: ");
                                int n = scanner.nextInt();
                                matriks Mat = new matriks(file_nama);
                                double[][] mat = Mat.toDoubleArray();
                                double[] x = InverseSPL.OperasiInverse(mat, m, n);
                                if (x != null) {
                                    System.out.println("Solusi dari SPL adalah:");
                                    for (int i = 0; i < m; i++) {
                                        System.out.printf("x%d = %.2f\n", i + 1, x[i]);
                                    }
                                }
                                System.out.println();
                                System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                                scanner.nextLine();
                                String file = scanner.nextLine();
                                if (file.equals("y")) {
                                    String str = matriks.matriksToString(x);
                                    try{
                                        matriks.simpan(str);
                                    } catch (IOException e){
                                        System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                    }
                                }
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                    case 4:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)" );

                        System.out.println("Masukkan Pilihan Anda (1/2):" );
                        scanner.nextLine();
                        pilihan3 = scanner.nextInt();
                        if (pilihan3 == 1){
                            System.out.print("Masukkan jumlah baris/kolom: ");
                            int baris = scanner.nextInt();
                            matriks Mat = new matriks(baris, baris+1);
                            Mat.ReadMat();
                            double[] x = new double[Mat.baris+5];
                            int flag = Cramer.OperasiCramer(Mat, Mat.baris, x, 0);
                            if(flag == 1){
                                for (int i = 0; i < Mat.baris; i++) {
                                    System.out.printf("x%d = %.2f\n", i+1, x[i]);
                                }
                            }else if(flag == 2) System.out.println("Solusi tak hingga.");
                            else System.out.println("Tidak ada solusi.");
                            System.out.println();
                            System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                            scanner.nextLine();
                            String file = scanner.nextLine();
                            if (file.equals("y")) {
                                String str = matriks.matriksToString(x);
                                try{
                                    matriks.simpan(str);
                                } catch (IOException e){
                                    System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                }
                            }
                        } else if (pilihan3 == 2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            scanner.nextLine();
                            String file_nama = scanner.nextLine();
                            
                            try {
                                System.out.printf("Masukkan jumlah baris: ");
                                int m = scanner.nextInt();
                                System.out.printf("Masukkan jumlah kolom: ");
                                int n = scanner.nextInt();
                                matriks Mat = new matriks(file_nama);
                                double[] x = new double[m+5];
                                int flag = Cramer.OperasiCramer(Mat, m, x, 0);
                                if(flag == 1){
                                    for (int i = 0; i < m; i++) {
                                        System.out.printf("x%d = %.2f\n", i+1, x[i]);
                                    }
                                }else if(flag == 2) System.out.println("Solusi tak hingga.");
                                else System.out.println("Tidak ada solusi.");
                                System.out.println();
                                System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                                scanner.nextLine();
                                String file = scanner.nextLine();
                                if (file.equals("y")) {
                                    String str = matriks.matriksToString(x);
                                    try{
                                        matriks.simpan(str);
                                    } catch (IOException e){
                                        System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                    }
                                }
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                System.out.println("1. Determinan Menggunakan OBE" );
                System.out.println("2. Determinan Kofaktor" );

                System.out.printf("Masukkan Pilihan Anda (1/2/etc): " );
                int pilihan2 = scanner.nextInt();
                scanner.nextLine();
                double det = 0;

                while (pilihan2 != 1 && pilihan2 != 2){
                    System.out.println("Masukkan Pilihan Anda (1/2/etc): " );
                    pilihan2 = scanner.nextInt();
                }
                switch (pilihan2) {
                    case 1:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.println("2. Masukan dari File (.txt)" );

                        System.out.printf("Masukkan Pilihan Anda (1/2): " );
                        int pilihan3 = scanner.nextInt();
                        scanner.nextLine();
                        if (pilihan3 == 1){
                            System.out.print("Masukkan jumlah baris/kolom: ");
                            int baris = scanner.nextInt();
                            matriks Mat = new matriks(baris, baris);
                            Mat.ReadMat();
                            det = Determinan.determinantOBE(Mat,baris);
                            System.out.printf("Determinan dari matriks tersebut: %.2f", det);

                            System.out.println();
                            System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                            scanner.nextLine();
                            String file = scanner.nextLine();
                            if (file.equals("y")) {
                                String str = String.format("%.2f", det);
                                try{
                                    matriks.simpan(str);
                                } catch (IOException e){
                                    System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                }
                            }
                        } else if (pilihan3 == 2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            String file_nama = scanner.nextLine();
                            
                            try {
                                matriks Mat = new matriks(file_nama);
                                int baris = Mat.baris;
                                det = Determinan.determinantOBE(Mat,baris);
                                System.out.printf("Determinan dari matriks tersebut: %.2f", det);

                                System.out.println();
                                System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                                String file = scanner.nextLine();
                                if (file.equals("y")) {
                                    String str = String.format("%.2f", det);
                                    try{
                                        matriks.simpan(str);
                                    } catch (IOException e){
                                        System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                    }
                                }
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.println("2. Masukan dari File (.txt)" );

                        System.out.printf("Masukkan Pilihan Anda (1/2): " );
                        int pilihan4 = scanner.nextInt();
                        scanner.nextLine();

                        if (pilihan4 == 1){
                            System.out.print("Masukkan jumlah baris/kolom: ");
                            int baris = scanner.nextInt();

                            matriks Mat= new matriks(baris, baris);
                            Mat.ReadMat();
                            det = Determinan.determinanKofaktor(Mat,baris);
                            System.out.printf("Determinan dari matriks tersebut: %.2f", det);
                            System.out.println();
                            System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                            scanner.nextLine();
                            String file = scanner.nextLine();
                            if (file.equals("y")) {
                                String str = String.format("%.2f", det);
                                try{
                                    matriks.simpan(str);
                                } catch (IOException e){
                                    System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                }
                            }

                        } else if (pilihan4 == 2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            String file_nama = scanner.nextLine();
                            try {
                                matriks Mat = new matriks(file_nama);
                                int baris = Mat.baris;
                                det = Determinan.determinanKofaktor(Mat,baris);
                                System.out.printf("Determinan dari matriks tersebut: %.2f", det);

                                System.out.println();
                                System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                                String file = scanner.nextLine();
                                if (file.equals("y")) {
                                    String str = String.format("%.2f", det);
                                    try{
                                        matriks.simpan(str);
                                    } catch (IOException e){
                                        System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                                    }
                                }
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                }
            break;
            case 3:
                System.out.println("1. Metode OBE" );
                System.out.println("2. Metode Matriks Adjoin" );
                pilihan1 = scanner.nextInt();
                while (pilihan1 != 1 && pilihan1 != 2){
                    System.out.printf("Masukkan Pilihan Anda (1/2):" );
                    pilihan1 = scanner.nextInt();
                }
                switch (pilihan1) {
                    case 1:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)\n" );

                        System.out.printf("Masukkan Pilihan Anda (1/2): " );
                        scanner.nextLine();
                        pilihan2=scanner.nextInt();
                        scanner.nextLine();
                        while (pilihan2 != 1 && pilihan2 != 2){
                            System.out.printf("Masukkan Pilihan Anda (1/2):" );
                            pilihan2 = scanner.nextInt();
                        }
                        if(pilihan2==1){
                            System.out.print("Masukkan jumlah baris/kolom: ");
                            int baris = scanner.nextInt();
                            while (baris<=0){
                                System.out.printf("Jumlah minimal baris/kolom = 1");
                                System.out.print("Masukkan jumlah baris/kolom: ");
                                baris = scanner.nextInt();
                            }
                        
                            double[][] mat = new double[baris][baris];
                            matriks Mat = new matriks(mat);
                            Mat.ReadMat();
                            mat = Mat.toDoubleArray();
                            try {
                                double[][] inverse = InverseOBE.inverseGaussJordan(mat, baris, baris);
                                for (int i = 0; i < baris; i++) {
                                    for (int j = 0; j < baris; j++) {
                                        System.out.printf("%.2f ", inverse[i][j]);
                                    }
                                    System.out.println();
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }  
                        } else if (pilihan2==2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            String file_nama = scanner.nextLine();

                            try {
                                matriks Mat = new matriks(file_nama);
                                int baris = Mat.baris;
                                int kolom = Mat.kolom;
                                double[][] mat = Mat.toDoubleArray();
                                if (baris!=kolom){
                                    System.out.print("Matriks tidak memiliki invers karena bukan matriks persegi");
                                    break;
                                }

                                try {
                                    double[][] inverse = InverseOBE.inverseGaussJordan(mat, baris, kolom);
                                    for (int i = 0; i < baris; i++) {
                                        for (int j = 0; j < kolom; j++) {
                                            System.out.printf("%.2f ", inverse[i][j]);
                                        }
                                        System.out.println();
                                    }
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)" );

                        System.out.println("Masukkan Pilihan Anda (1/2):" );
                        scanner.nextLine();
                        pilihan2=scanner.nextInt();
                        scanner.nextLine();
                        while (pilihan2 != 1 && pilihan2 != 2){
                            System.out.printf("Masukkan Pilihan Anda (1/2):" );
                            pilihan2 = scanner.nextInt();
                        }
                        if(pilihan2==1){
                            System.out.print("Masukkan jumlah baris/kolom: ");
                            int baris = scanner.nextInt();
                            while (baris<=0){
                                System.out.printf("Jumlah minimal baris/kolom = 1");
                                System.out.print("Masukkan jumlah baris/kolom: ");
                                baris = scanner.nextInt();
                            }

                            double[][] mat = new double[baris][baris];
                            matriks Mat = new matriks(mat);
                            Mat.ReadMat();
                            try {
                                matriks inverse = inverseADJ.inverseAdjoin(Mat);
                                for (int i = 0; i < inverse.baris; i++) {
                                    for (int j = 0; j < inverse.kolom; j++) {
                                        System.out.printf("%.2f ", inverse.mat[i][j]);
                                    }
                                    System.out.println();
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }  
                        } else if (pilihan2==2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            String file_nama = scanner.nextLine();

                            try {
                                matriks Mat = new matriks(file_nama);
                                int baris = Mat.baris;
                                int kolom = Mat.kolom;
                                if (baris!=kolom){
                                    System.out.print("Matriks tidak memiliki invers karena bukan matriks persegi");
                                    break;
                                }

                                try {
                                    matriks inverse = inverseADJ.inverseAdjoin(Mat);
                                    for (int i = 0; i < inverse.baris; i++) {
                                        for (int j = 0; j < inverse.kolom; j++) {
                                            System.out.printf("%.2f ", inverse.mat[i][j]);
                                        }
                                        System.out.println();
                                    }
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                }
            break;
            case 4:
                System.out.println("Silakan pilih input matriks: " );
                System.out.println("1. Masukan dari Keyboard" );
                System.out.printf("2. Masukan dari File (.txt)\n" );

                System.out.printf("Masukkan Pilihan Anda (1/2): " );
                pilihan2 = scanner.nextInt();
                scanner.nextLine();
                while (pilihan2 != 1 && pilihan2 != 2){
                    System.out.printf("Masukkan Pilihan Anda (1/2):" );
                    pilihan2 = scanner.nextInt();
                }if (pilihan2 == 1) {
                    System.out.print("Masukkan jumlah titik: ");
                    
                    // Menangani input untuk jumlah titik
                    while (!scanner.hasNextInt()) {
                        System.out.println("Input tidak valid. Harap masukkan angka bulat.");
                        scanner.next(); // Mengabaikan input yang tidak valid
                    }
                    int n = scanner.nextInt();
                    
                    matriks Mat = new matriks(n, 2);
                    Mat.ReadMat();
                    double[][] mat = Mat.toDoubleArray();
                
                    System.out.print("Masukkan nilai x: ");
                    
                    // Menangani input untuk nilai x
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Input tidak valid. Harap masukkan angka desimal.");
                        scanner.next(); // Mengabaikan input yang tidak valid
                    }
                    double x = scanner.nextDouble();
                
                    double[] a = InterPolinom.OperasiPolinom(n, InterPolinom.UbahKeAug(n, mat));
                    System.out.printf("f(x) = ");
                    
                    for (int i = n - 1; i >= 0; i--) {
                        System.out.printf("%.4f", a[i]);
                        if (i != 0) {
                            System.out.print("x");
                            if (i != 1) {
                                System.out.printf("^%d", i);
                            }
                            System.out.print(" + ");
                        } else {
                            System.out.println();
                        }
                    }
                
                    String result = "f(" + String.format("%.1f", x) + ") = " + String.format("%.4f", InterPolinom.Hitung(n, a, x));
                    System.out.println(result);
                    System.out.println();
                    System.out.print("Apakah ingin menyimpan hasil operasi ke file (y/n)? ");
                    
                    scanner.nextLine(); // Mengkonsumsi newline yang tersisa
                    String file = scanner.nextLine();
                    
                    if (file.equals("y")) {
                        String str = result;
                        try {
                            matriks.simpan(str);
                        } catch (IOException e) {
                            System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                        }
                    }
                }else if (pilihan2==2){
                    System.out.printf("Masukkan nama file (akhiran .txt): ");
                    String file_nama = scanner.nextLine();

                    try {
                        System.out.print("Masukkan jumlah titik: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Input tidak valid. Harap masukkan angka bulat.");
                            scanner.next(); // Mengabaikan input yang tidak valid
                        }
                        int n = scanner.nextInt();
                        matriks Mat = new matriks(file_nama, n);
                        double[][] mat = Mat.toDoubleArray();
                        double x = Mat.getX();
                        double[] a = InterPolinom.OperasiPolinom(n, InterPolinom.UbahKeAug(n, mat));
                        System.out.printf("f(x) = ");
                        for(int i=n-1; i>=0; i--){
                            System.out.printf("%.4f", a[i]);
                            if(i != 0){
                                System.out.printf("x", i);
                                if(i != 1) System.out.printf("^%d", i);
                                System.out.printf(" + ", i);
                            } else System.out.println();
                        }
                        String result = "f(" + String.format("%.1f", x) + ") = " + String.format("%.4f", InterPolinom.Hitung(n, a, x));
                        System.out.println(result);
                        System.out.println();
                        System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                        String file = scanner.nextLine();
                        if (file.equals("y")) {
                            String str = result;
                            try{
                                matriks.simpan(str);
                            } catch (IOException e){
                                System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                            }
                        }
                    } catch (FileNotFoundException e){
                        System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                    }
                }
                break;
            case 5:
                System.out.println("Silakan pilih input matriks: " );
                System.out.println("1. Masukan dari Keyboard" );
                System.out.println("2. Masukan dari File (.txt)" );

                System.out.printf("Masukkan Pilihan Anda (1/2): " );
                int pilihan3 = scanner.nextInt();
                scanner.nextLine();
                if (pilihan3 == 1){
                    matriks Mat= new matriks(4, 4);
                    Mat.ReadMat();
                    System.out.printf("Masukkan x: " );
                    Double x = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.printf("Masukkan y: " );
                    Double y = scanner.nextDouble();
                    scanner.nextLine();

                    Double hasil = Bicubic.hasil_Bicubic_Akhir(Mat, x, y);
                    System.out.printf("Hasil Binterpolasi Bicubic: %.2f\n", hasil );
                    
                    System.out.println();
                    System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );
                        String file = scanner.nextLine();
                        if (file.equals("y")) {
                            String str = String.format("%.2f", hasil);
                            try{
                                matriks.simpan(str);
                            } catch (IOException e){
                                System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                            }
                        }
                    
                } else if (pilihan3 == 2){
                    System.out.printf("Silakan masukan file text (.txt): " );
                    String file_nama = scanner.nextLine();     
                    try {
                        matriks Mat = new matriks(file_nama,4);
                        Double valueX = Mat.getX();
                        Double valueY = Mat.getY();
                        Double hasil = Bicubic.hasil_Bicubic_Akhir(Mat, valueX, valueY);
                        System.out.printf("Hasil Binterpolasi Bicubic: %.2f\n", hasil );

                        System.out.println();
                        System.out.printf("Apakah ingin menyimpan hasil operasi ke file (y/n)? " );

                        String file = scanner.nextLine();
                        if (file.equals("y")) {
                            String str = String.format("%.2f", hasil);
                            try{
                                matriks.simpan(str);
                            } catch (IOException e){
                                System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
                            }
                        }
                    } catch (FileNotFoundException e){
                        System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                    }
                }
            break;
            case 6:
                System.out.println("1. Regresi Linier Berganda" );
                System.out.println("2. Regresi Kuadratik Berganda" );

                System.out.printf("Masukkan Pilihan Anda (1/2):" );
                pilihan1 = scanner.nextInt();
                scanner.nextLine();
                while (pilihan1 != 1 && pilihan1 != 2){
                    System.out.printf("Masukkan Pilihan Anda (1/2):" );
                    pilihan1 = scanner.nextInt();
                }
                switch (pilihan1){
                    case 1:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)" );

                        System.out.println("Masukkan Pilihan Anda (1/2):" );
                        pilihan2=scanner.nextInt();
                        scanner.nextLine();
                        while (pilihan2 != 1 && pilihan2 != 2){
                            System.out.printf("Masukkan Pilihan Anda (1/2):" );
                            pilihan2 = scanner.nextInt();
                        }
                        if (pilihan2==1){
                            System.out.print("Masukkan jumlah peubah x/n: ");
                            int n=scanner.nextInt();
                            while (n<=1){
                                System.out.print("Jumlah minimal peubah x = 2");
                                System.out.print("Masukkan jumlah peubah x/n: ");
                                n=scanner.nextInt();
                            }
                            System.out.print("Masukkan jumlah sampel/m: ");
                            int m=scanner.nextInt();
                            while (m<=1){
                                System.out.print("Jumlah minimal sampel m = 1");
                                System.out.print("Masukkan jumlah sampel/m: ");
                                m=scanner.nextInt();
                            }
                            double[][] data_X = new double[m][n];
                            matriks Mat_X = new matriks(data_X);
                            Mat_X.ReadMat();
                            data_X=Mat_X.toDoubleArray();

                            double[] data_Y = new double[m];
                            System.out.println("Masukkan data y: ");
                            for (int i=0;i<m;i++){
                                data_Y[i]=scanner.nextDouble();
                            }

                            double[] koefisien= RegresiLinierBerganda.multipleLinierRegression(data_X, data_Y);
                            System.out.printf("f(x) = %.4f", koefisien[0]); // konstanta/intersep
                            for (int i = 1; i < koefisien.length; i++) {
                                if (koefisien[i] >= 0) {
                                    System.out.printf(" + %.4fx%d", koefisien[i], i);
                                } else {
                                    System.out.printf(" - %.4fx%d", Math.abs(koefisien[i]), i);
                                }
                            }
                            System.out.println();
                        } else if (pilihan2==2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            String file_nama = scanner.nextLine();

                            try {
                                matriks Mat = new matriks(file_nama);
                                int baris = Mat.baris;
                                int kolom = Mat.kolom;
                                double[][] data_X = new double[baris][kolom-1];
                                for (int i=0;i<baris;i++){
                                    for (int j=0;j<kolom-1;j++){
                                        data_X[i][j]= Mat.mat[i][j];
                                    }
                                }
                                double[] data_Y = new double[baris];
                                for (int i=0;i<baris;i++){
                                    for (int j=0;j<1;j++){
                                        data_Y[i]= Mat.mat[i][j];
                                    }
                                }

                                double[] koefisien= RegresiLinierBerganda.multipleLinierRegression(data_X, data_Y);
                                System.out.printf("f(x) = %.4f", koefisien[0]); // konstanta/intersep
                                for (int i = 1; i < koefisien.length; i++) {
                                    if (koefisien[i] >= 0) {
                                        System.out.printf(" + %.4fx%d", koefisien[i], i);
                                    } else {
                                        System.out.printf(" - %.4fx%d", Math.abs(koefisien[i]), i);
                                    }
                                }
                                System.out.println();
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)" );

                        System.out.println("Masukkan Pilihan Anda (1/2):" );
                        pilihan2=scanner.nextInt();
                        scanner.nextLine();
                        while (pilihan2 != 1 && pilihan2 != 2){
                            System.out.printf("Masukkan Pilihan Anda (1/2):" );
                            pilihan2 = scanner.nextInt();
                        }
                        if (pilihan2==1){
                            System.out.print("Masukkan jumlah peubah x/n: ");
                            int n=scanner.nextInt();
                            while (n<=1){
                                System.out.print("Jumlah minimal peubah x = 2");
                                System.out.print("Masukkan jumlah peubah x/n: ");
                                n=scanner.nextInt();
                            }
                            System.out.print("Masukkan jumlah sampel/m: ");
                            int m=scanner.nextInt();
                            while (m<=1){
                                System.out.print("Jumlah minimal sampel m = 1");
                                System.out.print("Masukkan jumlah sampel/m: ");
                                m=scanner.nextInt();
                            }
                            double[][] data_X = new double[m][n];
                            matriks Mat_X = new matriks(data_X);
                            Mat_X.ReadMat();
                            data_X=Mat_X.toDoubleArray();

                            double[] data_Y = new double[m];
                            System.out.println("Masukkan data y: ");
                            for (int i=0;i<m;i++){
                                data_Y[i]=scanner.nextDouble();
                            }

                            int dimensi = 1+n+((n*(n+1))/2); //ukuran matriks dengan 1 konstan N, n variabel linier, n variabel kuadrat, dan nC2 variabel interaksi
                            double[] koefisien= RegresiKuadratikBerganda.multipleQuadraticRegression(data_X, data_Y);
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
                        } else if (pilihan2==2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            String file_nama = scanner.nextLine();

                            try {
                                matriks Mat = new matriks(file_nama);
                                int baris = Mat.baris;
                                int kolom = Mat.kolom;
                                double[][] data_X = new double[baris][kolom-1];
                                for (int i=0;i<baris;i++){
                                    for (int j=0;j<kolom-1;j++){
                                        data_X[i][j]= Mat.mat[i][j];
                                    }
                                }
                                double[] data_Y = new double[baris];
                                for (int i=0;i<baris;i++){
                                    for (int j=0;j<1;j++){
                                        data_Y[i]= Mat.mat[i][j];
                                    }
                                }

                                int n = data_X[0].length;
                                int dimensi = 1+n+((n*(n+1))/2); //ukuran matriks dengan 1 konstan N, n variabel linier, n variabel kuadrat, dan nC2 variabel interaksi
                                double[] koefisien= RegresiKuadratikBerganda.multipleQuadraticRegression(data_X, data_Y);
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
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                }
            break;
        }
    }
}
