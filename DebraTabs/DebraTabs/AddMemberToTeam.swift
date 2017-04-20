//
//  AddMemberToTeam.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 4/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class AddMemberToTeam:UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    public var allInvitations:Array<String> = []
    public var hasAccepted:Array<Bool> = []
    public var dateAccepted:Array<Date> = []
    //public var teamID:Int = -1
    var currentTeam:Team = Team()
    
    public var json:JSON = []
    public var loadPage:Bool = true
    
    private var invitationsTableView: UITableView!
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var usernameField: UITextField = UITextField()
    var addUserButton: UIButton = UIButton()
    var cancelButton: UIButton = UIButton()
    
    let addUserLabel:String = "Invite User"
    let cancelLabel:String = "Cancel"
    let screenTitle:String = "Invite Users to Team"
    let sendInvitationsLabel:String = "Pending Invitations"
    let currentInvitationsLabel:String = "Pending Invitations"
    
    let pageTitle:UILabel = UILabel()
    let currentInvitations:UILabel = UILabel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        
        addUserButton = UIButton(frame: CGRect(x: displayWidth/2, y: barHeight * 9, width: 300, height: 44))
        addUserButton.center.x = self.view.center.x
        addUserButton.setTitle(self.addUserLabel, for: UIControlState.normal)
        addUserButton.setTitleColor(blueColor, for: UIControlState.normal)
        addUserButton.backgroundColor = UIColor.clear
        addUserButton.layer.borderWidth = 1.0
        addUserButton.layer.borderColor = blueColor.cgColor
        addUserButton.layer.cornerRadius = cornerRadius
        addUserButton.addTarget(self, action: #selector(addUserButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(addUserButton)
        
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
        
        usernameField = UITextField(frame: CGRect(x: 0, y: barHeight * 5, width: displayWidth, height: 50))
        usernameField.textAlignment = NSTextAlignment.center
        usernameField.textColor = blueColor
        usernameField.placeholder = "Username"
        usernameField.borderStyle = UITextBorderStyle.line
        usernameField.layer.borderWidth = 1
        usernameField.layer.borderColor = blueColor.cgColor
        self.view.addSubview(usernameField)
        
        invitationsTableView = UITableView(frame: CGRect(x: 0, y: 5 * self.view.frame.height / 12, width: displayWidth, height: 7 * self.view.frame.height / 12 - 100))
        invitationsTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        invitationsTableView.dataSource = self
        invitationsTableView.delegate = self
        
        invitationsTableView.layer.masksToBounds = true
        invitationsTableView.layer.borderColor = blueColor.cgColor
        invitationsTableView.layer.borderWidth = 2.0
        
        self.view.addSubview(invitationsTableView)
        
        pageTitle.frame = CGRect(x: self.view.center.x / 2, y: 10, width: displayWidth, height: 100)
        pageTitle.center.x = self.view.center.x
        pageTitle.textAlignment = .center
        pageTitle.text = self.screenTitle
        pageTitle.numberOfLines = 5
        pageTitle.textColor = blueColor
        pageTitle.font=UIFont.systemFont(ofSize: 30)
        pageTitle.backgroundColor=UIColor.clear
        
        self.view.addSubview(pageTitle)
        
        
        currentInvitations.frame = CGRect(x: 100, y: self.view.frame.height / 3, width: displayWidth, height: 75)
        currentInvitations.textAlignment = .center
        currentInvitations.center.x = self.view.center.x
        currentInvitations.text = self.currentInvitationsLabel
        currentInvitations.textColor = blueColor
        currentInvitations.font=UIFont.systemFont(ofSize: 20)
        currentInvitations.backgroundColor=UIColor.clear
        self.view.addSubview(currentInvitations)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func addUserButtonPressed(_ button: UIButton) {
        if (self.usernameField.text?.isEmpty)! {
            let message:String = "Please provide a username" + self.usernameField.text!
            let joinAction = UIAlertController(title: "Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
            joinAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(joinAction, animated: true, completion: nil)
            return
        }
        let parameters = Requests.inviteUserToTeam(username: self.usernameField.text!, id: self.currentTeam.teamID)
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
        Alamofire.request(Settings.getInviteMemberURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers:headers).validate()
            .responseString { response in
                switch response.result {
                case .success(let _):
                    print("Invitation sent to user")
                    let message:String = "Invitation successfully sent to " + self.usernameField.text!
                    let joinAction = UIAlertController(title: "Invitation Sent", message: message, preferredStyle: UIAlertControllerStyle.alert)
                    joinAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                        self.dismiss(animated: true, completion: nil)
                        return
                    }))
                    //self.usernameField.text = ""
                    self.present(joinAction, animated: true, completion: nil)
                    self.viewDidAppear(true)
                    
                case .failure(let error):
                    print("Request failed with error: \(error)")
                    if (response.response?.statusCode == 401) {
                        let message:String = "User Doesn't Exist"
                        let joinAction = UIAlertController(title: "Cannot Add User", message: message, preferredStyle: UIAlertControllerStyle.alert)
                        joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                            return
                        }))
                        self.present(joinAction, animated: true, completion: nil)
                        self.viewDidAppear(true)
                    } else if (response.response?.statusCode == 402) {
                        let message:String = "User Is Already In Your Team"
                        let joinAction = UIAlertController(title: "Cannot Add User", message: message, preferredStyle: UIAlertControllerStyle.alert)
                        joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                            return
                        }))
                        self.present(joinAction, animated: true, completion: nil)
                        self.viewDidAppear(true)
                    } else if (response.response?.statusCode == 403) {
                        let message:String = "User Is Already In Another Team"
                        let joinAction = UIAlertController(title: "Cannot Add User", message: message, preferredStyle: UIAlertControllerStyle.alert)
                        joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                            return
                        }))
                        self.present(joinAction, animated: true, completion: nil)
                        self.viewDidAppear(true)
                    } else if (response.response?.statusCode == 404) {
                        let message:String = "User Is Already Invited"
                        let joinAction = UIAlertController(title: "Cannot Add User", message: message, preferredStyle: UIAlertControllerStyle.alert)
                        joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                            return
                        }))
                        self.present(joinAction, animated: true, completion: nil)
                        self.viewDidAppear(true)
                    } else {
                        let message:String = "Error"
                        let joinAction = UIAlertController(title: "Cannot Add User", message: message, preferredStyle: UIAlertControllerStyle.alert)
                        joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                            return
                        }))
                        self.present(joinAction, animated: true, completion: nil)
                        self.viewDidAppear(true)
                    }
                }
        }
    }
    
    func cancelButtonPressed(_ button: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.invitationsTableView.deselectRow(at: indexPath, animated: true)
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
        cell.textLabel!.text = self.allInvitations[indexPath.row]
//        if self.hasAccepted[indexPath.row] {
//            let dateFormatter:DateFormatter = DateFormatter()
//            dateFormatter.dateFormat = "MMM dd, yyyy"
//            let dateString:String = dateFormatter.string(from: self.dateAccepted[indexPath.row])
//            cell.detailTextLabel?.text = "Accepted on " + dateString
//        } else {
//            cell.detailTextLabel?.text = "Pending"
//            cell.backgroundColor = UIColor.lightGray
//        }
        
        return cell
    }
    
    func processJSON() {
        print("UPDATING HOME SCREEN FOR ADD MEMBER")
        self.allInvitations = []
        self.hasAccepted = []
        self.dateAccepted = []
        let json = self.json
        let inTeam:Bool = json[JSONProtocolNames.inTeamHeaderName].boolValue
        if inTeam {
            let teamJSON:JSON = json[JSONProtocolNames.teamHeaderName]
            let team = Team(json: teamJSON)
            self.currentTeam = team
            if !team.isLeader {
                return
            }
            let invitedMembers = team.invitedUsers
            for item in invitedMembers {
                self.allInvitations.append(item)
                self.hasAccepted.append(false)
                self.dateAccepted.append(Date())
            }
            self.invitationsTableView.reloadData()
        } else {
            
        }
    }
    
    override func viewDidAppear(_ animated: Bool) {
        if self.loadPage {
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            
            Alamofire.request(Settings.getCommunityHomeURL(), method: .get, headers: headers).validate().responseJSON { response in
                switch response.result {
                case .success(let data):
                    self.json = JSON(data)
                    self.processJSON()
                    
                case .failure(let error):
                    print("Request failed with error: \(error)")
                }
            }
        } else {
            self.processJSON()
        }
        self.loadPage = true
    }
    
    
    
}
