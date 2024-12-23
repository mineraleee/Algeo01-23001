import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class matriks { //class
    Scanner scan = new Scanner (System.in); //kelas untuk menerima input
    public int baris; //instance - atribut
    public int kolom;
    public double[][]mat; //deklarasi array: mat[baris][kolom]
    public int barmin = 0;
    public int colmin = 0;
    public int barmax = 100;
    public int colmax = 100;
    public double[] lastRow;
    public double x;
    public double y;


    /*KONSTRUKTOR*/  //method
    /* Membuat matriks kosong */
    public matriks (int baris, int kolom){ 
        this.baris = baris;  //parameter
        this.kolom = kolom;
        this.mat = new double [baris][kolom];
    }


    public double[][] toDoubleArray() {
        double[][] array = new double[this.baris][this.kolom]; // Membuat array baru
        for (int i = 0; i < this.baris; i++) {
            for (int j = 0; j < this.kolom; j++) {
                array[i][j] = this.mat[i][j]; // Menyalin nilai dari matriks ke array
            }
        }
        return array; // Mengembalikan array
    }


    /* Salin matriks */
    public matriks (double [][] mat){
        this.baris = mat.length;
        this.kolom = mat[0].length;
        this.mat = new double [this.baris][this.kolom];

        for (int i =0; i< this.baris; i++){
            for (int j=0; j< this.kolom;j++){
                this.mat[i][j] = mat [i][j];
            }
        }
    }

    /* Matriks dari Pembacaan File */
    public matriks(String file_name) throws FileNotFoundException {// Membaca Matriks dari sebuah file
        ArrayList<ArrayList<Double>> mat = new ArrayList<ArrayList<Double>>();
        File file = new File(file_name); //membuat objek file
        Scanner scan = new Scanner(file); //membaca isi dari file

        int baris = -1; //Inisiasi baris

        while (scan.hasNextLine()){
            baris++;
            mat.add(new ArrayList<Double>()); //menambahkan ArrayList untuk tiap penambahan baris
            String input_baris = scan.nextLine(); //baca baris
            Scanner scan_baris = new Scanner(input_baris);
            while (scan_baris.hasNextDouble()) {
                Double element = scan_baris.nextDouble();
                mat.get(baris).add(element);
            }
            scan_baris.close();
        }
        scan.close();
        if (baris == -1) {
            System.out.println("Matriks tidak dapat dibaca"); 
        } else {
            this.kolom = mat.get(0).size(); //menghitung jumlah kolom
            this.baris = mat.size();

            this.mat = new double[this.baris][this.kolom];
            for (int i = 0; i < this.baris; i++) {
                for (int j = 0; j < this.kolom; j++) {
                    this.mat[i][j] = mat.get(i).get(j); //duplikasi matriks
                }
            }
        }
    }

    public matriks(String file_name, int n) throws FileNotFoundException {// Membaca Matriks dari sebuah file
       
        ArrayList<ArrayList<Double>> mat = new ArrayList<ArrayList<Double>>();
        File file = new File(file_name); //membuat objek file
        Scanner scan = new Scanner(file); //membaca isi dari file

        for (int i = 0; i < n && scan.hasNextLine(); i++) {
            mat.add(new ArrayList<Double>()); //menambahkan ArrayList untuk tiap penambahan baris
            String input_baris = scan.nextLine(); //baca baris
            Scanner scan_baris = new Scanner(input_baris);
            while (scan_baris.hasNextDouble()) {
                Double element = scan_baris.nextDouble();
                mat.get(i).add(element);
            }
            scan_baris.close();
        }
        if (mat.size()==0) {
            System.out.println("Matriks tidak dapat dibaca"); 
        } else {
            this.kolom = mat.get(0).size(); //menghitung jumlah kolom
            this.baris = mat.size();
            this.mat = new double[this.baris][this.kolom];
            for (int i = barmin; i < this.baris; i++) {
                for (int j = colmin; j < this.kolom; j++) {
                    this.mat[i][j] = mat.get(i).get(j); //duplikasi matriks
                }
            }
        }

        if (scan.hasNextLine()) {
            String inputxy = scan.nextLine();
            Scanner scan_selanjutnya = new Scanner(inputxy);
            if (scan_selanjutnya.hasNextDouble()) {
                x = scan_selanjutnya.nextDouble(); // Ambil nilai x
            }
            if (scan_selanjutnya.hasNextDouble()) {
                y = scan_selanjutnya.nextDouble(); // Ambil nilai y
            }
            scan_selanjutnya.close();
        }
        scan.close();
    }
    public static String matriksToString(double[] m) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m.length; i++) {
            sb.append(String.format("%.2f", m[i])); // Format nilai double ke 2 desimal
            sb.append("\n"); // Menambahkan baris baru setelah setiap baris matriks
        }
        return sb.toString(); // Kembalikan string hasil konversi
    }
    public static String matriksToString(double[][] m) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                sb.append(String.format("%.2f", m[i][j])); // Format nilai double ke 2 desimal
                if (j < m[i].length - 1) {
                    sb.append(" "); // Menambahkan spasi antar elemen di satu baris
                }
            }
            sb.append("\n"); // Menambahkan baris baru setelah setiap baris matriks
        }
        return sb.toString(); // Kembalikan string hasil konversi
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public static void simpan (String content) throws IOException{
        Scanner scan = new Scanner(System.in);
        System.out.printf("Simpan dengan Nama File (sertakan akhiran .txt): ");
        String fileSimpan = scan.nextLine();

        FileWriter writer = new FileWriter(fileSimpan);
        writer.write(content);
        writer.close();
        scan.close();
    }


    /*SELEKTOR*/
    public int GetFirstIdxBar (matriks M){
        return barmin;
    }
    public int GetFirstIdxCol (matriks M){
        return colmin;
    }
    public int GetLastIdxBar (matriks M){
        return M.baris-1;
    } 
    public int GetLastIdxCol (matriks M){
        return M.kolom-1;
    }
    public double GetElement(int m, int n){
        return mat[m][n];
    }
    public void SetElement (int m, int n, double value){
        mat[m][n]= value;
    }


    /* BACA dan TULIS dengan INPUT/OUTPUT device */
    /* Baca Matriks dari Input Manual */
    public void ReadMat (){
        System.out.println("Masukkan Matriks: ");
        for (int i=0;i<this.baris;i++){
            for (int j=0; j<this.kolom;j++){
                this.mat[i][j] = scan.nextDouble(); //input
            }
        }
    }   

    /* Tampilkan Matriks ke Layar */
    public void PrintMat (){
        for (int i=0;i<this.baris;i++){
            for (int j=0;j<this.kolom;j++){
                System.out.printf("%.2f",this.mat[i][j]); //printf karena ada format
            }
        System.out.printf("\n");
        }
    }

    /* Simpan ke File */


    /* KELOMPOK OPERASI ARITMATIKA TERHADAP TYPE */
    public static matriks Multiply (matriks M, double k){
        matriks result = new matriks (M.baris, M.kolom);
        for (int i=0;i<M.baris;i++){
            for (int j=0; j< M. kolom; j++){
                result.mat[i][j]=M.mat[i][j]*k;
            }
        } return result;
    } 

    public static matriks Multiply (matriks M, matriks N){
        matriks result = new matriks (M.baris, N.kolom);
        for (int i=0; i<result.baris; i++){
            for (int j = 0; j<result.kolom;j++){
                result.mat[i][j] = 0 ;
                for (int k=0;k<M.baris; k++){
                    result.mat[i][j] += M.mat[i][k]*N.mat[k][j];
                }
            }
        } return result;
    }

    /* KELOMPOK OPERASI BARIS ELEMENTER */
    public void Swap (int bar1, int bar2){
        double[] temp = mat[bar1];
        mat[bar1] = mat[bar2];
        mat[bar2] = temp;
    }

    public void MultiplyBaris (int baris, double k){
        for (int i=0;i<kolom;i++){
            mat[baris][i]*=k;
        } 
    }

    public void AddXBaris (int bar1, int bar2, double k){//tujuannya baris 1
        for (int i= 0;i<kolom;i++){
            mat[bar1][i] += mat[bar2][i]*k;
        }
    }

    /* Jenis Matriks */
    public static matriks Identity (int N){
        matriks I = new matriks(N, N);
        for (int i=0;i<N;i++){
            I.mat[i][i]=1; 
        } return I;
    }
    public void ChangeVal(int m, int n, double k){
        mat[m][n] = k;
    }
}