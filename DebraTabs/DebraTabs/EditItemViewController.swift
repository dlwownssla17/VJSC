//
//  EditItemViewController.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 3/8/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit
import Alamofire

class EditItemViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
    
    // Passed In
    public var ActivityTitle: String = String()
    public var ActivityDescription: String = String()
    public var ActivityTypeIndex: Int = Int()
    public var ActivityDuration: Int = Int()
    public var ActivityStartTime: String = String()
    public var ActivityID: Int = Int()
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var scheduleTitle: UITextField = UITextField()
    var scheduleDesc: UITextField = UITextField()
    var segmentedControl: UISegmentedControl = UISegmentedControl()
    
    // Exercise Picker
    var exercisePicker: UIPickerView! = UIPickerView()
    var exerciseValues = ["Exercise Duration (minutes):","5","10","15","20", "25", "30", "35", "40", "45", "50", "55", "60"]
    var scheduleDuration: UITextField = UITextField()
    
    // Start Time Picker
    var startTimePicker: UIDatePicker! = UIDatePicker()
    var scheduleStartTime: UITextField = UITextField()

    
    // Save Button
    var saveButton: UIButton = UIButton()
    
    var currentDayInfo:CurrentDayInfo = CurrentDayInfo()

    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        view.backgroundColor = .white
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        scheduleTitle = UITextField(frame: CGRect(x: 0, y: barHeight, width: displayWidth, height: 50))
        scheduleTitle.textAlignment = NSTextAlignment.center
        scheduleTitle.textColor = blueColor
        scheduleTitle.placeholder = "Activity Title"
        scheduleTitle.text = ActivityTitle
        scheduleTitle.borderStyle = UITextBorderStyle.line
        scheduleTitle.layer.borderWidth = 1
        scheduleTitle.layer.borderColor = blueColor.cgColor
        scheduleTitle.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(scheduleTitle)
        
        
        scheduleDesc = UITextField(frame: CGRect(x: 0, y: barHeight + 50, width: displayWidth, height: 50))
        scheduleDesc.textAlignment = NSTextAlignment.center
        scheduleDesc.textColor = blueColor
        scheduleDesc.placeholder = "Activity Description"
        scheduleDesc.text = ActivityDescription
        scheduleDesc.borderStyle = UITextBorderStyle.line
        scheduleDesc.layer.borderWidth = 1
        scheduleDesc.layer.borderColor = blueColor.cgColor
        scheduleDesc.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(scheduleDesc)
        
        let items = ScheduleItemType.ScheduleItemTypeOrderedStringMap
        segmentedControl = UISegmentedControl(items: items)
        segmentedControl.frame = CGRect(x: 0, y: barHeight + 100, width: displayWidth, height: 50)
        segmentedControl.addTarget(self, action: #selector(actTypeTapped(_:)), for: .valueChanged)
        segmentedControl.selectedSegmentIndex = ActivityTypeIndex
        self.segmentedControl.isUserInteractionEnabled = false
        view.addSubview(segmentedControl)
        
        exercisePicker = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        exercisePicker.delegate = self
        exercisePicker.dataSource = self
        
        scheduleDuration = UITextField(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 50))
        scheduleDuration.textAlignment = NSTextAlignment.center
        scheduleDuration.textColor = blueColor
        scheduleDuration.placeholder = "Exercise Duration"
        scheduleDuration.text = String(ActivityDuration)
        scheduleDuration.borderStyle = UITextBorderStyle.line
        scheduleDuration.layer.borderWidth = 1
        scheduleDuration.layer.borderColor = blueColor.cgColor
        scheduleDuration.isHidden = ActivityTypeIndex != 1
        scheduleDuration.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleDuration.isUserInteractionEnabled = false
//        self.scheduleDuration.inputView = self.exercisePicker

        self.view.addSubview(scheduleDuration)
        
        // Start Time Picker
        startTimePicker = UIDatePicker()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        startTimePicker.timeZone = NSTimeZone.local
        startTimePicker.addTarget(self, action: #selector(datePickerValueChanged(_:)), for: .valueChanged)
        startTimePicker.datePickerMode = UIDatePickerMode.time
        
        scheduleStartTime = UITextField(frame: CGRect(x: 0, y: barHeight + 400, width: displayWidth, height: 50))
        scheduleStartTime.textAlignment = NSTextAlignment.center
        scheduleStartTime.textColor = blueColor
        scheduleStartTime.placeholder = "Starting at What Time?"
        scheduleStartTime.text = ActivityStartTime
        scheduleStartTime.borderStyle = UITextBorderStyle.line
        scheduleStartTime.layer.borderWidth = 1
        scheduleStartTime.layer.borderColor = blueColor.cgColor
        scheduleStartTime.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleStartTime.inputView = self.startTimePicker
        scheduleStartTime.isHidden = false
        let toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.default
        toolBar.isTranslucent = true
        toolBar.tintColor = blueColor //UIColor(red: 76/255, green: 217/255, blue: 100/255, alpha: 1)
        toolBar.sizeToFit()
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItemStyle.plain, target: self, action: #selector(doneButtonTapped(_:)))
        toolBar.setItems([doneButton], animated: false)
        toolBar.isUserInteractionEnabled = true
        scheduleDuration.inputAccessoryView = toolBar
        scheduleStartTime.inputAccessoryView = toolBar
        self.view.addSubview(scheduleStartTime)
        
        saveButton = UIButton(frame: CGRect(x: displayWidth/2, y: barHeight + 500, width: 100, height: 44))
        saveButton.setTitle("Save", for: UIControlState.normal)
        saveButton.setTitleColor(blueColor, for: UIControlState.normal)
        saveButton.backgroundColor = UIColor.clear
        saveButton.layer.borderWidth = 1.0
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        saveButton.layer.borderColor = blueColor.cgColor
        saveButton.layer.cornerRadius = cornerRadius
        saveButton.addTarget(self, action: #selector(saveButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(saveButton)
        
    }
    
    func saveButtonTapped(_ button: UIButton) {
        
        // Title in scheduleTitle.text!
        // Description in scheduleDesc.text!
        // Type Index in segmentedControl.selectedSegmentIndex
        // Exercise Duration in scheduleDuration.text!
        // Start Time in scheduleStartTime.text!
        let editScheduleItem = ScheduleItem()
        editScheduleItem.scheduleItemTitle = scheduleTitle.text!
        editScheduleItem.scheduleItemDescription = scheduleDesc.text!
        let selectedScheduleItemString:String = ScheduleItemType.ScheduleItemTypeOrderedStringMap[segmentedControl.selectedSegmentIndex]
        let selectedScheduleItemType = ScheduleItemType.getSelectedScheduleItemType(item: selectedScheduleItemString)
        editScheduleItem.scheduleItemType = selectedScheduleItemType
        switch selectedScheduleItemType {
        case .Exercise:
            editScheduleItem.scheduleItemProgressType = .Percentage
            editScheduleItem.scheduleItemDuration = Int(scheduleDuration.text!)!
        default:
            editScheduleItem.scheduleItemProgressType = .Boolean
            editScheduleItem.scheduleItemDuration = JSONProtocolNames.durationProgressTypeNotNeeded
        }
        let ScheduleItemStart = scheduleStartTime.text!
        let dateFormatterIn = DateFormatter()
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatterIn.timeZone=timeZone as TimeZone!
        dateFormatterIn.dateFormat = "HH:mm a"
        editScheduleItem.scheduleItemStart = dateFormatterIn.date(from: ScheduleItemStart)!
        editScheduleItem.itemID = ActivityID
        
        let parameters = Requests.editScheduleItem(item: editScheduleItem)
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString, JSONProtocolNames.dateHeaderName: self.currentDayInfo.currentDayString]
        
        Alamofire.request(Settings.getEditScheduleItemURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
            .responseString { response in
                switch response.result {
                case .success(let _):
                    print("TEST SPIRO")
                    self.dismiss(animated: true, completion: nil)
                case .failure(let error):
                    print("Request failed with error: \(error)")
                }
        }
        
        
        //self.dismiss(animated: true, completion: nil)
    }
    
    func doneButtonTapped(_ sender:UIBarButtonItem) {
        scheduleDuration.resignFirstResponder()
        scheduleStartTime.resignFirstResponder()
    }
    
    // MARK: Button Action
    func actTypeTapped(_ sender:UISegmentedControl) {
        
        print("SC pressed")
        if (sender.selectedSegmentIndex == 1) {
            scheduleDuration.isHidden = false
        } else {
            scheduleDuration.isHidden = true
        }
        print(sender.selectedSegmentIndex)
    }
    
    func datePickerValueChanged(_ sender: UIDatePicker){
        
        if (sender == startTimePicker) {
            
            // Create date formatter
            let dateFormatter: DateFormatter = DateFormatter()
            // Set date format
            dateFormatter.dateFormat = "hh:mm a"
            // Apply date format
            let selectedDate: String = dateFormatter.string(from: sender.date)
            print("Selected value \(selectedDate)")
            scheduleStartTime.text = selectedDate
            
        }
    }

    
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        
        if (exercisePicker == pickerView) {
            return exerciseValues.count
        }
        
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if (exercisePicker == pickerView) {
            return exerciseValues[row]
        }
        
        return ""
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        if (exercisePicker == pickerView) {
            scheduleDuration.text = exerciseValues[row]
            if (row == 0) {
                pickerView.selectRow(row + 1, inComponent:component, animated:true)
                scheduleDuration.text = exerciseValues[1]
            }
        }
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    



}
