package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Position;

import java.util.List;
import java.util.Optional;

/**
 * The interface provides access to service methods.
 */
public interface PositionService {

    /**
     * Find all positions.
     *
     * @return positions list.
     */
    List<Position> findAllPosition();

    /**
     * Find position by id.
     *
     * @param positionId position id.
     * @return position.
     */
    Optional<Position> findPositionById(Integer positionId);

    /**
     *  Find positions by order id.
     *
     * @param positionOrderId position order id.
     * @return positions list.
     */
    List<Position> findPositionByOrderId(Integer positionOrderId);

    /**
     * Create position.
     *
     * @param position position.
     * @return created position id.
     */
    Integer create(Position position);

    /**
     * Update position.
     *
     * @param position position.
     * @return number of updated records in the database.
     */
    int update(Position position);

    /**
     * Delete position by Id.
     *
     * @param positionId position Id.
     * @return the number of rows affected.
     */
    int delete(Integer positionId);
}
