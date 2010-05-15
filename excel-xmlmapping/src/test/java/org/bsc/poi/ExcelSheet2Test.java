/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bsc.poi;

import java.io.File;
import java.text.SimpleDateFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.SimpleValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author softphone
 */
public class ExcelSheet2Test {
    XSSFWorkbook wb;

    java.io.Reader xmlSource;

    @Before
    public void openWorkbook() throws Exception  {
        File file = new File("src/test/resources/TestImportXml2.xlsx");
        File fileXml = new File("src/test/resources/TestImportData.xml");


        wb = new XSSFWorkbook(file.getAbsolutePath());// load an XLSX file with mapping informations

        xmlSource = new java.io.FileReader( fileXml );
    }

    @After
    public void closeAll() throws Exception {
        if( null!=xmlSource ) {
            xmlSource.close();
        }
    }


    @Test
    public void mappingXml() throws Exception {
        WorkbookUtils.registerDateTimeConverter( new DateTimeConverter());

        WorkbookUtils.importXML(wb, xmlSource, new java.io.FileOutputStream("target/testImportXml2out.xlsx"));


    }

}
