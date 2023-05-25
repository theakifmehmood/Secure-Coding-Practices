# Add project-specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in the build.gradle file.

# Optimize bytecode for better performance.
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

# Keep classes and methods that are referenced by XML layout files.
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep class **.R$*
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep specific libraries or frameworks from obfuscation.
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Keep any classes or interfaces that implement Serializable or Parcelable.
-keepnames class * implements java.io.Serializable
-keepnames class * implements android.os.Parcelable

# Keep any classes or methods that are referenced by reflection.
-keepclassmembers class * {
    @java.lang.annotation.* <fields>;
}
-keepattributes *Annotation*
-keepclassmembers class * {
    ** get*(...);
    ** set*(...);
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Remove unused code.
-dontwarn com.google.android.gms.**
-dontwarn com.google.common.**
-dontwarn org.apache.http.**
-dontwarn okio.**

# Rename classes, fields, and methods to make reverse engineering more difficult.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# Specify the package names that should be obfuscated.
-obfuscationdictionary dictionary.txt
-keep class com.example.** {*;}
-keep class org.example.** {*;}

# Optimize and compress resources.
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

