package ma.co.marsamaroc.gestion.decomptes.utils;

import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * 
 * @author ZGUIOUAR
 *
 */
public abstract class ConstantesExcel {
	
	// FONT
	public static final String FONT_NAME = "Calibri";
	
	// ALIGNMENT
	public static final HorizontalAlignment CENTER =  HorizontalAlignment.CENTER; 
	public static final HorizontalAlignment LEFT = HorizontalAlignment.LEFT;
	public static final HorizontalAlignment RIGHT = HorizontalAlignment.RIGHT;
	
	// Color
	public static final String FOOTER_TAB_GRIS = "#e5e5e3";
	public static final String HEADER_TAB_BRUNO = "#d1c9a9";
	
	// FORMAT NUMBER
	public static final String FORMAT_CURRENCY = "#,##0.00;\\-#,##0.00";
	
	// URL IMG
	public static final String URL_LOGO_IMG = "resources\\img\\logoMarsaMaoc.png";
	
	// DECOMPTE
	public static final String DECOMPE_TITRE_SHEET = "Decompte";
	public static final String DECOMPE_PROVISOIRE_TITRE_SHEET = "Decompte provisoire";
	public static final String DECOMPE_PARTIEL_TITRE_SHEET = "Decompte definitif dartiel";
	public static final String DECOMPE_DEFINITIF_TITRE_SHEET = "Decompte definitif";
	
	// ATTACHEMENT
	public static final String ATTACHEMENT_VALORISE_TITRE_SHEET = "Attachement valorisé";
	public static final String ATTACHEMENT_NON_VALORISE_TITRE_SHEET = "Attachement non valorisé";
	
	
	// IMPRESSION
	public static final String LIB_HEADER_FOOTER_PAGE = "Page ";
	
	/***
	 * 
	 * @param sheet
	 */
	public static void headerFooterPageNum(XSSFSheet sheet, int fontSize){
		Footer footer = sheet.getFooter();
	    footer.setRight(HSSFFooter.font(FONT_NAME, "Italic")+ HSSFFooter.fontSize((short) fontSize) 
	    				+LIB_HEADER_FOOTER_PAGE + HeaderFooter.page() + " / " + HeaderFooter.numPages() );
	}
}
