package tasks.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tasks.common.Utility;
import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class CommonHandle {

    // Lấy ra số tham chiếu
    public void handleSignInResponse(Response response) {
        JsonPath jsonPath = response.jsonPath();
        HashMap buyer = jsonPath.get("buyer");
        String uid = buyer.get("uid").toString();
        String email = buyer.get("email").toString();
//        Serenity.recordReportData().asEvidence().withTitle("UID").andContents(uid);
//        Serenity.recordReportData().asEvidence().withTitle("EMAIL").andContents(email);
    }

    public void getInfoFromHeaderSign(Response response) {
        String accessToken = response.header("access-token");
        String client = response.header("client");

        System.out.println("accesstoken = " + accessToken);
        System.out.println("client = " + client);

        Serenity.setSessionVariable("accessToken").to(accessToken);
        Serenity.setSessionVariable("client").to(client);
    }

    public static String setDate(String dateStr, String formatDate) {
        // Định dạng thời gian
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate);
        Calendar c1 = Calendar.getInstance();
        String day = "0";
        if (dateStr.contains("Plus")) {
            day = dateStr.substring(4);
            dateStr = dateStr.substring(0, 4);
        }
        if (dateStr.contains("Minus")) {
            day = "-" + dateStr.substring(5);
            dateStr = dateStr.substring(0, 5);
        }
        switch (dateStr) {
            case "currentDate":
                c1 = Calendar.getInstance();
                break;
            case "tomorrow":
                c1.roll(Calendar.DATE, 1);
                System.out.println("Ngày được tăng thêm 1 ngày (Sử dụng Roll) : " + dateFormat.format(c1.getTime()));
                break;
            case "currentMonth+1":
                c1.roll(Calendar.MONTH, 1);
                System.out.println("Ngày được tăng thêm 1 tháng (Sử dụng Roll) : " + dateFormat.format(c1.getTime()));
                break;
            case "Plus":
                c1.roll(Calendar.DATE, Integer.parseInt(day));
                System.out.println("Ngày được tăng thêm" + day + " ngày : " + Utility.getDateTimeFromToday(formatDate, Integer.parseInt(day)));
                break;
            case "Minus":
                c1.roll(Calendar.DATE, Integer.parseInt(day));
                System.out.println("Ngày được tăng thêm" + day + " ngày : " + Utility.getDateTimeFromToday(formatDate, Integer.parseInt(day)));
                break;
            default:
                Date date = new Date();
                try {
                    date = new SimpleDateFormat(formatDate).parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c1.setTime(date);
                break;
        }

        return dateFormat.format(c1.getTime());
    }

    public static String convertDateToWeekdays() {
        SimpleDateFormat simpleformat = new SimpleDateFormat("EEEE");
        String strDayofWeek = simpleformat.format(new Date());
        System.out.println("Day of Week = " + strDayofWeek);
        return strDayofWeek;
    }

    public static String convertFormatDate(String date, String format) {
        return Utility.convertFormatDate(format, date);
    }

    public static int compareDate(String date1, String date2, String format) {
        int i = Utility.compareDate(date1, date2, format);
        return i;
    }

    /**
     * Đọc data từ file json
     */
    public JsonNode readJsonNodeFile(String path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FileReader reader = new FileReader(path);
            return objectMapper.readTree(reader);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update data file json
     */
    public static JSONObject updateJson(JSONObject obj, String keyString, Object newValue) throws Exception {
        JSONObject json = new JSONObject();
        // get the keys of json object
        Iterator iterator = obj.keys();
        String key = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            // if the key is a string, then update the value
            if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
                if ((key.equals(keyString))) {
                    // put new value
                    obj.put(key, newValue);
                    return obj;
                }
            }
            // if it's jsonObject
            if (obj.optJSONObject(key) != null) {
                updateJson(obj.getJSONObject(key), keyString, newValue);
            }
            // if it's jsonArray
            if (obj.optJSONArray(key) != null) {
                JSONArray jArray = obj.getJSONArray(key);
                for (int i = 0; i < jArray.length(); i++) {
                    updateJson(jArray.getJSONObject(i), keyString, newValue);
                }
            }
        }
        return obj;
    }

    public static JSONObject updateJson1(JSONObject obj, String keyMain, String valueMain, String newValue) throws Exception {
        // We need to know keys of Jsonobject
        JSONObject json = new JSONObject();
        Iterator iterator = obj.keys();
        String key = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            System.out.println("key = " + key);
            // if object is just string we change value in key
            if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
                if ((key.equals(keyMain)) && (obj.get(key).toString().equals(valueMain))) {
                    // put new value
                    obj.put(key, newValue);
                    return obj;
                }
            }

            // if it's jsonobject
            if (obj.optJSONObject(key) != null) {
                updateJson1(obj.getJSONObject(key), keyMain, valueMain, newValue);
            }

            // if it's jsonarray object
            if (obj.optJSONArray(key) != null) {
                JSONArray jArray = obj.getJSONArray(key);
                for (int i = 0; i < jArray.length(); i++) {
                    if (!(jArray.get(i) instanceof Integer)) {
                        updateJson1(jArray.getJSONObject(i), keyMain, valueMain, newValue);
                    }
                }
            }
        }
        return obj;
    }

    public static List<String[]> convertDataTableToListArrayString(DataTable table) {
        List<List<String>> list = table.asLists(String.class);
        List<String[]> list2 = new ArrayList<>();
        for (List<String> e : list) {
            String[] targetArray = e.toArray(new String[0]);
            list2.add(targetArray);
        }
        return list2;
    }

    public static List<Map<String, String>> convertListArrayStringToMapString(List<String[]> original) {
        String[] keys = original.get(0);//with fist row are keys
        List<String[]> values = original.subList(1, original.size());
        List<Map<String, String>> listMap = new ArrayList<>();
        for (String[] valueArr : values) {
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < keys.length; i++) {
                map.put(keys[i], valueArr[i]);
            }
            listMap.add(map);
        }
        return listMap;
    }

    public static List<Map<String, String>> convertListArrayStringToMapString(List<String[]> original, int fromIndex, int length) {
        String[] keys = original.get(fromIndex);//with fist row are keys
        List<String[]> values = original.subList(fromIndex + 1, original.size());
        List<Map<String, String>> listMap = new ArrayList<>();
        for (String[] valueArr : values) {
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < length; i++) {
                map.put(keys[i], valueArr[i]);
            }
            listMap.add(map);
        }
        return listMap;
    }

    public static String getCurrentURL() {
        WebDriver driver = BrowseTheWeb.as(theActorInTheSpotlight()).getDriver();
        String url = driver.getCurrentUrl();
        return url;
    }

    /**
     * Comvert Map <String,String> to Map <String, Object>
     *
     * @param dt - datatable
     * @return Map <String, Object>
     */
    public static List<Map<String, Object>> convertDataTable(DataTable dt) {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);

        List<Map<String, Object>> infoObj = new ArrayList<>();
        for (Map<String, String> item : list) {
            infoObj.add(new HashMap<>(item));
        }
        return infoObj;
    }

    public static String valueToStringOrEmpty(Map<String, ?> map, String key) {
        Object value = map.get(key);
        return value == null ? "" : value.toString();
    }

    public static String getStringOnClipboard() {
        String result = "";
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            result = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
