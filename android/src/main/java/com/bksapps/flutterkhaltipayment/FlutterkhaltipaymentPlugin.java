package com.bksapps.flutterkhaltipayment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterkhaltipaymentPlugin */
public class FlutterkhaltipaymentPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private static Activity mContext;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "bksapps/flutterkhaltipayment");
    channel.setMethodCallHandler(this);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "bksapps/flutterkhaltipayment");
    channel.setMethodCallHandler(new FlutterkhaltipaymentPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("khaltiPayment")) {
      initKhaltiPayment(call);
    } else {
      result.notImplemented();
    }
  }

  private void initKhaltiPayment(MethodCall paymentData){
    Map<String, Object> map = new HashMap<>();
    map.put("merchant_extra", "This is extra data");

    String productId=paymentData.argument("productId").toString();
    String merchantKey=paymentData.argument("merchantKey").toString();
    String productName=paymentData.argument("productName").toString();
    double amount=paymentData.argument("amount");

//    Config.Builder builder = new Config.Builder("test_public_key_3793047616c14a108d09ddbaa551333f", "Product ID", "Main", 1100L, new OnCheckOutListener() {
//      @Override
//      public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
//        Log.i(action, errorMap.toString());
//        channel.invokeMethod("khaltiPaymentError", errorMap.toString());
//      }
//
//      @Override
//      public void onSuccess(@NonNull Map<String, Object> data) {
//        Log.i("success", data.toString());
//        channel.invokeMethod("khaltiPaymentSuccess", data);
//      }
//    })
//            .paymentPreferences(new ArrayList<PaymentPreference>() {{
//              add(PaymentPreference.KHALTI);
//            }})
//            .additionalData(map)
//            .productUrl("http://example.com/product")
//            .mobile("9800000000");

    Config.Builder builder = new Config.Builder(merchantKey, productId, productName, (long)amount, new OnCheckOutListener() {
      @Override
      public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
        Log.i(action, errorMap.toString());
        Map <String, String> errorMessage= new HashMap<String, String>();
        errorMessage.put("action",action);
        errorMessage.put("message",errorMap.toString());
        channel.invokeMethod("khaltiPaymentError", errorMessage);
      }

      @Override
      public void onSuccess(@NonNull Map<String, Object> data) {
        Log.i("success", data.toString());
        channel.invokeMethod("khaltiPaymentSuccess", data);
      }
    })
            .paymentPreferences(new ArrayList<PaymentPreference>() {{
              add(PaymentPreference.KHALTI);
            }});
    KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(mContext, builder.build());
    mContext.runOnUiThread(new Runnable() {
      public void run() {
        khaltiCheckOut.show();
      }
    });


  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    mContext= binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }
}
