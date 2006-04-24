package org.subethamail.smtp.command;

import org.subethamail.smtp.session.Session;
import org.subethamail.smtp.command.Command;
import org.subethamail.smtp.command.CommandDispatcher;
import org.subethamail.smtp.command.HelpMessage;

/**
 * @author Ian McFarland &lt;ian@neo.com&gt;
 */
public class VerifyCommand extends Command {
  public VerifyCommand(CommandDispatcher commandDispatcher) {
    super(commandDispatcher, "VRFY");
    helpMessage = new HelpMessage("VRFY", "<recipient>", "Verify an address. To see the address to which it aliases,\n" +
        "use EXPN instead.\n" +
        "This command is often disabled for security reasons.");
  }

  @Override
  public String execute(String commandString, Session session) {
    return "252 Cannot VRFY user; try RCPT to attemt delivery.";
  }

}