package tn.khammami.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SchoolListJsonUtils {

    public static List<School> getSchoolListFromJson(String schoolsJsonStr)
            throws JSONException {

        final String SL_DATA = "data";
        final String SL_NAME = "name";
        final String SL_DESCRIPTION = "description";
        final String SL_LOGO = "logo";

        List<School> parsedSchoolData = new ArrayList<>();

        JSONObject forecastJson = new JSONObject(schoolsJsonStr);

        JSONArray schoolsArray = forecastJson.getJSONArray(SL_DATA);

        for (int i = 0; i < schoolsArray.length(); i++) {
            String name;
            String logo;
            String description;

            /* Get the JSON object representing the school */
            JSONObject schoolObject = schoolsArray.getJSONObject(i);

            name = schoolObject.getString(SL_NAME);
            description = schoolObject.getString(SL_DESCRIPTION);
            logo = schoolObject.getString(SL_LOGO);

            parsedSchoolData.add(new School(name,description,logo));
        }

        return parsedSchoolData;
    }
}
