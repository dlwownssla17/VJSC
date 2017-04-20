//
//  CreateNewTeam.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/31/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class CreateNewTeam:UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
    
    var teamNameField: UITextField = UITextField()
    
    let screenTitle:String = "Create New Team"
    let cancelLabel:String = "Cancel"
    let createLabel:String = "Create"
    
    let pageTitle:UILabel = UILabel()
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var createTeamButton: UIButton = UIButton()
    var cancelButton: UIButton = UIButton()
    
    var maxNumberMembersPicker: UIPickerView! = UIPickerView()
    var maxNumberMembersValues = ["Select:","2","3","4","5", "6", "7", "8", "9", "10", "11", "12", "Unlimited"]
    var maxNumberMembersField:UITextField = UITextField()
    var selectedDuration:String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        
        teamNameField = UITextField(frame: CGRect(x: 0, y: barHeight * 5, width: displayWidth, height: 50))
        teamNameField.textAlignment = NSTextAlignment.center
        teamNameField.textColor = blueColor
        teamNameField.placeholder = "Team Name"
        teamNameField.borderStyle = UITextBorderStyle.line
        teamNameField.layer.borderWidth = 1
        teamNameField.layer.borderColor = blueColor.cgColor
        self.view.addSubview(teamNameField)
        
        pageTitle.frame = CGRect(x: self.view.center.x / 2, y: 10, width: displayWidth, height: 100)
        pageTitle.center.x = self.view.center.x
        pageTitle.textAlignment = .center
        pageTitle.text = self.screenTitle
        pageTitle.numberOfLines = 5
        pageTitle.textColor = blueColor
        pageTitle.font=UIFont.systemFont(ofSize: 30)
        pageTitle.backgroundColor=UIColor.clear
        
        self.view.addSubview(pageTitle)
        
        cancelButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 75, width: 300, height: 44))
        cancelButton.center.x = self.view.center.x
        cancelButton.setTitle(self.cancelLabel, for: UIControlState.normal)
        cancelButton.setTitleColor(blueColor, for: UIControlState.normal)
        cancelButton.backgroundColor = UIColor.clear
        cancelButton.layer.borderWidth = 1.0
        cancelButton.layer.borderColor = blueColor.cgColor
        cancelButton.layer.cornerRadius = cornerRadius
        cancelButton.addTarget(self, action: #selector(cancelButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(cancelButton)
        
        createTeamButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 175, width: 300, height: 44))
        createTeamButton.center.x = self.view.center.x
        createTeamButton.setTitle(self.createLabel, for: UIControlState.normal)
        createTeamButton.setTitleColor(blueColor, for: UIControlState.normal)
        createTeamButton.backgroundColor = UIColor.clear
        createTeamButton.layer.borderWidth = 1.0
        createTeamButton.layer.borderColor = blueColor.cgColor
        createTeamButton.layer.cornerRadius = cornerRadius
        createTeamButton.addTarget(self, action: #selector(createTeamButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(createTeamButton)
        
        maxNumberMembersPicker = UIPickerView()
        maxNumberMembersPicker.delegate = self
        maxNumberMembersPicker.dataSource = self
        
        maxNumberMembersField = UITextField(frame: CGRect(x: 0, y: barHeight * 8, width: displayWidth, height: 50))
        maxNumberMembersField.textAlignment = NSTextAlignment.center
        maxNumberMembersField.textColor = blueColor
        maxNumberMembersField.placeholder = "Max Number of Members"
        maxNumberMembersField.borderStyle = UITextBorderStyle.line
        maxNumberMembersField.layer.borderWidth = 1
        maxNumberMembersField.layer.borderColor = blueColor.cgColor
        self.maxNumberMembersField.inputView = self.maxNumberMembersPicker
        self.view.addSubview(maxNumberMembersField)
        
        let toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.default
        toolBar.isTranslucent = true
        toolBar.tintColor = blueColor
        toolBar.sizeToFit()
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItemStyle.plain, target: self, action: #selector(doneButtonTapped(_:)))
        toolBar.setItems([doneButton], animated: false)
        toolBar.isUserInteractionEnabled = true
        maxNumberMembersField.inputAccessoryView = toolBar
        
        
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func cancelButtonPressed(_ button: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func createTeamButtonPressed(_ button: UIButton) {
        if (teamNameField.text?.isEmpty)! {
            let message:String = "Please fill in the Team Name."
            let emptyNameAction = UIAlertController(title: "Empty Team Name", message: message, preferredStyle: UIAlertControllerStyle.alert)
            emptyNameAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(emptyNameAction, animated: true, completion: nil)
            return
        }
        
        if (maxNumberMembersField.text?.isEmpty)! {
            let message:String = "Please fill in the maximum number of people on a team."
            let emptyMaxNumberField = UIAlertController(title: "Empty Team Member Number", message: message, preferredStyle: UIAlertControllerStyle.alert)
            emptyMaxNumberField.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(emptyMaxNumberField, animated: true, completion: nil)
            return
        }
        print("Selected: \(maxNumberMembersField.text)")
        var maxTeamSize:Int = 0
        if maxNumberMembersField.text! == "Unlimited" {
            maxTeamSize = -1
        } else {
            maxTeamSize = Int(maxNumberMembersField.text!)!
        }
        let teamName:String = self.teamNameField.text!
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
        let parameters = Requests.createNewTeam(name: teamName, size: maxTeamSize)
        Alamofire.request(Settings.getCreateTeamURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers).validate().responseString { response in
            switch response.result {
            case .success(let data):
                //let json = JSON(data)
                print("Created New Team")
                self.dismiss(animated: true, completion: nil)
            case .failure(let error):
                if (response.response?.statusCode == 401) {
                    let message:String = "Team with name " + teamName + " already exists."
                    let joinAction = UIAlertController(title: "Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
                    joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                        return
                    }))
                    self.present(joinAction, animated: true, completion: nil)
                    print("Request failed with error: \(error)")
                } else {
                    let message:String = "Could not create team"
                    let joinAction = UIAlertController(title: "Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
                    joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                    return
                    }))
                    self.present(joinAction, animated: true, completion: nil)
                    print("Request failed with error: \(error)")
                }
            }
        }
    }
    
    func doneButtonTapped(_ sender:UIBarButtonItem) {
        maxNumberMembersField.resignFirstResponder()
        
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return self.maxNumberMembersValues.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return self.maxNumberMembersValues[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if (row == 0) {
            maxNumberMembersPicker.selectRow(row + 1, inComponent:component, animated:true)
            maxNumberMembersField.text = self.maxNumberMembersValues[1]
        } else {
            maxNumberMembersField.text = self.maxNumberMembersValues[row]
        }
    }

}
