package com.liftoff.social.newsreader.formatengine.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liftoff.social.newsreader.formatengine.IConcept;
import com.liftoff.social.newsreader.formatengine.IConceptFormatter;
import com.liftoff.social.newsreader.formatengine.IConceptFormatterMapRegistry;
import com.liftoff.social.newsreader.formatengine.IFormatService;
import com.liftoff.social.newsreader.formatengine.concept.AbstractConcept;
import com.liftoff.social.newsreader.formatengine.exceptions.FormatServiceException;

/**
 * 
 * Core facade post format service implementation. Handles input stream and
 * concepts given as input.
 * 
 * @author vakkiraju
 * 
 */
@Component
public class HtmlPostFormatService implements IFormatService {

	@Autowired
	IConceptFormatterMapRegistry conceptFormatterMapRegistry;

	private static final int CHUNK_SIZE = 1024;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liftoff.social.newsreader.formatengine.IFormatService#format(java.io.
	 * InputStream, java.util.List, java.io.OutputStream)
	 */
	@Override
	public void format(InputStream inputStream, List<IConcept> concepts,
			OutputStream outputStream) throws IOException,FormatServiceException {
		// Time complexity is order N - L where N is the number of bytes in the
		// input stream and L is sum of sizes of all concepts.
		// We only call read 2X + Z/CHUNK_SIZE number of times where X = no of
		// concepts, Z is no of bytes beyond the last concept in the input
		// stream.
		Map<Integer, IConcept> positionConceptMap = new HashMap<>();
		IConceptFormatter currFormatter = null;
		AbstractConcept currConcept = null;
		int currPosition = 0;
		int bufferStartPosition = 0;
		int bufferEndPosition;
		int currBuffLength;

		int finalPosition = 0;

		// Load the positionConceptMap map. Find the last concept end point
		// position.
		for (IConcept concept : concepts) {
			currConcept = (AbstractConcept) concept;
			positionConceptMap.put(currConcept.getStartPosition(), concept);
			if (finalPosition < currConcept.getEndPosition()) {
				finalPosition = currConcept.getEndPosition();
			}
		}
		currConcept = null;

		while (currPosition <= finalPosition) {
			if (positionConceptMap.get(currPosition) != null) {
				if (currPosition > 0) {
					// Copy nonConceptBuffer token traversed so far to output
					// stream
					bufferEndPosition = currPosition;
					currBuffLength = bufferEndPosition - bufferStartPosition;
					if (currBuffLength > 0) {
						outputStream.write(fetchBuffer(inputStream,
								currBuffLength));
					}
				}
				// Format the concept and copy the formatted buffer to output
				// stream
				currConcept = (AbstractConcept) positionConceptMap
						.get(currPosition);
				currBuffLength = currConcept.getEndPosition()
						- currConcept.getStartPosition();
				currFormatter = conceptFormatterMapRegistry
						.getFormatter(currConcept.getClass());
				if (currBuffLength > 0) {
					outputStream.write(currFormatter.format(fetchBuffer(
							inputStream, currBuffLength)));
				}
				currPosition += currBuffLength;
				// Set buffer start to curr position
				bufferStartPosition = currPosition;
			} else {
				currPosition++;
			}
		}

		// All concepts have been processed. Copy the training buffer from input
		// to output stream in chunks. We do this since we don't know the size
		// of input stream ahead of time.
		byte[] chunk = new byte[CHUNK_SIZE];
		int bytesRead;
		do {
			bytesRead = inputStream.read(chunk);
			if (bytesRead > 0) {
				outputStream.write(chunk, 0, bytesRead);
			}
		} while (bytesRead == CHUNK_SIZE);

	}

	/**
	 * Return byte array that represents currBuffLength bytes in inputStream
	 * from the position when it was last read
	 * 
	 * @param currBuff
	 * @param currBuffLength
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private byte[] fetchBuffer(InputStream inputStream, int currBuffLength)
			throws IOException {
		byte[] currBuff = new byte[currBuffLength];
		inputStream.read(currBuff, 0, currBuffLength);
		return currBuff;
	}
}