import UIKit
import common

class SearchViewController: BaseViewController, UISearchBarDelegate, SearchView {
  
    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet var tableView: UITableView!
    
    var tableDelegate: BookTableDelegate? = nil

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    //TODO This is overriden in kotlin SearchView interface, however that is not translated to swift.
    //For now the presenter function must be returned in swift.
    func presenter() -> (Presenter_middlewareView, Kotlinx_coroutines_coreCoroutineScope) -> (LibStore) -> () -> KotlinUnit {
        return SearchViewKt.searchPresenter
    }
    
    func showResults(books: [BookListItemViewState]) {
        tableDelegate?.bookList = books
        tableView.reloadData()
    }
    

    func showLoading() {
        showSpinner(onView: self.view)
    }
    
    func hideLoading() {
        removeSpinner()
    }
    
    func showError(msg: String) {
        print("SHOW Error")
        
    }
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
        searchBar.delegate = self
        setupLayout()
    }
    
    func setupLayout() {
        tableView.rowHeight = 161
        searchBar.showsScopeBar = false // you can show/hide this dependant on your layout
        searchBar.placeholder = "Search by title or author"
        tableDelegate = BookTableDelegate(tableView, d: dispatch)
        tableView.delegate = tableDelegate
        tableView.dataSource = tableDelegate
    }
    
    // We keep track of the pending work item as a property
    private var pendingRequestWorkItem: DispatchWorkItem?
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        // Cancel the currently pending item
        pendingRequestWorkItem?.cancel()
        
        // Wrap our request in a work item
        let requestWorkItem = DispatchWorkItem { [weak self] in
            dispatch(UiActions.SearchQueryEntered(query: searchText))
        }
        
        // Save the new work item and execute it after 250 ms
        pendingRequestWorkItem = requestWorkItem
        DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(500),
                                      execute: requestWorkItem)
    }
    
}

