package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

// Репозиторий для работы с вещями
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Получает все вещи пользователя по ID владельца
    List<Item> findByOwnerId(Long ownerId);

    // Ищет вещи по тексту в названии или описании (только доступные)
    @Query("SELECT i FROM Item i WHERE i.available = true AND " +
            "(LOWER(i.name) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%', :text, '%')))")
    List<Item> searchItems(@Param("text") String text);

    List<Item> findByRequestId(Long requestId);

    List<Item> findByRequestIdIn(List<Long> requestIds);
}