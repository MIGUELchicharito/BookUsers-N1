import javax.swing.*;
import java.util.ArrayList;

public class FormListado extends JFrame {
    private JPanel panelPrincipal;
    private JList<Usuario> lstUsuarios;
    private JButton btnVerDetalle;
    private JButton btnActualizar;
    private JButton btnEliminar;

    private final ArrayList<Usuario> usuarios;
    private final DefaultListModel<Usuario> modeloLista = new DefaultListModel<>();

    public FormListado(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;

        setTitle("BookUsers - Listado · " + FormMenu.ESTUDIANTE);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(null);

        lstUsuarios.setModel(modeloLista);
        cargarLista();

        btnVerDetalle.addActionListener(e -> abrirDetalle());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
    }

    public void cargarLista() {
        modeloLista.clear();
        for (Usuario u : usuarios) {
            modeloLista.addElement(u);
        }
    }

    private Usuario obtenerSeleccionado() {
        int indice = lstUsuarios.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario de la lista primero.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return modeloLista.getElementAt(indice);
    }

    private void abrirDetalle() {
        Usuario seleccionado = obtenerSeleccionado();
        if (seleccionado != null) {
            new FormDetalle(seleccionado).setVisible(true);
        }
    }

    private void actualizarUsuario() {
        Usuario seleccionado = obtenerSeleccionado();
        if (seleccionado != null) {
            new FormRegistro(usuarios, seleccionado, this::cargarLista).setVisible(true);
        }
    }

    private void eliminarUsuario() {
        Usuario seleccionado = obtenerSeleccionado();
        if (seleccionado == null) {
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar a " + seleccionado.getNombreCompleto() + " (ID " + seleccionado.getId() + ")?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.YES_OPTION) {
            usuarios.remove(seleccionado);
            cargarLista();
            JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
        }
    }
}
