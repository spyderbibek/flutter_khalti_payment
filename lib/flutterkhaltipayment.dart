
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

class FlutterKhaltiPayment {
  static const MethodChannel _channel =
      const MethodChannel('bksapps/flutterkhaltipayment');
  String _merchantKey, _productId, _productName, _productUrl, _urlSchemeIOS;
  double _amount;
  bool _enableEBanking, _enableMobileBanking, _enableIPS, _enableSCT;


  FlutterKhaltiPayment({
    @required String merchantKey,
    @required String productId,
    @required String productName,
    String productUrl="",
    @required double amount,
    @required String urlSchemeIOS,
    bool enableEBanking=false,
    bool enableMobileBanking=false,
    bool enableIPS=false,
    bool enableSCT=false

  }):this._merchantKey=merchantKey,
        this._productId=productId,
        this._productName=productName,
        this._productUrl=productUrl,
        this._amount=amount,
        this._urlSchemeIOS = urlSchemeIOS,
        this._enableEBanking = enableEBanking,
        this._enableMobileBanking = enableMobileBanking,
        this._enableIPS = enableIPS,
        this._enableSCT=enableSCT;

  initPayment({Function onSuccess, Function onError}){
    final Map<String, dynamic> params = <String, dynamic>{
      "merchantKey": _merchantKey,
      "productId": _productId,
      "productName": _productName,
      "productUrl": _productUrl,
      "urlSchemeIOS": _urlSchemeIOS,
      "amount": _amount,
      "enableEbanking": _enableEBanking,
      "enableMobileBanking": _enableMobileBanking,
      "enableIPS": _enableIPS,
      "enableSCT": _enableSCT,
    };
    _channel.invokeMethod("khaltiPaymentStart", params);
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
