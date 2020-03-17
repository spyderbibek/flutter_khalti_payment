import 'package:flutter/material.dart';
import 'package:flutterkhaltipayment/flutterkhaltipayment.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
   @override
  void initState() {
    super.initState();
  }
  _payViaKhalti() {
    FlutterKhaltiPayment(
      urlSchemeIOS: "KhaltiPayFlutterExampleScheme",
      merchantKey: "YOUR MERCHANT KEY HERE",
      productId: "0123",
      productName: "Product Name",
      amount: 1000,
      enableEBanking: false,
      enableIPS: false,
      enableMobileBanking: false,
      enableSCT: false,
      productUrl: "http://www.example.com/"
    ).initPayment(
      onSuccess: (data) {
        print("Token Got: ${data["token"].toString()}");
        print("Success got: ${data.toString()}");
      },
      onError: (error) {
        print("error");
        print(error);
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: FlatButton(
            color: Colors.purple,
            child: Text("Pay via khalti", style: TextStyle(color: Colors.white),),
            onPressed: () {
              _payViaKhalti();
            },
          ),
        ),
      ),
    );
  }
}
