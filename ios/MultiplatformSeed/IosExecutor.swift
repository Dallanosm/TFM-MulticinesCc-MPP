//
//  IosExecutor.swift
//  MultiplatformSeed
//
//  Created by Daniel Llanos Muñoz on 23/06/2019.
//  Copyright © 2019 Daniel Llanos Muñoz. All rights reserved.
//

import Foundation
import ios

class IosExecutor: NSObject, Executor {
    var main: Kotlinx_coroutines_core_nativeCoroutineDispatcher = UI()
}

public class UI: Kotlinx_coroutines_core_nativeCoroutineDispatcher {
    override public func dispatch(context: KotlinCoroutineContext, block: Kotlinx_coroutines_core_nativeRunnable) {
        DispatchQueue.main.async {
            block.run()
        }
    }
}

