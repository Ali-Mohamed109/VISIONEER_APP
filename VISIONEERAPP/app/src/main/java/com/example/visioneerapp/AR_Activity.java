//
//
//package com.example.visioneerapp;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
//import com.google.ar.core.Anchor;
//import com.google.ar.core.Frame;
//import com.google.ar.core.HitResult;
//import com.google.ar.core.Plane;
//import com.google.ar.core.Trackable;
//import com.google.ar.sceneform.AnchorNode;
//import com.google.ar.sceneform.ArSceneView;
//import com.google.ar.sceneform.rendering.ModelRenderable;
//
//import java.util.List;
//
//public class AR_Activity extends AppCompatActivity {
//    private static final String TAG = AR_Activity.class.getSimpleName();
//
//    private ExtendedFloatingActionButton placeButton;
//    private ArSceneView arSceneView;
//    private ModelRenderable modelRenderable;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ar);
//
//        placeButton = findViewById(R.id.place);
//        arSceneView = findViewById(R.id.ar_sceneview);
//
//        ModelRenderable.builder()
//                .setSource(this, Uri.parse("file:///android_asset/models/gendarmerie__cs2_agent_model_mask.glb"))
//                .build()
//                .thenAccept(renderable -> modelRenderable = renderable)
//                .exceptionally(throwable -> {
//                    Log.e(TAG, "Error loading 3D model", throwable);
//                    return null;
//                });
//
//        placeButton.setOnClickListener(view -> addModelToScene());
//
//        // Set a touch listener on the AR scene
//        arSceneView.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                // Perform a hit test on the AR scene when the screen is tapped
//                Frame frame = arSceneView.getArFrame();
//                if (frame != null) {
//                    List<HitResult> hitResults = frame.hitTest(event.getX(), event.getY());
//                    for (HitResult hitResult : hitResults) {
//                        // Check if the hit is on a plane
//                        Trackable trackable = hitResult.getTrackable();
//                        if (trackable instanceof Plane && ((Plane) trackable).isPoseInPolygon(hitResult.getHitPose())) {
//                            // Create an anchor at the hit position
//                            Anchor anchor = hitResult.createAnchor();
//                            // Handle the new anchor (e.g., add a 3D model at the anchor's position)
//                            handleNewAnchor(anchor);
//                            break;
//                        }
//                    }
//                }
//            }
//            return true;
//        });
//    }
//
//    private void addModelToScene() {
//        if (modelRenderable == null) {
//            Log.e(TAG, "Model not loaded yet");
//            return;
//        }
//
//        // Create a new AnchorNode with the anchor
//        AnchorNode anchorNode = new AnchorNode();
//        // Set the renderable as the AnchorNode's visual
//        anchorNode.setRenderable(modelRenderable);
//        // Add the AnchorNode to the AR scene
//        arSceneView.getScene().addChild(anchorNode);
//    }
//
//    private void handleNewAnchor(Anchor anchor) {
//        if (modelRenderable == null) {
//            Log.e(TAG, "Model not loaded yet");
//            return;
//        }
//
//        // Create a new AnchorNode with the anchor
//        AnchorNode anchorNode = new AnchorNode(anchor);
//        // Set the renderable as the AnchorNode's visual
//        anchorNode.setRenderable(modelRenderable);
//        // Add the AnchorNode to the AR scene
//        arSceneView.getScene().addChild(anchorNode);
//    }
//}