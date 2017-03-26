package ma.co.marsamaroc.gestion.decomptes.edition.excel;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.EditionAttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.ConstantesExcel;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

/***
 * 
 * @author ZGUIOUAR
 *
 */
@Component
public class AttachementExcel {

	/**
	 * DESCRIPTION : 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param editionAttachementDTO
	 * @param type
	 * @param url
	 * @throws Exception
	 */
	public void exportExcel(EditionAttachementDTO editionAttachementDTO , String type, String url) throws Exception {
		String xslAbsoluteLocation = url;
		
		// creation du fichier excel
		XSSFWorkbook wb = new XSSFWorkbook();
		int rowPosition = 0;
		
		// Nom du feuille de donnees
		String libelleSheet = null;
		if (Constantes.GlobalConstant.CONST_V.equalsIgnoreCase(editionAttachementDTO.getTypeEdition())) {
			libelleSheet = ConstantesExcel.ATTACHEMENT_VALORISE_TITRE_SHEET;	
		}else{
			libelleSheet = ConstantesExcel.ATTACHEMENT_NON_VALORISE_TITRE_SHEET;
		}
		XSSFSheet sheet = wb.createSheet(libelleSheet);

		// paramétrage logo marsa maroc
		int rowStart = 1;
		int rowEnd = 3;
		int cellStart = 0;
		int cellEnd = 2;
		// paramétrage deuxiéme partie
		int cellStart2 = 7;
		int cellEnd2 = 9;
		// paramétrage saute de page
		int sauteLigne = 4;
		// paramétrage en-tete tableau
		int sizeHeaderTab = 1;
		
		if (Constantes.GlobalConstant.CONST_V.equalsIgnoreCase(type)) {
			sizeCellule(sheet);
		}
		
		// Header
		rowPosition = enteteAttachement(editionAttachementDTO, wb, sheet, rowStart, rowEnd, cellStart, cellEnd, cellStart2, cellEnd2, sauteLigne);
		// Body
		rowPosition = rowPosition + sauteLigne;
		rowEnd = rowPosition + sizeHeaderTab;
		// row 
		int rowImpression = rowEnd+1;
		
		detailAttachement(editionAttachementDTO , wb, sheet, rowPosition, rowEnd, cellStart);
		// Footer
		rowEnd = rowEnd + editionAttachementDTO.getListBonReception().size() + 1 + sauteLigne;
		footer(wb, sheet, rowEnd, cellStart, cellEnd, cellStart2, cellEnd2);
		
		// Impression
		sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setFitWidth((short)1);
		sheet.getPrintSetup().setFitHeight((short)0);
		sheet.setRepeatingRows(CellRangeAddress.valueOf("$1:$" + rowImpression));	
		ConstantesExcel.headerFooterPageNum(sheet, 10);
	    
	    sheet.autoSizeColumn(0);
	    sheet.autoSizeColumn(1);
		
		FileOutputStream fileOut = new FileOutputStream(xslAbsoluteLocation);
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * DESCRIPTION : 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param editionAttachementDTO
	 * @param wb
	 * @param sheet
	 * @param rowStart
	 * @param rowEnd
	 * @param cellStart
	 * @param cellEnd
	 * @param cellStart2
	 * @param cellEnd2
	 * @param sauteLigne
	 * @return
	 * @throws Exception
	 */
	private int enteteAttachement(EditionAttachementDTO editionAttachementDTO, XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int rowEnd, int cellStart, int cellEnd,
			int cellStart2, int cellEnd2, int sauteLigne) throws Exception {
		
		premiereLigne(editionAttachementDTO.getCodeIso(), wb, sheet, rowStart, rowEnd, cellStart, cellEnd, cellStart2, cellEnd2);
		
		rowStart = rowEnd + sauteLigne;
		rowEnd = rowStart + 4;
		return derniereLigne(editionAttachementDTO,wb, sheet, rowStart, rowEnd, cellStart,
				cellStart2, cellEnd2);
	}

	/**
	 * DESCRIPTION : 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param editionAttachementDTO
	 * @param wb
	 * @param sheet
	 * @param rowStart
	 * @param rowEnd
	 * @param cellStart
	 * @throws Exception
	 */
	private void detailAttachement(EditionAttachementDTO editionAttachementDTO, XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int rowEnd, int cellStart) throws Exception {
		Map<String, List<Integer>> mapColumnCoordonees = enteteTabAttachement(editionAttachementDTO.getTypeEdition(),
				wb, sheet, rowStart, rowEnd, cellStart);
		detailTabAttachement(editionAttachementDTO, wb, sheet, (rowEnd + 1), mapColumnCoordonees);
	}

	/**
	 * DESCRIPTION : 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param codeIso
	 * @param wb
	 * @param sheet
	 * @param rowStart
	 * @param rowEnd
	 * @param cellStart
	 * @param cellEnd
	 * @param cellStart2
	 * @param cellEnd2
	 * @throws Exception
	 */
	private void premiereLigne(String codeIso, XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int rowEnd, int cellStart, int cellEnd,
			int cellStart2, int cellEnd2) throws Exception {

		// Style
		XSSFCellStyle cellStyleBorderGras = getStyleTable(wb, true, ConstantesExcel.LEFT, null, 10, 0, 0, 0, 0);
		creationCellules(sheet, cellStyleBorderGras, null, rowStart, rowEnd, cellStart, cellEnd);

		// FileInputStream obtains input bytes from the image file
		InputStream inputStream = new FileInputStream(Utilitaires.getUrlLogoMarsaMaroc());
		// Get the contents of an InputStream as a byte[].
		byte[] bytes = IOUtils.toByteArray(inputStream);
		// Adds a picture to the workbook
		int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		// close the input stream
		inputStream.close();
		// Returns an object that handles instantiating concrete classes
		CreationHelper helper = wb.getCreationHelper();
		// Creates the top-level drawing patriarch.
		Drawing drawing = sheet.createDrawingPatriarch();
		// Create an anchor that is attached to the worksheet
		ClientAnchor anchor = helper.createClientAnchor();
		// create an anchor with upper left cell _and_ bottom right cell
		anchor.setCol1(cellStart);
		anchor.setRow1(rowStart);
		anchor.setCol2(cellEnd + 1);
		anchor.setRow2(rowEnd + 1);
		// Creates a picture
		drawing.createPicture(anchor, pictureIdx);
		// IMAGE END

		// ISO
		int rowIso = (rowEnd - rowStart) / 2 + rowStart;
		creationCellules(sheet, cellStyleBorderGras, "EN.ATC.GE.AMAP.10", rowIso, rowIso, cellStart2, cellEnd2);
		// ISO END

	}

	/**
	 * DESCRIPTION : 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param editionAttachementDTO
	 * @param wb
	 * @param sheet
	 * @param rowStart
	 * @param rowEnd
	 * @param cellStart
	 * @param cellStart2
	 * @param cellEnd2
	 * @return
	 * @throws Exception
	 */
	private int derniereLigne(EditionAttachementDTO editionAttachementDTO, XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int rowEnd, int cellStart, int cellStart2,
			int cellEnd2) throws Exception {
		
		MarcheDTO marche = editionAttachementDTO.getMarcheDTO();
		AttachementDTO attachementDTO = editionAttachementDTO.getAttachementDTO();
		
		// Style
		XSSFCellStyle cellStyle = getStyleTable(wb, true, ConstantesExcel.LEFT, null, 10, 1, 1, 1, 1);
		XSSFCellStyle cellStyle2 = getStyleTable(wb, false, ConstantesExcel.LEFT, null, 10, 1, 1, 1, 1);

		int cellNum = 0;
		int cellNumSec = 0;
		// Tracer TABLEAU
		for (int i = rowStart; i <= rowEnd; ++i) {
			cellNum = cellStart;
			// LIBELLE
			creationCellules(sheet, cellStyle, null, i, i, cellNum++, cellNum);
			// VALUE
			cellNumSec = cellNum + 1;
			creationCellules(sheet, cellStyle2, null, i, i, cellNumSec++, ++cellNumSec);
		}

		cellNumSec = cellStart + 2;
		int rowInc = rowStart;
		
		// SITE
		creationCellules(sheet, cellStyle, "SITE", rowInc, rowInc, cellStart, cellStart);
		creationCellules(sheet, cellStyle2, editionAttachementDTO.getSiteCode(), rowInc, rowInc, cellNumSec, cellNumSec);
		rowInc++;
		
		// MARCHE LIBELLE
		creationCellules(sheet, cellStyle, "MARCHE N°", rowInc, rowInc, cellStart, cellStart);
		creationCellules(sheet, cellStyle2, marche.getNumMarche(), rowInc, rowInc, cellNumSec, cellNumSec);
		rowInc++;
		
		// INTITULE MARCHE LIBELLE
		creationCellules(sheet, cellStyle, "INTITULE MARCHE", rowInc, rowInc, cellStart, cellStart);
		creationCellules(sheet, cellStyle2, "", rowInc, rowInc, cellNumSec, cellNumSec);
		rowInc++;
		
		// TITULAIRE LIBELLE
		creationCellules(sheet, cellStyle, "TITULAIRE", rowInc, rowInc, cellStart, cellStart);
		creationCellules(sheet, cellStyle2, marche.getLibFournisseur(), rowInc, rowInc, cellNumSec, cellNumSec);
		rowInc++;

		// TRAVAUX REALISES LIBELLE
		String dateMarche = "DU " + marche.getDateDemarageStr() + " AU " + marche.getDateFinStr();
		creationCellules(sheet, cellStyle, "TRAVAUX REALISES", rowInc, rowInc, cellStart, cellStart);
		creationCellules(sheet, cellStyle2, dateMarche, rowInc, rowInc, cellNumSec, cellNumSec);
		rowInc++;
		
		//Num Attachement
		cellNum = cellStart2;
		rowInc = (rowEnd - rowStart) / 2 + rowStart;
		cellStyle = getStyleTable(wb, true, ConstantesExcel.LEFT, null, 11, 0, 0, 0, 0);
		String attachementNum = "ATTACHEMENT N° :  " + attachementDTO.getNumAttachement()  + " " + attachementDTO.getFlagDernierStr();
		if (Constantes.GlobalConstant.CONST_V.equalsIgnoreCase(editionAttachementDTO.getTypeEdition())) {
			creationCellules(sheet, cellStyle, attachementNum, rowInc, rowInc, cellNum, cellNum+5);
		}else{
			creationCellules(sheet, cellStyle, attachementNum, rowInc, rowInc, cellNum, cellNum+3);
		}
		
		return rowEnd;

	}
	
	/**
	 * DESCRIPTION : 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param type
	 * @param wb
	 * @param sheet
	 * @param rowStart
	 * @param rowEnd
	 * @param cellStart
	 * @return
	 * @throws Exception
	 */
	private Map<String, List<Integer>> enteteTabAttachement(String type, 
			XSSFWorkbook wb, XSSFSheet sheet, int rowStart, int rowEnd,
			int cellStart) throws Exception {

		Map<String, List<Integer>> mapColumnCoordonees = new HashMap<>();
		List<Integer> listCoordonees = new ArrayList<>();

		// Style
		XSSFCellStyle cellStyle = getStyleTable(wb, true, ConstantesExcel.CENTER, ConstantesExcel.HEADER_TAB_BRUNO, 10, 1, 1, 1, 1);
		int cellInc = 0;
		int cellEnd = cellStart + 5;
		// DESIGNIATION
		creationCellules(sheet, cellStyle, "DESIGNIATION", rowStart, rowEnd, cellStart, cellEnd);
		
		listCoordonees.add(cellStart);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("DESIGNIATION", listCoordonees);
		listCoordonees = new ArrayList<>();

		// UNITE
		cellEnd++;
		creationCellules(sheet, cellStyle, "UNITE", rowStart, rowEnd, cellEnd, cellEnd);
		
		listCoordonees.add(cellEnd);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("UNITE", listCoordonees);
		listCoordonees = new ArrayList<>();
		
		if (Constantes.GlobalConstant.CONST_V.equalsIgnoreCase(type)) {
			// PRIX UNIT
			cellInc = cellEnd + 1;
			cellEnd = cellEnd + 2;
			creationCellules(sheet, cellStyle, "PRIX UNIT", rowStart, rowEnd, cellInc, cellEnd);
	
			listCoordonees.add(cellInc);
			listCoordonees.add(cellEnd);
			mapColumnCoordonees.put("PRIX UNIT", listCoordonees);
			listCoordonees = new ArrayList<>();
		}

		// QTE ATTACHEMENT
		cellInc = cellEnd + 1;
		cellEnd = cellEnd + 2;
		creationCellules(sheet, cellStyle, "QTE ATTACHE", rowStart, rowEnd, cellInc, cellEnd);

		listCoordonees.add(cellInc);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("QTE ATTACHEMENT", listCoordonees);
		listCoordonees = new ArrayList<>();

		// QTE CUMUL
		cellInc = cellEnd + 1;
		cellEnd = cellEnd + 2;
		creationCellules(sheet, cellStyle, "QTE CUMUL", rowStart, rowEnd, cellInc, cellEnd);

		listCoordonees.add(cellInc);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("QTE CUMUL", listCoordonees);
		listCoordonees = new ArrayList<>();

		
		if (Constantes.GlobalConstant.CONST_V.equalsIgnoreCase(type)) {
			// MONTANT ATTACHE HT
			cellInc = cellEnd + 1;
			cellEnd = cellEnd + 2;
			creationCellules(sheet, cellStyle, "MONTANT ATTACHE HT", rowStart, rowEnd, cellInc, cellEnd);
	
			listCoordonees.add(cellInc);
			listCoordonees.add(cellEnd);
			mapColumnCoordonees.put("MONTANT ATTACHE HT", listCoordonees);
			listCoordonees = new ArrayList<>();
		}

		return mapColumnCoordonees;
	}

	/**
	 * DESCRIPTION : 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param editionAttachementDTO
	 * @param wb
	 * @param sheet
	 * @param rowStart
	 * @param mapColumnCoordonees
	 * @throws Exception
	 */
	private void detailTabAttachement(EditionAttachementDTO editionAttachementDTO ,XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, Map<String, List<Integer>> mapColumnCoordonees)
			throws Exception {
		XSSFCellStyle cellStyle = getStyleTable(wb, false, ConstantesExcel.LEFT, null, 10, 1, 1, 1, 1);
		
		XSSFCellStyle cellNumbreStyle = getStyleTable(wb, false, ConstantesExcel.RIGHT, null, 10, 1, 1, 1, 1);
		CreationHelper ch = wb.getCreationHelper();
		cellNumbreStyle.setDataFormat(ch.createDataFormat().getFormat(ConstantesExcel.FORMAT_CURRENCY));
		
		XSSFCellStyle cellStyleBackGrandColor = getStyleTable(wb, false, ConstantesExcel.RIGHT, ConstantesExcel.FOOTER_TAB_GRIS, 10, 1, 1, 1, 1);
		XSSFCellStyle cellStyleBackGrandColorLeft = getStyleTable(wb, false, ConstantesExcel.LEFT, ConstantesExcel.FOOTER_TAB_GRIS, 10, 1, 1, 1, 1);
		
		List<Integer> listCoordonees = new ArrayList<>();
		int rowPositions = rowStart;
		
		List<BonReceptionDTO> listBonReception = editionAttachementDTO.getListBonReception();
		String type = editionAttachementDTO.getTypeEdition();
		for (BonReceptionDTO bonReceptionDTO : listBonReception) {
			
			listCoordonees = mapColumnCoordonees.get("DESIGNIATION");
			creationCellules(sheet, cellStyle, bonReceptionDTO.getDesigniationBonReception(), rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			
			listCoordonees = mapColumnCoordonees.get("UNITE");
			creationCellules(sheet, cellStyle, bonReceptionDTO.getUnite(), rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			
			if (Constantes.GlobalConstant.CONST_V.equalsIgnoreCase(type)) {
				listCoordonees = mapColumnCoordonees.get("PRIX UNIT");
				creationCellules(sheet, cellNumbreStyle, bonReceptionDTO.getPrixUnitaire(), rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			}
			listCoordonees = mapColumnCoordonees.get("QTE ATTACHEMENT");
			Double qteAttachement = null != bonReceptionDTO.getQteAttachement() ? bonReceptionDTO.getQteAttachement() : 0;
			creationCellules(sheet, cellNumbreStyle, qteAttachement, rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			

			listCoordonees = mapColumnCoordonees.get("QTE CUMUL");
			Double qte = null != bonReceptionDTO.getQte() ? bonReceptionDTO.getQte() : 0;
			creationCellules(sheet, cellNumbreStyle, qte, rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			
			if (Constantes.GlobalConstant.CONST_V.equalsIgnoreCase(type)) {
				listCoordonees = mapColumnCoordonees.get("MONTANT ATTACHE HT");
				creationCellules(sheet, cellNumbreStyle, bonReceptionDTO.getMontant(), rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			}
			rowPositions++;

		}
		
		// TOTAL
		if (Constantes.GlobalConstant.CONST_V.equalsIgnoreCase(type)) {
			listCoordonees = mapColumnCoordonees.get("DESIGNIATION");
			creationCellules(sheet, cellStyleBackGrandColorLeft, "TOTAL", rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			
			listCoordonees = mapColumnCoordonees.get("UNITE");
			creationCellules(sheet, cellStyleBackGrandColor, null, rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			
			listCoordonees = mapColumnCoordonees.get("PRIX UNIT");
			creationCellules(sheet, cellStyleBackGrandColor, null, rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			
			listCoordonees = mapColumnCoordonees.get("QTE ATTACHEMENT");
			creationCellules(sheet, cellStyleBackGrandColor, null, rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			
			listCoordonees = mapColumnCoordonees.get("QTE CUMUL");
			creationCellules(sheet, cellStyleBackGrandColor, null, rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			
			listCoordonees = mapColumnCoordonees.get("MONTANT ATTACHE HT");
			cellStyleBackGrandColor.setDataFormat(ch.createDataFormat().getFormat("#,##0.00;\\-#,##0.00"));
			String value = "FORMULE:SUM(N"+(rowStart+1)+":O"+rowPositions+")";
			creationCellules(sheet, cellStyleBackGrandColor, value, rowPositions, rowPositions, listCoordonees.get(0), listCoordonees.get(1));
			
		}

	}
	
	/**
	 * DESCRIPTION : 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param wb
	 * @param sheet
	 * @param rowStart
	 * @param cellStart
	 * @param cellEnd
	 * @param cellStart2
	 * @param cellEnd2
	 * @throws Exception
	 */
	private void footer(XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int cellStart, int cellEnd,
			int cellStart2, int cellEnd2) throws Exception{
		XSSFRow row = null;
		XSSFCell cell;
		XSSFCellStyle cellStyle = getStyleTable(wb, true, ConstantesExcel.LEFT, null, 10, 0, 0, 0, 0);
		
		row = sheet.createRow(rowStart);
		for (int i = cellStart; i <= cellEnd; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
		}
		cell = row.getCell(cellStart);
		cell.setCellValue(new XSSFRichTextString("VISA TITULAIRE"));

		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowStart, cellStart, cellEnd));

		int cellNum = cellStart2;
		row = sheet.getRow(rowStart);
		cell = row.createCell(cellNum++);
		cell.setCellValue(new XSSFRichTextString("VISA MARSA MAROC"));
		cell.setCellStyle(cellStyle);
		cell = row.createCell(cellNum++);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(cellNum++);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(cellNum);
		cell.setCellStyle(cellStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowStart, cellStart2, cellNum));
		
	}
	
	/**
	 * DESCRIPTION : 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 *
	 * @return
	 */
	private XSSFColor getColorDecode(String decode) {
		return new XSSFColor(Color.decode(decode));
	}


	/**
	 * DESCRIPTION : Methode permet de 
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 *
	 * @param wb
	 * @param underline
	 * @param bold
	 * @param border
	 * @param align
	 * @param body
	 * @param foregroundColor
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private XSSFCellStyle getStyleTable(XSSFWorkbook wb, boolean blod,
			HorizontalAlignment alignment, String foregroundColor, int fontHeight, int borderLeft, int borderRight, int borderTop,
			int borderBottom) throws Exception {

		XSSFCellStyle cellStyle = wb.createCellStyle();
		XSSFFont fontEntete = wb.createFont();

		fontEntete.setFontName(ConstantesExcel.FONT_NAME);
		fontEntete.setFontHeightInPoints((short) fontHeight);
		fontEntete.setColor(HSSFColor.BLACK.index);
		if (blod) {
			fontEntete.setBold(true);
		}
		if (null != alignment) {
			cellStyle.setAlignment(alignment);
		}
		
		if (StringUtils.isNotBlank(foregroundColor)) {
			cellStyle.setFillForegroundColor(getColorDecode(foregroundColor));
			cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
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
	
	/**
	 * DESCRIPTION : Methode permet de créer les cellules d'un feuille excel
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param sheet
	 * @param cellStyle
	 * @param val
	 * @param rowStart
	 * @param rowEnd
	 * @param cellStart
	 * @param cellEnd
	 */
	private void creationCellules(XSSFSheet sheet, XSSFCellStyle cellStyle, Object val, int rowStart, int rowEnd, int cellStart, int cellEnd ){
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int i = rowStart; i <= rowEnd; ++i) {
			row = sheet.getRow(i);
			if (null == row) {
				row = sheet.createRow(i);
			}
			for (int j = cellStart; j <= cellEnd; j++) {
				cell = row.getCell(j);
				if (null == cell) {
					cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
				}
			}
		}
		if (rowStart != rowEnd || cellStart != cellEnd) {
			sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd, cellStart, cellEnd));			
		}
		if (null != val) {
			row = sheet.getRow(rowStart);
			cell = row.getCell(cellStart);
			if (val instanceof String) {
				String value =  (String) val;
				if (value.contains("FORMULE")) {
					cell.setCellFormula(value.replace("FORMULE:", ""));
				}else{
					cell.setCellValue(new XSSFRichTextString((String) val));					
				}
			}else if (val instanceof Double){
				cell.setCellValue((Double) val);
			}
			
		}
	}
	
	/**
	 * DESCRIPTION : Methode permet de resize column excel
	 * DATE DERNIERE MODIF : 24 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param sheet
	 */
	private void sizeCellule(XSSFSheet sheet){
		sheet.setColumnWidth((short) 0, (short) 2000);
		sheet.setColumnWidth((short) 1, (short) 2000);
		sheet.setColumnWidth((short) 2, (short) 2000);
		sheet.setColumnWidth((short) 3, (short) 2500);
		sheet.setColumnWidth((short) 4, (short) 2500);
		sheet.setColumnWidth((short) 5, (short) 2000);
		sheet.setColumnWidth((short) 6, (short) 2100);
		sheet.setColumnWidth((short) 7, (short) 1600);
		sheet.setColumnWidth((short) 8, (short) 1600);
		sheet.setColumnWidth((short) 9, (short) 1500);
		sheet.setColumnWidth((short) 10, (short) 1500);
		sheet.setColumnWidth((short) 11, (short) 1500);
		sheet.setColumnWidth((short) 12, (short) 1500);
		sheet.setColumnWidth((short) 13, (short) 1700);
		sheet.setColumnWidth((short) 14, (short) 1700);
	}


}