import java.awt.*;

class ArbolGrafico {
    private ArbolTrinario arbol;

    public ArbolGrafico(ArbolTrinario arbol) {
        this.arbol = arbol;
    }

    public void dibujarArbol(Graphics2D g2d, Nodo nodo, int x, int y, int dimensionX, int dimensionY) {
        if (nodo != null) {
            // Tamaño reducido de los nodos (10 píxeles de radio)
            int radio = 10;
            g2d.fillOval(x - radio, y - radio, radio * 2, radio * 2);
            g2d.drawString(nodo.etiqueta, x - radio / 2, y + radio / 2);

            if (nodo.izquierda != null) {
                g2d.drawLine(x, y, x - dimensionX, y + dimensionY);
                dibujarArbol(g2d, nodo.izquierda, x - dimensionX, y + dimensionY, dimensionX / 2, dimensionY);
            }

            if (nodo.central != null) {
                g2d.drawLine(x, y, x, y + dimensionY);
                dibujarArbol(g2d, nodo.central, x, y + dimensionY, dimensionX / 2, dimensionY);
            }

            if (nodo.derecha != null) {
                g2d.drawLine(x, y, x + dimensionX, y + dimensionY);
                dibujarArbol(g2d, nodo.derecha, x + dimensionX, y + dimensionY, dimensionX / 4, dimensionY);
            }
        }
    }
}
