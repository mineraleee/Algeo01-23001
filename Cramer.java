public class Cramer {
    public static int OperasiCramer (matriks M, int n, double[] x, int flag){
        flag = 1;
        double[][] a = new double[n+5][n+5];
        for(int i=0; i<n; i++){
            for(int j=0; j<=n; j++){
                a[i][j] = M.GetElement(i, j);
                //System.out.printf("%f ", a[i][j]);
            }
            //System.out.println();
        }
        matriks temp = new matriks(a);
        double detA = Determinan.determinanKofaktor(M, n);
        if(detA == 0) flag = 3;
        int idx = 0;
        while(idx < n){
            for(int i=0; i<n; i++){
                temp.ChangeVal(i, idx, temp.GetElement(i, n));
            }
            if(detA != 0){
                x[idx] = Determinan.determinanKofaktor(temp, n)/detA;
                matriks Temp = new matriks(a);
                temp = Temp;
                idx++;
            }else{
                if(Determinan.determinanKofaktor(temp, n) == 0) return 2;
            }
        }
        return flag;
    }
    public static void main(String[] args){
        //contoh matriks doang, masih error pas input matriks gatau kenapa
        ////solusi unik
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 7.0}}; //tidak ada solusi
        double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 6.0}};  //solusi tak berhingga
        //double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0}};
        //double[][] mat ={{1, 1, -1, -1, 1}, {2, 5, -7, -5, -2}, {2, -1, 1, 3, 4}, {5, 2, -4, 2, 6}}; //error fixed
        int m = 3;
        int n = 3; //mxn sudah include b
        if(m == n){
            double[] x = new double[n+5]; //nambahin aja buat jaga jaga kalo overflow
            matriks M = new matriks(mat);
            int flag = OperasiCramer(M, n, x, 0);
            if(flag == 1){
                for (int i = 0; i < n; i++) {
                    System.out.printf("x%d = %.2f\n", i+1, x[i]);
                }
            }else if(flag == 2) System.out.println("Solusi tak hingga.");
            else System.out.println("Tidak ada solusi.");
        }else System.out.println("Tidak bisa dihitung dengan Metode Cramer.");
    }
}