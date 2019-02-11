package com.github.maxopoly.Kira.command.commands;

import com.github.maxopoly.Kira.KiraMain;
import com.github.maxopoly.Kira.command.Command;
import com.github.maxopoly.Kira.command.InputSupplier;
import com.github.maxopoly.Kira.user.UserManager;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public class SyncUsernameCommand extends Command {

	public SyncUsernameCommand() {
		super("syncusernames", 0, 0);
	}

	@Override
	public String execute(InputSupplier sender, String[] args) {
		UserManager userMan = KiraMain.getInstance().getUserManager();
		Guild guild = KiraMain.getInstance().getGuild();
		Member self = KiraMain.getInstance().getGuild().getSelfMember();
		StringBuilder sb = new StringBuilder();
		userMan.getAllUsers().stream().forEach(user -> {
			if (!user.hasIngameAccount()) {
				return;
			}
			Member member = guild.getMemberById(user.getDiscordID());
			if (member == null) {
				return; // not in the discord
			}
			if (member.getNickname().equals(user.getName())) {
				return; //already correct
			}
			if (!self.canInteract(member)) {
				return; //no perm
			}
			sb.append("Changing name of " + user.toString() + " from " + member.getNickname() + " to " + user.getName() + "\n");
			guild.getController().setNickname(member, user.getName()).queue();

		});
		sb.append("Successfully updated all usernames");
		return sb.toString();
	}

	@Override
	public String getUsage() {
		return "syncusernames";
	}

	@Override
	public String getFunctionality() {
		return "Updates all discord names to match the linked ingame names";
	}

	@Override
	public String getRequiredPermission() {
		return "admin";
	}

}
