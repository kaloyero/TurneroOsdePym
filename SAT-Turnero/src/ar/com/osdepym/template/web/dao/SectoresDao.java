package ar.com.osdepym.template.web.dao;

// Turnero
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.RolSectores;

public class SectoresDao {
	

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.TURNERO
			+ "-" + SectoresDao.class);
	
	/**
	 * Obtiene los sectores de la base de datos.
	 * @return ArrayList<RolSectores>
	 */
	public ArrayList<RolSectores> execute() {

		Connection connection = new ConnectionMysql().createConnection();
		ArrayList<RolSectores> rolSectores = new ArrayList<RolSectores>(); // 1-
																			// admin
																			// 2
																			// -
																			// Usuario
																			// 3
																			// -
																			// estadisticas
		Calendar calendar = Calendar.getInstance();
		java.sql.Date startDate = new java.sql.Date(calendar.getTime()
				.getTime());
		try {
			RolSectores sector = new RolSectores();

			LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR);

			String query = "SELECT * from SAT.sector  WHERE habilitado = 'SI'";
			// String query =
			// "SELECT * from STA.usuario  WHERE nom_usuario = ?";
			if (connection!=null){
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setString (1,user );
			// preparedStmt.setString(2, passw);

			// execute the preparedstatement
			ResultSet rs = preparedStmt.executeQuery();

			/*
			 * usuario +
			 * "id_usuario  INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT," +
			 * "nom_usuario   varchar(15) NOT NULL," +
			 * "password   varchar(15) NOT NULL," +
			 * "id_sector   varchar(15) NOT NULL," +
			 * "id_perfil   int(1) NOT NULL," + "id_puesto   int(2) NOT NULL," +
			 * "fecha  timestamp NOT NULL);") ;
			 * 
			 * 
			 * Retornar una instancia de usuario
			 */

			while (rs.next()) {
				sector = new RolSectores();
				sector.setNumeroSector(rs.getInt("id_sector"));
				sector.setNombre(rs.getString("nom_sector"));
				sector.setLetra(rs.getString("cod_sector"));
				sector.setMensaje_error("");
				rolSectores.add(sector);
			}
			System.out.println("\n");

			preparedStmt.close();
			}else{
				sector.setMensaje_error("No hay conexion con la Base de Datos");
				rolSectores.add(sector);
			}
		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());

			e.getMessage();
			e.printStackTrace();
			System.out.println("Error de conexion 0" + e.getMessage());
		} finally {
			try {
				if (connection != null) {
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
					connection.close();
				}
			} catch (SQLException e) {
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				e.getMessage();
				e.printStackTrace();
				System.out.println("Error de conexion 1" + e.getMessage());
			}
		}
			return rolSectores;
		}
	}