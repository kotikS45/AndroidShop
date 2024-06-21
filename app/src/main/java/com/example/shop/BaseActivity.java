package com.example.shop;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private static boolean Authorized = false;

    public final boolean getAuthorized() {
        return Authorized;
    }

    public final void setAuthorized(boolean authorized) {
        Authorized = authorized;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        menu.setGroupVisible(R.id.menuGroup1, !Authorized);
        menu.setGroupVisible(R.id.menuGroup2, Authorized);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int select = item.getItemId();
        Intent intent = null;
        if (select==R.id.m_category){
            intent = new Intent(BaseActivity.this, CategoryActivity.class);
        }
        else if (select==R.id.m_profile){
            intent = mProfile(intent);
        }
        else if(select==R.id.m_signin) {
            intent = mSignIn(intent);
        }
        else if(select==R.id.m_signup) {
            intent = mSignUp(intent);
        }
        else if(select==R.id.m_settings) {
            intent = new Intent(BaseActivity.this, SettingsActivity.class);
        }
        else if(select==R.id.m_LogOut) {
            Authorized = false;
            intent = new Intent(BaseActivity.this, LoginActivity.class);
        }
        else if(select==R.id.m_info) {
            intent = new Intent(BaseActivity.this, InfoActivity.class);
        }
        else if(select==R.id.m_constructor) {
            intent = mConstructor(intent);
        }
        else if(select==R.id.m_anim) {
            intent = new Intent(BaseActivity.this, AnimationActivity.class);
        }
        else if(select==R.id.m_calc) {
            intent = new Intent(BaseActivity.this, CalculatorActivity.class);
        }
        else if(select==R.id.m_contacts) {
            intent = mContacts(intent);
        }
        else if(select==R.id.m_exit) {
            finish();
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return true;
    }

    protected Intent mConstructor (Intent intent) {
        return new Intent(BaseActivity.this, ConstructorActivity.class);
    }

    protected Intent mContacts (Intent intent) {
        return new Intent(BaseActivity.this, ContactsActivity.class);
    }

    protected Intent mSignUp (Intent intent) {
        return new Intent(BaseActivity.this, RegisterActivity.class);
    }

    protected Intent mSignIn (Intent intent) {
        return new Intent(BaseActivity.this, LoginActivity.class);
    }

    protected Intent mProfile (Intent intent) {
        return new Intent(BaseActivity.this, ProfileActivity.class);
    }
}
