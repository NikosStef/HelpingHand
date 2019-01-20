package gr.uom.android.helpinghand;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.io.IOException;
import java.util.List;

public class FaceTask extends AsyncTask<String, Void, Float> {

    private FirebaseVisionImage firebaseVisionImage;
    private FaceTrackerFrag frag;
    private FirebaseVisionFaceDetector detector;




    private float prob;


    public FaceTask(FirebaseVisionImage firebaseVisionImage, FaceTrackerFrag frag) {

        this.firebaseVisionImage = firebaseVisionImage;
        this.frag = frag;


        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
                .setMinFaceSize(0.4f)
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .build();

        detector = FirebaseVision.getInstance().getVisionFaceDetector(options);

    }

    @Override
    protected Float doInBackground(String... strings) {

        detector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener(
                        new OnSuccessListener<List<FirebaseVisionFace>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionFace> faces) {
                                for (FirebaseVisionFace face : faces) {

                                    if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                        prob = face.getSmilingProbability();
                                        Log.d("Nikos", "VRETHIKEEEEE" + prob);
                                        frag.probability = prob;
                                        TextView view = frag.getView().findViewById(R.id.smile);
                                        view.setText("Happiness probability is: \n" + prob);

                                        FragmentTransaction fragmentManager = frag.getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentManager.replace(R.id.flContent, frag).addToBackStack(null);
                                        fragmentManager.commit();

                                    } else
                                        Log.d("Nikos", "onFailure: " + "Prob NOT FOUND");
                                }
                                if (faces.isEmpty()) {
                                    Log.d("Nikos", "onFailure: " + "NO FACES FOUND");

                                }

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Nikos", "onFailure: " + "Firebase FAILED to detect face");
                            }
                        });
        return prob;
    }


    @Override
    protected void onPostExecute(Float aFloat) {
        try {
            detector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
