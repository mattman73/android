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

package org.amahi.anywhere.server.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ServerShare implements Parcelable
{
	@SerializedName("name")
	private String name;

	public String getName() {
		return name;
	}

	public static final Creator<ServerShare> CREATOR = new Creator<ServerShare>()
	{
		@Override
		public ServerShare createFromParcel(Parcel parcel) {
			return new ServerShare(parcel);
		}

		@Override
		public ServerShare[] newArray(int size) {
			return new ServerShare[size];
		}
	};

	private ServerShare(Parcel parcel) {
		this.name = parcel.readString();
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(name);
	}

	@Override
	public int describeContents() {
		return 0;
	}
}
