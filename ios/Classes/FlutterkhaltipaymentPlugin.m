#import "FlutterkhaltipaymentPlugin.h"
#if __has_include(<flutterkhaltipayment/flutterkhaltipayment-Swift.h>)
#import <flutterkhaltipayment/flutterkhaltipayment-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutterkhaltipayment-Swift.h"
#endif

@implementation FlutterkhaltipaymentPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterkhaltipaymentPlugin registerWithRegistrar:registrar];
}
@end
