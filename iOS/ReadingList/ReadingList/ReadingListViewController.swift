import UIKit
import common

class ReadingListViewController: BaseViewController, ReadingListView {

    @IBOutlet var tableView: UITableView!

    var tableDelegate: BookTableDelegate? = nil
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func presenter() -> (Presenter_middlewareView, Kotlinx_coroutines_coreCoroutineScope) -> (LibStore) -> () -> KotlinUnit {
        return ReadingListViewKt.readingListPresenter
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
    
    func showBooks(toReadBook: [Any]) {
        tableDelegate?.bookList = toReadBook
        tableView.reloadData()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
    
    
    func setupLayout() {
        tableView.rowHeight = 161
        tableDelegate = BookTableDelegate(tableView, d: dispatch)
        tableView.delegate = tableDelegate
        tableView.dataSource = tableDelegate
    }
    

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        dispatch(UiActions.ReadingListShown())
    }
}
