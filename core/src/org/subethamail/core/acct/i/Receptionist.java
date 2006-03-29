/*
 * $Id: Receptionist.java 110 2006-02-28 06:59:40Z jeff $
 * $URL: https://svn.infohazard.org/blorn/trunk/core/src/com/blorn/core/recep/i/Receptionist.java $
 */

package org.subethamail.core.acct.i;

import javax.ejb.Local;
import javax.mail.MessagingException;

import org.subethamail.common.NotFoundException;

/**
 * Allows users to create accounts, get their password if they forget it, etc.
 *
 * These methods have no security restrictions; anyone can call them.
 *
 * @author Jeff Schnitzer
 */
@Local
public interface Receptionist
{
	/** */
	public static final String JNDI_NAME = "Receptionist/local";
		
	/**
	 * Requests to subscribe to a mailing list.  Does not actually create a user;
	 * instead this causes a URL to be sent to the specified email address which
	 * contains a special token.  That token can be passed to createSubscription()
	 * in order to do the real work.
	 * 
	 * This process has all the benefits of email verification without any
	 * database overhead.
	 */
	public void requestSubscription(String email, Long listId, String name) throws NotFoundException, MessagingException;
	
	/**
	 * Performs the execution of the subscribe.  There are two cases:
	 * 
	 * The email address already exists in the db.  The person is subscribed
	 * to the list (possibly pending moderator approval).
	 * 
	 * The email address does not yet exist.  A new account is created with
	 * a random password and the account is subscribed (possibly pending moderator
	 * approval).
	 * 
	 * @param cipherToken must have been generated by requestSubscription()
	 * @return the id of the person subscribed
	 */
	public Long subscribe(String cipherToken) throws BadTokenException;

	/**
	 * Requests that the user's password be sent back to them in plaintext.
	 * 
	 * @throws NotFoundException if no account has that email address.
	 */
	public void forgotPassword(String email) throws NotFoundException, MessagingException;
}
