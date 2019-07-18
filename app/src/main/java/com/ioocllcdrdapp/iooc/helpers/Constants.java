package com.ioocllcdrdapp.iooc.helpers;

public class Constants {

    /**
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------- API ------------------------------------------------
     */
    public static final String BASE_URL = "https://iooc.herokuapp.com/api/";
    public static final String SERVICES_STUDENT_SIGN_UP = "students/register";
    public static final String SERVICES_INSTRUCTOR_SIGN_UP = "instructors/register";
    public static final String SERVICES_USER_SIGN_IN = "users/login";
    public static final String SERVICES_USER_SIGN_IN_SOCIAL = "users/loginSocial";
    public static final String SERVICES_USER_PROFILE = "users/me";
    public static final String SERVICES_UPDATE_PROFILE = "users/editProfile";
    public static final String SERVICES_GET_NOTIFICATIONS = "notifications";
    public static final String SERVICES_GET_INSTRUCTORS = "instructors";
    public static final String SERVICES_GET_INSTRUCTORS_BY_ID = "instructors";
    public static final String SERVICES_GET_ENROLLMENT = "groups/enrollments";
    public static final String SERVICES_GET_CHAT = "groups/chat";
    public static final String SERVICES_GET_COURSE = "courses";
    public static final String SERVICES_GET_COURSE_DETAILS = "courses/home";
    public static final String SERVICES_COURSES_SEARCH = "courses/search";
    public static final String SERVICES_INSTRUCTORS_SEARCH = "instructors/search";
    public static final String SERVICES_GET_CATEGORIES = "categories";
    public static final String SERVICES_GET_LANGUAGES = "languages";
    public static final String SERVICES_GET_GROUPS = "groups";
    public static final String SERVICES_ADD_COURSE = "courses/add";
    public static final String SERVICES_GET_COURSES_INSTRUCTOR = "courses/instructor";
    public static final String SERVICES_GET_GROUPS_BY_COURSE_ID = "groups/course";
    public static final String SERVICES_GET_SESSIONS_BY_GROUP_ID = "sessions/groupId";
    public static final String SERVICES_GET_GROUPS_INSTRUCTOR = "groups/instructor";
    public static final String SERVICES_GET_MOOC_PLATFORM = "moocProviders";
    public static final String SERVICES_GET_MOOC_Courses_BY_MOOC_PLATFORM_ID = "moocCourses/moocProviders";
    public static final String SERVICES_GET_CHAT_GROUP = "chats/byGroup";
    public static final String SERVICES_CHAT_SEND_MESSAGE = "chats/sendMessage";
    public static final String SERVICES_MOOC_COURSE_REGISTRATION = "moocApplies";
    public static final String SERVICES_APPLY_GROUP = "groups/apply";
    public static final String SERVICES_ADD_GROUPS = "groups/add";
    public static final String SERVICES_ADD_SESSION = "sessions";
    public static final String SERVICE_COURSE_RATE = "courseRatings";
    public static final String SERVICE_INSTRUCTOR_RATE = "instructorRatings";
    /**
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------- KEYS -----------------------------------------------
     */
    public static final String INTENT_ID = "intentId";
    public static final String INTENT_KEY = "intentKey";
    public static final String INTENT_NAME = "intentName";
    public static final String INTENT_LOCALE = "intentLocale";
    public static final String INTENT_OBJECT = "intentObject";

    public static final String INTENT_SESSION = "intentSession";
    public static final String INTENT_GROUP = "intentGroup";

    /**
     * ---------------------------------------------------------------------------------------------
     * --------------------------------- FONTS -----------------------------------------------------
     */
    public static final String FONT_GOTHAM_BOOK = "gotham_rounded_book.otf";
    public static final String FONT_GOTHAM_LIGHT = "gotham_rounded_light.otf";
    public static final String FONT_GOTHAM_MEDIUM = "gotham_rounded_medium.otf";
    public static final String FONT_GOTHAM_BOOK_ITALIC = "gotham_rounded_book_italic.otf";

    /**
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------- TIMING ---------------------------------------------
     */
    public static final int SPLASH_TIME_OUT = 2000;

    /**
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------- Language -------------------------------------------
     */
    public static final String LOCALE_ENGLISH = "en";
    public static final String LOCALE_ENGLISH_US = "en_US";
    public static final String LOCALE_ARABIC = "ar";
}
