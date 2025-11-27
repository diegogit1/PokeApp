package com.dam.trabajo_recuperacion_2025.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _currentUser = MutableStateFlow<Map<String, Any>?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        loadUser()
    }

    fun register(email: String, password: String, name: String) {
        _loading.value = true
        _message.value = ""
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val profile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                user?.updateProfile(profile)
                val map = mapOf("name" to name, "email" to email)
                user?.uid?.let { uid ->
                    db.collection("users").document(uid).set(map).addOnCompleteListener { writeTask ->
                        if (writeTask.isSuccessful) {
                            _message.value = "Registrado"
                            loadUser()
                        } else {
                            _message.value = writeTask.exception?.localizedMessage ?: "Error al guardar usuario"
                        }
                        _loading.value = false
                    }
                } ?: run {
                    _message.value = "Error: UID vacío"
                    _loading.value = false
                }
            } else {
                _message.value = task.exception?.localizedMessage ?: "Error en registro"
                _loading.value = false
            }
        }
    }

    fun login(email: String, password: String) {
        _loading.value = true
        _message.value = ""
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _message.value = "Entrado"
                loadUser()
            } else {
                _message.value = task.exception?.localizedMessage ?: "Error en login"
            }
            _loading.value = false
        }
    }

    fun logout() {
        auth.signOut()
        _currentUser.value = null
    }

    fun loadUser() {
        val u = auth.currentUser
        if (u == null) {
            _currentUser.value = null
            return
        }
        db.collection("users").document(u.uid).get().addOnSuccessListener { doc ->
            _currentUser.value = doc.data
        }.addOnFailureListener {
            _message.value = it.localizedMessage ?: "Error al leer usuario"
        }
    }

    fun updateProfile(name: String) {
        val u = auth.currentUser ?: run {
            _message.value = "Usuario no autenticado"
            return
        }
        _loading.value = true
        _message.value = ""
        val profile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        u.updateProfile(profile).addOnCompleteListener { t ->
            if (t.isSuccessful) {
                val map = mapOf("name" to name, "email" to (u.email ?: ""))
                db.collection("users").document(u.uid).set(map).addOnCompleteListener { writeTask ->
                    if (writeTask.isSuccessful) {
                        _message.value = "Guardado"
                        loadUser()
                    } else {
                        _message.value = writeTask.exception?.localizedMessage ?: "Error al guardar"
                    }
                    _loading.value = false
                }
            } else {
                _message.value = t.exception?.localizedMessage ?: "Error al actualizar perfil"
                _loading.value = false
            }
        }
    }
}
