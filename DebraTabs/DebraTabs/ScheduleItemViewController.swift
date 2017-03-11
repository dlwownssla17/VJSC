//
//  ScheduleItemViewController.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 3/7/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit
import Alamofire

class ScheduleItemViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    
    public var ObjectsArray = [ScheduleItem]()
    private var myTableView: UITableView!
    public var button: UIButton = UIButton()
    public var button2: UIButton = UIButton()
    var currentDate: UITextField = UITextField()
    public var calendarButton: UIButton = UIButton()
    var currentDateLabel:UILabel = UILabel()
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var calendarPicker: UIDatePicker! = UIDatePicker()
    
    var currentDayInfo:CurrentDayInfo = CurrentDayInfo()
    
    
    var barHeight: CGFloat = 0.0
    var displayWidth: CGFloat = 0.0

    
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
        
//        Alamofire.request("http://130.91.134.209:8000/test", method: .get).validate().responseJSON { response in
//            switch response.result {
//            case .success(let data):
//                let json = JSON(data)
//                print(json.count)
//                print("TestCAT")
//                let jsonObjectList = json[JSONProtocolNames.scheduleItemsListResponseName].arrayValue
//                self.ObjectsArray = []
//                for jsonObject in jsonObjectList {
//                    let scheduleItemObject = ScheduleItem(json: jsonObject)
//                    self.ObjectsArray.append(scheduleItemObject)
//                }
//                self.myTableView.reloadData()
//            //return scheduleItems
//            case .failure(let error):
//                print("Request failed with error: \(error)")
//                self.ObjectsArray = []
//            }
//        }
        
//        var act1 = ScheduleItem(jsonString: jsonString1)
//        var act2 = ScheduleItem(jsonString: jsonString1)
//        var act3 = ScheduleItem(jsonString: jsonString1)
//        
//        
//        // For Testing purposes
//        act1.scheduleItemTitle = "RunFaster"
//        act1.scheduleItemStart = (Date() as NSDate) as Date
//        
//        act2.scheduleItemTitle = "Eat"
//        act2.scheduleItemStart = (Date() as NSDate) as Date
//        
//        act3.scheduleItemTitle = "Code"
//        act3.scheduleItemStart = (Date() as NSDate) as Date
//        
//        ObjectsArray.append(act1)
//        ObjectsArray.append(act2)
//        ObjectsArray.append(act3)
        
        barHeight = UIApplication.shared.statusBarFrame.size.height
        displayWidth = self.view.frame.width
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
        //self.view.addSubview(currentDate)
        
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
        
        // Buttons
        button2 = UIButton(frame: CGRect(x: displayWidth/2 - 100, y: displayHeight - 110, width: 100, height: 44))
        button2.setTitle("Calendar", for: UIControlState.normal)
        button2.setTitleColor(UIColor.blue, for: UIControlState.normal)
        button2.backgroundColor = UIColor.clear
        button2.layer.borderWidth = 1.0
        button2.layer.borderColor = UIColor.blue.cgColor
        button2.layer.cornerRadius = cornerRadius
        button2.addTarget(self, action: #selector(calendarViewButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(button2)
        
        print("HELLO")
        JSONParser.testJSON()
        
        Requests.getDayViewRoot()
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
        secondViewController.currentDayInfo = currentDayInfo
        self.present(secondViewController, animated: true, completion: nil)
        
    }
    
    func calendarViewButtonTapped(_ button: UIButton) {
//        button.transform = CGAffineTransform(scaleX: 0.1, y: 0.1)
//        UIView.animate(withDuration: 2.0,
//                       delay: 0,
//                       usingSpringWithDamping: 0.2,
//                       initialSpringVelocity: 6.0,
//                       options: .allowUserInteraction,
//                       animations: { [weak self] in
//                        self?.button.transform = .identity
//            },
//                       completion: nil)
        
        print("Add Schedule Item Button pressed")
        
        
        let secondViewController:CalendarViewController = CalendarViewController()
        secondViewController.currentDayInfo = currentDayInfo
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
            secondViewController.currentDayInfo = self.currentDayInfo
            // activity title
            secondViewController.ActivityTitle = self.ObjectsArray[indexPath.row].scheduleItemTitle
            secondViewController.ActivityDescription = self.ObjectsArray[indexPath.row].scheduleItemDescription
            let selectedScheduleItemType = self.ObjectsArray[indexPath.row].scheduleItemType
            let selectedScheduleItemTypeString = ScheduleItemType.ScheduleItemTypeStringMap[selectedScheduleItemType]
            secondViewController.ActivityTypeIndex = ScheduleItemType.ScheduleItemTypeOrderedStringMap.index(of: selectedScheduleItemTypeString!)!
            secondViewController.ActivityID = self.ObjectsArray[indexPath.row].itemID
            if (selectedScheduleItemType == .Exercise) {
                secondViewController.ActivityDuration = self.ObjectsArray[indexPath.row].scheduleItemDuration
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
            
            
            let removeAlert = UIAlertController(title: "Remove Item", message: "Are you sure?", preferredStyle: UIAlertControllerStyle.alert)
            
            removeAlert.addAction(UIAlertAction(title: "Remove Just Today", style: .default, handler: { (action: UIAlertAction!) in
                let parameters = Requests.removeScheduleItem(item: self.ObjectsArray[indexPath.row], removeAllRecurring: false)
                //"http://130.91.134.209:8000/remove"
                Alamofire.request(Settings.getRemoveScheduleItemURL(userID: Settings.testUserID, date: self.currentDayInfo.currentDayString), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: nil)
                    .responseString { response in
                        switch response.result {
                        case .success(let _):
                            print("TEST SPIRO")
                            self.viewDidAppear(true)
                        case .failure(let error):
                            print("Request failed with error: \(error)")
                            let alert = UIAlertController(title: "Alert", message: "Could not delete item.", preferredStyle: UIAlertControllerStyle.alert)
                            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                            self.present(alert, animated: true, completion: nil)
                        }
                }
            }))
            
            if self.ObjectsArray[indexPath.row].recurringType != .NotRecurring {
                removeAlert.addAction(UIAlertAction(title: "Remove From Every Day", style: .default, handler: { (action: UIAlertAction!) in
                    let parameters = Requests.removeScheduleItem(item: self.ObjectsArray[indexPath.row], removeAllRecurring: true)
                    Alamofire.request(Settings.getRemoveScheduleItemURL(userID: Settings.testUserID, date: self.currentDayInfo.currentDayString), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: nil)
                        .responseString { response in
                            switch response.result {
                            case .success(let _):
                                print("TEST SPIRO")
                                self.viewDidAppear(true)
                            case .failure(let error):
                                print("Request failed with error: \(error)")
                                let alert = UIAlertController(title: "Alert", message: "Could not delete item.", preferredStyle: UIAlertControllerStyle.alert)
                                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                                self.present(alert, animated: true, completion: nil)
                            }
                    }
                }))
            }
            
            removeAlert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: { (action: UIAlertAction!) in
                return
            }))
            
            self.present(removeAlert, animated: true, completion: nil)

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
    
    override func viewDidAppear(_ animated: Bool) {
        //"http://130.91.134.209:8000/test"
        Alamofire.request(Settings.getDayViewURL(userID: Settings.testUserID, day: currentDayInfo.currentDayString), method: .get).validate().responseJSON { response in
            switch response.result {
            case .success(let data):
                let json = JSON(data)
                print(json.count)
                print("TestCAT")
                let jsonObjectList = json[JSONProtocolNames.scheduleItemsListResponseName].arrayValue
                self.ObjectsArray = []
                for jsonObject in jsonObjectList {
                    let scheduleItemObject = ScheduleItem(json: jsonObject)
                    self.ObjectsArray.append(scheduleItemObject)
                }
                self.myTableView.reloadData()
                
                self.currentDateLabel.removeFromSuperview()
                self.currentDateLabel.isHidden = true
                self.currentDateLabel = UILabel(frame: CGRect(x: 0, y: self.barHeight, width: self.displayWidth, height: 50))
                self.currentDateLabel.text = self.currentDayInfo.currentDayString
                self.view.addSubview(self.currentDateLabel)
            //return scheduleItems
            case .failure(let error):
                print("Request failed with error: \(error)")
                self.ObjectsArray = []
            }
        }
        print("FROM CALENDAR: " + currentDayInfo.currentDayString)
    }
    
}
