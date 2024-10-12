
public class Gauss{
    public static void Pivot(double[][] mat, int n){
        for(int i=0; i<n; i++){
            int pivot = i;
            for(int j=i+1; j<n; j++){
                if(Math.abs(mat[j][i]) > Math.abs(mat[pivot][i])){
                    pivot = j;
                }
            } 
            if(mat[pivot][i] == 0) continue;
            else{
                if(pivot != i){
                    //Menukarkan baris pivot menjadi paling atas
                    for(int j=i; j<=n; j++){
                        /*double tmp = mat[i][j];
                        mat[i][j] = mat[pivot][j];
                        mat[pivot][j] = tmp;*/
                        matriks.Swap(i, pivot);
                    }
                }
            }
            //Pembagian dengan faktor pivot
            for(int j=i+1; j<n; j++){
                double factor = mat[j][i]/mat[i][i];
                for(int k=i; k<=n; k++){
                    mat[j][k] -= factor*mat[i][k];
                }
            }
        }
    }

    public static int Cek(double[][] mat, int n, int flag){
        for(int i=0; i<n; i++){
            boolean tmp = true;
            for(int j=0; j<=n; j++){
                if(j == n && tmp){
                    if(mat[i][j] == 0) flag = 2;
                    else flag = 3;
                }else{
                    if(mat[i][j] != 0){
                        tmp = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    public static void Substitusi(double[][] mat, int n, double[] x){
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
        ////double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 7.0}}; //tidak ada solusi
        double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 6.0}};  //solusi tak berhingga
        int n = 3;
        double[] x = new double[n+5]; //nambahin aja buat jaga jaga kalo overflow
        Pivot(mat, n);
        int flag = Cek(mat, n, 1);
        if(flag == 1){
            Substitusi(mat, n, x);
            System.out.println("Solusi dari SPL adalah:");
            for (int i = 0; i < n; i++) {
                System.out.printf("%.2f\n", x[i]);
            }
        }else{
            if(flag == 2) System.out.println("Solusi tak berhingga");
            else if(flag == 3) System.out.println("Tidak ada solusi");
        }
    }
}
