
This project has moving to GITHUB from [KENAI](http://kenai.com/projects/excel-xmlmapping/pages/Home)

----

## What is Excel Xml Mapping

To import and export XML data in Excel, an XML Map that associates XML elements with data in cells to get the results, would be very useful. To create one, you need to have an XML schema file (.xsd) and an XML data file (.xml). After creating the XML Map, you can map XML elements the way you want.
The link below explain the xml mapping feature and how to use it.

> [Map XML elements to cells in a XML Map](https://support.office.com/en-us/article/Map-XML-elements-to-cells-in-an-XML-Map-ddb23edf-f5c5-4fbf-b736-b3bf977a0c53#__create_an_xml)

## Project goal

Since, until version 3.5-beta6, seems that [apache POI project](http://poi.apache.org/) doesn't support importXML feature yet, i've created this project to provide , in a easy way and without need of native code , this very useful feature from excel 2007.

### Usage

```java

XSSFWorkbook wb;
java.io.Reader xmlSource;
java.io.OutputStream result;

File file = new File("src/test/resources/TestImportXml.xlsx");
File fileXml = new File("src/test/resources/TestImportData.xml");

// load an XLSX file with mapping informations
wb = new XSSFWorkbook(file.getAbsolutePath());

xmlSource = new java.io.FileReader( fileXml );

result =  new java.io.FileOutputStream("target/testImportXmlout.xlsx");

WorkbookUtils.importXML( wb,  xmlSource, result );
```

### Data Conversion

The data conversion is supported for types:

* Boolean
* Date
* DateTime - ```xs:dateTime format supported [-]CCYY-MM-DDThh:mm:ss[Z|(+|-)hh:mm]```
* Time
* Double
* Long
* Float

### Customize data conversion

It is possible ovveride the default data conversion behaviour implementing the Converter interface

```java
public static interface WorkbookUtils.Converter<T> {
  T convert( SimpleValue value) throws Exception;
}
```

Through the following methods published from ```WorkbookUtils``` class

```java
public static Converter<Integer> registerIntConverter( Converter<Integer> converter );

public static Converter<Float> registerFloatConverter( Converter<Float> converter );

public static Converter<Double> registerDoubleConverter( Converter<Double> converter ) ;

public static Converter<Long> registerLongConverter( Converter<Long> converter ) ;

public static Converter<Boolean> registerBooleanConverter( Converter<Boolean> converter ) ;

public static Converter<java.util.Date> registerDateConverter( Converter<java.util.Date> converter ) ;

public static Converter<java.util.Date> registerDateTimeConverter( Converter<java.util.Date> converter ) ;

public static Converter<java.util.Date> registerTimeConverter( Converter<java.util.Date> converter ) ;
```
