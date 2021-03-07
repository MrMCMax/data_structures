package mrmcmax.data_structures.trees; 

public class BinaryHeap<E extends Comparable<E>> 
    implements PriorityQueue<E> {
        
    protected static final int C_P_D = 11; 
    protected E[] array; 
    protected int size;
  
    /** crea un Heap vacio */
    public BinaryHeap() { this(C_P_D); }
    
    /** crea una Cola de Prioridad (CP) vacia con capacidad inicial n */
    @SuppressWarnings("unchecked")
    public BinaryHeap(int n) { 
        array = (E[]) new Comparable[n];
        size = 0;
    }
  
    /** comprueba si un Heap es vacio en Theta(1) */
    public boolean isEmpty() { return size == 0; }
      
    /** devuelve el minimo de un Heap en Theta(1) */
    public E getMin() { return array[1]; }

    /** inserta e en un Heap */
    public void insert(E e) {
        if (size == array.length - 1) duplicarArray();
        // PASO 1. Buscar la posicion de insercion ordenada de e
        // (a) Preservar la Propiedad Estructural
        int posIns = ++size; 
        
        // (b) Preservar la Propiedad de Orden: reflotar 
        posIns = reflotar(e, posIns); 
        /*
        while (posIns > 1 && e.compareTo(elArray[posIns / 2]) < 0) { 
            elArray[posIns] = elArray[posIns / 2]; 
            posIns = posIns / 2;
        }
        */
        // PASO 2. Insertar e en su posicion de insercion ordenada
        array[posIns] = e;
    }
	
	protected int reflotar(E e, int posIns) {
        while (posIns > 1 && e.compareTo(array[posIns / 2]) < 0) { 
            array[posIns] = array[posIns / 2]; 
            posIns = posIns / 2;
        }
        return posIns;
    }

    @SuppressWarnings("unchecked")
    protected void duplicarArray() {
        E[] nuevo = (E[]) new Comparable[array.length * 2];
        System.arraycopy(array, 1, nuevo, 1, size);
        array = nuevo;
    }  
  
    /** recupera y elimina el minimo de un Heap */
    public E deleteMin() {
        E elMinimo = array[1];
        // PASO 1. Borrar el minimo del Heap
        // (a) Preservar la Propiedad Estructural: 
        //     borrar Por Niveles el minimo
        array[1] = array[size--];
        // (b) Preservar la Propiedad de Orden:
        //     buscar la posicion de insercion ordenada de elArray[1] 
        // PASO 2. Insertar elArray[1] en su posicion ordenada
        hundir(1); 
        return elMinimo;
    }
  
    protected void hundir(int pos) {
        int posActual = pos; 
        E aHundir = array[posActual]; 
        int hijo = posActual * 2; 
        boolean esHeap = false; 
        while (hijo <= size && !esHeap) {
            if (hijo < size && 
                array[hijo + 1].compareTo(array[hijo]) < 0) {
                hijo++;
            }
            if (array[hijo].compareTo(aHundir) < 0) {
                array[posActual] = array[hijo];
                posActual = hijo;  
                hijo = posActual * 2; 
            } 
            else { esHeap = true; }
        }
        array[posActual] = aHundir;
    }

    /** obtiene un String con los datos de una CP ordenados Por Niveles 
     *  y con el formato que se usa en el estandar de Java (entre corchetes
     *  cuadrados y separando cada elemento del anterior mediante una coma 
     *  seguida de un espacio en blanco); si la CP esta vacia el String 
     *  resultado es []
     */
    public String toString() { 
      // NOTA: se usa la clase StringBuilder, en lugar de String, 
      // por motivos de eficiencia
        StringBuilder res = new StringBuilder();
        if (size == 0) return res.append("[]").toString();
        int i = 1;
        res.append("[");
        while (i < size) res.append(array[i++] + ", ");
        res.append(array[i].toString() + "]"); 
        return res.toString();
    }
    
    /** devuelve el numero de hojas de un Heap en Theta(1) */
    public int contarHojas() { 
        return size - (size / 2);
    }
    
    /** devuelve el maximo de un Heap tras talla/2 compareTo */
    public E recuperarMax() { 
        int pos = size / 2 + 1;
        E max = array[pos];
        while (pos <= size) {
            if (array[pos].compareTo(max) > 0) {
                max = array[pos];
            } 
            pos++;
        }
        return max;
    }
    
    public void introducir(E e) {
        if (size == array.length - 1) { duplicarArray(); }
        array[++size] = e;
    }
    
    public void arreglar() { arreglar(1); }
    
    protected void arreglar(int i) {
        if  (i <= size / 2) { //si no es una Hoja
            if (2 * i <= size) { arreglar(2 * i); }
            if (2 * i + 1 <= size) { arreglar(2 * i + 1); } 
            hundir(i); 
        }
    }
    
    /*  Restablece la propiedad de orden de un Heap */ 
    //  hunde Por-Niveles y Descendente los nodos Internos 
    //  de elArray, pues las Hojas ya son Heaps
    public void arreglarIterativo() {
        for (int i = size / 2; i > 0; i--) {
            hundir(i);
        }
    } 

}
