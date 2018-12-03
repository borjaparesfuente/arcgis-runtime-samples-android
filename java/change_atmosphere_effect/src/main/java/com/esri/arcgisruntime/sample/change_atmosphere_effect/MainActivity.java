/* Copyright 2018 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.esri.arcgisruntime.sample.change_atmosphere_effect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Surface;
import com.esri.arcgisruntime.mapping.view.AtmosphereEffect;
import com.esri.arcgisruntime.mapping.view.SceneView;
import com.esri.arcgisruntime.mapping.view.Camera;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SceneView mSceneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a scene and add a basemap to it
        ArcGISScene scene = new ArcGISScene();
        scene.setBasemap(Basemap.createImagery());

        // create SceneView from layout
        mSceneView = (SceneView) findViewById(R.id.sceneView);
        mSceneView.setScene(scene);

        // add base surface for elevation data
        Surface surface = new Surface();
        ArcGISTiledElevationSource elevationSource = new ArcGISTiledElevationSource(
                getResources().getString(R.string.elevation_image_service));
       // scene.getBaseSurface().getElevationSources().add(elevationSource);
        surface.getElevationSources().add(elevationSource);
        scene.setBaseSurface(surface);

        // add a camera and initial camera position
        Camera camera = new Camera(64.416919, -14.483728, 100, 318, 105, 0);
        mSceneView.setViewpointCamera(camera);

    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.atmosphere_sources, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // setting different atmosphere effect on scene view
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        try {


            int i = item.getItemId();
            if (i == R.id.noAtmosphereEffect) {
                mSceneView.setAtmosphereEffect(AtmosphereEffect.NONE);
            } else if (i == R.id.realisticAtmosphereEffect) {
                mSceneView.setAtmosphereEffect(AtmosphereEffect.REALISTIC);
            } else if (i == R.id.horizonAtmosphereEffect) {
                mSceneView.setAtmosphereEffect(AtmosphereEffect.HORIZON_ONLY);
            } else {
                Log.e(TAG, "Menu option not implemented");
            }

        }
        catch (Exception e)
        {
            // on any error, display the stack trace.
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        // pause SceneView
        mSceneView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // resume SceneView
        mSceneView.resume();
        super.onResume();
    }

    @Override protected void onDestroy() {
        // dispose SceneView
        mSceneView.dispose();
        super.onDestroy();
    }
}
