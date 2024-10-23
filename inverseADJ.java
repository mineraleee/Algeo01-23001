public class inverseADJ {
    public static matriks Transpose(matriks M){
        int i,j;
        //membuat matriks baru dengan ukuran matriks M
        matriks transpose=new matriks(M.baris,M.kolom);

        //mengisi matriks transpose dengan setiap elemen m(i,j) ditukar nilainya dengan elemen m(j,i)
        for (i=0;i<M.baris;i++){
            for(j=0;j<M.kolom;j++){
                transpose.mat[i][j]=M.mat[j][i];
            }
        }
        return transpose;
    }

    public static matriks matriksKofaktor(matriks M){
        int i,j;
        //membuat matriks baru dengan ukuran matriks M untuk menyimpan hasil kofaktor
        matriks kofaktor = new matriks(M.baris,M.kolom);

        //proses pengisian matriks kofaktor
        for (i=0;i<M.baris;i++){
            for (j=0;j<M.kolom;j++){
                //menghitung minor entri dari M dengan menghilangkan baris i dan kolom j
                matriks minorEntri=new matriks(Determinan.Kofaktor(M,M.baris, i, j));//memanggil fungsi

                //menghitung determinan dari matriks minorEntri
                double determinanMinorEntri=Determinan.determinanKofaktor(minorEntri, minorEntri.baris);

                //penentuan tanda (+/-) untuk kofaktor
                int tanda;
                if ((i+j)%2==0){
                    tanda=1;
                }
                else{
                    tanda=-1;
                }
                kofaktor.mat[i][j]=tanda*determinanMinorEntri;
            }
        }
        return kofaktor;
    }
    public static matriks inverseAdjoin(matriks M) {
        int i,j;
        //mencari det(M)
        double determinan = Determinan.determinanKofaktor(M,M.baris);
        if (determinan == 0) {
            throw new IllegalArgumentException("Matriks tidak memiliki invers (determinan = 0).");
        }

        //mencari adj(kofaktor)
        matriks kofaktor = matriksKofaktor(M);
        matriks adjoin = Transpose(kofaktor);

        //membuat matriks baru ukuran matriks M untuk menyimpan hasil inverse
        matriks invers = new matriks(new double[M.baris][M.kolom]);

        //mengisi matriks inverse:membagi matriks adjoin dengan determinan
        for (i = 0; i < M.baris; i++) {
            for (j = 0; j < M.kolom; j++) {
                invers.mat[i][j] = adjoin.mat[i][j] / determinan;
            }
        }
        return invers;
    }
}