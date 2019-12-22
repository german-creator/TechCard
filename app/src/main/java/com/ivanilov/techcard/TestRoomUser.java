package com.ivanilov.techcard;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "users")
public class TestRoomUser {

    public  TestRoomUser (String id, String userName){
        this.mId = id;
        this.mUserName = userName;
    }

    @PrimaryKey
    @NonNull
    public String mId;

    public String getmId (){ return mId; }
    public void setmId (String mId){
        this.mId = mId;
    }

//    @ColumnInfo(name = "username")
    public String mUserName;

    public String getUserName (){
        return mUserName;
    }
    public void setmUserName (String mUserName){
        this.mUserName = mUserName;
    }



//    @ColumnInfo(name = "last_update")
//    private Date mDate;
//
//    @Ignore
//    public TestRoomUser(String userName) {
//        mId = UUID.randomUUID().toString();
//        mUserName = userName;
////        mDate = new Date(System.currentTimeMillis());
//    }
//
//    public TestRoomUser(String id, String userName, Date date) {
//        this.mId = id;
//        this.mUserName = userName;
////        this.mDate = date;
//    }

}
