import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

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

        btnGenerarPdf.addActionListener(e -> generarPdf());
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

    private void generarPdf() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar ficha del usuario en PDF");
        chooser.setFileFilter(new FileNameExtensionFilter("Documentos PDF (*.pdf)", "pdf"));
        chooser.setSelectedFile(new File("usuario_" + usuario.getUsername() + ".pdf"));

        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = chooser.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
            archivo = new File(archivo.getAbsolutePath() + ".pdf");
        }

        Document documento = new Document();
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            PdfWriter.getInstance(documento, fos);
            documento.open();

            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            documento.add(new Paragraph("BookUsers - Ficha de Usuario", fuenteTitulo));
            documento.add(new Paragraph(" "));

            String rutaFoto = usuario.getRutaFoto();
            if (rutaFoto != null && !rutaFoto.isBlank()) {
                try {
                    File archivoFoto = new File(rutaFoto);
                    if (archivoFoto.exists()) {
                        com.lowagie.text.Image fotoPdf = com.lowagie.text.Image.getInstance(rutaFoto);
                        fotoPdf.scaleToFit(120, 120);
                        documento.add(fotoPdf);
                        documento.add(new Paragraph(" "));
                    } else {
                        documento.add(new Paragraph("Foto: no encontrada en disco.", fuenteNormal));
                    }
                } catch (Exception imgEx) {
                    documento.add(new Paragraph("Foto: no se pudo cargar (" + imgEx.getMessage() + ").", fuenteNormal));
                }
            }

            documento.add(new Paragraph("ID: " + usuario.getId(), fuenteNormal));
            documento.add(new Paragraph("Tipo de identificación: " + usuario.getTipoIdentificacion(), fuenteNormal));
            documento.add(new Paragraph("Número de identificación: " + usuario.getNumeroIdentificacion(), fuenteNormal));
            documento.add(new Paragraph("Nombre completo: " + usuario.getNombreCompleto(), fuenteNormal));
            documento.add(new Paragraph("Username: " + usuario.getUsername(), fuenteNormal));
            documento.add(new Paragraph("Teléfono: " + usuario.getTelefono(), fuenteNormal));
            documento.add(new Paragraph("Correo: " + usuario.getCorreo(), fuenteNormal));

            documento.close();
            JOptionPane.showMessageDialog(this, "PDF generado correctamente en:\n" + archivo.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
