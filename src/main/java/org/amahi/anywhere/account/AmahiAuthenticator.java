/*
 * Copyright (c) 2014 Amahi
 *
 * This file is part of Amahi.
 *
 * Amahi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amahi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amahi. If not, see <http ://www.gnu.org/licenses/>.
 */

package org.amahi.anywhere.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.amahi.anywhere.activity.AuthenticationActivity;

class AmahiAuthenticator extends AbstractAccountAuthenticator
{
	private final Context context;

	public AmahiAuthenticator(Context context) {
		super(context);

		this.context = context;
	}

	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
		Intent accountIntent = new Intent(context, AuthenticationActivity.class);
		accountIntent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

		Bundle accountBundle = new Bundle();
		accountBundle.putParcelable(AccountManager.KEY_INTENT, accountIntent);
		return accountBundle;
	}

	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
		Bundle authBundle = new Bundle();

		String authToken = AccountManager.get(context).peekAuthToken(account, authTokenType);

		if (!TextUtils.isEmpty(authToken)) {
			authBundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
			authBundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
			authBundle.putString(AccountManager.KEY_AUTHTOKEN, authToken);
		} else {
			Intent authIntent = new Intent(context, AuthenticationActivity.class);
			authIntent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

			authBundle.putParcelable(AccountManager.KEY_INTENT, authIntent);
		}

		return authBundle;
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
		return null;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
		return null;
	}

	@Override
	public String getAuthTokenLabel(String s) {
		return null;
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
		return null;
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
		return null;
	}
}
