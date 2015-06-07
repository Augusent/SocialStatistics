package com.serovr.vkspy.app;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogManager {
    private int userID = 13307709; //TODO id авторизованного пользователя
    private int targetID;
    private ArrayList<String> States = new ArrayList<String>();
    private ArrayList<Date> Dates = new ArrayList<Date>();


    // Возвращает строку URL-запроса с заданными параметрами

    public void loadLog(int uID, int tID, String dateFrom, String dateTo) throws Exception{
        String apiString= "";

        URL url = new URL(apiString);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Accept", "application/json");

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (httpURLConnection.getInputStream())));

        String obj="", output;
        while ((output = br.readLine()) != null)
            obj=output;

        //System.out.println(obj);

        httpURLConnection.disconnect();

        JSONObject jObject  = new JSONObject(obj);
        JSONObject data = jObject.getJSONObject("response");
        String logString = data.getString("success");

        parseLogString(logString);
    }

    // Парсит строку лога, полученную с сервера, на коллекции дат и состояний
    public void parseLogString(String logString){
        String[] tokensLogData = logString.split(";");
        States.clear();
        Dates.clear();

        for(int i=0; i<tokensLogData.length; i++){
            States.add(tokensLogData[i].substring(0, 1));
            Log.i("ChartBuilder", "State is: " + States.get(i));
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm");
            ParsePosition position = new ParsePosition(2);
            try{
                Date date = format.parse(tokensLogData[i], position);
                Log.i("ChartBuilder", "Date is: "+date.toString());
                Dates.add(date);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Геттер коллекции состояний
    public ArrayList<String> getStates(){
        return States;
    }

    // Геттер коллекции дат
    public ArrayList<Date> getDates(){
        return Dates;
    }

    // Вывод форматированного лога активности
    public void showLog(TextView tvDateTime, TextView tvState){

        for (int i=Dates.size()-1; i>=0; i--) {
            tvDateTime.setText(tvDateTime.getText() + Dates.get(i).toLocaleString()+"\n");

            char state = States.get(i).charAt(0);
            switch (state){
                case 'o': tvState.setText(tvState.getText() + "Offline" +"\n");
                    break;
                case 'd': tvState.setText(tvState.getText() + "Desktop" +"\n");
                    break;
                case 'm': tvState.setText(tvState.getText() + "Mobile" +"\n");
                    break;
            }
        }
    }

/*
 * Статические методы класса LogManager
 */

    // Разница двух дат в минутах
    public static long minutesDifference(Date startDate, Date endDate){
        long minutesStart, minutesEnd, minutesDiff;
        minutesStart = (startDate.getTime()/1000)/60;
        minutesEnd = (endDate.getTime()/1000)/60;
        minutesDiff = Math.abs(minutesEnd - minutesStart);

        return minutesDiff;
    }

    // Разница двух дат в Днях, Часах, Минутах
    public static Map<String, String> datesDifference(Date startDate, Date endDate){
        long diff;
        int daysDifference, hoursDifference, minutesDifference;
        Map mapDHM = new HashMap<String, String>();
        mapDHM.clear();

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);

        diff = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        daysDifference = (int) diff/(24*3600*1000);
        diff-=(daysDifference*24*3600*1000);
        hoursDifference = (int) diff/(3600*1000);
        diff-=(hoursDifference * 3600*1000);
        minutesDifference =(int) diff/(60*1000);

        mapDHM.put("Days", minutesDifference);
        mapDHM.put("Hours", hoursDifference);
        mapDHM.put("Minutes", minutesDifference);

        Log.i("ChartBuilder", "Minutes "+minutesDifference);
        Log.i("ChartBuilder", "Hours "+hoursDifference);
        Log.i("ChartBuilder", "Days "+daysDifference);

        return mapDHM;
    }

    public static String getCurrentDateString(){
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy");
        String currentDate = df.format(c.getTime());

        return currentDate;
    }

    public static Date getCurrentDate(){
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();

        return date;
    }


}
