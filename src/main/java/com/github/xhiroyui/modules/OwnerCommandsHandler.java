package com.github.xhiroyui.modules;

import java.io.IOException;

import com.github.xhiroyui.DiscordClient;
import com.github.xhiroyui.UserWhitelist;
import com.github.xhiroyui.constant.FunctionConstant;
//import com.github.xhiroyui.util.Command;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class OwnerCommandsHandler extends ModuleHandler {
	public OwnerCommandsHandler() {
		createCommands();
	}

	private void createCommands() {
//		Command command;

//		command = new Command(FunctionConstant.OWNER_ADD_TO_WL);
//		command.setCommandName("Add to Whitelist");
//		command.setCommandDescription("Adds a user to whitelist");
//		command.setCommandCallers("wl");
//		command.setCommandCallers("whitelist");
//		command.setParams(new String[] { "User ID", "@Mention" });
//		command.setMaximumArgs(1);
//		command.setExample("@Rhestia");
//		commandList.add(command);
//
//		command = new Command(FunctionConstant.OWNER_REMOVE_FROM_WL);
//		command.setCommandName("Remove from Whitelist");
//		command.setCommandDescription("Removes a user from the whitelist");
//		command.setCommandCallers("removewl");
//		command.setCommandCallers("removewhitelist");
//		command.setParams(new String[] { "User ID", "@Mention" });
//		command.setMaximumArgs(1);
//		command.setExample("@Rhestia");
//		commandList.add(command);

	}

	@EventSubscriber
	public void OnMesageEvent(MessageReceivedEvent event)
			throws RateLimitException, DiscordException, MissingPermissionsException, IOException {
		String[] command = processCommand(event);
		if (command != null) {
			if (ownerCheck(event.getAuthor().getLongID())) {
				executeCommand(event, command);
			} else {
				sendMessage(event.getAuthor().getDisplayName(event.getGuild()) + " is not the owner and is unable to use the command.", event);
			}
		}
	}

	public void executeCommand(MessageReceivedEvent event, String[] command) {
		String commandCode = validateCommand(event, command);
		if (commandCode != null) {
			switch (commandCode) {
			case FunctionConstant.OWNER_ADD_TO_WL:
				try {
					addToWhitelist(command[1], event);
				} catch (Exception e) {
					throwError(FunctionConstant.OWNER_ADD_TO_WL, e, event);
				}
				break;
			case FunctionConstant.OWNER_REMOVE_FROM_WL:
				try {
					removeFromWhitelist(command[1], event);
				} catch (Exception e) {
					throwError(FunctionConstant.OWNER_REMOVE_FROM_WL, e, event);
				}
				break;
			}

		}
	}
	
	public boolean ownerCheck(Long userID) {
		if (userID.equals(DiscordClient.getClient().getApplicationOwner().getLongID())) 
			return true;
		return false;
	}
	
	// Command functions are placed below here
	
	private void addToWhitelist(String userID, MessageReceivedEvent event) {
//		sendMessage(userID, event);
		if (!UserWhitelist.getWhitelist().validateUser(userID)) {
			if (UserWhitelist.getWhitelist().addUserToWhitelist(userID))
			sendMessage("User " + event.getGuild().getUserByID(Long.parseLong(userID))
							.getNicknameForGuild(event.getGuild())
							+ " added to whitelist.\nReminder : Whitelist is persistant across servers.",
					event);
			else
				sendMessage("Error updating whitelist. Please contact bot author to rectify this issue", event);
		}
		else
			sendMessage("User already in whitelist. No actions were taken.", event);
	}
	
	private void removeFromWhitelist(String userID, MessageReceivedEvent event) {
		sendMessage("Command under construction. Tehepero XP", event);
	}
}