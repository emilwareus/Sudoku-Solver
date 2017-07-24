import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
	private int matrix[][]; //matrix[row][col];
	private int Smatrix[][]=new int[9][9];
	private ArrayList<Integer> filled = new ArrayList<>();
	private ArrayList<Integer> zero = new ArrayList<>();
	private int MinRow, MinCol;
	private boolean solved=false;


	public Matrix(){
		matrix=new int[9][9];


		for(int k=0;k<10;k++){
			filled.add(10);
		}
	}
	// Skapar en ArrayList med 1 till 9. Detta anv�nds for att hitta fungerande alternativ i en ruta. 
	private ArrayList<Integer> makeArray(){
		ArrayList<Integer> array=new ArrayList<>();
		for(int k=1;k<10;k++){
			array.add(k);

		}
		return array;
	}
	/**
	 * Metoden byterut attributet matrix som �r huvudmatrisen i klassen. 
	 * @param NewMatrix, den matrisen som kommer bli attributet matrix i klassen. 
	 */
	public void MatrixAppend(int[][] NewMatrix){

		matrix=NewMatrix;

	}
	/**
	 *	V�rdet p� en specifik plats i matrisen returneras.
	 *
	 * @param Raden f�r det v�rdet som ska returneras
	 * @param Colonen f�r det v�rdet som ska returneras
	 * @return ett int v�rde mellan 1 och 9 (0) som ligger i platsen (row,col)
	 */
	public int getValue(int row, int col){
		return matrix[row][col];
	}

	// Dett kollas vilka alternativ som finns p� raden row p� platsen (row, col). 
	// Detta g�rs f�r en specifik matris p� v�rderna i ArrayListen arrayRow.
	private ArrayList<Integer> checkRow(int row, int col, ArrayList<Integer> arrayRow, int Mtrix[][]){
		ArrayList<Integer> haveRemoved=new ArrayList<>();
		for(int k=0; k <9;k++){
			if(haveRemoved.contains(Mtrix[row][k])){
				return zero;
			}
			if(arrayRow.contains(Mtrix[row][k])){
				haveRemoved.add(Mtrix[row][k]);
				arrayRow.remove(arrayRow.indexOf(Mtrix[row][k]));
			}


		}

		return arrayRow; 
	}


	// Dett kollas vilka alternativ som finns p� Columnen row p� platsen (row, col). 
	// Detta g�rs f�r en specifik matris p� v�rderna i ArrayListen arrayCol.
	private ArrayList<Integer> checkCol(int row, int col, ArrayList<Integer> arrayCol, int Mtrix[][]){
		ArrayList<Integer> haveRemoved=new ArrayList<>();
		for(int k=0; k <9;k++){
			if(haveRemoved.contains(Mtrix[k][col])){

				return zero;
			}
			if(arrayCol.contains(Mtrix[k][col])){
				haveRemoved.add(Mtrix[k][col]);
				arrayCol.remove(arrayCol.indexOf(Mtrix[k][col]));

			}
		}
		return arrayCol; 
	}

	// Dett kollas vilka alternativ som finns i "rutan" p� platsen (row, col). 
	// Detta g�rs f�r en specifik matris p� v�rderna i ArrayListen arrayIM.
	private ArrayList<Integer> checkIM(int row, int col, ArrayList<Integer> arrayIM, int Mtrix[][]){
		ArrayList<Integer> haveRemoved=new ArrayList<>();
		int C=col/3*3;
		int R=row/3*3;
		for(int k=0;k<3;k++){
			for(int s=0;s<3;s++){
				if(haveRemoved.contains(Mtrix[s+R][k+C])){
					return zero;
				}
				if(arrayIM.contains(Mtrix[s+R][k+C])){
					haveRemoved.add(Mtrix[s+R][k+C]);
					arrayIM.remove(arrayIM.indexOf(Mtrix[s+R][k+C]));
				}

			}
		}

		return arrayIM;
	}

	// Alternativen testas f�r en specifik ruta. 
	// Dettar returnerar en ArrayList med de alternativen som �r till�tna f�r denna ruta. 
	private ArrayList<Integer> checkAlt(int row, int col, int Mtrix[][]){
		if(Mtrix[row][col]!=0){
			return filled;
		}

		ArrayList array = makeArray();
		array= checkRow(row, col, array, Mtrix);
		array= checkCol(row, col, array, Mtrix);
		array= checkIM(row, col, array, Mtrix);
		return array;
	}

	/**
	 * Metoden prinatar ut matrisen matrix i kommandof�nstret. Denna anv�nds f�r att 
	 * kolla funktionalliteten utan ett grafiskt anv�ndargr�nnssnitt. 
	 */
	public void Print(){
		for(int k=0; k<9;k++){
			for(int s=0; s<9;s++){
				System.out.print(matrix[k][s] + "\t");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Metoden printar antalet alternativ p� varje specifik plats i matrisen. Denna anv�nds f�r
	 * fels�kning och optimering av solve-metoden. 
	 */
	public void PrintAlt(){
		for (int k=0; k<9; k++){
			for (int s=0; s<9; s++){
				System.out.print(checkAlt(k,s, matrix).size() + "\t");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Metoden fyller alla platser som endast har 1 alternativ att v�lja p�.
	 * Detta f�r att optimera l�sningshastigheten. 
	 */
	public void fillOne(){
		fillOne(matrix);
	}

	//Privatemetoden till fillOne, 
	//en matris m�ste v�ljas under recutionen f�r att "r�tt" matris ska l�sas. 
	private int[][] fillOne(int Trix[][]){

		for(int k=0; k<9;k++){
			for(int s=0; s<9;s++){
				ArrayList<Integer> temp=checkAlt(k,s, Trix);
				if(temp.size()==1){
					Trix[k][s]=temp.get(0);
					s=0;
					k=0;

				}
			}
		}
		return Trix;

	}

	/**
	 * Denna metod hittar den ruta med minst antal alternativ i matrisen. 
	 * Den returnerar seda en matris inneh�llande dessa alternativ. 
	 * Attribut h�ller �ven reda p� var dessa alternativ kan anv�ndas i matrisen. 
	 * @param Mtrix �r den matrisen som ska kollas
	 * @return En ArrayList med alternativen
	 */
	public ArrayList minAlt(int Mtrix[][]){
		int t = 11;
		ArrayList<Integer> min=null;
		ArrayList temp;
		for(int k=0; k<9;k++){
			for(int s=0; s<9;s++){
				temp=checkAlt(k,s, Mtrix);
				if(temp.size()<t){
					MinRow=k;
					MinCol=s;
					t=temp.size();
					min=temp;
				}
			}
		}
		return min;
	}

	/**
	 * Metoden kollar om matrisen �r l�st. 
	 * 
	 * @return en boolean, true== l�st matrix
	 */
	public boolean isSolve(){
		return solved;
	}

	/**
	 * Denna metod l�ser en godtyckling matris recursivt. 
	 * Matrisen beh�ver ha till�tna v�rden farr att inte
	 * ge exception. Detta l�ses dock i anv�ndargr�nssnittet. 
	 */
	public void solve(){
		solved=false;
		solve(matrix);
	}
	// Den privata metoden f�r solvern. 
	// Den specifika matrisen som ska solvas m�ste matas in i varje f�rs�k. N�r den 
	// hittar en l�sning s�tter moteden den globala matrisen matrix till det korrekta v�rdet. 
	// Om detta sker n�ndras �ven solved till true
	private boolean solve(int[][] tmatrix){


		int[][] temp=matrixCopy(tmatrix);

		if(minAlt(temp).size()==0 && solved!=true){

			return false;

		}else if(minAlt(temp).size()==1 && solved!=true){
			temp=fillOne(temp);
			solve(temp);
		}
		else if(minAlt(temp).size()==10 && solved!=true){

			matrix=matrixCopy(temp);
			solved=true;
			return true;

		}
		else if(minAlt(temp).size()>1 && minAlt(temp).size()<10 && solved!=true){
			ArrayList<Integer> alts=minAlt(temp);  
			int R=MinRow;
			int C=MinCol;

			for(int k=0;k<alts.size();k++){
				int f=k+1;

				int y=(int)alts.get(k);
				temp[R][C]=y;

				solve(temp);
			}
		}
		return true;
	}


	// Denna metod kopierar matrisen matris. Detta anv�nds eftersom det blir fel
	// med refferenserna i solve om matrisen kopieras med "=". 
	private int[][] matrixCopy(int[][] matris){
		int[][] newMatrix = new int[9][9];
		for (int i = 0 ; i < 9; ++i) {
			for (int j = 0 ; j < 9; ++j) {
				newMatrix[i][j] = matris[i][j];				
			}

		}
		return newMatrix;
	}


}
