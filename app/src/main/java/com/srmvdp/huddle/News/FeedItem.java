package com.srmvdp.huddle.News;

public class FeedItem {
	private int id;
	private String name, status, image, profilePic, uploadtime, url;

	public FeedItem() {
	}

	public FeedItem(int id, String name, String image, String status,
			String profilePic, String time, String url) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.status = status;
		this.profilePic = profilePic;
		this.uploadtime = uploadtime;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImge() {
		return image;
	}

	public void setImge(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getTime() {
		return uploadtime;
	}

	public void setTime(String uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
