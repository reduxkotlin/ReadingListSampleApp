package com.willowtreeapps.common.external

import org.reduxkotlin.*

//Here only until a solution is found for rootDispatch & AttachView class not
//visible from swift.
fun attachView(view: View) = rootDispatch(AttachView(view))
fun detachView(view: View) = rootDispatch(DetachView(view))
fun clearView(view: View) = rootDispatch(ClearView(view))
