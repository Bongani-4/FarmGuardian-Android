package com.example.farmguardian

import Database
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
class DatabaseTest {

    private lateinit var database: Database

    @Before
    fun setup() {
        database = Database()
    }

    @Test
    fun testInitializeDatabaseWhenNodesExist(): Unit = runBlocking {
        // Given

        // Initializing the database to ensure the required nodes exist
        database.initializeDatabase()

        // When
        database.initializeDatabase()

        // Verify that no duplicate nodes are created for animal caretakers and no additional sample data is added
        val initialCaretakerList = database.getAcaretakerList().map { it.id }
        database.initializeDatabase() // Re-initialize to ensure no duplicate nodes are created
        val updatedCaretakerList = database.getAcaretakerList().map { it.id }

        assertThat(updatedCaretakerList).containsExactlyElementsIn(initialCaretakerList)
    }

    @Test
    fun testInitializeDatabase(): Unit = runBlocking {
        // When
        database.initializeDatabase()
        val caretakerList = database.getAcaretakerList()

        // Then
        // Is the  database is properly initialized
        val initialized = caretakerList.size>1

        assertThat(initialized).isTrue()
    }



    @Test
    fun testGetCaretakerList() = runBlocking {
        // When
        val caretakerList = database.getAcaretakerList()

        // Then
        // Verifying that the retrieved list contains  animal caretakers fetched from the Firebase Realtime Database
        assertThat(caretakerList).isNotEmpty()
    }

    @Test
    fun testSaveHiredCaretakerWithMissingDetails(): Unit = runBlocking {
        // Given
        val caretakerName = "" // Missing caretaker name
        val contacts = "1234567890"

        // When
        database.saveHiredCaretaker(caretakerName, contacts)

        // Then
        // Verifying that the hired caretaker is not saved to the Firebase Realtime Database
        val caretakerList = database.getHiredCaretakers()
        val hiredCaretaker = caretakerList.find { it.fullNames == caretakerName }
        assertThat(hiredCaretaker).isNull()
    }


}























