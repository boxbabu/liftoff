/**
 * 
 */
package com.liftoff.social.newsreader.formatengine.formatter.decorator;

import java.nio.charset.Charset;
import java.text.MessageFormat;

import org.springframework.stereotype.Component;

/**
 * 
 * A decorator that annotates a twitter concept with that of a twitter clickable
 * link. Note that @ is handled differently than that of a
 * DyanmicHRefTagDecorator
 * 
 * @author vakkiraju
 * 
 */
@Component
public class TwitterHRefTagDecorator extends AbstractConceptDecorator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liftoff.social.newsreader.formatengine.IConceptFormatter#format(java.
	 * lang.Byte[])
	 */
	@Override
	public byte[] format(byte[] concept) {
		String rootWord = new String(super.format(concept),
				Charset.defaultCharset());
		String twitterUserName = rootWord.substring(1);
		return MessageFormat.format(
				"@<a href=\"http://twitter.com/{0}\">{0}</a>", twitterUserName)
				.getBytes();
	}
}
