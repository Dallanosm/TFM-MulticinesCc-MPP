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

    private lazy var presenter: MoviesPresenter = MoviesPresenter(
        executor: IosExecutor(),
        repository: CommonRepository(local: IosLocalDataSource(), remote: CommonRemoteDataSource()),
        view: self,
        errorHandler: IosErrorHandler())

    override func viewDidLoad() {
        super.viewDidLoad()
        self.presenter.initialize()
    }
    
    func showProgress() {
        
    }
    
    func hideProgress() {
        
    }
    
    func showError(error: String) {
        
    }
    
    func showMessage(message: String) {
        
    }
    
    func showMovies(movies: [Movie]) {
        print(movies)
    }

}

