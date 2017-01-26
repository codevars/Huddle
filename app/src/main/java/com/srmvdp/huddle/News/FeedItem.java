package com.srmvdp.huddle.News;

public class FeedItem {

    private int id;

    private String name, privilege, title, status, image, subject, profilepic, uploadtime, url;

    public FeedItem() {}

    public FeedItem(int id, String name, String privilege, String image, String title, String status, String subject, String profilepic, String uploadtime, String url) {

        super();
        this.id = id;
        this.name = name;
        this.privilege = privilege;
        this.image = image;
        this.title = title;
        this.status = status;
        this.subject = subject;
        this.profilepic = profilepic;
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

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProfilePic() {
        return profilepic;
    }

    public void setProfilePic(String profilepic) {
        this.profilepic = profilepic;
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
