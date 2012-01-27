package jexcel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;


public final class JExcel extends Object {
    
    static public final Integer SHEET = 0;  // sheet 1
    static public final Integer CELL = 2;  // cell 3

    /**
     * creates an {@link HSSFWorkbook} the specified OS filename.
     */
    private static HSSFWorkbook readFile(String filename) throws IOException {
        return new HSSFWorkbook(new FileInputStream(filename));
    }

    /**
     * Method main
     *
     * Given 1 argument takes that as the filename, inputs it and dumps the
     * cell values/types out to sys.out.<br/>
     *
     * given 2 arguments where the second argument is the word "write" and the
     * first is the filename - writes out a sample (test) spreadsheet
     * see {@link HSSFReadWrite#testCreateSampleSheet(String)}.<br/>
     *
     * given 2 arguments where the first is an input filename and the second
     * an output filename (not write), attempts to fully read in the
     * spreadsheet and fully write it out.<br/>
     *
     * given 3 arguments where the first is an input filename and the second an
     * output filename (not write) and the third is "modify1", attempts to read in the
     * spreadsheet, deletes rows 0-24, 74-99.  Changes cell at row 39, col 3 to
     * "MODIFIED CELL" then writes it out.  Hence this is "modify test 1".  If you
     * take the output from the write test, you'll have a valid scenario.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("At least one argument expected");
            return;
        }

        String fileName = args[0];
        try {
            HSSFWorkbook wb = JExcel.readFile(fileName);

            HSSFSheet sheet = wb.getSheetAt(JExcel.SHEET);
            int rows = sheet.getPhysicalNumberOfRows();
            System.out.println("Sheet " + JExcel.SHEET + " has " + rows +
                    " row(s).");
            for (int r = 0; r < rows; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    System.out.println("Row " + r + " is empty. " +
                            "Continuing.");
                    continue;
                }

                int cells = row.getPhysicalNumberOfCells();
                System.out.println("Row " + r + " has " + cells +
                        " cell(s).");

                if (cells < 3) {
                    System.out.println("This row has less than 3 cells." +
                            "Continuing.");
                    continue;
                }

                String value = null;
                HSSFCell cell = null;
                try {
                    cell = row.getCell(JExcel.CELL);
                    switch (cell.getCellType()) {
                                                               
                        case HSSFCell.CELL_TYPE_FORMULA:
                            value = cell.getCellFormula();
                            break;
                                                                                   
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            value = Integer.toString(
                                    ((Double)cell.getNumericCellValue()).
                                    intValue());
                            break;
                                                                                   
                        case HSSFCell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                                                                                   
                        default:
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Continuing.");
                    continue;
                }
                System.out.println("CELL col=" + cell.getColumnIndex() +
                        " VALUE=" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}