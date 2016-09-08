package com.liftoff.social.newsreader.formatengine;

/**
 * 
 * A registry whose job is to maintain a conceptFormatter to concept type mapping.
 * This mapping can be loaded through a config at boot time and can be configured/changed
 * dynamically at run time.
 * 
 * @author vakkiraju
 * 
 */
public interface IConceptFormatterMapRegistry {

	/***
	 * 
	 * get IConceptFormatter associated with a IConcept
	 * 
	 * @param concept
	 * @return
	 */
	IConceptFormatter getFormatter(Class<? extends IConcept> conceptClass);

	/***
	 * 
	 * associate IConcept with a IConceptFormatter
	 * 
	 * @param concept
	 * @param conceptFormatter
	 */
	void registerConceptFormatterMap(Class<? extends IConcept> conceptClass,
			IConceptFormatter conceptFormatter);

	/***
	 * 
	 * Remove ConceptFormatter associated with that of a concept.
	 * 
	 * @param concept
	 */
	void unRegisterConceptFormatterMap(Class<? extends IConcept> conceptClass);

}
