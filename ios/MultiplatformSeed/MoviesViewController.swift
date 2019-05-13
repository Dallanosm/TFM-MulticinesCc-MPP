//
//  ViewController.swift
//  MultiplatformSeed
//
//  Created by Daniel Llanos Muñoz on 02/02/2019.
//  Copyright © 2019 Daniel Llanos Muñoz. All rights reserved.
//

import UIKit
import ios

class MoviesViewController: UIViewController, MoviesView {

    lazy var presenter: MoviesPresenter = MoviesPresenter(
        view: self,
        errorHandler: IosErrorHandler())

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    func showProgress() {
        
    }
    
    func hideProgress() {
        
    }
    
    func showError(error: String) {
        
    }
    
    func showMessage(message: String) {
        
    }

}

