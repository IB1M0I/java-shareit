package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Репозиторий для работы с бронированиями
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Получает все бронирования пользователя
    List<Booking> findByBookerId(Long bookerId);

    // Получает все бронирования пользователя с указанным статусом
    List<Booking> findByBookerIdAndStatus(Long bookerId, BookingStatus status);

    // Получает прошлые бронирования пользователя (завершенные)
    @Query("SELECT b FROM Booking b WHERE b.booker.id = :userId AND b.end < CURRENT_TIMESTAMP")
    List<Booking> findPastByBookerId(@Param("userId") Long userId);

    // Получает текущие бронирования пользователя (активные)
    @Query("SELECT b FROM Booking b WHERE b.booker.id = :userId AND b.start <= CURRENT_TIMESTAMP AND b.end >= CURRENT_TIMESTAMP")
    List<Booking> findCurrentByBookerId(@Param("userId") Long userId);

    // Получает будущие бронирования пользователя (предстоящие)
    @Query("SELECT b FROM Booking b WHERE b.booker.id = :userId AND b.start > CURRENT_TIMESTAMP")
    List<Booking> findFutureByBookerId(@Param("userId") Long userId);

    // Получает все бронирования вещей владельца
    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId")
    List<Booking> findByItemOwnerId(@Param("ownerId") Long ownerId);

    // Получает бронирования вещей владельца с указанным статусом
    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.status = :status")
    List<Booking> findByItemOwnerIdAndStatus(@Param("ownerId") Long ownerId,
                                             @Param("status") BookingStatus status);

    // Получает прошлые бронирования вещей владельца
    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.end < CURRENT_TIMESTAMP")
    List<Booking> findPastByItemOwnerId(@Param("ownerId") Long ownerId);

    // Получает текущие бронирования вещей владельца
    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.start <= CURRENT_TIMESTAMP AND b.end >= CURRENT_TIMESTAMP")
    List<Booking> findCurrentByItemOwnerId(@Param("ownerId") Long ownerId);

    // Получает будущие бронирования вещей владельца
    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.start > CURRENT_TIMESTAMP")
    List<Booking> findFutureByItemOwnerId(@Param("ownerId") Long ownerId);

    // Получает бронирование с подгрузкой вещи
    @Query("SELECT b FROM Booking b JOIN FETCH b.item WHERE b.id = :bookingId")
    Optional<Booking> findByIdWithItem(@Param("bookingId") Long bookingId);

    // Получает все бронирования вещи
    List<Booking> findByItemId(Long itemId);

    // Проверяет наличие завершенного бронирования вещи пользователем
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.item.id = :itemId AND b.booker.id = :bookerId AND b.end < :now")
    boolean existsByItemIdAndBookerIdAndEndBefore(
            @Param("itemId") Long itemId,
            @Param("bookerId") Long bookerId,
            @Param("now") LocalDateTime now);

    // Проверяет наличие завершенного и подтвержденного бронирования вещи пользователем
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.item.id = :itemId AND b.booker.id = :bookerId AND b.end < :now AND b.status = 'APPROVED'")
    boolean existsByItemIdAndBookerIdAndEndBeforeAndStatusApproved(
            @Param("itemId") Long itemId,
            @Param("bookerId") Long bookerId,
            @Param("now") LocalDateTime now);
}

