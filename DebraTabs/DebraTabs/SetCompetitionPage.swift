//
//  SetCompetitionPage.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 4/8/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class SetCompetitionPage:UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    public var allInvitations:Array<CompetitionInvitation> = []
    //public var teamID:Int = 0
    public var currentTeam:Team = Team()
    
    public var json:JSON = []
    public var loadPage:Bool = false
    
    private var invitationsTableView: UITableView!
    
    let titleLabelText:String = "View Competition"
    let invitationsLabelText:String = "Invitations:"
    let createCompetitionLabel:String = "Create Competition"
    let cancelLabel:String = "Cancel"
    
    var titleLabel:UILabel = UILabel()
    var invitationsLabel:UILabel = UILabel()
    
    var createCompetitionButton:UIButton = UIButton()
    var cancelButton:UIButton = UIButton()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        
        titleLabel.frame = CGRect(x: self.view.center.x / 2, y: 10, width: displayWidth, height: 100)
        titleLabel.center.x = self.view.center.x
        titleLabel.textAlignment = .center
        titleLabel.text = self.titleLabelText
        titleLabel.numberOfLines = 5
        titleLabel.textColor = blueColor
        titleLabel.font=UIFont.systemFont(ofSize: 30)
        titleLabel.backgroundColor=UIColor.clear
        self.view.addSubview(titleLabel)
        
        invitationsLabel.frame = CGRect(x: self.view.center.x / 2, y: 180, width: displayWidth, height: 50)
        invitationsLabel.center.x = self.view.center.x
        invitationsLabel.textAlignment = .center
        invitationsLabel.text = self.invitationsLabelText
        invitationsLabel.numberOfLines = 5
        invitationsLabel.textColor = blueColor
        invitationsLabel.font=UIFont.systemFont(ofSize: 20)
        invitationsLabel.backgroundColor=UIColor.clear
        self.view.addSubview(invitationsLabel)
        
        invitationsTableView = UITableView(frame: CGRect(x: 0, y: 220, width: displayWidth, height: self.view.frame.height - 320))
        invitationsTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        invitationsTableView.dataSource = self
        invitationsTableView.delegate = self
        
        invitationsTableView.layer.masksToBounds = true
        invitationsTableView.layer.borderColor = blueColor.cgColor
        invitationsTableView.layer.borderWidth = 2.0
        
        self.view.addSubview(invitationsTableView)
        
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
        
        createCompetitionButton = UIButton(frame: CGRect(x: displayWidth/2, y: 120, width: 300, height: 44))
        createCompetitionButton.center.x = self.view.center.x
        createCompetitionButton.setTitle(self.createCompetitionLabel, for: UIControlState.normal)
        createCompetitionButton.setTitleColor(blueColor, for: UIControlState.normal)
        createCompetitionButton.backgroundColor = UIColor.clear
        createCompetitionButton.layer.borderWidth = 1.0
        createCompetitionButton.layer.borderColor = blueColor.cgColor
        createCompetitionButton.layer.cornerRadius = cornerRadius
        createCompetitionButton.addTarget(self, action: #selector(createCompetitionButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(createCompetitionButton)
        
    }
    
    func cancelButtonPressed(_ button: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func createCompetitionButtonPressed(_ button: UIButton) {
        print("Create Competition")
        let secondViewController:CreateCompetition = CreateCompetition()
        secondViewController.teamID = self.currentTeam.teamID
        secondViewController.presentingView = self
        self.present(secondViewController, animated: true, completion: nil)
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MMM dd, yyyy"
        self.invitationsTableView.deselectRow(at: indexPath, animated: true)
        let message:String = "Name: " + self.allInvitations[indexPath.row].competitionName + "\nOther Team: " + self.allInvitations[indexPath.row].otherTeamName + "\nOther Leader: " + self.allInvitations[indexPath.row].otherTeamLeader + "\nOther Color: " + TeamColor.getColorString(type: self.allInvitations[indexPath.row].otherTeamColor) + "\nStart Date: " + dateFormatter.string(from: self.allInvitations[indexPath.row].competitionStartDate) + "\nEnd Date: " + dateFormatter.string(from: self.allInvitations[indexPath.row].competitionEndDate)
        let joinTeamAction = UIAlertController(title: "Join Competition?", message: message, preferredStyle: UIAlertControllerStyle.alert)
        joinTeamAction.addAction(UIAlertAction(title: "Yes", style: .default, handler: { (action: UIAlertAction!) in
            let parameters = Requests.joinCompetition(teamId: self.currentTeam.teamID, competitionId: self.allInvitations[indexPath.row].competitionId)
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            Alamofire.request(Settings.getJoinCompetitionURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
                .validate().responseString { response in
                    //print("Response String: \(response.response?.statusCode == 401)")
                    switch response.result {
                    case .success(let data):
                        print("Joined Competition")
                        self.dismiss(animated: true, completion: nil)
                        //self.viewDidAppear(true)
                        
                    case .failure(let error):
                        print("Request failed with error: \(error)")
                        if (response.response?.statusCode == 401) {
                            let message:String = "Competition does not exist."
                            let joinAction = UIAlertController(title: "Cannot Join", message: message, preferredStyle: UIAlertControllerStyle.alert)
                            joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                                return
                            }))
                            self.present(joinAction, animated: true, completion: nil)
                            //self.viewDidAppear(true)
                        } else if (response.response?.statusCode == 455) {
                            let message:String = "Error"
                            let joinAction = UIAlertController(title: "Cannot Join", message: message, preferredStyle: UIAlertControllerStyle.alert)
                            joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                                self.viewDidAppear(true)
                            }))
                            self.present(joinAction, animated: true, completion: nil)
                            //self.viewDidAppear(true)
                        } else {
                            let message:String = "Error"
                            let joinAction = UIAlertController(title: "Cannot Join", message: message, preferredStyle: UIAlertControllerStyle.alert)
                            joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                                self.viewDidAppear(true)
                            }))
                            self.present(joinAction, animated: true, completion: nil)
                        }
                    }
            }
        }))
        
        joinTeamAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
            return
        }))
        self.present(joinTeamAction, animated: true, completion: nil)
        
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let value = self.allInvitations.count
        if value > 0
        {
            self.invitationsTableView.separatorStyle = .singleLine
            self.invitationsTableView.backgroundView = nil
        }
        else
        {
            let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            noDataLabel.text          = "No Invitations"
            noDataLabel.textColor     = UIColor.black
            noDataLabel.textAlignment = .center
            self.invitationsTableView.backgroundView  = noDataLabel
            self.invitationsTableView.separatorStyle  = .none
        }
        return value
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")
        cell.detailTextLabel?.text = self.allInvitations[indexPath.row].otherTeamName
        cell.textLabel!.text = self.allInvitations[indexPath.row].competitionName
        cell.accessoryType = .disclosureIndicator
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {
        let delete = UITableViewRowAction(style: .normal, title: "Decline") { action, index in
            print("Delete Request")
            let parameters = Requests.declineCompetition(teamId: self.currentTeam.teamID, competitionId: self.allInvitations[indexPath.row].competitionId)
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            Alamofire.request(Settings.getDeclineCompetitionURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
                .responseString { response in
                    print("Response String: \(response.response?.statusCode == 401)")
                    switch response.result {
                    case .success(let _):
                        print("Deleted Competition Invide")
                        self.viewDidAppear(true)
                        
                    case .failure(let error):
                        print("Request failed with error: \(error)")
                    }
            }
            
        }
        delete.backgroundColor = .red
        return [delete]
    }
    
    func processJSON() {
        print("UPDATING COMPETITION INVITATIONS")
        self.allInvitations = []
        //print(json)
        let invitations:Array<JSON> = self.json[JSONProtocolNames.competitionInvitationsHeaderName].arrayValue
        for invitation in invitations {
            let item:CompetitionInvitation = CompetitionInvitation(json: invitation)
            self.allInvitations.append(item)
        }
        self.invitationsTableView.reloadData()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
        
        Alamofire.request(Settings.getCompetitionInvitationsURL(), method: .get, headers: headers).validate().responseJSON { response in
            switch response.result {
            case .success(let data):
                self.json = JSON(data)
                self.processJSON()
                
            case .failure(let error):
                print("Request failed with error: \(error)")
                self.dismiss(animated: false, completion: nil)
            }
        }
    }
    
}
