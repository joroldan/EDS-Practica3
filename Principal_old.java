import java.util.Scanner;
import java.util.NoSuchElementException;

public class Principal
{
	public static void main (String[] args)
	{
		SudokuConSolucion sudoku = new SudokuConSolucion(size());
		int i;
		do
		{
			i=menu();
			tratarOpcion(i,sudoku);
		} while (i!=7);

		System.out.println("\n\n\tGracias. Hasta pronto.");
	}

	private static int size()
	{
		Scanner scan = new Scanner(System.in);
		int opt=0;
		do
		{
			System.out.println("Introduzca cuantas celdas de alto/ancho tendra tu Sudoku");
			System.out.println("Recuerde que tiene que ser un cuadrado perfecto");
			try
			{
				opt = scan.nextInt();
			}
			catch (RuntimeException e)
			{
				System.out.println("El valor introducido no es valido, recuerde que ha de ser un cuadrado perfecto");
				opt = 0;
			}
		} while ((opt==0)||(n/(Math.ceil(Math.sqrt(n)))!=Math.sqrt(n)));

		return opt;
	}

	private static int menu()
	{
		Scanner scan = new Scanner(System.in);
		int opt=0;
		do
		{
			System.out.println("Que desea hacer a continuacion? \n\n");
			System.out.println("\t1. Mostrar sudoku");
			System.out.println("\t2. Restaurar sudoku");
			System.out.println("\t3. Ver si aun se puede resolver");
			System.out.println("\t4. Introducir un nuevo numero");
			System.out.println("\t5. Borrar un numero");
			System.out.println("\t6. Introducir numeros iniciales (no disponible)");
			System.out.println("\n\t7. Rendirse, resoverlo y salir");
			System.out.print("\n\n Tu opcion: ");
			try
			{
				opt = scan.nextInt();
			}
			catch (NoSuchElementException e)
			{
				System.out.println("Opcion no valida, era eso un numero?");
				opt = 0;
			}
		} while ((opt<1)||(opt>7));

		return opt;
	}

	private static void tratarOpcion(int i, SudokuConSolucion s)
	{
		System.out.println("\n\n");
		switch(i)
		{
			case 1: s.imprimir();
				break;
			case 2: s.restaurar();
				break;
			case 3: if (s.resoluble()) System.out.println("El sudoku se puede resolver.");
					else System.out.println("El sudoku no se puede resolver. Pruebe a restaurarlo.");
				break;
			case 4: change(s, true);
				break;
			case 5: change(s,false);
				break;
			case 6:	fastRead(s);
				break;
			case 7: s.resolver();
					s.imprimir();
				break;
		}
	}

	private static void change(Sudoku s, boolean o)
	{
		int f,c,n;
		Scanner scan = new Scanner(System.in);
		s.imprimir();
		do
		{
			System.out.print("\nIntroduzca la fila (0-" + s.getSize() + "): ");
			try
			{
				f = scan.nextInt();
			}
			catch (NoSuchElementException e)
			{
				System.out.println("Opcion no valida, era eso un numero?");
				f = 0;
			}
		} while ((f<0)||(f>=s.getSize()));

		do
		{
			System.out.print("\nIntroduzca la columna (0-" + s.getSize() + "): ");
			try
			{
				c = scan.nextInt();
			}
			catch (NoSuchElementException e)
			{
				System.out.println("Opcion no valida, era eso un numero?");
				c = 0;
			}
		} while ((c<0)||(c>=s.getSize()));

		if (o)
		{
			do
			{
				System.out.println("\nIntroduzca el nuevo valor a colocar en (" + f + ", " + c + "): ");
				try
				{
					n = scan.nextInt();
				}
				catch (NoSuchElementException e)
				{
					System.out.println("Opcion no valida, era eso un numero?");
					n = 0;
				}
			} while ((n<1)||(n>s.getSize()));
			s.addNumber(f,c,n);
		}
		else s.removeNumber(f,c);
	}

	private static void fastRead(SudokuConSolucion s)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Leeremos todos los valores iniciales del sudoku, si introduce un nº no valido, pasaremos por alto");
		for (int i=0; i<s.getSize(); i++)
		{
			for (int j=0; j<s.getSize(); j++)
			{
				System.out.print("Introduce el elemento: (" + i + ", " + j + "): ");
				s.darValorInicial(i,j,scan.nextInt());
			}
		}
	}
}