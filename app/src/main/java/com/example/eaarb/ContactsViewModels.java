package com.example.eaarb;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ContactsViewModels extends ViewModel {
    private ContactsRepository cRepository;

    private LiveData<List<Contact>> contacts;

    public ContactsViewModels() {
        cRepository = new ContactsRepository();
     //   contacts = cRepository.getAll();
    }



    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

 //public void add(Contact c) {  cRepository.add(c);}

 //public void delete(Contact c) {cRepository.delete(c);}

 //public  void  reload() {cRepository.reload();}
}
