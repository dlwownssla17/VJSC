//
//  CalendarViewController.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/10/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class CalendarViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    public var ObjectsArray = [DayItem]()
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
    }
    
    func addButtonTapped(_ button: UIButton) {
    }
    
    func calendarButtonTapped(_ button: UIButton) {
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return ObjectsArray.count
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {
        return []
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")
        return cell
    }

}
