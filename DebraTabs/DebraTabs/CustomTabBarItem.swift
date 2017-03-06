//
//  CustomTabBarItem.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/14/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class CustomTabBarItem: UIView {
    
    var iconView: UIImageView!
    var titleView: String!
    var titleLabel: UILabel!
    
    override init (frame : CGRect) {
        super.init(frame : frame)
        
    }
    
    convenience init () {
        self.init(frame:CGRect.zero)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setup(item: UITabBarItem) {
        
        guard let image = item.image else {
            fatalError("add images to tabbar items")
        }
        
        guard let titleView = item.title else {
            fatalError("add titles to tabbar items")
        }
        
        titleLabel = UILabel(frame: CGRect(x: 0, y: self.frame.height / 2, width: self.frame.width, height: self.frame.height / 2))
        titleLabel.text = titleView
        //titleLabel.backgroundColor = UIColor.red
        //titleLabel.center = CGPoint(x: self.frame.width / 2, y: self.frame.height / 2)
        titleLabel.textAlignment = NSTextAlignment.center
        
        
        // create imageView centered within a container
        iconView = UIImageView(frame: CGRect(x: (self.frame.width-image.size.width)/2, y: (self.frame.height-image.size
            .height)/2, width: self.frame.width, height: self.frame.height))
        
        iconView.image = image
        iconView.sizeToFit()
        
        self.addSubview(iconView)
        self.addSubview(titleLabel)
    }
    
}
