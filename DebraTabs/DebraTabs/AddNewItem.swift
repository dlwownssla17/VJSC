//
//  AddNewItem.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 3/7/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit

class AddNewItem: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
    
    // Exercise Picker
    var exercisePicker: UIPickerView! = UIPickerView()
    var exerciseValues = ["Exercise Duration (minutes):","5","10","15","20", "25", "30", "35", "40", "45", "50", "55", "60"]
    var scheduleDuration: UITextField = UITextField()
    
    // UIPickerView 1
    var frequencyPicker1: UIPickerView! = UIPickerView()
    var frequencyValues1 = ["Select Frequency:","None","Daily","Every X Days","Certain Days of Week", "Weekly", "Monthly"]
    var scheduleRecurringType: UITextField = UITextField()
    
    var frequencyPickerOptionEveryXDays: UIPickerView! = UIPickerView()
    var frequencyValuesOptionEveryXDays = ["Repeat Every How Many Days:","1","2","3","4", "5", "6", "7", "8", "9", "10"]
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
    var frequencyValuesOption2b = ["Repeat Once Weekly:","Monday","Tuesday","Wednesday","Thursday", "Friday", "Saturday", "Sunday"]
    var scheduleRecurringOption2b: UITextField = UITextField()
    
    var frequencyPickerOption2c: UIPickerView! = UIPickerView()
    var frequencyValuesOption2c = ["Repeat Once Monthly, Day:","1","2","3","4", "5", "6", "7", "8", "9", "10","11","12","13","14", "15", "16", "17", "18", "19", "20",
                                   "21","22","23","24", "25", "26", "27", "28", "29", "30", "31"]
    var scheduleRecurringOption2c: UITextField = UITextField()
    
    // Ending Pickers
    var endingPicker1: UIPickerView! = UIPickerView()
    var endingValues1 = ["Select When Repeats Should End:","Ending Never","Ending After X Times","Ending On a Certain Date"]
    var scheduleEndingField: UITextField = UITextField()
    
    var endingPicker2a: UIPickerView! = UIPickerView()
    var endingValues2a = ["Repeats Should End After How Many Times:","1","2","3","4", "5", "6", "7", "8", "9", "10"]
    var scheduleEndingField2a: UITextField = UITextField()
    
    var endingPicker2b: UIDatePicker! = UIDatePicker()
    var scheduleEndingField2b: UITextField = UITextField()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        view.backgroundColor = .white

        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        let scheduleTitle = UITextField(frame: CGRect(x: 0, y: barHeight, width: displayWidth, height: 50))
        scheduleTitle.textAlignment = NSTextAlignment.center
        scheduleTitle.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleTitle.placeholder = "Activity Title"
        scheduleTitle.borderStyle = UITextBorderStyle.line
        scheduleTitle.layer.borderWidth = 1
        scheduleTitle.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        scheduleTitle.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(scheduleTitle)
        
        
        let scheduleDesc = UITextField(frame: CGRect(x: 0, y: barHeight + 50, width: displayWidth, height: 50))
        scheduleDesc.textAlignment = NSTextAlignment.center
        scheduleDesc.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleDesc.placeholder = "Activity Description"
        scheduleDesc.borderStyle = UITextBorderStyle.line
        scheduleDesc.layer.borderWidth = 1
        scheduleDesc.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        scheduleDesc.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.view.addSubview(scheduleDesc)
        
        let items = ["Meds", "Exercise", "Food", "Blood Glucose"]
        let segmentedControl = UISegmentedControl(items: items)
        segmentedControl.frame = CGRect(x: 0, y: barHeight + 100, width: displayWidth, height: 50)
        segmentedControl.addTarget(self, action: #selector(actTypeTapped(_:)), for: .valueChanged)
        segmentedControl.selectedSegmentIndex = 1
        view.addSubview(segmentedControl)
        
        exercisePicker = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        exercisePicker.delegate = self
        exercisePicker.dataSource = self
        
        scheduleDuration = UITextField(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 50))
        scheduleDuration.textAlignment = NSTextAlignment.center
        scheduleDuration.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleDuration.placeholder = "Exercise Duration"
        scheduleDuration.borderStyle = UITextBorderStyle.line
        scheduleDuration.layer.borderWidth = 1
        scheduleDuration.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
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
        scheduleRecurringType.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleRecurringType.placeholder = "Activity Frequency"
        scheduleRecurringType.borderStyle = UITextBorderStyle.line
        scheduleRecurringType.layer.borderWidth = 1
        scheduleRecurringType.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        scheduleRecurringType.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleRecurringType.inputView = self.frequencyPicker1
        self.view.addSubview(scheduleRecurringType)
        
        
        // Frequency 2 Step
        frequencyPickerOptionEveryXDays = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        frequencyPickerOptionEveryXDays.delegate = self
        frequencyPickerOptionEveryXDays.dataSource = self
        
        scheduleRecurringOptionEveryXDays = UITextField(frame: CGRect(x: 0, y: barHeight + 250, width: displayWidth, height: 50))
        scheduleRecurringOptionEveryXDays.textAlignment = NSTextAlignment.center
        scheduleRecurringOptionEveryXDays.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleRecurringOptionEveryXDays.placeholder = "Activity Frequency Details"
        scheduleRecurringOptionEveryXDays.borderStyle = UITextBorderStyle.line
        scheduleRecurringOptionEveryXDays.layer.borderWidth = 1
        scheduleRecurringOptionEveryXDays.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        scheduleRecurringOptionEveryXDays.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleRecurringOptionEveryXDays.inputView = self.frequencyPickerOptionEveryXDays
        scheduleRecurringOptionEveryXDays.isHidden = true
        self.view.addSubview(scheduleRecurringOptionEveryXDays)
        
        monButton = UIButton(frame: CGRect(x: 0, y: barHeight + 250, width: displayWidth/7, height: 50))
        monButton.backgroundColor = .clear
        monButton.setBackgroundImage(getImageWithColor(color: UIColor.blue, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        monButton.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        monButton.layer.borderWidth = 1
        monButton.setTitle("Mon", for: .normal)
        monButton.setTitleColor(UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)), for: .normal)
        monButton.addTarget(self, action:#selector(self.MonButtonClicked), for: .touchUpInside)
        monButton.isHidden = true
        self.view.addSubview(monButton)
        
        tueButton = UIButton(frame: CGRect(x: displayWidth/7, y: barHeight + 250, width: displayWidth/7, height: 50))
        tueButton.backgroundColor = .clear
        tueButton.setBackgroundImage(getImageWithColor(color: UIColor.blue, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        tueButton.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        tueButton.layer.borderWidth = 1
        tueButton.setTitle("Tue", for: .normal)
        tueButton.setTitleColor(UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)), for: .normal)
        tueButton.addTarget(self, action:#selector(self.TueButtonClicked), for: .touchUpInside)
        tueButton.isHidden = true
        self.view.addSubview(tueButton)
        
        wedButton = UIButton(frame: CGRect(x: (displayWidth/7)*2, y: barHeight + 250, width: displayWidth/7, height: 50))
        wedButton.backgroundColor = .clear
        wedButton.setBackgroundImage(getImageWithColor(color: UIColor.blue, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        wedButton.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        wedButton.layer.borderWidth = 1
        wedButton.setTitle("Wed", for: .normal)
        wedButton.setTitleColor(UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)), for: .normal)
        wedButton.addTarget(self, action:#selector(self.WedButtonClicked), for: .touchUpInside)
        wedButton.isHidden = true
        self.view.addSubview(wedButton)
        
        thuButton = UIButton(frame: CGRect(x: (displayWidth/7)*3, y: barHeight + 250, width: displayWidth/7, height: 50))
        thuButton.backgroundColor = .clear
        thuButton.setBackgroundImage(getImageWithColor(color: UIColor.blue, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        tueButton.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        thuButton.layer.borderWidth = 1
        thuButton.setTitle("Thu", for: .normal)
        thuButton.setTitleColor(UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)), for: .normal)
        thuButton.addTarget(self, action:#selector(self.ThuButtonClicked), for: .touchUpInside)
        thuButton.isHidden = true
        self.view.addSubview(thuButton)
        
        friButton = UIButton(frame: CGRect(x: (displayWidth/7)*4, y: barHeight + 250, width: displayWidth/7, height: 50))
        friButton.backgroundColor = .clear
        friButton.setBackgroundImage(getImageWithColor(color: UIColor.blue, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        friButton.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        friButton.layer.borderWidth = 1
        friButton.setTitle("Fri", for: .normal)
        friButton.setTitleColor(UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)), for: .normal)
        friButton.addTarget(self, action:#selector(self.FriButtonClicked), for: .touchUpInside)
        friButton.isHidden = true
        self.view.addSubview(friButton)
        
        satButton = UIButton(frame: CGRect(x: (displayWidth/7)*5, y: barHeight + 250, width: displayWidth/7, height: 50))
        satButton.backgroundColor = .clear
        satButton.setBackgroundImage(getImageWithColor(color: UIColor.blue, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        satButton.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        satButton.layer.borderWidth = 1
        satButton.setTitle("Sat", for: .normal)
        satButton.setTitleColor(UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)), for: .normal)
        satButton.addTarget(self, action:#selector(self.SatButtonClicked), for: .touchUpInside)
        satButton.isHidden = true
        self.view.addSubview(satButton)
        
        sunButton = UIButton(frame: CGRect(x: (displayWidth/7)*6, y: barHeight + 250, width: displayWidth/7, height: 50))
        sunButton.backgroundColor = .clear
        sunButton.setBackgroundImage(getImageWithColor(color: UIColor.blue, size: CGSize(width: displayWidth/7, height: 50)), for: .selected)
        sunButton.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        sunButton.layer.borderWidth = 1
        sunButton.setTitle("Sun", for: .normal)
        sunButton.setTitleColor(UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)), for: .normal)
        sunButton.addTarget(self, action:#selector(self.SunButtonClicked), for: .touchUpInside)
        sunButton.isHidden = true
        self.view.addSubview(sunButton)
        
    
        frequencyPickerOption2b = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        frequencyPickerOption2b.delegate = self
        frequencyPickerOption2b.dataSource = self
        
        scheduleRecurringOption2b = UITextField(frame: CGRect(x: 0, y: barHeight + 250, width: displayWidth, height: 50))
        scheduleRecurringOption2b.textAlignment = NSTextAlignment.center
        scheduleRecurringOption2b.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleRecurringOption2b.placeholder = "Activity Frequency Details"
        scheduleRecurringOption2b.borderStyle = UITextBorderStyle.line
        scheduleRecurringOption2b.layer.borderWidth = 1
        scheduleRecurringOption2b.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        scheduleRecurringOption2b.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleRecurringOption2b.inputView = self.frequencyPickerOption2b
        scheduleRecurringOption2b.isHidden = true
        self.view.addSubview(scheduleRecurringOption2b)
        
        frequencyPickerOption2c = UIPickerView()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        frequencyPickerOption2c.delegate = self
        frequencyPickerOption2c.dataSource = self
        
        scheduleRecurringOption2c = UITextField(frame: CGRect(x: 0, y: barHeight + 250, width: displayWidth, height: 50))
        scheduleRecurringOption2c.textAlignment = NSTextAlignment.center
        scheduleRecurringOption2c.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleRecurringOption2c.placeholder = "Activity Frequency Details"
        scheduleRecurringOption2c.borderStyle = UITextBorderStyle.line
        scheduleRecurringOption2c.layer.borderWidth = 1
        scheduleRecurringOption2c.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
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
        scheduleEndingField.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleEndingField.placeholder = "Activity Ending Details"
        scheduleEndingField.borderStyle = UITextBorderStyle.line
        scheduleEndingField.layer.borderWidth = 1
        scheduleEndingField.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
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
        scheduleEndingField2a.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleEndingField2a.placeholder = "Ending After How Many Times?"
        scheduleEndingField2a.borderStyle = UITextBorderStyle.line
        scheduleEndingField2a.layer.borderWidth = 1
        scheduleEndingField2a.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        scheduleEndingField2a.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleEndingField2a.inputView = self.endingPicker2a
        scheduleEndingField2a.isHidden = true
        self.view.addSubview(scheduleEndingField2a)
        
        // Ending Step2b
        endingPicker2b = UIDatePicker()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        endingPicker2b.timeZone = NSTimeZone.local
        endingPicker2b.addTarget(self, action: #selector(datePickerValueChanged(_:)), for: .valueChanged)
//        endingPicker2b.delegate = self
//        endingPicker2b.dataSource = self
        
        scheduleEndingField2b = UITextField(frame: CGRect(x: 0, y: barHeight + 350, width: displayWidth, height: 50))
        scheduleEndingField2b.textAlignment = NSTextAlignment.center
        scheduleEndingField2b.textColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
        scheduleEndingField2b.placeholder = "Ending On What Date?"
        scheduleEndingField2b.borderStyle = UITextBorderStyle.line
        scheduleEndingField2b.layer.borderWidth = 1
        scheduleEndingField2b.layer.borderColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0)).cgColor
        scheduleEndingField2b.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.scheduleEndingField2b.inputView = self.endingPicker2b
        scheduleEndingField2b.isHidden = true
        self.view.addSubview(scheduleEndingField2b)
        
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
        
        // Create date formatter
        let dateFormatter: DateFormatter = DateFormatter()
        // Set date format
        dateFormatter.dateFormat = "MM/dd/yyyy hh:mm a"
        // Apply date format
        let selectedDate: String = dateFormatter.string(from: sender.date)
        print("Selected value \(selectedDate)")
        scheduleEndingField2b.text = selectedDate
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
        
        
        if (frequencyPicker1 == pickerView) {
            scheduleRecurringType.text = frequencyValues1[row]
            if (row == 0) {
                pickerView.selectRow(row + 1, inComponent:component, animated:true)
                scheduleRecurringType.text = frequencyValues1[1]
            }
            
            if (row == 1 || row == 2) {
                scheduleEndingField.isHidden = true
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
            if (row == 2) {
                scheduleEndingField2a.isHidden = false
            } else {
                scheduleEndingField2a.isHidden = true
            }
            
            // Selected "Repeats Should End: On a Certain Date"
            if (row == 3) {
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
