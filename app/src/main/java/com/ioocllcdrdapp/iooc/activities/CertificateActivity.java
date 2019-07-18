package com.ioocllcdrdapp.iooc.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CertificateActivity extends BaseActivity {


    private static final int STORAGE_CODE = 1000;
    @BindView(R.id.certificate_verified_text)
    TextView certificateVerifiedText;
    @BindView(R.id.certificate_iooc_text)
    TextView certificateIoocText;
    @BindView(R.id.certificate_info_text)
    TextView certificateInfoText;
    @BindView(R.id.certificate_student_name_text)
    TextView certificateStudentNameText;
    @BindView(R.id.certificate_course_name_text)
    TextView certificateCourseNameText;
    //    @BindView(R.id.certificate_date_text)
//    TextView certificateDateText;
    @BindView(R.id.certificate_save_pdf_btn)
    Button certificateSavePdfBtn;

    public User user;
    public Course course ;
    String studentName ;
    String courseName ;
    public CertificateActivity() {
        super(R.layout.activity_certificates, true);
    }

    @OnClick(R.id.certificate_save_pdf_btn)
    public void onViewClicked() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, STORAGE_CODE);
            } else {
                savePdf();
            }
        } else {
            savePdf();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savePdf();
                } else {
                    //No Permission
                    Toast.makeText(this, "permession Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void savePdf() {
        Document mDoc = new Document();
        //name of pdf
        String pdfName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        //pdf path
        String pdfPath = Environment.getExternalStorageDirectory() + "/" + pdfName + ".pdf";
        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(pdfPath));
            mDoc.open();

            String verifiedText = certificateVerifiedText.getText().toString();
            String ioocText = certificateIoocText.getText().toString();
            String infoText = certificateInfoText.getText().toString();
            String studentNameText = studentName;
            String courseNameText = courseName;


            mDoc.add(new Paragraph(verifiedText));
            mDoc.add(new Paragraph(ioocText));
            mDoc.add(new Paragraph(infoText));
            mDoc.add(new Paragraph(studentNameText));

            mDoc.close();

            Toast.makeText(this, pdfName + ".pdf\nis saved to\n" + pdfPath, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());
        ButterKnife.bind(this);
        fillData(UserManager.getInstance().getCurrentUser());
        course = (Course) getIntent().getSerializableExtra(Constants.INTENT_OBJECT);
        certificateCourseNameText.setText(course.getName());
    }

    private void fillData(User user) {
        String studentName = user.getFirstName() + " " + user.getLastName();
        certificateStudentNameText.setText(studentName);
    }
}