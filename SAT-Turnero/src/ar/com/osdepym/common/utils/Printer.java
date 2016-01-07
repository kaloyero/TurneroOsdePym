package ar.com.osdepym.common.utils;

import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JEditorPane;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;

import org.apache.log4j.Logger;

public class Printer {
	
	public static final String TICKET_URL = "C:/SAT-Florida/SAT-Turnero-print-template/templatePrint.html";  
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.TURNERO+"-"+Printer.class);
	//public static final String TICKET_URL= Printer.class.getClassLoader().getResource("/ar.com.osdepym.common.utils/templatePrint.html").getFile(); 

	/**
	 * Imprime ticket para el cliente con numero de turno y puesto.
	 * @param date
	 * @param hour
	 * @param sector
	 * @param turn
	 * @param nextTurn
	 * @param waitTime
	 * @throws IOException
	 * @throws PrinterException
	 */
	public synchronized static void printTicket(String date,String hour, String sector,String turn, String nextTurn , String waitTime) throws IOException,PrinterException{
		JEditorPane htmlPane;
		try {
			
//			createTicket(date,hour, sector,turn, nextTurn , waitTime);
			PrintWriter writer;
			
			try {
				LOGGER.debug("Preparando impresión");
				//String file= Printer.class.getClassLoader().getResource("/ar.com.osdepym.common.utils/templatePrint.html").getFile(); 
				writer = new PrintWriter(TICKET_URL, "UTF-8");
				writer.println("<html>");
				writer.println("<style>");
				writer.println("body {margin: 0px 0px 0px 0px;text-align: center;font-family: 'Tahoma';font-weight: 300;color: #5d5d5d;}");
				writer.println(".dates{color: #000000;font-weight: 400;font-size: 9px;margin-top: 6px;}");	
				writer.println(".sector-var{color: #000000;font-weight: 600;font-size: 24px;}");
				writer.println(".numero{color: #000000;font-weight: 600;font-size: 16px;line-height: 18px;padding-top: 3px;}");

				writer.println(".numero-var{color: #000000;font-weight: 700;font-size: 60px;}");
				
				writer.println("<img src='osdepym-logo-ticket-BN.png'>");
				writer.println("<div id='ticket'>");
				//writer.println("<div class='dates'>"+date+" | "+hour+" hs</div>");
				writer.println("<div class='dates'>"+date +"hs</div>");
				writer.println("<div class='numero'>Sector</div>");
				writer.println("<div class='sector-var'>"+sector+"</div>");
				writer.println("<div class='numero'>N&uacute;mero</div>");
				writer.println("<div class='numero-var'>"+turn+"</div>");
				writer.println("</div>");
				writer.println("<div id='pie'>");
				writer.println("<div class='dates'>Tiene <strong>"+nextTurn+"</strong> turnos por delante.</div>");
				//writer.println("<div class='dates'>Espera estimada: <strong>"+waitTime+"</strong> minutos</div>");
				writer.println("<div class='dates'>Muchas gracias por aguardar.</div>");
				writer.println("</div>");
				writer.println("</div>");
				writer.println("</div>");
				writer.println("</body>");
				writer.println("</html>");
				
				writer.close();
				
			} catch (FileNotFoundException e) {
				LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

				throw e;
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

				throw e;
			}				
			
			
			PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
			LOGGER.debug("Solicitando servicio de impresión");

			PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
	        attributeSet.add(MediaSizeName.ISO_A7);

			htmlPane= new JEditorPane();
			htmlPane.setPage(new URL("file:///"+TICKET_URL));

			htmlPane.setEditorKit(new HTMLEditorKit() {

				private static final long serialVersionUID = 1L;

						@Override
			            public ViewFactory getViewFactory() {
			                return new HTMLFactory() {
			
			                    @Override
			                    public View create(Element elem) {
			                        View view = super.create(elem);
			                        if (view instanceof ImageView) {
			                            ((ImageView) view).setLoadsSynchronously(true);
			                        }
			                        return view;
			                    }
			                };
			            }
			        });	 
			htmlPane.setPage(new URL("file:///"+TICKET_URL));
//			htmlPane.repaint();
//			htmlPane.setPage(new URL("file:///"+TICKET_URL));
			
			try {
			    Thread.sleep(500);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
	        if (defaultPrintService != null) {
	        	LOGGER.debug("Imprimiendo");

	        	htmlPane.print(null, null, false, defaultPrintService, attributeSet, false);	
	        } else {
	        	System.err.println("No existen impresoras instaladas");
	        	LOGGER.error(LoggerVariables.ERROR+"- No existen impresoras instaladas");

	        	throw new PrinterException("No existen impresoras instaladas");
	        }
						
		
		} catch (IOException e) {
			LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

			throw e;
		} catch (PrinterException e) {
			LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

			throw e;
		}
		
		
	}
	
//	private synchronized static JEditorPane getPrintable() throws MalformedURLException, IOException{
//
//		
//		return htmlPane;
//	}
    
//	private  synchronized static void createTicket(String date,String hour, String sector,String turn, String nextTurn , String waitTime) throws FileNotFoundException, UnsupportedEncodingException{
//
//
//	}
//    
		
    
}
