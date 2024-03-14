import android.util.Log
import com.example.farmguardian.AcaretakerModel
import com.github.javafaker.Faker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await
import java.util.*

class Database {

    private val usersRef: DatabaseReference
    private val toBeHiredRef: DatabaseReference
    private val hiredRef: DatabaseReference

    init {
        val firebaseDatabase = FirebaseDatabase.getInstance()

        // Reference to the "users" node where user profiles are stored
        usersRef = firebaseDatabase.getReference("users")

        // Reference to the "toBeHired" node where animal caretakers to be hired are stored
        toBeHiredRef = firebaseDatabase.getReference("toBeHired")

        // Reference to the "hired" node where hired caretakers are stored
        hiredRef = firebaseDatabase.getReference("hired")
    }

    // Method to save user profile to Firebase Realtime Database
    suspend fun saveProfile(username: String, email: String, password: String): Boolean {
        return try {
            val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            val currentUserRef = usersRef.child(authResult.user?.uid ?: "")
            currentUserRef.child("username").setValue(username)
            currentUserRef.child("email").setValue(email)
            currentUserRef.child("password").setValue(password)
            currentUserRef.child("request").setValue(0)
            true
        } catch (e: Exception) {
            Log.e("Database", "Error saving profile: ${e.message}")
            false
        }
    }

    // Method to save hired caretaker to Firebase Realtime Database
    suspend fun saveHiredCaretaker(caretakerName: String, contacts: String) {
        val hiredCaretakerRef = hiredRef.push()
        hiredCaretakerRef.child("caretaker_name").setValue(caretakerName)
        hiredCaretakerRef.child("contact").setValue(contacts)
    }

    // Method to retrieve list of animal caretakers from Firebase Realtime Database
    suspend fun getAcaretakerList(): List<AcaretakerModel> {
        val caretakerList = mutableListOf<AcaretakerModel>()
        val acUserRef = FirebaseDatabase.getInstance().getReference("ACUser").get().await()
        acUserRef.children.forEach { snapshot ->
            val fullNames = snapshot.child("fullnames").getValue(String::class.java) ?: ""
            val location = snapshot.child("location").getValue(String::class.java) ?: ""
            val contacts = snapshot.child("contacts").getValue(String::class.java) ?: ""
            val experience = snapshot.child("experience").getValue(String::class.java) ?: ""
            val CBavailable = snapshot.child("CBavailable").getValue(Int::class.java) ?: 0

            val caretaker = AcaretakerModel(fullNames, location, contacts, experience, CBavailable)
            caretakerList.add(caretaker)
        }
        return caretakerList
    }

    // Method to add sample data for animal caretakers
    private fun addAnimalCaretakers() {
        val faker = Faker()

        for (i in 0 until 20) {
            val caretakerRef = FirebaseDatabase.getInstance().getReference("ACUser").push()
            caretakerRef.child("username").setValue(faker.name().username())
            caretakerRef.child("contacts").setValue(faker.phoneNumber().cellPhone())
            caretakerRef.child("location").setValue(faker.address().city())
            caretakerRef.child("fullnames").setValue(faker.name().fullName())
            caretakerRef.child("experience").setValue((Random().nextInt(10) + i).toString())
            caretakerRef.child("CBavailable").setValue(i % 2)
        }
    }

    // Method to initialize the database
    suspend fun initializeDatabase() {
        val rootRef = FirebaseDatabase.getInstance().reference

        // Check if "users" node already exists
        rootRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // "users" node doesn't exist, create it
                    val usersNodeRef = rootRef.child("users")
                    usersNodeRef.setValue("")

                    // Check if "toBeHired" node already exists
                    usersNodeRef.child("toBeHired").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                // "toBeHired" node doesn't exist, create it
                                val toBeHiredNodeRef = usersNodeRef.child("toBeHired")
                                toBeHiredNodeRef.setValue("")
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("Database", "Error checking 'toBeHired' node existence: ${databaseError.message}")
                        }
                    })

                    // Check if "hired" node already exists
                    usersNodeRef.child("hired").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                // "hired" node doesn't exist, create it
                                val hiredNodeRef = usersNodeRef.child("hired")
                                hiredNodeRef.setValue("")
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("Database", "Error checking 'hired' node existence: ${databaseError.message}")
                        }
                    })
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Database", "Error checking 'users' node existence: ${databaseError.message}")
            }
        })

        // Check if "ACUser" node already exists
        rootRef.child("ACUser").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // "ACUser" node doesn't exist, create it
                    val acUserNodeRef = rootRef.child("ACUser")
                    acUserNodeRef.setValue("")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Database", "Error checking 'ACUser' node existence: ${databaseError.message}")
            }
        })

        // Add sample data for animal caretakers
        addAnimalCaretakers()
    }
}
