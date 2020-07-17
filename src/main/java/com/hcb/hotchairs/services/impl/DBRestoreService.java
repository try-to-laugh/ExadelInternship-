package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.daos.restore.IDBRestoreCitiesDAO;
import com.hcb.hotchairs.daos.restore.IDBRestoreCommentsDAO;
import com.hcb.hotchairs.daos.restore.IDBRestoreCountriesDAO;
import com.hcb.hotchairs.daos.restore.IDBRestoreDetailsDAO;
import com.hcb.hotchairs.daos.restore.IDBRestoreFloorsDAO;
import com.hcb.hotchairs.daos.restore.IDBRestoreOfficesDAO;
import com.hcb.hotchairs.daos.restore.IDBRestorePlacesDAO;
import com.hcb.hotchairs.daos.restore.IDBRestoreReservationsDAO;
import com.hcb.hotchairs.daos.restore.IDBRestoreRolesDAO;
import com.hcb.hotchairs.daos.restore.IDBRestoreTagsDAO;
import com.hcb.hotchairs.daos.restore.IDBRestoreUserDAO;
import com.hcb.hotchairs.entities.City;
import com.hcb.hotchairs.entities.Comment;
import com.hcb.hotchairs.entities.Country;
import com.hcb.hotchairs.entities.Floor;
import com.hcb.hotchairs.entities.Office;
import com.hcb.hotchairs.entities.Place;
import com.hcb.hotchairs.entities.Role;
import com.hcb.hotchairs.entities.Tag;
import com.hcb.hotchairs.entities.User;
import com.hcb.hotchairs.services.IDBRestoreService;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class DBRestoreService implements IDBRestoreService {

    @Autowired
    private IDBRestoreUserDAO restoreUserDAO;
    @Autowired
    private IDBRestoreTagsDAO restoreTagsDAO;
    @Autowired
    private IDBRestoreRolesDAO restoreRolesDAO;
    @Autowired
    private IDBRestoreReservationsDAO restoreReservationsDAO;
    @Autowired
    private IDBRestorePlacesDAO restorePlacesDAO;
    @Autowired
    private IDBRestoreOfficesDAO restoreOfficesDAO;
    @Autowired
    private IDBRestoreFloorsDAO restoreFloorsDAO;
    @Autowired
    private IDBRestoreDetailsDAO restoreDetailsDAO;
    @Autowired
    private IDBRestoreCountriesDAO restoreCountriesDAO;
    @Autowired
    private IDBRestoreCommentsDAO restoreCommentsDAO;
    @Autowired
    private IDBRestoreCitiesDAO restoreCitiesDAO;

    @Override
    @Transactional
    @Modifying
    public void restore() {
        restoreCommentsDAO.clearCommentTable();
        restoreDetailsDAO.clearDetailTable();
        restoreReservationsDAO.clearReservationTable();

        restoreUserDAO.clearUserTable();
        restorePlacesDAO.clearPlaceTable();
        restoreTagsDAO.clearTagTable();
        restoreRolesDAO.clearRoleTable();

        restoreFloorsDAO.clearFloorTable();
        restoreOfficesDAO.clearOfficeTable();
        restoreCitiesDAO.clearCityTable();
        restoreCountriesDAO.clearCountryTable();

        Role admin = new Role(null, "Admin", null, null);
        Role hr = new Role(null, "HR", null, null);
        Role oManager = new Role(null, "Office Manager", null, null);
        Role cppDev = new Role(null, "C++ Developer", null, null);
        Role qa = new Role(null, "QA", null, null);
        Role all = new Role(null, "All", null, null);

        admin = restoreRolesDAO.save(admin);
        hr = restoreRolesDAO.save(hr);
        oManager = restoreRolesDAO.save(oManager);
        cppDev = restoreRolesDAO.save(cppDev);
        qa = restoreRolesDAO.save(qa);
        all = restoreRolesDAO.save(all);

        User user2 = restoreUserDAO.save(
                new User(null, "Jetta Hamilton", "amusing_jetta@gmail.com",
                        "$2a$10$Hy2QZt8dkk9o4YLLADooPuEcZa2t9bPIOc6ZWI5qemPQVGIiRhALe",
                        null, null, new ArrayList<>(), null, null));

        User user1 = restoreUserDAO.save(
                new User(null, "Khalila Payne", "khalilapayne98@yandex.ru",
                        "$2a$10$6SbaKpoom9lny9Zr9ywBQOT0YK//8bc3AH9gknDHNfDJfEGpu/VGi",
                        user2, null, new ArrayList<>(), null, null));

        User user3 = restoreUserDAO.save(
                new User(null, "Anila Armstrong", "anila_armstrong_07@mail.ru",
                        "$2a$10$OyzgHtYPBhYn5QV0q71cQ.Ui1W.UNlJloQBDoj4M/SY1HS1x1yrl.",
                        user2, null, new ArrayList<>(), null, null));

        User user4 = restoreUserDAO.save(
                new User(null, "Eugina Willis", "zibiahwillis@bsu.by",
                        "$2a$10$jAzPVrPKRsB5/IBszp3EIudgXmnmaReFtFo2SOdkbssEdOOV.EVPm",
                        null, null, new ArrayList<>(), null, null));

        User user5 = restoreUserDAO.save(
                new User(null, "Alanis Kelley", "aerated_alanis@yahoo.com",
                        "$2a$10$uag0.oszIIp3UQHL6t3bJOxzU56Pl.qaRDsRw47Z/YwhGyx9W84mu",
                        user2, null, new ArrayList<>(), null, null));

        User user6 = restoreUserDAO.save(
                new User(null, "Janey Dean", "seinejaney@gmail.com",
                        "$2a$10$Dt6WQX2w0xoPbx10ZQEAzuEsauEMujl/fwbOyj89E/8byr0MdSMou",
                        user4, null, new ArrayList<>(), null, null));

        User user7 = restoreUserDAO.save(
                new User(null, "Tam Roberts", "tamroberts06@protonmail.com",
                        "$2a$10$gSz581vIBFLAmEZuJgJlI.6RIO1vxn5Xx71Vt8yEo7DlWekdwOy9S",
                        user2, null, new ArrayList<>(), null, null));

        User user8 = restoreUserDAO.save(
                new User(null, "Oliver Hart", "smart_hart@yandex.ru",
                        "$2a$10$KlhBC6CRs3HxbIJE5pQNbe38t5zkY..NtGPmf8UCdmXSll4h/LEmO",
                        user2, null, new ArrayList<>(), null, null));

        User user9 = restoreUserDAO.save(
                new User(null, "Elfreda Ortiz   ", "squeezeortiz@mail.ru",
                        "$2a$10$01KYmppcq2jNsOXrlL/a3eoK6D5VtnDB9m0W3famCY/gvjALmdiXS",
                        user4, null, new ArrayList<>(), null, null));

        User user10 = restoreUserDAO.save(
                new User(null, "Latoya Porter   ", "latoyaporter70@gmail.com",
                        "$2a$10$b0SWBQWlPnZvbzp1.njSZO0cnBGfzkEDrIYnHR7kfx2b8euf33n5y",
                        user4, null, new ArrayList<>(), null, null));

        user1.getRoles().add(admin);
        user2.getRoles().add(hr);
        user3.getRoles().add(oManager);
        user4.getRoles().add(hr);
        user4.getRoles().add(oManager);
        user5.getRoles().add(cppDev);
        user7.getRoles().add(cppDev);
        user8.getRoles().add(cppDev);
        user10.getRoles().add(cppDev);
        user6.getRoles().add(qa);
        user9.getRoles().add(qa);

        user1.getRoles().add(all);
        user2.getRoles().add(all);
        user3.getRoles().add(all);
        user4.getRoles().add(all);
        user5.getRoles().add(all);
        user6.getRoles().add(all);
        user7.getRoles().add(all);
        user8.getRoles().add(all);
        user9.getRoles().add(all);
        user10.getRoles().add(all);

        Tag tag1 = restoreTagsDAO.save(new Tag((long) 1, "Window", null));
        Tag tag2 = restoreTagsDAO.save(new Tag((long) 2, "Wardrobe", null));
        Tag tag3 = restoreTagsDAO.save(new Tag((long) 3, "Conditioner", null));
        Tag tag4 = restoreTagsDAO.save(new Tag((long) 4, "WС Near", null));
        Tag tag5 = restoreTagsDAO.save(new Tag((long) 5, "Cookies Near", null));

        // TODO: Country pictures

        Country belarus = restoreCountriesDAO.save(new Country((long) 1, "Belarus", null,
                "Wait, we find resource to place our pictures",
                "Wait, we find resource to place our pictures"));
        Country ukraine = restoreCountriesDAO.save(new Country((long) 2, "Ukraine", null,
                "Wait, we find resource to place our pictures",
                "Wait, we find resource to place our pictures"));
        Country russia = restoreCountriesDAO.save(new Country((long) 3, "Russia",  null,
                "Wait, we find resource to place our pictures",
                "Wait, we find resource to place our pictures"));

        City minsk = restoreCitiesDAO.save(new City((long) 1, "Minsk", null, belarus));
        City kharkiv = restoreCitiesDAO.save(new City((long) 2, "Kharkiv", null, ukraine));
        City chely = restoreCitiesDAO.save(new City((long) 3, "Chelyabinsk", null, russia));

        Office om1 = restoreOfficesDAO.save(new Office((long) 1, "Akademika Kuprevicha str., 3", null, minsk));
        Office om2 = restoreOfficesDAO.save(new Office((long) 2, "Pritizkogo str., 156", null, minsk));
        Office okh1 = restoreOfficesDAO.save(new Office((long) 3, "Nauky av., 27B", null, kharkiv));
        Office oche1 = restoreOfficesDAO.save(new Office((long) 4, "Lenina str., 55A", null, chely));

        Floor m11 = restoreFloorsDAO.save(new Floor((long) 1, "1 Floor", null, om1));
        Floor m12 = restoreFloorsDAO.save(new Floor((long) 2, "2 Floor", null, om1));
        Floor m13 = restoreFloorsDAO.save(new Floor((long) 3, "3 Floor", null, om1));
        Floor m21 = restoreFloorsDAO.save(new Floor((long) 4, "1 Floor", null, om2));
        Floor fkh1 = restoreFloorsDAO.save(new Floor((long) 5, "1 Floor", null, okh1));
        Floor fche1 = restoreFloorsDAO.save(new Floor((long) 6, "1 Floor", null, oche1));

        Place place1m11 = restorePlacesDAO.save(
                new Place(null, "Coworking Table", (long) 1,
                        new ArrayList<>(), new ArrayList<>(), m11, null, null));
        Place place2m11 = restorePlacesDAO.save(
                new Place(null, "Couch", (long) 3,
                        new ArrayList<>(), new ArrayList<>(), m11, null, null));
        Place place1m12 = restorePlacesDAO.save(
                new Place(null, "Workstation", (long) 1,
                        new ArrayList<>(), new ArrayList<>(), m12, null, null));
        Place place1m13 = restorePlacesDAO.save(
                new Place(null, "Sofa", (long) 2,
                        new ArrayList<>(), new ArrayList<>(), m13, null, null));
        Place place1m21 = restorePlacesDAO.save(
                new Place(null, "Wooden Chair", (long) 1,
                        new ArrayList<>(), new ArrayList<>(), m21, null, null));
        Place place1fkh1 = restorePlacesDAO.save(
                new Place(null, "Wooden Chair", (long) 1,
                        new ArrayList<>(), new ArrayList<>(), fkh1, null, null));
        Place place1fche1 = restorePlacesDAO.save(
                new Place(null, "Meeting room", (long) 8,
                        new ArrayList<>(), new ArrayList<>(), fche1, null, null));

        place1m11.getTags().add(tag1);
        place1m11.getTags().add(tag5);
        place1m11.getTags().add(tag4);

        place2m11.getTags().add(tag4);

        place1m12.getTags().add(tag2);

        place1m13.getTags().add(tag3);
        place1m13.getTags().add(tag4);
        place1m13.getTags().add(tag5);

        place1m21.getTags().add(tag2);
        place1m21.getTags().add(tag3);

        place1fkh1.getTags().add(tag3);

        place1fche1.getTags().add(tag2);
        place1fche1.getTags().add(tag4);

        place1m11.getRoles().add(all);

        place2m11.getRoles().add(cppDev);
        place2m11.getRoles().add(qa);

        place1m12.getRoles().add(all);

        place1m13.getRoles().add(qa);

        place1m21.getRoles().add(all);

        place1fkh1.getRoles().add(hr);

        place1fche1.getRoles().add(all);


        Comment comment1 = restoreCommentsDAO.save(new Comment(null, user1,
                new Timestamp(System.currentTimeMillis()), "Very comfortable place!", place1m11));
        Comment comment2 = restoreCommentsDAO.save(new Comment(null, user4,
                new Timestamp(System.currentTimeMillis()), "Not very good tbh.", place1m11));
        Comment comment3 = restoreCommentsDAO.save(new Comment(null, user5,
                new Timestamp(System.currentTimeMillis()), "Not enough light in the evening.", place1m12));
        Comment comment4 = restoreCommentsDAO.save(new Comment(null, user1,
                new Timestamp(System.currentTimeMillis()), "Very comfortable seat!", place2m11));
        Comment comment5 = restoreCommentsDAO.save(new Comment(null, user6,
                new Timestamp(System.currentTimeMillis()), "This place is overcrowded.", place2m11));
        Comment comment6 = restoreCommentsDAO.save(new Comment(null, user7,
                new Timestamp(System.currentTimeMillis()), "All is OK, but the atmosphere here is just depressing", place1m13));
        Comment comment7 = restoreCommentsDAO.save(new Comment(null, user10,
                new Timestamp(System.currentTimeMillis()), "Office Manager, do NOT touch this chair!", place1fkh1));
        Comment comment8 = restoreCommentsDAO.save(new Comment(null, user2,
                new Timestamp(System.currentTimeMillis()), "It's really hot here, we definitely need conditioner...", place1m12));
    }
}
