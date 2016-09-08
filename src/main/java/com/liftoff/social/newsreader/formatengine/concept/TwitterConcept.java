/**
 * 
 */
package com.liftoff.social.newsreader.formatengine.concept;

/**
 * 
 * Represents a twitter @<userName> concept
 * 
 * @author vakkiraju
 *
 */
public class TwitterConcept  extends AbstractConcept{

    /**
     * @param startPosition
     * @param endPosition
     */
    public TwitterConcept(int startPosition, int endPosition) {
        super(startPosition, endPosition);
    }
}
