package com.example.shop;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shop.data.DBHelper;
import com.example.shop.fragment.DeleteConfirmationFragment;
import com.example.shop.dto.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactsActivity extends BaseActivity implements View.OnClickListener, View.OnCreateContextMenuListener, DeleteConfirmationFragment.DeleteConfirmationListener {


    private static final int CM_EDIT_ID = 1;
    private static final int CM_DELETE_ID = 2;

    Button add;
    private ActivityResultLauncher<Intent> getResultAdd, getResultUpdate;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ListView listContacts;
    SimpleAdapter adapter;
    private long contactIdToDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        add = (Button) findViewById(R.id.btnAdd);
        listContacts = (ListView) findViewById(R.id.listContacts);
        add.setOnClickListener(this);

        registerForContextMenu(listContacts);

        getResultAdd = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String email = data.getStringExtra("email");
                            String name = data.getStringExtra("name");
                            if (email != null && name != null){
                                AddContact(db, email, name);
                            }
                        }
                    }
                }
        );

        getResultUpdate = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Contact contact = data.getParcelableExtra("contact");
                            if (contact != null){
                                UpdateContact(db, contact);
                            }
                        }
                    }
                }
        );

        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = dbHelper.getWritableDatabase();
        ReadContacts(db);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dbHelper.close();
    }

    private void AddContact(SQLiteDatabase db, String email, String name) {
        ContentValues cv = new ContentValues();

        cv.put("email", email);
        cv.put("name", name);

        long id = db.insert("contacts", null, cv);
        Log.d("DB", "row inserted, ID = " + id);
    }

    private void ReadContacts(SQLiteDatabase db) {

        Log.d("DB", "--- Rows in contacts: ---");
        Cursor c = db.query("contacts", null, null, null, null, null, null);

        if (c.moveToFirst()) {
            ArrayList<Contact> contacts = new ArrayList<>();

            int idColIndex = c.getColumnIndex("id");
            int emailColIndex = c.getColumnIndex("email");
            int nameColIndex = c.getColumnIndex("name");


            do {
                Log.d("DB","ID = " + c.getLong(idColIndex) +
                      ", email = " + c.getString(emailColIndex) + ", name = " + c.getString(nameColIndex));

                Contact contact = new Contact();

                contact.setName(c.getString(nameColIndex));
                contact.setEmail(c.getString(emailColIndex));
                contact.setId(c.getLong(idColIndex));

                contacts.add(contact);

            } while (c.moveToNext());

            ArrayList<Map<String, Object>> data = new ArrayList<>(contacts.size());
            Map<String, Object> m;
            for (int i = 0; i < contacts.size(); i++) {
                m = new HashMap<>();
                m.put("name", contacts.get(i).getName());
                m.put("email", contacts.get(i).getEmail());
                m.put("id", contacts.get(i).getId());
                data.add(m);
            }

            String[] from = { "name", "email", "id" };
            int[] to = { R.id.tvName, R.id.tvEmail, R.id.tvId};

            adapter = new SimpleAdapter(this, data, R.layout.contact, from, to);
            listContacts.setAdapter(adapter);
        } else
            Log.d("DB", "0 rows");
        c.close();
    }

    private Contact ReadContact(SQLiteDatabase db, long id) {
        Contact contact = null;

        Cursor c = db.query("contacts", null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int emailColIndex = c.getColumnIndex("email");
            int nameColIndex = c.getColumnIndex("name");

            do {
                if (c.getInt(idColIndex) == id){
                    contact = new Contact(c.getLong(idColIndex), c.getString(emailColIndex), c.getString(nameColIndex));
                    break;
                }
            } while (c.moveToNext());
        }
        c.close();

        return contact;
    }

    private void UpdateContact(SQLiteDatabase db, Contact contact) {
        ContentValues cv = new ContentValues();
        Log.d("DB", "--- Update mytable: ---");
        cv.put("name", contact.getName());
        cv.put("email", contact.getEmail());
        int updCount = db.update("contacts", cv, "id = ?", new String[] { String.valueOf(contact.getId()) });
        Log.d("DB", "updated rows count = " + updCount);
    }

    private void DeleteContact(SQLiteDatabase db, long id) {
        Log.d("DB", "--- Delete from mytable: ---");
        int delCount = db.delete("contacts", "id = " + id, null);
        Log.d("DB", "deleted rows count = " + delCount);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == add.getId()) {
            getResultAdd.launch(new Intent(this, CreateContactActivity.class));
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_EDIT_ID, 0, "Edit");
        menu.add(0, CM_DELETE_ID, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        assert info != null;
        View view = info.targetView;
        TextView textView = view.findViewById(R.id.tvId);
        String text = textView.getText().toString();
        long idValue = Long.parseLong(text);

        if (item.getItemId() == CM_EDIT_ID) {
            Intent i = new Intent(this, UpdateContactActivity.class);
            Contact contact = ReadContact(db, idValue);
            i.putExtra("contact", contact);
            getResultUpdate.launch(i);
        }
        if (item.getItemId() == CM_DELETE_ID) {

            contactIdToDelete = idValue;
            showDeleteConfirmationDialog();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        DeleteConfirmationFragment dialog = new DeleteConfirmationFragment(this);
        dialog.show(getSupportFragmentManager(), "DeleteConfirmationDialog");
    }

    @Override
    public void onConfirmDelete() {
        DeleteContact(db, contactIdToDelete);
        adapter.notifyDataSetChanged();
        ReadContacts(db);
    }
}