import javax.swing.*;
import java.util.ArrayList;

public class FormMenu extends JFrame {
    public static final String ESTUDIANTE = "Miguel Ulloa";

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

        btnRegistrar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Módulo de registro en construcción")
        );

        btnListado.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Módulo de listado en construcción")
        );

        btnCreditos.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Módulo de créditos en construcción")
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormMenu menu = new FormMenu();
            menu.setVisible(true);
        });
    }
}