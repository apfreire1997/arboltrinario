import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class ArbolGUI {
    private JTable tbMatrizAdyacencia;
    private JTextArea textArea;
    private JTextField txtRaiz;
    private JButton btnAgregarNodo;
    private JButton btnDibujarArbol;
    private JButton btnRecorridoAnchura;
    private JButton btnRecorridoProfundidad;
    private JLabel lblNodo;
    private JLabel lblRaiz;
    private JLabel lblHoja;
    private JComboBox<String> cbIzqDer;
    private JPanel panelArbol;
    private JPanel panelGeneral;
    private JPanel panelDatos;
    private JLabel lblRecorridos;
    private JButton btnMatrizAdyacencia;

    // Cambiar Arbol por ArbolTrinario
    private ArbolTrinario arbol = new ArbolTrinario();
    private ArbolGrafico arbolGrafico = new ArbolGrafico(arbol);
    private DefaultTableModel modeloTabla = new DefaultTableModel();

    public ArbolGUI() {
        // Inicialización de componentes
        panelGeneral = new JPanel(new BorderLayout());
        panelDatos = new JPanel(new GridLayout(3, 2, 5, 5));
        panelArbol = new JPanel();
        tbMatrizAdyacencia = new JTable();
        textArea = new JTextArea(10, 30);
        txtRaiz = new JTextField(15);
        cbIzqDer = new JComboBox<>(new String[]{"izquierda", "central", "derecha"});
        btnAgregarNodo = new JButton("Agregar Nodo");
        btnDibujarArbol = new JButton("Dibujar Árbol");
        btnRecorridoAnchura = new JButton("Recorrido Anchura");
        btnRecorridoProfundidad = new JButton("Recorrido Profundidad");
        btnMatrizAdyacencia = new JButton("Matriz Adyacencia");
        lblNodo = new JLabel("Etiqueta del Nodo Padre:");
        lblRecorridos = new JLabel("Recorridos:");

        // Configuración del área de texto
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Panel de datos
        panelDatos.add(lblNodo);
        panelDatos.add(txtRaiz);
        panelDatos.add(new JLabel("Posición del Hijo:"));
        panelDatos.add(cbIzqDer);
        panelDatos.add(btnAgregarNodo);
        panelDatos.add(btnDibujarArbol);

        // Agregar componentes al panel general
        panelGeneral.add(panelDatos, BorderLayout.NORTH);
        panelGeneral.add(panelArbol, BorderLayout.CENTER);
        panelGeneral.add(scrollPane, BorderLayout.SOUTH);

        // Barra lateral para botones
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 5, 5));
        panelBotones.add(btnRecorridoAnchura);
        panelBotones.add(btnRecorridoProfundidad);
        panelBotones.add(btnMatrizAdyacencia);
        panelGeneral.add(panelBotones, BorderLayout.EAST);

        // Acción de los botones
        btnAgregarNodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String etiqueta = arbol.getEtiquetaNodoSiguiente();
                    Nodo nuevoNodo = new Nodo(0, 0, etiqueta);
                    String etiquetaPadre = txtRaiz.getText().trim();
                    String posicion = cbIzqDer.getSelectedItem().toString(); // Obtener la posición como String

                    Nodo nodoPadre = null;
                    for (Nodo nodo : arbol.getNodos()) {
                        if (nodo.etiqueta.equals(etiquetaPadre)) {
                            nodoPadre = nodo;
                            break;
                        }
                    }
                    arbol.anadirNodo(nuevoNodo, nodoPadre, posicion);
                    imprimirArbol();
                    dibujarArbolEnPanel();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar nodo: " + ex.getMessage());
                }
            }
        });
        btnDibujarArbol.addActionListener(e -> {
            try {
                arbol.ajustarPosiciones(); // Ajusta las posiciones de los nodos
                dibujarArbolEnPanel();     // Dibuja el árbol con las posiciones actualizadas
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al dibujar el árbol: " + ex.getMessage());
            }
        });
        btnRecorridoAnchura.addActionListener(e -> textArea.append("Recorrido en Anchura (BFS): " + arbol.bfs() + "\n"));
        btnRecorridoProfundidad.addActionListener(e -> textArea.append("Recorrido en Profundidad (DFS): " + arbol.dfs() + "\n"));
        btnMatrizAdyacencia.addActionListener(e -> mostrarMatrizAdyacencia());
        btnDibujarArbol.addActionListener(e -> dibujarArbolEnPanel());
    }

    private void dibujarArbolEnPanel() {
        Graphics g = panelArbol.getGraphics();
        if (g != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.clearRect(0, 0, panelArbol.getWidth(), panelArbol.getHeight());
            int x = panelArbol.getWidth() / 2;
            int y = 40;
            arbolGrafico.dibujarArbol(g2d, arbol.getRaiz(), x, y, panelArbol.getWidth() / 4, 50);
        }
    }
    public void dibujarArbol(Graphics2D g2d, Nodo nodo, int x, int y, int dimensionX, int dimensionY) {
        if (nodo != null) {
            // Ajustar el tamaño del nodo (bolas más pequeñas)
            int radio = 10; // Tamaño del radio del nodo
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


    private void imprimirArbol() {
        textArea.setText("Nodos:\n");
        for (Nodo nodo : arbol.getNodos()) {
            textArea.append(nodo.etiqueta + ": " + nodo.toString() + "\n");
        }
    }

    private void mostrarMatrizAdyacencia() {
        Object[][] matriz = arbol.getMatrizAdyacencia();
        String[] nombreColumnas = new String[matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            nombreColumnas[i] = String.valueOf((char) ('A' + i));
        }
        modeloTabla.setDataVector(matriz, nombreColumnas);
        JOptionPane.showMessageDialog(null, new JScrollPane(new JTable(modeloTabla)), "Matriz de Adyacencia", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ArbolGUI");
        frame.setContentPane(new ArbolGUI().panelGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }
}
