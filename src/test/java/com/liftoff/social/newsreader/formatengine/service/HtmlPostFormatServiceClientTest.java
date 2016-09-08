/**
 * 
 */
package com.liftoff.social.newsreader.formatengine.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liftoff.social.newsreader.formatengine.IConcept;
import com.liftoff.social.newsreader.formatengine.IFormatService;
import com.liftoff.social.newsreader.formatengine.concept.LinkConcept;
import com.liftoff.social.newsreader.formatengine.concept.NounEntityConcept;
import com.liftoff.social.newsreader.formatengine.concept.TwitterConcept;
import com.liftoff.social.newsreader.formatengine.exceptions.FormatServiceException;

;
/**
 * 
 * Integration test case that tests html post format service exposed like an external client.
 * 
 * @author vakkiraju
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/integration-test-context.xml")
public class HtmlPostFormatServiceClientTest {
	private XLogger logger = XLoggerFactory.getXLogger(this.getClass());

	@Autowired
	private IFormatService htmlPostFormatService;

	@Test
	public void testFormattingObamaPost() throws IOException, FormatServiceException {
		logger.info("Starting test case for formatting a post..");
		
		//Start with the input sample post and a list of concepts
		String inputPost = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile";
		String expectedOutputPost = "<strong>Obama</strong> visited <strong>Facebook</strong> headquarters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\">elversatile</a>";
		List<IConcept> concepts = new ArrayList<IConcept>(Arrays.asList(
				new NounEntityConcept(14, 22), new NounEntityConcept(0, 5),
				new TwitterConcept(55, 67), new LinkConcept(37, 54)));
		
		OutputStream outputStream = new ByteArrayOutputStream();
		InputStream inputStream = IOUtils.toInputStream(inputPost,
				Charset.defaultCharset());
		
		//Call into our service. inputStream,concepts are the input, outputStream is the output
		htmlPostFormatService.format(inputStream, concepts, outputStream);				
		String actualPost = new String(
				((ByteArrayOutputStream) outputStream).toByteArray(),
				Charset.defaultCharset());
		actualPost = actualPost.trim();
		logger.info("Actual: " + actualPost);
		logger.info("Expected: " + expectedOutputPost);
		Assert.assertEquals(expectedOutputPost, actualPost);

	}

}
