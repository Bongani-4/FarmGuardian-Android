package com.example.farmguardian;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



public class AcaretakerModelTest {




        private AcaretakerModel caretaker;

        @Before
        public void setUp() {
            caretaker = new AcaretakerModel("1", "John Doe", "Location", "123456789", "5 years", 1);
        }

        @Test
        public void testConstructorWithId() {
            assertNotNull(caretaker);
            assertEquals("1", caretaker.getId());
            assertEquals("John Doe", caretaker.getFullNames());
            assertEquals("Location", caretaker.getLocation());
            assertEquals("123456789", caretaker.getContact());
            assertEquals("5 years", caretaker.getExperience());
            assertEquals(1, caretaker.isAvailable());
        }

        @Test
        public void testConstructorWithoutId() {
            AcaretakerModel caretakerWithoutId = new AcaretakerModel("John Doe", "Location", "123456789", "5 years", 1);
            assertNotNull(caretakerWithoutId);
            assertEquals("John Doe", caretakerWithoutId.getFullNames());
            assertEquals("Location", caretakerWithoutId.getLocation());
            assertEquals("123456789", caretakerWithoutId.getContact());
            assertEquals("5 years", caretakerWithoutId.getExperience());
            assertEquals(1, caretakerWithoutId.isAvailable());
        }


    }

