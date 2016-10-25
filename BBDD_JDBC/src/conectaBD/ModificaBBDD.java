// Referencias:
//
// Vídeo 204 - JDBC
//

package conectaBD;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ModificaBBDD {

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

		try {

			// 1.- Crear conexión
			Connection miConexion = DriverManager.getConnection(db_url, db_user, db_password);

			// 2. Crear statement
			Statement miStatement = miConexion.createStatement();

			// 3.1.- Ejecutar la instrucción SQL
			String sql1 = "INSERT INTO PRODUCTOS (CODIGOARTICULO, NOMBREARTICULO, PRECIO) VALUES('AR77', 'PANTALON', 25.35)";

			miStatement.executeUpdate(sql1);

			System.out.println("Registro insertado");

			// 3.2.- Ejecutar otra SQL
			String sql2 = "UPDATE PRODUCTOS SET PRECIO=PRECIO*2 WHERE CODIGOARTICULO='AR77'";

			miStatement.executeUpdate(sql2);

			System.out.println("Registros actualizados");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Fallo de conexión a la Base de Datos");
			e.printStackTrace();
		}
	}

}
