//
//  AddNewItem.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 3/7/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit
import Alamofire

class AddNewItem: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource, UIScrollViewDelegate {
    
    var scrollView: UIScrollView!
    var containerView = UIView()
    
    // Pass on
    var ScheduleItemRecurringType: String = String()
    var RecurringValue: String = String()
    var ScheduleItemEndingType: String = String()
    var EndingValue: String = String()
    var ScheduleItemTitle: String = String()
    var ScheduleItemDescription: String = String()
    var ScheduleItemTypeVar: String = String()
    var ScheduleItemStart: String = String()
    var ScheduleItemDuration: String = String() // Exercise only
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var scheduleTitle: UITextField = UITextField()
    var scheduleDesc: UITextField = UITextField()
    var segmentedControl: UISegmentedControl = UISegmentedControl()
    public var selectedIndex:Int = 1
    
    
    var toolBar = UIToolbar()
    
    
    // Exercise Picker
    var exercisePicker: UIPickerView! = UIPickerView()
    var exerciseValues = ["Exercise Duration (minutes):","5","10","15","20", "25", "30", "35", "40", "45", "50", "55", "60"]
    var medicationPicker: UIPickerView! = UIPickerView()
    var medicationValues = ["Select Medication:"] + Medications.medicationList
    var scheduleDuration: UITextField = UITextField()
    
    // UIPickerView 1
    var frequencyPicker1: UIPickerView! = UIPickerView()
    var frequencyValues1 = ["Select Frequency:"] + RecurringType.RecurringTypeOrderedStringMap
    var scheduleRecurringType: UITextField = UITextField()
    
    var frequencyPickerOptionEveryXDays: UIPickerView! = UIPickerView()
    var frequencyValuesOptionEveryXDays = ["Repeat Every How Many Days:"] + RecurringType.EveryXDaysOptions
    var scheduleRecurringOptionEveryXDays: UITextField = UITextField()
    
    // put multi-select days here
    var monButton: UIButton = UIButton()
    var tueButton: UIButton = UIButton()
    var wedButton: UIButton = UIButton()
    var thuButton: UIButton = UIButton()
    var friButton: UIButton = UIButton()
    var satButton: UIButton = UIButton()
    var sunButton: UIButton = UIButton()
    
    var frequencyPickerOption2b: UIPickerView! = UIPickerView()
    var frequencyValuesOption2b = ["Repeat Once Weekly:"] + RecurringType.DaysOfWeek
    var scheduleRecurringOption2b: UITextField = UITextField()
    
    var frequencyPickerOption2c: UIPickerView! = UIPickerView()
    var frequencyValuesOption2c = ["Repeat Once Monthly, Day:"] + RecurringType.MonthlyOptions
    var scheduleRecurringOption2c: UITextField = UITextField()
    
    // Ending Pickers
    var endingPicker1: UIPickerView! = UIPickerView()
    var endingValues1 = ["Select When Repeats Should End:"] + EndingType.EndingTypeOrderedStringMap
    var scheduleEndingField: UITextField = UITextField()
    
    var endingPicker2a: UIPickerView! = UIPickerView()
    var endingValues2a = ["Repeats Should End After How Many Times:"] + EndingType.AfterYOrruccencesOptions
    var scheduleEndingField2a: UITextField = UITextField()
    
    var endingPicker2b: UIDatePicker! = UIDatePicker()
    var scheduleEndingField2b: UITextField = UITextField()
    
    // Start Time Picker
    var startTimePicker: UIDatePicker! = UIDatePicker()
    var scheduleStartTime: UITextField = UITextField()
    
    // Save Button
    var saveButton: UIButton = UIButton()
    
    // Cancel Button
    var cancelButton: UIButton = UIButton()
    
    var currentDayInfo:CurrentDayInfo = CurrentDayInfo()
    
    var isToday:Bool = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        view.backgroundColor = .white
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        self.scrollView = UIScrollView()
        self.scrollView.delegate = self
        self.scrollView.contentSize = CGSize(width: displayWidth, height: displayHeight)
        containerView = UIView()
        self.scrollView.addSubview(containerView)
        self.view.addSubview(scrollView)
        
        medicationPicker = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        medicationPicker.delegate = self
        medicationPicker.dataSource = self
        
        scheduleTitle = UITextField(frame: CGRect(x: 0, y: barHeight, width: displayWidth, height: 50))
        scheduleTitle.textAlignment = NSTextAlignment.center
        scheduleTitle.textColor = blueColor
        scheduleTitle.placeholder = "Activity Title"
        scheduleTitle.borderStyle = UITextBorderStyle.line
        scheduleTitle.layer.borderWidth = 1
        scheduleTitle.layer.borderColor = blueColor.cgColor
        scheduleTitle.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        //self.scheduleTitle.inputView = self.medicationPicker
        self.view.addSubview(scheduleTitle)
        
        
        scheduleDesc = UITextField(frame: CGRect(x: 0, y: barHeight + 50, width: displayWidth, height: 50))
        scheduleDesc.textAlignment = NSTextAlignment.center
        scheduleDesc.textColor = blueColor
        scheduleDesc.placeholder = "Activity Description"
        scheduleDesc.borderStyle = UITextBorderStyle.line
        scheduleDesc.layer.borderWidth = 1
        scheduleDesc.layer.borderColor = blueColor.cgColor
        scheduleDesc.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(scheduleDesc)
        
        let items = ScheduleItemType.ScheduleItemTypeOrderedStringMap
        segmentedControl = UISegmentedControl(items: items)
        segmentedControl.frame = CGRect(x: 0, y: barHeight + 100, width: displayWidth, height: 50)
        segmentedControl.addTarget(self, action: #selector(actTypeTapped(_:)), for: .valueChanged)
        segmentedControl.selectedSegmentIndex = self.selectedIndex
        view.addSubview(segmentedControl)
        
        exercisePicker = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        exercisePicker.delegate = self
        exercisePicker.dataSource = self
        
        scheduleDuration = UITextField(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 50))
        scheduleDuration.textAlignment = NSTextAlignment.center
        scheduleDuration.textColor = blueColor
        scheduleDuration.placeholder = "Exercise Duration"
        scheduleDuration.borderStyle = UITextBorderStyle.line
        scheduleDuration.layer.borderWidth = 1
        scheduleDuration.layer.borderColor = blueColor.cgColor
        scheduleDuration.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleDuration.inputView = self.exercisePicker
        self.view.addSubview(scheduleDuration)
        
        
        // Event Specific Pickers Below
        frequencyPicker1 = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        frequencyPicker1.delegate = self
        frequencyPicker1.dataSource = self
        frequencyPicker1.tag = 1
        
        scheduleRecurringType = UITextField(frame: CGRect(x: 0, y: barHeight + 200, width: displayWidth, height: 50))
        scheduleRecurringType.textAlignment = NSTextAlignment.center
        scheduleRecurringType.textColor = blueColor
        scheduleRecurringType.placeholder = "Activity Frequency"
        scheduleRecurringType.borderStyle = UITextBorderStyle.line
        scheduleRecurringType.layer.borderWidth = 1
        scheduleRecurringType.layer.borderColor = blueColor.cgColor
        scheduleRecurringType.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleRecurringType.inputView = self.frequencyPicker1
        self.view.addSubview(scheduleRecurringType)
        
        
        // Frequency 2 Step
        frequencyPickerOptionEveryXDays = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        frequencyPickerOptionEveryXDays.delegate = self
        frequencyPickerOptionEveryXDays.dataSource = self
        
        scheduleRecurringOptionEveryXDays = UITextField(frame: CGRect(x: 0, y: barHeight + 250, width: displayWidth, height: 50))
        scheduleRecurringOptionEveryXDays.textAlignment = NSTextAlignment.center
        scheduleRecurringOptionEveryXDays.textColor = blueColor
        scheduleRecurringOptionEveryXDays.placeholder = "Activity Frequency Details"
        scheduleRecurringOptionEveryXDays.borderStyle = UITextBorderStyle.line
        scheduleRecurringOptionEveryXDays.layer.borderWidth = 1
        scheduleRecurringOptionEveryXDays.layer.borderColor = blueColor.cgColor
        scheduleRecurringOptionEveryXDays.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleRecurringOptionEveryXDays.inputView = self.frequencyPickerOptionEveryXDays
        scheduleRecurringOptionEveryXDays.isHidden = true
        self.view.addSubview(scheduleRecurringOptionEveryXDays)
        
        monButton = UIButton(frame: CGRect(x: 0, y: barHeight + 250, width: displayWidth/7, height: 50))
        monButton.backgroundColor = .clear
        monButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        monButton.layer.borderColor = blueColor.cgColor
        monButton.layer.borderWidth = 1
        monButton.setTitle("Mon", for: .normal)
        monButton.setTitleColor(blueColor, for: .normal)
        monButton.setTitleColor(.white, for: .selected)
        monButton.addTarget(self, action:#selector(self.MonButtonClicked), for: .touchUpInside)
        monButton.isHidden = true
        self.view.addSubview(monButton)
        
        tueButton = UIButton(frame: CGRect(x: displayWidth/7, y: barHeight + 250, width: displayWidth/7, height: 50))
        tueButton.backgroundColor = .clear
        tueButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        tueButton.layer.borderColor = blueColor.cgColor
        tueButton.layer.borderWidth = 1
        tueButton.setTitle("Tue", for: .normal)
        tueButton.setTitleColor(blueColor, for: .normal)
        tueButton.setTitleColor(.white, for: .selected)
        tueButton.addTarget(self, action:#selector(self.TueButtonClicked), for: .touchUpInside)
        tueButton.isHidden = true
        self.view.addSubview(tueButton)
        
        wedButton = UIButton(frame: CGRect(x: (displayWidth/7)*2, y: barHeight + 250, width: displayWidth/7, height: 50))
        wedButton.backgroundColor = .clear
        wedButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        wedButton.layer.borderColor = blueColor.cgColor
        wedButton.layer.borderWidth = 1
        wedButton.setTitle("Wed", for: .normal)
        wedButton.setTitleColor(blueColor, for: .normal)
        wedButton.setTitleColor(.white, for: .selected)
        wedButton.addTarget(self, action:#selector(self.WedButtonClicked), for: .touchUpInside)
        wedButton.isHidden = true
        self.view.addSubview(wedButton)
        
        thuButton = UIButton(frame: CGRect(x: (displayWidth/7)*3, y: barHeight + 250, width: displayWidth/7, height: 50))
        thuButton.backgroundColor = .clear
        thuButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        tueButton.layer.borderColor = blueColor.cgColor
        thuButton.layer.borderWidth = 1
        thuButton.setTitle("Thu", for: .normal)
        thuButton.setTitleColor(blueColor, for: .normal)
        thuButton.setTitleColor(.white, for: .selected)
        thuButton.addTarget(self, action:#selector(self.ThuButtonClicked), for: .touchUpInside)
        thuButton.isHidden = true
        self.view.addSubview(thuButton)
        
        friButton = UIButton(frame: CGRect(x: (displayWidth/7)*4, y: barHeight + 250, width: displayWidth/7, height: 50))
        friButton.backgroundColor = .clear
        friButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        friButton.layer.borderColor = blueColor.cgColor
        friButton.layer.borderWidth = 1
        friButton.setTitle("Fri", for: .normal)
        friButton.setTitleColor(blueColor, for: .normal)
        friButton.setTitleColor(.white, for: .selected)
        friButton.addTarget(self, action:#selector(self.FriButtonClicked), for: .touchUpInside)
        friButton.isHidden = true
        self.view.addSubview(friButton)
        
        satButton = UIButton(frame: CGRect(x: (displayWidth/7)*5, y: barHeight + 250, width: displayWidth/7, height: 50))
        satButton.backgroundColor = .clear
        satButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        satButton.layer.borderColor = blueColor.cgColor
        satButton.layer.borderWidth = 1
        satButton.setTitle("Sat", for: .normal)
        satButton.setTitleColor(blueColor, for: .normal)
        satButton.setTitleColor(.white, for: .selected)
        satButton.addTarget(self, action:#selector(self.SatButtonClicked), for: .touchUpInside)
        satButton.isHidden = true
        self.view.addSubview(satButton)
        
        sunButton = UIButton(frame: CGRect(x: (displayWidth/7)*6, y: barHeight + 250, width: displayWidth/7, height: 50))
        sunButton.backgroundColor = .clear
        sunButton.setBackgroundImage(getImageWithColor(color: blueColor, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        sunButton.layer.borderColor = blueColor.cgColor
        sunButton.layer.borderWidth = 1
        sunButton.setTitle("Sun", for: .normal)
        sunButton.setTitleColor(blueColor, for: .normal)
        sunButton.setTitleColor(.white, for: .selected)
        sunButton.addTarget(self, action:#selector(self.SunButtonClicked), for: .touchUpInside)
        sunButton.isHidden = true
        self.view.addSubview(sunButton)
        
        frequencyPickerOption2b = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        frequencyPickerOption2b.delegate = self
        frequencyPickerOption2b.dataSource = self
        
        scheduleRecurringOption2b = UITextField(frame: CGRect(x: 0, y: barHeight + 250, width: displayWidth, height: 50))
        scheduleRecurringOption2b.textAlignment = NSTextAlignment.center
        scheduleRecurringOption2b.textColor = blueColor
        scheduleRecurringOption2b.placeholder = "Activity Frequency Details"
        scheduleRecurringOption2b.borderStyle = UITextBorderStyle.line
        scheduleRecurringOption2b.layer.borderWidth = 1
        scheduleRecurringOption2b.layer.borderColor = blueColor.cgColor
        scheduleRecurringOption2b.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleRecurringOption2b.inputView = self.frequencyPickerOption2b
        scheduleRecurringOption2b.isHidden = true
        self.view.addSubview(scheduleRecurringOption2b)
        
        frequencyPickerOption2c = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        frequencyPickerOption2c.delegate = self
        frequencyPickerOption2c.dataSource = self
        
        scheduleRecurringOption2c = UITextField(frame: CGRect(x: 0, y: barHeight + 250, width: displayWidth, height: 50))
        scheduleRecurringOption2c.textAlignment = NSTextAlignment.center
        scheduleRecurringOption2c.textColor = blueColor
        scheduleRecurringOption2c.placeholder = "Activity Frequency Details"
        scheduleRecurringOption2c.borderStyle = UITextBorderStyle.line
        scheduleRecurringOption2c.layer.borderWidth = 1
        scheduleRecurringOption2c.layer.borderColor = blueColor.cgColor
        scheduleRecurringOption2c.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleRecurringOption2c.inputView = self.frequencyPickerOption2c
        scheduleRecurringOption2c.isHidden = true
        self.view.addSubview(scheduleRecurringOption2c)
        
        
        
        // Ending Step
        endingPicker1 = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        endingPicker1.delegate = self
        endingPicker1.dataSource = self
        
        scheduleEndingField = UITextField(frame: CGRect(x: 0, y: barHeight + 300, width: displayWidth, height: 50))
        scheduleEndingField.textAlignment = NSTextAlignment.center
        scheduleEndingField.textColor = blueColor
        scheduleEndingField.placeholder = "Activity Ending Details"
        scheduleEndingField.borderStyle = UITextBorderStyle.line
        scheduleEndingField.layer.borderWidth = 1
        scheduleEndingField.layer.borderColor = blueColor.cgColor
        scheduleEndingField.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleEndingField.inputView = self.endingPicker1
        scheduleEndingField.isHidden = true
        self.view.addSubview(scheduleEndingField)
        
        // Ending Step2a
        endingPicker2a = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        endingPicker2a.delegate = self
        endingPicker2a.dataSource = self
        
        scheduleEndingField2a = UITextField(frame: CGRect(x: 0, y: barHeight + 350, width: displayWidth, height: 50))
        scheduleEndingField2a.textAlignment = NSTextAlignment.center
        scheduleEndingField2a.textColor = blueColor
        scheduleEndingField2a.placeholder = "Ending After How Many Times?"
        scheduleEndingField2a.borderStyle = UITextBorderStyle.line
        scheduleEndingField2a.layer.borderWidth = 1
        scheduleEndingField2a.layer.borderColor = blueColor.cgColor
        scheduleEndingField2a.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleEndingField2a.inputView = self.endingPicker2a
        scheduleEndingField2a.isHidden = true
        self.view.addSubview(scheduleEndingField2a)
        
        // Ending Step2b
        endingPicker2b = UIDatePicker()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        endingPicker2b.timeZone = NSTimeZone.local
        endingPicker2b.datePickerMode = UIDatePickerMode.date
        let currentDateFormatter:DateFormatter = DateFormatter()
        currentDateFormatter.dateFormat = "yyyy-MM-dd"
        let currentDay = currentDateFormatter.date(from: self.currentDayInfo.currentDayString)
        endingPicker2b.minimumDate = currentDay
        endingPicker2b.addTarget(self, action: #selector(datePickerValueChanged(_:)), for: .valueChanged)
//        endingPicker2b.delegate = self
//        endingPicker2b.dataSource = self
        
        scheduleEndingField2b = UITextField(frame: CGRect(x: 0, y: barHeight + 350, width: displayWidth, height: 50))
        scheduleEndingField2b.textAlignment = NSTextAlignment.center
        scheduleEndingField2b.textColor = blueColor
        scheduleEndingField2b.placeholder = "Ending On What Date?"
        scheduleEndingField2b.borderStyle = UITextBorderStyle.line
        scheduleEndingField2b.layer.borderWidth = 1
        scheduleEndingField2b.layer.borderColor = blueColor.cgColor
        scheduleEndingField2b.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleEndingField2b.inputView = self.endingPicker2b
        scheduleEndingField2b.isHidden = true
        self.view.addSubview(scheduleEndingField2b)
        
        // Start Time Picker
        startTimePicker = UIDatePicker()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        startTimePicker.timeZone = NSTimeZone.local
        startTimePicker.addTarget(self, action: #selector(datePickerValueChanged(_:)), for: .valueChanged)
        startTimePicker.datePickerMode = UIDatePickerMode.time
        
        let startDateFormatter: DateFormatter = DateFormatter()
        startDateFormatter.dateFormat = "yyyy-MM-dd HH:mm"
        let currentTime:Date = Date()
        let currentTimeFormatter:DateFormatter = DateFormatter()
        currentTimeFormatter.dateFormat = "HH:mm"
        let currentTimeString:String = currentTimeFormatter.string(from: currentTime)
        
        let currentDayFormatter:DateFormatter = DateFormatter()
        currentDayFormatter.dateFormat = "yyyy-MM-dd"
        let actualDayValueString:String = currentDayFormatter.string(from: Date())
        isToday = actualDayValueString == self.currentDayInfo.currentDayString
        
        var startDateString:String = ""
        if isToday {
            startDateString = self.currentDayInfo.currentDayString + " " + currentTimeString
        } else {
            startDateString = self.currentDayInfo.currentDayString + " 0:00"
        }
        // Apply date format
        print(startDateString)
        let startTimeDate: Date = startDateFormatter.date(from: startDateString)!
        startTimePicker.date = startTimeDate
        startTimePicker.minimumDate = startTimeDate
        //        endingPicker2b.delegate = self
        //        endingPicker2b.dataSource = self
        
        scheduleStartTime = UITextField(frame: CGRect(x: 0, y: barHeight + 400, width: displayWidth, height: 50))
        scheduleStartTime.textAlignment = NSTextAlignment.center
        scheduleStartTime.textColor = blueColor
        scheduleStartTime.placeholder = "Starting At What Time?"
        scheduleStartTime.borderStyle = UITextBorderStyle.line
        scheduleStartTime.layer.borderWidth = 1
        scheduleStartTime.layer.borderColor = blueColor.cgColor
        scheduleStartTime.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleStartTime.inputView = self.startTimePicker
        scheduleStartTime.isHidden = false
        self.view.addSubview(scheduleStartTime)
        
        toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.default
        toolBar.isTranslucent = true
        toolBar.tintColor = blueColor //UIColor(red: 76/255, green: 217/255, blue: 100/255, alpha: 1)
        toolBar.sizeToFit()
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItemStyle.plain, target: self, action: #selector(doneButtonTapped(_:)))
        toolBar.setItems([doneButton], animated: false)
        toolBar.isUserInteractionEnabled = true
        scheduleStartTime.inputAccessoryView = toolBar
        scheduleDuration.inputAccessoryView = toolBar
        scheduleRecurringType.inputAccessoryView = toolBar
        scheduleRecurringOptionEveryXDays.inputAccessoryView = toolBar
        scheduleRecurringOption2b.inputAccessoryView = toolBar
        scheduleRecurringOption2c.inputAccessoryView = toolBar
        scheduleEndingField.inputAccessoryView = toolBar
        scheduleEndingField2a.inputAccessoryView = toolBar
        scheduleEndingField2b.inputAccessoryView = toolBar
        //scheduleTitle.inputAccessoryView = toolBar
        
        
        // Save Button
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
        
        cancelButton = UIButton(frame: CGRect(x: displayWidth/4, y: barHeight + 500, width: 100, height: 44))
        cancelButton.setTitle("Cancel", for: UIControlState.normal)
        cancelButton.setTitleColor(blueColor, for: UIControlState.normal)
        cancelButton.backgroundColor = UIColor.clear
        cancelButton.layer.borderWidth = 1.0
        cancelButton.layer.borderColor = blueColor.cgColor
        cancelButton.layer.cornerRadius = cornerRadius
        cancelButton.addTarget(self, action: #selector(cancelButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(cancelButton)
        
    }
    
    
    func doneButtonTapped(_ sender:UIBarButtonItem) {
        scheduleDuration.resignFirstResponder()
        scheduleRecurringType.resignFirstResponder()
        scheduleRecurringOptionEveryXDays.resignFirstResponder()
        scheduleRecurringOption2b.resignFirstResponder()
        scheduleRecurringOption2c.resignFirstResponder()
        scheduleEndingField.resignFirstResponder()
        scheduleEndingField2a.resignFirstResponder()
        scheduleEndingField2b.resignFirstResponder()
        scheduleStartTime.resignFirstResponder()
        scheduleTitle.resignFirstResponder()
        
    }
    
    func cancelButtonTapped(_ button: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    // MARK: Button Action
    func saveButtonTapped(_ button: UIButton) {
        
        // Start check for required fields
        if (scheduleTitle.text?.isEmpty)! {
            let alert = UIAlertController(title: "Alert", message: "Please Provide an Schedule Title", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        } else if (scheduleRecurringType.text?.isEmpty)! {
            let alert = UIAlertController(title: "Alert", message: "Please Select Activity Frequency", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        } else if (scheduleStartTime.text?.isEmpty)! {
            let alert = UIAlertController(title: "Alert", message: "Please Select Start Time", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        }
        
        // End check for required field
        
        let newScheduleItem = ScheduleItem()

        // Recurring-Type
        // "None","Daily","Every X Days","Certain Days of Week", "Weekly", "Monthly"
        let scheduleRecurringTypeText = scheduleRecurringType.text!
        let selectedRecurringType = RecurringType.getSelectedRecurringItemType(item: scheduleRecurringTypeText)
        newScheduleItem.recurringType = selectedRecurringType
        
        // Recurring-Value
        switch selectedRecurringType {
        case .NotRecurring:
            newScheduleItem.recurringValue = []
        case .Daily:
            newScheduleItem.recurringValue = []
        case .EveryXDays:
            let everyXDaysSelected:Int = Int(scheduleRecurringOptionEveryXDays.text!)!
            newScheduleItem.recurringValue = [everyXDaysSelected]
        case .CertainDaysOfWeek:
            var dayList = [Int]()
            if (monButton.isSelected) {
                dayList.append(1)
            }
            if (tueButton.isSelected) {
                dayList.append(2)
            }
            if (wedButton.isSelected) {
                dayList.append(3)
            }
            if (thuButton.isSelected) {
                dayList.append(4)
            }
            if (friButton.isSelected) {
                dayList.append(5)
            }
            if (satButton.isSelected) {
                dayList.append(6)
            }
            if (sunButton.isSelected) {
                dayList.append(0)
            }
            newScheduleItem.recurringValue = dayList
            if (dayList.count == 0) {
                let alert = UIAlertController(title: "Alert", message: "Please Select Weekdays", preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
                return
            }
        case .Weekly:
            if (scheduleRecurringOption2b.text?.isEmpty)! {
                let alert = UIAlertController(title: "Alert", message: "Please Select a Weekday", preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
                return
            }
            let pickedDay = scheduleRecurringOption2b.text!
            newScheduleItem.recurringValue = [RecurringType.DaysOfWeek.index(of: pickedDay)!]
        case .Monthly:
            if (scheduleRecurringOption2c.text?.isEmpty)! {
                let alert = UIAlertController(title: "Alert", message: "Please Select a Day", preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
                return
            }
            newScheduleItem.recurringValue = [Int(scheduleRecurringOption2c.text!)!]
        }
        
        // Ending-Type
        if selectedRecurringType == .NotRecurring {
            newScheduleItem.endingType = .NotRecurring
            newScheduleItem.endingValue = JSONProtocolNames.endingTypeNotNeeded
        } else {
            if (scheduleEndingField.text?.isEmpty)! {
                let alert = UIAlertController(title: "Alert", message: "Please Select End Time", preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
                return
            }

            let selectedEndingType = EndingType.getSelectedEndingItemType(item: scheduleEndingField.text!)
            newScheduleItem.endingType = selectedEndingType
        
            // Ending Value
            switch selectedEndingType {
            case .NotRecurring:
                newScheduleItem.endingValue = JSONProtocolNames.endingTypeNotNeeded
            case .Never:
                newScheduleItem.endingValue = JSONProtocolNames.endingTypeNotNeeded
            case .AfterYOccurrences:
                if (scheduleEndingField2a.text?.isEmpty)! {
                    let alert = UIAlertController(title: "Alert", message: "Please Select Occurrences", preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                    return
                }
                newScheduleItem.endingValue = scheduleEndingField2a.text!
            case .OnT:
                if (scheduleEndingField2b.text?.isEmpty)! {
                    let alert = UIAlertController(title: "Alert", message: "Please Select End Date", preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                    return
                }
                let currentDateFormatter:DateFormatter = DateFormatter()
                currentDateFormatter.dateFormat = "yyyy-MM-dd"
                let currentDay = currentDateFormatter.date(from: self.currentDayInfo.currentDayString)
                let selectedEndDateValue:Date = currentDateFormatter.date(from: scheduleEndingField2b.text!)!
                if selectedEndDateValue < currentDay! {
                    let alert = UIAlertController(title: "Alert", message: "End Day cannot be in the past", preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                    return
                }
                newScheduleItem.endingValue = scheduleEndingField2b.text!
            }
        }
        
        // Schedule-Item-Title
        if (scheduleTitle.text!).characters.count > JSONProtocolNames.scheduleItemTitleMaxLength {
            let alert = UIAlertController(title: "Alert", message: "Title should be \(JSONProtocolNames.scheduleItemTitleMaxLength) characters or less.", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        } else {
            newScheduleItem.scheduleItemTitle = scheduleTitle.text!
        }
        
        // Schedule-Item-Description
        if scheduleDesc.text!.isEmpty {
            newScheduleItem.scheduleItemDescription = ""
        } else {
            if (scheduleDesc.text!).characters.count > JSONProtocolNames.scheduleItemDescriptionLength {
                let alert = UIAlertController(title: "Alert", message: "Description should be \(JSONProtocolNames.scheduleItemDescriptionLength) characters or less.", preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
                return
            } else {
                newScheduleItem.scheduleItemDescription = scheduleDesc.text!
            }
        }
        
        // Schedule-Item-Type
        let selectedScheduleItemString:String = ScheduleItemType.ScheduleItemTypeOrderedStringMap[segmentedControl.selectedSegmentIndex]
        let selectedScheduleItemType = ScheduleItemType.getSelectedScheduleItemType(item: selectedScheduleItemString)
        newScheduleItem.scheduleItemType = selectedScheduleItemType
        
        // Schedule-Item-Duration
        switch selectedScheduleItemType {
        case .Exercise:
            if (scheduleDuration.text?.isEmpty)! {
                let alert = UIAlertController(title: "Alert", message: "Please Select Duration", preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
                return
            }
            newScheduleItem.scheduleItemProgressType = .Percentage
            newScheduleItem.scheduleItemDuration = Int(scheduleDuration.text!)!
        default:
            newScheduleItem.scheduleItemProgressType = .Boolean
            newScheduleItem.scheduleItemDuration = JSONProtocolNames.durationProgressTypeNotNeeded
        }
        
        // Schedule-Item-Start
        ScheduleItemStart = self.currentDayInfo.currentDayString + " " + scheduleStartTime.text!
        let dateFormatterIn = DateFormatter()
        //dateFormatterIn.timeZone = NSTimeZone(name: "GMT") as TimeZone!
        dateFormatterIn.dateFormat = "yyyy-MM-dd hh:mm a"
        let selectedDate:Date = dateFormatterIn.date(from: ScheduleItemStart)!
        
        let dateFormatterOut = DateFormatter()
        dateFormatterOut.timeZone = NSTimeZone(name: "GMT") as TimeZone!
        
        let currentDate:Date = Date()
        if selectedRecurringType == .NotRecurring && selectedDate < currentDate {
            let alert = UIAlertController(title: "Alert", message: "Start time cannot be in the past for Non-Recurring Schedule Items", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        } else {
            newScheduleItem.scheduleItemStart = selectedDate
        }
        print("CAT:  \(newScheduleItem.scheduleItemStart)")
        
        
        //print(Requests.addScheduleItemJSON(item: newScheduleItem))
        
        
        print("SaveButton pressed")
        print("TEST2")
        
        let parameters = Requests.addScheduleItemJSON(item: newScheduleItem)
        
        //"http://130.91.134.209:8000/add"
        let currentDayFormatter:DateFormatter = DateFormatter()
        currentDayFormatter.dateFormat = "yyyy-MM-dd"
        currentDayFormatter.timeZone = NSTimeZone(name: "GMT") as TimeZone!
        let currentDayHeaderString:String = currentDayFormatter.string(from: newScheduleItem.scheduleItemStart)
        print("HEADER: \(currentDayHeaderString)")
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString, JSONProtocolNames.dateHeaderName: currentDayHeaderString]
        Alamofire.request(Settings.getAddScheduleItemURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
            .responseString { response in
                switch response.result {
                case .success(let _):
                    print("TEST SPIRO")
                    if Medications.medicationsWithFood.contains(newScheduleItem.scheduleItemTitle) {
                        let message:String = newScheduleItem.scheduleItemTitle + " must be taken with food."
                        let addFoodAction = UIAlertController(title: "Add Food", message: message, preferredStyle: UIAlertControllerStyle.alert)
                        addFoodAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                            self.dismiss(animated: true, completion: nil)
                        }))
                        self.present(addFoodAction, animated: true, completion: nil)
                    } else {
                        self.dismiss(animated: true, completion: nil)
                    }
                case .failure(let error):
                    print("Request failed with error: \(error)")
                    let alert = UIAlertController(title: "Alert", message: "Could not add Schedule Item", preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                }
        }
        
        // Dismiss VC
        //self.dismiss(animated: true, completion: nil)
        
    }

    
    func checkButtonStatus(){
        if (monButton.isSelected || tueButton.isSelected || wedButton.isSelected || thuButton.isSelected || friButton.isSelected || satButton.isSelected || sunButton.isSelected) {
            scheduleEndingField.isHidden = false
        } else {
            scheduleEndingField.isHidden = true
        }
    }
    
    func MonButtonClicked(sender:UIButton) {
        sender.isSelected = !sender.isSelected;
        
        checkButtonStatus()
        print("Mon Button Clicked")
    }
    
    func TueButtonClicked(sender:UIButton) {
        sender.isSelected = !sender.isSelected;
        checkButtonStatus()

        print("Tue Button Clicked")
    }
    
    func WedButtonClicked(sender:UIButton) {
        sender.isSelected = !sender.isSelected;
        checkButtonStatus()

        print("Wed Button Clicked")
    }
    
    func ThuButtonClicked(sender:UIButton) {
        sender.isSelected = !sender.isSelected;
        checkButtonStatus()

        print("Thu Button Clicked")
    }
    
    func FriButtonClicked(sender:UIButton) {
        sender.isSelected = !sender.isSelected;
        checkButtonStatus()

        print("Fri Button Clicked")
    }
    
    func SatButtonClicked(sender:UIButton) {
        sender.isSelected = !sender.isSelected;
        checkButtonStatus()

        print("Sat Button Clicked")
    }
    
    func SunButtonClicked(sender:UIButton) {
        sender.isSelected = !sender.isSelected;
        checkButtonStatus()

        print("Sun Button Clicked")
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
    
    func datePickerValueChanged(_ sender: UIDatePicker){
        
        if (sender == endingPicker2b) {
        
        // Create date formatter
        let dateFormatter: DateFormatter = DateFormatter()
        // Set date format
        dateFormatter.dateFormat = "yyyy-MM-dd"
        // Apply date format
        let selectedDate: String = dateFormatter.string(from: sender.date)
        print("Selected value \(selectedDate)")
        scheduleEndingField2b.text = selectedDate
        }
        
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
        
        if (frequencyPicker1 == pickerView) {
            return frequencyValues1.count
        }
        
        if (frequencyPickerOptionEveryXDays == pickerView) {
            return frequencyValuesOptionEveryXDays.count
        }
        
        if (frequencyPickerOption2b == pickerView) {
            return frequencyValuesOption2b.count
        }
        
        if (frequencyPickerOption2c == pickerView) {
            return frequencyValuesOption2c.count
        }
        
        if (endingPicker1 == pickerView) {
            return endingValues1.count
        }
        
        if (endingPicker2a == pickerView) {
            return endingValues2a.count
        }
        
        if (medicationPicker == pickerView) {
            return medicationValues.count
        }
        
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if (exercisePicker == pickerView) {
            return exerciseValues[row]
        }
        
        if (frequencyPicker1 == pickerView) {
            return frequencyValues1[row]
        }
        
        if (frequencyPickerOptionEveryXDays == pickerView) {
            return frequencyValuesOptionEveryXDays[row]
        }
        
        if (frequencyPickerOption2b == pickerView) {
            return frequencyValuesOption2b[row]
        }
        
        if (frequencyPickerOption2c == pickerView) {
            return frequencyValuesOption2c[row]
        }
        
        if (endingPicker1 == pickerView) {
            return endingValues1[row]
        }
        
        if (endingPicker2a == pickerView) {
            return endingValues2a[row]
        }
        
        if (medicationPicker == pickerView) {
            return medicationValues[row]
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
        
        if (medicationPicker == pickerView) {
            scheduleTitle.text = medicationValues[row]
            if (row == 0) {
                pickerView.selectRow(row + 1, inComponent:component, animated:true)
                scheduleTitle.text = medicationValues[1]
            }
            
        }
        
        
        if (frequencyPicker1 == pickerView) {
            scheduleRecurringType.text = frequencyValues1[row]
            if (row == 0) {
                pickerView.selectRow(row + 1, inComponent:component, animated:true)
                scheduleRecurringType.text = frequencyValues1[1]
            }
            
            if (row == 1 || row == 2) {
                if (row == 2) {
                    scheduleEndingField.isHidden = false
                } else {
                    scheduleEndingField.isHidden = true
                }
                scheduleEndingField2a.isHidden = true
                scheduleEndingField2b.isHidden = true
            }
            
            // Selected "Select Frequency: Every X Days"
            if (row == 3) {
                scheduleRecurringOptionEveryXDays.isHidden = false
            } else {
                scheduleRecurringOptionEveryXDays.isHidden = true
            }
            
            // Selected "Select Frequency: Certain Days of Week"
            if (row == 4) {
                monButton.isHidden = false
                tueButton.isHidden = false
                wedButton.isHidden = false
                thuButton.isHidden = false
                friButton.isHidden = false
                satButton.isHidden = false
                sunButton.isHidden = false
            } else {
                monButton.isHidden = true
                tueButton.isHidden = true
                wedButton.isHidden = true
                thuButton.isHidden = true
                friButton.isHidden = true
                satButton.isHidden = true
                sunButton.isHidden = true
            }
            
            // Selected "Select Frequency: Weekly"
            if (row == 5) {
                scheduleRecurringOption2b.isHidden = false
            } else {
                scheduleRecurringOption2b.isHidden = true
            }
            
            // Selected "Select Frequency: Monthly"
            if (row == 6) {
                scheduleRecurringOption2c.isHidden = false
            } else {
                scheduleRecurringOption2c.isHidden = true
            }
        }
        
        if (frequencyPickerOptionEveryXDays == pickerView) {
            scheduleRecurringOptionEveryXDays.text = frequencyValuesOptionEveryXDays[row]
            if (row == 0) {
                pickerView.selectRow(row + 1, inComponent:component, animated:true)
                scheduleRecurringOptionEveryXDays.text = frequencyValuesOptionEveryXDays[1]
            }
            
            scheduleEndingField.isHidden = false
        }
        
        if (frequencyPickerOption2b == pickerView) {
            scheduleRecurringOption2b.text = frequencyValuesOption2b[row]
            if (row == 0) {
                pickerView.selectRow(row + 1, inComponent:component, animated:true)
                scheduleRecurringOption2b.text = frequencyValuesOption2b[1]
            }
            
            scheduleEndingField.isHidden = false
        }
        
        if (frequencyPickerOption2c == pickerView) {
            scheduleRecurringOption2c.text = frequencyValuesOption2c[row]
            if (row == 0) {
                pickerView.selectRow(row + 1, inComponent:component, animated:true)
                scheduleRecurringOption2c.text = frequencyValuesOption2c[1]
            }
            
            scheduleEndingField.isHidden = false
        }
        
        if (endingPicker1 == pickerView) {
            scheduleEndingField.text = endingValues1[row]
            if (row == 0) {
                pickerView.selectRow(row + 1, inComponent:component, animated:true)
                scheduleEndingField.text = endingValues1[1]
            }
            
            // Selected "Repeats Should End: After X Times"
            if (row == 1) {
                scheduleEndingField2a.isHidden = false
            } else {
                scheduleEndingField2a.isHidden = true
            }
            
            // Selected "Repeats Should End: On a Certain Date"
            if (row == 2) {
                scheduleEndingField2b.isHidden = false
            } else {
                scheduleEndingField2b.isHidden = true
            }

        
        }
        
        if (endingPicker2a == pickerView) {
            scheduleEndingField2a.text = endingValues2a[row]
            if (row == 0) {
                pickerView.selectRow(row + 1, inComponent:component, animated:true)
                scheduleEndingField2a.text = endingValues2a[1]
            }
        }
        
    }
    
    // MARK: Button Action
    func actTypeTapped(_ sender:UISegmentedControl) {
        
        print("SC pressed")
        if (sender.selectedSegmentIndex == 1) {
            scheduleDuration.isHidden = false
        } else {
            scheduleDuration.isHidden = true
        }
        
        if (sender.selectedSegmentIndex == 0) {
            self.scheduleTitle.inputView = self.medicationPicker
            self.scheduleTitle.inputAccessoryView = self.toolBar
            self.scheduleTitle.reloadInputViews()
        } else {
            self.scheduleTitle.inputView = nil
            self.scheduleTitle.inputAccessoryView = nil
            self.scheduleTitle.reloadInputViews()
        }
        print(sender.selectedSegmentIndex)
        
    }

}

extension UIViewController {
    func hideKeyboardWhenTappedAround() {
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(UIViewController.dismissKeyboard))
        view.addGestureRecognizer(tap)
    }
    
    func dismissKeyboard() {
        view.endEditing(true)
    }
}
