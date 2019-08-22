import Foundation
import UIKit
import common

class BookTableDelegate: NSObject, UITableViewDataSource, UITableViewDelegate {
    var clickListener: (Int) -> ()
    
    init(_ tableView: UITableView, _ clickListener: @escaping (Int) -> ()) {
        tableView.register(UINib(nibName: "TitleCell", bundle: nil), forCellReuseIdentifier: "titleCell")
        tableView.register(UINib(nibName: "BookCell", bundle: nil), forCellReuseIdentifier: "bookCell")
        self.clickListener = clickListener
    }
    
    
    var bookList = [Any]()
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return bookList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let item = bookList[indexPath.row]
        
        if (item is BookListItemViewState) {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: "bookCell") as? BookCell else {
                return BookCell()
            }
            let vm = bookList[indexPath.row] as! BookListItemViewState
            cell.titleTextView.text = vm.title
            cell.authorTextView.text = vm.author
            cell.coverImageView.image = nil
            cell.coverImageView.downloaded(from: vm.coverImageUrl, contentMode: UIView.ContentMode.scaleAspectFill)
        
            return cell
        } else if (item is ListHeader) {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: "titleCell") as? TitleCell else {
                return TitleCell()
            }
            let title = (item as! ListHeader).title
            cell.titleTextView.text = title
            return cell
        } else {
            return UITableViewCell()
        }
    }
    
    
    func tableView(_ tableView: UITableView,
                   didSelectRowAt indexPath: IndexPath) {
        if (bookList[indexPath.row] is BookListItemViewState) {
            clickListener(indexPath.row)
        }
    }
    
}
