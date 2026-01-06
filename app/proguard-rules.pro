# [SECURITY & COMPRESSION]
# These rules prevent the builder from breaking Media3 and Kotlin code during optimization.

-keep class androidx.media3.** { *; }
-keep class com.watermelon.player.database.** { *; }
-dontwarn androidx.media3.**
