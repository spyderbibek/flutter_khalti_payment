package com.bksapps.flutterkhaltipayment;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
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

import static android.content.ContentValues.TAG;

/** FlutterkhaltipaymentPlugin */
public class FlutterkhaltipaymentPlugin implements MethodCallHandler, FlutterPlugin, ActivityAware  {
  private MethodChannel channel;
  private static Activity mContext;

  public static void registerWith(Registrar registrar) {
    mContext = registrar.activity();
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "bksapps/flutterkhaltipayment");
    channel.setMethodCallHandler(new FlutterkhaltipaymentPlugin());
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "bksapps/flutterkhaltipayment");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("khaltiPaymentStart")) {
      initKhaltiPayment(call);
    } else {
      result.notImplemented();
    }
  }

  private void initKhaltiPayment(MethodCall paymentData){
    Map<String, Object> customData = new HashMap<>(paymentData.argument("customData"));
    String productId=paymentData.argument("productId").toString();
    String merchantKey=paymentData.argument("merchantKey").toString();
    String productName=paymentData.argument("productName").toString();
    double amount=paymentData.argument("amount");
    Log.d(TAG, "initKhaltiPayment: "+ productId+" "+ merchantKey+" "+ productName+" "+ amount);

    Config.Builder builder = new Config.Builder(merchantKey, productId, productName, (long)amount, new OnCheckOutListener() {
      @Override
      public void onSuccess(@NonNull Map<String, Object> data) {
        //{"idx":"TsHxaYRrDd6RBJsqtJnQTT","token":"AX7yDXcJmDYheiaQYTcDRk","amount":1,"mobile":"98XXXXXXXX","product_identity":"123","product_name":"Name"}
        channel.invokeMethod("khaltiPaymentSuccess", data);
      }

      @Override
      public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
        Map <String, String> errorMessage= new HashMap<String, String>();
        errorMessage.put("action",action);
        errorMessage.put("message",errorMap.toString());
        channel.invokeMethod("khaltiPaymentError", errorMessage);
      }
    })
            .additionalData(customData);
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
