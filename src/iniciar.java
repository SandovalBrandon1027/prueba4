import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class iniciar {
    private JPanel Registro;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton borrarButton;
    private JButton actualizarButton;
    private JButton agregarButton;
    private JButton mostrarButton;
    private JButton limpiarFormularioButton;
    private JButton buscarPorCodigoButton;
    private JButton buscarPorNombreButton;
    private JButton buscarPorSignoButton;

    static final String DB_URL = "jdbc:mysql://localhost/registro";
    static final String USER = "root";
    static final String PASS = "root_bas3";
    static final String QUERY = "SELECT * FROM personas WHERE Codigo=? AND Nombre=?";
    static final String DELETE_QUERY = "DELETE FROM personas WHERE Codigo=? AND  Nombre=?";
    static final String INSERT_QUERY = "INSERT INTO personas (codigo,cedula,nombre,fecha_nacimiento,signo_zodiacal ) VALUES (?,?,?,?,?)";

    public iniciar() {

        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = textField1.getText();
                String nombre = textField3.getText();

                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                     PreparedStatement stmt = conn.prepareStatement(QUERY)) {

                    stmt.setString(1, codigo);
                    stmt.setString(2, nombre);

                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        int Codigo = rs.getInt("Codigo");
                        String Cedula = rs.getString("Cedula");
                        String Nombre = rs.getString("Nombre");
                        String Fecha_nacimiento = rs.getString("Fecha_nacimiento");
                        String Zigno = rs.getString("Signo_zodiacal");

                        System.out.println("Datos del usuario:");
                        System.out.println("Código: " + Codigo);
                        System.out.println("Nombre: " + Nombre);
                        System.out.println("Cédula: " + Cedula);
                        System.out.println("Fecha de nacimiento: " + Fecha_nacimiento);
                        System.out.println("Signo del zodiaco: " + Zigno);
                        System.out.println("-------------------------");

                        JOptionPane.showMessageDialog(Registro, "Inicio de sesión exitoso.");
                    } else {
                        JOptionPane.showMessageDialog(Registro, "Credenciales incorrectas. Inténtalo de nuevo.",
                                "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Registro, "Error al intentar conectarse a la base de datos.",
                            "Error de conexión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Codigo = textField1.getText();
                String Nombre = textField3.getText();

                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                     PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {

                    stmt.setString(1, Codigo);
                    stmt.setString(2, Nombre);

                    int deletedRows = stmt.executeUpdate();

                    if (deletedRows > 0) {
                        JOptionPane.showMessageDialog(Registro, "Usuario eliminado exitosamente.");
                        textField1.setText("");
                        textField3.setText("");
                    } else {
                        JOptionPane.showMessageDialog(Registro, "No se encontró ningún usuario para eliminar.",
                                "Error de eliminación", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Registro, "Error al intentar conectarse a la base de datos.",
                            "Error de conexión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Codigo = textField1.getText();
                String Cedula = textField2.getText();
                String Nombre = textField3.getText();
                String Fecha_nacimiento = textField4.getText();
                String signo_zodiacal = textField5.getText();

                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                     PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)) {

                    stmt.setString(1, Codigo);
                    stmt.setString(2, Cedula);
                    stmt.setString(3, Nombre);
                    stmt.setString(4, Fecha_nacimiento);
                    stmt.setString(5, signo_zodiacal);

                    int insertedRows = stmt.executeUpdate();

                    if (insertedRows > 0) {
                        JOptionPane.showMessageDialog(Registro, "Usuario agregado exitosamente.");
                        textField1.setText("");
                        textField2.setText("");
                        textField3.setText("");
                        textField4.setText("");
                        textField5.setText("");
                    } else {
                        JOptionPane.showMessageDialog(Registro, "Error al agregar usuario.",
                                "Error de inserción", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Registro, "Error al intentar conectarse a la base de datos.",
                            "Error de conexión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Codigo = textField1.getText();
                String Nombre = textField3.getText();

                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                     PreparedStatement stmt = conn.prepareStatement("UPDATE personas SET codigo = ? WHERE nombre = ?")) {

                    stmt.setString(1, Codigo);
                    stmt.setString(2, Nombre);

                    int updatedRows = stmt.executeUpdate();

                    if (updatedRows > 0) {
                        JOptionPane.showMessageDialog(Registro, "Datos actualizados exitosamente.");
                        textField1.setText("");
                        textField3.setText("");
                    } else {
                        JOptionPane.showMessageDialog(Registro, "No se encontró ningún usuario para actualizar.",
                                "Error de actualización", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Registro, "Error al intentar conectarse a la base de datos.",
                            "Error de conexión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("iniciar");
        frame.setContentPane(new iniciar().Registro);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
