package com.example.eventpoc;


import com.example.eventpoc.user.AppUser;
import com.example.eventpoc.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @AfterEach
    public void cleanUp(){
        //TODO durch Testcontainer ersetzen
        userService.deleteAll();
    }

    @Test
    public void testCreateSingle() {
        /* Default User bekommt erst bei der Persistierung eine Id zugewiesen */
        /* Default User wird erstellt und es wird automatisch eine Id generiert */
        /* Default User wird erstellt und zurückgegeben */
        AppUser defaultUser = new AppUser();
        assertEquals(0, defaultUser.getId());
        var createdDefaultUser = userService.create(defaultUser);
        assertNotEquals(0, defaultUser.getId());
        assertEquals(defaultUser, createdDefaultUser);

        /* User wird erstellt und zurückgegeben */
        /* User mit Name wurde mit Name persistiert und zurückgegeben - kein Lazyloading */
        /* Default User und User mit Name sind nicht die selben */
        AppUser userWithName = new AppUser("UserName");
        var createdUserWithName = userService.create(userWithName);
        assertEquals(userWithName, createdUserWithName);
        assertEquals(userWithName.getName(), createdUserWithName.getName());
        assertNotEquals(defaultUser, createdUserWithName);
    }

    @Test
    public void testGetUserById() {
        /* Default user erstellt, der dann korrekt gefunden wird*/
        AppUser defaultUser = new AppUser();
        userService.create(defaultUser);
        var foundDefaultUser = userService.getUserById(defaultUser.getId()).get();
        assertEquals(defaultUser, foundDefaultUser);

        /* Mehrmaliges finden des Users ändert nichts am Ergebnis */
        var againFoundDefaultUser = userService.getUserById(defaultUser.getId()).get();
        assertEquals(foundDefaultUser, againFoundDefaultUser);

        /* Anderen User erstellt, welcher trotz vorhanden sein des ersten users, ebenso gefunden wird */
        AppUser differentUser = new AppUser();
        userService.create(differentUser);
        var foundDifferentUser = userService.getUserById(differentUser.getId()).get();
        assertEquals(differentUser, foundDifferentUser);

        /* Die beiden gefundenen User unterscheiden sich */
        assertNotEquals(foundDefaultUser, foundDifferentUser);

        /* Nicht persistierter User wird nicht gefunden */
        AppUser notPersistedUser = new AppUser();
        var foundNotPersistedUser = userService.getUserById(notPersistedUser.getId());
        assertTrue(foundNotPersistedUser.isEmpty());

        /* Nicht existente User-Id gibt keinen User zurück */
        var foundNotExistingUser = userService.getUserById(-1l);
        assertTrue(foundNotExistingUser.isEmpty());
    }

    @Test
    public void testGetUserByObj() {
        /* Default user erstellt, der dann korrekt gefunden wird*/
        AppUser defaultUser = new AppUser();
        userService.create(defaultUser);
        var foundDefaultUser = userService.getUserByObj(defaultUser).get();
        assertEquals(defaultUser, foundDefaultUser);

        /* Mehrmaliges finden des Users ändert nichts am Ergebnis */
        var againFoundDefaultUser = userService.getUserByObj(defaultUser).get();
        assertEquals(foundDefaultUser, againFoundDefaultUser);

        /* Anderer User erstellt, welcher trotz vorhanden sein des ersten users, ebenso gefunden wird */
        /* Anderer User hat einen Namen, dieser wird mitgeliefert - kein LazyLoading */
        final String userName = "UserName";
        AppUser userWithName = new AppUser(userName);
        userService.create(userWithName);
        var foundDifferentUser = userService.getUserByObj(userWithName).get();
        assertEquals(userWithName, foundDifferentUser);
        assertEquals(userName, foundDifferentUser.getName());

        /* Die beiden gefundenen User unterscheiden sich */
        assertNotEquals(foundDefaultUser, foundDifferentUser);

        /* Nicht persistierter User wird nicht gefunden */
        AppUser notPersistedUser = new AppUser();
        var foundNotPersistedUser = userService.getUserByObj(notPersistedUser);
        assertTrue(foundNotPersistedUser.isEmpty());

        /* Null gibt keinen User zurück */
        AppUser nullUser = null;
        var foundNullUser = userService.getUserByObj(nullUser);
        assertTrue(foundNullUser.isEmpty());

        /* Nicht existente User-Id gibt keinen User zurück */
        AppUser notExistingUser = new AppUser();
        var foundNotExistingUser = userService.getUserByObj(notExistingUser);
        assertTrue(foundNotExistingUser.isEmpty());
    }

    @Test
    public void testUpdateSingle() {
        /* Gesetzer default Name und der persistierte default Name stimmen überein - Grundvoraussetzung */
        String defaultName = "DefaultName";
        AppUser defaultUser = new AppUser(defaultName);
        userService.create(defaultUser);
        String foundDefaultUserName = userService.getUserByObj(defaultUser).get().getName();
        assertEquals(defaultName, foundDefaultUserName);

        /* Sichergehen, dass die Name ursprünglich unterschiedlich sind */
        /* Updating des Namen des Users updatet diesen zum angegeben Wert */
        /* Ursprünglicher Name und ge-updateter Name unterscheiden sich */
        String newName = "NewName";
        defaultUser.setName(newName);
        userService.update(defaultUser);
        var foundNewUserName = userService.getUserByObj(defaultUser).get().getName();
        assertNotEquals(defaultName, newName);
        assertEquals(newName, foundNewUserName);
        assertNotEquals(defaultName, foundNewUserName);
    }

    @Test
    public void testDeleteSingle() {
        AppUser defaultUser = new AppUser();
        userService.create(defaultUser);

        /* Persistierter User wird gefunden */
        var foundDefaultUser = userService.getUserByObj(defaultUser);
        assertTrue(foundDefaultUser.isPresent());

        /* Peristierter User wird gelöscht und nicht mehr gefunden */
        userService.delete(defaultUser);
        foundDefaultUser = userService.getUserByObj(defaultUser);
        assertTrue(foundDefaultUser.isEmpty());
    }

    @Test
    public void testDeleteById() {
        AppUser defaultUser = new AppUser();
        userService.create(defaultUser);

        /* Persistierter User wird gefunden */
        var foundDefaultUser = userService.getUserByObj(defaultUser);
        assertTrue(foundDefaultUser.isPresent());

        /* Peristierter User wird gelöscht und nicht mehr gefunden */
        userService.delete(defaultUser.getId());
        foundDefaultUser = userService.getUserByObj(defaultUser);
        assertTrue(foundDefaultUser.isEmpty());
    }

    @Test
    public void testCreateMultiple() {
        AppUser defaultUser1 = new AppUser();
        AppUser defaultUser2 = new AppUser();
        AppUser defaultUser3 = new AppUser();

        List<AppUser> defaultUsers = List.of(defaultUser1, defaultUser2, defaultUser3);
        List<AppUser> persistedUsers = userService.create(defaultUsers);

        /* Persistierte User haben eine Id */
        for (AppUser user : persistedUsers) {
            assertTrue(user.getId() != 0);
        }

        /* Default Users werden erstellt und können gefunden werden */
        for (AppUser user : defaultUsers) {
            assertTrue(userService.getUserByObj(user).isPresent());
        }

        /* Gefundene Users unterscheiden sich */
        assertNotEquals(defaultUser1, defaultUser2);
        assertNotEquals(defaultUser2, defaultUser3);
        assertNotEquals(defaultUser3, defaultUser1);
    }

    @Test
    public void testGetAllUsers() {
        AppUser defaultUser1 = new AppUser();
        AppUser defaultUser2 = new AppUser();
        AppUser defaultUser3 = new AppUser();
        userService.create(List.of(defaultUser1, defaultUser2, defaultUser3));

        /* Gefundene Users müssen alle bei create übergebene User sein */
        List<AppUser> foundUsers = userService.getAllUsers();
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser1.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser2.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser3.getId()));

        /* Gefundene Users müssen IMMERNOCH alle bei create übergebene User sein */
        AppUser defaultUser4 = new AppUser();
        foundUsers = userService.getAllUsers();
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser1.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser2.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser3.getId()));

        /* Nicht erstelle User sind nicht dabei */
        assertFalse(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser4.getId()));

        /* Neu ersteller User ist nun unter allen Usern*/
        userService.create(defaultUser4);
        foundUsers = userService.getAllUsers();
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser4.getId()));

        /* Gelöschter User ist nicht mehr unter allen Usern */
        userService.delete(defaultUser4);
        foundUsers = userService.getAllUsers();
        assertTrue(foundUsers.stream().noneMatch(u -> u.getId() == defaultUser4.getId()));
    }

    @Test
    public void testGetAllUsersById() {
        AppUser defaultUser1 = new AppUser();
        AppUser defaultUser2 = new AppUser();
        AppUser defaultUser3 = new AppUser();
        userService.create(List.of(defaultUser1, defaultUser2, defaultUser3));

        /* Gefundene Users müssen alle bei create übergebene User sein */
        List<AppUser> foundUsers = userService.getUserById(List.of(defaultUser1.getId(), defaultUser2.getId(), defaultUser3.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser1.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser2.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser3.getId()));

        /* Gefundene Users müssen IMMERNOCH alle bei create übergebene User sein */
        AppUser defaultUser4 = new AppUser();
        foundUsers = userService.getUserById(List.of(defaultUser1.getId(), defaultUser2.getId(), defaultUser3.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser1.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser2.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser3.getId()));

        /* Nicht erstelle User sind nicht dabei */
        assertFalse(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser4.getId()));
    }

    @Test
    public void testGetUserByObjMult() {
        AppUser defaultUser1 = new AppUser();
        AppUser defaultUser2 = new AppUser();
        AppUser defaultUser3 = new AppUser();
        userService.create(List.of(defaultUser1, defaultUser2, defaultUser3));

        /* Gefundene Users müssen alle bei create übergebene User sein */
        List<AppUser> foundUsers = userService.getUserByObj(defaultUser1, defaultUser2, defaultUser3);
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser1.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser2.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser3.getId()));

        /* Gefundene Users müssen IMMERNOCH alle bei create übergebene User sein */
        AppUser defaultUser4 = new AppUser();
        foundUsers = userService.getUserByObj(defaultUser1, defaultUser2, defaultUser3);
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser1.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser2.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser3.getId()));

        /* Nicht erstelle User sind nicht dabei */
        assertFalse(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser4.getId()));

    }

    @Test
    public void testGetUserByObjMultList() {
        AppUser defaultUser1 = new AppUser();
        AppUser defaultUser2 = new AppUser();
        AppUser defaultUser3 = new AppUser();
        userService.create(List.of(defaultUser1, defaultUser2, defaultUser3));

        /* Gefundene Users müssen alle bei create übergebene User sein */
        List<AppUser> foundUsers = userService.getUserByObj(List.of(defaultUser1, defaultUser2, defaultUser3));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser1.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser2.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser3.getId()));

        /* Gefundene Users müssen IMMERNOCH alle bei create übergebene User sein */
        AppUser defaultUser4 = new AppUser();
        foundUsers = userService.getUserByObj(List.of(defaultUser1, defaultUser2, defaultUser3));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser1.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser2.getId()));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser3.getId()));

        /* Nicht erstelle User sind nicht dabei */
        assertFalse(foundUsers.stream().anyMatch(u -> u.getId() == defaultUser4.getId()));
    }

    @Test
    public void testUpdate() {
        /* Gesetzer default Name und der persistierte default Name stimmen überein - Grundvoraussetzung */
        String defaultName = "DefaultName";
        AppUser defaultUser1 = new AppUser(defaultName);
        AppUser defaultUser2 = new AppUser(defaultName);
        AppUser defaultUser3 = new AppUser(defaultName);
        userService.create(List.of(defaultUser1, defaultUser2, defaultUser3));
        List<AppUser> foundUsers = userService.getUserById(List.of(defaultUser1.getId(), defaultUser2.getId(), defaultUser3.getId()));
        for (AppUser user : foundUsers) {
            assertEquals(defaultName, user.getName());
        }

        /* Sichergehen, dass die Name ursprünglich unterschiedlich sind */
        /* Updating des Namen des Users updatet diesen zum angegeben Wert */
        /* Ursprünglicher Name und ge-updateter Name unterscheiden sich */
        String newName = "NewName";
        for (AppUser user : foundUsers) {
            user.setName(newName);
        }
        userService.update(foundUsers);
        foundUsers = userService.getUserById(List.of(defaultUser1.getId(), defaultUser2.getId(), defaultUser3.getId()));
        assertNotEquals(defaultName, newName);
        for (AppUser user : foundUsers) {
            assertEquals(newName, user.getName());
        }
        assertNotEquals(defaultName, foundUsers.get(0).getName());

        /* Updaten von nur einem User updatet nur diesen User */
        String otherName = "OtherName";
        assertNotEquals(defaultName, otherName);
        assertNotEquals(newName, otherName);
        defaultUser1.setName(otherName);
        userService.update(List.of(defaultUser1));

        String foundOtherName = userService.getUserByObj(defaultUser1).get().getName();
        assertEquals(otherName, foundOtherName);
        var foundUsersAgain = userService.getUserById(List.of(defaultUser2.getId(), defaultUser3.getId()));
        for(var user: foundUsersAgain){
            assertNotEquals(otherName, user.getName());
        }
    }

    @Test
    public void testDelete() {
        AppUser defaultUser1 = new AppUser();
        AppUser defaultUser2 = new AppUser();
        AppUser defaultUser3 = new AppUser();
        userService.create(List.of(defaultUser1, defaultUser2, defaultUser3));

        /* Alle angegebenen User werden gelöscht */
        /* Genau nur die Users die angegeben wurden werden gelöscht */
        userService.delete(List.of(defaultUser1, defaultUser2));
        var foundSpecificUsers = userService.getUserById(List.of(defaultUser1.getId(), defaultUser2.getId()));
        assertEquals(0, foundSpecificUsers.size());
        var foundUsers = userService.getAllUsers();
        assertEquals(1, foundUsers.size());
        assertEquals(defaultUser3, foundUsers.get(0));
    }
}
