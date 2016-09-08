/**
 * 
 */
package com.liftoff.social.newsreader.formatengine.formatter.decorator;

import org.springframework.stereotype.Component;

import com.liftoff.social.newsreader.formatengine.IConceptFormatter;

/**
 * 
 * Decorator pattern to format a formatted concept in a nested structure.
 * 
 * @author vakkiraju
 * 
 */
@Component
public abstract class AbstractConceptDecorator implements IConceptFormatter {
	private IConceptFormatter decorated;

	@Override
	public byte[] format(byte[] concept) {
		return getDecorated() != null ? getDecorated().format(concept)
				: concept;
	}

	/**
	 * @return the decorated
	 */
	public IConceptFormatter getDecorated() {
		return decorated;
	}

	/**
	 * @param decorated
	 *            the decorated to set
	 */
	public void setDecorated(IConceptFormatter decorated) {
		this.decorated = decorated;
	}

}