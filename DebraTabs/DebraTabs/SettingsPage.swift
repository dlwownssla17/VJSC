//
//  SettingsPage.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/5/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class SettingsPage:UIViewController {
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var fitBitButton: UIButton = UIButton()
    var cancelButton: UIButton = UIButton()
    
    var AMPMButton = UIButton()
    var MilitaryTimeButton = UIButton()
    
    let pageTitle:UILabel = UILabel()
    
    let pageTitleText:String = "App Settings"
    let fitBitButttonText:String = "Sync With FitBit"
    let cancelButtonText:String = "Cancel"
    
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
        
        fitBitButton = UIButton(frame: CGRect(x: displayWidth/2, y: 200, width: 300, height: 44))
        fitBitButton.center.x = self.view.center.x
        fitBitButton.setTitle(self.fitBitButttonText, for: UIControlState.normal)
        fitBitButton.setTitleColor(blueColor, for: UIControlState.normal)
        fitBitButton.backgroundColor = UIColor.clear
        fitBitButton.layer.borderWidth = 1.0
        fitBitButton.layer.borderColor = blueColor.cgColor
        fitBitButton.layer.cornerRadius = cornerRadius
        fitBitButton.addTarget(self, action: #selector(fitBitButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(fitBitButton)
        
        let center = self.view.frame.width / 2
        AMPMButton = UIButton(frame: CGRect(x: center - 125, y: 310, width: 100, height: 50))
        AMPMButton.backgroundColor = .clear
        AMPMButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: 100, height: 50)), for: .selected)
        AMPMButton.layer.borderColor = blueColor.cgColor
        AMPMButton.layer.borderWidth = 1
        AMPMButton.setTitle("AM/PM", for: .normal)
        AMPMButton.setTitleColor(blueColor, for: .normal)
        AMPMButton.setTitleColor(.white, for: .selected)
        AMPMButton.addTarget(self, action:#selector(self.AMPMButtonAction), for: .touchUpInside)
        AMPMButton.isHidden = false
        self.view.addSubview(AMPMButton)
        
        MilitaryTimeButton = UIButton(frame: CGRect(x: center + 25, y: 310, width: 100, height: 50))
        MilitaryTimeButton.backgroundColor = .clear
        MilitaryTimeButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: 100, height: 50)), for: .selected)
        MilitaryTimeButton.layer.borderColor = blueColor.cgColor
        MilitaryTimeButton.layer.borderWidth = 1
        MilitaryTimeButton.setTitle("Military", for: .normal)
        MilitaryTimeButton.setTitleColor(blueColor, for: .normal)
        MilitaryTimeButton.setTitleColor(.white, for: .selected)
        MilitaryTimeButton.addTarget(self, action:#selector(self.militaryTimeButtonAction), for: .touchUpInside)
        MilitaryTimeButton.isHidden = false
        self.view.addSubview(MilitaryTimeButton)
        
        if Settings.displayAMPM {
            AMPMButton.isSelected = true
            MilitaryTimeButton.isSelected = false
        } else {
            AMPMButton.isSelected = false
            MilitaryTimeButton.isSelected = true
        }
        
    }
    
    func cancelButtonPressed(_ button: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func fitBitButtonPressed(_ button: UIButton) {
        if let url = URL(string: Settings.getFitBitURL(username: Settings.usernameString)) {
            UIApplication.shared.open(url, options: [:])
        }
    }
    
    func getImageWithColor(color: UIColor, size: CGSize) -> UIImage
    {
        let rect = CGRect(origin: CGPoint(x: 0, y: 0), size: CGSize(width: size.width, height: size.height))
        UIGraphicsBeginImageContextWithOptions(size, false, 0)
        color.setFill()
        UIRectFill(rect)
        let image: UIImage = UIGraphicsGetImageFromCurrentImageContext()!
        UIGraphicsEndImageContext()
        return image
    }
    
    func AMPMButtonAction(sender: UIButton!) {
        AMPMButton.isSelected = true
        MilitaryTimeButton.isSelected = false
        Settings.displayAMPM = true
    }
    
    func militaryTimeButtonAction(sender: UIButton!) {
        AMPMButton.isSelected = false
        MilitaryTimeButton.isSelected = true
        Settings.displayAMPM = false
    }
    
    
    
}
