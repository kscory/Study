package com.example.kyung.realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kyung.realm.realm.Bbs;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void create() {
        /* 동기로 데이터 입력 */
        // 1. 인스턴스 생성 - connection
        Realm realm = Realm.getDefaultInstance();
        // 2. 트랜잭션 시작
        realm.beginTransaction();
        // 테이블 처리
        // 자동증가 로직처럼 사용하기
        Number maxValue = realm.where(Bbs.class).max("no");
        int no = (maxValue!=null) ? maxValue.intValue() +1:1;
        // 프라이머리 키는 class 뒤에 설정해준다.
        Bbs bbs = realm.createObject(Bbs.class, no); // 레코드 한개 생성
        bbs.setTitle("제목 1");
        bbs.setContent("내용을 여기에넣는다. \n  ");
        bbs.setDate(System.currentTimeMillis());
        realm.commitTransaction();

        /* 비동기로 데이터 입력 */
        RealmAsyncTask transaction = realm.executeTransactionAsync(
                asyncRealm -> {
                    Bbs bbs2 = asyncRealm.createObject(Bbs.class);
                    bbs2.setNo(2);
                    bbs2.setTitle("제목 2");
                    bbs2.setContent("내용을 여기에넣는다. \n  ");
                    bbs2.setDate(System.currentTimeMillis());
                }
                , () -> { // 성공시 진행
                    afterCreation(); // 데이터 베이스 처리가 끝나고 호출될 함수를 지정
                }
                , error -> {// 에러처리

                }
        );

        RealmAsyncTask transaction2 = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }
    public void afterCreation() {

    }

    public void read() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Bbs> query = realm.where(Bbs.class);
        query.equalTo("no",1);
        query.or();
        query.equalTo("title", "제목 1");
        // select * from bbs where no = 1 or title = '제목1'
        /* 동기로 질의 */
        RealmResults<Bbs> result1 = query.findAll(); // 결과는 array로 가져오게 된다.


        /* 비동기로 질의 */ // 비동기인 경우는 먼저 찍히기 때문에 콜백이 필요
        query.findAllAsync().addChangeListener(new RealmChangeListener<RealmResults<Bbs>>() {
            @Override
            public void onChange(RealmResults<Bbs> bbs) {
                // 결과처리
            }
        });
    }

    public void update() { //read 후에 update를 실행한다.

        // 동기로 하는 경우
        Realm realm3 = Realm.getDefaultInstance();
        RealmQuery<Bbs> query2 = realm3.where(Bbs.class);
        query2.equalTo("no",1);
        query2.or();
        query2.equalTo("title", "제목 1");
        // select * from bbs where no = 1 or title = '제목1'
        /* 동기로 질의 */
        RealmResults<Bbs> result2 = query2.findAll();
        Bbs bbsFirst = result2.first();
        realm3.beginTransaction();
        bbsFirst.setTitle("수정된 제목");
        realm3.commitTransaction();

        // 비동기1
        Bbs bbs = new Bbs();
        bbs.setNo(1);
        bbs.setTitle("제목");
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bbs);
            }
        });

        // 비동기2
        Realm realm2 = Realm.getDefaultInstance();
        RealmQuery<Bbs> query = realm2.where(Bbs.class);
        query.equalTo("no",1);
        query.findAllAsync().addChangeListener(new RealmChangeListener<RealmResults<Bbs>>() {
            @Override
            public void onChange(RealmResults<Bbs> bbs) {
                Bbs bbs1 = bbs.first();
                bbs1.setTitle("수정");
            }
        });
    }

    public void delete() {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Bbs> result = realm.where(Bbs.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFirstFromRealm(); // 검색결과의 첫번째 삭제

                Bbs bbs = result.get(2); // 특정 행 삭제
                bbs.deleteFromRealm();
                // result.deleteFromRealm(2); // 위와 동일

                result.deleteAllFromRealm(); // 검색결과 전체삭제
            }
        });
    }
}
