package ru.team.up.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest

class TeamupCoreApplicationTests extends Assertions {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestsRepository interestsRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserMessageRepository userMessageRepository;

    private Admin adminTest;

    private Moderator moderatorTest;

    private Interests interestsTest1, interestsTest2;

    private User subscriberTest1, subscriberTest2;

    private Set<Interests> interestsSet = new HashSet<>();

    private User userTest;

    private Status statusTest;

    private EventType eventTypeTest;

    private Event eventTest;

    @BeforeEach
    public void setUpEntity() {
        adminTest = Admin.builder()
                .name("testAdmin")
                .lastName("testAdminLastName")
                .middleName("testAdminMiddleName")
                .login("testAdminLogin")
                .email("testAdmin@mail.ru")
                .password("admin")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .build();

        moderatorTest = Moderator.builder()
                .name("testModerator")
                .lastName("testModeratorLastName")
                .middleName("testModeratorMiddleName")
                .login("testModeratorLogin")
                .email("testModerator@mail.ru")
                .password("moderator")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .amountOfCheckedEvents(10L)
                .amountOfDeletedEvents(11L)
                .amountOfClosedRequests(12L)
                .build();

        interestsTest1 = Interests.builder()
                .title("Football")
                .shortDescription("Like to play football")
                .build();

        interestsTest2 = Interests.builder()
                .title("Fishing")
                .shortDescription("Like to going fishing")
                .build();

        userTest = User.builder()
                .name("testUser")
                .lastName("testUserLastName")
                .middleName("testUserMiddleName")
                .login("testUserLogin")
                .email("testUser@mail.ru")
                .password("user")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Moscow")
                .age(30)
                .aboutUser("testUserAbout")
                .build();

        subscriberTest1 = User.builder()
                .name("testSubscriber1Name")
                .lastName("testSubscriber1LastName")
                .middleName("testSubscriber1MiddleName")
                .login("testSubscriber1Login")
                .email("testSubscriber1@mail.ru")
                .password("subscriber1")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Rostov-on-Don")
                .age(35)
                .aboutUser("testSubscriber1About")
                .build();

        subscriberTest2 = User.builder()
                .name("testSubscriber2Name")
                .lastName("testSubscriber2LastName")
                .middleName("testSubscriber2MiddleName")
                .login("testSubscriber2Login")
                .email("testSubscriber2@mail.ru")
                .password("testSubscriber2")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Minsk")
                .age(40)
                .aboutUser("testSubscriber2About")
                .build();

        statusTest = Status.builder()
                .status("New")
                .build();

        eventTypeTest = EventType.builder()
                .type("Game")
                .build();

        eventTest = Event.builder()
                .eventName("Football game")
                .descriptionEvent("Join people to play football math")
                .placeEvent("Moscow")
                .timeEvent(LocalDateTime.now())
                .build();
    }

    @Test
    @Transactional
    void adminTest() {
        // Сохранили тестового админа в БД
        adminRepository.save(adminTest);
        // Получили назначенный ID
        Long testAdminId = adminTest.getId();

        // Проверка на наличие тестового админа с полученным ID
        assertNotNull(adminRepository.findById(testAdminId));

        // Создали нового тестового админа и получили его из БД по ID
        Admin testAdminBD = adminRepository.findById(testAdminId).get();

        // Проверили данные на совпадение
        assertEquals(testAdminBD.getName(), "testAdmin");
        assertEquals(testAdminBD.getLastName(), "testAdminLastName");
        assertEquals(testAdminBD.getLogin(), "testAdminLogin");
        assertEquals(testAdminBD.getEmail(), "testAdmin@mail.ru");
        assertEquals(testAdminBD.getPassword(), "admin");
        assertNotNull(testAdminBD.getAccountCreatedTime());
        assertNotNull(testAdminBD.getLastAccountActivity());

        // Удалили тестового админа по ID
        adminRepository.deleteById(testAdminId);
        // Проверили что тестового админа больше нет в БД
        assertEquals(adminRepository.findById(testAdminId), Optional.empty());
    }

    @Test
    @Transactional
    void moderatorTest() {
        // Сохранили тестового модератора в БД
        moderatorRepository.save(moderatorTest);
        // Получили назначенный ID
        Long testModeratorId = moderatorTest.getId();

        // Проверка на наличие тестового модератора с полученным ID
        assertNotNull(moderatorRepository.findById(testModeratorId));

        // Создали нового тестового модератора и получили его из БД по ID
        Moderator moderatorBD = moderatorRepository.findById(testModeratorId).get();

        // Проверили данные на совпадение
        assertEquals(moderatorBD.getName(), "testModerator");
        assertEquals(moderatorBD.getLastName(), "testModeratorLastName");
        assertEquals(moderatorBD.getLogin(), "testModeratorLogin");
        assertEquals(moderatorBD.getEmail(), "testModerator@mail.ru");
        assertEquals(moderatorBD.getPassword(), "moderator");
        assertEquals(moderatorBD.getAmountOfCheckedEvents(), 10L);
        assertEquals(moderatorBD.getAmountOfDeletedEvents(), 11L);
        assertEquals(moderatorBD.getAmountOfClosedRequests(), 12L);
        assertNotNull(moderatorBD.getAccountCreatedTime());
        assertNotNull(moderatorBD.getLastAccountActivity());

        // Удалили тестового модератора по ID
        moderatorRepository.deleteById(testModeratorId);
        // Проверили что тестового модератора больше нет в БД
        assertEquals(moderatorRepository.findById(testModeratorId), Optional.empty());
    }

    @Test
    @Transactional
    void userTest() {
        // Сохранили интересы
        interestsRepository.save(interestsTest1);
        interestsRepository.save(interestsTest2);
        // Получили назначенные ID
        Long interestIdTest1 = interestsTest1.getId();
        Long interestIdTest2 = interestsTest2.getId();
        // Создали список интересов
        Set<Interests> interestsSet = new HashSet<>();
        interestsSet.add(interestsTest1);
        interestsSet.add(interestsTest2);

        // Проверили что интересы с полученным ID существуют
        assertNotNull(interestsRepository.findById(interestIdTest1));
        assertNotNull(interestsRepository.findById(interestIdTest2));

        // Создали новые интересы и получили существующие интересы из БД по назначенным ID
        Interests interestsBD1 = interestsRepository.findById(interestIdTest1).get();
        Interests interestsBD2 = interestsRepository.findById(interestIdTest2).get();

        // Проверили данные на совпадение
        assertEquals(interestsBD1.getTitle(), "Football" );
        assertEquals(interestsBD1.getShortDescription(), "Like to play football");
        assertEquals(interestsBD2.getTitle(), "Fishing");
        assertEquals(interestsBD2.getShortDescription(), "Like to going fishing");

        // Сохранили тестового пользователя
        User testUser = userTest;
        // Добавили список интересов пользователю
        testUser.setUserInterests(interestsSet);
        userRepository.save(testUser);
        // Получили назначенный ID
        Long testUserId = testUser.getId();
        // Проверка на наличие тестового пользователя на назначенному ID
        assertNotNull(userRepository.findById(testUserId));
        // Создали нового тестового пользователя и получили данные из БД по назначенному ID
        User userBD = userRepository.findById(testUserId).get();

        // Проверили данные на соответствие
        assertEquals(userBD.getName(), "testUser");
        assertEquals(userBD.getLastName(), "testUserLastName");
        assertEquals(userBD.getMiddleName(), "testUserMiddleName");
        assertEquals(userBD.getLogin(), "testUserLogin");
        assertEquals(userBD.getEmail(), "testUser@mail.ru");
        assertEquals(userBD.getPassword(), "user");
        assertNotNull(userBD.getAccountCreatedTime());
        assertNotNull(userBD.getLastAccountActivity());
        assertEquals(userBD.getAge(), 30);
        assertEquals(userBD.getCity(), "Moscow");
        assertNotNull(userBD.getUserInterests());

        // Удалили первый интерес
        interestsRepository.deleteById(interestIdTest1);
        // Проверили что первый интерес отсутствует в БД
        assertEquals(interestsRepository.findById(interestIdTest1), Optional.empty());
        // Удалили второй интерес
        interestsRepository.deleteById(interestIdTest2);
        // Проверили что второй интерес отсутствует в БД
        assertEquals(interestsRepository.findById(interestIdTest2), Optional.empty());
        // Удалили тестового пользователя
        userRepository.deleteById(testUserId);
        // Проверили что тестовый пользователь отсутствует в БД
        assertEquals(userRepository.findById(testUserId), Optional.empty());
    }

    @Test
    @Transactional
    void subscribersTest(){
        // Создали первого подписчика
        User subscriber1 = subscriberTest1;

        // Создали второго подписчика
        User subscriber2 = subscriberTest2;

        // Сохранили подписчиков как пользователей
        userRepository.save(subscriber1);
        userRepository.save(subscriber2);

        // Получили назначенные ID
        Long subscriberId1 = subscriber1.getId();
        Long SubscriberId2 = subscriber2.getId();

        // Создали множество из двух подписчиков
        Set<User> setTwoSubscribers = new HashSet<>();
        setTwoSubscribers.add(subscriber1);
        setTwoSubscribers.add(subscriber2);

        // Создали пользователя с двумя подписчиками
        User testUser = User.builder()
                .name("testUserWithTwoSubscribersName")
                .lastName("testUserWithTwoSubscribersLastName")
                .middleName("testUserWithTwoSubscribersMiddleName")
                .login("testUserWithTwoSubscribersLogin")
                .email("testUserWithTwoSubscribers@mail.ru")
                .password("testUserWithTwoSubscribers")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Moskow")
                .age(30)
                .aboutUser("testUserWithTwoSubscribersAbout")
                .subscribers(setTwoSubscribers).build();

        // Сохранили пользователя с двумя подписчиками
        userRepository.save(testUser);
        // Получили назначенные ID
        Long testUserId = testUser.getId();

        // Проверка на наличие подписчиков у пользователя
        assertNotNull(userRepository.findById(testUserId).get().getSubscribers());

        // Создали множество из одного подписчика
        Set<User> setOneSubscriber = new HashSet<>();
        setOneSubscriber.add(subscriber2);

        // Создали нового тестового пользователя и получили его из БД
        User testUserBD = userRepository.findById(testUserId).get();

        // Назначили для нового пользователя одного подписчика
        testUserBD.setSubscribers(setOneSubscriber);
        userRepository.save(testUserBD);

        // Удалили из БД первого подписчика
        userRepository.deleteById(subscriberId1);

        // Проверили что у тестового пользователя остался один подписчик
        assertNotNull(userRepository.findById(testUserId).get().getSubscribers());

        // У тестового пользователя удалили всех подписчиков
        Set<User> subscribersEmpty = new HashSet<>();
        testUserBD.setSubscribers(subscribersEmpty);
        userRepository.save(testUserBD);

        // Удалили из БД второго подписчика
        userRepository.deleteById(SubscriberId2);

        // Проверили что у пользователя больше нет подписчиков
        assertEquals(userRepository.findById(testUserId).get().getSubscribers(), Collections.emptySet());
    }

    @Test
    @Transactional
    void eventTest(){
        // Сохранили тестового пользователя
        User testUser = userTest;
        userRepository.save(testUser);
        // Получили назначенный ID
        Long testUserId = testUser.getId();

        EventType testEventType = eventTypeTest;
        eventTypeRepository.save(testEventType);
        Long testEventTypeId = testEventType.getId();

        Status testStatus = statusTest;
        statusRepository.save(testStatus);
        Long testStatusId = testStatus.getId();


        // Создали новое мероприятие
        Event testEvent = eventTest;
        testEvent.setParticipantsEvent(Set.of(testUser));
        testEvent.setEventType(testEventType);
        testEvent.setAuthorId(testUser);
        testEvent.setStatus(testStatus);


        // Сохранили новое мероприятие
        eventRepository.save(testEvent);
        // Получили ID нового мероприятия
        Long testEventId = testEvent.getId();
        // Создали новое мероприятие и получили данные из тестового мероприятия из БД
        Event eventBD = eventRepository.getOne(testEventId);
        // Проверили данные на соответствие
        assertEquals(eventBD.getEventName(), "Football game");
        assertEquals(eventBD.getDescriptionEvent(), "Join people to play football math");
        assertEquals(eventBD.getPlaceEvent(), "Moscow");
        assertNotNull(eventBD.getTimeEvent());
        assertNotNull(eventBD.getParticipantsEvent());
        assertEquals(eventBD.getEventType(), eventTypeTest);
        assertEquals(eventBD.getAuthorId(), userTest);
        assertEquals(eventBD.getStatus(), statusTest);
        // Удалили мероприятие из БД
        eventRepository.deleteById(testEventId);
        // Проверили что мероприятие отсутствует в БД
        assertEquals(eventRepository.findById(testEventId), Optional.empty());
    }

    @Test
    void adminTestNull() {
        assertThrows(DataIntegrityViolationException.class,
                ()->{adminRepository.save(Admin.builder().build());
                });
    }

    @Test
    void moderatorTestNull(){
        assertThrows(DataIntegrityViolationException.class,
                ()->{moderatorRepository.save(Moderator.builder().build());
                });
    }

    @Test
    void userTestNull(){
        assertThrows(DataIntegrityViolationException.class,
                ()->{userRepository.save(User.builder().build());
                });
    }
}
