//
//  CheckIn.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/26/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class CheckInViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    public var ObjectsArray = [ScheduleItem]()
    var codedLabel: UILabel = UILabel()


    private var myTableView: UITableView!
    
    var currentDayInfo:CurrentDayInfo = CurrentDayInfo()
    
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
        
        //ObjectsArray.append(act1)
        //ObjectsArray.append(act2)
        //ObjectsArray.append(act3)

        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        myTableView = UITableView(frame: CGRect(x: 0, y: barHeight, width: displayWidth, height: displayHeight - barHeight))
        myTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        myTableView.dataSource = self
        myTableView.delegate = self
        self.view.addSubview(myTableView)
        
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("Num: \(indexPath.row)")
        print("Value: \(ObjectsArray[indexPath.row].scheduleItemTitle)")
        print("Type: \(ScheduleItemType.ScheduleItemTypeStringMap[self.ObjectsArray[indexPath.row].scheduleItemType]!)")
        
        
        // put slider here, that goes between 0 and duration value
        let typeString = ScheduleItemType.ScheduleItemTypeStringMap[self.ObjectsArray[indexPath.row].scheduleItemType]!
        var itemDuration = 0 // change back to 0
        if (typeString == "Exercise") {
            itemDuration = ObjectsArray[indexPath.row].scheduleItemDuration
            print(itemDuration)
            print("heyyheherherherh")
        }

        
        // http://stackoverflow.com/questions/36394997/uialertview-was-deprecated-in-ios-9-0-use-uialertcontroller-with-a-preferreds
        
        //START
        if self.ObjectsArray[indexPath.row].scheduleItemCheckedInAtStart {
        var myFrame = CGRect(x: 10.0, y: 10.0, width: 250.0, height: 10.0)
        var slider = UISlider(frame: myFrame)
        
        
        let alertController = UIAlertController(title: "Congratulations!", message: "You completed this Activity - \(self.ObjectsArray[indexPath.row].scheduleItemTitle)", preferredStyle: UIAlertControllerStyle.alert)
        
        let okAction = UIAlertAction(title: "Done", style: UIAlertActionStyle.default)
        {
            (result : UIAlertAction) -> Void in
            print("You pressed OK")
            let dur = self.ObjectsArray[indexPath.row].scheduleItemDuration
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            var parameters = [JSONProtocolNames.scheduleItemIDHeaderName: String(self.ObjectsArray[indexPath.row].itemID)]
            if self.ObjectsArray[indexPath.row].scheduleItemCheckedInAtStart {
                parameters[JSONProtocolNames.progressHeaderName] = String(Double(slider.value) / Double(dur))
            } else {
                parameters[JSONProtocolNames.progressHeaderName] = String(-1)
            }
            Alamofire.request(Settings.getCheckinURL(), method: .post, parameters: parameters, headers: headers)
                .responseString { response in
                    switch response.result {
                    case .success(let _):
                        print("TEST SPIRO CHECKING")
                        let alertController2 = UIAlertController(title: "Congratulations!", message: "You completed this Activity - \(self.ObjectsArray[indexPath.row].scheduleItemTitle)", preferredStyle: UIAlertControllerStyle.alert)
                        
                        let okAction = UIAlertAction(title: "Done", style: UIAlertActionStyle.default)
                        {
                            (result : UIAlertAction) -> Void in
                            print("You pressed OK")
                        }
                        alertController2.addAction(okAction)
                        
                        self.present(alertController2, animated: true, completion: nil)
                    case .failure(let error):
                        print("Request failed with error: \(error)")
                    }
            }
        }
        
        
        if (self.ObjectsArray[indexPath.row].scheduleItemCheckedInAtStart) {
            //                        var view = UIView();
            var view = UIViewController();
            view.preferredContentSize = CGSize(width: 250,height: 100)
            //                        view.view.frame = CGRect(x: 0, y:0 , width: 250.0, height: 250.0)
            //var myFrame = CGRect(x: 10.0, y: 10.0, width: 250.0, height: 10.0)
            //var slider = UISlider(frame: myFrame)
            slider.minimumValue = 0
            slider.maximumValue = Float(itemDuration)
            slider.value = 0
            slider.addTarget(self, action:#selector(self.sliderValueDidChange), for: .valueChanged)
            view.view.addSubview(slider)
            
            self.codedLabel.frame = CGRect(x: 0, y: 15, width: 250, height: 55)
            self.codedLabel.textAlignment = .center
            self.codedLabel.text = "Minutes Completed: 0"
            self.codedLabel.numberOfLines = 5
            self.codedLabel.textColor = UIColor.black
            self.codedLabel.font=UIFont.systemFont(ofSize: 15)
            self.codedLabel.backgroundColor=UIColor.clear
            //                        self.codedLabel.translatesAutoresizingMaskIntoConstraints = false
            //                        codedLabel.heightAnchor.constraint(equalToConstant: 200).isActive = true
            //                        self.codedLabel.leftAnchor.constraintEqualToAnchor(NSNumber(10)).isActive = true
            
            
            
            //                            .constraint(equalToConstant: 0).isActive = true
            //                        self.codedLabel.centerXAnchor.constraint(equalTo: self.codedLabel.superview!.centerXAnchor).isActive = true
            
            view.view.addSubview(self.codedLabel)
            
            alertController.setValue(view, forKey: "contentViewController")
            //                        alertController.view.addSubview(view.view)
        }
        
        alertController.addAction(okAction)
        
        self.present(alertController, animated: true, completion: nil)
        } else {
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            var parameters = [JSONProtocolNames.scheduleItemIDHeaderName: String(self.ObjectsArray[indexPath.row].itemID)]
            parameters[JSONProtocolNames.progressHeaderName] = String(-1)
            Alamofire.request(Settings.getCheckinURL(), method: .post, parameters: parameters, headers: headers)
                .responseString { response in
                    switch response.result {
                    case .success(let _):
                        print("TEST SPIRO CHECKING")
                        let alertController2 = UIAlertController(title: "Congratulations!", message: "You completed this Activity - \(self.ObjectsArray[indexPath.row].scheduleItemTitle)", preferredStyle: UIAlertControllerStyle.alert)
                        
                        let okAction = UIAlertAction(title: "Done", style: UIAlertActionStyle.default)
                        {
                            (result : UIAlertAction) -> Void in
                            print("You pressed OK")
                        }
                        alertController2.addAction(okAction)
                        
                        self.present(alertController2, animated: true, completion: nil)
                    case .failure(let error):
                        print("Request failed with error: \(error)")
                    }
            }
        }
        //END
        
//        Alamofire.request(Settings.getCheckinURL(userID: Settings.usernameString, itemID: ObjectsArray[indexPath.row].itemID), method: .post)
//            .responseString { response in
//                switch response.result {
//                case .success(let _):
//                    print("TEST SPIRO CHECKING")
//                    let alertController2 = UIAlertController(title: "Congratulations!", message: "You completed this Activity - \(self.ObjectsArray[indexPath.row].scheduleItemTitle)", preferredStyle: UIAlertControllerStyle.alert)
//                    
//                    let okAction = UIAlertAction(title: "Done", style: UIAlertActionStyle.default)
//                    {
//                        (result : UIAlertAction) -> Void in
//                        print("You pressed OK")
//                    }
//                    
//                    
////                    if (itemDuration != 0) {
//////                        var view = UIView();
////                        var view = UIViewController();
////                        view.preferredContentSize = CGSize(width: 250,height: 100)
//////                        view.view.frame = CGRect(x: 0, y:0 , width: 250.0, height: 250.0)
////                        var myFrame = CGRect(x: 10.0, y: 10.0, width: 250.0, height: 10.0)
////                        var slider = UISlider(frame: myFrame)
////                        slider.minimumValue = 0
////                        slider.maximumValue = Float(itemDuration)
////                        slider.value = 0
////                        slider.addTarget(self, action:#selector(self.sliderValueDidChange), for: .valueChanged)
////                        view.view.addSubview(slider)
////                        
////                        self.codedLabel.frame = CGRect(x: 0, y: 15, width: 250, height: 55)
////                        self.codedLabel.textAlignment = .center
////                        self.codedLabel.text = "Minutes Completed: 0"
////                        self.codedLabel.numberOfLines = 5
////                        self.codedLabel.textColor = UIColor.black
////                        self.codedLabel.font=UIFont.systemFont(ofSize: 15)
////                        self.codedLabel.backgroundColor=UIColor.clear
//////                        self.codedLabel.translatesAutoresizingMaskIntoConstraints = false
//////                        codedLabel.heightAnchor.constraint(equalToConstant: 200).isActive = true
//////                        self.codedLabel.leftAnchor.constraintEqualToAnchor(NSNumber(10)).isActive = true
////
////                            
////                            
//////                            .constraint(equalToConstant: 0).isActive = true
//////                        self.codedLabel.centerXAnchor.constraint(equalTo: self.codedLabel.superview!.centerXAnchor).isActive = true
////                        
////                        view.view.addSubview(self.codedLabel)
////                        
////                        alertController.setValue(view, forKey: "contentViewController")
//////                        alertController.view.addSubview(view.view)
////                    }
//
//                    alertController2.addAction(okAction)
//                    
//                    self.present(alertController2, animated: true, completion: nil)
//                case .failure(let error):
//                    print("Request failed with error: \(error)")
//                }
//        }
        
    }
    
    func sliderValueDidChange(sender:UISlider!) {
        self.codedLabel.text = "Minutes Completed: \(sender.value)"
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return ObjectsArray.count
    }
    
    //    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    //        // Return the number of rows in the section.
    //        return 1
    //    }
    //
    //    func numberOfSections(in tableView: UITableView) -> Int {
    //        // Return the number of sections.
    //        return indexOfNumbers.count
    //    }
    //
    //    func sectionIndexTitlesForTableView(_ tableView: UITableView) -> [AnyObject]! {
    //        return indexOfNumbers as [AnyObject]!
    //    }
    //
    //    func tableView(_ tableView: UITableView, sectionForSectionIndexTitle title: String, at index: Int) -> Int {
    //        var temp = indexOfNumbers as NSArray
    //        return temp.index(of: title)
    //   }
    //
    //     func numberOfSections(in tableView: UITableView) -> Int {
    //        return Array.count
    //    }
    //
    //    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
    //        return "Section \(section)"
    //    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //        let cell = tableView.dequeueReusableCell(withIdentifier: "MyCell", for: indexPath as IndexPath)
        //        cell.textLabel!.text = "\(Array[indexPath.row])"
        //        return cell
        
        
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")
        
        cell.textLabel!.text = "\(ObjectsArray[indexPath.row].scheduleItemTitle)"
        cell.detailTextLabel?.text = "\(ObjectsArray[indexPath.row].scheduleItemStart)"
        cell.isUserInteractionEnabled = ObjectsArray[indexPath.row].scheduleItemActive
        return cell
    }
    
    override func viewDidAppear(_ animated: Bool) {
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
        Alamofire.request(Settings.getCheckinViewURL(), method: .get, headers: headers).validate().responseJSON { response in
            switch response.result {
            case .success(let data):
                let json = JSON(data)
                print(json.count)
                print("TestCAT")
                let jsonObjectList = json[JSONProtocolNames.scheduleItemsListResponseName].arrayValue
                self.ObjectsArray = []
                for jsonObject in jsonObjectList {
                    let scheduleItemObject = ScheduleItem(json: jsonObject, itemDay: self.currentDayInfo.currentDayString)
                    self.ObjectsArray.append(scheduleItemObject)
                }
                self.myTableView.reloadData()
            //return scheduleItems
            case .failure(let error):
                print("Request failed with error: \(error)")
                self.ObjectsArray = []
            }
        }
    }
}
