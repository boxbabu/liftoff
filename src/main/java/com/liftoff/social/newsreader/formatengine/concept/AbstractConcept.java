/**
 * 
 */
package com.liftoff.social.newsreader.formatengine.concept;

import com.liftoff.social.newsreader.formatengine.IConcept;

/**
 * 
 * Represents a generic concept with a start and end positions corresponding to a concept within a stream 
 * 
 * @author vakkiraju
 *
 */
public abstract class AbstractConcept  implements IConcept{
    private int startPosition;
    private int endPosition;
    public AbstractConcept(int startPosition, int endPosition)
    {
        this.setStartPosition(startPosition);
        this.setEndPosition(endPosition);
    }
    public int getStartPosition() {
        return startPosition;
    }
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }
    public int getEndPosition() {
        return endPosition;
    }
    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }
}
