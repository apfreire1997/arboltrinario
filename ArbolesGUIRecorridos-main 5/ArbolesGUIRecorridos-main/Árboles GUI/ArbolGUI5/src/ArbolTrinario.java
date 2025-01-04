import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class ArbolTrinario {
    private Nodo raiz;
    private ArrayList<Nodo> nodos;
    private int numNodos;
    private static final int DISTANCIA_VERTICAL = 50;
    private static final int ESPACIO_INICIAL = 300;

    public ArbolTrinario() {
        raiz = null;
        nodos = new ArrayList<>();
        numNodos = 0;
    }

    public void anadirNodo(Nodo nodo, Nodo padre, String posicion) {
        if (padre == null) {
            if (raiz == null) {
                raiz = nodo; // El nodo es la raíz
                nodos.add(nodo);
                return;
            } else {
                throw new IllegalArgumentException("La raíz ya existe");
            }
        }

        if (posicion == null || (!posicion.equalsIgnoreCase("izquierda") && !posicion.equalsIgnoreCase("central") && !posicion.equalsIgnoreCase("derecha"))) {
            throw new IllegalArgumentException("Posición inválida. Use 'izquierda', 'central' o 'derecha'");
        }

        switch (posicion.toLowerCase()) {
            case "izquierda":
                if (padre.izquierda == null) {
                    padre.izquierda = nodo;
                } else {
                    throw new IllegalArgumentException("Hijo izquierdo ya existe");
                }
                break;
            case "central":
                if (padre.central == null) {
                    padre.central = nodo;
                } else {
                    throw new IllegalArgumentException("Hijo central ya existe");
                }
                break;
            case "derecha":
                if (padre.derecha == null) {
                    padre.derecha = nodo;
                } else {
                    throw new IllegalArgumentException("Hijo derecho ya existe");
                }
                break;
        }
        nodos.add(nodo);
    }

    public void ajustarPosiciones() {
        int anchoTotal = calcularAnchoTotal(raiz);
        recalcularPosiciones(raiz, 0, 0, anchoTotal / 2);
    }

    private int calcularAnchoTotal(Nodo nodo) {
        if (nodo == null) return 0;
        int anchoIzquierdo = calcularAnchoTotal(nodo.izquierda);
        int anchoCentral = calcularAnchoTotal(nodo.central);
        int anchoDerecho = calcularAnchoTotal(nodo.derecha);
        return Math.max(anchoIzquierdo + anchoCentral + anchoDerecho, ESPACIO_INICIAL);
    }

    private void recalcularPosiciones(Nodo nodo, int x, int y, int espacio) {
        if (nodo == null) return;

        nodo.x = x;
        nodo.y = y;

        int nuevoEspacio = espacio / 2;
        if (nodo.izquierda != null) {
            recalcularPosiciones(nodo.izquierda, x - espacio, y + DISTANCIA_VERTICAL, nuevoEspacio);
        }

        if (nodo.central != null) {
            recalcularPosiciones(nodo.central, x, y + DISTANCIA_VERTICAL, nuevoEspacio);
        }

        if (nodo.derecha != null) {
            recalcularPosiciones(nodo.derecha, x + espacio, y + DISTANCIA_VERTICAL, nuevoEspacio);
        }
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    public String getEtiquetaNodoSiguiente() {
        return String.valueOf((char) ('A' + numNodos++));
    }

    // Recorrido en Anchura (BFS)
    public String bfs() {
        if (raiz == null) return "";

        StringBuilder resultado = new StringBuilder();
        Queue<Nodo> queue = new LinkedList<>();
        queue.add(raiz);

        while (!queue.isEmpty()) {
            Nodo nodo = queue.poll();
            resultado.append(nodo.etiqueta).append(" ");
            if (nodo.izquierda != null) queue.add(nodo.izquierda);
            if (nodo.central != null) queue.add(nodo.central);
            if (nodo.derecha != null) queue.add(nodo.derecha);
        }

        return resultado.toString().trim();
    }

    // Recorrido en Profundidad (DFS)
    public String dfs() {
        if (raiz == null) return "";

        StringBuilder resultado = new StringBuilder();
        Stack<Nodo> stack = new Stack<>();
        stack.push(raiz);

        while (!stack.isEmpty()) {
            Nodo nodo = stack.pop();
            resultado.append(nodo.etiqueta).append(" ");
            if (nodo.derecha != null) stack.push(nodo.derecha);
            if (nodo.central != null) stack.push(nodo.central);
            if (nodo.izquierda != null) stack.push(nodo.izquierda);
        }

        return resultado.toString().trim();
    }

    public Object[][] getMatrizAdyacencia() {
        int tam = nodos.size();
        Object[][] matriz = new Object[tam][tam];

        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                matriz[i][j] = 0;
            }
        }

        for (Nodo nodo : nodos) {
            int desdeIndice = nodos.indexOf(nodo);
            if (nodo.izquierda != null) matriz[desdeIndice][nodos.indexOf(nodo.izquierda)] = 1;
            if (nodo.central != null) matriz[desdeIndice][nodos.indexOf(nodo.central)] = 1;
            if (nodo.derecha != null) matriz[desdeIndice][nodos.indexOf(nodo.derecha)] = 1;
        }

        return matriz;
    }
}