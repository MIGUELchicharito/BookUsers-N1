import javax.swing.*;
import java.util.ArrayList;

public class FormMenu extends JFrame {
    public static final String ESTUDIANTE = "Miguel Ángel Ulloa Ávila";

    private JPanel panelPrincipal;
    private JButton btnRegistrar;
    private JButton btnListado;
    private JButton btnCreditos;

    private final ArrayList<Usuario> usuarios = new ArrayList<>();
    private static int contadorId = 1;

    public static int generarSiguienteId() {
        return contadorId++;
    }

    public FormMenu() {
        setTitle("BookUsers · " + ESTUDIANTE);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        btnRegistrar.addActionListener(e -> new FormRegistro(usuarios).setVisible(true));
        btnListado.addActionListener(e -> new FormListado(usuarios).setVisible(true));
        btnCreditos.addActionListener(e -> new FormCreditos().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormMenu menu = new FormMenu();
            menu.setVisible(true);
        });
    }
}
