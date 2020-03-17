# flutterkhaltipayment

An unofficial Khalti SDK that can be used by Khalti merchants to integrate Khalti payment on their flutter apps.

> Supported Platforms
> - Android
> - iOS (Soon)

## Usage

```yaml
# add this line to your dependencies
dependencies:
  flutterkhaltipayment: ^0.2.0
```

```dart
import 'package:flutterkhaltipayment/flutterkhaltipayment.dart';  
```

```dart
    FlutterKhaltiPayment(
      urlSchemeIOS: "KhaltiPayFlutterExampleScheme",
      merchantKey: "YOUR MERCHANT KEY HERE",
      productId: "012",
      productName: "Product Name",
      amount: 1000,
      customData: {
        "extra data": "extra data",
      },
    ).initPayment(
      onSuccess: (data) {
        print(data);
      },
      onError: (error) {
        print(error);
      },
    );
```

| property        | description                                                        |
| --------------- | ------------------------------------------------------------------ |
| merchantKey     | String (Not Null)(required)                                        |
| productId       | String (Not Null) (required)                                       |
| productName     | String (Not Null) (required)                                       |
| amount          | double (Not Null) (required)                                       |
| customData      | Map<String, String> (Not Null) (required)                          |

## If you need any features suggest
