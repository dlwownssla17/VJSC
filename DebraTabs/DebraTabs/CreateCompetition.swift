//
//  CreateCompetition.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/5/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class CreateCompetition:UIViewController {//, UIPickerViewDelegate, UIPickerViewDataSource {
    
    public var teamID:Int = 0
    public var presentingView:SetCompetitionPage = SetCompetitionPage()
    
    var competitionNameField: UITextField = UITextField()
    var otherTeamNameField: UITextField = UITextField()
    var colorRedButton = UIButton()
    var colorBlueButton = UIButton()
    var showMembersButton = UIButton()
    var hideMembersButton = UIButton()
    //var teamColorNameField: UITextField = UITextField()
    //var showTeamMembersNameField: UITextField = UITextField()
    
    let screenTitle:String = "Create New Competition"
    let cancelLabel:String = "Cancel"
    let createLabel:String = "Create"
    let teamColorLabel:String = "Your Color:"
    let displayMembersLabel:String = "Show Members:"
    
    let pageTitle:UILabel = UILabel()
    let teamColorText:UILabel = UILabel()
    let displayMembersText:UILabel = UILabel()
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var createCompetitionButton: UIButton = UIButton()
    var cancelButton: UIButton = UIButton()
    
    var startTimePicker: UIDatePicker! = UIDatePicker()
    var scheduleStartTime: UITextField = UITextField()
    
    var endTimePicker: UIDatePicker! = UIDatePicker()
    var scheduleEndTime: UITextField = UITextField()
    
    var showMembers:Bool = false
    var color:String = ""
    var showMembersSelected:Bool = false
    var colorSelected:Bool = false
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        
        competitionNameField = UITextField(frame: CGRect(x: 0, y: barHeight * 5, width: displayWidth, height: 50))
        competitionNameField.textAlignment = NSTextAlignment.center
        competitionNameField.textColor = blueColor
        competitionNameField.placeholder = "Competition Name"
        competitionNameField.borderStyle = UITextBorderStyle.line
        competitionNameField.layer.borderWidth = 1
        competitionNameField.layer.borderColor = blueColor.cgColor
        self.view.addSubview(competitionNameField)
        
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
        
        createCompetitionButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 175, width: 300, height: 44))
        createCompetitionButton.center.x = self.view.center.x
        createCompetitionButton.setTitle(self.createLabel, for: UIControlState.normal)
        createCompetitionButton.setTitleColor(blueColor, for: UIControlState.normal)
        createCompetitionButton.backgroundColor = UIColor.clear
        createCompetitionButton.layer.borderWidth = 1.0
        createCompetitionButton.layer.borderColor = blueColor.cgColor
        createCompetitionButton.layer.cornerRadius = cornerRadius
        createCompetitionButton.addTarget(self, action: #selector(createCompetitionButton(_:)), for: .touchDown)
        self.view.addSubview(createCompetitionButton)
        
        
        let toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.default
        toolBar.isTranslucent = true
        toolBar.tintColor = blueColor
        toolBar.sizeToFit()
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItemStyle.plain, target: self, action: #selector(doneButtonTapped(_:)))
        toolBar.setItems([doneButton], animated: false)
        toolBar.isUserInteractionEnabled = true
        //maxNumberMembersField.inputAccessoryView = toolBar
        
        // Start Time Picker
        startTimePicker = UIDatePicker()
        startTimePicker.timeZone = NSTimeZone.local
        startTimePicker.addTarget(self, action: #selector(datePickerValueChanged(_:)), for: .valueChanged)
        startTimePicker.datePickerMode = UIDatePickerMode.date
        
        let startDateFormatter: DateFormatter = DateFormatter()
        startDateFormatter.dateFormat = "yyyy-MM-dd"
        var currentTime:Date = Date()
        var todayString:String = startDateFormatter.string(from: currentTime)
        let startDateFormatter2: DateFormatter = DateFormatter()
        startDateFormatter2.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let startTimeDate: Date = startDateFormatter2.date(from: todayString + " 00:00:00")!
        startTimePicker.date = startTimeDate
        startTimePicker.minimumDate = startTimeDate
        
        scheduleStartTime = UITextField(frame: CGRect(x: 0, y: 180, width: displayWidth, height: 50))
        scheduleStartTime.textAlignment = NSTextAlignment.center
        scheduleStartTime.textColor = blueColor
        scheduleStartTime.placeholder = "Starting At What Time?"
        scheduleStartTime.borderStyle = UITextBorderStyle.line
        scheduleStartTime.layer.borderWidth = 1
        scheduleStartTime.layer.borderColor = blueColor.cgColor
        self.scheduleStartTime.inputView = self.startTimePicker
        scheduleStartTime.isHidden = false
        scheduleStartTime.inputAccessoryView = toolBar
        self.view.addSubview(scheduleStartTime)
        
        
        
        // End Time Picker
        endTimePicker = UIDatePicker()
        endTimePicker.timeZone = NSTimeZone.local
        endTimePicker.addTarget(self, action: #selector(datePickerValueChanged(_:)), for: .valueChanged)
        endTimePicker.datePickerMode = UIDatePickerMode.date
        
        var endDateFormatter: DateFormatter = DateFormatter()
        endDateFormatter.dateFormat = "yyyy-MM-dd"
        todayString = endDateFormatter.string(from: currentTime)
        let endDateFormatter2: DateFormatter = DateFormatter()
        endDateFormatter2.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let endTimeDate: Date = endDateFormatter2.date(from: todayString + " 00:00:00")!
        endTimePicker.date = endTimeDate
        endTimePicker.minimumDate = endTimeDate
        
        scheduleEndTime = UITextField(frame: CGRect(x: 0, y: 260, width: displayWidth, height: 50))
        scheduleEndTime.textAlignment = NSTextAlignment.center
        scheduleEndTime.textColor = blueColor
        scheduleEndTime.placeholder = "Ending At What Time?"
        scheduleEndTime.borderStyle = UITextBorderStyle.line
        scheduleEndTime.layer.borderWidth = 1
        scheduleEndTime.layer.borderColor = blueColor.cgColor
        self.scheduleEndTime.inputView = self.endTimePicker
        scheduleEndTime.isHidden = false
        scheduleEndTime.inputAccessoryView = toolBar
        self.view.addSubview(scheduleEndTime)
        
        otherTeamNameField = UITextField(frame: CGRect(x: 0, y: 340, width: displayWidth, height: 50))
        otherTeamNameField.textAlignment = NSTextAlignment.center
        otherTeamNameField.textColor = blueColor
        otherTeamNameField.placeholder = "Other Team Name"
        otherTeamNameField.borderStyle = UITextBorderStyle.line
        otherTeamNameField.layer.borderWidth = 1
        otherTeamNameField.layer.borderColor = blueColor.cgColor
        self.view.addSubview(otherTeamNameField)
        
        teamColorText.frame = CGRect(x: 10, y: 420, width: 100, height: 50)
        teamColorText.textAlignment = .left
        teamColorText.center.y = 445
        teamColorText.text = self.teamColorLabel
        teamColorText.numberOfLines = 5
        teamColorText.textColor = blueColor
        teamColorText.font=UIFont.systemFont(ofSize: 18)
        teamColorText.backgroundColor=UIColor.clear
        self.view.addSubview(teamColorText)
        
        colorRedButton = UIButton(frame: CGRect(x: 150, y: 420, width: 100, height: 50))
        colorRedButton.backgroundColor = .clear
        colorRedButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: 100, height: 50)), for: .selected)
        colorRedButton.layer.borderColor = blueColor.cgColor
        colorRedButton.layer.borderWidth = 1
        colorRedButton.setTitle("Red", for: .normal)
        colorRedButton.setTitleColor(blueColor, for: .normal)
        colorRedButton.setTitleColor(.white, for: .selected)
        colorRedButton.addTarget(self, action:#selector(self.redButtonAction), for: .touchUpInside)
        colorRedButton.isHidden = false
        
        self.view.addSubview(colorRedButton)
        
        colorBlueButton = UIButton(frame: CGRect(x: 275, y: 420, width: 100, height: 50))
        colorBlueButton.backgroundColor = .clear
        colorBlueButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: 100, height: 50)), for: .selected)
        colorBlueButton.layer.borderColor = blueColor.cgColor
        colorBlueButton.layer.borderWidth = 1
        colorBlueButton.setTitle("Blue", for: .normal)
        colorBlueButton.setTitleColor(blueColor, for: .normal)
        colorBlueButton.setTitleColor(.white, for: .selected)
        colorBlueButton.addTarget(self, action:#selector(self.buleButtonAction), for: .touchUpInside)
        colorBlueButton.isHidden = false
        
        self.view.addSubview(colorBlueButton)
        
        displayMembersText.frame = CGRect(x: 10, y: 490, width: 150, height: 50)
        displayMembersText.textAlignment = .left
        displayMembersText.center.y = 515
        displayMembersText.text = self.displayMembersLabel
        displayMembersText.numberOfLines = 5
        displayMembersText.textColor = blueColor
        displayMembersText.font=UIFont.systemFont(ofSize: 18)
        displayMembersText.backgroundColor=UIColor.clear
        self.view.addSubview(displayMembersText)
        
        
        showMembersButton = UIButton(frame: CGRect(x: 150, y: 490, width: 100, height: 50))
        showMembersButton.backgroundColor = .clear
        showMembersButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: 100, height: 50)), for: .selected)
        showMembersButton.layer.borderColor = blueColor.cgColor
        showMembersButton.layer.borderWidth = 1
        showMembersButton.setTitle("Show", for: .normal)
        showMembersButton.setTitleColor(blueColor, for: .normal)
        showMembersButton.setTitleColor(.white, for: .selected)
        showMembersButton.addTarget(self, action:#selector(self.showMembersButtonAction), for: .touchUpInside)
        showMembersButton.isHidden = false
        
        self.view.addSubview(showMembersButton)
        
        hideMembersButton = UIButton(frame: CGRect(x: 275, y: 490, width: 100, height: 50))
        hideMembersButton.backgroundColor = .clear
        hideMembersButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: 100, height: 50)), for: .selected)
        hideMembersButton.layer.borderColor = blueColor.cgColor
        hideMembersButton.layer.borderWidth = 1
        hideMembersButton.setTitle("Hide", for: .normal)
        hideMembersButton.setTitleColor(blueColor, for: .normal)
        hideMembersButton.setTitleColor(.white, for: .selected)
        hideMembersButton.addTarget(self, action:#selector(self.hideMembersButtonAction), for: .touchUpInside)
        hideMembersButton.isHidden = false
        
        self.view.addSubview(hideMembersButton)
        
        
        
        
//        teamColorNameField = UITextField(frame: CGRect(x: 0, y: 420, width: displayWidth, height: 50))
//        teamColorNameField.textAlignment = NSTextAlignment.center
//        teamColorNameField.textColor = blueColor
//        teamColorNameField.placeholder = "Team Color"
//        teamColorNameField.borderStyle = UITextBorderStyle.line
//        teamColorNameField.layer.borderWidth = 1
//        teamColorNameField.layer.borderColor = blueColor.cgColor
//        self.view.addSubview(teamColorNameField)
//        
//        showTeamMembersNameField = UITextField(frame: CGRect(x: 0, y: 500, width: displayWidth, height: 50))
//        showTeamMembersNameField.textAlignment = NSTextAlignment.center
//        showTeamMembersNameField.textColor = blueColor
//        showTeamMembersNameField.placeholder = "Show Team Members"
//        showTeamMembersNameField.borderStyle = UITextBorderStyle.line
//        showTeamMembersNameField.layer.borderWidth = 1
//        showTeamMembersNameField.layer.borderColor = blueColor.cgColor
//        self.view.addSubview(showTeamMembersNameField)
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
    
    func redButtonAction(sender: UIButton!) {
        //var btnsendtag: UIButton = sender
        colorRedButton.isSelected = true
        colorBlueButton.isSelected = false
        color = "red"
        colorSelected = true
        print("Red Button Selected")
    }
    
    func buleButtonAction(sender: UIButton!) {
        //var btnsendtag: UIButton = sender
        colorRedButton.isSelected = false
        colorBlueButton.isSelected = true
        color = "blue"
        colorSelected = true
        print("Blue Button Selected")
    }
    
    func showMembersButtonAction(sender: UIButton!) {
        //var btnsendtag: UIButton = sender
        showMembersButton.isSelected = true
        hideMembersButton.isSelected = false
        showMembers = true
        showMembersSelected = true
        print("Show Members Button Selected")
    }
    
    func hideMembersButtonAction(sender: UIButton!) {
        //var btnsendtag: UIButton = sender
        showMembersButton.isSelected = false
        hideMembersButton.isSelected = true
        showMembers = false
        showMembersSelected = true
        print("Hide Members Button Selected")
    }
    
    func datePickerValueChanged(_ sender: UIDatePicker) {
        if (sender == startTimePicker) {
            let dateFormatter: DateFormatter = DateFormatter()
            // Set date format
            dateFormatter.dateFormat = "yyyy-MM-dd"
            // Apply date format
            let selectedDate: String = dateFormatter.string(from: sender.date)
            print("Selected value \(selectedDate)")
            scheduleStartTime.text = selectedDate
        } else if (sender == endTimePicker) {
            let dateFormatter: DateFormatter = DateFormatter()
            // Set date format
            dateFormatter.dateFormat = "yyyy-MM-dd"
            // Apply date format
            let selectedDate: String = dateFormatter.string(from: sender.date)
            print("Selected value \(selectedDate)")
            scheduleEndTime.text = selectedDate
        }
    }
    
    func cancelButtonPressed(_ button: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func createCompetitionButton(_ button: UIButton) {
        if (competitionNameField.text?.isEmpty)! {
            let message:String = "Please fill in the Competition Name."
            let emptyNameAction = UIAlertController(title: "Empty Competition Name", message: message, preferredStyle: UIAlertControllerStyle.alert)
            emptyNameAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(emptyNameAction, animated: true, completion: nil)
            return
        }
        let competitionName = self.competitionNameField.text!
        
        if (self.otherTeamNameField.text?.isEmpty)! {
            let message:String = "Please fill in the Other Team Name."
            let emptyNameAction = UIAlertController(title: "Empty Team Name", message: message, preferredStyle: UIAlertControllerStyle.alert)
            emptyNameAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(emptyNameAction, animated: true, completion: nil)
            return
        }
        let otherTeamNameField = self.otherTeamNameField.text!
        
        if (self.scheduleStartTime.text?.isEmpty)! {
            let message:String = "Please fill in the Start Time."
            let emptyNameAction = UIAlertController(title: "Empty Start Time", message: message, preferredStyle: UIAlertControllerStyle.alert)
            emptyNameAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(emptyNameAction, animated: true, completion: nil)
            return
        }
        let startTime = self.scheduleStartTime.text!
        
        if (self.scheduleEndTime.text?.isEmpty)! {
            let message:String = "Please fill in the End Time."
            let emptyNameAction = UIAlertController(title: "Empty End Time", message: message, preferredStyle: UIAlertControllerStyle.alert)
            emptyNameAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(emptyNameAction, animated: true, completion: nil)
            return
        }
        let endTime = self.scheduleEndTime.text!
        
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        let start = dateFormatter.date(from: startTime)
        let end = dateFormatter.date(from: endTime)
        if start! > end! {
            let message:String = "Start time is after end time."
            let emptyNameAction = UIAlertController(title: "End Date Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
            emptyNameAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(emptyNameAction, animated: true, completion: nil)
            return
        }
        
        if !self.colorSelected {
            let message:String = "Please select a color."
            let emptyNameAction = UIAlertController(title: "Color Not Selected", message: message, preferredStyle: UIAlertControllerStyle.alert)
            emptyNameAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(emptyNameAction, animated: true, completion: nil)
            return
        }
        
        if !self.showMembersSelected {
            let message:String = "Please select whether team members will be displayed."
            let emptyNameAction = UIAlertController(title: "Show Team Members Not Selected", message: message, preferredStyle: UIAlertControllerStyle.alert)
            emptyNameAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(emptyNameAction, animated: true, completion: nil)
            return
        }
        
        
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
        let parameters = Requests.createCompetition(teamId: self.teamID, competitionName: competitionName, teamName: otherTeamNameField, start: startTime, end: endTime, color: self.color, show: self.showMembers)
        Alamofire.request(Settings.getCreateCompetitionURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers).validate().responseString { response in
            switch response.result {
            case .success(let data):
                //let json = JSON(data)
                print("Created New Competition")
                self.dismiss(animated: true, completion: nil)
                self.presentingView.dismiss(animated: true, completion: nil)
            case .failure(let error):
                if (response.response?.statusCode == 401) {
                    let message:String = "Other team does not exist."
                    let joinAction = UIAlertController(title: "Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
                    joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                        return
                    }))
                    self.present(joinAction, animated: true, completion: nil)
                } else if (response.response?.statusCode == 402) {
                    let message:String = "Other team is already in competition."
                    let joinAction = UIAlertController(title: "Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
                    joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                        return
                    }))
                    self.present(joinAction, animated: true, completion: nil)
                } else {
                    let message:String = "Could not create team."
                    let joinAction = UIAlertController(title: "Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
                    joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                        return
                    }))
                    self.present(joinAction, animated: true, completion: nil)
                }
                print("Request failed with error: \(error)")
            }
        }
    }
    
    func doneButtonTapped(_ sender:UIBarButtonItem) {
        scheduleStartTime.resignFirstResponder()
        scheduleEndTime.resignFirstResponder()
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
//    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
//        //return self.maxNumberMembersValues.count
//    }
//    
//    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
//        //return self.maxNumberMembersValues[row]
//    }
//    
//    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
//        if (row == 0) {
//            //maxNumberMembersPicker.selectRow(row + 1, inComponent:component, animated:true)
//            //maxNumberMembersField.text = self.maxNumberMembersValues[1]
//        } else {
//            //maxNumberMembersField.text = self.maxNumberMembersValues[row]
//        }
//    }
}
