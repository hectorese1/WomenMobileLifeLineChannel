package com.ali.mirachannel.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ali.mirachannel.model.ChildDtl;
import com.ali.mirachannel.model.CloseCaseDtl;
import com.ali.mirachannel.model.FamilyPlanning;
import com.ali.mirachannel.model.HouseDtl;
import com.ali.mirachannel.model.PregnantDtl;
import com.ali.mirachannel.model.VaccinatedDtl;
import com.ali.mirachannel.model.VaccineDtl;
import com.ali.mirachannel.model.WeeklyDtl;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;

import java.util.ArrayList;
import java.util.List;

/**this is the main class for database */
public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "MIRAChannel";
	// Database Version
	private static final int  DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "miraDBMS";
	// Table Names
	private static final String TABLE_LOG = "table_userlog";
	private static final String TABLE_HOUSE = "tabhouse";
	private static final String TABLE_WOMEN = "tabwomen";
	private static final String TABLE_WEEKINFO = "tabweekinfo";
	private static final String TABLE_PREGNANT = "pregnant";
	
	private static final String TABLE_CLOSECASE = "closecase";
	private static final String TABLE_CHILD = "tabchild";
	private static final String TABLE_CHILD_Dummy = "tabchild_Dummy";

	private static final String TABLE_VACCINE = "tabvaccine";
	private static final String TABLE_VACCINESTAUS = "tabvaccinestatus";
	private static final String TABLE_FAMILY = "tabfamplan";

	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";
	// Colums for TABLE_USERLOG;
	private static final String USER_ACTIVITY = "activity";
	private static final String USER_TIMECREATED = "created_at";
	// TABLE_HOUSE COLUMS
	private static final String HOUSE_ID = "houseid";
	private static final String HOUSE_NUMBER = "housenumber";
	private static final String HOUSE_LANDMARK = "landmark";
	private static final String HOUSE_FAMILY_HEAD = "familyhead";
	private static final String HOUSE_LATITUDE = "latitude";
	private static final String HOUSE_LONGITUD = "logitude";
	private static final String HOUSE_MEMBERS = "housemembers";
	private static final String HOUSE_MARRIED = "marriedmember";
	private static final String HOUSE_UNMARRIED = "unmarriedmembers";
	private static final String HOUSE_ADOLECENCE = "adolecencegirls";
	private static final String HOUSE_CHILDREN = "children";
	private static final String HOUSE_UPLOADED = "uploaded";
	
	private static final String DATA_UPLOAD_STATU = "uploaded";
	
	private static final String CREATE_TABLE_HOUSE = "CREATE TABLE "
			+ TABLE_HOUSE + " (" + KEY_ID + " INTEGER PRIMARY KEY," + HOUSE_ID
			+ " TEXT," + HOUSE_NUMBER + " TEXT," + HOUSE_LANDMARK + " TEXT,"
			+ HOUSE_FAMILY_HEAD + " TEXT," + HOUSE_LATITUDE + " TEXT,"
			+ HOUSE_LONGITUD + " TEXT," + HOUSE_MEMBERS + " INTEGER,"
			+ HOUSE_MARRIED + " INTEGER," + HOUSE_UNMARRIED + " INTEGER,"
			+ HOUSE_ADOLECENCE + " INTEGER," + HOUSE_CHILDREN + " INTEGER,"
			+ HOUSE_UPLOADED + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";
	
	// TABLE_WOMEN COLUMNS
	private static final String WOMAN_ID = "womanid";
	private static final String PREGNANT_ID = "pregid";
	private static final String WOMAN_NAME = "womanname";
	private static final String WOMAN_HUSBAND = "husbandname";
	private static final String WOMAN_AGE = "age";
	private static final String WOMAN_CHILDREN = "children";
	private static final String WOMAN_LMCDATE = "lmcdate";
	private static final String WOMAN_STATUS = "status";
	private static final String WOMAN_UPLOADED = "uploaded";

	private static final String CREATE_TABLE_WOMEN = "CREATE TABLE "
			+ TABLE_WOMEN + " (" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ PREGNANT_ID + " TEXT," + HOUSE_ID + " TEXT," + WOMAN_ID
			+ " TEXT," + WOMAN_HUSBAND + " TEXT," + WOMAN_NAME + " TEXT,"
			+ WOMAN_AGE + " INTEGER," + WOMAN_CHILDREN + " INTEGER,"
			+ WOMAN_LMCDATE + " DATETIME," + WOMAN_STATUS + " INTEGER,"
			+ WOMAN_UPLOADED + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

	// TABLE_CHILD COLUMNS
	private static final String CHILD_ID = "childid";
	private static final String CHILD_NAME = "name";
	private static final String CHILD_SEX = "sex";
	private static final String CHILD_DOB = "dob";
	private static final String CHILD_STATUS = "status";
	private static final String CHILD_UPLOAD = "uploaded";

	private static final String CREATE_TABLE_CHILD = "CREATE TABLE "
			+ TABLE_CHILD + " (" + KEY_ID + " INTEGER PRIMARY KEY," 
//			+ HOUSE_ID+ " TEXT," 
			+ WOMAN_ID + " TEXT," 
			+ CHILD_ID + " TEXT," 
			+ CHILD_NAME + " TEXT," 
			+ CHILD_SEX + " TEXT," 
			+ CHILD_DOB + " DATETIME,"
			+ CHILD_STATUS + " INTEGER," 
			+ CHILD_UPLOAD + " TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	// TABLE_VACCINE COLUMNS
	private static final String VACCINE_NAME = "vac_name";
	private static final String VACCINE_DEPEND = "depnd";
	private static final String VACCINE_STRDATE = "strdate";
	private static final String VACCINE_ENDDATE = "enddate";
	private static final String VACCINE_DIFDAY = "difday";
	
	private static final String CREATE_TABLE_VACCINE = "CREATE TABLE "
			+ TABLE_VACCINE + " (" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ VACCINE_NAME + " TEXT," 
			+ VACCINE_DEPEND  + " TEXT,"
			+ VACCINE_STRDATE+ " INTEGER,"
			+ VACCINE_ENDDATE + " INTEGER," 
			+ VACCINE_DIFDAY+ " INTEGER"+ ")";

	private static final String CREATE_TABLE_LOG = "CREATE TABLE " + TABLE_LOG
			+ " (" + KEY_ID + " INTEGER PRIMARY KEY," + USER_ACTIVITY
			+ " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

	
	private static final String CLOSECASESTATUS = "status";
	private static final String LOCATIONSTATUS="location";
	private static final String CREATE_TABLE_CLOSECASE = "CREATE TABLE " + TABLE_CLOSECASE
			+ " (" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ PREGNANT_ID + " TEXT,"	
			+ CLOSECASESTATUS  + " TEXT,"
			+ DATA_UPLOAD_STATU + " TEXT,"
			+ LOCATIONSTATUS+" TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";
	
	// TABLE_VACCINE COLUMNS
	private static final String WEEK_NUM = "weeknum";
	private static final String QUEST_ONE = "quesone";
	private static final String QUEST_TWO = "questwo";
	private static final String QUEST_THREE = "questhree";
	private static final String QUEST_FOUR = "quesfour";
	private static final String QUEST_FIVE = "quesfive";
		
	private static final String CREATE_TABLE_WEEKINFO = "CREATE TABLE " + TABLE_WEEKINFO
			+ " (" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ WOMAN_ID	+ " TEXT,"
			+ PREGNANT_ID	+ " TEXT,"
			+ WEEK_NUM + " INTEGER,"
			+ QUEST_ONE + " INTEGER,"			
			+ QUEST_TWO + " INTEGER,"
			+ QUEST_THREE + " INTEGER,"
			+ QUEST_FOUR + " INTEGER,"
			+ QUEST_FIVE + " INTEGER,"
			+ DATA_UPLOAD_STATU + " TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";
	
	
	
	
	private static final String VACCINATED_AT = "vaccat";
	private static final String CREATE_TABLE_VACCSTATUS = "CREATE TABLE " + TABLE_VACCINESTAUS
			+ " (" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ VACCINE_NAME	+ " TEXT,"
			+ CHILD_ID + " TEXT,"
			+ VACCINATED_AT + " DATETIME,"				
			+ DATA_UPLOAD_STATU + " TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";
	
//	
	
	private static final String FP_CYCLE = "cycledays";
	private static final String CREATE_TABLE_FAMILY = "CREATE TABLE " + TABLE_FAMILY
			+ " (" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ WOMAN_NAME	+ " TEXT,"
			+ FP_CYCLE + " TEXT,"
			+ WOMAN_LMCDATE + " DATETIME,"				
			+ KEY_CREATED_AT + " DATETIME" + ")";
	
	public static final DatabaseHelper newInstance(Context context) {
		DatabaseHelper databaseHelper = new DatabaseHelper(context);		
		return databaseHelper;
	}
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}



	public long insertintoHouse(HouseDtl house) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(HOUSE_ID, house.getHouseID());
		values.put(HOUSE_NUMBER, house.getHouseNumber());
		values.put(HOUSE_FAMILY_HEAD, house.getFamilyHead());
		values.put(HOUSE_LANDMARK, house.getLandMark());
		values.put(HOUSE_LATITUDE, house.getLatitude());
		values.put(HOUSE_LONGITUD, house.getLongitude());
		values.put(HOUSE_MEMBERS, house.getFamilyMembers());
		values.put(HOUSE_MARRIED, house.getMarriedWomen());
		values.put(HOUSE_UNMARRIED, house.getUnmarriedWomen());
		values.put(HOUSE_ADOLECENCE, house.getAdolecenceGorls());
		values.put(HOUSE_CHILDREN, house.getChildrens());
		values.put(HOUSE_UPLOADED, "0");
		values.put(KEY_CREATED_AT, MiraConstants.getDateTime());
		return db.insert(TABLE_HOUSE, null, values);
	}

	public List<HouseDtl> gethouseByTag(String tag_name) {
		List<HouseDtl>houseDtls = new ArrayList<HouseDtl>();
		String selectQuery = "SELECT *FROM tabhouse WHERE "+tag_name;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				HouseDtl hd = new HouseDtl();
				hd.setKeyId((c.getInt((c.getColumnIndex(KEY_ID)))));
				hd.setHouseID(String.valueOf(c.getInt((c.getColumnIndex(HOUSE_ID)))));
				hd.setHouseNumber((c.getString(c.getColumnIndex(HOUSE_NUMBER))));
				hd.setFamilyHead(c.getString(c.getColumnIndex(HOUSE_FAMILY_HEAD)));
				hd.setLandMark(c.getString(c.getColumnIndex(HOUSE_LANDMARK)));
				hd.setLatitude(Long.parseLong(c.getString(c.getColumnIndex(HOUSE_LATITUDE))));
				hd.setLongitude(Long.parseLong(c.getString(c.getColumnIndex(HOUSE_LONGITUD))));
				hd.setFamilyMembers(c.getInt((c.getColumnIndex(HOUSE_MEMBERS))));
				hd.setMarriedWomen(c.getInt((c.getColumnIndex(HOUSE_MARRIED))));
				hd.setUnmarriedWomen(c.getInt((c.getColumnIndex(HOUSE_UNMARRIED))));
				hd.setAdolecenceGorls(c.getInt((c.getColumnIndex(HOUSE_ADOLECENCE))));
				hd.setChildrens(c.getInt((c.getColumnIndex(HOUSE_CHILDREN))));
				hd.setUploaded(c.getString((c.getColumnIndex(HOUSE_UPLOADED))));
				hd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));				
				// adding to todo list
				houseDtls.add(hd);
			} while (c.moveToNext());
		}
		
		return houseDtls;
	}
	
	public int updateHouseId(HouseDtl house) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(HOUSE_ID, house.getHouseID());
		values.put(HOUSE_NUMBER, house.getHouseNumber());
		values.put(HOUSE_FAMILY_HEAD, house.getFamilyHead());
		values.put(HOUSE_LANDMARK, house.getLandMark());
		values.put(HOUSE_LATITUDE, house.getLatitude());
		values.put(HOUSE_LONGITUD, house.getLongitude());
		values.put(HOUSE_MEMBERS, house.getFamilyMembers());
		values.put(HOUSE_MARRIED, house.getMarriedWomen());
		values.put(HOUSE_UNMARRIED, house.getUnmarriedWomen());
		values.put(HOUSE_ADOLECENCE, house.getAdolecenceGorls());
		values.put(HOUSE_CHILDREN, house.getChildrens());
		values.put(HOUSE_UPLOADED,house.getUploaded());
		values.put(KEY_CREATED_AT, house.getCreatedAt());
		
		// updating row
		return db.update(TABLE_HOUSE, values, KEY_ID + " = ?",
				new String[]{String.valueOf(house.getKeyId())});
	}
	
	public long insertintoUserLog(String activity) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(USER_ACTIVITY, activity);

		values.put(USER_TIMECREATED, MiraConstants.getDateTime());
		return db.insert(TABLE_LOG, null, values);

	}

	public long insertWomenRec(WomenDtl womenDtl) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(HOUSE_ID, womenDtl.getHouseID());
		values.put(WOMAN_ID, womenDtl.getWomanId());
		values.put(PREGNANT_ID, womenDtl.getPregnentId());
		values.put(WOMAN_NAME, womenDtl.getName());		
		values.put(WOMAN_HUSBAND, womenDtl.getHusname());
		values.put(WOMAN_AGE, womenDtl.getAge());
		values.put(WOMAN_CHILDREN, womenDtl.getChildren());
		values.put(WOMAN_LMCDATE, womenDtl.getLmcDate());
		values.put(WOMAN_STATUS, womenDtl.getStatus());
		values.put(WOMAN_UPLOADED, womenDtl.getUploade());
		values.put(KEY_CREATED_AT, MiraConstants.getDateTime());
		return db.insert(TABLE_WOMEN, null, values);
	}
	
	public int updateWomanRec(WomenDtl womenDtl) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(HOUSE_ID, womenDtl.getHouseID());
		values.put(WOMAN_ID, womenDtl.getWomanId());
		values.put(PREGNANT_ID, womenDtl.getPregnentId());
		values.put(WOMAN_NAME, womenDtl.getName());		
		values.put(WOMAN_HUSBAND, womenDtl.getHusname());
		values.put(WOMAN_AGE, womenDtl.getAge());
		values.put(WOMAN_CHILDREN, womenDtl.getChildren());
		values.put(WOMAN_LMCDATE, womenDtl.getLmcDate());
		values.put(WOMAN_STATUS, womenDtl.getStatus());
		values.put(WOMAN_UPLOADED, womenDtl.getUploade());
		values.put(KEY_CREATED_AT, womenDtl.getCreatedAt());
		
		return db.update(TABLE_WOMEN, values, KEY_ID + " = ?",
				new String[]{String.valueOf(womenDtl.getKeyId())});
	}
	
	public void updateWomanRec(String query_string) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query_string);
	}
	
	public List<WomenDtl> getWomenByTag(String tag_name) {
		List<WomenDtl>womenDtls = new ArrayList<WomenDtl>();
		String selectQuery = "SELECT * FROM tabwomen WHERE "+tag_name;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				WomenDtl wd = new WomenDtl();
				wd.setKeyId((c.getInt((c.getColumnIndex(KEY_ID)))));
				wd.setHouseID(String.valueOf(c.getInt((c.getColumnIndex(HOUSE_ID)))));
				wd.setWomanId((c.getString(c.getColumnIndex(WOMAN_ID))));
				wd.setStatus((c.getString(c.getColumnIndex(WOMAN_STATUS))));
				wd.setPregnentId((c.getString(c.getColumnIndex(PREGNANT_ID))));
				wd.setName(c.getString(c.getColumnIndex(WOMAN_NAME)));
				wd.setHusname(c.getString(c.getColumnIndex(WOMAN_HUSBAND)));
				wd.setAge(c.getInt(c.getColumnIndex(WOMAN_AGE)));
				wd.setChildren(c.getInt(c.getColumnIndex(WOMAN_CHILDREN)));
				wd.setLmcDate(c.getString((c.getColumnIndex(WOMAN_LMCDATE))));
				wd.setUploade(c.getString((c.getColumnIndex(HOUSE_UPLOADED))));
				wd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));				
				womenDtls.add(wd);
			} while (c.moveToNext());
		}
		
		return womenDtls;
	}


	public List<WomenDtl> getWomenByTag_Fa(String tag_name) {
		List<WomenDtl>womenDtls = new ArrayList<WomenDtl>();
		String selectQuery = "SELECT * FROM tabwomen WHERE "+tag_name;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				String s=c.getString((c.getColumnIndex(WOMAN_LMCDATE)));
				int weeknum=MiraConstants.calculateWeekNumber(s);
				if(weeknum>20){
					WomenDtl wd = new WomenDtl();
					wd.setKeyId((c.getInt((c.getColumnIndex(KEY_ID)))));
					wd.setHouseID(String.valueOf(c.getInt((c.getColumnIndex(HOUSE_ID)))));
					wd.setWomanId((c.getString(c.getColumnIndex(WOMAN_ID))));
					wd.setStatus((c.getString(c.getColumnIndex(WOMAN_STATUS))));
					wd.setPregnentId((c.getString(c.getColumnIndex(PREGNANT_ID))));
					wd.setName(c.getString(c.getColumnIndex(WOMAN_NAME)));
					wd.setHusname(c.getString(c.getColumnIndex(WOMAN_HUSBAND)));
					wd.setAge(c.getInt(c.getColumnIndex(WOMAN_AGE)));
					wd.setChildren(c.getInt(c.getColumnIndex(WOMAN_CHILDREN)));
					wd.setLmcDate(c.getString((c.getColumnIndex(WOMAN_LMCDATE))));
					wd.setUploade(c.getString((c.getColumnIndex(HOUSE_UPLOADED))));
					wd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));
					womenDtls.add(wd);
				}

			} while (c.moveToNext());
		}

		return womenDtls;
	}

	public List<WomenDtl> getWomenByTag_F(String tag_name) {
		List<WomenDtl>womenDtls = new ArrayList<WomenDtl>();
		String selectQuery = tag_name;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				WomenDtl wd = new WomenDtl();
				wd.setKeyId((c.getInt((c.getColumnIndex(KEY_ID)))));
				wd.setHouseID(String.valueOf(c.getInt((c.getColumnIndex(HOUSE_ID)))));
				wd.setWomanId((c.getString(c.getColumnIndex(WOMAN_ID))));
				wd.setStatus((c.getString(c.getColumnIndex(WOMAN_STATUS))));
				wd.setPregnentId((c.getString(c.getColumnIndex(PREGNANT_ID))));
				wd.setName(c.getString(c.getColumnIndex(WOMAN_NAME)));
				wd.setHusname(c.getString(c.getColumnIndex(WOMAN_HUSBAND)));
				wd.setAge(c.getInt(c.getColumnIndex(WOMAN_AGE)));
				wd.setChildren(c.getInt(c.getColumnIndex(WOMAN_CHILDREN)));
				wd.setLmcDate(c.getString((c.getColumnIndex(WOMAN_LMCDATE))));
				wd.setUploade(c.getString((c.getColumnIndex(HOUSE_UPLOADED))));
				wd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));
				womenDtls.add(wd);
			} while (c.moveToNext());
		}

		return womenDtls;
	}
	
	public List<WomenDtl> getWomenByQuery(String queryString) {
		List<WomenDtl>womenDtls = new ArrayList<WomenDtl>();
		String selectQuery = queryString;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				WomenDtl wd = new WomenDtl();
				wd.setKeyId((c.getInt((c.getColumnIndex(KEY_ID)))));
				wd.setHouseID(String.valueOf(c.getInt((c.getColumnIndex(HOUSE_ID)))));
				wd.setHouseNumber((c.getString(c.getColumnIndex(HOUSE_NUMBER))));
				wd.setWomanId((c.getString(c.getColumnIndex(WOMAN_ID))));
				wd.setPregnentId((c.getString(c.getColumnIndex(PREGNANT_ID))));
				wd.setName(c.getString(c.getColumnIndex(WOMAN_NAME)));
				wd.setHusname(c.getString(c.getColumnIndex(WOMAN_HUSBAND)));
				wd.setAge(c.getInt(c.getColumnIndex(WOMAN_AGE)));
				wd.setChildren(c.getInt(c.getColumnIndex(WOMAN_CHILDREN)));
				wd.setLmcDate(c.getString((c.getColumnIndex(WOMAN_LMCDATE))));
				wd.setUploade(c.getString((c.getColumnIndex(HOUSE_UPLOADED))));
				wd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));	
				
				wd.setAnc_1(c.getInt(c.getColumnIndex(ANC_1)));
				wd.setAnc_2(c.getInt(c.getColumnIndex(ANC_2)));
				wd.setAnc_3(c.getInt(c.getColumnIndex(ANC_3)));
				wd.setAnc_4(c.getInt(c.getColumnIndex(ANC_4)));
				
				womenDtls.add(wd);
			} while (c.moveToNext());
		}
		
		return womenDtls;
	}
		
	public void updateWomenDetails(String query_string) {
		query_string = "UPDATE "+TABLE_WOMEN+" SET "+query_string;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query_string);
	}
	
	public long insertChildRec(ChildDtl childDtl) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(HOUSE_ID, childDtl.getHouseID());
		values.put(WOMAN_ID, childDtl.getWomanId());
		values.put(CHILD_ID, childDtl.getChildId());
		values.put(CHILD_NAME, childDtl.getName());
		values.put(CHILD_SEX, childDtl.getSex());
		values.put(CHILD_DOB, childDtl.getDob());
		values.put(CHILD_STATUS, childDtl.getStatus());//childDtl.getStatus());
		values.put(CHILD_UPLOAD, childDtl.getUpload());//childDtl.getUpload());
		values.put(KEY_CREATED_AT, MiraConstants.getDateTime());
		return db.insert(TABLE_CHILD, null, values);
	}

	
	public int updateChildRec(ChildDtl childDtl) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(HOUSE_ID, childDtl.getHouseID());
		values.put(WOMAN_ID, childDtl.getWomanId());
		values.put(CHILD_ID, childDtl.getChildId());
		values.put(CHILD_NAME, childDtl.getName());
		values.put(CHILD_SEX, childDtl.getSex());
		values.put(CHILD_DOB, childDtl.getDob());
		values.put(CHILD_STATUS, childDtl.getStatus());
		values.put(CHILD_UPLOAD, childDtl.getUpload());
		values.put(KEY_CREATED_AT, childDtl.getCreatedAt());
		
		return db.update(TABLE_CHILD, values, KEY_ID + " = ?",
				new String[] { String.valueOf(childDtl.getKeyId()) });
	}
	
	public List<ChildDtl> getChildByTag(String tag_name) {
		List<ChildDtl>childDtls = new ArrayList<ChildDtl>();
		String selectQuery = "SELECT *FROM tabchild WHERE "+tag_name;
				
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				ChildDtl cd = new ChildDtl();
				cd.setKeyId((c.getInt((c.getColumnIndex(KEY_ID)))));
//				cd.setHouseID(String.valueOf(c.getInt((c.getColumnIndex(HOUSE_ID)))));
				cd.setWomanId((c.getString(c.getColumnIndex(WOMAN_ID))));
				cd.setChildId((c.getString(c.getColumnIndex(CHILD_ID))));
				cd.setName(c.getString(c.getColumnIndex(CHILD_NAME)));
				cd.setSex(c.getString(c.getColumnIndex(CHILD_SEX)));
				cd.setDob(c.getString(c.getColumnIndex(CHILD_DOB)));
				cd.setStatus(c.getString(c.getColumnIndex(CHILD_STATUS)));
				cd.setUpload(c.getString((c.getColumnIndex(CHILD_UPLOAD))));
				cd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));				
				childDtls.add(cd);
			} while (c.moveToNext());
		}		
		return childDtls;
	}
		
	public List<ChildDtl> getChildByQuery(String tag_name) {
		List<ChildDtl>childDtls = new ArrayList<ChildDtl>();
		String selectQuery = tag_name;//"SELECT *FROM tabchild WHERE "+tag_name;
				
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				ChildDtl cd = new ChildDtl();
				cd.setKeyId((c.getInt((c.getColumnIndex(KEY_ID)))));
//				cd.setHouseID(String.valueOf(c.getInt((c.getColumnIndex(HOUSE_ID)))));
				cd.setWomanId((c.getString(c.getColumnIndex(WOMAN_ID))));
				cd.setWomanName((c.getString(c.getColumnIndex(WOMAN_NAME))));
				cd.setChildId((c.getString(c.getColumnIndex(CHILD_ID))));
				cd.setName(c.getString(c.getColumnIndex(CHILD_NAME)));
				cd.setSex(c.getString(c.getColumnIndex(CHILD_SEX)));
				cd.setDob(c.getString(c.getColumnIndex(CHILD_DOB)));
				cd.setStatus(c.getString(c.getColumnIndex(CHILD_STATUS)));
				cd.setUpload(c.getString((c.getColumnIndex(CHILD_UPLOAD))));
				cd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));			
				childDtls.add(cd);
			} while (c.moveToNext());
		}		
		return childDtls;
	}
	
	public long insetWeeklyDetails(WeeklyDtl weeklyDtl) {
        System.out.println("Record Saved....");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(WOMAN_ID, weeklyDtl.getWomanId());
		values.put(PREGNANT_ID, weeklyDtl.getPregnentId());
		values.put(WEEK_NUM, weeklyDtl.getWeekNum());		
		values.put(QUEST_ONE, weeklyDtl.getQuesOne());
		values.put(QUEST_TWO, weeklyDtl.getQuesTwo());
		values.put(QUEST_THREE, weeklyDtl.getQuesThree());
		values.put(QUEST_FOUR, weeklyDtl.getQuesFour());
		values.put(QUEST_FIVE, weeklyDtl.getQuesFive());
		values.put(DATA_UPLOAD_STATU, weeklyDtl.getUploaded());
		values.put(KEY_CREATED_AT, MiraConstants.getDateTime());		
		return db.insert(TABLE_WEEKINFO, null, values);
	}
	
	public List<WeeklyDtl> getWeeklyDetailsByTag(String tag_name) {
		List<WeeklyDtl>weeklyDtls = new ArrayList<WeeklyDtl>();
		String selectQuery = tag_name;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				WeeklyDtl wd = new WeeklyDtl();
				wd.setKeyId((c.getInt((c.getColumnIndex(KEY_ID)))));
				wd.setWomanId((c.getString(c.getColumnIndex(WOMAN_ID))));
				wd.setPregnentId((c.getString(c.getColumnIndex(PREGNANT_ID))));
				wd.setWeekNum((c.getInt((c.getColumnIndex(WEEK_NUM)))));
				
				wd.setQuesOne((c.getInt((c.getColumnIndex(QUEST_ONE)))));
				wd.setQuesTwo((c.getInt((c.getColumnIndex(QUEST_TWO)))));
				wd.setQuesThree((c.getInt((c.getColumnIndex(QUEST_THREE)))));
				wd.setQuesFour((c.getInt((c.getColumnIndex(QUEST_FOUR)))));
				wd.setQuesFive((c.getInt((c.getColumnIndex(QUEST_FIVE)))));
				
				wd.setUploaded(c.getString((c.getColumnIndex(DATA_UPLOAD_STATU))));
				wd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));				
				weeklyDtls.add(wd);
                System.out.println(" WeeklyDtl "+wd);
			} while (c.moveToNext());
		}		
		return weeklyDtls;		
	}
	
	public void updateWeeklyInfo(String update_query) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(update_query);
	}
	
	public void updateWeeklyinfoDetails(WeeklyDtl weeklyDtl) {
		SQLiteDatabase db = this.getWritableDatabase();		
		ContentValues values = new ContentValues();
		
		values.put(QUEST_ONE, weeklyDtl.getQuesOne());
		values.put(QUEST_TWO, weeklyDtl.getQuesTwo());
		values.put(QUEST_THREE, weeklyDtl.getQuesThree());
		values.put(QUEST_FOUR, weeklyDtl.getQuesFour());
		values.put(QUEST_FIVE, weeklyDtl.getQuesFive());
		
		int i= db.update(TABLE_WEEKINFO, values, PREGNANT_ID + " = ?", new String[] { String.valueOf(weeklyDtl.getPregnentId())});
		System.out.println(i);
	}
	
	public long insetVaccineTable(VaccineDtl vaccineDtl) {
		SQLiteDatabase db = this.getWritableDatabase();		
		ContentValues values = new ContentValues();
		values.put(VACCINE_NAME, vaccineDtl.getVaccineName());
		values.put(VACCINE_DEPEND, vaccineDtl.getVaccineDepend());
		values.put(VACCINE_STRDATE, vaccineDtl.getStartDay());
		values.put(VACCINE_ENDDATE, vaccineDtl.getEndDay());
		values.put(VACCINE_DIFDAY, vaccineDtl.getDiffDay());

		return db.insert(TABLE_VACCINE, null, values);
	}
	
	public List<VaccineDtl> getVaccineByTag(String tag_name) {
		System.out.println("Tag Name passed....."+tag_name);
        System.out.println("VaccineDtl From Database");
		List<VaccineDtl>vaccineDtls = new ArrayList<VaccineDtl>();
		String selectQuery = "SELECT *FROM tabvaccine";// WHERE "+tag_name;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);		
		if (c.moveToFirst()) {
			do {
				VaccineDtl vd = new VaccineDtl();

                vd.setVaccineName((c.getString(c.getColumnIndex(VACCINE_NAME))));
				vd.setVaccineDepend((c.getString(c.getColumnIndex(VACCINE_DEPEND))));
				vd.setStartDay(c.getInt((c.getColumnIndex(VACCINE_STRDATE))));
				vd.setEndDay(c.getInt((c.getColumnIndex(VACCINE_ENDDATE))));
				vd.setDiffDay(c.getInt((c.getColumnIndex(VACCINE_DIFDAY))));			
				vaccineDtls.add(vd);
                System.out.println("VaccineDtl "+vd);
			} while (c.moveToNext());
		}
		return vaccineDtls;
	}
	 
	public long insetVaccineStatusTable(VaccinatedDtl vaccinatedDtl) {
		SQLiteDatabase db = this.getWritableDatabase();		
		ContentValues values = new ContentValues();
		values.put(VACCINE_NAME, vaccinatedDtl.getName());
		values.put(CHILD_ID, vaccinatedDtl.getChildId());
		values.put(VACCINATED_AT, vaccinatedDtl.getDate());
		values.put(DATA_UPLOAD_STATU, vaccinatedDtl.getUpload());
		values.put(KEY_CREATED_AT, MiraConstants.getDateTime());
        System.out.println("Vaccinated Details "+vaccinatedDtl);
		return db.insert(TABLE_VACCINESTAUS, null, values);
	}
	
	public List<VaccinatedDtl> getvaccStatusByTag(String tag_name) {
		List<VaccinatedDtl>vaccinatedDtls = new ArrayList<VaccinatedDtl>();
		String selectQuery = tag_name;
		System.out.println("VaccinatedDtl From Database");
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				VaccinatedDtl vd = new VaccinatedDtl();
				vd.setKeyId(c.getInt((c.getColumnIndex(KEY_ID))));
				vd.setChildId(c.getString(c.getColumnIndex(CHILD_ID)));
				vd.setName(c.getString(c.getColumnIndex(VACCINE_NAME)));
				vd.setDate(c.getString(c.getColumnIndex(VACCINATED_AT)));				
				
				vd.setUpload(c.getString((c.getColumnIndex(DATA_UPLOAD_STATU))));
				vd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));				
				vaccinatedDtls.add(vd);
                System.out.println("VaccinatedDtl "+vd);
			} while (c.moveToNext());
		}		
		return vaccinatedDtls;		
	}
	
	
	
	public long insertCloseCase(CloseCaseDtl closeCaseDtl) {
		SQLiteDatabase db = this.getWritableDatabase();		
		ContentValues values = new ContentValues();
		values.put(PREGNANT_ID, closeCaseDtl.getPregnentId());
		values.put(CLOSECASESTATUS, closeCaseDtl.getStatus());
		values.put(DATA_UPLOAD_STATU, closeCaseDtl.getUploade());
		values.put(KEY_CREATED_AT, closeCaseDtl.getCreatedAt());
		return db.insert(TABLE_CLOSECASE, null, values);
	}
	
	
	public List<CloseCaseDtl> getCloseCaseStatusByTag(String tag_name) {
		List<CloseCaseDtl>closeCaseDtls = new ArrayList<CloseCaseDtl>();
		String selectQuery = tag_name;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				CloseCaseDtl vd = new CloseCaseDtl();
				vd.setKeyId(c.getInt((c.getColumnIndex(KEY_ID))));
				vd.setPregnentId(c.getString(c.getColumnIndex(PREGNANT_ID)));
				vd.setStatus(c.getInt(c.getColumnIndex(CLOSECASESTATUS)));										
				vd.setUploade(c.getString((c.getColumnIndex(DATA_UPLOAD_STATU))));
				vd.setCreatedAt(c.getString((c.getColumnIndex(KEY_CREATED_AT))));				
				closeCaseDtls.add(vd);
			} while (c.moveToNext());
		}		
		return closeCaseDtls;		
	}
	
	public void updateCloseCase(String tag_name) {
		SQLiteDatabase db = this.getWritableDatabase();	
		tag_name = "UPDATE closecase SET uploaded = '1' WHERE uploaded='0'";
		db.execSQL(tag_name);
	}
	
	public void updateVaccineTable(String tag_name) {
		SQLiteDatabase db = this.getWritableDatabase();	
		tag_name = "UPDATE tabvaccinestatus SET uploaded = '1' WHERE uploaded = '0'";
		db.execSQL(tag_name);
	}
	
	public long insertFamiyPlanning(FamilyPlanning planning) {
		SQLiteDatabase db = this.getWritableDatabase();		
		ContentValues values = new ContentValues();
		values.put(WOMAN_NAME, planning.getName());
		values.put(FP_CYCLE, planning.getCycleDays());
		values.put(WOMAN_LMCDATE, planning.getLmcDate());
		values.put(KEY_CREATED_AT,MiraConstants.getDateTime());
		return db.insert(TABLE_FAMILY, null, values);
	}
	
	public List<FamilyPlanning> getFamilyPlanning(String tag_name) {
		List<FamilyPlanning>familyPlannings = new ArrayList<FamilyPlanning>();
		String selectQuery = "SELECT *FROM tabfamplan";//tag_name;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				FamilyPlanning vd = new FamilyPlanning();
				vd.setKeyId(c.getInt((c.getColumnIndex(KEY_ID))));
				vd.setCycleDays(c.getInt(c.getColumnIndex(FP_CYCLE)));
				vd.setName(c.getString(c.getColumnIndex(WOMAN_NAME)));
				vd.setLmcDate(c.getString(c.getColumnIndex(WOMAN_LMCDATE)));																	
				familyPlannings.add(vd);
			} while (c.moveToNext());
		}		
		return familyPlannings;		
	}
	
	public List<FamilyPlanning> getFamilyPlanningIndivid(int tag_name) {
		List<FamilyPlanning>familyPlannings = new ArrayList<FamilyPlanning>();
		String selectQuery = "SELECT *FROM tabfamplan WHERE "+KEY_ID+" = "+tag_name;//tag_name;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				FamilyPlanning vd = new FamilyPlanning();
				vd.setKeyId(c.getInt((c.getColumnIndex(KEY_ID))));
				vd.setCycleDays(c.getInt(c.getColumnIndex(FP_CYCLE)));
				vd.setName(c.getString(c.getColumnIndex(WOMAN_NAME)));
				vd.setLmcDate(c.getString(c.getColumnIndex(WOMAN_LMCDATE)));																	
				familyPlannings.add(vd);
			} while (c.moveToNext());
		}		
		return familyPlannings;		
	}
	
	public void updateFamilyPlanning(String query_string) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query_string);
	}
	
	private static String ANC_1 = "anc1";
	private static String ANC_2 = "anc2";
	private static String ANC_3 = "anc3";
	private static String ANC_4 = "anc4";
	
	private static final String CREATE_TABLE_PREGNANT = "CREATE TABLE " + TABLE_PREGNANT
			+ " (" 
			+ KEY_ID + " INTEGER PRIMARY KEY,"
			+ WOMAN_ID	+ " TEXT,"
			+ PREGNANT_ID + " TEXT,"	
			+ WOMAN_LMCDATE + " DATETIME,"
			+ ANC_1 + " TEXT,"	
			+ ANC_2 + " TEXT,"	
			+ ANC_3 + " TEXT,"	
			+ ANC_4 + " TEXT,"	
			+ KEY_CREATED_AT + " DATETIME" + ")";
	
	public long insertPregAnc(PregnantDtl pregnantDtl) {
		SQLiteDatabase db = this.getWritableDatabase();		
		ContentValues values = new ContentValues();
		values.put(WOMAN_ID, pregnantDtl.getWomanId());
		values.put(PREGNANT_ID, pregnantDtl.getPregId());
		values.put(WOMAN_LMCDATE, pregnantDtl.getLmcDate());
		values.put(ANC_1, pregnantDtl.getAnc_1());
		values.put(ANC_2, pregnantDtl.getAnc_2());
		values.put(ANC_3, pregnantDtl.getAnc_3());
		values.put(ANC_4, pregnantDtl.getAnc_4());
		values.put(KEY_CREATED_AT, MiraConstants.getDateTime());
		return db.insert(TABLE_PREGNANT, null, values);
	} 
	
	public List<PregnantDtl> getPregnantANC(String tag_name) {
		List<PregnantDtl> pregnantDtls = new ArrayList<PregnantDtl>();
		String selectQuery = "SELECT *FROM pregnant";//tag_name;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				PregnantDtl vd = new PregnantDtl();
				vd.setKeyId(c.getInt((c.getColumnIndex(KEY_ID))));
				vd.setWomanId(c.getInt(c.getColumnIndex(WOMAN_ID)));
				vd.setPregId(c.getInt(c.getColumnIndex(PREGNANT_ID)));
				
				vd.setAnc_1(c.getInt(c.getColumnIndex(ANC_1)));
				vd.setAnc_2(c.getInt(c.getColumnIndex(ANC_2)));
				vd.setAnc_3(c.getInt(c.getColumnIndex(ANC_3)));
				vd.setAnc_4(c.getInt(c.getColumnIndex(ANC_4)));
				
				vd.setLmcDate(c.getString(c.getColumnIndex(WOMAN_LMCDATE)));				
				vd.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));																	
				pregnantDtls.add(vd);
			} while (c.moveToNext());
		}		
		return pregnantDtls;		
	}
	
	public void updatePregnantANC(String update_query) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(update_query);
	}
	
	public void deletePregnantWoman(int id) {
		SQLiteDatabase db = this.getWritableDatabase();		
       int del = db.delete(TABLE_PREGNANT, KEY_ID + "=" + id, null);
        db.close(); // Closing database connection
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_LOG);
		db.execSQL(CREATE_TABLE_HOUSE);
		db.execSQL(CREATE_TABLE_WOMEN);
		db.execSQL(CREATE_TABLE_CHILD);
		db.execSQL(CREATE_TABLE_VACCINE);
		db.execSQL(CREATE_TABLE_PREGNANT);
		db.execSQL(CREATE_TABLE_WEEKINFO);
		db.execSQL(CREATE_TABLE_VACCSTATUS);
		db.execSQL(CREATE_TABLE_CLOSECASE);
		db.execSQL(CREATE_TABLE_FAMILY);
		
	}
		
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}




}
