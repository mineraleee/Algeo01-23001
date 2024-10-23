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

                System.out.printf("Masukkan Pilihan Anda (1/2/etc):" );
                int pilihan1 = scanner.nextInt();
                switch (pilihan1) {
                    case 1:
                        
                        break;
                
                    default:
                        break;
                }
                break;
            case 2:
                System.out.println("1. Determinan Menggunakan OBE" );
                System.out.println("2. Determinan Kofaktor" );

                System.out.printf("Masukkan Pilihan Anda (1/2/etc):" );
                int pilihan2 = scanner.nextInt();
                scanner.nextLine();
                double det = 0;

                while (pilihan2 != 1 && pilihan2 != 2){
                    System.out.printf("Masukkan Pilihan Anda (1/2/etc):" );
                    pilihan2 = scanner.nextInt();
                }
                switch (pilihan2) {
                    case 1:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)" );

                        System.out.println("Masukkan Pilihan Anda (1/2):" );
                        int pilihan3 = scanner.nextInt();
                        if (pilihan3 == 1){
                            System.out.print("Masukkan jumlah baris/kolom: ");
                            int baris = scanner.nextInt();
                            matriks Mat= new matriks(baris, baris);
                            Mat.ReadMat();
                            det = Determinan.determinantOBE(Mat,baris);
                            System.out.printf("Determinan dari matriks tersebut: %.2f", det);
                        } else if (pilihan3 == 2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            String file_nama = scanner.nextLine();
                            
                            try {
                                matriks Mat = new matriks(file_nama);
                                int baris = Mat.baris;
                                det = Determinan.determinantOBE(Mat,baris);
                                System.out.printf("Determinan dari matriks tersebut: %.2f", det);
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
                        int pilihan4 = scanner.nextInt();
                        if (pilihan4 == 1){
                            System.out.print("Masukkan jumlah baris/kolom: ");
                            int baris = scanner.nextInt();

                            matriks Mat= new matriks(baris, baris);
                            Mat.ReadMat();
                            det = Determinan.determinanKofaktor(Mat,baris);
                            System.out.printf("Determinan dari matriks tersebut: %.2f", det);
                        } else if (pilihan4 == 2){
                            System.out.printf("Masukkan nama file (akhiran .txt): ");
                            String file_nama = scanner.nextLine();
                            
                            try {
                                matriks Mat = new matriks(file_nama);
                                int baris = Mat.baris;
                                det = Determinan.determinanKofaktor(Mat,baris);
                                System.out.printf("Determinan dari matriks tersebut: %.2f", det);
                            } catch (FileNotFoundException e){
                                System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                            }
                        }
                        break;
                }

            case 3:
                System.out.println("1. Metode OBE" );
                System.out.println("2. Metode Matriks Adjoin" );

                System.out.printf("Masukkan Pilihan Anda (1/2):" );
                pilihan1 = scanner.nextInt();
                scanner.nextLine();
                while (pilihan1 != 1 && pilihan1 != 2){
                    System.out.printf("Masukkan Pilihan Anda (1/2):" );
                    pilihan1 = scanner.nextInt();
                }
                switch (pilihan1) {
                    case 1:
                        System.out.println("Silakan pilih input matriks: " );
                        System.out.println("1. Masukan dari Keyboard" );
                        System.out.printf("2. Masukan dari File (.txt)" );

                        System.out.println("Masukkan Pilihan Anda (1/2):" );
                        pilihan2=scanner.nextInt();
                        if(pilihan2==1){
                            System.out.print("Masukkan jumlah baris: ");
                            int baris = scanner.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            int kolom = scanner.nextInt();
                            
                            while (baris!=kolom){
                                System.out.print("Matriks harus merupakan matriks persegi");
                                System.out.print("Masukkan jumlah baris: ");
                                baris = scanner.nextInt();
                                System.out.print("Masukkan jumlah kolom: ");
                                kolom = scanner.nextInt();
                            }
                            double[][] mat = new double[baris][kolom];
                            matriks Mat = new matriks(mat);
                            Mat.ReadMat();
                            mat = Mat.toDoubleArray();
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


                }

            break;
            case 4:
            break;
            case 5:
                System.out.printf("Silakan masukan file text (.txt): " );
                String file_nama = scanner.nextLine();        
                    try {
                        matriks Mat = new matriks(file_nama,4);
                        
                        Double valueX = matriks.getX();
                        Double valueY = matriks.getY();
                        Double hasil = Bicubic.hasil_Bicubic_Akhir(Mat, valueX, valueY);
                        System.out.printf("Hasil Binterpolasi Bicubic: %.2f", hasil );

                        System.out.printf("Apakah ingin menyimpann hasil operasi ke file? (y/n)" );
                        String file = scanner.nextLine();
                        if (file.equals("y")){
                            String fileContent = "";
                            while (scanner.hasNextLine()){
                                fileContent = fileContent.concat(scanner.nextLine()+"\n");
                            }
                            try {
                                FileWriter writer = new FileWriter ("newfile.txt");
                                writer.write(fileContent);
                                writer.close();
                            } catch (IOException e){
                                System.out.println("Terjadi kesalahan. " + e.getMessage());
                            }
                    
                        }
                    } catch (FileNotFoundException e){
                        System.out.println("File tidak ditemukan: "+e.getMessage()); //kembalikan nama file
                    }
            break;

            default:
                break;
        }
    }
}
