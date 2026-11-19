import javax.swing.*;

public class FormCreditos extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private JLabel lblCodigo;
    private JLabel lblCorreo;
    private JButton btnCerrar;

    public FormCreditos() {
        setTitle("BookUsers - Créditos · " + FormMenu.ESTUDIANTE);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 260);
        setLocationRelativeTo(null);

        lblNombre.setText("Miguel Ángel Ulloa Ávila");
        lblCodigo.setText("UNP-CAXF-KWRW");
        lblCorreo.setText("ulloaavilamiguelangel@gmail.com");

        btnCerrar.addActionListener(e -> dispose());
    }
}
