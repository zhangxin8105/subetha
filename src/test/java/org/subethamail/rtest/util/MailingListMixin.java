/*
 * $Id$
 * $URL$
 */

package org.subethamail.rtest.util;

import javax.mail.internet.InternetAddress;

/**
 * Easy way to create a mailing list from a unit test.
 * 
 * @author Jeff Schnitzer
 */
public class MailingListMixin extends MailingListInfoMixin
{
	Long id;
	InternetAddress[] initialOwners;
	
	/**
	 * @param initialOwner can be null to create an ownerless list. 
	 */
	public MailingListMixin(AdminMixin adminMixin, InternetAddress initialOwner) throws Exception
	{
		this(adminMixin, initialOwner, null);
	}
	
	/**
	 * @param initialOwner can be null to create an ownerless list.
	 * @param blueprint can be null to get a nonblueprinted list. 
	 */
	public MailingListMixin(AdminMixin adminMixin, InternetAddress initialOwner, String blueprint) throws Exception
	{
		super();
		
		if (initialOwner == null)
			this.initialOwners = new InternetAddress[0];
		else
			this.initialOwners = new InternetAddress[] { initialOwner };
		
		if (blueprint == null)
			this.id = adminMixin.getAdmin().createMailingList(this.address, this.url, this.description, this.initialOwners);
		else
			this.id = adminMixin.getListWizard().createMailingList(this.address, this.url, this.description, this.initialOwners, blueprint);
	}
	
	/** */
	public Long getId() { return this.id; }
}
