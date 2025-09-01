package com.weatherapp.db.fb

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class FBDatabase {
    interface Listener {
        fun onUserLoaded(user: FBUser)
        fun onUserSignOut()
        fun onCityAdded(city: FBCity)
        fun onCityUpdated(city: FBCity)
        fun onCityRemoved(city: FBCity)
    }

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    val user: Flow<FBUser>
        get() {
            if (auth.currentUser == null) return emptyFlow()
            return db.collection("users")
                .document(auth.currentUser!!.uid)
                .snapshots().map { it.toObject(FBUser::class.java)!! }
        }
    val cities: Flow<List<FBCity>>
        get() {
            if (auth.currentUser == null) return emptyFlow()
            return db.collection("users")
                .document(auth.currentUser!!.uid)
                .collection("cities")
                .snapshots().map {
                        snapshot -> snapshot.toObjects(FBCity::class.java)
                }
        }

    private var citiesListReg: ListenerRegistration? = null
    private var listener : Listener? = null

//    init {
//        auth.addAuthStateListener { auth ->
//            if (auth.currentUser == null) {
//                citiesListReg?.remove()
//                listener?.onUserSignOut()
//                return@addAuthStateListener
//            }
//
//            val refCurrUser = db.collection("users").document(auth.currentUser!!.uid)
//            refCurrUser.get().addOnSuccessListener {
//                it.toObject(FBUser::class.java)?.let { user ->
//                    listener?.onUserLoaded(user)
//                }
//            }
//
//            citiesListReg = refCurrUser.collection("cities")
//                .addSnapshotListener { snapshots, ex ->
//                    if (ex != null) return@addSnapshotListener
//
//                    snapshots?.documentChanges?.forEach { change ->
//                        val fbCity = change.document.toObject(FBCity::class.java)
//                        if (change.type == DocumentChange.Type.ADDED) {
//                            listener?.onCityAdded(fbCity)
//                        } else if (change.type == DocumentChange.Type.MODIFIED) {
//                            listener?.onCityUpdated(fbCity)
//                        } else if (change.type == DocumentChange.Type.REMOVED) {
//                            listener?.onCityRemoved(fbCity)
//                        }
//                    }
//                }
//        }
//    }

    fun setListener(listener: Listener? = null) {
        this.listener = listener
    }

    fun register(user: FBUser) {
        if (auth.currentUser == null)
            throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid + "").set(user)
    }

    fun add(city: FBCity) {
        if (auth.currentUser == null)
            throw RuntimeException("User not logged in!")
        if (city.name == null || city.name!!.isEmpty())
            throw RuntimeException("City with null or empty name!")

        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid).collection("cities")
            .document(city.name!!).set(city)
    }

    fun remove(city: FBCity) {
        if (auth.currentUser == null)
            throw RuntimeException("User not logged in!")
        if (city.name == null || city.name!!.isEmpty())
            throw RuntimeException("City with null or empty name!")

        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid).collection("cities")
            .document(city.name!!).delete()
    }

    fun update(city: FBCity) {
        if (auth.currentUser == null) throw RuntimeException("Not logged in")
        val uid = auth.currentUser!!.uid
        val changes = mapOf("lat" to city.lat,"lng" to city.lng,"monitored" to city.monitored)
        db.collection("users").document(uid)
            .collection("cities").document(city.name!!).update(changes)
    }
}