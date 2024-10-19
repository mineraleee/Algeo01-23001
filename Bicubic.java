import java.util.Scanner;

public class Bicubic {

    public static void fungsi (matriks M, double x, double y, int baris, int kolom){
        for (int j=0;j<=3;j++){
            for (int i=0;i<=3;i++){
                M.SetElement(baris, kolom, Math.pow(x,i)* Math.pow(y, j));
                kolom++;
            }
        }
    }

    public static void turunanX (matriks M, double x, double y, int baris, int kolom){
        for (int j=0;j<=3;j++){
            for (int i=0;i<=3;i++){
                if (i == 0){
                    M.SetElement(baris, kolom, 0);
                    kolom++;
                } else {
                    M.SetElement(baris, kolom, i* Math.pow(x,i-1)* Math.pow(y, j));
                    kolom++;
                }
            }
        }
    }

    public static void turunanY (matriks M, double x, double y, int baris, int kolom){
        for (int j=0;j<=3;j++){
            for (int i=0;i<=3;i++){
                if (j==0){
                    M.SetElement(baris, kolom, 0);
                    kolom++;
                } else {
                    M.SetElement(baris, kolom, j* Math.pow(x,i)* Math.pow(y, j-1));
                    kolom++;
                }
            }
        }
    }

    public static void turunanXY (matriks M, double x, double y, int baris, int kolom){
        for (int j=0;j<=3;j++){
            for (int i=0;i<=3;i++){
                if (j == 0 || i == 0){
                    M.SetElement(baris, kolom, 0);
                    kolom++;
                } else {
                    M.SetElement(baris, kolom, i * j * Math.pow(x,i-1)* Math.pow(y, j-1));
                    kolom++;
                }
            }
        }
    }
    
    public static void solusiMatrix (matriks M){
            fungsi(M, 0, 0, 0, 0);
            fungsi(M, 1, 0, 1, 0);
            fungsi(M, 0, 1, 2, 0);
            fungsi(M, 1, 1, 3, 0);
            turunanX(M, 0, 0, 4, 0);
            turunanX(M, 1, 0, 5, 0);
            turunanX(M, 0, 1, 6, 0);
            turunanX(M, 1, 1, 7, 0);
            turunanY(M, 0, 0, 8, 0);
            turunanY(M, 1, 0, 9, 0);
            turunanY(M, 0, 1, 10, 0);
            turunanY(M, 1, 1, 11, 0);
            turunanXY(M, 0, 0, 12,0);
            turunanXY(M, 1, 0, 13, 0);
            turunanXY(M, 0, 1, 14, 0);
            turunanXY(M, 1, 1, 15, 0);
     }

     public static double interpolate (matriks M, double x, double y){
        double hasil_interpolasi=0;
        int kolom = 0;
        for (int j=0;j<=3;j++){
            for (int i=0;i<=3;i++){
                hasil_interpolasi += M.GetElement(kolom++, 0)*Math.pow(x,i)* Math.pow(y, j);
            }
        }
        return hasil_interpolasi;
    }
     public static void main(String[] args) {
        matriks M = new matriks(16,16);
        matriks M_Input = new matriks (4,4);
        matriks M_Input2 = new matriks (16,1);
        matriks result = new matriks (16,1);

        solusiMatrix(M);
        //M.PrintMat ();
        double [][] array = M.toDoubleArray();
        double [][] inverse = InverseOBE.inverseGaussJordan(array, 16, 16);
        /* 
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.printf("%.2f ", inverse[i][j]);
            }
            System.out.println();
        }
        */
        M_Input.ReadMat();
        int k = 0;
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                M_Input2.SetElement(k++, 0, M_Input.GetElement(i, j));
            }
        }
        //M_Input2.PrintMat();        
        result = M.Multiply(new matriks(inverse), M_Input2);
        //result.PrintMat(); 

        System.out.println("Nilai yang ingin dicari: ");
        Scanner scan = new Scanner (System.in);
        Double valueX = scan.nextDouble();
        Double valueY = scan.nextDouble();

        double hasil_akhir = interpolate(result,valueX,valueY);
        System.out.println("Hasil Interpolasi: ");
        System.out.println(hasil_akhir);
     }

}