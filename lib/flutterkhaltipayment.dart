
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

class FlutterKhaltiPayment {
  static const MethodChannel _channel =
      const MethodChannel('bksapps/flutterkhaltipayment');
  String _merchantKey, _productId, _productName, _productUrl, _urlSchemeIOS;
  double _amount;
  Map<String, String> _customData;


  FlutterKhaltiPayment({
    @required String merchantKey,
    @required String productId,
    @required String productName,
    String productUrl="",
    @required double amount,
    @required String urlSchemeIOS,
    Map<String, String> customData= const {}
  }):this._merchantKey=merchantKey,
        this._productId=productId,
        this._productName=productName,
        this._productUrl=productUrl,
        this._amount=amount,
        this._urlSchemeIOS = urlSchemeIOS,
        this._customData=customData;

  initPayment({Function onSuccess, Function onError}){
    _channel.invokeMethod("khaltiPaymentStart", {
      "merchantKey": _merchantKey,
      "productId": _productId,
      "productName": _productName,
      "productUrl": _productUrl,
      "urlSchemeIOS": _urlSchemeIOS,
      "amount": _amount,
      "customData": _customData,
    });
    _listenToResponse(onSuccess, onError);
  }

  _listenToResponse(Function onSuccess, Function onError) {
    _channel.setMethodCallHandler((call) {
      switch (call.method) {
        case "khaltiPaymentSuccess":
          return onSuccess(call.arguments);
          break;
        case "khaltiPaymentError":
          return onError(call.arguments);
          break;
        default:
          return null;
      }
    });
  }
}
