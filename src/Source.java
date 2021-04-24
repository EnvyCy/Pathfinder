//Jan Cichy - gr 1
import java.util.Scanner;
import java.util.LinkedList;
//RecursiveAlgorithm działa na zasadzie rekurencyjnego szukania najkrótszej ścieżki w labiryncie.
//Poruszamy się po planszy do momentu gdy nie znajdziemy się na miejscu końcowym.
//Jeśli nie możemy się dalej ruszyć to się cofamy i szukamy innej ścieżki.
//Jeśli znaleźliśmy ścieżkę sprawdzamy czy jest najkrótsza,
//przypisujemy do wyjściowego napisu listę kroków z najkrótszej ścieżki.
//Algorytm spełnia warunki zadania, gdyż nie zawiera żadnej pętli oraz każdy krok wykonuje się w czasie stałym

//IterativeAlgorithm działa na zasadzie iteracyjnego szukania ścieżki z wykorzystaniem stosu.
//Poruszamy się po planszy do momentu gdy nie znajdziemy się na miejscu końcowym.
//Poruszając się zapisujemy parametry związane z obecną pozycją na stosie parametrów.
//Gdy nie możemy się dalej ruszyć - cofamy się i szukamy innej ścieżki.
//Algorytm spełnia warunki zadania, gdyż posiada wyłącznie jedną pętlę oraz każdy krok wykonuje się w czasie stałym



class RecursiveAlgorithm{
    private static int m ;  //wysokość
    private static int n;   //szerokość
    public static String temp;  //tymczasowy string
    public static String output;    //wyjściowy string

    RecursiveAlgorithm(int height, int width){  //konstruktor
        m = height;
        n = width;
        temp = "";
        output = "";
    }

    public static void Assign(){    //przepisanie tymczasowego stringa do wyjściowego
        output = temp;
    }

    private static boolean FitsInMaze(int X, int Y)     //sprawdza czy współrzędne nie wychodzą poza planszę
    {
        return (X < n && Y < m && X >= 0 && Y >= 0);
    }

    private static boolean CanMove(int maze[][], boolean checked[][], int X, int Y)     //sprawdza czy możemy się ruszyć na dane miejsce
    {
        return !(maze[X][Y] == 1 || checked[X][Y]);     //jeśli nowe miejsce jest ścianą (1) lub zostało już sprawdzone zwracamy fałsz
    }


    public static int Pathfinder(int maze[][], boolean checked[][], int i, int j, int X, int Y, int shortest_distance, int distance) {      //funkcja szukająca najkrótszej ścieżki
        if (i == X && j == Y) {     //jeśli znaleźliśmy koniec
            if (distance < shortest_distance) {     //jeśli jest to najkrótsza ścieżka
                Assign();
                return distance;            //zwracamy długość ścieżki, koniec algorytmu
            }
        }

        checked[i][j] = true;       //zaznaczamy że przeszliśmy aktualne miejsce

        if (FitsInMaze(i, j + 1) && CanMove(maze, checked, i, j + 1)) {     //sprawdzamy czy możemy się ruszyć do góry (North)
            temp += " N";                                                   //jeśli tak - dodajemy na wynik krok "N", ruszamy się w górę
            shortest_distance = Pathfinder(maze, checked, i, j + 1, X, Y, shortest_distance, distance + 1);     //szukamy dalej     (*)
        }


        if (FitsInMaze(i, j - 1) && CanMove(maze, checked, i, j - 1)) {     //sprawdzamy czy możemy się ruszyć w dół (South)
            temp += " S";                                                    //jeśli tak - dodajemy na wynik krok "S", ruszamy się w dół
            shortest_distance = Pathfinder(maze, checked, i, j - 1, X, Y, shortest_distance, distance + 1);     //szukamy dalej
        }


        if (FitsInMaze(i - 1, j) && CanMove(maze, checked, i - 1, j)) {     //sprawdzamy czy możemy się ruszyć w lewo (West)
            temp += " W";                                                   //jeśli tak - dodajemy na wynik krok "W", ruszamy się w lewo
            shortest_distance = Pathfinder(maze, checked, i - 1, j, X, Y, shortest_distance, distance + 1);     //szukamy dalej
        }


        if (FitsInMaze(i + 1, j) && CanMove(maze, checked, i + 1, j)) {     //sprawdzamy czy możemy się ruszyć w prawo (East)
            temp += " E";                                               //jeśli tak - dodajemy na wynik krok "E", ruszamy się w prawo
            shortest_distance = Pathfinder(maze, checked, i + 1, j, X, Y, shortest_distance, distance + 1);     //szukamy dalej
        }

        checked[i][j] = false;      //jeśli nie możemy się ruszyć, zaznaczamy powrót w tablicy

        if(temp.length() != 0 && temp.length() != 1) {
            temp = temp.substring(0, temp.length() - 2);    //usuwamy ostatni krok z wyjścia
        }

        return shortest_distance;       //szukamy ścieżki od miejsca z którego się cofamy (czyli np. jeśli cofamy się z " N" to idziemy od (*)

    }

    void Display(){
        System.out.println(output);
    }   //wyświetl ścieżkę
}

class Params {
    public int[][] path;
    public int shortest_distance, distance, currentX, currentY, past_address;

    public Params(int distance1, int shortest_distance1, int currentX1, int currentY1, int[][] visited1, int past_address1) {
        currentX = currentX1;       //obecna współrzędna X
        currentY = currentY1;       //obecna współrzędna Y
        distance = distance1;       //dystans ścieżki
        shortest_distance = shortest_distance1; //dystans najkrótszej ścieżki
        past_address = past_address1;
        path = visited1;    //tablica sprawdzonych już miejsc
    }
}

class IterativeAlgorithm{
    public char type;   //rodzaj algorytmu
    public int[][] maze;    //plansza
    public int height;      //wysokość planszy
    public int width;   //szerokość planszy
    public String backtrack;    //ścieżka
    
    private boolean CanMove(int path[][], int X, int Y) {   //sprawdza czy można się ruszyć na dane miejsce
        return !(maze[X][Y] == 1 || path[X][Y] != 0);   //jeśli miejsce jest ścianą lub na nim już byliśmy zwróć fałsz
    }
    
    private boolean FitsInMaze(int X, int Y) {         //sprawdza czy dane miejsce znajduje się w planszy
        return (X < width && Y < height && X >= 0 && Y >= 0);
    }

    public IterativeAlgorithm(int[][] b, int h, int w) {    //konstruktor
        type = 'i';
        maze = b;
        height = h;
        width = w;
    }
    
    public void Pathfinder(int begX, int begY, int endX, int endY) {    //iteratywne szukanie ścieźki
        System.out.print(type);                                 //wyświetl 'i'
        int shortest_distance = 2147483647, distance = 0, i, j;
        backtrack = "";     //na początku ścieźka jest pusta
        String output = "";     //wyjście również
        i = begX;           //ustawiamy współrzędne na początek
        j = begY;

        LinkedList<Params> stack = new LinkedList<>(); //gotowy stos z biblioteki
        Params ParamsStack, Temp;   //ParamsStack - stos parametrów
        int[][] path = new int[width][height];      //tworzymy pustą tablicę sprawdzonych miejsc
        int temp_label, label = 1, past_address = label;    //past_address - pamięta wcześniejszy label
        boolean step = false, found = true;

        while (!step) {
            switch(label) {
                case 1:
                    path[i][j] = label;     //zapisujemy 1 na tej pozycji w tablicy sprawdzonych miejsc
                    ParamsStack = new Params(distance, shortest_distance, i, j, path, past_address);    //zapisujemy obecne parametry
                    stack.push(ParamsStack);    //dodaj parametry na stos
                    label = 2;      //idziemy do kroku 2
                    break;

                case 2:
                    ParamsStack = stack.getFirst();     //bierzemy parametry z czubku stosu
                    path[ParamsStack.currentX][ParamsStack.currentY] = 1;   //zapisujemy 1 na tej pozycji w tablicy sprawdzonych miejsc
                    if (ParamsStack.currentX == endX && ParamsStack.currentY == endY) {     //jeśli znajdujemy się na końcu - znaleziono ścieżkę
                        label = 12;                                                     //idziemy do kroku 12
                        System.out.println(output);                                     //wyświetlamy ścieżkę
                    } else                              //w przeciwnym wypadku
                        label = 3;                      //idziemy do kroku 3
                    break;

                case 3:
                    Temp = stack.getFirst();                //Temp = pierwszy element z listy wiązanej
                    if (FitsInMaze(Temp.currentX, Temp.currentY - 1) && CanMove(path, Temp.currentX, Temp.currentY - 1)) {      //sprawdzamy czy możemy się ruszyć w dół (South)
                        output += " S";                                                                                             //jeśli tak- dodajemy do wyniku krok "S", idziemy w dół
                        Params recordPar = new Params(distance + 1, shortest_distance, Temp.currentX, Temp.currentY - 1, path, label);      //zapisujemy obecne parametry
                        stack.push(recordPar);               //dodajemy je do listy zapisanych parametrów
                        label = 2;                  //idziemy do kroku 2
                    } else                      //w przeciwnym wypadku
                        label = 4;                  //idziemy do kroku 4
                    break;

                case 4:
                    Temp = stack.getFirst();        //Temp = pierwszy element z listy wiązanej
                    if (FitsInMaze(Temp.currentX + 1, Temp.currentY) && CanMove(path, Temp.currentX + 1, Temp.currentY)) {      //sprawdzamy czy możemy się ruszyć w prawo (East)
                        output += " E";                                                                                             //jeśli tak- dodajemy do wyniku krok "E", idziemy w prawo
                        Params recordPar = new Params(distance + 1, shortest_distance, Temp.currentX + 1, Temp.currentY, path, label);  //zapisujemy obecne parametry
                        stack.push(recordPar);  //dodajemy je do listy zapisanych parametrów
                        label = 2;              //idziemy do kroku 2
                    } else                  //w przeciwnym wypadku
                        label = 5;          //idziemy do kroku 5
                    break;

                case 5:
                    Temp = stack.getFirst();        //Temp = pierwszy element z listy wiązanej
                    if (FitsInMaze(Temp.currentX, Temp.currentY + 1) && CanMove(path, Temp.currentX, Temp.currentY + 1)) {      //sprawdzamy czy możemy się ruszyć w górę (North)
                        output += " N";                                                                                             //jeśli tak- dodajemy do wyniku krok "E", idziemy w prawo
                        Params recordPar = new Params(distance + 1, shortest_distance, Temp.currentX, Temp.currentY + 1, path, label);  //zapisujemy obecne parametry
                        stack.push(recordPar);      //dodajemy je do listy zapisanych parametrów
                        label = 2;                  //idziemy do kroku 2
                    } else                  //w przeciwnym wypadku
                        label = 6;          //idziemy do kroku 6
                    break;

                case 6:
                    Temp = stack.getFirst();        //Temp = pierwszy element z listy wiązanej
                    if (FitsInMaze(Temp.currentX - 1, Temp.currentY) && CanMove(path, Temp.currentX - 1, Temp.currentY)) {      //sprawdzamy czy możemy się ruszyć w lewo (West)
                        output += " W";                                                                                             //jeśli tak - dodajemy do wyniku krok "W", idziemy w lewo
                        Params recordPar = new Params(distance + 1, shortest_distance, Temp.currentX - 1, Temp.currentY, path, label);  ////zapisujemy obecne parametry
                        stack.push(recordPar);      //dodajemy je do listy zapisanych parametrów
                        label = 2;                  //idziemy do kroku 2
                    } else                          //w przeciwnym wypadku
                        label = 7;                 //idziemy do kroku 7
                    break;

                case 7:                             //nie możemy się nigdzie ruszyć, musimy zawrócić
                    Temp = stack.getFirst();        //Temp = pierwszy element z listy wiązanej
                    path[Temp.currentX][Temp.currentY] = 0;     //zaznaczamy, że miejsce jest niesprawdzone

                    if (output.length() != 0 && output.length() != 1)       //usuwamy ostatni krok z wyjścia
                        output = output.substring(0, output.length() - 2);

                    temp_label = Temp.past_address;     //wracamy się do poprzedniego kroku
                    stack.pop();                //usuwamy element z góry stosu
                    label = temp_label + 1;      //przechodzimy do kolejnego kroku, szukamy ścieżki od miejsca z którego się cofamy

                    if (stack.isEmpty()) {  //jeśli stos jest pusty
                        found = false;      //to nie znaleźliśmy ścieżki
                        label = 12;         //przechodzimy do kroku 12
                    }
                    break;

                case 12:            //koniec algorytmu
                    step = true;
                    break;
            }

        }
        if(!found)                      //jeśli nie znaleźliśmy ścieżki
            System.out.println(" X");       //wypisujemy na wyjście brak ścieżki
    }
}

public class Source{
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int width = scan.nextInt();     //szerokość planszy
        int height = scan.nextInt();        //wysokość planszy
        int maze[][] = new int[width][height];      //plansza
        int typeamount, begX, begY, endX, endY, p;      //typeamount - liczba zapytań
        char type;                                  //rodzaj algorytmu (rekurencyjny lub iteracyjny)


        for(int i = 0; i < height; i++){        //kodowanie planszy
            for(int j = 0; j < width; j++){
                int a = scan.nextInt();
                maze[j][height - i - 1] = a;
            }
        }

        typeamount = scan.nextInt();        //podaj liczbę zapytań
        for(int i = 0; i < typeamount; i++){
            type = scan.next().charAt(0);

            switch(type){       //switch od rodzaju algorytmu
                case 'r': {                 //algorytm rekurencyjny
                    begX = scan.nextInt();      //współrzędna X początku
                    begY = scan.nextInt();      //współrzędna Y początku
                    endX = scan.nextInt();      //współrzędna X końca
                    endY = scan.nextInt();      //współrzędna Y końca

                    boolean [][]checked = new boolean[width][height];   //tablica 2D przechowująca miejsca na których już byliśmy

                    if (begX == endX && begY == endY) {     //gdy początek i koniec są w tym samym miejscu
                        System.out.println('r');            //wypisz 'r'
                        break;                              //i wyjdź z pętli
                    }

                    RecursiveAlgorithm obj = new RecursiveAlgorithm(height, width);     //tworzymy obiekt rekurencyjnego algorymtu
                    p = obj.Pathfinder(maze, checked, begX, begY, endX, endY, 2147483647, 0);   //p - ścieżka
                    if( p != 2147483647){   //jeśli znaleźliśmy ścieżkę
                        System.out.print('r');      //wypisz 'r'
                        obj.Display();              //i wypisz ścieżkę
                    }
                    else System.out.println("r X");     //w przeciwnym wypadku wypisz brak ścieżki
                    break;                              //wyjdź z pętli
                }
                case 'i': {                 //algorytm iteracyjny
                    begX = scan.nextInt();      //współrzędna X początku
                    begY = scan.nextInt();      //współrzędna Y początku
                    endX = scan.nextInt();      //współrzędna X końca
                    endY = scan.nextInt();      //współrzędna Y końca

                    if(begX == endX && begY == endY){   //gdy początek i koniec są w tym samym miejscu
                        System.out.println('i');        //wypisz 'i'
                        break;                          //i wyjdź z pętli
                    }

                    IterativeAlgorithm obj = new IterativeAlgorithm(maze, height, width);   //tworzymy obiekt iteracyjnego algorytmu
                    obj.Pathfinder(begX, begY, endX, endY);                                 //znajdź ścieżkę
                    break;                                                                  //i wyjdź z pętli
                }
            }
        }

    }
}