package ar.com.osdepym.template.common.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.service.TurnoDto;

public class ExportarEstadisticas {
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + ExportarEstadisticas.class);
	/**
	 * Metodo que genera el archivo a exportar
	 * @param turnos
	 * @param nomExpo
	 * @param titulo
	 * @return String
	 */
	public static String exportar(List<TurnoDto> turnos, String nomExpo,
			String titulo) {
		String path = "";
		LOGGER.debug(LoggerVariables.PREPARANDO_EXPORTAR);
		try {

			File archivoXLS = new File(System.getProperty("user.home") + "\\"
					+ nomExpo);

			path = archivoXLS.getPath();
			Workbook libro = new HSSFWorkbook();

			FileOutputStream arch = new FileOutputStream(archivoXLS);

			Sheet hoja = libro.createSheet("Reporte");
			Row fila;
			Font font = libro.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			CellStyle style = libro.createCellStyle();
			CellStyle tablaStyle = libro.createCellStyle();
			CellStyle encabezadostyle = libro.createCellStyle();
			
			encabezadostyle.setFont(font);
			encabezadostyle.setAlignment(CellStyle.ALIGN_LEFT);
			
			Font tablaFont = libro.createFont();
			tablaFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
			tablaStyle.setFont(tablaFont);
			tablaStyle.setAlignment(CellStyle.ALIGN_LEFT);
			
			style.setFont(font);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Date date = new Date();
			path = archivoXLS.getPath();

			Cell celda;
			int i = 7;
			String fecha = sdf.format(date);
			fila = hoja.createRow(0);
			celda = fila.createCell(1);
			celda.setCellValue("OSDEPYM");
			celda.setCellStyle(encabezadostyle);
			celda = fila.createCell(8);
			style.setAlignment(CellStyle.ALIGN_RIGHT);
			celda.setCellStyle(style);
			celda.setCellValue("SISTEMA DE ADMINISTRACION DE TURNOS");
			fila = hoja.createRow(1);
			celda = fila.createCell(8);
			celda.setCellValue(fecha);
			celda.setCellStyle(style);
			
			fila = hoja.createRow(3);
			celda = fila.createCell(0);
			celda.setCellValue(titulo);
			celda.setCellStyle(encabezadostyle);

			fila = hoja.createRow(6);
			celda = fila.createCell(0);
			celda.setCellValue("Fecha Turno");
			celda.setCellStyle(encabezadostyle);
			celda = fila.createCell(1);
			celda.setCellValue("Hora Ticket");
			celda.setCellStyle(encabezadostyle);
			celda = fila.createCell(2);
			celda.setCellValue("Hora Atencion");
			celda.setCellStyle(encabezadostyle);
			celda = fila.createCell(3);
			celda.setCellValue("Hora Fin");
			celda.setCellStyle(encabezadostyle);
			celda = fila.createCell(4);
			celda.setCellValue("Sector");
			celda.setCellStyle(encabezadostyle);
			celda = fila.createCell(5);
			celda.setCellValue("Codigo Sector");
			celda.setCellStyle(encabezadostyle);
			celda = fila.createCell(6);
			celda.setCellValue("Numero Turno");
			celda.setCellStyle(encabezadostyle);
			celda = fila.createCell(7);
			celda.setCellValue("Atendido");
			celda.setCellStyle(encabezadostyle);
			celda = fila.createCell(8);
			celda.setCellValue("Usuario");
			celda.setCellStyle(encabezadostyle);

			for (TurnoDto turno : turnos) {
				fila = hoja.createRow(i);
				celda = fila.createCell(0);
				celda.setCellValue(turno.getFecha_ticket());
				celda.setCellStyle(tablaStyle);
				celda = fila.createCell(1);
				celda.setCellValue(turno.getHoraTurno());
				celda.setCellStyle(tablaStyle);
				celda = fila.createCell(2);
				celda.setCellValue(turno.getHoraAtencion());
				celda.setCellStyle(tablaStyle);
				celda = fila.createCell(3);
				celda.setCellValue(turno.getHoraFin());
				celda.setCellStyle(tablaStyle);
				celda = fila.createCell(4);
				celda.setCellValue(turno.getNomSector());
				celda.setCellStyle(tablaStyle);
				celda = fila.createCell(5);
				celda.setCellValue(turno.getId_cod_sector());
				celda.setCellStyle(tablaStyle);
				celda = fila.createCell(6);
				celda.setCellValue(turno.getNumero_turno());
				celda.setCellStyle(tablaStyle);
				celda = fila.createCell(7);
				celda.setCellValue(turno.getAtendido());
				celda.setCellStyle(tablaStyle);
				celda = fila.createCell(8);
				celda.setCellValue(turno.getNomUsuario());
				celda.setCellStyle(tablaStyle);
				i++;
			}
			libro.write(arch);

			arch.close();
			LOGGER.debug("Exportado exitosamente");

		} catch (FileNotFoundException e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			e.printStackTrace();
		} catch (IOException ie) {
			LOGGER.error(LoggerVariables.ERROR + "-" + ie.getMessage());
			ie.printStackTrace();
		} catch (NullPointerException ex) {
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());
		}
		System.out.println(path);
		return path;
	}

}
