/************************************************************************
  GrafoConColores.java

  Implementacion de un grafo con colores con vertices enteros

  Jorge Roldan Lopez
************************************************************************/

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class GrafoConColores extends Grafo
{
	private Map<Integer,Integer> tablaColores;

	public GrafoConColores()
	{
		super();
		tablaColores = new HashMap<Integer,Integer>();
	}

	public GrafoConColores(GrafoConColores c)
	{
		super(c);
		this.tablaColores = new HashMap<Integer,Integer>(c.tablaColores);
		
	}

	public boolean isValid(Integer vertice, int color)
	{
		if (estaVertice(vertice))
		{
			List<Integer> l = this.listaVerticesAdyacentes(vertice);
			for (Integer i : l)
			{
				if ((this.getColorVertice(i)!=null)&&(this.getColorVertice(i)==color)) return false;
			}
			return true;
		}
		else return false;
	}

	public void setColor(Integer vertice, int color)
	{
		tablaColores.put(vertice,color);
	}

	public void removeColor(Integer i)
	{
		tablaColores.remove(i);
	}

	public Integer getColorVertice(Integer i)
	{
		return tablaColores.get(i);
	}

	public void borrarColores()
	{
		tablaColores.clear();
	}

	public List<Integer> listaVerticesColoreados()
	{
		return new ArrayList<Integer>(tablaColores.keySet());
	}

	/****************************************************************************** 
		Para poder aplicar el algoritmo de coloracion necesito tener los vertices
		almacenados en orden. En primer lugar los que tienen ya un color asignado
		(ya fijo) y a continuacion el resto, a los que el algoritmo les ira dando
		colores hasta dar con una combinación correcta. Si no puede lo restaurara
	******************************************************************************/
	public boolean colorear(int numColores)
	{
		List<Integer> listaVerticesColoreados   = this.listaVerticesColoreados();
		List<Integer> listaVerticesNoColoreados = this.listaVertices();
		listaVerticesNoColoreados.removeAll(listaVerticesColoreados);
		//Los coloco en el orden correcto
		List<Integer> listaVertices = new Vector<Integer>();
		listaVertices.addAll(listaVerticesColoreados);
		listaVertices.addAll(listaVerticesNoColoreados);
		//Proceso a colorear
		int k = listaVerticesColoreados.size();
		boolean b = coloreoConRetroceso(listaVertices,k,numColores);
		//Si no se ha podido colorear el grafo y vuelvo a dejarlo como estaba
		if (!b)
		{
			for (Integer i: listaVerticesNoColoreados) tablaColores.remove(i);
		}

		return b;
	}

	/******************************************************************************
		Devuelve true si al vertice en la posición k puedo asignarle el color color
		sin provocar problemas.
	******************************************************************************/
	private boolean aceptable(List<Integer> listaVertices, int color, int posicion)
	{
		boolean acept=true;
		for (int i=0; i<posicion && acept; i++)
		{
			if (this.sonAdyacentes(listaVertices.get(i),listaVertices.get(posicion)) &&
				this.getColorVertice(listaVertices.get(i))==color)
				acept = false;
		}
		return acept;
	}

	/******************************************************************************
		Supone que los vertices situados en las posiciones 0,...,k-1 de
		listaVertices ya tienen color. Busca un color valido para la posicion k.
	******************************************************************************/
	private boolean coloreoConRetroceso(List<Integer> listaVertices, int k, int numColores)
	{
		if (k>=listaVertices.size()) return true;
		else
		{
			for (int c=1; c<=numColores; c++)
			{
				if (this.aceptable(listaVertices,c,k))
				{
					tablaColores.put(listaVertices.get(k),c);
					boolean b = coloreoConRetroceso(listaVertices, k+1, numColores);
					if (b) return b;
				}
			}
		}
		// he recorrido todas las combinaciones y ninguna es válida, devuelvo falso.
		return false;
	}
}