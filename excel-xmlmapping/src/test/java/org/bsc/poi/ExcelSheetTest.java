package org.bsc.poi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.PackageRelationshipCollection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.SimpleValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCell;
import static org.junit.Assert.*;
/**
 *
 * @author softphone
 */

public class ExcelSheetTest {

    XSSFWorkbook wb;
    
    java.io.Reader xmlSource;

    @Before
    public void openWorkbook() throws Exception  {
        File file = new File("src/test/resources/TestImportXml.xlsx");
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

    //@Test
    public void loadSheet() throws Exception {

        java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("TestImportXml.xls");

        HSSFWorkbook workbook = new HSSFWorkbook(is);

        for( Object obj : workbook.getAllEmbeddedObjects() ) {
            System.out.printf( "object [%s]\n", obj.toString());

        }
        int numOfSheets = workbook.getNumberOfSheets();
        assertTrue( numOfSheets>=1 );

        HSSFSheet sheet = workbook.getSheetAt(0);
        assertNotNull(sheet);


        System.out.printf( "sheet [%s]\n", sheet.toString());

        int i=0;
        for( Row r : sheet ) {

            HSSFRow rr = (HSSFRow) r;

            for( Cell c: r ) {

                HSSFCell cc = (HSSFCell) c;

                System.out.printf( "[%s][%s]", c.getStringCellValue(), c.getCellComment() );
            }
            System.out.println();
            break;

        }

        workbook.write( new java.io.FileOutputStream( new File( "target/result.xls")) );
    }


    @Test
    public void getSheetTableRelations() throws Exception {

        int numOfSheets = wb.getNumberOfSheets();
        assertTrue( numOfSheets>=1 );

        int i=0;
        for( XSSFSheet sheet : wb ) {

            System.out.println( wb.getSheetName(i));

            PackageRelationshipCollection prc = sheet.getPackagePart().getRelationshipsByType("http://schemas.openxmlformats.org/officeDocument/2006/relationships/table");

            for( PackageRelationship pr : prc ) {

                System.out.printf("\trelation collection [%s]\n", pr);

            }


            for( POIXMLDocumentPart doc : sheet.getRelations() ) {

                System.out.printf("\trelations [%s]\n", doc.getPackagePart());

            }

            ++i;
        }
    }

    //@Test
    public void getContent() throws Exception {

        int numOfSheets = wb.getNumberOfSheets();
        assertTrue( numOfSheets>=1 );

        XSSFSheet sheet = wb.getSheetAt(0);
        assertNotNull(sheet);

        sheet.getRelations();
        System.out.printf( "sheet [%s]\n", sheet.toString());

        int i=0;
        for( Row r : sheet ) {

            XSSFRow rr = (XSSFRow) r;


            for( Cell c: r ) {

                XSSFCell cc = (XSSFCell) c;


                CTCell ctc = cc.getCTCell();

                System.out.printf( "[%s]V(%s)S(%s)]|", c.getStringCellValue(), ctc.getV(),ctc.getS() );
            }
            System.out.println();

        }

    }

    //@Test
    public void getWorkbookRelations() throws Exception {

        ArrayList<PackagePart> tablesSC = wb.getPackage().getPartsByContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.tableSingleCells+xml");

        for( PackagePart tableSC : tablesSC ) {

            System.out.printf( "tableSC [%s]\n", tableSC.getPartName() ) ;
        }


        for(POIXMLDocumentPart p : wb.getRelations()){
/*
            if(p instanceof MapInfo){

                mapInfo = (MapInfo) p;
                XSSFMap map = mapInfo.getXSSFMapById(mapId);
                XSSFExportToXml exporter = new XSSFExportToXml(map);

                ByteArrayOutputStream os = new ByteArrayOutputStream();

                exporter.exportToXML(os,true);

                String xml = os.toString("UTF-8");

                System.out.println(xml);
            }
 */
                System.out.printf( "class: [%s] -[%s]\n", p.getClass().getName(), p.toString() );

                if( p.getPackagePart().getPartName().getName().equals("/xl/connections.xml")) {

                    java.io.InputStream is = p.getPackagePart().getInputStream();

                    int c;
                    while( (c=is.read())!=-1 ) {

                        System.out.print((char)c);
                    }
                }
                System.out.println();

        }

    }

    //@Test
    public void testSelectPath() {
        /*
        String queryExpression =
                "declare namespace main='http://schemas.openxmlformats.org/spreadsheetml/2006/main'" +
                "$this//main:tableColumn";

        System.out.println( "==> "+ table.selectPath(queryExpression).length);

        printChildren( table.getDomNode() );
        */

    }

    @Test
    public void mappingXml() throws Exception {

        WorkbookUtils.registerDateTimeConverter( new DateTimeConverter());

        WorkbookUtils.importXML(wb, xmlSource, new java.io.FileOutputStream("target/testImportXmlout.xlsx"));


    }
}
