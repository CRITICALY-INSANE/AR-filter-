package com.example.parkourarcore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.AugmentedFace;
import com.google.ar.core.Config;
import com.google.ar.core.Config.AugmentedFaceMode;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.AugmentedFaceNode;


import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ModelRenderable mdelrend;
    private Texture txture;
    private Boolean isad=false;
    private Button item,ex,stc_filter;
    private static int frd=0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;
    CustomAR arFragment;
    Scene scene;
    ArSceneView sceneView;
    AugmentedFaceNode augfacemode;
    
    private final HashMap<AugmentedFace, AugmentedFaceNode> faceNodeMap = new HashMap<>();
    View arrayView[];
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment=(CustomAR)getSupportFragmentManager().findFragmentById(R.id.arfragment);
        sceneView = arFragment.getArSceneView();
        item=findViewById(R.id.select);
        ex=findViewById(R.id.select2);
        stc_filter=findViewById(R.id.select3);
        sceneView.setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);
        scene = sceneView.getScene();
    
        stc_filter.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              dispatchTakePictureIntent();
                                              
                                          }
                                      }
        );
        
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show2();
            }
        });
        
    }
    
    private void show2() {
        faceNodeMap.clear();
        ModelRenderable.builder()
                .setSource(this,R.raw.final4)
                // .setSource(this,R.raw.Footba)
                .build()
                .thenAccept(renderable ->{
                    mdelrend=renderable;
                    mdelrend.setShadowCaster(false);
                    mdelrend.setShadowReceiver(false);});
        
      /*  Texture.builder()
                //.setSource(this,R.drawable.iron).build()
                
               // .setSource(this,R.drawable.fox_face_mesh_texture).build()
                .setSource(this,R.drawable.glas).build()
                .thenAccept(txture ->this.txture=txture);*/
    
        //arFragment.getArSceneView().setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);
        scene.addOnUpdateListener(frameTime -> {
            
            //if (mdelrend == null || txture == null) {
            if (mdelrend == null ) {
                return;
            }
            Frame frame = arFragment.getArSceneView().getArFrame();
            Collection<AugmentedFace> aaugemface = frame.getUpdatedTrackables(AugmentedFace.class);
            for (AugmentedFace augface : aaugemface) {
                if (isad) {
                    return;
                }
    
                augfacemode = new AugmentedFaceNode(augface);
                augfacemode.setParent(scene);
                augfacemode.setFaceRegionsRenderable(mdelrend);
                // augfacemode.setFaceMeshTexture(txture);
                isad = true;
            }
        });
    }
    
    protected void show(){
        faceNodeMap.clear();
        ModelRenderable.builder()
                .setSource(this,R.raw.final1)
                // .setSource(this,R.raw.gas)
                .build()
                .thenAccept(renderable ->{
                    mdelrend=renderable;
                    mdelrend.setShadowCaster(false);
                    mdelrend.setShadowReceiver(false);});
        
       /* Texture.builder()
                //.setSource(this,R.drawable.iron).build()
                
               // .setSource(this,R.drawable.fox_face_mesh_texture).build()
                .setSource(this,R.drawable.skul).build()
                .thenAccept(txture ->this.txture=txture);*/
    
        //arFragment.getArSceneView().setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);
        scene.addOnUpdateListener(frameTime -> {
            
            //if (mdelrend == null || txture == null) {
            if (mdelrend == null ) {
                return;
            }
            Frame frame = arFragment.getArSceneView().getArFrame();
            Collection<AugmentedFace> aaugemface = frame.getUpdatedTrackables(AugmentedFace.class);
            for (AugmentedFace augface : aaugemface) {
                if (isad) {
                    return;
                }
                
                augfacemode = new AugmentedFaceNode(augface);
                augfacemode.setParent(scene);
                augfacemode.setFaceRegionsRenderable(mdelrend);
                // augfacemode.setFaceMeshTexture(txture);
                isad = true;
            }
            
            
        });
        
    }
    
    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA}, 100);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
        Bitmap capture=(Bitmap)data.getExtras().get("data");
       // imageview.setImageBitmap(capture);
            Intent nxt=new Intent();
            nxt.setClass(MainActivity.this,fitter_new.class);
            nxt.putExtra("bitmap",capture);
            startActivity(nxt);
        }
    }
    
    
}
