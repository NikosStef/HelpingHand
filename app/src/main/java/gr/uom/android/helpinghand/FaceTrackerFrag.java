package gr.uom.android.helpinghand;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CAMERA_SERVICE;


public class FaceTrackerFrag extends Fragment {

    private View view;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    public float probability;

    public FaceTrackerFrag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        dispatchTakePictureIntent();

        view = inflater.inflate(R.layout.facetrack_frag, container, false);

        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap BitmapImage = (Bitmap) extras.get("data");



            try {
                BitmapImage = rotateBitmap(BitmapImage, getRotationCompensation(String.valueOf(getFrontFacingCameraID()), getActivity(), getContext()), getFrontFacingCameraID());
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

            FirebaseVisionImage firebaseImage = FirebaseVisionImage.fromBitmap(BitmapImage);

            FirebaseApp.initializeApp(getContext());
            FaceTask faceTask = new FaceTask(firebaseImage, this);
            faceTask.execute();
            setUpButton();

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, Context context)
            throws CameraAccessException {

        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

        int result;
        switch (rotationCompensation) {
            case 0:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                break;
            case 90:
                result = FirebaseVisionImageMetadata.ROTATION_90;
                break;
            case 180:
                result = FirebaseVisionImageMetadata.ROTATION_180;
                break;
            case 270:
                result = FirebaseVisionImageMetadata.ROTATION_270;
                break;
            default:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                Log.e("Nikos", "Bad rotation value: " + rotationCompensation);
        }
        return result;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    private static Bitmap rotateBitmap(Bitmap bitmap, int rotation, int facing) {
        Matrix matrix = new Matrix();
        int rotationDegree = 0;
        switch (rotation) {
            case FirebaseVisionImageMetadata.ROTATION_90:
                rotationDegree = 90;
                break;
            case FirebaseVisionImageMetadata.ROTATION_180:
                rotationDegree = 180;
                break;
            case FirebaseVisionImageMetadata.ROTATION_270:
                rotationDegree = 270;
                break;
            default:
                break;
        }

        matrix.postRotate(rotationDegree);
        if (facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } else {
            matrix.postScale(-1.0f, 1.0f);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
    }

    private void setUpButton(){
        Button button = view.findViewById(R.id.suggButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putFloat("prob", probability);
                Fragment newFrag = new GetSuggFrag();
                newFrag.setArguments(bundle);
                FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.flContent, newFrag);
                fragmentManager.commit();
            }
        });
    }

    private int getFrontFacingCameraID() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return cameraInfo.facing;
            }
        }
        return 1;
    }

}
