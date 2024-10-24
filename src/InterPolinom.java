//n = jumlah titik
//berarti persamaannya n aja tapi pangkat tertinggi x nya n-1

public class InterPolinom {
    public static double[] OperasiPolinom(int n, double[][] mat){
        double[] a = new double[n+5];
        Gauss.OperasiGauss(mat, n, n, 0);
        Substitusi(mat, n, n, a);
        return a;
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
    public static double[][] UbahKeAug(int n, double[][] titik){
        double[][] tmp = new double[n+5][n+5];
        for(int i=0; i<n; i++){
            for(int j=0; j<=n; j++){
                if(j == n) tmp[i][j] = titik[i][1];
                else tmp[i][j] = Math.pow(titik[i][0], j);
            }
        }
        return tmp;
    }
    public static double Hitung(int n, double[] a, double x){
        double val = 0;
        for(int i=0; i<n; i++){
            val += a[i]*Math.pow(x, i);
        }
        return val;
    }
    public static void main(String[] args){
        int n = 3;
        double[][] pers = {{8.0, 2.0794}, {9.0, 2.1972}, {9.5, 2.2513}};
        double x = 9.2;
        double[] a = OperasiPolinom(n, UbahKeAug(n, pers));
        System.out.printf("f(x) = ");
        for(int i=n-1; i>=0; i--){
            System.out.printf("%.4f", a[i]);
            if(i != 0){
                System.out.printf("x", i);
                if(i != 1) System.out.printf("^%d", i);
                System.out.printf(" + ", i);
            } else System.out.println();
        }
        System.out.printf("f(%.1f) = %.4f", x, Hitung(n, a, x));
    }
}