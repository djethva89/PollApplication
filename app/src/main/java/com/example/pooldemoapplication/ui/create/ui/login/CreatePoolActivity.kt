package com.example.pooldemoapplication.ui.create.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.example.pooldemoapplication.R
import com.example.pooldemoapplication.databinding.ActivityCreatePoolBinding
import com.example.pooldemoapplication.model.OptionTableEntity
import com.example.pooldemoapplication.model.PoolTableEntity
import com.example.pooldemoapplication.viewmodel.PoolViewModel
import kotlin.random.Random
import kotlin.random.nextInt

class CreatePoolActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityCreatePoolBinding

    lateinit var poolViewModel: PoolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        poolViewModel = ViewModelProvider(this).get(PoolViewModel::class.java)


        val poolEntity =
            PoolTableEntity(poolName = "First Pool ${Random.nextInt(1000)}", createAt = "Abc")

        val optionList = arrayListOf(
            OptionTableEntity(
                optionName = "A ${Random.nextInt(100)}",
                percentage = "0",
                createAt = ""
            ),
            OptionTableEntity(
                optionName = "B ${Random.nextInt(100)}",
                percentage = "0",
                createAt = ""
            ),
            OptionTableEntity(
                optionName = "C ${Random.nextInt(100)}",
                percentage = "0",
                createAt = ""
            ),
            OptionTableEntity(
                optionName = "D ${Random.nextInt(100)}",
                percentage = "0",
                createAt = ""
            ),
        )

        poolViewModel.insertPoolWithOption(
            context = this,
            poolTableEntity = poolEntity,
            optionTableEntity = optionList
        )

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        loginViewModel.loginFormState.observe(this@CreatePoolActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@CreatePoolActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }

        binding.backNavigation!!.setOnClickListener {
            finish()
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}