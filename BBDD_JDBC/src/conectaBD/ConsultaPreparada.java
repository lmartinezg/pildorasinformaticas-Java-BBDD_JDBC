// Referencias:
//
// Vídeo 206 - JDBC
//
// Sentencias preparadas

package conectaBD;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.mysql.jdbc.StringUtils;

public class ConsultaPreparada {

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

			// 2.- Preparar la instrucción SQL
			String sql1 = "SELECT * FROM PRODUCTOS WHERE SECCION=? AND PAISDEORIGEN=?";
			PreparedStatement miStatement = miConexion.prepareStatement(sql1);

			// 3.- Establecer los parámetros
			miStatement.setString(1, "FERRETERIA");
			miStatement.setString(2, "ESPAÑA");

			// 4.- Ejecutar consulta
			ResultSet miResultSet = miStatement.executeQuery();

			// 4.- Recorrer el ResultSet
			listar(miResultSet);

			// 5.- Cerrar el ResultSet
			miResultSet.close();

			// Otros criterios (reutilizar el PreparedStatement):

			// 3.- Establecer los parámetros
			miStatement.setString(1, "CONFECCION");
			miStatement.setString(2, "ITALIA");

			// 4.- Ejecutar consulta
			miResultSet = miStatement.executeQuery();

			// 4.- Recorrer el ResultSet
			listar(miResultSet);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Fallo de conexión a la Base de Datos");
			e.printStackTrace();
		}
	}

	public static void listar(ResultSet miResultSet) {

		DateFormat df = DateFormat.getDateInstance();

		System.out.printf("%-10s ", "Código    ");
		System.out.printf("%-30s ", "Descripción                   ");
		System.out.printf("%-10s ", "Precio    ");
		System.out.printf("%-11s ", "Fecha      ");
		System.out.printf("%-30s\n", "Procedencia                   ");

		System.out.printf("%-10s ", "----------");
		System.out.printf("%-30s ", "------------------------------");
		System.out.printf("%-10s ", "----------");
		System.out.printf("%-11s ", "-----------");
		System.out.printf("%-30s\n", "------------------------------");

		try {

			miResultSet.first();
			do {

				String codigo_articulo;
				String nombre_articulo;
				BigDecimal precio_articulo;
				Date fecha_articulo;
				String pais_articulo;

				// Se puede utilizar el nombre de columna o el índice de la
				// misma (1, 2, 3...)
				codigo_articulo = miResultSet.getString("CODIGOARTICULO");
				nombre_articulo = miResultSet.getString("NOMBREARTICULO");
				precio_articulo = miResultSet.getBigDecimal("PRECIO");
				fecha_articulo = miResultSet.getDate("FECHA");
				pais_articulo = miResultSet.getString("PAISDEORIGEN");

				System.out.printf("%-10s ", codigo_articulo);
				System.out.printf("%-30s ", nombre_articulo);
				System.out.printf("%10.2f ", precio_articulo);
				System.out.printf("%11s ", df.format(fecha_articulo));
				System.out.printf("%-30s\n", pais_articulo);

				// System.out.printf("%10s %30s %5.2d %10s %30s\n",
				// codigo_articulo, nombre_articulo, precio_articulo,
				// fecha_articulo, pais_articulo);

				// System.out.println(codigo_articulo + " " + nombre_articulo +
				// " " + precio_articulo + " "
				// + fecha_articulo + " " + pais_articulo);
			} while (miResultSet.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.printf("%-10s ", "----------");
		System.out.printf("%-30s ", "------------------------------");
		System.out.printf("%-10s ", "----------");
		System.out.printf("%-11s ", "-----------");
		System.out.printf("%-30s\n", "------------------------------");
		System.out.println();

	}

}
