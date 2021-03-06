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

package org.amahi.anywhere.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import org.amahi.anywhere.AmahiApplication;
import org.amahi.anywhere.R;
import org.amahi.anywhere.adapter.ServerFileImagePagerAdapter;
import org.amahi.anywhere.server.client.ServerClient;
import org.amahi.anywhere.server.model.ServerFile;
import org.amahi.anywhere.server.model.ServerShare;
import org.amahi.anywhere.util.Intents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class ServerFileImageActivity extends Activity implements ViewPager.OnPageChangeListener
{
	public static final Set<String> SUPPORTED_FORMATS;

	static {
		SUPPORTED_FORMATS = new HashSet<String>(Arrays.asList(
			"image/bmp",
			"image/jpeg",
			"image/gif",
			"image/png",
			"image/webp"
		));
	}

	@Inject
	ServerClient serverClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_file_image);

		setUpInjections();

		setUpHomeNavigation();

		setUpImage();
	}

	private void setUpInjections() {
		AmahiApplication.from(this).inject(this);
	}

	private void setUpHomeNavigation() {
		getActionBar().setHomeButtonEnabled(true);
	}

	private void setUpImage() {
		setUpImageTitle();
		setUpImageAdapter();
		setUpImagePosition();
		setUpImageListener();
	}

	private void setUpImageTitle() {
		setUpImageTitle(getFile());
	}

	private void setUpImageTitle(ServerFile file) {
		getActionBar().setTitle(file.getName());
	}

	private ServerFile getFile() {
		return getIntent().getParcelableExtra(Intents.Extras.SERVER_FILE);
	}

	private void setUpImageAdapter() {
		getImagePager().setAdapter(new ServerFileImagePagerAdapter(getFragmentManager(), getShare(), getImageFiles()));
	}

	private ViewPager getImagePager() {
		return (ViewPager) findViewById(R.id.pager_images);
	}

	private ServerShare getShare() {
		return getIntent().getParcelableExtra(Intents.Extras.SERVER_SHARE);
	}

	private List<ServerFile> getImageFiles() {
		List<ServerFile> imageFiles = new ArrayList<ServerFile>();

		for (ServerFile file : getFiles()) {
			if (SUPPORTED_FORMATS.contains(file.getMime())) {
				imageFiles.add(file);
			}
		}

		return imageFiles;
	}

	private List<ServerFile> getFiles() {
		return getIntent().getParcelableArrayListExtra(Intents.Extras.SERVER_FILES);
	}

	private void setUpImagePosition() {
		getImagePager().setCurrentItem(getImageFiles().indexOf(getFile()));
	}

	private void setUpImageListener() {
		getImagePager().setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageSelected(int position) {
		setUpImageTitle(getImageFiles().get(position));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case android.R.id.home:
				finish();
				return true;

			default:
				return super.onOptionsItemSelected(menuItem);
		}
	}
}
