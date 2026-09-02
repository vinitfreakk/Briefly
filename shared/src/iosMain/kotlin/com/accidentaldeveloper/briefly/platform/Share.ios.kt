package com.accidentaldeveloper.briefly.platform

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.popoverPresentationController

class IosShare : Share {
    @OptIn(ExperimentalForeignApi::class)
    override fun share(link: String) {
        val activityViewController = UIActivityViewController(
            activityItems = listOf(link),
            applicationActivities = null
        )

        val rootViewController = UIApplication.sharedApplication
            .keyWindow
            ?.rootViewController

        rootViewController?.let { root ->
            activityViewController.popoverPresentationController?.apply {
                sourceView = root.view
                sourceRect = root.view.bounds
            }

            root.presentViewController(
                activityViewController,
                animated = true,
                completion = null
            )
        }
    }
}