//
//  LogInViewController.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 3/10/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit

class LogInViewController: UIViewController {
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))

    var loginUsername: UITextField = UITextField()
    var loginPassword: UITextField = UITextField()
    
    var loginButton: UIButton = UIButton()
    var registerButton: UIButton = UIButton()

    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        view.backgroundColor = .white
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        loginUsername = UITextField(frame: CGRect(x: 0, y: barHeight + 50, width: displayWidth, height: 50))
        loginUsername.textAlignment = NSTextAlignment.center
        loginUsername.textColor = blueColor
        loginUsername.placeholder = "Log-In Username"
        loginUsername.borderStyle = UITextBorderStyle.line
        loginUsername.layer.borderWidth = 1
        loginUsername.layer.borderColor = blueColor.cgColor
        loginUsername.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(loginUsername)
        
        
        loginPassword = UITextField(frame: CGRect(x: 0, y: barHeight + 100, width: displayWidth, height: 50))
        loginPassword.textAlignment = NSTextAlignment.center
        loginPassword.textColor = blueColor
        loginPassword.placeholder = "Log-In Password"
        loginPassword.borderStyle = UITextBorderStyle.line
        loginPassword.layer.borderWidth = 1
        loginPassword.layer.borderColor = blueColor.cgColor
        loginPassword.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(loginPassword)
        
        // Register Button
        registerButton = UIButton(frame: CGRect(x: 0, y: barHeight + 170, width: displayWidth, height: 20))
        registerButton.setTitle("Click here to create an account!", for: UIControlState.normal)
        registerButton.setTitleColor(blueColor, for: UIControlState.normal)
        registerButton.backgroundColor = UIColor.clear
//        registerButton.layer.borderWidth = 1.0
//        let borderAlpha : CGFloat = 0.7
//        let cornerRadius : CGFloat = 5.0
//        registerButton.layer.borderColor = blueColor.cgColor
//        registerButton.layer.cornerRadius = cornerRadius
        registerButton.addTarget(self, action: #selector(registerButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(registerButton)
        
        // Log_In Button
        loginButton = UIButton(frame: CGRect(x: displayWidth/3, y: barHeight + 230, width: 100, height: 44))
        loginButton.setTitle("Log-In", for: UIControlState.normal)
        loginButton.setTitleColor(blueColor, for: UIControlState.normal)
        loginButton.backgroundColor = UIColor.clear
        loginButton.layer.borderWidth = 1.0
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        loginButton.layer.borderColor = blueColor.cgColor
        loginButton.layer.cornerRadius = cornerRadius
        loginButton.addTarget(self, action: #selector(loginButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(loginButton)


    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func loginButtonTapped(_ sender:UIBarButtonItem) {
        print(loginUsername.text!)
        print(loginPassword.text!)
        
        let text2 = loginUsername.text?.trimmingCharacters(in: .whitespaces)
        let text3 = loginPassword.text?.trimmingCharacters(in: .whitespaces)
        
        if ((text2?.isEmpty)!) {
            let alert = UIAlertController(title: "Alert", message: "Username Empty!", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        } else if ((text3?.isEmpty)!) {
            let alert = UIAlertController(title: "Alert", message: "Password Empty!", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        } else {
            // Send Stuff to DB
            
            
            
             // Save DB Response username
             dataLayer.storeTranscription(username: text2!)
            
            
            //Dismiss VC
            self.dismiss(animated: true, completion: nil)
            let mainStoryboard = UIStoryboard(name: "Main" , bundle: nil)
            self.present(mainStoryboard.instantiateInitialViewController()!, animated: true, completion: nil)
            
        }

    }
    
    func registerButtonTapped(_ sender:UIBarButtonItem) {
        print(loginUsername.text!)
        print(loginPassword.text!)
        
        // Go to register screen
        let thirdiewController:RegisterViewController = RegisterViewController()
        self.present(thirdiewController, animated: true, completion: nil)
    }

}
