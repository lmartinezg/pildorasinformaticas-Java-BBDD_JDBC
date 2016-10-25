// Referencias
//
// Vídeo 219 - JDBC - Procedimientos almacenados con parámetros

package procAlmacenado;

import java.sql.*;

import javax.swing.JOptionPane;

public class Actualiza_Productos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Proceso2 miProceso = new Proceso2();

	}
}

class Proceso2 {

	public Proceso2() {

		int nPrecio = Integer.parseInt(JOptionPane.showInputDialog("Introduce precio"));
		String nArticulo = JOptionPane.showInputDialog("Introduce nombre artículo");

		boolean connected = dbConnect();

		if (connected) {
			CallableStatement miSentencia;
			try {
				miSentencia = miConexion.prepareCall("{call ACTUALIZA_PROD(?, ?)}");

				miSentencia.setInt(1, nPrecio);
				miSentencia.setString(2, nArticulo);

				miSentencia.execute();
				System.out.println("Sentencia SQL ejecutada.");

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

	public Connection miConexion = null;
}
