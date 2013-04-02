/************************************************************************
  Sudoku.java

  Implementacion de un sudoku

  Jorge Roldan Lopez
************************************************************************/

public class Sudoku
{
	private int [][] inicial;
	private int [][] celdas;
	private final int tam;

	public Sudoku(int n)
	{
		if (Math.ceil(Math.sqrt(n))!=Math.sqrt(n)) throw new RuntimeException("Tamaño inválido");
		
		this.tam = n;

		this.celdas  = new int[tam][tam];
		this.inicial = new int[tam][tam];
	}

	public int getSize()
	{
		return this.tam;
	}

	public void darValorInicial(int f, int c, int n)
	{
		if ((f<tam)&&(c<tam)&&(0<=f)&&(0<=c)&&(0<n)&&(n<=tam))
		{
			this.celdas [f][c]  = n;
			this.inicial[f][c]  = n;
		}
		else System.out.println("Celda o valor invalido");
	}

	public void addNumber(int f, int c, int n)
	{
		if ((f<tam)&&(c<tam)&&(0<=f)&&(0<=c)&&(0<n)&&(n<=tam))
		{
			this.celdas[f][c] = n;
		}
		else System.out.println("Celda o valor invalido");
	}

	public void removeNumber(int f, int c)
	{
		if ((f<tam)&&(c<tam)&&(0<=f)&&(0<=c))
		{
			this.celdas[f][c] = 0;
		}
		else System.out.println("Celda invalida");
	}

	public void imprimir()
	{
		barra();
		for (int i=0; i<tam; i++)
		{	
			System.out.print("\n |  ");
			for (int j=0; j<tam; j++)
			{
				imprimirCelda(i,j);
				if ((j+1)%Math.sqrt(tam)==0) System.out.print("  |  ");
				else                         System.out.print("   ");
			}
			if ((i+1)%Math.sqrt(tam)==0) barra();
		}
		System.out.println("\n\n");
	}

	private void barra()
	{
		System.out.print("\n +-----");
		for (int i=1; i<tam; i++)
		{
			if ((i+1)%Math.sqrt(tam)==0) System.out.print("------+");
			else 						 System.out.print("-----");
		}
	}

	private void imprimirCelda(int i, int j)
	{
		if (this.celdas[i][j]==0) System.out.print("--");
		else 					  System.out.format("%2d",celdas[i][j]);
	}

	public void restaurar()
	{
		for (int i=0; i<tam;i++)
		{
			for (int j=0; j<tam; j++) this.celdas[i][j]  = this.inicial[i][j];
		}
	}

	public int valorInicial(int f, int c)
	{
		if ((f<tam)&&(c<tam)&&(0<=f)&&(0<=c))
		{
			return this.inicial[f][c];
		}
		else return -1;
	}

	public int valorActual(int f, int c)
	{
		if ((f<tam)&&(c<tam)&&(0<=f)&&(0<=c))
		{
			return this.celdas[f][c];
		}
		else return -1;
	}
}