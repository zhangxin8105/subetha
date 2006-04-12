/*
 * $Id: Receptionist.java 110 2006-02-28 06:59:40Z jeff $
 * $URL: https://svn.infohazard.org/blorn/trunk/core/src/com/blorn/core/recep/i/Receptionist.java $
 */

package org.subethamail.core.admin.i;

import java.net.URL;
import java.util.List;

import javax.ejb.Local;
import javax.mail.internet.InternetAddress;

import org.subethamail.common.NotFoundException;
import org.subethamail.core.acct.i.SubscribeResult;
import org.subethamail.core.lists.i.MailingListData;

/**
 * Administrative interface for managing the site.
 * 
 * @author Jeff Schnitzer
 */
@Local
public interface Admin
{
	/** */
	public static final String JNDI_NAME = "subetha/Admin/local";

	/**
	 * Creates a mailing list.  If any of the initial owner addresses
	 * have not been registered, accounts will be created without confirmation.
	 * 
	 * @param address contains both the email address and the short textual name of the list
	 * @param url is a valid list URL, including the /list/ portion.
	 * @param description is a long description of this list
	 * @param initialOwners is a list of email addresses.
	 * 
	 * @throws CreateMailingListException if the address or url are already in use.
	 */
	public Long createMailingList(InternetAddress address, URL url, String description, InternetAddress[] initialOwners) throws CreateMailingListException;
	
	/**
	 * Finds a person's id if the user exists, or creates a user account and
	 * returns the new person's id.  If the user already exists, the password
	 * and any additional personal name information in the address is ignored.
	 * 
	 * @param address contains both the email addy and name of the user.
	 * @param password can be null to get a random password
	 * 
	 * @return the id of the person with that email, which may have just
	 *  now been created.
	 */
	public Long establishPerson(InternetAddress address, String password);
	
	/**
	 * Subscribes an existing user to the list, or changes the delivery
	 * address of an existing subscription. 
	 * 
	 * @param email must be one of the current user's email addresses,
	 *  or null to subscribe delivery disabled.
	 *  
	 * @return either OK or HELD
	 *  
	 * @throws NotFoundException if the list id or person id is not valid.
	 */
	public SubscribeResult subscribe(Long listId, Long personId, String email) throws NotFoundException;

	/**
	 * Subscribes a potentially never-before-seen user to the list.
	 * 
	 * @param address can be an existing email address or a new one, in which
	 *  case a new person will be created.
	 *  
	 * @return either OK or HELD
	 *  
	 * @throws NotFoundException if the list id is not valid.
	 */
	public SubscribeResult subscribe(Long listId, InternetAddress address) throws NotFoundException;

	/**
	 * Sets whether or not the person is a site admin.
	 */
	public void setSiteAdmin(Long personId, boolean value) throws NotFoundException;
	
	/**
	 * TODO:  this (and the UI) should probably be paginated.
	 * 
	 * @return some information about all the lists on the site.
	 */
	public List<MailingListData> getAllLists();
}
