package com.ioocllcdrdapp.iooc.backend.api;

import android.util.Log;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Category;
import com.ioocllcdrdapp.iooc.backend.models.Chat;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.CourseDetails;
import com.ioocllcdrdapp.iooc.backend.models.CourseRate;
import com.ioocllcdrdapp.iooc.backend.models.EditProfileForm;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.models.GroupID;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;
import com.ioocllcdrdapp.iooc.backend.models.InstructorRate;
import com.ioocllcdrdapp.iooc.backend.models.Language;
import com.ioocllcdrdapp.iooc.backend.models.MoocCourse;
import com.ioocllcdrdapp.iooc.backend.models.MoocPlatform;
import com.ioocllcdrdapp.iooc.backend.models.Notification;
import com.ioocllcdrdapp.iooc.backend.models.NtlForm;
import com.ioocllcdrdapp.iooc.backend.models.SearchFrom;
import com.ioocllcdrdapp.iooc.backend.models.Session;
import com.ioocllcdrdapp.iooc.backend.models.SignForm;
import com.ioocllcdrdapp.iooc.backend.models.Token;
import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.controllers.adapters.MoocCourseAdapter;
import com.ioocllcdrdapp.iooc.utilities.BaseApplication;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsManager {

    private static final String TAG = "OperationsManager";
    private static OperationsManager _instance = null;

    public static OperationsManager getInstance() {
        if (_instance == null)
            _instance = new OperationsManager();
        return _instance;
    }

    public ResponseBody doStudentSignUp(SignForm signForm) throws IOException {
        Log.v(TAG, "doStudentSignUp");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doStudentSignUp(headers, signForm);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public ResponseBody doInstructorSignUp(SignForm signForm) throws IOException {
        Log.v(TAG, "doInstructorSignUp");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doInstructorSignUp(headers, signForm);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public Token doUserSignIn(SignForm signForm) throws IOException {
        Log.v(TAG, "doUserSignIn");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Token> call = apiService.doUserSignIn(headers, signForm);
        Response<Token> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public Token doUserSignInSocial(SignForm signForm) throws IOException {
        Log.v(TAG, "doUserSignInSocial");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Token> call = apiService.doUserSignInSocial(headers, signForm);
        Response<Token> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Group> doGetChat() throws IOException {
        Log.v(TAG, "doGetChat");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Group>> call = apiService.doGetChat(headers);
        Response<List<Group>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Course> doGetCourse() throws IOException {
        Log.v(TAG, "doGetCourse");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Course>> call = apiService.doGetCourse(headers);
        Response<List<Course>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Group> doGetGroup() throws IOException {
        Log.v(TAG, "doGetGroup");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Group>> call = apiService.doGetGroup(headers);
        Response<List<Group>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }


    public CourseDetails doGetCourseDetails() throws IOException {
        Log.v(TAG, "doGetCourseDetails");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CourseDetails> call = apiService.doGetCourseDetails(headers);
        Response<CourseDetails> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }


    public List<Notification> doGetNotifications() throws IOException {
        Log.v(TAG, "doGetNotifications");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Notification>> call = apiService.doGetNotifications(headers);
        Response<List<Notification>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Group> doGetEnrollment() throws IOException {
        Log.v(TAG, "doGetEnrollment");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Group>> call = apiService.doGetEnrollment(headers);
        Response<List<Group>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public User doUserProfile() throws IOException {
        Log.v(TAG, "doUserProfile");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.doUserProfile(headers);
        Response<User> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }


    public ResponseBody doUpdateProfile(EditProfileForm editProfileForm) throws IOException {
        Log.v(TAG, "doUpdateProfile");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doUpdateProfile(headers, editProfileForm);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }
    public List<Instructor> doGetInstructor() throws IOException {
        Log.v(TAG, "doGetEnrollment");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Instructor>> call = apiService.doGetInstructor(headers);
        Response<List<Instructor>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public Instructor doGetInstructorById(String id) throws IOException {
        Log.v(TAG, "doGetInstructorById");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Instructor> call = apiService.doGetInstructorById(headers, id);
        Response<Instructor> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Group> doGetGroupsByInstructorId(String id) throws IOException {
        Log.v(TAG, "doGetGroupsByInstructorId");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Group>> call = apiService.doGetGroupByInstructorId(headers, id);
        Response<List<Group>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }


    public List<Session> doGetSessionByGroupId(String id) throws IOException {
        Log.v(TAG, "doGetSessionByGroupId");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Session>> call = apiService.doGetSessionByGroupId(headers, id);
        Response<List<Session>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Group> doGetGroupsByCourseId(String id) throws IOException {
        Log.v(TAG, "doGetGroupsByCourseId");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Group>> call = apiService.doGetGroupsByCourseId(headers, id);
        Response<List<Group>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Chat> doGetChatByGroupId(String id) throws IOException {
        Log.v(TAG, "doGetChatByGroupId");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Chat>> call = apiService.doGetChatByGroupId(headers, id);
        Response<List<Chat>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }


    public ResponseBody doSendChat(Chat chat) throws IOException {
        Log.v(TAG, "doSendChat");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doSendChat(headers, chat);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Course> doSearchCourses(SearchFrom searchFrom) throws IOException {
        Log.v(TAG, "doSearchCourses");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Course>> call = apiService.doSearchCourses(headers, searchFrom);
        Response<List<Course>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Instructor> doSearchInstructors(SearchFrom searchFrom) throws IOException {
        Log.v(TAG, "doSearchInstructors");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Instructor>> call = apiService.doSearchInstructors(headers, searchFrom);
        Response<List<Instructor>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Category> doGetCategory() throws IOException {
        Log.v(TAG, "doGetCategory");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Category>> call = apiService.doGetCategory(headers);
        Response<List<Category>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Language> doGetLanguage() throws IOException {
        Log.v(TAG, "doGetLanguage");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Language>> call = apiService.doGetLanguage(headers);
        Response<List<Language>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<Course> doGetCoursesByInstructorId(String id) throws IOException {
        Log.v(TAG, "doGetCoursesByInstructorId");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Course>> call = apiService.doGetCoursesByInstructorId(headers, id);
        Response<List<Course>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public List<MoocPlatform> doGetMoocPlatforms() throws IOException {
        Log.v(TAG, "doGetMoocPlatforms");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MoocPlatform>> call = apiService.doGetMoocPlatforms(headers);
        Response<List<MoocPlatform>> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public ResponseBody doInstractorAddCourse(Course course) throws IOException {
        Log.v(TAG, "doInstractorAddCourse");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doInstractorAddCourse(headers, course);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public ResponseBody doInstractorAddGroup(Group group) throws IOException {
        Log.v(TAG, "doInstractorAddGroup");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doInstractorAddGroup(headers, group);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public ResponseBody doMoocCourseRegistration(NtlForm ntlForm) throws IOException {
        Log.v(TAG, "doMoocCourseRegistration");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doMoocCourseRegistration(headers, ntlForm);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public ResponseBody doGetGroupID(GroupID groupID) throws IOException {
        Log.v(TAG, "doGetGroupID");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doGetGroupID(headers, groupID);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public ResponseBody doAddSession(Session session) throws IOException {
        Log.v(TAG, "doAddSession");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doAddSession(headers, session);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public ResponseBody doCourseRate(CourseRate courseRate) throws IOException {
        Log.v(TAG, "doCourseRate");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doCourseRate(headers, courseRate);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    public ResponseBody doInstructorRate(InstructorRate instructorRate) throws IOException {
        Log.v(TAG, "doInstructorRate");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.doInstructorRate(headers, instructorRate);
        Response<ResponseBody> response = call.execute();

        ensureHttpSuccess(response);

        return response.body();
    }

    /**
     * Ensure http request has success
     *
     * @param response of the api
     * @throws IOException if an error found, then throw an exception with the error, and the above layer (Operation) will catch it.
     */
    private void ensureHttpSuccess(Response response) throws IOException {
        if (!response.isSuccessful() && response.errorBody() != null) {
            ResponseBody errorBody = response.errorBody();
            // assert errorBody != null;
            String errorMSG = errorBody.string();
            int code = response.code();
            if (code == 504 && Utilities.isNullString(errorMSG)) // Request timeout
                errorMSG = BaseApplication.getContext().getString(R.string.request_error);
            else if (!Utilities.isNullString(errorMSG) && errorMSG.trim().startsWith("{")
                    && errorMSG.trim().endsWith("}")) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(errorMSG);
                    errorMSG = jsonObject.getString("message");
                    if (jsonObject.has("code"))
                        code = jsonObject.optInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            throw new CTHttpError(errorMSG, code);
        }
    }
}
