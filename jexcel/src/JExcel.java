package jexcel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public final class JExcel extends Object {
    
    static public final Integer SHEET = 0;  // sheet 1
    static public final Integer CELL = 2;  // cell 3

    /** 
     * Process a Microsoft Excel file, extracts MSISDN content and returns it as
     * a list of Strings.
     *
     * @param filePath  The filepaht to process.
     * @param ignoreHeaderRow   Indicates if must ignore first row because it
     * is a header row. 
     *
     * @return List<String>
    */

    static public List<String> ExtractMSISDNFromExcel() {
        return new ArrayList<String>();
    }

    static public List<String> ExtractMSISDNFromExcel(String filePath) {
        return JExcel.ExtractMSISDNFromExcel(filePath, false);
    }

    static public List<String> ExtractMSISDNFromExcel(String filePath,
            boolean ignoreHeaderRow) { 
        
        List<String> msisdns = new ArrayList<String>();

        try {
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
                                                                                    
            HSSFSheet sheet = wb.getSheetAt(JExcel.SHEET);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int r = ignoreHeaderRow ? 1 : 0; r < rows; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                                                                                    
                if (row.getPhysicalNumberOfCells() < 3) {
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
                    System.out.println("There was a problem processing a row:");
                    e.printStackTrace();
                    continue;
                }

                msisdns.add(value);
            }
        } catch (Exception e) {
            System.out.println("There was a problem processing a the file '" +
                    filePath + "':");
            e.printStackTrace();
        }

        return msisdns;
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

        System.out.println("Extracting MSISDNs...");
        List<String> msisdns = JExcel.ExtractMSISDNFromExcel(args[0], true);
        System.out.println("Done.");

        Iterator iter = msisdns.iterator();
        while (iter.hasNext())
            System.out.println("MSISDN: " + iter.next());
        }
}
