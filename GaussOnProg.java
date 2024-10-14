//Nyoba implement ADT tapi belum jadi

public class GaussOnProg {
    public static matriks Pivot(matriks M, int m, int n){
        for(int j=0; j<n; j++){
            int pivot = j;
            for(int i=j+1; i<m; i++){
                if(Math.abs(M.GetElement(i, j)) > Math.abs(M.GetElement(pivot, j))) pivot = i;
            }
            if(M.GetElement(pivot, j) == 0) continue;
            else{
                if(pivot != j){
                    for(int k=j; k<=n; k++){
                        double tmp = M.GetElement(j, k);
                        M.ChangeVal(j, k, M.GetElement(pivot, k));
                        M.ChangeVal(pivot, k, tmp);
                    }
                }    
            }

            for(int i=j+1; i<m; i++){
                double factor = M.GetElement(i, j)/M.GetElement(j, j);
                for(int k=j; k<=n; k++){
                    M.Add(i, k, factor*M.GetElement(j, k));
                }
            }
        }
        return M;
    }
    public static int Cek(matriks M, int m, int n, int flag){
        for(int j=0; j<=n; j++){
            if(j == n){
                if(M.GetElement(m-1, j) == 0) flag = 2;
                else flag = 3;
            }else{
                if(Math.abs(M.GetElement(m-1, j)) > 0.001) return flag;
            }
        }
        return flag;
    }
    public static void Substitusi(matriks M, int m, int n, double[] x){
        for(int i=n-1; i>=0; i--){
            double jml = 0;
            for(int j=i+1; j<n-1; j++){
                jml += M.GetElement(i, j)*x[j];
            }
            x[i] = (M.GetElement(i, n)-jml)/M.GetElement(i, i);
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
            matriks M = new matriks(mat);
            M = Pivot(M, m, n);
            int flag = Cek(M, m, n, 1);
            if(flag == 1){
                Substitusi(M, m, n, x);
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
