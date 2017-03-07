//
//  ScheduleItemViewController.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 3/7/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit

class ScheduleItemViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    //    var detailViewController: DetailViewController? = nil
    //    var objects = [Any]()
    
    public var Array: NSArray = ["Run","Eat","Code"]
    public var Array2: NSArray = ["2:00 PM", "1:00 AM", "Forever"]
    private var myTableView: UITableView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = .white
        
        
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
        
        
        
        //        // Do any additional setup after loading the view, typically from a nib.
        //        self.navigationItem.leftBarButtonItem = self.editButtonItem
        //
        //        //let addButton = UIBarButtonItem(barButtonSystemItem: .add, target: self, action: #selector(insertNewObject(_:)))
        //        let addButton = UIBarButtonItem(barButtonSystemItem: .add, target: self, action: #selector(insertNewScheduleItemForm(_:)))
        //        self.navigationItem.rightBarButtonItem = addButton
        //        if let split = self.splitViewController {
        //            let controllers = split.viewControllers
        //            self.detailViewController = (controllers[controllers.count-1] as! UINavigationController).topViewController as? DetailViewController
        //        }
        print("HELLO")
        JSONParser.testJSON()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return Array.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //        let cell = tableView.dequeueReusableCell(withIdentifier: "MyCell", for: indexPath as IndexPath)
        //        cell.textLabel!.text = "\(Array[indexPath.row])"
        //        return cell
        
        
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")
        
        cell.textLabel!.text = "\(Array[indexPath.row])"
        cell.detailTextLabel?.text = "\(Array2[indexPath.row])"
        
        return cell
    }
    
}
