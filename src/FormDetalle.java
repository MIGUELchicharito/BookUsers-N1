import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FormDetalle extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblFoto;
    private JLabel lblId;
    private JLabel lblTipoId;
    private JLabel lblNumeroId;
    private JLabel lblNombreCompleto;
    private JLabel lblUsername;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JButton btnGenerarPdf;

    private final Usuario usuario;

    public FormDetalle(Usuario usuario) {
        this.usuario = usuario;

        setTitle("BookUsers - Detalle · " + FormMenu.ESTUDIANTE);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(460, 480);
        setLocationRelativeTo(null);

        cargarDatos();

        btnGenerarPdf.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "La generación de PDF se agrega en el siguiente commit.")
        );
    }

    private void cargarDatos() {
        lblId.setText(String.valueOf(usuario.getId()));
        lblTipoId.setText(usuario.getTipoIdentificacion());
        lblNumeroId.setText(usuario.getNumeroIdentificacion());
        lblNombreCompleto.setText(usuario.getNombreCompleto());
        lblUsername.setText(usuario.getUsername());
        lblTelefono.setText(usuario.getTelefono());
        lblCorreo.setText(usuario.getCorreo());

        String ruta = usuario.getRutaFoto();
        if (ruta != null && !ruta.isBlank() && new File(ruta).exists()) {
            ImageIcon icono = new ImageIcon(ruta);
            Image img = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(img));
            lblFoto.setText("");
        } else {
            lblFoto.setIcon(null);
            lblFoto.setText("Sin foto");
        }
    }
}
