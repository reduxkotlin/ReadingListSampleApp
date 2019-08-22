import Foundation
import UIKit
import common

/*
 * Extension functions for downloading images and displaying on UIImageViews.  Includes simple in-memory caching
 */


let imageCache = NSCache<NSString, UIImage>()

public extension UIImageView {
    
    func downloaded(from url: URL, contentMode mode: UIView.ContentMode = .scaleAspectFit, onComplete: ((UIImage) -> ())? = nil) {
        contentMode = mode
        if let cachedImage = imageCache.object(forKey: url.absoluteString as NSString) {
            setImage(image: cachedImage, onComplete: onComplete)
        } else {
        URLSession.shared.dataTask(with: url) { data, response, error in
            guard
                let httpURLResponse = response as? HTTPURLResponse, httpURLResponse.statusCode == 200,
                let mimeType = response?.mimeType, mimeType.hasPrefix("image"),
                let data = data, error == nil,
                let image = UIImage(data: data)
                else {
//                    Logger().d(message: "error fetching image " + url.absoluteString + " " + error!.localizedDescription)
                    return
            }
            imageCache.setObject(image, forKey: url.absoluteString as NSString)
            self.setImage(image: image, onComplete: onComplete)
            }.resume()
        }
    }
    
    func setImage(image: UIImage, onComplete: ((UIImage) -> ())? = nil) {
        DispatchQueue.main.async() {
            self.image = image
            if (onComplete != nil) {
                onComplete!(image)
            }
        }
    }
    
    func downloaded(from link: String, contentMode mode: UIView.ContentMode = .scaleAspectFit, onComplete: ((UIImage) -> ())? = nil) {
        guard let url = URL(string: link) else {
            return
        }
        downloaded(from: url, contentMode: mode, onComplete: onComplete)
    }
}
