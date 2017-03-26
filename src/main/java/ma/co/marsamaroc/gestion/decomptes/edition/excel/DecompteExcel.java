package ma.co.marsamaroc.gestion.decomptes.edition.excel;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ma.co.marsamaroc.gestion.decomptes.dto.BordereauPrixDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.PrintDecompteDTO;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.ConstantesExcel;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

public class DecompteExcel {

	private PrintDecompteDTO printDecompte = new PrintDecompteDTO();

	public DecompteExcel(PrintDecompteDTO printDecompteDTO){
		this.printDecompte = printDecompteDTO; 
	}
	
	/**
	 * Décompte Provisoire
	 */
	public String printDecompteProviore(String FILE_NAME){
		Double prixUnitaire,qte,montant;
		Integer refRow;
		Integer refCell,refCellLeft,refCellRight;
		String cellValue;
		try {
		refRow = 5;	
		refCell = 0;
		refCellLeft = refCell;
		refCellRight = refCellLeft+17;
		
		XSSFWorkbook workbook = new XSSFWorkbook ();
		XSSFSheet sheet = workbook.createSheet(ConstantesExcel.DECOMPE_PROVISOIRE_TITRE_SHEET);
		//set paper size
		sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
		sheet.setZoom(50);
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setFitWidth((short)1);
		sheet.getPrintSetup().setFitHeight((short)0);
		sheet.setRepeatingRows(CellRangeAddress.valueOf("$1:$16"));
		ConstantesExcel.headerFooterPageNum(sheet, 22);
		sheet.setMargin(Sheet.RightMargin, 0.3 /* inches */ );
		sheet.setMargin(Sheet.LeftMargin, 0.3);
		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String imgPath =servletContext.getRealPath("/")+ConstantesExcel.URL_LOGO_IMG;
		// FileInputStream obtains input bytes from the image file
		InputStream inputStream = new FileInputStream(imgPath);
		// Get the contents of an InputStream as a byte[].
		byte[] bytes = IOUtils.toByteArray(inputStream);
		// Adds a picture to the workbook
		int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		// close the input stream
		inputStream.close();
		// Returns an object that handles instantiating concrete classes
		CreationHelper helper = workbook.getCreationHelper();
		// Creates the top-level drawing patriarch.
		Drawing drawing = sheet.createDrawingPatriarch();
		// Create an anchor that is attached to the worksheet
		ClientAnchor anchor = helper.createClientAnchor();
		// create an anchor with upper left cell _and_ bottom right cell
		anchor.setCol1(refCellLeft);
		anchor.setRow1(refRow);
		anchor.setCol2(refCellLeft+6);
		anchor.setRow2(refRow+9);
		// Creates a picture
		drawing.createPicture(anchor, pictureIdx);
		// IMAGE END
		
		refRow+=3;
		//Titre Document
		XSSFCellStyle cellTitleStyle = getCellStyle(workbook, true, ConstantesExcel.CENTER, 0, 0, 0, 0,"TITLE");
		cellValue = "DECOMPTE PROVISOIRE N°:"+printDecompte.getDecompte().getNumDecompte();
		CellManufacture(cellValue,refRow,refRow+4,refCellLeft+6,refCellLeft+20,cellTitleStyle,sheet);
		//Devise Marché
		XSSFCellStyle cellSubTitleStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"SUB_TITLE");
		cellValue = printDecompte.getMarche().getDeviseStr();
		CellManufacture(cellValue,refRow+5,refRow+6,refCellLeft+13,refCellLeft+14,cellSubTitleStyle,sheet);
		refRow+=8;
		//NumMarche
		XSSFCellStyle cellNumMarcheStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 1, 0,"NAME");
		CellManufacture("N° DU MARCHE ",refRow,refRow,refCellLeft,refCellLeft+6,cellNumMarcheStyle,sheet);
		XSSFCellStyle cellNumMarcheValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 0, 1, 0,"VAL");
		cellValue = printDecompte.getMarche().getNumMarche();
		CellManufacture(cellValue,refRow,refRow,refCellLeft+7,refCellLeft+16,cellNumMarcheValStyle,sheet);
		//Date Commencement
		XSSFCellStyle cellDateCommencementStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 1, 0,"NAME");
		CellManufacture("DATE DE COMMENCEMENT",refRow,refRow,refCellRight,refCellRight+7,cellDateCommencementStyle,sheet);
		XSSFCellStyle cellDateCommencementVALStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 1, 0,"VAL");
		cellValue = printDecompte.getMarche().getDateDemarageStr();
		CellManufacture(cellValue,refRow,refRow,refCellRight+8,refCellRight+11,cellDateCommencementVALStyle,sheet);
		refRow++;
		//Montant Marche
		XSSFCellStyle cellMontantMarcheStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 0, 0,"NAME");
		CellManufacture("MONTANT DU MARCHE TTC",refRow,refRow,refCellLeft,refCellLeft+6,cellMontantMarcheStyle,sheet);
		XSSFCellStyle cellMontantMarcheValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 0, 0, 0,"VAL");
		cellValue = printDecompte.getMarche().getMontantStr();
		CellManufacture(cellValue,refRow,refRow,refCellLeft+7,refCellLeft+16,cellMontantMarcheValStyle,sheet);
		//Delai D'Execution
		XSSFCellStyle cellDelaiExecutionStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"NAME");
		CellManufacture("DELAI D'EXECUTION",refRow,refRow,refCellRight,refCellRight+7,cellDelaiExecutionStyle,sheet);
		XSSFCellStyle cellDelaiExecutionVALStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 0, 0,"VAL");
		cellValue = printDecompte.getMarche().getDelaiExecution();
		CellManufacture(cellValue,refRow,refRow,refCellRight+8,refCellRight+11,cellDelaiExecutionVALStyle,sheet);
		refRow++;
		//Cell Vide
		XSSFCellStyle cellVideStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 0, 0,"NAME");
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+6,cellVideStyle,sheet);
		XSSFCellStyle cellVideValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 0, 0, 0,"VAL");
		CellManufacture("",refRow,refRow,refCellLeft+7,refCellLeft+16,cellVideValStyle,sheet);
		//Budget
		XSSFCellStyle cellBudgetStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"NAME");
		CellManufacture("BUDGET",refRow,refRow,refCellRight,refCellRight+7,cellBudgetStyle,sheet);
		XSSFCellStyle cellBudgetVALStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 0, 0,"VAL");
		cellValue = printDecompte.getMarche().getBudget();
		CellManufacture(cellValue,refRow,refRow,refCellRight+8,refCellRight+11,cellBudgetVALStyle,sheet);
		refRow++;
		//Objet Marche
		CellManufacture("OBJET DU MARCHE",refRow,refRow,refCellLeft,refCellLeft+6,cellVideStyle,sheet);
		CellManufacture("",refRow,refRow,refCellLeft+7,refCellLeft+16,cellVideValStyle,sheet);
		//CHAP.// ART.// PARAG.
		CellManufacture("CHAP.// ART.// PARAG.",refRow,refRow,refCellRight,refCellRight+7,cellBudgetStyle,sheet);
		CellManufacture("",refRow,refRow,refCellRight+8,refCellRight+11,cellBudgetVALStyle,sheet);
		refRow++;
		//Fournisseur
		CellManufacture("ENTREPRENEUR",refRow,refRow,refCellLeft,refCellLeft+6,cellVideStyle,sheet);
		cellValue = printDecompte.getMarche().getLibFournisseur();
		CellManufacture(cellValue,refRow,refRow,refCellLeft+7,refCellLeft+16,cellVideValStyle,sheet);
		//MONTANT RESTANT A PAYER
		XSSFCellStyle cellMontantAPayerStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 1,"NAME");
		CellManufacture("MONTANT RESTANT A PAYER",refRow,refRow+1,refCellRight,refCellRight+7,cellMontantAPayerStyle,sheet);
		XSSFCellStyle cellMontantAPayerValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 0, 1,"VAL");
		CellManufacture("",refRow,refRow+1,refCellRight+8,refCellRight+11,cellMontantAPayerValStyle,sheet);				
		refRow++;
		//NANTI AU PROFIT DE
		XSSFCellStyle cellNantiStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 0, 1,"NAME");
		CellManufacture("NANTI AU PROFIT DE",refRow,refRow,refCellLeft,refCellLeft+6,cellNantiStyle,sheet);
		XSSFCellStyle cellNantiValStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 1,"VAL");
		CellManufacture("",refRow,refRow,refCellLeft+7,refCellLeft+16,cellNantiValStyle,sheet);
		refRow+=2;
		//NET DU DECOMPTE  TTC
		XSSFCellStyle cellNetDecompteTtcStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 1, 1, 1,"NAME");
		CellManufacture("NET DU DECOMPTE  TTC",refRow,refRow,refCellRight,refCellRight+7,cellNetDecompteTtcStyle,sheet);
		XSSFCellStyle cellNetDecompteTtcValStyle = getCellStyle(workbook, false,ConstantesExcel.RIGHT, 1, 1, 1, 1,"VAL");
		XSSFCell cellNetDecompteTtcVal =CellManufacture("",refRow,refRow,refCellRight+8,refCellRight+11,cellNetDecompteTtcValStyle,sheet);
		refRow++;
		//OUVRAGES ET DEPENSES AU
		CellManufacture("OUVRAGES ET DEPENSES AU",refRow,refRow+1,refCellRight,refCellRight+7,cellNetDecompteTtcStyle,sheet);
		CellManufacture("",refRow,refRow+1,refCellRight+8,refCellRight+11,cellNetDecompteTtcValStyle,sheet);
		refRow+=3;		
		//OUVRAGES ET DEPENSES "Titre"
		XSSFCellStyle cellNameStyle = getCellStyle(workbook, true, ConstantesExcel.CENTER, 1, 1, 1, 1,"NAME");
		CellManufacture("DONNEES DU MARCHE",refRow,refRow,refCellLeft+13,refCellLeft+16,cellNameStyle,sheet);
		CellManufacture("ANCIEN CUMUL",refRow,refRow,refCellLeft+17,refCellLeft+20,cellNameStyle,sheet);
		CellManufacture("NOUVEAU CUMUL",refRow,refRow,refCellLeft+21,refCellLeft+24,cellNameStyle,sheet);
		CellManufacture("DECOMPTE",refRow,refRow,refCellLeft+25,refCellLeft+28,cellNameStyle,sheet);
		refRow++;
		CellManufacture("OUVRAGES ET DEPENSES",refRow,refRow,refCellLeft,refCellLeft+11,cellNameStyle,sheet);
		CellManufacture("U",refRow,refRow,refCellLeft+12,refCellLeft+12,cellNameStyle,sheet);
		CellManufacture("QTE",refRow,refRow,refCellLeft+13,refCellLeft+14,cellNameStyle,sheet);
		CellManufacture("PU HT",refRow,refRow,refCellLeft+15,refCellLeft+16,cellNameStyle,sheet);
		CellManufacture("QTE",refRow,refRow,refCellLeft+17,refCellLeft+18,cellNameStyle,sheet);
		CellManufacture("Montant HT",refRow,refRow,refCellLeft+19,refCellLeft+20,cellNameStyle,sheet);
		CellManufacture("QTE",refRow,refRow,refCellLeft+21,refCellLeft+22,cellNameStyle,sheet);
		CellManufacture("Montant HT",refRow,refRow,refCellLeft+23,refCellLeft+24,cellNameStyle,sheet);
		CellManufacture("QTE",refRow,refRow,refCellLeft+25,refCellLeft+26,cellNameStyle,sheet);
		CellManufacture("Montant HT",refRow,refRow,refCellLeft+27,refCellLeft+28,cellNameStyle,sheet);		
		
		XSSFCellStyle cellValStyle = getCellStyle(workbook, false, ConstantesExcel.CENTER, 1, 1, 0, 0,"VAL");
		XSSFCellStyle cellValStyleLeft = getCellStyle(workbook, false,ConstantesExcel.LEFT, 1, 1, 0, 0,"VAL");
		XSSFCellStyle cellValStyleRight = getCellStyle(workbook, false,ConstantesExcel.RIGHT, 1, 1, 0, 0,"VAL");	
		int ligneStart=refRow+1,ligneEnd=0;
		for (BordereauPrixDTO bPDTO : printDecompte.getDataMarche()) {
			refRow++;
			prixUnitaire = bPDTO.getPrixUnitaire();
			CellManufacture(bPDTO.getDesigniation(),refRow,refRow,refCellLeft,refCellLeft+11,cellValStyleLeft,sheet);
			CellManufacture(bPDTO.getUnite(),refRow,refRow,refCellLeft+12,refCellLeft+12,cellValStyle,sheet);
			CellManufacture(doubleStr(bPDTO.getQte()),refRow,refRow,refCellLeft+13,refCellLeft+14,cellValStyleRight,sheet);
			CellManufacture(doubleStr(prixUnitaire),refRow,refRow,refCellLeft+15,refCellLeft+16,cellValStyleRight,sheet);
			CellManufacture(doubleStr(bPDTO.getQteAncienCumul()),refRow,refRow,refCellLeft+17,refCellLeft+18,cellValStyleRight,sheet);
			montant = bPDTO.getQteAncienCumul() * prixUnitaire;
			printDecompte.getDecompteAncienCumul().AddMontantTtc(montant);
			printDecompte.getDecompteAncienCumul().AddMontantTva(bPDTO.getTva()/100 * montant);
			CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+19,refCellLeft+20,cellValStyleRight,sheet);
			qte = bPDTO.getQteAncienCumul()+bPDTO.getQteDecompte();
			CellManufacture(doubleStr(qte),refRow,refRow,refCellLeft+21,refCellLeft+22,cellValStyleRight,sheet);
			montant = qte * prixUnitaire;
			printDecompte.getDecompteNouveauCumul().AddMontantTtc(montant);
			printDecompte.getDecompteNouveauCumul().AddMontantTva(bPDTO.getTva()/100 * montant);
			CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+23,refCellLeft+24,cellValStyleRight,sheet);
			CellManufacture(doubleStr(bPDTO.getQteDecompte()),refRow,refRow,refCellLeft+25,refCellLeft+26,cellValStyleRight,sheet);
			montant = bPDTO.getQteDecompte() * prixUnitaire;
			printDecompte.getDecompte().AddMontantTtc(montant);
			printDecompte.getDecompte().AddMontantTva(bPDTO.getTva()/100 * montant);
			CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+27,refCellLeft+28,cellValStyleRight,sheet);
			ligneEnd++;
		}
				
		ligneEnd+=ligneStart;
		refRow++;
		XSSFCellStyle cellVALSUMStyle = getCellStyle(workbook, true, ConstantesExcel.CENTER, 1, 1, 1, 1,"VAL_SUM");
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+12,cellNumMarcheValStyle,sheet);
		CellManufacture("TOTAL",refRow,refRow,refCellLeft+13,refCellLeft+16,cellNameStyle,sheet);
		cellValue = "FORMULA:SUM(T"+ligneStart+":U"+ligneEnd+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellVALSUMStyle,sheet);
		cellValue = "FORMULA:SUM(X"+ligneStart+":Y"+ligneEnd+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+21,refCellLeft+24,cellVALSUMStyle,sheet);
		cellValue = "FORMULA:SUM(AB"+ligneStart+":AC"+ligneEnd+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellVALSUMStyle,sheet);
		refRow+=3;
		
		//Fotter
		XSSFCellStyle cellNameStyleFotter = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 1, 0, 0,"NAME");
		CellManufacture("RECAPITULATIF",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyle,sheet);
		CellManufacture("ANCIEN CUMUL",refRow,refRow,refCellLeft+17,refCellLeft+20,cellNameStyle,sheet);
		CellManufacture("NOUVEAU CUMUL",refRow,refRow,refCellLeft+21,refCellLeft+24,cellNameStyle,sheet);
		CellManufacture("DECOMPTE",refRow,refRow,refCellLeft+25,refCellLeft+28,cellNameStyle,sheet);
		refRow++;
		CellManufacture("MONTANT DES PRESTATIONS REALISEES",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		cellValue = "FORMULA:SUM(T"+ligneStart+":U"+ligneEnd+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		cellValue = "FORMULA:SUM(X"+ligneStart+":Y"+ligneEnd+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		cellValue = "FORMULA:SUM(AB"+ligneStart+":AC"+ligneEnd+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);
		refRow++;
		CellManufacture("APPROVISIONNEMENT",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		CellManufacture("0,0",refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		CellManufacture("0,0",refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		CellManufacture("0,0",refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);			
		refRow++;
		CellManufacture("MONTANT TVA",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		cellValue = doubleStr(printDecompte.getDecompteAncienCumul().getMontantTva());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		cellValue = doubleStr(printDecompte.getDecompteNouveauCumul().getMontantTva());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		cellValue = doubleStr(printDecompte.getDecompte().getMontantTva());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);			
		refRow++;
		CellManufacture("TOTAL BRUT",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		cellValue = "FORMULA:SUM(R"+(refRow)+"+R"+(refRow-2)+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		cellValue = "FORMULA:SUM(V"+(refRow)+"+V"+(refRow-2)+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		cellValue = "FORMULA:SUM(Z"+(refRow)+"+Z"+(refRow-2)+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);		
		refRow++;
		CellManufacture("RETENUE SUR AVANCE",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		cellValue = doubleStr(printDecompte.getDecompteAncienCumul().getRetenuAvance());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		montant =printDecompte.getDecompteAncienCumul().getRetenuAvance()+printDecompte.getDecompte().getRetenuAvance();
		CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		cellValue = doubleStr(printDecompte.getDecompte().getRetenuAvance());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);	
		refRow++;
		CellManufacture("RETENUE DE GARANTIE",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		cellValue = doubleStr(printDecompte.getDecompteAncienCumul().getRetenuGarantie());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		montant = printDecompte.getDecompteAncienCumul().getRetenuGarantie()+printDecompte.getDecompte().getRetenuGarantie();
		CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		cellValue = doubleStr(printDecompte.getDecompte().getRetenuGarantie());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);
		refRow++;
		CellManufacture("PENALITE DE RETARD",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		cellValue = doubleStr(printDecompte.getDecompteAncienCumul().getPenaliteRetard());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		montant = printDecompte.getDecompteAncienCumul().getPenaliteRetard()+printDecompte.getDecompte().getPenaliteRetard();
		CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		cellValue = doubleStr(printDecompte.getDecompte().getPenaliteRetard());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);
		refRow++;
		CellManufacture("AUTRES RETENUES",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		cellValue = doubleStr(printDecompte.getDecompteAncienCumul().getAutresRetenues());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		montant = printDecompte.getDecompteAncienCumul().getAutresRetenues()+printDecompte.getDecompte().getAutresRetenues();
		CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		cellValue = doubleStr(printDecompte.getDecompte().getAutresRetenues());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);
		refRow++;
		CellManufacture("REVISION DES PRIX",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		cellValue = doubleStr(printDecompte.getDecompteAncienCumul().getRevisionPrix());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		montant = printDecompte.getDecompteAncienCumul().getRevisionPrix()+printDecompte.getDecompte().getRevisionPrix();
		CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		cellValue = doubleStr(printDecompte.getDecompte().getRevisionPrix());
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);
		refRow++;
		CellManufacture("TOTAL DES RETENUES",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyleFotter,sheet);
		cellValue = "FORMULA:SUM(U"+(refRow)+":R"+(refRow-4)+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
		cellValue = "FORMULA:SUM(V"+(refRow)+":Y"+(refRow-4)+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
		cellValue = "FORMULA:SUM(Z"+(refRow)+":AC"+(refRow-4)+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);
		refRow++;
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+12,cellNumMarcheValStyle,sheet);
		CellManufacture("MONTANTS DEJA PAYES:",refRow,refRow+2,refCellLeft+13,refCellLeft+16,cellNameStyle,sheet);
		cellValue = "FORMULA:SUM(R"+(refRow-9)+"-R"+refRow+")";
		CellManufacture(cellValue,refRow,refRow+2,refCellLeft+17,refCellLeft+20,cellVALSUMStyle,sheet);
		CellManufacture("MONTANT A PAYER POUR CE DECOMPTE :",refRow,refRow+2,refCellLeft+21,refCellLeft+24,cellNameStyle,sheet);
		cellValue = "FORMULA:SUM(Z"+(refRow-9)+"-Z"+refRow+")";
		CellManufacture(cellValue,refRow,refRow+2,refCellLeft+25,refCellLeft+28,cellVALSUMStyle,sheet);
		cellNetDecompteTtcVal.setCellFormula("Z"+(refRow+1));
		refRow+=4;
		CellManufacture("Dressé par : ",refRow,refRow,refCellLeft,refCellLeft+6,cellSubTitleStyle,sheet);
		refRow+=6;
		cellValue =printDecompte.getVille()+", le : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+11,cellSubTitleStyle,sheet);
		cellValue =printDecompte.getVille()+", le : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+28,cellSubTitleStyle,sheet);
		refRow++;
		CellManufacture("LE CHEF DE LA DIVISION …………………………",refRow,refRow,refCellLeft,refCellLeft+11,cellSubTitleStyle,sheet);
		CellManufacture("LE DIRECTEUR …………………………",refRow,refRow,refCellLeft+17,refCellLeft+28,cellSubTitleStyle,sheet);
		
		FileOutputStream fos =new FileOutputStream(new File(FILE_NAME));
		workbook.write(fos);
		fos.close();
		workbook.close();
		
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
			//LOGGER.error(e);
		}
		return FILE_NAME;
	}
	
	
	/**
	 * Décompte Partiel
	 */
	public String printDecomptePartiel(String FILE_NAME){
		Double prixUnitaire,qte,montant,montantTtc = 0.0,montantTva=0.0;
		Integer refRow;
		Integer refCell,refCellLeft,refCellRight;
		String cellValue;
		try {
		refRow = 5;	
		refCell = 0;
		refCellLeft = refCell;
		refCellRight = refCellLeft+17;
		
		XSSFWorkbook workbook = new XSSFWorkbook ();
		XSSFSheet sheet = workbook.createSheet(ConstantesExcel.DECOMPE_PROVISOIRE_TITRE_SHEET);
		//set paper size
		sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
		sheet.setZoom(50);
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setFitWidth((short)1);
		sheet.getPrintSetup().setFitHeight((short)0);
		sheet.setRepeatingRows(CellRangeAddress.valueOf("$1:$16"));
		ConstantesExcel.headerFooterPageNum(sheet, 22);
		sheet.setMargin(Sheet.RightMargin, 0.3 /* inches */ );
		sheet.setMargin(Sheet.LeftMargin, 0.3);
	    
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String imgPath =servletContext.getRealPath("/")+ConstantesExcel.URL_LOGO_IMG;
		// FileInputStream obtains input bytes from the image file
		InputStream inputStream = new FileInputStream(imgPath);
		// Get the contents of an InputStream as a byte[].
		byte[] bytes = IOUtils.toByteArray(inputStream);
		// Adds a picture to the workbook
		int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		// close the input stream
		inputStream.close();
		// Returns an object that handles instantiating concrete classes
		CreationHelper helper = workbook.getCreationHelper();
		// Creates the top-level drawing patriarch.
		Drawing drawing = sheet.createDrawingPatriarch();
		// Create an anchor that is attached to the worksheet
		ClientAnchor anchor = helper.createClientAnchor();
		// create an anchor with upper left cell _and_ bottom right cell
		anchor.setCol1(refCellLeft);
		anchor.setRow1(refRow);
		anchor.setCol2(refCellLeft+6);
		anchor.setRow2(refRow+9);
		// Creates a picture
		drawing.createPicture(anchor, pictureIdx);
		// IMAGE END
		
		refRow+=3;		
		//Titre Document
		XSSFCellStyle cellTitleStyle = getCellStyle(workbook, true, ConstantesExcel.CENTER, 0, 0, 0, 0,"TITLE");
		cellValue = "DECOMPTE DEFINITIF PARTIEL ";
		CellManufacture(cellValue,refRow,refRow+4,refCellLeft+6,refCellLeft+20,cellTitleStyle,sheet);
		//Devise Marché
		XSSFCellStyle cellSubTitleStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"SUB_TITLE");
		cellValue = printDecompte.getMarche().getDeviseStr();
		CellManufacture(cellValue,refRow+5,refRow+6,refCellLeft+13,refCellLeft+14,cellSubTitleStyle,sheet);
		refRow+=8;
		//NumMarche
		XSSFCellStyle cellNumMarcheStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 1, 0,"NAME");
		CellManufacture("N° DU MARCHE ",refRow,refRow,refCellLeft,refCellLeft+6,cellNumMarcheStyle,sheet);
		XSSFCellStyle cellNumMarcheValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 0, 1, 0,"VAL");
		cellValue = printDecompte.getMarche().getNumMarche();
		CellManufacture(cellValue,refRow,refRow,refCellLeft+7,refCellLeft+16,cellNumMarcheValStyle,sheet);
		//Date Commencement
		XSSFCellStyle cellDateCommencementStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 1, 0,"NAME");
		CellManufacture("DATE DE COMMENCEMENT",refRow,refRow,refCellRight,refCellRight+7,cellDateCommencementStyle,sheet);
		XSSFCellStyle cellDateCommencementVALStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 1, 0,"VAL");
		cellValue = printDecompte.getMarche().getDateDemarageStr();
		CellManufacture(cellValue,refRow,refRow,refCellRight+8,refCellRight+11,cellDateCommencementVALStyle,sheet);
		refRow++;
		//Montant Marche
		XSSFCellStyle cellMontantMarcheStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 0, 0,"NAME");
		CellManufacture("MONTANT DU MARCHE TTC",refRow,refRow,refCellLeft,refCellLeft+6,cellMontantMarcheStyle,sheet);
		XSSFCellStyle cellMontantMarcheValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 0, 0, 0,"VAL");
		cellValue = printDecompte.getMarche().getMontantStr();
		CellManufacture(cellValue,refRow,refRow,refCellLeft+7,refCellLeft+16,cellMontantMarcheValStyle,sheet);
		//Delai D'Execution
		XSSFCellStyle cellDelaiExecutionStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"NAME");
		CellManufacture("DELAI D'EXECUTION",refRow,refRow,refCellRight,refCellRight+7,cellDelaiExecutionStyle,sheet);
		XSSFCellStyle cellDelaiExecutionVALStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 0, 0,"VAL");
		cellValue = printDecompte.getMarche().getDelaiExecution();
		CellManufacture(cellValue,refRow,refRow,refCellRight+8,refCellRight+11,cellDelaiExecutionVALStyle,sheet);
		refRow++;
		//Cell Vide
		XSSFCellStyle cellVideStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 0, 0,"NAME");
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+6,cellVideStyle,sheet);
		XSSFCellStyle cellVideValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 0, 0, 0,"VAL");
		CellManufacture("",refRow,refRow,refCellLeft+7,refCellLeft+16,cellVideValStyle,sheet);
		//Budget
		XSSFCellStyle cellBudgetStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"NAME");
		CellManufacture("BUDGET",refRow,refRow,refCellRight,refCellRight+7,cellBudgetStyle,sheet);
		XSSFCellStyle cellBudgetVALStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 0, 0,"VAL");
		cellValue = printDecompte.getMarche().getBudget();
		CellManufacture(cellValue,refRow,refRow,refCellRight+8,refCellRight+11,cellBudgetVALStyle,sheet);
		refRow++;
		//Objet Marche
		CellManufacture("OBJET DU MARCHE",refRow,refRow,refCellLeft,refCellLeft+6,cellVideStyle,sheet);
		CellManufacture("",refRow,refRow,refCellLeft+7,refCellLeft+16,cellVideValStyle,sheet);
		//CHAP.// ART.// PARAG.
		CellManufacture("CHAP.// ART.// PARAG.",refRow,refRow,refCellRight,refCellRight+7,cellBudgetStyle,sheet);
		CellManufacture("",refRow,refRow,refCellRight+8,refCellRight+11,cellBudgetVALStyle,sheet);
		refRow++;
		//Fournisseur
		CellManufacture("ENTREPRENEUR",refRow,refRow,refCellLeft,refCellLeft+6,cellVideStyle,sheet);
		cellValue = printDecompte.getMarche().getLibFournisseur();
		CellManufacture(cellValue,refRow,refRow,refCellLeft+7,refCellLeft+16,cellVideValStyle,sheet);
		//MONTANT RESTANT A PAYER
		XSSFCellStyle cellMontantAPayerStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 1,"NAME");
		CellManufacture("MONTANT RESTANT A PAYER",refRow,refRow+1,refCellRight,refCellRight+7,cellMontantAPayerStyle,sheet);
		XSSFCellStyle cellMontantAPayerValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 0, 1,"VAL");
		CellManufacture("",refRow,refRow+1,refCellRight+8,refCellRight+11,cellMontantAPayerValStyle,sheet);				
		refRow++;
		//NANTI AU PROFIT DE
		XSSFCellStyle cellNantiStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 0, 1,"NAME");
		CellManufacture("NANTI AU PROFIT DE",refRow,refRow,refCellLeft,refCellLeft+6,cellNantiStyle,sheet);
		XSSFCellStyle cellNantiValStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 1,"VAL");
		CellManufacture("",refRow,refRow,refCellLeft+7,refCellLeft+16,cellNantiValStyle,sheet);
		refRow+=2;		
		//OUVRAGES ET DEPENSES "Titre"
		XSSFCellStyle cellNameStyle = getCellStyle(workbook, true, ConstantesExcel.CENTER, 1, 1, 1, 1,"NAME");
		CellManufacture("OUVRAGES ET DEPENSES",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyle,sheet);
		CellManufacture("MONTANT",refRow,refRow,refCellLeft+17,refCellLeft+20,cellNameStyle,sheet);
		CellManufacture("QUANTITE",refRow,refRow,refCellLeft+21,refCellLeft+24,cellNameStyle,sheet);
		CellManufacture("TOTAUX",refRow,refRow,refCellLeft+25,refCellLeft+28,cellNameStyle,sheet);		
		
		XSSFCellStyle cellValStyle = getCellStyle(workbook, false, ConstantesExcel.CENTER, 1, 1, 0, 0,"VAL");
		XSSFCellStyle cellValStyleLeft = getCellStyle(workbook, false,ConstantesExcel.LEFT, 1, 1, 0, 0,"VAL");	
		XSSFCellStyle cellValStyleRight = getCellStyle(workbook, false,ConstantesExcel.RIGHT, 1, 1, 0, 0,"VAL");
		int ligneStart=refRow+1,ligneEnd=0;
		for (BordereauPrixDTO bPDTO : printDecompte.getDataMarche()) {
			refRow++;
			prixUnitaire = bPDTO.getPrixUnitaire();
			qte = bPDTO.getQteAncienCumul();
			montant = qte * prixUnitaire;
			montantTtc+= montant;
			montantTva+= bPDTO.getTva()/100 * montant;
			CellManufacture(bPDTO.getDesigniation(),refRow,refRow,refCellLeft,refCellLeft+16,cellValStyleLeft,sheet);
			CellManufacture(doubleStr(prixUnitaire),refRow,refRow,refCellLeft+17,refCellLeft+20,cellValStyleRight,sheet);
			CellManufacture(doubleStr(qte),refRow,refRow,refCellLeft+21,refCellLeft+24,cellValStyleRight,sheet);
			CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+25,refCellLeft+28,cellValStyleRight,sheet);
			ligneEnd++;
		}
				
		ligneEnd+=ligneStart;
		refRow++;
		XSSFCellStyle cellVALSUMStyle = getCellStyle(workbook, true, ConstantesExcel.CENTER, 1, 1, 1, 1,"VAL_SUM");
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+16,cellNumMarcheValStyle,sheet);
		CellManufacture("A REPORTER",refRow,refRow,refCellLeft+17,refCellLeft+24,cellNameStyle,sheet);
	
		cellValue = "FORMULA:SUM(Z"+ligneStart+":AC"+ligneEnd+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+25,refCellLeft+28,cellVALSUMStyle,sheet);
		refRow+=3;
		
		//Fotter
		//XSSFCellStyle cellNameStyleFotter = getStyleTable(workbook, true,ConstantesExcel.LEFT, 1, 1, 0, 0,"NAME");
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+20,cellNameStyle,sheet);
		CellManufacture("TOTAUX",refRow,refRow,refCellLeft+21,refCellLeft+28,cellNameStyle,sheet);
		refRow++;
		CellManufacture("MONTANT HT",refRow,refRow,refCellLeft,refCellLeft+20,cellValStyle,sheet);
		cellValue = "FORMULA:Z"+(refRow-3);
		CellManufacture(cellValue,refRow,refRow,refCellLeft+21,refCellLeft+28,cellValStyleRight,sheet);
		
		
		refRow++;
		CellManufacture("TVA",refRow,refRow,refCellLeft,refCellLeft+20,cellValStyle,sheet);
		cellValue = doubleStr(montantTva);
		CellManufacture(cellValue,refRow,refRow,refCellLeft+21,refCellLeft+28,cellValStyleRight,sheet);

		refRow++;
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+12,cellNumMarcheValStyle,sheet);
		CellManufacture("TOTAL EN DH TTC",refRow,refRow+2,refCellLeft+13,refCellLeft+20,cellNameStyle,sheet);
		
		//cellSubTitleStyle
		XSSFCellStyle cellTexttyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"TEXT");
		
		cellValue = "FORMULA:SUM(V"+(refRow-1)+"+V"+(refRow)+")";
		CellManufacture(cellValue,refRow,refRow+2,refCellLeft+21,refCellLeft+28,cellVALSUMStyle,sheet);
		refRow+=4;
		montant = montantTtc+montantTva;
		cellValue = "LE PRESENT DECOMPTE DEFINITIF  S'ELEVE A LA SOMME DE : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow++;
		cellValue = Utilitaires.montantEnLettre(montant,Constantes.GlobalConstant.DEVISE_DH,Constantes.GlobalConstant.DEVISE_DH_FRACTION);
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow+=3;
		cellValue = "-NOMBRE DE DECOMPTES ETABLIS : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow++;
		cellValue = "-NOMBRE DE DECOMPTES PROVISOIRES : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow++;
		cellValue = "-DECOMPTE DEFINITIF PARTIEL : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow+=4;
		CellManufacture("DRESSE PAR: ",refRow,refRow,refCellLeft,refCellLeft+11,cellTexttyle,sheet);
		CellManufacture("LU ET ACCEPTE PAR  LE TITULAIRE DU MARCHE : ",refRow,refRow,refCellLeft+17,refCellLeft+28,cellTexttyle,sheet);
		refRow+=6;
		cellValue =printDecompte.getVille()+", le : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+11,cellTexttyle,sheet);
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+28,cellTexttyle,sheet);
		refRow++;
		CellManufacture("LE DIRECTEUR …………………………",refRow,refRow,refCellLeft,refCellLeft+11,cellTexttyle,sheet);
		CellManufacture("LE CHEF DE LA DIVISION …………………………",refRow,refRow,refCellLeft+17,refCellLeft+28,cellTexttyle,sheet);
		
		FileOutputStream fos =new FileOutputStream(new File(FILE_NAME));
		workbook.write(fos);
		fos.close();
		workbook.close();
		
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
		}
		return FILE_NAME;
	}
	
	/**
	 * Décompte Definitif
	 */
	public String printDecompteDefinitif(String FILE_NAME){
		Double prixUnitaire,qte,montant,montantTtc = 0.0,montantTva=0.0;
		Integer refRow;
		Integer refCell,refCellLeft,refCellRight;
		String cellValue;
		try {
		refRow = 5;	
		refCell = 0;
		refCellLeft = refCell;
		refCellRight = refCellLeft+17;
		
		XSSFWorkbook workbook = new XSSFWorkbook ();
		XSSFSheet sheet = workbook.createSheet(ConstantesExcel.DECOMPE_DEFINITIF_TITRE_SHEET);
		//set paper size
		sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
		sheet.setZoom(50);
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setFitWidth((short)1);
		sheet.getPrintSetup().setFitHeight((short)0);
		sheet.setRepeatingRows(CellRangeAddress.valueOf("$1:$16"));
		ConstantesExcel.headerFooterPageNum(sheet, 22);
		sheet.setMargin(Sheet.RightMargin, 0.3 /* inches */ );
		sheet.setMargin(Sheet.LeftMargin, 0.3);
	    
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String imgPath =servletContext.getRealPath("/")+ConstantesExcel.URL_LOGO_IMG;
		// FileInputStream obtains input bytes from the image file
		InputStream inputStream = new FileInputStream(imgPath);
		// Get the contents of an InputStream as a byte[].
		byte[] bytes = IOUtils.toByteArray(inputStream);
		// Adds a picture to the workbook
		int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		// close the input stream
		inputStream.close();
		// Returns an object that handles instantiating concrete classes
		CreationHelper helper = workbook.getCreationHelper();
		// Creates the top-level drawing patriarch.
		Drawing drawing = sheet.createDrawingPatriarch();
		// Create an anchor that is attached to the worksheet
		ClientAnchor anchor = helper.createClientAnchor();
		// create an anchor with upper left cell _and_ bottom right cell
		anchor.setCol1(refCellLeft);
		anchor.setRow1(refRow);
		anchor.setCol2(refCellLeft+6);
		anchor.setRow2(refRow+9);
		// Creates a picture
		drawing.createPicture(anchor, pictureIdx);
		// IMAGE END
		
		refRow+=3;		
		//Titre Document
		XSSFCellStyle cellTitleStyle = getCellStyle(workbook, true, ConstantesExcel.CENTER, 0, 0, 0, 0,"TITLE");
		cellValue = "DECOMPTE DEFINITIF";
		CellManufacture(cellValue,refRow,refRow+4,refCellLeft+6,refCellLeft+20,cellTitleStyle,sheet);
		//Devise Marché
		XSSFCellStyle cellSubTitleStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"SUB_TITLE");
		cellValue = printDecompte.getMarche().getDeviseStr();
		CellManufacture(cellValue,refRow+5,refRow+6,refCellLeft+13,refCellLeft+14,cellSubTitleStyle,sheet);
		refRow+=8;
		//NumMarche
		XSSFCellStyle cellNumMarcheStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 1, 0,"NAME");
		CellManufacture("N° DU MARCHE ",refRow,refRow,refCellLeft,refCellLeft+6,cellNumMarcheStyle,sheet);
		XSSFCellStyle cellNumMarcheValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 0, 1, 0,"VAL");
		cellValue = printDecompte.getMarche().getNumMarche();
		CellManufacture(cellValue,refRow,refRow,refCellLeft+7,refCellLeft+16,cellNumMarcheValStyle,sheet);
		//Date Commencement
		XSSFCellStyle cellDateCommencementStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 1, 0,"NAME");
		CellManufacture("DATE DE COMMENCEMENT",refRow,refRow,refCellRight,refCellRight+7,cellDateCommencementStyle,sheet);
		XSSFCellStyle cellDateCommencementVALStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 1, 0,"VAL");
		cellValue = printDecompte.getMarche().getDateDemarageStr();
		CellManufacture(cellValue,refRow,refRow,refCellRight+8,refCellRight+11,cellDateCommencementVALStyle,sheet);
		refRow++;
		//Montant Marche
		XSSFCellStyle cellMontantMarcheStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 0, 0,"NAME");
		CellManufacture("MONTANT DU MARCHE TTC",refRow,refRow,refCellLeft,refCellLeft+6,cellMontantMarcheStyle,sheet);
		XSSFCellStyle cellMontantMarcheValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 0, 0, 0,"VAL");
		cellValue = printDecompte.getMarche().getMontantStr();
		CellManufacture(cellValue,refRow,refRow,refCellLeft+7,refCellLeft+16,cellMontantMarcheValStyle,sheet);
		//Delai D'Execution
		XSSFCellStyle cellDelaiExecutionStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"NAME");
		CellManufacture("DELAI D'EXECUTION",refRow,refRow,refCellRight,refCellRight+7,cellDelaiExecutionStyle,sheet);
		XSSFCellStyle cellDelaiExecutionVALStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 0, 0,"VAL");
		cellValue = printDecompte.getMarche().getDelaiExecution();
		CellManufacture(cellValue,refRow,refRow,refCellRight+8,refCellRight+11,cellDelaiExecutionVALStyle,sheet);
		refRow++;
		//Cell Vide
		XSSFCellStyle cellVideStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 0, 0,"NAME");
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+6,cellVideStyle,sheet);
		XSSFCellStyle cellVideValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 0, 0, 0,"VAL");
		CellManufacture("",refRow,refRow,refCellLeft+7,refCellLeft+16,cellVideValStyle,sheet);
		//Budget
		XSSFCellStyle cellBudgetStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"NAME");
		CellManufacture("BUDGET",refRow,refRow,refCellRight,refCellRight+7,cellBudgetStyle,sheet);
		XSSFCellStyle cellBudgetVALStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 0, 0,"VAL");
		cellValue = printDecompte.getMarche().getBudget();
		CellManufacture(cellValue,refRow,refRow,refCellRight+8,refCellRight+11,cellBudgetVALStyle,sheet);
		refRow++;
		//Objet Marche
		CellManufacture("OBJET DU MARCHE",refRow,refRow,refCellLeft,refCellLeft+6,cellVideStyle,sheet);
		CellManufacture("",refRow,refRow,refCellLeft+7,refCellLeft+16,cellVideValStyle,sheet);
		//CHAP.// ART.// PARAG.
		CellManufacture("CHAP.// ART.// PARAG.",refRow,refRow,refCellRight,refCellRight+7,cellBudgetStyle,sheet);
		CellManufacture("",refRow,refRow,refCellRight+8,refCellRight+11,cellBudgetVALStyle,sheet);
		refRow++;
		//Fournisseur
		CellManufacture("ENTREPRENEUR",refRow,refRow,refCellLeft,refCellLeft+6,cellVideStyle,sheet);
		cellValue = printDecompte.getMarche().getLibFournisseur();
		CellManufacture(cellValue,refRow,refRow,refCellLeft+7,refCellLeft+16,cellVideValStyle,sheet);
		//MONTANT RESTANT A PAYER
		XSSFCellStyle cellMontantAPayerStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 1,"NAME");
		CellManufacture("",refRow,refRow+1,refCellRight,refCellRight+7,cellMontantAPayerStyle,sheet);
		XSSFCellStyle cellMontantAPayerValStyle = getCellStyle(workbook, false,ConstantesExcel.LEFT, 0, 1, 0, 1,"VAL");
		CellManufacture("",refRow,refRow+1,refCellRight+8,refCellRight+11,cellMontantAPayerValStyle,sheet);				
		refRow++;
		//NANTI AU PROFIT DE
		XSSFCellStyle cellNantiStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 1, 0, 0, 1,"NAME");
		CellManufacture("NANTI AU PROFIT DE",refRow,refRow,refCellLeft,refCellLeft+6,cellNantiStyle,sheet);
		XSSFCellStyle cellNantiValStyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 1,"VAL");
		CellManufacture("",refRow,refRow,refCellLeft+7,refCellLeft+16,cellNantiValStyle,sheet);
		refRow+=2;		
		//OUVRAGES ET DEPENSES "Titre"
		XSSFCellStyle cellNameStyle = getCellStyle(workbook, true, ConstantesExcel.CENTER, 1, 1, 1, 1,"NAME");
		CellManufacture("OUVRAGES ET DEPENSES",refRow,refRow,refCellLeft,refCellLeft+10,cellNameStyle,sheet);
		CellManufacture("MONTANT EN EURO",refRow,refRow,refCellLeft+11,refCellLeft+13,cellNameStyle,sheet);
		CellManufacture("QTE",refRow,refRow,refCellLeft+14,refCellLeft+16,cellNameStyle,sheet);
		CellManufacture("TOTAL",refRow,refRow,refCellLeft+17,refCellLeft+19,cellNameStyle,sheet);
		CellManufacture("MONTANT EN DH",refRow,refRow,refCellLeft+20,refCellLeft+22,cellNameStyle,sheet);
		CellManufacture("QTE",refRow,refRow,refCellLeft+23,refCellLeft+25,cellNameStyle,sheet);
		CellManufacture("TOTAl",refRow,refRow,refCellLeft+26,refCellLeft+28,cellNameStyle,sheet);		
		
		XSSFCellStyle cellValStyle = getCellStyle(workbook, false, ConstantesExcel.CENTER, 1, 1, 0, 0,"VAL");
		XSSFCellStyle cellValStyleLeft = getCellStyle(workbook, false,ConstantesExcel.LEFT, 1, 1, 0, 0,"VAL");
		XSSFCellStyle cellValStyleRight = getCellStyle(workbook, false,ConstantesExcel.RIGHT, 1, 1, 0, 0,"VAL");
		int ligneStart=refRow+1,ligneEnd=0;
		for (BordereauPrixDTO bPDTO : printDecompte.getDataMarche()) {
			refRow++;
			prixUnitaire = bPDTO.getPrixUnitaire();
			qte = bPDTO.getQteAncienCumul();
			montant = qte * prixUnitaire;
			montantTtc+= montant;
			montantTva+= bPDTO.getTva()/100 * montant;
			CellManufacture(bPDTO.getDesigniation(),refRow,refRow,refCellLeft,refCellLeft+10,cellValStyleLeft,sheet);
			CellManufacture("",refRow,refRow,refCellLeft+11,refCellLeft+13,cellValStyleRight,sheet);
			CellManufacture("",refRow,refRow,refCellLeft+14,refCellLeft+16,cellValStyleRight,sheet);
			CellManufacture("",refRow,refRow,refCellLeft+17,refCellLeft+19,cellValStyleRight,sheet);
			CellManufacture(doubleStr(prixUnitaire),refRow,refRow,refCellLeft+20,refCellLeft+22,cellValStyleRight,sheet);
			CellManufacture(doubleStr(qte),refRow,refRow,refCellLeft+23,refCellLeft+25,cellValStyleRight,sheet);
			CellManufacture(doubleStr(montant),refRow,refRow,refCellLeft+26,refCellLeft+28,cellValStyleRight,sheet);
			ligneEnd++;
		}
				
		ligneEnd+=ligneStart;
		refRow++;
		XSSFCellStyle cellVALSUMStyle = getCellStyle(workbook, true, ConstantesExcel.CENTER, 1, 1, 1, 1,"VAL_SUM");
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+16,cellNumMarcheValStyle,sheet);
		cellValue = "FORMULA:SUM(R"+ligneStart+":T"+ligneEnd+")";
		CellManufacture("",refRow,refRow,refCellLeft+17,refCellLeft+19,cellVALSUMStyle,sheet);
		CellManufacture("",refRow,refRow,refCellLeft+20,refCellLeft+25,cellNumMarcheValStyle,sheet);
	
		cellValue = "FORMULA:SUM(AA"+ligneStart+":AC"+ligneEnd+")";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+26,refCellLeft+28,cellVALSUMStyle,sheet);
		refRow+=3;
		
		//Fotter
		//XSSFCellStyle cellNameStyleFotter = getStyleTable(workbook, true,ConstantesExcel.LEFT, 1, 1, 0, 0,"NAME");
		CellManufacture("RECAPITULATIF",refRow,refRow,refCellLeft,refCellLeft+16,cellNameStyle,sheet);
		CellManufacture("TOTAL",refRow,refRow,refCellLeft+17,refCellLeft+22,cellNameStyle,sheet);
		CellManufacture("TOTAL",refRow,refRow,refCellLeft+23,refCellLeft+28,cellNameStyle,sheet);
		refRow++;
		CellManufacture("MONTANT ",refRow,refRow,refCellLeft,refCellLeft+16,cellValStyle,sheet);
		CellManufacture("",refRow,refRow,refCellLeft+17,refCellLeft+22,cellValStyleRight,sheet);
		cellValue = "FORMULA:AA"+(refRow-3);
		CellManufacture(cellValue,refRow,refRow,refCellLeft+23,refCellLeft+28,cellValStyleRight,sheet);
		refRow++;
		CellManufacture("PENALITE DE RETARD",refRow,refRow,refCellLeft,refCellLeft+16,cellValStyle,sheet);
		CellManufacture("",refRow,refRow,refCellLeft+17,refCellLeft+22,cellValStyleRight,sheet);
		cellValue = "0";
		CellManufacture(cellValue,refRow,refRow,refCellLeft+23,refCellLeft+28,cellValStyleRight,sheet);
		

		refRow++;
		CellManufacture("",refRow,refRow,refCellLeft,refCellLeft+10,cellNumMarcheValStyle,sheet);
		CellManufacture("TOTAL",refRow,refRow+2,refCellLeft+11,refCellLeft+16,cellNameStyle,sheet);
		
		//cellSubTitleStyle
		XSSFCellStyle cellTexttyle = getCellStyle(workbook, true,ConstantesExcel.LEFT, 0, 0, 0, 0,"TEXT");
		cellValue = "";
		CellManufacture(cellValue,refRow,refRow+2,refCellLeft+17,refCellLeft+22,cellVALSUMStyle,sheet);	
		cellValue = "FORMULA:SUM(X"+(refRow-1)+"-X"+(refRow)+")";
		CellManufacture(cellValue,refRow,refRow+2,refCellLeft+23,refCellLeft+28,cellVALSUMStyle,sheet);
		refRow+=4;
		montant = montantTtc; //+montantTva
		cellValue = "LE PRESENT DECOMPTE DEFINITIF  S'ELEVE A LA SOMME DE : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow++;
		cellValue = Utilitaires.montantEnLettre(montant,Constantes.GlobalConstant.DEVISE_DH,Constantes.GlobalConstant.DEVISE_DH_FRACTION);
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow+=3;
		cellValue = "RESTE A RESTITUER :";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow++;
		cellValue = "- LES CAUTIONS DEFINITIVES : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow++;
		cellValue = "- LA CAUTION DE RETENUE DE GARANTIE";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow++;
		cellValue = "- LES MONTANTS DES  RETENUES DE GARANTIES DE (EN LETTRES): ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow++;
		montant=0.0;
		cellValue = Utilitaires.montantEnLettre(montant,Constantes.GlobalConstant.DEVISE_DH,Constantes.GlobalConstant.DEVISE_DH_FRACTION);
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+20,cellTexttyle,sheet);
		refRow+=6;
		cellValue =printDecompte.getVille()+", le : ";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+11,cellTexttyle,sheet);
		CellManufacture(cellValue,refRow,refRow,refCellLeft+17,refCellLeft+28,cellTexttyle,sheet);
		refRow++;
		CellManufacture("LE CHEF DE LA DIVISION …………………………",refRow,refRow,refCellLeft,refCellLeft+11,cellTexttyle,sheet);
		CellManufacture("LE DIRECTEUR …………………………",refRow,refRow,refCellLeft+17,refCellLeft+28,cellTexttyle,sheet);
		refRow+=4;
		cellValue =printDecompte.getVille()+", le : …………………………";
		CellManufacture(cellValue,refRow,refRow,refCellLeft,refCellLeft+11,cellTexttyle,sheet);
		refRow++;
		CellManufacture(" LU ET ACCEPTE PAR LE TITULAIRE DU MARCHE",refRow,refRow,refCellLeft,refCellLeft+11,cellTexttyle,sheet);
		
		FileOutputStream fos =new FileOutputStream(new File(FILE_NAME));
		workbook.write(fos);
		fos.close();
		workbook.close();
		
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
		}
		return FILE_NAME;
	}
	
	/***
	 * 
	 * Double Formater
	 */
	private String doubleStr(Double number){
		return number.toString();
	}
		
	/***
	 * Merger des cellules avec affectation de style
	 * 
	 */
	
	public XSSFCell CellManufacture(String Value,Integer rowStart,Integer rowEnd,Integer cellStart,Integer cellEnd,XSSFCellStyle cellStyle,XSSFSheet sheet){
		XSSFRow row ;
		XSSFCell cell;
		Double valDouble;
		
		for (int i = rowStart; i <= rowEnd; ++i) {
			row = sheet.getRow(i);
			if(null == row){
				row = sheet.createRow(i);	
			}
			for (int j = cellStart; j <= cellEnd; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
			}
		}
		if(cellStart < cellEnd){
			sheet.addMergedRegion(new CellRangeAddress(rowStart,rowEnd,cellStart,cellEnd));	
		}
		
		row = sheet.getRow(rowStart);
		cell = row.getCell(cellStart);
	
		if(Value.contains("FORMULA:")){
			Value = Value.replace("FORMULA:", "");
			cell.setCellFormula(Value);
			
		}else{
			if(NumberUtils.isNumber(Value)){
				valDouble = NumberUtils.createDouble(Value);
				cell.setCellValue(valDouble);
			}else{
				cell.setCellValue(Value);	
			}
		}
		
		return cell;
	}
	
	/**
	 * Methode permet de mettre en oeuvre les cellule d'un feuille excel
	 *
	 * @param wb
	 * @param underline
	 * @param bold
	 * @param border
	 * @param align
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static XSSFCellStyle getCellStyle(XSSFWorkbook wb, boolean blod,HorizontalAlignment alignment,
			int borderLeft, int borderRight, int borderTop,int borderBottom,String type) throws Exception {
		
		XSSFCellStyle cellStyle = wb.createCellStyle();
		XSSFFont fontEntete = wb.createFont();
		CreationHelper ch = wb.getCreationHelper();
		fontEntete.setFontName(ConstantesExcel.FONT_NAME);
		fontEntete.setColor(HSSFColor.BLACK.index);
		
		switch (type) {
		case "TITLE":
			fontEntete.setFontHeight(40);
			break;
		case "SUB_TITLE":
			fontEntete.setFontHeight(28);
			break;
		case "TEXT":
			fontEntete.setFontHeight(20);
			break;
		case "NAME":
			fontEntete.setFontHeight(18);
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			cellStyle.setFillForegroundColor(new XSSFColor(Color.decode(ConstantesExcel.FOOTER_TAB_GRIS)));
			cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			break;
		case "VAL":
			fontEntete.setFontHeight(20);
			cellStyle.setDataFormat(ch.createDataFormat().getFormat(ConstantesExcel.FORMAT_CURRENCY));
			break;
		case "VAL_SUM":
			fontEntete.setFontHeight(25);
			cellStyle.setDataFormat(ch.createDataFormat().getFormat(ConstantesExcel.FORMAT_CURRENCY));
			break;
			
		default:
			fontEntete.setFontHeight(20);
			break;
		}
		if (blod) {
			fontEntete.setBold(true);
		}
		if (null != alignment ) {
			cellStyle.setAlignment(alignment);
		}
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderLeft((short) borderLeft);
		cellStyle.setBorderRight((short) borderRight);
		cellStyle.setBorderTop((short) borderTop);
		cellStyle.setBorderBottom((short) borderBottom);
		cellStyle.setFont(fontEntete);

		return cellStyle;
	}
	
	

}
