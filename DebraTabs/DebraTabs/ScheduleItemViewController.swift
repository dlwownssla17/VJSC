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
        
        myTableView = UITableView(frame: CGRect(x: 0, y: barHeight + 50, width: displayWidth, height: displayHeight - 200))//barHeight*1.5))
        print("yo")
        print(displayHeight)
        print(barHeight)
        myTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        myTableView.dataSource = self
        myTableView.delegate = self
        self.view.addSubview(myTableView)
        
        print("HELLO")
        JSONParser.testJSON()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return ObjectsArray.count
    }
    
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
