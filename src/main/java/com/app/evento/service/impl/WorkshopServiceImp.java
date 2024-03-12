package com.app.evento.service.impl;

import com.app.evento.dto.GuestDto;

import com.app.evento.models.Workshop;
import com.app.evento.repositories.GuestRepository;
import com.app.evento.repositories.UserRepository;
import com.app.evento.repositories.WorkshopRepository;
import com.app.evento.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopServiceImp implements WorkshopService {
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    WorkshopRepository workshopRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Workshop addWorkshop(Workshop workshop) {
        if (workshop == null) {
            throw new IllegalArgumentException("Workshop cannot be null");
        }
        workshop.getTrainees().forEach(guest -> {
            if (guestRepository.findById(guest.getId()).isEmpty()){
                try {
                    throw new Exception("cet invité avec cet id"+guest.getId()+"nexiste pas");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }  );
    return workshopRepository.save(workshop) ;
    }

    @Override
    public Workshop getWorkshop(String id) throws Exception {
        Optional<Workshop> workshop=workshopRepository.findById(id);
        if(workshop.isEmpty())
        {
            throw new Exception("pas de workshop avec cet Id"+id);

        }
        return workshop.get();
    }

    @Override
    public List<Workshop> getWorkshops() {
        return workshopRepository.findAll();
    }

    @Override
    public Workshop updateWorkShop(Workshop workshop, String id) throws Exception {
        Optional<Workshop> workshopfind=workshopRepository.findById(id);
        if(workshopfind.isEmpty())
        {
            throw new Exception("pas de workshop avec cet Id"+id);

        }
        Workshop workshopUpdate= workshopfind.get();
        if(workshop.getTrainees()!=null){
            workshop.getTrainees().forEach(guest -> {
                if (guestRepository.findById(guest.getId()).isEmpty()){
                    try {
                        throw new Exception("cet invité avec cet id"+guest.getId()+"nexiste pas");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }  );
            workshopUpdate.setTrainees(workshop.getTrainees());
        }
        if(workshop.getAgent()!=null){
            if (userRepository.findById(workshop.getAgent().getId()).isEmpty()){
                throw new Exception("pas de agent avec cet id"+workshop.getAgent().getId());
            }
            workshopUpdate.setAgent(workshop.getAgent());
        }
        if(workshop.getName()!=null){
            workshopUpdate.setName(workshop.getName());
        }
        if(workshop.getDescription()!=null){
            workshopUpdate.setDescription(workshop.getDescription());
        }
        if(workshop.getPrices()!=null){
            workshopUpdate.setPrices(workshop.getPrices());
        }
        if(workshop.getTrainerName()!=null){
            workshopUpdate.setTrainerName(workshop.getTrainerName());
        }
        return workshopRepository.save(workshopUpdate);
    }

    @Override
    public void DeleteWorkshop(String id) throws Exception {
        Optional<Workshop> workshop=workshopRepository.findById(id);
        if(workshop.isEmpty())
        {
            throw new Exception("pas de workshop avec cet Id "+id);

        }
        workshopRepository.deleteById(id);
    }

    @Override
    public boolean changeGuestPaymentStatus(String workShopId, String guestId, boolean pmtStatus) throws Exception {
        Optional<Workshop> workshop=workshopRepository.findById(workShopId);
        if(workshop.isEmpty())
        {
            throw new Exception("pas de workshop avec cet Id "+workShopId);

        }
        Workshop workshopPresent=workshop.get();
     Optional<GuestDto> guest= workshopPresent.getTrainees().stream().filter(guestDto->guestDto.getId().equals(guestId)).findFirst();
        guest.ifPresent(guestDto -> guestDto.setPaymentState(pmtStatus));
        workshopRepository.save(workshopPresent);
        return guest.get().isPaymentState();
    }

    @Override
    public boolean changeGuestPresence(String workShopId, String guestId, boolean presence) throws Exception {
        Optional<Workshop> workshop=workshopRepository.findById(workShopId);
        if(workshop.isEmpty())
        {
            throw new Exception("pas de workshop avec cet Id "+workShopId);

        }
        Workshop workshopPresent=workshop.get();
        Optional<GuestDto> guest=workshopPresent.getTrainees().stream().filter(guestDto->guestDto.getId().equals(guestId)).findFirst();
        guest.ifPresent(guestDto -> guestDto.setPresent(presence));
        workshopRepository.save(workshopPresent);
        return guest.get().isPresent();
    }
}
