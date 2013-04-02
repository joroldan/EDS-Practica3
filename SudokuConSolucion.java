/************************************************************************
  SudokuConSolucion.java

  Implementacion de un sudoku que se puede resolver automaticamente

  Jorge Roldan Lopez
************************************************************************/

import java.util.List;

public class SudokuConSolucion extends Sudoku
{
	private GrafoConColores gr;

	public SudokuConSolucion(int n)
	{
		super(n);
		gr = new GrafoConColores();

		construirGrafoInicial();
	}

	public boolean resoluble()
	{
		if (checkValues())
		{
			GrafoConColores aux = new GrafoConColores(gr);
			return aux.colorear(getSize());
		}
		else return false;
	}

	public void resolver()
	{
		if (resoluble())
		{
			gr.colorear(getSize());
			//Recoger colores
			int f, c, n;
			List<Integer> lista = gr.listaVerticesColoreados();
			for (Integer i: lista)
			{
				f = (i-1)/getSize();
				c = (i-1)%getSize();
				n = gr.getColorVertice(i);
				addNumber(f,c,n);
			}
		}
		else System.out.println("\n\tEl sudoku no se podia resolver");
	}

	public boolean valorValido(int f, int d, int c)
	{
		if (c!=0) return gr.isValid(f*getSize()+d+1, c);
		else return true; //Cero es el valor por defecto, por eso se permite
	}

	private void actualizarGrafo()
	{
		gr.borrarColores();

		for(int i=0; i<getSize(); i++)
		{
			for(int j=0; j<getSize(); j++)
			{
				if(valorActual(i,j)!=0)
					gr.setColor(i*getSize()+j+1, valorActual(i,j));
			}
		}
	}

	public void darValorInicial(int f, int c, int n)
	{
		if ((f<getSize())&&(c<getSize())&&(0<=f)&&(0<=c)&&(0<n)&&(n<=getSize()))
		{
			super.darValorInicial(f,c,n);
			gr.setColor(f*getSize()+c+1, n);
		}
		else System.out.println("Celda o valor invalido");
	}

	public void addNumber(int f, int c, int n)
	{
		super.addNumber(f,c,n);
		if ((f<getSize())&&(c<getSize())&&(0<=f)&&(0<=c)&&(0<n)&&(n<=getSize()))
		{
			gr.setColor(f*getSize()+c+1, n);
		}
		else System.out.println("Celda o valor invalido");
	}

	public void removeNumber(int f, int c)
	{
		super.removeNumber(f,c);
		if ((f<getSize())&&(c<getSize())&&(0<=f)&&(0<=c))
		{
			gr.removeColor(f*getSize()+c+1);
		}
		else System.out.println("Celda invalida");
	}

	public void restaurar()
	{
		super.restaurar();
		actualizarGrafo();
	}

	private boolean checkValues()
	{
		List<Integer> l = gr.listaVerticesColoreados();
		boolean ans=true;
		for (Integer i : l)
		{
			ans = ans && gr.isValid(i, gr.getColorVertice(i));
		}
		return ans;
	}

	private void construirGrafoInicial()
	{
		int numFilas=this.getSize();
		int numVertices=numFilas*numFilas;
		for (int v=1; v<=numVertices; v++)
			gr.addVertice(v);
		//Aristas para vertices en la misma fila
		for (int i=0; i<numFilas ; i++)
		{
			for (int j=0; j<numFilas; j++)
			{
				for (int k=j+1; k<numFilas; k++)
				{
					int v1 = numFilas*i+j+1;
					int v2 = numFilas*i+k+1;
					gr.addArista(v1,v2);
				}
			}
		}
		//Aristas para vertices en la misma columna
		for (int j=0; j<numFilas; j++)
		{
			for (int i=0; i<numFilas; i++)
			{
				for (int k=i+1; k<numFilas; k++)
				{
					int v1 = numFilas*i+j+1;
					int v2 = numFilas*k+j+1;
					gr.addArista(v1,v2);
				}
			}
		}
		//Aristas para vertices en la misma region
		int n = (int)Math.sqrt(numFilas);
		for (int i=0; i<n ; i++)
		{
			for (int j=0; j<n; j++)
			{
				int i0 = i*n;
				int j0 = j*n;
				// (i0,j0) es la esquina superior izquierda de la región
				for (int i1=i0; i1<i0+n; i1++)
				{
					for (int j1=j0; j1<j0+n; j1++)
					{
						for (int i2=i0; i2<i0+n; i2++)
						{
							for (int j2=j0; j2<j0+n; j2++)
							{
								int v1 = numFilas * i1 + j1 + 1;
								int v2 = numFilas * i2 + j2 + 1;
								if (v1!=v2) gr.addArista(v1,v2);
							}
						}
					}
				}
			}
		}
		// Valores iniciales del sudoku, en nuestro caso no haria falta pues se ponen despues
		for (int i=0; i<numFilas; i++)
		{
			for (int j=0; j<numFilas; j++)
			{
				if (this.valorInicial(i,j)!=0)
					gr.setColor(i*numFilas+j+1,this.valorInicial(i,j));
			}
		}
	} 
}