package com.peep.pea.hotel.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.peep.pea.hotel.data.entity.Guest;
import com.peep.pea.hotel.data.entity.Reservation;
import com.peep.pea.hotel.data.entity.Room;
import com.peep.pea.hotel.data.repository.GuestRepository;
import com.peep.pea.hotel.data.repository.ReservationRepository;
import com.peep.pea.hotel.data.repository.RoomRepository;
import com.peep.pea.hotel.service.model.RoomReservation;

@Service
public class RoomReservationService {
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public RoomReservationService(GuestRepository guestRepository, RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(String reservationDate) {
        Date date = null;
        if (StringUtils.isNotEmpty(reservationDate)) {
            date = Date.valueOf(reservationDate);
        } else {
            date = new Date(new java.util.Date().getTime());
        }
        Map<Long, RoomReservation> roomReservations = new HashMap<>();
        List<Room> rooms = this.roomRepository.findAll();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservations.put(roomReservation.getRoomId(), roomReservation);
        });
        List<Reservation> reservations = this.reservationRepository.findAllByReservationDate(date);
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservations.get(reservation.getRoomId());
            roomReservation.setReservationId(reservation.getId());
            roomReservation.setReservationDate(reservation.getReservationDate().toString());
            Optional<Guest> guest = this.guestRepository.findById(reservation.getGuestId());
            roomReservation.setGuestId(guest.get().getId());
            roomReservation.setFirstName(guest.get().getFirstName());
            roomReservation.setLastName(guest.get().getLastName());
        });
        return roomReservations.values().stream().toList();
    }
}