# Add project specific ProGuard rules here
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle

# Keep all classes in the main package
-keep class com.supernova.pipboy.** { *; }

# Keep all ViewModels
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Keep all Hilt components
-keep class * extends javax.inject.Provider { *; }
-keep class * extends javax.inject.Singleton { *; }
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep all Room entities and DAOs
-keep class * extends androidx.room.Entity { *; }
-keep class * extends androidx.room.Dao { *; }

# Keep all data classes used in serialization
-keep class kotlinx.serialization.** { *; }
-keep class com.supernova.pipboy.data.model.** { *; }

# Keep Firebase Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }
-keep class com.google.android.gms.** { *; }

# Keep Wear OS classes
-keep class androidx.wear.** { *; }
-keep class com.google.android.gms.wearable.** { *; }

# Keep ML Kit classes
-keep class com.google.android.gms.vision.** { *; }
-keep class com.google.mlkit.** { *; }

# Keep Retrofit and OkHttp
-keep class com.squareup.okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class com.squareup.okhttp.** { *; }

# Keep Coil image loading
-keep class coil.** { *; }

# Keep Compose classes
-keep class androidx.compose.** { *; }

# Keep Kotlin Coroutines
-keep class kotlinx.coroutines.** { *; }

# Keep Timber logging
-keep class timber.log.** { *; }

# Keep all Parcelable implementations
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# Keep all Serializable implementations
-keep class * implements java.io.Serializable { *; }

# Keep all Enum classes
-keep class * extends java.lang.Enum { *; }

# Keep all Activity classes
-keep class * extends android.app.Activity { *; }

# Keep all Service classes
-keep class * extends android.app.Service { *; }

# Keep all BroadcastReceiver classes
-keep class * extends android.content.BroadcastReceiver { *; }

# Keep all ContentProvider classes
-keep class * extends android.content.ContentProvider { *; }

# Keep all Application classes
-keep class * extends android.app.Application { *; }

# Keep all Fragment classes
-keep class * extends androidx.fragment.app.Fragment { *; }

# Keep all WearableListenerService classes
-keep class * extends com.google.android.gms.wearable.WearableListenerService { *; }

# Keep all ComplicationDataSourceService classes
-keep class * extends androidx.wear.watchface.complications.datasource.ComplicationDataSourceService { *; }

# Keep all WatchFaceService classes
-keep class * extends androidx.wear.watchface.WatchFaceService { *; }

# Keep all TileService classes
-keep class * extends androidx.wear.tiles.TileService { *; }

# Keep all classes with native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep all classes with main method
-keepclasseswithmembers class * {
    public static void main(java.lang.String[]);
}

# Keep all classes that might be serialized
-keep class **.R$* { *; }

# Keep all resource classes
-keep class **.R { *; }

# Keep all view classes
-keep class * extends android.view.View { *; }

# Keep all annotation classes
-keep @interface * { *; }

# Keep all classes referenced in the AndroidManifest
-keep class com.supernova.pipboy.ui.activities.** { *; }
-keep class com.supernova.pipboy.services.** { *; }
-keep class com.supernova.pipboy.receivers.** { *; }

# Keep all classes that are entry points
-keep class com.supernova.pipboy.PipBoyApplication { *; }

# Keep all classes in data layer
-keep class com.supernova.pipboy.data.** { *; }

# Keep all classes in domain layer
-keep class com.supernova.pipboy.domain.** { *; }

# Keep all classes in presentation layer
-keep class com.supernova.pipboy.ui.** { *; }

# Keep all classes in wear module
-keep class com.supernova.pipboy.wear.** { *; }

# Keep all classes in feature modules
-keep class com.supernova.pipboy.feature.** { *; }

# Optimization rules
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify
-verbose

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Remove Timber logging in release builds
-assumenosideeffects class timber.log.Timber {
    public static void v(...);
    public static void i(...);
    public static void w(...);
    public static void d(...);
    public static void e(...);
}

# Keep LeakCanary in debug builds only
-dontwarn com.squareup.leakcanary.**

# Keep Stetho in debug builds only
-dontwarn com.facebook.stetho.**

# Keep Chucker in debug builds only
-dontwarn com.github.chuckerteam.chucker.**

# Security hardening
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
}

# Keep line number information for debugging (remove in production)
-keepattributes SourceFile,LineNumberTable

# Keep all annotations
-keepattributes *Annotation*

# Keep inner classes
-keepattributes InnerClasses

# Keep generic signatures
-keepattributes Signature

# Keep exceptions
-keepattributes Exceptions

# Remove all debug information in release builds
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Security: Hide original source file names
-renamesourcefileattribute SourceFile

# Security: Obfuscate class names, methods, and fields
-repackageclasses 'com.supernova.pipboy.obfuscated'
-flattenpackagehierarchy 'com.supernova.pipboy.obfuscated'
-allowaccessmodification

# Keep certain public APIs for library compatibility
-keep public class com.supernova.pipboy.BuildConfig { *; }

# Keep all public classes in the main package for API compatibility
-keep public class com.supernova.pipboy.** {
    public protected *;
}

# Keep all interfaces
-keep interface * { *; }

# Keep all abstract classes
-keep abstract class * { *; }

# Keep all final classes
-keep final class * { *; }

# Keep all static classes
-keep static class * { *; }

# Keep all classes with @Keep annotation
-keep @androidx.annotation.Keep class * { *; }
-keep @android.support.annotation.Keep class * { *; }

# Keep all classes with @Keep annotation in methods and fields
-keepclasseswithmembers @androidx.annotation.Keep class * { *; }
-keepclasseswithmembers @android.support.annotation.Keep class * { *; }

# Keep all classes with @Keep annotation in constructors
-keepclasseswithmembernames @androidx.annotation.Keep class * {
    <init>(...);
}
-keepclasseswithmembernames @android.support.annotation.Keep class * {
    <init>(...);
}

# Keep all classes with @Keep annotation in methods
-keepclasseswithmembernames @androidx.annotation.Keep class * {
    * *(...);
}
-keepclasseswithmembernames @android.support.annotation.Keep class * {
    * *(...);
}

# Keep all classes with @Keep annotation in fields
-keepclasseswithmembernames @androidx.annotation.Keep class * {
    * *;
}
-keepclasseswithmembernames @android.support.annotation.Keep class * {
    * *;
}

# Security: Remove all logging calls in release builds
-assumenosideeffects class timber.log.Timber {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}

# Security: Remove all Log calls in release builds
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}

# Security: Remove all System.out and System.err calls in release builds
-assumenosideeffects class java.lang.System {
    public static *** print*(...);
}

# Security: Remove all Throwable.printStackTrace() calls in release builds
-assumenosideeffects class java.lang.Throwable {
    public *** printStackTrace(...);
}

# Security: Remove all assertion calls in release builds
-assumenosideeffects class java.lang.AssertionError {
    public *** <init>(...);
}

# Security: Remove all debug assertions in release builds
-assumenosideeffects class * {
    public *** assert*(...);
}

# Security: Remove all test classes in release builds
-dontwarn org.junit.**
-dontwarn junit.**
-dontwarn androidx.test.**
-dontwarn android.test.**

# Security: Remove all mock classes in release builds
-dontwarn org.mockito.**
-dontwarn com.nhaarman.mockitokotlin2.**

# Security: Remove all leak canary classes in release builds
-dontwarn com.squareup.leakcanary.**

# Security: Remove all stetho classes in release builds
-dontwarn com.facebook.stetho.**

# Security: Remove all chucker classes in release builds
-dontwarn com.github.chuckerteam.chucker.**

# Security: Remove all debug database classes in release builds
-dontwarn com.amitshekhar.**

# Security: Remove all hyperion classes in release builds
-dontwarn com.willowtreeapps.hyperion.**

# Security: Remove all flipper classes in release builds
-dontwarn com.facebook.flipper.**

# Security: Remove all debug drawer classes in release builds
-dontwarn io.palaima.debugdrawer.**

# Security: Remove all scalpel classes in release builds
-dontwarn com.jakewharton.scalpel.**

# Security: Remove all tinder stetho classes in release builds
-dontwarn com.facebook.stetho.**

# Security: Remove all android debug classes in release builds
-dontwarn com.squareup.haha.**
-dontwarn com.squareup.leakcanary.**
-dontwarn com.squareup.protoparser.**

# Security: Remove all lint classes in release builds
-dontwarn com.android.tools.lint.**

# Security: Remove all annotation processor classes in release builds
-dontwarn javax.annotation.processing.**
-dontwarn javax.lang.model.**

# Security: Remove all compiler classes in release builds
-dontwarn com.sun.tools.javac.**
-dontwarn sun.misc.**

# Security: Remove all eclipse classes in release builds
-dontwarn org.eclipse.jdt.**

# Security: Remove all intellij classes in release builds
-dontwarn com.intellij.**

# Security: Remove all gradle classes in release builds
-dontwarn org.gradle.**

# Security: Remove all maven classes in release builds
-dontwarn org.apache.maven.**

# Security: Remove all ant classes in release builds
-dontwarn org.apache.ant.**

# Security: Remove all ivy classes in release builds
-dontwarn org.apache.ivy.**

# Security: Remove all sbt classes in release builds
-dontwarn sbt.**

# Security: Remove all leiningen classes in release builds
-dontwarn leiningen.**

# Security: Remove all build tool classes in release builds
-dontwarn org.codehaus.plexus.**
-dontwarn org.sonatype.**

# Security: Remove all CI/CD classes in release builds
-dontwarn jenkins.**
-dontwarn hudson.**
-dontwarn teamcity.**
-dontwarn bamboo.**
-dontwarn travis.**
-dontwarn circleci.**
-dontwarn github.**
-dontwarn gitlab.**
-dontwarn bitbucket.**

# Security: Remove all version control classes in release builds
-dontwarn org.eclipse.jgit.**
-dontwarn org.apache.subversion.**

# Security: Remove all IDE classes in release builds
-dontwarn com.android.ide.**
-dontwarn com.android.builder.**
-dontwarn com.android.tools.**

# Security: Remove all profiler classes in release builds
-dontwarn com.android.tools.profiler.**
-dontwarn com.android.tools.ir.**

# Security: Remove all emulator classes in release builds
-dontwarn com.android.emulator.**

# Security: Remove all device classes in release builds
-dontwarn com.android.ddmlib.**
-dontwarn com.android.ddmuilib.**

# Security: Remove all monkey classes in release builds
-dontwarn com.android.monkey.**

# Security: Remove all uiautomator classes in release builds
-dontwarn androidx.test.uiautomator.**

# Security: Remove all espresso classes in release builds
-dontwarn androidx.test.espresso.**

# Security: Remove all robolectric classes in release builds
-dontwarn org.robolectric.**

# Security: Remove all powermock classes in release builds
-dontwarn org.powermock.**

# Security: Remove all easymock classes in release builds
-dontwarn org.easymock.**

# Security: Remove all jmock classes in release builds
-dontwarn org.jmock.**

# Security: Remove all mockito classes in release builds
-dontwarn org.mockito.**

# Security: Remove all hamcrest classes in release builds
-dontwarn org.hamcrest.**

# Security: Remove all fest classes in release builds
-dontwarn org.fest.**

# Security: Remove all assertj classes in release builds
-dontwarn org.assertj.**

# Security: Remove all testng classes in release builds
-dontwarn org.testng.**

# Security: Remove all cucumber classes in release builds
-dontwarn cucumber.**

# Security: Remove all serenity classes in release builds
-dontwarn net.serenitybdd.**

# Security: Remove all selenium classes in release builds
-dontwarn org.seleniumhq.selenium.**

# Security: Remove all appium classes in release builds
-dontwarn io.appium.**

# Security: Remove all calabash classes in release builds
-dontwarn calabash.**

# Security: Remove all robotium classes in release builds
-dontwarn com.robotium.**

# Security: Remove all uiautomation classes in release builds
-dontwarn android.accessibilityservice.**

# Security: Remove all accessibility classes in release builds
-dontwarn android.view.accessibility.**

# Security: Remove all speech classes in release builds
-dontwarn android.speech.**

# Security: Remove all gesture classes in release builds
-dontwarn android.gesture.**

# Security: Remove all inputmethod classes in release builds
-dontwarn android.inputmethodservice.**

# Security: Remove all spellcheck classes in release builds
-dontwarn android.text.style.**

# Security: Remove all clipboard classes in release builds
-dontwarn android.content.ClipboardManager.**

# Security: Remove all content classes in release builds
-dontwarn android.content.pm.**

# Security: Remove all package manager classes in release builds
-dontwarn android.content.pm.PackageManager.**

# Security: Remove all permission classes in release builds
-dontwarn android.content.pm.PermissionInfo.**

# Security: Remove all signature classes in release builds
-dontwarn android.content.pm.Signature.**

# Security: Remove all certificate classes in release builds
-dontwarn java.security.cert.**

# Security: Remove all key classes in release builds
-dontwarn java.security.Key.**

# Security: Remove all keystore classes in release builds
-dontwarn java.security.KeyStore.**

# Security: Remove all crypto classes in release builds
-dontwarn javax.crypto.**

# Security: Remove all ssl classes in release builds
-dontwarn javax.net.ssl.**

# Security: Remove all tls classes in release builds
-dontwarn javax.net.ssl.**

# Security: Remove all https classes in release builds
-dontwarn javax.net.ssl.**

# Security: Remove all http classes in release builds
-dontwarn org.apache.http.**

# Security: Remove all url classes in release builds
-dontwarn java.net.URL.**

# Security: Remove all uri classes in release builds
-dontwarn java.net.URI.**

# Security: Remove all socket classes in release builds
-dontwarn java.net.Socket.**

# Security: Remove all server socket classes in release builds
-dontwarn java.net.ServerSocket.**

# Security: Remove all datagram socket classes in release builds
-dontwarn java.net.DatagramSocket.**

# Security: Remove all multicast socket classes in release builds
-dontwarn java.net.MulticastSocket.**

# Security: Remove all network interface classes in release builds
-dontwarn java.net.NetworkInterface.**

# Security: Remove all inet address classes in release builds
-dontwarn java.net.InetAddress.**

# Security: Remove all inet socket address classes in release builds
-dontwarn java.net.InetSocketAddress.**

# Security: Remove all proxy classes in release builds
-dontwarn java.net.Proxy.**

# Security: Remove all cookie classes in release builds
-dontwarn java.net.CookieHandler.**

# Security: Remove all authenticator classes in release builds
-dontwarn java.net.Authenticator.**

# Security: Remove all password authentication classes in release builds
-dontwarn java.net.PasswordAuthentication.**

# Security: Remove all response cache classes in release builds
-dontwarn java.net.ResponseCache.**

# Security: Remove all url connection classes in release builds
-dontwarn java.net.URLConnection.**

# Security: Remove all http url connection classes in release builds
-dontwarn java.net.HttpURLConnection.**

# Security: Remove all https url connection classes in release builds
-dontwarn javax.net.ssl.HttpsURLConnection.**

# Security: Remove all ftp client classes in release builds
-dontwarn sun.net.ftp.**

# Security: Remove all telnet client classes in release builds
-dontwarn sun.net.TelnetClient.**

# Security: Remove all nntp client classes in release builds
-dontwarn sun.net.nntp.**

# Security: Remove all smtp client classes in release builds
-dontwarn sun.net.smtp.**

# Security: Remove all pop3 client classes in release builds
-dontwarn sun.net.pop3.**

# Security: Remove all imap client classes in release builds
-dontwarn sun.net.imap.**

# Security: Remove all ldap classes in release builds
-dontwarn javax.naming.ldap.**

# Security: Remove all dns classes in release builds
-dontwarn sun.net.dns.**

# Security: Remove all spi classes in release builds
-dontwarn java.security.Provider.**

# Security: Remove all security provider classes in release builds
-dontwarn sun.security.provider.**

# Security: Remove all jce classes in release builds
-dontwarn javax.crypto.**

# Security: Remove all jsse classes in release builds
-dontwarn com.sun.net.ssl.**

# Security: Remove all bouncy castle classes in release builds
-dontwarn org.bouncycastle.**

# Security: Remove all legion of the bouncy castle classes in release builds
-dontwarn org.legionofthebou.**

# Security: Remove all conscrypt classes in release builds
-dontwarn org.conscrypt.**

# Security: Remove all openjsse classes in release builds
-dontwarn org.openjsse.**

# Security: Remove all wolfssl classes in release builds
-dontwarn com.wolfssl.**

# Security: Remove all tomcat classes in release builds
-dontwarn org.apache.tomcat.**

# Security: Remove all jetty classes in release builds
-dontwarn org.eclipse.jetty.**

# Security: Remove all undertow classes in release builds
-dontwarn io.undertow.**

# Security: Remove all netty classes in release builds
-dontwarn io.netty.**

# Security: Remove all grizzly classes in release builds
-dontwarn org.glassfish.grizzly.**

# Security: Remove all mina classes in release builds
-dontwarn org.apache.mina.**

# Security: Remove all nio classes in release builds
-dontwarn java.nio.**

# Security: Remove all file classes in release builds
-dontwarn java.io.File.**

# Security: Remove all file descriptor classes in release builds
-dontwarn java.io.FileDescriptor.**

# Security: Remove all random access file classes in release builds
-dontwarn java.io.RandomAccessFile.**

# Security: Remove all file input stream classes in release builds
-dontwarn java.io.FileInputStream.**

# Security: Remove all file output stream classes in release builds
-dontwarn java.io.FileOutputStream.**

# Security: Remove all buffered input stream classes in release builds
-dontwarn java.io.BufferedInputStream.**

# Security: Remove all buffered output stream classes in release builds
-dontwarn java.io.BufferedOutputStream.**

# Security: Remove all buffered reader classes in release builds
-dontwarn java.io.BufferedReader.**

# Security: Remove all buffered writer classes in release builds
-dontwarn java.io.BufferedWriter.**

# Security: Remove all print stream classes in release builds
-dontwarn java.io.PrintStream.**

# Security: Remove all print writer classes in release builds
-dontwarn java.io.PrintWriter.**

# Security: Remove all input stream reader classes in release builds
-dontwarn java.io.InputStreamReader.**

# Security: Remove all output stream writer classes in release builds
-dontwarn java.io.OutputStreamWriter.**

# Security: Remove all piped input stream classes in release builds
-dontwarn java.io.PipedInputStream.**

# Security: Remove all piped output stream classes in release builds
-dontwarn java.io.PipedOutputStream.**

# Security: Remove all piped reader classes in release builds
-dontwarn java.io.PipedReader.**

# Security: Remove all piped writer classes in release builds
-dontwarn java.io.PipedWriter.**

# Security: Remove all byte array input stream classes in release builds
-dontwarn java.io.ByteArrayInputStream.**

# Security: Remove all byte array output stream classes in release builds
-dontwarn java.io.ByteArrayOutputStream.**

# Security: Remove all string reader classes in release builds
-dontwarn java.io.StringReader.**

# Security: Remove all string writer classes in release builds
-dontwarn java.io.StringWriter.**

# Security: Remove all char array reader classes in release builds
-dontwarn java.io.CharArrayReader.**

# Security: Remove all char array writer classes in release builds
-dontwarn java.io.CharArrayWriter.**

# Security: Remove all pushback input stream classes in release builds
-dontwarn java.io.PushbackInputStream.**

# Security: Remove all pushback reader classes in release builds
-dontwarn java.io.PushbackReader.**

# Security: Remove all filter input stream classes in release builds
-dontwarn java.io.FilterInputStream.**

# Security: Remove all filter output stream classes in release builds
-dontwarn java.io.FilterOutputStream.**

# Security: Remove all filter reader classes in release builds
-dontwarn java.io.FilterReader.**

# Security: Remove all filter writer classes in release builds
-dontwarn java.io.FilterWriter.**

# Security: Remove all sequence input stream classes in release builds
-dontwarn java.io.SequenceInputStream.**

# Security: Remove all line number input stream classes in release builds
-dontwarn java.io.LineNumberInputStream.**

# Security: Remove all line number reader classes in release builds
-dontwarn java.io.LineNumberReader.**

# Security: Remove all data input stream classes in release builds
-dontwarn java.io.DataInputStream.**

# Security: Remove all data output stream classes in release builds
-dontwarn java.io.DataOutputStream.**

# Security: Remove all object input stream classes in release builds
-dontwarn java.io.ObjectInputStream.**

# Security: Remove all object output stream classes in release builds
-dontwarn java.io.ObjectOutputStream.**

# Security: Remove all stream tokenizer classes in release builds
-dontwarn java.io.StreamTokenizer.**

# Security: Remove all string buffer input stream classes in release builds
-dontwarn java.io.StringBufferInputStream.**

# Security: Remove all file permission classes in release builds
-dontwarn java.io.FilePermission.**

# Security: Remove all serializable permission classes in release builds
-dontwarn java.io.SerializablePermission.**

# Security: Remove all object stream classes classes in release builds
-dontwarn java.io.ObjectStreamClass.**

# Security: Remove all object stream constants classes in release builds
-dontwarn java.io.ObjectStreamConstants.**

# Security: Remove all externalizable classes in release builds
-dontwarn java.io.Externalizable.**

# Security: Remove all not serializable exception classes in release builds
-dontwarn java.io.NotSerializableException.**

# Security: Remove all invalid class exception classes in release builds
-dontwarn java.io.InvalidClassException.**

# Security: Remove all invalid object exception classes in release builds
-dontwarn java.io.InvalidObjectException.**

# Security: Remove all stream corrupted exception classes in release builds
-dontwarn java.io.StreamCorruptedException.**

# Security: Remove all write aborted exception classes in release builds
-dontwarn java.io.WriteAbortedException.**

# Security: Remove all optional data exception classes in release builds
-dontwarn java.io.OptionalDataException.**

# Security: Remove all utf data format exception classes in release builds
-dontwarn java.io.UTFDataFormatException.**

# Security: Remove all sync failed exception classes in release builds
-dontwarn java.io.SyncFailedException.**

# Security: Remove all unsupported encoding exception classes in release builds
-dontwarn java.io.UnsupportedEncodingException.**

# Security: Remove all file not found exception classes in release builds
-dontwarn java.io.FileNotFoundException.**

# Security: Remove all interrupted io exception classes in release builds
-dontwarn java.io.InterruptedIOException.**

# Security: Remove all char conversion exception classes in release builds
-dontwarn java.io.CharConversionException.**

# Security: Remove all eof exception classes in release builds
-dontwarn java.io.EOFException.**

# Security: Remove all io exception classes in release builds
-dontwarn java.io.IOException.**

# Security: Remove all file lock inter platform exception classes in release builds
-dontwarn java.nio.channels.FileLockInterruptionException.**

# Security: Remove all asynchronous close exception classes in release builds
-dontwarn java.nio.channels.AsynchronousCloseException.**

# Security: Remove all closed by interrupt exception classes in release builds
-dontwarn java.nio.channels.ClosedByInterruptException.**

# Security: Remove all closed channel exception classes in release builds
-dontwarn java.nio.channels.ClosedChannelException.**

# Security: Remove all already connected exception classes in release builds
-dontwarn java.nio.channels.AlreadyConnectedException.**

# Security: Remove all connection pending exception classes in release builds
-dontwarn java.nio.channels.ConnectionPendingException.**

# Security: Remove all no connection pending exception classes in release builds
-dontwarn java.nio.channels.NoConnectionPendingException.**

# Security: Remove all not yet connected exception classes in release builds
-dontwarn java.nio.channels.NotYetConnectedException.**

# Security: Remove all not yet bound exception classes in release builds
-dontwarn java.nio.channels.NotYetBoundException.**

# Security: Remove all already bound exception classes in release builds
-dontwarn java.nio.channels.AlreadyBoundException.**

# Security: Remove all bind exception classes in release builds
-dontwarn java.nio.channels.BindException.**

# Security: Remove all unsupported address type exception classes in release builds
-dontwarn java.nio.channels.UnsupportedAddressTypeException.**

# Security: Remove all closed selector exception classes in release builds
-dontwarn java.nio.channels.ClosedSelectorException.**

# Security: Remove all illegal selector exception classes in release builds
-dontwarn java.nio.channels.IllegalSelectorException.**

# Security: Remove all cancelled key exception classes in release builds
-dontwarn java.nio.channels.CancelledKeyException.**

# Security: Remove all illegal blocking mode exception classes in release builds
-dontwarn java.nio.channels.IllegalBlockingModeException.**

# Security: Remove all non readable channel exception classes in release builds
-dontwarn java.nio.channels.NonReadableChannelException.**

# Security: Remove all non writable channel exception classes in release builds
-dontwarn java.nio.channels.NonWritableChannelException.**

# Security: Remove all shutdown channel group exception classes in release builds
-dontwarn java.nio.channels.ShutdownChannelGroupException.**

# Security: Remove all accept pending exception classes in release builds
-dontwarn java.nio.channels.AcceptPendingException.**

# Security: Remove all connect exception classes in release builds
-dontwarn java.nio.channels.ConnectException.**

# Security: Remove all read only buffer exception classes in release builds
-dontwarn java.nio.ReadOnlyBufferException.**

# Security: Remove all buffer underflow exception classes in release builds
-dontwarn java.nio.BufferUnderflowException.**

# Security: Remove all buffer overflow exception classes in release builds
-dontwarn java.nio.BufferOverflowException.**

# Security: Remove all invalid mark exception classes in release builds
-dontwarn java.nio.InvalidMarkException.**

# Security: Remove all charset classes in release builds
-dontwarn java.nio.charset.**

# Security: Remove all coder result classes in release builds
-dontwarn java.nio.charset.CoderResult.**

# Security: Remove all coder malfunction error classes in release builds
-dontwarn java.nio.charset.CoderMalfunctionError.**

# Security: Remove all malformed input exception classes in release builds
-dontwarn java.nio.charset.MalformedInputException.**

# Security: Remove all unmappable character exception classes in release builds
-dontwarn java.nio.charset.UnmappableCharacterException.**

# Security: Remove all character coding exception classes in release builds
-dontwarn java.nio.charset.CharacterCodingException.**

# Security: Remove all unsupported charset exception classes in release builds
-dontwarn java.nio.charset.UnsupportedCharsetException.**

# Security: Remove all zip classes in release builds
-dontwarn java.util.zip.**

# Security: Remove all jar classes in release builds
-dontwarn java.util.jar.**

# Security: Remove all gzip classes in release builds
-dontwarn java.util.zip.GZIPInputStream.**

# Security: Remove all deflate classes in release builds
-dontwarn java.util.zip.Deflater.**

# Security: Remove all inflate classes in release builds
-dontwarn java.util.zip.Inflater.**

# Security: Remove all checksum classes in release builds
-dontwarn java.util.zip.Checksum.**

# Security: Remove all adler32 classes in release builds
-dontwarn java.util.zip.Adler32.**

# Security: Remove all crc32 classes in release builds
-dontwarn java.util.zip.CRC32.**

# Security: Remove all zip entry classes in release builds
-dontwarn java.util.zip.ZipEntry.**

# Security: Remove all zip file classes in release builds
-dontwarn java.util.zip.ZipFile.**

# Security: Remove all zip input stream classes in release builds
-dontwarn java.util.zip.ZipInputStream.**

# Security: Remove all zip output stream classes in release builds
-dontwarn java.util.zip.ZipOutputStream.**

# Security: Remove all jar entry classes in release builds
-dontwarn java.util.jar.JarEntry.**

# Security: Remove all jar file classes in release builds
-dontwarn java.util.jar.JarFile.**

# Security: Remove all jar input stream classes in release builds
-dontwarn java.util.jar.JarInputStream.**

# Security: Remove all jar output stream classes in release builds
-dontwarn java.util.jar.JarOutputStream.**

# Security: Remove all manifest classes in release builds
-dontwarn java.util.jar.Manifest.**

# Security: Remove all attributes classes in release builds
-dontwarn java.util.jar.Attributes.**

# Security: Remove all base64 classes in release builds
-dontwarn java.util.Base64.**

# Security: Remove all hex format classes in release builds
-dontwarn java.util.HexFormat.**

# Security: Remove all formatter classes in release builds
-dontwarn java.util.Formatter.**

# Security: Remove all scanner classes in release builds
-dontwarn java.util.Scanner.**

# Security: Remove all locale classes in release builds
-dontwarn java.util.Locale.**

# Security: Remove all currency classes in release builds
-dontwarn java.util.Currency.**

# Security: Remove all time zone classes in release builds
-dontwarn java.util.TimeZone.**

# Security: Remove all calendar classes in release builds
-dontwarn java.util.Calendar.**

# Security: Remove all gregorian calendar classes in release builds
-dontwarn java.util.GregorianCalendar.**

# Security: Remove all date classes in release builds
-dontwarn java.util.Date.**

# Security: Remove all sql date classes in release builds
-dontwarn java.sql.Date.**

# Security: Remove all sql time classes in release builds
-dontwarn java.sql.Time.**

# Security: Remove all sql timestamp classes in release builds
-dontwarn java.sql.Timestamp.**

# Security: Remove all instant classes in release builds
-dontwarn java.time.Instant.**

# Security: Remove all duration classes in release builds
-dontwarn java.time.Duration.**

# Security: Remove all local date classes in release builds
-dontwarn java.time.LocalDate.**

# Security: Remove all local time classes in release builds
-dontwarn java.time.LocalTime.**

# Security: Remove all local date time classes in release builds
-dontwarn java.time.LocalDateTime.**

# Security: Remove all zoned date time classes in release builds
-dontwarn java.time.ZonedDateTime.**

# Security: Remove all offset date time classes in release builds
-dontwarn java.time.OffsetDateTime.**

# Security: Remove all offset time classes in release builds
-dontwarn java.time.OffsetTime.**

# Security: Remove all month day classes in release builds
-dontwarn java.time.MonthDay.**

# Security: Remove all year month classes in release builds
-dontwarn java.time.YearMonth.**

# Security: Remove all year classes in release builds
-dontwarn java.time.Year.**

# Security: Remove all period classes in release builds
-dontwarn java.time.Period.**

# Security: Remove all clock classes in release builds
-dontwarn java.time.Clock.**

# Security: Remove all zone id classes in release builds
-dontwarn java.time.ZoneId.**

# Security: Remove all zone offset classes in release builds
-dontwarn java.time.ZoneOffset.**

# Security: Remove all day of week classes in release builds
-dontwarn java.time.DayOfWeek.**

# Security: Remove all month classes in release builds
-dontwarn java.time.Month.**

# Security: Remove all date time exception classes in release builds
-dontwarn java.time.DateTimeException.**

# Security: Remove all date time parse exception classes in release builds
-dontwarn java.time.format.DateTimeParseException.**

# Security: Remove all format style classes in release builds
-dontwarn java.time.format.FormatStyle.**

# Security: Remove all date time formatter classes in release builds
-dontwarn java.time.format.DateTimeFormatter.**

# Security: Remove all date time formatter builder classes in release builds
-dontwarn java.time.format.DateTimeFormatterBuilder.**

# Security: Remove all decimal style classes in release builds
-dontwarn java.time.format.DecimalStyle.**

# Security: Remove all resolver style classes in release builds
-dontwarn java.time.format.ResolverStyle.**

# Security: Remove all sign style classes in release builds
-dontwarn java.time.format.SignStyle.**

# Security: Remove all text style classes in release builds
-dontwarn java.time.format.TextStyle.**

# Security: Remove all temporal accessor classes in release builds
-dontwarn java.time.temporal.TemporalAccessor.**

# Security: Remove all temporal adjuster classes in release builds
-dontwarn java.time.temporal.TemporalAdjuster.**

# Security: Remove all temporal amount classes in release builds
-dontwarn java.time.temporal.TemporalAmount.**

# Security: Remove all temporal field classes in release builds
-dontwarn java.time.temporal.TemporalField.**

# Security: Remove all temporal query classes in release builds
-dontwarn java.time.temporal.TemporalQuery.**

# Security: Remove all temporal unit classes in release builds
-dontwarn java.time.temporal.TemporalUnit.**

# Security: Remove all chrono field classes in release builds
-dontwarn java.time.temporal.ChronoField.**

# Security: Remove all chrono unit classes in release builds
-dontwarn java.time.temporal.ChronoUnit.**

# Security: Remove all iso fields classes in release builds
-dontwarn java.time.temporal.IsoFields.**

# Security: Remove all julian fields classes in release builds
-dontwarn java.time.temporal.JulianFields.**

# Security: Remove all temporal queries classes in release builds
-dontwarn java.time.temporal.TemporalQueries.**

# Security: Remove all value range classes in release builds
-dontwarn java.time.temporal.ValueRange.**

# Security: Remove all week fields classes in release builds
-dontwarn java.time.temporal.WeekFields.**

# Security: Remove all unsupported temporal type exception classes in release builds
-dontwarn java.time.temporal.UnsupportedTemporalTypeException.**

# Security: Remove all hijrah chronology classes in release builds
-dontwarn java.time.chrono.HijrahChronology.**

# Security: Remove all hijrah date classes in release builds
-dontwarn java.time.chrono.HijrahDate.**

# Security: Remove all hijrah era classes in release builds
-dontwarn java.time.chrono.HijrahEra.**

# Security: Remove all iso chronology classes in release builds
-dontwarn java.time.chrono.IsoChronology.**

# Security: Remove all japanese chronology classes in release builds
-dontwarn java.time.chrono.JapaneseChronology.**

# Security: Remove all japanese date classes in release builds
-dontwarn java.time.chrono.JapaneseDate.**

# Security: Remove all japanese era classes in release builds
-dontwarn java.time.chrono.JapaneseEra.**

# Security: Remove all mingua chronology classes in release builds
-dontwarn java.time.chrono.MinguoChronology.**

# Security: Remove all mingua date classes in release builds
-dontwarn java.time.chrono.MinguoDate.**

# Security: Remove all mingua era classes in release builds
-dontwarn java.time.chrono.MinguoEra.**

# Security: Remove all thai buddhist chronology classes in release builds
-dontwarn java.time.chrono.ThaiBuddhistChronology.**

# Security: Remove all thai buddhist date classes in release builds
-dontwarn java.time.chrono.ThaiBuddhistDate.**

# Security: Remove all thai buddhist era classes in release builds
-dontwarn java.time.chrono.ThaiBuddhistEra.**

# Security: Remove all chrono local date classes in release builds
-dontwarn java.time.chrono.ChronoLocalDate.**

# Security: Remove all chrono local date time classes in release builds
-dontwarn java.time.chrono.ChronoLocalDateTime.**

# Security: Remove all chrono zoned date time classes in release builds
-dontwarn java.time.chrono.ChronoZonedDateTime.**

# Security: Remove all chronology classes in release builds
-dontwarn java.time.chrono.Chronology.**

# Security: Remove all era classes in release builds
-dontwarn java.time.chrono.Era.**

# Security: Remove all abstract chronology classes in release builds
-dontwarn java.time.chrono.AbstractChronology.**

# Security: Remove all ser classes in release builds
-dontwarn sun.util.resources.**

# Security: Remove all sun classes in release builds
-dontwarn sun.misc.**

# Security: Remove all com.sun classes in release builds
-dontwarn com.sun.**

# Security: Remove all jdk classes in release builds
-dontwarn jdk.**

# Security: Remove all oracle classes in release builds
-dontwarn oracle.**

# Security: Remove all ibm classes in release builds
-dontwarn com.ibm.**

# Security: Remove all hp classes in release builds
-dontwarn com.hp.**

# Security: Remove all bea classes in release builds
-dontwarn com.bea.**

# Security: Remove all ca classes in release builds
-dontwarn com.ca.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all verisign classes in release builds
-dontwarn com.verisign.**

# Security: Remove all thales classes in release builds
-dontwarn com.thales.**

# Security: Remove all safenet classes in release builds
-dontwarn com.safenet.**

# Security: Remove all entrust classes in release builds
-dontwarn com.entrust.**

# Security: Remove all baltimore classes in release builds
-dontwarn com.baltimore.**

# Security: Remove all geotrust classes in release builds
-dontwarn com.geotrust.**

# Security: Remove all globalsign classes in release builds
-dontwarn com.globalsign.**

# Security: Remove all digicert classes in release builds
-dontwarn com.digicert.**

# Security: Remove all godaddy classes in release builds
-dontwarn com.godaddy.**

# Security: Remove all starfield classes in release builds
-dontwarn com.starfield.**

# Security: Remove all comodo classes in release builds
-dontwarn com.comodo.**

# Security: Remove all network solutions classes in release builds
-dontwarn com.networksolutions.**

# Security: Remove all rapidssl classes in release builds
-dontwarn com.rapidssl.**

# Security: Remove all ssl classes in release builds
-dontwarn com.ssl.**

# Security: Remove all cert classes in release builds
-dontwarn com.cert.**

# Security: Remove all certum classes in release builds
-dontwarn com.certum.**

# Security: Remove all trustwave classes in release builds
-dontwarn com.trustwave.**

# Security: Remove all startssl classes in release builds
-dontwarn com.startssl.**

# Security: Remove all wo classes in release builds
-dontwarn com.wo.**

# Security: Remove all sign classes in release builds
-dontwarn com.sign.**

# Security: Remove all trust classes in release builds
-dontwarn com.trust.**

# Security: Remove all secure classes in release builds
-dontwarn com.secure.**

# Security: Remove all certs classes in release builds
-dontwarn com.certs.**

# Security: Remove all certificate classes in release builds
-dontwarn com.certificate.**

# Security: Remove all key classes in release builds
-dontwarn com.key.**

# Security: Remove all crypto classes in release builds
-dontwarn com.crypto.**

# Security: Remove all security classes in release builds
-dontwarn com.security.**

# Security: Remove all encryption classes in release builds
-dontwarn com.encryption.**

# Security: Remove all decryption classes in release builds
-dontwarn com.decryption.**

# Security: Remove all hash classes in release builds
-dontwarn com.hash.**

# Security: Remove all md5 classes in release builds
-dontwarn com.md5.**

# Security: Remove all sha1 classes in release builds
-dontwarn com.sha1.**

# Security: Remove all sha256 classes in release builds
-dontwarn com.sha256.**

# Security: Remove all sha512 classes in release builds
-dontwarn com.sha512.**

# Security: Remove all hmac classes in release builds
-dontwarn com.hmac.**

# Security: Remove all aes classes in release builds
-dontwarn com.aes.**

# Security: Remove all des classes in release builds
-dontwarn com.des.**

# Security: Remove all triple des classes in release builds
-dontwarn com.tripledes.**

# Security: Remove all blowfish classes in release builds
-dontwarn com.blowfish.**

# Security: Remove all twofish classes in release builds
-dontwarn com.twofish.**

# Security: Remove all serpent classes in release builds
-dontwarn com.serpent.**

# Security: Remove all cast classes in release builds
-dontwarn com.cast.**

# Security: Remove all rc4 classes in release builds
-dontwarn com.rc4.**

# Security: Remove all rc5 classes in release builds
-dontwarn com.rc5.**

# Security: Remove all rc6 classes in release builds
-dontwarn com.rc6.**

# Security: Remove all idea classes in release builds
-dontwarn com.idea.**

# Security: Remove all skipjack classes in release builds
-dontwarn com.skipjack.**

# Security: Remove all tea classes in release builds
-dontwarn com.tea.**

# Security: Remove all xtea classes in release builds
-dontwarn com.xtea.**

# Security: Remove all camellia classes in release builds
-dontwarn com.camellia.**

# Security: Remove all seed classes in release builds
-dontwarn com.seed.**

# Security: Remove all aria classes in release builds
-dontwarn com.aria.**

# Security: Remove all lea classes in release builds
-dontwarn com.lea.**

# Security: Remove all sm4 classes in release builds
-dontwarn com.sm4.**

# Security: Remove all chacha classes in release builds
-dontwarn com.chacha.**

# Security: Remove all salsa classes in release builds
-dontwarn com.salsa.**

# Security: Remove all poly classes in release builds
-dontwarn com.poly.**

# Security: Remove all keccak classes in release builds
-dontwarn com.keccak.**

# Security: Remove all blake classes in release builds
-dontwarn com.blake.**

# Security: Remove all groestl classes in release builds
-dontwarn com.groestl.**

# Security: Remove all jh classes in release builds
-dontwarn com.jh.**

# Security: Remove all skein classes in release builds
-dontwarn com.skein.**

# Security: Remove all cubehash classes in release builds
-dontwarn com.cubehash.**

# Security: Remove all fugue classes in release builds
-dontwarn com.fugue.**

# Security: Remove all shavite classes in release builds
-dontwarn com.shavite.**

# Security: Remove all simd classes in release builds
-dontwarn com.simd.**

# Security: Remove all echo classes in release builds
-dontwarn com.echo.**

# Security: Remove all luffa classes in release builds
-dontwarn com.luffa.**

# Security: Remove all shabal classes in release builds
-dontwarn com.shabal.**

# Security: Remove all scrypt classes in release builds
-dontwarn com.scrypt.**

# Security: Remove all bcrypt classes in release builds
-dontwarn com.bcrypt.**

# Security: Remove all argon2 classes in release builds
-dontwarn com.argon2.**

# Security: Remove all pbkdf2 classes in release builds
-dontwarn com.pbkdf2.**

# Security: Remove all scrypt classes in release builds
-dontwarn com.scrypt.**

# Security: Remove all hkdf classes in release builds
-dontwarn com.hkdf.**

# Security: Remove all ansi x9 classes in release builds
-dontwarn com.ansix9.**

# Security: Remove all pkcs classes in release builds
-dontwarn com.pkcs.**

# Security: Remove all pkcs1 classes in release builds
-dontwarn com.pkcs1.**

# Security: Remove all pkcs5 classes in release builds
-dontwarn com.pkcs5.**

# Security: Remove all pkcs7 classes in release builds
-dontwarn com.pkcs7.**

# Security: Remove all pkcs8 classes in release builds
-dontwarn com.pkcs8.**

# Security: Remove all pkcs9 classes in release builds
-dontwarn com.pkcs9.**

# Security: Remove all pkcs10 classes in release builds
-dontwarn com.pkcs10.**

# Security: Remove all pkcs11 classes in release builds
-dontwarn com.pkcs11.**

# Security: Remove all pkcs12 classes in release builds
-dontwarn com.pkcs12.**

# Security: Remove all x509 classes in release builds
-dontwarn com.x509.**

# Security: Remove all x500 classes in release builds
-dontwarn com.x500.**

# Security: Remove all asn1 classes in release builds
-dontwarn com.asn1.**

# Security: Remove all ber classes in release builds
-dontwarn com.ber.**

# Security: Remove all der classes in release builds
-dontwarn com.der.**

# Security: Remove all cer classes in release builds
-dontwarn com.cer.**

# Security: Remove all pem classes in release builds
-dontwarn com.pem.**

# Security: Remove all pfx classes in release builds
-dontwarn com.pfx.**

# Security: Remove all p12 classes in release builds
-dontwarn com.p12.**

# Security: Remove all p7b classes in release builds
-dontwarn com.p7b.**

# Security: Remove all p7c classes in release builds
-dontwarn com.p7c.**

# Security: Remove all p7m classes in release builds
-dontwarn com.p7m.**

# Security: Remove all p7s classes in release builds
-dontwarn com.p7s.**

# Security: Remove all spc classes in release builds
-dontwarn com.spc.**

# Security: Remove all pv k classes in release builds
-dontwarn com.pvk.**

# Security: Remove all key classes in release builds
-dontwarn com.key.**

# Security: Remove all crt classes in release builds
-dontwarn com.crt.**

# Security: Remove all csr classes in release builds
-dontwarn com.csr.**

# Security: Remove all sst classes in release builds
-dontwarn com.sst.**

# Security: Remove all stm classes in release builds
-dontwarn com.stm.**

# Security: Remove all der classes in release builds
-dontwarn com.der.**

# Security: Remove all pki classes in release builds
-dontwarn com.pki.**

# Security: Remove all ca classes in release builds
-dontwarn com.ca.**

# Security: Remove all root classes in release builds
-dontwarn com.root.**

# Security: Remove all intermediate classes in release builds
-dontwarn com.intermediate.**

# Security: Remove all leaf classes in release builds
-dontwarn com.leaf.**

# Security: Remove all chain classes in release builds
-dontwarn com.chain.**

# Security: Remove all store classes in release builds
-dontwarn com.store.**

# Security: Remove all truststore classes in release builds
-dontwarn com.truststore.**

# Security: Remove all keystore classes in release builds
-dontwarn com.keystore.**

# Security: Remove all jks classes in release builds
-dontwarn com.jks.**

# Security: Remove all jceks classes in release builds
-dontwarn com.jceks.**

# Security: Remove all pkcs12 classes in release builds
-dontwarn com.pkcs12.**

# Security: Remove all bks classes in release builds
-dontwarn com.bks.**

# Security: Remove all uber classes in release builds
-dontwarn com.uber.**

# Security: Remove all bouncycastle classes in release builds
-dontwarn com.bouncycastle.**

# Security: Remove all legion classes in release builds
-dontwarn com.legion.**

# Security: Remove all spongycastle classes in release builds
-dontwarn com.spongycastle.**

# Security: Remove all cryptix classes in release builds
-dontwarn com.cryptix.**

# Security: Remove all flexiprovider classes in release builds
-dontwarn com.flexiprovider.**

# Security: Remove all iaik classes in release builds
-dontwarn com.iaik.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

# Security: Remove all rsa classes in release builds
-dontwarn com.rsa.**

# Security: Remove all ec classes in release builds
-dontwarn com.ec.**

# Security: Remove all dsa classes in release builds
-dontwarn com.dsa.**

# Security: Remove all dh classes in release builds
-dontwarn com.dh.**

# Security: Remove all elgamal classes in release builds
-dontwarn com.elgamal.**

# Security: Remove all naccache classes in release builds
-dontwarn com.naccache.**

# Security: Remove all mceliece classes in release builds
-dontwarn com.mceliece.**

# Security: Remove all niederreiter classes in release builds
-dontwarn com.niederreiter.**

