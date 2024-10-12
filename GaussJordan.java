public class GaussJordan {
    public static int Operation(double[][] mat, int m, int n, int flag){
        int c;
        for(int i=0; i<m; i++){
            if(mat[i][i] == 0){ //jika nol, maka cari baris lain yang punya bilangan bukan nol untuk ditukar
                c = 1;
                while(i+c < n && mat[i+c][c] == 0) c++; //cari baris dengan nilai bukan nol paling kiri
                if(i+c == n){
                    flag = 1; //pada satu kolom, nilainya nol semua sehingga tidak ada pivot
                    break;
                }
                for(int k=0; k<=n; k++){
                    double tmp = mat[i][k];
                    mat[i][k] = mat[i+c][k];
                    mat[i+c][k] = tmp;
                }
            }
            for(int j=0; j<m; j++){
                if(i != j){
                    double factor = mat[j][i]/mat[i][i];
                    for(int k=0; k<=n; k++){
                        mat[j][k] -= factor*mat[i][k];
                    } 
                }
            }
        }
        return flag;
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
    public static void main(String[] args){
        //contoh matriks doang, masih error pas input matriks gatau kenapa
        //double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0},{5.0, -3, 1.0, 14.0}}; //solusi unik
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 7.0}}; //tidak ada solusi
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 6.0}};  //solusi tak berhingga
        double[][] mat ={{1, 1, -1, -1, 1}, {2, 5, -7, -5, -2}, {2, -1, 1, 3, 4}, {5, 2, -4, 2, 6}};
        int m = 4;
        int n = 4;
        if(m >= n){
            int flag = Operation(mat, m, n, 0);
            if(flag == 1){
                flag = Cek(mat, m, n, flag);
                if(flag == 2) System.out.println("Solusi tak berhingga");
                else if(flag == 3) System.out.println("Tidak ada solusi");
            }else{
                System.out.println("Solusi dari SPL adalah:");
                for (int i=0; i<m; i++) {
                    System.out.printf("%.2f\n", mat[i][n]/mat[i][i]); //konstanta dibagi dengan pivot
                }
            }
        }else System.out.println("Solusi tak berhingga");
    }
}
