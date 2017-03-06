//
//  NewScheduleItemForm.swift
//  TestMasterDetailApp
//
//  Created by Spiro Metaxas on 2/26/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class NewScheduleItemForm: UIViewController, UITextFieldDelegate, UIPickerViewDelegate, UIPickerViewDataSource {
    
    var TypeList = ["Medication", "Execise", "Food", "GlucoseLevel"]
    var typePickerView: UIPickerView = UIPickerView()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        let button2:UIButton = UIButton(frame: CGRect(x: 100, y: 500, width: 100, height: 50))
        button2.backgroundColor = .black
        button2.setTitle("Cancel", for: .normal)
        button2.addTarget(self, action:#selector(self.buttonClicked), for: .touchUpInside)
        self.view.addSubview(button2)
        
        view.addSubview(button)
        view.addSubview(textFieldUsername)
        view.addSubview(textFieldDescription)
        
        view.setNeedsUpdateConstraints()
        
        self.typePickerView.frame = CGRect(x: 100, y: 300, width: 100, height: 162)
        self.typePickerView.dataSource = self
        self.typePickerView.delegate = self
        self.typePickerView.backgroundColor = UIColor.blue
        self.typePickerView.layer.borderColor = UIColor.white.cgColor
        self.typePickerView.layer.borderWidth = 1
        self.view.addSubview(typePickerView)
        
    }
    
    public func numberOfComponents(in pickerView: UIPickerView) -> Int{
        return 1
        
    }
    
    public func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int{
        return TypeList.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        self.view.endEditing(true)
        return TypeList[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        print("Selected \(row)")
    }
    
    func buttonClicked() {
        print("Button Clicked")
        self.dismiss(animated: true, completion: nil);
    }
    
    lazy var button: UIButton! = {
        let view = UIButton()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.addTarget(self, action: #selector(NewScheduleItemForm.buttonPressed), for: .touchDown)
        view.setTitle("Create", for: .normal)
        view.backgroundColor = UIColor.blue
        return view
    }()
    
    lazy var textFieldUsername: UITextField! = {
        let view = UITextField()
        view.text = "Username"
        view.translatesAutoresizingMaskIntoConstraints = false
        view.borderStyle = .roundedRect
        return view
    }()
    
    lazy var textFieldDescription: UITextField! = {
        let view = UITextField()
        view.text = "Description"
        view.translatesAutoresizingMaskIntoConstraints = false
        view.borderStyle = .roundedRect
        return view
    }()
    
    func textFieldConstraintsUsername() {
        NSLayoutConstraint(
            item: textFieldUsername,
            attribute: .centerX,
            relatedBy: .equal,
            toItem: view,
            attribute: .centerX,
            multiplier: 1.0,
            constant: 0.0)
            .isActive = true
        
        NSLayoutConstraint(
            item: textFieldUsername,
            attribute: .width,
            relatedBy: .equal,
            toItem: view,
            attribute: .width,
            multiplier: 0.8,
            constant: 0.0)
            .isActive = true
        
        
        NSLayoutConstraint(
            item: textFieldUsername,
            attribute: .top,
            relatedBy: .equal,
            toItem: view,
            attribute: .bottom,
            multiplier: 0.1,
            constant: 0.0)
            .isActive = true
    }
    
    func textFieldConstraintsDescription() {
        NSLayoutConstraint(
            item: textFieldDescription,
            attribute: .centerX,
            relatedBy: .equal,
            toItem: view,
            attribute: .centerX,
            multiplier: 1.0,
            constant: 0.0)
            .isActive = true
        
        NSLayoutConstraint(
            item: textFieldDescription,
            attribute: .width,
            relatedBy: .equal,
            toItem: view,
            attribute: .width,
            multiplier: 0.8,
            constant: 0.0)
            .isActive = true
        
        
        NSLayoutConstraint(
            item: textFieldDescription,
            attribute: .top,
            relatedBy: .equal,
            toItem: view,
            attribute: .bottom,
            multiplier: 0.3,
            constant: 0.0)
            .isActive = true
    }
    
    func buttonConstraints() {
        // Center button in Page View
        NSLayoutConstraint(
            item: button,
            attribute: .centerX,
            relatedBy: .equal,
            toItem: view,
            attribute: .centerX,
            multiplier: 1.0,
            constant: 0.0)
            .isActive = true
        
        // Set Width to be 30% of the Page View Width
        NSLayoutConstraint(
            item: button,
            attribute: .width,
            relatedBy: .equal,
            toItem: view,
            attribute: .width,
            multiplier: 0.3,
            constant: 0.0)
            .isActive = true
        
        // Set Y Position Relative to Bottom of Page View
        NSLayoutConstraint(
            item: button,
            attribute: .bottom,
            relatedBy: .equal,
            toItem: view,
            attribute: .bottom,
            multiplier: 0.9,
            constant: 0.0)
            .isActive = true
    }
    
    override func updateViewConstraints() {
        textFieldConstraintsUsername()
        textFieldConstraintsDescription()
        buttonConstraints()
        super.updateViewConstraints()
    }
    
    func buttonPressed() {
        print("TEST")
        let jsonObject: [String: AnyObject] = [
            "name": textFieldUsername.text as AnyObject,
            "description": textFieldDescription.text as AnyObject,
            "type": typePickerView.selectedRow(inComponent: 0) as AnyObject
        ]
        let JSON = try? JSONSerialization.data(withJSONObject: jsonObject)
        print(String(data: JSON!, encoding: .utf8))

    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
}
