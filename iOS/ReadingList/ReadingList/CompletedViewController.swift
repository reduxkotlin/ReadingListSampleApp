import UIKit
import common

class CompletedViewController: BaseViewController, CompletedView {
    
    func presenter() -> (Presenter_middlewareView, Kotlinx_coroutines_coreCoroutineScope) -> (LibStore) -> () -> KotlinUnit {
        return CompletedViewKt.completedPresenter
    }
    
    @IBOutlet var tableView: UITableView!
    
    var tableDelegate: BookTableDelegate? = nil
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    
    func showLoading() {
        print("SHOWLOADING")
    }
    
    func hideLoading() {
        print("HIDE LOADING")
        
    }
    
    func showError(msg: String) {
        print("SHOW Error")
        
    }
    
    func showBooks(books: [Any]) {
        tableDelegate?.bookList = books
        tableView.reloadData()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
    
    func setupLayout() {
        tableView.rowHeight = 161
        tableDelegate = BookTableDelegate(tableView, {pos in dispatch(UiActions.CompletedBookTapped(position: Int32(pos - 1)))})
        tableView.delegate = tableDelegate
        tableView.dataSource = tableDelegate
    }
    
    override func viewDidAppear(_ animated: Bool) {
        dispatch(UiActions.CompletedListShown())
    }
}
