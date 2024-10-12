public class GaussJordan {
    public static int Operation(double[][] mat, int n, int flag){
        int c;
        for(int i=0; i<n; i++){
            if(mat[i][i] == 0){ //jika nol, maka cari baris lain yang punya bilangan bukan nol untuk ditukar
                c = 1;
                while(i+c < n && mat[i+c][c] == 0) c++; //cari baris dengan nilai bukan nol paling kiri
                if(i+c == n){
                    flag = 1; //pada satu kolom, nilainya nol semua sehingga tidak ada pivot
                    break;
                }
                for(int k=0; k<=n; k++){
                    matriks.Swap(i, i+c);
                }
            }
            for(int j=0; j<n; j++){
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
    public static void main(String[] args){
        //contoh matriks doang, masih error pas input matriks gatau kenapa
        double[][] mat = {{3.0, 2.0,-4.0, 3.0},{2.0, 3.0, 3.0, 15.0},{5.0, -3, 1.0, 14.0}}; //solusi unik
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 7.0}}; //tidak ada solusi
        //double[][] mat = {{1.0, 1.0,2.0, 4.0},{2.0, -1.0, 1.0, 2.0},{1.0, 2.0, 3.0, 6.0}};  //solusi tak berhingga
        int n = 3;
        int flag = Operation(mat, n, 0);
        if(flag == 1){
            flag = Cek(mat, n, flag);
            if(flag == 2) System.out.println("Solusi tak berhingga");
            else if(flag == 3) System.out.println("Tidak ada solusi");
        }else{
            System.out.println("Solusi dari SPL adalah:");
            for (int i = 0; i < n; i++) {
                System.out.printf("%.2f\n", mat[i][n]/mat[i][i]); //konstanta dibagi dengan pivot
            }
        }
    }
}
