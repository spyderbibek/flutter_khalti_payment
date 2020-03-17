import 'package:flutter/material.dart';
import 'package:flutterkhaltipayment/flutterkhaltipayment.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
  }
  _payViaKhalti() {
    FlutterKhaltiPayment(
      urlSchemeIOS: "KhaltiPayFlutterExampleScheme",
      merchantKey: "test_public_key_3793047616c14a108d09ddbaa551333f",
      productId: "0123",
      productName: "Product Name",
      amount: 100,
      customData: {
        "studentId": "123456",
      },
    ).initPayment(
      onSuccess: (data) {
        print("success");
        print(data);
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
