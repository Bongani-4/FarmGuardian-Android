package com.example.farmguardian

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import io.mockk.mockk
import io.mockk.every
import io.mockk.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class BecomeAnimalcaretakerFragmentTest {

    @Mock
    private lateinit var mockFirebaseAuth: FirebaseAuth

    @Mock
    private lateinit var mockDatabaseReference: DatabaseReference

    private lateinit var fragment: BecomeAnimalcaretakerFragment

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        fragment = BecomeAnimalcaretakerFragment()
     //   fragment.firebaseAuth = mockFirebaseAuth
     //   fragment.databaseReference = mockDatabaseReference


// Mocking the LayoutInflater and View
        val inflater = mockk<LayoutInflater>()
        val view = mockk<View>()
// Mocking the behavior of inflater.inflate(...)
        every { inflater.inflate(any<Int>(), any(), any()) } returns view


// Call onViewCreated with the mocked view
        fragment.onViewCreated(view, null)


    }
    @Test
    fun testButtonClickWithData() {

        // Mocking UI components
        val contactsEditText = mockk<EditText>()
        val locationEditText = mockk<EditText>()
        val fullnamesEditText = mockk<EditText>()
        val experienceEditText = mockk<EditText>()
        val availableCheckBox = mockk<CheckBox>()
        val doneButton = mockk<Button>()

        // Set up the fragment
        val fragment = BecomeAnimalcaretakerFragment().apply {
            contacts = contactsEditText
            location = locationEditText
            fullnames = fullnamesEditText
            experience = experienceEditText
            CBavailable = availableCheckBox
            btnDone = doneButton
        }

        // Set up expected input
        val contacts = "123456789"
        val location = "Location"
        val fullnames = "John Doe"
        val experience = "5 years"

        // Mocking user input
        `when`(contactsEditText.text.toString()).thenReturn(contacts)
        `when`(locationEditText.text.toString()).thenReturn(location)
        `when`(fullnamesEditText.text.toString()).thenReturn(fullnames)
        `when`(experienceEditText.text.toString()).thenReturn(experience)
        `when`(availableCheckBox.isChecked).thenReturn(true)

        // Performing button click
        doneButton.performClick()

        // Mocking user input
        every { contactsEditText.text } returns mockk<Editable>().also {
            every { it.toString() } returns contacts
        }
        every { locationEditText.text } returns mockk<Editable>().also {
            every { it.toString() } returns location
        }
        every { fullnamesEditText.text } returns mockk<Editable>().also {
            every { it.toString() } returns fullnames
        }
        every { experienceEditText.text } returns mockk<Editable>().also {
            every { it.toString() } returns experience
        }

        // Perform button click
        fragment.btnDone.performClick()

        // Verify Firebase operations
        verify(mockDatabaseReference).child("ACUser").child(anyString()).child("contacts")
            .setValue(anyString())
        verify(mockDatabaseReference).child("ACUser").child(anyString()).child("location")
            .setValue(anyString())
        verify(mockDatabaseReference).child("ACUser").child(anyString()).child("fullnames")
            .setValue(anyString())
        verify(mockDatabaseReference).child("ACUser").child(anyString()).child("experience")
            .setValue(anyString())
        verify(mockDatabaseReference).child("ACUser").child(anyString()).child("available")
            .setValue(anyBoolean())
    }
}

