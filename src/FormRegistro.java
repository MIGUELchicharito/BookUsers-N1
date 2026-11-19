import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class FormRegistro extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<String> cmbTipoId;
    private JTextField txtNumeroId;
    private JTextField txtPrimerNombre;
    private JTextField txtSegundoNombre;
    private JTextField txtPrimerApellido;
    private JTextField txtSegundoApellido;
    private JLabel lblUsername;
    private JPasswordField txtPassword;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JButton btnCargarFoto;
    private JLabel lblFoto;
    private JButton btnGuardar;

    private final ArrayList<Usuario> usuarios;
    private final Usuario usuarioEditar;
    private final Runnable alGuardar;
    private String rutaFotoSeleccionada = "";

    public FormRegistro(ArrayList<Usuario> usuarios) {
        this(usuarios, null, null);
    }

    public FormRegistro(ArrayList<Usuario> usuarios, Usuario usuarioEditar, Runnable alGuardar) {
        this.usuarios = usuarios;
        this.usuarioEditar = usuarioEditar;
        this.alGuardar = alGuardar;

        setTitle((usuarioEditar == null ? "BookUsers - Registro · " : "BookUsers - Actualizar · ") + FormMenu.ESTUDIANTE);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 620);
        setLocationRelativeTo(null);

        cargarTiposDocumento();
        configurarListenerUsername();

        if (usuarioEditar != null) {
            btnGuardar.setText("Guardar cambios");
            cargarDatosUsuario(usuarioEditar);
        }

        btnCargarFoto.addActionListener(e -> seleccionarFoto());
        btnGuardar.addActionListener(e -> guardarUsuario());
    }

    private void cargarTiposDocumento() {
        String[] tipos = {
                "11 - Registro civil de nacimiento",
                "12 - Tarjeta de identidad",
                "13 - Cédula de ciudadanía",
                "21 - Tarjeta de extranjería",
                "22 - Cédula de extranjería",
                "41 - Pasaporte",
                "42 - Documento de identificación extranjero",
                "47 - Permiso Especial de Permanencia (PEP)",
                "91 - NUIP"
        };
        for (String tipo : tipos) {
            cmbTipoId.addItem(tipo);
        }
        cmbTipoId.setSelectedIndex(2);
    }

    private void cargarDatosUsuario(Usuario u) {
        cmbTipoId.setSelectedItem(u.getTipoIdentificacion());
        txtNumeroId.setText(u.getNumeroIdentificacion());
        txtPrimerNombre.setText(u.getPrimerNombre());
        txtSegundoNombre.setText(u.getSegundoNombre());
        txtPrimerApellido.setText(u.getPrimerApellido());
        txtSegundoApellido.setText(u.getSegundoApellido());
        txtPassword.setText(u.getPassword());
        txtTelefono.setText(u.getTelefono());
        txtCorreo.setText(u.getCorreo());

        rutaFotoSeleccionada = u.getRutaFoto() != null ? u.getRutaFoto() : "";
        if (!rutaFotoSeleccionada.isBlank() && new File(rutaFotoSeleccionada).exists()) {
            mostrarImagenEscalada(rutaFotoSeleccionada);
        }
        actualizarUsername();
    }

    private void configurarListenerUsername() {
        DocumentListener listener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { actualizarUsername(); }
            @Override public void removeUpdate(DocumentEvent e) { actualizarUsername(); }
            @Override public void changedUpdate(DocumentEvent e) { actualizarUsername(); }
        };
        txtPrimerNombre.getDocument().addDocumentListener(listener);
        txtPrimerApellido.getDocument().addDocumentListener(listener);
        txtSegundoApellido.getDocument().addDocumentListener(listener);
    }

    private void actualizarUsername() {
        String user = Usuario.generarUsername(
                txtPrimerNombre.getText(),
                txtPrimerApellido.getText(),
                txtSegundoApellido.getText()
        );
        lblUsername.setText(user.isEmpty() ? "(se genera automáticamente)" : user);
    }

    private void seleccionarFoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar foto del usuario");
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (.png, .jpg)", "png", "jpg", "jpeg"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            rutaFotoSeleccionada = archivo.getAbsolutePath();
            mostrarImagenEscalada(rutaFotoSeleccionada);
        }
    }

    private void mostrarImagenEscalada(String ruta) {
        ImageIcon iconoOriginal = new ImageIcon(ruta);
        Image imgEscalada = iconoOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        lblFoto.setIcon(new ImageIcon(imgEscalada));
        lblFoto.setText("");
    }

    private void guardarUsuario() {
        String tipoId = (String) cmbTipoId.getSelectedItem();
        String numeroId = txtNumeroId.getText().trim();
        String primerNombre = txtPrimerNombre.getText().trim();
        String segundoNombre = txtSegundoNombre.getText().trim();
        String primerApellido = txtPrimerApellido.getText().trim();
        String segundoApellido = txtSegundoApellido.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (numeroId.isEmpty() || primerNombre.isEmpty() || primerApellido.isEmpty()
                || password.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor completa todos los campos obligatorios.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean permiteAlfanumerico = tipoId != null && (tipoId.startsWith("41") || tipoId.startsWith("42"));
        if (!permiteAlfanumerico && !numeroId.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El número de identificación solo debe contener dígitos (excepto pasaporte o documento extranjero).", "Identificación inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!correo.contains("@")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico debe contener '@'.", "Correo inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (usuarioEditar != null) {
            usuarioEditar.setTipoIdentificacion(tipoId);
            usuarioEditar.setNumeroIdentificacion(numeroId);
            usuarioEditar.setPrimerNombre(primerNombre);
            usuarioEditar.setSegundoNombre(segundoNombre);
            usuarioEditar.setPrimerApellido(primerApellido);
            usuarioEditar.setSegundoApellido(segundoApellido);
            usuarioEditar.setPassword(password);
            usuarioEditar.setTelefono(telefono);
            usuarioEditar.setCorreo(correo);
            usuarioEditar.setRutaFoto(rutaFotoSeleccionada);

            if (alGuardar != null) {
                alGuardar.run();
            }
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.\nID conservado: " + usuarioEditar.getId() + "\nUsername: " + usuarioEditar.getUsername());
            dispose();
            return;
        }

        int id = FormMenu.generarSiguienteId();
        Usuario nuevo = new Usuario(
                id, tipoId, numeroId,
                primerNombre, segundoNombre,
                primerApellido, segundoApellido,
                password, telefono, correo, rutaFotoSeleccionada
        );
        usuarios.add(nuevo);

        JOptionPane.showMessageDialog(this, "Usuario guardado correctamente.\nID: " + nuevo.getId() + "\nUsername: " + nuevo.getUsername());
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        cmbTipoId.setSelectedIndex(2);
        txtNumeroId.setText("");
        txtPrimerNombre.setText("");
        txtSegundoNombre.setText("");
        txtPrimerApellido.setText("");
        txtSegundoApellido.setText("");
        lblUsername.setText("(se genera automáticamente)");
        txtPassword.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        rutaFotoSeleccionada = "";
        lblFoto.setIcon(null);
        lblFoto.setText("Sin foto");
    }
}