import SwiftUI
import Shared

@main
struct iOSApp: App {
    init(){
        IosModuleKt.startAppKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}