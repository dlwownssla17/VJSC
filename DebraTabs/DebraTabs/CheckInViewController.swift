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
    
    public var Array: NSArray = ["Run","Eat","Code"]
    public var Array2: NSArray = ["2:00 PM", "1:00 AM", "Forever"]
    //    public var indexOfNumbers: NSArray = ["1", "2", "3", "4", "5"]
    private var myTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
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
        print("Value: \(Array[indexPath.row])")
        
        // http://stackoverflow.com/questions/36394997/uialertview-was-deprecated-in-ios-9-0-use-uialertcontroller-with-a-preferreds
        
        let alertController = UIAlertController(title: "Congratulations!", message: "You completed this Activity - \(Array[indexPath.row])", preferredStyle: UIAlertControllerStyle.alert)
        
        let okAction = UIAlertAction(title: "Done", style: UIAlertActionStyle.default)
        {
            (result : UIAlertAction) -> Void in
            print("You pressed OK")
        }
        alertController.addAction(okAction)
        self.present(alertController, animated: true, completion: nil)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return Array.count
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
        
        cell.textLabel!.text = "\(Array[indexPath.row])"
        cell.detailTextLabel?.text = "\(Array2[indexPath.row])"
        
        return cell
    }
}
