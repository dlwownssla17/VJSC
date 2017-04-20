//
//  HelpPage.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 4/19/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class HelpPage:UIViewController {
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var fitBitButton: UIButton = UIButton()
    var cancelButton: UIButton = UIButton()
    
    let pageTitle:UILabel = UILabel()
    let helpLabel:UILabel = UILabel()
    
    let pageTitleText:String = "Debra Usage"
    let cancelButtonText:String = "OK"
    let helpText:String = "HELP!!!"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        
        pageTitle.frame = CGRect(x: self.view.center.x / 2, y: 10, width: displayWidth, height: 100)
        pageTitle.center.x = self.view.center.x
        pageTitle.textAlignment = .center
        pageTitle.text = self.pageTitleText
        pageTitle.numberOfLines = 5
        pageTitle.textColor = blueColor
        pageTitle.font=UIFont.systemFont(ofSize: 30)
        pageTitle.backgroundColor=UIColor.clear
        self.view.addSubview(pageTitle)
        
        helpLabel.frame = CGRect(x: self.view.center.x / 2, y: 50, width: displayWidth, height: displayHeight - 200)
        helpLabel.center.x = self.view.center.x
        helpLabel.textAlignment = .center
        helpLabel.text = self.helpText
        helpLabel.numberOfLines = 5
        helpLabel.textColor = blueColor
        helpLabel.font=UIFont.systemFont(ofSize: 30)
        helpLabel.backgroundColor=UIColor.clear
        self.view.addSubview(helpLabel)
        
        cancelButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 75, width: 300, height: 44))
        cancelButton.center.x = self.view.center.x
        cancelButton.setTitle(self.cancelButtonText, for: UIControlState.normal)
        cancelButton.setTitleColor(blueColor, for: UIControlState.normal)
        cancelButton.backgroundColor = UIColor.clear
        cancelButton.layer.borderWidth = 1.0
        cancelButton.layer.borderColor = blueColor.cgColor
        cancelButton.layer.cornerRadius = cornerRadius
        cancelButton.addTarget(self, action: #selector(cancelButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(cancelButton)
        
    }
    
    func cancelButtonPressed(_ button: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
}
