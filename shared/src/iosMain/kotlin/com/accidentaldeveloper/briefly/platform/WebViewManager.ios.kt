package com.accidentaldeveloper.briefly.platform

import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIApplication

class IosWebViewManager : WebViewManager {
    override fun open(url: String) {
        val nsUrl = platform.Foundation.NSURL.URLWithString(url) ?: return
        val safariVC = SFSafariViewController(nsUrl)

        val rootViewController = UIApplication.sharedApplication
            .keyWindow
            ?.rootViewController

        rootViewController?.presentViewController(safariVC, animated = true, completion = null)
    }
}