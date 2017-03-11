//
//  DataViewController.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/14/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class DataViewController: UIViewController {
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))

    
    override func viewDidLoad() {
        super.viewDidLoad()
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        let codedLabel:UILabel = UILabel()
        codedLabel.frame = CGRect(x: 100, y: 100, width: displayWidth, height: 200)
        codedLabel.textAlignment = .center
        codedLabel.text = "Under Construction! Check back later please."
        codedLabel.numberOfLines = 5
        codedLabel.textColor = blueColor
        codedLabel.font=UIFont.systemFont(ofSize: 40)
        codedLabel.backgroundColor=UIColor.clear
        
        self.view.addSubview(codedLabel)
        codedLabel.translatesAutoresizingMaskIntoConstraints = false
        codedLabel.heightAnchor.constraint(equalToConstant: 200).isActive = true
        codedLabel.widthAnchor.constraint(equalToConstant: displayWidth).isActive = true
        codedLabel.centerXAnchor.constraint(equalTo: codedLabel.superview!.centerXAnchor).isActive = true
        codedLabel.centerYAnchor.constraint(equalTo: codedLabel.superview!.centerYAnchor).isActive = true
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}
