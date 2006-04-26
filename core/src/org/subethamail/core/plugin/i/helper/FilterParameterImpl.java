/*
 * $Id$
 * $URL$
 */

package org.subethamail.core.plugin.i.helper;

import org.subethamail.core.plugin.i.FilterParameter;


/**
 * Simple implementation of FilterParameter
 * 
 * @author Jeff Schnitzer
 */
public class FilterParameterImpl implements FilterParameter
{
	String name;
	String description;
	Class type;
	Object defaultValue;
	int textLines;
	
	/** */
	public FilterParameterImpl(String name, String description, Class type, Object defaultValue)
	{
		this.name = name;
		this.description = description;
		this.type = type;
		this.defaultValue = defaultValue;
	}

	/** */
	public FilterParameterImpl(String name, String description, Class type, Object defaultValue, int textLines)
	{
		this(name, description, type, defaultValue);
		
		this.textLines = textLines;
	}

	/**
	 */
	public String getName() { return this.name; }
	
	/**
	 */
	public String getDescription() { return this.description; }
	
	/**
	 */
	public Class getType() { return this.type; }
	
	/**
	 */
	public Object getDefaultValue() { return this.defaultValue; }

	/**
	 */
	public int getTextLines()
	{
		return this.textLines;
	}
}
