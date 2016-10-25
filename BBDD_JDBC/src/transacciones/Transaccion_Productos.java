// Referencias:
//
// Vídeo 221 - JDBC - Transacciones
//
// setAutoCommit(false);
// miConexion.commit();
// miConexion.rollback();

package transacciones;

import java.sql.*;

import javax.swing.JOptionPane;

public class Transaccion_Productos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection miConexion = null;

		try {

			miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebas", "root", "");
			miConexion.setAutoCommit(false);

			Statement miStatement = miConexion.createStatement();

			String instruccionSql_1 = "DELETE FROM PRODUCTOS WHERE PAÍSDEORIGEN='ITALIA';";
			String instruccionSql_2 = "DELETE FROM PRODUCTOS WHERE PRECIO>300;";
			String instruccionSql_3 = "UPDATE PRODUCTOS SET PRECIO=PRECIO*1.15;";

			boolean ejecutar = ejecutar = ejecutar_transaccion();
			if (ejecutar) {
				miStatement.executeUpdate(instruccionSql_1);
				miStatement.executeUpdate(instruccionSql_2);
				miStatement.executeUpdate(instruccionSql_3);
				miConexion.commit();
				System.out.println("Transacción completada");
			} else {
				System.out.println("No se realizó ningún cambio en la base de datos.");
			}

		} catch (Exception e) {

			System.out.println("Error al ejecutar la transacción!!");

			e.printStackTrace();

			try {

				miConexion.rollback();
				System.out.println("rollback() ejecutado.");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("Fallo al ejecutar el rollback().");
				e1.printStackTrace();
			}
		}

	}

	static boolean ejecutar_transaccion() {

		String ejecucion = JOptionPane.showInputDialog("¿Ejecutar transacción?");
		if (ejecucion.equals("Sí"))
			return true;
		else
			return false;

	}

}
