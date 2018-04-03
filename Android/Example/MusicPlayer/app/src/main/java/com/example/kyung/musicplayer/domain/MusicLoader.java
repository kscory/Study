package com.example.kyung.musicplayer.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * 음원데이터 로더
 */

public class MusicLoader {
    // Singleton 디자인 패턴
    private static MusicLoader instance = null;
    private MusicLoader(){ }
    public static MusicLoader getInstance(){
        if(instance == null){
            instance = new MusicLoader();
        }
        return instance;
    }

    // 음악 데이터 리스트
    public List<Music> musics = new ArrayList<>();

    // 음악 데이터를 불러오는 메소드
    public void load(Context context){
        ContentResolver resolver = context.getContentResolver();
        // 1. 테이블명 정의
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // 2. 불러올 컬럼명 정의
        String projection[] = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };
        // 3. 쿼리
        Cursor cursor = resolver.query(uri,projection,null,null,projection[2]+" asc");
        // 4. 쿼리 결과가 담긴 커서를 통해서 꺼내기
        if(cursor != null){
            while (cursor.moveToNext()){
                Music music = new Music();
                // 데이터 세팅
                music.setId(getValue(cursor,projection[0]));
                music.setAlbumId(getValue(cursor,projection[1]));
                music.setTitle(getValue(cursor,projection[2]));
                music.setArtist(getValue(cursor,projection[3]));
                // Uri를 세팅
                music.setMusicUri(findMusicUri(music.getId()));
                music.setAlbumUri(findAlbumUri(music.getAlbumId()));
                // 데이터 추가
                musics.add(music);
            }
        }
        // 5. 끝나면 닫기
        cursor.close();
    }

    // 커서의 데이터를 바로 대입하기 위한 메소드
    private String getValue(Cursor cursor, String name){
        int index = cursor.getColumnIndex(name);
        return cursor.getString(index);
    }

    // musicUri(음악 주소)를 찾아서 리턴 (내장된 uri를 사용)
    private Uri findMusicUri(String musicId){
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // 합쳐진 완성된 음원의 Uri
        return Uri.withAppendedPath(contentUri, musicId);
    }

    // albumUri(앨범이미지주소)를 찾아서 리턴 (직접 찾는다)
    private Uri findAlbumUri(String albumId){
        String contentUri = "content://media/external/audio/albumart/";
        return Uri.parse(contentUri + albumId);
    }
}
