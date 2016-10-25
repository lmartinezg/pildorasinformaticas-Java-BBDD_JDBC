// Referencias:
//
// Vídeo 230 - Metadatos

package metaDatos;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Info_MetaDatos {

	public static void main(String[] args) {
		conecta_BBDD();
		mostrarInfo_BBDD();
		mostrarInfo_Tablas();
		mostrarInfo_Columnas();
	}

	static void conecta_BBDD() {

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
					System.out.println("Fallo de conexión a la base de datos. El usuario canceló el reintento.");
					// e.printStackTrace();
					// return false;
					System.exit(0);
				}
			}
		} while (connected == false);
		// return true;

		System.out.println();

	}

	static void mostrarInfo_BBDD() {

		try {

			// Obtener metadatos
			datosBBDD = miConexion.getMetaData();

			// Imprimir metadatos
			System.out.println("DatabaseProductName:    " + datosBBDD.getDatabaseProductName());
			System.out.println("DatabaseProductVersion: " + datosBBDD.getDatabaseProductVersion());
			System.out.println("DriverName:             " + datosBBDD.getDriverName());
			System.out.println("DriverVersion:          " + datosBBDD.getDriverVersion());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println();

	}

	static void mostrarInfo_Tablas() {

		System.out.println("Lista de tablas");
		System.out.println("---------------");

		String catalog = null;
		String schemaPattern = null;
		// String tableNamePattern = "p%";
		String tableNamePattern = null;
		String[] types = null;
		try {
			rs = datosBBDD.getTables(catalog, schemaPattern, tableNamePattern, types);
			while (rs.next()) {
				System.out.println(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println();
	}

	static void mostrarInfo_Columnas() {

		System.out.println("Lista de columnas de la tabla 'productos'");
		System.out.println("-----------------------------------------");

		String catalog = null;
		String schemaPattern = null;
		String tableNamePattern = "productos";
		// String tableNamePattern = null;
		String columnNamePattern = null;
		try {
			rs = datosBBDD.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
			while (rs.next()) {
				System.out.println(rs.getString("COLUMN_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println();
	}

	static Connection miConexion = null;
	static DatabaseMetaData datosBBDD = null;
	static ResultSet rs = null;

}
