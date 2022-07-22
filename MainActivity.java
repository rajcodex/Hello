package com.example.project;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                        Toast.makeText(MainActivity.this, "Runtime Permission Given "+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() , Toast.LENGTH_SHORT).show();
                        ArrayList<File> myModels=fetchModels(Environment.getExternalStorageDirectory().toString());
                        String[] items=new String[myModels.size()];
                        for(int i=0;i<myModels.size();i++)
                        {
                            items[i]=myModels.get(i).getName();
                        }

                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,items);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent=new Intent(MainActivity.this,Models.class);
                                intent.putExtra("com.example.project.ModelCollections",myModels);
                                intent.putExtra("com.example.project.position",i);
                                intent.putExtra("com.example.project.ModelNames",items);
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }


    public ArrayList<File> fetchModels(String path)
    {

        ArrayList<File> arrayList=new ArrayList<>();
//        File[] models=file.listFiles();
//        if(models !=null)
//        {
//            for(File myFile: models)
//            {
//                if(myFile.isDirectory() && !myFile.isHidden())
//                {
//                    arrayList.addAll(fetchModels(myFile));
//                }
//                else
//                {
//                    if(myFile.getName().endsWith(".mp3") && !myFile.getName().startsWith("."))
//                    {
//                        arrayList.add(myFile);
//                    }
//                }
//            }
//        }
        File file=new File(path);
        File[] models=file.listFiles();
        if(models!=null)
        {
            for(File myFile: models)
            {
                if(myFile.isDirectory() && !myFile.isHidden())
                arrayList.addAll(fetchModels(myFile.getPath()));
                else
                {
                    if(myFile.getName().endsWith(".obj") && !myFile.getName().startsWith("."))
                    {
                        arrayList.add(myFile);
                    }
                }
            }
        }


        return arrayList;
    }


    private boolean isExternalStroageReadable()
    {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState()))
            return true;
        return false;
    }
}