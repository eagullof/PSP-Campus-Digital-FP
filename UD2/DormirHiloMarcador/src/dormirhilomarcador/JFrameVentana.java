/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dormirhilomarcador;

/*
 * Cuando ejecutamos una aplicación de ventana basada en un formulario
 * JFrame, la máquina virtual de Java crea tres hilos por defecto:
 *
 *  1. el principal, donde corre el método Main() que inicia la aplicación
 *  2. el conocido como GC (recolector de basura), donde corre el código que se
 *      encarga de optimizar los recursos de memoria
 *  3. el conocido como EDT (hilo despachador de eventos) de la AWT (paquete
 *      abstracto de herramientas de ventana), donde corre el código encargado
 *      de atender las solicitudes de dibujo del contenido de la ventana, y de
 *      atender a los eventos de ratón o teclado provocados por el usuario
 *      (como la ejecución del código programado para cuando el usuario hace
 *      click en un botón)
 *
 * Puesto que el hilo EDT puede llegar a ser el más ocupado de los tres con
 * diferencia, debe de ponerse especial cuidado en no sobrecargarlo demasiado.
 *
 * En esta aplicación queremos realizar una cuenta de 1 a 20, cuyo avance
 * se vaya reflejando en el JPanel que hemos colocado a modo de marcador. La
 * cuenta se pondrá en marcha cuando el usuario haga click en un botón.
 *
 * Obviamente, para reflejar ese avance es necesario repintar el JPanel a cada
 * nuevo valor (lo que se le solicita al hilo EDT -que es el encargado de ello,
 * mediante una llamada al método repaint() del JPanel).
 *
 * El primer botón intenta hacerlo sin poner más hilos en juego. No lo consigue.
 * Y no lo consigue porque precisamente el hilo que recibe las peticiones de
 * pintado, es justo el que las está enviando a cada nuevo valor de la cuenta.
 * Y claro, se satura. Sólo cuando finaliza la cuenta (justo en el 20), puede
 * atender por fin a la solicitud de dibujo del marcador.
 *
 * Por el contrario el segundo y tercer botón delega en un hilo auxiliar, tanto
 * la cuenta
 * como las peticiones de repintado del marcador. Además este hilo auxiliar,
 * en el caso del Botón 3
 * se duerme 1 décima de segundo por cuenta, para que EDT tenga una buen ocasión
 * de atender la petición de dibujo que le acaba de enviar. El resultado es el
 * esperado por fin
 */
import javax.swing.*;
import java.awt.*;

public class JFrameVentana extends javax.swing.JFrame {

    JPanelMarcador marcador;

    public JFrameVentana() {
        initComponents();
        setTitle("Contador de Marcador");
        setSize(450, 350);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 245, 238)); // Fondo en color crema suave

        marcador = new JPanelMarcador();
        marcador.setBorder(BorderFactory.createLineBorder(new Color(255, 160, 122), 3)); // Borde salmón claro
        marcador.setPreferredSize(new Dimension(250, 70));

        // Panel central para el marcador
        JPanel marcadorPanel = new JPanel();
        marcadorPanel.setOpaque(false);
        marcadorPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        marcadorPanel.add(marcador);

        // Panel inferior para los botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setOpaque(false);
        botonesPanel.setLayout(new GridLayout(2, 2, 10, 10));
        botonesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado alrededor de botones

        // Agregar botones redondeados con estilo
        botonesPanel.add(new RoundedButton("Sin hilos auxiliares", new Color(255, 182, 193)));
        botonesPanel.add(new RoundedButton("Con hilo auxiliar", new Color(255, 222, 173)));
        botonesPanel.add(new RoundedButton("Con hilo y espera", new Color(144, 238, 144)));
        botonesPanel.add(new RoundedButton("Borrar marcador", new Color(135, 206, 250)));
        botonesPanel.add(new RoundedButton("Salir", new Color(216, 191, 216)));

        setLayout(new BorderLayout());
        add(marcadorPanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
    }

    private void initComponents() {
        jLabel1 = new JLabel("Marcador de cuenta (1 a 20)", SwingConstants.CENTER);

        // Personalizar la etiqueta de título
        jLabel1.setFont(new Font("Arial", Font.BOLD, 18));
        jLabel1.setForeground(new Color(255, 99, 71)); // Color coral
        add(jLabel1, BorderLayout.NORTH); // Colocar la etiqueta en la parte superior
    }

    // Clase para crear botones redondeados
    private class RoundedButton extends JButton {

        public RoundedButton(String text, Color backgroundColor) {
            super(text);
            setContentAreaFilled(false); // Evita el fondo estándar
            setFocusPainted(false); // Quita el borde de enfoque
            setBackground(backgroundColor); // Color de fondo
            setForeground(Color.WHITE); // Color del texto
            setFont(new Font("Arial", Font.BOLD, 14));
            setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Margen interno
            addActionListener(evt -> accionBoton(this)); // Agregar funcionalidad
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujar fondo redondeado
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Ajusta el redondeo con los últimos dos valores

            // Dibujar texto
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No se necesita borde adicional
        }
    }

    private void accionBoton(JButton boton) {
        switch (boton.getText()) {
            case "Sin hilos auxiliares" -> {
                for (int i = 1; i <= 20; i++) {
                    marcador.valor = i;
                    marcador.repaint();
                }
            }
            case "Con hilo auxiliar" -> new HiloAuxiliar(false, marcador).start();
            case "Con hilo y espera" -> new HiloAuxiliar(true, marcador).start();
            case "Borrar marcador" -> {
                marcador.valor = 0;
                marcador.repaint();
            }
            default -> {
                System.exit(0);
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new JFrameVentana().setVisible(true));
    }

    private JLabel jLabel1;
}
