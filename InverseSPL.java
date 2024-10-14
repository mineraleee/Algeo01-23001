public class InverseSPL {
    public static matriks OperasiInverse(matriks M, int m, int n){
        // AKU MASIH GATAU CARA CATCH EXCEPTION ERROR
        double[][] temp = new double[m+5][n+5];
        double[] b = new double[m+5];
        double[][] res = new double[m+5][n+5];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                temp[i][j] = M.GetElement(i, j);
            }
        }
        matriks A = new matriks(temp);
        matriks InvA = InverseOBE.inverseGaussJordan(A);
        for(int i=0; i<m; i++) b[i] = M.GetElement(i, 0);
        for (int i=0; i<m; i++){
            for (int j = 0; j<n;j++){
                res[i][j] = 0 ;
                for (int k=0;k<m; k++){
                    res[i][j] += InvA.GetElement(i, k)*b[k];
                }
            }
        }
        matriks result = new matriks(res);
        return result;
    }
    public static void main(String[] args){
        //contoh matriks doang, masih error pas input matriks gatau kenapa
        double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0},{5.0, -3, 1.0, 14.0}}; //solusi unik
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 7.0}}; //tidak ada solusi
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 6.0}};  //solusi tak berhingga
        //double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0}};
        //double[][] mat ={{1, 1, -1, -1, 1}, {2, 5, -7, -5, -2}, {2, -1, 1, 3, 4}, {5, 2, -4, 2, 6}}; //error fixed
        int m = 3;
        int n = 3; //mxn sudah include b
        matriks M = new matriks(mat);
        M = OperasiInverse(M, m, n);
        System.out.println("Solusi dari SPL adalah:");
        for (int i = 0; i < n; i++) {
            System.out.printf("%.2f\n", M.GetElement(i, 0));
        }
    }
}
