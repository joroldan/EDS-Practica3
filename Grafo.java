/************************************************************************
  Grafo.java

  Implementacion de un grafo formado por vertices (enteros) y sus aristas

  Jorge Roldan Lopez
************************************************************************/

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Grafo
{
	private Map<Integer,List<Integer>> graf;
	
	public Grafo()
	{
		graf = new HashMap<Integer,List<Integer>>();
	}

	public Grafo (Grafo f)
	{
		this.graf = new HashMap<Integer,List<Integer>>();
		List<Integer> vert = f.listaVertices();
		//Copio vertices
		for (Integer i: vert)
		{
			this.addVertice(i);
		}
		//Copio aristas
		for (Integer i: vert)
		{
			List<Integer> art = f.listaVerticesAdyacentes(i);
			for (Integer j: art)
			{
				this.addArista(i, j);
			}
		}
	}

	public void addVertice(Integer i)
	{
		if (!estaVertice(i)) graf.put(i,new ArrayList<Integer>());
		else System.out.println("Ya existe ese vertica");
	}

	public void addArista(Integer i, Integer j)
	{
		if (estaVertice(i) && estaVertice(j))
		{
			graf.get(i).add(j);
			graf.get(j).add(i);
		}
		else System.out.println("No se ha creado la arista faltaba alguno de los vertices");
	}

	public int numVertices()
	{
		return graf.size();
	}

	public boolean sonAdyacentes(Integer i, Integer j)
	{
		if (estaVertice(i)) return graf.get(i).contains(j);
		else return false;
	}

	public boolean estaVertice(Integer i)
	{
		return graf.containsKey(i);
	}

	public List<Integer> listaVertices()
	{
		return new ArrayList<Integer>(this.graf.keySet());
	}

	public List<Integer> listaVerticesAdyacentes(Integer i)
	{
		return new ArrayList<Integer>(this.graf.get(i));
	}

	public void deleteVertice(Integer i)
	{
		if (estaVertice(i))
		{
			List<Integer> l = this.listaVerticesAdyacentes(i);
			for (Integer v : l)
			{
				this.graf.get(v).remove(i);
			}
			this.graf.remove(i);
		}
	}

	public void deleteArista(Integer i, Integer j)
	{
		if (sonAdyacentes(i,j))
		{
			this.graf.get(i).remove(j);
			this.graf.get(j).remove(i);
		}
	}
}