//PENTING, DISINI AKU BUAT MXN ITU CUMA MATRIKS PERSAMAAN TANPA KONSTANTA
//MAKANYA UNTUK KOLOM, AKU KADANG BUAT PERULANGANNYA JADI <= n
//SAMA  HALNYA DENGAN GAUSS-JORDAN
//TAPI KALO INPUTNYA MXN INCLUDE KONSTANTA NANTI TINGGAL DI ADJUST AJA KOK

public class Gauss {
    public static void Pivot(double[][] mat, int m, int n){
        for(int j=0; j<n; j++){
            int pivot = j;
            for(int i=j+1; i<m; i++){
                if(Math.abs(mat[i][j]) > Math.abs(mat[pivot][j])) pivot = i;
            }
            if(mat[pivot][j] == 0) continue;
            else{
                if(pivot != j){
                    for(int k=j; k<=n; k++){
                        double tmp = mat[j][k];
                        mat[j][k] = mat[pivot][k];
                        mat[pivot][k] = tmp;
                    }
                }    
            }

            for(int i=j+1; i<m; i++){
                double factor = mat[i][j]/mat[j][j];
                for(int k=j; k<=n; k++){
                    mat[i][k] -= factor*mat[j][k];
                }
            }
        }
    }
    public static int Cek(double[][] mat, int m, int n, int flag){
        for(int j=0; j<=n; j++){
            if(j == n){
                if(mat[m-1][j] == 0) flag = 2;
                else flag = 3;
            }else{
                if(Math.abs(mat[m-1][j]) > 0.001) return flag;
            }
        }
        return flag;
    }
    public static void Substitusi(double[][] mat, int m, int n, double[] x){
        for(int i=n-1; i>=0; i--){
            double jml = 0;
            for(int j=i+1; j<n; j++){
                jml += mat[i][j]*x[j];
            }
            x[i] = (mat[i][n]-jml)/mat[i][i];
        }
    }

    public static void main(String[] args){
        //contoh matriks doang, masih error pas input matriks gatau kenapa
        //double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0},{5.0, -3, 1.0, 14.0}}; //solusi unik
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 7.0}}; //tidak ada solusi
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 6.0}};  //solusi tak berhingga
        //double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0}};
        double[][] mat ={{1, 1, -1, -1, 1}, {2, 5, -7, -5, -2}, {2, -1, 1, 3, 4}, {5, 2, -4, 2, 6}}; //error fixed
        int m = 4;
        int n = 4; //mxn sudah include b
        if(m >= n){
            double[] x = new double[n+5]; //nambahin aja buat jaga jaga kalo overflow
            Pivot(mat, m, n);
            int flag = Cek(mat, m, n, 1);
            if(flag == 1){
                Substitusi(mat, m, n, x);
                System.out.println("Solusi dari SPL adalah:");
                for (int i = 0; i < n; i++) {
                    System.out.printf("%.2f\n", x[i]);
                }
            }else{
                if(flag == 2) System.out.println("Solusi tak berhingga");
                else if(flag == 3) System.out.println("Tidak ada solusi");
            }
        }else System.out.println("Solusi tak berhingga");
    }
}
