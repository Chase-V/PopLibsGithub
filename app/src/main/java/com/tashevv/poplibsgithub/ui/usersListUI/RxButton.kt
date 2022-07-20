package com.tashevv.poplibsgithub.ui.usersListUI

import android.content.Context
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import io.reactivex.rxjava3.core.Observable

class RxButton(context: Context) : AppCompatButton(context) {

    companion object{
        internal fun createButtonClickObservable(button: Button): Observable<String> {

            return Observable.create { emitter ->

                button.setOnClickListener {
                    emitter.onNext("Button Click")
                }

                emitter.setCancellable {
                    button.setOnClickListener(null)
                }
            }
        }
    }



}