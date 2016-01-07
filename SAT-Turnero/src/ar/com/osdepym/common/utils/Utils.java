package ar.com.osdepym.common.utils;

import java.io.IOException;

import org.apache.log4j.Logger;

public class Utils {
	
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.TURNERO
			+ "-" + Utils.class);
	/**
	 * Apagado del sistema
	 */
	public static void shutdown (){
		try {
			LOGGER.debug("Apagando el sistema");

		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec("shutdown -s -t 10");

		} catch (IOException e) {
			LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

			e.printStackTrace();
		}
		System.exit(0);
	}
	/**
	 * Reinicio del sistema
	 */
	public static void restart(){
		try {
			LOGGER.debug("Reiniciando el sistema");

			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("shutdown -r -t 10");

			} catch (IOException e) {
				LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

				e.printStackTrace();
			}
			System.exit(0);		
		
	}

}
