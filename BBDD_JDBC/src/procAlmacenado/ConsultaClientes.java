package procAlmacenado;

import java.sql.*;

import javax.swing.JOptionPane;

public class ConsultaClientes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Proceso miProceso = new Proceso();

	}
}

class Proceso {

	public Proceso() {

		boolean connected = dbConnect();

		if (connected) {
			CallableStatement miSentencia;
			try {
				miSentencia = miConexion.prepareCall("{call MUESTRA_CLIENTES}");
				ResultSet rs = miSentencia.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
				}
				rs.close();
				miConexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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

	private Connection miConexion = null;
}
