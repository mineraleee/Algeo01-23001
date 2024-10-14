public class Determinan {
    /* Menghitung determinan menggunakan OBE */
    public static double determinantOBE (matriks M, int n){

        double total = 1.0;
        double [] temp = new double [n]; //menyimpan nilai baris sementara

        if (n == 1) //jika matriks 1x1
            return M.GetElement(0,0);

        for (int i=0;i<n;i++){ 
            int index = i;
            while (index < n && M.GetElement(index, i)==0){ //mencari yang tidak 0
                index++;
            } 
            if (index ==n){ //jika sudah di baris terakhir, berpindah ke kolom selanjutnya
                continue;
            }
            if (index != i){
                M.Swap(index, i); //pertukaran baris
                total *= -1; //perkalian -1 setiap dilakukan pertukaran baris
            }

            for (int j=0;j<n;j++){
                temp[j] = M.GetElement(i, j); //menyimpan elemen baris i
            }

            for (int j=i+1;j<n;j++){
                double bil1 = temp[i]; //sebagai acuan untuk perkalian
                double bil2 = M.GetElement(j, i); //nilai dari baris selanjutnya

                for (int k=0;k<n;k++){
                    double hasil = (bil1 * M.GetElement(j, k) - (bil2 * temp[k])); //operasi OBE
                    M.mat[j][k] = hasil;
                }
                total *=bil1; //perkalian baris
            }
        }

        double det =1.0;
        for (int i=0;i<n;i++){
            det *= M.GetElement(i,i);
        }
        return det/total;
    }

    /* Menghitung determinan menggunakan kofaktor */
    static double[][] Kofaktor (matriks M, int n, int p, int q ){
        double [][] temp = new double [n-1][n-1]; //minor entri
        int fillRow = 0, fillCol = 0;
        for (int i=0; i<n; i++){
            for (int j=0; j<n;j++){
                if (i != p && j != q){ //mengisi matriks dengan mengecualikan elemen baris dan kolom yang ditentukan.
                    temp[fillRow][fillCol++]= M.GetElement(i, j);
                    if (fillCol==n - 1){
                        fillCol=0;
                        fillRow++;
                    }
                }
            }
        } 
        return temp;
    }

    static double determinanKofaktor (matriks M, int n){
        double result = 0;
        if (n == 1){ //jika matriks 1x1
            return M.GetElement(0,0);
        }
        if (n == 2){ //jika matriks 2x2
            return (M.GetElement(0, 0) * M.GetElement(1, 1)) - (M.GetElement(0, 1) * M.GetElement(1, 0));
        }
         int flag =1; //tanda pada matriks

         for (int f=0;f<n;f++){
            double[][] temp = Kofaktor(M, n, 0, f);
            matriks subMatriks = new matriks(temp);
            result += flag * M.GetElement(0,f)* determinanKofaktor(subMatriks, n-1); //perhitungan determinan
            
            flag =-flag; //perubahan tanda minus positive
         }

         return result;
    }
}
