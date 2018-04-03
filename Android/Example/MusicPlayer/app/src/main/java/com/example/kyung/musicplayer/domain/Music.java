package com.example.kyung.musicplayer.domain;

import android.net.Uri;

/**
 * 실제 음원 데이터
 */

public class Music {
    private String id;      // 음악의 아이디
    private String albumId; // 음악 앨범 아이디
    private String artist;  // 가수 이름
    private String title;   // 노래 제목

    private Uri musicUri;   // 음악 주소 (Uri를 완성해서 넣으며 사실상 id랑 동일하다.)
    private Uri albumUri;   // 앨범이미지 주소

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Music)
            return id.equals(((Music)obj).id);
        return false;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAlbumId() {
        return albumId;
    }
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Uri getMusicUri() {
        return musicUri;
    }
    public void setMusicUri(Uri musicUri) {
        this.musicUri = musicUri;
    }
    public Uri getAlbumUri() {
        return albumUri;
    }
    public void setAlbumUri(Uri albumUri) {
        this.albumUri = albumUri;
    }
}
