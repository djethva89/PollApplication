package com.example.pooldemoapplication.ui.createPolls.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.example.pooldemoapplication.databinding.ActivityCreatePoolBinding
import com.example.pooldemoapplication.config.room.entity.OptionTableModel
import com.example.pooldemoapplication.config.room.entity.PollsTableModel
import com.example.pooldemoapplication.ui.createPolls.adapter.ItemMoveCallbackListener
import com.example.pooldemoapplication.ui.createPolls.adapter.OptionsAdapter
import com.example.pooldemoapplication.viewmodel.PollsViewModel
import kotlin.random.Random

class CreatePoolActivity : AppCompatActivity(), ItemMoveCallbackListener.OnStartDragListener {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityCreatePoolBinding

    lateinit var touchHelper: ItemTouchHelper

    lateinit var pollsViewModel: PollsViewModel

    val n = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pollsViewModel = ViewModelProvider(this)[PollsViewModel::class.java]

        val pollQuestion = binding.poolQuestion

//        loginViewModel =
//            ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

//        loginViewModel.loginFormState.observe(this@CreatePoolActivity, Observer {
//            val loginState = it ?: return@Observer
//
//            // disable login button unless both username / password is valid
//            login.isEnabled = loginState.isDataValid
//
//            if (loginState.usernameError != null) {
//                username.error = getString(loginState.usernameError)
//            }
//            if (loginState.passwordError != null) {
//                password.error = getString(loginState.passwordError)
//            }
//        })

//        loginViewModel.loginResult.observe(this@CreatePoolActivity, Observer {
//            val loginResult = it ?: return@Observer
//
//            loading.visibility = View.GONE
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
//            }
//            setResult(Activity.RESULT_OK)
//
//            //Complete and destroy login activity once successful
//            finish()
//        })

        val optionsAdapter = OptionsAdapter(this)
        bindAdapter(optionsAdapter)

        binding.addOption!!.setOnClickListener {
            optionsAdapter.addOption("")
        }
        binding.create!!.setOnClickListener {

            if (pollQuestion!!.text == null && pollQuestion.text!!.isEmpty()) {
                Toast.makeText(this, "Please enter poll options!", Toast.LENGTH_SHORT).show()
            } else {
                createPolls(poolName = pollQuestion.run { text.toString() })
            }

        }
        binding.backNavigation!!.setOnClickListener {
            finish()
        }
    }


    private fun bindAdapter(optionsAdapter: OptionsAdapter) {
        optionsAdapter.addOption("")
        val callback: ItemTouchHelper.Callback = ItemMoveCallbackListener(optionsAdapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.questions!!)
        binding.questions!!.adapter = optionsAdapter
        binding.questions!!.layoutManager = LinearLayoutManager(this)

    }

    fun createPolls(poolName: String) {

        val poolEntity =
            PollsTableModel(
                poolName = poolName,
                createAt = System.currentTimeMillis()
            )

        val optionList = arrayListOf(
            OptionTableModel(
                optionName = "A ${Random.nextInt(100)}",
                createAt = poolEntity.createAt
            ),
            OptionTableModel(
                optionName = "B ${Random.nextInt(100)}",
                createAt = poolEntity.createAt
            ),
            OptionTableModel(
                optionName = "C ${Random.nextInt(100)}",
                createAt = poolEntity.createAt
            ),
            OptionTableModel(
                optionName = "D ${Random.nextInt(100)}",
                createAt = poolEntity.createAt
            ),
        )

        pollsViewModel.insertPoolWithOption(
            context = this,
            pollsTableModel = poolEntity,
            optionTableEntity = optionList
        )
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }


//    private fun updateUiWithUser(model: LoggedInUserView) {
//        val welcome = getString(R.string.welcome)
//        val displayName = model.displayName
//        Toast.makeText(
//            applicationContext,
//            "$welcome $displayName",
//            Toast.LENGTH_LONG
//        ).show()
//    }

//    private fun showLoginFailed(@StringRes errorString: Int) {
//        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
//    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
//fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
//    this.addTextChangedListener(object : TextWatcher {
//        override fun afterTextChanged(editable: Editable?) {
//            afterTextChanged.invoke(editable.toString())
//        }
//
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//    })
//}
