/*
 * $Id: BlornCipher.java 86 2006-02-22 03:36:01Z jeff $
 * $URL: https://svn.infohazard.org/blorn/trunk/core/src/com/blorn/core/util/BlornCipher.java $
 */

package org.subethamail.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Static methods to convert between Base64 and "Base62" encoding.
 * A "Base62" string is composed of only the characters A-Za-z0-9.
 * This helps prevent MUAs from putting linebreaks in the middle
 * of longish encryption tokens.
 * 
 * Note that the start strings must already be Base64 encoded!
 *
 * @author Jeff Schnitzer
 */
public class Base62
{
	/** */
	private static Log log = LogFactory.getLog(Base62.class);
	
	/**
	 * Takes a base64 encoded string and eliminates the '+' and '/'.
	 * Also eliminates any CRs.
	 * 
	 * Having tokens that are a seamless string of letters and numbers
	 * means that MUAs are less likely to linebreak a long token.
	 */
	public static String encode(String base64)
	{
		StringBuffer buf = new StringBuffer(base64.length() * 2);
		
		for (int i=0; i<base64.length(); i++)
		{
			char ch = base64.charAt(i);
			switch (ch)
			{
				case 'i':
					buf.append("ii");
					break;
					
				case '+':
					buf.append("ip");
					break;
					
				case '/':
					buf.append("is");
					break;
					
				case '=':
					buf.append("ie");
					break;
					
				case '\n':
					// Strip out
					break;
					
				default:
					buf.append(ch);
			}
		}
		
		if (log.isDebugEnabled())
			log.debug("encodeBase62 from " + base64 + " to " + buf.toString());
		
		return buf.toString();
	}
	
	/**
	 * Returns a string encoded with encodeBase62 to its original
	 * (base64 encoded) state.
	 */
	public static String decode(String base62)
	{
		StringBuffer buf = new StringBuffer(base62.length());
		
		int i = 0;
		while (i < base62.length())
		{
			char ch = base62.charAt(i);
			
			if (ch == 'i')
			{
				i++;
				char code = base62.charAt(i);
				switch (code)
				{
					case 'i':
						buf.append('i');
						break;
						
					case 'p':
						buf.append('+');
						break;
						
					case 's':
						buf.append('/');
						break;
						
					case 'e':
						buf.append('=');
						break;
						
					default:
						throw new IllegalStateException("Illegal code in base62 encoding");
				}
			}
			else
			{
				buf.append(ch);
			}
			
			i++;
		}
		
		if (log.isDebugEnabled())
			log.debug("decodeBase62 from " + base62 + " to " + buf.toString());
		
		return buf.toString();
	}
}


	