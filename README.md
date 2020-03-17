# flutterkhaltipayment
[![flutterkhaltipayment pub package](https://img.shields.io/badge/pub-v0.4.0-brightgreen)(https://pub.dev/packages/flutterkhaltipayment)

An unofficial Khalti SDK that can be used by Khalti merchants to integrate Khalti payment on their flutter apps.

> Supported Platforms
> - Android
> - iOS (Soon)

## Usage

```yaml
# add this line to your dependencies
dependencies:
  flutterkhaltipayment: ^0.4.0
```

```dart
import 'package:flutterkhaltipayment/flutterkhaltipayment.dart';  
```

```dart
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
```

| property           | description                                              |
| -------------------|----------------------------------------------------------|
| merchantKey        | String (Not Null)(required)                              |
| productId          | String (Not Null) (required)                             |
| productName        | String (Not Null) (required)                             |
| amount             | double (Not Null) (required)                             |
| enableEBanking     | boolean (optional)                                       |
| enableIPS          | boolean (optional)                                       |
| enableMobileBanking| boolean (optional)                                       |
| enableSCT          | boolean (optional)                                       |
| productUrl         | String (Nullable) (optional)                             |

## If you need any features suggest
