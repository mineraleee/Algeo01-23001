public class GaussJordan {
    public static int OperasiGaussJordan(double[][] mat, int m, int n, int flag){
        int c;
        for(int i=0; i<m; i++){
            if(mat[i][i] == 0){ //jika nol, maka cari baris lain yang punya bilangan bukan nol untuk ditukar
                c = 1;
                while(i+c < n && mat[i+c][i] == 0) c++; //cari baris dengan nilai bukan nol paling kiri
                if(i+c == n){
                    flag = 1; //pada satu kolom, nilainya nol semua sehingga tidak ada pivot
                    break;
                }
                for(int k=i; k<=n; k++){
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
    public static void SubstitusiParametrik(double[][] mat, int m, int n) {
        boolean flag;
        int cnt = 0;
        char param = 's'; //variabel untuk nama parameter
    
        String[] sol = new String[n]; //array untuk menyimpan solusi sebagai string
    
        for (int i = n - 1; i >= 0; i--) {
            flag = true;
            for (int j = 0; j < n; j++) {
                if (mat[i][j] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                sol[i] = String.valueOf(param); //variabel parameter misal 's', 't', dll.
                param++;
                cnt++;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%.2f", mat[i][n]/mat[i][i]));
    
                for (int j = i + 1; j < n; j++) {
                    if (mat[i][j] != 0) {
                        if(mat[i][j]/mat[i][i] == 1) sb.append(" - ").append(sol[j]);
                        else if(mat[i][j] >= 0) sb.append(" - ").append(String.format("%.2f", mat[i][j]/mat[i][i])).append(sol[j]);
                        else sb.append(" + ").append(String.format("%.2f", -1*mat[i][j]/mat[i][i])).append(sol[j]);
                    }
                }
                //if(mat[i][i] != 1)sb.append(") / ").append(String.format("%.2f", mat[i][i])); 
                sol[i] = sb.toString();
            }
        }
        // Output solusi parametrik
        for (int i = 0; i < n; i++) {
            System.out.println("x" + (i + 1) + " = " + sol[i]);
        }
    }      
    public static void main(String[] args){
        //contoh matriks doang, masih error pas input matriks gatau kenapa
        //double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0},{5.0, -3, 1.0, 14.0}}; //solusi unik
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 7.0}}; //tidak ada solusi
        double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 6.0}};  //solusi tak berhingga
        //double[][] mat ={{1, 1, -1, -1, 1}, {2, 5, -7, -5, -2}, {2, -1, 1, 3, 4}, {5, 2, -4, 2, 6}};
        int m = 3;
        int n = 3;
        int flag = OperasiGaussJordan(mat, m, n, 0);
        if(flag == 1){
            flag = Cek(mat, m, n, flag);
            if(flag == 2){
                SubstitusiParametrik(mat, m, n);
            }
            else if(flag == 3) System.out.println("Tidak ada solusi");
        }else{
            for (int i=0; i<m; i++) {
                System.out.printf("x%d = %.2f\n", i+1, mat[i][n]/mat[i][i]); //konstanta dibagi dengan pivot
            }
        }
    }
}
