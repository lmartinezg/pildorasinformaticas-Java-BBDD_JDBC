package conectaBD;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;

public class Aplicacion_Consulta {

	public static void main(String[] args) {
		JFrame mimarco = new Marco_Aplicacion();
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mimarco.setVisible(true);
		
	}

}

class Marco_Aplicacion extends JFrame {

	public Marco_Aplicacion() {

		prepareFrame();

		// Conectar con BBDD
		boolean connected = dbConnect();

		if (connected) {
			// Rellenar valores para los JComboBox
			prepareCombos();
		}

	}

	private void prepareFrame() {
		setTitle("Consulta BBDD");
		setBounds(200, 200, 1024, 600);
		setLayout(new BorderLayout());

		JPanel menus = new JPanel();
		menus.setLayout(new FlowLayout());

		secciones = new JComboBox();
		secciones.setEditable(false);
		secciones.addItem("Todos");

		paises = new JComboBox();
		paises.setEditable(false);
		paises.addItem("Todos");

		resultado = new JTextArea(4, 50);
		resultado.setEditable(false);
		resultado.setFont(new Font("monospaced", Font.PLAIN, resultado.getFont().getSize()));
		add(resultado);

		menus.add(new JLabel("Secciones:"));
		menus.add(secciones);

		menus.add(new JLabel("Paises:"));
		menus.add(paises);

		add(menus, BorderLayout.NORTH);
		add(resultado, BorderLayout.CENTER);

		JButton botonConsulta = new JButton("Consulta");

		botonConsulta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ejecutaConsulta();
			}
		});
		add(botonConsulta, BorderLayout.SOUTH);
	}

	private boolean dbConnect() {
		String db_driver = "jdbc:mysql://";
		String db_host = "localhost";
		String db_port = "3306";
		String db_name = "pruebas";

		// String db_url = "jdbc:mysql://localhost:3306";
		String db_url = db_driver + db_host + ":" + db_port + "/" + db_name;
		String db_user = "root";
		String db_password = "";

		boolean connected = false;
		do {
			try {
				miConexion = DriverManager.getConnection(db_url, db_user, db_password);
				connected = true;
			} catch (SQLException e) {
				int opcion;
				Object[] options = { "Reintentar", "Cancelar" };
				opcion = JOptionPane.showOptionDialog(null, "No se pudo conectar con la Base de Datos",
						"Fallo de conexión", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
						options[0]);
				if (opcion != 0) {
					e.printStackTrace();
					return false;
					// System.exit(0);
				}
			}
		} while (connected == false);
		return true;
	}

	private void prepareCombos() {
		try {

			ResultSet rs;

			selectSecciones = miConexion.prepareStatement(sqlSecciones);
			rs = selectSecciones.executeQuery();
			while (rs.next()) {
				secciones.addItem(rs.getString("SECCION"));
			}
			rs.close();

			selectPaises = miConexion.prepareStatement(sqlPaises);
			rs = selectPaises.executeQuery();
			while (rs.next()) {
				paises.addItem(rs.getString("PAISDEORIGEN"));
			}
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printHeaders(String tipo) {
		String format1 = " %-10s %-30s %-30s %-10s %-11s %-30s\n";
		String s;

		if (tipo != null) {
			if (tipo.equals("HEADER")) {
				s = String.format(format1, "Código", "Descripción", "Sección", "Precio", "Fecha", "Procedencia");
				resultado.append(s);
			}
		}

		s = String.format(format1, "----------", "------------------------------", "------------------------------",
				"----------", "-----------", "------------------------------");
		resultado.append(s);
	}

	// Método para ejecutar la consulta
	private void ejecutaConsulta() {

		DateFormat df = DateFormat.getDateInstance();

		String format2 = " %-10s %-30s %-30s %10.2f %-11s %-30s\n";

		String s;

		ResultSet rs = null;

		String seccion = (String) secciones.getSelectedItem();
		String pais = (String) paises.getSelectedItem();

		try {
			resultado.setText("");
			printHeaders("HEADER");

			if (!seccion.equals("Todos") && pais.equals("Todos")) {
				// Solo se ha seleccionado la sección
				selectDetalle = miConexion.prepareStatement(sqlDetPrefix1);
				selectDetalle.setString(1, seccion);
			} else if (seccion.equals("Todos") && !pais.equals("Todos")) {
				// Solo se ha seleccionado el país
				selectDetalle = miConexion.prepareStatement(sqlDetPrefix2);
				selectDetalle.setString(1, pais);
			} else if (!seccion.equals("Todos") && !pais.equals("Todos")) {
				// Se ha seleccionado la sección y el país
				selectDetalle = miConexion.prepareStatement(sqlDetPrefix3);
				selectDetalle.setString(1, seccion);
				selectDetalle.setString(2, pais);
			} else {
				// No se ha seleccionado no sección ni país
				selectDetalle = miConexion.prepareStatement(sqlDetPrefix4);
			}

			rs = selectDetalle.executeQuery();
			while (rs.next()) {

				String codigo_articulo;
				String nombre_articulo;
				String pais_articulo;
				String seccion_articulo;
				BigDecimal precio_articulo;
				Date fecha_articulo;
				String s_fecha_articulo;

				codigo_articulo = rs.getString("CODIGOARTICULO");
				nombre_articulo = rs.getString("NOMBREARTICULO");
				pais_articulo = rs.getString("PAISDEORIGEN");
				seccion_articulo = rs.getString("SECCION");
				fecha_articulo = rs.getDate("FECHA");
				precio_articulo = rs.getBigDecimal("PRECIO");

				if (fecha_articulo == null) {
					s_fecha_articulo = "null";
				} else {
					s_fecha_articulo = df.format(fecha_articulo);
				}

				s = String.format(format2, codigo_articulo, nombre_articulo, seccion_articulo, precio_articulo,
						s_fecha_articulo, pais_articulo);

				resultado.append(s);

			}
			printHeaders(null);

			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private JComboBox secciones;
	private JComboBox paises;
	private JTextArea resultado;

	private Connection miConexion = null;

	private final String sqlSecciones = "SELECT DISTINCT SECCION FROM PRODUCTOS WHERE SECCION IS NOT NULL ORDER BY 1";
	private final String sqlPaises = "SELECT DISTINCT PAISDEORIGEN FROM PRODUCTOS WHERE PAISDEORIGEN IS NOT NULL ORDER BY 1";
	private final String sqlDetPrefix = "SELECT CODIGOARTICULO, NOMBREARTICULO, SECCION, PRECIO, FECHA, PAISDEORIGEN FROM PRODUCTOS ";
	private final String sqlDetSuffix = " ORDER BY 1";
	private final String sqlDetPrefix1 = sqlDetPrefix + "WHERE SECCION=?" + sqlDetSuffix;
	private final String sqlDetPrefix2 = sqlDetPrefix + "WHERE PAISDEORIGEN=?" + sqlDetSuffix;
	private final String sqlDetPrefix3 = sqlDetPrefix + "WHERE SECCION=? AND PAISDEORIGEN=?" + sqlDetSuffix;
	private final String sqlDetPrefix4 = sqlDetPrefix + sqlDetSuffix;

	private PreparedStatement selectSecciones;
	private PreparedStatement selectPaises;
	private PreparedStatement selectDetalle;

}
