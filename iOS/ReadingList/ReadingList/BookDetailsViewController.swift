import UIKit
import common

class BookDetailsViewController: BaseViewController, DetailsView {

    

    @IBOutlet weak var navBar: UINavigationBar!
    @IBOutlet weak var coverImageView: UIImageView!
    @IBOutlet weak var titleTextView: UITextField!
    @IBOutlet weak var readingListButton: UIBarButtonItem!
    @IBOutlet weak var completedButton: UIBarButtonItem!
    
    @IBAction func onReadingListButtonClick(_ sender: UIBarButtonItem) {
        dispatch(UiActions.AddToReadingButtonTapped())
    }
    
    @IBAction func onCompletedButtonClick(_ sender: Any) {
        dispatch(UiActions.AddToCompletedButtonTapped())
    }
    
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setToolbarHidden(true, animated: animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setToolbarHidden(false, animated: animated)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setToolbarHidden(true, animated: animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    func presenter() -> (Presenter_middlewareView, Kotlinx_coroutines_coreCoroutineScope) -> (LibStore) -> () -> KotlinUnit {
        return DetailsViewKt.detailsPresenter
    }
    
    func render(detailsViewState: BookDetailViewState) {
        coverImageView.downloaded(from: detailsViewState.book.coverImageUrl, contentMode: UIView.ContentMode.scaleToFill, onComplete: {(image:UIImage) -> () in
         
            let imageRatio = image.size.height / image.size.width
            self.coverImageView.frame = CGRect(x: 0, y: 0, width: self.view.frame.width, height: self.view.frame.width * imageRatio)
        })
        titleTextView.text = detailsViewState.book.title
    }
}
