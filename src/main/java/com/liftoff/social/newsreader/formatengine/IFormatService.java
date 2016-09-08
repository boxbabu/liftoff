/**
 * 
 */
package com.liftoff.social.newsreader.formatengine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.liftoff.social.newsreader.formatengine.exceptions.FormatServiceException;

/**
 * 
 * This is the core service for module3 that handles taking a stream, formatting
 * it and outputting a stream. Note that inputStream can be quite long pointing to a
 * resource that doesn't fit into the memory. This interface would
 * still work.
 * 
 * @author vakkiraju
 * 
 */
@Component
public interface IFormatService {
	/***
	 * 
	 * @param inputStream
	 *            Source of input data stream. Method doesn't close the stream. It expects a valid input stream.
	 * @param concepts
	 * 			  List of concepts with their positions
	 * @param outputStream
	 * 				Output stream. This is an out variable. Note that the method doesn't close the output stream. Expects a valid stream.
	 * @throws IOException
	 */
	void format(InputStream inputStream, List<IConcept> concepts,
			OutputStream outputStream) throws IOException,FormatServiceException;
}