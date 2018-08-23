package ulukmyrzategin.auapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ulukmyrzategin.auapp.data.model.SearchsModel;
import ulukmyrzategin.auapp.data.model.VacanciesModel;


/**
 * Created by $TheSusanin on 21.08.2018.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AUApplication";
    private static final int DB_VERSION = 2;

    private static final String ID = "_id";

    private static final String TABLE_MAIN = "TABLE_MAIN";
    private static final String TABLE_FEATURED = "TABLE_FEATURED"; //Подходящие
    private static final String TABLE_WATCHED = "TABLE_WATCHED";
    private static final String TABLE_SEARCH = "TABLE_SEARCH";

    // Variables of table main && featured
    private static final String PID = "PID";
    private static final String PROFESSION = "PROFESSION";
    private static final String DATE = "DATE";
    private static final String HEADER = "HEADER";
    private static final String SALARY = "SALARY";
    private static final String CHECKBOX_FEATURED = "CHECKBOX_ELECTED";
    private static final String PROFILE = "PROFILE";
    private static final String WATCHED = "WATCHED";
    private static final String SITE_ADDRESS = "SITE_ADDRESS";
    private static final String TELEPHONE = "TELEPHONE";
    private static final String BODY = "BODY";

    // Variables of table search
    private static final String REGIME_SEARCH = "REGIME_SEARCH"; // режим работы
    private static final String SALARY_SEARCH = "SALARY_SEARCH"; // Оклад

    // Creating main vacancies table
    private static final String CREATE_TABLE_MAIN = "CREATE TABLE IF NOT EXISTS " +
            TABLE_MAIN + "(" +
            ID + " INTEGER_PRIMARY_KEY, " +
            PID + " TEXT, " +
            PROFILE + " TEXT, " +
            PROFESSION + " TEXT, " +
            DATE + " TEXT, " +
            HEADER + " TEXT, " +
            SALARY + " TEXT, " +
            SITE_ADDRESS + " TEXT, " +
            TELEPHONE + " TEXT, " +
            BODY + " TEXT);";

    // Creating elected vacancies table
    private static final String CREATE_TABLE_FEATURED = "CREATE TABLE IF NOT EXISTS " +
            TABLE_FEATURED + "(" +
            ID + " INTEGER_PRIMARY_KEY, " +
            PID + " TEXT, " +
            PROFILE + " TEXT, " +
            PROFESSION + " TEXT, " +
            DATE + " TEXT, " +
            HEADER + " TEXT, " +
            SALARY + " TEXT, " +
            SITE_ADDRESS + " TEXT, " +
            TELEPHONE + " TEXT, " +
            BODY + " TEXT, " +
            CHECKBOX_FEATURED + " INTEGER DEFAULT 0);";

    // Creating watched table
    private static final String CREATE_TABLE_WATCHED = "CREATE TABLE IF NOT EXISTS " +
            TABLE_WATCHED + "(" +
            ID + " INTEGER_PRIMARY_KEY, " +
            PID + " TEXT, " +
            WATCHED + " INTEGER DEFAULT 0);";

    // Creating search table
    private static final String CREATE_TABLE_SEARCH = "CREATE TABLE IF NOT EXISTS " +
            TABLE_SEARCH + "(" +
            ID + " INTEGER_PRIMARY_KEY, " +
            REGIME_SEARCH + " TEXT, " +
            SALARY_SEARCH + " TEXT);";


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MAIN);
        sqLiteDatabase.execSQL(CREATE_TABLE_FEATURED);
        sqLiteDatabase.execSQL(CREATE_TABLE_WATCHED);
        sqLiteDatabase.execSQL(CREATE_TABLE_SEARCH);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FEATURED);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCHED);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH);
        onCreate(sqLiteDatabase);
    }

    // Start of list without Internet

    public void saveListWithoutInternet(List<VacanciesModel> vacancyModels){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        db.delete(TABLE_MAIN, null, null);

        for (int i = 0; i < vacancyModels.size(); i++) {
            VacanciesModel vacancyModel = vacancyModels.get(i);
            cv.put(PID, vacancyModel.getPid());
            cv.put(PROFESSION, vacancyModel.getProfession());
            cv.put(DATE, vacancyModel.getData());
            cv.put(HEADER, vacancyModel.getHeader());
            cv.put(SALARY, vacancyModel.getSalary());
            cv.put(PROFILE, vacancyModel.getProfile());
            cv.put(SITE_ADDRESS, vacancyModel.getSiteAddress());
            cv.put(TELEPHONE, vacancyModel.getTelephone());
            cv.put(BODY, vacancyModel.getBody());
            db.insert(TABLE_MAIN, null, cv);
        }
        db.close();
    }

    public ArrayList<VacanciesModel> getListWithoutInternet(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<VacanciesModel> vacancyModels = new ArrayList<>();
        Cursor cursor = db.query(TABLE_MAIN, null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            int pidIndex = cursor.getColumnIndex(PID);
            int professionIndex = cursor.getColumnIndex(PROFESSION);
            int dateIndex = cursor.getColumnIndex(DATE);
            int headerIndex = cursor.getColumnIndex(HEADER);
            int salaryIndex = cursor.getColumnIndex(SALARY);
            int profileIndex = cursor.getColumnIndex(PROFILE);
            int siteAddressIndex = cursor.getColumnIndex(SITE_ADDRESS);
            int telephoneIndex = cursor.getColumnIndex(TELEPHONE);
            int bodyIndex = cursor.getColumnIndex(BODY);
            do{
                VacanciesModel vacanciesModel = new VacanciesModel();
                String pid = cursor.getString(pidIndex);
                String profession = cursor.getString(professionIndex);
                String date = cursor.getString(dateIndex);
                String header = cursor.getString(headerIndex);
                String salary = cursor.getString(salaryIndex);
                String profile = cursor.getString(profileIndex);
                String siteAddress = cursor.getString(siteAddressIndex);
                String telephone = cursor.getString(telephoneIndex);
                String body = cursor.getString(bodyIndex);
                vacanciesModel.setPid(pid);
                vacanciesModel.setProfession(profession);
                vacanciesModel.setData(date);
                vacanciesModel.setHeader(header);
                vacanciesModel.setSalary(salary);
                vacanciesModel.setProfile(profile);
                vacanciesModel.setSiteAddress(siteAddress);
                vacanciesModel.setTelephone(telephone);
                vacanciesModel.setBody(body);
                vacancyModels.add(vacanciesModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return vacancyModels;
    }

    // конец получения из Интернета

    ///////////////////////////////////////
    public void saveWatchedVacancies(String pid, boolean watched){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PID, pid);
        cv.put(WATCHED, watched);

        db.insert(TABLE_WATCHED, null, cv);
        db.close();
    }

    public boolean isWatchedVacancies(String pid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_WATCHED, null, PID + "='" + pid + "'", null, null, null, null);

        boolean isWatched = false;
        if(cursor.moveToFirst()){
            int watchedIndex = cursor.getColumnIndex(WATCHED);
            do{
                String elected = cursor.getString(watchedIndex);
                if(elected.equals("1")){
                    isWatched = true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return isWatched;
    }

    ///////////////////////////////////////////////

    // начало FeaturedActivity - избранные вакансии
    public void saveFeaturedVacancies(VacanciesModel vacanciesModel, boolean featured){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PID, vacanciesModel.getPid());
        cv.put(PROFESSION, vacanciesModel.getProfession());
        cv.put(DATE, vacanciesModel.getData());
        cv.put(HEADER, vacanciesModel.getHeader());
        cv.put(SALARY, vacanciesModel.getSalary());
        cv.put(PROFILE, vacanciesModel.getProfile());
        cv.put(SITE_ADDRESS, vacanciesModel.getSiteAddress());
        cv.put(TELEPHONE, vacanciesModel.getTelephone());
        cv.put(BODY, vacanciesModel.getBody());
        cv.put(CHECKBOX_FEATURED, featured);
        Log.d("SAVE", "featured: " + featured);
        db.insert(TABLE_FEATURED, null, cv);
        db.close();
    }

    public boolean getFeaturedVacancy(String pid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_FEATURED, null, PID + "='" + pid + "'", null, null, null, null);

        boolean isFeatured = false;
        if(cursor.moveToFirst()){
            int electedIndex = cursor.getColumnIndex(CHECKBOX_FEATURED);
            do{
                String elected = cursor.getString(electedIndex);
                if(elected.equals("1")){
                    isFeatured = true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return isFeatured;
    }

    public ArrayList<VacanciesModel> getFeaturedVacancies(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<VacanciesModel> vacancyModels = new ArrayList<>();
        Cursor cursor = db.query(TABLE_FEATURED, null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            int pidIndex = cursor.getColumnIndex(PID);
            int professionIndex = cursor.getColumnIndex(PROFESSION);
            int dateIndex = cursor.getColumnIndex(DATE);
            int headerIndex = cursor.getColumnIndex(HEADER);
            int salaryIndex = cursor.getColumnIndex(SALARY);
            int profileIndex = cursor.getColumnIndex(PROFILE);
            int siteAddressIndex = cursor.getColumnIndex(SITE_ADDRESS);
            int telephoneIndex = cursor.getColumnIndex(TELEPHONE);
            int bodyIndex = cursor.getColumnIndex(BODY);
            do{
                VacanciesModel vacancyModel = new VacanciesModel();
                String pid = cursor.getString(pidIndex);
                String profession = cursor.getString(professionIndex);
                String date = cursor.getString(dateIndex);
                String header = cursor.getString(headerIndex);
                String salary = cursor.getString(salaryIndex);
                String profile = cursor.getString(profileIndex);
                String siteAddress = cursor.getString(siteAddressIndex);
                String telephone = cursor.getString(telephoneIndex);
                String body = cursor.getString(bodyIndex);
                vacancyModel.setPid(pid);
                vacancyModel.setProfession(profession);
                vacancyModel.setData(date);
                vacancyModel.setHeader(header);
                vacancyModel.setSalary(salary);
                vacancyModel.setProfile(profile);
                vacancyModel.setSiteAddress(siteAddress);
                vacancyModel.setTelephone(telephone);
                vacancyModel.setBody(body);
                vacancyModels.add(vacancyModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return vacancyModels;
    }

    public void deleteFeaturedVacancy(String pid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FEATURED, PID + "='" + pid + "'", null);
        db.close();
    }

    ////////////////////////////////////////

    public void saveRadioButtons(SearchsModel searchsModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        db.delete(TABLE_SEARCH, null, null);

        cv.put(REGIME_SEARCH, searchsModel.getRegime());
        cv.put(SALARY_SEARCH, searchsModel.getSalary());

        db.insert(TABLE_SEARCH, null, cv);
        db.close();
    }

    public SearchsModel getRadioButtons(){
        SQLiteDatabase db = this.getWritableDatabase();
        SearchsModel searchButtonsModel = new SearchsModel();
        Cursor cursor = db.query(TABLE_SEARCH, null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            int regimeIndex = cursor.getColumnIndex(REGIME_SEARCH);
            int salaryIndex = cursor.getColumnIndex(SALARY_SEARCH);
            do{
                String regime = cursor.getString(regimeIndex);
                String salary = cursor.getString(salaryIndex);
                searchButtonsModel.setRegime(regime);
                searchButtonsModel.setSalary(salary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return searchButtonsModel;
    }
}
