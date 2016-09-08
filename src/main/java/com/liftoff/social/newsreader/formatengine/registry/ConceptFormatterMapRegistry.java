/**
 * 
 */
package com.liftoff.social.newsreader.formatengine.registry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.liftoff.social.newsreader.formatengine.IConcept;
import com.liftoff.social.newsreader.formatengine.IConceptFormatter;
import com.liftoff.social.newsreader.formatengine.IConceptFormatterMapRegistry;

/**
 * 
 * Maintains mapping of concept type with that of a conceptFormatter instance.
 * Doing so will ensure class reuse and decoupling of both these components. New
 * concepts and New ConceptFormatters can be developed independent of each
 * other. Their mapping can be decided statically, or boot time or runtime. Note
 * that ConceptFormatter instances themselves can be nested since their abstract
 * class supports decorator pattern.
 * 
 * @author vakkiraju
 * 
 */
@Component
public class ConceptFormatterMapRegistry implements IConceptFormatterMapRegistry {

	private Map<Class<? extends IConcept>, IConceptFormatter> conceptFormatterMapper;

	/***
	 * Load from spring config.
	 */
	@Resource
	private Map<String, IConceptFormatter> initConceptFormatterMapper;

	/**
	 * Initialize conceptFormatterMapper map during boot time.
	 * @throws ClassNotFoundException
	 */
	@PostConstruct
	public void init() throws ClassNotFoundException {
		conceptFormatterMapper = new HashMap<>();
		for (String className : initConceptFormatterMapper.keySet()) {
			Class<?> cls = Class.forName(className);
			Class<? extends IConcept> concept = (Class<? extends IConcept>) cls;
			registerConceptFormatterMap(concept,
					initConceptFormatterMapper.get(className));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liftoff.social.newsreader.formatengine.IConceptFormatterRegistry#
	 * getFormatter(com.liftoff.social.newsreader.formatengine.IConcept)
	 */
	@Override
	public IConceptFormatter getFormatter(Class<? extends IConcept> conceptClass) {
		if (conceptFormatterMapper == null) {
			return null;
		}
		return conceptFormatterMapper.get(conceptClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liftoff.social.newsreader.formatengine.IConceptFormatterRegistry#
	 * registerConceptFormatterMap(java.lang.Class,
	 * com.liftoff.social.newsreader.formatengine.IConceptFormatter)
	 */
	@Override
	public void registerConceptFormatterMap(
			Class<? extends IConcept> conceptClass,
			IConceptFormatter conceptFormatter) {
		//Override any previously set mapping
		conceptFormatterMapper.put(conceptClass, conceptFormatter);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liftoff.social.newsreader.formatengine.IConceptFormatterRegistry#
	 * unRegisterConceptFormatterMap(java.lang.Class)
	 */
	@Override
	public void unRegisterConceptFormatterMap(
			Class<? extends IConcept> conceptClass) {
		if (conceptFormatterMapper.containsKey(conceptClass)) {
			conceptFormatterMapper.remove(conceptClass);
		}

	}
}
