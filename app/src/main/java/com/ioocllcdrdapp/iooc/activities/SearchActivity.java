package com.ioocllcdrdapp.iooc.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Category;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;
import com.ioocllcdrdapp.iooc.backend.models.Language;
import com.ioocllcdrdapp.iooc.backend.models.SearchFrom;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.CategoryOperation;
import com.ioocllcdrdapp.iooc.backend.operations.LanguageOperation;
import com.ioocllcdrdapp.iooc.backend.operations.SearchCoursesOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.CourseAdapter;
import com.ioocllcdrdapp.iooc.controllers.adapters.InstructorAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.utilities.Utilities;
import com.ioocllcdrdapp.iooc.views.AppSpinnerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements RequestObserver,
        AdapterView.OnItemSelectedListener {

    @BindView(R.id.search_type_spinner)
    AppCompatSpinner typeSpinner;
    @BindView(R.id.search_category_spinner)
    AppCompatSpinner categorySpinner;
    @BindView(R.id.search_language_spinner)
    AppCompatSpinner languageSpinner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private final static int REQUEST_SEARCH_COURSES = 1;
    private final static int REQUEST_SEARCH_INSTRUCTORS = 2;
    private final static int REQUEST_GET_CATEGORIES = 3;
    private final static int REQUEST_GET_LANGUAGES = 4;

    private final static String TYPE_COURSES = "Courses";
    private final static String TYPE_INSTRUCTORS = "Instructors";

    private SearchFrom searchFrom;
    private String type;

    List<Course> courses;
    List<Instructor> instructors;
    private List<Category> categories;
    private List<Language> languages;

    public SearchActivity() {
        super(R.layout.activity_search, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        toolbarTextView.setVisibility(View.GONE);
        toolbarSearchEditText.setVisibility(View.VISIBLE);
        searchFrom = new SearchFrom();

        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());

        initRV();
        fillTypeSpinner();
        submitSearch(TYPE_COURSES, searchFrom);
        getCategories();
        getLanguages();

        toolbarSearchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard();
                addSearchKeyword();
                runSwipeRefreshLayout();
                return true;
            }
            return false;
        });
    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            Utilities.hideSoftKeyboard(this, Objects.requireNonNull(getCurrentFocus()));
        }
    }

    private void initRV() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @SuppressWarnings("unchecked")
    private void fillTypeSpinner() {
        List<String> types = new ArrayList<>();
        types.add(TYPE_COURSES);
        types.add(TYPE_INSTRUCTORS);
        AppSpinnerAdapter spinnerAdapter = new AppSpinnerAdapter(this, R.layout.spinner_item, types);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(spinnerAdapter);
        typeSpinner.setOnItemSelectedListener(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleRequestFinished(Object requestId, Throwable error, Object resultObject) {
        if (error != null) {
            if (error instanceof CTHttpError) {
                int code = ((CTHttpError) error).getStatusCode();
                String errorMsg = ((CTHttpError) error).getErrorMsg();
                if (code == -1 || Utilities.isNullString(errorMsg)) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), getString(R.string.request_server_error), this);
                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), errorMsg, this);
                }
            }
        } else if (resultObject != null) {
            AppSpinnerAdapter spinnerAdapter;
            switch ((int) requestId) {
                case REQUEST_SEARCH_COURSES:
                    courses = (List<Course>) resultObject;
                    CourseAdapter courseAdapter = new CourseAdapter(courses, this, CourseAdapter.LAND_SCAPE);
                    recyclerView.setAdapter(courseAdapter);
                    break;
                case REQUEST_SEARCH_INSTRUCTORS:
                    instructors = (List<Instructor>) resultObject;
                    InstructorAdapter instructorAdapter = new InstructorAdapter(instructors, this);
                    recyclerView.setAdapter(instructorAdapter);
                    break;
                case REQUEST_GET_CATEGORIES:
                    categories = new ArrayList<>();
                    categories.add(new Category("-1", getString(R.string.category)));
                    categories.addAll((Collection<? extends Category>) resultObject);
                    spinnerAdapter = new AppSpinnerAdapter(this, R.layout.spinner_item, categories);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(spinnerAdapter);
                    categorySpinner.setOnItemSelectedListener(this);
                    break;
                case REQUEST_GET_LANGUAGES:
                    languages = new ArrayList<>();
                    languages.add(new Language("-1", getString(R.string.language)));
                    languages.addAll((Collection<? extends Language>) resultObject);
                    spinnerAdapter = new AppSpinnerAdapter(this, R.layout.spinner_item, languages);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    languageSpinner.setAdapter(spinnerAdapter);
                    languageSpinner.setOnItemSelectedListener(this);
                    break;
            }
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.search_type_spinner:
                if (position == 0) {
                    type = TYPE_COURSES;
                    languageSpinner.setVisibility(View.VISIBLE);
                } else {
                    type = TYPE_INSTRUCTORS;
                    languageSpinner.setVisibility(View.GONE);
                }
                break;
            case R.id.search_category_spinner:
                if (position == 0) searchFrom.setCategoryId(null);
                else searchFrom.setCategoryId(categories.get(position).getId());
                break;
            case R.id.search_language_spinner:
                if (position == 0) searchFrom.setLanguageId(null);
                else searchFrom.setLanguageId(languages.get(position).getId());
                break;
        }
        addSearchKeyword();
        runSwipeRefreshLayout();
    }

    private void runSwipeRefreshLayout() {
        recyclerView.setAdapter(null);
        submitSearch(type, searchFrom);
    }

    private void addSearchKeyword() {
        String keyword = toolbarSearchEditText.getText().toString().trim();
        if (keyword.isEmpty()) {
            searchFrom.setKeyword(null);
            return;
        }
        searchFrom.setKeyword(keyword);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void submitSearch(String type, SearchFrom searchFrom) {
        switch (type) {
            case TYPE_COURSES:
                submitSearchCourse(searchFrom);
                break;
            case TYPE_INSTRUCTORS:
                submitSearchInstructors(searchFrom);
                break;
        }
    }

    private void getCategories() {
        CategoryOperation operation = new CategoryOperation(REQUEST_GET_CATEGORIES, false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void getLanguages() {
        LanguageOperation operation = new LanguageOperation(REQUEST_GET_LANGUAGES, false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void submitSearchCourse(SearchFrom searchFrom) {
        SearchCoursesOperation operation = new SearchCoursesOperation(searchFrom, REQUEST_SEARCH_COURSES, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void submitSearchInstructors(SearchFrom searchFrom) {
        SearchCoursesOperation operation = new SearchCoursesOperation(searchFrom, REQUEST_SEARCH_COURSES, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }
}
