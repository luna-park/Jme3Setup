package org.lunapark.dev.jme3setup;

import org.lunapark.dev.jme3setup.layout.EventListener;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * File creator
 * Created by znak on 07.04.2017.
 */
public class Creator {
//    ProjectName /app/src/main/java/ com/package / sName

    private final String appMaskSrc = "/app/src/main/java/";
    private final String appMaskAssets = "/app/src/main/assets/";
    private final String appMaskRes = "/app/src/main/res/values/";
    private final String appMaskManifest = "/app/src/main/";
    private final String appMaskGradle = "/app/";
    private final String coreMask = "/core/src/";
    private final String desktopMask = "/desktop/src/";

    private final String[] assetsDirs = {
            "Interface",
            "MatDefs",
            "Materials",
            "Models",
            "Scenes",
            "Shaders",
            "Sounds",
            "Textures"
    };


    private final String fileDesktopLauncher = "package %s;\n\npublic class DesktopLauncher {\n\n    public static void main(String[] args) {\n        Game game = new Game();\n        game.start();\n    }\n}\n";
    private final String fileGame = "package %s;\n\nimport com.jme3.app.SimpleApplication;\n\npublic class Game extends SimpleApplication {\n\n    public static void main(String[] args) {\n        Game app = new Game();\n        app.start();\n    }\n\n    @Override\n    public void simpleInitApp() {\n\n    }\n}\n";
    private final String fileActivity = "package %s;\n\nimport com.jme3.app.AndroidHarness;\n\npublic class MainActivity extends AndroidHarness {\n\n    public MainActivity() {\n        // Set main project class (fully qualified path)\n        appClass = Game.class.getCanonicalName();\n        // Options\n    }\n}\n";
    private final String fileManifest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n    package=\"%s\">\n\n    <application\n        android:allowBackup=\"true\"\n        \n        android:label=\"@string/app_name\"\n        android:launchMode=\"singleTask\"\n        android:supportsRtl=\"true\"\n        android:theme=\"@style/AppTheme\">\n        <activity\n            android:name=\".MainActivity\"\n            android:screenOrientation=\"landscape\">\n            <intent-filter>\n                <action android:name=\"android.intent.action.MAIN\" />\n\n                <category android:name=\"android.intent.category.LAUNCHER\" />\n            </intent-filter>\n        </activity>\n    </application>\n\n</manifest>";
    private final String fileStrings = "<resources>\n    <string name=\"app_name\">%s</string>\n</resources>\n";
    private final String fileStyles = "<resources>\n\n    <!-- Base application theme. -->\n    <style name=\"AppTheme\" parent=\"android:Theme.Holo.Light.DarkActionBar\">\n        <!-- Customize your theme here. -->\n    </style>\n\n</resources>";
    private final String fileGradleApp = "apply plugin: 'com.android.application'\n\nandroid {\n    compileSdkVersion 25\n    buildToolsVersion \"25.0.2\"\n    defaultConfig {\n        applicationId \"%s\"\n        minSdkVersion 15\n        targetSdkVersion 25\n        versionCode 1\n        versionName \"1.0\"\n        multiDexEnabled true\n    }\n    buildTypes {\n        release {\n            minifyEnabled false\n            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'\n        }\n    }\n}\n\ndependencies {\n    compile fileTree(dir: 'libs', include: ['*.jar'])\n}";
    private final String fileGradleRoot = "// Top-level build file where you can add configuration options common to all sub-projects/modules.\n\nbuildscript {\n    repositories {\n        jcenter()\n    }\n    dependencies {\n        classpath 'com.android.tools.build:gradle:2.3.1'\n\n        // NOTE: Do not place your application dependencies here; they belong\n        // in the individual module build.gradle files\n    }\n}\n\nallprojects {\n    repositories {\n        jcenter()\n        maven { url \"http://dl.bintray.com/jmonkeyengine/org.jmonkeyengine\" }\n    }\n\n    tasks.withType(JavaCompile) {\n        sourceCompatibility = \"1.7\"\n        targetCompatibility = \"1.7\"\n    }\n}\n\n// All modules:\n//    jme3-android-native\n//    jme3-android\n//    jme3-blender\n//    jme3-bullet-native-android\n//    jme3-bullet-native\n//    jme3-bullet\n//    jme3-core\n//    jme3-desktop\n//    jme3-effects\n//    jme3-examples\n//    jme3-ios\n//    jme3-jbullet\n//    jme3-jogg\n//    jme3-jogl\n//    jme3-lwjgl\n//    jme3-lwjgl3\n//    jme3-networking\n//    jme3-niftygui\n//    jme3-plugins\n//    jme3-terrain\n\ndef jme3 = [v:'3.1.0-stable', g:'org.jmonkeyengine']\n\nproject(\":core\") {\n    apply plugin: \"java\"\n    sourceSets.main.java.srcDirs = [\"src/\"]\n    dependencies {\n        // Basic\n        compile \"${jme3.g}:jme3-core:${jme3.v}\"\n\n        // Optional\n//        compile \"${jme3.g}:jme3-effects:${jme3.v}\"\n//        compile \"${jme3.g}:jme3-plugins:${jme3.v}\"\n//        compile \"${jme3.g}:jme3-jogg:${jme3.v}\"\n//        compile \"${jme3.g}:jme3-terrain:${jme3.v}\"\n//        compile \"${jme3.g}:jme3-blender:${jme3.v}\"\n//        compile \"${jme3.g}:jme3-bullet-native:${jme3.v}\"\n//        compile \"${jme3.g}:jme3-networking:${jme3.v}\"\n\n        // Resources\n//        compile \"net.sf.sociaal:jME3-testdata:3.0.0.20130526\"\n    }\n}\n\nproject(\":desktop\") {\n    apply plugin: \"java\"\n    sourceSets.main.java.srcDirs = [\"src/\"]\n    dependencies {\n        compile files(\"../app/src/main/assets\")\n        compile project(\":core\")\n        compile \"${jme3.g}:jme3-desktop:${jme3.v}\"\n        compile \"${jme3.g}:jme3-lwjgl:${jme3.v}\"\n    }\n}\n\nproject(\":app\") {\n    apply plugin: \"android\"\n    dependencies {\n        compile project(\":core\")\n        compile \"${jme3.g}:jme3-android:${jme3.v}\"\n        compile \"${jme3.g}:jme3-android-native:${jme3.v}\"\n//        compile \"${jme3.g}:jme3-bullet-native-android:${jme3.v}\"\n    }\n}\n\ntask clean(type: Delete) {\n    delete rootProject.buildDir\n}";


    private String sProjectName;
    private String sPackageName;
    private String sName;
    private String sPackageDirs;

    private EventListener eventListener;
    private int count;

    public Creator(String sProjectName, String sPackageName, EventListener eventListener) {
        this.eventListener = eventListener;
        // Check strings for null etc
        if (sProjectName.length() < 1) sProjectName = "MyGame";
        if (sPackageName.length() < 1) sPackageName = "org.gamepackage";
        this.sProjectName = sProjectName.trim();
        this.sPackageName = sPackageName.trim();
        sName = sProjectName.toLowerCase();
        sPackageDirs = sPackageName.replace(".", "/");
        startTheDance();
    }

    private void startTheDance() {
        // Make dirs
        // If ProjectDir exist add index
        createRootDir(sProjectName);

        String appSrcDir = sProjectName + appMaskSrc + sPackageDirs + "/" + sName;
        createDirs(appSrcDir);
        String appResDir = sProjectName + appMaskRes;
        createDirs(appResDir);

        String coreSrcDir = sProjectName + coreMask + sPackageDirs + "/" + sName;
        createDirs(coreSrcDir);

        String desktopSrcDir = sProjectName + desktopMask + sPackageDirs + "/" + sName;
        createDirs(desktopSrcDir);

        // Create assets dir
        for (String s : assetsDirs) {
            createDirs(sProjectName + appMaskAssets + s);
        }

        // Create files
        String sPackageNameForFiles = sPackageName + "." + sName;

        createFile(desktopSrcDir + "/DesktopLauncher.java", String.format(fileDesktopLauncher, sPackageNameForFiles));
        createFile(coreSrcDir + "/Game.java", String.format(fileGame, sPackageNameForFiles));

        String sGragleRootPath = sProjectName + "/build.gradle";
        createFile(sGragleRootPath, fileGradleRoot);

        String sSettingsPath = sProjectName + "/settings.gradle";
        createFile(sSettingsPath, "include ':app', ':core', ':desktop'");

        //// Android section
        createFile(appSrcDir + "/MainActivity.java", String.format(fileActivity, sPackageNameForFiles));
        String sManifestPath = sProjectName + appMaskManifest + "AndroidManifest.xml";
        createFile(sManifestPath, String.format(fileManifest, sPackageNameForFiles));
        createFile(appResDir + "/strings.xml", String.format(fileStrings, sProjectName));
        createFile(appResDir + "/styles.xml", fileStyles);
        String sGradleAppPath = sProjectName + appMaskGradle + "build.gradle";
        createFile(sGradleAppPath, String.format(fileGradleApp, sPackageNameForFiles));

        // Mission Complete
        eventListener.onEvent("New -> Import Project ...");
        eventListener.complete();
    }

    private void createRootDir(String dirName) {
        boolean dirCreated;
        do {
            dirCreated = new File(dirName).mkdir();
            if (!dirCreated) {
                count++;
                dirName += String.valueOf(count);
            }

        } while (!dirCreated);

        sProjectName = dirName;
    }

    private void createDirs(String sPath) {
        if (new File(sPath).mkdirs()) {
            eventListener.onEvent("Make dir: " + sPath);
        } else {
            fail("Make dir");
        }
    }

    private void createFile(String name, String content) {
        System.out.println(name);
        System.out.println(content);

        File file = new File(name);

        try {
            //проверяем, что если файл не существует то создаем его
            if (!file.exists()) {
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст s файл
                out.print(content);
                eventListener.onEvent("Created " + name);
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch (IOException e) {
            fail("Create " + name);
        }
    }

    private void fail(String message) {
        eventListener.onEvent(message + " failed");
    }
}
