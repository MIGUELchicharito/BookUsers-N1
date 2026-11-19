import java.text.Normalizer;

public class Usuario {
    private int id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String username;
    private String password;
    private String telefono;
    private String correo;
    private String rutaFoto;

    public Usuario(int id, String tipoIdentificacion, String numeroIdentificacion,
                   String primerNombre, String segundoNombre,
                   String primerApellido, String segundoApellido,
                   String password, String telefono, String correo, String rutaFoto) {
        this.id = id;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.password = password;
        this.telefono = telefono;
        this.correo = correo;
        this.rutaFoto = rutaFoto;
        this.username = generarUsername(primerNombre, primerApellido, segundoApellido);
    }

    public static String generarUsername(String primerNombre, String primerApellido, String segundoApellido) {
        String pNombre = limpiarTexto(primerNombre);
        String pApellido = limpiarTexto(primerApellido);
        String sApellido = limpiarTexto(segundoApellido);

        String inicial1 = pApellido.isEmpty() ? "" : pApellido.substring(0, 1);
        String inicial2 = sApellido.isEmpty() ? "" : sApellido.substring(0, 1);

        return inicial1 + inicial2 + pNombre;
    }

    private static String limpiarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        String normalizado = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD);
        return normalizado.replaceAll("\\p{M}", "")
                .toLowerCase()
                .replace(" ", "");
    }

    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        if (primerNombre != null && !primerNombre.isBlank()) sb.append(primerNombre.trim());
        if (segundoNombre != null && !segundoNombre.isBlank()) sb.append(" ").append(segundoNombre.trim());
        if (primerApellido != null && !primerApellido.isBlank()) sb.append(" ").append(primerApellido.trim());
        if (segundoApellido != null && !segundoApellido.isBlank()) sb.append(" ").append(segundoApellido.trim());
        return sb.toString();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }

    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
        this.username = generarUsername(this.primerNombre, this.primerApellido, this.segundoApellido);
    }

    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }

    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        this.username = generarUsername(this.primerNombre, this.primerApellido, this.segundoApellido);
    }

    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        this.username = generarUsername(this.primerNombre, this.primerApellido, this.segundoApellido);
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getRutaFoto() { return rutaFoto; }
    public void setRutaFoto(String rutaFoto) { this.rutaFoto = rutaFoto; }

    @Override
    public String toString() {
        return id + " - " + getNombreCompleto() + " (" + username + ")";
    }
}