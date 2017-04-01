//
//  DataViewController.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/14/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class DataViewController: UIViewController , UITableViewDelegate, UITableViewDataSource {
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var createNewTeamButton: UIButton = UIButton()
    let teamStatusLabel:UILabel = UILabel()
    private var myTableView: UITableView!
    
    public var ObjectsArray = [ScheduleItem]()

    
    override func viewDidLoad() {
        super.viewDidLoad()
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        let codedLabel:UILabel = UILabel()
        codedLabel.frame = CGRect(x: 100, y: 100, width: displayWidth, height: 200)
        codedLabel.textAlignment = .center
        codedLabel.text = "Under Construction! Check back later please."
        codedLabel.numberOfLines = 5
        codedLabel.textColor = blueColor
        codedLabel.font=UIFont.systemFont(ofSize: 40)
        codedLabel.backgroundColor=UIColor.clear
        
//        self.view.addSubview(codedLabel)
//        codedLabel.translatesAutoresizingMaskIntoConstraints = false
//        codedLabel.heightAnchor.constraint(equalToConstant: 200).isActive = true
//        codedLabel.widthAnchor.constraint(equalToConstant: displayWidth).isActive = true
//        codedLabel.centerXAnchor.constraint(equalTo: codedLabel.superview!.centerXAnchor).isActive = true
//        codedLabel.centerYAnchor.constraint(equalTo: codedLabel.superview!.centerYAnchor).isActive = true
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        
        createNewTeamButton = UIButton(frame: CGRect(x: displayWidth/2, y: barHeight * 6, width: 200, height: 44))
        createNewTeamButton.center.x = self.view.center.x
        createNewTeamButton.setTitle("Create New Team", for: UIControlState.normal)
        createNewTeamButton.setTitleColor(blueColor, for: UIControlState.normal)
        createNewTeamButton.backgroundColor = UIColor.clear
        createNewTeamButton.layer.borderWidth = 1.0
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        createNewTeamButton.layer.borderColor = blueColor.cgColor
        createNewTeamButton.layer.cornerRadius = cornerRadius
        createNewTeamButton.addTarget(self, action: #selector(createNewTeamButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(createNewTeamButton)
        
        teamStatusLabel.frame = CGRect(x: 100, y: 20, width: displayWidth, height: 100)
        teamStatusLabel.textAlignment = .center
        teamStatusLabel.center.x = self.view.center.x
        teamStatusLabel.text = "No Team"
        teamStatusLabel.textColor = blueColor
        teamStatusLabel.font=UIFont.systemFont(ofSize: 40)
        teamStatusLabel.backgroundColor=UIColor.clear
        self.view.addSubview(teamStatusLabel)
        
        myTableView = UITableView(frame: CGRect(x: 0, y: barHeight * 10, width: displayWidth, height: displayHeight - barHeight * 8))
        myTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        myTableView.dataSource = self
        myTableView.delegate = self
        
        myTableView.layer.masksToBounds = true
        myTableView.layer.borderColor = blueColor.cgColor
        myTableView.layer.borderWidth = 2.0
        
        self.view.addSubview(myTableView)
        
        
    }
    
    func createNewTeamButtonPressed(_ button: UIButton) {
        let secondViewController:AddNewItem = AddNewItem()
        self.present(secondViewController, animated: true, completion: nil)
    }
    
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.myTableView.deselectRow(at: indexPath, animated: true)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        var numOfSections: Int = 0
        if ObjectsArray.count > 0
        {
            myTableView.separatorStyle = .singleLine
            numOfSections            = ObjectsArray.count
            myTableView.backgroundView = nil
        }
        else
        {
            let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            noDataLabel.text          = "No Team Requests"
            noDataLabel.textColor     = UIColor.black
            noDataLabel.textAlignment = .center
            myTableView.backgroundView  = noDataLabel
            myTableView.separatorStyle  = .none
        }
        return ObjectsArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")
        return cell
    }
    
}
