/**
 * 
 */
package com.liftoff.social.newsreader.formatengine.formatter.decorator;

import java.nio.charset.Charset;
import java.text.MessageFormat;

import org.springframework.stereotype.Component;

/**
 * 
 * A decorator that annotates a link string with that of an html link
 * 
 * @author vakkiraju
 *
 */
@Component
public class DynamicHRefTagDecorator extends AbstractConceptDecorator {    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.liftoff.social.newsreader.formatengine.IConceptFormatter#format(java.
     * lang.Byte[])
     */
    @Override
    public byte[] format(byte[] concept) {
        String rootWord = new String(super.format(concept), Charset.defaultCharset());
        return MessageFormat.format("<a href=\"{0}\">{0}</a>", rootWord).getBytes();
    }
}
