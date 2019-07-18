package com.ioocllcdrdapp.iooc.backend.api;

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
import com.ioocllcdrdapp.iooc.helpers.Constants;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST(ApiClient.BASE_URL + Constants.SERVICES_STUDENT_SIGN_UP)
    Call<ResponseBody> doStudentSignUp(@HeaderMap Map<String, String> headers,
                                       @Body SignForm signForm);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_INSTRUCTOR_SIGN_UP)
    Call<ResponseBody> doInstructorSignUp(@HeaderMap Map<String, String> headers,
                                          @Body SignForm signForm);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_USER_SIGN_IN)
    Call<Token> doUserSignIn(@HeaderMap Map<String, String> headers,
                             @Body SignForm signForm);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_USER_SIGN_IN_SOCIAL)
    Call<Token> doUserSignInSocial(@HeaderMap Map<String, String> headers,
                                   @Body SignForm signForm);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_COURSES_SEARCH)
    Call<List<Course>> doSearchCourses(@HeaderMap Map<String, String> headers,
                                       @Body SearchFrom searchFrom);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_INSTRUCTORS_SEARCH)
    Call<List<Instructor>> doSearchInstructors(@HeaderMap Map<String, String> headers,
                                               @Body SearchFrom searchFrom);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_CATEGORIES)
    Call<List<Category>> doGetCategory(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_LANGUAGES)
    Call<List<Language>> doGetLanguage(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_CHAT)
    Call<List<Group>> doGetChat(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_CHAT_GROUP + "/{id}")
    Call<List<Chat>> doGetChatByGroupId(@HeaderMap Map<String, String> headers,
                                        @Path("id") String id);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_CHAT_SEND_MESSAGE)
    Call<ResponseBody> doSendChat(@HeaderMap Map<String, String> headers,
                                  @Body Chat chat);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_COURSE)
    Call<List<Course>> doGetCourse(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_COURSE_DETAILS)
    Call<CourseDetails> doGetCourseDetails(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_NOTIFICATIONS)
    Call<List<Notification>> doGetNotifications(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_ENROLLMENT)
    Call<List<Group>> doGetEnrollment(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_USER_PROFILE)
    Call<User> doUserProfile(@HeaderMap Map<String, String> headers);


    @POST(ApiClient.BASE_URL + Constants.SERVICES_UPDATE_PROFILE)
    Call<ResponseBody> doUpdateProfile(@HeaderMap Map<String, String> headers,
                                       @Body EditProfileForm editProfileForm);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_INSTRUCTORS)
    Call<List<Instructor>> doGetInstructor(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_INSTRUCTORS_BY_ID + "/{id}")
    Call<Instructor> doGetInstructorById(@HeaderMap Map<String, String> headers,
                                         @Path("id") String id);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_GROUPS)
    Call<List<Group>> doGetGroup(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_COURSES_INSTRUCTOR + "/{id}")
    Call<List<Course>> doGetCoursesByInstructorId(@HeaderMap Map<String, String> headers,
                                                  @Path("id") String id);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_GROUPS_BY_COURSE_ID + "/{id}")
    Call<List<Group>> doGetGroupsByCourseId(@HeaderMap Map<String, String> headers,
                                            @Path("id") String id);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_GROUPS_INSTRUCTOR + "/{id}")
    Call<List<Group>> doGetGroupByInstructorId(@HeaderMap Map<String, String> headers,
                                               @Path("id") String id);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_SESSIONS_BY_GROUP_ID + "/{id}")
    Call<List<Session>> doGetSessionByGroupId(@HeaderMap Map<String, String> headers,
                                              @Path("id") String id);
    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_MOOC_PLATFORM)
    Call<List<MoocPlatform>> doGetMoocPlatforms(@HeaderMap Map<String, String> headers);

    @GET(ApiClient.BASE_URL + Constants.SERVICES_GET_MOOC_Courses_BY_MOOC_PLATFORM_ID + "/{id}")
    Call<List<MoocCourse>> doGetMoocCourses(@HeaderMap Map<String, String> headers,
                                            @Path("id") String id);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_ADD_COURSE)
    Call<ResponseBody> doInstractorAddCourse(@HeaderMap Map<String, String> headers,
                                             @Body Course course);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_ADD_GROUPS)
    Call<ResponseBody> doInstractorAddGroup(@HeaderMap Map<String, String> headers,
                                            @Body Group group);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_MOOC_COURSE_REGISTRATION)
    Call<ResponseBody> doMoocCourseRegistration(@HeaderMap Map<String, String> headers,
                                                @Body NtlForm ntlForm);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_APPLY_GROUP)
    Call<ResponseBody> doGetGroupID(@HeaderMap Map<String, String> headers,
                                    @Body GroupID groupID);

    @POST(ApiClient.BASE_URL + Constants.SERVICES_ADD_SESSION)
    Call<ResponseBody> doAddSession(@HeaderMap Map<String, String> headers,
                                    @Body Session session);

    @POST(ApiClient.BASE_URL + Constants.SERVICE_COURSE_RATE)
    Call<ResponseBody> doCourseRate(@HeaderMap Map<String, String> headers,
                                    @Body CourseRate courseRate);

    @POST(ApiClient.BASE_URL + Constants.SERVICE_INSTRUCTOR_RATE)
    Call<ResponseBody> doInstructorRate(@HeaderMap Map<String, String> headers,
                                    @Body InstructorRate instructorRate);
}
