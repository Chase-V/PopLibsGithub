package com.tashevv.poplibsgithub.ui.usersListUI

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import io.reactivex.rxjava3.core.Observable

class RxButton(context: Context, attrs: AttributeSet?) : AppCompatButton(context, attrs) {

    fun createButtonClickObservable(): Observable<String> {

        return Observable.create { emitter ->

            setOnClickListener {
                emitter.onNext("Button Click")
            }

            emitter.setCancellable {
                setOnClickListener(null)
            }
        }
    }

}