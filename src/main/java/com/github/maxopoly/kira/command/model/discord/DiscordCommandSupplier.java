package com.github.maxopoly.kira.command.model.discord;

import com.github.maxopoly.kira.KiraMain;
import com.github.maxopoly.kira.command.model.top.InputSupplier;
import com.github.maxopoly.kira.user.KiraUser;

public abstract class DiscordCommandSupplier implements InputSupplier {

	protected KiraUser user;

	public DiscordCommandSupplier(KiraUser user) {
		if (user == null) {
			throw new IllegalArgumentException("User can't be null");
		}
		this.user = user;
	}

	@Override
	public String getIdentifier() {
		return user.toString();
	}

	@Override
	public KiraUser getUser() {
		return user;
	}

	@Override
	public boolean hasPermission(String perm) {
		return KiraMain.getInstance().getKiraRoleManager().hasPermission(user, perm);
	}

}
