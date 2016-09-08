/**
 * 
 */
package com.liftoff.social.newsreader.formatengine.formatter.decorator;

import java.nio.ByteBuffer;
import java.text.MessageFormat;

import org.springframework.stereotype.Component;

/**
 * 
 * A decorator that annotates a concept with that of a pre-configured html tag
 * 
 * @author vakkiraju
 *
 */
@Component
public class HtmlTagDecorator extends AbstractConceptDecorator {
	private String tag;
    byte[] startTagBuffer;
    byte[] endTagBuffer;
    int inpOutBuffLength = -1;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.liftoff.social.newsreader.formatengine.IConceptFormatter#format(java.
     * lang.Byte[])
     */
    @Override
    public byte[] format(byte[] concept) {
    	if(inpOutBuffLength == -1)
    	{
            startTagBuffer = MessageFormat.format("<{0}>", getTag()).getBytes();
            endTagBuffer = MessageFormat.format("</{0}>", getTag()).getBytes();
            inpOutBuffLength = endTagBuffer.length + startTagBuffer.length;    		
    	}
        concept = super.format(concept);
        ByteBuffer buffer = ByteBuffer.allocate(inpOutBuffLength + concept.length);
        buffer.put(startTagBuffer);
        buffer.put(concept);
        buffer.put(endTagBuffer);
        return buffer.array();
    }

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
}
