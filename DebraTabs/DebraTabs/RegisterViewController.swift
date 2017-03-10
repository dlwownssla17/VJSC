//
//  RegisterViewController.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 3/10/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit

class RegisterViewController: UIViewController {

    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var registerUsername: UITextField = UITextField()
    var registerPassword: UITextField = UITextField()
    var registerPasswordCheck: UITextField = UITextField()
    
    var registerButton: UIButton = UIButton()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        view.backgroundColor = .white
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        registerUsername = UITextField(frame: CGRect(x: 0, y: barHeight + 50, width: displayWidth, height: 50))
        registerUsername.textAlignment = NSTextAlignment.center
        registerUsername.textColor = blueColor
        registerUsername.placeholder = "Register Username"
        registerUsername.borderStyle = UITextBorderStyle.line
        registerUsername.layer.borderWidth = 1
        registerUsername.layer.borderColor = blueColor.cgColor
        registerUsername.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(registerUsername)
        
        registerPassword = UITextField(frame: CGRect(x: 0, y: barHeight + 100, width: displayWidth, height: 50))
        registerPassword.textAlignment = NSTextAlignment.center
        registerPassword.textColor = blueColor
        registerPassword.placeholder = "Register Password"
        registerPassword.borderStyle = UITextBorderStyle.line
        registerPassword.layer.borderWidth = 1
        registerPassword.layer.borderColor = blueColor.cgColor
        registerPassword.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(registerPassword)
        
        registerPasswordCheck = UITextField(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 50))
        registerPasswordCheck.textAlignment = NSTextAlignment.center
        registerPasswordCheck.textColor = blueColor
        registerPasswordCheck.placeholder = "Verify Password"
        registerPasswordCheck.borderStyle = UITextBorderStyle.line
        registerPasswordCheck.layer.borderWidth = 1
        registerPasswordCheck.layer.borderColor = blueColor.cgColor
        registerPasswordCheck.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(registerPasswordCheck)
        
        // Register Button
        registerButton = UIButton(frame: CGRect(x: displayWidth/3, y: barHeight + 230, width: 100, height: 44))
        registerButton.setTitle("Register", for: UIControlState.normal)
        registerButton.setTitleColor(blueColor, for: UIControlState.normal)
        registerButton.backgroundColor = UIColor.clear
        registerButton.layer.borderWidth = 1.0
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        registerButton.layer.borderColor = blueColor.cgColor
        registerButton.layer.cornerRadius = cornerRadius
        registerButton.setTitleColor(UIColor.gray, for: .disabled)
        registerButton.setTitleShadowColor(UIColor.gray, for: .disabled)
        registerButton.addTarget(self, action: #selector(registerButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(registerButton)
        
//        registerButton.isEnabled = false
//        registerUsername.addTarget(self, action: #selector(editingChanged(_:)), for: .editingChanged)
//        registerPassword.addTarget(self, action: #selector(editingChanged(_:)), for: .editingChanged)
//        registerPasswordCheck.addTarget(self, action: #selector(editingChanged(_:)), for: .editingChanged)
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func registerButtonTapped(_ sender:UIBarButtonItem) {
        print(registerUsername.text!)
        print(registerPassword.text!)
        
        let text2 = registerPassword.text?.trimmingCharacters(in: .whitespaces)
        let text3 = registerPasswordCheck.text?.trimmingCharacters(in: .whitespaces)
        
        if let text = registerUsername.text?.trimmingCharacters(in: .whitespaces), text.isEmpty {
            let alert = UIAlertController(title: "Alert", message: "Username Empty!", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        } else if (text2 != text3) {
            let alert = UIAlertController(title: "Alert", message: "Password's Do Not Match!", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        } else if ((text2?.isEmpty)! || (text3?.isEmpty)!) {
            let alert = UIAlertController(title: "Alert", message: "Password Empty!", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        } else {
             // Send Stuff to DB
            
            
            //Dismiss VC
            self.dismiss(animated: true, completion: nil)

        }
        
    }
    
}
