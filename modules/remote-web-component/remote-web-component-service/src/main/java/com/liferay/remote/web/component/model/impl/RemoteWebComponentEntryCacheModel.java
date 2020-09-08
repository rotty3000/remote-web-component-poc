/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.remote.web.component.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.remote.web.component.model.RemoteWebComponentEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RemoteWebComponentEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RemoteWebComponentEntryCacheModel
	implements CacheModel<RemoteWebComponentEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RemoteWebComponentEntryCacheModel)) {
			return false;
		}

		RemoteWebComponentEntryCacheModel remoteWebComponentEntryCacheModel =
			(RemoteWebComponentEntryCacheModel)object;

		if ((remoteWebComponentEntryId ==
				remoteWebComponentEntryCacheModel.remoteWebComponentEntryId) &&
			(mvccVersion == remoteWebComponentEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, remoteWebComponentEntryId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", remoteWebComponentEntryId=");
		sb.append(remoteWebComponentEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", url=");
		sb.append(url);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RemoteWebComponentEntry toEntityModel() {
		RemoteWebComponentEntryImpl remoteWebComponentEntryImpl =
			new RemoteWebComponentEntryImpl();

		remoteWebComponentEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			remoteWebComponentEntryImpl.setUuid("");
		}
		else {
			remoteWebComponentEntryImpl.setUuid(uuid);
		}

		remoteWebComponentEntryImpl.setRemoteWebComponentEntryId(
			remoteWebComponentEntryId);
		remoteWebComponentEntryImpl.setCompanyId(companyId);
		remoteWebComponentEntryImpl.setUserId(userId);

		if (userName == null) {
			remoteWebComponentEntryImpl.setUserName("");
		}
		else {
			remoteWebComponentEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			remoteWebComponentEntryImpl.setCreateDate(null);
		}
		else {
			remoteWebComponentEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			remoteWebComponentEntryImpl.setModifiedDate(null);
		}
		else {
			remoteWebComponentEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			remoteWebComponentEntryImpl.setName("");
		}
		else {
			remoteWebComponentEntryImpl.setName(name);
		}

		if (url == null) {
			remoteWebComponentEntryImpl.setUrl("");
		}
		else {
			remoteWebComponentEntryImpl.setUrl(url);
		}

		remoteWebComponentEntryImpl.resetOriginalValues();

		return remoteWebComponentEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		remoteWebComponentEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		url = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(remoteWebComponentEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long remoteWebComponentEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String url;

}