/**
 * 
 */
package com.liftoff.social.newsreader.formatengine.formatter;

import org.springframework.stereotype.Component;

import com.liftoff.social.newsreader.formatengine.IConceptFormatter;

/**
 * 
 * Default concept formatter that just returns the given input as output. This
 * can, in production, actually implement most basic formatting needs for all
 * concepts such as looking for profane content, removal of unnecessary
 * characters etc
 * 
 * @author vakkiraju
 * 
 */
@Component
public class DefaultConceptFormatter implements IConceptFormatter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liftoff.social.newsreader.formatengine.IConceptFormatter#format(java
	 * .lang.Byte[])
	 */
	@Override
	public byte[] format(byte[] concept) {
		return concept;
	}
}
