package com.dam.trabajo_recuperacion_2025.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class AuthViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()
    private val _currentUser = MutableStateFlow<Map<String, Any>?>(null)
    val currentUser = _currentUser.asStateFlow()
    init { loadUser() }
    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            _loading.value = true
            _message.value = ""
            try {
                auth.createUserWithEmailAndPassword(email,
                    password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profile =
                            UserProfileChangeRequest.Builder().setDisplayName(name).build()
                        user?.updateProfile(profile)
                        12
                        val map = mapOf("name" to name, "email" to email)
                        user?.uid?.let {
                            db.collection("users").document(it).set(map) }
                        _message.value = "Registrado"
                        loadUser()
                    } else {
                        _message.value = task.exception?.localizedMessage ?:
                                "Error"
                    }
                    _loading.value = false
                }
            } catch (e: Exception) {
                _message.value = e.localizedMessage ?: "Error"
                _loading.value = false
            }
        }
    }
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _message.value = ""
            try {
                auth.signInWithEmailAndPassword(email,
                    password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _message.value = "Entrado"
                        loadUser()
                    } else {
                        _message.value = task.exception?.localizedMessage ?:
                                "Error"
                    }
                    _loading.value = false
                }
            } catch (e: Exception) {
                _message.value = e.localizedMessage ?: "Error"
                _loading.value = false
            }
        }
    }
    fun logout() {
        auth.signOut()
        _currentUser.value = null
    }
    fun loadUser() {
        val u = auth.currentUser
        if (u == null) { _currentUser.value = null; return }
        db.collection("users").document(u.uid).get().addOnSuccessListener {
                doc ->
            _currentUser.value = doc.data
        }
    }
    fun updateProfile(name: String) {
        viewModelScope.launch {
            _loading.value = true
            _message.value = ""
            val u = auth.currentUser
            try {
                val profile =
                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                u?.updateProfile(profile)
                val map = mapOf("name" to name, "email" to (u?.email ?: ""))
                u?.uid?.let {
                    db.collection("users").document(it).set(map).addOnSuccessListener {
                        _message.value = "Guardado"; loadUser() }.addOnFailureListener {
                        _message.value = "Error" } }
            } catch (e: Exception) {
                _message.value = e.localizedMessage ?: "Error"
            }
            _loading.value = false
        }
    }
}