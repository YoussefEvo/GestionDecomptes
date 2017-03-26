package ma.co.marsamaroc.gestion.decomptes.edition.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AttachementNoValoriseExcel {

	private static final String FONT_NAME = "Calibri";
	private static final short FONT_HEIGHT = 10 * 20;
	private static final short CENTER = XSSFCellStyle.ALIGN_CENTER;
	private static final short LEFT = XSSFCellStyle.ALIGN_LEFT;
	private static final short RIGHT = XSSFCellStyle.ALIGN_RIGHT;

	public static void main(String[] args) throws Exception {
		String xslAbsoluteLocation = "E:/test.xlsx";

		// creation du fichier excel
		XSSFWorkbook wb = new XSSFWorkbook();
		int rowPosition = 0;
		int cellPosition = 0;
		int cellPosTotal = 0;

		// Nom du feuille de donnees
		String libelleSheet = "ATTACHEMENT NON VALORISE";
		XSSFSheet sheet = wb.createSheet(libelleSheet);
		XSSFRow row = null;
		XSSFCell cell = null;

		// paramétrage logo marsa maroc
		int rowStart = 1;
		int rowEnd = 4;
		int cellStart = 1;
		int cellEnd = 3;
		// paramétrage deuxiéme partie
		int cellStart2 = 7;
		int cellEnd2 = 9;
		// paramétrage saute de page
		int sauteLigne = 4;
		// paramétrage en-tete tableau
		int sizeHeaderTab = 1;
		// Style
		// XSSFCellStyle cellStyleBorderGras = getStyleTable(wb, true, LEFT, 1,
		// 1, 1, 1);

		// Entete d'attachement
		rowPosition = enteteAttachement(wb, sheet, rowStart, rowEnd, cellStart,
				cellEnd, cellStart2, cellEnd2, sauteLigne);
		rowPosition = rowPosition + sauteLigne;
		rowEnd = rowPosition + sizeHeaderTab;
		detailAttachement(wb, sheet, rowPosition, rowEnd, cellStart);
		rowEnd = rowEnd + 20 + 1 + sauteLigne;
		footer(wb, sheet, rowEnd, cellStart, cellEnd, cellStart2, cellEnd2);

		FileOutputStream fileOut = new FileOutputStream(xslAbsoluteLocation);
		wb.write(fileOut);
		fileOut.close();

	}

	public static int enteteAttachement(XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int rowEnd, int cellStart, int cellEnd,
			int cellStart2, int cellEnd2, int sauteLigne) throws Exception {
		premiereLigne(wb, sheet, rowStart, rowEnd, cellStart, cellEnd,
				cellStart2, cellEnd2);
		rowStart = rowEnd + sauteLigne;
		rowEnd = rowStart + 5;
		return derniereLigne(wb, sheet, rowStart, rowEnd, cellStart,
				cellStart2, cellEnd2);
	}

	public static void detailAttachement(XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int rowEnd, int cellStart) throws Exception {
		Map<String, List<Integer>> mapColumnCoordonees = enteteTabAttachement(
				wb, sheet, rowStart, rowEnd, cellStart);
		detailTabAttachement(wb, sheet, (rowEnd + 1), mapColumnCoordonees);
	}

	public static void premiereLigne(XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int rowEnd, int cellStart, int cellEnd,
			int cellStart2, int cellEnd2) throws Exception {

		XSSFRow row = null;
		XSSFCell cell;
		// Style
		XSSFCellStyle cellStyleBorderGras = getStyleTable(wb, true, LEFT, 1, 1,
				1, 1);

		for (int i = rowStart; i <= rowEnd; ++i) {
			row = sheet.createRow(i);
			for (int j = cellStart; j <= cellEnd; j++) {
				cell = row.createCell(j);
				// cell.setCellStyle(getStyleTable(wb, true, 0, 2, 2, 2, 2));
			}
		}
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd, cellStart,
				cellEnd));

		// FileInputStream obtains input bytes from the image file
		InputStream inputStream = new FileInputStream("E:/t26d9aojmj.png");
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
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		// IMAGE END

		// ISO
		int rowIso = (rowEnd - rowStart) / 2 + rowStart;
		row = sheet.getRow(rowIso);

		for (int i = cellStart2; i <= cellEnd2; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(cellStyleBorderGras);
		}
		cell = row.getCell(cellStart2);
		cell.setCellValue(new XSSFRichTextString("EN.ATC.GE.AMAP.10"));

		sheet.addMergedRegion(new CellRangeAddress(rowIso, rowIso, cellStart2,
				cellEnd2));
		// ISO END

	}

	public static int derniereLigne(XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int rowEnd, int cellStart, int cellStart2,
			int cellEnd2) throws Exception {
		XSSFRow row = null;
		XSSFCell cell;
		// TABLE HEADER

		// Style
		XSSFCellStyle cellStyle = getStyleTable(wb, true, LEFT, 1, 1, 1, 1);

		int cellNum = 0;
		int cellNumSec = 0;
		// Tracer TABLEAU
		for (int i = rowStart; i <= rowEnd; ++i) {
			cellNum = cellStart;
			// LIBELLE
			row = sheet.createRow(i);
			cell = row.createCell(cellNum++);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(cellNum);
			cell.setCellStyle(cellStyle);
			sheet.addMergedRegion(new CellRangeAddress(i, i, cellStart, cellNum));
			// VALUE
			cellNumSec = cellNum + 1;
			cell = row.createCell(cellNumSec++);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(cellNumSec++);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(cellNumSec);
			cell.setCellStyle(cellStyle);
			sheet.addMergedRegion(new CellRangeAddress(i, i, cellNum + 1,
					cellNumSec));

		}

		cellNumSec = cellStart + 2;
		int rowInc = rowStart;

		row = sheet.getRow(rowInc++);
		cell = row.getCell(cellStart);
		cell.setCellValue(new XSSFRichTextString("SITE"));
		// SITE VALUE
		cell = row.getCell(cellNumSec);
		cell.setCellValue(new XSSFRichTextString("309500"));

		// MARCHE LIBELLE
		row = sheet.getRow(rowInc++);
		cell = row.getCell(cellStart);
		cell.setCellValue(new XSSFRichTextString("MARCHE N°"));
		// MARCHE VALUE
		cell = row.getCell(cellNumSec);
		cell.setCellValue(new XSSFRichTextString("06/DG2016"));

		// INTITULE MARCHE LIBELLE
		row = sheet.getRow(rowInc++);
		cell = row.getCell(cellStart);
		cell.setCellValue(new XSSFRichTextString("INTITULE MARCHE"));
		// INTITULE MARCHE VALUE
		cell = row.getCell(cellNumSec);
		cell.setCellValue(new XSSFRichTextString(""));

		// TITULAIRE LIBELLE
		row = sheet.getRow(rowInc++);
		cell = row.getCell(cellStart);
		cell.setCellValue(new XSSFRichTextString("TITULAIRE"));
		// TITULAIRE VALUE
		cell = row.getCell(cellNumSec);
		cell.setCellValue(new XSSFRichTextString("BDO"));

		// TRAVAUX REALISES LIBELLE
		row = sheet.getRow(rowInc++);
		cell = row.getCell(cellStart);
		cell.setCellValue(new XSSFRichTextString("TRAVAUX REALISES"));
		// TITULAIRE VALUE
		cell = row.getCell(cellNumSec);
		cell.setCellValue(new XSSFRichTextString("DU 05/09/2016 AU 22/09/2016"));

		// BR N° LIBELLE
		row = sheet.getRow(rowInc++);
		cell = row.getCell(cellStart);
		cell.setCellValue(new XSSFRichTextString("BR N°"));
		// TITULAIRE VALUE
		cell = row.getCell(cellNumSec);
		cell.setCellValue(new XSSFRichTextString("1007440"));

		cellNum = cellStart2;
		rowInc = (rowEnd - rowStart) / 2 + rowStart;
		row = sheet.getRow(rowInc);
		cell = row.createCell(cellNum++);
		cell.setCellValue(new XSSFRichTextString("ATTACHEMENT N° : 03/2016 ET DERNIER"));
		cell.setCellStyle(cellStyle);
		cell = row.createCell(cellNum++);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(cellNum++);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(cellNum);
		cell.setCellStyle(cellStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowInc, rowInc, cellStart2,
				cellNum));
		return rowEnd;

	}

	public static Map<String, List<Integer>> enteteTabAttachement(
			XSSFWorkbook wb, XSSFSheet sheet, int rowStart, int rowEnd,
			int cellStart) throws Exception {

		Map<String, List<Integer>> mapColumnCoordonees = new HashMap<>();
		List<Integer> listCoordonees = new ArrayList<>();

		XSSFRow row = null;
		XSSFCell cell;
		// Style
		XSSFCellStyle cellStyle = getStyleTable(wb, true, CENTER, 1, 1, 1, 1);
		int cellInc = 0;
		int cellEnd = cellStart + 5;
		for (int i = rowStart; i <= rowEnd; ++i) {
			row = sheet.createRow(i);
			for (int j = cellStart; j <= cellEnd; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
			}
		}
		row = sheet.getRow(rowStart);
		cell = row.getCell(cellStart);
		cell.setCellValue(new XSSFRichTextString("DESIGNIATION"));
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd, cellStart,
				cellEnd));
		listCoordonees.add(cellStart);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("DESIGNIATION", listCoordonees);
		listCoordonees = new ArrayList<>();

		// UNITE
		cellEnd++;
		for (int i = rowStart; i <= rowEnd; ++i) {
			row = sheet.getRow(i);
			cell = row.createCell(cellEnd);
			cell.setCellStyle(cellStyle);
		}
		row = sheet.getRow(rowStart);
		cell = row.getCell(cellEnd);
		cell.setCellValue(new XSSFRichTextString("UNITE"));
		cell.setCellStyle(cellStyle);
		if (rowStart != rowEnd) {
			sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd,
					cellEnd, cellEnd));
		}

		listCoordonees.add(cellEnd);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("UNITE", listCoordonees);
		listCoordonees = new ArrayList<>();

		// PRIX UNIT
		cellInc = cellEnd + 1;
		cellEnd = cellEnd + 2;
		for (int i = rowStart; i <= rowEnd; ++i) {
			row = sheet.getRow(i);
			for (int j = cellInc; j <= cellEnd; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
			}
		}
		row = sheet.getRow(rowStart);
		cell = row.getCell(cellInc);
		cell.setCellValue(new XSSFRichTextString("PRIX UNIT"));
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd, cellInc,
				cellEnd));

		listCoordonees.add(cellInc);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("PRIX UNIT", listCoordonees);
		listCoordonees = new ArrayList<>();

		// QTE ATTACHEMENT
		cellInc = cellEnd + 1;
		cellEnd = cellEnd + 2;
		for (int i = rowStart; i <= rowEnd; ++i) {
			row = sheet.getRow(i);
			for (int j = cellInc; j <= cellEnd; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
			}
		}
		row = sheet.getRow(rowStart);
		cell = row.getCell(cellInc);
		cell.setCellValue(new XSSFRichTextString("QTE ATTACHEMENT"));
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd, cellInc,
				cellEnd));

		listCoordonees.add(cellInc);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("QTE ATTACHEMENT", listCoordonees);
		listCoordonees = new ArrayList<>();

		// QTE CUMUL
		cellInc = cellEnd + 1;
		cellEnd = cellEnd + 2;
		for (int i = rowStart; i <= rowEnd; ++i) {
			row = sheet.getRow(i);
			for (int j = cellInc; j <= cellEnd; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
			}
		}
		row = sheet.getRow(rowStart);
		cell = row.getCell(cellInc);
		cell.setCellValue(new XSSFRichTextString("QTE CUMUL"));
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd, cellInc,
				cellEnd));

		listCoordonees.add(cellInc);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("QTE CUMUL", listCoordonees);
		listCoordonees = new ArrayList<>();

		// MONTANT ATTACHE HT
		cellInc = cellEnd + 1;
		cellEnd = cellEnd + 2;
		for (int i = rowStart; i <= rowEnd; ++i) {
			row = sheet.getRow(i);
			for (int j = cellInc; j <= cellEnd; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
			}
		}
		row = sheet.getRow(rowStart);
		cell = row.getCell(cellInc);
		cell.setCellValue(new XSSFRichTextString("MONTANT ATTACHE HT"));
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowEnd, cellInc,
				cellEnd));

		listCoordonees.add(cellInc);
		listCoordonees.add(cellEnd);
		mapColumnCoordonees.put("MONTANT ATTACHE HT", listCoordonees);
		listCoordonees = new ArrayList<>();

		return mapColumnCoordonees;
	}

	public static void detailTabAttachement(XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, Map<String, List<Integer>> mapColumnCoordonees)
			throws Exception {
		XSSFRow row = null;
		XSSFCell cell;
		XSSFCellStyle cellStyle = getStyleTable(wb, false, LEFT, 1, 1, 1, 1);
		XSSFCellStyle cellNumbreStyle = getStyleTable(wb, false, RIGHT, 1, 1,
				1, 1);
		List<Integer> listCoordonees = new ArrayList<>();
		int rowPositions = rowStart;
		for (int i = 1; i <= 20; ++i) {

			listCoordonees = mapColumnCoordonees.get("DESIGNIATION");
			row = sheet.createRow(rowPositions);
			for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
			}
			row = sheet.getRow(rowPositions);
			cell = row.getCell(listCoordonees.get(0));
			cell.setCellValue(new XSSFRichTextString("DESIGNIATION " + i));
			// row.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());
			sheet.addMergedRegion(new CellRangeAddress(rowPositions,
					rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

			listCoordonees = mapColumnCoordonees.get("UNITE");
			row = sheet.getRow(rowPositions);
			for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
			}
			row = sheet.getRow(rowPositions);
			cell = row.getCell(listCoordonees.get(0));
			cell.setCellValue(new XSSFRichTextString("UNITE" + i));

			listCoordonees = mapColumnCoordonees.get("PRIX UNIT");
			row = sheet.getRow(rowPositions);
			for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellNumbreStyle);
			}
			row = sheet.getRow(rowPositions);
			cell = row.getCell(listCoordonees.get(0));
			cell.setCellValue(i);
			sheet.addMergedRegion(new CellRangeAddress(rowPositions,
					rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

			listCoordonees = mapColumnCoordonees.get("QTE ATTACHEMENT");
			row = sheet.getRow(rowPositions);
			for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellNumbreStyle);
			}
			row = sheet.getRow(rowPositions);
			cell = row.getCell(listCoordonees.get(0));
			cell.setCellValue(i);
			sheet.addMergedRegion(new CellRangeAddress(rowPositions,
					rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

			listCoordonees = mapColumnCoordonees.get("QTE CUMUL");
			row = sheet.getRow(rowPositions);
			for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellNumbreStyle);
			}
			row = sheet.getRow(rowPositions);
			cell = row.getCell(listCoordonees.get(0));
			cell.setCellValue(i);
			sheet.addMergedRegion(new CellRangeAddress(rowPositions,
					rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

			listCoordonees = mapColumnCoordonees.get("MONTANT ATTACHE HT");
			row = sheet.getRow(rowPositions);
			for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellNumbreStyle);
			}
			row = sheet.getRow(rowPositions);
			cell = row.getCell(listCoordonees.get(0));
			cell.setCellValue(i);
			sheet.addMergedRegion(new CellRangeAddress(rowPositions,
					rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

			rowPositions++;

		}
		
		// TOTAL
		
		listCoordonees = mapColumnCoordonees.get("DESIGNIATION");
		row = sheet.createRow(rowPositions);
		for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
			cell = row.createCell(j);
			cell.setCellStyle(cellStyle);
		}
		row = sheet.getRow(rowPositions);
		cell = row.getCell(listCoordonees.get(0));
		cell.setCellValue(new XSSFRichTextString("TOTAL"));
		// row.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());
		sheet.addMergedRegion(new CellRangeAddress(rowPositions,
				rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

		listCoordonees = mapColumnCoordonees.get("UNITE");
		row = sheet.getRow(rowPositions);
		for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
			cell = row.createCell(j);
			cell.setCellStyle(cellStyle);
		}
		row = sheet.getRow(rowPositions);
		cell = row.getCell(listCoordonees.get(0));
		cell.setCellValue(new XSSFRichTextString(""));

		listCoordonees = mapColumnCoordonees.get("PRIX UNIT");
		row = sheet.getRow(rowPositions);
		for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
			cell = row.createCell(j);
			cell.setCellStyle(cellNumbreStyle);
		}
		row = sheet.getRow(rowPositions);
		cell = row.getCell(listCoordonees.get(0));
		cell.setCellValue(new XSSFRichTextString(""));
		sheet.addMergedRegion(new CellRangeAddress(rowPositions,
				rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

		listCoordonees = mapColumnCoordonees.get("QTE ATTACHEMENT");
		row = sheet.getRow(rowPositions);
		for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
			cell = row.createCell(j);
			cell.setCellStyle(cellNumbreStyle);
		}
		row = sheet.getRow(rowPositions);
		cell = row.getCell(listCoordonees.get(0));
		cell.setCellValue(new XSSFRichTextString(""));
		sheet.addMergedRegion(new CellRangeAddress(rowPositions,
				rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

		listCoordonees = mapColumnCoordonees.get("QTE CUMUL");
		row = sheet.getRow(rowPositions);
		for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
			cell = row.createCell(j);
			cell.setCellStyle(cellNumbreStyle);
		}
		row = sheet.getRow(rowPositions);
		cell = row.getCell(listCoordonees.get(0));
		cell.setCellValue(new XSSFRichTextString(""));
		sheet.addMergedRegion(new CellRangeAddress(rowPositions,
				rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

		listCoordonees = mapColumnCoordonees.get("MONTANT ATTACHE HT");
		row = sheet.getRow(rowPositions);
		for (int j = listCoordonees.get(0); j <= listCoordonees.get(1); j++) {
			cell = row.createCell(j);
			cell.setCellStyle(cellNumbreStyle);
		}
		row = sheet.getRow(rowPositions);
		cell = row.getCell(listCoordonees.get(0));
		cell.setCellValue(124591);
		sheet.addMergedRegion(new CellRangeAddress(rowPositions,
				rowPositions, listCoordonees.get(0), listCoordonees.get(1)));

	}
	
	public static void footer(XSSFWorkbook wb, XSSFSheet sheet,
			int rowStart, int cellStart, int cellEnd,
			int cellStart2, int cellEnd2) throws Exception{
		XSSFRow row = null;
		XSSFCell cell;
		XSSFCellStyle cellStyle = getStyleTable(wb, false, LEFT,  1, 1, 1, 1);
		
		row = sheet.createRow(rowStart);
		for (int i = cellStart; i <= cellEnd; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
		}
		cell = row.getCell(cellStart);
		cell.setCellValue(new XSSFRichTextString("VISA TITULAIRE"));

		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowStart, cellStart,
				cellEnd));

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
		sheet.addMergedRegion(new CellRangeAddress(rowStart, rowStart, cellStart2,
				cellNum));
		
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

	@SuppressWarnings("unused")
	public static XSSFCellStyle getStyleTable(XSSFWorkbook wb, boolean blod,
			int alignment, int borderLeft, int borderRight, int borderTop,
			int borderBottom) throws Exception {

		XSSFCellStyle cellStyle = wb.createCellStyle();
		XSSFFont fontEntete = wb.createFont();

		fontEntete.setFontName(FONT_NAME);
		fontEntete.setFontHeight(FONT_HEIGHT);
		fontEntete.setColor(HSSFColor.BLACK.index);
		if (blod) {
			fontEntete.setBold(true);
		}
		if (alignment != 0) {
			cellStyle.setAlignment((short) alignment);
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