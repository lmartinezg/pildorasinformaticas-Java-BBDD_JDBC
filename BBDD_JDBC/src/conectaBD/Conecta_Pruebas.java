// Referencias:
//
// Vídeo 204 - JDBC
//

package conectaBD;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Conecta_Pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String db_driver = "jdbc:mysql://";
		String db_host = "localhost";
		String db_port = "3306";
		String db_name = "pruebas";

		// String db_url = "jdbc:mysql://localhost:3306";
		String db_url = db_driver + db_host + ":" + db_port + "/" + db_name;
		String db_user = "root";
		String db_password = "";

		String sql1 = "SELECT * FROM PRODUCTOS";

		try {

			// 1.- Crear conexión
			Connection miConexion = DriverManager.getConnection(db_url, db_user, db_password);

			// 2. Crear statement
			Statement miStatement = miConexion.createStatement();

			// 3.- Ejecutar la instrucción SQL
			ResultSet miResultSet = miStatement.executeQuery(sql1);

			// 4.- Recorrer el ResultSet

			miResultSet.first();
			do {

				String codigo_articulo;
				String nombre_articulo;
				BigDecimal precio_articulo;
				String fecha_articulo;

				// Se puede utilizar el nombre de columna o el índice de la
				// misma (1, 2, 3...)
				codigo_articulo = miResultSet.getString("CODIGOARTICULO");
				nombre_articulo = miResultSet.getString("NOMBREARTICULO");
				precio_articulo = miResultSet.getBigDecimal("PRECIO");
				fecha_articulo = miResultSet.getString("FECHA");

				System.out.println(
						codigo_articulo + " " + nombre_articulo + " " + precio_articulo + " " + fecha_articulo);
			} while (miResultSet.next());

			System.out.println("------------------------------------------------------------");

			miResultSet.first();
			do {

				String codigo_articulo;
				String nombre_articulo;
				BigDecimal precio_articulo;
				Date fecha_articulo;

				// Se puede utilizar el nombre de columna o el índice de la
				// misma (1, 2, 3...)
				codigo_articulo = miResultSet.getString("CODIGOARTICULO");
				nombre_articulo = miResultSet.getString("NOMBREARTICULO");
				precio_articulo = miResultSet.getBigDecimal("PRECIO");
				fecha_articulo = miResultSet.getDate("FECHA");

				System.out.println(
						codigo_articulo + " " + nombre_articulo + " " + precio_articulo + " " + fecha_articulo);
			} while (miResultSet.next());
			
			miResultSet.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Fallo de conexión a la Base de Datos");
			e.printStackTrace();
		}
	}

}
