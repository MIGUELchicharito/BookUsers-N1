import javax.swing.*;
import java.util.ArrayList;

public class FormListado extends JFrame {
    private JPanel panelPrincipal;
    private JList<Usuario> lstUsuarios;
    private JButton btnVerDetalle;

    private final ArrayList<Usuario> usuarios;
    private final DefaultListModel<Usuario> modeloLista = new DefaultListModel<>();

    public FormListado(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;

        setTitle("BookUsers - Listado · " + FormMenu.ESTUDIANTE);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(480, 420);
        setLocationRelativeTo(null);

        lstUsuarios.setModel(modeloLista);
        cargarLista();

        btnVerDetalle.addActionListener(e -> abrirDetalle());
    }

    public void cargarLista() {
        modeloLista.clear();
        for (Usuario u : usuarios) {
            modeloLista.addElement(u);
        }
    }

    private void abrirDetalle() {
        int indice = lstUsuarios.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario de la lista primero.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuario seleccionado = modeloLista.getElementAt(indice);
        new FormDetalle(seleccionado).setVisible(true);
    }
}
