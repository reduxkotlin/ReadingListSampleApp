import Foundation
import UIKit
import common
extension String: Error {}

class BaseViewController: UIViewController, Presenter_middlewareView {
//    func presenter() -> (Presenter_middlewareView, Kotlinx_coroutines_coreCoroutineScope) -> (LibStore) -> () -> KotlinUnit {
//        return { _,_ in return {_ in return {  return KotlinUnit()} }}
//    }
    
    required init?(coder aDecoder: NSCoder) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.initilize()
        super.init(coder: aDecoder)
    }


    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        PresenterInjectorKt.detachView(view: self)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        PresenterInjectorKt.attachView(view: self)

    }
    

}
