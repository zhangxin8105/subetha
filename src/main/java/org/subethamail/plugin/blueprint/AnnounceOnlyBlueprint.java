/*
 * $Id$
 * $URL$
 */

package org.subethamail.plugin.blueprint;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.subethamail.common.NotFoundException;
import org.subethamail.core.lists.i.ListMgr;
import org.subethamail.core.plugin.i.Blueprint;
import org.subethamail.entity.i.Permission;
import org.subethamail.entity.i.PermissionException;
import org.subethamail.plugin.filter.AppendFooterFilter;
import org.subethamail.plugin.filter.HoldEverythingFilter;
import org.subethamail.plugin.filter.ListHeaderFilter;

/**
 * Creates an announce-only list. 
 * 
 * @author Jeff Schnitzer
 * @author Jon Stevens
 * @author Scott Hernandez
 */
@Singleton
public class AnnounceOnlyBlueprint implements Blueprint
{
	@Inject ListMgr listMgr;
	
	/** */
	public String getName()
	{
		return "Announce-Only List";
	}

	/** */
	public String getDescription()
	{
		return 
			"Create a list which allows only moderators to post and view the subscriber list. " +
			"All messages are held for manual approval to prevent spoofing. " +
			"Subscribers can read the archives.";
	}
	
	/** */
	public void configureMailingList(Long listId)
	{
		try
		{
			// Subscriber
			Set<Permission> perms = new HashSet<Permission>();
			perms.add(Permission.VIEW_ARCHIVES);
			Long roleId = listMgr.addRole(listId, "Subscriber", perms);
			listMgr.setDefaultRole(listId, roleId);

			// Guest
			perms = new HashSet<Permission>();
			roleId = listMgr.addRole(listId, "Guest", perms);
			listMgr.setAnonymousRole(listId, roleId);

			// Moderator
			perms = new HashSet<Permission>();
			perms.add(Permission.POST);
			perms.add(Permission.VIEW_ARCHIVES);
			perms.add(Permission.VIEW_ADDRESSES);
			perms.add(Permission.VIEW_SUBSCRIBERS);
			perms.add(Permission.APPROVE_MESSAGES);
			perms.add(Permission.APPROVE_SUBSCRIPTIONS);
			perms.add(Permission.VIEW_ROLES);
			listMgr.addRole(listId, "Moderator", perms);

			// Add a couple useful filters
			listMgr.setFilterDefault(listId, AppendFooterFilter.class.getName());
			listMgr.setFilterDefault(listId, ListHeaderFilter.class.getName());
			listMgr.setFilterDefault(listId, HoldEverythingFilter.class.getName());
		}
		catch (PermissionException pe)
		{
			throw new RuntimeException(pe);
		}
		catch (NotFoundException nfe)
		{
			throw new RuntimeException(nfe);
		}
	}
}