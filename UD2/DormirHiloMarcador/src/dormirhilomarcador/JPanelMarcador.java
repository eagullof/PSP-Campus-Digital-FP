/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dormirhilomarcador;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class JPanelMarcador extends JPanel {

    public int valor;

    public JPanelMarcador() {
        valor = 0;
        this.setSize(250, 70);
        this.setFont(new Font("SansSerif", Font.BOLD, 48));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo con un solo color pastel (sin degradado)
        g2d.setColor(new Color(255, 228, 225)); // Color de fondo rosa claro
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Borde redondeado
        g2d.setColor(new Color(255, 160, 122)); // Borde en color salmón suave
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        // Configuración del texto
        g2d.setColor(Color.WHITE); // Texto en blanco
        String strValor = String.valueOf(valor);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int anchuraValor = fontMetrics.stringWidth(strValor);
        int alturaValor = fontMetrics.getAscent();

        // Dibujar el valor en el centro del panel
        int x = (getWidth() - anchuraValor) / 2;
        int y = (getHeight() + alturaValor) / 2 - fontMetrics.getDescent();
        g2d.drawString(strValor, x, y);
    }
}
