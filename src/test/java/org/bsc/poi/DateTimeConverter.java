/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bsc.poi;

import java.text.SimpleDateFormat;
import org.apache.xmlbeans.SimpleValue;

/**
 *
 * @author softphone
 */
public class DateTimeConverter implements WorkbookUtils.Converter<java.util.Date> {

    public java.util.Date convert(SimpleValue value) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        return df.parse( value.getStringValue());
    }
}


