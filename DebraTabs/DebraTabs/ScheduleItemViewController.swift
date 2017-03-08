//
//  ScheduleItemViewController.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 3/7/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit

class ScheduleItemViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    
    public var ObjectsArray = [ScheduleItem]()
    private var myTableView: UITableView!
    public var button: UIButton = UIButton()
    var currentDate: UITextField = UITextField()
    public var calendarButton: UIButton = UIButton()
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var calendarPicker: UIDatePicker! = UIDatePicker()

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = .white
        
        let jsonString1 = "{" +
            "\"Language\": {" +
            "\"Field\":[" +
            "{" +
            "\"Number\":\"976\"," +
            "\"Name\":\"Test\"" +
            "}," +
            "{" +
            "\"Number\":\"977\"," +
            "\"Name\":\"Test\"" +
            "}" +
            "]" +
            "}" +
        "}"
        
        var act1 = ScheduleItem(jsonString: jsonString1)
        var act2 = ScheduleItem(jsonString: jsonString1)
        var act3 = ScheduleItem(jsonString: jsonString1)
        
        
        // For Testing purposes
        act1.scheduleItemTitle = "Run"
        act1.scheduleItemStart = (Date() as NSDate) as Date
        
        act2.scheduleItemTitle = "Eat"
        act2.scheduleItemStart = (Date() as NSDate) as Date
        
        act3.scheduleItemTitle = "Code"
        act3.scheduleItemStart = (Date() as NSDate) as Date
        
        ObjectsArray.append(act1)
        ObjectsArray.append(act2)
        ObjectsArray.append(act3)
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        calendarPicker = UIDatePicker()//(frame: CGRect(x: 0, y: barHeight + 150, width: displayWidth, height: 280.0))
        calendarPicker.timeZone = NSTimeZone.local
        calendarPicker.datePickerMode = UIDatePickerMode.date

        calendarPicker.addTarget(self, action: #selector(datePickerValueChanged(_:)), for: .valueChanged)
        let toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.default
        toolBar.isTranslucent = true
        toolBar.tintColor = blueColor //UIColor(red: 76/255, green: 217/255, blue: 100/255, alpha: 1)
        toolBar.sizeToFit()
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItemStyle.plain, target: self, action: #selector(calendarButtonTapped(_:)))
        toolBar.setItems([doneButton], animated: false)
        toolBar.isUserInteractionEnabled = true
        
        
        currentDate = UITextField(frame: CGRect(x: 0, y: barHeight, width: displayWidth, height: 50))
        currentDate.textAlignment = NSTextAlignment.center
        currentDate.textColor = UIColor.black
        currentDate.text = "Today's Date (tap to change day)"
        currentDate.borderStyle = UITextBorderStyle.line
        currentDate.layer.borderWidth = 1
        currentDate.layer.borderColor = blueColor.cgColor
        currentDate.autocapitalizationType = UITextAutocapitalizationType.words // If you need any capitalization
        self.currentDate.inputView = self.calendarPicker
        currentDate.inputAccessoryView = toolBar
        self.view.addSubview(currentDate)
        
        myTableView = UITableView(frame: CGRect(x: 0, y: barHeight + 50, width: displayWidth, height: displayHeight - 200))//barHeight*1.5))
        print("yo")
        print(displayHeight)
        print(barHeight)
        myTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        myTableView.dataSource = self
        myTableView.delegate = self
        
        myTableView.layer.masksToBounds = true
        myTableView.layer.borderColor = blueColor.cgColor
        myTableView.layer.borderWidth = 2.0
        
        self.view.addSubview(myTableView)
        
        // Buttons
        button = UIButton(frame: CGRect(x: displayWidth/2, y: displayHeight - 110, width: 100, height: 44))
        button.setTitle("Add Item", for: UIControlState.normal)
        button.setTitleColor(UIColor.blue, for: UIControlState.normal)
        button.backgroundColor = UIColor.clear
        button.layer.borderWidth = 1.0
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        button.layer.borderColor = UIColor.blue.cgColor
        button.layer.cornerRadius = cornerRadius
        button.addTarget(self, action: #selector(addButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(button)
        
        print("HELLO")
        JSONParser.testJSON()
    }
    
    // MARK: Button Action
    func addButtonTapped(_ button: UIButton) {
        button.transform = CGAffineTransform(scaleX: 0.1, y: 0.1)
        UIView.animate(withDuration: 2.0,
                       delay: 0,
                       usingSpringWithDamping: 0.2,
                       initialSpringVelocity: 6.0,
                       options: .allowUserInteraction,
                       animations: { [weak self] in
                        self?.button.transform = .identity
            },
                       completion: nil)
        
        print("Add Schedule Item Button pressed")
        
        
        let secondViewController:AddNewItem = AddNewItem()
        self.present(secondViewController, animated: true, completion: nil)
        
    }
    
    // MARK: Button Action
    func calendarButtonTapped(_ button: UIButton) {
        
        currentDate.resignFirstResponder()
        
        print("Calendar Button pressed")
        
        
        // view for picker
        
    }
    
    func datePickerValueChanged(_ sender: UIDatePicker){
        if (sender == calendarPicker) {
            // Create date formatter
            let dateFormatter: DateFormatter = DateFormatter()
            // Set date format
            dateFormatter.dateFormat = "MMM d, yyyy"
            // Apply date format
            let selectedDate: String = dateFormatter.string(from: sender.date)
            print("Selected value \(selectedDate)")
            currentDate.text = selectedDate
        }
    }
    
    func textFieldDidBeginEditing(textField: UITextField) { // became first responder
        
        //move textfields up
        let myScreenRect: CGRect = UIScreen.main.bounds
        let keyboardHeight : CGFloat = 216
        
        UIView.beginAnimations( "animateView", context: nil)
        var movementDuration:TimeInterval = 0.35
        var needToMove: CGFloat = 0
        
        var frame : CGRect = self.view.frame
        if (textField.frame.origin.y + textField.frame.size.height + /*self.navigationController.navigationBar.frame.size.height + */UIApplication.shared.statusBarFrame.size.height > (myScreenRect.size.height - keyboardHeight)) {
            needToMove = (textField.frame.origin.y + textField.frame.size.height + /*self.navigationController.navigationBar.frame.size.height +*/ UIApplication.shared.statusBarFrame.size.height) - (myScreenRect.size.height - keyboardHeight);
        }
        
        frame.origin.y = -needToMove
        self.view.frame = frame
        UIView.commitAnimations()
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        //move textfields back down
        UIView.beginAnimations( "animateView", context: nil)
        var movementDuration:TimeInterval = 0.35
        var frame : CGRect = self.view.frame
        frame.origin.y = 0
        self.view.frame = frame
        UIView.commitAnimations()
    }

    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return ObjectsArray.count
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {
        
        let edit = UITableViewRowAction(style: .normal, title: "Edit") { action, index in
            print("edit button tapped")
            print(self.ObjectsArray[indexPath.row].scheduleItemType)
            var secondViewController:EditItemViewController = EditItemViewController()
            // activity title
            secondViewController.ActivityTitle = self.ObjectsArray[indexPath.row].scheduleItemTitle
            secondViewController.ActivityDescription = self.ObjectsArray[indexPath.row].scheduleItemDescription
            secondViewController.ActivityTypeIndex = 0
            if (secondViewController.ActivityTypeIndex == 1) {
                secondViewController.ActivityDuration = "5"
            }
            let dateFormatter: DateFormatter = DateFormatter()
            dateFormatter.dateFormat = "hh:mm a"
            let selectedDate: String = dateFormatter.string(from: self.ObjectsArray[indexPath.row].scheduleItemStart as Date)
            secondViewController.ActivityStartTime = selectedDate
            // activity start time
            self.present(secondViewController, animated: true, completion: nil)
        }
        edit.backgroundColor = .orange
        
        let delete = UITableViewRowAction(style: .normal, title: "Delete") { action, index in
            print("delete button tapped")
            // handle delete (by removing the data from your array and updating the tableview)
            //            // SEND DELETE COMMAND TO BACKEND
            //
            ////            myTableView.beginUpdates()
            ////            ObjectsArray.remove(at: indexPath.row)
            ////            myTableView.deleteRows(at: [indexPath], with: UITableViewRowAnimation.automatic)
            ////            myTableView.endUpdates()

        }
        delete.backgroundColor = .red
    
        
        return [delete, edit]
    }
    
//    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
//        if (editingStyle == UITableViewCellEditingStyle.delete) {
//            // handle delete (by removing the data from your array and updating the tableview)
//            // SEND DELETE COMMAND TO BACKEND
//            
////            myTableView.beginUpdates()
////            ObjectsArray.remove(at: indexPath.row)
////            myTableView.deleteRows(at: [indexPath], with: UITableViewRowAnimation.automatic)
////            myTableView.endUpdates()
//        }
//    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //        let cell = tableView.dequeueReusableCell(withIdentifier: "MyCell", for: indexPath as IndexPath)
        //        cell.textLabel!.text = "\(Array[indexPath.row])"
        //        return cell
        
        
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")

        cell.textLabel!.text = "\(ObjectsArray[indexPath.row].scheduleItemTitle)"
        cell.detailTextLabel?.text = "\(ObjectsArray[indexPath.row].scheduleItemStart)"
        
        return cell
    }
    
}
