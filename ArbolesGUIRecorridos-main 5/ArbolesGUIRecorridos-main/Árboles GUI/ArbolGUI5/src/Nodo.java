class Nodo {
    int x, y;
    String etiqueta;
    Nodo izquierda, central, derecha;

    public Nodo(int x, int y, String etiqueta) {
        this.x = x;
        this.y = y;
        this.etiqueta = etiqueta;
        this.izquierda = null;
        this.central = null;
        this.derecha = null;
    }

    @Override
    public String toString() {
        return "Nodo{" + "etiqueta='" + etiqueta + '\'' + ", izquierda=" + (izquierda != null ? izquierda.etiqueta : "null") + ", central=" + (central != null ? central.etiqueta : "null") + ", derecha=" + (derecha != null ? derecha.etiqueta : "null") + '}';
    }
}