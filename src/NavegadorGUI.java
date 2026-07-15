/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Proyecto - Maydee
 * @author XIOMARA MAYDEE
 */
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;

public class NavegadorGUI extends JFrame {

    private static final Color BG_DARK = new Color(19, 17, 28);
    private static final Color BG_PANEL = new Color(30, 27, 46);
    private static final Color BG_CARD = new Color(38, 35, 53);
    private static final Color ACCENT_VIOLET = new Color(139, 92, 246);
    private static final Color ACCENT_MAGENTA = new Color(236, 72, 153);
    private static final Color ACCENT_CYAN = new Color(6, 182, 212);
    private static final Color TEXT_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_MUTED = new Color(167, 139, 250);
    private static final Color BORDER_COLOR = new Color(59, 50, 83);
    private static final Color TEXT_INACTIVO = new Color(195, 190, 220);

    private HistorialNavegador navegador;

    private JButton btnAtras;
    private JButton btnAdelante;
    private JTextField campoUrl;
    private JButton btnIr;
    private JLabel labelPaginaActual;
    private JLabel labelSubtitulo;
    private JPanel panelContenidoSimulado;
    private DefaultListModel<String> modeloAtras;
    private DefaultListModel<String> modeloAdelante;
    private JList<String> listaAtras;
    private JList<String> listaAdelante;
    private JLabel statusBar;

    public NavegadorGUI() {
        navegador = new HistorialNavegador();
        configurarVentana();
        construirInterfaz();
        actualizarVista();
    }

    private void configurarVentana() {
        setTitle("Simulador de Navegador - Estructura de Pilas LIFO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(950, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_DARK);
    }

    private void construirInterfaz() {
        add(construirBarraSuperior(), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirStatusBar(), BorderLayout.SOUTH);
    }

    private JPanel construirBarraSuperior() {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(BG_DARK);

        JPanel titulo = new JPanel(new BorderLayout());
        titulo.setBackground(BG_DARK);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));

        JPanel izqTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        izqTitulo.setOpaque(false);
        izqTitulo.add(new JLabel(new IconoLogo(26, ACCENT_MAGENTA, ACCENT_VIOLET)));
        JLabel appName = new JLabel("Navegador Web"
                + "");
        appName.setForeground(TEXT_WHITE);
        appName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        izqTitulo.add(appName);
        titulo.add(izqTitulo, BorderLayout.WEST);

        JPanel nav = new JPanel(new BorderLayout(15, 0));
        nav.setBackground(BG_PANEL);
        nav.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)));

        JPanel botonesNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botonesNav.setOpaque(false);
        btnAtras = crearBotonNav("Atrás", true);
        btnAdelante = crearBotonNav("Adelante", false);
        botonesNav.add(btnAtras);
        botonesNav.add(btnAdelante);
        nav.add(botonesNav, BorderLayout.WEST);

        JPanel barraUrl = new JPanel(new BorderLayout(10, 0));
        barraUrl.setBackground(BG_CARD);
        barraUrl.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(6, 15, 6, 8)));

        barraUrl.add(new JLabel(new IconoLupa(18, TEXT_MUTED)), BorderLayout.WEST);

        campoUrl = new JTextField();
        campoUrl.setBackground(BG_CARD);
        campoUrl.setForeground(TEXT_WHITE);
        campoUrl.setCaretColor(TEXT_WHITE);
        campoUrl.setBorder(null);
        campoUrl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        campoUrl.addActionListener(e -> visitarPagina());
        barraUrl.add(campoUrl, BorderLayout.CENTER);

        btnIr = crearBotonAccion("Ir a Página", ACCENT_VIOLET, TEXT_WHITE);
        btnIr.setPreferredSize(new Dimension(120, 36));
        btnIr.addActionListener(e -> visitarPagina());
        barraUrl.add(btnIr, BorderLayout.EAST);

        nav.add(barraUrl, BorderLayout.CENTER);

        contenedor.add(titulo);
        contenedor.add(nav);

        btnAtras.addActionListener(e -> retroceder());
        btnAdelante.addActionListener(e -> avanzar());

        return contenedor;
    }

    private JPanel construirPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 20));

        JPanel pantallaWeb = new JPanel(new BorderLayout());
        pantallaWeb.setBackground(BG_PANEL);
        pantallaWeb.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JPanel cabeceraWeb = new JPanel(new BorderLayout());
        cabeceraWeb.setBackground(BG_CARD);
        cabeceraWeb.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JLabel tagEstado = new JLabel("VISTA SIMULADA DEL NAVEGADOR");
        tagEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tagEstado.setForeground(ACCENT_CYAN);
        cabeceraWeb.add(tagEstado, BorderLayout.WEST);

        pantallaWeb.add(cabeceraWeb, BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setBackground(BG_PANEL);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        labelPaginaActual = new JLabel("Ninguna página cargada", SwingConstants.CENTER);
        labelPaginaActual.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPaginaActual.setFont(new Font("Segoe UI", Font.BOLD, 28));
        labelPaginaActual.setForeground(TEXT_WHITE);

        labelSubtitulo = new JLabel("Escribe una URL o dominio arriba para empezar a navegar", SwingConstants.CENTER);
        labelSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        labelSubtitulo.setForeground(TEXT_MUTED);
        labelSubtitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        panelContenidoSimulado = new JPanel(new BorderLayout());
        panelContenidoSimulado.setBackground(BG_CARD);
        panelContenidoSimulado.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)));
        panelContenidoSimulado.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenidoSimulado.setMaximumSize(new Dimension(750, 350));

        centro.add(Box.createVerticalGlue());
        centro.add(labelPaginaActual);
        centro.add(labelSubtitulo);
        centro.add(panelContenidoSimulado);
        centro.add(Box.createVerticalGlue());

        pantallaWeb.add(centro, BorderLayout.CENTER);

        panel.add(pantallaWeb, BorderLayout.CENTER);
        panel.add(construirPanelLateral(), BorderLayout.EAST);

        return panel;
    }

    private JPanel construirPanelLateral() {
        JPanel lateral = new JPanel();
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setPreferredSize(new Dimension(320, 0));
        lateral.setBackground(BG_DARK);
        lateral.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JPanel tarjetaPilas = new JPanel();
        tarjetaPilas.setLayout(new BoxLayout(tarjetaPilas, BoxLayout.Y_AXIS));
        tarjetaPilas.setBackground(BG_PANEL);
        tarjetaPilas.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(20, 18, 20, 18)));

        JLabel tituloPanel = new JLabel("ESTRUCTURA DE PILAS");
        tituloPanel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tituloPanel.setForeground(TEXT_WHITE);
        tituloPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjetaPilas.add(tituloPanel);
        tarjetaPilas.add(Box.createVerticalStrut(15));

        modeloAtras = new DefaultListModel<>();
        modeloAdelante = new DefaultListModel<>();
        listaAtras = new JList<>(modeloAtras);
        listaAdelante = new JList<>(modeloAdelante);

        tarjetaPilas.add(tituloSeccion("Pila Atrás (LIFO)", ACCENT_MAGENTA));
        tarjetaPilas.add(Box.createVerticalStrut(8));
        tarjetaPilas.add(envolverLista(listaAtras));
        tarjetaPilas.add(Box.createVerticalStrut(18));

        tarjetaPilas.add(tituloSeccion("Pila Adelante (LIFO)", ACCENT_CYAN));
        tarjetaPilas.add(Box.createVerticalStrut(8));
        tarjetaPilas.add(envolverLista(listaAdelante));

        tarjetaPilas.add(Box.createVerticalGlue());
        tarjetaPilas.add(Box.createVerticalStrut(20));

        JButton btnHistorial = crearBotonAccion("Ver Historial Completo", ACCENT_VIOLET, TEXT_WHITE);
        btnHistorial.addActionListener(e -> mostrarHistorialCompleto());

        JButton btnLimpiar = crearBotonAccion("Limpiar Historial", new Color(190, 24, 93), TEXT_WHITE);
        btnLimpiar.addActionListener(e -> limpiarHistorial());

        tarjetaPilas.add(btnHistorial);
        tarjetaPilas.add(Box.createVerticalStrut(12));
        tarjetaPilas.add(btnLimpiar);

        lateral.add(tarjetaPilas);

        return lateral;
    }

    private JLabel tituloSeccion(String texto, Color color) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_MUTED);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, color),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JScrollPane envolverLista(JList<String> lista) {
        lista.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lista.setFixedCellHeight(30);
        lista.setBackground(BG_CARD);
        lista.setForeground(TEXT_WHITE);
        lista.setSelectionBackground(ACCENT_VIOLET);
        lista.setSelectionForeground(TEXT_WHITE);
        lista.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(new LineBorder(BORDER_COLOR, 1));
        scroll.setPreferredSize(new Dimension(280, 140));
        scroll.setMaximumSize(new Dimension(320, 200));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        return scroll;
    }

    private JButton crearBotonAccion(String texto, Color bg, Color fg) {
        JButton boton = new JButton(texto);
        boton.setUI(new BasicButtonUI());
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setMaximumSize(new Dimension(320, 42));
        boton.setPreferredSize(new Dimension(280, 42));
        boton.setBackground(bg);
        boton.setForeground(fg);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JButton crearBotonNav(String texto, boolean esAtras) {
        JButton boton = new JButton(texto);
        boton.setUI(new BasicButtonUI());
        boton.setFocusPainted(false);
        boton.setBackground(BG_CARD);
        boton.setForeground(TEXT_WHITE);
        boton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setIcon(new IconoFlecha(12, esAtras));
        boton.setIconTextGap(8);
        if (esAtras) {
            boton.setHorizontalTextPosition(SwingConstants.LEFT);
        } else {
            boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        }
        return boton;
    }

    private JPanel construirStatusBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        statusBar = new JLabel("Listo para navegar.");
        statusBar.setForeground(TEXT_MUTED);
        statusBar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(statusBar, BorderLayout.WEST);

        JLabel infoAlgoritmos = new JLabel("Algoritmos y Estructura de Datos - Pilas");
        infoAlgoritmos.setForeground(new Color(100, 85, 140));
        infoAlgoritmos.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(infoAlgoritmos, BorderLayout.EAST);

        return panel;
    }

    private void visitarPagina() {
        String url = campoUrl.getText().trim();
        if (url.isEmpty()) {
            statusBar.setText("Por favor, escribe una URL válida.");
            return;
        }
        navegador.visitarPagina(url);
        campoUrl.setText("");
        statusBar.setText("Página visitada: " + url);
        actualizarVista();
    }

    private void retroceder() {
        if (!navegador.puedeRetroceder()) {
            statusBar.setText("No hay páginas anteriores en el historial.");
            return;
        }
        navegador.retroceder();
        statusBar.setText("Retrocediste a: " + navegador.obtenerPaginaActual());
        actualizarVista();
    }

    private void avanzar() {
        if (!navegador.puedeAvanzar()) {
            statusBar.setText("No hay páginas siguientes en el historial.");
            return;
        }
        navegador.avanzar();
        statusBar.setText("Avanzaste a: " + navegador.obtenerPaginaActual());
        actualizarVista();
    }

    private void limpiarHistorial() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas vaciar ambas pilas del historial?",
                "Limpiar Historial", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmacion == JOptionPane.YES_OPTION) {
            navegador.limpiarHistorial();
            statusBar.setText("Historial y pilas limpiados correctamente.");
            actualizarVista();
        }
    }

    private void mostrarHistorialCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='width:320px; font-family:Segoe UI, sans-serif; font-size:13px; padding:10px; background-color:#1E1B2E; color:#FFFFFF;'>");
        sb.append("<h3 style='color:#A78BFA; margin-top:0;'>Estado de las Pilas</h3>");
        
        sb.append("<b style='color:#EC4899;'>Pila Atrás:</b><br>");
        List<String> atras = navegador.obtenerHistorialAtras();
        if (atras.isEmpty()) {
            sb.append("<i style='color:#64558C;'>&nbsp;&nbsp;(Vacío)</i><br>");
        } else {
            for (int i = atras.size() - 1; i >= 0; i--) {
                sb.append("&nbsp;&nbsp;* ").append(atras.get(i)).append("<br>");
            }
        }

        sb.append("<br><b style='color:#10B981;'>Página Actual:</b><br>");
        String actual = navegador.obtenerPaginaActual();
        if (actual != null) {
            sb.append("&nbsp;&nbsp;-> <b>").append(actual).append("</b><br>");
        } else {
            sb.append("<i style='color:#64558C;'>&nbsp;&nbsp;(Sin página)</i><br>");
        }

        sb.append("<br><b style='color:#06B6D4;'>Pila Adelante:</b><br>");
        List<String> adelante = navegador.obtenerHistorialAdelante();
        if (adelante.isEmpty()) {
            sb.append("<i style='color:#64558C;'>&nbsp;&nbsp;(Vacío)</i><br>");
        } else {
            for (String url : adelante) {
                sb.append("&nbsp;&nbsp;* ").append(url).append("<br>");
            }
        }
        sb.append("</body></html>");

        JOptionPane.showMessageDialog(this, new JLabel(sb.toString()),
                "Historial Completo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarVista() {
        String actual = navegador.obtenerPaginaActual();
        List<String> atras = navegador.obtenerHistorialAtras();
        List<String> adelante = navegador.obtenerHistorialAdelante();

        modeloAtras.clear();
        for (int i = atras.size() - 1; i >= 0; i--) {
            modeloAtras.addElement("  " + atras.get(i));
        }

        modeloAdelante.clear();
        for (String url : adelante) {
            modeloAdelante.addElement("  " + url);
        }

        panelContenidoSimulado.removeAll();

        if (actual != null) {
            labelPaginaActual.setText(actual);
            labelSubtitulo.setText("Estado: Conectado y renderizando en memoria");
            renderizarContenidoSimulado(actual);
            panelContenidoSimulado.setVisible(true);
        } else {
            labelPaginaActual.setText("Ninguna página cargada");
            labelSubtitulo.setText("Escribe una URL o dominio arriba para empezar a navegar");
            panelContenidoSimulado.setVisible(false);
        }

        panelContenidoSimulado.revalidate();
        panelContenidoSimulado.repaint();

        btnAtras.setEnabled(navegador.puedeRetroceder());
        btnAdelante.setEnabled(navegador.puedeAvanzar());

        if (btnAtras.isEnabled()) {
            btnAtras.setBackground(ACCENT_VIOLET);
            btnAtras.setForeground(TEXT_WHITE);
        } else {
            btnAtras.setBackground(BG_CARD);
            btnAtras.setForeground(TEXT_INACTIVO);
        }

        if (btnAdelante.isEnabled()) {
            btnAdelante.setBackground(ACCENT_VIOLET);
            btnAdelante.setForeground(TEXT_WHITE);
        } else {
            btnAdelante.setBackground(BG_CARD);
            btnAdelante.setForeground(TEXT_INACTIVO);
        }
    }

    private void renderizarContenidoSimulado(String url) {
        String u = url.toLowerCase();
        JPanel inner = new JPanel(new BorderLayout(15, 15));
        inner.setBackground(BG_CARD);

        if (u.contains("youtube") || u.contains("video")) {
            JPanel videoBox = new JPanel(new GridBagLayout());
            videoBox.setBackground(new Color(15, 15, 20));
            videoBox.setPreferredSize(new Dimension(400, 180));
            videoBox.setBorder(new LineBorder(ACCENT_MAGENTA, 2));
            videoBox.add(new JLabel(new IconoPlay(50, ACCENT_MAGENTA)));
            
            JLabel title = new JLabel("Reproduciendo Video en Streaming...");
            title.setForeground(TEXT_WHITE);
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            
            inner.add(videoBox, BorderLayout.CENTER);
            inner.add(title, BorderLayout.SOUTH);
        } else if (u.contains("google") || u.contains("search") || u.contains("buscar")) {
            JPanel searchBox = new JPanel();
            searchBox.setLayout(new BoxLayout(searchBox, BoxLayout.Y_AXIS));
            searchBox.setBackground(BG_CARD);
            
            JLabel logo = new JLabel("Google Search");
            logo.setFont(new Font("Segoe UI", Font.BOLD, 32));
            logo.setForeground(ACCENT_CYAN);
            logo.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JTextField mockInput = new JTextField(" Algoritmos de Pilas en Java...");
            mockInput.setMaximumSize(new Dimension(450, 40));
            mockInput.setBackground(BG_PANEL);
            mockInput.setForeground(TEXT_WHITE);
            mockInput.setBorder(new LineBorder(ACCENT_VIOLET, 1));
            mockInput.setEditable(false);
            
            searchBox.add(Box.createVerticalStrut(20));
            searchBox.add(logo);
            searchBox.add(Box.createVerticalStrut(20));
            searchBox.add(mockInput);
            inner.add(searchBox, BorderLayout.CENTER);
        } else if (u.contains("github") || u.contains("code") || u.contains("git")) {
            JPanel codeBox = new JPanel(new BorderLayout());
            codeBox.setBackground(new Color(20, 20, 30));
            codeBox.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JTextArea codeText = new JTextArea(
                "public class Pila<T> {\n" +
                "    private NodoPila<T> tope;\n" +
                "    public void apilar(T dato) { ... }\n" +
                "    public T desapilar() { ... }\n" +
                "}"
            );
            codeText.setBackground(new Color(20, 20, 30));
            codeText.setForeground(new Color(52, 211, 153));
            codeText.setFont(new Font("Consolas", Font.PLAIN, 14));
            codeText.setEditable(false);
            
            codeBox.add(new JLabel("Repositorio: SimuladorNavegador / src / Pila.java"), BorderLayout.NORTH);
            codeBox.add(codeText, BorderLayout.CENTER);
            inner.add(codeBox, BorderLayout.CENTER);
        } else {
            JPanel articleBox = new JPanel();
            articleBox.setLayout(new BoxLayout(articleBox, BoxLayout.Y_AXIS));
            articleBox.setBackground(BG_CARD);
            
            JLabel artTitle = new JLabel("Bienvenido al Portal Web: " + url);
            artTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
            artTitle.setForeground(TEXT_WHITE);
            
            JLabel artBody = new JLabel("<html><p style='color:#A78BFA; width:500px; font-size:14px;'>Inicia sesión o regístrate para continuar explorando esta plataforma y acceder a todos los servicios interactivos disponibles.</p></html>");
            
            articleBox.add(artTitle);
            articleBox.add(Box.createVerticalStrut(15));
            articleBox.add(artBody);
            inner.add(articleBox, BorderLayout.CENTER);
        }

        panelContenidoSimulado.add(inner, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            NavegadorGUI ventana = new NavegadorGUI();
            ventana.setVisible(true);
        });
    }

    static class IconoLogo implements Icon {
        private final int w;
        private final Color c1, c2;

        IconoLogo(int w, Color c1, Color c2) {
            this.w = w; this.c1 = c1; this.c2 = c2;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(x, y, c1, x + w, y + w, c2));
            g2.fillRoundRect(x, y, w, w, 10, 10);
            g2.setColor(Color.WHITE);
            g2.fillOval(x + w/4, y + w/4, w/2, w/2);
            g2.dispose();
        }

        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return w; }
    }

    static class IconoLupa implements Icon {
        private final int s;
        private final Color col;

        IconoLupa(int s, Color col) { this.s = s; this.col = col; }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(col);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x + 2, y + 2, s - 8, s - 8);
            g2.drawLine(x + s - 7, y + s - 7, x + s - 2, y + s - 2);
            g2.dispose();
        }

        @Override public int getIconWidth() { return s; }
        @Override public int getIconHeight() { return s; }
    }

    static class IconoFlecha implements Icon {
        private final int s;
        private final boolean izd;

        IconoFlecha(int s, boolean izd) {
            this.s = s; this.izd = izd;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground());
            int[] px = izd ? new int[]{x + s, x, x + s} : new int[]{x, x + s, x};
            int[] py = new int[]{y, y + s/2, y + s};
            g2.fillPolygon(px, py, 3);
            g2.dispose();
        }

        @Override public int getIconWidth() { return s; }
        @Override public int getIconHeight() { return s; }
    }

    static class IconoPlay implements Icon {
        private final int s;
        private final Color col;

        IconoPlay(int s, Color col) { this.s = s; this.col = col; }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(col);
            g2.fillOval(x, y, s, s);
            g2.setColor(Color.WHITE);
            int[] px = new int[]{x + s/3, x + s/3, x + (int)(s*0.75)};
            int[] py = new int[]{y + s/4, y + (int)(s*0.75), y + s/2};
            g2.fillPolygon(px, py, 3);
            g2.dispose();
        }

        @Override public int getIconWidth() { return s; }
        @Override public int getIconHeight() { return s; }
    }
}