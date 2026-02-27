package app;

import config.UIConfig;
import model.Persona;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EjemploJListDialog {
    // Modelo de datos de la lista: aqui vive la informacion que luego pinta JList.
    private final DefaultListModel<Persona> modeloLista = new DefaultListModel<>();

    private JList<Persona> listaPersonas;
    private JButton botonVerDetalles;
    private JButton botonEliminarSeleccion;
    private JLabel etiquetaEstado;

    private JLabel valorNombre;
    private JLabel valorDni;
    private JLabel valorCiclo;
    private JLabel valorEmail;

    private int contadorNuevasPersonas = 1;

    public static void main(String[] args) {
        // En Swing, la UI se crea en el Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> {
            UIConfig.aplicarEstilos();
            new EjemploJListDialog().crearYMostrarInterfaz();
        });
    }

    private void crearYMostrarInterfaz() {
        JFrame ventana = new JFrame("UT5.2 Swing - JList + JDialog (Ejemplo Didactico)");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(920, 560);
        ventana.setMinimumSize(new Dimension(860, 500));
        ventana.setLocationRelativeTo(null);

        JPanel contenedor = new JPanel(new BorderLayout(14, 14));
        contenedor.setBorder(new EmptyBorder(16, 16, 16, 16));

        contenedor.add(crearCabecera(), BorderLayout.NORTH);
        contenedor.add(crearZonaCentral(ventana), BorderLayout.CENTER);
        contenedor.add(crearZonaInferior(ventana), BorderLayout.SOUTH);

        cargarDatosIniciales();
        actualizarFichaSeleccion();

        ventana.setContentPane(contenedor);
        ventana.setVisible(true);
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBorder(new EmptyBorder(8, 10, 8, 10));
        cabecera.setBackground(new Color(235, 243, 252));

        JLabel titulo = new JLabel("Ejemplo visual de JList, eventos y JDialog");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(new Color(31, 63, 94));

        JLabel subtitulo = new JLabel(
                "Selecciona una persona y observa como cambian la ficha, los botones y el dialogo modal.");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(57, 71, 86));

        cabecera.add(titulo, BorderLayout.NORTH);
        cabecera.add(subtitulo, BorderLayout.SOUTH);
        return cabecera;
    }

    private JSplitPane crearZonaCentral(JFrame ventana) {
        JPanel panelLista = crearPanelLista(ventana);
        JPanel panelDetalle = crearPanelDetalle();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLista, panelDetalle);
        splitPane.setDividerLocation(460);
        splitPane.setResizeWeight(0.52);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        return splitPane;
    }

    private JPanel crearPanelLista(JFrame ventana) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("1) Lista de alumnado (JList + DefaultListModel)"));

        listaPersonas = new JList<>(modeloLista);
        listaPersonas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPersonas.setFixedCellHeight(56);
        listaPersonas.setCellRenderer(new PersonaCellRenderer());

        // Evento de seleccion: actualiza ficha y estado.
        listaPersonas.addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                actualizarFichaSeleccion();
            }
        });

        // Evento de raton: doble clic para abrir el mismo dialogo que el boton.
        listaPersonas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evento) {
                if (evento.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(evento)) {
                    abrirDialogoDeSeleccion(ventana);
                }
            }
        });

        JLabel ayuda = new JLabel("Tip de clase: prueba clic simple, doble clic y botones para ver eventos.");
        ayuda.setForeground(new Color(88, 97, 106));
        ayuda.setFont(new Font("SansSerif", Font.PLAIN, 12));

        panel.add(new JScrollPane(listaPersonas), BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelDetalle() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("2) Ficha en tiempo real (vista del objeto seleccionado)"));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        valorNombre = new JLabel("-");
        valorDni = new JLabel("-");
        valorCiclo = new JLabel("-");
        valorEmail = new JLabel("-");

        agregarFila(panel, gbc, 0, "Nombre:", valorNombre);
        agregarFila(panel, gbc, 1, "DNI:", valorDni);
        agregarFila(panel, gbc, 2, "Ciclo:", valorCiclo);
        agregarFila(panel, gbc, 3, "Email:", valorEmail);

        return panel;
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, String textoEtiqueta, JLabel valor) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        JLabel etiqueta = new JLabel(textoEtiqueta);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 14));
        etiqueta.setForeground(new Color(47, 56, 64));
        panel.add(etiqueta, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        valor.setFont(new Font("SansSerif", Font.PLAIN, 14));
        valor.setForeground(new Color(34, 65, 103));
        panel.add(valor, gbc);
    }

    private JPanel crearZonaInferior(JFrame ventana) {
        JPanel panel = new JPanel(new BorderLayout(10, 8));

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        JButton botonAgregar = new JButton("Agregar ejemplo");
        botonAgregar.addActionListener(evento -> agregarPersonaDeEjemplo());

        botonEliminarSeleccion = new JButton("Eliminar seleccion");
        botonEliminarSeleccion.addActionListener(evento -> eliminarSeleccion());

        botonVerDetalles = new JButton("Abrir JDialog");
        botonVerDetalles.addActionListener(evento -> abrirDialogoDeSeleccion(ventana));

        JButton botonLimpiar = new JButton("Limpiar seleccion");
        botonLimpiar.addActionListener(evento -> listaPersonas.clearSelection());

        acciones.add(botonAgregar);
        acciones.add(botonEliminarSeleccion);
        acciones.add(botonVerDetalles);
        acciones.add(botonLimpiar);

        etiquetaEstado = new JLabel("Estado: selecciona una persona para empezar.");
        etiquetaEstado.setBorder(new EmptyBorder(4, 8, 4, 8));
        etiquetaEstado.setOpaque(true);
        etiquetaEstado.setBackground(new Color(242, 246, 250));
        etiquetaEstado.setForeground(new Color(58, 73, 88));

        panel.add(acciones, BorderLayout.NORTH);
        panel.add(etiquetaEstado, BorderLayout.SOUTH);
        return panel;
    }

    private void cargarDatosIniciales() {
        modeloLista.addElement(new Persona("Ana Torres", "12345678Z", "1 DAM", "ana.torres@centro.edu"));
        modeloLista.addElement(new Persona("Carlos Ruiz", "87654321X", "1 DAW", "carlos.ruiz@centro.edu"));
        modeloLista.addElement(new Persona("Elena Gomez", "11223344M", "2 DAM", "elena.gomez@centro.edu"));
        modeloLista.addElement(new Persona("Fernando Lara", "55667788N", "2 DAW", "fernando.lara@centro.edu"));
        modeloLista.addElement(new Persona("Maria Soto", "99887766P", "1 DAW", "maria.soto@centro.edu"));
    }

    private void agregarPersonaDeEjemplo() {
        String[] nombresBase = {"Lucia", "David", "Nuria", "Hector", "Paula", "Iker"};
        String[] apellidosBase = {"Martin", "Vega", "Santos", "Prieto", "Ramos", "Iglesias"};
        String[] ciclos = {"1 DAM", "1 DAW", "2 DAM", "2 DAW"};

        int indice = contadorNuevasPersonas - 1;
        String nombre = nombresBase[indice % nombresBase.length] + " "
                + apellidosBase[indice % apellidosBase.length];
        String ciclo = ciclos[indice % ciclos.length];
        String dni = generarDniDemo(contadorNuevasPersonas);
        String email = "alumno" + contadorNuevasPersonas + "@centro.edu";

        Persona nueva = new Persona(nombre, dni, ciclo, email);
        modeloLista.addElement(nueva);
        listaPersonas.setSelectedValue(nueva, true);
        contadorNuevasPersonas++;
        etiquetaEstado.setText("Estado: se ha agregado un nuevo elemento al modelo de la lista.");
    }

    private String generarDniDemo(int numeroSecuencial) {
        int numero = 23000000 + (numeroSecuencial * 91);
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        char letra = letras.charAt(numero % letras.length());
        return String.format("%08d%c", numero, letra);
    }

    private void eliminarSeleccion() {
        int indice = listaPersonas.getSelectedIndex();
        if (indice == -1) {
            etiquetaEstado.setText("Estado: no hay seleccion para eliminar.");
            return;
        }

        Persona eliminada = modeloLista.getElementAt(indice);
        modeloLista.remove(indice);
        etiquetaEstado.setText("Estado: eliminado -> " + eliminada.getNombre());
        actualizarFichaSeleccion();
    }

    private void abrirDialogoDeSeleccion(JFrame ventanaPadre) {
        Persona seleccionada = listaPersonas.getSelectedValue();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(
                    ventanaPadre,
                    "Selecciona una persona antes de abrir el dialogo.",
                    "Sin seleccion",
                    JOptionPane.WARNING_MESSAGE
            );
            etiquetaEstado.setText("Estado: intento de abrir dialogo sin seleccion.");
            return;
        }
        mostrarDialogo(ventanaPadre, seleccionada);
    }

    private void actualizarFichaSeleccion() {
        Persona seleccionada = listaPersonas.getSelectedValue();
        boolean haySeleccion = seleccionada != null;

        botonVerDetalles.setEnabled(haySeleccion);
        botonEliminarSeleccion.setEnabled(haySeleccion);

        if (!haySeleccion) {
            valorNombre.setText("-");
            valorDni.setText("-");
            valorCiclo.setText("-");
            valorEmail.setText("-");
            etiquetaEstado.setText("Estado: seleccion vacia.");
            return;
        }

        valorNombre.setText(seleccionada.getNombre());
        valorDni.setText(seleccionada.getDni());
        valorCiclo.setText(seleccionada.getCiclo());
        valorEmail.setText(seleccionada.getEmail());
        etiquetaEstado.setText("Estado: seleccion activa -> " + seleccionada.getNombre());
    }

    private void mostrarDialogo(JFrame ventana, Persona persona) {
        JDialog dialogo = new JDialog(ventana, "Detalle de " + persona.getNombre(), true);
        dialogo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialogo.setSize(420, 280);
        dialogo.setLocationRelativeTo(ventana);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBorder(new EmptyBorder(12, 14, 6, 14));
        cabecera.setBackground(new Color(225, 236, 248));

        JLabel titulo = new JLabel("JDialog modal: detalle de la seleccion");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setForeground(new Color(29, 60, 91));
        cabecera.add(titulo, BorderLayout.CENTER);

        JPanel contenido = new JPanel(new GridLayout(4, 1, 6, 6));
        contenido.setBorder(new EmptyBorder(12, 14, 8, 14));
        contenido.add(new JLabel("Nombre: " + persona.getNombre()));
        contenido.add(new JLabel("DNI: " + persona.getDni()));
        contenido.add(new JLabel("Ciclo: " + persona.getCiclo()));
        contenido.add(new JLabel("Email: " + persona.getEmail()));

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(evento -> dialogo.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBorder(new EmptyBorder(0, 10, 10, 10));
        panelBoton.add(cerrar);

        dialogo.add(cabecera, BorderLayout.NORTH);
        dialogo.add(contenido, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.getRootPane().setDefaultButton(cerrar);
        dialogo.setVisible(true);
    }

    private static class PersonaCellRenderer extends JPanel implements ListCellRenderer<Persona> {
        private final JLabel etiquetaNombre = new JLabel();
        private final JLabel etiquetaMeta = new JLabel();

        private PersonaCellRenderer() {
            setLayout(new BorderLayout(0, 2));
            setOpaque(true);
            setBorder(new EmptyBorder(8, 10, 8, 10));

            etiquetaNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
            etiquetaMeta.setFont(new Font("SansSerif", Font.PLAIN, 12));

            add(etiquetaNombre, BorderLayout.NORTH);
            add(etiquetaMeta, BorderLayout.SOUTH);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends Persona> list,
                Persona persona,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            etiquetaNombre.setText(persona.getNombre());
            etiquetaMeta.setText(persona.getCiclo() + "  |  DNI: " + persona.getDni());

            Color fondoPar = new Color(250, 252, 255);
            Color fondoImpar = new Color(244, 248, 252);
            Color fondoBase = (index % 2 == 0) ? fondoPar : fondoImpar;

            if (isSelected) {
                setBackground(new Color(67, 126, 188));
                etiquetaNombre.setForeground(Color.WHITE);
                etiquetaMeta.setForeground(new Color(232, 241, 250));
            } else {
                setBackground(fondoBase);
                etiquetaNombre.setForeground(new Color(36, 42, 48));
                etiquetaMeta.setForeground(new Color(89, 98, 108));
            }

            if (cellHasFocus) {
                setBorder(new LineBorder(new Color(33, 98, 169), 1, true));
            } else {
                setBorder(new EmptyBorder(8, 10, 8, 10));
            }

            return this;
        }
    }
}
