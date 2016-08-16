package com.movitech.mobilesafe.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jennifer on 8/16/16.
 */
public class AddressDao {

    private static final String PATH = "data/data/com.movitech.mobilesafe/files/address.db";

    public static String getAddress(String number) {
        String address = "未知号码";

//        ^1[3-8]\d{9}$
        if (number.matches("^1[3-8]\\d{9}$")) {
            SQLiteDatabase database = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = database.rawQuery("SELECT location FROM data2 WHERE id = (SELECT outkey FROM data1 WHERE id = ?)", new String[]{number.substring(0, 7)});
            if (cursor.moveToNext()) {
                address = cursor.getString(0);
            }
        }else if (number.matches("^\\d+$")) {
            switch (number.length()) {
                case 3:
                    address = "报警电话";
                    break;
                case 4:
                    address = "模拟器";
                    break;
                case 5:
                    address = "客服电话";
                    break;
                case 7:
                case 8:
                    address = "本地电话";
                default:
                    break;
            }
        }

        return address;
    }
}
