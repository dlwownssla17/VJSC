//
//  CheckIn.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/26/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class CheckInViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    public var ObjectsArray = [ScheduleItem]()

    private var myTableView: UITableView!
    
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
        act1.scheduleItemStart = Date() as NSDate
        
        act2.scheduleItemTitle = "Eat"
        act2.scheduleItemStart = Date() as NSDate
        
        act3.scheduleItemTitle = "Code"
        act3.scheduleItemStart = Date() as NSDate
        
        ObjectsArray.append(act1)
        ObjectsArray.append(act2)
        ObjectsArray.append(act3)

        
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
        
        // http://stackoverflow.com/questions/36394997/uialertview-was-deprecated-in-ios-9-0-use-uialertcontroller-with-a-preferreds
        
        let alertController = UIAlertController(title: "Congratulations!", message: "You completed this Activity - \(ObjectsArray[indexPath.row].scheduleItemTitle)", preferredStyle: UIAlertControllerStyle.alert)
        
        let okAction = UIAlertAction(title: "Done", style: UIAlertActionStyle.default)
        {
            (result : UIAlertAction) -> Void in
            print("You pressed OK")
        }
        alertController.addAction(okAction)
        self.present(alertController, animated: true, completion: nil)
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
        
        return cell
    }
}
