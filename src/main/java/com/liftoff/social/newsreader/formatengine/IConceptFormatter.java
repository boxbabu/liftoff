/**
 * 
 */
package com.liftoff.social.newsreader.formatengine;

/**
 * 
 * Handles how to transform a set of bytes. Can handle images, strings, any
 * serializable objects, numbers, floats, videos etc. IConcept is just one way
 * to represent a group of bytes. This formatter can handle much beyond. This
 * can also form a baseline for a nested decorator formatters.
 * 
 * @author vakkiraju
 * 
 */
public interface IConceptFormatter {
	byte[] format(byte[] concept);
}