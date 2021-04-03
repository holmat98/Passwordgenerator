package com.example.passwordgenerator.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.passwordgenerator.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    private var passwdGeneratorFragment: PasswdGeneratorFragment = PasswdGeneratorFragment.newInstance("","")
    private var passwordsFragment: PasswordsFragment = PasswordsFragment.newInstance("","")
    private var passwordTester: PasswordTesterFragment = PasswordTesterFragment.newInstance("", "")

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentViewer, fragment)
            .commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        makeCurrentFragment(passwdGeneratorFragment)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.passwordGeneratorFragment -> makeCurrentFragment(passwdGeneratorFragment)
                R.id.yourPasswordsFragment -> makeCurrentFragment(passwordsFragment)
                R.id.passwordTester -> makeCurrentFragment(passwordTester)
            }
            true
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}