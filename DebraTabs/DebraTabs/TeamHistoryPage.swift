//
//  TeamHistoryPage.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class TeamHistoryPage:UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    private var teamHistoryTableView: UITableView!
    
    public var teamHistory:Array<TeamHistoryInfo> = []
    
    var cancelButton: UIButton = UIButton()
    
    let pageTitle:UILabel = UILabel()
    
    let cancelLabel:String = "Close"
    let screenTitle:String = "Team Competition History"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        
        teamHistoryTableView = UITableView(frame: CGRect(x: 0, y: 1 * self.view.frame.height / 6, width: displayWidth, height: 5 * self.view.frame.height / 6 - 100))
        teamHistoryTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        teamHistoryTableView.dataSource = self
        teamHistoryTableView.delegate = self
        
        teamHistoryTableView.layer.masksToBounds = true
        teamHistoryTableView.layer.borderColor = blueColor.cgColor
        teamHistoryTableView.layer.borderWidth = 2.0
        
        self.view.addSubview(teamHistoryTableView)
        
        cancelButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 75, width: 300, height: 44))
        cancelButton.center.x = self.view.center.x
        cancelButton.setTitle(self.cancelLabel, for: UIControlState.normal)
        cancelButton.setTitleColor(blueColor, for: UIControlState.normal)
        cancelButton.backgroundColor = UIColor.clear
        cancelButton.layer.borderWidth = 1.0
        cancelButton.layer.borderColor = blueColor.cgColor
        cancelButton.layer.cornerRadius = cornerRadius
        cancelButton.addTarget(self, action: #selector(cancelButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(cancelButton)
        
        pageTitle.frame = CGRect(x: self.view.center.x / 2, y: 10, width: displayWidth, height: 100)
        pageTitle.center.x = self.view.center.x
        pageTitle.textAlignment = .center
        pageTitle.text = self.screenTitle
        pageTitle.numberOfLines = 5
        pageTitle.textColor = blueColor
        pageTitle.font=UIFont.systemFont(ofSize: 30)
        pageTitle.backgroundColor=UIColor.clear
        self.view.addSubview(pageTitle)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func cancelButtonPressed(_ button: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.teamHistoryTableView.deselectRow(at: indexPath, animated: true)
        var resultString:String = ""
        switch self.teamHistory[indexPath.row].competitionResult {
        case .Won:
            resultString = "Won"
        case .Tied:
            resultString = "Tied"
        case .Lost:
            resultString = "Lost"
        }
        let teamRedName = self.teamHistory[indexPath.row].teamRedName
        let teamRedScore = self.teamHistory[indexPath.row].teamRedScore
        let teamRedFinish = !self.teamHistory[indexPath.row].teamRedLeft
        let teamBlueName = self.teamHistory[indexPath.row].teamBlueName
        let teamBlueScore = self.teamHistory[indexPath.row].teamBlueScore
        let teamBlueFinish = !self.teamHistory[indexPath.row].teamBlueLeft
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MMM dd, yyyy"
        let startString:String = dateFormatter.string(from: self.teamHistory[indexPath.row].competitionStartDate)
        let endString:String = dateFormatter.string(from: self.teamHistory[indexPath.row].competitionEndDate)
        let message:String = "Competition Result: " + resultString + "\n\nTeam Red: " + teamRedName + "\nScore: " + String(teamRedScore) + "\nFinished: " + String(teamRedFinish) + "\n\nTeam Blue: " + teamBlueName + "\nScore: " + String(teamBlueScore) + "\nFinished: " + String(teamBlueFinish) + "\n\nStarted: " + startString + "\nEnded: " + endString
        let showHistoryAction = UIAlertController(title: self.teamHistory[indexPath.row].competitionName, message: message, preferredStyle: UIAlertControllerStyle.alert)
        
        showHistoryAction.addAction(UIAlertAction(title: "Close", style: .default, handler: { (action: UIAlertAction!) in
            return
        }))
        self.present(showHistoryAction, animated: true, completion: nil)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let value = self.teamHistory.count
        if value > 0
        {
            self.teamHistoryTableView.separatorStyle = .singleLine
            self.teamHistoryTableView.backgroundView = nil
        }
        else
        {
            let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            noDataLabel.text          = "No Past Competitions"
            noDataLabel.textColor     = UIColor.black
            noDataLabel.textAlignment = .center
            self.teamHistoryTableView.backgroundView  = noDataLabel
            self.teamHistoryTableView.separatorStyle  = .none
        }
        return value
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")
        cell.textLabel!.text = self.teamHistory[indexPath.row].competitionName
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MMM dd, yyyy"
        let timeRange:String = dateFormatter.string(from: self.teamHistory[indexPath.row].competitionStartDate) + " - " +  dateFormatter.string(from: self.teamHistory[indexPath.row].competitionEndDate)
        cell.detailTextLabel?.text = timeRange
        switch self.teamHistory[indexPath.row].competitionResult {
        case .Won:
            cell.backgroundColor = UIColor.green
        case .Tied:
            cell.backgroundColor = UIColor.yellow
        case .Lost:
            cell.backgroundColor = UIColor.red
        }
        
        return cell
    }
}
